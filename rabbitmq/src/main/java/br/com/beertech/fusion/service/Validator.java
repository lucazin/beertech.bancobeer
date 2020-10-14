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

	public boolean ValidateResponseRMQ() {
		boolean passed = false;

		if (objectValidator.getTipoOperacao().equals("DEPOSITO") || objectValidator.getTipoOperacao().equals("SAQUE")) {
			if (objectValidator.getValorOperacao() > 0)
				passed = true;
		}

		return passed;
	}

	public boolean ValidateTransferResponseRMQ() {
		boolean passed = false;

		if (transferValidator.getHashOrigin() != null && transferValidator.getHashDestination()!= null
				&& transferValidator.getValue() > 0) {
			passed = true;
		}

		return passed;
	}
}
