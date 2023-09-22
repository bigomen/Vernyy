package com.luxfacta.vernyy.api.rest;

import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.TipoTelefone;

@Mapper.ReferenceModel(className = TipoTelefone.class)
public class RestTipoTelefone implements Serializable, IRestModel {

    private static final long serialVersionUID = 1L;
    private String id;

    private String descricao;


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


    
}
