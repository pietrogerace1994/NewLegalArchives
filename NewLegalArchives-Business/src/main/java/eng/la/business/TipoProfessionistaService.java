package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.TipoProfessionistaView;

public interface TipoProfessionistaService {

	public List<TipoProfessionistaView> leggi(Locale locale) throws Throwable;
	
	public TipoProfessionistaView leggi(long id) throws Throwable;
	
	public TipoProfessionistaView leggi(String codice, Locale locale) throws Throwable;
	
}
