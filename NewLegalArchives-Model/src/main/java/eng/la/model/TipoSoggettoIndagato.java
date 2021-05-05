package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TIPO_SOGGETTO_INDAGATO database table.
 * 
 */
@Entity
@Table(name="TIPO_SOGGETTO_INDAGATO")
@NamedQuery(name="TipoSoggettoIndagato.findAll", query="SELECT t FROM TipoSoggettoIndagato t")
public class TipoSoggettoIndagato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_SOGGETTO_INDAGATO_ID_GENERATOR", sequenceName="TIPO_SOGGETTO_INDAGATO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPO_SOGGETTO_INDAGATO_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private String nome;

	//bi-directional many-to-one association to SoggettoIndagato
	@OneToMany(mappedBy="tipoSoggettoIndagato")
	private Set<SoggettoIndagato> soggettoIndagatos;

	public TipoSoggettoIndagato() {
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

	public Set<SoggettoIndagato> getSoggettoIndagatos() {
		return this.soggettoIndagatos;
	}

	public void setSoggettoIndagatos(Set<SoggettoIndagato> soggettoIndagatos) {
		this.soggettoIndagatos = soggettoIndagatos;
	}

	public SoggettoIndagato addSoggettoIndagato(SoggettoIndagato soggettoIndagato) {
		getSoggettoIndagatos().add(soggettoIndagato);
		soggettoIndagato.setTipoSoggettoIndagato(this);

		return soggettoIndagato;
	}

	public SoggettoIndagato removeSoggettoIndagato(SoggettoIndagato soggettoIndagato) {
		getSoggettoIndagatos().remove(soggettoIndagato);
		soggettoIndagato.setTipoSoggettoIndagato(null);

		return soggettoIndagato;
	}

}