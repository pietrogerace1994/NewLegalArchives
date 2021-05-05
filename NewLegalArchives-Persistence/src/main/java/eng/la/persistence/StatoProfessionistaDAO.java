package eng.la.persistence;

import java.util.List;

import eng.la.model.StatoProfessionista;

public interface StatoProfessionistaDAO {

	public List<StatoProfessionista> leggi(String lingua) throws Throwable;
	
	public StatoProfessionista leggi(String codice, String lingua) throws Throwable;
	
}
