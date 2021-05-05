package eng.la.persistence;

import java.util.List;

import eng.la.model.TipologiaSchedaFr;

public interface TipologiaSchedaFrDAO {

	public List<TipologiaSchedaFr> leggi(String lingua) throws Throwable;
	
	public TipologiaSchedaFr leggi(String code, String lingua) throws Throwable;

}
