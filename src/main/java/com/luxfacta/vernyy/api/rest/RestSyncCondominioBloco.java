package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Bloco;

@Mapper.ReferenceModel(className = Bloco.class)
public class RestSyncCondominioBloco implements Serializable, IRestModel {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String descricao;
	
	private RestSyncCondominio condominio;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public RestSyncCondominio getCondominio() {
		return condominio;
	}

	public void setCondominio(RestSyncCondominio condominio) {
		this.condominio = condominio;
	}
	
	
}
