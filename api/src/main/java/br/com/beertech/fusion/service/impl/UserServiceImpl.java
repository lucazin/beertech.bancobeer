package br.com.beertech.fusion.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.domain.UsersRoles;
import br.com.beertech.fusion.domain.security.roles.EnumRole;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.repository.UserRepository;
import br.com.beertech.fusion.repository.UserRoleRepository;
import br.com.beertech.fusion.service.UserService;
import br.com.beertech.fusion.service.security.jwt.JwtUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository currentUserRepository;

	@Autowired
	private UserRoleRepository currentUserRoleRepository;

	@Autowired
    private JwtUtils jwtUtils;

	@Autowired
	private CurrentAccountUserRepositoryImpl currentAccountUserRepositoryImpl;

	@Override
	public void updateUserRole(Long idUser) {
		UsersRoles currentRole = currentUserRoleRepository.findUsersRolesByUsuariosId(idUser);
		currentRole.setRoleId(EnumRole.ROLE_MODERATOR.id);
		currentUserRoleRepository.save(currentRole);
	}

	@Override
	public List<Users> listUsers() {
		return currentUserRepository.findAllBy();
	}

	public Optional<Users> userByToken(HttpServletRequest request) {

		String tokenComplete = request.getHeader("Authorization");
		String token = tokenComplete.substring(7, tokenComplete.length());
		String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(token);

		return currentUserRepository.findByUsername(userNameFromJwtToken);
	}
	

    @Override
    public Optional<Users> getUserByToken(String tokenComplete) {
        // TODO Auto-generated method stub
        String token = tokenComplete.substring(7, tokenComplete.length());
        String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(token);

        return currentUserRepository.findByUsername(userNameFromJwtToken);
	}

	public Optional<Users> findUserByToken(String token) {

		token = token.substring(7, token.length());
		String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(token);

		return currentUserRepository.findByUsername(userNameFromJwtToken);
	}

	public void validateUserLogged(String token, String hash) throws FusionException {

		Optional<Users> userByToken = findUserByToken(token);

		if (userByToken.isPresent()) {

			Optional<Users> user = currentUserRepository.findByUsername(userByToken.get().getUsername());
			String hashUser = currentAccountUserRepositoryImpl.findAccountByUser(user.get().getUsername());

			if (!hashUser.equals(hash)) {
				throw new FusionException("Conta informada não corresponde à conta do usuário!");
			}
		} else {
			throw new FusionException("Usuário inexistente!");
		}

	}
}
