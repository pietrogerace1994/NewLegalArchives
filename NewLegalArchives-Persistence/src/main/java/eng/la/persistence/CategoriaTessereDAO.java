package eng.la.persistence;

import java.util.List;

import eng.la.model.CategoriaTessere;

public interface CategoriaTessereDAO {
	
	public List<CategoriaTessere> leggi(String lingua) throws Throwable;
	
	public CategoriaTessere leggi(long id) throws Throwable;

}
