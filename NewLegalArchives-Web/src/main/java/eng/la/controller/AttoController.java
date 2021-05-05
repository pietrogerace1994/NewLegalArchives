package eng.la.controller;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eng.la.persistence.DocumentaleDdsDAOImpl;
import it.snam.ned.libs.dds.dtos.v2.Document;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

//import com.filenet.api.core.Document;

import eng.la.business.AttoService;
import eng.la.business.AutorizzazioneService;
import eng.la.business.NotificaWebService;
import eng.la.business.UtenteService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.business.websocket.WebSocketPublisher;
import eng.la.business.workflow.AttoWfService;
import eng.la.model.Atto;
import eng.la.model.CategoriaAtto;
import eng.la.model.Documento;
import eng.la.model.EsitoAtto;
import eng.la.model.Fascicolo;
import eng.la.model.NotificaWeb;
import eng.la.model.Societa;
import eng.la.model.StatoAtto;
import eng.la.model.Utente;
import eng.la.model.custom.AttoRicercaCustom;
import eng.la.model.rest.AttoRest;
import eng.la.model.rest.Event;
import eng.la.model.rest.RicercaAttoRest;
import eng.la.model.rest.RicercaUtilRest;
import eng.la.model.rest.StepWFRest;
import eng.la.model.rest.UtilRest;
import eng.la.model.view.AttoView;
import eng.la.model.view.AttoWfView;
import eng.la.model.view.AutorizzazioneView;
import eng.la.model.view.NotificaWebView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.audit.AuditInterceptor;
import eng.la.util.BASE64DecodedMultipartFile;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("attoController")
@SessionAttributes("attoView")
public class AttoController {
	private static final Logger logger = Logger.getLogger(AttoController.class);
	/**
	 * Inserimento della pagina di visualizzazione degli atti da validare
	 * @author MASSIMO CARUSO
	 */
	private static final String PAGINA_VALIDA_ATTO = "atto/validaAtto";
	
	private static final String PAGINA_RICERCA_ATTO = "atto/ricercaAtto";
	private static final String PAGINA_NUOVO_ATTO = "atto/creaAtto";
	private static final String PAGINA_VISUALIZZA_STAMPA = "atto/visualizzaAttoStampa";
	private static final String PAGINA_MODIFICA_ATTO = "atto/modificaAtto";
	private static final String PAGINA_AZIONI_ATTO = "atto/azioniAtto";
	private static final String PAGINA_AZIONI_ATTO_TRIVIEW = "atto/azioniAttoTriview";
	private static final String MODEL_VIEW_NOME = "attoView";
	
	@Autowired
	private EmailNotificationService emailNotificationService;

	public void setEmailNotificationService(EmailNotificationService emailNotificationService) {
		this.emailNotificationService = emailNotificationService;
	}

	@Autowired
	private NotificaWebService notificaWebService;

	@Autowired
	private AttoService attoService;

	@Autowired
	private AttoWfService attoWfService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	AutorizzazioneService autorizzazioneService;
 
	
	@RequestMapping(value = "/atto/registaAttoControlloUtenteIsPresente", method = RequestMethod.POST, produces = "text/html")
	public @ResponseBody String avviaWflowControlloUtenteIsPresente(HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {


		// Registra
		String html="KO";	
		String matricolaUtenteAssegnatario = "";
		long idAtto = 0L;

		if (request.getParameter("idAtto") != null && !request.getParameter("idAtto").trim().equals("")) {
			idAtto = new Long(request.getParameter("idAtto").toString()).longValue();
			
			Atto atto = attoService.getAtto(idAtto);
			matricolaUtenteAssegnatario = atto.getDestinatario();
			UtenteView utenteAssegnatario = utenteService.leggiUtenteDaMatricola(matricolaUtenteAssegnatario);
			
			
			String languages =(locale.getLanguage().toUpperCase()!=null && !locale.getLanguage().trim().equals(""))?locale.getLanguage().toUpperCase():"IT";
			String[][] lang=new String[2][8];
			
			lang[0][0]="Contollo Utente Assente";
			lang[0][1]="RICHIEDI REGISTRAZIONE ATTO";
			lang[0][2]="Seleziona un assegnatario presente";
			
		 
			lang[1][0]="Control User Away";
			lang[1][1]="REQUEST REGISTRATION ACT";
			lang[1][2]="Select an assignee present";
			

			
			int lg_=0;
			if(languages.equalsIgnoreCase("IT"))
				lg_=0;
			if(languages.equalsIgnoreCase("EN"))
				lg_=1;
			
			
			
			
			
			if (utenteAssegnatario != null && utenteAssegnatario.getVo()!=null && utenteAssegnatario.getVo().getAssente().equals("F")) {

				
				
				 html="<div class='modal fade' id='modal-registra-atto' tabindex='-1' role='dialog' aria-hidden='true'>" +
							"<div class='modal-dialog modal-default' style='width:400px;'>" +
							"<div class='modal-content' style='border-radius: 12px;'>" +
							"<div class='modal-header' style='padding: 15px 26px;background: #66926c;border-radius: 10px 10px 0px 0px;'>" +
							"<h4 class='modal-title' style='color:#fff;font-size:14px;'>"+lang[lg_][0]+"  </h4>" +
							"</div>" +
							"<div class='modal-body'>" +
							"<fieldset>" +
							"<div id='info-message' style='width:100%;color:#000;min-height:100px;font-size:16px;font-weight:bold;padding-top: 30px;text-align:center;'>" ;
					
				 html+="<button type='button' class='btn btn-success' style='text-align:center' onclick=\"registraAttoControlloUtenteIsPresente('"+idAtto+"','0')\" >"+lang[lg_][1]+"</button>";
					
				 html+=	"</div>" +
								"</fieldset>" +
								"<div class='modal-footer'>" +
								"<button type='button' class='btn btn-warning' style='float:right;' data-dismiss='modal'>Close</button>" +
								"</div>" +
								"</div>" +
								"</div>" +
								"</div>" +
								"</div>";

				
				 
				
			return html;
			
			
			}
			else if (utenteAssegnatario != null && utenteAssegnatario.getVo()!=null && utenteAssegnatario.getVo().getAssente().equals("T")) {
				
				List<UtenteView> utentiPresenti=this.getAssegnatarioNonAssente(utenteAssegnatario);
				 	
				 html="<div class='modal fade' id='modal-registra-atto' tabindex='-1' role='dialog' aria-hidden='true'>" +
						"<div class='modal-dialog modal-default' style='width:400px;'>" +
						"<div class='modal-content' style='border-radius: 12px;'>" +
						"<div class='modal-header' style='padding: 15px 26px;background: #66926c;border-radius: 10px 10px 0px 0px;'>" +
						"<h4 class='modal-title' style='color:#fff;font-size:14px;'>"+lang[lg_][0]+" </h4>" +
						"</div>" +
						"<div class='modal-body'>" +
						"<fieldset>" +
						"<div id='info-message' style='width:100%;color:#000;min-height:100px;font-size:16px;font-weight:bold;padding-top: 30px;text-align:center;'>" ;
				 html+="<label style='padding:10px;text-align:center;width:100%;'>"+lang[lg_][2]+"</label>";
				 html+="<select id='assegnPresente' name='assegnPresente' style='width:80%;'>";
				for(UtenteView  u:utentiPresenti){
				 html+="<option value='"+u.getVo().getMatricolaUtil()+"'>";
				 html+=u.getVo().getNominativoUtil();
				 html+="</option>";
				}
				 html+="</select>";
				 html+="<div class='col-md-12' style='padding:20px;text-align:center;'><button type='button' class='btn btn-success' style='text-align:center' onclick=\"registraAttoAssPresent('"+idAtto+"')\" >"+lang[lg_][1]+"</button></div>";
					
				 html+=	"</div>" +
							"</fieldset>" +
							"<div class='modal-footer'>" +
							"<button type='button' class='btn btn-warning' style='float:right;' data-dismiss='modal'>Close</button>" +
							"</div>" +
							"</div>" +
							"</div>" +
							"</div>" +
							"</div>";
			}
			  
			}

		 

		return html;
	}
	
	
	
	
	@RequestMapping(value = "/atto/registaAtto", method = RequestMethod.POST, produces = "text/html")
	public @ResponseBody String avviaWflow(HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {



		UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		String userIdUtil = "";
		userIdUtil = utenteView.getVo().getUseridUtil();
		String matricolaUtenteAssegnatario = "";
		long idAtto = 0L;

		boolean esito = false;
		if (request.getParameter("idAtto") != null && !request.getParameter("idAtto").trim().equals("")) {
			idAtto = new Long(request.getParameter("idAtto").toString());

			Atto atto = attoService.getAtto((long) idAtto);
			matricolaUtenteAssegnatario = atto.getDestinatario();
			if (atto != null)
				esito = attoWfService.avviaWorkflow(atto.getId(), matricolaUtenteAssegnatario, userIdUtil);
			if (esito) {
				// invio la notifica
				UtenteView utenteAssegnatario = utenteService.leggiUtenteDaMatricola(matricolaUtenteAssegnatario);
				if (utenteAssegnatario != null) {

					//**************************
					 
					// se assegnatario risulta assente > avanzo il wf

						UtenteView utenteAssPresente =null;	
					if (request.getParameter("assegnPresente") != null && !request.getParameter("assegnPresente").trim().equals("0")) {
						
						AttoWfView attoWf= attoWfService.leggiWorkflowInCorso(idAtto);	
						
						utenteAssPresente	=utenteService.leggiUtenteDaMatricola(request.getParameter("assegnPresente"));
						
						if(utenteAssPresente!=null && utenteAssPresente.getVo()!=null){
							esito=attoWfService.avanzaWorkflow(attoWf.getVo().getId(), utenteAssPresente.getVo().getMatricolaUtil(), utenteAssegnatario.getVo().getUseridUtil());
							if(esito)
							utenteAssegnatario=utenteAssPresente;
						}
					
					}
					
					//**************************
					
					StepWFRest stepSuccessivo = new StepWFRest();
					stepSuccessivo.setId(0);
					/*
					 * DateFormat df = new
					 * SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); //recupero il
					 * numero di attivit� pendenti List<StepWfView> steps =
					 * stepWfService.leggiAttivitaPendenti(
					 * matricolaUtenteAssegnatario,
					 * CostantiDAO.LINGUA_ITALIANA); AttoWfView wf =
					 * attoWfService.leggiWorkflowInCorso(atto.getId());
					 * 
					 * StepWfView newStep =
					 * stepWfService.leggiStepCorrente(wf.getVo().getId(),
					 * CostantiDAO.REGISTRAZIONE_ATTO); String codiceLingua =
					 * newStep.getVo().getConfigurazioneStepWf().
					 * getCodGruppoLingua(); ConfigurazioneStepWfView
					 * configurazioneTradottaNew =
					 * configurazioneStepWfService.leggiConfigurazioneTradotta(
					 * codiceLingua, locale.getLanguage().toUpperCase());
					 * 
					 * 
					 * stepSuccessivo.setDescLinguaCorrente(
					 * configurazioneTradottaNew.getVo().getDescrStateTo());
					 * stepSuccessivo.setIdAtto(atto.getId());
					 * stepSuccessivo.setIdAttoWf(wf.getVo().getId());
					 * stepSuccessivo.setDataCreazione(df.format(newStep.getVo()
					 * .getDataCreazione()));
					 * stepSuccessivo.setDescLinguaCorrente(
					 * configurazioneTradottaNew.getVo().getDescrStateTo());
					 * stepSuccessivo.setNoteSpecifiche(CostantiDAO.ATTO + " " +
					 * atto.getNumeroProtocollo());
					 * stepSuccessivo.setFascicoloConValore(StringUtils.EMPTY);
					 * stepSuccessivo.setId(newStep.getVo().getId());
					 * stepSuccessivo.setCodice(CostantiDAO.REGISTRAZIONE_ATTO);
					 * stepSuccessivo.setMotivoRifiutoStepPrecedente(newStep.
					 * getVo().getMotivoRifiutoStepPrecedente());
					 * stepSuccessivo.setNumeroStep(steps.size());
					 */

					Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE,
							stepSuccessivo, utenteAssegnatario.getVo().getUseridUtil());
					WebSocketPublisher.getInstance().publishEvent(event);
				}
				// invio la e-mail
				try {
					emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.ATTO, atto.getId(),
							locale.getLanguage().toUpperCase(), userIdUtil);
				} catch (Exception e) {
					System.out.println("Errore in invio e-mail" + e);
				}
			}

		}

		return new Boolean(esito).toString();
	}

