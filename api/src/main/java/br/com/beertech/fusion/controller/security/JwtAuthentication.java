package br.com.beertech.fusion.controller.security;

import br.com.beertech.fusion.controller.dto.UsuarioDto;
import br.com.beertech.fusion.controller.security.config.JwtTokenUtil;
import br.com.beertech.fusion.domain.security.JwtRequest;
import br.com.beertech.fusion.domain.security.JwtResponse;
import br.com.beertech.fusion.service.security.JwtService;
import br.com.beertech.fusion.util.Support;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/bankbeer")
public class JwtAuthentication {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtService userDetailsService;

	@PostMapping(value = "/autoriza")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping(value = "/registra")
	public ResponseEntity<?> saveUser(@RequestBody UsuarioDto user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("Usuario Desabilitado", e);
		} catch (BadCredentialsException e) {
			throw new Exception("Crendenciais Inválidas", e);
		}
	}
}
