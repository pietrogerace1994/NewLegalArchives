package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.PosizioneOrganizzativa;
import eng.la.model.view.PosizioneOrganizzativaView;
import eng.la.persistence.PosizioneOrganizzativaDAO;

@Service("posizioneOrganizzativaService")
public class PosizioneOrganizzativaServiceImpl extends BaseService<PosizioneOrganizzativa,PosizioneOrganizzativaView> implements PosizioneOrganizzativaService {

	@Autowired
	private PosizioneOrganizzativaDAO posizioneOrganizzativaDAO;
	
	@Override
	public List<PosizioneOrganizzativaView> leggi(Locale locale) throws Throwable {
		List<PosizioneOrganizzativa> lista = posizioneOrganizzativaDAO.leggi(locale.getLanguage().toUpperCase());
		List<PosizioneOrganizzativaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public PosizioneOrganizzativaView leggi(String code, Locale locale) throws Throwable {
		PosizioneOrganizzativa posizioneOrganizzativa = posizioneOrganizzativaDAO.leggi(code, locale.getLanguage().toUpperCase());
		PosizioneOrganizzativaView posizioneOrganizzativaView = (PosizioneOrganizzativaView) convertiVoInView(posizioneOrganizzativa);
		return posizioneOrganizzativaView;
	}
	
	@Override
	public PosizioneOrganizzativaView leggi(Long id) throws Throwable {
		PosizioneOrganizzativa posizioneOrganizzativa = posizioneOrganizzativaDAO.leggi(id);
		PosizioneOrganizzativaView posizioneOrganizzativaView = (PosizioneOrganizzativaView) convertiVoInView(posizioneOrganizzativa);
		return posizioneOrganizzativaView;
	}


	@Override
	protected Class<PosizioneOrganizzativa> leggiClassVO() { 
		return PosizioneOrganizzativa.class;
	}

	@Override
	protected Class<PosizioneOrganizzativaView> leggiClassView() {
		return PosizioneOrganizzativaView.class;
	}


	
 
}
