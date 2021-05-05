package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;

import eng.la.model.audit.AuditedObjectName;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the STATO_BEAUTY_CONTEST database table.
 * 
 */
@Entity
@Table(name="STATO_BEAUTY_CONTEST")
@NamedQuery(name="StatoBeautyContest.findAll", query="SELECT s FROM StatoBeautyContest s")
public class StatoBeautyContest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_BEAUTY_CONTEST_ID_GENERATOR", sequenceName="STATO_BEAUTY_CONTEST_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_BEAUTY_CONTEST_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to BeautyContest
	@OneToMany(mappedBy="statoBeautyContest")
	private Set<BeautyContest> beautyContests;

	public StatoBeautyContest() {
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

	public Set<BeautyContest> getBeautyContests() {
		return this.beautyContests;
	}

	public void setBeautyContests(Set<BeautyContest> beautyContests) {
		this.beautyContests = beautyContests;
	}

	public BeautyContest addBeautyContest(BeautyContest beautyContest) {
		getBeautyContests().add(beautyContest);
		beautyContest.setStatoBeautyContest(this);
		return beautyContest;
	}

	public BeautyContest removeBeautyContest(BeautyContest beautyContest) {
		getBeautyContests().remove(beautyContest);
		beautyContest.setStatoBeautyContest(null);
		return beautyContest;
	}

}