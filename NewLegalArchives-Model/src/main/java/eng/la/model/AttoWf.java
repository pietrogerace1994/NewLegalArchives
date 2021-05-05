package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the ATTO_WF database table.
 * 
 */
@Entity
@Table(name="ATTO_WF")
@NamedQuery(name="AttoWf.findAll", query="SELECT a FROM AttoWf a")
public class AttoWf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ATTO_WF_ID_GENERATOR", sequenceName="ATTO_WF_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="ATTO_WF_ID_GENERATOR")
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

	@Column(name="UTENTE_CREAZIONE")
	private String utenteCreazione;
	
	@Column(name="UTENTE_ASSEGNATARIO")
	private String utenteAssegnatario;

	//bi-directional many-to-one association to Atto
	@ManyToOne()
	@JoinColumn(name="ID_ATTO")
	private Atto atto;

	//bi-directional many-to-one association to StatoWf
	@ManyToOne()
	@JoinColumn(name="ID_STATO_WF")
	private StatoWf statoWf;

	//bi-directional many-to-one association to StepWf
	@OneToMany(mappedBy="attoWf")
	private Set<StepWf> stepWfs;

	public AttoWf() {
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

	public String getUtenteCreazione() {
		return this.utenteCreazione;
	}

	public void setUtenteCreazione(String utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}

	public Atto getAtto() {
		return this.atto;
	}

	public void setAtto(Atto atto) {
		this.atto = atto;
	}

	public StatoWf getStatoWf() {
		return this.statoWf;
	}

	public void setStatoWf(StatoWf statoWf) {
		this.statoWf = statoWf;
	}

	public String getUtenteAssegnatario() {
		return this.utenteAssegnatario;
	}

	public void setUtenteAssegnatario(String utenteAssegnatario) {
		this.utenteAssegnatario = utenteAssegnatario;
	}

	public Set<StepWf> getStepWfs() {
		return this.stepWfs;
	}

	public void setStepWfs(Set<StepWf> stepWfs) {
		this.stepWfs = stepWfs;
	}

	public StepWf addStepWf(StepWf stepWf) {
		getStepWfs().add(stepWf);
		stepWf.setAttoWf(this);

		return stepWf;
	}

	public StepWf removeStepWf(StepWf stepWf) {
		getStepWfs().remove(stepWf);
		stepWf.setAttoWf(null);

		return stepWf;
	}

}