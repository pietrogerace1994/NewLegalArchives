package eng.la.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "AUTORIZZAZIONE")
public class Autorizzazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AUTORIZZAZIONE_ID_GENERATOR", sequenceName = "AUTORIZZAZIONE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "AUTORIZZAZIONE_ID_GENERATOR")
	private long id;

	@ManyToOne()
	@JoinColumn(name = "MATRICOLA_UTIL")
	private Utente utente;

	@ManyToOne()
	@JoinColumn(name = "UTENTE_FOR_RESP")
	private Utente utenteForResp;

	@Column(name = "NOME_CLASSE")
	private String nomeClasse;

	@ManyToOne()
	@JoinColumn(name = "ID_TIPO_AUTORIZZAZIONE")
	private TipoAutorizzazione tipoAutorizzazione;

	@ManyToOne()
	@JoinColumn(name = "ID_GRUPPO_UTENTE")
	private GruppoUtente gruppoUtente;

	@Column(name = "ID_ENTITA")
	private long idEntita;

	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Utente getUtenteForResp() {
		return utenteForResp;
	}

	public void setUtenteForResp(Utente utenteForResp) {
		this.utenteForResp = utenteForResp;
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	public TipoAutorizzazione getTipoAutorizzazione() {
		return tipoAutorizzazione;
	}

	public void setTipoAutorizzazione(TipoAutorizzazione tipoAutorizzazione) {
		this.tipoAutorizzazione = tipoAutorizzazione;
	}

	public GruppoUtente getGruppoUtente() {
		return gruppoUtente;
	}

	public void setGruppoUtente(GruppoUtente gruppoUtente) {
		this.gruppoUtente = gruppoUtente;
	}

	public void setIdEntita(long idEntita) {
		this.idEntita = idEntita;
	}

	public long getIdEntita() {
		return idEntita;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}
