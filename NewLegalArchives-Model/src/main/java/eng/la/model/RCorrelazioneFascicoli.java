package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the R_CORRELAZIONE_FASCICOLI database table.
 * 
 */
@Entity
@Table(name="R_CORRELAZIONE_FASCICOLI")
@NamedQuery(name="RCorrelazioneFascicoli.findAll", query="SELECT r FROM RCorrelazioneFascicoli r")
public class RCorrelazioneFascicoli implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_CORRELAZIONE_FASCICOLI_ID_GENERATOR", sequenceName="R_CORR_FASCICOLI_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_CORRELAZIONE_FASCICOLI_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO1")
	private Fascicolo fascicolo1;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO2")
	private Fascicolo fascicolo2;

	public RCorrelazioneFascicoli() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Fascicolo getFascicolo1() {
		return this.fascicolo1;
	}

	public void setFascicolo1(Fascicolo fascicolo1) {
		this.fascicolo1 = fascicolo1;
	}

	public Fascicolo getFascicolo2() {
		return this.fascicolo2;
	}

	public void setFascicolo2(Fascicolo fascicolo2) {
		this.fascicolo2 = fascicolo2;
	}

}