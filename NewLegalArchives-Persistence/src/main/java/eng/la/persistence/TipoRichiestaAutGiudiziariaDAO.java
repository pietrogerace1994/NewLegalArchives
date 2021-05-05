package eng.la.persistence;

import java.util.List;

import eng.la.model.TipologiaRichiesta;

public interface TipoRichiestaAutGiudiziariaDAO {

	public List<TipologiaRichiesta> leggi(String lingua) throws Throwable;
	  
}
