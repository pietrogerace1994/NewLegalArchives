package eng.la.business;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.SchedaValutazione;
import eng.la.model.view.SchedaValutazioneView;
import eng.la.persistence.SchedaValutazioneDAO;

/**
 * <h1>Classe di business SchedaValutazioneServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author 
 * @version 1.0
 * @since 2016-07-01
 */
@Service("schedaValutazioneService")
public class SchedaValutazioneServiceImpl extends BaseService<SchedaValutazione,SchedaValutazioneView> implements SchedaValutazioneService {
	
	@Autowired
	private SchedaValutazioneDAO schedaValutazioneDao;
	
	/**
	 * Metodo di set della istanza DAO passata come argomento, al corrispondente
	 * membro di classe.
	 * <p>
	 * @param dao oggetto della classe SchedaValutazioneDAO
	 * @see SchedaValutazioneDAO
	 */
	public void setSchedaValutazioneDao(SchedaValutazioneDAO schedaValutazioneDao) {
		this.schedaValutazioneDao = schedaValutazioneDao;
	}
	
	/**
	 * Elenca tutte le SchedeValutazione presenti in base dati.
	 * <p>
	 * @return lista SchedeValutazioneView
	 * @throws Throwable
	 */
	@Override
	public List<SchedaValutazioneView> leggi() throws Throwable {
		List<SchedaValutazione> lista = schedaValutazioneDao.leggi();
		List<SchedaValutazioneView> listaRitorno = (List<SchedaValutazioneView>) convertiVoInView(lista);		
		return listaRitorno;
	}
	
	@Override
	public List<SchedaValutazioneView> cerca(SchedaValutazioneView view) throws Throwable {
		return null;
	}

	// metodi di scrittura
	/**
	 * Cancellazione logica di una occorrenza.
	 * <p>
	 * @param id
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		schedaValutazioneDao.cancella(id);
	}

	/**
	 * Lettura schedaValutazione per l'id.
	 * <p>
	 * @param id
	 * @throws Throwable
	 */
	@Override
	public SchedaValutazioneView leggi(long id) throws Throwable {
		SchedaValutazione schedaValutazione = schedaValutazioneDao.leggi(id);
		return (SchedaValutazioneView) convertiVoInView(schedaValutazione);
	}
	
	/**
	 * Esegue inserimento di una schedaValutazione in base dati.
	 * <p>
	 * @param schedaValutazioneView 
	 * @return schedaValutazioneView ritorna l'occorenza inserita.
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public SchedaValutazioneView inserisci(SchedaValutazioneView schedaValutazioneView) throws Throwable {
		SchedaValutazione schedaValutazione = schedaValutazioneDao.inserisci(schedaValutazioneView.getVo());
		SchedaValutazioneView view = new SchedaValutazioneView();
		view.setVo(schedaValutazione);
		return view;
	}

	/**
	 * Esegue la modifca di una determinata occorrenza.
	 * <p>
	 * @param schedaValutazioneView
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void modifica(SchedaValutazioneView schedaValutazioneView) throws Throwable {
		schedaValutazioneDao.modifica(schedaValutazioneView.getVo());
	}
	
	// metodi extra
	@Override
	protected Class<SchedaValutazione> leggiClassVO() { 
		return SchedaValutazione.class;
	}

	@Override
	protected Class<SchedaValutazioneView> leggiClassView() { 
		return SchedaValutazioneView.class;
	}
}