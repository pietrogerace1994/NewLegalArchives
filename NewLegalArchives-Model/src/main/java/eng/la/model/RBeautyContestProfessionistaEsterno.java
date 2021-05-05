package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_BC_PROF_EST database table.
 * 
 */
@Entity
@Table(name="R_BC_PROF_EST")
@NamedQuery(name="RBcProfEst.findAll", query="SELECT r FROM RBeautyContestProfessionistaEsterno r")
public class RBeautyContestProfessionistaEsterno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_BC_PROF_EST_ID_GENERATOR", sequenceName="R_BC_PROF_EST_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_BC_PROF_EST_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to BeautyContest
	@ManyToOne()
	@JoinColumn(name="ID_BEAUTY_CONTEST")
	private BeautyContest beautyContest;

	//bi-directional many-to-one association to ProfessionistaEsterno
	@ManyToOne()
	@JoinColumn(name="ID_PROF_EST")
	private ProfessionistaEsterno professionistaEsterno;

	public RBeautyContestProfessionistaEsterno() {
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

	public ProfessionistaEsterno getProfessionistaEsterno() {
		return this.professionistaEsterno;
	}

	public void setProfessionistaEsterno(ProfessionistaEsterno professionistaEsterno) {
		this.professionistaEsterno = professionistaEsterno;
	}

}