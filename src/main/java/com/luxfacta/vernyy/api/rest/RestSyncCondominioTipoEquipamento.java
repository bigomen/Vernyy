package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.TipoEquipamento;

@Mapper.ReferenceModel(className = TipoEquipamento.class)
public class RestSyncCondominioTipoEquipamento implements Serializable, IRestModel {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private Long id;

    private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
