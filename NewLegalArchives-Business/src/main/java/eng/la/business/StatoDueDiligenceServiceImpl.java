package eng.la.business;
 
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.StatoDueDiligence;
import eng.la.model.view.StatoDueDiligenceView;
import eng.la.persistence.StatoDueDiligenceDAO;

@Service("statoDueDiligenceService")
public class StatoDueDiligenceServiceImpl extends BaseService<StatoDueDiligence,StatoDueDiligenceView> implements StatoDueDiligenceService {

	@Autowired
	private StatoDueDiligenceDAO statoDueDiligenceDAO;
	

	@Override
	public List<StatoDueDiligenceView> leggi(Locale locale) throws Throwable {
		List<StatoDueDiligence> lista = statoDueDiligenceDAO.leggi(locale.getLanguage().toUpperCase());
		List<StatoDueDiligenceView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public StatoDueDiligence readStatoDueDiligenceByFilter(Locale locale, String codGruppoLingua) throws Throwable {
		StatoDueDiligence statoDueDiligence = statoDueDiligenceDAO.readStatoDueDiligenceByFilter(
				locale.getLanguage().toUpperCase(), codGruppoLingua);
		return statoDueDiligence;
	}
	
	
	@Override
	protected Class<StatoDueDiligence> leggiClassVO() { 
		return StatoDueDiligence.class;
	}

	@Override
	protected Class<StatoDueDiligenceView> leggiClassView() { 
		return StatoDueDiligenceView.class;
	}

	public StatoDueDiligenceDAO getStatoDueDiligenceDAO() {
		return statoDueDiligenceDAO;
	}

	public void setStatoDueDiligenceDAO(StatoDueDiligenceDAO statoDueDiligenceDAO) {
		this.statoDueDiligenceDAO = statoDueDiligenceDAO;
	}

}
