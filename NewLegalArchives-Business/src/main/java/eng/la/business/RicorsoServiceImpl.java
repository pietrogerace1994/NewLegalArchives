package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.Ricorso;
import eng.la.model.view.RicorsoView;
import eng.la.persistence.RicorsoDAO;

@Service("ricorsoService")
public class RicorsoServiceImpl extends BaseService<Ricorso,RicorsoView> implements RicorsoService {

	@Autowired
	private RicorsoDAO ricorsoDAO;

	public void setDao(RicorsoDAO dao) {
		this.ricorsoDAO = dao;
	}

	public RicorsoDAO getDao() {
		return ricorsoDAO;
	}

	@Override
	public List<RicorsoView> leggi() throws Throwable {
		List<Ricorso> lista = ricorsoDAO.leggi();
		List<RicorsoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public RicorsoView leggi(long id) throws Throwable {
		Ricorso ricorso = ricorsoDAO.leggi(id);
		return (RicorsoView) convertiVoInView(ricorso);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public RicorsoView inserisci(RicorsoView ricorsoView) throws Throwable {
		Ricorso ricorso = ricorsoDAO.inserisci(ricorsoView.getVo());
		RicorsoView view = new RicorsoView();
		view.setVo(ricorso);
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(RicorsoView ricorsoView) throws Throwable {
		ricorsoDAO.modifica(ricorsoView.getVo());
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(RicorsoView ricorsoView) throws Throwable {
		ricorsoDAO.cancella(ricorsoView.getVo().getId());
	}
 
	@Override
	public RicorsoView leggi(String codice, Locale locale, boolean tutte) throws Throwable {
		Ricorso ricorso = ricorsoDAO.leggi(codice, locale.getLanguage().toUpperCase(), tutte);
		return (RicorsoView) convertiVoInView(ricorso);
	}

	@Override
	public List<RicorsoView> leggi(Locale locale, boolean tutte) throws Throwable {
		List<Ricorso> lista = ricorsoDAO.leggi(locale.getLanguage().toUpperCase(),tutte);
		List<RicorsoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	protected Class<Ricorso> leggiClassVO() {
		return Ricorso.class;
	}

	@Override
	protected Class<RicorsoView> leggiClassView() {
		return RicorsoView.class;
	}

}
