package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;

import eng.la.model.audit.AuditedObjectName;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the STATO_INCARICO database table.
 * 
 */
@Entity
@Table(name="STATO_SCHEDA_FONDO_RISCHI")
@NamedQuery(name="StatoSchedaFondoRischi.findAll", query="SELECT s FROM StatoSchedaFondoRischi s")
public class StatoSchedaFondoRischi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_SCHEDA_FONDO_RISCHI_ID_GENERATOR", sequenceName="STATO_SCHEDA_FONDO_RISCHI_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_SCHEDA_FONDO_RISCHI_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to SchedaFondoRischi
	@OneToMany(mappedBy="statoSchedaFondoRischi")
	private Set<SchedaFondoRischi> schedas;

	public StatoSchedaFondoRischi() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodGruppoLingua() {
		return this.codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	@AuditedObjectName
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Set<SchedaFondoRischi> getSchedas() {
		return this.schedas;
	}

	public void setSchedas(Set<SchedaFondoRischi> schedas) {
		this.schedas = schedas;
	}

	public SchedaFondoRischi addSchedaFondoRischi(SchedaFondoRischi schedaFondoRischi) {
		getSchedas().add(schedaFondoRischi);
		schedaFondoRischi.setStatoSchedaFondoRischi(this);

		return schedaFondoRischi;
	}

	public SchedaFondoRischi removeschedaFondoRischi(SchedaFondoRischi schedaFondoRischi) {
		getSchedas().remove(schedaFondoRischi);
		schedaFondoRischi.setStatoSchedaFondoRischi(null);

		return schedaFondoRischi;
	}

}