package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.ApartamentoFuncionario;

@Mapper.ReferenceModel(className = ApartamentoFuncionario.class)
public class RestApartamentoFuncionario implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;
    
    private String id;

    private String nome;

    private String fotoFrente;

    private String fotoDireita;

    private String fotoEsquerda;

    private String fotoAcima;

    private String fotoAbaixo;

    private String cronInicio1;

    private String cronFim1;

    private String cronInicio2;

    private String cronFim2;

    private String cronInicio3;

    private String cronFim3;

    private String aptId;

    private String cpf;

    private String email;

    private String funcao;

    private String telefoneCelular;

    private Date dataNascimento;

    private Date dataContratacao;
    
    @IgnoreMapping
    private String senha;
    
    @IgnoreMapping
    private String senhaPanico;

    @IgnoreMapping
    private String[] fotos;


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

	public String getAptId() {
		return aptId;
	}

	public void setAptId(String aptId) {
		this.aptId = aptId;
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

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
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

	public Date getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public String[] getFotos() {
		return fotos;
	}

	public void setFotos(String[] fotos) {
		this.fotos = fotos;
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

    
    
}
