package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Mapper.ReferenceModel(className = Usuario.class)
public class RestLogin implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Mapper.CryptoRequired
    private String id;

    @JsonProperty("nome")
    @NotBlank(message = "Nome")
    private String nome;


    @JsonProperty("perfil")
    @NotNull(message = "Perfil")
    private RestPerfil perfil;


	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public RestPerfil getPerfil() {
		return perfil;
	}


	public void setPerfil(RestPerfil perfil) {
		this.perfil = perfil;
	}

	


}
