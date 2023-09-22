package com.luxfacta.vernyy.api.rest;

import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Veiculo;

import jakarta.validation.constraints.NotNull;

@Mapper.ReferenceModel(className = Veiculo.class)
public class RestVeiculo implements Serializable, IRestModel {

    private static final long serialVersionUID = 1L;
    private String id;

    private String rfid;

    @NotNull
    private String placa;

    @NotNull
    private String fabricante;

    @NotNull
    private String modelo;

    private String cor;

    @NotNull
    private String aptId;


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


	public String getAptId() {
		return aptId;
	}

	public void setAptId(String aptId) {
		this.aptId = aptId;
	}

    /*
	public RestApartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(RestApartamento apartamento) {
		this.apartamento = apartamento;
	}
	*/


    
}
