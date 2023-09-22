package com.luxfacta.vernyy.api.rest;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.AgendamentoVisitante;
import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.NotNull;

@Mapper.ReferenceModel(className = AgendamentoVisitante.class)
public class RestAgendamentoVisitante implements Serializable, IRestModel {

    private static final long serialVersionUID = 1L;
    
    private String id;
    
    @NotNull
    private Date dataInicio;
    
    @NotNull
    private Date dataFim;
    
    @NotNull
    private String vapId;

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

	public String getVapId() {
		return vapId;
	}

	public void setVapId(String vapId) {
		this.vapId = vapId;
	}



    
}
