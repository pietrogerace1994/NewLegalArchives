package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.TipologiaSchedaFr;
import eng.la.model.view.TipologiaSchedaFrView;
import eng.la.persistence.TipologiaSchedaFrDAO;

@Service("tipologiaSchedaFrService")
public class TipologiaSchedaFrServiceImpl extends BaseService<TipologiaSchedaFr,TipologiaSchedaFrView> implements TipologiaSchedaFrService {

	@Autowired
	private TipologiaSchedaFrDAO tipologiaSchedaFrDAO;
	
	public void setTipologiaSchedaFrDAO(TipologiaSchedaFrDAO tipologiaSchedaFrDAO) {
		this.tipologiaSchedaFrDAO = tipologiaSchedaFrDAO;
	}
	
	public TipologiaSchedaFrDAO getTipologiaSchedaFrDAO() {
		return tipologiaSchedaFrDAO;
	}
	
	/**
	 * Ritorna la lista di TipologiaSchedaFrView.  
	 * @return      List<TipologiaSchedaFrView>
	 * @throws Throwable
	 */
	@Override
	public List<TipologiaSchedaFrView> leggi(Locale locale) throws Throwable {
		List<TipologiaSchedaFr> lista = tipologiaSchedaFrDAO.leggi(locale.getLanguage().toUpperCase());
		List<TipologiaSchedaFrView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	/**
	 * Ritorna la TipologiaSchedaFrView.  
	 * @return      TipologiaSchedaFrView
	 * @throws Throwable
	 */
	@Override
	public TipologiaSchedaFrView leggi(String code, Locale locale) throws Throwable {
		TipologiaSchedaFr tipologiaSchedaFr = tipologiaSchedaFrDAO.leggi(code, locale.getLanguage().toUpperCase());
		TipologiaSchedaFrView tipologiaSchedaFrView = (TipologiaSchedaFrView) convertiVoInView(tipologiaSchedaFr);
		return tipologiaSchedaFrView;
	}


	@Override
	protected Class<TipologiaSchedaFr> leggiClassVO() { 
		return TipologiaSchedaFr.class;
	}

	@Override
	protected Class<TipologiaSchedaFrView> leggiClassView() {
		return TipologiaSchedaFrView.class;
	}
 
}
