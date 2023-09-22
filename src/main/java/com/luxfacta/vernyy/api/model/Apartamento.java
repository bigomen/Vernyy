package com.luxfacta.vernyy.api.model;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "APARTMENT")
public class Apartamento implements Serializable, IDatabaseModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_APARTMENT")
    @jakarta.persistence.SequenceGenerator(name = "SQ_APARTMENT", sequenceName = "SQ_APARTMENT" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "APT_ID", nullable = false)
    @CryptoRequired
    private Long id;

    @Basic(optional = false)
    @Column(name = "APT_NUMBER", nullable = false, length = 60)
    private String numero;

    @Basic(optional = false)
    @Column(name = "APT_FLOOR", nullable = false, length = 20)
    private String andar;

    @Basic(optional = false)
    @Column(name = "APT_PARKING_SPACE", nullable = false)
    private int quantidadeVagasGaragem;

    @Basic(optional = false)
    @Column(name = "APT_CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Basic(optional = false)
    @Column(name = "APT_LAST_UPDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "apartamento")
    private List<ApartamentoMorador> apartamentoMoradorList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartamento")
    private List<ApartamentoFuncionario> apartamentoFuncionarioList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartamento")
    private List<AgendamentoArea> agendamentoAreaList;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartamento")
    private List<Veiculo> veiculoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartamento")
    private List<ApartamentoVisitante> apartamentoVisitanteList;

    @JoinColumn(name = "BLO_ID", referencedColumnName = "BLO_ID", insertable = false, updatable = false, nullable = false)
    @ManyToOne(optional = false)
    private Bloco bloco;

    @Column(name = "BLO_ID", nullable = false)
    @CryptoRequired
    private Long bloId;

    
	@OneToMany(mappedBy="apartamento")
	private List<Incidente> incidenteList;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAndar() {
        return andar;
    }

    public void setAndar(String andar) {
        this.andar = andar;
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

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public List<ApartamentoMorador> getApartamentoMoradorList() {
        return apartamentoMoradorList;
    }

    public void setApartamentoMoradorList(List<ApartamentoMorador> apartamentoMoradorList) {
        this.apartamentoMoradorList = apartamentoMoradorList;
    }

    public List<ApartamentoFuncionario> getApartamentoFuncionarioList() {
        return apartamentoFuncionarioList;
    }

    public void setApartamentoFuncionarioList(List<ApartamentoFuncionario> apartamentoFuncionarioList) {
        this.apartamentoFuncionarioList = apartamentoFuncionarioList;
    }

    public List<AgendamentoArea> getAgendamentoAreaList() {
        return agendamentoAreaList;
    }

    public void setAgendamentoAreaList(List<AgendamentoArea> agendamentoAreaList) {
        this.agendamentoAreaList = agendamentoAreaList;
    }

 
    public List<Veiculo> getVeiculoList() {
        return veiculoList;
    }

    public void setVeiculoList(List<Veiculo> veiculoList) {
        this.veiculoList = veiculoList;
    }

    public List<ApartamentoVisitante> getApartamentoVisitanteList() {
        return apartamentoVisitanteList;
    }

    public void setApartamentoVisitanteList(List<ApartamentoVisitante> apartamentoVisitanteList) {
        this.apartamentoVisitanteList = apartamentoVisitanteList;
    }

    public Bloco getBloco() {
        return bloco;
    }

    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }

    public Long getBloId() {
        return bloId;
    }

    public void setBloId(Long bloId) {
        this.bloId = bloId;
    }

	public List<Incidente> getIncidenteList() {
		return incidenteList;
	}

	public void setIncidenteList(List<Incidente> incidenteList) {
		this.incidenteList = incidenteList;
	}



}
