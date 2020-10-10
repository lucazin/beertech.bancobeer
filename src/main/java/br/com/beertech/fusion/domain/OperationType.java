package br.com.beertech.fusion.domain;

public enum OperationType {

    DEPOSITO(1),
    SAQUE(2);

    public int ID;
    OperationType(int value) {
        ID = value;
    }
}
