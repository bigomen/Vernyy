package com.luxfacta.vernyy.api.rest;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Push;
import java.io.Serializable;

@Mapper.ReferenceModel(className = Push.class)
public class RestPush implements Serializable, IRestModel {

    private static final long serialVersionUID = 1L;

    private String id;

    private String token;

    private RestMorador morador;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RestMorador getMorador() {
        return morador;
    }

    public void setMorador(RestMorador morador) {
        this.morador = morador;
    }

}
