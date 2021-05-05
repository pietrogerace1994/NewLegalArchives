package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_INCARICO_PROFORMA_SOCIETA database table.
 * 
 */
@Entity
@Table(name="R_INCARICO_PROFORMA_SOCIETA")
@NamedQuery(name="RIncaricoProformaSocieta.findAll", query="SELECT r FROM RIncaricoProformaSocieta r")
public class RIncaricoProformaSocieta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_INCARICO_PROFORMA_SOCIETA_ID_GENERATOR", sequenceName="R_INC_PROF_SOC_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_INCARICO_PROFORMA_SOCIETA_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to Incarico
	@ManyToOne()
	@JoinColumn(name="ID_INCARICO")
	private Incarico incarico;

	//bi-directional many-to-one association to Proforma
	@ManyToOne()
	@JoinColumn(name="ID_PROFORMA")
	private Proforma proforma;

	//bi-directional many-to-one association to Societa
	@ManyToOne()
	@JoinColumn(name="ID_SOCIETA")
	private Societa societa;

	public RIncaricoProformaSocieta() {
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

	public Incarico getIncarico() {
		return this.incarico;
	}

	public void setIncarico(Incarico incarico) {
		this.incarico = incarico;
	}

	public Proforma getProforma() {
		return this.proforma;
	}

	public void setProforma(Proforma proforma) {
		this.proforma = proforma;
	}

	public Societa getSocieta() {
		return this.societa;
	}

	public void setSocieta(Societa societa) {
		this.societa = societa;
	}

}