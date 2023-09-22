package com.luxfacta.vernyy.api.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.*;

@Entity
@Table(name = "BLOCK")
public class Bloco implements Serializable, IDatabaseModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_BLOCK")
    @jakarta.persistence.SequenceGenerator(name = "SQ_BLOCK", sequenceName = "SQ_BLOCK" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "BLO_ID", nullable = false)
    @Mapper.CryptoRequired
    private Long id;

    @Basic(optional = false)
    @Column(name = "BLO_DESCRIPTION", nullable = false, length = 128)
    private String descricao;

    @Basic(optional = false)
    @Column(name = "BLO_CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Basic(optional = false)
    @Column(name = "BLO_LAST_UPDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @JoinColumn(name = "CON_ID", referencedColumnName = "CON_ID", nullable = false)
    @ManyToOne(optional = false)
    private Condominio condominio;

    @Column(name = "CON_ID", nullable = false, updatable = false, insertable = false)
    private Long conId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloco", fetch = FetchType.LAZY)
    private List<Apartamento> apartamentoList;

   
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

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }


    public List<Apartamento> getApartamentoList() {
        return apartamentoList;
    }

    public void setApartamentoList(List<Apartamento> apartamentoList) {
        this.apartamentoList = apartamentoList;
    }

    public Long getConId() {
        return conId;
    }

    public void setConId(Long conId) {
        this.conId = conId;
    }

    
    
}
