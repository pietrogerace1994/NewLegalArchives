package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RisultatoOperazioneRest {
	public static final String OK = "OK";
	public static final String KO = "KO";
	public static final String WARN = "WARN";
	private String stato;// OK|KO|WARN
	private String messaggio;

	public RisultatoOperazioneRest() {
		// TODO Auto-generated constructor stub
	}
	public RisultatoOperazioneRest(String stato, String messaggio) {
		this.stato=stato;
		this.messaggio=messaggio;
	}
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	@Override
	public String toString() {
		return "RisultatoOperazioneRest [stato=" + stato + ", messaggio=" + messaggio + "]";
	}
	
	

}
