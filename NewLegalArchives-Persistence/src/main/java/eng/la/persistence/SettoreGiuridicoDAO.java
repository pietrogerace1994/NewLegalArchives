package eng.la.persistence;

import java.util.List;

import eng.la.model.SettoreGiuridico;

public interface SettoreGiuridicoDAO {

	public List<SettoreGiuridico> leggi() throws Throwable;

	public SettoreGiuridico leggi(long id) throws Throwable;

	public List<SettoreGiuridico> cerca(String nome, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable;

	public List<SettoreGiuridico> cerca(String nome) throws Throwable;

	public SettoreGiuridico inserisci(SettoreGiuridico vo) throws Throwable;

	public void modifica(SettoreGiuridico vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public SettoreGiuridico leggi(String codice, String lingua, boolean tutte) throws Throwable;
	
	public List<SettoreGiuridico> leggiPerTipologiaId(long id, boolean tutte) throws Throwable;

	public List<SettoreGiuridico> leggiPerTipologiaId(long id) throws Throwable;

	public List<SettoreGiuridico> leggi(String lingua)throws Throwable;

}
