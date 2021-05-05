package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the RICERCA database table.
 * 
 */
@Entity
@NamedQuery(name="Ricerca.findAll", query="SELECT r FROM Ricerca r")
public class Ricerca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RICERCA_ID_GENERATOR", sequenceName="RICERCA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="RICERCA_ID_GENERATOR")
	private long id;

	@Column(name="CLASSE_RICERCA")
	private String classeRicerca;

	@Column(name="FILTRI_RICERCA")
	private String filtriRicerca;

	//bi-directional many-to-one association to RUtenteRicerca
	@OneToMany(mappedBy="ricerca")
	private Set<RUtenteRicerca> RUtenteRicercas;

	public Ricerca() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClasseRicerca() {
		return this.classeRicerca;
	}

	public void setClasseRicerca(String classeRicerca) {
		this.classeRicerca = classeRicerca;
	}

	public String getFiltriRicerca() {
		return this.filtriRicerca;
	}

	public void setFiltriRicerca(String filtriRicerca) {
		this.filtriRicerca = filtriRicerca;
	}

	public Set<RUtenteRicerca> getRUtenteRicercas() {
		return this.RUtenteRicercas;
	}

	public void setRUtenteRicercas(Set<RUtenteRicerca> RUtenteRicercas) {
		this.RUtenteRicercas = RUtenteRicercas;
	}

	public RUtenteRicerca addRUtenteRicerca(RUtenteRicerca RUtenteRicerca) {
		getRUtenteRicercas().add(RUtenteRicerca);
		RUtenteRicerca.setRicerca(this);

		return RUtenteRicerca;
	}

	public RUtenteRicerca removeRUtenteRicerca(RUtenteRicerca RUtenteRicerca) {
		getRUtenteRicercas().remove(RUtenteRicerca);
		RUtenteRicerca.setRicerca(null);

		return RUtenteRicerca;
	}

}