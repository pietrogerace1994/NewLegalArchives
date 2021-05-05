package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the FATTURA database table.
 * 
 */
@Entity
@NamedQuery(name="Fattura.findAll", query="SELECT f FROM Fattura f")
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

	//bi-directional many-to-one association to RProformaFattura
	@OneToMany(mappedBy="fattura")
	private Set<RProformaFattura> RProformaFatturas;

 
	
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
	
	public Set<RProformaFattura> getRProformaFatturas() {
		return this.RProformaFatturas;
	}

	public void setRProformaFatturas(Set<RProformaFattura> RProformaFatturas) {
		this.RProformaFatturas = RProformaFatturas;
	}

	public RProformaFattura addRProformaFattura(RProformaFattura RProformaFattura) {
		getRProformaFatturas().add(RProformaFattura);
		RProformaFattura.setFattura(this);

		return RProformaFattura;
	}

	public RProformaFattura removeRProformaFattura(RProformaFattura RProformaFattura) {
		getRProformaFatturas().remove(RProformaFattura);
		RProformaFattura.setFattura(null);

		return RProformaFattura;
	}
	
 

}