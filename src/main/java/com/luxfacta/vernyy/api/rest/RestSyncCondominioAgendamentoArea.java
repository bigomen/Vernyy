package com.luxfacta.vernyy.api.rest;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.AgendamentoArea;


@Mapper.ReferenceModel(className = AgendamentoArea.class)
public class RestSyncCondominioAgendamentoArea implements Serializable, IRestModel {
	
	@Serial
	private static final long serialVersionUID = 1L;
    
    private String id;
    
    private Date dataInicio;
    
    private Date dataFim;
    
    private RestSyncCondominioApartamento apartamento;

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

	public RestSyncCondominioApartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(RestSyncCondominioApartamento apartamento) {
		this.apartamento = apartamento;
	}

}
