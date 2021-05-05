package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.TerzoChiamatoCausaView;

public interface TerzoChiamatoCausaService {

	public List<TerzoChiamatoCausaView> leggiPerAutocomplete(Locale locale, String term)throws Throwable;

	public TerzoChiamatoCausaView leggi(long id)throws Throwable;

}
