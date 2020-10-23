package br.com.beertech.fusion.service.impl;

import br.com.beertech.fusion.controller.dto.CurrentAccountDTO;
import br.com.beertech.fusion.controller.dto.CurrentAccountUserDTO;
import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.domain.UsersRoles;
import br.com.beertech.fusion.domain.security.roles.EnumRole;
import br.com.beertech.fusion.repository.CurrentAccountRepository;
import br.com.beertech.fusion.repository.CurrentAccountUserRepository;
import br.com.beertech.fusion.repository.UserRepository;
import br.com.beertech.fusion.repository.UserRoleRepository;
import br.com.beertech.fusion.service.CurrentAccountService;
import br.com.beertech.fusion.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository currentUserRepository;
    @Autowired
    private UserRoleRepository currentUserRoleRepository;

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
}
