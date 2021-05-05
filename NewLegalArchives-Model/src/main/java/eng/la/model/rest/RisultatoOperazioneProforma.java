package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RisultatoOperazioneProforma {
	public static final String OK = "OK";
	public static final String KO = "KO";
	public static final String WARN = "WARN";
	private String idProforma;
	private String stato;// OK|KO|WARN
	private String messaggio;

	public RisultatoOperazioneProforma() {
	}
	
	public RisultatoOperazioneProforma(String idProforma, String stato) {
		super();
		this.idProforma = idProforma;
		this.stato = stato;
		this.messaggio="";
	}

	public RisultatoOperazioneProforma(String idProforma, String stato, String messaggio) {
		super();
		this.idProforma = idProforma;
		this.stato = stato;
		this.messaggio = messaggio;
	}

	public String getIdProforma() {
		return idProforma;
	}

	public void setIdProforma(String idProforma) {
		this.idProforma = idProforma;
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
}
