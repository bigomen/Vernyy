package com.luxfacta.vernyy.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

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
@Table(name = "VISITANT")
public class Visitante implements Serializable, IDatabaseModel {

	
    private static final long serialVersionUID = 1L;

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_VISITANT")
    @jakarta.persistence.SequenceGenerator(name = "SQ_VISITANT", sequenceName = "SQ_VISITANT" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "VIS_ID", nullable = false)
    @CryptoRequired
    private Long id;

    @Column(name = "VIS_CPF", length = 11)
    private String cpf;

    @Basic(optional = false)
    @Column(name = "VIS_CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Basic(optional = false)
    @Column(name = "VIS_BLACK_LIST", nullable = false)
    private Boolean listaBloqueio;

    @Column(name = "VIS_BLACK_LIST_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataListaBloqueio;

    @Column(name = "VIS_BLACK_LIST_REASON", length = 256)
    private String motivoListaBloqueio;

    @Basic(optional = false)
    @Column(name = "VIS_TRAINED", nullable = false)
    private Boolean mapeado;

    
    @Column(name = "VIS_PICKLE")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String pickle;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "visitante")
    private List<ApartamentoVisitante> apartamentoVisitanteList = new ArrayList<>();

	@OneToMany(mappedBy="visitante")
	private List<Incidente> incidenteList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Boolean getListaBloqueio() {
        return listaBloqueio;
    }

    public void setListaBloqueio(Boolean listaBloqueio) {
        this.listaBloqueio = listaBloqueio;
    }

    public Date getDataListaBloqueio() {
        return dataListaBloqueio;
    }

    public void setDataListaBloqueio(Date dataListaBloqueio) {
        this.dataListaBloqueio = dataListaBloqueio;
    }

    public String getMotivoListaBloqueio() {
        return motivoListaBloqueio;
    }

    public void setMotivoListaBloqueio(String motivoListaBloqueio) {
        this.motivoListaBloqueio = motivoListaBloqueio;
    }

    public Boolean getMapeado() {
        return mapeado;
    }

    public void setMapeado(Boolean mapeado) {
        this.mapeado = mapeado;
    }

    public List<ApartamentoVisitante> getApartamentoVisitanteList() {
        return apartamentoVisitanteList;
    }

    public void setApartamentoVisitanteList(List<ApartamentoVisitante> apartamentoVisitanteList) {
        this.apartamentoVisitanteList = apartamentoVisitanteList;
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
