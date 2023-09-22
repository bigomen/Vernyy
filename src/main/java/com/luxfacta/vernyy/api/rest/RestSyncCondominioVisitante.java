package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Visitante;

@Mapper.ReferenceModel(className = Visitante.class)
public class RestSyncCondominioVisitante implements Serializable, IRestModel {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private String id;

    private Boolean listaBloqueio;

    private Date dataListaBloqueio;

    private String motivoListaBloqueio;

    private Boolean mapeado;
    
    private List<RestSyncCondominioApartamentoVisitante> apartamentoVisitanteList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Boolean getMapeado() {
		return mapeado;
	}

	public void setMapeado(Boolean mapeado) {
		this.mapeado = mapeado;
	}

	public List<RestSyncCondominioApartamentoVisitante> getApartamentoVisitanteList() {
		return apartamentoVisitanteList;
	}

	public void setApartamentoVisitanteList(List<RestSyncCondominioApartamentoVisitante> apartamentoVisitanteList) {
		this.apartamentoVisitanteList = apartamentoVisitanteList;
	}
}
