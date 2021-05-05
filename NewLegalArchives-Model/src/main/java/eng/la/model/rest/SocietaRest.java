package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SocietaRest {
	
	private long id;
	private String nome;
	private String ragioneSociale;
	private List<String> emailAmministrazione;
	private String indirizzo;
	private String cap;
	private String citta;
	private Long idTipoSocieta;
	private Long idNazione;
	private String codGruppoLingua;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public Long getIdTipoSocieta() {
		return idTipoSocieta;
	}

	public void setIdTipoSocieta(Long idTipoSocieta) {
		this.idTipoSocieta = idTipoSocieta;
	}

	public Long getIdNazione() {
		return idNazione;
	}

	public void setIdNazione(Long idNazione) {
		this.idNazione = idNazione;
	}

	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public List<String> getEmailAmministrazione() {
		return emailAmministrazione;
	}

	public void setEmailAmministrazione(List<String> emailAmministrazione) {
		this.emailAmministrazione = emailAmministrazione;
	}

}
