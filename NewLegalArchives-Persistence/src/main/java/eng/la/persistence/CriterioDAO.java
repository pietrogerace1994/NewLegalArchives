package eng.la.persistence;

import java.util.List;

import eng.la.model.Criterio;

public interface CriterioDAO {

	public Criterio leggi(long id) throws Throwable;
	public List<Criterio> leggi(String lingua) throws Throwable;
	public Criterio inserisci(Criterio vo) throws Throwable;
	
}