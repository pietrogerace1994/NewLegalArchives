package eng.la.persistence;

import java.util.List;

import eng.la.model.Procuratore;

public interface ProcuratoreDAO {

	public List<Procuratore> leggi(boolean tutti)throws Throwable;

	public Procuratore leggi(long id) throws Throwable;

}
