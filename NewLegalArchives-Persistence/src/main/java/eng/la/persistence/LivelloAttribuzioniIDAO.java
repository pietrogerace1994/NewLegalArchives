package eng.la.persistence;

import java.util.List;

import eng.la.model.LivelloAttribuzioniI;

public interface LivelloAttribuzioniIDAO {

	public List<LivelloAttribuzioniI> leggi(String lingua) throws Throwable;
	
	public LivelloAttribuzioniI leggi(String code, String lingua) throws Throwable;
	
	public LivelloAttribuzioniI findLivelloAttribuzioniIByPk(long id) throws Throwable;
	

}
