package com.luxfacta.vernyy.api.model;

import com.luxfacta.vernyy.api.base.IDatabaseModel;
import com.luxfacta.vernyy.api.shared.ImageUtils;
import jakarta.persistence.*;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CONDOMINIUM_EMPLOYEE")
public class CondominioFuncionario implements Serializable, IDatabaseModel {

	public void ajustaImagens() throws IOException {
        this.setFotoAbaixo((ImageUtils.scaleImage(this.getFotoAbaixo(), 600, 800)));
        this.setFotoAcima((ImageUtils.scaleImage(this.getFotoAcima(), 600, 800)));
        this.setFotoFrente((ImageUtils.scaleImage(this.getFotoFrente(), 600, 800)));
        this.setFotoEsquerda((ImageUtils.scaleImage(this.getFotoEsquerda(), 600, 800)));
        this.setFotoDireita((ImageUtils.scaleImage(this.getFotoDireita(), 600, 800)));
	}
	
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_CONDOMINIUM_EMPLOYEE")
    @jakarta.persistence.SequenceGenerator(name = "SQ_CONDOMINIUM_EMPLOYEE", sequenceName = "SQ_CONDOMINIUM_EMPLOYEE" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "CEM_ID", nullable = false)
    @Mapper.CryptoRequired
    private Long id;

    @Column(name = "CEM_PATTERN_1_START", length = 100)
    private String cronInicio1;

    @Column(name = "CEM_PATTERN_1_END", length = 100)
    private String cronFim1;

    @Column(name = "CEM_PATTERN_2_START", length = 100)
    private String cronInicio2;

    @Column(name = "CEM_PATTERN_2_END", length = 100)
    private String cronFim2;

    @Column(name = "CEM_PATTERN_3_START", length = 100)
    private String cronInicio3;

    @Column(name = "CEM_PATTERN_3_END", length = 100)
    private String cronFim3;

    @Basic(optional = false)
    @Column(name = "CEM_BIRTHDAY", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;

    @Column(name = "CEM_NAME", length = 128, nullable = false)
    private String nome;

    @Column(name = "CEM_EMAIL", length = 120)
    private String email;

    @Column(name = "CEM_MOBILE_NUMBER", length = 20)
    private String telefoneCelular;

    @Basic(optional = false)
    @Column(name = "CEM_HIRING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataContratacao;

    @Column(name = "CEM_JOB", length = 100)
    private String funcao;

    @Column(name = "CEM_PHOTO_FRONT", length = 128)
    private String fotoFrente;

    @Column(name = "CEM_PHOTO_RIGHT", length = 128)
    private String fotoDireita;

    @Column(name = "CEM_PHOTO_LEFT", length = 128)
    private String fotoEsquerda;

    @Column(name = "CEM_PHOTO_ABOVE", length = 128)
    private String fotoAcima;

    @Column(name = "CEM_PHOTO_BELOW", length = 128)
    private String fotoAbaixo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EMPLOYEE_AREA", joinColumns = @JoinColumn(name = "CEM_ID"), inverseJoinColumns = @JoinColumn(name = "ARE_ID"))
    private List<Area> areaList = new ArrayList<>();

    @JoinColumn(name = "CON_ID", referencedColumnName = "CON_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Condominio condominio;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID", nullable = false)
    @ManyToOne(optional = false)
    private Funcionario funcionario;


    @Column(name = "CON_ID", nullable = false)
    @Mapper.CryptoRequired
    private Long conId;


    @Column(name = "EMP_ID", nullable = false, insertable = false, updatable = false)
    @Mapper.CryptoRequired
    private Long empId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCronInicio1() {
        return cronInicio1;
    }

    public void setCronInicio1(String cronInicio1) {
        this.cronInicio1 = cronInicio1;
    }

    public String getCronFim1() {
        return cronFim1;
    }

    public void setCronFim1(String cronFim1) {
        this.cronFim1 = cronFim1;
    }

    public String getCronInicio2() {
        return cronInicio2;
    }

    public void setCronInicio2(String cronInicio2) {
        this.cronInicio2 = cronInicio2;
    }

    public String getCronFim2() {
        return cronFim2;
    }

    public void setCronFim2(String cronFim2) {
        this.cronFim2 = cronFim2;
    }

    public String getCronInicio3() {
        return cronInicio3;
    }

    public void setCronInicio3(String cronInicio3) {
        this.cronInicio3 = cronInicio3;
    }

    public String getCronFim3() {
        return cronFim3;
    }

    public void setCronFim3(String cronFim3) {
        this.cronFim3 = cronFim3;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Long getConId() {
        return conId;
    }

    public void setConId(Long conId) {
        this.conId = conId;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public Date getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getFotoFrente() {
        return fotoFrente;
    }

    public void setFotoFrente(String fotoFrente) {
        this.fotoFrente = fotoFrente;
    }

    public String getFotoDireita() {
        return fotoDireita;
    }

    public void setFotoDireita(String fotoDireita) {
        this.fotoDireita = fotoDireita;
    }

    public String getFotoEsquerda() {
        return fotoEsquerda;
    }

    public void setFotoEsquerda(String fotoEsquerda) {
        this.fotoEsquerda = fotoEsquerda;
    }

    public String getFotoAcima() {
        return fotoAcima;
    }

    public void setFotoAcima(String fotoAcima) {
        this.fotoAcima = fotoAcima;
    }

    public String getFotoAbaixo() {
        return fotoAbaixo;
    }

    public void setFotoAbaixo(String fotoAbaixo) {
        this.fotoAbaixo = fotoAbaixo;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }
}
