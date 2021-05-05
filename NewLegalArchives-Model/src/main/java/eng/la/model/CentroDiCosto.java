package eng.la.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CENTRO_DI_COSTO database table.
 * 
 */
@Entity
@Table(name="CENTRO_DI_COSTO")
@NamedQuery(name="CentroDiCosto.findAll", query="SELECT c FROM CentroDiCosto c")
public class CentroDiCosto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CENTRO_DI_COSTO_ID_GENERATOR", sequenceName="CENTRO_DI_COSTO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CENTRO_DI_COSTO_ID_GENERATOR")
	private long id;

	private BigDecimal cdc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="UNITA_LEGALE")
	private String unitaLegale;

	private String descrizione;

	//bi-directional many-to-one association to SettoreGiuridico
	@ManyToOne()
	@JoinColumn(name="ID_SETTORE_GIURIDICO")
	private SettoreGiuridico settoreGiuridico;

	//bi-directional many-to-one association to Societa
	@ManyToOne()
	@JoinColumn(name="ID_SOCIETA")
	private Societa societa;

	//bi-directional many-to-one association to TipologiaFascicolo
	@ManyToOne()
	@JoinColumn(name="ID_TIPOLOGIA_FASCICOLO")
	private TipologiaFascicolo tipologiaFascicolo; 

	public CentroDiCosto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
 
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigDecimal getCdc() {
		return this.cdc;
	}

	public void setCdc(BigDecimal cdc) {
		this.cdc = cdc;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getUnitaLegale() {
		return this.unitaLegale;
	}

	public void setUnitaLegale(String unitaLegale) {
		this.unitaLegale = unitaLegale;
	}

	public SettoreGiuridico getSettoreGiuridico() {
		return this.settoreGiuridico;
	}

	public void setSettoreGiuridico(SettoreGiuridico settoreGiuridico) {
		this.settoreGiuridico = settoreGiuridico;
	}

	public Societa getSocieta() {
		return this.societa;
	}

	public void setSocieta(Societa societa) {
		this.societa = societa;
	}

	public TipologiaFascicolo getTipologiaFascicolo() {
		return this.tipologiaFascicolo;
	}

	public void setTipologiaFascicolo(TipologiaFascicolo tipologiaFascicolo) {
		this.tipologiaFascicolo = tipologiaFascicolo;
	} 

}