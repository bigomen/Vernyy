package com.luxfacta.vernyy.api.model;

import java.io.Serializable;
import java.util.Date;

import com.luxfacta.vernyy.api.base.IDatabaseModel;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "VEHICLE")
public class Veiculo implements Serializable, IDatabaseModel {

    private static final long serialVersionUID = 1L;
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_VEHICLE")
    @jakarta.persistence.SequenceGenerator(name = "SQ_VEHICLE", sequenceName = "SQ_VEHICLE" ,allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "VEH_ID", nullable = false)
    @CryptoRequired
    private Long id;

    @Column(name = "VEH_RFID", length = 50)
    private String rfid;

    @Basic(optional = false)
    @Column(name = "VEH_PLATE", nullable = false, length = 10)
    private String placa;

    @Column(name = "VEH_CARMAKER", length = 50, nullable = false)
    private String fabricante;

    @Column(name = "VEH_MODEL", length = 50, nullable = false)
    private String modelo;

    @Column(name = "VEH_COLOR", length = 50)
    private String cor;

    @Basic(optional = false)
    @Column(name = "VEH_CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;


    @Basic(optional = false)
    @Column(name = "VEH_LAST_UPDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;


    @JoinColumn(name = "APT_ID", referencedColumnName = "APT_ID", nullable = false, insertable=false, updatable=false)
    @ManyToOne
    private Apartamento apartamento;

    @Column(name = "APT_ID", nullable = false)
    @CryptoRequired
    private Long aptId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }


    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public Long getAptId() {
        return aptId;
    }

    public void setAptId(Long aptId) {
        this.aptId = aptId;
    }

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

    

    
}
