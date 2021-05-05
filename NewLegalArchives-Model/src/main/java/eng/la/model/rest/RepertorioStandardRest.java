package eng.la.model.rest;

import java.util.Date;

public class RepertorioStandardRest {

	private long id;
	private String nome;
	private String nota;
	private Date dataCreazione;
	private Date dataModifica;
	private String lingua;
	private String codGruppoLingua;
	private String utente;
	private String societa;
	private String primoLivelloAttribuzioni;
	private String secondoLivelloAttribuzioni;
	private String posizioneOrganizzativa;
	
	private String azioni;

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

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getSocieta() {
		return societa;
	}

	public void setSocieta(String societa) {
		this.societa = societa;
	}

	public String getPrimoLivelloAttribuzioni() {
		return primoLivelloAttribuzioni;
	}

	public void setPrimoLivelloAttribuzioni(String primoLivelloAttribuzioni) {
		this.primoLivelloAttribuzioni = primoLivelloAttribuzioni;
	}

	public String getSecondoLivelloAttribuzioni() {
		return secondoLivelloAttribuzioni;
	}

	public void setSecondoLivelloAttribuzioni(String secondoLivelloAttribuzioni) {
		this.secondoLivelloAttribuzioni = secondoLivelloAttribuzioni;
	}

	public String getPosizioneOrganizzativa() {
		return posizioneOrganizzativa;
	}

	public void setPosizioneOrganizzativa(String posizioneOrganizzativa) {
		this.posizioneOrganizzativa = posizioneOrganizzativa;
	}

	public String getAzioni() {
		return azioni;
	}

	public void setAzioni(String azioni) {
		this.azioni = azioni;
	}
	
	
	
}
