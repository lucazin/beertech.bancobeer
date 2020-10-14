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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "operacao")
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long idOperacao;

    private String horarioOperacao;

    private int tipoOperacao;
    private Double valorOperacao;
    private String hash;

    public Operation() {
    }

    public Operation(OperationDTO operacaoDTO) {
        this.tipoOperacao = operacaoDTO.getTipoOperacao().ID;
        this.valorOperacao = operacaoDTO.getValorOperacao();
        this.horarioOperacao = getDataAtual();
        this.hash = operacaoDTO.getHash();
    }

    public Operation(TransferDTO transferDTO, OperationType operationType, String hash) {
    	this.tipoOperacao = operationType.ID;
    	this.valorOperacao = transferDTO.getValue();
    	this.horarioOperacao = getDataAtual();
    	this.hash = hash;    	
    }
    
    @JsonIgnore
    public OperationDTO getOperacaoDto() {
        return new OperationDTO(OperationType.getById(this.tipoOperacao), this.valorOperacao,this.hash);
    }
    
    public String getHorarioOperacao() {
        return horarioOperacao;
    }

    public void setHorarioOperacao(String horarioOperacao) {
        this.horarioOperacao = horarioOperacao;
    }

    public Long getIdOperacao() {
        return idOperacao;
    }

    public void setIdOperacao(Long idOperacao) {
        this.idOperacao = idOperacao;
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
    
    public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
    public int hashCode() {
        return Objects.hash(idOperacao, tipoOperacao, valorOperacao, hash);
    }

    private String getDataAtual() {
        SimpleDateFormat HoraFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date now = new Date();
        return HoraFormat.format(now);
    }
}
