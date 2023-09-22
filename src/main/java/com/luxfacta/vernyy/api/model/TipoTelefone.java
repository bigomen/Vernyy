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
@Table(name = "PHONE_TYPE")
public class TipoTelefone implements Serializable, IDatabaseModel {

    private static final long serialVersionUID = 1L;
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_PHONE_TYPE")
    @jakarta.persistence.SequenceGenerator(name = "SQ_PHONE_TYPE", sequenceName = "SQ_PHONE_TYPE" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PHT_ID", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "PHT_DESCRIPTION", nullable = false, length = 128)
    private String descricao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoTelefone")
    private List<Contato> contatoList;


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

    public List<Contato> getContatoList() {
        return contatoList;
    }

    public void setContatoList(List<Contato> contatoList) {
        this.contatoList = contatoList;
    }

    
}
