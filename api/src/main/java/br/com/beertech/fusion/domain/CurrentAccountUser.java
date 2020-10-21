package br.com.beertech.fusion.domain;

import java.io.Serializable;
import java.util.Objects;

import br.com.beertech.fusion.controller.dto.CurrentAccountUserDTO;

public class CurrentAccountUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String hash;
	private String nome;
	private String email;
	private String cnpj;

	public CurrentAccountUser(CurrentAccountUserDTO currentAccountDTO) {
		this.hash = currentAccountDTO.getHash();
		this.cnpj = currentAccountDTO.getCnpj();
		this.email = currentAccountDTO.getEmail();
		this.nome = currentAccountDTO.getNome();
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hash, nome, email, cnpj);
	}

}
