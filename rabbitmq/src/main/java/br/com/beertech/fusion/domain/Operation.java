package br.com.beertech.fusion.domain;

import java.io.Serializable;
import java.util.Objects;


public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoOperacao;
    private Double valorOperacao;
    private String hash;

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
	
    @Override
    public int hashCode() {
        return Objects.hash(tipoOperacao, valorOperacao, hash);
    }
}
