package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.StatoEsitoValutazioneProfView;

public interface StatoEsitoValutazioneProfService {

	public List<StatoEsitoValutazioneProfView> leggi(Locale locale) throws Throwable;
	
	public StatoEsitoValutazioneProfView leggi(String codice, Locale locale) throws Throwable;
	
}
