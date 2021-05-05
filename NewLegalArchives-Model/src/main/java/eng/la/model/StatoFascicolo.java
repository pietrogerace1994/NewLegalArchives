package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;

import eng.la.model.audit.AuditedObjectName;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the STATO_FASCICOLO database table.
 * 
 */
@Entity
@Table(name="STATO_FASCICOLO")
@NamedQuery(name="StatoFascicolo.findAll", query="SELECT s FROM StatoFascicolo s")
public class StatoFascicolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_FASCICOLO_ID_GENERATOR", sequenceName="STATO_FASCICOLO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_FASCICOLO_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to Fascicolo
	@OneToMany(mappedBy="statoFascicolo")
	private Set<Fascicolo> fascicolos;

	public StatoFascicolo() {
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

	public Set<Fascicolo> getFascicolos() {
		return this.fascicolos;
	}

	public void setFascicolos(Set<Fascicolo> fascicolos) {
		this.fascicolos = fascicolos;
	}

	public Fascicolo addFascicolo(Fascicolo fascicolo) {
		getFascicolos().add(fascicolo);
		fascicolo.setStatoFascicolo(this);

		return fascicolo;
	}

	public Fascicolo removeFascicolo(Fascicolo fascicolo) {
		getFascicolos().remove(fascicolo);
		fascicolo.setStatoFascicolo(null);

		return fascicolo;
	}

}