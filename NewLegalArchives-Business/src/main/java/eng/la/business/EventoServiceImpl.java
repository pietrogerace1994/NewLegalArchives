package eng.la.business;


import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.Evento;
import eng.la.model.view.EventoView;
import eng.la.persistence.EventoDAO;

/**
 * <h1>Classe di business EventoService </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * 
 * @author 
 * @version 1.0
 * @since 2016-06-16
 */
@Service("eventoService")
public class EventoServiceImpl extends BaseService<Evento,EventoView> implements EventoService {
	
	@Autowired
	private EventoDAO eventoDao;
	
	/**
	 * Metodo di set della istanza DAO passata come argomento, al corrispondente
	 * membro di classe.
	 * 
	 * @param dao oggetto della classe EventoDAO
	 * @see EventoDAO
	 */
	public void setEventoDao(EventoDAO eventoDao) {
		this.eventoDao = eventoDao;
	}
	
	
	/**
	 * Elenca tutte le fatture presenti in base dati
	 * @return lista fatture
	 * @throws Throwable
	 */
	@Override
	public List<EventoView> leggi() throws Throwable {
		List<Evento> lista = eventoDao.leggi();
		List<EventoView> listaRitorno = convertiVoInView(lista);		
		return listaRitorno;
	}
	
	@Override
	public List<EventoView> leggi(Date inizio, Date fine) throws Throwable {
		List<Evento> lista = eventoDao.leggi(inizio,fine);
		List<EventoView> listaRitorno = convertiVoInView(lista);		
		return listaRitorno;
	}
	
	@Override
	public List<EventoView> cerca(EventoView view) throws Throwable {
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		eventoDao.cancella(id);
	}

	/**
	 * Lettura evento per l'id.
	 * <p>
	 * @param id
	 * @throws Throwable
	 */
	@Override
	public EventoView leggi(long id) throws Throwable {
		Evento evento = eventoDao.leggi(id);
		return (EventoView) convertiVoInView(evento);
	}
	
	/**
	 * Esegue inserimento di una evento in base dati.
	 * <p>
	 * @param eventoView 
	 * @return eventoView ritorna l'occorenza inserita.
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public EventoView inserisci(EventoView eventoView) throws Throwable {
		Evento evento = eventoDao.inserisci(eventoView.getVo());
		EventoView view = new EventoView();
		view.setVo(evento);
		return view;
	}

	/**
	 * Esegue la modifca di una determinata occorrenza.
	 * <p>
	 * @param eventoView
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void modifica(EventoView eventoView) throws Throwable {
		eventoDao.modifica(eventoView.getVo());
	}
	
	@Override
	protected Class<Evento> leggiClassVO() { 
		return Evento.class;
	}

	@Override
	protected Class<EventoView> leggiClassView() { 
		return EventoView.class;
	}
}