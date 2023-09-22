package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Apartamento;

import jakarta.validation.constraints.NotNull;

@Mapper.ReferenceModel(className = Apartamento.class)
public class RestApartamento implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull
    private String numero;

    @NotNull
    private String andar;

    private int quantidadeVagasGaragem;

    @NotNull
    private String bloId;

    private String blocoNome;
    
    private String condominioNome;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAndar() {
        return andar;
    }

    public void setAndar(String andar) {
        this.andar = andar;
    }

    public int getQuantidadeVagasGaragem() {
        return quantidadeVagasGaragem;
    }

    public void setQuantidadeVagasGaragem(int quantidadeVagasGaragem) {
        this.quantidadeVagasGaragem = quantidadeVagasGaragem;
    }



	public String getBlocoNome() {
		return blocoNome;
	}

	public void setBlocoNome(String blocoNome) {
		this.blocoNome = blocoNome;
	}

	public String getCondominioNome() {
		return condominioNome;
	}

	public void setCondominioNome(String condominioNome) {
		this.condominioNome = condominioNome;
	}

	public String getBloId() {
		return bloId;
	}

	public void setBloId(String bloId) {
		this.bloId = bloId;
	}




}
