package com.luxfacta.vernyy.api.model;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "SCHEDULE_AREA")
public class AgendamentoArea implements Serializable, IDatabaseModel {

    private static final long serialVersionUID = 1L;
    
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_SCHEDULE_AREA")
    @jakarta.persistence.SequenceGenerator(name = "SQ_SCHEDULE_AREA", sequenceName = "SQ_SCHEDULE_AREA" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "SCA_ID", nullable = false)
    @CryptoRequired
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "SCA_START", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
    
    @Basic(optional = false)
    @Column(name = "SCA_END", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;
    
    
    @JoinColumn(name = "APT_ID", referencedColumnName = "APT_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne
    private Apartamento apartamento;
    
    @JoinColumn(name = "ARE_ID", referencedColumnName = "ARE_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Area area;

    @Column(name = "APT_ID", nullable = false)
    @CryptoRequired
    private Long aptId;

    @Column(name = "ARE_ID", nullable = false)
    @CryptoRequired
    private Long areId;
    

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Long getAptId() {
        return aptId;
    }

    public void setAptId(Long aptId) {
        this.aptId = aptId;
    }

    public Long getAreId() {
        return areId;
    }

    public void setAreId(Long areId) {
        this.areId = areId;
    }

    
    
}
