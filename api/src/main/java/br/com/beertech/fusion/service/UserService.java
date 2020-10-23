package br.com.beertech.fusion.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import br.com.beertech.fusion.domain.Users;

public interface UserService {

    void updateUserRole(Long idUser);

    List<Users> listUsers();
    
    public Optional<Users> userByToken(HttpServletRequest request);
}
