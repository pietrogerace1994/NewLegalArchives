package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;

import eng.la.model.audit.AuditedAttribute;
import eng.la.model.audit.AuditedObjectName;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the PROFORMA database table.
 * 
 */
@Entity
@NamedQuery(name="Proforma.findAll", query="SELECT p FROM Proforma p")
public class Proforma extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROFORMA_ID_GENERATOR", sequenceName="PROFORMA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PROFORMA_ID_GENERATOR")
	private long id;

	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="ANNO_ESERCIZIO_FINANZIARIO")
	private BigDecimal annoEsercizioFinanziario;

	@AuditedAttribute
	private String autorizzatore;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_AUTORIZZAZIONE")
	private Date dataAutorizzazione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_COMPOSIZIONE")
	private Date dataComposizione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_INSERIMENTO")
	private Date dataInserimento;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_INVIO_AMMINISTRATIVO")
	private Date dataInvioAmministrativo;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_RICH_AUTORIZZAZIONE")
	private Date dataRichAutorizzazione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ULTIMA_MODIFICA")
	private Date dataUltimaModifica;

	@AuditedAttribute
	@Column(name="ESITO_VERIFICA_PROFORMA")
	private String esitoVerificaProforma;

	private String lang;

	@AuditedAttribute
	@Column(name="NOME_PROFORMA")
	private String nomeProforma;

	@AuditedAttribute
	private String note;

	@AuditedAttribute
	private String numero;

	@AuditedAttribute
	private BigDecimal onorari;

	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="SPESE_IMPONIBILI")
	private BigDecimal speseImponibili;

	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="SPESE_NON_IMPONIBILI")
	private BigDecimal speseNonImponibili;

	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="TOTALE_AUTORIZZATO")
	private BigDecimal totaleAutorizzato;
	
	@AuditedAttribute
	@Column(name="UTENTE_PROCESSAMENTO")
	private String utenteProcessamento;
	

	@AuditedAttribute
	@Column(name="PROCESSATO")
	private String processato;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_PROCESSAMENTO")
	private Date dataProcessamento;

	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="TOTALE_IMPONIBILE")
	private BigDecimal totaleImponibile;

	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="DIRITTI")
	private BigDecimal diritti;

	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="CPA")
	private BigDecimal cpa;

	@AuditedAttribute
	private String ultimo;

	@AuditedAttribute
	@Column(name="CENTRO_DI_COSTO")
	private String centroDiCosto;

	//bi-directional many-to-one association to SchedaValutazione
	@ManyToOne()
	@JoinColumn(name="ID_SCHEDA_VALUTAZIONE")
	private SchedaValutazione schedaValutazione;

	//bi-directional many-to-one association to StatoProforma

	@AuditedAttribute(classType=StatoProforma.class)
	@ManyToOne()
	@JoinColumn(name="ID_STATO_PROFORMA")
	private StatoProforma statoProforma;

	//bi-directional many-to-one association to TipoValuta
	@ManyToOne()
	@JoinColumn(name="ID_VALUTA")
	private TipoValuta tipoValuta;

	@AuditedAttribute
	@Column(name="VOCE_DI_CONTO")
	private String voceDiConto;

	@AuditedAttribute
	@Column(name="DA_PROF_ESTERNO")
	private String daProfEsterno;
	
	//bi-directional many-to-one association to ProformaWf
	@OneToMany(mappedBy="proforma")
	private Set<ProformaWf> proformaWfs;

	//bi-directional many-to-one association to RIncaricoProformaSocieta
	@OneToMany(mappedBy="proforma")
	private Set<RIncaricoProformaSocieta> RIncaricoProformaSocietas;

	//bi-directional many-to-one association to RProformaFattura
	@OneToMany(mappedBy="proforma")
	private Set<RProformaFattura> RProformaFatturas;
	
	//bi-directional many-to-one association to CategoriaAtto
	@ManyToOne()
	@JoinColumn(name="ID_TIPO_PROFORMA")
	private TipoProforma tipoProforma;

	@Column(name="IS_CPA_DISABLED")
	private boolean isCPADisabled;
	
	
	
	
	public boolean isCPADisabled() {
		return isCPADisabled;
	}

	public void setCPADisabled(boolean isCPADisabled) {
		this.isCPADisabled = isCPADisabled;
	}

	public TipoProforma getTipoProforma() {
		return tipoProforma;
	}

	public void setTipoProforma(TipoProforma tipoProforma) {
		this.tipoProforma = tipoProforma;
	}

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

	@AuditedObjectName
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
 

	public SchedaValutazione getSchedaValutazione() {
		return this.schedaValutazione;
	}

	public void setSchedaValutazione(SchedaValutazione schedaValutazione) {
		this.schedaValutazione = schedaValutazione;
	}

	public StatoProforma getStatoProforma() {
		return this.statoProforma;
	}

	public void setStatoProforma(StatoProforma statoProforma) {
		this.statoProforma = statoProforma;
	}

	public TipoValuta getTipoValuta() {
		return this.tipoValuta;
	}

	public void setTipoValuta(TipoValuta tipoValuta) {
		this.tipoValuta = tipoValuta;
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

	public Set<ProformaWf> getProformaWfs() {
		return this.proformaWfs;
	}

	public void setProformaWfs(Set<ProformaWf> proformaWfs) {
		this.proformaWfs = proformaWfs;
	}
	
	 public void setDaProfEsterno(String daProfEsterno) {
		this.daProfEsterno = daProfEsterno;
	}
	 
	 public String getDaProfEsterno() {
		return daProfEsterno;
	}

	public ProformaWf addProformaWf(ProformaWf proformaWf) {
		getProformaWfs().add(proformaWf);
		proformaWf.setProforma(this);

		return proformaWf;
	}

	public ProformaWf removeProformaWf(ProformaWf proformaWf) {
		getProformaWfs().remove(proformaWf);
		proformaWf.setProforma(null);

		return proformaWf;
	}

	public Set<RIncaricoProformaSocieta> getRIncaricoProformaSocietas() {
		return this.RIncaricoProformaSocietas;
	}

	public void setRIncaricoProformaSocietas(Set<RIncaricoProformaSocieta> RIncaricoProformaSocietas) {
		this.RIncaricoProformaSocietas = RIncaricoProformaSocietas;
	}

	public RIncaricoProformaSocieta addRIncaricoProformaSocieta(RIncaricoProformaSocieta RIncaricoProformaSocieta) {
		getRIncaricoProformaSocietas().add(RIncaricoProformaSocieta);
		RIncaricoProformaSocieta.setProforma(this);

		return RIncaricoProformaSocieta;
	}

	public RIncaricoProformaSocieta removeRIncaricoProformaSocieta(RIncaricoProformaSocieta RIncaricoProformaSocieta) {
		getRIncaricoProformaSocietas().remove(RIncaricoProformaSocieta);
		RIncaricoProformaSocieta.setProforma(null);

		return RIncaricoProformaSocieta;
	}

	public Set<RProformaFattura> getRProformaFatturas() {
		return this.RProformaFatturas;
	}

	public void setRProformaFatturas(Set<RProformaFattura> RProformaFatturas) {
		this.RProformaFatturas = RProformaFatturas;
	}

	public RProformaFattura addRProformaFattura(RProformaFattura RProformaFattura) {
		getRProformaFatturas().add(RProformaFattura);
		RProformaFattura.setProforma(this);

		return RProformaFattura;
	}

	public RProformaFattura removeRProformaFattura(RProformaFattura RProformaFattura) {
		getRProformaFatturas().remove(RProformaFattura);
		RProformaFattura.setProforma(null);

		return RProformaFattura;
	}

}