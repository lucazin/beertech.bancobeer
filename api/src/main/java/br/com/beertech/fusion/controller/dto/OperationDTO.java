package br.com.beertech.fusion.controller.dto;

import br.com.beertech.fusion.domain.OperationType;

public class OperationDTO {

    private OperationType tipoOperacao;
    private Double valorOperacao;
    private String hash;

    public OperationDTO() {
    }
    
    public OperationDTO(OperationType tipoOperacao, Double valorOperacao, String hash) {
        this.tipoOperacao = tipoOperacao;
        this.valorOperacao = valorOperacao;
        this.hash = hash;
    }
    
    public OperationType getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(OperationType tipoOperacao) {
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
}
