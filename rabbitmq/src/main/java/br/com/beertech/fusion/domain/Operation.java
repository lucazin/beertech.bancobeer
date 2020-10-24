package br.com.beertech.fusion.domain;

import java.io.Serializable;
import java.util.Objects;


public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoOperacao;
    private Double valorOperacao;
    private String hash;
    private String debitCredit;
    private String authToken;

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public Double getValorOperacao() {
        return valorOperacao;
    }

    public void setValorOperacao(Double valorOperacao) {
        this.valorOperacao = valorOperacao;
    }

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}

	@Override
    public int hashCode() {
        return Objects.hash(tipoOperacao, valorOperacao, hash, debitCredit);
    }

    public String getAuthToken() { return authToken; }

    public void setAuthToken(String authToken) { this.authToken = authToken; }

}
