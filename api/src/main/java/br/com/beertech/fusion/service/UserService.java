package br.com.beertech.fusion.service;

import br.com.beertech.fusion.controller.dto.UserDTO;
import br.com.beertech.fusion.domain.security.request.SignupRequest;;

public interface UserService {

    public UserDTO creatUser(SignupRequest signUpRequest);
}
