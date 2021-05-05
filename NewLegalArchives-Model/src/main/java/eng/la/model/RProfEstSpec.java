package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_PROF_EST_SPEC database table.
 * 
 */
@Entity
@Table(name="R_PROF_EST_SPEC")
@NamedQuery(name="RProfEstSpec.findAll", query="SELECT r FROM RProfEstSpec r")
public class RProfEstSpec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_PROF_EST_SPEC_ID_GENERATOR", sequenceName="R_PROF_EST_SPEC_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_PROF_EST_SPEC_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to ProfessionistaEsterno
	@ManyToOne()
	@JoinColumn(name="ID_PROF_ESTERNO")
	private ProfessionistaEsterno professionistaEsterno;

	//bi-directional many-to-one association to Specializzazione
	@ManyToOne()
	@JoinColumn(name="ID_SPECIALIZZAZIONE")
	private Specializzazione specializzazione;

	public RProfEstSpec() {
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

	public ProfessionistaEsterno getProfessionistaEsterno() {
		return this.professionistaEsterno;
	}

	public void setProfessionistaEsterno(ProfessionistaEsterno professionistaEsterno) {
		this.professionistaEsterno = professionistaEsterno;
	}

	public Specializzazione getSpecializzazione() {
		return this.specializzazione;
	}

	public void setSpecializzazione(Specializzazione specializzazione) {
		this.specializzazione = specializzazione;
	}

}