package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the CATEGORIA_CONTEST database table.
 * 
 */
@Entity
@Table(name="CATEGORIA_CONTEST")
@NamedQuery(name="CategoriaContest.findAll", query="SELECT c FROM CategoriaContest c")
public class CategoriaContest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CATEGORIA_CONTEST_ID_GENERATOR", sequenceName="CATEGORIA_CONTEST_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CATEGORIA_CONTEST_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to ProfessionistaEsterno
	@OneToMany(mappedBy="categoriaContest")
	private Set<ProfessionistaEsterno> professionistaEsternos;

	public CategoriaContest() {
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

	public Set<ProfessionistaEsterno> getProfessionistaEsternos() {
		return this.professionistaEsternos;
	}

	public void setProfessionistaEsternos(Set<ProfessionistaEsterno> professionistaEsternos) {
		this.professionistaEsternos = professionistaEsternos;
	}

	public ProfessionistaEsterno addProfessionistaEsterno(ProfessionistaEsterno professionistaEsterno) {
		getProfessionistaEsternos().add(professionistaEsterno);
		professionistaEsterno.setCategoriaContest(this);

		return professionistaEsterno;
	}

	public ProfessionistaEsterno removeProfessionistaEsterno(ProfessionistaEsterno professionistaEsterno) {
		getProfessionistaEsternos().remove(professionistaEsterno);
		professionistaEsterno.setCategoriaContest(null);

		return professionistaEsterno;
	}

}