package eng.la.persistence;

import java.util.List;

import eng.la.model.LivelloAttribuzioniII;

public interface LivelloAttribuzioniIIDAO {

	public List<LivelloAttribuzioniII> leggi(String lingua) throws Throwable;
	
	public LivelloAttribuzioniII leggi(String code, String lingua) throws Throwable;

	public LivelloAttribuzioniII findLivelloAttribuzioniIIByPk(long id) throws Throwable;
	
}
