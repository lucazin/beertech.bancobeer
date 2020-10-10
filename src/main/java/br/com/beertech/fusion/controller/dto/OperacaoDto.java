package br.com.beertech.fusion.controller.dto;

import br.com.beertech.fusion.domain.OperationType;

public class OperacaoDto {

    private OperationType tipo;
    private Double valor;

    public OperationType getTipo() {
        return tipo;
    }

    public void setTipo(OperationType tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
