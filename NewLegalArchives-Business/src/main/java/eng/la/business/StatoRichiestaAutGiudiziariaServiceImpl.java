package eng.la.business;
 
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.StatoRichAutGiud;
import eng.la.model.view.StatoRichAutGiudView;
import eng.la.persistence.StatoRichiestaAutGiudiziariaDAO;

@Service("statoRichiestaAutGiudiziariaService")
public class StatoRichiestaAutGiudiziariaServiceImpl extends BaseService<StatoRichAutGiud,StatoRichAutGiudView> implements StatoRichiestaAutGiudiziariaService {

	@Autowired
	private StatoRichiestaAutGiudiziariaDAO statoRichiestaAutGiudiziariaDAO;
	

	@Override
	public List<StatoRichAutGiudView> leggi(Locale locale) throws Throwable {
		List<StatoRichAutGiud> lista = statoRichiestaAutGiudiziariaDAO.leggi(locale.getLanguage().toUpperCase());
		List<StatoRichAutGiudView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public StatoRichAutGiud readStatoRichAutGiudByFilter(Locale locale, String codGruppoLingua) throws Throwable {
		StatoRichAutGiud statoRichAutGiud = statoRichiestaAutGiudiziariaDAO.readStatoRichAutGiudByFilter(
				locale.getLanguage().toUpperCase(), codGruppoLingua);
		return statoRichAutGiud;
	}
	
	
	@Override
	protected Class<StatoRichAutGiud> leggiClassVO() { 
		return StatoRichAutGiud.class;
	}

	@Override
	protected Class<StatoRichAutGiudView> leggiClassView() { 
		return StatoRichAutGiudView.class;
	}


	public StatoRichiestaAutGiudiziariaDAO getStatoRichiestaAutGiudiziariaDAO() {
		return statoRichiestaAutGiudiziariaDAO;
	}


	public void setStatoRichiestaAutGiudiziariaDAO(StatoRichiestaAutGiudiziariaDAO statoRichiestaAutGiudiziariaDAO) {
		this.statoRichiestaAutGiudiziariaDAO = statoRichiestaAutGiudiziariaDAO;
	}

}
