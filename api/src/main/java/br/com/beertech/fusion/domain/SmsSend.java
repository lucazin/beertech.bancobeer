package br.com.beertech.fusion.domain;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class SmsSend implements Serializable {

    private static final long serialVersionUID = 1L;


    private int motivo;
    private String mensagem;
    private boolean sucesso;
    private int status;

    public SmsSend() { }

    public int getMotivo() {
        return motivo;
    }

    public void setMotivo(int motivo) {
        this.motivo = motivo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
