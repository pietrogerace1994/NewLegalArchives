package eng.la.model.view;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class BaseView implements Serializable {
	private static final long serialVersionUID = 1L;

	private Locale locale;
	private List<NazioneView> listaNazioni;
	private List<SocietaView> listaSocieta;
	private List<SpecializzazioneView> listaSpecializzazioni;
	private List<RubricaView> listaRubrica;
	private List<EmailView> listaArticoli;
	private boolean isPenale;
	private String op;
	
	
	public Locale getLocale() {
		if( locale != null )
			return locale;
		else
			return new Locale("it_IT", "IT");
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	
	
	public List<EmailView> getListaArticoli() {
		return listaArticoli;
	}

	public void setListaArticoli(List<EmailView> listaArticoli) {
		this.listaArticoli = listaArticoli;
	}

	public List<RubricaView> getListaRubrica() {
		return listaRubrica;
	}

	public void setListaRubrica(List<RubricaView> listaRubrica) {
		this.listaRubrica = listaRubrica;
	}

	public List<NazioneView> getListaNazioni() {
		return listaNazioni;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public void setListaNazioni(List<NazioneView> listaNazioni) {
		this.listaNazioni = listaNazioni;
	}

	public List<SocietaView> getListaSocieta() {
		return listaSocieta;
	}

	public void setListaSocieta(List<SocietaView> listaSocieta) {
		this.listaSocieta = listaSocieta;
	}

	public List<SpecializzazioneView> getListaSpecializzazioni() {
		return listaSpecializzazioni;
	}

	public void setListaSpecializzazioni(List<SpecializzazioneView> listaSpecializzazioni) {
		this.listaSpecializzazioni = listaSpecializzazioni;
	}

	public boolean isPenale() {
		return isPenale;
	}

	public void setPenale(boolean isPenale) {
		this.isPenale = isPenale;
	}

}
