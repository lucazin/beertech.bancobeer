package br.com.beertech.fusion.service.security.services;

import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.domain.collections.UserDocument;
import br.com.beertech.fusion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	UserRepository userRepository;

	public UserDetailsServiceImpl() { }

	public UserDetailsServiceImpl(UserRepository userRepository)
	{
		this.userRepository  = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDocument usuario = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

		return UserDetailsImpl.build(usuario);
	}

}
