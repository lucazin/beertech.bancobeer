package br.com.beertech.fusion.controller.dto;

public class CurrentAccountUserDTO {

	private String hash;
	private String nome;
	private String email;
	private String cnpj;
	private String phonenumber;

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

	public String getPhonenumber() { return phonenumber; }

	public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }


}
