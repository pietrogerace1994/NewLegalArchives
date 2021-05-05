package eng.la.business;
 
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.TipologiaRichiesta;
import eng.la.model.view.TipologiaRichiestaView;
import eng.la.persistence.TipoRichiestaAutGiudiziariaDAO;

@Service("tipoRichiestaAutGiudiziariaService")
public class TipoRichiestaAutGiudiziariaServiceImpl extends BaseService<TipologiaRichiesta,TipologiaRichiestaView> implements TipoRichiestaAutGiudiziariaService {

	@Autowired
	private TipoRichiestaAutGiudiziariaDAO tipoRichiestaAutGiudiziariaDAO;
	

	@Override
	public List<TipologiaRichiestaView> leggi(Locale locale) throws Throwable {
		List<TipologiaRichiesta> lista = tipoRichiestaAutGiudiziariaDAO.leggi(locale.getLanguage().toUpperCase());
		List<TipologiaRichiestaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	
	@Override
	protected Class<TipologiaRichiesta> leggiClassVO() { 
		return TipologiaRichiesta.class;
	}

	@Override
	protected Class<TipologiaRichiestaView> leggiClassView() { 
		return TipologiaRichiestaView.class;
	}

	public TipoRichiestaAutGiudiziariaDAO getTipoRichiestaAutGiudiziariaDAO() {
		return tipoRichiestaAutGiudiziariaDAO;
	}

	public void setTipoRichiestaAutGiudiziariaDAO(TipoRichiestaAutGiudiziariaDAO tipoRichiestaAutGiudiziariaDAO) {
		this.tipoRichiestaAutGiudiziariaDAO = tipoRichiestaAutGiudiziariaDAO;
	}
}
