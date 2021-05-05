package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.PosizioneOrganizzativaView;

public interface PosizioneOrganizzativaService {
	public List<PosizioneOrganizzativaView> leggi(Locale locale) throws Throwable;
	
	public PosizioneOrganizzativaView leggi(String code, Locale locale) throws Throwable;
	
	public PosizioneOrganizzativaView leggi(Long id) throws Throwable;
}
