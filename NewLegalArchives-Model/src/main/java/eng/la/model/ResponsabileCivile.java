package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the RESPONSABILE_CIVILE database table.
 * 
 */
@Entity
@Table(name="RESPONSABILE_CIVILE")
@NamedQuery(name="ResponsabileCivile.findAll", query="SELECT r FROM ResponsabileCivile r")
public class ResponsabileCivile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RESPONSABILE_CIVILE_ID_GENERATOR", sequenceName="RESPONSABILE_CIVILE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="RESPONSABILE_CIVILE_ID_GENERATOR")
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
	@JoinColumn(name="ID_TIPO_RESPONSABILE")
	private TipoEntita tipoEntita;

	public ResponsabileCivile() {
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