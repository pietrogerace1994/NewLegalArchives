package eng.la.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.Criterio;
import eng.la.model.view.CriterioView;
import eng.la.persistence.CriterioDAO;

@Service("criterioService")
public class CriterioServiceImpl extends BaseService<Criterio,CriterioView> implements CriterioService {

	@Autowired
	private CriterioDAO criterioDAO;
	
	@Override
	protected Class<Criterio> leggiClassVO() {
		return Criterio.class;
	}

	@Override
	protected Class<CriterioView> leggiClassView() {
		return CriterioView.class;
	}

	@Override
	public CriterioView leggi(long id) throws Throwable {
		Criterio criterio = criterioDAO.leggi(id);
		CriterioView criterioView = convertiVoInView(criterio);
		return criterioView;
	}
	
	@Override
	public List<CriterioView> leggi(String lingua) throws Throwable {
		List<Criterio> lista = criterioDAO.leggi(lingua);
		List<CriterioView> listaView = convertiVoInView(lista);
		return listaView;
	}
	
	@Override
	public CriterioView inserisci(CriterioView criterioView) throws Throwable {
		Criterio criterio = criterioDAO.inserisci(criterioView.getVo());
		CriterioView ret = convertiVoInView(criterio);
		return ret;
	}
}
