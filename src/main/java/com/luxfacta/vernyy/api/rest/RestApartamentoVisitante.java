package com.luxfacta.vernyy.api.rest;

import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.ApartamentoVisitante;

import jakarta.validation.constraints.NotNull;

@Mapper.ReferenceModel(className = ApartamentoVisitante.class)
public class RestApartamentoVisitante implements Serializable, IRestModel {

    private static final long serialVersionUID = 1L;
    
    private String id;
    
    @NotNull
    private String nome;
    
    private String telefoneCelular;
    
    private String foto;
    
    private String empresa;

    private String cpf;
    
    @NotNull
    private String aptId;
  
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getAptId() {
		return aptId;
	}

	public void setAptId(String aptId) {
		this.aptId = aptId;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}



    
}
