package br.com.beertech.fusion.controller.dto;

import br.com.beertech.fusion.domain.OperationType;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TransferDTO {
	
	private String transferHashOrigin;
	private String transferHashDestination;
	private Double trasferValue;
	private String tokenOperation;

	public TransferDTO() {
	}

	public TransferDTO(String transferHashOrigin, String transferHashDestination, Double trasferValue,String tokenOperation) {
		this.transferHashOrigin = transferHashOrigin;
		this.transferHashDestination = transferHashDestination;
		this.trasferValue = trasferValue;
		this.tokenOperation = tokenOperation;
	}

	public String getTransferHashOrigin() {
		return transferHashOrigin;
	}

	public void setTransferHashOrigin(String transferHashOrigin) {
		this.transferHashOrigin = transferHashOrigin;
	}

	public String getTransferHashDestination() {
		return transferHashDestination;
	}

	public void setTransferHashDestination(String transferHashDestination) { this.transferHashDestination = transferHashDestination; }

	public Double getTrasferValue() {
		return trasferValue;
	}

	public void setTrasferValue(Double trasferValue) {
		this.trasferValue = trasferValue;
	}

	public String getTokenOperation() { return tokenOperation; }

	public void setTokenOperation(String tokenOperation) { this.tokenOperation = tokenOperation; }
}
