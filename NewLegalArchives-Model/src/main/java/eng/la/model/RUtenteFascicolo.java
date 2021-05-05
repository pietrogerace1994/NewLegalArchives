package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_UTENTE_FASCICOLO database table.
 * 
 */
@Entity
@Table(name="R_UTENTE_FASCICOLO")
@NamedQuery(name="RUtenteFascicolo.findAll", query="SELECT r FROM RUtenteFascicolo r")
public class RUtenteFascicolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_UTENTE_FASCICOLO_ID_GENERATOR", sequenceName="R_UTENTE_FASCICOLO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_UTENTE_FASCICOLO_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to Utente
	@ManyToOne()
	@JoinColumn(name="MATRICOLA_UTIL")
	private Utente utente;

	public RUtenteFascicolo() {
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

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}