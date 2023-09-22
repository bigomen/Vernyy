package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.ApartamentoFuncionario;

@Mapper.ReferenceModel(className = ApartamentoFuncionario.class)
public class RestSyncCondominioApartamentoFuncionario implements Serializable, IRestModel {
	
	@Serial
    private static final long serialVersionUID = 1L;
	
	private String id;

    private String nome;

    private String funcao;

    private Date dataContratacao;
    
    private String cronInicio1;

    private String cronFim1;

    private String cronInicio2;

    private String cronFim2;

    private String cronInicio3;

    private String cronFim3;

    private RestSyncCondominioApartamento apartamento;

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

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public Date getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
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

	public RestSyncCondominioApartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(RestSyncCondominioApartamento apartamento) {
		this.apartamento = apartamento;
	}

}
