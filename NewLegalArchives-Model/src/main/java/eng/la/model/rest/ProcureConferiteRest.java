package eng.la.model.rest;

import java.util.List;

import eng.la.model.custom.NomeFilePath;

public class ProcureConferiteRest {

	private String repertorioProcure;
	private String tipologia;
	private String inizio;
	private String revoca;
	private String societa;
	private String primoLivello;
	private String secondoLivello;
	private String posizioneOrganizzativa;
	private String legaleInterno;
	private String tipologiaRipulita;
	private List<NomeFilePath> nomeFile;
	
	
	public String getRepertorioProcure() {
		return repertorioProcure;
	}
	public void setRepertorioProcure(String repertorioProcure) {
		this.repertorioProcure = repertorioProcure;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getInizio() {
		return inizio;
	}
	public void setInizio(String inizio) {
		this.inizio = inizio;
	}
	public String getRevoca() {
		return revoca;
	}
	public void setRevoca(String revoca) {
		this.revoca = revoca;
	}
	public String getSocieta() {
		return societa;
	}
	public void setSocieta(String societa) {
		this.societa = societa;
	}
	public String getPrimoLivello() {
		return primoLivello;
	}
	public void setPrimoLivello(String primoLivello) {
		this.primoLivello = primoLivello;
	}
	public String getSecondoLivello() {
		return secondoLivello;
	}
	public void setSecondoLivello(String secondoLivello) {
		this.secondoLivello = secondoLivello;
	}
	public String getPosizioneOrganizzativa() {
		return posizioneOrganizzativa;
	}
	public void setPosizioneOrganizzativa(String posizioneOrganizzativa) {
		this.posizioneOrganizzativa = posizioneOrganizzativa;
	}
	public String getLegaleInterno() {
		return legaleInterno;
	}
	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}
	public String getTipologiaRipulita() {
		return tipologiaRipulita;
	}
	public void setTipologiaRipulita(String tipologiaRipulita) {
		this.tipologiaRipulita = tipologiaRipulita;
	}
	public List<NomeFilePath> getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(List<NomeFilePath> nomeFile) {
		this.nomeFile = nomeFile;
	}
	@Override
	public String toString() {
		return "ProcureConferiteRest [repertorioProcure=" + repertorioProcure + ", tipologia=" + tipologia + ", inizio="
				+ inizio + ", revoca=" + revoca + ", societa=" + societa + ", primoLivello=" + primoLivello
				+ ", secondoLivello=" + secondoLivello + ", posizioneOrganizzativa=" + posizioneOrganizzativa
				+ ", legaleInterno=" + legaleInterno + ", tipologiaRipulita=" + tipologiaRipulita + ", nomeFile="
				+ nomeFile + "]";
	}

	
	
	
	
}
