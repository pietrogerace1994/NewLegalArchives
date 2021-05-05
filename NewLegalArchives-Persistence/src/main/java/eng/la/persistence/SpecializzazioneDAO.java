package eng.la.persistence;

import java.util.List;

import eng.la.model.Specializzazione;

public interface SpecializzazioneDAO {

	public List<Specializzazione> leggi() throws Throwable;

	public Specializzazione leggi(long id) throws Throwable;

	public List<Specializzazione> leggi(String lingua) throws Throwable;
	
	public Specializzazione leggi(String codice, String lingua) throws Throwable;
	
	public List<Specializzazione> leggibyCodice(String codice) throws Throwable;
	
	public List<String> getCodGruppoLinguaList() throws Throwable;
	
	public boolean controlla(String descrizione) throws Throwable;
	
	public void cancella(long id) throws Throwable;
	
	public Specializzazione inserisci(Specializzazione vo) throws Throwable;
	
	public void modifica(Specializzazione vo) throws Throwable;

	public List<Specializzazione> leggiSpecializzazioniAssociatiAProfessionista(long id) throws Throwable;
	
}
