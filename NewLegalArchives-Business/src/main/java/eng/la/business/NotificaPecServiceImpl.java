package eng.la.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.NotificaPec;
import eng.la.model.Utente;
import eng.la.model.UtentePec;
import eng.la.model.view.NotificaPecView;
import eng.la.persistence.NotificaPecDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.persistence.UtentePecDAO;


/**
 * <h1>Classe di business NotificaPecServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di lettura
 * sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author 
 * @version 1.0
 * @since 2016-07-01
 */
@Service("notificaPecService")
public class NotificaPecServiceImpl extends BaseService<NotificaPec,NotificaPecView> implements NotificaPecService{
	
	@Autowired
	private NotificaPecDAO notificaPecDAO;
	
	@Autowired
	private UtenteDAO utenteDAO;
	
	@Autowired
	private UtentePecDAO utentePecDAO;
	
	public NotificaPecDAO getNotificaPecDAO() {
		return notificaPecDAO;
	}
	
	public UtenteDAO getUtenteDAO() {
		return utenteDAO;
	}
	
	@Override
	public void inserisci(Long id) throws Throwable {
		UtentePec utentePec = utentePecDAO.leggi(id);
		List<Utente> opSegreteria = utenteDAO.leggiUtentiOperatoriSegreteria();
		for( Utente op : opSegreteria){
			NotificaPec notificaPec = new NotificaPec();
			notificaPec.setUtentePec(utentePec);
			notificaPec.setDataNotifica(new Date());
			notificaPec.setStato(0);
			notificaPec.setCancellato(0);
			notificaPec.setUtente(op);
			
			notificaPecDAO.inserisci(notificaPec);
		}
	}

	public void cancellaAltre(Long id, Long idUtentePec) throws Throwable {
		notificaPecDAO.cancellaAltre(id, idUtentePec);
	}
	
	@Override
	public void rifiuta(Long id, String motivo) throws Throwable{
		NotificaPec notifica = notificaPecDAO.leggiById(id);
		notifica.setMotivoRifiuto(motivo);
		notifica.setStato(2);
		notificaPecDAO.aggiorna(notifica);
	}
	
	@Override
	public void trasforma(Long id) throws Throwable{
		NotificaPec notifica = notificaPecDAO.leggiById(id);
		notifica.setStato(1);
		notificaPecDAO.aggiorna(notifica);
	}
	
	@Override
	public List<NotificaPecView> leggi(String matricola) throws Throwable {
		
		List<NotificaPec> lista = notificaPecDAO.leggi(matricola);
		
		if (lista==null) return new ArrayList<>();
		
		List<NotificaPecView> listaRitorno = convertiVoInView(lista); 
		
		return listaRitorno;
	}
	
	@Override
	protected Class<NotificaPec> leggiClassVO() {
		return NotificaPec.class;
	}

	@Override
	protected Class<NotificaPecView> leggiClassView() {
		return NotificaPecView.class;
	}

	@Override
	public void marcaNotificaPecLetta(Long id, Date date) throws Throwable {
		NotificaPec notifica = notificaPecDAO.leggiById(id);
		if(notifica.getDataLettura()==null){
			notifica.setDataLettura(date);
			notificaPecDAO.aggiorna(notifica);
		}
	}

	
}
