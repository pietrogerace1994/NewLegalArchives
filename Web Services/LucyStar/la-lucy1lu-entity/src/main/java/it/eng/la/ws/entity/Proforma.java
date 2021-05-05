package it.eng.la.ws.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the PROFORMA database table.
 * 
 */
@Entity
@NamedQuery(name = "Proforma.findAll", query = "SELECT p FROM Proforma p")
public class Proforma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PROFORMA_ID_GENERATOR", sequenceName = "PROFORMA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PROFORMA_ID_GENERATOR")
	private long id;

	@Column(name = "ANNO_ESERCIZIO_FINANZIARIO")
	private BigDecimal annoEsercizioFinanziario;

	private String autorizzatore;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_AUTORIZZAZIONE")
	private Date dataAutorizzazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_COMPOSIZIONE")
	private Date dataComposizione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INSERIMENTO")
	private Date dataInserimento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INVIO_AMMINISTRATIVO")
	private Date dataInvioAmministrativo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_RICH_AUTORIZZAZIONE")
	private Date dataRichAutorizzazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ULTIMA_MODIFICA")
	private Date dataUltimaModifica;

	@Column(name = "ESITO_VERIFICA_PROFORMA")
	private String esitoVerificaProforma;

	private String lang;

	@Column(name = "NOME_PROFORMA")
	private String nomeProforma;

	private String note;

	private String numero;

	private BigDecimal onorari;

	@Column(name = "SPESE_IMPONIBILI")
	private BigDecimal speseImponibili;

	@Column(name = "SPESE_NON_IMPONIBILI")
	private BigDecimal speseNonImponibili;

	@Column(name = "TOTALE_AUTORIZZATO")
	private BigDecimal totaleAutorizzato;

	@Column(name = "UTENTE_PROCESSAMENTO")
	private String utenteProcessamento;

	@Column(name = "PROCESSATO")
	private String processato;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_PROCESSAMENTO")
	private Date dataProcessamento;

	@Column(name = "TOTALE_IMPONIBILE")
	private BigDecimal totaleImponibile;

	@Column(name = "DIRITTI")
	private BigDecimal diritti;

	@Column(name = "CPA")
	private BigDecimal cpa;

	private String ultimo;

	@Column(name = "CENTRO_DI_COSTO")
	private String centroDiCosto;

	@Column(name = "VOCE_DI_CONTO")
	private String voceDiConto;

	@Column(name = "DA_PROF_ESTERNO")
	private String daProfEsterno;

	// bi-directional many-to-one association to RIncaricoProformaSocieta
	@OneToMany(mappedBy = "proforma")
	private Set<RIncaricoProformaSocieta> RIncaricoProformaSocietas;

	public Proforma() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getTotaleImponibile() {
		return totaleImponibile;
	}

	public void setTotaleImponibile(BigDecimal totaleImponibile) {
		this.totaleImponibile = totaleImponibile;
	}

	public BigDecimal getAnnoEsercizioFinanziario() {
		return this.annoEsercizioFinanziario;
	}

	public void setAnnoEsercizioFinanziario(BigDecimal annoEsercizioFinanziario) {
		this.annoEsercizioFinanziario = annoEsercizioFinanziario;
	}

	public String getAutorizzatore() {
		return this.autorizzatore;
	}

	public void setAutorizzatore(String autorizzatore) {
		this.autorizzatore = autorizzatore;
	}

	public Date getDataAutorizzazione() {
		return this.dataAutorizzazione;
	}

	public void setDataAutorizzazione(Date dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public String getUtenteProcessamento() {
		return utenteProcessamento;
	}

	public void setUtenteProcessamento(String utenteProcessamento) {
		this.utenteProcessamento = utenteProcessamento;
	}

	public String getProcessato() {
		return processato;
	}

	public void setProcessato(String processato) {
		this.processato = processato;
	}

	public Date getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataComposizione() {
		return this.dataComposizione;
	}

	public void setDataComposizione(Date dataComposizione) {
		this.dataComposizione = dataComposizione;
	}

	public Date getDataInserimento() {
		return this.dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataInvioAmministrativo() {
		return this.dataInvioAmministrativo;
	}

	public void setDataInvioAmministrativo(Date dataInvioAmministrativo) {
		this.dataInvioAmministrativo = dataInvioAmministrativo;
	}

	public Date getDataRichAutorizzazione() {
		return this.dataRichAutorizzazione;
	}

	public void setDataRichAutorizzazione(Date dataRichAutorizzazione) {
		this.dataRichAutorizzazione = dataRichAutorizzazione;
	}

	public Date getDataUltimaModifica() {
		return this.dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getEsitoVerificaProforma() {
		return this.esitoVerificaProforma;
	}

	public void setEsitoVerificaProforma(String esitoVerificaProforma) {
		this.esitoVerificaProforma = esitoVerificaProforma;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNomeProforma() {
		return this.nomeProforma;
	}

	public void setNomeProforma(String nomeProforma) {
		this.nomeProforma = nomeProforma;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getOnorari() {
		return this.onorari;
	}

	public void setOnorari(BigDecimal onorari) {
		this.onorari = onorari;
	}

	public BigDecimal getSpeseImponibili() {
		return this.speseImponibili;
	}

	public void setSpeseImponibili(BigDecimal speseImponibili) {
		this.speseImponibili = speseImponibili;
	}

	public BigDecimal getSpeseNonImponibili() {
		return this.speseNonImponibili;
	}

	public void setSpeseNonImponibili(BigDecimal speseNonImponibili) {
		this.speseNonImponibili = speseNonImponibili;
	}

	public BigDecimal getTotaleAutorizzato() {
		return this.totaleAutorizzato;
	}

	public void setTotaleAutorizzato(BigDecimal totaleAutorizzato) {
		this.totaleAutorizzato = totaleAutorizzato;
	}

	public BigDecimal getDiritti() {
		return this.diritti;
	}

	public void setDiritti(BigDecimal diritti) {
		this.diritti = diritti;
	}

	public BigDecimal getCpa() {
		return this.cpa;
	}

	public void setCpa(BigDecimal cpa) {
		this.cpa = cpa;
	}

	public String getUltimo() {
		return this.ultimo;
	}

	public void setUltimo(String ultimo) {
		this.ultimo = ultimo;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public String getVoceDiConto() {
		return voceDiConto;
	}

	public void setVoceDiConto(String voceDiConto) {
		this.voceDiConto = voceDiConto;
	}

	public void setDaProfEsterno(String daProfEsterno) {
		this.daProfEsterno = daProfEsterno;
	}

	public String getDaProfEsterno() {
		return daProfEsterno;
	}

	public Set<RIncaricoProformaSocieta> getRIncaricoProformaSocietas() {
		return this.RIncaricoProformaSocietas;
	}

	public void setRIncaricoProformaSocietas(
			Set<RIncaricoProformaSocieta> RIncaricoProformaSocietas) {
		this.RIncaricoProformaSocietas = RIncaricoProformaSocietas;
	}

	public RIncaricoProformaSocieta addRIncaricoProformaSocieta(
			RIncaricoProformaSocieta RIncaricoProformaSocieta) {
		getRIncaricoProformaSocietas().add(RIncaricoProformaSocieta);
		RIncaricoProformaSocieta.setProforma(this);

		return RIncaricoProformaSocieta;
	}

	public RIncaricoProformaSocieta removeRIncaricoProformaSocieta(
			RIncaricoProformaSocieta RIncaricoProformaSocieta) {
		getRIncaricoProformaSocietas().remove(RIncaricoProformaSocieta);
		RIncaricoProformaSocieta.setProforma(null);

		return RIncaricoProformaSocieta;
	}

}