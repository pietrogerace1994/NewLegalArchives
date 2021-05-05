package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the STATO_RICH_AUT_GIUD database table.
 * 
 */
@Entity
@Table(name="STATO_RICH_AUT_GIUD")
@NamedQuery(name="StatoRichAutGiud.findAll", query="SELECT s FROM StatoRichAutGiud s")
public class StatoRichAutGiud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_RICH_AUT_GIUD_ID_GENERATOR", sequenceName="STATO_RICH_AUT_GIUD_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_RICH_AUT_GIUD_ID_GENERATOR")
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

	//bi-directional many-to-one association to RichAutGiud
	@OneToMany(mappedBy="statoRichAutGiud")
	private Set<RichAutGiud> richAutGiuds;

	public StatoRichAutGiud() {
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

	public Set<RichAutGiud> getRichAutGiuds() {
		return this.richAutGiuds;
	}

	public void setRichAutGiuds(Set<RichAutGiud> richAutGiuds) {
		this.richAutGiuds = richAutGiuds;
	}

	public RichAutGiud addRichAutGiud(RichAutGiud richAutGiud) {
		getRichAutGiuds().add(richAutGiud);
		richAutGiud.setStatoRichAutGiud(this);

		return richAutGiud;
	}

	public RichAutGiud removeRichAutGiud(RichAutGiud richAutGiud) {
		getRichAutGiuds().remove(richAutGiud);
		richAutGiud.setStatoRichAutGiud(null);

		return richAutGiud;
	}

}