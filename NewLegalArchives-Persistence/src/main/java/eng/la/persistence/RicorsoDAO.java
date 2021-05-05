package eng.la.persistence;

import java.util.List;

import eng.la.model.Ricorso;

public interface RicorsoDAO {
	public List<Ricorso> leggi() throws Throwable;

	public Ricorso leggi(long id) throws Throwable;

	public Ricorso inserisci(Ricorso vo) throws Throwable;

	public void modifica(Ricorso vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public Ricorso leggi(String codice, String lingua, boolean tutte)throws Throwable ;

	public List<Ricorso> leggi(String lingua, boolean tutte)throws Throwable ;
 
}
