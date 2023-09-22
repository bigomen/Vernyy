package com.luxfacta.vernyy.api.model;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PROFILE")
public class Perfil implements Serializable, IDatabaseModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_PROFILE")
    @jakarta.persistence.SequenceGenerator(name = "SQ_PROFILE", sequenceName = "SQ_PROFILE" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PRO_ID", nullable = false)
    @Mapper.CryptoRequired
    private Long id;

    @Basic(optional = false)
    @Column(name = "PRO_DESCRIPTION", nullable = false, length = 50)
    private String descricao;

    @Basic(optional = false)
    @Column(name = "PRO_ROLE", nullable = false, length = 1)
    private String role;

    
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}




}
