package br.com.beertech.fusion.service;

import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.exception.FusionException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    void updateUserRole(Long idUser);

    List<Users> listUsers();
}
