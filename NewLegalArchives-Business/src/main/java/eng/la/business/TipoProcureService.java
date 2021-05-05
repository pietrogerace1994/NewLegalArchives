package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.TipoProcure;
import eng.la.model.view.TipoProcureView;

public interface TipoProcureService {

	public List<TipoProcureView> leggi(Locale locale) throws Throwable;
	
	public TipoProcureView leggi(Long id) throws Throwable;

	public List<TipoProcureView> leggibyCodice(String tipoProcureCode)  throws Throwable;

	public boolean controlla(TipoProcureView tipoProcureView) throws Throwable;

	public void inserisci(TipoProcureView tipoProcureView) throws Throwable;
	public void cancella(TipoProcureView tipoProcureView) throws Throwable;

	public void modifica(TipoProcureView tipoProcureView) throws Throwable;
	
	public TipoProcureView leggiNotDataCancellazione(long id) throws Throwable;
	
	public List<TipoProcureView> leggiListNotDataCancellazione(Locale locale) throws Throwable;

}
