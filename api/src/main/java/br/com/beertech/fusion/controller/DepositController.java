package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.DebitCreditType;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.service.PublishTransaction;

@RestController
@RequestMapping("/beercoin")
public class DepositController {

  @Autowired private PublishTransaction publishTransaction;

  @PostMapping("/deposits")
  @PreAuthorize("hasRole('ROLE_MODERATOR')")
  public ResponseEntity<Void> queueDeposit(
      @RequestBody OperationDTO operationDTO,
      @RequestHeader(value = "Authorization", required = false) String token) {

    publishTransaction.publishOperation(
        new OperationDTO(
            OperationType.DEPOSITO,
            operationDTO.getValorOperacao(),
            operationDTO.getHash(),
            DebitCreditType.CREDITO,
            token));
    return new ResponseEntity<>(ACCEPTED);
  }
}
