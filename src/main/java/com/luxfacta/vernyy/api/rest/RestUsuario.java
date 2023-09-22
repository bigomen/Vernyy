package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.constants.Constantes;
import com.luxfacta.vernyy.api.model.Usuario;

import jakarta.validation.constraints.NotBlank;

@Mapper.ReferenceModel(className = Usuario.class)
public class RestUsuario implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Mapper.CryptoRequired
    private String id;

    @NotBlank(message = "Nome")
    private String nome;

    @JsonFormat(pattern = Constantes.PATTERN_DATA)
    private Date dataNascimento;

    private String cpf;

    @NotBlank(message = "Email")
    private String email;

    private String telefoneCelular;

    @JsonFormat(pattern = Constantes.PATTERN_DATA)
    @NotBlank(message = "Data contratacão")
    private Date dataContratacao;

    @JsonFormat(pattern = Constantes.PATTERN_DATA)
    private Date dataUltimoAcesso;

    private String proId;

    private List<RestCondominio> condominioList;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public Date getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}


	public List<RestCondominio> getCondominioList() {
		return condominioList;
	}

	public void setCondominioList(List<RestCondominio> condominioList) {
		this.condominioList = condominioList;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}
	
	
	
}
