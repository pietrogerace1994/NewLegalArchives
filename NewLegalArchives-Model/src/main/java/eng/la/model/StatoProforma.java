package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;

import eng.la.model.audit.AuditedObjectName;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the STATO_PROFORMA database table.
 * 
 */
@Entity
@Table(name="STATO_PROFORMA")
@NamedQuery(name="StatoProforma.findAll", query="SELECT s FROM StatoProforma s")
public class StatoProforma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_PROFORMA_ID_GENERATOR", sequenceName="STATO_PROFORMA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_PROFORMA_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to Proforma
	@OneToMany(mappedBy="statoProforma")
	private Set<Proforma> proformas;

	public StatoProforma() {
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

	public Set<Proforma> getProformas() {
		return this.proformas;
	}

	public void setProformas(Set<Proforma> proformas) {
		this.proformas = proformas;
	}

	public Proforma addProforma(Proforma proforma) {
		getProformas().add(proforma);
		proforma.setStatoProforma(this);

		return proforma;
	}

	public Proforma removeProforma(Proforma proforma) {
		getProformas().remove(proforma);
		proforma.setStatoProforma(null);

		return proforma;
	}

}