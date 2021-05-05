package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_PROFESSIONISTA_NAZIONE database table.
 * 
 */
@Entity
@Table(name="R_PROFESSIONISTA_NAZIONE")
@NamedQuery(name="RProfessionistaNazione.findAll", query="SELECT r FROM RProfessionistaNazione r")
public class RProfessionistaNazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_PROFESSIONISTA_NAZIONE_ID_GENERATOR", sequenceName="R_PROFESSIONISTA_NAZIONE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_PROFESSIONISTA_NAZIONE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to Nazione
	@ManyToOne()
	@JoinColumn(name="ID_NAZIONE")
	private Nazione nazione;

	//bi-directional many-to-one association to ProfessionistaEsterno
	@ManyToOne()
	@JoinColumn(name="ID_PROF_ESTERNO")
	private ProfessionistaEsterno professionistaEsterno;

	public RProfessionistaNazione() {
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

	public Nazione getNazione() {
		return this.nazione;
	}

	public void setNazione(Nazione nazione) {
		this.nazione = nazione;
	}

	public ProfessionistaEsterno getProfessionistaEsterno() {
		return this.professionistaEsterno;
	}

	public void setProfessionistaEsterno(ProfessionistaEsterno professionistaEsterno) {
		this.professionistaEsterno = professionistaEsterno;
	}

}