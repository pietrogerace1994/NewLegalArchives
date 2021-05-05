package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the STATO_PFR database table.
 * 
 */
@Entity
@Table(name="STATO_PFR")
@NamedQuery(name="StatoPfr.findAll", query="SELECT s FROM StatoPfr s")
public class StatoPfr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_PFR_ID_GENERATOR", sequenceName="STATO_PFR_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_PFR_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to Pfr
	@OneToMany(mappedBy="statoPfr")
	private Set<Pfr> pfrs;

	public StatoPfr() {
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

	public Set<Pfr> getPfrs() {
		return this.pfrs;
	}

	public void setPfrs(Set<Pfr> pfrs) {
		this.pfrs = pfrs;
	}

	public Pfr addPfr(Pfr pfr) {
		getPfrs().add(pfr);
		pfr.setStatoPfr(this);

		return pfr;
	}

	public Pfr removePfr(Pfr pfr) {
		getPfrs().remove(pfr);
		pfr.setStatoPfr(null);

		return pfr;
	}

}