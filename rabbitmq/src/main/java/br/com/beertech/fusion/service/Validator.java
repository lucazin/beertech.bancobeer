package br.com.beertech.fusion.service;

import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.Transfer;

public class Validator {

  Operation objectValidator;
  Transfer transferValidator;

  public Validator(Operation objectValidatorParameter) {
    objectValidator = objectValidatorParameter;
  }

  public Validator(Transfer objectValidatorParameter) {
    transferValidator = objectValidatorParameter;
  }

  public boolean validateResponseRMQ() {

    return ((objectValidator.getTipoOperacao().equals("DEPOSITO")
            || objectValidator.getTipoOperacao().equals("SAQUE")) && objectValidator.getValorOperacao() > 0) && !objectValidator.getAuthToken().isEmpty();
  }

  public boolean validateTransferResponseRMQ() {

    return (transferValidator.getHashOrigin() != null && transferValidator.getHashDestination() != null && transferValidator.getValue() > 0)
                && !transferValidator.getAuthToken().isEmpty();
  }
}
