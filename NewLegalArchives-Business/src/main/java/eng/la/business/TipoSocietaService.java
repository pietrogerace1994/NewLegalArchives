package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.NazioneView;
import eng.la.model.view.TipoSocietaView;

public interface TipoSocietaService {

	public List<TipoSocietaView> leggi(Locale locale) throws Throwable;
	
	public TipoSocietaView leggi(Long id) throws Throwable;

}
