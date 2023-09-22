package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Condominio;

import jakarta.validation.constraints.NotNull;

@Mapper.ReferenceModel(className = Condominio.class)
public class RestCondominio implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull
    private String nome;

    @NotNull
    private String logradouro;

    @NotNull
    private String numero;

    @NotNull
    private String cep;

    private int quantidadeVagasGaragem;

    private String ipExterno;
    private String ipInterno;

    private RestCidade cidade;

    @NotNull
    private String cidId;

    @IgnoreMapping
    private String ufId;

    @IgnoreMapping
    private String token;

    
    private List<RestBloco> blocoList;


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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getQuantidadeVagasGaragem() {
        return quantidadeVagasGaragem;
    }

    public void setQuantidadeVagasGaragem(int quantidadeVagasGaragem) {
        this.quantidadeVagasGaragem = quantidadeVagasGaragem;
    }

    public String getIpExterno() {
        return ipExterno;
    }

    public void setIpExterno(String ipExterno) {
        this.ipExterno = ipExterno;
    }

    public RestCidade getCidade() {
        return cidade;
    }

    public void setCidade(RestCidade cidade) {
        this.cidade = cidade;
    }

    public List<RestBloco> getBlocoList() {
        return blocoList;
    }

    public void setBlocoList(List<RestBloco> blocoList) {
        this.blocoList = blocoList;
    }

	public String getCidId() {
		return cidId;
	}

	public void setCidId(String cidId) {
		this.cidId = cidId;
	}

	public String getUfId() {
		return ufId;
	}

	public void setUfId(String ufId) {
		this.ufId = ufId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIpInterno() {
		return ipInterno;
	}

	public void setIpInterno(String ipInterno) {
		this.ipInterno = ipInterno;
	}

	
    
}
