package com.luxfacta.vernyy.api.model;

import java.io.Serial;
import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CITY")
public class Cidade implements Serializable, IDatabaseModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_CITY")
    @jakarta.persistence.SequenceGenerator(name = "SQ_CITY", sequenceName = "SQ_CITY" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "CIT_ID", nullable = false)
    @CryptoRequired
    private Long id;

    @Basic(optional = false)
    @Column(name = "CIT_NAME", nullable = false, length = 40)
    private String nome;

    @JoinColumn(name = "STA_ID", referencedColumnName = "STA_ID", nullable = false)
    @ManyToOne(optional = false)
    private Uf uf;

    @Column(name = "STA_ID", updatable = false, insertable = false)
    private Long ufId;

    public Long getUfId() {
        return ufId;
    }

    public void setUfId(Long ufId) {
        this.ufId = ufId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Uf getUf() {
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
    }

    
    
}
