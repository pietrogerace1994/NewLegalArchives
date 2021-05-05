package eng.la.model;

import java.io.Serializable;
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

import eng.la.model.audit.AuditedObjectName;


/**
 * The persistent class for the UTENTE database table.
 * 
 */
@Entity
@NamedQuery(name="Utente.findAll", query="SELECT u FROM Utente u")
public class Utente extends AbstractEntity implements Serializable, Comparable<Utente> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="UTENTE_MATRICOLAUTIL_GENERATOR", sequenceName="UTENTE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="UTENTE_MATRICOLAUTIL_GENERATOR")
	@Column(name="MATRICOLA_UTIL")
	private String matricolaUtil;

	@Column(name="CODICE_SOCIETA_APPART")
	private String codiceSocietaAppart;

	@Column(name="CODICE_SOCIETA_UTIL")
	private String codiceSocietaUtil;

	@Column(name="CODICE_UNITA_APPART")
	private String codiceUnitaAppart;

	@Column(name="CODICE_UNITA_UTIL")
	private String codiceUnitaUtil;

	@Column(name="DESCR_UNITA_APPART")
	private String descrUnitaAppart;

	@Column(name="DESCR_UNITA_UTIL")
	private String descrUnitaUtil;

	@Column(name="MATRICOLA_APPART")
	private String matricolaAppart;

	@Column(name="MATRICOLA_RESP_APPART")
	private String matricolaRespAppart;

	@Column(name="MATRICOLA_RESP_UTIL")
	private String matricolaRespUtil;

	@Column(name="NOMINATIVO_APPART")
	private String nominativoAppart;

	@Column(name="NOMINATIVO_RESP_APPART")
	private String nominativoRespAppart;

	@Column(name="NOMINATIVO_RESP_UTIL")
	private String nominativoRespUtil;

	@Column(name="NOMINATIVO_UTIL")
	private String nominativoUtil;

	@Column(name="RAGIONE_SOCIALE_SOCIETA_APPART")
	private String ragioneSocialeSocietaAppart;

	@Column(name="RAGIONE_SOCIALE_SOCIETA_UTIL")
	private String ragioneSocialeSocietaUtil;

	@Column(name="RESPONSABILE_APPART")
	private String responsabileAppart;

	@Column(name="RESPONSABILE_UTIL")
	private String responsabileUtil;

	@Column(name="USER_ID_RESPONSABILE_APPART")
	private String userIdResponsabileAppart;

	@Column(name="USER_ID_RESPONSABILE_UTIL")
	private String userIdResponsabileUtil;

	@Column(name="USERID_UTIL")
	private String useridUtil;
	
	@Column(name="EMAIL_UTIL")
	private String emailUtil;
	
	@Column(name="ASSENTE")
	private String assente;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to ConfigurazioneStepWf
	@OneToMany(mappedBy="utente")
	private Set<ConfigurazioneStepWf> configurazioneStepWfs;
	
	//bi-directional many-to-one association to stepWf
	@OneToMany(mappedBy="utente")
	private Set<StepWf> stepWfs;

	//bi-directional many-to-one association to RUtenteFascicolo
	@OneToMany(mappedBy="utente")
	private Set<RUtenteFascicolo> RUtenteFascicolos;

	//bi-directional many-to-one association to RUtenteReport
	@OneToMany(mappedBy="utente")
	private Set<RUtenteReport> RUtenteReports;

	//bi-directional many-to-one association to RUtenteRicerca
	@OneToMany(mappedBy="utente")
	private Set<RUtenteRicerca> RUtenteRicercas;
	
	//bi-directional many-to-one association to RUtenteGruppo
	@OneToMany(mappedBy="utente")
	private Set<RUtenteGruppo> RUtenteGruppos;
	
	@Column(name="SIGLA_UTENTE")
	private String siglaUtente;

	public Utente() {
	}
	
	

	public String getSiglaUtente() {
		return siglaUtente;
	}



	public void setSiglaUtente(String siglaUtente) {
		this.siglaUtente = siglaUtente;
	}



	public String getMatricolaUtil() {
		return this.matricolaUtil;
	}

	public void setMatricolaUtil(String matricolaUtil) {
		this.matricolaUtil = matricolaUtil;
	}

	public String getCodiceSocietaAppart() {
		return this.codiceSocietaAppart;
	}

	public void setCodiceSocietaAppart(String codiceSocietaAppart) {
		this.codiceSocietaAppart = codiceSocietaAppart;
	}

	public String getCodiceSocietaUtil() {
		return this.codiceSocietaUtil;
	}

	public void setCodiceSocietaUtil(String codiceSocietaUtil) {
		this.codiceSocietaUtil = codiceSocietaUtil;
	}

	public String getCodiceUnitaAppart() {
		return this.codiceUnitaAppart;
	}

	public void setCodiceUnitaAppart(String codiceUnitaAppart) {
		this.codiceUnitaAppart = codiceUnitaAppart;
	}

	public String getCodiceUnitaUtil() {
		return this.codiceUnitaUtil;
	}

	public void setCodiceUnitaUtil(String codiceUnitaUtil) {
		this.codiceUnitaUtil = codiceUnitaUtil;
	}

	public String getDescrUnitaAppart() {
		return this.descrUnitaAppart;
	}

	public void setDescrUnitaAppart(String descrUnitaAppart) {
		this.descrUnitaAppart = descrUnitaAppart;
	}

	public String getDescrUnitaUtil() {
		return this.descrUnitaUtil;
	}

	public void setDescrUnitaUtil(String descrUnitaUtil) {
		this.descrUnitaUtil = descrUnitaUtil;
	}

	public String getMatricolaAppart() {
		return this.matricolaAppart;
	}

	public void setMatricolaAppart(String matricolaAppart) {
		this.matricolaAppart = matricolaAppart;
	}

	public String getMatricolaRespAppart() {
		return this.matricolaRespAppart;
	}

	public void setMatricolaRespAppart(String matricolaRespAppart) {
		this.matricolaRespAppart = matricolaRespAppart;
	}

	public String getMatricolaRespUtil() {
		return this.matricolaRespUtil;
	}

	public void setMatricolaRespUtil(String matricolaRespUtil) {
		this.matricolaRespUtil = matricolaRespUtil;
	}

	public String getNominativoAppart() {
		return this.nominativoAppart;
	}

	public void setNominativoAppart(String nominativoAppart) {
		this.nominativoAppart = nominativoAppart;
	}

	public String getNominativoRespAppart() {
		return this.nominativoRespAppart;
	}

	public void setNominativoRespAppart(String nominativoRespAppart) {
		this.nominativoRespAppart = nominativoRespAppart;
	}

	public String getNominativoRespUtil() {
		return this.nominativoRespUtil;
	}

	public void setNominativoRespUtil(String nominativoRespUtil) {
		this.nominativoRespUtil = nominativoRespUtil;
	}

	@AuditedObjectName
	public String getNominativoUtil() {
		return this.nominativoUtil;
	}

	public void setNominativoUtil(String nominativoUtil) {
		this.nominativoUtil = nominativoUtil;
	}

	public String getRagioneSocialeSocietaAppart() {
		return this.ragioneSocialeSocietaAppart;
	}

	public void setRagioneSocialeSocietaAppart(String ragioneSocialeSocietaAppart) {
		this.ragioneSocialeSocietaAppart = ragioneSocialeSocietaAppart;
	}

	public String getRagioneSocialeSocietaUtil() {
		return this.ragioneSocialeSocietaUtil;
	}

	public void setRagioneSocialeSocietaUtil(String ragioneSocialeSocietaUtil) {
		this.ragioneSocialeSocietaUtil = ragioneSocialeSocietaUtil;
	}

	public String getResponsabileAppart() {
		return this.responsabileAppart;
	}

	public void setResponsabileAppart(String responsabileAppart) {
		this.responsabileAppart = responsabileAppart;
	}

	public String getResponsabileUtil() {
		return this.responsabileUtil;
	}

	public void setResponsabileUtil(String responsabileUtil) {
		this.responsabileUtil = responsabileUtil;
	}

	public String getUserIdResponsabileAppart() {
		return this.userIdResponsabileAppart;
	}

	public void setUserIdResponsabileAppart(String userIdResponsabileAppart) {
		this.userIdResponsabileAppart = userIdResponsabileAppart;
	}

	public String getUserIdResponsabileUtil() {
		return this.userIdResponsabileUtil;
	}

	public void setUserIdResponsabileUtil(String userIdResponsabileUtil) {
		this.userIdResponsabileUtil = userIdResponsabileUtil;
	}

	public String getUseridUtil() {
		return this.useridUtil;
	}

	public void setUseridUtil(String useridUtil) {
		this.useridUtil = useridUtil;
	}
	
	public String getEmailUtil() {
		return this.emailUtil;
	}

	public void setEmailUtil(String emailUtil) {
		this.emailUtil = emailUtil;
	}

	public Set<ConfigurazioneStepWf> getConfigurazioneStepWfs() {
		return this.configurazioneStepWfs;
	}

	public void setConfigurazioneStepWfs(Set<ConfigurazioneStepWf> configurazioneStepWfs) {
		this.configurazioneStepWfs = configurazioneStepWfs;
	}

	public ConfigurazioneStepWf addConfigurazioneStepWf(ConfigurazioneStepWf configurazioneStepWf) {
		getConfigurazioneStepWfs().add(configurazioneStepWf);
		configurazioneStepWf.setUtente(this);

		return configurazioneStepWf;
	}

	public ConfigurazioneStepWf removeConfigurazioneStepWf(ConfigurazioneStepWf configurazioneStepWf) {
		getConfigurazioneStepWfs().remove(configurazioneStepWf);
		configurazioneStepWf.setUtente(null);

		return configurazioneStepWf;
	}
	
	public Set<StepWf> getStepWfs() {
		return this.stepWfs;
	}

	public void setStepWfs(Set<StepWf> stepWfs) {
		this.stepWfs = stepWfs;
	}

	public StepWf addStepWf(StepWf stepWf) {
		getStepWfs().add(stepWf);
		stepWf.setUtente(this);

		return stepWf;
	}

	public StepWf removeStepWf(StepWf stepWf) {
		getStepWfs().remove(stepWf);
		stepWf.setUtente(null);

		return stepWf;
	}

	public Set<RUtenteFascicolo> getRUtenteFascicolos() {
		return this.RUtenteFascicolos;
	}

	public void setRUtenteFascicolos(Set<RUtenteFascicolo> RUtenteFascicolos) {
		this.RUtenteFascicolos = RUtenteFascicolos;
	}

	public RUtenteFascicolo addRUtenteFascicolo(RUtenteFascicolo RUtenteFascicolo) {
		getRUtenteFascicolos().add(RUtenteFascicolo);
		RUtenteFascicolo.setUtente(this);

		return RUtenteFascicolo;
	}

	public RUtenteFascicolo removeRUtenteFascicolo(RUtenteFascicolo RUtenteFascicolo) {
		getRUtenteFascicolos().remove(RUtenteFascicolo);
		RUtenteFascicolo.setUtente(null);

		return RUtenteFascicolo;
	}

	public Set<RUtenteReport> getRUtenteReports() {
		return this.RUtenteReports;
	}

	public void setRUtenteReports(Set<RUtenteReport> RUtenteReports) {
		this.RUtenteReports = RUtenteReports;
	}

	public RUtenteReport addRUtenteReport(RUtenteReport RUtenteReport) {
		getRUtenteReports().add(RUtenteReport);
		RUtenteReport.setUtente(this);

		return RUtenteReport;
	}

	public RUtenteReport removeRUtenteReport(RUtenteReport RUtenteReport) {
		getRUtenteReports().remove(RUtenteReport);
		RUtenteReport.setUtente(null);

		return RUtenteReport;
	}

	public Set<RUtenteRicerca> getRUtenteRicercas() {
		return this.RUtenteRicercas;
	}

	public void setRUtenteRicercas(Set<RUtenteRicerca> RUtenteRicercas) {
		this.RUtenteRicercas = RUtenteRicercas;
	}

	public RUtenteRicerca addRUtenteRicerca(RUtenteRicerca RUtenteRicerca) {
		getRUtenteRicercas().add(RUtenteRicerca);
		RUtenteRicerca.setUtente(this);

		return RUtenteRicerca;
	}

	public RUtenteRicerca removeRUtenteRicerca(RUtenteRicerca RUtenteRicerca) {
		getRUtenteRicercas().remove(RUtenteRicerca);
		RUtenteRicerca.setUtente(null);

		return RUtenteRicerca;
	}
	
	public Set<RUtenteGruppo> getRUtenteGruppos() {
		return this.RUtenteGruppos;
	}

	public void setRUtenteGruppos(Set<RUtenteGruppo> RUtenteGruppos) {
		this.RUtenteGruppos = RUtenteGruppos;
	}

	public RUtenteGruppo addRUtenteGruppo(RUtenteGruppo RUtenteGruppo) {
		getRUtenteGruppos().add(RUtenteGruppo);
		RUtenteGruppo.setUtente(this);

		return RUtenteGruppo;
	}

	public RUtenteGruppo removeRUtenteGruppo(RUtenteGruppo RUtenteGruppo) {
		getRUtenteGruppos().remove(RUtenteGruppo);
		RUtenteGruppo.setUtente(null);

		return RUtenteGruppo;
	}

	public String getAssente() {
		return assente;
	}
	
	public void setAssente(String assente) {
		this.assente = assente;
	}
	
	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}



	@Override
	public int compareTo(Utente o) {
		return o.getNominativoUtil().compareTo(this.nominativoUtil);
	}
}