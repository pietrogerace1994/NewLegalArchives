package eng.la.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.IncaricoService;
import eng.la.business.ProformaService;
import eng.la.business.SocietaService;
import eng.la.business.UtenteService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.business.websocket.WebSocketPublisher;
import eng.la.business.workflow.ProformaWfService;
import eng.la.model.ProformaWf;
import eng.la.model.RIncaricoProformaSocieta;
import eng.la.model.RProformaFattura;
import eng.la.model.rest.Event;
import eng.la.model.rest.ProformaRest;
import eng.la.model.rest.RicercaProformaRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.rest.StepWFRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.ProformaView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.StatoProformaView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("proformaRicercaController") 
public class ProformaRicercaController extends BaseController {
	private static final String MODEL_VIEW_NOME = "proformaRicercaView"; 
	private static final String PAGINA_RICERCA_PATH = "proforma/cercaProforma";
	private static final String PAGINA_AZIONI_CERCA_PROFORMA = "proforma/azioniCercaProforma";
	private static final String PAGINA_AZIONI_PROFORMA = "proforma/azioniProforma";
 
	@Autowired
	private SocietaService societaService;

	public void setSocietaService(SocietaService societaService) {
		this.societaService = societaService;
	}

	@Autowired
	private ProformaWfService proformaWfService;

	public void setProformaWfService(ProformaWfService proformaWfService) {
		this.proformaWfService = proformaWfService;
	} 
	@Autowired
	private UtenteService utenteService;

	public void setUtenteService(UtenteService utenteService) {
		this.utenteService = utenteService;
	} 

	@Autowired
	private ProformaService proformaService;

	public void setProformaService(ProformaService proformaService) {
		this.proformaService = proformaService;
	}

	@Autowired
	private IncaricoService incaricoService;

	public void setIncaricoService(IncaricoService incaricoService) {
		this.incaricoService = incaricoService;
	} 

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	public void setAnagraficaStatiTipiService(AnagraficaStatiTipiService anagraficaStatiTipiService) {
		this.anagraficaStatiTipiService = anagraficaStatiTipiService;
	}

	@Autowired
	private EmailNotificationService emailNotificationService;

	public void setEmailNotificationService(EmailNotificationService emailNotificationService) {
		this.emailNotificationService = emailNotificationService;
	}

 

