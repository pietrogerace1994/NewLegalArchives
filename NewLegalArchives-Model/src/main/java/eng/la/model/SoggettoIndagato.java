package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Formula;

import java.util.Date;


/**
 * The persistent class for the SOGGETTO_INDAGATO database table.
 * 
 */
@Entity
@Table(name="SOGGETTO_INDAGATO")
@NamedQuery(name="SoggettoIndagato.findAll", query="SELECT s FROM SoggettoIndagato s")
public class SoggettoIndagato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SOGGETTO_INDAGATO_ID_GENERATOR", sequenceName="SOGGETTO_INDAGATO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SOGGETTO_INDAGATO_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private String nome;
	
	@Formula(value="CIFRA_DECIFRA.decifra(nome)")
	private String nomeDecrypt;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to TipoSoggettoIndagato
	@ManyToOne()
	@JoinColumn(name="ID_TIPO_SOGGETTO")
	private TipoSoggettoIndagato tipoSoggettoIndagato;

	public SoggettoIndagato() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Fascicolo getFascicolo() {
		return this.fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

	public TipoSoggettoIndagato getTipoSoggettoIndagato() {
		return this.tipoSoggettoIndagato;
	}

	public void setTipoSoggettoIndagato(TipoSoggettoIndagato tipoSoggettoIndagato) {
		this.tipoSoggettoIndagato = tipoSoggettoIndagato;
	}

	public String getNomeDecrypt() {
		return nomeDecrypt;
	}

	public void setNomeDecrypt(String nomeDecrypt) {
		this.nomeDecrypt = nomeDecrypt;
	}

	
}