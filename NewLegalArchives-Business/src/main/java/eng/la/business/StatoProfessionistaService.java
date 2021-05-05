package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.StatoProfessionistaView;

public interface StatoProfessionistaService {

	public List<StatoProfessionistaView> leggi(Locale locale) throws Throwable;
	
	public StatoProfessionistaView leggi(String codice, Locale locale) throws Throwable;
	
}
