package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.TerzoChiamatoCausa;
import eng.la.model.view.TerzoChiamatoCausaView;
import eng.la.persistence.TerzoChiamatoInCausaDAO;


@Service("terzoChiamatoCausaService")
public class TerzoChiamatoCausaServiceImpl extends BaseService<TerzoChiamatoCausa,TerzoChiamatoCausaView> implements TerzoChiamatoCausaService {
	@Autowired
	private TerzoChiamatoInCausaDAO terzoChiamatoInCausaDAO;

	public TerzoChiamatoInCausaDAO getAnagraficaStatiTipiDAO() {
		return terzoChiamatoInCausaDAO;
	}

	public void setTerzoChiamatoInCausaDAO(TerzoChiamatoInCausaDAO terzoChiamatoInCausaDAO) {
		this.terzoChiamatoInCausaDAO = terzoChiamatoInCausaDAO;
	}

	@Override
	public List<TerzoChiamatoCausaView> leggiPerAutocomplete(Locale locale, String term) throws Throwable {
		List<TerzoChiamatoCausa> lista = terzoChiamatoInCausaDAO.leggiPerAutocomplete(term, locale.getLanguage().toUpperCase());
		List<TerzoChiamatoCausaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno; 
	}
	
	@Override
	public TerzoChiamatoCausaView leggi(long id) throws Throwable {
		TerzoChiamatoCausa terzoChiamatoCausa = terzoChiamatoInCausaDAO.leggi(id);
		TerzoChiamatoCausaView view = new TerzoChiamatoCausaView();
		view.setVo(terzoChiamatoCausa);
		return view;
	}

	@Override
	protected Class<TerzoChiamatoCausa> leggiClassVO() {
		return TerzoChiamatoCausa.class;
	}

	@Override
	protected Class<TerzoChiamatoCausaView> leggiClassView() {
		return TerzoChiamatoCausaView.class;
	}


}
