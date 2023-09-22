package com.luxfacta.vernyy.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "EMPLOYEE")
public class Funcionario implements Serializable, IDatabaseModel {

    private static final long serialVersionUID = 1L;
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_EMPLOYEE")
    @jakarta.persistence.SequenceGenerator(name = "SQ_EMPLOYEE", sequenceName = "SQ_EMPLOYEE" ,allocationSize = 1)
    @CryptoRequired
    @Basic(optional = false)
    @Column(name = "EMP_ID", nullable = false)
    private Long id;

    @Column(name = "EMP_CPF", length = 11, nullable = false)
    private String cpf;

    @Basic(optional = false)
    @Column(name = "EMP_TRAINED", nullable = false)
    private Boolean mapeado;

    @Basic(optional = false)
    @Column(name = "EMP_PASSWORD", length = 100)
    private String senha;

    @Basic(optional = false)
    @Column(name = "EMP_PANIC_PASSWORD", length = 100)
    private String senhaPanico;


    @Basic(optional = false)
    @Column(name = "EMP_CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Basic(optional = false)
    @Column(name = "EMP_LAST_UPDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Basic(optional = false)
    @Column(name = "EMP_BLACK_LIST", nullable = false)
    private Boolean listaBloqueio;

    @Column(name = "EMP_BLACK_LIST_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataListaBloqueio;

    @Column(name = "EMP_BLACK_LIST_REASON", length = 256)
    private String motivoListaBloqueio;

    @Column(name = "EMP_PICKLE")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String pickle;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private List<ApartamentoFuncionario> apartamentoFuncionarioList = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private List<CondominioFuncionario> condominioFuncionarioList = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public Boolean getMapeado() {
        return mapeado;
    }

    public void setMapeado(Boolean mapeado) {
        this.mapeado = mapeado;
    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaPanico() {
        return senhaPanico;
    }

    public void setSenhaPanico(String senhaPanico) {
        this.senhaPanico = senhaPanico;
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

    public Boolean getListaBloqueio() {
        return listaBloqueio;
    }

    public void setListaBloqueio(Boolean listaBloqueio) {
        this.listaBloqueio = listaBloqueio;
    }

    public Date getDataListaBloqueio() {
        return dataListaBloqueio;
    }

    public void setDataListaBloqueio(Date dataListaBloqueio) {
        this.dataListaBloqueio = dataListaBloqueio;
    }

    public String getMotivoListaBloqueio() {
        return motivoListaBloqueio;
    }

    public void setMotivoListaBloqueio(String motivoListaBloqueio) {
        this.motivoListaBloqueio = motivoListaBloqueio;
    }


    public List<ApartamentoFuncionario> getApartamentoFuncionarioList() {
        return apartamentoFuncionarioList;
    }

    public void setApartamentoFuncionarioList(List<ApartamentoFuncionario> apartamentoFuncionarioList) {
        this.apartamentoFuncionarioList = apartamentoFuncionarioList;
    }


    public List<CondominioFuncionario> getCondominioFuncionarioList() {
        return condominioFuncionarioList;
    }

    public void setCondominioFuncionarioList(List<CondominioFuncionario> condominioFuncionarioList) {
        this.condominioFuncionarioList = condominioFuncionarioList;
    }

	public String getPickle() {
		return pickle;
	}

	public void setPickle(String pickle) {
		this.pickle = pickle;
	}

    
}
