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

import br.com.beertech.fusion.controller.dto.OperacaoDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "operacao")
public class Operacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @JsonIgnore
    private Long idOperacao;

    private String horarioOperacao;

    private int tipoOperacao;
    private Double valorOperacao;



    public Operacao() {
    }

    public Operacao(OperacaoDto operacaoDto) {
        this.tipoOperacao = operacaoDto.getTipoOperacao().ID;
        this.valorOperacao = operacaoDto.getValorOperacao();
        this.horarioOperacao = getDataAtual();
    }

    @JsonIgnore
    public OperacaoDto getOperacaoDto() {
        return new OperacaoDto(OperationType.getById(this.tipoOperacao), this.valorOperacao);
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

    @Override
    public int hashCode() {
        return Objects.hash(idOperacao, tipoOperacao, valorOperacao);
    }

    private String getDataAtual() {
        SimpleDateFormat HoraFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date now = new Date();
        return HoraFormat.format(now);
    }
}
