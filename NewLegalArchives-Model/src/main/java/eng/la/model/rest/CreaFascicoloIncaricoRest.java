package eng.la.model.rest;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class CreaFascicoloIncaricoRest implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String notaio;
	
	private List<String> societa;
	
	private List<String> procure;


	public List<String> getProcure() {
		return procure;
	}

	public void setProcure(List<String> procure) {
		this.procure = procure;
	}

	public List<String> getSocieta() {
		return societa;
	}

	public void setSocieta(List<String> societa) {
		this.societa = societa;
	}

	public String getNotaio() {
		return notaio;
	}

	public void setNotaio(String notaio) {
		this.notaio = notaio;
	}

	public Locale getLocale() {
		return null;
	}
}
