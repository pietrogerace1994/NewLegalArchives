package eng.la.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.NotificaWeb;
import eng.la.model.Utente;
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
@Service("notificaWebMailService")
public class NotificaWebMailServiceImpl implements NotificaWebMailService{
	
	private static final String mbx = "mbx.legalarchives@snam.it";
	
	private static final String atto_inviato_altri_uffici = "atto.inviato.altri.uffici";
	private static final String fascicolo_chiuso_indata = "fascicolo.chiuso.indata";
	private static final String professionista_esterno_inserito = "professionista.esterno.inserito";
	
	private static final String oggetto_invio_settimanale_notifiche_web = "oggetto.invio.settimanale.notifiche.web";
	
	private static final String notifica_settimanale_registrazione_atti = "notifica.settimanale.registrazione.atti";
	private static final String notifica_settimanale_chiusura_fascicoli = "notifica.settimanale.chiusura.fascicoli";
	private static final String notifica_settimanale_inserimento_professionisti_esterni = "notifica.settimanale.inserimento.professionisti.esterni";
	
	
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
	
	@Autowired
	private EmailService emailService;
	
    @Autowired  
    private MessageSource messageSource;
	
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void inviaNotificheWebMail() throws Throwable {
		
		Utente top = utenteDAO.leggiResponsabileTop();
		
		inviaMail(top);


		List<Utente> primiRiporti = utenteDAO.leggiCollaboratoriDiretti(top.getMatricolaUtil());
		for (Iterator<Utente> iterator = primiRiporti.iterator(); iterator.hasNext();) {
			Utente utente = iterator.next();
			
			inviaMail(utente);
		}
	}
	
	private void inviaMail(Utente utente) throws NoSuchMessageException, Throwable{
		
		List<NotificaWeb> notificheRegAtto = notificaWebDAO.leggiPerInvioMail(utente.getMatricolaUtil(),atto_inviato_altri_uffici);
		List<NotificaWeb> notificheChiusuraFasc = notificaWebDAO.leggiPerInvioMail(utente.getMatricolaUtil(),fascicolo_chiuso_indata);
		List<NotificaWeb> notificheRegProfEst = notificaWebDAO.leggiPerInvioMail(utente.getMatricolaUtil(),professionista_esterno_inserito);
		
		String lingua = "";
		
		try {
			lingua = utente.getRUtenteGruppos().iterator().next().getGruppoUtente().getLang();
		} catch (Exception e) {
			lingua="IT";
		}
		
		
		Locale l = new Locale(lingua.toLowerCase(), lingua.toUpperCase());
		StringBuilder sb = new StringBuilder();
		
		boolean sendMail = false;
		
		if (notificheRegAtto != null && !notificheRegAtto.isEmpty()){
			
			String[] nNotifiche = {notificheRegAtto.size()+""};

			String Titolomsg = "<strong>" + messageSource.getMessage(notifica_settimanale_registrazione_atti,nNotifiche, l)+"</strong><br>";
			sb.append(Titolomsg);
			sb.append("<br>");

			for (Iterator<NotificaWeb> iterator = notificheRegAtto.iterator(); iterator.hasNext();) {
				NotificaWeb notificaWeb = iterator.next();
				
				String param = notificaWeb.getJsonParam();
				
				String msg = messageSource.getMessage(notificaWeb.getKeyMessage(), getArrayParams(param), l)+"<br>";
				sb.append(msg);
				notificaWeb.setDataInvioMail(new Date());
				notificaWebDAO.aggiorna(notificaWeb);
			}
			
			sendMail = true;
		}

		sb.append("<br>");
		sb.append("<br>");
		
		if (notificheChiusuraFasc != null && !notificheChiusuraFasc.isEmpty()){
			
			String[] nNotifiche = {notificheChiusuraFasc.size()+""};

			String Titolomsg = "<strong>" + messageSource.getMessage(notifica_settimanale_chiusura_fascicoli,nNotifiche, l)+"</strong><br>";
			sb.append(Titolomsg);
			sb.append("<br>");

			for (Iterator<NotificaWeb> iterator = notificheChiusuraFasc.iterator(); iterator.hasNext();) {
				NotificaWeb notificaWeb = iterator.next();
				
				String param = notificaWeb.getJsonParam();
				
				String msg = messageSource.getMessage(notificaWeb.getKeyMessage(), getArrayParams(param), l)+"<br>";
				sb.append(msg);

				notificaWeb.setDataInvioMail(new Date());
				notificaWebDAO.aggiorna(notificaWeb);
			}
			sendMail = true;
		}
		sb.append("<br>");
		sb.append("<br>");

		if (notificheRegProfEst != null && !notificheRegProfEst.isEmpty()){
			
			String[] nNotifiche = {notificheRegProfEst.size()+""};

			String Titolomsg = "<strong>" + messageSource.getMessage(notifica_settimanale_inserimento_professionisti_esterni,nNotifiche, l)+"</strong><br>";
			sb.append(Titolomsg);
			sb.append("<br>");

			for (Iterator<NotificaWeb> iterator = notificheRegProfEst.iterator(); iterator.hasNext();) {
				NotificaWeb notificaWeb = iterator.next();
				
				String param = notificaWeb.getJsonParam();
				
				String msg = messageSource.getMessage(notificaWeb.getKeyMessage(), getArrayParams(param), l)+"<br>";
				sb.append(msg);

				notificaWeb.setDataInvioMail(new Date());
				notificaWebDAO.aggiorna(notificaWeb);
			}
			sendMail = true;
		}

		if (sendMail){
			emailService.sendEmail(mbx, utente.getEmailUtil(), messageSource.getMessage(oggetto_invio_settimanale_notifiche_web, null, l),sb.toString());
		}
		
	}
	
	private String[] getArrayParams(String jParam){
		String[] params=null;
		
		if (jParam != null && jParam.indexOf("$")>0){
			StringTokenizer t = new StringTokenizer(jParam, "$");
			ArrayList<String> list = new ArrayList<>();
			while (t.hasMoreElements()) {
				String s = (String) t.nextElement();
				list.add(s);
			}
			String[] sa = new String[list.size()];
			params = list.toArray(sa);
		}
		return params;
	}



}
