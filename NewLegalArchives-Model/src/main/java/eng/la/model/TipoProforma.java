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
 * The persistent class for the TIPOLOGIA_SCHEDA_FR database table.
 * 
 */
@Entity
@Table(name="TIPO_PROFORMA")
@NamedQuery(name="TipoProforma.findAll", query="SELECT t FROM TipoProforma t")
public class TipoProforma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_PROFORMA_ID_GENERATOR", sequenceName="TIPO_PROFORMA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPO_PROFORMA_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;
	
	//bi-directional many-to-one association to Atto
		@OneToMany(mappedBy="tipoProforma")
		private Set<Proforma> proformas;

	public TipoProforma() {
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
	
	public Set<Proforma> getProformas() {
		return this.proformas;
	}

	public void setProformas(Set<Proforma> proformas) {
		this.proformas = proformas;
	}

	public Proforma addProforma(Proforma proforma) {
		getProformas().add(proforma);
		proforma.setTipoProforma(this);

		return proforma;
	}

	public Proforma removeProforma(Proforma proforma) {
		getProformas().remove(proforma);
		proforma.setTipoProforma(null);

		return proforma;
	}

}