package com.luxfacta.vernyy.api.model;

import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name="INCIDENT_TYPE_ACTION")
public class AcaoTipoIncidente implements Serializable, IDatabaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ITA_ID")
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_INCIDENT_TYPE_ACTION")
    @jakarta.persistence.SequenceGenerator(name = "SQ_INCIDENT_TYPE_ACTION", sequenceName = "SQ_INCIDENT_TYPE_ACTION" ,allocationSize = 1)
    @Basic(optional = false)
	@CryptoRequired
	private Long id;

	
	@Column(name="ITA_ORDER")
	private Long ordem;

	@Column(name="ITA_ACTION")
	private String acao;
	
	@Column(name="INT_ID")
	@CryptoRequired
	private Long intId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INT_ID", referencedColumnName = "INT_ID", nullable = false, updatable = false, insertable = false)
	private TipoIncidente tipoIncidente;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Long getOrdem() {
		return ordem;
	}

	public void setOrdem(Long ordem) {
		this.ordem = ordem;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public TipoIncidente getTipoIncidente() {
		return this.tipoIncidente;
	}

	public void setTipoIncidente(TipoIncidente incidentType) {
		this.tipoIncidente = incidentType;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	
	
	
}