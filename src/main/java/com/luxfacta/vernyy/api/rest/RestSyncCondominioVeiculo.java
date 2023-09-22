package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Veiculo;

@Mapper.ReferenceModel(className = Veiculo.class)
public class RestSyncCondominioVeiculo implements Serializable, IRestModel {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String rfid;
	
	private String placa;
	
	private String fabricante;
	
    private String modelo;

    private String cor;
    
    private RestSyncCondominioApartamento apartamento;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public RestSyncCondominioApartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(RestSyncCondominioApartamento apartamento) {
		this.apartamento = apartamento;
	}
    
}
