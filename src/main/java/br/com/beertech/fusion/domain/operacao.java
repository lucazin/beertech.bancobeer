package br.com.beertech.fusion.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name="operacao")
public class operacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperacao;
    private int tipoOperacao;
    private Double valorOperacao;
    private String horarioOperacao;


    public operacao() {
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

    public  String GetDataAtual() {
        Date HoraDataAtual = null;
        SimpleDateFormat HoraFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date now = new Date();
        return HoraFormat.format(now);
    }
}
