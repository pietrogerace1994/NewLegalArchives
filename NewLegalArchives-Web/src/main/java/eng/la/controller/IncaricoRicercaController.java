package eng.la.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.IncaricoService;
import eng.la.business.UtenteService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.business.websocket.WebSocketPublisher;
import eng.la.business.workflow.IncaricoWfService;
import eng.la.model.IncaricoWf;
import eng.la.model.rest.CollegioArbitraleRest;
import eng.la.model.rest.Event;
import eng.la.model.rest.IncaricoRest;
import eng.la.model.rest.RicercaCollegioArbitraleRest;
import eng.la.model.rest.RicercaIncaricoRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.rest.StepWFRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.CollegioArbitraleView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.StatoIncaricoView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.costants.Costanti;
import eng.la.util.va.csrf.HTMLActionSupport;


@Controller("incaricoRicercaController")
@SessionAttributes("incaricoRicercaView")
public class IncaricoRicercaController extends BaseController  {

	private static final String MODEL_VIEW_NOME = "incaricoRicercaView"; 
	private static final String PAGINA_RICERCA_PATH = "incarico/cercaIncarico";
	private static final String PAGINA_AZIONI_CERCA_INCARICO = "incarico/azioniCercaIncarico";  
	private static final String PAGINA_AZIONI_CERCA_ARBITRALE = "incarico/azioniCercaArbitrale";
	private static final String PAGINA_AZIONI_ARBITRALE = "incarico/azioniArbitrale";
	private static final String PAGINA_AZIONI_INCARICO = "incarico/azioniIncarico";    
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	private IncaricoService incaricoService;

	@Autowired
	private IncaricoWfService incaricoWfService;  
	
	
	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;
	
