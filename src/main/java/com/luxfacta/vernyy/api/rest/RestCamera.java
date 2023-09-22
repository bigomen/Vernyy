package com.luxfacta.vernyy.api.rest;

import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Camera;

@Mapper.ReferenceModel(className = Camera.class)
public class RestCamera implements Serializable, IRestModel {

    private static final long serialVersionUID = 1L;

    private String id;

    private String descricao;

    private String autenticacao;

    private String areId;


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


    public String getAutenticacao() {
        return autenticacao;
    }

    public void setAutenticacao(String autenticacao) {
        this.autenticacao = autenticacao;
    }

	public String getAreId() {
		return areId;
	}

	public void setAreId(String areId) {
		this.areId = areId;
	}


    
    
}
