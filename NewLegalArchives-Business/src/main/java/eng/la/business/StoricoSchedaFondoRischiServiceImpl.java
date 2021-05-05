package eng.la.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.StoricoSchedaFondoRischi;
import eng.la.model.view.StoricoSchedaFondoRischiView;
import eng.la.persistence.SchedaFondoRischiDAO;

@Service("storicoSchedaFondoRischiService")
public class StoricoSchedaFondoRischiServiceImpl extends BaseService<StoricoSchedaFondoRischi,StoricoSchedaFondoRischiView> implements StoricoSchedaFondoRischiService {

	@Autowired
	private SchedaFondoRischiDAO schedaFondoRischiDAO;

	
	@Override
	public List<StoricoSchedaFondoRischiView> leggiVersioniPrecedenti(long idScheda) throws Throwable {
		List<StoricoSchedaFondoRischi> lista = schedaFondoRischiDAO.leggiVersioniPrecedenti(idScheda);
		List<StoricoSchedaFondoRischiView> listaView = (List<StoricoSchedaFondoRischiView>) convertiVoInView(lista);
		return listaView;
	}

	@Override
	protected Class<StoricoSchedaFondoRischi> leggiClassVO() {
		return StoricoSchedaFondoRischi.class;
	}

	@Override
	protected Class<StoricoSchedaFondoRischiView> leggiClassView() {
		return StoricoSchedaFondoRischiView.class;
	}
}
