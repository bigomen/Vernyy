package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.SyncPickleMorador;

@Mapper.ReferenceModel(className = SyncPickleMorador.class)
public class RestSyncPickleMorador implements Serializable, IRestModel {
	
	@Serial
    private static final long serialVersionUID = 1L;

    private String id;
    
    private String pickle;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPickle() {
		return pickle;
	}

	public void setPickle(String pickle) {
		this.pickle = pickle;
	}

}
