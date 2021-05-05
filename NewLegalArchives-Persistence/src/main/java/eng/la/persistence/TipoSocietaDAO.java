package eng.la.persistence;

import java.util.List;

import eng.la.model.TipoSocieta;

public interface TipoSocietaDAO {
	
	public List<TipoSocieta> leggi(String lingua) throws Throwable;
	
	public TipoSocieta leggi(long id) throws Throwable;

}
