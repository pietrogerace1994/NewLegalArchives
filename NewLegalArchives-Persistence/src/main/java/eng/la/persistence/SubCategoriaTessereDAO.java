package eng.la.persistence;

import java.util.List;

import eng.la.model.SubCategoriaTessere;

public interface SubCategoriaTessereDAO {
	
	public List<SubCategoriaTessere> leggi(String lingua) throws Throwable;
	
	public SubCategoriaTessere leggi(long id) throws Throwable;

}
