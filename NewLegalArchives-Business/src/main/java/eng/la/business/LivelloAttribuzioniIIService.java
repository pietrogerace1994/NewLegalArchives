package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.LivelloAttribuzioniIIView;

public interface LivelloAttribuzioniIIService {
	
	public List<LivelloAttribuzioniIIView> leggi(Locale locale) throws Throwable;
	
	public LivelloAttribuzioniIIView leggi(String code, Locale locale) throws Throwable;
	
	public LivelloAttribuzioniIIView findLivelloAttribuzioniIIByPk(long id) throws Throwable;
}
