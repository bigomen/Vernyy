package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Incidente;

@Mapper.ReferenceModel(className = Incidente.class)
public class RestHistoricoIncidente implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

	private Date data;
	private String observacao;
	private String acao;

	private String incId;
	private String itaId;
	
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getIncId() {
		return incId;
	}
	public void setIncId(String incId) {
		this.incId = incId;
	}
	public String getItaId() {
		return itaId;
	}
	public void setItaId(String itaId) {
		this.itaId = itaId;
	}
	
	
	
    
}
