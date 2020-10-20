package br.com.beertech.fusion.controller.security;

import br.com.beertech.fusion.domain.AccountType;
import br.com.beertech.fusion.domain.security.request.LoginRequest;
import br.com.beertech.fusion.domain.security.request.SignupRequest;
import br.com.beertech.fusion.domain.security.response.JwtResponse;
import br.com.beertech.fusion.domain.security.response.MessageResponse;
import br.com.beertech.fusion.domain.security.roles.EnumRole;
import br.com.beertech.fusion.domain.security.roles.Role;
import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.repository.CurrentAccountRepository;
import br.com.beertech.fusion.repository.RoleRepository;
import br.com.beertech.fusion.repository.UserRepository;
import br.com.beertech.fusion.service.CurrentAccountService;
import br.com.beertech.fusion.service.security.jwt.JwtUtils;
import br.com.beertech.fusion.service.security.services.UserDetailsImpl;
import br.com.beertech.fusion.util.SwaggerDoc;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/beercoin/auth")
public class AuthController {
	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CurrentAccountRepository AccountRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
    PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private CurrentAccountService currentAccountService;

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
	public CompletableFuture<ResponseEntity> registerUserAccount(@Valid @RequestBody SignupRequest signUpRequest)  throws ExecutionException, InterruptedException {

		CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
		try
		{
			future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>()
			{
				@Override
				public ResponseEntity get()
				{
					Users usuario = null;

					if(signUpRequest.getAccountType() == AccountType.CORRENTE_POUPANCA.ID)
					{
						if(!ValidateUserCreate(signUpRequest))
							return ResponseEntity
									.badRequest()
									.body(new MessageResponse("Erro: Este usuário já existe para este tipo de conta!"));
						else
						{
							if(!ValidateAccountCreate(signUpRequest))
							{
								return ResponseEntity
										.badRequest()
										.body(new MessageResponse("Erro: Tipo de conta existente!"));
							}
							else
							{
								usuario = RegisterUserandAccount(signUpRequest);

								if(!currentAccountService.saveNewAccountRegister(signUpRequest,userRepository,usuario).getAccountNumber().isEmpty())
									return new ResponseEntity<>("Nova conta criada!",CREATED);
								else
									return new ResponseEntity<>("Ocorreu um erro ao criar a conta.",INTERNAL_SERVER_ERROR);
							}
						}
					}
					else if(signUpRequest.getAccountType() == AccountType.POUPANCA.ID)
					{
						if(!ValidateUserCreate(signUpRequest))
							return ResponseEntity
									.badRequest()
									.body(new MessageResponse("Erro: Este usuário já existe para este tipo de conta!"));
						else
						{
							if(!ValidateAccountCreate(signUpRequest))
							{
								return ResponseEntity
										.badRequest()
										.body(new MessageResponse("Erro: Tipo de conta existente!"));
							}
							else
							{
								usuario = RegisterUserandAccount(signUpRequest);

								if(!currentAccountService.saveNewAccountRegister(signUpRequest,userRepository,usuario).getAccountNumber().isEmpty())
									return new ResponseEntity<>("Nova conta criada!",CREATED);
								else
									return new ResponseEntity<>("Ocorreu um erro ao criar a conta.",INTERNAL_SERVER_ERROR);
							}
						}
					}
					else
					{
						if(!ValidateUserCreate(signUpRequest))
							return ResponseEntity
									.badRequest()
									.body(new MessageResponse("Erro: Este usuário já existe para este tipo de conta!"));
						else
						{
							if(!ValidateAccountCreate(signUpRequest))
							{
								return ResponseEntity
										.badRequest()
										.body(new MessageResponse("Erro: Tipo de conta existente!"));
							}
							else
							{
								usuario = RegisterUserandAccount(signUpRequest);

								if(!currentAccountService.saveNewAccountRegister(signUpRequest,userRepository,usuario).getAccountNumber().isEmpty())
									return new ResponseEntity<>("Nova conta criada!",CREATED);
								else
									return new ResponseEntity<>("Ocorreu um erro ao criar a conta.",INTERNAL_SERVER_ERROR);
							}

						}
					}
				}
			});
		}
		catch (Exception e)
		{ e.printStackTrace(); }
		return CompletableFuture.completedFuture(future.get());

	}

	public boolean ValidateUserCreate(SignupRequest signUpRequest)
	{
		boolean valid = true;
		if (userRepository.existsByUsernameAndAccountType(signUpRequest.getUsername(),signUpRequest.getAccountType()))
			valid  = false;

		if (userRepository.existsByEmailAndAccountType(signUpRequest.getEmail(),signUpRequest.getAccountType()))
			valid  = false;

		if (userRepository.existsByCnpjAndAccountType(signUpRequest.getCnpj(),signUpRequest.getAccountType()))
			valid  = false;

			return valid;
	}

	public boolean ValidateAccountCreate(SignupRequest signUpRequest)
	{
		boolean valid = true;

		if(signUpRequest.getAccountType() == AccountType.CORRENTE.ID)
		{
			if (AccountRepository.existsBydocumentCustomerAndAccountType(signUpRequest.getCnpj(),AccountType.CORRENTE_POUPANCA.ID))
				valid  = false;
		}
		else if(signUpRequest.getAccountType() == AccountType.CORRENTE_POUPANCA.ID || signUpRequest.getAccountType() == AccountType.POUPANCA.ID)
		{
			if (AccountRepository.existsBydocumentCustomerAndAccountType(signUpRequest.getCnpj(),AccountType.POUPANCA.ID))
				valid  = false;
		}
		return valid;
	}

	public Users RegisterUserandAccount(SignupRequest signUpRequest)
	{
		//Cria nova conta geral do cliente
		Users usuario = new Users(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getCnpj(),signUpRequest.getName(),signUpRequest.getPhone());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		// verifica roles
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Erro: Role não encontrada"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "user": //usuario comum
						Role adminRole = roleRepository.findByName(EnumRole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Erro: Role não encontrada"));
						roles.add(adminRole);

						break;
					case "moderator": //administrador
						Role modRole = roleRepository.findByName(EnumRole.ROLE_MODERATOR)
								.orElseThrow(() -> new RuntimeException("Erro: Role não encontrada"));
						roles.add(modRole);

						break;
				}
			});
		}

		usuario.setRoles(roles);

		return usuario;
	}

}
