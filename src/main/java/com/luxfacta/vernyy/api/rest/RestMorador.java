package com.luxfacta.vernyy.api.rest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Morador;

import jakarta.validation.constraints.NotNull;

@Mapper.ReferenceModel(className = Morador.class)
public class RestMorador implements Serializable, IRestModel {

	private static final long serialVersionUID = 1L;
    private String id;

    @NotNull
    private String nome;

    @NotNull
    private Date dataNascimento;

    private String email;

    private String telefoneCelular;

    @NotNull
    private String cpf;

    private String fotoFrente;

    private String fotoDireita;

    private String fotoEsquerda;

    private String fotoAcima;

    private String fotoAbaixo;

    private Boolean proprietario;

    @IgnoreMapping
    private String senha;

    @IgnoreMapping
    private String senhaPanico;
    
    @IgnoreMapping
    private String[] fotos;
    
    @IgnoreMapping
    private String aptId;

    @IgnoreMapping
    private String conId;
    
    @IgnoreMapping
    private Boolean permiteExclusao;

    private List<RestApartamentoMorador> listApartamentosCondominio;
    

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

	public Boolean getProprietario() {
		return proprietario;
	}

	public void setProprietario(Boolean proprietario) {
		this.proprietario = proprietario;
	}
	
	public String getAptId() {
		return aptId;
	}

	public void setAptId(String aptId) {
		this.aptId = aptId;
	}

	public Boolean getPermiteExclusao() {
		return permiteExclusao;
	}

	public void setPermiteExclusao(Boolean permiteExclusao) {
		this.permiteExclusao = permiteExclusao;
	}

	public List<RestApartamentoMorador> getListApartamentosCondominio() {
		return listApartamentosCondominio;
	}

	public void setListApartamentosCondominio(List<RestApartamentoMorador> listApartamentosCondominio) {
		this.listApartamentosCondominio = listApartamentosCondominio;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
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



    
}
