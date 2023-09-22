package com.luxfacta.vernyy.api.rest;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.AgendamentoArea;
import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.NotNull;

@Mapper.ReferenceModel(className = AgendamentoArea.class)
public class RestAgendamentoArea implements Serializable, IRestModel {

    private static final long serialVersionUID = 1L;
    
    private String id;
    
    @NotNull
    private Date dataInicio;
    
    @NotNull
    private Date dataFim;
    
    @NotNull
    private String areId;

    @NotNull
    private String aptId;

    private Boolean podeExcluir;
    
    
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

	public String getAreId() {
		return areId;
	}

	public void setAreId(String areId) {
		this.areId = areId;
	}

	public String getAptId() {
		return aptId;
	}

	public void setAptId(String aptId) {
		this.aptId = aptId;
	}

	public Boolean getPodeExcluir() {
		return podeExcluir;
	}

	public void setPodeExcluir(Boolean podeExcluir) {
		this.podeExcluir = podeExcluir;
	}


    
}
