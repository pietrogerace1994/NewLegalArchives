package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the LIVELLO_ATTRIBUZIONI_II database table.
 * 
 */
@Entity
@Table(name="LIVELLO_ATTRIBUZIONI_II")
@NamedQuery(name="SecondoLivelloAttribuzioni.findAll", query="SELECT t FROM SecondoLivelloAttribuzioni t")
public class SecondoLivelloAttribuzioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LIVELLO_ATTRIBUZIONI_II_ID_GENERATOR", sequenceName="LIVELLO_ATTRIBUZIONI_II_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="LIVELLO_ATTRIBUZIONI_II_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to RepertorioStandard
	@OneToMany(mappedBy="secondoLivelloAttribuzioni")
	private Set<RepertorioStandard> repertorioStandards;

	public SecondoLivelloAttribuzioni() {
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

	public Set<RepertorioStandard> getRepertorioStandards() {
		return this.repertorioStandards;
	}

	public void setRepertorioStandards(Set<RepertorioStandard> repertorioStandards) {
		this.repertorioStandards = repertorioStandards;
	}

	public RepertorioStandard addRepertorioStandard(RepertorioStandard repertorioStandard) {
		getRepertorioStandards().add(repertorioStandard);
		repertorioStandard.setSecondoLivelloAttribuzioni(this);

		return repertorioStandard;
	}

	public RepertorioStandard removeRepertorioStandard(RepertorioStandard repertorioStandard) {
		getRepertorioStandards().remove(repertorioStandard);
		repertorioStandard.setSecondoLivelloAttribuzioni(null);

		return repertorioStandard;
	}

}