package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the REPORT database table.
 * 
 */
@Entity
@NamedQuery(name="Report.findAll", query="SELECT r FROM Report r")
public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REPORT_ID_GENERATOR", sequenceName="REPORT_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="REPORT_ID_GENERATOR")
	private long id;

	@Column(name="CLASSE_REPORT")
	private String classeReport;

	@Column(name="FILTRI_REPORT")
	private String filtriReport;

	//bi-directional many-to-one association to RUtenteReport
	@OneToMany(mappedBy="report")
	private Set<RUtenteReport> RUtenteReports;

	public Report() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClasseReport() {
		return this.classeReport;
	}

	public void setClasseReport(String classeReport) {
		this.classeReport = classeReport;
	}

	public String getFiltriReport() {
		return this.filtriReport;
	}

	public void setFiltriReport(String filtriReport) {
		this.filtriReport = filtriReport;
	}

	public Set<RUtenteReport> getRUtenteReports() {
		return this.RUtenteReports;
	}

	public void setRUtenteReports(Set<RUtenteReport> RUtenteReports) {
		this.RUtenteReports = RUtenteReports;
	}

	public RUtenteReport addRUtenteReport(RUtenteReport RUtenteReport) {
		getRUtenteReports().add(RUtenteReport);
		RUtenteReport.setReport(this);

		return RUtenteReport;
	}

	public RUtenteReport removeRUtenteReport(RUtenteReport RUtenteReport) {
		getRUtenteReports().remove(RUtenteReport);
		RUtenteReport.setReport(null);

		return RUtenteReport;
	}

}