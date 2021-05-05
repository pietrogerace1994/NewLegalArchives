package eng.la.business;

import java.util.List;

import eng.la.model.view.CriterioView;

public interface CriterioService {
	
	public CriterioView leggi(long id) throws Throwable;
	public List<CriterioView> leggi(String lingua) throws Throwable;
	public CriterioView inserisci(CriterioView criterioView) throws Throwable;
	
}