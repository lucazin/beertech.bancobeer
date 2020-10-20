package br.com.beertech.fusion.domain;

import java.io.Serializable;
import java.util.Objects;

public class Transfer implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String transferHashOrigin;
	private String transferHashDestination;
	private Double trasferValue;
	private String tokenOperation;

	public Transfer(){}

	public Transfer(String transferHashOrigin,String transferHashDestination,Double trasferValue,String tokenOperation)
	{
		this.transferHashOrigin = transferHashOrigin;
		this.transferHashDestination = transferHashDestination;
		this.trasferValue = trasferValue;
		this.tokenOperation = tokenOperation;
	}

	public String getTokenOperation() {
		return tokenOperation;
	}

	public void setTokenOperation(String tokenOperation) {
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
	
	@Override
    public int hashCode() {
        return Objects.hash(this.transferHashOrigin, this.transferHashDestination, this.trasferValue);
    }
}
