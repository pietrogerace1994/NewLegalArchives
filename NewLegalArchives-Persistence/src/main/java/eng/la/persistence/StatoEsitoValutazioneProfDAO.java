package eng.la.persistence;

import java.util.List;

import eng.la.model.StatoEsitoValutazioneProf;

public interface StatoEsitoValutazioneProfDAO {

	public List<StatoEsitoValutazioneProf> leggi(String lingua) throws Throwable;
	
	public StatoEsitoValutazioneProf leggi(String codice, String lingua) throws Throwable;
	
}
