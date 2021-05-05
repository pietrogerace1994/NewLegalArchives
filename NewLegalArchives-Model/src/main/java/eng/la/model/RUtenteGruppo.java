package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_UTENTE_GRUPPO database table.
 * 
 */
@Entity
@Table(name="R_UTENTE_GRUPPO")
@NamedQuery(name="RUtenteGruppo.findAll", query="SELECT r FROM RUtenteGruppo r")
public class RUtenteGruppo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_UTENTE_GRUPPO_ID_GENERATOR", sequenceName="R_UTENTE_GRUPPO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_UTENTE_GRUPPO_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to Utente
	@ManyToOne()
	@JoinColumn(name="MATRICOLA_UTENTE")
	private Utente utente;

	//bi-directional many-to-one association to GruppoUtente
	@ManyToOne()
	@JoinColumn(name="ID_GRUPPO_UTENTE")
	private GruppoUtente gruppoUtente;

	public RUtenteGruppo() {
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

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public GruppoUtente getGruppoUtente() {
		return this.gruppoUtente;
	}

	public void setGruppoUtente(GruppoUtente gruppoUtente) {
		this.gruppoUtente = gruppoUtente;
	}

}