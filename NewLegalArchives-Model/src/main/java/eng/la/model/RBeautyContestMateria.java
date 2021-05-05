package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_BC_MATERIA database table.
 * 
 */
@Entity
@Table(name="R_BC_MATERIA")
@NamedQuery(name="RBcMateria.findAll", query="SELECT r FROM RBeautyContestMateria r")
public class RBeautyContestMateria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_BC_MATERIA_ID_GENERATOR", sequenceName="R_BC_MATERIA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_BC_MATERIA_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to BeautyContest
	@ManyToOne()
	@JoinColumn(name="ID_BEAUTY_CONTEST")
	private BeautyContest beautyContest;

	//bi-directional many-to-one association to Materia
	@ManyToOne()
	@JoinColumn(name="ID_MATERIA")
	private Materia materia;

	public RBeautyContestMateria() {
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

	public BeautyContest getBeautyContest() {
		return this.beautyContest;
	}

	public void setBeautyContest(BeautyContest beautyContest) {
		this.beautyContest = beautyContest;
	}

	public Materia getMateria() {
		return this.materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

}