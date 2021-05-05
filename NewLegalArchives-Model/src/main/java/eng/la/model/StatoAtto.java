package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;

import eng.la.model.audit.AuditedObjectName;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the STATO_ATTO database table.
 * 
 */
@Entity
@Table(name="STATO_ATTO")
@NamedQuery(name="StatoAtto.findAll", query="SELECT s FROM StatoAtto s")
public class StatoAtto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_ATTO_ID_GENERATOR", sequenceName="STATO_ATTO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_ATTO_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to Atto
	@OneToMany(mappedBy="statoAtto")
	private Set<Atto> attos;

	public StatoAtto() {
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

	public Set<Atto> getAttos() {
		return this.attos;
	}

	public void setAttos(Set<Atto> attos) {
		this.attos = attos;
	}

	public Atto addAtto(Atto atto) {
		getAttos().add(atto);
		atto.setStatoAtto(this);

		return atto;
	}

	public Atto removeAtto(Atto atto) {
		getAttos().remove(atto);
		atto.setStatoAtto(null);

		return atto;
	}

}