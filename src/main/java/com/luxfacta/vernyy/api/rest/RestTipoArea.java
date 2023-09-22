package com.luxfacta.vernyy.api.rest;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.TipoArea;
import java.io.Serializable;

@Mapper.ReferenceModel(className = TipoArea.class)
public class RestTipoArea implements Serializable, IRestModel {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String descricao;

    private Long permiteAgendamento;

    private Long permiteAberturaPorta;
    
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

	public Long getPermiteAgendamento() {
		return permiteAgendamento;
	}

	public void setPermiteAgendamento(Long permiteAgendamento) {
		this.permiteAgendamento = permiteAgendamento;
	}

	public Long getPermiteAberturaPorta() {
		return permiteAberturaPorta;
	}

	public void setPermiteAberturaPorta(Long permiteAberturaPorta) {
		this.permiteAberturaPorta = permiteAberturaPorta;
	}

    
    
}
