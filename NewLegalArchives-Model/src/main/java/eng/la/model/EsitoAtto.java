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
@Table(name="ESITO_ATTO")
@NamedQuery(name="EsitoAtto.findAll", query="SELECT t FROM EsitoAtto t")
public class EsitoAtto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ESITO_ATTO_ID_GENERATOR", sequenceName="ESITO_ATTO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="ESITO_ATTO_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;
	
	//bi-directional many-to-one association to Atto
		@OneToMany(mappedBy="esitoAtto")
		private Set<Atto> attos;

	public EsitoAtto() {
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
	
	public Set<Atto> getAttos() {
		return this.attos;
	}

	public void setAttos(Set<Atto> attos) {
		this.attos = attos;
	}

	public Atto addAtto(Atto atto) {
		getAttos().add(atto);
		atto.setEsitoAtto(this);

		return atto;
	}

	public Atto removeAtto(Atto atto) {
		getAttos().remove(atto);
		atto.setEsitoAtto(null);

		return atto;
	}

}