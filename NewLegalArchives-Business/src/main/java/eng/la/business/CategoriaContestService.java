package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.CategoriaContestView;

public interface CategoriaContestService {

	public List<CategoriaContestView> leggi(Locale locale) throws Throwable;
	
	public CategoriaContestView leggi(String codice, Locale locale) throws Throwable;
	
}
