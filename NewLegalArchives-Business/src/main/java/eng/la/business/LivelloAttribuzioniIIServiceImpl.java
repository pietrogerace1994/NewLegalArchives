package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.LivelloAttribuzioniII;
import eng.la.model.view.LivelloAttribuzioniIIView;
import eng.la.persistence.LivelloAttribuzioniIIDAO;

@Service("livelloAttribuzioniIIService")
public class LivelloAttribuzioniIIServiceImpl extends BaseService<LivelloAttribuzioniII,LivelloAttribuzioniIIView> implements LivelloAttribuzioniIIService {

	@Autowired
	private LivelloAttribuzioniIIDAO livelloAttribuzioniIIDAO;
	
	@Override
	public List<LivelloAttribuzioniIIView> leggi(Locale locale) throws Throwable {
		List<LivelloAttribuzioniII> lista = livelloAttribuzioniIIDAO.leggi(locale.getLanguage().toUpperCase());
		List<LivelloAttribuzioniIIView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public LivelloAttribuzioniIIView leggi(String code, Locale locale) throws Throwable {
		LivelloAttribuzioniII livelloAttribuzioniII = livelloAttribuzioniIIDAO.leggi(code, locale.getLanguage().toUpperCase());
		LivelloAttribuzioniIIView livelloAttribuzioniIIView = (LivelloAttribuzioniIIView) convertiVoInView(livelloAttribuzioniII);
		return livelloAttribuzioniIIView;
	}

	@Override
	protected Class<LivelloAttribuzioniII> leggiClassVO() { 
		return LivelloAttribuzioniII.class;
	}

	@Override
	protected Class<LivelloAttribuzioniIIView> leggiClassView() {
		return LivelloAttribuzioniIIView.class;
	}

	@Override
	public LivelloAttribuzioniIIView findLivelloAttribuzioniIIByPk(long id) throws Throwable {
		return (LivelloAttribuzioniIIView) convertiVoInView(livelloAttribuzioniIIDAO.findLivelloAttribuzioniIIByPk(id));
	}


	
 
}
