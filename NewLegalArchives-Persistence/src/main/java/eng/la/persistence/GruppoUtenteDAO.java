package eng.la.persistence;

import java.util.List;

import eng.la.model.GruppoUtente;

public interface GruppoUtenteDAO {
	
	public List<GruppoUtente> leggiGruppiUtente(String matricola) throws Throwable;
	public GruppoUtente getGruppoUtente(String codice) throws Throwable;
}
