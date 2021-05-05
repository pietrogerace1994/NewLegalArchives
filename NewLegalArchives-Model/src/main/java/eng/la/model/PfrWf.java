package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the PFR_WF database table.
 * 
 */
@Entity
@Table(name="PFR_WF")
@NamedQuery(name="PfrWf.findAll", query="SELECT p FROM PfrWf p")
public class PfrWf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PFR_WF_ID_GENERATOR", sequenceName="PFR_WF_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PFR_WF_ID_GENERATOR")
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

	//bi-directional many-to-one association to Pfr
	@ManyToOne()
	@JoinColumn(name="ID_PFR")
	private Pfr pfr;

	//bi-directional many-to-one association to StatoWf
	@ManyToOne()
	@JoinColumn(name="ID_STATO_WF")
	private StatoWf statoWf;

	//bi-directional many-to-one association to StepWf
	@OneToMany(mappedBy="pfrWf")
	private Set<StepWf> stepWfs;

	public PfrWf() {
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

	public Pfr getPfr() {
		return this.pfr;
	}

	public void setPfr(Pfr pfr) {
		this.pfr = pfr;
	}

	public StatoWf getStatoWf() {
		return this.statoWf;
	}

	public void setStatoWf(StatoWf statoWf) {
		this.statoWf = statoWf;
	}

	public Set<StepWf> getStepWfs() {
		return this.stepWfs;
	}

	public void setStepWfs(Set<StepWf> stepWfs) {
		this.stepWfs = stepWfs;
	}

	public StepWf addStepWf(StepWf stepWf) {
		getStepWfs().add(stepWf);
		stepWf.setPfrWf(this);

		return stepWf;
	}

	public StepWf removeStepWf(StepWf stepWf) {
		getStepWfs().remove(stepWf);
		stepWf.setPfrWf(null);

		return stepWf;
	}

}