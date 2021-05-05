package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the R_UTENTE_REPORT database table.
 * 
 */
@Entity
@Table(name="R_UTENTE_REPORT")
@NamedQuery(name="RUtenteReport.findAll", query="SELECT r FROM RUtenteReport r")
public class RUtenteReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_UTENTE_REPORT_ID_GENERATOR", sequenceName="R_UTENTE_REPORT_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_UTENTE_REPORT_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to Report
	@ManyToOne()
	@JoinColumn(name="ID_REPORT")
	private Report report;

	//bi-directional many-to-one association to Utente
	@ManyToOne()
	@JoinColumn(name="MATRICOLA_UTIL")
	private Utente utente;

	public RUtenteReport() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Report getReport() {
		return this.report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}