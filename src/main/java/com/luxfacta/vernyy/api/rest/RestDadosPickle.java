package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

public class RestDadosPickle implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    //private String token;
    private String pickle;
    private Long id;
    
    /*
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	*/
    
	public String getPickle() {
		return pickle;
	}
	public void setPickle(String pickle) {
		this.pickle = pickle;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
    


}
