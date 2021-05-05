package eng.la.persistence;

import java.util.List;

import eng.la.model.Lingua;

public interface LinguaDAO {

	public List<Lingua> leggi() throws Throwable;

}