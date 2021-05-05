package eng.la.model.rest;

public class ProcureStandardRest {
	
	private String nome;
	private String societa;
	private String dataModifica;
	private String categoria;
	private String autoreUltimaModifica;
	private String note;
	private String primoLivello;
	private String secondoLivello;
	private String posizione;
	private String dataCreazione;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSocieta() {
		return societa;
	}
	public void setSocieta(String societa) {
		this.societa = societa;
	}
	public String getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(String dataModifica) {
		this.dataModifica = dataModifica;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getAutoreUltimaModifica() {
		return autoreUltimaModifica;
	}
	public void setAutoreUltimaModifica(String autoreUltimaModifica) {
		this.autoreUltimaModifica = autoreUltimaModifica;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String getPosizione() {
		return posizione;
	}
	public void setPosizione(String posizione) {
		this.posizione = posizione;
	}
	public String getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	@Override
	public String toString() {
		return "ProcureStandardRest [nome=" + nome + ", societa=" + societa + ", dataModifica=" + dataModifica
				+ ", categoria=" + categoria + ", autoreUltimaModifica=" + autoreUltimaModifica + ", note=" + note
				+ ", primoLivello=" + primoLivello + ", secondoLivello=" + secondoLivello + ", posizione=" + posizione
				+ ", dataCreazione=" + dataCreazione + "]";
	}
	
	
	
		


}
