package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Perfil;

@Mapper.ReferenceModel(className = Perfil.class)
public class RestPerfil implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Mapper.CryptoRequired
    private String id;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("role")
    private String role;

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    
    
    

}
