package br.com.beertech.fusion.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.beertech.fusion.controller.dto.UserDTO;
import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.domain.User;
import br.com.beertech.fusion.domain.security.request.SignupRequest;
import br.com.beertech.fusion.domain.security.roles.EnumRole;
import br.com.beertech.fusion.domain.security.roles.Role;
import br.com.beertech.fusion.repository.RoleRepository;
import br.com.beertech.fusion.repository.UserRepository;
import br.com.beertech.fusion.service.CurrentAccountService;
import br.com.beertech.fusion.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final CurrentAccountService currentAccountService;
    private final UserRepository userRepository;

    public UserServiceImpl(RoleRepository roleRepository, PasswordEncoder encoder,
            CurrentAccountService currentAccountService, UserRepository userRepository) {
        super();
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.currentAccountService = currentAccountService;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO creatUser(SignupRequest signUpRequest) {
        // Cria nova conta geral do cliente
        User usuario = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getCnpj(), signUpRequest.getName(),
                signUpRequest.getPhone());

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
                case "user": // usuario comum
                    Role adminRole = roleRepository.findByName(EnumRole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Erro: Role não encontrada"));
                    roles.add(adminRole);

                    break;
                case "moderator": // administrador
                    Role modRole = roleRepository.findByName(EnumRole.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Erro: Role não encontrada"));
                    roles.add(modRole);

                    break;
                }
            });
        }

        usuario.setRoles(roles);
        CurrentAccount newAccount = currentAccountService.saveNewAccountRegister(signUpRequest, userRepository, usuario);
        if (newAccount.getAccountNumber().isEmpty()) {
            throw new RuntimeException("\"Ocorreu um erro ao criar a conta.");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setCnpj(usuario.getCnpj());
        userDTO.setEmail(usuario.getEmail());
        userDTO.setHashAccount(newAccount.getHash());
        userDTO.setRoles(usuario.getRoles().stream().map(r -> r.getName().toString()).collect(Collectors.toList()));
        return userDTO;
    }

}
