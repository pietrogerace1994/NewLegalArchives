package eng.la.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.Lingua;
import eng.la.model.view.LinguaView;
import eng.la.persistence.LinguaDAO;

@Service("linguaService")
public class LinguaServiceImpl extends BaseService<Lingua,LinguaView> implements LinguaService {

	@Autowired
	private LinguaDAO linguaDAO;

	public void setDao(LinguaDAO dao) {
		this.linguaDAO = dao;
	}

	public LinguaDAO getDao() {
		return linguaDAO;
	}

	@Override
	public List<LinguaView> leggi() throws Throwable {
		List<Lingua> lista = linguaDAO.leggi();
		List<LinguaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	protected Class<Lingua> leggiClassVO() {
		return Lingua.class;
	}

	@Override
	protected Class<LinguaView> leggiClassView() {
		return LinguaView.class;
	}

}
