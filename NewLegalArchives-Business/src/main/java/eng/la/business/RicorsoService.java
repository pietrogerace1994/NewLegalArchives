package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.RicorsoView;

public interface RicorsoService {
	public List<RicorsoView> leggi() throws Throwable;

	public RicorsoView leggi(long id) throws Throwable;

	public RicorsoView leggi(String codice, Locale locale, boolean tutti) throws Throwable;

	public RicorsoView inserisci(RicorsoView ricorsoView) throws Throwable;

	public void modifica(RicorsoView ricorsoView) throws Throwable;

	public void cancella(RicorsoView ricorsoView) throws Throwable;

	public List<RicorsoView> leggi(Locale locale, boolean tutte) throws Throwable;;
 
}
