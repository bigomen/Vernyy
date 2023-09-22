package com.luxfacta.vernyy.api.model;

import com.luxfacta.vernyy.api.base.IDatabaseModel;
import com.luxfacta.vernyy.api.shared.ImageUtils;

import java.io.IOException;
import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "VISITANT_APARTMENT")
public class ApartamentoVisitante implements Serializable, IDatabaseModel {

	
    public void ajustaImagens() throws IOException {
        this.setFoto((ImageUtils.scaleImage(this.getFoto(), 600, 800)));
    }

    private static final long serialVersionUID = 1L;
    
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_VISITANT_APARTMENT")
    @jakarta.persistence.SequenceGenerator(name = "SQ_VISITANT_APARTMENT", sequenceName = "SQ_VISITANT_APARTMENT" ,allocationSize = 1)
    @Basic(optional = false)
    @CryptoRequired
    @Column(name = "VAP_ID", nullable = false)
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "VAP_NAME", nullable = false, length = 50)
    private String nome;
    
    @Column(name = "VAP_MOBILE_NUMBER", length = 20)
    private String telefoneCelular;
    
    @Column(name = "VAP_PHOTO")
    @Basic(fetch=FetchType.LAZY)
    @Lob 
    private String foto;
    
    @Column(name = "VAP_COMPANY", length = 100)
    private String empresa;

    
    @JoinColumn(name = "APT_ID", referencedColumnName = "APT_ID", nullable = false, updatable = false, insertable = false)
    @ManyToOne(optional = false)
    private Apartamento apartamento;
    
    @JoinColumn(name = "VIS_ID", referencedColumnName = "VIS_ID", nullable = false)
    @ManyToOne(optional = false)
    private Visitante visitante;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartamentoVisitante")
    private List<AgendamentoVisitante> agendamentoVisitanteList;

    @Column(name = "APT_ID", nullable = false)
    @CryptoRequired
    private Long aptId;

    
    @Column(name = "VIS_ID", updatable = false, insertable = false, nullable = false)
    @CryptoRequired
    private Long visId;

    
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

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public Visitante getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
    }

    public List<AgendamentoVisitante> getAgendamentoVisitanteList() {
        return agendamentoVisitanteList;
    }

    public void setAgendamentoVisitanteList(List<AgendamentoVisitante> agendamentoVisitanteList) {
        this.agendamentoVisitanteList = agendamentoVisitanteList;
    }

    public Long getAptId() {
        return aptId;
    }

    public void setAptId(Long aptId) {
        this.aptId = aptId;
    }

	public Long getVisId() {
		return visId;
	}

	public void setVisId(Long visId) {
		this.visId = visId;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

    
    
}
