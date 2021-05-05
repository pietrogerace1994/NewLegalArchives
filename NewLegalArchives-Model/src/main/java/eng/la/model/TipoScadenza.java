package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TIPO_SCADENZA database table.
 * 
 */
@Entity
@Table(name="TIPO_SCADENZA")
@NamedQuery(name="TipoScadenza.findAll", query="SELECT t FROM TipoScadenza t")
public class TipoScadenza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_SCADENZA_ID_GENERATOR", sequenceName="TIPO_SCADENZA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPO_SCADENZA_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private String nome;

	//bi-directional many-to-one association to Scadenza
	@OneToMany(mappedBy="tipoScadenza")
	private Set<Scadenza> scadenzas;

	public TipoScadenza() {
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

	public Set<Scadenza> getScadenzas() {
		return this.scadenzas;
	}

	public void setScadenzas(Set<Scadenza> scadenzas) {
		this.scadenzas = scadenzas;
	}

	public Scadenza addScadenza(Scadenza scadenza) {
		getScadenzas().add(scadenza);
		scadenza.setTipoScadenza(this);

		return scadenza;
	}

	public Scadenza removeScadenza(Scadenza scadenza) {
		getScadenzas().remove(scadenza);
		scadenza.setTipoScadenza(null);

		return scadenza;
	}

}