package eng.la.persistence;

import java.util.List;

import eng.la.model.CategoriaContest;

public interface CategoriaContestDAO {

	public List<CategoriaContest> leggi(String lingua) throws Throwable;
	
	public CategoriaContest leggi(String codice, String lingua) throws Throwable;
	
}
