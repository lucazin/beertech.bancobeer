package br.com.beertech.fusion.service;

import java.util.List;
import java.util.Optional;

import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.exception.FusionException;

public interface UserService {

    void updateUserRole(Long idUser);

    List<Users> listUsers();

    Optional<Users> userByToken(String token) throws FusionException;

    void validateUserLogged(String token, String hash) throws FusionException;
}
