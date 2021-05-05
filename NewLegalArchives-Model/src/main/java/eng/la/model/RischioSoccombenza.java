package eng.la.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the RISCHIO_SOCCOMBENZA database table.
 * 
 */
@Entity
@Table(name="RISCHIO_SOCCOMBENZA")
@NamedQuery(name="RischioSoccombenza.findAll", query="SELECT r FROM RischioSoccombenza r")
public class RischioSoccombenza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RISCHIO_SOCCOMBENZA_ID_GENERATOR", sequenceName="RISCHIO_SOCCOMBENZA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="RISCHIO_SOCCOMBENZA_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to SchedaFondoRischi
		@OneToMany(mappedBy="rischioSoccombenza")
		private Set<SchedaFondoRischi> schedaFondoRischis;


	public RischioSoccombenza() {
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

	public Set<SchedaFondoRischi> getSchedaFondoRischis() {
		return this.schedaFondoRischis;
	}

	public void setSchedaFondoRischis(Set<SchedaFondoRischi> schedaFondoRischis) {
		this.schedaFondoRischis = schedaFondoRischis;
	}

	public SchedaFondoRischi addSchedaFondoRischi(SchedaFondoRischi schedaFondoRischi) {
		getSchedaFondoRischis().add(schedaFondoRischi);
		schedaFondoRischi.setRischioSoccombenza(this);

		return schedaFondoRischi;
	}

	public SchedaFondoRischi removeSchedaFondoRischi(SchedaFondoRischi schedaFondoRischi) {
		getSchedaFondoRischis().remove(schedaFondoRischi);
		schedaFondoRischi.setRischioSoccombenza(null);

		return schedaFondoRischi;
	}
}