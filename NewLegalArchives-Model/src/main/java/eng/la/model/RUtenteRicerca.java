package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the R_UTENTE_RICERCA database table.
 * 
 */
@Entity
@Table(name="R_UTENTE_RICERCA")
@NamedQuery(name="RUtenteRicerca.findAll", query="SELECT r FROM RUtenteRicerca r")
public class RUtenteRicerca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_UTENTE_RICERCA_ID_GENERATOR", sequenceName="R_UTENTE_RICERCA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_UTENTE_RICERCA_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to Ricerca
	@ManyToOne()
	@JoinColumn(name="ID_RICERCA")
	private Ricerca ricerca;

	//bi-directional many-to-one association to Utente
	@ManyToOne()
	@JoinColumn(name="MATRICOLA_UTIL")
	private Utente utente;

	public RUtenteRicerca() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Ricerca getRicerca() {
		return this.ricerca;
	}

	public void setRicerca(Ricerca ricerca) {
		this.ricerca = ricerca;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}