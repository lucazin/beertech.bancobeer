package br.com.beertech.fusion.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "conta_corrente")
public class CurrentAccount implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
	private Long id;
	
	private String hash;

	private String agency;

	private String accountNumber;

	private int accountType;

	private double approvedLimit;

	private double usedLimit;

	private double balance;

	private String createdAt;

	private String documentCustomer;

	private int active;

	private String activeDateUpdate;

	public Long getIdConta() {
		return id;
	}

	public void setIdConta(Long idConta) {
		this.id = idConta;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}


	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public double getApprovedLimit() {
		return approvedLimit;
	}

	public void setApprovedLimit(double approvedLimit) {
		this.approvedLimit = approvedLimit;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getDocumentCustomer() {
		return documentCustomer;
	}

	public void setDocumentCustomer(String documentCustomer) {
		this.documentCustomer = documentCustomer;
	}

	public double getBalance() { return balance; }

	public void setBalance(double balance) { this.balance = balance; }

	public int getActive() { return active; }

	public void setActive(int active) { this.active = active; }

	public String getActiveDateUpdate() { return activeDateUpdate; }

	public void setActiveDateUpdate(String activeDateUpdate) { this.activeDateUpdate = activeDateUpdate; }

	public double getUsedLimit() { return usedLimit; }

	public void setUsedLimit(double usedLimit) { this.usedLimit = usedLimit; }
}
