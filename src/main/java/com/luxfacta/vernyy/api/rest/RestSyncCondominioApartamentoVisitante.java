package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.ApartamentoVisitante;


@Mapper.ReferenceModel(className = ApartamentoVisitante.class)
public class RestSyncCondominioApartamentoVisitante implements Serializable, IRestModel {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private String id;
    
    private String nome;
    
    private String empresa;

    private RestSyncCondominioApartamento apartamento;

    private List<RestSyncCondominioAgendamentoVisitante> agendamentoVisitanteList;

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

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public RestSyncCondominioApartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(RestSyncCondominioApartamento apartamento) {
		this.apartamento = apartamento;
	}

	public List<RestSyncCondominioAgendamentoVisitante> getAgendamentoVisitanteList() {
		return agendamentoVisitanteList;
	}

	public void setAgendamentoVisitanteList(List<RestSyncCondominioAgendamentoVisitante> agendamentoVisitanteList) {
		this.agendamentoVisitanteList = agendamentoVisitanteList;
	}
    
}
