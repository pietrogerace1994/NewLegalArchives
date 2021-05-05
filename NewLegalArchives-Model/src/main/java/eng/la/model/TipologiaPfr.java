package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TIPOLOGIA_PFR database table.
 * 
 */
@Entity
@Table(name="TIPOLOGIA_PFR")
@NamedQuery(name="TipologiaPfr.findAll", query="SELECT t FROM TipologiaPfr t")
public class TipologiaPfr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPOLOGIA_PFR_ID_GENERATOR", sequenceName="TIPOLOGIA_PFR_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPOLOGIA_PFR_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private String nome;

	//bi-directional many-to-one association to Pfr
	@OneToMany(mappedBy="tipologiaPfr")
	private Set<Pfr> pfrs;

	public TipologiaPfr() {
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

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Pfr> getPfrs() {
		return this.pfrs;
	}

	public void setPfrs(Set<Pfr> pfrs) {
		this.pfrs = pfrs;
	}

	public Pfr addPfr(Pfr pfr) {
		getPfrs().add(pfr);
		pfr.setTipologiaPfr(this);

		return pfr;
	}

	public Pfr removePfr(Pfr pfr) {
		getPfrs().remove(pfr);
		pfr.setTipologiaPfr(null);

		return pfr;
	}

}