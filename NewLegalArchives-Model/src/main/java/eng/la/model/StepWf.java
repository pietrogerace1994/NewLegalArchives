package eng.la.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the STEP_WF database table.
 * 
 */
@Entity
@Table(name="STEP_WF")
@NamedQuery(name="StepWf.findAll", query="SELECT s FROM StepWf s")
public class StepWf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STEP_WF_ID_GENERATOR", sequenceName="STEP_WF_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STEP_WF_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CHIUSURA")
	private Date dataChiusura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

	private String lang;
	
	private String discarded;
	
	@Column(name="MOTIVO_RIFIUTO")
	private String motivoRifiuto;
	
	@Column(name="MOTIVO_RIFIUTO_STEP_PRECEDENTE")
	private String motivoRifiutoStepPrecedente;

	@Column(name="UTENTE_CHIUSURA")
	private String utenteChiusura;

	//bi-directional many-to-one association to Utente
	@ManyToOne()
	@JoinColumn(name="UTENTE_CREAZIONE")
	private Utente utente;

	//bi-directional many-to-one association to AttoWf
	@ManyToOne()
	@JoinColumn(name="ID_ATTO_WF")
	private AttoWf attoWf;

	//bi-directional many-to-one association to ConfigurazioneStepWf
	@ManyToOne()
	@JoinColumn(name="ID_CONFIGURAZIONE_STEP_WF")
	private ConfigurazioneStepWf configurazioneStepWf;

	//bi-directional many-to-one association to FascicoloWf
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO_WF")
	private FascicoloWf fascicoloWf;

	//bi-directional many-to-one association to IncaricoWf
	@ManyToOne()
	@JoinColumn(name="ID_INCARICO_WF")
	private IncaricoWf incaricoWf;

	//bi-directional many-to-one association to PfrWf
	@ManyToOne()
	@JoinColumn(name="ID_PFR_WF")
	private PfrWf pfrWf;

	//bi-directional many-to-one association to ProfessionistaEsternoWf
	@ManyToOne()
	@JoinColumn(name="ID_PROF_EST_WF")
	private ProfessionistaEsternoWf professionistaEsternoWf;

	//bi-directional many-to-one association to ProformaWf
	@ManyToOne()
	@JoinColumn(name="ID_PROFORMA_WF")
	private ProformaWf proformaWf;
	
	//bi-directional many-to-one association to SchedaFondoRischiWf
	@ManyToOne()
	@JoinColumn(name="ID_SCHEDA_FR")
	private SchedaFondoRischiWf schedaFondoRischiWf;
	
	//bi-directional many-to-one association to SchedaFondoRischiWf
	@ManyToOne()
	@JoinColumn(name="ID_BC_WF")
	private BeautyContestWf beautyContestWf;

	public StepWf() {
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

	public Date getDataChiusura() {
		return this.dataChiusura;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getUtenteChiusura() {
		return this.utenteChiusura;
	}

	public void setUtenteChiusura(String utenteChiusura) {
		this.utenteChiusura = utenteChiusura;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public AttoWf getAttoWf() {
		return this.attoWf;
	}

	public void setAttoWf(AttoWf attoWf) {
		this.attoWf = attoWf;
	}

	public ConfigurazioneStepWf getConfigurazioneStepWf() {
		return this.configurazioneStepWf;
	}

	public void setConfigurazioneStepWf(ConfigurazioneStepWf configurazioneStepWf) {
		this.configurazioneStepWf = configurazioneStepWf;
	}

	public FascicoloWf getFascicoloWf() {
		return this.fascicoloWf;
	}

	public void setFascicoloWf(FascicoloWf fascicoloWf) {
		this.fascicoloWf = fascicoloWf;
	}

	public IncaricoWf getIncaricoWf() {
		return this.incaricoWf;
	}

	public void setIncaricoWf(IncaricoWf incaricoWf) {
		this.incaricoWf = incaricoWf;
	}

	public PfrWf getPfrWf() {
		return this.pfrWf;
	}

	public void setPfrWf(PfrWf pfrWf) {
		this.pfrWf = pfrWf;
	}

	public ProfessionistaEsternoWf getProfessionistaEsternoWf() {
		return this.professionistaEsternoWf;
	}

	public void setProfessionistaEsternoWf(ProfessionistaEsternoWf professionistaEsternoWf) {
		this.professionistaEsternoWf = professionistaEsternoWf;
	}

	public ProformaWf getProformaWf() {
		return this.proformaWf;
	}

	public void setProformaWf(ProformaWf proformaWf) {
		this.proformaWf = proformaWf;
	}
	
	public SchedaFondoRischiWf getSchedaFondoRischiWf() {
		return this.schedaFondoRischiWf;
	}

	public void setSchedaFondoRischiWf(SchedaFondoRischiWf schedaFondoRischiWf) {
		this.schedaFondoRischiWf = schedaFondoRischiWf;
	}
	
	public BeautyContestWf getBeautyContestWf() {
		return this.beautyContestWf;
	}

	public void setBeautyContestWf(BeautyContestWf beautyContestWf) {
		this.beautyContestWf = beautyContestWf;
	}

	public String getMotivoRifiuto() {
		return motivoRifiuto;
	}

	public void setMotivoRifiuto(String motivoRifiuto) {
		this.motivoRifiuto = motivoRifiuto;
	}
	
	public String getMotivoRifiutoStepPrecedente() {
		return motivoRifiutoStepPrecedente;
	}

	public void setMotivoRifiutoStepPrecedente(String motivoRifiutoStepPrecedente) {
		this.motivoRifiutoStepPrecedente = motivoRifiutoStepPrecedente;
	}

	public String getDiscarded() {
		return discarded;
	}

	public void setDiscarded(String discarded) {
		this.discarded = discarded;
	}
	
	@Override
	public String toString(){
		return "StepWFView{" +
				"idWorkflow=" + id +
				"idFascicolo=" + fascicoloWf + 
				"idIncarico=" + incaricoWf + 
				"idProforma=" + proformaWf + 
				"idSchedaFR=" + schedaFondoRischiWf + 
				"idProfessionistaEsterno" + professionistaEsternoWf + 
				"idAtto=" + attoWf 
				+ "}";
	}

}