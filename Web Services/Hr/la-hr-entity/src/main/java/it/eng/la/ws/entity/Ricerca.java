package it.eng.la.ws.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RICERCA database table.
 * 
 */
@Entity
public class Ricerca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="CLASSE_RICERCA")
	private String classeRicerca;

	@Column(name="FILTRI_RICERCA")
	private String filtriRicerca;

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

}