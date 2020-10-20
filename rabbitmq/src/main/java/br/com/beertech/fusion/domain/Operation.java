package br.com.beertech.fusion.domain;

import java.io.Serializable;
import java.util.Objects;


public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String typeOperation;
    private Double valueOperation;
    private String hashOperation;
    private String agencyOperation;
    private String accountOperation;
    private String tokenOperation;

    public Operation(){}

    public Operation(String typeOperation, Double valueOperation, String hashOperation,
                     String agencyOperation,String accountOperation,String tokenOperation)
    {
        this.typeOperation = typeOperation;
        this.valueOperation = valueOperation;
        this.hashOperation = hashOperation;
        this.agencyOperation = agencyOperation;
        this.accountOperation = accountOperation;
        this.tokenOperation = tokenOperation;
    }

    public String getTokenOperation() {
        return tokenOperation;
    }

    public void setTokenOperation(String tokenOperation) {
        this.tokenOperation = tokenOperation;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Double getValueOperation() {
        return valueOperation;
    }

    public void setValueOperation(Double valueOperation) {
        this.valueOperation = valueOperation;
    }

    public String getHashOperation() {
        return hashOperation;
    }

    public void setHashOperation(String hashOperation) {
        this.hashOperation = hashOperation;
    }

    public String getAgencyOperation() {
        return agencyOperation;
    }

    public void setAgencyOperation(String agencyOperation) {
        this.agencyOperation = agencyOperation;
    }

    public String getAccountOperation() {
        return accountOperation;
    }

    public void setAccountOperation(String accountOperation) {
        this.accountOperation = accountOperation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOperation, valueOperation, hashOperation,agencyOperation,accountOperation);
    }
}
