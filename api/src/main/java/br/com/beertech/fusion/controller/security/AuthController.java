package br.com.beertech.fusion.controller.security;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.controller.dto.CurrentAccountDTO;
import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.domain.security.request.LoginRequest;
import br.com.beertech.fusion.domain.security.request.SignupRequest;
import br.com.beertech.fusion.domain.security.response.JwtResponse;
import br.com.beertech.fusion.domain.security.response.MessageResponse;
import br.com.beertech.fusion.domain.security.roles.EnumRole;
import br.com.beertech.fusion.domain.security.roles.Role;
import br.com.beertech.fusion.repository.RoleRepository;
import br.com.beertech.fusion.repository.UserRepository;
import br.com.beertech.fusion.service.CurrentAccountService;
import br.com.beertech.fusion.service.security.jwt.JwtUtils;
import br.com.beertech.fusion.service.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/beercoin/auth")
public class AuthController {
	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private CurrentAccountService currentAccountService;

	@Autowired
    PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		 String roles = userDetails.getAuthorities().stream()
				 .map(item -> item.getAuthority()).findFirst().get().toString();
		
		String hash = currentAccountService.findByCnpj(userDetails.getCnpj()).map(CurrentAccount::getHash).orElse(null);
		
		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(),
												 hash,roles,userDetails.getName()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Erro: Usuário já existente"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Erro: E-mail já existente!"));
		}

		//Cria nova conta do cliente
		Users usuario = new Users(signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),
							 signUpRequest.getCnpj(),signUpRequest.getNome());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(EnumRole.ROLE_USER).orElseThrow(() -> new RuntimeException("Erro: Role não encontrada"));
		roles.add(userRole);

		usuario.setRoles(roles);
		userRepository.save(usuario);

		CurrentAccountDTO currentAccount = new CurrentAccountDTO();
		currentAccount.setCnpj(usuario.getCnpj());
		currentAccountService.saveAccount(currentAccount);

		return ResponseEntity.ok(new MessageResponse("Cliente cadastrado com sucesso!"));
	}
}
