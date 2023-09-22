package com.luxfacta.vernyy.api.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import net.jcip.annotations.Immutable;

@Entity
@Table(name = "STATE")
@Immutable
public class Uf implements Serializable, IDatabaseModel {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "STA_ID", nullable = false)
    @CryptoRequired
    private Long id;

    @Basic(optional = false)
    @Column(name = "STA_NAME", nullable = false, length = 20)
    private String nome;

    @Basic(optional = false)
    @Column(name = "STA_ABBREVIATION", nullable = false, length = 2)
    private String sigla;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uf")
    private List<Cidade> cidadeList;


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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }


    public List<Cidade> getCidadeList() {
        return cidadeList;
    }

    public void setCidadeList(List<Cidade> cidadeList) {
        this.cidadeList = cidadeList;
    }

    
}
