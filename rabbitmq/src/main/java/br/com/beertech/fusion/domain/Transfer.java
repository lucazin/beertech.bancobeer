package br.com.beertech.fusion.domain;

import java.io.Serializable;
import java.util.Objects;

public class Transfer implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String hashOrigin;
	private String hashDestination;
	private Double value;
    private String debitCredit;

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
	
	
	public String getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}

	@Override
    public int hashCode() {
        return Objects.hash(hashOrigin, hashDestination, value, debitCredit);
    }
}
