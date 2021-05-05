package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.hibernate.FetchMode;

import eng.la.model.view.SettoreGiuridicoView;
import eng.la.util.ListaPaginata;

public interface SettoreGiuridicoService {
	public List<SettoreGiuridicoView> leggi() throws Throwable;

	public SettoreGiuridicoView leggi(long id) throws Throwable;

	public ListaPaginata<SettoreGiuridicoView> cerca(String nome, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable;

	public List<SettoreGiuridicoView> cerca(String nome) throws Throwable;

	public SettoreGiuridicoView inserisci(SettoreGiuridicoView settoreGiuridicoView) throws Throwable;

	public void modifica(SettoreGiuridicoView settoreGiuridicoView) throws Throwable;

	public void cancella(SettoreGiuridicoView settoreGiuridicoView) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public SettoreGiuridicoView leggi(String codice, Locale locale, boolean tutte) throws Throwable;

	public List<SettoreGiuridicoView> leggi(Locale locale) throws Throwable;

	public List<SettoreGiuridicoView> leggiPerTipologiaId(long id, boolean tutte) throws Throwable;
	
	public List<SettoreGiuridicoView> leggiPerTipologiaId(long id) throws Throwable;

}
