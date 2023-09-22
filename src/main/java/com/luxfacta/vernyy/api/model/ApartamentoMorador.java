package com.luxfacta.vernyy.api.model;

import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "RESIDENT_APARTMENT")
public class ApartamentoMorador implements Serializable, IDatabaseModel {

    private static final long serialVersionUID = 1L;
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_RESIDENT_APARTMENT")
    @jakarta.persistence.SequenceGenerator(name = "SQ_RESIDENT_APARTMENT", sequenceName = "SQ_RESIDENT_APARTMENT" ,allocationSize = 1)
    @Basic(optional = false)
    @CryptoRequired
    @Column(name = "RAP_ID", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "RAP_OWNER", nullable = false)
    private Boolean proprietario;


    @Basic(optional = false)
    @Column(name = "RAP_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Basic(optional = false)
    @Column(name = "RAP_LAST_UPDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @JoinColumn(name = "APT_ID", referencedColumnName = "APT_ID", nullable = false, updatable = false, insertable = false)
    @ManyToOne(optional = false)
    private Apartamento apartamento;

    @JoinColumn(name = "RES_ID", referencedColumnName = "RES_ID", nullable = false)
    @ManyToOne(optional = false)
    private Morador morador;

    @Column(name = "APT_ID", nullable = false)
    @CryptoRequired
    private Long aptId;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getProprietario() {
        return proprietario;
    }

    public void setProprietario(Boolean proprietario) {
        this.proprietario = proprietario;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }

	public Long getAptId() {
		return aptId;
	}

	public void setAptId(Long aptId) {
		this.aptId = aptId;
	}


    
}
