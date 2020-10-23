package br.com.beertech.fusion.controller;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.DebitCreditType;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.service.PublishTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("/beercoin")
public class WithdrawController {

  @Autowired private PublishTransaction publishTransaction;

  @PostMapping("/withdrawals")
  @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
  public ResponseEntity<Void> queueWithdrawal(
      @RequestBody OperationDTO operationDTO,
      @RequestHeader(value = "Authorization", required = false) String token) {

    publishTransaction.publishOperation(
        new OperationDTO(
            OperationType.SAQUE,
            operationDTO.getValorOperacao(),
            operationDTO.getHash(),
            DebitCreditType.DEBITO,
            token));

    return new ResponseEntity<>(ACCEPTED);
  }
}
