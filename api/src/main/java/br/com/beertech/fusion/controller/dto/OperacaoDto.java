package br.com.beertech.fusion.controller.dto;

import br.com.beertech.fusion.domain.OperationType;

public class OperacaoDto {

    private OperationType tipoOperacao;
    private Double valorOperacao;

    public OperacaoDto() {
    }
    
    public OperacaoDto(OperationType tipoOperacao, Double valorOperacao) {
        this.tipoOperacao = tipoOperacao;
        this.valorOperacao = valorOperacao;
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
}
