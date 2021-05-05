package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.SpecializzazioneView;

public interface SpecializzazioneService {

	public List<SpecializzazioneView> leggi() throws Throwable;
	
	public SpecializzazioneView leggi(long id) throws Throwable;

	public List<SpecializzazioneView> leggi(Locale locale) throws Throwable;
	
	public SpecializzazioneView leggi(String codice, Locale locale) throws Throwable;
	
	public List<SpecializzazioneView> leggibyCodice(String codice) throws Throwable;
	
	public void inserisci(SpecializzazioneView nazione) throws Throwable;
	
	public boolean controlla(SpecializzazioneView nazione) throws Throwable;
	
	public void cancella(long id) throws Throwable;
	
	public void cancella(SpecializzazioneView nazione) throws Throwable;
	
	public void modifica(SpecializzazioneView nazione) throws Throwable;
	
}
