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
 * The persistent class for the STATO_DUE_DILIGENCE database table.
 * 
 */
@Entity
@Table(name="STATO_DUE_DILIGENCE")
@NamedQuery(name="StatoDueDiligence.findAll", query="SELECT s FROM StatoDueDiligence s")
public class StatoDueDiligence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_DUE_DILIGENCE_ID_GENERATOR", sequenceName="STATO_DUE_DILIGENCE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_DUE_DILIGENCE_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	@Column(name="LANG")
	private String lang;

	@OneToMany(mappedBy="statoDueDiligence")
	private Set<DueDiligence> dueDiligences;

	public StatoDueDiligence() {
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

	public Set<DueDiligence> getDueDiligences() {
		return this.dueDiligences;
	}

	public void setDueDiligences(Set<DueDiligence> dueDiligences) {
		this.dueDiligences = dueDiligences;
	}

	public DueDiligence addDueDiligence(DueDiligence dueDiligence) {
		getDueDiligences().add(dueDiligence);
		dueDiligence.setStatoDueDiligence(this);

		return dueDiligence;
	}

	public DueDiligence removeDueDiligence(DueDiligence dueDiligence) {
		getDueDiligences().remove(dueDiligence);
		dueDiligence.setStatoDueDiligence(null);

		return dueDiligence;
	}

}