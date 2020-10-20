package br.com.beertech.fusion.controller.dto;

public class BankDepositDTO {

    private Double valorOperacao;
    private String hashContaCorrente;

    public String getHashContaCorrente() {
        return hashContaCorrente;
    }

    public void setHashContaCorrente(String hashContaCorrente) {
        this.hashContaCorrente = hashContaCorrente;
    }

    public Double getValorOperacao() {
        return valorOperacao;
    }

    public void setValorOperacao(Double valorOperacao) {
        this.valorOperacao = valorOperacao;
    }
}
