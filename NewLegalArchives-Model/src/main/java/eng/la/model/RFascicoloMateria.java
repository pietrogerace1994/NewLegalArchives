package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_FASCICOLO_MATERIA database table.
 * 
 */
@Entity
@Table(name="R_FASCICOLO_MATERIA")
@NamedQuery(name="RFascicoloMateria.findAll", query="SELECT r FROM RFascicoloMateria r")
public class RFascicoloMateria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_FASCICOLO_MATERIA_ID_GENERATOR", sequenceName="R_FASCICOLO_MATERIA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_FASCICOLO_MATERIA_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to Materia
	@ManyToOne()
	@JoinColumn(name="ID_MATERIA")
	private Materia materia;

	public RFascicoloMateria() {
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

	public Fascicolo getFascicolo() {
		return this.fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

	public Materia getMateria() {
		return this.materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

}