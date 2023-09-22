package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Funcionario;

@Mapper.ReferenceModel(className = Funcionario.class)
public class RestSyncCondominioFuncionario implements Serializable, IRestModel {
	
	@Serial
    private static final long serialVersionUID = 1L;
	
	private String id;

    private Boolean mapeado;

    private String senha;

    private String senhaPanico;

    private Boolean listaBloqueio;

    private Date dataListaBloqueio;

    private String motivoListaBloqueio;
    
    private List<RestSyncCondominioApartamentoFuncionario> apartamentoFuncionarioList = new ArrayList<>();
    
    private List<RestSyncCondominioFuncionarioCondominio> condominioFuncionarioList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getMapeado() {
		return mapeado;
	}

	public void setMapeado(Boolean mapeado) {
		this.mapeado = mapeado;
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

	public List<RestSyncCondominioApartamentoFuncionario> getApartamentoFuncionarioList() {
		return apartamentoFuncionarioList;
	}

	public void setApartamentoFuncionarioList(List<RestSyncCondominioApartamentoFuncionario> apartamentoFuncionarioList) {
		this.apartamentoFuncionarioList = apartamentoFuncionarioList;
	}

	public List<RestSyncCondominioFuncionarioCondominio> getCondominioFuncionarioList() {
		return condominioFuncionarioList;
	}

	public void setCondominioFuncionarioList(List<RestSyncCondominioFuncionarioCondominio> condominioFuncionarioList) {
		this.condominioFuncionarioList = condominioFuncionarioList;
	}

}
