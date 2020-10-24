package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;

import java.util.List;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.beertech.fusion.domain.entities.User;
import br.com.beertech.fusion.domain.security.response.MessageResponse;
import br.com.beertech.fusion.service.UserService;

@RestController
@RequestMapping("/beercoin")
public class UsersController {

  @Autowired
  private UserService userService;

  @GetMapping("/users/list")
  @PreAuthorize("hasRole('ROLE_MODERATOR')")
  public ResponseEntity<List<User>> listUsers()
  {
    List<User> users = userService.listUsers();
    return new ResponseEntity<>(users, ACCEPTED);
  }

  @PutMapping("/users/roleupdate/")
  @PreAuthorize("hasRole('ROLE_MODERATOR')")
  public ResponseEntity<?> updateRole(@RequestBody RoleDTO RoleDTO)
  {
    userService.updateUserRole(RoleDTO);
    return ResponseEntity.ok(new MessageResponse("Permissão atualizada com sucesso!"));
  }

}
