package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.ApartamentoMorador;

@Mapper.ReferenceModel(className = ApartamentoMorador.class)
public class RestSyncCondominioApartamentoMorador implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;
    
    private String id;

    private Boolean proprietario;
    
    private RestSyncCondominioApartamento apartamento;

	public Boolean getProprietario() {
		return proprietario;
	}

	public void setProprietario(Boolean proprietario) {
		this.proprietario = proprietario;
	}

	public RestSyncCondominioApartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(RestSyncCondominioApartamento apartamento) {
		this.apartamento = apartamento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
    


}
