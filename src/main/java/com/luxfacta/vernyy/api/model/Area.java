package com.luxfacta.vernyy.api.model;

import com.luxfacta.vernyy.api.base.IDatabaseModel;
import com.luxfacta.vernyy.api.shared.ImageUtils;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "AREA")
public class Area implements Serializable, IDatabaseModel {

    public void ajustaImagens() throws IOException {
        this.setFoto(ImageUtils.scaleImage(this.getFoto(), 800, 600));
    }

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_AREA")
    @jakarta.persistence.SequenceGenerator(name = "SQ_AREA", sequenceName = "SQ_AREA" ,allocationSize = 1)
    @CryptoRequired
    @Basic(optional = false)
    @Column(name = "ARE_ID", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "ARE_DESCRIPTION", nullable = false, length = 128)
    private String descricao;


    @Column(name = "ARE_PHOTO")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String foto;

    @Basic(optional = false)
    @Column(name = "ARE_DANGEROUS", nullable = false)
    private Boolean perigo;

    @Basic(optional = false)
    @Column(name = "ARE_CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Basic(optional = false)
    @Column(name = "ARE_LAST_UPDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Column(name = "ARE_PATTERN_1_START", length = 100)
    private String cronInicio1;

    @Column(name = "ARE_PATTERN_1_END", length = 100)
    private String cronFim1;

    @Column(name = "ARE_PATTERN_2_START", length = 100)
    private String cronInicio2;

    @Column(name = "ARE_PATTERN_2_END", length = 100)
    private String cronFim2;

    @Column(name = "ARE_PATTERN_3_START", length = 100)
    private String cronInicio3;

    @Column(name = "ARE_PATTERN_3_END", length = 100)
    private String cronFim3;

    
    
    @Column(name = "ARE_MAC_ADDRESS_EXTERNO", length = 32)
    private String macExterno;

    @Column(name = "ARE_MAC_ADDRESS_INTERNO", length = 32)
    private String macInterno;
    
    @Column(name = "ARE_URL_EXTERNO_ABRIR", length = 256)
    private String urlExternoAbrir;
    
    @Column(name = "ARE_URL_EXTERNO_FECHAR", length = 256)
    private String urlExternoFechar;
    
    @Column(name = "ARE_URL_INTERNO_ABRIR", length = 256)
    private String urlInternoAbrir;
    
    @Column(name = "ARE_URL_INTERNO_FECHAR", length = 256)
    private String urlInternoFechar;

    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "area", fetch = FetchType.LAZY)
    private List<AgendamentoArea> areaAgendamentoList;

    @JoinColumn(name = "ART_ID", referencedColumnName = "ART_ID", updatable = false, insertable = false, nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoArea tipoArea;

    @JoinColumn(name = "CON_ID", referencedColumnName = "CON_ID", updatable = false, insertable = false, nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Condominio condominio;


    @JoinColumn(name = "DET_ID", referencedColumnName = "DET_ID", updatable = false, insertable = false, nullable = true)
    @ManyToOne(optional = true)
    private TipoEquipamento tipoEquipamento;

    @Column(name = "DET_ID",  nullable = true)
    private Long detId;
    
    @Column(name = "ART_ID",  nullable = true)
    private Long artId;
    
    @Column(name = "CON_ID",  nullable = false)
    @CryptoRequired
    private Long conId;

    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private List<Camera> cameraList;

    @ManyToMany(mappedBy = "areaList")
    private List<CondominioFuncionario> condominioFuncionarioList = new ArrayList<>();

	@OneToMany(mappedBy="area")
	private List<Incidente> incidenteList;

    
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


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Boolean getPerigo() {
        return perigo;
    }

    public void setPerigo(Boolean perigo) {
        this.perigo = perigo;
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

    public List<AgendamentoArea> getAreaAgendamentoList() {
        return areaAgendamentoList;
    }

    public void setAreaAgendamentoList(List<AgendamentoArea> areaAgendamentoList) {
        this.areaAgendamentoList = areaAgendamentoList;
    }

    public TipoArea getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(TipoArea tipoArea) {
        this.tipoArea = tipoArea;
    }


    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public List<Camera> getCameraList() {
        return cameraList;
    }

    public void setCameraList(List<Camera> cameraList) {
        this.cameraList = cameraList;
    }
    public Long getConId() {
        return conId;
    }

    public void setConId(Long conId) {
        this.conId = conId;
    }

	public TipoEquipamento getTipoEquipamento() {
		return tipoEquipamento;
	}

	public void setTipoEquipamento(TipoEquipamento tipoEquipamento) {
		this.tipoEquipamento = tipoEquipamento;
	}

	public Long getDetId() {
		return detId;
	}

	public void setDetId(Long detId) {
		this.detId = detId;
	}

	public Long getArtId() {
		return artId;
	}

	public void setArtId(Long artId) {
		this.artId = artId;
	}

    public List<CondominioFuncionario> getCondominioFuncionarioList() {
        return condominioFuncionarioList;
    }

    public void setCondominioFuncionarioList(List<CondominioFuncionario> condominioFuncionarioList) {
        this.condominioFuncionarioList = condominioFuncionarioList;
    }

	public String getMacExterno() {
		return macExterno;
	}

	public void setMacExterno(String macExterno) {
		this.macExterno = macExterno;
	}

	public String getMacInterno() {
		return macInterno;
	}

	public void setMacInterno(String macInterno) {
		this.macInterno = macInterno;
	}

	public String getUrlExternoAbrir() {
		return urlExternoAbrir;
	}

	public void setUrlExternoAbrir(String urlExternoAbrir) {
		this.urlExternoAbrir = urlExternoAbrir;
	}

	public String getUrlExternoFechar() {
		return urlExternoFechar;
	}

	public void setUrlExternoFechar(String urlExternoFechar) {
		this.urlExternoFechar = urlExternoFechar;
	}

	public String getUrlInternoAbrir() {
		return urlInternoAbrir;
	}

	public void setUrlInternoAbrir(String urlInternoAbrir) {
		this.urlInternoAbrir = urlInternoAbrir;
	}

	public String getUrlInternoFechar() {
		return urlInternoFechar;
	}

	public void setUrlInternoFechar(String urlInternoFechar) {
		this.urlInternoFechar = urlInternoFechar;
	}

	public List<Incidente> getIncidenteList() {
		return incidenteList;
	}

	public void setIncidenteList(List<Incidente> incidenteList) {
		this.incidenteList = incidenteList;
	}



}