	@Autowired
	private EmailNotificationService emailNotificationService;

	
	@RequestMapping(value="/incarico/eliminaIncarico", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaIncarico(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			IncaricoView incaricoView = incaricoService.leggi(id);
			incaricoService.cancella(incaricoView);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	
	@RequestMapping(value="/incarico/eliminaArbitrale", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaArbitrale(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			CollegioArbitraleView view = incaricoService.leggiCollegioArbitrale(id);
			incaricoService.cancellaCollegioArbitrale(view);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	//Controlla se l'assegnatatio è Assente -  Ritorna un assegnatario Presente se disponibile
	private UtenteView checkAssegnatarioPresente(UtenteView assegnatario,long idEntita) throws Throwable{
	
		
		boolean esito=false;
		 
		if(assegnatario.getVo()!=null && assegnatario.getVo().getAssente().equals("T")){
		IncaricoWf wf = incaricoWfService.leggiWorkflowInCorsoNotView(idEntita);
		if(wf!=null)
		esito=incaricoWfService.avanzaWorkflow(wf.getId(), assegnatario.getVo().getUseridUtil());
		if(esito){
			UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfIncarico(idEntita);
			if(assegnatarioNew==null) return assegnatario;
			if(assegnatarioNew.getVo()!=null && assegnatarioNew.getVo().getAssente().equals("T")){
				if(!utenteService.isAssegnatarioManualeCorrenteStandard(wf.getId(),CostantiDAO.AUTORIZZAZIONE_INCARICO)){	
					return	checkAssegnatarioPresente(assegnatarioNew,idEntita);	
				}
			}else{ return assegnatarioNew;}

		}//esito
		 
		}
	 
	 
		return assegnatario;
	}

	
	
	
	
	@RequestMapping(value="/incarico/avviaWorkFlowIncarico", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest avviaWorkFlowIncarico(@RequestParam("id") long id, @RequestParam("matricola_dest") String matricola_dest,
			HttpServletRequest request) {
		//DARIO AGGIUNTO @RequestParam("matricola_dest") String matricola_dest
				
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
				
		try {
				UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
				if( utenteView != null ){
					IncaricoView incaricoView = incaricoService.leggi(id);
					if( incaricoView.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_BOZZA)){
						
						//DARIO C **************************************************************************************************
						//boolean ret = incaricoWfService.avviaWorkflow(id, utenteView.getVo().getUseridUtil(), false);
						boolean ret = incaricoWfService.avviaWorkflow(id, utenteView.getVo().getUseridUtil(), false,matricola_dest);
						//**********************************************************************************************************
						
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
						if(ret){
							
							//invio la notifica
							
							StepWFRest stepSuccessivo = new StepWFRest();
							stepSuccessivo.setId(0);
					
							
							UtenteView assegnatario = utenteService.leggiAssegnatarioWfIncarico(id);
							
							if(assegnatario != null){
							
								//MS controllo Assegnatario Assente
							assegnatario=checkAssegnatarioPresente(assegnatario, id);
								
							Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(event); 
	
						}
						//invio la e-mail
						try{
							//DARIO C ***********************************************************************************************************************************************************************
							//emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.INCARICO, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil());
							emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.INCARICO, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil(),matricola_dest);
							//*****************************************************************************************************************************************************************************
							
						}
						catch (Exception e) { 
							System.out.println("Errore in invio e-mail"+ e);
						}
					}
					}else {
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.incarico.non.valido");
					}
				}else{
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
				}
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return risultato;
	}

	@RequestMapping(value="/incarico/avviaWorkFlowIncaricoArbitrale", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest avviaWorkFlowIncaricoArbitrale(@RequestParam("id") long id, @RequestParam("matricola_dest") String matricola_dest,
			HttpServletRequest request) {
		//DARIO AGGIUNTO @RequestParam("matricola_dest") String matricola_dest
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if( utenteView != null ){
				CollegioArbitraleView view = incaricoService.leggiCollegioArbitrale(id);
				if( view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_BOZZA)){
					
					//DARIO C **************************************************************************************************
					//boolean ret = incaricoWfService.avviaWorkflow(id, utenteView.getVo().getUseridUtil(), true);
					boolean ret = incaricoWfService.avviaWorkflow(id, utenteView.getVo().getUseridUtil(), true, matricola_dest);
					//*********************************************************************************************************
					
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
					if(ret){
						
						UtenteView assegnatario = utenteService.leggiAssegnatarioWfIncarico(id);
						
						if(assegnatario != null){
							
						StepWFRest stepSuccessivo = new StepWFRest();
						stepSuccessivo.setId(0);
						
						//MS controllo Assegnatario Assente	
						assegnatario=checkAssegnatarioPresente(assegnatario, id);
						
						Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
						WebSocketPublisher.getInstance().publishEvent(event); 


						}
						//invio la e-mail
						try{
							//DARIO C ***********************************************************************************************************************************************************************
							//emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.ARBITRATO, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil());
							emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.ARBITRATO, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil(),matricola_dest);
							//*******************************************************************************************************************************************************************************
						}
						catch (Exception e) { 
							System.out.println("Errore in invio e-mail"+ e);
						}
					}
				}else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.incarico.non.valido");
				}
			}else{
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
  

	@RequestMapping(value="/incarico/riportaInBozzaWorkFlowIncarico", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest riportaInBozzaWorkFlowIncarico(@RequestParam("id") long id, HttpServletRequest request) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if( utenteView != null ){
				IncaricoView view = incaricoService.leggi(id);
				if( !view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_BOZZA) ){ 
					//recupero l'eventuale utente corrente del workflow per notificargli la rimozione dell'incarico
					UtenteView assegnatario = utenteService.leggiAssegnatarioWfIncarico(id);

					boolean avviato = incaricoWfService.riportaInBozzaWorkflow(id, utenteView.getVo().getUseridUtil(), false);
					if( avviato ){
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
						if(assegnatario != null){
							//invio la notifica
							StepWFRest stepSuccessivo = new StepWFRest();
							stepSuccessivo.setId(0);
							Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(event); 
						}
					}else{
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.WARN, "messaggio.ko");
					}
				}else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.incarico.non.valido");
				}
			}else{
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}
			
			try{
				emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.INCARICO, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil());
			}
			catch (Exception e) { 
				System.out.println("Errore in invio e-mail"+ e);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
  
	
	@RequestMapping(value="/incarico/riportaInBozzaWorkFlowIncaricoArbitrale", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest riportaInBozzaWorkFlowIncaricoArbitrale(@RequestParam("id") long id, HttpServletRequest request) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if( utenteView != null ){
				CollegioArbitraleView view = incaricoService.leggiCollegioArbitrale(id);
				if( !view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_BOZZA) ){ 
					//recupero l'eventuale utente corrente del workflow per notificargli la rimozione dell'incarico
					UtenteView assegnatario = utenteService.leggiAssegnatarioWfIncarico(id);

					boolean avviato = incaricoWfService.riportaInBozzaWorkflow(id, utenteView.getVo().getUseridUtil(), true);
					if( avviato ){
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
						if(assegnatario != null){
							//invio la notifica
							StepWFRest stepSuccessivo = new StepWFRest();
							stepSuccessivo.setId(0);
							Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(event); 
						}
		
					}else{
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.WARN, "messaggio.ko");
					}
				}else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.incarico.non.valido");
				}
			}else{
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}
			
