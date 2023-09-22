package com.luxfacta.vernyy.api.rest;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.luxfacta.vernyy.api.base.IRestModel;
import com.luxfacta.vernyy.api.model.Area;

import jakarta.validation.constraints.NotNull;

@Mapper.ReferenceModel(className = Area.class)
public class RestArea implements Serializable, IRestModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Mapper.CryptoRequired
    private String id;

    @NotNull
    private String descricao;

    private String foto;

    @NotNull
    private Boolean perigo;

    private String cronInicio1;

    private String cronFim1;

    private String cronInicio2;

    private String cronFim2;

    private String cronInicio3;

    private String cronFim3;

    @IgnoreMapping
    private List<RestCamera> cameras;
    
    @IgnoreMapping
    private List<String> disponibilidades;
        
    @NotNull
    private RestTipoArea tipoArea;

    private Long artId;

    private Long detId;

    private String conId;
    
    private String macExterno;

    private String macInterno;
    
    private String urlExternoAbrir;
    
    private String urlExternoFechar;
    
    private String urlInternoAbrir;
    
    private String urlInternoFechar;
    
    private String ipCondominio;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
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


    public RestTipoArea getTipoArea() {
		return tipoArea;
	}

	public void setTipoArea(RestTipoArea tipoArea) {
		this.tipoArea = tipoArea;
	}


	public Long getArtId() {
		return artId;
	}

	public void setArtId(Long artId) {
		this.artId = artId;
	}

	public Long getDetId() {
		return detId;
	}

	public void setDetId(Long detId) {
		this.detId = detId;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public List<String> getDisponibilidades() {
		return disponibilidades;
	}

	public void setDisponibilidades(List<String> disponibilidades) {
		this.disponibilidades = disponibilidades;
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

	public String getIpCondominio() {
		return ipCondominio;
	}

	public void setIpCondominio(String ipCondominio) {
		this.ipCondominio = ipCondominio;
	}

	public List<RestCamera> getCameras() {
		return cameras;
	}

	public void setCameras(List<RestCamera> cameras) {
		this.cameras = cameras;
	}
    
	
    
    
}
