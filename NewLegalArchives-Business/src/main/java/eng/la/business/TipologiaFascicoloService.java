package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.TipologiaFascicoloView;
import eng.la.util.ListaPaginata;

public interface TipologiaFascicoloService {
	public List<TipologiaFascicoloView> leggi() throws Throwable;

	public TipologiaFascicoloView leggi(long id) throws Throwable;

	public List<TipologiaFascicoloView> cerca(String nome) throws Throwable;

	public ListaPaginata<TipologiaFascicoloView> cerca(String nome, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	public TipologiaFascicoloView inserisci(TipologiaFascicoloView tipologiaFascicoloView) throws Throwable;

	public void modifica(TipologiaFascicoloView tipologiaFascicoloView) throws Throwable;

	public void cancella(TipologiaFascicoloView tipologiaFascicoloView) throws Throwable;

	public List<TipologiaFascicoloView> leggi(Locale locale, boolean tutte) throws Throwable;

	public TipologiaFascicoloView leggi(String codice, Locale locale, boolean tutte) throws Throwable;
	
	public List<TipologiaFascicoloView> leggiPerSettoreGiuridicoId(long id, boolean tutte) throws Throwable;
}