	@RequestMapping(value = "/proforma/eliminaProforma", produces = "application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaProforma(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {
			ProformaView proformaView = proformaService.leggi(id);
			proformaService.cancella(proformaView);
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
		ProformaWf wf = proformaWfService.leggiWorkflowInCorsoNotView(idEntita);
		if(wf!=null)
		esito=proformaWfService.avanzaWorkflow(wf.getId(), assegnatario.getVo().getUseridUtil());
		if(esito){
			UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfIncarico(idEntita);
			if(assegnatarioNew==null) return assegnatario;
			if(assegnatarioNew.getVo()!=null && assegnatarioNew.getVo().getAssente().equals("T")){
				if(!utenteService.isAssegnatarioManualeCorrenteStandard(wf.getId(),CostantiDAO.AUTORIZZAZIONE_PROFORMA)){	
					return	checkAssegnatarioPresente(assegnatarioNew,idEntita);	
				}
			}else{ return assegnatarioNew;}

		}//esito
		 
		}
	 
	 
		return assegnatario;
	}
	
	
	@RequestMapping(value = "/proforma/avviaWorkFlowProforma", produces = "application/json")
	public @ResponseBody RisultatoOperazioneRest avviaWorkFlowProforma(@RequestParam("id") long id, @RequestParam("matricola_dest") String matricola_dest,
			HttpServletRequest request) {
		//DARIO AGGIUNTO @RequestParam("matricola_dest") String matricola_dest

		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		try {
			UtenteView utenteView = (UtenteView) request.getSession()
					.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if (utenteView != null) {
				ProformaView view = proformaService.leggi(id);
				if (view.getVo().getStatoProforma().getCodGruppoLingua().equals(Costanti.PROFORMA_STATO_BOZZA)) {
					
					//DARIO C **************************************************************************************************
					//boolean ret = proformaWfService.avviaWorkflow(id, utenteView.getVo().getUseridUtil());
					boolean ret = proformaWfService.avviaWorkflow(id, utenteView.getVo().getUseridUtil(),matricola_dest);
					//**********************************************************************************************************
					
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");

					if (ret) {

						// invio la notifica
						
						UtenteView assegnatario = utenteService.leggiAssegnatarioWfProforma(id);
						
						if (assegnatario != null) {

							StepWFRest stepSuccessivo = new StepWFRest();
							stepSuccessivo.setId(0);
							
							assegnatario=checkAssegnatarioPresente(assegnatario, id);
				
							Event event = WebSocketPublisher.getInstance().createEvent(
									Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo,
									assegnatario.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(event);
						}

						// invio la e-mail
						try {
							//DARIO C *************************************************************************************
//							emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.PROFORMA, id,
//									request.getLocale().getLanguage().toUpperCase(),
//									utenteView.getVo().getUseridUtil());
							emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.PROFORMA, id,
									request.getLocale().getLanguage().toUpperCase(),
									utenteView.getVo().getUseridUtil(),matricola_dest);
							//*********************************************************************************************
						} catch (Exception e) {
							System.out.println("Errore in invio e-mail" + e);
						}
					}
				} else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.proforma.non.valido");
				}
			} else {
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@RequestMapping(value = "/proforma/riportaInBozzaWorkFlowProforma", produces = "application/json")
	public @ResponseBody RisultatoOperazioneRest riportaInBozzaWorkFlowProforma(@RequestParam("id") long id,
			HttpServletRequest request) {


		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UtenteView utenteView = (UtenteView) request.getSession()
					.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if (utenteView != null) {
				ProformaView view = proformaService.leggi(id);
				if (!view.getVo().getStatoProforma().getCodGruppoLingua().equals(Costanti.PROFORMA_STATO_BOZZA)) {
					// recupero l'eventuale utente corrente del workflow per
					// notificargli la rimozione dell'incarico
					UtenteView assegnatario = utenteService.leggiAssegnatarioWfProforma(id);
					boolean ret = proformaWfService.riportaInBozzaWorkflow(id, utenteView.getVo().getUseridUtil());
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
					if (ret && assegnatario != null) {
						if (assegnatario != null) {
							// invio la notifica
							StepWFRest stepSuccessivo = new StepWFRest();
							stepSuccessivo.setId(0);
							Event event = WebSocketPublisher.getInstance().createEvent(
									Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo,
									assegnatario.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(event);
						}
						// invio la e-mail
					}
					try {
						emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.PROFORMA, id,
								request.getLocale().getLanguage().toUpperCase(),
								utenteView.getVo().getUseridUtil());
					} catch (Exception e) {
						System.out.println("Errore in invio e-mail" + e);
					}
				} else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.proforma.non.valido");
				}
			} else {
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@RequestMapping(value = "/proforma/arretraWorkFlowProforma", produces = "application/json")
	public @ResponseBody RisultatoOperazioneRest arretraWorkFlowProforma(@RequestParam("id") long id,
			HttpServletRequest request) {


		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UtenteView utenteView = (UtenteView) request.getSession()
					.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if (utenteView != null) {
				ProformaView view = proformaService.leggi(id);
				if (!view.getVo().getStatoProforma().getCodGruppoLingua().equals(Costanti.PROFORMA_STATO_BOZZA) && !view
						.getVo().getStatoProforma().getCodGruppoLingua().equals(Costanti.PROFORMA_STATO_AUTORIZZATO)) {
					// recupero l'eventuale utente corrente del workflow per
					// notificargli la rimozione dell'incarico
					UtenteView assegnatarioOld = utenteService.leggiAssegnatarioWfProforma(id);
					boolean ret = proformaWfService.discardStep(id, utenteView.getVo().getUseridUtil());
					if (ret) {
						UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfProforma(id);
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

					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
				} else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.proforma.non.valido");
				}
			} else {
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@RequestMapping(value = "/proforma/ricerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaProformaRest cercaProforma(HttpServletRequest request, Locale locale) {
		// engsecurity VA json
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {
			int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA
					: NumberUtils.toInt(request.getParameter("limit"));
			String offset = request.getParameter("offset");
			int numeroPagina = offset == null || offset.equals("0") ? 1
					: (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
			String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
			String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
			if( request.getParameter("sort") == null ){
				tipoOrdinamento = "DESC";
			}
			String nome = request.getParameter("nomeProforma") == null ? ""
					: URLDecoder.decode(request.getParameter("nomeProforma"), "UTF-8");
			String statoCode = request.getParameter("statoCode") == null ? "" : request.getParameter("statoCode");
			String nomeFascicolo = request.getParameter("nomeFascicolo") == null ? ""
					: URLDecoder.decode(request.getParameter("nomeFascicolo"), "UTF-8");
			String nomeIncarico = request.getParameter("nomeIncarico") == null ? ""
					: URLDecoder.decode(request.getParameter("nomeIncarico"), "UTF-8");
			long societaAddebito = request.getParameter("societaAddebito") == null ? -1
					: NumberUtils.toLong(request.getParameter("societaAddebito"));
			String dal = request.getParameter("dal") == null ? ""
					: URLDecoder.decode(request.getParameter("dal"), "UTF-8");
			String al = request.getParameter("al") == null ? ""
					: URLDecoder.decode(request.getParameter("al"), "UTF-8");
			String fatturato = request.getParameter("fatturato") == null ? ""
					: URLDecoder.decode(request.getParameter("fatturato"), "UTF-8");
			String contabilizzato = request.getParameter("contabilizzato") == null ? ""
					: URLDecoder.decode(request.getParameter("contabilizzato"), "UTF-8"); 
		 
			ListaPaginata<ProformaView> lista = (ListaPaginata<ProformaView>) proformaService.cerca(nome, statoCode,
					nomeFascicolo, nomeIncarico, societaAddebito, dal, al, numElementiPerPagina, numeroPagina, ordinamento,
					tipoOrdinamento,fatturato,contabilizzato);
			
	 
			
			RicercaProformaRest ricercaModelRest = new RicercaProformaRest();
			
 
			 
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<ProformaRest> rows = new ArrayList<ProformaRest>();
			for (ProformaView view : lista) {
				ProformaRest proformaRest = new ProformaRest();
				
				proformaRest.setId(view.getVo().getId());
				proformaRest.setNomeProforma(view.getVo().getNomeProforma());
				proformaRest.setFattura("");
				proformaRest.setContabilizzata("");
				if(view.getVo().getStatoProforma()!=null && view.getVo().getStatoProforma().getCodGruppoLingua().equalsIgnoreCase("A")){
				RProformaFattura rProformaFattura=proformaService.getRProformaFattura(view.getVo().getId());
				if(rProformaFattura!=null){
					proformaRest.setFattura(rProformaFattura.getFattura().getNumeroFattura());	
				if(rProformaFattura.getFattura().getDataRegistrazione()!=null && rProformaFattura.getFattura().getnProtocolloFiscale()!=null && rProformaFattura.getFattura().getnProtocolloFiscale().intValue()>0)	
					proformaRest.setContabilizzata(DateUtil.formattaData(rProformaFattura.getFattura().getDataRegistrazione().getTime()));
				}	
				} 
				//proformaRest.setNumero(view.getVo().getNumero());
				if(view.getVo().getAnnoEsercizioFinanziario()!=null)
				proformaRest.setAnnoEsercizioFinanziario(view.getVo().getAnnoEsercizioFinanziario().intValue()+"");
				if(view.getVo().getAnnoEsercizioFinanziario()==null)
					proformaRest.setAnnoEsercizioFinanziario("");	
				proformaRest.setDataInserimento(DateUtil.formattaData(view.getVo().getDataInserimento().getTime()));
				if (view.getVo().getRIncaricoProformaSocietas() != null
						&& view.getVo().getRIncaricoProformaSocietas().size() > 0) {
					RIncaricoProformaSocieta rIncaricoProformaSocieta = view.getVo().getRIncaricoProformaSocietas()
							.iterator().next();
					IncaricoView incaricoView = incaricoService.leggi(rIncaricoProformaSocieta.getIncarico().getId());
					String societaDesc = "";
					for( RIncaricoProformaSocieta tmp : view.getVo().getRIncaricoProformaSocietas() ){ 
						SocietaView societaView = societaService.leggi(tmp.getSocieta().getId());
						if(societaView.getVo()!=null)
							societaDesc += societaView.getVo().getRagioneSociale() + "; ";
					} 
					proformaRest.setNomeSocieta(societaDesc);
					
					if (incaricoView.getVo() != null) {
						proformaRest.setNomeIncarico(incaricoView.getVo().getNomeIncarico());
					} 
					
				}
				StatoProformaView statoProformaView = anagraficaStatiTipiService.leggiStatoProforma(
						view.getVo().getStatoProforma().getCodGruppoLingua(), locale.getLanguage().toUpperCase());

				String incaricato = "-";
				UtenteView assegnatarioView = utenteService.leggiAssegnatarioWfProforma(view.getVo().getId());
				if (assegnatarioView != null && assegnatarioView.getVo() != null) {
					incaricato = StringEscapeUtils.escapeJavaScript(assegnatarioView.getVo().getNominativoUtil());
				}
				proformaRest.setStato("<a href='javascript:void(0)' title='" + incaricato + "'>"
						+ statoProformaView.getVo().getDescrizione() + "</a>");
				proformaRest.setAzioni("<p id='containerAzioniRigaProforma" + view.getVo().getId() + "'></p>");
				rows.add(proformaRest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}
	

	@RequestMapping(value = "/proforma/cerca", method = RequestMethod.GET)
	public String cercaProforma(HttpServletRequest request, Model model, Locale locale) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {
			ProformaView view = new ProformaView();
			model.addAttribute(MODEL_VIEW_NOME, view);
			return PAGINA_RICERCA_PATH;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/proforma/caricaAzioniProforma", method = RequestMethod.POST)
	public String caricaAzioniProforma(@RequestParam("idProforma") long idProforma, HttpServletRequest req,
			Locale locale) {
		// engsecurity VA - errore 
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(req);
        //removeCSRFToken(request);
		try {

			// TODO: LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI
			// SUL PROFORMA
			req.setAttribute("idProforma", idProforma);
			
			RProformaFattura rProformaFattura=proformaService.getRProformaFattura(idProforma);
			if(rProformaFattura!=null){
				req.setAttribute("isFatturato", "T");	 
			if(rProformaFattura.getFattura().getDataRegistrazione()!=null && rProformaFattura.getFattura().getnProtocolloFiscale()!=null && rProformaFattura.getFattura().getnProtocolloFiscale().intValue()>0)	
				req.setAttribute("isContabilizzato", "T");
			else
				req.setAttribute("isContabilizzato", "F");	
			}else{
				req.setAttribute("isFatturato", "F");
				req.setAttribute("isContabilizzato", "F");
			}	
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if (req.getParameter("onlyContent") != null) {

			return PAGINA_AZIONI_PROFORMA;
		}

		return PAGINA_AZIONI_CERCA_PROFORMA;
	} 

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}


	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
	}
}
