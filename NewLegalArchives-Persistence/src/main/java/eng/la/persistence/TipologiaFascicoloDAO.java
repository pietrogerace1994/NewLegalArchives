package eng.la.persistence;

import java.util.List;

import eng.la.model.TipologiaFascicolo;

public interface TipologiaFascicoloDAO {

	public List<TipologiaFascicolo> leggi() throws Throwable;

	public TipologiaFascicolo leggi(long id) throws Throwable;
	
	public List<TipologiaFascicolo> leggi(String lingua, boolean tutte) throws Throwable;

	public List<TipologiaFascicolo> cerca(String nome, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable;

	public List<TipologiaFascicolo> cerca(String nome) throws Throwable;

	public TipologiaFascicolo inserisci(TipologiaFascicolo vo) throws Throwable;

	public void modifica(TipologiaFascicolo vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public TipologiaFascicolo leggi(String codice, String lingua, boolean tutte) throws Throwable;
	
	public List<TipologiaFascicolo> leggiPerSettoreGiuridicoId(long id, boolean tutte) throws Throwable;

}
