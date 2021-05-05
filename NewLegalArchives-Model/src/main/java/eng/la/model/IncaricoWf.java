package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the INCARICO_WF database table.
 * 
 */
@Entity
@Table(name="INCARICO_WF")
@NamedQuery(name="IncaricoWf.findAll", query="SELECT i FROM IncaricoWf i")
public class IncaricoWf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INCARICO_WF_ID_GENERATOR", sequenceName="INCARICO_WF_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="INCARICO_WF_ID_GENERATOR")
	private long id;

	@Column(name="COLLEGIO_ARBITRALE")
	private String collegioArbitrale;

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

	//DARIO C *** aggiunta colonna in tabella ***********
	@Column(name="UTENTE_ASSEGNATARIO")
	private String utenteAssegnatario;
	//***************************************************
	
	//bi-directional many-to-one association to Incarico
	@ManyToOne()
	@JoinColumn(name="ID_INCARICO")
	private Incarico incarico;

	//bi-directional many-to-one association to StatoWf
	@ManyToOne()
	@JoinColumn(name="ID_STATO_WF")
	private StatoWf statoWf;

	//bi-directional many-to-one association to StepWf
	@OneToMany(mappedBy="incaricoWf")
	private Set<StepWf> stepWfs;

	public IncaricoWf() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCollegioArbitrale() {
		return this.collegioArbitrale;
	}

	public void setCollegioArbitrale(String collegioArbitrale) {
		this.collegioArbitrale = collegioArbitrale;
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

	
	//DARIO C *****************************************************
	public String getUtenteAssegnatario() {
		return utenteAssegnatario;
	}

	public void setUtenteAssegnatario(String utenteAssegnatario) {
		this.utenteAssegnatario = utenteAssegnatario;
	}
	//*************************************************************
	
	public Incarico getIncarico() {
		return this.incarico;
	}

	public void setIncarico(Incarico incarico) {
		this.incarico = incarico;
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
		stepWf.setIncaricoWf(this);

		return stepWf;
	}

	public StepWf removeStepWf(StepWf stepWf) {
		getStepWfs().remove(stepWf);
		stepWf.setIncaricoWf(null);

		return stepWf;
	}

}