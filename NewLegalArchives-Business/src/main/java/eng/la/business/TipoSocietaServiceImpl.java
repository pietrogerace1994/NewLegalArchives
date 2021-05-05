package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.TipoSocieta;
import eng.la.model.view.TipoSocietaView;
import eng.la.persistence.TipoSocietaDAO;


@Service("tipoSocietaService")
public class TipoSocietaServiceImpl extends BaseService<TipoSocieta,TipoSocietaView> implements TipoSocietaService {

	@Autowired
	private TipoSocietaDAO tipoSocietaDao;

	public TipoSocietaDAO getTipoSocietaDao() {
		return tipoSocietaDao;
	}

	public void setTipoSocietaDao(TipoSocietaDAO tipoSocietaDao) {
		this.tipoSocietaDao = tipoSocietaDao;
	}
	
	/**
	 * Ritorna la lista delle TipoSocietaView. 
	 * @return      List<NazioneView>
	 */
	@Override
	public List<TipoSocietaView> leggi(Locale locale) throws Throwable {
		 
		List<TipoSocieta> lista = tipoSocietaDao.leggi(locale.getLanguage().toUpperCase()); 
		List<TipoSocietaView> listaRitorno = convertiVoInView(lista);
		
		return listaRitorno;
	}
	
	/**
	 * Ritorna la lista delle TipoSocietaView. 
	 * @return      List<NazioneView>
	 */
	@Override
	public TipoSocietaView leggi(Long id) throws Throwable {
		 
		TipoSocieta tipoSocieta = tipoSocietaDao.leggi(id); 
		TipoSocietaView tipoSocietaView = (TipoSocietaView) convertiVoInView(tipoSocieta);
		
		return tipoSocietaView;
	}

	@Override
	protected Class<TipoSocieta> leggiClassVO() {
		return TipoSocieta.class;
	}

	@Override
	protected Class<TipoSocietaView> leggiClassView() {
		return TipoSocietaView.class;
	}
	

}
