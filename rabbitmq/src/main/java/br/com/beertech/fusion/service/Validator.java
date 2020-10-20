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

		if (objectValidator.getTypeOperation().equals("BANKDEPOSIT")
				|| objectValidator.getTypeOperation().equals("WITHDRAW")
				|| objectValidator.getTypeOperation().equals("TRANSFER"))
		{
			if (objectValidator.getValueOperation() > 0 &&
					(objectValidator.getTokenOperation() != null && !objectValidator.getTokenOperation().isEmpty()))
				passed = true;
		}
		return passed;
	}
	public boolean ValidateTransferResponseRMQ() {
		boolean passed = false;

		if (transferValidator.getTransferHashOrigin() != null && transferValidator.getTransferHashDestination()!= null
				&& transferValidator.getTrasferValue() > 0) {
			passed = true;
		}
		return passed;
	}
}
