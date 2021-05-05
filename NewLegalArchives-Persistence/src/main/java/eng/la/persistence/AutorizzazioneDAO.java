package eng.la.persistence;

import java.util.List;

import eng.la.model.Autorizzazione;

public interface AutorizzazioneDAO {

	public Autorizzazione leggiAutorizzazioneUtenteCorrente(long idEntita, String nomeClasse) throws Throwable;

	public List<Autorizzazione> leggiAutorizzazioni(long idEntita, String nomeClasse)throws Throwable;
	public List<Autorizzazione> leggiAutorizzazioni2(long idEntita, String nomeClasse) throws Throwable;
	
	public void cancellaAutorizzazioni(Long idEntita, String nomeClasse, String ownerUserId)throws Throwable;
	
	public void cancellaAutorizzazione(Long idEntita, String nomeClasse, String serId)throws Throwable;

	public void inserisci(Autorizzazione autorizzazione)throws Throwable;

	public void cancellaAutorizzazioniPerCambioOwner(Long idFascicolo, String ownerUserId) throws Throwable;
	
	public List<String> leggiAutorizzati(List<Long> idEntitas, String nomeClasse) throws Throwable;
}
