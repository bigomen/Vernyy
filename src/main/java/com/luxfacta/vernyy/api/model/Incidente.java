package com.luxfacta.vernyy.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.Basic;


@Entity
@Table(name="INCIDENT")
public class Incidente implements Serializable, IDatabaseModel {
	private static final long serialVersionUID = 1L;
	
	@Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_INCIDENT")
    @jakarta.persistence.SequenceGenerator(name = "SQ_INCIDENT", sequenceName = "SQ_INCIDENT" ,allocationSize = 1)
    @Basic(optional = false)
	@Column(name="INC_ID", nullable = false)
	@CryptoRequired
	private Long id;

	@Column(name="APT_ID")
    @CryptoRequired
    private Long aptId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="APT_ID", referencedColumnName = "APT_ID", nullable = true, updatable = false, insertable = false)
	private Apartamento apartamento;

	
	@Column(name="ARE_ID")
    @CryptoRequired
    private Long areId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ARE_ID", referencedColumnName = "ARE_ID", nullable = true, updatable = false, insertable = false)
	private Area area;

	
	@Column(name="CON_ID")
    @CryptoRequired
	private Long conId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CON_ID", referencedColumnName = "CON_ID", nullable = false, updatable = false, insertable = false)
	private Condominio condominio;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EMP_ID", referencedColumnName = "EMP_ID", nullable = true, updatable = false, insertable = false)
	private Funcionario funcionario;

	@Column(name="EMP_ID")
    @CryptoRequired
	private Long empId;

	@Temporal(TemporalType.DATE)
	@Column(name="INC_DATE")
	private Date dataAbertura;

	@Column(name="INC_DESCRIPTION")
	private String descricao;

	@Column(name="INC_NOTE")
	private String observacao;

	@Temporal(TemporalType.DATE)
	@Column(name="INC_SOLVED_DATE")
	private Date dataSolucao;

	@Column(name="INC_VIEWED")
	private boolean visualizado;

	@Column(name="RES_ID")
    @CryptoRequired
	private Long resId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RES_ID", referencedColumnName = "RES_ID", nullable = true, updatable = false, insertable = false)
	private Morador morador;

	
	@Column(name="USE_ID")
    @CryptoRequired
	private Long usuId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USE_ID", referencedColumnName = "USE_ID", nullable = true, updatable = false, insertable = false)
	private Usuario usuario;
	
	 
	@Column(name="VIS_ID")
    @CryptoRequired
	private Long visId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="VIS_ID", referencedColumnName = "VIS_ID", nullable = true, updatable = false, insertable = false)
	private Visitante visitante;
	
	@Column(name="INS_ID")
	private Long insId;
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INS_ID", referencedColumnName = "INS_ID", nullable = false, updatable = false, insertable = false)
	private StatusIncidente statusIncidente;

	@Column(name="INT_ID")
    @CryptoRequired
	private Long intId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INT_ID", referencedColumnName = "INT_ID", nullable = false, updatable = false, insertable = false)
	private TipoIncidente tipoIncidente;

	@OneToMany(mappedBy="incidente")
	private List<HistoricoIncidente> historicoIncidenteList;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAptId() {
		return aptId;
	}

	public void setAptId(Long aptId) {
		this.aptId = aptId;
	}

	public Long getConId() {
		return conId;
	}

	public void setConId(Long conId) {
		this.conId = conId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataSolucao() {
		return dataSolucao;
	}

	public void setDataSolucao(Date dataSolucao) {
		this.dataSolucao = dataSolucao;
	}

	public boolean isVisualizado() {
		return visualizado;
	}

	public void setVisualizado(boolean visualizado) {
		this.visualizado = visualizado;
	}

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public Long getUsuId() {
		return usuId;
	}

	public void setUsuId(Long usuId) {
		this.usuId = usuId;
	}

	public Long getVisId() {
		return visId;
	}

	public void setVisId(Long visId) {
		this.visId = visId;
	}

	public Long getInsId() {
		return insId;
	}

	public void setInsId(Long insId) {
		this.insId = insId;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public StatusIncidente getStatusIncidente() {
		return this.statusIncidente;
	}

	public void setStatusIncidente(StatusIncidente incidentStatus) {
		this.statusIncidente = incidentStatus;
	}

	public TipoIncidente getTipoIncidente() {
		return this.tipoIncidente;
	}

	public void setTipoIncidente(TipoIncidente incidentType) {
		this.tipoIncidente = incidentType;
	}

	public List<HistoricoIncidente> getHistoricoIncidenteList() {
		return this.historicoIncidenteList;
	}

	public void setHistoricoIncidenteList(List<HistoricoIncidente> incidentHistories) {
		this.historicoIncidenteList = incidentHistories;
	}

	public HistoricoIncidente addIncidentHistory(HistoricoIncidente incidentHistory) {
		getHistoricoIncidenteList().add(incidentHistory);
		incidentHistory.setIncident(this);

		return incidentHistory;
	}

	public HistoricoIncidente removeIncidentHistory(HistoricoIncidente incidentHistory) {
		getHistoricoIncidenteList().remove(incidentHistory);
		incidentHistory.setIncident(null);

		return incidentHistory;
	}

}