			try{
				emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.INCARICO, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil());
			}
			catch (Exception e) { 
				System.out.println("Errore in invio e-mail"+ e);
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	
	@RequestMapping(value="/incarico/arretraWorkFlowIncarico", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest arretraWorkFlowIncarico(@RequestParam("id") long id, HttpServletRequest request) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if( utenteView != null ){
				IncaricoView view = incaricoService.leggi(id);
				if( !view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_BOZZA) && 
						!view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_AUTORIZZATO) ){ 
					//recupero l'eventuale utente corrente del workflow per notificargli la rimozione dell'incarico
					UtenteView assegnatarioOld = utenteService.leggiAssegnatarioWfIncarico(id);
					boolean discard = incaricoWfService.discardStep(id, utenteView.getVo().getUseridUtil(), false);
					if( discard ){
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
						//recupero l'eventuale nuovo utente corrente del workflow per notificargli l'assegnazione dell'incarico
						UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfIncarico(id);
						if(assegnatarioOld != null){
							//invio la notifica
							StepWFRest stepPrecedente = new StepWFRest();
							stepPrecedente.setId(0);
							Event eventOld = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepPrecedente, assegnatarioOld.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(eventOld); 

						}
						if(assegnatarioNew != null){
							//invio la notifica
							StepWFRest stepSuccessivo = new StepWFRest();
							stepSuccessivo.setId(0);
							Event eventNew = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatarioNew.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(eventNew); 

						}
					}else{
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.WARN, "messaggio.ko");
					}
				}else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.incarico.non.valido");
				}
			}else{
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
  
	
	@RequestMapping(value="/incarico/arretraWorkFlowIncaricoArbitrale", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest arretraWorkFlowIncaricoArbitrale(@RequestParam("id") long id, HttpServletRequest request) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if( utenteView != null ){
				CollegioArbitraleView view = incaricoService.leggiCollegioArbitrale(id);
				if( !view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_BOZZA) && 
						!view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_AUTORIZZATO) ){ 
					//recupero l'eventuale utente corrente del workflow per notificargli la rimozione dell'incarico
					UtenteView assegnatarioOld = utenteService.leggiAssegnatarioWfIncarico(id);
					boolean discard = incaricoWfService.discardStep(id, utenteView.getVo().getUseridUtil(), true);
					if( discard ){
						
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
						UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfIncarico(id);
						if(assegnatarioOld != null){
							//invio la notifica
							StepWFRest stepPrecedente = new StepWFRest();
							stepPrecedente.setId(0);
							Event eventOld = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepPrecedente, assegnatarioOld.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(eventOld); 

						}
						if(assegnatarioNew != null){
							//invio la notifica
							StepWFRest stepSuccessivo = new StepWFRest();
							stepSuccessivo.setId(0);
							Event eventNew = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatarioNew.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(eventNew); 

						}
					}else{
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.WARN, "messaggio.ko");
					}
				}else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.incarico.non.valido");
				}
			}else{
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
  

	@RequestMapping(value = "/incarico/ricerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaIncaricoRest cercaIncarico(HttpServletRequest request, Locale locale) {
		try {  

			int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA : NumberUtils.toInt(request.getParameter("limit"));
			String offset = request.getParameter("offset");
			int numeroPagina = offset == null || offset.equals("0") ? 1
					: (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
			String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
			String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
			if( request.getParameter("sort") == null ){
				tipoOrdinamento = "DESC";
			}
			String nome = request.getParameter("nomeIncarico") == null ? "" : URLDecoder.decode(request.getParameter("nomeIncarico"),"UTF-8");
			String statoIncaricoCode = request.getParameter("statoIncaricoCode") == null ? "" : request.getParameter("statoIncaricoCode");
			String nomeFascicolo = request.getParameter("nomeFascicolo") == null ? "" : URLDecoder.decode( request.getParameter("nomeFascicolo"),"UTF-8");
			
			String dal = request.getParameter("dal") == null ? "" : URLDecoder.decode(request.getParameter("dal"),"UTF-8");
			String al = request.getParameter("al") == null ? "" :  URLDecoder.decode(request.getParameter("al"),"UTF-8");
			 
			
			long professionistaEsternoId = StringUtils.isNotBlank(request.getParameter("professionistaEsternoId"))? NumberUtils.toLong(request.getParameter("professionistaEsternoId")): 0 ;
			 
			ListaPaginata<IncaricoView> lista = (ListaPaginata<IncaricoView>) incaricoService.cerca(nome, null,professionistaEsternoId, dal, al, statoIncaricoCode, nomeFascicolo, numElementiPerPagina, numeroPagina,
					ordinamento, tipoOrdinamento);
			RicercaIncaricoRest ricercaModelRest = new RicercaIncaricoRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<IncaricoRest> rows = new ArrayList<IncaricoRest>();
			for (IncaricoView view : lista) {
				IncaricoRest incaricoRest = new IncaricoRest();
				incaricoRest.setId(view.getVo().getId());
				incaricoRest.setNomeIncarico(view.getVo().getNomeIncarico());
				incaricoRest.setCommento(view.getVo().getCommento());
				incaricoRest.setAnno(DateUtil.getAnno(view.getVo().getDataCreazione()) + "");
				incaricoRest.setDataCreazione(DateUtil.formattaData(view.getVo().getDataCreazione().getTime()));
				incaricoRest.setNomeFascicolo(view.getVo().getFascicolo().getNome());
				StatoIncaricoView statoIncaricoView = anagraficaStatiTipiService.leggiStatoIncarico(view.getVo().getStatoIncarico().getCodGruppoLingua(), locale.getLanguage().toUpperCase());
				incaricoRest.setStato(statoIncaricoView.getVo().getDescrizione());
				
				String assegnatario="-"; 
				if(view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("APAP")
						||view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("APAP1")
						||view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("AAUT")
						||view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("APAP2")){
					UtenteView utenteV=utenteService.leggiAssegnatarioWfIncarico(view.getVo().getId());
					assegnatario=utenteV == null ? "N.D." : utenteV.getVo().getNominativoUtil();
				}
				
				
				 
				incaricoRest.setAzioni("<p id='containerAzioniRigaIncarico" + view.getVo().getId() + "' title='"+assegnatario+"' alt='"+assegnatario+"' ></p>");
				rows.add(incaricoRest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}
	

	@RequestMapping(value = "/incarico/ricercaArbitrale", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaCollegioArbitraleRest cercaArbitrale(HttpServletRequest request, Locale locale) {
		try { 
			 
			String offset = request.getParameter("offset");
			int numeroPagina = offset == null || offset.equals("0") ? 1
					: (NumberUtils.toInt(offset) / ELEMENTI_PER_PAGINA) + 1;
			String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
			String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
			if( request.getParameter("sort") == null ){
				tipoOrdinamento = "DESC";
			}
			String nome = request.getParameter("nomeArbitrale") == null ? "" : request.getParameter("nomeArbitrale");
			String statoIncaricoArbitraleCode = request.getParameter("statoIncaricoArbitraleCode") == null ? "" : request.getParameter("statoIncaricoArbitraleCode");
			String nomeFascicolo = request.getParameter("nomeFascicolo") == null ? "" : URLDecoder.decode(request.getParameter("nomeFascicolo"),"UTF-8");
			
			String dal = request.getParameter("dalArbitrale") == null ? "" : URLDecoder.decode(request.getParameter("dalArbitrale"),"UTF-8");
			String al = request.getParameter("alArbitrale") == null ? "" :  URLDecoder.decode(request.getParameter("alArbitrale"),"UTF-8");
			 
			
			long professionistaEsternoId = StringUtils.isNotBlank(request.getParameter("professionistaEsternoId"))? NumberUtils.toLong(request.getParameter("professionistaEsternoId")): 0 ;
			 
			int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA : NumberUtils.toInt(request.getParameter("limit"));
			ListaPaginata<CollegioArbitraleView> lista = (ListaPaginata<CollegioArbitraleView>) incaricoService.cercaArbitrale(nome, null,null,professionistaEsternoId, dal, al, statoIncaricoArbitraleCode, nomeFascicolo, numElementiPerPagina, numeroPagina,
					ordinamento, tipoOrdinamento);
			RicercaCollegioArbitraleRest ricercaModelRest = new RicercaCollegioArbitraleRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<CollegioArbitraleRest> rows = new ArrayList<CollegioArbitraleRest>();
			for (CollegioArbitraleView view : lista) {
				CollegioArbitraleRest rest = new CollegioArbitraleRest(); 
				rest.setId(view.getVo().getId());
				rest.setNomeCollegioArbitrale(view.getVo().getNomeCollegioArbitrale());				
				rest.setAnno(DateUtil.getAnno(view.getVo().getDataCreazione()) + "");
				rest.setDataCreazione(DateUtil.formattaData(view.getVo().getDataCreazione().getTime()));
				rest.setNomeIncarico(view.getVo().getIncarico().getNomeIncarico());
				StatoIncaricoView statoIncaricoView = anagraficaStatiTipiService.leggiStatoIncarico(view.getVo().getStatoIncarico().getCodGruppoLingua(), locale.getLanguage().toUpperCase());
				rest.setStato(statoIncaricoView.getVo().getDescrizione());
				rest.setAzioni("<p id='containerAzioniRigaArbitrale" + view.getVo().getId() + "'></p>");
				rows.add(rest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}
	 
	
	@RequestMapping(value = "/incarico/cerca", method = RequestMethod.GET)
	public String cercaIncarichi(HttpServletRequest request, Model model, Locale locale) {
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		
		try {
			IncaricoView view = new IncaricoView();
			view.setListaStatoIncarico(anagraficaStatiTipiService.leggiStatiIncarico(locale.getLanguage().toUpperCase()));
			model.addAttribute(MODEL_VIEW_NOME, view);
			return PAGINA_RICERCA_PATH;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}
  

	@RequestMapping(value = "/incarico/caricaAzioniArbitrale", method = RequestMethod.POST)
	public String caricaAzioniArbitrale(@RequestParam("idIncarico") long idIncarico, HttpServletRequest req,
			Locale locale) {
		try {

			// TODO: LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI
			// SUL FASCICOLO
			req.setAttribute("idIncarico", idIncarico);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		if( req.getParameter("onlyContent") != null  ){
			return PAGINA_AZIONI_ARBITRALE;
		}
		return PAGINA_AZIONI_CERCA_ARBITRALE;
	}

	

	@RequestMapping(value = "/incarico/caricaAzioniIncarico", method = RequestMethod.POST)
	public String caricaAzioniIncarico(@RequestParam("idIncarico") long idIncarico, HttpServletRequest req,
			Locale locale) {
		try {

			// TODO: LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI
			// SULL'INCARICO
			req.setAttribute("idIncarico", idIncarico);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if( req.getParameter("onlyContent") != null  ){
			return PAGINA_AZIONI_INCARICO;
		}
		return PAGINA_AZIONI_CERCA_INCARICO;
	}

	 
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		  
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		 
	}
  

}
