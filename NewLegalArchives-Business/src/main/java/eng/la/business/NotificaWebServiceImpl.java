package eng.la.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.NotificaWeb;
import eng.la.model.Utente;
import eng.la.model.view.NotificaWebView;
import eng.la.persistence.NotificaWebDAO;
import eng.la.persistence.UtenteDAO;


/**
 * <h1>Classe di business NotoficaWebServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di lettura
 * sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author 
 * @version 1.0
 * @since 2016-07-01
 */
@Service("notificaWebService")
public class NotificaWebServiceImpl extends BaseService<NotificaWeb,NotificaWebView> implements NotificaWebService{
	
	@Autowired
	private NotificaWebDAO notificaWebDAO;
	
	@Autowired
	private UtenteDAO utenteDAO;
	
	public NotificaWebDAO getNotificaWebDAO() {
		return notificaWebDAO;
	}
	
	public UtenteDAO getUtenteDAO() {
		return utenteDAO;
	}
	
	@Override
	public void inserisci(NotificaWebView notificaWeb) throws Throwable {
		notificaWebDAO.inserisci(notificaWeb.getVo());
	}


	@Override
	public void inserisciAttoInviatoAdAltriUffici(NotificaWebView notificaWeb) throws Throwable {

		notificaWebDAO.inserisci(notificaWeb.getVo());
	}

	@Override
	public void aggiorna(NotificaWebView notificaWeb) throws Throwable {
		notificaWebDAO.aggiorna(notificaWeb.getVo());
	}

	@Override
	public List<NotificaWebView> leggi(String matricola) throws Throwable {
		List<NotificaWeb> lista = notificaWebDAO.leggi(matricola);
		
		if (lista==null) return new ArrayList<>();
		
		List<NotificaWebView> listaRitorno = convertiVoInView(lista); 
		return listaRitorno;
	}
	
	@Override
	public void marcaLetti(List<NotificaWebView> notifiche) throws Throwable {
		
		Date d = new Date();
		if (notifiche != null && !notifiche.isEmpty())
			for (NotificaWebView notificaWebView : notifiche) {
				notificaWebView.getVo().setDataLettura(d);
				notificaWebDAO.aggiorna(notificaWebView.getVo());
			}
	}

	@Override
	protected Class<NotificaWeb> leggiClassVO() {
		return NotificaWeb.class;
	}

	@Override
	protected Class<NotificaWebView> leggiClassView() {
		return NotificaWebView.class;
	}

	@Override
	public void inserisciChiusuraFascicolo(NotificaWebView notificaWeb)  throws Throwable {

		notificaWebDAO.inserisci(notificaWeb.getVo());
		
		Utente top = utenteDAO.leggiResponsabileTop();
		notificaWeb.getVo().setMatricolaDest(top);
		notificaWeb.getVo().setDataLettura(notificaWeb.getVo().getDataNotifica());
		notificaWebDAO.inserisci(notificaWeb.getVo());

		List<Utente> primiRiporti = utenteDAO.leggiCollaboratoriDiretti(top.getMatricolaUtil());
		for (Iterator<Utente> iterator = primiRiporti.iterator(); iterator.hasNext();) {
			Utente utente = iterator.next();
				notificaWeb.getVo().setMatricolaDest(utente);
				notificaWeb.getVo().setDataLettura(notificaWeb.getVo().getDataNotifica());
				notificaWebDAO.inserisci(notificaWeb.getVo());
			
		}
	}

	@Override
	public void inserisciProfessionistaEsterno(NotificaWebView notificaWeb) throws Throwable {
		List<Utente> tutti = utenteDAO.getListaUtentiNotAmmistrativiNotAmministratore();
		
		Utente top = utenteDAO.leggiResponsabileTop();
		notificaWeb.getVo().setMatricolaDest(top);
		notificaWeb.getVo().setDataLettura(notificaWeb.getVo().getDataNotifica());
		notificaWebDAO.inserisci(notificaWeb.getVo());

		List<Utente> primiRiporti = utenteDAO.leggiCollaboratoriDiretti(top.getMatricolaUtil());
		for (Iterator<Utente> iterator = primiRiporti.iterator(); iterator.hasNext();) {
			Utente utente = iterator.next();
				notificaWeb.getVo().setMatricolaDest(utente);
				notificaWeb.getVo().setDataLettura(notificaWeb.getVo().getDataNotifica());
				notificaWebDAO.inserisci(notificaWeb.getVo());
			
		}

		List<String> matricolePrimiRiporti = new ArrayList<>();
		for (Iterator<Utente> iterator = tutti.iterator(); iterator.hasNext();) {
			Utente utente = iterator.next();
			
			matricolePrimiRiporti.add(utente.getMatricolaUtil());
			
		}
		
		for (Iterator<Utente> iterator = tutti.iterator(); iterator.hasNext();) {
			Utente utente = iterator.next();
			
			if (!utente.getMatricolaUtil().equals(top.getMatricolaUtil()) && !matricolePrimiRiporti.contains(utente.getMatricolaUtil())){
				notificaWeb.getVo().setMatricolaDest(utente);
				notificaWeb.getVo().setDataLettura(null);
				notificaWebDAO.inserisci(notificaWeb.getVo());
			}
		}
	}

	@Override
	public void marcaNotificaWebLetta(Long id, Date date) throws Throwable {
		NotificaWeb notifica = notificaWebDAO.leggiById(id);
		notifica.setDataLettura(date);
		notificaWebDAO.aggiorna(notifica);
	}

	@Override
	public void inserisciEstensionePermessi(NotificaWebView notificaWeb, String[] permessiScritturaArray) throws Throwable {
		if (permessiScritturaArray!=null){
			for (int i = 0; i < permessiScritturaArray.length; i++) {
				Utente utente = utenteDAO.leggiUtenteDaUserId(permessiScritturaArray[i]);
				notificaWeb.getVo().setMatricolaDest(utente);
				notificaWebDAO.inserisci(notificaWeb.getVo());
			}
		}
		
	}
}
