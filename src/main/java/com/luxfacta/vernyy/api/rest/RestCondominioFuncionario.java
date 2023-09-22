package com.luxfacta.vernyy.api.rest;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.CondominioFuncionario;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Mapper.ReferenceModel(className = CondominioFuncionario.class)
public class RestCondominioFuncionario implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Mapper.CryptoRequired
    private String id;

    private String cronInicio1;

    private String cronFim1;

    private String cronInicio2;

    private String cronFim2;

    private String cronInicio3;

    private String cronFim3;

    @Mapper.CryptoRequired
    private String conId;

    private String cpf;

    @Mapper.CryptoRequired
    private String empId;

    private List<RestArea> areaList;

    private Date dataNascimento;

    private String nome;

    private String email;

    private String telefoneCelular;

    private Date dataContratacao;

    private String funcao;

    @IgnoreMapping
    private String[] fotos;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public List<RestArea> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<RestArea> areaList) {
        this.areaList = areaList;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

   

    public String[] getFotos() {
		return fotos;
	}

	public void setFotos(String[] fotos) {
		this.fotos = fotos;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
