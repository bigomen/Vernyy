package com.luxfacta.vernyy.api.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.luxfacta.vernyy.api.base.IDatabaseModel;
import com.luxfacta.vernyy.api.shared.ImageUtils;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "RESIDENT")
public class Morador implements Serializable, IDatabaseModel {

    private static final long serialVersionUID = 1L;
    
    public void ajustaImagens() throws IOException {
        this.setFotoAbaixo((ImageUtils.scaleImage(this.getFotoAbaixo(), 600, 800)));
        this.setFotoAcima((ImageUtils.scaleImage(this.getFotoAcima(), 600, 800)));
        this.setFotoFrente((ImageUtils.scaleImage(this.getFotoFrente(), 600, 800)));
        this.setFotoEsquerda((ImageUtils.scaleImage(this.getFotoEsquerda(), 600, 800)));
        this.setFotoDireita((ImageUtils.scaleImage(this.getFotoDireita(), 600, 800)));
    }
    
    
    
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_RESIDENT")
    @jakarta.persistence.SequenceGenerator(name = "SQ_RESIDENT", sequenceName = "SQ_RESIDENT" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "RES_ID", nullable = false)
    @CryptoRequired
    private Long id;

    @Basic(optional = false)
    @Column(name = "RES_NAME", nullable = false, length = 128)
    private String nome;

    @Basic(optional = false)
    @Column(name = "RES_BIRTHDAY", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;

    @Column(name = "RES_EMAIL", length = 120)
    private String email;

    @Column(name = "RES_MOBILE_NUMBER", length = 20, nullable = true)
    private String telefoneCelular;

    @Column(name = "RES_CPF", length = 11, nullable = false)
    private String cpf;

    @Column(name = "RES_PHOTO_FRONT")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String fotoFrente;

    @Column(name = "RES_PHOTO_RIGHT")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String fotoDireita;

    @Column(name = "RES_PHOTO_LEFT")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String fotoEsquerda;

    @Column(name = "RES_PHOTO_TOP")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String fotoAcima;

    @Column(name = "RES_PHOTO_BOTTOM")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String fotoAbaixo;

    @Basic(optional = false)
    @Column(name = "RES_TRAINED", nullable = false)
    private Boolean mapeado;


    @Basic(optional = false)
    @Column(name = "RES_CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Basic(optional = false)
    @Column(name = "RES_PASSWORD", nullable = true, length = 100)
    private String senha;

    @Basic(optional = false)
    @Column(name = "RES_PANIC_PASSWORD", nullable = true, length = 100)
    private String senhaPanico;

    @Basic(optional = false)
    @Column(name = "RES_LAST_UPDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
    
    @Column(name = "RES_PICKLE")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String pickle;

    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "morador")
    private List<ApartamentoMorador> apartamentoMoradorList = new ArrayList<>();
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "morador")
    private List<Push> pushList;


	@OneToMany(mappedBy="morador")
	private List<Incidente> incidenteList;


    
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
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


	public Boolean getMapeado() {
		return mapeado;
	}


	public void setMapeado(Boolean mapeado) {
		this.mapeado = mapeado;
	}


	public Date getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
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


	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}


	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}


	public List<ApartamentoMorador> getApartamentoMoradorList() {
		return apartamentoMoradorList;
	}


	public void setApartamentoMoradorList(List<ApartamentoMorador> apartamentoMoradorList) {
		this.apartamentoMoradorList = apartamentoMoradorList;
	}


	public List<Push> getPushList() {
		return pushList;
	}


	public void setPushList(List<Push> pushList) {
		this.pushList = pushList;
	}

	public String getPickle() {
		return pickle;
	}

	public void setPickle(String pickle) {
		this.pickle = pickle;
	}


	public List<Incidente> getIncidenteList() {
		return incidenteList;
	}


	public void setIncidenteList(List<Incidente> incidenteList) {
		this.incidenteList = incidenteList;
	}

	
    
}
