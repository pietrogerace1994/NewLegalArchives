package eng.la.persistence;

import java.util.List;

import eng.la.model.RichAutGiud;
import eng.la.model.view.AutoritaGiudiziariaView;

public interface RichiestaAutGiudiziariaDAO {

	public RichAutGiud save(RichAutGiud richAutGiud) throws Throwable;

	public List<RichAutGiud> search(boolean isActive) throws Throwable;

	public RichAutGiud read(long id) throws Throwable;

	public RichAutGiud update(RichAutGiud richAutGiud) throws Throwable;

	public void delete(Long id) throws Throwable;

	public List<RichAutGiud> searchRichAutGiudByFilter(AutoritaGiudiziariaView autoritaGiudiziariaView)
			throws Throwable;
	  
}
