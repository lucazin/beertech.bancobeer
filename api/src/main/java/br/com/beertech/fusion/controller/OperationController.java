package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.service.CurrentAccountService;
import br.com.beertech.fusion.service.OperationService;
import br.com.beertech.fusion.service.PublishTransaction;
import br.com.beertech.fusion.service.UserService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/beercoin")
public class OperationController {

  @Autowired private OperationService operationService;

  @Autowired private PublishTransaction publishTransaction;
  
  @Autowired
  private UserService userService;

  @Autowired
  private CurrentAccountService currentAccountService;

  @GetMapping("/bank-statement/")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR')")
  public ResponseEntity<List<Operation>> listUserBankStatement(
          @RequestHeader(value = "Authorization", required = false) String token) {
      Optional<Users> user = userService.getUserByToken(token);
      List<Operation> operations = operationService.listTransactionByCnpj(user.map(Users::getCnpj).orElseThrow(null));
      return new ResponseEntity<>(operations, OK);
  }

  @GetMapping("/balance/")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR')")
  public ResponseEntity<Balance> gettUserBalanceAccount(
          @RequestHeader(value = "Authorization", required = false) String token) {
      Optional<Users> user = userService.getUserByToken(token);
      Balance balance = operationService.calculateBalanceByCnpj(user.map(Users::getCnpj).orElseThrow(null));
      return new ResponseEntity<>(balance, OK);
  }

  @GetMapping("/bank-statement/{hash}")
  @PreAuthorize("hasRole('ROLE_MODERATOR')")
  public ResponseEntity<List<Operation>> listExtract(@PathVariable String hash) {
      Optional<CurrentAccount> currentAccount = currentAccountService.findByHash(hash);
      if (!currentAccount.isPresent()) {
          return ResponseEntity.badRequest().build();
      }
      List<Operation> operations = operationService.listTransactionByHash(hash);
    return new ResponseEntity<>(operations, OK);
  }

  @GetMapping("/balance/{hash}")
  @PreAuthorize("hasRole('ROLE_MODERATOR')")
  public ResponseEntity<Balance> listBalanceAccount(@PathVariable String hash) {
      Optional<CurrentAccount> currentAccount = currentAccountService.findByHash(hash);
      if (!currentAccount.isPresent()) {
          return ResponseEntity.badRequest().build();
      }
      Balance balance = operationService.calculateBalanceByHash(hash);
      return new ResponseEntity<>(balance, OK);
  }

  @PostMapping("/transfer")
  @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
  public ResponseEntity<Void> queueTransfer(
      @RequestHeader(value = "Authorization", required = false) String token,
      @RequestBody TransferDTO transferDTO) {

    transferDTO.setAuthToken(token);
    publishTransaction.publishTransfer(transferDTO);
    return new ResponseEntity<>(ACCEPTED);
  }

  @ApiIgnore
  @PostMapping("/operation/save")
  @PreAuthorize("hasRole('ROLE_MODERATOR')")
  public ResponseEntity<Operation> saveOperations(@RequestBody OperationDTO operationDTO) {

    Operation operation = new Operation(operationDTO);
    return new ResponseEntity<>(operationService.newTransaction(operation), OK);
  }

  @ApiIgnore
  @PostMapping("/transfer/save")
  @PreAuthorize("hasRole('ROLE_MODERATOR')")
  public ResponseEntity<TransferDTO> saveTransfer(@RequestBody TransferDTO transferDTO)
          throws FusionException {

    return new ResponseEntity<>(operationService.saveTransfer(transferDTO), OK);
  }
}
