package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.StatoRichAutGiud;
import eng.la.model.view.StatoRichAutGiudView;

public interface StatoRichiestaAutGiudiziariaService {

	public List<StatoRichAutGiudView> leggi(Locale locale) throws Throwable;
	
	public StatoRichAutGiud readStatoRichAutGiudByFilter(Locale locale, String codGruppoLingua) throws Throwable;
	
}
