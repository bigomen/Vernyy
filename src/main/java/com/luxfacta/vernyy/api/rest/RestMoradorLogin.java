package com.luxfacta.vernyy.api.rest;

import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Morador;

@Mapper.ReferenceModel(className = Morador.class)
public class RestMoradorLogin implements Serializable, IRestModel {

	private static final long serialVersionUID = 1L;
    private String id;

    @IgnoreMapping
    private String senha;

    @IgnoreMapping
    private String senhaPanico;

    @IgnoreMapping
    private String telefoneCelular;
    
    @IgnoreMapping
    private Date dataNascimento;
    
    @IgnoreMapping
    private String cpf;
    
    @IgnoreMapping
    private String apartamento;
    
    @IgnoreMapping
    private String cep;
    
    private String fotoFrente;

    private String fotoDireita;

    private String fotoEsquerda;

    private String fotoAcima;

    private String fotoAbaixo;

    private String token;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaPanico() {
		return senhaPanico;
	}

	public void setSenhaPanico(String senhaPanico) {
		this.senhaPanico = senhaPanico;
	}



	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getApartamento() {
		return apartamento;
	}

	public void setApartamento(String apartamento) {
		this.apartamento = apartamento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getFotoFrente() {
		return fotoFrente;
	}

	public void setFotoFrente(String fotoFrente) {
		this.fotoFrente = fotoFrente;
	}

	public String getFotoDireita() {
		return fotoDireita;
	}

	public void setFotoDireita(String fotoDireita) {
		this.fotoDireita = fotoDireita;
	}

	public String getFotoEsquerda() {
		return fotoEsquerda;
	}

	public void setFotoEsquerda(String fotoEsquerda) {
		this.fotoEsquerda = fotoEsquerda;
	}

	public String getFotoAcima() {
		return fotoAcima;
	}

	public void setFotoAcima(String fotoAcima) {
		this.fotoAcima = fotoAcima;
	}

	public String getFotoAbaixo() {
		return fotoAbaixo;
	}

	public void setFotoAbaixo(String fotoAbaixo) {
		this.fotoAbaixo = fotoAbaixo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}



    
}
