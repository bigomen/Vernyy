package com.luxfacta.vernyy.api.rest;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.ApartamentoMorador;
import java.io.Serializable;
import java.util.Date;

@Mapper.ReferenceModel(className = ApartamentoMorador.class)
public class RestApartamentoMorador implements Serializable, IRestModel {

    private static final long serialVersionUID = 1L;
    private String id;

    private Boolean proprietario;

    private Boolean ativo;

    private Date dataCriacao;

    private Date dataAtualizacao;

    //private RestApartamento apartamento;

    //private RestMorador morador;

    private String aptId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getProprietario() {
        return proprietario;
    }

    public void setProprietario(Boolean proprietario) {
        this.proprietario = proprietario;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    /*
    public RestApartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(RestApartamento apartamento) {
        this.apartamento = apartamento;
    }

    public RestMorador getMorador() {
        return morador;
    }

    public void setMorador(RestMorador morador) {
        this.morador = morador;
    }
	*/
    
	public String getAptId() {
		return aptId;
	}

	public void setAptId(String aptId) {
		this.aptId = aptId;
	}

    
}
