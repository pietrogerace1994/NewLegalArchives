package eng.la.business;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import eng.la.model.Controparte;
import eng.la.model.view.ControparteView; 

public interface ControparteService {
	public List<ControparteView> leggi() throws Throwable;

	public ControparteView leggi(long id) throws Throwable;

	public ControparteView inserisci(ControparteView controparteView) throws Throwable;

	public void modifica(ControparteView controparteView) throws Throwable;

	public void cancella(ControparteView controparteView) throws Throwable;

	public List<ControparteView> leggi(Locale locale, String term)throws Throwable;

	public Collection<ControparteView> leggiDaFascicolo(long id)throws Throwable;
	
	public List<Controparte> getControparteDaIdFascicolo(long idFascicolo)throws Throwable;

}
