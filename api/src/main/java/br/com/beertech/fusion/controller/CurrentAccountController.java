package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.controller.dto.CurrentAccountDTO;
import br.com.beertech.fusion.controller.dto.CurrentAccountUserDTO;
import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.service.CurrentAccountService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/beercoin/current-account")
public class CurrentAccountController {

  @Autowired private CurrentAccountService currentAccountService;

  @ApiIgnore
  @GetMapping("/allAccount")
  public ResponseEntity<List<CurrentAccount>> getAllAccounts() {
    return new ResponseEntity<>(currentAccountService.listAccounts(), HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<CurrentAccount> getAccountByIdOrHash(final Long id, final String hash) {
    return new ResponseEntity<>(currentAccountService.getAccount(id, hash), HttpStatus.OK);
  }

  @ApiIgnore
  @PostMapping()
  public ResponseEntity<CurrentAccount> saveCurrentAccount(
      @RequestBody @Valid final CurrentAccountDTO accountDTO) {
    return new ResponseEntity<>(currentAccountService.saveAccount(accountDTO), HttpStatus.CREATED);
  }
  
  @GetMapping("/accountList")
  public ResponseEntity<List<CurrentAccountUserDTO>> listAccountUser() {
    return new ResponseEntity<>(currentAccountService.findAllUser(), OK);
  }
}
