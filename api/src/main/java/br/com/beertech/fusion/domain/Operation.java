package br.com.beertech.fusion.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;

import br.com.beertech.fusion.util.Support;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "operacao")
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String operationDate;
    private int operationType;
    private Double operationValue;
    private String operationHash;
    private String operationAgency;
    private String operationAccountNumber;

    public Operation() { }

    public Operation(OperationDTO operationDTO) {
        this.operationType = operationDTO.getTypeOperation().ID;
        this.operationValue = operationDTO.getValueOperation();
        this.operationDate = Support.getDataAtual();
        this.operationHash = operationDTO.getHashOperation();
        this.operationAgency = operationDTO.getAgencyOperation();
        this.operationAccountNumber = operationDTO.getAccountOperation();
    }

    public Operation(TransferDTO transferDTO, OperationType operationType, String hash) {
    	this.operationType = operationType.ID;
    	this.operationValue = transferDTO.getTrasferValue();
    	this.operationDate = Support.getDataAtual();
    	this.operationHash = hash;
    }
    
	@Override
    public int hashCode() {
        return Objects.hash(id, operationType, operationValue, operationHash);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public Double getOperationValue() {
        return operationValue;
    }

    public void setOperationValue(Double operationValue) {
        this.operationValue = operationValue;
    }

    public String getOperationHash() {
        return operationHash;
    }

    public void setOperationHash(String operationHash) { this.operationHash = operationHash; }

    public String getOperationAgency() { return operationAgency; }

    public void setOperationAgency(String operationAgency) { this.operationAgency = operationAgency; }

    public String getOperationAccountNumber() { return operationAccountNumber; }

    public void setOperationAccountNumber(String operationAccountNumber) { this.operationAccountNumber = operationAccountNumber; }
}
