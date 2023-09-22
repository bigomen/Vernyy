package com.luxfacta.vernyy.api.model;

import java.io.Serializable;
import jakarta.persistence.*;

import java.util.List;



@Entity
@Table(name="INCIDENT_STATUS")
public class StatusIncidente implements Serializable, com.luxfacta.vernyy.api.base.IDatabaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="INS_ID")
	@CryptoRequired
	private Long id;

	@Column(name="INS_DESCRIPTION")
	private String descricao;

	@OneToMany(mappedBy="statusIncidente")
	private List<Incidente> incidenteList;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String insDescription) {
		this.descricao = insDescription;
	}

	public List<Incidente> getIncidenteList() {
		return this.incidenteList;
	}

	public void setIncidenteList(List<Incidente> incidentes) {
		this.incidenteList = incidentes;
	}

	public Incidente addIncident(Incidente incidente) {
		getIncidenteList().add(incidente);
		incidente.setStatusIncidente(this);

		return incidente;
	}

	public Incidente removeIncident(Incidente incidente) {
		getIncidenteList().remove(incidente);
		incidente.setStatusIncidente(null);

		return incidente;
	}

}