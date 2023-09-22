package com.luxfacta.vernyy.api.model;

import java.io.Serializable;
import jakarta.persistence.*;


import java.util.Date;



@Entity
@Table(name="INCIDENT_HISTORY")
public class HistoricoIncidente implements Serializable, com.luxfacta.vernyy.api.base.IDatabaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="INH_ID")
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_INCIDENT_HISTORY")
    @jakarta.persistence.SequenceGenerator(name = "SQ_INCIDENT_HISTORY", sequenceName = "SQ_INCIDENT_HISTORY" ,allocationSize = 1)
	@CryptoRequired
	private Long id;

	@Column(name="INH_ACTION")
	private String acao;
	
	@Temporal(TemporalType.DATE)
	@Column(name="INH_CREATED_AT")
	private Date data;

	@Column(name="INH_NOTE")
	private String observacao;

	@Column(name="USE_ID")
	@CryptoRequired
	private Long useId;

	@Column(name="INC_ID")
	@CryptoRequired
	private Long incId;
	
	//bi-directional many-to-one association to Incident
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INC_ID", referencedColumnName = "INC_ID", nullable = false, updatable = false, insertable = false)
	private Incidente incidente;

	public Incidente getIncidente() {
		return this.incidente;
	}

	public void setIncident(Incidente incident) {
		this.incidente = incident;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getUseId() {
		return useId;
	}

	public void setUseId(Long useId) {
		this.useId = useId;
	}

	public Long getIncId() {
		return incId;
	}

	public void setIncId(Long incId) {
		this.incId = incId;
	}

	public void setIncidente(Incidente incidente) {
		this.incidente = incidente;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	
	
	
}