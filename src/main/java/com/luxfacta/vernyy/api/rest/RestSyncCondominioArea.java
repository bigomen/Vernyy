package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Area;


@Mapper.ReferenceModel(className = Area.class)
public class RestSyncCondominioArea implements Serializable, IRestModel {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private String id;

    private String descricao;

    private Boolean perigo;

    private String cronInicio1;

    private String cronFim1;

    private String cronInicio2;

    private String cronFim2;

    private String cronInicio3;

    private String cronFim3;

    private String macExterno;

    private String macInterno;
    
    private String urlExternoAbrir;
    
    private String urlExternoFechar;
    
    private String urlInternoAbrir;
    
    private String urlInternoFechar;
    
    private List<RestSyncCondominioAgendamentoArea> areaAgendamentoList;
    
    private RestSyncCondominioTipoArea tipoArea;
    
    private RestSyncCondominioTipoEquipamento tipoEquipamento;
    
    private List<RestSyncCondominioCamera> cameraList;
    
    private RestSyncCondominio condominio;

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

	public Boolean getPerigo() {
		return perigo;
	}

	public void setPerigo(Boolean perigo) {
		this.perigo = perigo;
	}

	public String getCronInicio1() {
		return cronInicio1;
	}

	public void setCronInicio1(String cronInicio1) {
		this.cronInicio1 = cronInicio1;
	}

	public String getCronFim1() {
		return cronFim1;
	}

	public void setCronFim1(String cronFim1) {
		this.cronFim1 = cronFim1;
	}

	public String getCronInicio2() {
		return cronInicio2;
	}

	public void setCronInicio2(String cronInicio2) {
		this.cronInicio2 = cronInicio2;
	}

	public String getCronFim2() {
		return cronFim2;
	}

	public void setCronFim2(String cronFim2) {
		this.cronFim2 = cronFim2;
	}

	public String getCronInicio3() {
		return cronInicio3;
	}

	public void setCronInicio3(String cronInicio3) {
		this.cronInicio3 = cronInicio3;
	}

	public String getCronFim3() {
		return cronFim3;
	}

	public void setCronFim3(String cronFim3) {
		this.cronFim3 = cronFim3;
	}

	public String getMacExterno() {
		return macExterno;
	}

	public void setMacExterno(String macExterno) {
		this.macExterno = macExterno;
	}

	public String getMacInterno() {
		return macInterno;
	}

	public void setMacInterno(String macInterno) {
		this.macInterno = macInterno;
	}

	public String getUrlExternoAbrir() {
		return urlExternoAbrir;
	}

	public void setUrlExternoAbrir(String urlExternoAbrir) {
		this.urlExternoAbrir = urlExternoAbrir;
	}

	public String getUrlExternoFechar() {
		return urlExternoFechar;
	}

	public void setUrlExternoFechar(String urlExternoFechar) {
		this.urlExternoFechar = urlExternoFechar;
	}

	public String getUrlInternoAbrir() {
		return urlInternoAbrir;
	}

	public void setUrlInternoAbrir(String urlInternoAbrir) {
		this.urlInternoAbrir = urlInternoAbrir;
	}

	public String getUrlInternoFechar() {
		return urlInternoFechar;
	}

	public void setUrlInternoFechar(String urlInternoFechar) {
		this.urlInternoFechar = urlInternoFechar;
	}

	public List<RestSyncCondominioAgendamentoArea> getAreaAgendamentoList() {
		return areaAgendamentoList;
	}

	public void setAreaAgendamentoList(List<RestSyncCondominioAgendamentoArea> areaAgendamentoList) {
		this.areaAgendamentoList = areaAgendamentoList;
	}

	public RestSyncCondominioTipoArea getTipoArea() {
		return tipoArea;
	}

	public void setTipoArea(RestSyncCondominioTipoArea tipoArea) {
		this.tipoArea = tipoArea;
	}

	public RestSyncCondominioTipoEquipamento getTipoEquipamento() {
		return tipoEquipamento;
	}

	public void setTipoEquipamento(RestSyncCondominioTipoEquipamento tipoEquipamento) {
		this.tipoEquipamento = tipoEquipamento;
	}

	public List<RestSyncCondominioCamera> getCameraList() {
		return cameraList;
	}

	public void setCameraList(List<RestSyncCondominioCamera> cameraList) {
		this.cameraList = cameraList;
	}

	public RestSyncCondominio getCondominio() {
		return condominio;
	}

	public void setCondominio(RestSyncCondominio condominio) {
		this.condominio = condominio;
	}
}
