package br.com.beertech.fusion.service;

import java.util.List;
import java.util.Optional;

import br.com.beertech.fusion.controller.dto.RoleDTO;
import br.com.beertech.fusion.domain.entities.User;
import br.com.beertech.fusion.exception.FusionException;

public interface UserService {

    void updateUserRole(RoleDTO Roledto);

    List<User> listUsers();

    Optional<User> findUserByToken(String token);

    void validateUserLogged(String token, String hash) throws FusionException;
}
