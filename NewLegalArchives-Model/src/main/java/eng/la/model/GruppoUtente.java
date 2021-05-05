package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the GRUPPO_UTENTE database table.
 * 
 */
@Entity
@Table(name="GRUPPO_UTENTE")
@NamedQuery(name="GruppoUtente.findAll", query="SELECT g FROM GruppoUtente g")
public class GruppoUtente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GRUPPO_UTENTE_ID_GENERATOR", sequenceName="GRUPPO_UTENTE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="GRUPPO_UTENTE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String nome;
	private String codice;
	private String lang;
	
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	//bi-directional many-to-one association to RUtenteGruppo
	@OneToMany(mappedBy="gruppoUtente")
	private Set<RUtenteGruppo> RUtenteGruppos;

	public GruppoUtente() {
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

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public Set<RUtenteGruppo> getRUtenteGruppos() {
		return this.RUtenteGruppos;
	}

	public void setRUtenteGruppos(Set<RUtenteGruppo> RUtenteGruppos) {
		this.RUtenteGruppos = RUtenteGruppos;
	}

	public RUtenteGruppo addRUtenteGruppo(RUtenteGruppo RUtenteGruppo) {
		getRUtenteGruppos().add(RUtenteGruppo);
		RUtenteGruppo.setGruppoUtente(this);

		return RUtenteGruppo;
	}

	public RUtenteGruppo removeRUtenteGruppo(RUtenteGruppo RUtenteGruppo) {
		getRUtenteGruppos().remove(RUtenteGruppo);
		RUtenteGruppo.setGruppoUtente(null);

		return RUtenteGruppo;
	}

}