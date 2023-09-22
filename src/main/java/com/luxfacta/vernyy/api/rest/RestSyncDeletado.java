package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.SyncDeletado;

@Mapper.ReferenceModel(className = SyncDeletado.class)
public class RestSyncDeletado implements Serializable, IRestModel {
	
	@Serial
    private static final long serialVersionUID = 1L;

    private String id;
    
    private String idTipo;
    
    private String resId;

	private String visId;
    
    private String empId;
	
    private String aemId;
    
    private String cemId;
    
    private String vapId;
    
    private String vehId;
    
    private String areId;
    
    private Date dataAtualizacao;
    
    private String tipo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(String idTipo) {
		this.idTipo = idTipo;
	}
    
    public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getVisId() {
		return visId;
	}

	public void setVisId(String visId) {
		this.visId = visId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAemId() {
		return aemId;
	}

	public void setAemId(String aemId) {
		this.aemId = aemId;
	}

	public String getCemId() {
		return cemId;
	}

	public void setCemId(String cemId) {
		this.cemId = cemId;
	}

	public String getVapId() {
		return vapId;
	}

	public void setVapId(String vapId) {
		this.vapId = vapId;
	}

	public String getVehId() {
		return vehId;
	}

	public void setVehId(String vehId) {
		this.vehId = vehId;
	}

	public String getAreId() {
		return areId;
	}

	public void setAreId(String areId) {
		this.areId = areId;
	}

}
