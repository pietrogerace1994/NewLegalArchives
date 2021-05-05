package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.RischioSoccombenzaView;

public interface RischioSoccombenzaService {
	public List<RischioSoccombenzaView> leggi(Locale locale) throws Throwable;
	
	public RischioSoccombenzaView leggi(String code, Locale locale) throws Throwable;
}
