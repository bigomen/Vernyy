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
@Table(name = "SCHEDULE_VISITANT")
public class AgendamentoVisitante implements Serializable, IDatabaseModel {

    private static final long serialVersionUID = 1L;
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_SCHEDULE_VISITANT")
    @jakarta.persistence.SequenceGenerator(name = "SQ_SCHEDULE_VISITANT", sequenceName = "SQ_SCHEDULE_VISITANT" ,allocationSize = 1)
    @CryptoRequired
    @Basic(optional = false)
    @Column(name = "SCH_ID", nullable = false)
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "SCH_CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    
    @Column(name = "SCH_VISIT_START_DATE_HOUR", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
    
    @Column(name = "SCH_VISIT_END_DATE_HOUR", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;
    
    
    @JoinColumn(name = "VAP_ID", referencedColumnName = "VAP_ID", nullable = false, updatable = false, insertable = false)
    @ManyToOne
    private ApartamentoVisitante apartamentoVisitante;

    @Column(name = "VAP_ID", nullable = false)
    @CryptoRequired
    private Long vapId;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
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


    public ApartamentoVisitante getApartamentoVisitante() {
        return apartamentoVisitante;
    }

    public void setApartamentoVisitante(ApartamentoVisitante apartamentoVisitante) {
        this.apartamentoVisitante = apartamentoVisitante;
    }

    public Long getVapId() {
        return vapId;
    }

    public void setVapId(Long vapId) {
        this.vapId = vapId;
    }


    
}
