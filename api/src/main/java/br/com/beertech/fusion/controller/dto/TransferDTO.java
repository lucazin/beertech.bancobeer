package br.com.beertech.fusion.controller.dto;

import br.com.beertech.fusion.domain.DebitCreditType;

public class TransferDTO {
	
	private String hashOrigin;
	private String hashDestination;
	private Double value;
	private DebitCreditType debitCredit;
	private String authToken;


	public String getHashOrigin() {
		return hashOrigin;
	}

	public void setHashOrigin(String hashOrigin) {
		this.hashOrigin = hashOrigin;
	}

	public String getHashDestination() {
		return hashDestination;
	}

	public void setHashDestination(String hashDestination) {
		this.hashDestination = hashDestination;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public DebitCreditType getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(DebitCreditType debitCredit) {
		this.debitCredit = debitCredit;
	}

	public String getAuthToken() { return authToken; }

	public void setAuthToken(String authToken) { this.authToken = authToken; }
}
