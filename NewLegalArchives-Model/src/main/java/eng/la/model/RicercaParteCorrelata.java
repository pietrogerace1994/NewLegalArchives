package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the RICERCA_PARTE_CORRELATA database table.
 * 
 */
@Entity
@Table(name="RICERCA_PARTE_CORRELATA")
@NamedQuery(name="RicercaParteCorrelata.findAll", query="SELECT r FROM RicercaParteCorrelata r")
public class RicercaParteCorrelata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RICERCA_PARTE_CORRELATA_ID_GENERATOR", sequenceName="RIC_PARTE_CORRELATA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="RICERCA_PARTE_CORRELATA_ID_GENERATOR")
	private long id;

	@Column(name="COD_FISCALE")
	private String codFiscale;
	
	@Column(name="PARTITA_IVA")
	private String partitaIva;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_IN")
	private Date dataIn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_RICERCA")
	private Date dataRicerca;

	@Column(name="DENOMINAZIONE_IN")
	private String denominazioneIn;

	private String esito;

	private String lang;

	@Lob
	private byte[] report;

	@Column(name="USER_RICERCA")
	private String userRicerca;

	public RicercaParteCorrelata() {
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

	public Date getDataIn() {
		return this.dataIn;
	}

	public void setDataIn(Date dataIn) {
		this.dataIn = dataIn;
	}

	public Date getDataRicerca() {
		return this.dataRicerca;
	}

	public void setDataRicerca(Date dataRicerca) {
		this.dataRicerca = dataRicerca;
	}

	public String getDenominazioneIn() {
		return this.denominazioneIn;
	}

	public void setDenominazioneIn(String denominazioneIn) {
		this.denominazioneIn = denominazioneIn;
	}

	public String getEsito() {
		return this.esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public byte[] getReport() {
		return this.report;
	}

	public void setReport(byte[] report) {
		this.report = report;
	}

	public String getUserRicerca() {
		return this.userRicerca;
	}

	public void setUserRicerca(String userRicerca) {
		this.userRicerca = userRicerca;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

}