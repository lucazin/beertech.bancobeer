package br.com.beertech.fusion.controller.dto;

import br.com.beertech.fusion.domain.OperationType;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OperationDTO {

    private OperationType typeOperation;
    private Double valueOperation;
    private String hashOperation;
    private String agencyOperation;
    private String accountOperation;

    private String tokenOperation;

    public OperationDTO() { }
    
    public OperationDTO(OperationType typeOperation, Double valueOperation, String hashOperation,
                        String agencyOperation,String AccountOperation,String tokenOperation)
    {
        this.typeOperation = typeOperation;
        this.valueOperation = valueOperation;
        this.hashOperation = hashOperation;
        this.agencyOperation = agencyOperation;
        this.accountOperation = AccountOperation;
        this.tokenOperation = tokenOperation;
    }

    public String getTokenOperation() {
        return tokenOperation;
    }

    public void setTokenOperation(String tokenOperation) {
        this.tokenOperation = tokenOperation;
    }

    public String getAgencyOperation() { return agencyOperation; }

    public void setAgencyOperation(String agencyOperation) { this.agencyOperation = agencyOperation; }

    public String getAccountOperation() { return accountOperation; }

    public void setAccountOperation(String accountOperation) { this.accountOperation = accountOperation; }

    public OperationType getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(OperationType typeOperation) {
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
}
