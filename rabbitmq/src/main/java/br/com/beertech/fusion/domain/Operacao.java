package br.com.beertech.fusion.domain;

import java.io.Serializable;
import java.util.Objects;


public class Operacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoOperacao;
    private Double valorOperacao;

    public Operacao() { }

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



    @Override
    public int hashCode() {
        return Objects.hash(tipoOperacao, valorOperacao);
    }

}
