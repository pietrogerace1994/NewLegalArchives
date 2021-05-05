package eng.la.model.custom;


public class IncaricoCron {

	private String utenteAutorizzante;
	private String utenteAutorizzato;
	private String dataAutorizzazione;
	private long idProfessionistaEsterno;
	


	public long getIdProfessionistaEsterno() {
		return idProfessionistaEsterno;
	}

	public void setIdProfessionistaEsterno(long idProfessionistaEsterno) {
		this.idProfessionistaEsterno = idProfessionistaEsterno;
	}
	
	public String getUtenteAutorizzante() {
		return utenteAutorizzante;
	}
	public void setUtenteAutorizzante(String utenteAutorizzante) {
		this.utenteAutorizzante = utenteAutorizzante;
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
