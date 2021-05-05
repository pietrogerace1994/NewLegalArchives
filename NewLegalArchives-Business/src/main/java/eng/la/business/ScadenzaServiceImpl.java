package eng.la.business;


import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.Scadenza;
import eng.la.model.view.ScadenzaView;
import eng.la.persistence.ScadenzaDAO;

/**
 * <h1>Classe di business ScadenzaService </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * 
 * @author 
 * @version 1.0
 * @since 2016-06-16
 */
@Service("scadenzaService")
public class ScadenzaServiceImpl extends BaseService<Scadenza,ScadenzaView> implements ScadenzaService {
	
	@Autowired
	private ScadenzaDAO scadenzaDao;
	
	/**
	 * Metodo di set della istanza DAO passata come argomento, al corrispondente
	 * membro di classe.
	 * 
	 * @param dao oggetto della classe ScadenzaDAO
	 * @see ScadenzaDAO
	 */
	public void setScadenzaDao(ScadenzaDAO scadenzaDao) {
		this.scadenzaDao = scadenzaDao;
	}
	
	
	/**
	 * Elenca tutte le fatture presenti in base dati
	 * @return lista fatture
	 * @throws Throwable
	 */
	@Override
	public List<ScadenzaView> leggi() throws Throwable {
		List<Scadenza> lista = scadenzaDao.leggi();
		List<ScadenzaView> listaRitorno = (List<ScadenzaView>) convertiVoInView(lista);		
		return listaRitorno;
	}
	
	@Override
	public List<ScadenzaView> leggi(Date inizio,Date fine) throws Throwable {
		List<Scadenza> lista = scadenzaDao.leggi(inizio,fine);
		List<ScadenzaView> listaRitorno = (List<ScadenzaView>) convertiVoInView(lista);		
		return listaRitorno;
	}
	
	@Override
	public List<ScadenzaView> cerca(ScadenzaView view) throws Throwable {
		return null;
	}

	// metodi di scrittura
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		scadenzaDao.cancella(id);
	}

	/**
	 * Lettura scadenza per l'id.
	 * <p>
	 * @param id
	 * @throws Throwable
	 */
	@Override
	public ScadenzaView leggi(long id) throws Throwable {
		Scadenza scadenza = scadenzaDao.leggi(id);
		return (ScadenzaView) convertiVoInView(scadenza);
	}
	
	/**
	 * Esegue inserimento di una scadenza in base dati.
	 * <p>
	 * @param scadenzaView 
	 * @return scadenzaView ritorna l'occorenza inserita.
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public ScadenzaView inserisci(ScadenzaView scadenzaView) throws Throwable {
		Scadenza scadenza = scadenzaDao.inserisci(scadenzaView.getVo());
		ScadenzaView view = new ScadenzaView();
		view.setVo(scadenza);
		return view;
	}

	/**
	 * Esegue la modifca di una determinata occorrenza.
	 * <p>
	 * @param scadenzaView
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void modifica(ScadenzaView scadenzaView) throws Throwable {
		scadenzaDao.modifica(scadenzaView.getVo());
	}
	
	// metodi extra
	//...
	
	@Override
	protected Class<Scadenza> leggiClassVO() { 
		return Scadenza.class;
	}

	@Override
	protected Class<ScadenzaView> leggiClassView() { 
		return ScadenzaView.class;
	}
}