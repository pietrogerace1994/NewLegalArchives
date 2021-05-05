package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RisultatoOperazioneSentenze {
	public static final String OK = "OK";
	public static final String KO = "KO";
	public static final String WARN = "WARN";
	private String numeroProtocollo;
	private String stato;// OK|KO|WARN
	private String messaggio;

	public RisultatoOperazioneSentenze() {
	}

	public RisultatoOperazioneSentenze(String numeroProtocollo, String stato) {
		super();
		this.numeroProtocollo = numeroProtocollo;
		this.stato = stato;
		this.messaggio="";
	}

	public RisultatoOperazioneSentenze(String numeroProtocollo, String stato, String messaggio) {
		super();
		this.numeroProtocollo = numeroProtocollo;
		this.stato = stato;
		this.messaggio = messaggio;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
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
