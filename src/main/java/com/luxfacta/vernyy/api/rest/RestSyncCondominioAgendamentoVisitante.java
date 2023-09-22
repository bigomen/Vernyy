package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.AgendamentoVisitante;


@Mapper.ReferenceModel(className = AgendamentoVisitante.class)
public class RestSyncCondominioAgendamentoVisitante implements Serializable, IRestModel {
    
	@Serial
	private static final long serialVersionUID = 1L;
	
	private String id;
    
    private Date dataInicio;
    
    private Date dataFim;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
