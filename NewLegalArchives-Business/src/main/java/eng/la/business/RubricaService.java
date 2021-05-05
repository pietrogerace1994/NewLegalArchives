package eng.la.business;

import java.util.Collection;
import java.util.List;

import eng.la.model.view.RubricaView;
import eng.la.util.ListaPaginata;

public interface RubricaService {
	public RubricaView leggiRubrica(long id) throws Throwable;

	public List<RubricaView> leggiRubrica() throws Throwable;

	public RubricaView salvaRubrica(RubricaView rubricaView) throws Throwable;

	public void cancellaRubrica(long id) throws Throwable;

	public void modificaRubrica(RubricaView rubricaView) throws Throwable;
	
	public ListaPaginata<RubricaView> cerca (String nominativo, String email, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

}
