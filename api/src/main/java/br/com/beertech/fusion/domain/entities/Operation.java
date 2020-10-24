package br.com.beertech.fusion.domain.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.DebitCreditType;
import br.com.beertech.fusion.domain.OperationType;

@Entity
@Table(name = "operation")
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String horarioOperacao; // FIXME mudar para Date ou Timestamp
    private int tipoOperacao;
    private Double valorOperacao;
    private String debitCredit; // FIXME Mudar para boolean ou bit

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_account_fk", nullable = false)
    private CurrentAccount currentAccount;

	public Operation() {
    }

    public Operation(OperationDTO operacaoDTO) {
        this.tipoOperacao = operacaoDTO.getTipoOperacao().ID;
        this.valorOperacao = operacaoDTO.getValorOperacao();
        this.horarioOperacao = getDataAtual();
    }

    public Operation(TransferDTO transferDTO, OperationType operationType, String hash, DebitCreditType debitCredit) {
    	this.tipoOperacao = operationType.ID;
    	this.valorOperacao = transferDTO.getValue();
    	this.horarioOperacao = getDataAtual();
//    	this.hash = hash; FIXME    	
    	this.debitCredit = debitCredit.id;
    }
    
    @JsonIgnore
    public OperationDTO getOperacaoDto() {
        return new OperationDTO(OperationType.getById(this.tipoOperacao), this.valorOperacao,this.hash, DebitCreditType.getById(this.debitCredit));
    }
    
    public String getHorarioOperacao() {
        return horarioOperacao;
    }

    public void setHorarioOperacao(String horarioOperacao) {
        this.horarioOperacao = horarioOperacao;
    }

    public Long getIdOperacao() {
        return id;
    }

    public void setIdOperacao(Long idOperacao) {
        this.id = idOperacao;
    }

    public int getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(int tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public Double getValorOperacao() {
        return valorOperacao;
    }

    public void setValorOperacao(Double valorOperacao) {
        this.valorOperacao = valorOperacao;
    }
    
    public String getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, tipoOperacao, valorOperacao, debitCredit);
    }

    private String getDataAtual() {
        SimpleDateFormat HoraFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date now = new Date();
        return HoraFormat.format(now);
    }

    public CurrentAccount getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(CurrentAccount currentAccount) {
        this.currentAccount = currentAccount;
    }
}
