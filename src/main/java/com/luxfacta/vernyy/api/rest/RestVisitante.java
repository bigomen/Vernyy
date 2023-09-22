package com.luxfacta.vernyy.api.rest;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Visitante;
import java.io.Serializable;
import java.util.Date;

@Mapper.ReferenceModel(className = Visitante.class)
public class RestVisitante implements Serializable, IRestModel {

    private static final long serialVersionUID = 1L;

    private String id;

    private String cpf;

    private Date dataCriacao;

    private Boolean listaBloqueio;

    private Date dataListaBloqueio;

    private String motivoListaBloqueio;
  

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Boolean getListaBloqueio() {
        return listaBloqueio;
    }

    public void setListaBloqueio(Boolean listaBloqueio) {
        this.listaBloqueio = listaBloqueio;
    }

    public Date getDataListaBloqueio() {
        return dataListaBloqueio;
    }

    public void setDataListaBloqueio(Date dataListaBloqueio) {
        this.dataListaBloqueio = dataListaBloqueio;
    }

    public String getMotivoListaBloqueio() {
        return motivoListaBloqueio;
    }

    public void setMotivoListaBloqueio(String motivoListaBloqueio) {
        this.motivoListaBloqueio = motivoListaBloqueio;
    }


    
}