	@RequestMapping(value = "/atto/riportaBozza", method = RequestMethod.POST, produces = "text/html")
	public @ResponseBody String riportaInBozzaWflow(HttpServletRequest request, HttpServletResponse respons,
			Model model, Locale locale) throws Throwable {



		UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		String userIdUtil = "";
		userIdUtil = utenteView.getVo().getUseridUtil();

		long idAtto = 0L;

		boolean esito = false;
		if (request.getParameter("idAtto") != null && !request.getParameter("idAtto").trim().equals("")) {
			idAtto = new Long(request.getParameter("idAtto").toString());

			Atto atto = attoService.getAtto((long) idAtto);

			if (atto != null) {
				// recupero l'eventuale utente corrente del workflow per
				// notificargli la rimozione dell'incarico
				UtenteView assegnatario = utenteService.leggiAssegnatarioWfAtto((long) idAtto);
				esito = attoWfService.riportaInBozzaWorkflow(atto.getId(), userIdUtil);
				if (esito && assegnatario != null) {
					// invio la notifica
					StepWFRest stepSuccessivo = new StepWFRest();
					stepSuccessivo.setId(0);
					Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE,
							stepSuccessivo, assegnatario.getVo().getUseridUtil());
					WebSocketPublisher.getInstance().publishEvent(event);
				}
				// invio la e-mail
				try {
					emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.ATTO, atto.getId(),
							locale.getLanguage().toUpperCase(), userIdUtil);
				} catch (Exception e) {
					System.out.println("Errore in invio e-mail" + e);
				}

			}

		}

		return new Boolean(esito).toString();
	}

	// DISCARD WORKLOW
	@RequestMapping(value = "/atto/discardStep", method = RequestMethod.POST, produces = "text/html")
	public @ResponseBody String discardWflow(HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {



		UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		String userIdUtil = "";
		userIdUtil = utenteView.getVo().getUseridUtil();

		long idAtto = 0L;

		boolean esito = false;
		if (request.getParameter("idAtto") != null && !request.getParameter("idAtto").trim().equals("")) {
			idAtto = new Long(request.getParameter("idAtto").toString());

			Atto atto = attoService.getAtto((long) idAtto);

			if (atto != null) {
				// recupero l'eventuale utente corrente del workflow per
				// notificargli la rimozione dell'incarico
				UtenteView assegnatarioOld = utenteService.leggiAssegnatarioWfAtto((long) idAtto);

				esito = attoWfService.discardStep(atto.getId(), userIdUtil);
				if (esito) {
					// recupero l'eventuale nuovo utente corrente del workflow
					// per notificargli l'assegnazione dell'incarico
					UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfAtto((long) idAtto);
					if (assegnatarioOld != null) {
						// invio la notifica
						StepWFRest stepPrecedente = new StepWFRest();
						stepPrecedente.setId(0);
						Event eventOld = WebSocketPublisher.getInstance().createEvent(
								Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepPrecedente,
								assegnatarioOld.getVo().getUseridUtil());
						WebSocketPublisher.getInstance().publishEvent(eventOld);

					}
					if (assegnatarioNew != null) {
						// invio la notifica
						StepWFRest stepSuccessivo = new StepWFRest();
						stepSuccessivo.setId(0);
						Event eventNew = WebSocketPublisher.getInstance().createEvent(
								Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo,
								assegnatarioNew.getVo().getUseridUtil());
						WebSocketPublisher.getInstance().publishEvent(eventNew);

					}
				}
			}

		}

		return new Boolean(esito).toString();
	}

	@RequestMapping(value = "/atto/assegnaFascicoloAtto", method = RequestMethod.POST, produces = "text/html")
	public @ResponseBody String assegnaFascicoloAtto(@RequestParam("idAtto") long idAtto,
			@RequestParam("idFascicolo") long idFascicolo, HttpServletRequest request, HttpServletResponse respons,
			Model model, Locale locale) throws Throwable {



		Fascicolo fascicolo = attoService.getFascicolo(idFascicolo);
		Atto atto = attoService.getAtto(idAtto);

		if (fascicolo != null && atto != null) {
			AttoView attoView = new AttoView();
			atto.setFascicolo(fascicolo);
			attoView.setVo(atto);
			attoService.modifica(attoView);
			return "OK";
		} else {

			return "KO";
		}
	}

	@RequestMapping(value = "/atto/ricerca")
	public String ricercaAtto(AttoView attoView, HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {
		
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
       
		
		if (attoView == null)
			attoView = new AttoView();

		try {
			// matricolaUtil
			List<Utente> listaDestinatario = attoService.getGcDestinatario();
			model.addAttribute("listaDestinatario", listaDestinatario);

			List<Societa> listaSocieta = attoService.getlistSocieta();
			String lingua = locale.getLanguage().toUpperCase();
			List<CategoriaAtto> listaCategorie = attoService.listaCategoriaAtto(lingua);
			model.addAttribute("attoView", attoView);
			model.addAttribute("listaSocieta", listaSocieta);
			model.addAttribute("listaCategorie", listaCategorie);

			if (request.getParameter("export") != null && !request.getParameter("export").trim().equals("")) {
				downloadAtti(request, respons, model, locale);
				return null;
			} else {

				List<Fascicolo> listaFascicolo = (List<Fascicolo>) attoService.cercaFascicolo(null, 1000000000, 1);

				model.addAttribute("listaFascicolo", listaFascicolo);

			}

		} catch (Exception e) {
		}

		model.addAttribute("attoView", attoView);

		return PAGINA_RICERCA_ATTO;
	}

	/**
	 * Servizio di ricerca atti da validare da parte della segreteria
	 * @author MASSIMO CARUSO
	 * @param attoView
	 * @param request
	 * @param respons
	 * @param model
	 * @param locale
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/atto/valida")
	public String ricercaAttoDaValidare(AttoView attoView, HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {
		
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
       
		
		if (attoView == null)
			attoView = new AttoView();

		try {
			// matricolaUtil
			List<Utente> listaDestinatario = attoService.getGcDestinatario();
			model.addAttribute("listaDestinatario", listaDestinatario);

			List<Societa> listaSocieta = attoService.getlistSocieta();
			Societa to_find_soc = null;
			for(Societa soc : listaSocieta){
				if(soc.getNome().equalsIgnoreCase("----------")){
					to_find_soc = soc;
					break;
				}
			}
			listaSocieta = new ArrayList<Societa>();
			listaSocieta.add(to_find_soc);
			String lingua = locale.getLanguage().toUpperCase();
			List<CategoriaAtto> listaCategorie = attoService.listaCategoriaAtto(lingua);
			CategoriaAtto to_find_cat = null;
			for(CategoriaAtto cat : listaCategorie){
				if(cat.getNome().equalsIgnoreCase("-----")){
					to_find_cat = cat;
					break;
				}
			}
			listaCategorie = new ArrayList<CategoriaAtto>();
			listaCategorie.add(to_find_cat);
			model.addAttribute("attoView", attoView);
			model.addAttribute("listaSocieta", listaSocieta);
			model.addAttribute("listaCategorie", listaCategorie);

			if (request.getParameter("export") != null && !request.getParameter("export").trim().equals("")) {
				downloadAtti(request, respons, model, locale);
				return null;
			} 
			else {

				List<Fascicolo> listaFascicolo = (List<Fascicolo>) attoService.cercaFascicolo(null, 1000000000, 1);

				model.addAttribute("listaFascicolo", listaFascicolo);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("attoView", attoView);

		return PAGINA_VALIDA_ATTO;
	}
	
	@RequestMapping(value = "/atto/affinaricerca", method = RequestMethod.POST)
	public String affinaRicercaAtto(HttpServletRequest request, Model model, Locale locale) throws Throwable {

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		
		AttoView attoView = new AttoView();
		List<Atto> listaAtti = null;

		String dal = request.getParameter("dataDal");
		String al = request.getParameter("dataAl");
		String numeroProtocollo = request.getParameter("numeroProtocollo");
		boolean flagAltriUffici = false;

		String idCat = request.getParameter("idCategoriaAtto");

		long idCategoriaAtto = new Long(idCat != null && !idCat.toString().equals("") ? idCat : "0").longValue();
		String idSoc = request.getParameter("idSocieta");

		long idSocieta = new Long(idSoc != null && !idSoc.toString().equals("") ? idSoc : "0").longValue();

		String tipoAtto = request.getParameter("dataAl");

		listaAtti = attoService.getCercaAtti(dal, al, numeroProtocollo, idCategoriaAtto, idSocieta, tipoAtto, 1000, 1,"desc", flagAltriUffici, false);

		String lingua = locale.getLanguage().toUpperCase();

		List<AttoRicercaCustom> customsList = new ArrayList<AttoRicercaCustom>();
		if (listaAtti != null)
			for (Atto a : listaAtti) {
				AttoRicercaCustom arCustom = new AttoRicercaCustom();
				arCustom.setCategoriaAtto(a.getCategoriaAtto().getNome());
				arCustom.setId(a.getId());
				arCustom.setNumeroProtocollo(a.getNumeroProtocollo());
				arCustom.setOwner(a.getOwner());
				arCustom.setSocieta(a.getSocieta().getRagioneSociale());
				arCustom.setStatoAttoCodLingua(a.getStatoAtto().getCodGruppoLingua());
				StatoAtto statoAtto = attoService.getStatoAttoPerLingua(lingua, a.getStatoAtto().getCodGruppoLingua());
				if (statoAtto != null) {
					arCustom.setStatoAtto(statoAtto.getDescrizione());
				} else {

					arCustom.setStatoAtto(a.getStatoAtto().getDescrizione());

				}
				if (a.getOwner() != null && !a.getOwner().trim().equals("")) {
					Utente utente = attoService.leggiUtenteDaUserId(a.getOwner());
					arCustom.setUnitaLegale(utente != null ? utente.getDescrUnitaUtil() : "");
				} 
				customsList.add(arCustom);

			}

		List<Societa> listaSocieta = attoService.getlistSocieta();

		List<CategoriaAtto> listaCategorie = attoService.listaCategoriaAtto(lingua);
		model.addAttribute("attoView", attoView);
		model.addAttribute("listaSocieta", listaSocieta);
		model.addAttribute("listaCategorie", listaCategorie);

		model.addAttribute("listaAttiCustom", customsList);

		model.addAttribute("attoView", attoView);

		return PAGINA_RICERCA_ATTO;
	}

	@RequestMapping(value = "/atto/caricaAzioniAtto", method = RequestMethod.POST)
	public String caricaAzioniAtto(@RequestParam("idAtto") long idAtto, @RequestParam("statoCodice") String statoCodice,
			HttpServletRequest req, Locale locale) {
		// engsecurity VA
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(req);
        //removeCSRFToken(request);
		try {

			UtenteView utenteView = (UtenteView) req.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if (utenteView != null && utenteView.isAmministratore()) {
				req.setAttribute("amministratore", "yes");
			} else {
				req.setAttribute("amministratore", "not");
			}

			AutorizzazioneView autorizzazioneView = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(idAtto,
					CostantiDAO.ATTO.toUpperCase());

			if (autorizzazioneView != null && autorizzazioneView.getVo() != null
					&& autorizzazioneView.getVo().getTipoAutorizzazione() != null				
					&& autorizzazioneView.getVo().getTipoAutorizzazione().getCodGruppoLingua().contains("W")) {  //leg_arc
				req.setAttribute("modifica", "yes");
			} else {
				req.setAttribute("modifica", "not");
			}
			
			Atto atto = attoService.getAtto((long) idAtto);
			String idFascicolo="";
			if(atto!=null && atto.getFascicolo()!=null){
				idFascicolo=""+atto.getFascicolo().getId();	
			}
			req.setAttribute("idFascicolo", idFascicolo);
			req.setAttribute("idAtto", idAtto);
			req.setAttribute("statoCodice", statoCodice);
			/**
			 * Aggiunta del flag valida nella richiesta per la gestione
			 * degli atti da validare
			 * @author MASSIMO CARUSO
			 */
			if(atto.getStatoAtto().getCodGruppoLingua().equalsIgnoreCase("VAL"))
				if(atto.getDestinatario().equals("0000000000"))
					req.setAttribute("validato","false");
				else
					req.setAttribute("validato","true");

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return PAGINA_AZIONI_ATTO;
	}

	@RequestMapping(value = "/atto/caricaAzioniAttoTriview", method = RequestMethod.POST)
	public String caricaAzioniAttoTriview(@RequestParam("idAtto") long idAtto, HttpServletRequest req, Locale locale) {
		// engsecurity VA
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(req);
        //removeCSRFToken(request);
		try {

			/*
			 * metodo nuovo per il menu triview
			 */
 
			req.setAttribute("idAtto", idAtto);
			 

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return PAGINA_AZIONI_ATTO_TRIVIEW;
	}

	@RequestMapping(value = "/atto/cercafascicoli", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaUtilRest cercaFascicoli(HttpServletRequest request, Model model, Locale locale)
			throws Throwable {



		Integer limit = new Integer((request.getParameter("limit") != null && !request.getParameter("limit").equals(""))
				? request.getParameter("limit") : "20");
		if (limit == null || (limit != null && limit.intValue() < 20))
			limit = new Integer(20);
		Integer current = new Integer(
				(request.getParameter("offset") != null && !request.getParameter("offset").equals(""))
						? request.getParameter("offset") : "0");
		if (current == null || (current != null && current.intValue() < 0))
			current = new Integer(0);

		String search = "";
		if (request.getParameter("search") != null) {

			search = request.getParameter("search");
		}

		List<Fascicolo> listaFascicolo = (List<Fascicolo>) attoService.cercaFascicolo(search, limit.intValue(),
				current.intValue());

		Long totaleFascicoli = (Long) attoService.countFascicolo(search);

		RicercaUtilRest ricercaUtilRest = new RicercaUtilRest();
		ricercaUtilRest.setTotal(totaleFascicoli);
		ricercaUtilRest.setRows(convertUtilRest(listaFascicolo));

		return ricercaUtilRest;

	}

	

 
	public String downloadAtti(HttpServletRequest request,HttpServletResponse response, Model model,Locale locale) throws Throwable {
	
		List<Atto> listaAtti = null;

		String dal = null;
		String al = null;
		String numeroProtocollo = null;
		String idCat = null;
		String tipoAtto = null;
		boolean flagAltriUffici=false;
		boolean flagValida = false;
		 
		if (request.getParameter("dataDal") != null) {
			dal = request.getParameter("dataDal");
			if (dal.trim().equals("0") || dal.trim().equals(""))
				dal = null;
		}
		if (request.getParameter("dataAl") != null) {
			al = request.getParameter("dataAl");
			if (al.trim().equals("0") || al.trim().equals(""))
				al = null;
		}

		if (request.getParameter("numeroProtocollo") != null) {
			numeroProtocollo = request.getParameter("numeroProtocollo");
			if (numeroProtocollo.trim().equals("0") || numeroProtocollo.trim().equals(""))
				numeroProtocollo = null;
		}

		if (request.getParameter("idCategoriaAtto") != null) {
			idCat = request.getParameter("idCategoriaAtto");
			if (idCat.trim().equals("0") || idCat.trim().equals(""))
				idCat = null;
		}
		if (request.getParameter("tipoAtto") != null) {
			tipoAtto = request.getParameter("tipoAtto");
			if (tipoAtto.trim().equals("0") || tipoAtto.trim().equals(""))
				tipoAtto = null;
		}

		long idCategoriaAtto = new Long((idCat != null && idCat.trim().length() > 0) ? idCat : "0").longValue();

		String idSoc = null;

		if (request.getParameter("idSocieta") != null) {
			idSoc = request.getParameter("idSocieta");
			if (idSoc.trim().equals(""))
				idSoc = "0";
		}
		
		if (request.getParameter("flagAltriUffici") != null) {
			flagAltriUffici = request.getParameter("flagAltriUffici").equals("true")?true:false;
		}
		
		if(request.getParameter("valida") != null) {
			flagValida = request.getParameter("valida").equals("true")? true : false;
		}

 
		long idSocieta = new Long((idSoc != null && idSoc.trim().length() > 0) ? idSoc : "0").longValue();
 
		try {

			listaAtti = attoService.getCercaAtti(dal, al, numeroProtocollo, idCategoriaAtto, idSocieta, tipoAtto,
					900000, 1, "desc", flagAltriUffici, flagValida);

			attoService.exportExcell(listaAtti, response);

		} catch (Exception e) {
			 
		}
		return null;
	}


	
	
	@RequestMapping(value = "/atto/cerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody  RicercaAttoRest cercaAtto(AttoView attoView, HttpServletRequest request, Model model,
			Locale locale) throws Throwable {


		if (attoView == null)
			attoView = new AttoView(); 
		
		String lingua = locale.getLanguage().toUpperCase();

		int numElementiPerPagina = request.getParameter("limit") == null ? 108 : NumberUtils.toInt(request.getParameter("limit"));

		String offset = request.getParameter("offset");
		int numeroPagina = offset == null || offset.equals("0") ? 1
				: (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;

		List<Atto> listaAtti = null;

		String dal = null;
		String al = null;
		String numeroProtocollo = null;
		String idCat = null;
		String tipoAtto = null;
		String order = null;
		boolean flagAltriUffici=false;
		boolean flagValida = false;
		
		if (request.getParameter("dataDal") != null) {
			dal = request.getParameter("dataDal");
			if (dal.trim().equals("0") || dal.trim().equals(""))
				dal = null;
		}
		if (request.getParameter("dataAl") != null) {
			al = request.getParameter("dataAl");
			if (al.trim().equals("0") || al.trim().equals(""))
				al = null;
		}

		if (request.getParameter("numeroProtocollo") != null) {
			numeroProtocollo = request.getParameter("numeroProtocollo");
			if (numeroProtocollo.trim().equals("0") || numeroProtocollo.trim().equals(""))
				numeroProtocollo = null;
		}

		if (request.getParameter("idCategoriaAtto") != null) {
			idCat = request.getParameter("idCategoriaAtto");
			if (idCat.trim().equals("0") || idCat.trim().equals(""))
				idCat = null;
		}
		if (request.getParameter("tipoAtto") != null) {
			tipoAtto = request.getParameter("tipoAtto");
			if (tipoAtto.trim().equals("0") || tipoAtto.trim().equals(""))
				tipoAtto = null;
		}

		long idCategoriaAtto = new Long((idCat != null && idCat.trim().length() > 0) ? idCat : "0").longValue();

		String idSoc = null;

		if (request.getParameter("idSocieta") != null) {
			idSoc = request.getParameter("idSocieta");
			if (idSoc.trim().equals(""))
				idSoc = "0";
		}

		if (request.getParameter("order") != null) {
			order = request.getParameter("order");
			if (order.trim().equals(""))
				order = "desc";
		}
		
		if (request.getParameter("flagAltriUffici") != null) {
			flagAltriUffici = request.getParameter("flagAltriUffici").equals("true")?true:false;
		}
		
		/**
		 * Aggiunta del flag per distinguere un operazione di ricerca fatta per atti da validare
		 * @author MASSIMO CARUSO
		 */
		if(request.getParameter("valida") != null){
			flagValida = (request.getParameter("valida").equals("true"))? true : false;
		}		
		
		long idSocieta = new Long((idSoc != null && idSoc.trim().length() > 0) ? idSoc : "0").longValue();
 
		
		try {

			listaAtti = attoService.getCercaAtti(dal, al, numeroProtocollo, idCategoriaAtto, idSocieta, tipoAtto,
					numElementiPerPagina, numeroPagina, order, flagAltriUffici,flagValida);
			

			Long totale = attoService.countAtti(dal, al, numeroProtocollo, idCategoriaAtto, idSocieta, tipoAtto, flagAltriUffici, flagValida);

			
			RicercaAttoRest ricercaAttoRest = new RicercaAttoRest();

			ricercaAttoRest.setTotal(totale.longValue());

			ricercaAttoRest.setRows(convertAttoToRest(listaAtti, lingua));
			return ricercaAttoRest;

		} catch (Exception e) {
			e.printStackTrace();
			return new RicercaAttoRest();
		}

	}

	private List<AttoRest> convertAttoToRest(List<Atto> list, String lingua) throws Throwable {
		List<AttoRest> lRest = new ArrayList<AttoRest>();
		for (Atto a : list) {
			AttoRest r = new AttoRest();
			r.setCategoriaAtto(a.getCategoriaAtto().getNome());

			r.setId(a.getId());
			Utente utente=null;
			if (a.getOwner() != null && !a.getOwner().trim().equals("")) {
				 utente = attoService.leggiUtenteDaUserId(a.getOwner());
			
			}
			
			r.setUnitaLegale(utente != null ? utente.getDescrUnitaUtil() : "");
			r.setOwner(utente!=null ? utente.getNominativoUtil():"");
			
			r.setNumeroProtocollo(a.getNumeroProtocollo());
			r.setSocieta(a.getSocieta().getRagioneSociale());
			
			if(a.getTipoAtto()!=null && a.getTipoAtto().length()>39){
			r.setTipoAtto(a.getTipoAtto().substring(0, 35)+"...");
			}else{
			r.setTipoAtto(a.getTipoAtto());	
			}
			//
			r.setStatoCodice(a.getStatoAtto().getCodGruppoLingua());
			StatoAtto statoAtto = attoService.getStatoAttoPerLingua(lingua, a.getStatoAtto().getCodGruppoLingua());
			if (statoAtto != null) {
				r.setStatoAtto(statoAtto.getDescrizione());
			} else {
				r.setStatoAtto(a.getStatoAtto().getDescrizione());
			}

			
			String titleAlt = "-";
			if (a.getStatoAtto().getCodGruppoLingua().equalsIgnoreCase("ADR")
					|| a.getStatoAtto().getCodGruppoLingua().equalsIgnoreCase("APIC")
					|| a.getStatoAtto().getCodGruppoLingua().equalsIgnoreCase("APICL")) {
				UtenteView utenteV = utenteService.leggiAssegnatarioWfAtto(a.getId());
				titleAlt = utenteV.getVo().getNominativoUtil();
			}

			r.setAzioni("<p id='action-atto-" + a.getId() + "' alt='" + titleAlt + "' title='" + titleAlt + "'></p>");
			lRest.add(r);

		}
		return lRest;

	}

	private List<UtilRest> convertUtilRest(List<Fascicolo> list) throws Throwable {
		List<UtilRest> lRest = new ArrayList<UtilRest>();
		for (Fascicolo a : list) {
			UtilRest r = new UtilRest();
			r.setId(a.getId());
			r.setNome(a.getNome());
			r.setAzioni("<p id='action-atto-" + a.getId() + "'></p>");
			lRest.add(r);
		}
		return lRest;

	}

	@RequestMapping(value = "/atto/crea", method = RequestMethod.GET)
	public String creaAtto(AttoView attoView, HttpServletRequest request, Model model, Locale locale) throws Throwable {
		
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		if (attoView == null)
			attoView = new AttoView();
		UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		
		try {

			String creatoDaNome = "", userIdUtil = "";
			userIdUtil = utenteView.getVo().getUseridUtil();
			Utente utente = attoService.leggiUtenteDaUserId(userIdUtil);
			creatoDaNome = utente.getNominativoUtil();

			model.addAttribute("creatoDa", userIdUtil);
			model.addAttribute("creatoDaNome", creatoDaNome);
			Date date = new Date();
			model.addAttribute("dataCreazione", date);
			model.addAttribute("dataUltimaModifica", date);

			List<Societa> listaSocieta = attoService.getlistSocieta();
			String lingua = locale.getLanguage().toUpperCase();
			List<CategoriaAtto> listaCategorie = attoService.listaCategoriaAtto(lingua);
			List<EsitoAtto> listaEsitoAtto = attoService.listaEsitoAtto(lingua);
			model.addAttribute("attoView", attoView);
			model.addAttribute("listaSocieta", listaSocieta);
			model.addAttribute("listaCategorie", listaCategorie);
			model.addAttribute("listaEsitoAtto", listaEsitoAtto);
			List<Utente> listaDestinatario = attoService.getGcDestinatario();
			model.addAttribute("listaDestinatario", listaDestinatario);
			
			model.addAttribute("numeroProtocollo", "");
			
			
		} catch (Exception e) {
		}

		return PAGINA_NUOVO_ATTO;
	}
	
	@RequestMapping(value = "/atto/creaDaPec", method = RequestMethod.GET)
	public String creaAttoDaPec(AttoView attoView, HttpServletRequest request, Model model, Locale locale) throws Throwable {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		if (attoView == null)
			attoView = new AttoView();
		UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

		try {

			String creatoDaNome = "", userIdUtil = "";
			userIdUtil = utenteView.getVo().getUseridUtil();
			Utente utente = attoService.leggiUtenteDaUserId(userIdUtil);
			creatoDaNome = utente.getNominativoUtil();

			model.addAttribute("creatoDa", userIdUtil);
			model.addAttribute("creatoDaNome", creatoDaNome);
			Date date = new Date();
			model.addAttribute("dataCreazione", date);
			model.addAttribute("dataUltimaModifica", date);

			List<Societa> listaSocieta = attoService.getlistSocieta();
			String lingua = locale.getLanguage().toUpperCase();
			List<CategoriaAtto> listaCategorie = attoService.listaCategoriaAtto(lingua);
			List<EsitoAtto> listaEsitoAtto = attoService.listaEsitoAtto(lingua);
			model.addAttribute("attoView", attoView);
			model.addAttribute("listaSocieta", listaSocieta);
			model.addAttribute("listaCategorie", listaCategorie);
			model.addAttribute("listaEsitoAtto", listaEsitoAtto);
			List<Utente> listaDestinatario = attoService.getGcDestinatario();
			model.addAttribute("listaDestinatario", listaDestinatario);
			
			model.addAttribute("numeroProtocollo", "");
			
			if(request.getParameter("uuidPec") != null){
				String uuidPec = request.getParameter("uuidPec");
				
				if((uuidPec.indexOf("{") == -1) 
						|| (uuidPec.indexOf("}") == -1) 
						|| (uuidPec.indexOf("/") == -1)
						|| (uuidPec.indexOf("\\") == -1)){
					return  "redirect:/errore.action";
				}else{
					model.addAttribute("uuidPec", request.getParameter("uuidPec"));
				}
			}
			
		} catch (Exception e) {
			return  "redirect:/errore.action";
		}

		return PAGINA_NUOVO_ATTO;
	}
	
	private String errorCreaAtto(HttpServletRequest request, Model model, Locale locale) throws Throwable {
		 
			AttoView attoView = new AttoView();
		UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

		try {

			String creatoDaNome = "", userIdUtil = "";
			userIdUtil = utenteView.getVo().getUseridUtil();
			Utente utente = attoService.leggiUtenteDaUserId(userIdUtil);
			creatoDaNome = utente.getNominativoUtil();

			model.addAttribute("creatoDa", userIdUtil);
			model.addAttribute("creatoDaNome", creatoDaNome);
			Date date = new Date();
			model.addAttribute("dataCreazione", date);
			model.addAttribute("dataUltimaModifica", date);

			List<Societa> listaSocieta = attoService.getlistSocieta();
			String lingua = locale.getLanguage().toUpperCase();
			List<CategoriaAtto> listaCategorie = attoService.listaCategoriaAtto(lingua);
			List<EsitoAtto> listaEsitoAtto = attoService.listaEsitoAtto(lingua);
			model.addAttribute("attoView", attoView);
			model.addAttribute("listaSocieta", listaSocieta);
			model.addAttribute("listaCategorie", listaCategorie);
			model.addAttribute("listaEsitoAtto", listaEsitoAtto);
			List<Utente> listaDestinatario = attoService.getGcDestinatario();
			model.addAttribute("listaDestinatario", listaDestinatario);
			
			model.addAttribute("numeroProtocollo", "");
			
			
		} catch (Exception e) {
		}

		return PAGINA_NUOVO_ATTO;
	}

	

	
	@RequestMapping(value = "/atto/visualizza", method = RequestMethod.GET)
	public String visualizzaAtto(@RequestParam("id") long id, @RequestParam("azione") String azione,AttoView attoView,
			Model model, Locale locale, HttpServletRequest request) throws Throwable {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		try{
		if (attoView == null)
			attoView = new AttoView();
		model.addAttribute("attoView", attoView);
		List<Societa> listaSocieta = attoService.getlistSocieta();
		String lingua = locale.getLanguage().toUpperCase();
		List<CategoriaAtto> listaCategorie = attoService.listaCategoriaAtto(lingua);
		List<EsitoAtto> listaEsitoAtto = attoService.listaEsitoAtto(lingua);
		model.addAttribute("listaSocieta", listaSocieta);
		model.addAttribute("listaCategorie", listaCategorie);
		model.addAttribute("listaEsitoAtto", listaEsitoAtto);
		List<Utente> listaDestinatario = attoService.getGcDestinatario();
		model.addAttribute("listaDestinatario", listaDestinatario);

		model.addAttribute("azione", azione);

		Atto atto = (Atto) attoService.getAtto(id);

		// Setto lo Stato per la lingua corrente
		if (atto != null) {
			StatoAtto statoAtto = null;
			if (atto.getStatoAtto() != null)
				statoAtto = attoService.getStatoAttoPerLingua(lingua, atto.getStatoAtto().getCodGruppoLingua());
			if (statoAtto != null)
				atto.setStatoAtto(statoAtto);

			model.addAttribute("atto", atto);
			String creatoDaNome = "";
			Utente utente = attoService.leggiUtenteDaUserId(atto.getCreatoDa());
			if (utente != null) {
				creatoDaNome = utente.getNominativoUtil();
				model.addAttribute("creatoDaNome", creatoDaNome);
			}
			

			AuditInterceptor auditInterceptor = (AuditInterceptor) SpringUtil.getBean("auditInterceptor"); 
			auditInterceptor.auditRead(atto);
		}
		if (atto == null)
			model.addAttribute("errorMessage", "errore.generico");
 
		return PAGINA_MODIFICA_ATTO;
		
		}catch (Exception ex) {
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}
	}

	@RequestMapping(value = "/atto/stampa", method = RequestMethod.GET)
	public String stampaAtto(@RequestParam("id") long id,AttoView attoView,
			Model model, Locale locale
			, HttpServletRequest request) throws Throwable {
		// engsecurity VA ??
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try{
		if (attoView == null)
			attoView = new AttoView();
		model.addAttribute("attoView", attoView);
		List<Societa> listaSocieta = attoService.getlistSocieta();
		String lingua = locale.getLanguage().toUpperCase();
		List<CategoriaAtto> listaCategorie = attoService.listaCategoriaAtto(lingua);
		model.addAttribute("listaSocieta", listaSocieta);
		model.addAttribute("listaCategorie", listaCategorie);
		List<Utente> listaDestinatario = attoService.getGcDestinatario();
		model.addAttribute("listaDestinatario", listaDestinatario);

		model.addAttribute("azione", "visualizza");

		Atto atto = (Atto) attoService.getAtto(id);

		// Setto lo Stato per la lingua corrente
		if (atto != null) {
			StatoAtto statoAtto = null;
			if (atto.getStatoAtto() != null)
				statoAtto = attoService.getStatoAttoPerLingua(lingua, atto.getStatoAtto().getCodGruppoLingua());
			if (statoAtto != null)
				atto.setStatoAtto(statoAtto);

			model.addAttribute("atto", atto);
			String creatoDaNome = "";
			Utente utente = attoService.leggiUtenteDaUserId(atto.getCreatoDa());
			if (utente != null) {
				creatoDaNome = utente.getNominativoUtil();
				model.addAttribute("creatoDaNome", creatoDaNome);
			}
			

			AuditInterceptor auditInterceptor = (AuditInterceptor) SpringUtil.getBean("auditInterceptor"); 
			auditInterceptor.auditRead(atto);
		}
		if (atto == null)
			model.addAttribute("errorMessage", "errore.generico");
		 
		return PAGINA_VISUALIZZA_STAMPA;
	 
		}catch (Exception ex) {
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}
	}
	
	
	
	@RequestMapping(value = "/atto/modifica", method = RequestMethod.POST)
	public String modificaAtto(AttoView attoView, HttpServletRequest request, Model model, Locale locale)
			throws Throwable {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);

		if (attoView == null)
			attoView = new AttoView();
		model.addAttribute("attoView", attoView);

		
		UtenteView utenteConnesso = (UtenteView) request.getSession()
				.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
	try{
		
		
		
		Atto atto = (Atto) attoService.getAtto(attoView.getIdAtto());
		if (attoView.getOperazioneCorrente().equalsIgnoreCase("inviaaltriuffici")) {
			atto.setEmailInvioAltriUffici(attoView.getEmailInvioAltriUffici());
			atto.setUnitaLegInvioAltriUffici(attoView.getUnitaLegInvioAltriUffici());
			atto.setUtenteInvioAltriUffici(attoView.getUtenteInvioAltriUffici());
			atto.setDataUltimaModifica(new Date());
			attoView.setVo(atto);

			StatoAtto statoAtto = (StatoAtto) attoService.getStatoAttoPerLingua("IT", "IAU");
			atto.setStatoAtto(statoAtto);

			attoService.modifica(attoView);

			// se la modifica � andata a buon fine invio la e-mail
			AttoView attoModificato = attoService.leggi(attoView.getIdAtto());

			if (attoModificato.getVo().getStatoAtto().getCodGruppoLingua()
					.equalsIgnoreCase(CostantiDAO.ATTO_STATO_INVIATO_ALTRI_UFFICI)) {
				try {
				
					// inviaEmailConAllegato
					BASE64DecodedMultipartFile	allegato =null;
				
				    String uuid=atto.getDocumento()==null?null:atto.getDocumento().getUuid();
				    if(uuid!=null && !uuid.equals("")){
					Document documento=attoService.leggiDocumentoUUID(uuid);

					if(documento!=null){
					byte[] bytes=attoService.leggiContenutoDocumentoUUID(uuid);
					
					//@@DDS allegato = new BASE64DecodedMultipartFile(bytes, documento.get_Name(), documento.get_Name(), documento.get_MimeType());
					allegato = new BASE64DecodedMultipartFile(bytes, documento.getContents().get(0).getContentsName(), documento.getContents().get(0).getContentsName(), documento.getContents().get(0).getContentsMimeType());
					}
				    }
					//Invio Email con allegato se presente	
					emailNotificationService.inviaNotificaInvioAltriUffici(attoView.getIdAtto(),
							locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil(),allegato);
				
					try {
						NotificaWebView notificaWeb = new NotificaWebView();
						notificaWeb.setEmailInvioAltriUffici(attoView.getEmailInvioAltriUffici());
						notificaWeb.setUnitaLegInvioAltriUffici(attoView.getUnitaLegInvioAltriUffici());
						notificaWeb.setUtenteInvioAltriUffici(attoView.getUtenteInvioAltriUffici());
						notificaWeb.setNumeroProtocollo(attoView.getNumeroProtocollo());
						
						Utente matricolaDest = utenteService.leggiUtenteDaMatricola(attoView.getVo().getDestinatario()).getVo();
						
						NotificaWeb notifica = new NotificaWeb();
						notifica.setDataNotifica(new Date());
						notifica.setKeyMessage("atto.inviato.altri.uffici");
						notifica.setJsonParam(notificaWeb.getJsonParamAltriUffici());
						notifica.setMatricolaMitt(utenteConnesso.getVo());
						notifica.setMatricolaDest(matricolaDest);
						notificaWeb.setVo(notifica);
						
						notificaWebService.inserisciAttoInviatoAdAltriUffici(notificaWeb);
					} catch (Exception e) {
						System.out.println("Errore in invio notifica" + e);
					}

				} catch (Exception e) {
					System.out.println("Errore in invio e-mail" + e);
				}
			}
		} else if (attoView.getOperazioneCorrente().equalsIgnoreCase("modifica")) {
			
			List<String> listError = new ArrayList<String>();
			if (isNotValid(attoView, listError,locale.getLanguage().toUpperCase())) {
				model.addAttribute("listError", listError);
				return visualizzaAtto(atto.getId(), "visualizza", attoView, model, locale,request);
			}
			
			CategoriaAtto categoriaAtto = (CategoriaAtto) attoService.getCategoria(attoView.getIdCategoriaAtto());
			atto.setCategoriaAtto(categoriaAtto);

			if (attoView.getDataNotifica() != null && !attoView.getDataNotifica().trim().equalsIgnoreCase(""))
				atto.setDataNotifica(DateUtil.toDate(attoView.getDataNotifica()));
			if (attoView.getDataUdienza() != null && !attoView.getDataUdienza().trim().equalsIgnoreCase(""))
				atto.setDataUdienza(DateUtil.toDate(attoView.getDataUdienza()));

			atto.setDataUltimaModifica(new Date());
			atto.setDestinatario(attoView.getDestinatario());
			
			if(categoriaAtto.getCodGruppoLingua().equals("TPAT_3")){
				
				EsitoAtto esitoAtto = attoService.getEsito(attoView.getIdEsito());
				atto.setEsitoAtto(esitoAtto);
				atto.setPagamentoDovuto(attoView.getPagamentoDovuto());
				atto.setSpeseCarico(attoView.getSpeseCarico());
				atto.setSpeseFavore(attoView.getSpeseFavore());
			}
			else{
				atto.setEsitoAtto(null);
				atto.setPagamentoDovuto(null);
				atto.setSpeseCarico(null);
				atto.setSpeseFavore(null);
			}

			atto.setForoCompetente(attoView.getForoCompetente());
			atto.setLang(locale.getLanguage().toUpperCase());
			atto.setNote(attoView.getNote());

			atto.setParteNotificante(attoView.getParteNotificante());
			atto.setRilevante(attoView.getRilevante());
			Societa societa = (Societa) attoService.getSocieta(attoView.getIdSocieta());
			atto.setSocieta(societa);
			

			atto.setTipoAtto(attoView.getTipoAtto());

			Long idNewAtto = atto.getId();
			Documento document = null;
			if (attoView.getFile() != null && !attoView.getFile().isEmpty()) {
				document = attoService.aggiungiDocumento(atto.getDataCreazione(), idNewAtto, attoView.getFile(),
						attoView.getNumeroProtocollo());
				atto.setDocumento(document);

			}
			attoView.setVo(atto);

			attoService.modifica(attoView);

		}
		
		return visualizzaAtto(atto.getId(), "visualizza", attoView, model, locale,request);
	
	}catch(Exception ex){
		model.addAttribute("errorMessage", "errore.generico");
		return "redirect:/errore.action";
	}

		 
	}

	@RequestMapping(value = "/atto/save", method = RequestMethod.POST)
	public String salvaNuovoAtto(@ModelAttribute(MODEL_VIEW_NOME) AttoView attoView , HttpServletRequest request, Model model, Locale locale, BindingResult bindingResult)
			throws Throwable {
		
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		if (attoView == null)
			attoView = new AttoView();
		//FIX duplicato
		attoView.setNumeroProtocollo(this.getNumeroProtocolloAtto());
	try{
		Atto atto = new Atto();
		List<String> listError = new ArrayList<String>();
		if (isNotValid(attoView, listError,locale.getLanguage().toUpperCase())) {
			model.addAttribute("listError", listError);
			return errorCreaAtto(request, model, locale);
		} else {

			UtenteView utenteView = (UtenteView) request.getSession()
					.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if (utenteView == null) {
				new RuntimeException("Utente Non Connesso!");

			}
			
			CategoriaAtto categoriaAtto = (CategoriaAtto) attoService.getCategoria(attoView.getIdCategoriaAtto());
			CategoriaAtto ctItalia = (CategoriaAtto) attoService.getCategoriaPreLingua("IT",
					categoriaAtto.getCodGruppoLingua());
			atto.setCategoriaAtto(ctItalia);
			atto.setCreatoDa(utenteView.getVo().getUseridUtil());

			if(categoriaAtto.getCodGruppoLingua().equals("TPAT_3")){
				
				EsitoAtto esitoAtto = attoService.getEsito(attoView.getIdEsito());
				atto.setEsitoAtto(esitoAtto);
				atto.setPagamentoDovuto(attoView.getPagamentoDovuto());
				atto.setSpeseCarico(attoView.getSpeseCarico());
				atto.setSpeseFavore(attoView.getSpeseFavore());
			}
			
			
			atto.setDataCreazione(new Date());
			if (attoView.getDataNotifica() != null && !attoView.getDataNotifica().trim().equalsIgnoreCase(""))
				atto.setDataNotifica(DateUtil.toDate(attoView.getDataNotifica()));
			if (attoView.getDataUdienza() != null && !attoView.getDataUdienza().trim().equalsIgnoreCase(""))
				atto.setDataUdienza(DateUtil.toDate(attoView.getDataUdienza()));

			
			atto.setDataUltimaModifica(new Date());
			atto.setDestinatario(attoView.getDestinatario());
			atto.setEmailInvioAltriUffici(attoView.getEmailInvioAltriUffici());

			
			

			atto.setForoCompetente(attoView.getForoCompetente());
			atto.setLang(locale.getLanguage().toUpperCase());
			atto.setNote(attoView.getNote());
			atto.setNumeroProtocollo(attoView.getNumeroProtocollo());
			atto.setParteNotificante(attoView.getParteNotificante());
			atto.setRilevante(attoView.getRilevante());
			Societa societa = (Societa) attoService.getSocieta(attoView.getIdSocieta());
			atto.setSocieta(societa);
			// BOZZA
			StatoAtto statoAtto = (StatoAtto) attoService.getStatoAttoPerLingua("IT", "B");
			atto.setStatoAtto(statoAtto);

			atto.setTipoAtto(attoView.getTipoAtto());
			atto.setUnitaLegInvioAltriUffici(attoView.getUnitaLegInvioAltriUffici());
			atto.setUtenteInvioAltriUffici(attoView.getUtenteInvioAltriUffici());
			attoView.setVo(atto);

			AttoView attoView2 = attoService.inserisci(attoView);
			Long idNewAtto = attoView2.getVo().getId();
			Documento document = null;
			if (attoView.getFile() != null && !attoView.getFile().isEmpty()) {
				try{
				document = attoService.aggiungiDocumento(atto.getDataCreazione(), idNewAtto, attoView.getFile(),
						attoView.getNumeroProtocollo());
				attoView2.getVo().setDocumento(document);
				attoService.modifica(attoView2);
				} catch(Exception ex){ }
			}

			if(attoView.getUuidPecAtto()!=null && !attoView.getUuidPecAtto().equals("")){
				try{
				document = attoService.aggiungiDocumentoDaUuid(atto.getDataCreazione(), idNewAtto, attoView.getUuidPecAtto(),
						attoView.getNumeroProtocollo());
				attoView2.getVo().setDocumento(document);
				attoService.modifica(attoView2);
				} catch(Exception ex){ }
			}

			List<Societa> listaSocieta = attoService.getlistSocieta();
			String lingua = locale.getLanguage().toUpperCase();
			List<CategoriaAtto> listaCategorie = attoService.listaCategoriaAtto(lingua);
			model.addAttribute("attoView", attoView);
			model.addAttribute("listaSocieta", listaSocieta);
			model.addAttribute("listaCategorie", listaCategorie);

			return visualizzaAtto(idNewAtto.longValue(), "nuovo", attoView, model, locale,request);// PAGINA_NUOVO_ATTO;
		}
		}catch(Exception ex){
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}
	}

	private String getNumeroProtocolloAtto() throws Throwable{
		
		String protocolloNumero = "";
		Atto atto = attoService.getAttoPerNumeroProtocollo();

		if (atto == null) {
			protocolloNumero = "Snam/1/" + new Integer(DateUtil.getAnno(new Date())).intValue();
		 
		 
		} else if (atto != null && atto.getNumeroProtocollo() != null) {
			Integer prog = null, anno = null, annoCorrente = null;
			String[] numers = atto.getNumeroProtocollo().split("/");
			if (numers.length == 3) {
				prog = new Integer(numers[1]);

				annoCorrente = new Integer(DateUtil.getAnno(new Date()));
				anno = new Integer(numers[2]);

				if (anno.intValue() < annoCorrente.intValue()) {
					prog = new Integer("0");

				}

				protocolloNumero = "Snam/" + ((int) prog.intValue() + 1) + "/" + annoCorrente.intValue();

			} else {

				protocolloNumero = "Snam/1/" + new Integer(DateUtil.getAnno(new Date())).intValue();

			}

		} else {

			protocolloNumero = "Snam/1/" + new Integer(DateUtil.getAnno(new Date())).intValue();

		}
		return protocolloNumero;
		
	}
	
	private boolean isNotValid(AttoView aView, List<String> listError,String localLang) {
		boolean notValid = false;

		String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";
		String[][] lang=new String[2][8];
		
		lang[0][0]="SOCIETA *Campo Obbligatorio";
		lang[0][1]="PARTE NOTIFICANTE *Campo Obbligatorio";
		lang[0][2]="CATEGORIA ATTO *Campo Obbligatorio";
		lang[0][3]="DESTINATARIO *Campo Obbligatorio";
		lang[0][4]="TIPO ATTO GIUDIZIARIO *Campo Obbligatorio";
		lang[0][5]="FORO COMPETENTE *Campo Obbligatorio";
		lang[0][6]="DATA DELLA NOTIFICA *Campo Obbligatorio";
		lang[0][7]="RILEVANZA *Campo Obbligatorio";
	 
		lang[1][0]="COMPANY *Required field";
		lang[1][1]="THE NOTIFYING *Required field";
		lang[1][2]="CATEGORY ACT *Required field";
		lang[1][3]="RECIPIENT *Required field";
		lang[1][4]="TYPE ACT JUDICIAL *Required field";
		lang[1][5]="COMPETENT COURT *Required field";
		lang[1][6]="DATE OF NOTIFICATION *Required field";
		lang[1][7]="RELEVANCE *Required field";

		
		int language=0;
		if(languages.equalsIgnoreCase("IT"))
			language=0;
		if(languages.equalsIgnoreCase("EN"))
			language=1;	
		
		
		
		if (checkEmpty(aView.getCategoriaNome())) {
			listError.add(lang[language][2]);
			notValid = true;
		}
		if (checkEmpty(aView.getParteNotificante())) {
			listError.add(lang[language][1]);
			notValid = true;
		}
		if (checkEmpty(aView.getIdSocieta() + "")) {
			listError.add(lang[language][0]);
			notValid = true;
		}
		if (checkEmpty(aView.getForoCompetente())) {
			listError.add(lang[language][5]);
			notValid = true;
		}
		if (checkEmpty(aView.getDestinatario())) {
			listError.add(lang[language][3]);
			notValid = true;
		}
		if (checkEmpty(aView.getTipoAtto())) {
			listError.add(lang[language][4]);
			notValid = true;
		}

		if (checkEmpty(aView.getRilevante())) {
			listError.add(lang[language][7]);
			notValid = true;
		}
		if (checkData(aView.getDataNotifica())) {
			listError.add(lang[language][6]);
			notValid = true;
		}
		if (notValid)
			return true;
		return false;
	}

	private boolean checkEmpty(String s) {
		if (s != null)
			return s.trim().equalsIgnoreCase("");
		return false;
	}

	private boolean checkData(String s) {
		if (s != null)
			return !DateUtil.isData(s);

		return false;
	}

	@RequestMapping(value = "/atto/download", method = RequestMethod.GET)
	public String downloadDoc(@RequestParam("uuid") String uuid, HttpServletRequest request,
			HttpServletResponse response, Model model, Locale locale) throws Throwable {
		logger.info("@@DDS ___________ /atto/download");
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		Document document = attoService.leggiDocumentoUUID(uuid);
		String contentType = document.getContents().get(0).getContentsMimeType(); //@@DDS document.get_MimeType();
		
		String name = document.getContents().get(0).getContentsName();//@@DDS document.get_Name();
		
		ByteArrayInputStream is = null;
		OutputStream os = null;
 
		
		try {
		
 	 
			byte[] contenuto=attoService.leggiContenutoDocumentoUUID(uuid);
			
			response.setContentLength(contenuto.length);
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment;filename=" + name);
			response.setHeader("Cache-control", "");
			is = new ByteArrayInputStream(contenuto);
			os = response.getOutputStream();
			IOUtils.copy(is, os);
			

		} catch (Exception e) {

		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}

		return null;
	}

	//---------------- DA UTILIZZARE PER RECUPERARE LA LISTA UTENTI ASSOCIATI
	private List<UtenteView> getAssegnatarioNonAssente(UtenteView assegnatario) throws Throwable{
		UtenteView utenteConnesso=new UtenteView();
		
		utenteConnesso.setResponsabileFoglia(utenteService.leggiSeResponsabileFoglia(assegnatario));
		utenteConnesso.setResponsabileSenzaCollaboratori(utenteService.leggiSeResponsabileSenzaCollaboratori(assegnatario));
		utenteConnesso.setOperatoreSegreteria(utenteService.leggiSeOperatoreSegreteria(assegnatario));
		
		List<UtenteView> utenti=null;
		if(assegnatario.isResponsabile() && !utenteConnesso.isResponsabileFoglia() && !utenteConnesso.isResponsabileSenzaCollaboratori()){
			utenti = utenteService.leggiCollaboratoriResponsabili(assegnatario.getVo().getMatricolaUtil());
			 
			utenti=this.escludeAssenti(utenti);
			 
		}
		else if(utenteConnesso.isResponsabileFoglia() && !utenteConnesso.isResponsabileSenzaCollaboratori()){
			utenti = utenteService.leggiCollaboratoriLegaliInterni(assegnatario.getVo().getMatricolaUtil());
			utenti=this.escludeAssenti(utenti);
		 
		}
		else if(utenteConnesso.isOperatoreSegreteria()){
			utenti = utenteService.leggiGC();
			utenti=this.escludeAssenti(utenti);
		}
		
 
		 
		
		return utenti;
	}
	
	//Filtra utenti Presenti
		private List<UtenteView> escludeAssenti(List<UtenteView> utenti){
		List<UtenteView> presenti=new ArrayList<UtenteView>();
		if(utenti!=null)
			for(UtenteView uV:utenti)
				if(uV.getVo()!=null && !uV.getVo().getAssente().equalsIgnoreCase("T"))
					presenti.add(uV);
		return presenti;	
		}
	
	
	/*
	 * @InitBinder protected void initBinder(WebDataBinder binder) {
	 * binder.setValidator(new AttoValidator());
	 * binder.registerCustomEditor(byte[].class, new
	 * ByteArrayMultipartFileEditor()); }
	 */

}
