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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the SCADENZA database table.
 * 
 */
@Entity
@NamedQuery(name="Scadenza.findAll", query="SELECT s FROM Scadenza s")
public class Scadenza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SCADENZA_ID_GENERATOR", sequenceName="SCADENZA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SCADENZA_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_INSERIMENTO")
	private Date dataInserimento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_SCADENZA")
	private Date dataScadenza;

	@Column(name="DELAY_AVVISO")
	private BigDecimal delayAvviso;

	private String descrizione;

	@Column(name="FREQUENZA_AVVISO")
	private BigDecimal frequenzaAvviso;

	private String lang;

	private String oggetto;

	@Column(name="TEMPO_A_DISPOSIZIONE")
	private BigDecimal tempoADisposizione;

	//bi-directional many-to-one association to Scadenza
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to TipoScadenza
	@ManyToOne()
	@JoinColumn(name="ID_TIPO_SCADENZA")
	private TipoScadenza tipoScadenza;

	public Scadenza() {
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

	public Date getDataInserimento() {
		return this.dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataScadenza() {
		return this.dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public BigDecimal getDelayAvviso() {
		return this.delayAvviso;
	}

	public void setDelayAvviso(BigDecimal delayAvviso) {
		this.delayAvviso = delayAvviso;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigDecimal getFrequenzaAvviso() {
		return this.frequenzaAvviso;
	}

	public void setFrequenzaAvviso(BigDecimal frequenzaAvviso) {
		this.frequenzaAvviso = frequenzaAvviso;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public BigDecimal getTempoADisposizione() {
		return this.tempoADisposizione;
	}

	public void setTempoADisposizione(BigDecimal tempoADisposizione) {
		this.tempoADisposizione = tempoADisposizione;
	}

	public TipoScadenza getTipoScadenza() {
		return this.tipoScadenza;
	}

	public void setTipoScadenza(TipoScadenza tipoScadenza) {
		this.tipoScadenza = tipoScadenza;
	}

	public Fascicolo getFascicolo() {
		return fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

}