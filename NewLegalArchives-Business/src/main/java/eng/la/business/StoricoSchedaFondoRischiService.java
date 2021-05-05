/*
 * @author Benedetto Giordano
 */
package eng.la.business;

import java.util.List;

import eng.la.model.view.StoricoSchedaFondoRischiView;
/**
 * Interface StoricoSchedaFondoRischiService.
 */
public interface StoricoSchedaFondoRischiService {
	
	public List<StoricoSchedaFondoRischiView> leggiVersioniPrecedenti(long idScheda) throws Throwable;
	
}
