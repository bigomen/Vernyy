package com.luxfacta.vernyy.api.model;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USERS")
public class Usuario implements Serializable, IDatabaseModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_USER")
    @jakarta.persistence.SequenceGenerator(name = "SQ_USER", sequenceName = "SQ_USER" ,allocationSize = 1)
    @Basic(optional = false)
    @Mapper.CryptoRequired
    @Column(name = "USE_ID", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "USE_NAME", nullable = false, length = 128)
    private String nome;

    @Column(name = "USE_BIRTH")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Column(name = "USE_CPF", length = 11)
    private String cpf;

    @Column(name = "USE_EMAIL", length = 120, nullable = false)
    private String email;

    @Basic(optional = false)
    @Column(name = "USE_SYSTEM_PASSWORD", length = 100)
    private String senha;

    @Column(name = "USE_MOBILE_NUMBER", length = 20)
    private String telefoneCelular;

    @Basic(optional = false)
    @Column(name = "USE_CREATED_AT", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;

    @Basic(optional = false)
    @Column(name = "USE_LAST_UPDATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataAtualizacao;
    
    @Column(name = "USE_TOKEN")
    private String token;

    @Column(name = "USE_PUSH_TOKEN")
    private String pushToken;

    
    @Basic(optional = false)
    @Column(name = "USE_EXPIRATION_TOKEN")
    @Temporal(TemporalType.DATE)
    private Date tokenExpiracao;

    
    @Basic(optional = false)
    @Column(name = "USE_HIRING_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataContratacao;

    @Column(name = "USE_LAST_ACCESS")
    @Temporal(TemporalType.DATE)
    private Date dataUltimoAcesso;

    @Basic(optional = false)
    @Column(name = "USE_ACTIVE", nullable = false)
    private Boolean ativo;

    @JoinColumn(name = "PRO_ID", referencedColumnName = "PRO_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Perfil perfil;

    @Column(name = "PRO_ID", nullable = false)
    @CryptoRequired
    private Long proId;

    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_CONDOMINIUM",
            joinColumns = @JoinColumn(name = "USE_ID"),
            inverseJoinColumns = @JoinColumn(name = "CON_ID"))
    private List<Condominio> condominioList = new ArrayList<>();

    
	@OneToMany(mappedBy="usuario")
	private List<Incidente> incidenteList;


    
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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExpiracao() {
		return tokenExpiracao;
	}

	public void setTokenExpiracao(Date tokenExpiracao) {
		this.tokenExpiracao = tokenExpiracao;
	}

	public Date getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Condominio> getCondominioList() {
		return condominioList;
	}

	public void setCondominioList(List<Condominio> condominioList) {
		this.condominioList = condominioList;
	}


	public void addCondominio(Condominio condominio)
	{
		condominio.getUsuarioList().add(this);
		this.condominioList.add(condominio);
	}
    
    public void removeCondominio(Condominio condominio){
    	condominio.getUsuarioList().remove(this);
		this.condominioList.remove(condominio);
	}

	public Long getProId() {
		return proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public List<Incidente> getIncidenteList() {
		return incidenteList;
	}

	public void setIncidenteList(List<Incidente> incidenteList) {
		this.incidenteList = incidenteList;
	}
    
    
    
    
}
