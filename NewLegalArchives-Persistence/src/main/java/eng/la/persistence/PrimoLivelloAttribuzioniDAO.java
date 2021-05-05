package eng.la.persistence;

import java.util.List;

import eng.la.model.PrimoLivelloAttribuzioni;

public interface PrimoLivelloAttribuzioniDAO {
	
	public List<PrimoLivelloAttribuzioni> leggi(String lingua) throws Throwable;
	
	public PrimoLivelloAttribuzioni leggi(long id) throws Throwable;

}
