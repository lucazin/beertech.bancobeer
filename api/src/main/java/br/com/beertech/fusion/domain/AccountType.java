package br.com.beertech.fusion.domain;

public enum AccountType {

    CORRENTE_POUPANCA(1),
    POUPANCA(2),
    CORRENTE(3);

    public int ID;

    AccountType(int value) {
        ID = value;
    }

    public static AccountType getById(int id) {
        if (id == 1) {
            return CORRENTE_POUPANCA;
        } else {
            return POUPANCA;
        }
    }
}
