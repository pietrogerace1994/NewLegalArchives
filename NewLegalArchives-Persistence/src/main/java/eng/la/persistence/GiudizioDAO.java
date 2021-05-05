package eng.la.persistence;

import java.util.List;

import eng.la.model.Giudizio;

public interface GiudizioDAO {

	public List<Giudizio> leggi() throws Throwable;

	public Giudizio leggi(long id) throws Throwable;

	public Giudizio inserisci(Giudizio vo) throws Throwable;

	public void modifica(Giudizio vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public List<Giudizio> leggi(String lingua) throws Throwable;

	public List<Giudizio> leggiDaSettoreGiuridicoId(long idSettoreGiuridico, boolean tutte) throws Throwable;

	public Giudizio leggi(String codice, String lingua, boolean tutte)throws Throwable;


}
