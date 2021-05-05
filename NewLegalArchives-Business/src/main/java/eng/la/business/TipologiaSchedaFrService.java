package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.TipologiaSchedaFrView;

public interface TipologiaSchedaFrService {
	public List<TipologiaSchedaFrView> leggi(Locale locale) throws Throwable;
	
	public TipologiaSchedaFrView leggi(String code, Locale locale) throws Throwable;
}
