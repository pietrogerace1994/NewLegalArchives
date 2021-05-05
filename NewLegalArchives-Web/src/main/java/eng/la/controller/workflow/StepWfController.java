package eng.la.controller.workflow;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.FascicoloService;
import eng.la.business.IncaricoService;
import eng.la.business.NotificaWebService;
import eng.la.business.ProfessionistaEsternoService;
import eng.la.business.StoricoSchedaFondoRischiService;
import eng.la.business.UtenteService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.business.websocket.WebSocketPublisher;
import eng.la.business.workflow.AttoWfService;
import eng.la.business.workflow.BeautyContestWfService;
import eng.la.business.workflow.FascicoloWfService;
import eng.la.business.workflow.IncaricoWfService;
import eng.la.business.workflow.ProfessionistaEsternoWfService;
import eng.la.business.workflow.ProformaWfService;
import eng.la.business.workflow.SchedaFondoRischiWfService;
import eng.la.business.workflow.StepWfService;
import eng.la.controller.BaseController;
import eng.la.controller.ParteCorrelataController;
import eng.la.model.Atto;
import eng.la.model.BeautyContest;
import eng.la.model.Incarico;
import eng.la.model.NotificaWeb;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.Proforma;
import eng.la.model.SchedaFondoRischi;
import eng.la.model.Utente;
import eng.la.model.rest.Event;
import eng.la.model.rest.StepWFRest;
import eng.la.model.view.AttoWfView;
import eng.la.model.view.BaseView;
import eng.la.model.view.BeautyContestWfView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.FascicoloWfView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.IncaricoWfView;
import eng.la.model.view.NotificaWebView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.ProfessionistaEsternoWfView;
import eng.la.model.view.ProformaWfView;
import eng.la.model.view.SchedaFondoRischiWfView;
import eng.la.model.view.StepWfView;
import eng.la.model.view.StoricoSchedaFondoRischiView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.util.costants.Costanti;

@Controller("stepWfController")
@SessionAttributes("stepWfView")


public class StepWfController extends BaseController {

	private static final String MODEL_VIEW_NOME = "stepWfView";
	private static final String PAGINA_ELENCO_PATH = "../parts/notifiche";
	private static final String PAGINA_FORM_WORKFLOW = "workflow/formWorkflow";
	private static final String PAGINA_FORM_WORKFLOW_ATTO = "workflow/formWorkflowAtto";
//	DARIO **************************************************************************
	private static final String PAGINA_FORM_WORKFLOW_2 = "workflow/formWorkflow_2";
	private static final String PAGINA_FORM_WORKFLOW_ATTO_2 = "workflow/formWorkflowAtto_2";
//	********************************************************************************	
	private static final String PAGINA_FORM_RESPONSE_WORKFLOW = "workflow/formResponseWorkflow";

	private static final Logger logger = Logger.getLogger(StepWfController.class);
	
	@Autowired
	private NotificaWebService notificaWebService;

	@Autowired
	private ProfessionistaEsternoService professionistaEsternoService;

	@Autowired
	private FascicoloService fascicoloService;

	@Autowired
	private StepWfService stepWfService;

	@Autowired
	private IncaricoService incaricoService;

	@Autowired
	private FascicoloWfService fascicoloWfService;

	@Autowired
	private IncaricoWfService incaricoWfService;

	@Autowired
	private SchedaFondoRischiWfService schedaFondoRischiWfService;

	@Autowired
	private ProfessionistaEsternoWfService professionistaEsternoWfService;

	@Autowired
	private AttoWfService attoWfService;

	@Autowired
	private ProformaWfService proformaWfService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private EmailNotificationService emailNotificationService;

	@Autowired
	private BeautyContestWfService beautyContestWfService;
	
