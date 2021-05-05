package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TIPOLOGIA_RICHIESTA database table.
 * 
 */
@Entity
@Table(name="TIPOLOGIA_RICHIESTA")
@NamedQuery(name="TipologiaRichiesta.findAll", query="SELECT t FROM TipologiaRichiesta t")
public class TipologiaRichiesta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPOLOGIA_RICHIESTA_ID_GENERATOR", sequenceName="TIPOLOGIA_RICHIESTA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPOLOGIA_RICHIESTA_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="LANG")
	private String lang;

	@Column(name="NOME")
	private String nome;

	//bi-directional many-to-one association to RichAutGiud
	@OneToMany(mappedBy="tipologiaRichiesta")
	private Set<RichAutGiud> richAutGiuds;

	public TipologiaRichiesta() {
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

	public Set<RichAutGiud> getRichAutGiuds() {
		return this.richAutGiuds;
	}

	public void setRichAutGiuds(Set<RichAutGiud> richAutGiuds) {
		this.richAutGiuds = richAutGiuds;
	}

	public RichAutGiud addRichAutGiud(RichAutGiud richAutGiud) {
		getRichAutGiuds().add(richAutGiud);
		richAutGiud.setTipologiaRichiesta(this);

		return richAutGiud;
	}

	public RichAutGiud removeRichAutGiud(RichAutGiud richAutGiud) {
		getRichAutGiuds().remove(richAutGiud);
		richAutGiud.setTipologiaRichiesta(null);

		return richAutGiud;
	}

}