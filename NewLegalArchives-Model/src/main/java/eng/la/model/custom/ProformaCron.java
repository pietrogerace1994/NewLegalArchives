package eng.la.model.custom;


public class ProformaCron {

	private boolean autorizzato;
	private String utenteAutorizzato;
	private String dataAutorizzazione;
	private long idProforma;
	private String stato;
	



	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public boolean isAutorizzato() {
		return autorizzato;
	}
	public void setAutorizzato(boolean autorizzato) {
		this.autorizzato = autorizzato;
	}
	public long getIdProforma() {
		return idProforma;
	}
	public void setIdProforma(long idProforma) {
		this.idProforma = idProforma;
	}
	public String getUtenteAutorizzato() {
		return utenteAutorizzato;
	}
	public void setUtenteAutorizzato(String utenteAutorizzato) {
		this.utenteAutorizzato = utenteAutorizzato;
	}
	public String getDataAutorizzazione() {
		return dataAutorizzazione;
	}
	public void setDataAutorizzazione(String dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}
	

	

}
