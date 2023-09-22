package com.luxfacta.vernyy.api.model;

import java.io.Serializable;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author rcerqueira
 */
@Entity
@Table(name = "PUSH")
public class Push implements Serializable, IDatabaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_PUSH")
    @jakarta.persistence.SequenceGenerator(name = "SQ_PUSH", sequenceName = "SQ_PUSH" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PUS_ID", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "PUS_TOKEN", nullable = false, length = 256)
    private String token;

    @JoinColumn(name = "RES_ID", referencedColumnName = "RES_ID", nullable = false)
    @ManyToOne(optional = false)
    private Morador morador;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }

}
