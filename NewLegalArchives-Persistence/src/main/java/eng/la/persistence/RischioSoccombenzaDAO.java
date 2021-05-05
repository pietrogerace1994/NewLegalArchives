package eng.la.persistence;

import java.util.List;

import eng.la.model.RischioSoccombenza;

public interface RischioSoccombenzaDAO {

	public List<RischioSoccombenza> leggi(String lingua) throws Throwable;
	
	public RischioSoccombenza leggi(String code, String lingua) throws Throwable;

}
