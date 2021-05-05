package eng.la.business;
 
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.CategoriaContest;
import eng.la.model.view.CategoriaContestView;
import eng.la.persistence.CategoriaContestDAO;

@Service("categoriaContestService")
public class CategoriaContestServiceImpl extends BaseService<CategoriaContest, CategoriaContestView> implements CategoriaContestService {

	@Autowired
	private CategoriaContestDAO categoriaContestDao;
	

	public CategoriaContestDAO getCategoriaContestDao() {
		return categoriaContestDao;
	}

	public void setCategoriaContestDao(CategoriaContestDAO categoriaContestDao) {
		this.categoriaContestDao = categoriaContestDao;
	}

	@Override
	public List<CategoriaContestView> leggi(Locale locale) throws Throwable {
		List<CategoriaContest> lista = categoriaContestDao.leggi(locale.getLanguage().toUpperCase());
		List<CategoriaContestView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public CategoriaContestView leggi(String codice, Locale locale) throws Throwable { 
		CategoriaContest categoriaContest = categoriaContestDao.leggi(codice, locale.getLanguage().toUpperCase());
		CategoriaContestView categoriaContestView = new CategoriaContestView();
		categoriaContestView.setVo(categoriaContest);
		return categoriaContestView;
	}
	
	@Override
	protected Class<CategoriaContest> leggiClassVO() { 
		return CategoriaContest.class;
	}

	@Override
	protected Class<CategoriaContestView> leggiClassView() { 
		return CategoriaContestView.class;
	}

}
