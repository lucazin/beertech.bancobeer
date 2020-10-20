package br.com.beertech.fusion.controller.dto;

public class ReportDTO {

    private String hashAccount;
    private int operationType;

    public String getHashAccount() {
        return hashAccount;
    }

    public void setHashAccount(String hashAccount) {
        this.hashAccount = hashAccount;
    }

    public int getOperationType() { return operationType; }

    public void setOperationType(int operationType) { this.operationType = operationType; }

}
