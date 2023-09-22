package com.luxfacta.vernyy.api.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

import com.luxfacta.vernyy.api.base.IDatabaseModel;


/**
 * The persistent class for the INCIDENT_TYPE database table.
 * 
 */
@Entity
@Table(name="INCIDENT_TYPE")
public class TipoIncidente implements Serializable, IDatabaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="INT_ID")
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_INCIDENT_TYPE")
    @jakarta.persistence.SequenceGenerator(name = "SQ_INCIDENT_TYPE", sequenceName = "SQ_INCIDENT_TYPE" ,allocationSize = 1)
    @Mapper.CryptoRequired
	private Long id;

	@Column(name="INT_DESCRIPTION")
	private String descricao;

	@OneToMany(mappedBy="tipoIncidente")
	private List<Incidente> incidenteList;

	@OneToMany(mappedBy="tipoIncidente")
	private List<AcaoTipoIncidente> acaoTipoIncidenteList;

	
	public Incidente addIncident(Incidente incident) {
		getIncidenteList().add(incident);
		incident.setTipoIncidente(this);

		return incident;
	}

	public Incidente removeIncident(Incidente incident) {
		getIncidenteList().remove(incident);
		incident.setTipoIncidente(null);

		return incident;
	}

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

	public List<Incidente> getIncidenteList() {
		return incidenteList;
	}

	public void setIncidenteList(List<Incidente> incidentes) {
		this.incidenteList = incidentes;
	}

	public List<AcaoTipoIncidente> getAcaoTipoIncidenteList() {
		return this.acaoTipoIncidenteList;
	}

	public void setAcaoTipoIncidenteList(List<AcaoTipoIncidente> incidentTypeActions) {
		this.acaoTipoIncidenteList = incidentTypeActions;
	}

	public AcaoTipoIncidente addIncidentTypeAction(AcaoTipoIncidente incidentTypeAction) {
		getAcaoTipoIncidenteList().add(incidentTypeAction);
		incidentTypeAction.setTipoIncidente(this);

		return incidentTypeAction;
	}

	public AcaoTipoIncidente removeIncidentTypeAction(AcaoTipoIncidente incidentTypeAction) {
		getAcaoTipoIncidenteList().remove(incidentTypeAction);
		incidentTypeAction.setTipoIncidente(null);

		return incidentTypeAction;
	}

}