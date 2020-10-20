package br.com.beertech.fusion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;

@Document
public class CurrentAccountDocument implements Serializable {

	private String hash;
	private String cnpj;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}
