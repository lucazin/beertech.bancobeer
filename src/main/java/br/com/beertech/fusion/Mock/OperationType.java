package br.com.beertech.fusion.Mock;

public enum OperationType {

    DEPOSITO(1),
    SAQUE(2);

    public int ID;
    OperationType(int value) {
        ID = value;
    }
}
