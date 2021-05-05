package eng.la.controller;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.ArchivioProtocolloService;
import eng.la.business.NotificaPecService;
import eng.la.business.UtentePecService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.model.ArchivioProtocollo;
import eng.la.model.view.BaseView;
import eng.la.model.view.NotificaPecView;
import eng.la.model.view.ProtocolloView;
import eng.la.model.view.UtentePecView;
import eng.la.model.view.UtenteView;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("notificaPecController")
@SessionAttributes("notificaPecView")

public class NotificaPecController extends BaseController {

//	private static final String MODEL_VIEW_NOME = "notificaPecView";
	private static final String PAGINA_FORM_PEC = "notificaPec/formPecOp";
//	private static final Logger logger = Logger.getLogger(NotificaPecController.class);
	
	@Autowired
	private NotificaPecService notificaPecService;
	
	@Autowired
	private UtentePecService utentePecService;
	
	@Autowired
	private EmailNotificationService emailNotificationService;

	@Autowired
	private ArchivioProtocolloService archivioProtocolloService;

	@RequestMapping(value = "/notificaPec/annullaPec", method = RequestMethod.POST)
	public @ResponseBody void annullaPec(HttpServletRequest request, Locale locale) {
		try {
			Long id = Long.valueOf(request.getParameter("id") != null ? request.getParameter("id") : null);

			utentePecService.annullaPec(id);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/notificaPec/inviaAltriUffPec", method = RequestMethod.POST)
	public @ResponseBody void inviaAltriUffPec(HttpServletRequest request, Locale locale) {
		try {
			Long id = Long.valueOf(request.getParameter("id") != null ? request.getParameter("id") : null);

			String utenteAltriUff = request.getParameter("utenteAltriUff") != null ? request.getParameter("utenteAltriUff") : null;
			String emailAltriUff = request.getParameter("emailAltriUff") != null ? request.getParameter("emailAltriUff") : null;
			
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			
			UtentePecView utentePecView = utentePecService.leggi(id);
			
			emailNotificationService.inviaNotificaInvioAltriUfficiPec(utentePecView,
					locale.getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil(), utenteAltriUff, emailAltriUff);
			
			utentePecService.inviaAltriUffPec(id, utenteAltriUff, emailAltriUff);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/notificaPec/spostProtPec", method = RequestMethod.POST)
	public @ResponseBody void spostProtPec(HttpServletRequest request, Locale locale) {
		try {
			Long id = Long.valueOf(request.getParameter("id") != null ? request.getParameter("id") : null);
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			
			UtentePecView utentePecView = utentePecService.leggi(id);
			
			String unita;
			if(utenteView.getVo().getCodiceUnitaUtil()!=null){
				unita=utenteView.getVo().getCodiceUnitaUtil().substring(5);
			}else{
				unita="Nessuna";
			}
			
			String numProtocollo = archivioProtocolloService.generaNumeroProtocollo(utenteView.getVo(), utentePecView.getVo().getPecMittente(), unita, utentePecView.getVo().getPecOggetto(), "IN", utenteView.getVo(), locale);
			ProtocolloView archivioProtocolloView = archivioProtocolloService.leggi(numProtocollo);
			ArchivioProtocollo protocollo = archivioProtocolloView.getVo();
			
			String uuid = utentePecView.getVo().getUUId().replaceAll("\\{", "").replaceAll("\\}","");
			archivioProtocolloService.salvaDocumentoProtocolloDaUuid(uuid, protocollo, locale);
			
			utentePecService.spostProtPec(id);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/notificaPec/trasformaPec", method = RequestMethod.POST)
	public @ResponseBody void trasformaPec(HttpServletRequest request, Locale locale) {


		try {
			Long id = Long.valueOf(request.getParameter("id") != null ? request.getParameter("id") : null);
			UtenteView utenteConnesso = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			
			//invio mail a tutti gli operatori di segreteria
			emailNotificationService.inviaNotificaPecOperatoriSegreteria(id.longValue(), locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
			
			//inserimento in notifica_pec
			notificaPecService.inserisci(id);
			
			utentePecService.trasformaPec(id);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/notificaPec/processaPecOp", method = RequestMethod.POST)
	public String processaPecOp(NotificaPecView notificaPecView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {
			
			notificaPecService.marcaNotificaPecLetta(Long.valueOf(notificaPecView.getIdNotifica()), new Date());
			
			return PAGINA_FORM_PEC;
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/notificaPec/rifiutaPecOp", method = RequestMethod.POST)
	public String rifiutaPecOp(NotificaPecView notificaPecView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {
		
			notificaPecService.rifiuta(Long.valueOf(notificaPecView.getIdNotifica()), notificaPecView.getNoteOp());
			notificaPecService.cancellaAltre(Long.valueOf(notificaPecView.getIdNotifica()), Long.valueOf(notificaPecView.getIdUtentePec()));
			utentePecService.riportaPec(Long.valueOf(notificaPecView.getIdUtentePec()));
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/notificaPec/trasformaPecOp", method = RequestMethod.POST)
	public String trasformaPecOp(NotificaPecView notificaPecView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {
			
			notificaPecService.trasforma(Long.valueOf(notificaPecView.getIdNotifica()));
			notificaPecService.cancellaAltre(Long.valueOf(notificaPecView.getIdNotifica()), Long.valueOf(notificaPecView.getIdUtentePec()));
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale)
			throws Throwable {
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale)
			throws Throwable {
	}

}
