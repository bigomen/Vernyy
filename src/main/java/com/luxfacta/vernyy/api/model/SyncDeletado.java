package com.luxfacta.vernyy.api.model;

import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "VW_SYNC_DELETADO")
public class SyncDeletado implements Serializable, IDatabaseModel {
	
private static final long serialVersionUID = 1L;
	
	@Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_RESIDENT")
    @jakarta.persistence.SequenceGenerator(name = "SQ_RESIDENT", sequenceName = "SQ_RESIDENT" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID_VIEW", nullable = false)
    @CryptoRequired
    private Long id;
	
	@Column(name = "ID_TIPO", nullable = false)
    @CryptoRequired
	private Long idTipo;
	
	@Column(name = "RES_ID", nullable = false)
    @CryptoRequired
	private Long resId;
	
	@Column(name = "VIS_ID", nullable = false)
    @CryptoRequired
	private Long visId;
	
	@Column(name = "EMP_ID", nullable = false)
    @CryptoRequired
	private Long empId;
	
	@Column(name = "AEM_ID", nullable = false)
    @CryptoRequired
	private Long aemId;
	
	@Column(name = "CEM_ID", nullable = false)
    @CryptoRequired
	private Long cemId;
	
	@Column(name = "VAP_ID", nullable = false)
    @CryptoRequired
	private Long vapId;
	
	@Column(name = "VEH_ID", nullable = false)
    @CryptoRequired
	private Long vehId;
	
	@Column(name = "ARE_ID", nullable = false)
    @CryptoRequired
	private Long areId;
	
	@Basic(optional = false)
    @Column(name = "DATA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
	
	@Column(name = "TIPO")
	private String tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
