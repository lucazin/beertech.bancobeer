package br.com.beertech.fusion.domain;

public enum DebitCreditType {

    DEBITO("D"),
    CREDITO("C");

    public String id;

    DebitCreditType(String value) {
        id = value;
    }

    public static DebitCreditType getById(String id) {
        if (id.equals("D")) {
            return DEBITO;
        } else {
        	return CREDITO;
        }
        	
    }
}
