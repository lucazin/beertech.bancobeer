package br.com.beertech.fusion.domain;

import java.io.Serializable;
import java.util.Objects;

public class operacao_saldo implements Serializable {

    private Double saldo;

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public operacao_saldo(Double saldo) {
        this.saldo = saldo;
    }


    @Override
    public int hashCode() {
        return Objects.hash(saldo);
    }

}
