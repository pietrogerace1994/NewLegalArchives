package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the CONFIGURAZIONE_STEP_WF database table.
 * 
 */
@Entity
@Table(name="CONFIGURAZIONE_STEP_WF")
@NamedQuery(name="ConfigurazioneStepWf.findAll", query="SELECT c FROM ConfigurazioneStepWf c")
public class ConfigurazioneStepWf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONFIGURAZIONE_STEP_WF_ID_GENERATOR", sequenceName="CONF_STEP_WF_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CONFIGURAZIONE_STEP_WF_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_FINE_VALIDITA")
	private Date dataFineValidita;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_INIZIO_VALIDITA")
	private Date dataInizioValidita;

	@Column(name="DESCR_STATE_FROM")
	private String descrStateFrom;

	@Column(name="DESCR_STATE_TO")
	private String descrStateTo;

	private String lang;

	@Column(name="NOME_STEP")
	private String nomeStep;

	@Column(name="NOTIFICA_MAIL_CC")
	private String notificaMailCc;

	@Column(name="NOTIFICA_MAIL_TO")
	private String notificaMailTo;

	@Column(name="NUMERO_AZIONE_STEP")
	private String numeroAzioneStep;

	@Column(name="STATE_FROM")
	private String stateFrom;

	@Column(name="STATE_TO")
	private String stateTo;

	@Column(name="TIPO_ASSEGNAZIONE")
	private String tipoAssegnazione;

	private String unita;

	//bi-directional many-to-one association to ClasseWf
	@ManyToOne()
	@JoinColumn(name="ID_CLASSE_WF")
	private ClasseWf classeWf;

	//bi-directional many-to-one association to R
	@ManyToOne()
	@JoinColumn(name="ID_MATRICOLA_UTIL")
	private Utente utente;
	
	//bi-directional many-to-one association to R
	@ManyToOne()
	@JoinColumn(name="ID_GRUPPO_UTENTE")
	private GruppoUtente gruppoUtente;

	//bi-directional many-to-one association to StepWf
	@OneToMany(mappedBy="configurazioneStepWf")
	private Set<StepWf> stepWfs;

	public ConfigurazioneStepWf() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodGruppoLingua() {
		return this.codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataFineValidita() {
		return this.dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public Date getDataInizioValidita() {
		return this.dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public String getDescrStateFrom() {
		return this.descrStateFrom;
	}

	public void setDescrStateFrom(String descrStateFrom) {
		this.descrStateFrom = descrStateFrom;
	}

	public String getDescrStateTo() {
		return this.descrStateTo;
	}

	public void setDescrStateTo(String descrStateTo) {
		this.descrStateTo = descrStateTo;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNomeStep() {
		return this.nomeStep;
	}

	public void setNomeStep(String nomeStep) {
		this.nomeStep = nomeStep;
	}

	public String getNotificaMailCc() {
		return this.notificaMailCc;
	}

	public void setNotificaMailCc(String notificaMailCc) {
		this.notificaMailCc = notificaMailCc;
	}

	public String getNotificaMailTo() {
		return this.notificaMailTo;
	}

	public void setNotificaMailTo(String notificaMailTo) {
		this.notificaMailTo = notificaMailTo;
	}

	public String getNumeroAzioneStep() {
		return this.numeroAzioneStep;
	}

	public void setNumeroAzioneStep(String numeroAzioneStep) {
		this.numeroAzioneStep = numeroAzioneStep;
	}

	public String getStateFrom() {
		return this.stateFrom;
	}

	public void setStateFrom(String stateFrom) {
		this.stateFrom = stateFrom;
	}

	public String getStateTo() {
		return this.stateTo;
	}

	public void setStateTo(String stateTo) {
		this.stateTo = stateTo;
	}

	public String getTipoAssegnazione() {
		return this.tipoAssegnazione;
	}

	public void setTipoAssegnazione(String tipoAssegnazione) {
		this.tipoAssegnazione = tipoAssegnazione;
	}

	public String getUnita() {
		return this.unita;
	}

	public void setUnita(String unita) {
		this.unita = unita;
	}

	public ClasseWf getClasseWf() {
		return this.classeWf;
	}

	public void setClasseWf(ClasseWf classeWf) {
		this.classeWf = classeWf;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	public GruppoUtente getGruppoUtente() {
		return this.gruppoUtente;
	}

	public void setGruppoUtente(GruppoUtente gruppoUtente) {
		this.gruppoUtente = gruppoUtente;
	}

	public Set<StepWf> getStepWfs() {
		return this.stepWfs;
	}

	public void setStepWfs(Set<StepWf> stepWfs) {
		this.stepWfs = stepWfs;
	}

	public StepWf addStepWf(StepWf stepWf) {
		getStepWfs().add(stepWf);
		stepWf.setConfigurazioneStepWf(this);

		return stepWf;
	}

	public StepWf removeStepWf(StepWf stepWf) {
		getStepWfs().remove(stepWf);
		stepWf.setConfigurazioneStepWf(null);

		return stepWf;
	}

}