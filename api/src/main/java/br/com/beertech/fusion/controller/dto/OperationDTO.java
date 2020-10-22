package br.com.beertech.fusion.controller.dto;

import br.com.beertech.fusion.domain.DebitCreditType;
import br.com.beertech.fusion.domain.OperationType;
import io.swagger.annotations.ApiModelProperty;

public class OperationDTO {

  @ApiModelProperty(hidden = true)
  private OperationType tipoOperacao;
  private Double valorOperacao;
  private String hash;
  @ApiModelProperty(hidden = true)
  private DebitCreditType debitCredit;
  @ApiModelProperty(hidden = true)
  private String authToken;

    public OperationDTO() {
    }
    
    public OperationDTO(OperationType tipoOperacao, Double valorOperacao, String hash, DebitCreditType debitCredit) {
        this.tipoOperacao = tipoOperacao;
        this.valorOperacao = valorOperacao;
        this.hash = hash;
        this.debitCredit = debitCredit;
    }

    public OperationDTO(OperationType tipoOperacao, Double valorOperacao, String hash, DebitCreditType debitCredit,String authToken) {
        this.tipoOperacao = tipoOperacao;
        this.valorOperacao = valorOperacao;
        this.hash = hash;
        this.debitCredit = debitCredit;
        this.authToken = authToken;

    }


	public OperationType getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(OperationType tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public Double getValorOperacao() {
        return valorOperacao;
    }

    public void setValorOperacao(Double valorOperacao) {
        this.valorOperacao = valorOperacao;
    }
    
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    
    public DebitCreditType getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(DebitCreditType debitCredit) {
		this.debitCredit = debitCredit;
	}

    public String getAuthToken() { return authToken; }

    public void setAuthToken(String authToken) { this.authToken = authToken; }
}
