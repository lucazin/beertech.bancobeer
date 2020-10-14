package br.com.beertech.fusion.controller.dto;

public class TransferDTO {
	
	private String hashOrigin;
	
	private String hashDestination;
	
	private Double value;

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
}
