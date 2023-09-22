package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Morador;

@Mapper.ReferenceModel(className = Morador.class)
public class RestSyncCondominioMorador implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    
    private String nome;

    private String senha;

    private String senhaPanico;
    
    private Boolean mapeado;

	private List<RestSyncCondominioApartamentoMorador> apartamentoMoradorList = new ArrayList<>();

    public Boolean getMapeado() {
		return mapeado;
	}

	public void setMapeado(Boolean mapeado) {
		this.mapeado = mapeado;
	}

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaPanico() {
		return senhaPanico;
	}

	public void setSenhaPanico(String senhaPanico) {
		this.senhaPanico = senhaPanico;
	}

	public List<RestSyncCondominioApartamentoMorador> getApartamentoMoradorList() {
		return apartamentoMoradorList;
	}

	public void setApartamentoMoradorList(List<RestSyncCondominioApartamentoMorador> apartamentoMoradorList) {
		this.apartamentoMoradorList = apartamentoMoradorList;
	}
    

}
