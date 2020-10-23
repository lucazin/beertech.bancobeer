package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.domain.security.response.MessageResponse;
import br.com.beertech.fusion.service.UserService;

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
