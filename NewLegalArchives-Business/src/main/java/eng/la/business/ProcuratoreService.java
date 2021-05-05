package eng.la.business;

import java.util.List;

import eng.la.model.view.ProcuratoreView;

public interface ProcuratoreService  {
	public List<ProcuratoreView> leggi(boolean tutti)throws Throwable;
	
	public ProcuratoreView leggi(long id)throws Throwable;
	
}
