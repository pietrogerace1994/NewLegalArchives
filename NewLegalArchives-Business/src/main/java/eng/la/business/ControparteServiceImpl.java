package eng.la.business;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.Controparte;
import eng.la.model.view.ControparteView;
import eng.la.persistence.ControparteDAO;

@Service("controparteService")
public class ControparteServiceImpl extends BaseService<Controparte,ControparteView> implements ControparteService{

	@Autowired
	private ControparteDAO controparteDAO;

	public void setDao(ControparteDAO dao) {
		this.controparteDAO = dao;
	}

	public ControparteDAO getDao() {
		return controparteDAO;
	} 
	
	@Override
	public List<ControparteView> leggi() throws Throwable {
		return null;
	}

	@Override
	public ControparteView leggi(long id) throws Throwable { 
		Controparte controparte = controparteDAO.leggi(id);
		return convertiVoInView(controparte);
	}

	@Override
	public List<ControparteView> leggi(Locale locale, String term) throws Throwable { 
		List<Controparte> lista = controparteDAO.leggi(term, locale.getLanguage().toUpperCase());
		List<ControparteView> listaRitorno = convertiVoInView(lista);
		return listaRitorno; 
	}
	
	@Override
	public Collection<ControparteView> leggiDaFascicolo(long id) throws Throwable {
		List<Controparte> lista = controparteDAO.leggiDaFascicolo(id);
		List<ControparteView> listaRitorno = convertiVoInView(lista);
		return listaRitorno; 
	}
	
	@Override
	public ControparteView inserisci(ControparteView controparteView) throws Throwable {
		return null;
	}

	@Override
	public void modifica(ControparteView controparteView) throws Throwable {
		
	}

	@Override
	public void cancella(ControparteView controparteView) throws Throwable {
		
	}
	
	@Override
	public List<Controparte> getControparteDaIdFascicolo(long idFascicolo) throws Throwable {
	
		return controparteDAO.getControparteDaIdFascicolo(idFascicolo);
	}

	@Override
	protected Class<Controparte> leggiClassVO() {
		return Controparte.class;
	}

	@Override
	protected Class<ControparteView> leggiClassView() {
		return ControparteView.class;
	}


	
}
