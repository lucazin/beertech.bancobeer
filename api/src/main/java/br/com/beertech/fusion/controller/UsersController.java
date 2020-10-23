package br.com.beertech.fusion.controller;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.domain.security.response.MessageResponse;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.service.OperationService;
import br.com.beertech.fusion.service.PublishTransaction;
import br.com.beertech.fusion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/beercoin")
public class UsersController {

  @Autowired
  private UserService userService;

  @GetMapping("/users/list")
  @PreAuthorize("hasRole('ROLE_MODERATOR')")
  public ResponseEntity<List<Users>> listUsers()
  {
    List<Users> users = userService.listUsers();
    return new ResponseEntity<>(users, ACCEPTED);
  }

  @PutMapping("/users/roleupdate/{idUser}")
  @PreAuthorize("hasRole('ROLE_MODERATOR')")
  public ResponseEntity<?> updateRole(@PathVariable(value = "idUser") Long idUser)
  {
      userService.updateUserRole(idUser);
      return ResponseEntity.ok(new MessageResponse("Permissão atualizada com sucesso!"));
  }

}
