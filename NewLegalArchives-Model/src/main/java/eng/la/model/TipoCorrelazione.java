package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TIPO_CORRELAZIONE database table.
 * 
 */
@Entity
@Table(name="TIPO_CORRELAZIONE")
@NamedQuery(name="TipoCorrelazione.findAll", query="SELECT t FROM TipoCorrelazione t")
public class TipoCorrelazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_CORRELAZIONE_ID_GENERATOR", sequenceName="TIPO_CORRELAZIONE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPO_CORRELAZIONE_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to ParteCorrelata
	@OneToMany(mappedBy="tipoCorrelazione")
	private Set<ParteCorrelata> parteCorrelatas;

	public TipoCorrelazione() {
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

	public Set<ParteCorrelata> getParteCorrelatas() {
		return this.parteCorrelatas;
	}

	public void setParteCorrelatas(Set<ParteCorrelata> parteCorrelatas) {
		this.parteCorrelatas = parteCorrelatas;
	}

	public ParteCorrelata addParteCorrelata(ParteCorrelata parteCorrelata) {
		getParteCorrelatas().add(parteCorrelata);
		parteCorrelata.setTipoCorrelazione(this);

		return parteCorrelata;
	}

	public ParteCorrelata removeParteCorrelata(ParteCorrelata parteCorrelata) {
		getParteCorrelatas().remove(parteCorrelata);
		parteCorrelata.setTipoCorrelazione(null);

		return parteCorrelata;
	}

}