	@Autowired
	private StoricoSchedaFondoRischiService storicoSchedaFondoRischiService;


	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {

	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {

	}

	/**
	 * Lista attività pendenti.
	 * @param model modello dei dati
	 * @param locale country locale.
	 * @return
	 */
	@RequestMapping(value = "/stepWf/lista", method = RequestMethod.GET)
	public String listaAttivitaPendenti(Model model,Locale locale) {
		StepWfView stepWfView = new StepWfView();
		try {
			model.addAttribute("listaAttivitaPendenti", stepWfService.leggiAttivitaPendenti("0910004797", locale.getLanguage().toUpperCase()));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, stepWfView);
		return PAGINA_ELENCO_PATH;
	}

	@RequestMapping(value = "/stepWf/processaWorkflow", method = RequestMethod.POST)
	public String processaWorkflow(StepWfView stepWfView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response,
			//DARIO aggiunto parametro flagAssegnatari ********************
			@RequestParam("flagAssegnatari") boolean flagAssegnatari) {
			//***************************************************************
		
		
		try {  
			
						
			//DARIO *****************************************************************************************************
			if (flagAssegnatari){
				UtenteView utenteConnesso=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
				List<UtenteView> assegnatari = utenteService.leggiAssegnatari(utenteConnesso);
				model.addAttribute("assegnatari",assegnatari);	
			}
			
			//***********************************************************************************************************
			
			StepWfView stepWf = stepWfService.leggiStep(stepWfView.getIdStep());

			switch(stepWfView.getCodiceClasse()){
			case CostantiDAO.CHIUSURA_FASCICOLO:
				stepWfView.setIdFascicolo(stepWfView.getIdObject());
				stepWfView.setIdIncarico(0);
				stepWfView.setIdProforma(0);
				stepWfView.setIdProfessionista(0);
				stepWfView.setIdArbitrato(0);
				//DARIO**********************
				//return PAGINA_FORM_WORKFLOW;
				return PAGINA_FORM_WORKFLOW_2;
				//***************************
			case CostantiDAO.AUTORIZZAZIONE_INCARICO:
				stepWfView.setIdIncarico(stepWfView.getIdObject());
				stepWf = stepWfService.leggiStep(stepWfView.getIdStep());
				if(stepWf.getVo().getIncaricoWf().getIncarico().getCollegioArbitrale().equalsIgnoreCase(Character.toString(CostantiDAO.FALSE_CHAR))){
					stepWfView.setIdArbitrato(0);
					stepWfView.setIdIncarico(stepWfView.getIdObject());
				}
				else{
					stepWfView.setIdArbitrato(stepWfView.getIdObject());
					stepWfView.setIdIncarico(stepWf.getVo().getIncaricoWf().getIncarico().getIncarico().getId());
				}

				stepWfView.setIdFascicolo(stepWf.getVo().getIncaricoWf().getIncarico().getFascicolo().getId());
				stepWfView.setIdProforma(0);
				stepWfView.setIdProfessionista(0);
				//DARIO**********************
				//return PAGINA_FORM_WORKFLOW;
				return PAGINA_FORM_WORKFLOW_2;
				//***************************
			case CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI:
				stepWfView.setIdSchedaFr(stepWfView.getIdObject());
				stepWf = stepWfService.leggiStep(stepWfView.getIdStep());
				stepWfView.setIdSchedaFr(stepWfView.getIdObject());
				stepWfView.setIdFascicolo(stepWf.getVo().getSchedaFondoRischiWf().getSchedaFondoRischi().getFascicolo().getId());
				stepWfView.setIdProforma(0);
				stepWfView.setIdProfessionista(0);
				stepWfView.setVerificatore(false);
				if(stepWf.getVo().getSchedaFondoRischiWf().getSchedaFondoRischi().getStatoSchedaFondoRischi() != null){
					if(stepWf.getVo().getSchedaFondoRischiWf().getSchedaFondoRischi().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_ATTESA_VERIFICA))
						stepWfView.setVerificatore(true);
				}
				
				List<StoricoSchedaFondoRischiView> versioniPrecedenti = storicoSchedaFondoRischiService.leggiVersioniPrecedenti(stepWfView.getIdObject());
				if(versioniPrecedenti != null && !versioniPrecedenti.isEmpty()){
						
					stepWfView.setStorico(true);
				}
				//DARIO**********************
				//return PAGINA_FORM_WORKFLOW;
				return PAGINA_FORM_WORKFLOW_2;
				//***************************
			case CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST:
				stepWfView.setIdBeautyContest(stepWfView.getIdObject());
				stepWf = stepWfService.leggiStep(stepWfView.getIdStep());
				stepWfView.setIdFascicolo(0);
				stepWfView.setIdProforma(0);
				stepWfView.setIdProfessionista(0);
				//DARIO**********************
				//return PAGINA_FORM_WORKFLOW;
				return PAGINA_FORM_WORKFLOW_2;
				//***************************
			case CostantiDAO.AUTORIZZAZIONE_PROFORMA:
				stepWfView.setIdProforma(stepWfView.getIdObject());
				/*Collection<RIncaricoProformaSocieta> listaIncarichi =stepWf.getVo().getProformaWf().getProforma().getRIncaricoProformaSocietas();
					Incarico incaricoProforma = null;
					if(listaIncarichi.size() > 0)
						incaricoProforma = ((List<RIncaricoProformaSocieta>)listaIncarichi).get(0).getIncarico();*/
				IncaricoView incaricoView = incaricoService.leggiIncaricoAssociatoAProforma(stepWf.getVo().getProformaWf().getProforma().getId());
				if(incaricoView.getVo().getCollegioArbitrale().equalsIgnoreCase(Character.toString(CostantiDAO.FALSE_CHAR))){
					stepWfView.setIdArbitrato(0);
					stepWfView.setIdIncarico(incaricoView.getVo().getId());
				}
				else{
					stepWfView.setIdArbitrato(incaricoView.getVo().getId());
					stepWfView.setIdIncarico(incaricoView.getVo().getIncarico().getId());
				}
				stepWfView.setIdFascicolo(incaricoView.getVo().getFascicolo().getId());
				stepWfView.setIdProfessionista(0);
				//DARIO**********************
				//return PAGINA_FORM_WORKFLOW;
				return PAGINA_FORM_WORKFLOW_2;
				//***************************
			case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:
				stepWfView.setIdFascicolo(0);
				stepWfView.setIdIncarico(0);
				stepWfView.setIdProforma(0);
				stepWfView.setIdArbitrato(0);
				stepWfView.setIdProfessionista(stepWfView.getIdObject());
				//DARIO**********************
				//return PAGINA_FORM_WORKFLOW;
				return PAGINA_FORM_WORKFLOW_2;
				//***************************
			default:
				break;


			}


		} catch (Throwable e) {

			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/stepWf/processaWorkflowAtto", method = RequestMethod.POST)
	public String processaWorkflowAtto(StepWfView stepWfView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response,
			//DARIO aggiunto parametro flagAssegnatari ********************
			@RequestParam("flagAssegnatari") boolean flagAssegnatari) {
			//***************************************************************
			

		try { 
			UtenteView utenteConnesso = (UtenteView) request.getSession()
					.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			
			stepWfView.setIdAtto(stepWfView.getIdObject());
			StepWfView stepWf = stepWfService.leggiStep(stepWfView.getIdStep());
			stepWfView.setNote(stepWf.getVo().getMotivoRifiutoStepPrecedente());
			
			//DARIO *****************************************************************************************************
			if (flagAssegnatari){
				List<UtenteView> assegnatari = utenteService.leggiAssegnatariAtto(utenteConnesso);
				model.addAttribute("assegnatari",assegnatari);	
				
				stepWfView.setLegaliInterni(null);
				stepWfView.setCollaboratoriResponsabili(null);
				stepWfView.setGC(null);
				
				return PAGINA_FORM_WORKFLOW_ATTO_2;
			}
			
			//***********************************************************************************************************
			
			
			
			List<UtenteView> utenti = null;
			if(utenteConnesso.isResponsabile() && !utenteConnesso.isResponsabileFoglia() && !utenteConnesso.isResponsabileSenzaCollaboratori()){
				utenti = utenteService.leggiCollaboratoriResponsabili(utenteConnesso.getVo().getMatricolaUtil());
				stepWfView.setLegaliInterni(null);
				stepWfView.setCollaboratoriResponsabili(utenti);
				stepWfView.setGC(null);
			}
			else if(utenteConnesso.isResponsabileFoglia() && !utenteConnesso.isResponsabileSenzaCollaboratori()){
				utenti = utenteService.leggiCollaboratoriLegaliInterni(utenteConnesso.getVo().getMatricolaUtil());
				stepWfView.setLegaliInterni(utenti);
				stepWfView.setCollaboratoriResponsabili(null);
				stepWfView.setGC(null);
			}
			else if(utenteConnesso.isOperatoreSegreteria()){
				utenti = utenteService.leggiGC();
				stepWfView.setLegaliInterni(null);
				stepWfView.setCollaboratoriResponsabili(null);
				stepWfView.setGC(utenti);
			}
			//DARIO******************************
			//return PAGINA_FORM_WORKFLOW_ATTO;
			return PAGINA_FORM_WORKFLOW_ATTO_2;
			//***********************************
		} catch (Throwable e) {

			e.printStackTrace();
		}
		return null;
	}


	@RequestMapping(value = "/stepWf/avanzaWorkflow", method = RequestMethod.POST)
	public String avanzaWorkflow(StepWfView stepWfView, @RequestParam("matricola_dest") String matricola_dest, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) throws Throwable //l'eccezione è gestita a livello Ajax
	{
		//DARIO AGGIUNTO @RequestParam("matricola_dest") String matricola_dest
		UtenteView utenteConnesso = (UtenteView) request.getSession()
				.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		long idEntita = 0;
		boolean ret = false;
		switch(stepWfView.getCodiceClasse()){


		case CostantiDAO.CHIUSURA_FASCICOLO:
			FascicoloWfView fascicoloWf = fascicoloWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			idEntita = fascicoloWf.getVo().getFascicolo().getId();

			ret = fascicoloWfService.chiudiFascicolo(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil());
			if(ret){
				//invio la e-mail
				try{
					emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.FASCICOLO, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}
				try {
					NotificaWebView notificaWeb = new NotificaWebView();
					notificaWeb.setIdFascicolo(stepWfView.getIdFascicolo());

					FascicoloView fascicolo = fascicoloService.leggi(stepWfView.getIdFascicolo());

					notificaWeb.setNomeFascicolo(fascicolo.getVo().getNome());
					notificaWeb.setDataInserimento(new Date());
					NotificaWeb notifica = new NotificaWeb();
					notifica.setMatricolaMitt(utenteConnesso.getVo());
					Utente matricolaDest = utenteService.leggiUtenteDaUserId(fascicolo.getVo().getLegaleInterno()).getVo();
					notifica.setMatricolaDest(matricolaDest==null?utenteConnesso.getVo():matricolaDest);
					notifica.setDataNotifica(new Date());
					notifica.setKeyMessage("fascicolo.chiuso.indata");
					notifica.setJsonParam(notificaWeb.getJsonChiusuraFascicolo());
					notificaWeb.setVo(notifica);
					notificaWebService.inserisciChiusuraFascicolo(notificaWeb);

				} catch (Exception e) {
					System.out.println("Errore in invio notificaweb"+ e);
				}
			}
			break;

		case CostantiDAO.AUTORIZZAZIONE_INCARICO:
			boolean arbitrato = false;
			IncaricoWfView incaricoWf = incaricoWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			Incarico incarico = incaricoWf.getVo().getIncarico();
			idEntita = incarico.getId();

			if(incarico.getCollegioArbitrale().equalsIgnoreCase(Character.toString(CostantiDAO.TRUE_CHAR)))
				arbitrato = true;

			//DARIO C *****************************************************************************************************************
			//ret = incaricoWfService.avanzaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil());
			ret = incaricoWfService.avanzaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(),matricola_dest);
			//*************************************************************************************************************************
			if(ret){
				//invio la notifica
				
				UtenteView assegnatario = utenteService.leggiAssegnatarioWfIncarico(idEntita);
				
				if(assegnatario != null){	

					//Controllo che l'assegnatario non sia assente in caso contrario avanza il workFlow
					assegnatario= checkUtenteNonAssente(stepWfView.getIdWorkflow(), CostantiDAO.AUTORIZZAZIONE_INCARICO, idEntita, utenteConnesso, assegnatario);


					StepWFRest stepSuccessivo = new StepWFRest();
					stepSuccessivo.setId(0);

					Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
					WebSocketPublisher.getInstance().publishEvent(event); 

				}

				
				try{
					String tipoEntita = CostantiDAO.INCARICO;
					if(arbitrato)
						tipoEntita = CostantiDAO.ARBITRATO;
					//DARIO C ***************************************************************************************************************************************************************
					//emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, tipoEntita, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
					emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, tipoEntita, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil(),matricola_dest);
					//****************************************************************************************************************************************************************
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}


			}
			break;

		case CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI:
			SchedaFondoRischiWfView schedaFondoRischiWf = schedaFondoRischiWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			SchedaFondoRischi schedaFondoRischi = schedaFondoRischiWf.getVo().getSchedaFondoRischi();
			idEntita = schedaFondoRischi.getId();
			
			//DARIO C *****************************************************************************************************************
			//ret = schedaFondoRischiWfService.avanzaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil());
			ret = schedaFondoRischiWfService.avanzaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(),matricola_dest);
			//*************************************************************************************************************************
			if(ret){
				//invio la notifica
				List<UtenteView> assegnatari = utenteService.leggiAssegnatarioWfSchedaFondoRischi(idEntita);
				if(assegnatari != null && !assegnatari.isEmpty()){

					for(UtenteView assegnatario : assegnatari){

						//Controllo che l'assegnatario non sia assente in caso contrario avanza il workFlow
						assegnatario= checkUtenteNonAssente(stepWfView.getIdWorkflow(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI, idEntita, utenteConnesso, assegnatario);


						StepWFRest stepSuccessivo = new StepWFRest();
						stepSuccessivo.setId(0);

						Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
						WebSocketPublisher.getInstance().publishEvent(event); 
					}
				}

				//invio la e-mail
				try{
					String tipoEntita = CostantiDAO.SCHEDA_FONDO_RISCHI;
					//DARIO C *********************************************************************************************************************************************************
					//emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, tipoEntita, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
					emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, tipoEntita, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil(),matricola_dest);
					//****************************************************************************************************************************************************************
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}


			}
			//return PAGINA_FORM_RESPONSE_WORKFLOW;
			break;	

		case CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST:
			BeautyContestWfView beautyContestWf = beautyContestWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			BeautyContest beautyContest = beautyContestWf.getVo().getBeautyContest();
			idEntita = beautyContest.getId();

			//DARIO C *****************************************************************************************************************
			//ret = beautyContestWfService.avanzaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil());
			ret = beautyContestWfService.avanzaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(),matricola_dest);
			//*************************************************************************************************************************
			if(ret){
				//invio la notifica
				UtenteView assegnatario = utenteService.leggiAssegnatarioWfBeautyContest(idEntita);
				if(assegnatario != null){	

					//Controllo che l'assegnatario non sia assente in caso contrario avanza il workFlow
					assegnatario= checkUtenteNonAssente(stepWfView.getIdWorkflow(), CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST, idEntita, utenteConnesso, assegnatario);

					StepWFRest stepSuccessivo = new StepWFRest();
					stepSuccessivo.setId(0);

					Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
					WebSocketPublisher.getInstance().publishEvent(event); 
				}

				//invio la e-mail
				try{
					String tipoEntita = CostantiDAO.BEAUTY_CONTEST;
					//DARIO C ***************************************************************************************************************************************************************
					//emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, tipoEntita, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
					emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, tipoEntita, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil(),matricola_dest);
					
					//****************************************************************************************************************************************************************
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}
			}
			break;

		case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:
			ProfessionistaEsternoWfView professionistaWf = professionistaEsternoWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			ProfessionistaEsterno professionista = professionistaWf.getVo().getProfessionistaEsterno();
			idEntita = professionista.getId();

			ret = professionistaEsternoWfService.avanzaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil());
			if(ret){

				UtenteView assegnatario = utenteService.leggiAssegnatarioWfProfessionistaEsterno(idEntita);
				if(assegnatario != null){
					//Controllo che l'assegnatario non sia assente in caso contrario avanza il workFlow
					assegnatario= checkUtenteNonAssente(stepWfView.getIdWorkflow(), CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO, idEntita, utenteConnesso, assegnatario);

					StepWFRest stepSuccessivo = new StepWFRest();
					stepSuccessivo.setId(0);

					Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
					WebSocketPublisher.getInstance().publishEvent(event); 

				}

				//invio la e-mail
				try{
					emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.PROFESSIONISTA_ESTERNO, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}

				try {

					long idProfessionista = stepWfView.getIdProfessionista();

					ProfessionistaEsternoView pe = professionistaEsternoService.leggi(idProfessionista);

					NotificaWebView notificaWeb = new NotificaWebView();
					notificaWeb.setNomeLegaleEsterno(pe.getCognomeNome());
					notificaWeb.setDenominazioneStudioLegaleEsterno(pe.getStudioLegaleDenominazione());
					notificaWeb.setDataInserimento(new Date());

					NotificaWeb notifica = new NotificaWeb();
					notifica.setMatricolaMitt(utenteConnesso.getVo());
					notifica.setDataNotifica(new Date());
					notifica.setKeyMessage("professionista.esterno.inserito");
					notifica.setJsonParam(notificaWeb.getJsonInserimentoPEsterno());
					notificaWeb.setVo(notifica);
					notificaWebService.inserisciProfessionistaEsterno(notificaWeb);

				} catch (Exception e) {
					System.out.println("Errore in invio notificaweb"+ e);
				}
			}
			break;
		case CostantiDAO.AUTORIZZAZIONE_PROFORMA:
			ProformaWfView proformaWf = proformaWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			Proforma proforma = proformaWf.getVo().getProforma();
			idEntita = proforma.getId();
			
			//DARIO C *****************************************************************************************************************
			//ret = proformaWfService.avanzaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil());
			ret = proformaWfService.avanzaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(),matricola_dest);
			//*************************************************************************************************************************
			
			if(ret){
				//invio la notifica
				UtenteView assegnatario = utenteService.leggiAssegnatarioWfProforma(idEntita);
				if(assegnatario != null){
					//Controllo che l'assegnatario non sia assente in caso contrario avanza il workFlow
					assegnatario= checkUtenteNonAssente(stepWfView.getIdWorkflow(), CostantiDAO.AUTORIZZAZIONE_PROFORMA, idEntita, utenteConnesso, assegnatario);

					StepWFRest stepSuccessivo = new StepWFRest();
					stepSuccessivo.setId(0);
					/*

						//recupero il numero di attività pendenti
						List<StepWfView> steps = stepWfService.leggiAttivitaPendenti(assegnatario.getVo().getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
						StepWfView newStep = stepWfService.leggiStepCorrente(stepWfView.getIdWorkflow(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
						String codiceLingua = newStep.getVo().getConfigurazioneStepWf().getCodGruppoLingua();
						ConfigurazioneStepWfView configurazioneTradottaNew = configurazioneStepWfService.leggiConfigurazioneTradotta(codiceLingua, locale.getLanguage().toUpperCase());


						IncaricoView incaricoProforma = incaricoService.leggiIncaricoAssociatoAProforma(idEntita) ;

						stepSuccessivo.setDescLinguaCorrente(configurazioneTradottaNew.getVo().getDescrStateTo());
						stepSuccessivo.setIdProforma(idEntita);
						stepSuccessivo.setIdProformaWf(stepWfView.getIdWorkflow());
						stepSuccessivo.setDataCreazione(df.format(newStep.getVo().getDataCreazione()));
						stepSuccessivo.setDescLinguaCorrente(configurazioneTradottaNew.getVo().getDescrStateTo());
						stepSuccessivo.setNoteSpecifiche(CostantiDAO.PROFORMA + " " + proforma.getNomeProforma());						
						stepSuccessivo.setFascicoloConValore(CostantiDAO.FASCICOLO + " " + incaricoProforma.getVo().getFascicolo().getNome());
						stepSuccessivo.setId(newStep.getVo().getId());
						stepSuccessivo.setCodice(stepWfView.getCodiceClasse());
						stepSuccessivo.setNumeroStep(steps.size());*/

					Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
					WebSocketPublisher.getInstance().publishEvent(event); 

				}


				//invio la e-mail
				try{
					//DARIO C ***************************************************************************************************************************************************************
					//emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.PROFORMA, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
					emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.PROFORMA, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil(),matricola_dest);
					//****************************************************************************************************************************************************************
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}
			}
			break;

		default:
			break;


		}
		return null;
	}


	// Ritorna un UtenteAssegnatario Non Assente
	private UtenteView checkUtenteNonAssente(long idWorkflow,String codiceClasse, long idEntita, UtenteView utenteConnesso,UtenteView assegnatario) throws Throwable //l'eccezione è gestita a livello Ajax
	{


		boolean ret = false;
		switch(codiceClasse){


		case CostantiDAO.CHIUSURA_FASCICOLO:
			//				FascicoloWfView fascicoloWf = fascicoloWfService.leggiWorkflow(idWorkflow);
			//				idEntita = fascicoloWf.getVo().getFascicolo().getId();
			//
			//				ret = fascicoloWfService.chiudiFascicolo(idWorkflow, utenteConnesso.getVo().getUseridUtil());
			//				if(ret){
			//					//invio la e-mail
			//				}
			//				
			return assegnatario;

		case CostantiDAO.AUTORIZZAZIONE_INCARICO:

			if(assegnatario!=null && assegnatario.getVo()!=null && assegnatario.getVo().getAssente().equals("T")){
				if(!utenteService.isAssegnatarioManualeCorrenteStandard(idWorkflow,CostantiDAO.AUTORIZZAZIONE_INCARICO)){	
					ret=incaricoWfService.avanzaWorkflow(idWorkflow, assegnatario.getVo().getUseridUtil());
					if(ret){
						UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfIncarico(idEntita);
						if(assegnatarioNew==null) return assegnatario;
						return 	 checkUtenteNonAssente(idWorkflow, codiceClasse, idEntita, utenteConnesso, assegnatarioNew);
					}
				}else{ return assegnatario;}

			}else{ return assegnatario;}


			break;

		case CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI:

			if(!utenteService.isAssegnatarioManualeCorrenteStandard(idWorkflow,CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI)){		
				if(assegnatario!=null && assegnatario.getVo()!=null && assegnatario.getVo().getAssente().equals("T")){
					ret=schedaFondoRischiWfService.avanzaWorkflow(idWorkflow, assegnatario.getVo().getUseridUtil());
					if(ret){
						List<UtenteView> assegnatariNew = utenteService.leggiAssegnatarioWfSchedaFondoRischi(idEntita);

						if(assegnatariNew==null) return assegnatario;
						return 	 checkUtenteNonAssente(idWorkflow, codiceClasse, idEntita, utenteConnesso, assegnatariNew.get(0));
					}
				}else{ return assegnatario;}

			}else{ return assegnatario;}


			break;

		case CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST:

			if(!utenteService.isAssegnatarioManualeCorrenteStandard(idWorkflow,CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST)){		
				if(assegnatario!=null && assegnatario.getVo()!=null && assegnatario.getVo().getAssente().equals("T")){
					ret=beautyContestWfService.avanzaWorkflow(idWorkflow, assegnatario.getVo().getUseridUtil());
					if(ret){
						UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfBeautyContest(idEntita);

						if(assegnatarioNew==null) return assegnatario;
						return 	 checkUtenteNonAssente(idWorkflow, codiceClasse, idEntita, utenteConnesso, assegnatarioNew);
					}
				}else{ return assegnatario;}

			}else{ return assegnatario;}

			break;

		case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:


			if(assegnatario!=null && assegnatario.getVo()!=null && assegnatario.getVo().getAssente().equals("T")){
				if(!utenteService.isAssegnatarioManualeCorrenteStandard(idWorkflow,CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO)){		
					ret=professionistaEsternoWfService.avanzaWorkflow(idWorkflow, assegnatario.getVo().getUseridUtil());
					if(ret){
						UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfProfessionistaEsterno(idEntita);
						if(assegnatarioNew==null) return assegnatario;
						return 	 checkUtenteNonAssente(idWorkflow, codiceClasse, idEntita, utenteConnesso, assegnatarioNew);
					}
				}else{ return assegnatario;}

			}else{ return assegnatario;}

			break;
		case CostantiDAO.AUTORIZZAZIONE_PROFORMA:

			if(assegnatario!=null && assegnatario.getVo()!=null && assegnatario.getVo().getAssente().equals("T")){
				if(!utenteService.isAssegnatarioManualeCorrenteStandard(idWorkflow,CostantiDAO.AUTORIZZAZIONE_PROFORMA)){		
					ret=proformaWfService.avanzaWorkflow(idWorkflow, assegnatario.getVo().getUseridUtil());
					if(ret){
						UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfProforma(idEntita);
						if(assegnatarioNew==null) return assegnatario;
						return 	 checkUtenteNonAssente(idWorkflow, codiceClasse, idEntita, utenteConnesso, assegnatarioNew);
					}
				}else{ return assegnatario;}

			}else{ return assegnatario;}


			break;



		default:
			return assegnatario;


		}
		return assegnatario;
	}




	@RequestMapping(value = "/stepWf/registraAtto", method = RequestMethod.POST)
	public String registraAtto(StepWfView stepWfView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) throws Throwable //l'eccezione è gestita a livello Ajax
	{
		//		Mail mail = new Mail();
		//		mail.setDocumentLink("link");
		//		mail.setDocumentName("Nome Documento");
		//		mail.setPendingActivitiesLink("Altro link");
		//		emailNotificationService.sendNotification(mail);
		UtenteView utenteConnesso = (UtenteView) request.getSession()
				.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		AttoWfView attoWf = attoWfService.leggiWorkflow(stepWfView.getIdWorkflow());
		long idEntita = attoWf.getVo().getAtto().getId();

		boolean chiudi = attoWfService.chiudiAtto(stepWfView.getIdWorkflow(), CostantiDAO.ATTO_STATO_REGISTRATO, utenteConnesso.getVo().getUseridUtil());
		if(chiudi){

			try{
				emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.ATTO, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
			}
			catch (Exception e) { 
				System.out.println("Errore in invio e-mail"+ e);
			}
		}


		return null;
	}

	@RequestMapping(value = "/stepWf/inviaAltriUffici", method = RequestMethod.POST)
	public String inviaAltriUffici(StepWfView stepWfView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) throws Throwable //l'eccezione è gestita a livello Ajax
	{
		UtenteView utenteConnesso = (UtenteView) request.getSession()
				.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		attoWfService.chiudiAtto(stepWfView.getIdWorkflow(), CostantiDAO.ATTO_STATO_INVIATO_ALTRI_UFFICI, utenteConnesso.getVo().getUseridUtil());

		return null;
	}



	@RequestMapping(value = "/stepWf/avanzaWorkflowAtto", method = RequestMethod.POST)
	public String avanzaWorkflowAtto(@RequestParam("idWorkflow") long idWorkflow, @RequestParam("assegnatario") String assegnatario, 
			StepWfView stepWfView, BindingResult bindingResult, Locale locale,
			HttpServletRequest request, HttpServletResponse response) throws Throwable //l'eccezione è gestita a livello Ajax
	{
		
		
		UtenteView utenteConnesso = (UtenteView) request.getSession()
				.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

		AttoWfView attoWf = attoWfService.leggiWorkflow(idWorkflow);
		Atto atto = attoWf.getVo().getAtto();
		long idEntita = atto.getId();

				
		boolean success = false;
		try {
			success = attoWfService.avanzaWorkflow(idWorkflow, assegnatario, utenteConnesso.getVo().getUseridUtil() );
		} catch (Throwable e) {
			if(!success)
				throw(e);
		}
		if(success){
			//invio la notifica
			UtenteView utenteAssegnatario = utenteService.leggiUtenteDaMatricola(assegnatario);
			if(utenteAssegnatario != null){

				StepWFRest stepSuccessivo = new StepWFRest();
				stepSuccessivo.setId(0);
				/*				
				//recupero il numero di attività pendenti
				List<StepWfView> steps = stepWfService.leggiAttivitaPendenti(assegnatario, CostantiDAO.LINGUA_ITALIANA);
				StepWfView newStep = stepWfService.leggiStepCorrente(stepWfView.getIdWorkflow(), CostantiDAO.REGISTRAZIONE_ATTO);
				String codiceLingua = newStep.getVo().getConfigurazioneStepWf().getCodGruppoLingua();
				ConfigurazioneStepWfView configurazioneTradottaNew = configurazioneStepWfService.leggiConfigurazioneTradotta(codiceLingua, locale.getLanguage().toUpperCase());

				stepSuccessivo.setDescLinguaCorrente(configurazioneTradottaNew.getVo().getDescrStateTo());
				stepSuccessivo.setIdAtto(idEntita);
				stepSuccessivo.setIdAttoWf(stepWfView.getIdWorkflow());
				stepSuccessivo.setDataCreazione(df.format(newStep.getVo().getDataCreazione()));
				stepSuccessivo.setDescLinguaCorrente(configurazioneTradottaNew.getVo().getDescrStateTo());
				stepSuccessivo.setNoteSpecifiche(CostantiDAO.ATTO + " " + atto.getNumeroProtocollo());						
				stepSuccessivo.setFascicoloConValore(StringUtils.EMPTY);
				stepSuccessivo.setId(newStep.getVo().getId());
				stepSuccessivo.setCodice(stepWfView.getCodiceClasse());
				stepSuccessivo.setMotivoRifiutoStepPrecedente(newStep.getVo().getMotivoRifiutoStepPrecedente());
				stepSuccessivo.setNumeroStep(steps.size());*/

				Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, utenteAssegnatario.getVo().getUseridUtil());
				WebSocketPublisher.getInstance().publishEvent(event); 

			}

			try{
				emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.ATTO, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
			}
			catch (Exception e) { 
				System.out.println("Errore in invio e-mail"+ e);
			}
		}
		return null;
	}

	@RequestMapping(value = "/stepWf/rifiutaWorkflow", method = RequestMethod.POST)
	public String rifiutaWorkflow(StepWfView stepWfView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) throws Throwable //l'eccezione è gestita a livello Ajax
	{

		UtenteView utenteConnesso = (UtenteView) request.getSession()
				.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

		long idEntita = 0;
		boolean ret = false;

		switch(stepWfView.getCodiceClasse()){
		case CostantiDAO.CHIUSURA_FASCICOLO:
			FascicoloWfView fascicoloWf = fascicoloWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			idEntita = fascicoloWf.getVo().getFascicolo().getId();
			ret = fascicoloWfService.rifiutaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(), stepWfView.getNote());

			if(ret){
				try{
					emailNotificationService.inviaNotifica(CostantiDAO.RIFIUTO, CostantiDAO.FASCICOLO, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}
			}
			break;

		case CostantiDAO.AUTORIZZAZIONE_INCARICO:

			boolean arbitrato = false;
			IncaricoWfView incaricoWf = incaricoWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			Incarico incarico = incaricoWf.getVo().getIncarico();
			idEntita = incarico.getId();

			if(incarico.getCollegioArbitrale().equalsIgnoreCase(Character.toString(CostantiDAO.TRUE_CHAR)))
				arbitrato = true;

			ret = incaricoWfService.rifiutaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(), stepWfView.getNote());

			if(ret){
				//invio la notifica

				//invio la e-mail
				try{
					String tipoEntita = CostantiDAO.INCARICO;
					if(arbitrato)
						tipoEntita = CostantiDAO.ARBITRATO;
					emailNotificationService.inviaNotifica(CostantiDAO.RIFIUTO, tipoEntita, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}
			}
			break;

		case CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI:

			SchedaFondoRischiWfView schedaFondoRischiWf = schedaFondoRischiWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			SchedaFondoRischi schedaFondoRischi = schedaFondoRischiWf.getVo().getSchedaFondoRischi();
			idEntita = schedaFondoRischi.getId();

			ret = schedaFondoRischiWfService.rifiutaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(), stepWfView.getNote());

			if(ret){
				//invio la notifica

				//invio la e-mail
				try{
					String tipoEntita = CostantiDAO.SCHEDA_FONDO_RISCHI;
					emailNotificationService.inviaNotifica(CostantiDAO.RIFIUTO, tipoEntita, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}
			}
			break;	
		
		case CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST:

			BeautyContestWfView beautyContestWf = beautyContestWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			BeautyContest beautyContest = beautyContestWf.getVo().getBeautyContest();
			idEntita = beautyContest.getId();

			ret = beautyContestWfService.rifiutaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(), stepWfView.getNote());

			if(ret){
				//invio la e-mail
				try{
					String tipoEntita = CostantiDAO.BEAUTY_CONTEST;
					emailNotificationService.inviaNotifica(CostantiDAO.RIFIUTO, tipoEntita, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}
			}
			break;	
			
		case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:

			ProfessionistaEsternoWfView professionistaWf = professionistaEsternoWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			idEntita = professionistaWf.getVo().getProfessionistaEsterno().getId();

			ret = professionistaEsternoWfService.rifiutaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(), stepWfView.getNote());

			if(ret){
				//invio la notifica

				//invio la e-mail
				try{
					emailNotificationService.inviaNotifica(CostantiDAO.RIFIUTO, CostantiDAO.PROFESSIONISTA_ESTERNO, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}
			}
			break;
		case CostantiDAO.AUTORIZZAZIONE_PROFORMA:

			ProformaWfView proformaWf = proformaWfService.leggiWorkflow(stepWfView.getIdWorkflow());
			idEntita = proformaWf.getVo().getProforma().getId();


			ret = proformaWfService.rifiutaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(), stepWfView.getNote());

			if(ret){
				//invio la notifica

				//invio la e-mail
				try{
					emailNotificationService.inviaNotifica(CostantiDAO.RIFIUTO, CostantiDAO.PROFORMA, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
				}
				catch (Exception e) { 
					System.out.println("Errore in invio e-mail"+ e);
				}
			}
			break;


		default:
			break;

		}

		return null;
	}

	@RequestMapping(value = "/stepWf/rifiutaWorkflowAtto", method = RequestMethod.POST)
	public String rifiutaWorkflowAtto(StepWfView stepWfView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) throws Throwable //l'eccezione è gestita a livello Ajax
	{

		UtenteView utenteConnesso = (UtenteView) request.getSession()
				.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

		AttoWfView attoWf = attoWfService.leggiWorkflow(stepWfView.getIdWorkflow());
		Atto atto = attoWf.getVo().getAtto();
		long idEntita = atto.getId();


		boolean ret = attoWfService.rifiutaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(), stepWfView.getNote());

		if(ret){
			//invio la notifica
			UtenteView assegnatario = utenteService.leggiAssegnatarioWfAtto(idEntita);
			if(assegnatario != null){

				StepWFRest stepSuccessivo = new StepWFRest();
				stepSuccessivo.setId(0);

				/*				
				//recupero il numero di attività pendenti
				List<StepWfView> steps = stepWfService.leggiAttivitaPendenti(assegnatario.getVo().getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
				StepWfView newStep = stepWfService.leggiStepCorrente(stepWfView.getIdWorkflow(), CostantiDAO.REGISTRAZIONE_ATTO);
				String codiceLingua = newStep.getVo().getConfigurazioneStepWf().getCodGruppoLingua();
				ConfigurazioneStepWfView configurazioneTradottaNew = configurazioneStepWfService.leggiConfigurazioneTradotta(codiceLingua, locale.getLanguage().toUpperCase());


				stepSuccessivo.setDescLinguaCorrente(configurazioneTradottaNew.getVo().getDescrStateTo());
				stepSuccessivo.setIdAtto(idEntita);
				stepSuccessivo.setIdAttoWf(stepWfView.getIdWorkflow());
				stepSuccessivo.setDataCreazione(df.format(newStep.getVo().getDataCreazione()));
				stepSuccessivo.setDescLinguaCorrente(configurazioneTradottaNew.getVo().getDescrStateTo());
				stepSuccessivo.setNoteSpecifiche(CostantiDAO.ATTO + " " + atto.getNumeroProtocollo());						
				stepSuccessivo.setFascicoloConValore(StringUtils.EMPTY);
				stepSuccessivo.setId(newStep.getVo().getId());
				stepSuccessivo.setCodice(stepWfView.getCodiceClasse());
				stepSuccessivo.setNumeroStep(steps.size());*/

				Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
				WebSocketPublisher.getInstance().publishEvent(event); 

			}


			//invio la e-mail
			try{
				emailNotificationService.inviaNotifica(CostantiDAO.RIFIUTO, CostantiDAO.ATTO, idEntita, locale.getLanguage().toUpperCase(), utenteConnesso.getVo().getUseridUtil());
			}
			catch (Exception e) { 
				System.out.println("Errore in invio e-mail"+ e);
			}
		}


		return null;
	}

	@RequestMapping(value = "/stepWf/validaRifiutoWorkflow", method = RequestMethod.POST)
	public String validaRifiutoWorkflow(StepWfView stepWfView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) throws Throwable //l'eccezione è gestita a livello Ajax
	{

		return PAGINA_FORM_RESPONSE_WORKFLOW;

	}

	@RequestMapping(value = "/stepWf/salva", method = RequestMethod.POST)
	public String salvaWorkflow(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated StepWfView stepWfView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response){

		// engsecurity VA
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);

		UtenteView utenteConnesso = (UtenteView) request.getSession()
				.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

		if (stepWfView.getOp().equals("confermaWorkflow")) {
			try {  
				switch(stepWfView.getCodiceClasse()){
				case CostantiDAO.CHIUSURA_FASCICOLO:

					fascicoloWfService.chiudiFascicolo(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil());

					try {
						NotificaWebView notificaWeb = new NotificaWebView();
						notificaWeb.setIdFascicolo(stepWfView.getIdFascicolo());

						FascicoloView fascicolo = fascicoloService.leggi(stepWfView.getIdFascicolo());

						notificaWeb.setNomeFascicolo(fascicolo.getVo().getNome());
						notificaWeb.setDataInserimento(new Date());
						NotificaWeb notifica = new NotificaWeb();
						notifica.setMatricolaMitt(utenteConnesso.getVo());
						Utente matricolaDest = utenteService.leggiUtenteDaUserId(fascicolo.getVo().getLegaleInterno()).getVo();
						notifica.setMatricolaDest(matricolaDest==null?utenteConnesso.getVo():matricolaDest);
						notifica.setDataNotifica(new Date());
						notifica.setKeyMessage("fascicolo.chiuso.indata");
						notifica.setJsonParam(notificaWeb.getJsonChiusuraFascicolo());
						notificaWeb.setVo(notifica);
						notificaWebService.inserisciChiusuraFascicolo(notificaWeb);

					} catch (Exception e) {
						System.out.println("Errore in invio notificaweb"+ e);
					}


					return PAGINA_FORM_WORKFLOW;
				case CostantiDAO.AUTORIZZAZIONE_INCARICO:

					return PAGINA_FORM_WORKFLOW;

				case CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI:

					return PAGINA_FORM_WORKFLOW;
				
				case CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST:

					return PAGINA_FORM_WORKFLOW;

				case CostantiDAO.AUTORIZZAZIONE_COLLEGIO_ARBITRALE:

					return PAGINA_FORM_WORKFLOW;

				case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:

					try {

						long idProfessionista = stepWfView.getIdProfessionista();

						ProfessionistaEsternoView pe = professionistaEsternoService.leggi(idProfessionista);

						NotificaWebView notificaWeb = new NotificaWebView();
						notificaWeb.setNomeLegaleEsterno(pe.getCognomeNome());
						notificaWeb.setDenominazioneStudioLegaleEsterno(pe.getStudioLegaleDenominazione());
						notificaWeb.setDataInserimento(new Date());

						NotificaWeb notifica = new NotificaWeb();
						notifica.setMatricolaMitt(utenteConnesso.getVo());
						notifica.setDataNotifica(new Date());
						notifica.setKeyMessage("professionista.esterno.inserito");
						notifica.setJsonParam(notificaWeb.getJsonInserimentoPEsterno());
						notificaWeb.setVo(notifica);
						notificaWebService.inserisciProfessionistaEsterno(notificaWeb);

					} catch (Exception e) {
						System.out.println("Errore in invio notificaweb"+ e);
					}
					return PAGINA_FORM_WORKFLOW;

				case CostantiDAO.AUTORIZZAZIONE_PROFORMA:
					return PAGINA_FORM_WORKFLOW;
				default:
					break;


				}
				model.addAttribute("successMessage", "messaggio.operazione.ok");


			} catch (Throwable e) {

				e.printStackTrace();
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			}

		}
		else if(stepWfView.getOp().equals("rifiutaWorkflow")){
			try {  
				switch(stepWfView.getCodiceClasse()){
				case CostantiDAO.CHIUSURA_FASCICOLO:
					fascicoloWfService.rifiutaWorkflow(stepWfView.getIdWorkflow(), utenteConnesso.getVo().getUseridUtil(), stepWfView.getNote());
					break;
				case CostantiDAO.AUTORIZZAZIONE_INCARICO:
				case CostantiDAO.AUTORIZZAZIONE_COLLEGIO_ARBITRALE:
				case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:
				case CostantiDAO.AUTORIZZAZIONE_PROFORMA:
				case CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI:
				case CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST:
					return PAGINA_FORM_WORKFLOW;
				default:
					break;


				}
				model.addAttribute("successMessage", "messaggio.operazione.ok");


			} catch (Throwable e) {

				e.printStackTrace();
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			}
		}
		return null;
	}

}
