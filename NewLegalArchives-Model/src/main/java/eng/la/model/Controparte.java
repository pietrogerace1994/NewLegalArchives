package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the CONTROPARTE database table.
 * 
 */
@Entity
@NamedQuery(name="Controparte.findAll", query="SELECT c FROM Controparte c")
public class Controparte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTROPARTE_ID_GENERATOR", sequenceName="CONTROPARTE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CONTROPARTE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private String nome;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to TipoEntita
	@ManyToOne()
	@JoinColumn(name="ID_TIPO_CONTROPARTE")
	private TipoEntita tipoEntita;

	public Controparte() {
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

	public TipoEntita getTipoEntita() {
		return this.tipoEntita;
	}

	public void setTipoEntita(TipoEntita tipoEntita) {
		this.tipoEntita = tipoEntita;
	}

}