package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RisultatoOperazioneLettera {
	public static final String OK = "OK";
	public static final String KO = "KO";
	public static final String WARN = "WARN";
	private String idIncarico;
	private String stato;// OK|KO|WARN
	private String messaggio;

	public RisultatoOperazioneLettera() {
	}
	
	public RisultatoOperazioneLettera(String idIncarico, String stato) {
		super();
		this.idIncarico = idIncarico;
		this.stato = stato;
		this.messaggio="";
	}

	public RisultatoOperazioneLettera(String idIncarico, String stato, String messaggio) {
		super();
		this.idIncarico = idIncarico;
		this.stato = stato;
		this.messaggio = messaggio;
	}

	public String getIdIncarico() {
		return idIncarico;
	}

	public void setIdIncarico(String idIncarico) {
		this.idIncarico = idIncarico;
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
