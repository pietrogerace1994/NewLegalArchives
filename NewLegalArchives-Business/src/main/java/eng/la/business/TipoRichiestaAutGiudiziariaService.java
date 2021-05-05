package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.TipologiaRichiestaView;

public interface TipoRichiestaAutGiudiziariaService {

	public List<TipologiaRichiestaView> leggi(Locale locale) throws Throwable;
}
