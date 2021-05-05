package eng.la.persistence;

import java.util.List;
import eng.la.model.TipoScadenza;

public interface TipoScadenzaDAO {

	public List<TipoScadenza> leggi(String lingua) throws Throwable;
	public TipoScadenza leggi(long id) throws Throwable;
}