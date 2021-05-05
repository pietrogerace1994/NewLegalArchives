package eng.la.persistence;

import java.util.List;

import eng.la.model.TipoOrganoSociale;

public interface TipoOrganoSocialeDAO {
	
	public List<TipoOrganoSociale> leggi(String lingua) throws Throwable;
	
	public TipoOrganoSociale leggi(long id) throws Throwable;

}
