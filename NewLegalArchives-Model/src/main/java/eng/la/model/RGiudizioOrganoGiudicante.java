package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_GIUDIZIO_ORGANO_GIUDICANTE database table.
 * 
 */
@Entity
@Table(name="R_GIUDIZIO_ORGANO_GIUDICANTE")
@NamedQuery(name="RGiudizioOrganoGiudicante.findAll", query="SELECT r FROM RGiudizioOrganoGiudicante r")
public class RGiudizioOrganoGiudicante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_GIUDIZIO_ORGANO_GIUDICANTE_ID_GENERATOR", sequenceName="R_GIUDIZIO_ORGANO_GIUDICANTE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_GIUDIZIO_ORGANO_GIUDICANTE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to Giudizio
	@ManyToOne()
	@JoinColumn(name="ID_GIUDIZIO")
	private Giudizio giudizio;

	//bi-directional many-to-one association to OrganoGiudicante
	@ManyToOne()
	@JoinColumn(name="ID_ORGANO_GIUDICANTE")
	private OrganoGiudicante organoGiudicante;

	public RGiudizioOrganoGiudicante() {
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

	public Giudizio getGiudizio() {
		return this.giudizio;
	}

	public void setGiudizio(Giudizio giudizio) {
		this.giudizio = giudizio;
	}

	public OrganoGiudicante getOrganoGiudicante() {
		return this.organoGiudicante;
	}

	public void setOrganoGiudicante(OrganoGiudicante organoGiudicante) {
		this.organoGiudicante = organoGiudicante;
	}

}