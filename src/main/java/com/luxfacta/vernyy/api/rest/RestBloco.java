package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Bloco;

import jakarta.validation.constraints.NotNull;

@Mapper.ReferenceModel(className = Bloco.class)
public class RestBloco implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Mapper.CryptoRequired
    private String id;

    @NotNull
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
