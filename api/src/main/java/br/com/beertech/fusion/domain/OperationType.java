package br.com.beertech.fusion.domain;

public enum OperationType {

    DEPOSITO(1),
    SAQUE(2);

    public int ID;

    OperationType(int value) {
        ID = value;
    }

    public static OperationType getById(int id) {
        if (id == 1) {
            return DEPOSITO;
        } else {
            return SAQUE;
        }
    }
}
