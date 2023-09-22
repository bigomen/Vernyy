package com.luxfacta.vernyy.api.model;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IDatabaseModel;
import com.luxfacta.vernyy.api.shared.ImageUtils;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "APARTMENT_EMPLOYEE")
public class ApartamentoFuncionario implements Serializable, IDatabaseModel {

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
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_APARTMENT_EMPLOYEE")
    @jakarta.persistence.SequenceGenerator(name = "SQ_APARTMENT_EMPLOYEE", sequenceName = "SQ_APARTMENT_EMPLOYEE" ,allocationSize = 1)
    @CryptoRequired
    @Basic(optional = false)
    @Column(name = "AEM_ID", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "AEM_NAME", nullable = false, length = 128)
    private String nome;

    @Column(name = "AEM_EMAIL", length = 120)
    private String email;

    @Column(name = "AEM_JOB", length = 100)
    private String funcao;

    @Basic(optional = false)
    @Column(name = "AEM_MOBILE_NUMBER", nullable = false, length = 20)
    private String telefoneCelular;

    @Basic(optional = false)
    @Column(name = "AEM_BIRTHDAY", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;

    @Basic(optional = false)
    @Column(name = "AEM_HIRING_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataContratacao;
    
    @Column(name = "AEM_PHOTO_FRONT")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String fotoFrente;

    @Column(name = "AEM_PHOTO_RIGHT")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String fotoDireita;

    @Column(name = "AEM_PHOTO_LEFT")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String fotoEsquerda;

    @Column(name = "AEM_PHOTO_TOP")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String fotoAcima;

    @Column(name = "AEM_PHOTO_BOTTOM")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String fotoAbaixo;

    @Column(name = "AEM_PATTERN_1_START", length = 100)
    private String cronInicio1;

    @Column(name = "AEM_PATTERN_1_END", length = 100)
    private String cronFim1;

    @Column(name = "AEM_PATTERN_2_START", length = 100)
    private String cronInicio2;

    @Column(name = "AEM_PATTERN_2_END", length = 100)
    private String cronFim2;

    @Column(name = "AEM_PATTERN_3_START", length = 100)
    private String cronInicio3;

    @Column(name = "AEM_PATTERN_3_END", length = 100)
    private String cronFim3;

    @JoinColumn(name = "APT_ID", referencedColumnName = "APT_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Apartamento apartamento;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID", nullable = false)
    @ManyToOne(optional = false)
    private Funcionario funcionario;

    @Column(name = "APT_ID", nullable = false)
    @CryptoRequired
    private Long aptId;

    @Column(name = "EMP_ID", nullable = false, insertable = false, updatable = false)
    @CryptoRequired
    private Long empId;

    
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

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Long getAptId() {
        return aptId;
    }

    public void setAptId(Long aptId) {
        this.aptId = aptId;
    }

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

    
}
