package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the SPECIALIZZAZIONE database table.
 * 
 */
@Entity
@NamedQuery(name="Specializzazione.findAll", query="SELECT s FROM Specializzazione s")
public class Specializzazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPECIALIZZAZIONE_ID_GENERATOR", sequenceName="SPECIALIZZAZIONE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SPECIALIZZAZIONE_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to RProfEstSpec
	@OneToMany(mappedBy="specializzazione")
	private Set<RProfEstSpec> RProfEstSpecs;

	public Specializzazione() {
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

	public Set<RProfEstSpec> getRProfEstSpecs() {
		return this.RProfEstSpecs;
	}

	public void setRProfEstSpecs(Set<RProfEstSpec> RProfEstSpecs) {
		this.RProfEstSpecs = RProfEstSpecs;
	}

	public RProfEstSpec addRProfEstSpec(RProfEstSpec RProfEstSpec) {
		getRProfEstSpecs().add(RProfEstSpec);
		RProfEstSpec.setSpecializzazione(this);

		return RProfEstSpec;
	}

	public RProfEstSpec removeRProfEstSpec(RProfEstSpec RProfEstSpec) {
		getRProfEstSpecs().remove(RProfEstSpec);
		RProfEstSpec.setSpecializzazione(null);

		return RProfEstSpec;
	}

}