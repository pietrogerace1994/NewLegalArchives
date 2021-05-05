package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.RischioSoccombenza;
import eng.la.model.view.RischioSoccombenzaView;
import eng.la.persistence.RischioSoccombenzaDAO;

@Service("rischioSoccombenzaService")
public class RischioSoccombenzaServiceImpl extends BaseService<RischioSoccombenza,RischioSoccombenzaView> implements RischioSoccombenzaService {

	@Autowired
	private RischioSoccombenzaDAO rischioSoccombenzaDAO;
	
	/**
	 * Ritorna la lista di TipologiaSchedaFrView.  
	 * @return      List<TipologiaSchedaFrView>
	 * @throws Throwable
	 */
	@Override
	public List<RischioSoccombenzaView> leggi(Locale locale) throws Throwable {
		List<RischioSoccombenza> lista = rischioSoccombenzaDAO.leggi(locale.getLanguage().toUpperCase());
		List<RischioSoccombenzaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public RischioSoccombenzaView leggi(String code, Locale locale) throws Throwable {
		RischioSoccombenza rischioSoccombenza = rischioSoccombenzaDAO.leggi(code, locale.getLanguage().toUpperCase());
		RischioSoccombenzaView rischioSoccombenzaView = (RischioSoccombenzaView) convertiVoInView(rischioSoccombenza);
		return rischioSoccombenzaView;
	}

	@Override
	protected Class<RischioSoccombenza> leggiClassVO() { 
		return RischioSoccombenza.class;
	}

	@Override
	protected Class<RischioSoccombenzaView> leggiClassView() {
		return RischioSoccombenzaView.class;
	}


	
 
}
