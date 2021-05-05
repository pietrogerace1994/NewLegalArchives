package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.LivelloAttribuzioniI;
import eng.la.model.view.LivelloAttribuzioniIView;
import eng.la.persistence.LivelloAttribuzioniIDAO;

@Service("livelloAttribuzioniIService")
public class LivelloAttribuzioniIServiceImpl extends BaseService<LivelloAttribuzioniI,LivelloAttribuzioniIView> implements LivelloAttribuzioniIService {

	@Autowired
	private LivelloAttribuzioniIDAO livelloAttribuzioniIDAO;
	
	@Override
	public List<LivelloAttribuzioniIView> leggi(Locale locale) throws Throwable {
		List<LivelloAttribuzioniI> lista = livelloAttribuzioniIDAO.leggi(locale.getLanguage().toUpperCase());
		List<LivelloAttribuzioniIView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public LivelloAttribuzioniIView leggi(String code, Locale locale) throws Throwable {
		LivelloAttribuzioniI livelloAttribuzioniI = livelloAttribuzioniIDAO.leggi(code, locale.getLanguage().toUpperCase());
		LivelloAttribuzioniIView livelloAttribuzioniIView = convertiVoInView(livelloAttribuzioniI);
		return livelloAttribuzioniIView;
	}


	@Override
	protected Class<LivelloAttribuzioniI> leggiClassVO() { 
		return LivelloAttribuzioniI.class;
	}

	@Override
	protected Class<LivelloAttribuzioniIView> leggiClassView() {
		return LivelloAttribuzioniIView.class;
	}

	@Override
	public LivelloAttribuzioniIView findLivelloAttribuzioniIByPk(long id) throws Throwable {
		return (LivelloAttribuzioniIView) convertiVoInView(livelloAttribuzioniIDAO.findLivelloAttribuzioniIByPk(id));
	}


	
 
}
