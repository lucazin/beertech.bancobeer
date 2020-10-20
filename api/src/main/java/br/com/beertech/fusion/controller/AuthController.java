package br.com.beertech.fusion.controller;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.domain.CurrentAccountDocument;
import br.com.beertech.fusion.domain.MessageResponse;
import br.com.beertech.fusion.domain.SignupRequest;
import br.com.beertech.fusion.domain.UserDocument;
import br.com.beertech.fusion.service.CurrentAccountService;

@RestController
@RequestMapping("/bankbeer/auth")
public class AuthController {

	@Autowired
	private CurrentAccountService currentAccountService;


	@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

		//Cria nova conta do cliente
		UserDocument usuario = new UserDocument(signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
				  			 signUpRequest.getPassword(),
							 signUpRequest.getCnpj(),signUpRequest.getNome());


		Set<String> strRoles = signUpRequest.getRole();

		usuario.setRoles(new ArrayList<>(strRoles));

		currentAccountService.SaveUser(usuario);

		CurrentAccountDocument currentAccount = new CurrentAccountDocument();
		currentAccount.setCnpj(usuario.getCnpj());
		currentAccountService.saveAccount(currentAccount);

		return ResponseEntity.ok(new MessageResponse("Cliente cadastrado com sucesso!"));
	}
}
