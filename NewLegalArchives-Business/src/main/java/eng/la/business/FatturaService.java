package eng.la.business;

import java.util.Date;
import java.util.List;

import eng.la.model.view.FatturaView;

public interface FatturaService {
	public void cancella(long id) throws Throwable;
	public FatturaView inserisci(FatturaView fattura) throws Throwable;
	public void modifica(FatturaView fattura) throws Throwable;
	
	public List<FatturaView> cerca(FatturaView view) throws Throwable;
	public FatturaView leggi(long id) throws Throwable;
	public List<FatturaView> leggi() throws Throwable;
}