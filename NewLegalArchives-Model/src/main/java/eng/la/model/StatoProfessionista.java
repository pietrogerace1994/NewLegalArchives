package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the STATO_PROFESSIONISTA database table.
 * 
 */
@Entity
@Table(name="STATO_PROFESSIONISTA")
@NamedQuery(name="StatoProfessionista.findAll", query="SELECT s FROM StatoProfessionista s")
public class StatoProfessionista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_PROFESSIONISTA_ID_GENERATOR", sequenceName="STATO_PROFESSIONISTA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_PROFESSIONISTA_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to ProfessionistaEsterno
	@OneToMany(mappedBy="statoProfessionista")
	private Set<ProfessionistaEsterno> professionistaEsternos;

	public StatoProfessionista() {
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
		professionistaEsterno.setStatoProfessionista(this);

		return professionistaEsterno;
	}

	public ProfessionistaEsterno removeProfessionistaEsterno(ProfessionistaEsterno professionistaEsterno) {
		getProfessionistaEsternos().remove(professionistaEsterno);
		professionistaEsterno.setStatoProfessionista(null);

		return professionistaEsterno;
	}

}