package eng.la.persistence;

import java.util.List;

import eng.la.model.Controparte;

public interface ControparteDAO {

	public void inserisciControparte(Controparte controparte) throws Throwable;

	public List<Controparte> leggi(String term, String lingua) throws Throwable;

	public Controparte leggi(long id) throws Throwable;

	public List<Controparte> leggiDaFascicolo(long id) throws Throwable;
	public List<Controparte> getControparteDaIdFascicolo(long idFascicolo) throws Throwable ;
	
}
