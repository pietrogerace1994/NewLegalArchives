package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.GiudizioView;

public interface GiudizioService {
	public List<GiudizioView> leggi() throws Throwable;

	public GiudizioView leggi(long id) throws Throwable;

	public GiudizioView inserisci(GiudizioView giudizioView) throws Throwable;

	public void modifica(GiudizioView giudizioView) throws Throwable;

	public void cancella(GiudizioView giudizioView) throws Throwable;

	public List<GiudizioView> leggi(String country) throws Throwable;

	public GiudizioView leggi(String codice, Locale locale, boolean tutti) throws Throwable;

	public List<GiudizioView> leggiDaSettoreGiuridicoId(long idSettoreGiuridico, boolean tutte) throws Throwable;
}
