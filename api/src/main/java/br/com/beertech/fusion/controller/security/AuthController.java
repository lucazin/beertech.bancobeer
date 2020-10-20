package br.com.beertech.fusion.controller.security;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.controller.dto.UserDTO;
import br.com.beertech.fusion.domain.AccountType;
import br.com.beertech.fusion.domain.security.request.LoginRequest;
import br.com.beertech.fusion.domain.security.request.SignupRequest;
import br.com.beertech.fusion.domain.security.response.JwtResponse;
import br.com.beertech.fusion.domain.security.response.MessageResponse;
import br.com.beertech.fusion.repository.CurrentAccountRepository;
import br.com.beertech.fusion.repository.UserRepository;
import br.com.beertech.fusion.service.UserService;
import br.com.beertech.fusion.service.security.jwt.JwtUtils;
import br.com.beertech.fusion.service.security.services.UserDetailsImpl;
import br.com.beertech.fusion.util.SwaggerDoc;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/beercoin/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CurrentAccountRepository accountRepository;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
            CurrentAccountRepository accountRepository, JwtUtils jwtUtils, UserService userService) {
        super();
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/signin")
	@ApiOperation(value =  SwaggerDoc.signinTitle,  notes = SwaggerDoc.signinNotes)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(),
												 roles));
	}

	@PostMapping("/signup")
	@ApiOperation(value =  SwaggerDoc.signupTitle,  notes = SwaggerDoc.signupNotes)
    public CompletableFuture<ResponseEntity<UserDTO>> registerUserAccount(
            @Valid @RequestBody SignupRequest signUpRequest) throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity<UserDTO>> future = new CompletableFuture<>();
        try {
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity<UserDTO>>() {
                @Override
                public ResponseEntity get() {
                    if (!validateUserCreate(signUpRequest)) {
                        return ResponseEntity.badRequest()
                                .body(new MessageResponse("Erro: Este usuário já existe para este tipo de conta!"));}
                    if (!validateAccountCreate(signUpRequest)) {
                        return ResponseEntity.badRequest()
                                .body(new MessageResponse("Erro: Tipo de conta existente!"));}

                    UserDTO usuario = userService.creatUser(signUpRequest);
                    return new ResponseEntity<>(usuario, CREATED);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            future.complete(new ResponseEntity("Ocorreu um erro ao criar a conta.", INTERNAL_SERVER_ERROR));
        }
        return CompletableFuture.completedFuture(future.get());

	}

    public boolean validateUserCreate(SignupRequest signUpRequest) {
        int accountType = signUpRequest.getAccountType();
        return !userRepository.existsByUsernameAndAccountType(signUpRequest.getUsername(), accountType)
                && !userRepository.existsByEmailAndAccountType(signUpRequest.getEmail(), accountType)
                && !userRepository.existsByCnpjAndAccountType(signUpRequest.getCnpj(), accountType);
    }

    public boolean validateAccountCreate(SignupRequest signUpRequest) {
        int accountType = signUpRequest.getAccountType();
        return !(accountType == AccountType.CORRENTE.ID && accountRepository
                .existsBydocumentCustomerAndAccountType(signUpRequest.getCnpj(), AccountType.CORRENTE_POUPANCA.ID))
                && !((accountType == AccountType.CORRENTE_POUPANCA.ID || accountType == AccountType.POUPANCA.ID)
                        && accountRepository.existsBydocumentCustomerAndAccountType(signUpRequest.getCnpj(),
                                AccountType.POUPANCA.ID));
    }

}
