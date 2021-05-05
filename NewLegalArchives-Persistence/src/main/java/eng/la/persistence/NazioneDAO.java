package eng.la.persistence;

import java.util.List;

import eng.la.model.Nazione;

public interface NazioneDAO {

	public List<Nazione> leggi() throws Throwable;

	public Nazione leggi(long id) throws Throwable;

	public List<Nazione> leggi(String lingua, boolean tutte) throws Throwable;
	
	public List<Nazione> leggiPartiCorrelate(String lingua, boolean partiCorrelate) throws Throwable;

	public Nazione leggi(String codice, String lingua, boolean tutte) throws Throwable;
	
	public List<Nazione> leggi(String lingua) throws Throwable;
	  
	public List<Nazione> leggibyCodice(String codice) throws Throwable;
	
	public List<String> getCodGruppoLinguaList() throws Throwable;
	
	public boolean controlla(String descrizione) throws Throwable;
	
	public void cancella(long id) throws Throwable;
	
	public Nazione inserisci(Nazione vo) throws Throwable;
	
	public void modifica(Nazione vo) throws Throwable;

	public Nazione leggiNazioneTradotta(String code, String lingua) throws Throwable;
	
}
