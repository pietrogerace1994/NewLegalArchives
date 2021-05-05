package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.TipoScadenzaView;

public interface TipoScadenzaService {

	public List<TipoScadenzaView> leggi(Locale locale) throws Throwable;
	public TipoScadenzaView leggi(Long id) throws Throwable;
}
