package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.TipoScadenza;
import eng.la.model.view.TipoScadenzaView;
import eng.la.persistence.TipoScadenzaDAO;

@Service("tipoScadenzaService")
public class TipoScadenzaServiceImpl extends BaseService<TipoScadenza,TipoScadenzaView> implements TipoScadenzaService {

	@Autowired
	private TipoScadenzaDAO tipoScadenzaDao;

	@Override
	public List<TipoScadenzaView> leggi(Locale locale) throws Throwable {
		List<TipoScadenza> lista = tipoScadenzaDao.leggi(locale.getLanguage().toUpperCase()); 
		List<TipoScadenzaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public TipoScadenzaView leggi(Long id) throws Throwable {
		TipoScadenza tipoScadenza = tipoScadenzaDao.leggi(id); 
		TipoScadenzaView tipoScadenzaView = (TipoScadenzaView) convertiVoInView(tipoScadenza);
		return tipoScadenzaView;
	}
	

	@Override
	protected Class<TipoScadenza> leggiClassVO() {
		return TipoScadenza.class;
	}

	@Override
	protected Class<TipoScadenzaView> leggiClassView() {
		return TipoScadenzaView.class;
	}
	

}
