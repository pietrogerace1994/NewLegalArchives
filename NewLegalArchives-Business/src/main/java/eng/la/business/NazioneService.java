package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.NazioneView;

public interface NazioneService {

	public List<NazioneView> leggi() throws Throwable;
	
	public NazioneView leggi(long id) throws Throwable;

	public List<NazioneView> leggi(Locale locale, boolean tutte) throws Throwable;
	
	public List<NazioneView> leggiPartiCorrelate(Locale locale, boolean partiCorrelate) throws Throwable;
	
	public NazioneView leggi(String codice, Locale locale, boolean tutte) throws Throwable;
	
	public List<NazioneView> leggi(Locale locale) throws Throwable;
	
	public List<NazioneView> leggibyCodice(String codice) throws Throwable;
	
	public void inserisci(NazioneView nazione) throws Throwable;
	
	public boolean controlla(NazioneView nazione) throws Throwable;
	
	public void cancella(long id) throws Throwable;
	
	public void cancella(NazioneView nazione) throws Throwable;
	
	public void modifica(NazioneView nazione) throws Throwable;
	
}
