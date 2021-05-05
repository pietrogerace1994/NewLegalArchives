package eng.la.business;
 
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.StatoProfessionista;
import eng.la.model.view.StatoProfessionistaView;
import eng.la.persistence.StatoProfessionistaDAO;

@Service("statoProfessionistaService")
public class StatoProfessionistaServiceImpl extends BaseService<StatoProfessionista,StatoProfessionistaView> implements StatoProfessionistaService {

	@Autowired
	private StatoProfessionistaDAO statoProfessionistaDao;
	

	public StatoProfessionistaDAO getStatoProfessionistaDao() {
		return statoProfessionistaDao;
	}

	public void setStatoProfessionistaDao(StatoProfessionistaDAO statoProfessionistaDao) {
		this.statoProfessionistaDao = statoProfessionistaDao;
	}

	@Override
	public List<StatoProfessionistaView> leggi(Locale locale) throws Throwable {
		List<StatoProfessionista> lista = statoProfessionistaDao.leggi(locale.getLanguage().toUpperCase());
		List<StatoProfessionistaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public StatoProfessionistaView leggi(String codice, Locale locale) throws Throwable { 
		StatoProfessionista statoProfessionista = statoProfessionistaDao.leggi(codice, locale.getLanguage().toUpperCase());
		StatoProfessionistaView statoProfessionistaView = new StatoProfessionistaView();
		statoProfessionistaView.setVo(statoProfessionista);
		return statoProfessionistaView;
	}
	
	@Override
	protected Class<StatoProfessionista> leggiClassVO() { 
		return StatoProfessionista.class;
	}

	@Override
	protected Class<StatoProfessionistaView> leggiClassView() { 
		return StatoProfessionistaView.class;
	}

}
