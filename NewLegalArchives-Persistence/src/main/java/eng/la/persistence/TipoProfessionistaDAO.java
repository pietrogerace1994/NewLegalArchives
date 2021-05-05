package eng.la.persistence;

import java.util.List;

import eng.la.model.TipoProfessionista;

public interface TipoProfessionistaDAO {

	public List<TipoProfessionista> leggi(String lingua) throws Throwable;
	
	public TipoProfessionista leggi(long id) throws Throwable;
	
	public TipoProfessionista leggi(String codice, String lingua) throws Throwable;
	
}
