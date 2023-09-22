package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Apartamento;

@Mapper.ReferenceModel(className = Apartamento.class)
public class RestSyncCondominioApartamento implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String numero;

    private String andar;
    
    private RestSyncCondominioBloco bloco;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAndar() {
		return andar;
	}

	public void setAndar(String andar) {
		this.andar = andar;
	}

	public RestSyncCondominioBloco getBloco() {
		return bloco;
	}

	public void setBloco(RestSyncCondominioBloco bloco) {
		this.bloco = bloco;
	}
    

}
