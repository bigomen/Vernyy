package com.luxfacta.vernyy.api.model;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "CAMERA")
public class Camera implements Serializable, IDatabaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_CAMERA")
    @jakarta.persistence.SequenceGenerator(name = "SQ_CAMERA", sequenceName = "SQ_CAMERA" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "CAM_ID", nullable = false)
    @CryptoRequired
    private Long id;

    @Column(name = "CAM_DESCRIPTION", length = 128, nullable = false)
    private String descricao;

    @Basic(optional = false)
    @Column(name = "CAM_CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Basic(optional = false)
    @Column(name = "CAM_LAST_UPDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Column(name = "CAM_CREDENTIALS", length = 256, nullable = false)
    private String autenticacao;

    @JoinColumn(name = "ARE_ID", referencedColumnName = "ARE_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne
    private Area area;

    @Column(name = "ARE_ID", nullable = false)
    @CryptoRequired
    private Long areId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getAutenticacao() {
        return autenticacao;
    }

    public void setAutenticacao(String autenticacao) {
        this.autenticacao = autenticacao;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

	public Long getAreId() {
		return areId;
	}

	public void setAreId(Long areId) {
		this.areId = areId;
	}


}
