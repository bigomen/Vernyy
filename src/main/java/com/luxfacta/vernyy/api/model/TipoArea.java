package com.luxfacta.vernyy.api.model;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "AREA_TYPE")
public class TipoArea implements Serializable, IDatabaseModel {

    private static final long serialVersionUID = 1L;
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_AREA_TYPE")
    @jakarta.persistence.SequenceGenerator(name = "SQ_AREA_TYPE", sequenceName = "SQ_AREA_TYPE" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ART_ID", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "ART_DESCRIPTION", nullable = false, length = 128)
    private String descricao;

    @Column(name = "ART_ALLOW_SCHEDULE", nullable = false)
    private Long permiteAgendamento;

    @Column(name = "ART_ACCESS_DOOR", nullable = false)
    private Long permiteAberturaPorta;

    
    
    @OneToMany(mappedBy = "tipoArea")
    private List<Area> areaList;

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

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

	public Long getPermiteAgendamento() {
		return permiteAgendamento;
	}

	public void setPermiteAgendamento(Long permiteAgendamento) {
		this.permiteAgendamento = permiteAgendamento;
	}

	public Long getPermiteAberturaPorta() {
		return permiteAberturaPorta;
	}

	public void setPermiteAberturaPorta(Long permiteAberturaPorta) {
		this.permiteAberturaPorta = permiteAberturaPorta;
	}

	
	
}
