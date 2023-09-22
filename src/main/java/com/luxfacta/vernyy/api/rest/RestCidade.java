package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Cidade;

@Mapper.ReferenceModel(className = Cidade.class)
public class RestCidade implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String nome;

    private RestUf uf;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public RestUf getUf() {
        return uf;
    }

    public void setUf(RestUf uf) {
        this.uf = uf;
    }
}
