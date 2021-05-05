package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.LivelloAttribuzioniIView;

public interface LivelloAttribuzioniIService {
	
	public List<LivelloAttribuzioniIView> leggi(Locale locale) throws Throwable;
	
	public LivelloAttribuzioniIView leggi(String code, Locale locale) throws Throwable;
	
	public LivelloAttribuzioniIView findLivelloAttribuzioniIByPk(long id) throws Throwable;
	
}
