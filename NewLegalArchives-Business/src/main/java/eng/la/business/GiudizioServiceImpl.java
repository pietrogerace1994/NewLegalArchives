package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.Giudizio;
import eng.la.model.view.GiudizioView;
import eng.la.persistence.GiudizioDAO;

@Service("giudizioService")
public class GiudizioServiceImpl extends BaseService<Giudizio,GiudizioView> implements GiudizioService {

	@Autowired
	private GiudizioDAO giudizioDAO;

	public void setDao(GiudizioDAO dao) {
		this.giudizioDAO = dao;
	}

	public GiudizioDAO getDao() {
		return giudizioDAO;
	}

	@Override
	public List<GiudizioView> leggi() throws Throwable {
		List<Giudizio> lista = giudizioDAO.leggi();
		List<GiudizioView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public GiudizioView leggi(long id) throws Throwable {
		Giudizio giudizio = giudizioDAO.leggi(id);
		return convertiVoInView(giudizio);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public GiudizioView inserisci(GiudizioView giudizioView) throws Throwable {
		Giudizio giudizio = giudizioDAO.inserisci(giudizioView.getVo());
		GiudizioView view = new GiudizioView();
		view.setVo(giudizio);
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(GiudizioView giudizioView) throws Throwable {
		giudizioDAO.modifica(giudizioView.getVo()); 
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(GiudizioView giudizioView) throws Throwable {
		giudizioDAO.cancella(giudizioView.getVo().getId()); 
	}

	@Override
	public List<GiudizioView> leggi(String lingua) throws Throwable {
		List<Giudizio> lista = giudizioDAO.leggi(lingua);
		List<GiudizioView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public List<GiudizioView> leggiDaSettoreGiuridicoId(long idSettoreGiuridico, boolean tutte) throws Throwable {
		List<Giudizio> lista = giudizioDAO.leggiDaSettoreGiuridicoId(idSettoreGiuridico,tutte);
		List<GiudizioView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public GiudizioView leggi(String codice, Locale locale, boolean tutte) throws Throwable {
		Giudizio giudizio = giudizioDAO.leggi(codice, locale.getLanguage().toUpperCase(), tutte);
		return convertiVoInView(giudizio);
	}
	
	@Override
	protected Class<Giudizio> leggiClassVO() { 
		return Giudizio.class;
	}

	@Override
	protected Class<GiudizioView> leggiClassView() { 
		return GiudizioView.class;
	}
 
}
