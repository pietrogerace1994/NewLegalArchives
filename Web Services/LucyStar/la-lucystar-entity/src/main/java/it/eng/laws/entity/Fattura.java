package it.eng.laws.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the FATTURA database table.
 * 
 */
@Entity
@NamedQuery(name="Fattura.findByNumCode", 
query="SELECT f FROM Fattura f where f.numeroFattura = :numeroFattura and f.codiceSap = :codiceSap")
public class Fattura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FATTURA_ID_GENERATOR", sequenceName="FATTURA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="FATTURA_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_FATTURA")
	private Date dataFattura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_REGISTRAZIONE")
	private Date dataRegistrazione;
	
	@Column(name="NUMERO_FATTURA")
	private String numeroFattura;
	
	@Column(name="N_PROTOCOLLO_FISCALE")
	private BigDecimal nProtocolloFiscale;

	@Column(name="CODICE_SAP")
	private String codiceSap;
	
	public Fattura() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataFattura() {
		return this.dataFattura;
	}

	public void setDataFattura(Date dataFattura) {
		this.dataFattura = dataFattura;
	}

	public String getNumeroFattura() {
		return this.numeroFattura;
	}

	public void setNumeroFattura(String numeroFattura) {
		this.numeroFattura = numeroFattura;
	}

	public BigDecimal getnProtocolloFiscale() {
		return nProtocolloFiscale;
	}
	
	public void setnProtocolloFiscale(BigDecimal nProtocolloFiscale) {
		this.nProtocolloFiscale = nProtocolloFiscale;
	}
	
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}
	
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	
	public void setCodiceSap(String codiceSap) {
		this.codiceSap = codiceSap;
	}
	
	public String getCodiceSap() {
		return codiceSap;
	}

}