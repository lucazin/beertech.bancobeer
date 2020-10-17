package br.com.beertech.fusion.service.security;

import br.com.beertech.fusion.controller.dto.UserDto;
import br.com.beertech.fusion.domain.security.Usuarios;
import br.com.beertech.fusion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtService implements UserDetailsService {
	@Autowired
	private UsuarioRepository userDao;

	@Autowired
	private PasswordEncoder PasswordEncrypt;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuarios user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Usuario não encontrado: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	public Usuarios save(UserDto user) {
		Usuarios newUser = new Usuarios();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(PasswordEncrypt.encode(user.getPassword()));
		return userDao.save(newUser);
	}
}