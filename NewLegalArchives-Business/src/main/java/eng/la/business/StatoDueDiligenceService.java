package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.StatoDueDiligence;
import eng.la.model.view.StatoDueDiligenceView;

public interface StatoDueDiligenceService {

	public List<StatoDueDiligenceView> leggi(Locale locale) throws Throwable;
	
	public StatoDueDiligence readStatoDueDiligenceByFilter(Locale locale, String codGruppoLingua) throws Throwable;
	
}
