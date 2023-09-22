package com.luxfacta.vernyy.api.model;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTACT")
public class Contato implements Serializable, IDatabaseModel {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_CONTACT")
    @jakarta.persistence.SequenceGenerator(name = "SQ_CONTACT", sequenceName = "SQ_CONTACT" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "COT_ID", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "COT_WORKER_NAME", nullable = false, length = 60)
    private String nome;

    @Basic(optional = false)
    @Column(name = "COT_NUMBER", nullable = false, length = 20)
    private String numeroTelefone;

    @Basic(optional = false)
    @Column(name = "COT_ACTIVE", nullable = false)
    private Boolean ativo;

    @JoinColumn(name = "PHT_ID", referencedColumnName = "PHT_ID", nullable = false)
    @ManyToOne(optional = false)
    private TipoTelefone tipoTelefone;

    @JoinColumn(name = "CON_ID", referencedColumnName = "CON_ID", nullable = false)
    @ManyToOne(optional = false)
    private Condominio condominio;


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

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public TipoTelefone getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(TipoTelefone tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    
    
}
