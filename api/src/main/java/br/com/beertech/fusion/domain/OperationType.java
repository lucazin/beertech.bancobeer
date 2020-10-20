package br.com.beertech.fusion.domain;

public enum OperationType {

    BANKDEPOSIT(1),
    WITHDRAW(2),
    TRANSFER(3),
    NONE(4);

    public int ID;

    OperationType(int value) {
        ID = value;
    }

    public static OperationType getById(int id) {
        if (id == 1) {
            return BANKDEPOSIT;
        }
        if (id == 2) {
            return WITHDRAW;
        }
        if (id == 3) {
            return TRANSFER;
        }
        else {
            return NONE;
        }
    }
}
