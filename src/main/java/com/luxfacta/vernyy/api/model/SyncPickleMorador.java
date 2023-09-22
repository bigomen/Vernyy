package com.luxfacta.vernyy.api.model;

import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "VW_SYNC_PICKLE_MORADOR")
public class SyncPickleMorador implements Serializable, IDatabaseModel {

	private static final long serialVersionUID = 1L;
	
	@Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_RESIDENT")
    @jakarta.persistence.SequenceGenerator(name = "SQ_RESIDENT", sequenceName = "SQ_RESIDENT" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "RES_ID", nullable = false)
    @CryptoRequired
    private Long id;
	
	@Column(name = "RES_PICKLE")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String pickle;
	
	@Basic(optional = false)
	@Column(name = "CON_ID")
	@CryptoRequired
	private Long conId;
	
	@Basic(optional = false)
    @Column(name = "SYN_DATA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPickle() {
		return pickle;
	}

	public void setPickle(String pickle) {
		this.pickle = pickle;
	}

	public Long getConId() {
		return conId;
	}

	public void setConId(Long conId) {
		this.conId = conId;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

}
