package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.AcaoTipoIncidente;

@Mapper.ReferenceModel(className = AcaoTipoIncidente.class)
public class RestAcaoTipoIncidente implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    
    
    private String id;
	private String acao;
	private String intId;
	private Long ordem;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getIntId() {
		return intId;
	}
	public void setIntId(String intId) {
		this.intId = intId;
	}
	public Long getOrdem() {
		return ordem;
	}
	public void setOrdem(Long ordem) {
		this.ordem = ordem;
	}

	
	
    
}
