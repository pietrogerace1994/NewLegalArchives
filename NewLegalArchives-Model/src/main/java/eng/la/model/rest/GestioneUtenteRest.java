package eng.la.model.rest;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GestioneUtenteRest {

	private String userid;
	private String nominativo;
	private String assente;
	private String azione;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getNominativo() {
		return nominativo;
	}
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}
	public String getAssente() {
		return assente;
	}
	public void setAssente(String assente) {
		this.assente = assente;
	}
	
	public void setAzione(String azione) {
		this.azione = azione;
	}
	
	public String getAzione() {
		return azione;
	}
	
	
}
