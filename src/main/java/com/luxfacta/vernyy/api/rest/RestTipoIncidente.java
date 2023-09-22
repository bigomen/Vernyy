package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.TipoIncidente;

@Mapper.ReferenceModel(className = TipoIncidente.class)
public class RestTipoIncidente implements Serializable, IRestModel {

	@Serial
    private static final long serialVersionUID = 1L;

    private String id;
	private String descricao;

	private List<RestAcaoTipoIncidente> acaoTipoIncidenteList;

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

	public List<RestAcaoTipoIncidente> getAcaoTipoIncidenteList() {
		return acaoTipoIncidenteList;
	}

	public void setAcaoTipoIncidenteList(List<RestAcaoTipoIncidente> acaoTipoIncidenteList) {
		this.acaoTipoIncidenteList = acaoTipoIncidenteList;
	}

	
	
	
    
}
