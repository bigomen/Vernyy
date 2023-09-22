package com.luxfacta.vernyy.api.model;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "DEVICE_TYPE")
public class TipoEquipamento implements Serializable, IDatabaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_DEVICE_TYPE")
    @jakarta.persistence.SequenceGenerator(name = "SQ_DEVICE_TYPE", sequenceName = "SQ_DEVICE_TYPE" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "DET_ID", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "DET_DESCRIPTION", nullable = false, length = 50)
    private String descricao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoEquipamento")
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

    

    
}
