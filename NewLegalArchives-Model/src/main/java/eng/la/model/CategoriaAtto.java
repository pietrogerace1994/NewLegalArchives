package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the CATEGORIA_ATTO database table.
 * 
 */
@Entity
@Table(name="CATEGORIA_ATTO")
@NamedQuery(name="CategoriaAtto.findAll", query="SELECT c FROM CategoriaAtto c")
public class CategoriaAtto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CATEGORIA_ATTO_ID_GENERATOR", sequenceName="CATEGORIA_ATTO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CATEGORIA_ATTO_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private String nome;

	//bi-directional many-to-one association to Atto
	@OneToMany(mappedBy="categoriaAtto")
	private Set<Atto> attos;

	public CategoriaAtto() {
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

	public Set<Atto> getAttos() {
		return this.attos;
	}

	public void setAttos(Set<Atto> attos) {
		this.attos = attos;
	}

	public Atto addAtto(Atto atto) {
		getAttos().add(atto);
		atto.setCategoriaAtto(this);

		return atto;
	}

	public Atto removeAtto(Atto atto) {
		getAttos().remove(atto);
		atto.setCategoriaAtto(null);

		return atto;
	}

}