package it.eng.la.ws.dto;

import java.io.Serializable;

public class Risultato implements Serializable {

	private static final long serialVersionUID = 6599993387752115083L;

	private String esito; //valori OK/KO
	private String messaggio; //null se Esito “OK” - valorizzato con messaggio di errore se Esito “KO”

	public Risultato() {
		super();
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String value) {
		this.esito = value;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String value) {
		this.messaggio = value;
	}

}
