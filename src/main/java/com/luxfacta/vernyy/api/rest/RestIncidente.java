package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Incidente;

@Mapper.ReferenceModel(className = Incidente.class)
public class RestIncidente implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
	private String conId;

	private String usuId;
	private String resId;
    private String aptId;
    private String areId;
	
	private String visId;
	private String empId;

	private Long insId;
	private String intId;

	private String descricao;
	private String observacao;
	private Date dataSolucao;
	private Date dataAbertura;

	
	private RestUsuario usuario;
	private RestApartamento apartamento;
	private RestMorador morador;
	private RestArea area;
	private RestHistoricoIncidente historicoIncidenteList;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAptId() {
		return aptId;
	}
	public void setAptId(String aptId) {
		this.aptId = aptId;
	}
	public String getAreId() {
		return areId;
	}
	public void setAreId(String areId) {
		this.areId = areId;
	}
	public String getConId() {
		return conId;
	}
	public void setConId(String conId) {
		this.conId = conId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public Date getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Date getDataSolucao() {
		return dataSolucao;
	}
	public void setDataSolucao(Date dataSolucao) {
		this.dataSolucao = dataSolucao;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getUsuId() {
		return usuId;
	}
	public void setUsuId(String usuId) {
		this.usuId = usuId;
	}
	public String getVisId() {
		return visId;
	}
	public void setVisId(String visId) {
		this.visId = visId;
	}
	public Long getInsId() {
		return insId;
	}
	public void setInsId(Long insId) {
		this.insId = insId;
	}
	public String getIntId() {
		return intId;
	}
	public void setIntId(String intId) {
		this.intId = intId;
	}
	public RestUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(RestUsuario usuario) {
		this.usuario = usuario;
	}
	public RestApartamento getApartamento() {
		return apartamento;
	}
	public void setApartamento(RestApartamento apartamento) {
		this.apartamento = apartamento;
	}
	public RestMorador getMorador() {
		return morador;
	}
	public void setMorador(RestMorador morador) {
		this.morador = morador;
	}
	public RestArea getArea() {
		return area;
	}
	public void setArea(RestArea area) {
		this.area = area;
	}
    
	public RestHistoricoIncidente getHistoricoIncidenteList() {
		return historicoIncidenteList;
	}
	public void setHistoricoIncidenteList(RestHistoricoIncidente historicoIncidenteList) {
		this.historicoIncidenteList = historicoIncidenteList;
	}
	
	
    
}
