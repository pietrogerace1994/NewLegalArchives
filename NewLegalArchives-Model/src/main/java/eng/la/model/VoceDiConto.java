package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date; 


/**
 * The persistent class for the VOCE_DI_CONTO database table.
 * 
 */
@Entity
@Table(name="VOCE_DI_CONTO")
@NamedQuery(name="VoceDiConto.findAll", query="SELECT v FROM VoceDiConto v")
public class VoceDiConto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="VOCE_DI_CONTO_ID_GENERATOR", sequenceName="VOCE_DI_CONTO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="VOCE_DI_CONTO_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="UNITA_LEGALE")
	private String unitaLegale; 
	
	private String descrizione;

	private BigDecimal vdc;
 
	//bi-directional many-to-one association to SettoreGiuridico
	@ManyToOne()
	@JoinColumn(name="ID_SETTORE_GIURIDICO")
	private SettoreGiuridico settoreGiuridico;

	//bi-directional many-to-one association to TipologiaFascicolo
	@ManyToOne()
	@JoinColumn(name="ID_TIPOLOGIA_FASCICOLO")
	private TipologiaFascicolo tipologiaFascicolo;

	public VoceDiConto() {
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

	public BigDecimal getVdc() {
		return this.vdc;
	}

	public void setVdc(BigDecimal vdc) {
		this.vdc = vdc;
	} 
	
	public SettoreGiuridico getSettoreGiuridico() {
		return this.settoreGiuridico;
	}

	public void setSettoreGiuridico(SettoreGiuridico settoreGiuridico) {
		this.settoreGiuridico = settoreGiuridico;
	}

	public TipologiaFascicolo getTipologiaFascicolo() {
		return this.tipologiaFascicolo;
	}

	public void setTipologiaFascicolo(TipologiaFascicolo tipologiaFascicolo) {
		this.tipologiaFascicolo = tipologiaFascicolo;
	}

}