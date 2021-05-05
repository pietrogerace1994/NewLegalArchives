package eng.la.business;
 
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.StatoEsitoValutazioneProf;
import eng.la.model.view.StatoEsitoValutazioneProfView;
import eng.la.persistence.StatoEsitoValutazioneProfDAO;

@Service("statoEsitoValutazioneProfService")
public class StatoEsitoValutazioneProfServiceImpl extends BaseService<StatoEsitoValutazioneProf,StatoEsitoValutazioneProfView> implements StatoEsitoValutazioneProfService {

	@Autowired
	private StatoEsitoValutazioneProfDAO statoEsitoValutazioneProfDao;
	

	public StatoEsitoValutazioneProfDAO getStatoEsitoValutazioneProfDao() {
		return statoEsitoValutazioneProfDao;
	}

	public void setStatoEsitoValutazioneProfDao(StatoEsitoValutazioneProfDAO statoEsitoValutazioneProfDao) {
		this.statoEsitoValutazioneProfDao = statoEsitoValutazioneProfDao;
	}

	@Override
	public List<StatoEsitoValutazioneProfView> leggi(Locale locale) throws Throwable {
		List<StatoEsitoValutazioneProf> lista = statoEsitoValutazioneProfDao.leggi(locale.getLanguage().toUpperCase());
		List<StatoEsitoValutazioneProfView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public StatoEsitoValutazioneProfView leggi(String codice, Locale locale) throws Throwable { 
		StatoEsitoValutazioneProf statoEsitoValutazioneProf = statoEsitoValutazioneProfDao.leggi(codice, locale.getLanguage().toUpperCase());
		StatoEsitoValutazioneProfView statoEsitoValutazioneProfView = new StatoEsitoValutazioneProfView();
		statoEsitoValutazioneProfView.setVo(statoEsitoValutazioneProf);
		return statoEsitoValutazioneProfView;
	}
	
	@Override
	protected Class<StatoEsitoValutazioneProf> leggiClassVO() { 
		return StatoEsitoValutazioneProf.class;
	}

	@Override
	protected Class<StatoEsitoValutazioneProfView> leggiClassView() { 
		return StatoEsitoValutazioneProfView.class;
	}

}
