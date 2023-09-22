package com.luxfacta.vernyy.api.rest;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Contato;

import java.io.Serial;
import java.io.Serializable;

@Mapper.ReferenceModel(className = Contato.class)
public class RestContato implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;
    private String id;

    private String nome;

    private String numeroTelefone;

    private RestTipoTelefone tipoTelefone;


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

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }


    public RestTipoTelefone getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(RestTipoTelefone tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }


    
    
}
