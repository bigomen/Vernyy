package com.luxfacta.vernyy.api.model;

import java.io.Serial;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "CONDOMINIUM")
public class Condominio implements Serializable, IDatabaseModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_CONDOMINIUM")
    @jakarta.persistence.SequenceGenerator(name = "SQ_CONDOMINIUM", sequenceName = "SQ_CONDOMINIUM" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "CON_ID", nullable = false)
    @Mapper.CryptoRequired
    private Long id;

    @Basic(optional = false)
    @Column(name = "CON_NAME", nullable = false, length = 128)
    private String nome;

    @Basic(optional = false)
    @Column(name = "CON_ADDRESS", nullable = false, length = 255)
    private String logradouro;

    @Basic(optional = false)
    @Column(name = "CON_NUMBER", nullable = false, length = 10)
    private String numero;

    @Basic(optional = false)
    @Column(name = "CON_CEP", nullable = false, length = 9)
    private String cep;

    @Basic(optional = false)
    @Column(name = "CON_PARKING_SPACE", nullable = false)
    private int quantidadeVagasGaragem;

    @Basic(optional = false)
    @Column(name = "CON_CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Basic(optional = false)
    @Column(name = "CON_LAST_UPDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimaAtualizacao;

    @Basic(optional = false)
    @Column(name = "CON_ACTIVE", nullable = false)
    private Boolean ativo;

    @Column(name = "CON_NOTIFICATION", nullable = false)
    private Boolean notificacao;

    
    @Column(name = "CON_EXTERNAL_IP", length = 50)
    private String ipExterno;

    @Column(name = "CON_INTERNAL_IP", length = 50)
    private String ipInterno;

    @OneToMany(mappedBy = "condominio")
    private List<Area> areaList;

    @JoinColumn(name = "CIT_ID", referencedColumnName = "CIT_ID", nullable = false, updatable = false, insertable = false)
    @ManyToOne(optional = false)
    private Cidade cidade;

    @Column(name = "CIT_ID", nullable = false)
    @CryptoRequired
    private Long cidId;

    
    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "condominio", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Bloco> blocoList = new ArrayList<>();

    @OneToMany(mappedBy = "condominio")
    private List<Contato> contatoList;
    
    @OneToMany(mappedBy = "condominio")
    private List<CondominioFuncionario> condominioFuncionarioList;

    @Column(name = "CON_CAMERAS")
    private Boolean statusCamera;

    @Column(name = "CON_LINK")
    private Boolean statusLink;

    @Column(name = "CON_CONCIERGE")
    private Boolean statusSistema;

    @Column(name = "CON_ENERGY")
    private Boolean statusEnergia;

    @Column(name = "CON_LAST_SYNC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimaSincronizacao;

    
    @ManyToMany(mappedBy = "condominioList")
    private List<Usuario>  usuarioList = new ArrayList<>();

    

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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getQuantidadeVagasGaragem() {
        return quantidadeVagasGaragem;
    }

    public void setQuantidadeVagasGaragem(int quantidadeVagasGaragem) {
        this.quantidadeVagasGaragem = quantidadeVagasGaragem;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getIpExterno() {
        return ipExterno;
    }

    public void setIpExterno(String ipExterno) {
        this.ipExterno = ipExterno;
    }


    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    
    public List<Bloco> getBlocoList() {
        return blocoList;
    }

    public void setBlocoList(List<Bloco> blocoList) {
        this.blocoList = blocoList;
    }

    public List<CondominioFuncionario> getCondominioFuncionarioList() {
        return condominioFuncionarioList;
    }

    public void setCondominioFuncionarioList(List<CondominioFuncionario> condominioFuncionarioList) {
        this.condominioFuncionarioList = condominioFuncionarioList;
    }

    public Boolean getStatusCamera() {
        return statusCamera;
    }

    public void setStatusCamera(Boolean statusCamera) {
        this.statusCamera = statusCamera;
    }

    public Boolean getStatusLink() {
        return statusLink;
    }

    public void setStatusLink(Boolean statusLink) {
        this.statusLink = statusLink;
    }

    public Boolean getStatusSistema() {
        return statusSistema;
    }

    public void setStatusSistema(Boolean statusSistema) {
        this.statusSistema = statusSistema;
    }

    public Boolean getStatusEnergia() {
        return statusEnergia;
    }

    public void setStatusEnergia(Boolean statusEnergia) {
        this.statusEnergia = statusEnergia;
    }

    public List<Contato> getContatoList() {
        return contatoList;
    }

    public void setContatoList(List<Contato> contatoList) {
        this.contatoList = contatoList;
    }

	public Date getDataUltimaSincronizacao() {
		return dataUltimaSincronizacao;
	}

	public void setDataUltimaSincronizacao(Date dataUltimaSincronizacao) {
		this.dataUltimaSincronizacao = dataUltimaSincronizacao;
	}

	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}
	
	

	public void addUsuario(Usuario usuario)
	{
		usuario.getCondominioList().add(this);
		this.usuarioList.add(usuario);
	}
    
    public void removeCondominio(Usuario usuario){
    	usuario.getCondominioList().remove(this);
		this.usuarioList.remove(usuario);
	}

	public Long getCidId() {
		return cidId;
	}

	public void setCidId(Long cidId) {
		this.cidId = cidId;
	}

	public String getIpInterno() {
		return ipInterno;
	}

	public void setIpInterno(String ipInterno) {
		this.ipInterno = ipInterno;
	}

	public Boolean getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Boolean notificacao) {
		this.notificacao = notificacao;
	}

    
    
}
