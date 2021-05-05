package eng.la.business;
 
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.TipoProfessionista;
import eng.la.model.view.TipoProfessionistaView;
import eng.la.persistence.TipoProfessionistaDAO;

@Service("tipoProfessionistaService")
public class TipoProfessionistaServiceImpl extends BaseService<TipoProfessionista,TipoProfessionistaView> implements TipoProfessionistaService {

	@Autowired
	private TipoProfessionistaDAO tipoProfessionistaDao;
	

	public TipoProfessionistaDAO getTipoProfessionistaDao() {
		return tipoProfessionistaDao;
	}

	public void setTipoProfessionistaDao(TipoProfessionistaDAO tipoProfessionistaDao) {
		this.tipoProfessionistaDao = tipoProfessionistaDao;
	}

	@Override
	public List<TipoProfessionistaView> leggi(Locale locale) throws Throwable {
		List<TipoProfessionista> lista = tipoProfessionistaDao.leggi(locale.getLanguage().toUpperCase());
		List<TipoProfessionistaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public TipoProfessionistaView leggi(long id) throws Throwable {
		TipoProfessionista tipoProfessionista = tipoProfessionistaDao.leggi(id);
		return (TipoProfessionistaView) convertiVoInView(tipoProfessionista);
	}
	
	@Override
	public TipoProfessionistaView leggi(String codice, Locale locale) throws Throwable { 
		TipoProfessionista tipoProfessionista = tipoProfessionistaDao.leggi(codice, locale.getLanguage().toUpperCase());
		TipoProfessionistaView tipoProfessionistaView = new TipoProfessionistaView();
		tipoProfessionistaView.setVo(tipoProfessionista);
		return tipoProfessionistaView;
	}
	
	@Override
	protected Class<TipoProfessionista> leggiClassVO() { 
		return TipoProfessionista.class;
	}

	@Override
	protected Class<TipoProfessionistaView> leggiClassView() { 
		return TipoProfessionistaView.class;
	}

}
