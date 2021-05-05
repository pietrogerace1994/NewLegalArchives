package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_RICORSO_ORGANO_GIUDICANTE database table.
 * 
 */
@Entity
@Table(name="R_RICORSO_ORGANO_GIUDICANTE")
@NamedQuery(name="RRicorsoOrganoGiudicante.findAll", query="SELECT r FROM RRicorsoOrganoGiudicante r")
public class RRicorsoOrganoGiudicante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_RICORSO_ORGANO_GIUDICANTE_ID_GENERATOR", sequenceName="R_RICORSO_ORGANO_GIUDICANTE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_RICORSO_ORGANO_GIUDICANTE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to OrganoGiudicante
	@ManyToOne()
	@JoinColumn(name="ID_ORGANO_GIUDICANTE")
	private OrganoGiudicante organoGiudicante;

	//bi-directional many-to-one association to Ricorso
	@ManyToOne()
	@JoinColumn(name="ID_RICORSO")
	private Ricorso ricorso;

	public RRicorsoOrganoGiudicante() {
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

	public OrganoGiudicante getOrganoGiudicante() {
		return this.organoGiudicante;
	}

	public void setOrganoGiudicante(OrganoGiudicante organoGiudicante) {
		this.organoGiudicante = organoGiudicante;
	}

	public Ricorso getRicorso() {
		return this.ricorso;
	}

	public void setRicorso(Ricorso ricorso) {
		this.ricorso = ricorso;
	}

}