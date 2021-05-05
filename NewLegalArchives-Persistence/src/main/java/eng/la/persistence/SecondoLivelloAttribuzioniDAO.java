package eng.la.persistence;

import java.util.List;

import eng.la.model.SecondoLivelloAttribuzioni;

public interface SecondoLivelloAttribuzioniDAO {
	
	public List<SecondoLivelloAttribuzioni> leggi(String lingua) throws Throwable;
	
	public SecondoLivelloAttribuzioni leggi(long id) throws Throwable;

}
