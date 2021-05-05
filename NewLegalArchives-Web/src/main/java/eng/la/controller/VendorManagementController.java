package eng.la.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.AutorizzazioneService;
import eng.la.business.CriterioService;
import eng.la.business.FascicoloService;
import eng.la.business.IncaricoService;
import eng.la.business.NazioneService;
import eng.la.business.ProfessionistaEsternoService;
import eng.la.business.ReportingService;
import eng.la.business.SpecializzazioneService;
import eng.la.business.UtenteService;
import eng.la.business.VendorManagementService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.model.Controparte;
import eng.la.model.Criterio;
import eng.la.model.Incarico;
import eng.la.model.Nazione;
import eng.la.model.Specializzazione;
import eng.la.model.VendorManagement;
import eng.la.model.rest.IncaricoRest;
import eng.la.model.rest.NazioneRest;
import eng.la.model.rest.RicercaIncaricoRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.rest.SpecializzazioneRest;
import eng.la.model.rest.VendormanagementRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.CriterioView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.NazioneView;
import eng.la.model.view.RProfEstSpecView;
import eng.la.model.view.RProfessionistaNazioneView;
import eng.la.model.view.SemestreView;
import eng.la.model.view.SpecializzazioneView;
import eng.la.model.view.StatoFascicoloView;
import eng.la.model.view.StatoIncaricoView;
import eng.la.model.view.TabellaSemestriView;
import eng.la.model.view.UtenteView;
import eng.la.model.view.VendorManagementView;
import eng.la.persistence.CostantiDAO;
import eng.la.util.DateUtil;
import eng.la.util.DateUtil2;
import eng.la.util.ListaPaginata;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("vendorManagementController")
@SessionAttributes("vendorManagementView")
public class VendorManagementController  extends BaseController {

	private static final Logger logger = Logger.getLogger(VendorManagementController.class);

	private static final String MODEL_VIEW_NOME = "vendorManagementView";
	private static final String PAGINA_AZIONI_CERCA_INCARICO = "vendormanagement/azioniCercaIncarico";  
	private static final String PAGINA_AZIONI_INCARICO = "vendormanagement/azioniIncarico"; 
	private static final String BUTTON_RINVIA_VOTAZIONE = "vendormanagement/buttonRinviaVotazione"; 

	@Autowired
	private IncaricoService incaricoService;

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;
	
	@Autowired
	private FascicoloService fascicoloService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private ProfessionistaEsternoService professionistaEsternoService;

	@Autowired
	private VendorManagementService vendorManagementService;

	@Autowired
	private CriterioService criterioService;

	@Autowired
	private SpecializzazioneService specializzazioneService;

	@Autowired
	private NazioneService nazioneService;
	
	@Autowired
	private EmailNotificationService emailNotificationService;
	
	@Autowired
	private AutorizzazioneService autorizzazioneService;
	
	@Autowired
	private ReportingService reportingService;

	
	@RequestMapping(value = "/vendormanagement/visualizzaVotazioni", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String visualizzaVotazioni(HttpServletRequest request, Model model, Locale locale) {
		VendorManagementView vendorManagementView = new VendorManagementView();

		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try 
		{
			String idProfEst_req=request.getParameter("idProfEst");
			Long idProfEst = Long.valueOf(idProfEst_req);

			// Legale Interno Owner
			// UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			// String matricolaUtil = utenteView.getVo().getMatricolaUtil();

			// Lista di votazioni effettuate per il professionista esterno
			List<VendorManagementView> listaPerConta2= vendorManagementService.leggi(locale.getLanguage().toUpperCase());
			List<VendorManagementView> listaPerConta = filtraPerProfEst(idProfEst,listaPerConta2);

			if(listaPerConta.size()==0) {
				vendorManagementView.setNessunaVotazione(true);
			}
			else if(listaPerConta.size()>0) {
				vendorManagementView.setNessunaVotazione(false);

				// ----- TABELLE SEMESTRI -----
				List<String> listaSemestri = estraiSemestriDaListaIncarico(listaPerConta);
				List<NazioneView> listaNazioni = estraiNazioniDaListaIncarico(listaPerConta);
				List<SpecializzazioneView> listaSpecializzazioni = estraiSpecializzazioniDaListaIncarico(listaPerConta);

				//se non ho trovato nazioni o specializzazioni nella tabella di relazione allora aggiungo quelle del voto
				if(listaNazioni.size()==0) {
					Nazione nazItalia = new Nazione();
					nazItalia.setCodGruppoLingua(Costanti.CODGRUPPOLINGUA_NAZIONE_ITALIA);
					nazItalia.setDescrizione(Costanti.DESCRIZIONE_NAZIONE_ITALIA);
					NazioneView nazViewItalia = new NazioneView();
					nazViewItalia.setVo(nazItalia);
					listaNazioni.add(nazViewItalia);
				}
				if(listaSpecializzazioni.size()==0) {
					Specializzazione spec = new Specializzazione();
					spec.setCodGruppoLingua(Costanti.CODGRUPPOLINGUA_SPECIALIZZAZIONE_ALTRO);
					spec.setDescrizione(Costanti.DESCRIZIONE_SPECIALIZZAZIONE_ALTRO);
					SpecializzazioneView specView = new SpecializzazioneView();
					specView.setVo(spec);
					listaSpecializzazioni.add(specView);
				}

				List<TabellaSemestriView> listTabelleSemestriView = new ArrayList<TabellaSemestriView>();
				for(NazioneView nazView:listaNazioni) {

					String codNaz=nazView.getVo().getCodGruppoLingua();
					String nazDesc=nazView.getVo().getDescrizione();

					for(SpecializzazioneView specView: listaSpecializzazioni) {

						String codSpec=specView.getVo().getCodGruppoLingua();
						String specDesc=specView.getVo().getDescrizione();
						
						List<VendorManagementView> lista = filtraPerNazioneSpecializzazione(codNaz, codSpec, listaPerConta);
						
						if(lista.size() > 0){
							
							TabellaSemestriView tabellaSemestri=creaTabellaSemestri(lista, listaSemestri, idProfEst);
							tabellaSemestri.setNazioneDesc(nazDesc);
							tabellaSemestri.setSpecializzazioneDesc(specDesc);
							
							listTabelleSemestriView.add(tabellaSemestri);
						}
					}
				}
				vendorManagementView.setListTabelleSemestriView(listTabelleSemestriView);
			}
		} 
		catch (Throwable e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		vendorManagementView.setVo( new VendorManagement() );
		model.addAttribute(MODEL_VIEW_NOME, vendorManagementView);
		return "vendormanagement/subformVisualizzaVotazioni";
	}

	@RequestMapping("/vendormanagement/votazioni")
	public String votazioni(HttpServletRequest request, Model model, Locale locale) {
		VendorManagementView vendorManagementView = new VendorManagementView();

		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try 
		{

		} 
		catch (Throwable e) {
			e.printStackTrace();
		}

		vendorManagementView.setVo( new VendorManagement() );
		model.addAttribute(MODEL_VIEW_NOME, vendorManagementView);
		return "vendormanagement/formVotazioni";
	}

	@RequestMapping(value = "/vendormanagement/caricaAzioniIncarico", method = RequestMethod.POST)
	public String caricaAzioniIncarico(@RequestParam("idIncarico") long idIncarico, HttpServletRequest req,
			Locale locale) {
		// engsecurity VA -- in loading
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(req);
        //removeCSRFToken(request);
		try {
			req.setAttribute("idIncarico", idIncarico);
			req.setAttribute("coloreVoto", getColoreVoto(req, idIncarico) );
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if( req.getParameter("onlyContent") != null  ){
			return PAGINA_AZIONI_INCARICO;
		}
		return PAGINA_AZIONI_CERCA_INCARICO;
	}
	
	private String getColoreVoto(HttpServletRequest req, Long idIncarico) {
		String coloreVoto=null;
		try { 

			List<VendorManagementView> listVendorManViewByIdIncarico = vendorManagementService.leggiByIdIncaricoNew(idIncarico);

			if(listVendorManViewByIdIncarico.size()==0) {
				
				coloreVoto=null;
				
				IncaricoView incaricoView = incaricoService.leggiTutti(idIncarico);
				if(incaricoView != null){
					
					if(incaricoView.getVo() != null){
						
						if(incaricoView.getVo().getDataRinvioVotazione() != null){
							
							Date dataRinvioVotazione = incaricoView.getVo().getDataRinvioVotazione();
							List<Date> dateInizioFine = DateUtil2.estraiDateSemestre();
							
							if( dataRinvioVotazione.compareTo(dateInizioFine.get(0)) >= 0 
									&& dataRinvioVotazione.compareTo(dateInizioFine.get(1))<= 0){
								coloreVoto="Red";
							}
						}
					}
				}
			}
			else {
				coloreVoto="Red";

				VendorManagementView vendorManagementView =  listVendorManViewByIdIncarico.get(0);
				
				if(vendorManagementView.getVo() != null){
					
					if(vendorManagementView.getVo().getStatoVendorManagement() != null){
						
						if(vendorManagementView.getVo().getStatoVendorManagement().getCodGruppoLingua().equals(CostantiDAO.VENDOR_MANAGEMENT_STATO_BOZZA)){
							coloreVoto="Orange";
						}
					}
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}

		return coloreVoto;
	}

	@RequestMapping(value = "/vendormanagement/getIncaricoById", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody VendormanagementRest getIncaricoById(HttpServletRequest request, Locale locale) {
		VendormanagementRest vendormanagementRest = new VendormanagementRest();

		try { 
			String idIncarico_req=request.getParameter("idIncarico");
			Long idIncarico = Long.valueOf(idIncarico_req);
			IncaricoView incaricoView=incaricoService.leggiTutti(idIncarico);
			
			String studioLegale="";
			String professionistaEsternoNomeCognome=incaricoView.getVo().getProfessionistaEsterno().getNome()+" "+incaricoView.getVo().getProfessionistaEsterno().getCognome();
			Long professionistaEsternoId=incaricoView.getVo().getProfessionistaEsterno().getId();
				
			if(incaricoView.getVo().getProfessionistaEsterno().getStudioLegale() != null){
				
				studioLegale = incaricoView.getVo().getProfessionistaEsterno().getStudioLegale().getDenominazione();
			}
			
			// nazione
			List<NazioneRest> listNazioni = new ArrayList<NazioneRest>();
			Nazione nazione = incaricoView.getVo().getNazione();
			if(nazione==null) {
				NazioneRest nazItalia = new NazioneRest();
				nazItalia.setCodGruppoLingua(Costanti.CODGRUPPOLINGUA_NAZIONE_ITALIA);
				nazItalia.setDescrizione(Costanti.DESCRIZIONE_NAZIONE_ITALIA);
				List<NazioneView> listaNaz=nazioneService.leggibyCodice(Costanti.CODGRUPPOLINGUA_NAZIONE_ITALIA);
				if(listaNaz.size()>0) {
					NazioneView nazViewItalia=listaNaz.get(0);
					nazItalia.setId(nazViewItalia.getVo().getId());
					listNazioni.add(nazItalia);
				}
			}
			else { 
				NazioneRest nazioneRest = new NazioneRest();
				nazioneRest.setCodGruppoLingua(nazione.getCodGruppoLingua());
				nazioneRest.setDescrizione(nazione.getDescrizione());

				listNazioni.add(nazioneRest);
			}
			vendormanagementRest.setListaNazioniRest(listNazioni);

			// specializzazione
			List<SpecializzazioneRest> listSpec = new ArrayList<SpecializzazioneRest>();
			Specializzazione specializzazione = incaricoView.getVo().getSpecializzazione();
			if(specializzazione == null) {
				SpecializzazioneRest specializzazioneRest = new SpecializzazioneRest();
				specializzazioneRest.setCodGruppoLingua(Costanti.CODGRUPPOLINGUA_SPECIALIZZAZIONE_ALTRO);
				specializzazioneRest.setDescrizione(Costanti.DESCRIZIONE_SPECIALIZZAZIONE_ALTRO);

				List<SpecializzazioneView> listaSpec=specializzazioneService.leggibyCodice(Costanti.CODGRUPPOLINGUA_SPECIALIZZAZIONE_ALTRO);
				if(listaSpec.size()>0) {
					SpecializzazioneView specViewAltro=listaSpec.get(0);
					specializzazioneRest.setId(specViewAltro.getVo().getId());
					listSpec.add(specializzazioneRest);
				}
			}
			else {
				SpecializzazioneRest specializzazioneRest = new SpecializzazioneRest();
				specializzazioneRest.setDescrizione(specializzazione.getDescrizione());
				specializzazioneRest.setCodGruppoLingua(specializzazione.getCodGruppoLingua());

				listSpec.add(specializzazioneRest);
			}
			vendormanagementRest.setListaSpecializzazioneRest(listSpec);

			// valutatore 
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			String valutatore = utenteView.getVo().getNominativoUtil();

			vendormanagementRest.setStudioLegale(studioLegale);
			vendormanagementRest.setProfessionistaEsternoNomeCognome(professionistaEsternoNomeCognome);
			vendormanagementRest.setProfessionistaEsternoId(professionistaEsternoId);
			vendormanagementRest.setValutatore(valutatore);
			
			// semestre riferimento
			String semestreRiferimento = DateUtil2.estraiSemestreDaData(new Date());
			
			if(semestreRiferimento != null && !semestreRiferimento.isEmpty()){
				
				vendormanagementRest.setSemestreRiferimento(semestreRiferimento);
			}
			
			
			/** Se esiste, recupero la votazione **/
			List<VendorManagementView> votazioni = vendorManagementService.leggiByIdIncaricoNew(idIncarico);
			
			if(votazioni != null && !votazioni.isEmpty()){
				
				VendorManagementView votazione = votazioni.get(0);
				vendormanagementRest.setReperibilita(votazione.getVo().getValutazioneReperibilita() + "");
				vendormanagementRest.setAutorevolezza(votazione.getVo().getValutazioneAutorevolezza() + "");
				vendormanagementRest.setComprensione(votazione.getVo().getValutazioneCapacita() + "");
				vendormanagementRest.setProfessionalita(votazione.getVo().getValutazioneCompetenze() + "");
				vendormanagementRest.setCosti(votazione.getVo().getValutazioneCosti() + "");
				vendormanagementRest.setFlessibilita(votazione.getVo().getValutazioneFlessibilita() + "");
				vendormanagementRest.setTempestivita(votazione.getVo().getValutazioneTempi() + "");
				
				vendormanagementRest.setValutazioneComplessiva(votazione.getVo().getValutazione() + "");
				vendormanagementRest.setNota(votazione.getVo().getNote() + "");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return vendormanagementRest;
	}
	
	@RequestMapping(value = "/vendormanagement/caricaButtonRinvioVotazione", method = RequestMethod.POST)
	public String caricaButtonRinvioVotazione(@RequestParam("idIncarico") long idIncarico, @RequestParam("title") String title, 
			HttpServletRequest req, Locale locale) {
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(req);
        //removeCSRFToken(request);
		try {
			req.setAttribute("idIncarico", idIncarico);
			req.setAttribute("title", title);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return BUTTON_RINVIA_VOTAZIONE;
	}

	@RequestMapping(value = "/vendormanagement/ricerca", method = RequestMethod.GET, produces = "application/json")
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

			List<String> dateInizioFine = DateUtil2.estraiSemestre();
			Date inizioSemestre = DateUtil.toDate(dateInizioFine.get(0));
			Date fineSemestre = DateUtil.toDate(dateInizioFine.get(1));
			
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			String userIdOwner = utenteView.getVo().getUseridUtil();
			boolean isGestoreVendor = utenteView.isGestoreVendor();
			
			ListaPaginata<IncaricoView> lista = (ListaPaginata<IncaricoView>) incaricoService.leggiIncarichiAutorizzati(inizioSemestre, fineSemestre, userIdOwner, isGestoreVendor,
					numElementiPerPagina, numeroPagina,ordinamento, tipoOrdinamento);
			
			RicercaIncaricoRest ricercaModelRest = new RicercaIncaricoRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());

			List<IncaricoRest> rows = new ArrayList<IncaricoRest>();
			for (IncaricoView view : lista) {
				
				if(view.getVo().getDataCancellazione() == null){
					
					IncaricoRest incaricoRest = new IncaricoRest();
					incaricoRest.setId(view.getVo().getId());
					incaricoRest.setNomeIncarico(view.getVo().getNomeIncarico());
					incaricoRest.setCommento(view.getVo().getCommento());
					incaricoRest.setAnno(DateUtil.getAnno(view.getVo().getDataCreazione()) + "");
					incaricoRest.setDataCreazione(DateUtil.formattaData(view.getVo().getDataCreazione().getTime()));
					incaricoRest.setDataAutorizzazione(DateUtil.formattaData(view.getVo().getDataAutorizzazione().getTime()));
					incaricoRest.setNomeFascicolo(view.getVo().getFascicolo().getNome());
					StatoIncaricoView statoIncaricoView = anagraficaStatiTipiService.leggiStatoIncarico(view.getVo().getStatoIncarico().getCodGruppoLingua(), locale.getLanguage().toUpperCase());
					incaricoRest.setStato(statoIncaricoView.getVo().getDescrizione());
					incaricoRest.setLegaleInterno(view.getVo().getFascicolo().getLegaleInterno());
					
					String controparte = "";
					
					FascicoloView fascicoloView = fascicoloService.leggiTutti(view.getVo().getFascicolo().getId(), FetchMode.JOIN);
					
					if(fascicoloView != null){
						
						if(fascicoloView.getVo().getContropartes() != null && !fascicoloView.getVo().getContropartes().isEmpty()){
							
							for(Controparte cp : fascicoloView.getVo().getContropartes()){
								
								controparte += cp.getNome() + "; ";
							}
						}
					}
					
					incaricoRest.setControparte(controparte);
					StatoFascicoloView statoFascicoloView = anagraficaStatiTipiService.leggiStatiFascicolo(view.getVo().getFascicolo().getStatoFascicolo().getCodGruppoLingua(), locale.getLanguage().toUpperCase());
					incaricoRest.setStatoFascicolo(statoFascicoloView.getVo().getDescrizione());
					
					String assegnatario="-"; 
					if(view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("APAP")
							||view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("APAP1")
							||view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("AAUT")
							||view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("APAP2")){
						UtenteView utenteV=utenteService.leggiAssegnatarioWfIncarico(view.getVo().getId());
						assegnatario=utenteV == null ? "N.D." : utenteV.getVo().getNominativoUtil();
					}
					
					incaricoRest.setAzioni("<p id='containerAzioniRigaIncarico" + view.getVo().getId() + "' title='"+assegnatario+"' alt='"+assegnatario+"' ></p>");
					
					List<VendorManagementView> votazioni = vendorManagementService.leggiByIdIncaricoNew(view.getVo().getId());
					
					if (votazioni == null || votazioni.isEmpty()){
						
						String check = "<div><input data-incarico='"+ view.getVo().getId() +"' data-professionista='"+ view.getVo().getProfessionistaEsterno().getId() +"'  type='checkbox' id='action-check-"+ view.getVo().getId() +"' value=" + view.getVo().getId() + " onchange='addListaVotazioni(this)' ></div>";

						if(utenteView.isGestoreVendor()){
							check = "<div><input type='checkbox' value='' disabled></div>";
						}
						
						
						if(view.getVo().getDataRinvioVotazione() != null){
							
							Date dataRinvioVotazione = view.getVo().getDataRinvioVotazione();
							List<Date> dateInizioFineSem = DateUtil2.estraiDateSemestre();
							
							if( dataRinvioVotazione.compareTo(dateInizioFineSem.get(0)) >= 0 
									&& dataRinvioVotazione.compareTo(dateInizioFineSem.get(1))<= 0){
								check = "<div><input type='checkbox' value='' checked disabled></div>";
							}
						}
						incaricoRest.setCheck(check);
					}
					else{
						
						incaricoRest.setCheck("<div><input type='checkbox' value='' checked disabled></div>");
					}
					
					String rinvioVotazione = "<p id='containerButtonRinvioVotazione" + view.getVo().getId() + "' title='Rinviabile'></p>";
					
					Date now = new Date();
					String semestreCorrente = DateUtil2.estraiSemestreDaData(now);
					
					if(view.getVo().getDataRinvioVotazione() != null){
						String semestreRinvio = DateUtil2.estraiSemestreDaData(view.getVo().getDataRinvioVotazione());
						
						if(semestreCorrente.equals(semestreRinvio)){
							rinvioVotazione = "<p id='containerButtonRinvioVotazione" + view.getVo().getId() + "' title='Rinviata'></p>";
						}
					}
					incaricoRest.setRinvioVotazione(rinvioVotazione);
					
					rows.add(incaricoRest);
				}
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} 
		catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/vendormanagement/salvaVotazione", method = RequestMethod.POST)
	public 
	@ResponseBody VendormanagementRest salvaVotazione(HttpServletRequest request, Locale locale) {
		VendormanagementRest vendormanagementRest = new VendormanagementRest();
		// engsecurity VA 
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try 
		{
			String idIncarico_req=request.getParameter("idIncarico");
			String idsIncarichi_req=request.getParameter("idsIncarichi");
			String valutazioneReperibilita_req=request.getParameter("valutazioneReperibilita");
			String valutazioneTempi_req=request.getParameter("valutazioneTempi");
			String valutazioneCosti_req=request.getParameter("valutazioneCosti");
			String valutazioneFlessibilita_req=request.getParameter("valutazioneFlessibilita");
			String valutazioneCompetenza_req=request.getParameter("valutazioneCompetenza");
			String valutazioneCapacita_req=request.getParameter("valutazioneCapacita");
			String valutazioneAutorevolezza_req=request.getParameter("valutazioneAutorevolezza");
			String media_req=request.getParameter("media");
			String note_req= new String(request.getParameter("note").getBytes(), "UTF-8");
			String dataValutazione_req=request.getParameter("dataValutazione");
			String valNazione_req=request.getParameter("valNazione");
			String valSpecializzazione_req=request.getParameter("valSpecializzazione");

			Long idIncarico = idIncarico_req==null?0: Long.valueOf(idIncarico_req);
			Long valutazioneReperibilita=valutazioneReperibilita_req==null?0: Long.valueOf(valutazioneReperibilita_req);
			Long valutazioneTempi=valutazioneTempi_req==null?0: Long.valueOf(valutazioneTempi_req);
			Long valutazioneCosti=valutazioneCosti_req==null?0: Long.valueOf(valutazioneCosti_req);
			Long valutazioneFlessibilita=valutazioneFlessibilita_req==null?0: Long.valueOf(valutazioneFlessibilita_req);
			Long valutazioneCompetenza=valutazioneCompetenza_req==null?0: Long.valueOf(valutazioneCompetenza_req);
			Long valutazioneCapacita=valutazioneCapacita_req==null?0: Long.valueOf(valutazioneCapacita_req);
			Long valutazioneAutorevolezza=valutazioneAutorevolezza_req==null?0: Long.valueOf(valutazioneAutorevolezza_req);
			BigDecimal media=media_req==null?new BigDecimal(0): new BigDecimal(media_req);
			Date dataValutazione = dataValutazione_req==null?null:  DateUtil.toDate_ddMMyyyy(dataValutazione_req);

			String[] ids = null;
			if(idsIncarichi_req != null && !idsIncarichi_req.isEmpty()){
				
				ids = idsIncarichi_req.split(",");
			}

			// criterio vo
			CriterioView criterioView = new CriterioView();
			Criterio criterioVo = new Criterio();
			criterioVo.setPercReperibilita(new BigDecimal(Costanti.ASSE_PERCENTUALE_REPERIBILITA));
			criterioVo.setPercTempi(new BigDecimal(Costanti.ASSE_PERCENTUALE_TEMPI));
			criterioVo.setPercFlessibilita(new BigDecimal(Costanti.ASSE_PERCENTUALE_FLESSIBILITA));
			criterioVo.setPercCosti(new BigDecimal(Costanti.ASSE_PERCENTUALE_COSTI));
			criterioVo.setPercCompetenze(new BigDecimal(Costanti.ASSE_PERCENTUALE_COMPETENZA));
			criterioVo.setPercCapacita(new BigDecimal(Costanti.ASSE_PERCENTUALE_CAPACITA));
			criterioVo.setPercAutorevolezza(new BigDecimal(Costanti.ASSE_PERCENTUALE_AUTOREVOLEZZA));
			criterioView.setVo(criterioVo);


			CriterioView retCriterioView=criterioService.inserisci(criterioView);

			if(ids != null){
				// Votazione Multipla
				for(int i=0; i<ids.length; i++){
					
					String id = ids[i];
					if(id.indexOf(",") > 0)
						id=id.replace(",", "");
					
					Long idIncaricoSingolo = Long.valueOf(id);
					
					VendorManagement vo = new VendorManagement();

					IncaricoView incaricoView=incaricoService.leggiTutti(idIncaricoSingolo);

					vo.setCriterio(retCriterioView.getVo());
					vo.setIncarico(incaricoView.getVo());
					vo.setNote(note_req);
					vo.setInattivitaProfessionista("T");
					vo.setValutazioneReperibilita(new BigDecimal(valutazioneReperibilita));
					vo.setValutazioneTempi(new BigDecimal(valutazioneTempi));
					vo.setValutazioneCosti(new BigDecimal(valutazioneCosti));
					vo.setValutazioneFlessibilita(new BigDecimal(valutazioneFlessibilita));
					vo.setValutazioneCompetenze(new BigDecimal(valutazioneCompetenza));
					vo.setValutazioneCapacita(new BigDecimal(valutazioneCapacita));
					vo.setValutazioneAutorevolezza(new BigDecimal(valutazioneAutorevolezza));
					vo.setValutazione(media);
					vo.setLang("IT");
					vo.setDataValutazione(dataValutazione);

					List<NazioneView> listNazioneView = nazioneService.leggibyCodice(valNazione_req);
					if( listNazioneView != null && !listNazioneView.isEmpty() )
						vo.setNazione(listNazioneView.get(0).getVo());

					List<SpecializzazioneView> listSpecializzazioneView = specializzazioneService.leggibyCodice(valSpecializzazione_req);
					if( listSpecializzazioneView != null && !listSpecializzazioneView.isEmpty() )
						vo.setSpecializzazione(listSpecializzazioneView.get(0).getVo());

					//inserisco
					VendorManagementView vendorManagementView = new VendorManagementView();
					vendorManagementView.setVo(vo);
						vendorManagementService.inserisci(vendorManagementView, locale);
				}
			}
			else{
				// Singola Votazione
			VendorManagement vo = new VendorManagement();

			IncaricoView incaricoView=incaricoService.leggiTutti(idIncarico);

			vo.setCriterio(retCriterioView.getVo());
			vo.setIncarico(incaricoView.getVo());
			vo.setNote(note_req);
			vo.setInattivitaProfessionista("T");
			vo.setValutazioneReperibilita(new BigDecimal(valutazioneReperibilita));
			vo.setValutazioneTempi(new BigDecimal(valutazioneTempi));
			vo.setValutazioneCosti(new BigDecimal(valutazioneCosti));
			vo.setValutazioneFlessibilita(new BigDecimal(valutazioneFlessibilita));
			vo.setValutazioneCompetenze(new BigDecimal(valutazioneCompetenza));
			vo.setValutazioneCapacita(new BigDecimal(valutazioneCapacita));
			vo.setValutazioneAutorevolezza(new BigDecimal(valutazioneAutorevolezza));
			vo.setValutazione(media);
			vo.setLang("IT");
			vo.setDataValutazione(dataValutazione);

			List<NazioneView> listNazioneView = nazioneService.leggibyCodice(valNazione_req);
			if( listNazioneView != null && !listNazioneView.isEmpty() )
				vo.setNazione(listNazioneView.get(0).getVo());

			List<SpecializzazioneView> listSpecializzazioneView = specializzazioneService.leggibyCodice(valSpecializzazione_req);
			if( listSpecializzazioneView != null && !listSpecializzazioneView.isEmpty() )
				vo.setSpecializzazione(listSpecializzazioneView.get(0).getVo());

			//inserisco
			VendorManagementView vendorManagementView = new VendorManagementView();
			vendorManagementView.setVo(vo);
				
				
				List<VendorManagementView> votazioni = vendorManagementService.leggiByIdIncaricoNew(idIncarico);
				if(votazioni != null && ! votazioni.isEmpty()){
					vendorManagementView.getVo().setId(votazioni.get(0).getVo().getId());
					vendorManagementView.getVo().setStatoVendorManagement(votazioni.get(0).getVo().getStatoVendorManagement());
					vendorManagementService.modifica(vendorManagementView);
				}
				else{
					vendorManagementService.inserisci(vendorManagementView, locale);
				}
			}


		} catch (Throwable e) {
			e.printStackTrace();
		}
		return vendormanagementRest;
	}
	
	@RequestMapping("/vendormanagement/gestioneVotazioni")
	public String gestioneVotazioni(HttpServletRequest request, Model model, Locale locale) {
		VendorManagementView vendorManagementView = new VendorManagementView();

		// engsecurity VA 
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try 
		{

		} 
		catch (Throwable e) {
			e.printStackTrace();
		}

		vendorManagementView.setVo( new VendorManagement() );
		model.addAttribute(MODEL_VIEW_NOME, vendorManagementView);
		return "vendormanagement/formGestioneVotazioni";
	}

	@RequestMapping(value = "/vendormanagement/calcolaStatisticaVotazioni", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String calcolaStatisticaVotazioni(@RequestParam("semestre") String sem, HttpServletRequest request, Locale locale) {
		try {  
			
			Long votazioniTotali = null;
			
			/** Estraggo le date di inizio e fine del semestre corrente **/
			List<Date> semestre = DateUtil2.estraiDateSemestre(sem);
			Date dataInizio = semestre.get(0);
			Date dataFine = semestre.get(1);
			
			List<Date> semestreSuccessivo = DateUtil2.estraiDateSemestreSuccessivo(sem);
			Date dataInizioSuccessivo = semestreSuccessivo.get(0);
			Date dataFineSuccessivo = semestreSuccessivo.get(1);
			
			/** Estraggo la lista di tutti gli incarichi soggetti a votazione nel semestre richiesto **/
			votazioniTotali = incaricoService.contaIncarichiAutorizzati(dataInizio, dataFine);
			
			/** Estraggo la lista di tutte le votazioni effettuate nel semestre di riferimento **/
			Long votazioniEffettuate = vendorManagementService.conta(locale.getLanguage().toUpperCase(), dataInizioSuccessivo, dataFineSuccessivo);

			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			
			if(votazioniTotali != null && votazioniEffettuate != null) {
				
				if(votazioniTotali > 0){
					
					Long votiDaEffettuare = votazioniTotali - votazioniEffettuate;
					
					jsonObject.put("data", votiDaEffettuare);
					jsonObject.put("color", "#F44336");
					jsonObject.put("label", "Totale voti da effettuare" );
					
					jsonArray.put(jsonObject);
					
					JSONObject jsonObject2 = new JSONObject();
					
					jsonObject2.put("data", votazioniEffettuate);
					jsonObject2.put("color", "#1AF73B");
					jsonObject2.put("label", "Totale voti effettuati" );
					
					jsonArray.put(jsonObject2);
				}
			} 
			else{
				return null;
			}
			return jsonArray.toString();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/vendormanagement/calcolaSemestreCorrente", method = RequestMethod.POST)
	public @ResponseBody String calcolaSemestreCorrente(HttpServletRequest request, Locale locale) {


		try {  
			
			String result = "";
			Date now = new Date();
			result = DateUtil2.estraiSemestreDaData(now);
			
			return result;
					
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/vendormanagement/caricaSemestriDisponibili", method = RequestMethod.POST)
	public @ResponseBody String caricaSemestriDisponibili(HttpServletRequest request, Locale locale) {


		try {  
			
			String semestreCorrente = "";
			List<String> semestriDisponibili = new ArrayList<String>();
			
			Date now = new Date();
			semestreCorrente = DateUtil2.estraiSemestreDaData(now);
			semestriDisponibili.add(semestreCorrente);
			
			if(!semestreCorrente.equals("II - 2016")){
				
				while(!semestreCorrente.equals("II - 2016")){
					
					if(semestreCorrente.indexOf("II") >-1){
						semestreCorrente = semestreCorrente.replace("II", "I");
						semestriDisponibili.add(semestreCorrente);
					}
					else{
						int anno = Integer.parseInt(semestreCorrente.substring(semestreCorrente.indexOf("- ") + 2));
						anno = anno - 1;
						semestreCorrente = "II - " + anno;
						semestriDisponibili.add(semestreCorrente);
					}
				}
			}
			
			JSONArray jsonArray = new JSONArray();
			
			for(String semestre : semestriDisponibili){
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("semestre", semestre);
				jsonArray.put(jsonObject);
			}
			return jsonArray.toString();
					
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value="/vendormanagement/sollecitaMail", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest sollecitaMail(@RequestParam("mailAggiuntive") String mailAggiuntive, HttpServletRequest request) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {
				UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
				if( utenteView != null ){
					
					List<Date> semestre = DateUtil2.estraiDateSemestrePrecedente();
					Date start = semestre.get(0);
					Date end = semestre.get(1);
					
					List<String> listaIndirizziMail = null;
					List<String> listaIndirizziAggiuntivi = new ArrayList<String>();
					
					/** Si estraggono gli indirizzi mail aggiuntivi inseriti in fase di conferma se ce ne sono **/
					if(mailAggiuntive != null && !mailAggiuntive.isEmpty()){
						
						String[] mails = mailAggiuntive.split(",");
						
						if(mails.length > 0){
							
							for(int i=0; i<mails.length; i++){
								listaIndirizziAggiuntivi.add(mails[i]);
							}
						}
					}
					
					/** Si estrae la lista dei legali interni aventi diritti al voto **/
					List<Long> listaIdFascicoli = incaricoService.estraiListaFascicoli(start, end);
					
					if(listaIdFascicoli != null && !listaIdFascicoli.isEmpty()){
						
						listaIndirizziMail = autorizzazioneService.leggiAutorizzati(listaIdFascicoli, TIPO_ENTITA_FASCICOLO);
					}
					
					if(!listaIndirizziAggiuntivi.isEmpty()){
						
						if(listaIndirizziMail == null){
							listaIndirizziMail = new ArrayList<String>();
						}
						
						for(String mailAddress : listaIndirizziAggiuntivi){
							listaIndirizziMail.add(mailAddress);
						}
					}
					
					if(listaIndirizziMail != null && !listaIndirizziMail.isEmpty()){
						
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
						
						try{
							emailNotificationService.inviaSollecitoVotazioni(listaIndirizziMail, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil());
						}
						catch (Exception e) { 
							System.out.println("Errore in invio e-mail"+ e);
						}
						
					}
					else {
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "label.errore");
					}
				}else{
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
				}
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return risultato;
	}
	
	@RequestMapping(value = "/vendormanagement/rinviaVotazione",method = RequestMethod.POST,produces="text/html")
	public @ResponseBody String rinviaVotazione(@RequestParam("incaricoId") String incaricoId,HttpServletRequest request, Model model, Locale locale) throws Throwable {

		if(incaricoService.rinviaVotazione(incaricoId) != null)
			return "OK";
		else
			return "KO";
	}
	
	@RequestMapping(value = "/vendormanagement/confermaVotazione",method = RequestMethod.POST,produces="text/html")
	public @ResponseBody String confermaVotazione(@RequestParam("incaricoId") String incaricoId,HttpServletRequest request, Model model, Locale locale) throws Throwable {

		if(vendorManagementService.confermaVotazione(incaricoId, locale) != null)
			return "OK";
		else
			return "KO";
	}
	
	@RequestMapping(value = "/vendormanagement/confermaVotazioni",method = RequestMethod.POST,produces="text/html")
	public @ResponseBody String confermaVotazioni(@RequestParam("incarichiIds") String incarichiIds,HttpServletRequest request, Model model, Locale locale) throws Throwable {
		
		if(incarichiIds != null && !incarichiIds.isEmpty()){
			
			List<String> ids = new ArrayList<String>();
			StringTokenizer strt = new StringTokenizer(incarichiIds, ";");
			
			while(strt.hasMoreTokens()){
				
				ids.add(strt.nextToken());
			}
			
			if(vendorManagementService.confermaVotazioni(ids, locale) != null)
				return "OK";
			else
				return "KO";
		}
		else{
			return "NO";
		}
	}
	
	@RequestMapping(value ="/vendormanagement/generaReportVendor", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest generaReportVendor(@RequestParam("semestre") String sem, Locale locale, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		
		try {
			List<Date> semestreSuccessivo = DateUtil2.estraiDateSemestreSuccessivo(sem);
			Date dataInizioSuccessivo = semestreSuccessivo.get(0);
			Date dataFineSuccessivo = semestreSuccessivo.get(1);
			
			/** Estraggo la lista di tutte le votazioni effettuate nel semestre di riferimento **/
			List<VendorManagementView> votazioniEffettuate = vendorManagementService.leggi(locale.getLanguage().toUpperCase(), dataInizioSuccessivo, dataFineSuccessivo);
			
			if(votazioniEffettuate != null && !votazioniEffettuate.isEmpty()){
				
				Map<Long, List<VendorManagementView>> voteMap = new HashMap<Long, List<VendorManagementView>>();
				
				for(VendorManagementView entry : votazioniEffettuate){
					
					if(entry.getVo() != null){
						
						if(entry.getVo().getIncarico() != null){
							
							if(entry.getVo().getIncarico().getProfessionistaEsterno() != null){
								
								long idProfEst = entry.getVo().getIncarico().getProfessionistaEsterno().getId();
								
								if(voteMap.get(idProfEst) != null)
									voteMap.get(idProfEst).add(entry);
								else{
									List<VendorManagementView> votazioni = new ArrayList<VendorManagementView>();
									votazioni.add(entry);
									voteMap.put(idProfEst, votazioni);
								}
							}
						}
					}
				}
				
				if(!voteMap.isEmpty()){
					
					reportingService.generaReportVendor(voteMap, response, locale.getLanguage().toUpperCase());
					return null;
					
				}else{
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
				}
				
				
			}else{
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
			}
			
		} catch (Throwable e) {
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
			e.printStackTrace();
		}
		return risultato;
	}
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}


	@SuppressWarnings("unused")
	private List<VendorManagementView> filtraPerAutorizzazioniOwner(String matricolaOwner, List<VendorManagementView> lista) throws Throwable {

		List<VendorManagementView> listaOut = new ArrayList<VendorManagementView>();
		for(int i=0; i<lista.size(); i++) {
			VendorManagementView venManView =lista.get(i);
			Incarico incarico = venManView.getVo().getIncarico();

			// is owner
			String userIdOwnerFascicolo=incarico.getFascicolo().getLegaleInterno();
			UtenteView utenteView2=utenteService.leggiUtenteDaUserId(userIdOwnerFascicolo);
			String matricolaUtil=utenteView2.getVo().getMatricolaUtil();
			if( matricolaUtil!=null && matricolaOwner.equals(matricolaUtil) ) {
				listaOut.add(venManView);
				break;
			}
		}
		return listaOut;
	}


	private List<VendorManagementView> filtraPerProfEst(Long idProfEst, List<VendorManagementView> lista) throws Throwable {

		List<VendorManagementView> listaOut = new ArrayList<VendorManagementView>();
		for(VendorManagementView venManView:lista) {
			
			Incarico incarico = venManView.getVo().getIncarico();
			Long idIncProfEst = incarico.getProfessionistaEsterno().getId();
			if(idIncProfEst.equals(idProfEst)) {
				listaOut.add(venManView);
			}
		}
		return listaOut;
	}


	private List<VendorManagementView> filtraPerNazioneSpecializzazione(String codNazione, String codSpecializzazione, List<VendorManagementView> lista) throws Throwable {

		List<VendorManagementView> listaOut = new ArrayList<VendorManagementView>();
		for(VendorManagementView venManView:lista) {
			
			String codNaz=venManView.getVo().getNazione().getCodGruppoLingua();
			String codSpec=venManView.getVo().getSpecializzazione().getCodGruppoLingua();

			if( (codNaz!=null && codNaz.equals(codNazione)) && (codSpec!=null && codSpec.equals(codSpecializzazione))) {
				
				listaOut.add(venManView);
			}
		}
		return listaOut;
	}

	private List<SpecializzazioneView> estraiSpecializzazioniDaListaIncarico(List<VendorManagementView> lista) throws Throwable {
		List<SpecializzazioneView> listaSpecializzazioniOut = new ArrayList<SpecializzazioneView>();

		for(int i=0; i<lista.size(); i++) {
			VendorManagementView venManView =lista.get(i);
			Incarico incarico = venManView.getVo().getIncarico();

			Long professionistaEsternoId=incarico.getProfessionistaEsterno().getId();

			List<RProfEstSpecView> listaProfessionistaSpecializzazione = professionistaEsternoService.leggiProfSpecbyId(professionistaEsternoId);
			for (RProfEstSpecView profSpecView : listaProfessionistaSpecializzazione) {

				boolean trovato=false;
				for (SpecializzazioneView specView : listaSpecializzazioniOut) {
					Specializzazione s1=profSpecView.getVo().getSpecializzazione();
					Specializzazione s2=specView.getVo();
					String cod1=s1.getCodGruppoLingua();
					String cod2=s2.getCodGruppoLingua();
					if(cod1.equals(cod2)) {
						trovato=true;
						break;
					}
				}

				if(trovato==false) {
					SpecializzazioneView specView = new SpecializzazioneView();
					specView.setVo(profSpecView.getVo().getSpecializzazione());
					listaSpecializzazioniOut.add(specView);
				}

			}

		}

		return listaSpecializzazioniOut;
	}

	
	private List<String> estraiSemestriDaListaIncarico(List<VendorManagementView> lista) throws Throwable{
		List<String> semestri = new ArrayList<String>();
		
		for(VendorManagementView venManView : lista){
			Date dataValutazione = venManView.getVo().getDataValutazione();
			int anno = DateUtil.getAnno(dataValutazione);
			int mese = DateUtil.getMese(dataValutazione);
			
			String semestre = null;
			
			if( mese>=1 && mese<=6){
				anno = anno - 1;
				semestre = "II - "+anno;
			}
			else{
				semestre = "I - "+anno;
			}
			
			if(semestre != null){
				if(!semestri.contains(semestre)){
					semestri.add(semestre);
				}
			}
		}
		return semestri;
	}
	
	
	private List<NazioneView> estraiNazioniDaListaIncarico(List<VendorManagementView> lista) throws Throwable {
		List<NazioneView> listaNazioniOut = new ArrayList<NazioneView>();

		for(int i=0; i<lista.size(); i++) {
			VendorManagementView venManView =lista.get(i);
			Incarico incarico = venManView.getVo().getIncarico();

			Long professionistaEsternoId=incarico.getProfessionistaEsterno().getId();

			List<RProfessionistaNazioneView> listaProfessionistaNazione = professionistaEsternoService.leggiProfNazionebyId(professionistaEsternoId);
			for (RProfessionistaNazioneView profNazView : listaProfessionistaNazione) {

				boolean trovato=false;
				for (NazioneView nazView : listaNazioniOut) {
					Nazione n1=profNazView.getVo().getNazione();
					Nazione n2=nazView.getVo();
					String cod1=n1.getCodGruppoLingua();
					String cod2=n2.getCodGruppoLingua();
					if(cod1.equals(cod2)) {
						trovato=true;
						break;
					}
				}
				if(trovato==false) {
					NazioneView nazioneView = new NazioneView();
					nazioneView.setVo(profNazView.getVo().getNazione());
					listaNazioniOut.add(nazioneView);
				}
			}
		}
		return listaNazioniOut;
	}
	
	private TabellaSemestriView creaTabellaSemestri(List<VendorManagementView> lista, List<String> semestri, Long idProfEst) throws Throwable {
		
		TabellaSemestriView tabellaSemestriView = new TabellaSemestriView();
		
		for(String semestre : semestri){
			
			List<Date> inizioFine = DateUtil2.estraiDateSemestre(semestre);
			List<Date> inizioFineSemSuccessivo = DateUtil2.estraiDateSemestreSuccessivo(semestre);
			
			if(inizioFine.size() == 2){
				
				SemestreView semestreView = new SemestreView();
				Date dataInizio = inizioFine.get(0);
				Date dataFine = inizioFine.get(1);
				Date dataInizioSucc = inizioFineSemSuccessivo.get(0);
				Date dataFineSucc = inizioFineSemSuccessivo.get(1);
				
				Long numeroVotazioniPossibili = incaricoService.contaIncarichiAutorizzati(dataInizio, dataFine, idProfEst);
				
				for(VendorManagementView vendorManagementView : lista){
					
					Date dataValutazione = vendorManagementView.getVo().getDataValutazione();
					
					if(dataValutazione != null){
						
						if(dataValutazione.compareTo(dataInizioSucc) >= 0 && dataValutazione.compareTo(dataFineSucc) <= 0){
							
							semestreView.setSemestreStr(semestre);
							semestreView.setId(vendorManagementView.getVo().getId());
							semestreView.addVotazione(vendorManagementView);
							
							BigDecimal autorevolezza = vendorManagementView.getVo().getValutazioneAutorevolezza();
							semestreView.getAutorevolezza().add(autorevolezza!=null ? autorevolezza : new BigDecimal(0));
							
							BigDecimal capacita = vendorManagementView.getVo().getValutazioneCapacita();
							semestreView.getCapacita().add(capacita!=null ? capacita : new BigDecimal(0));
							
							BigDecimal competenze = vendorManagementView.getVo().getValutazioneCompetenze();
							semestreView.getCompetenze().add(competenze!=null ? competenze : new BigDecimal(0));
							
							BigDecimal costi = vendorManagementView.getVo().getValutazioneCosti();
							semestreView.getCosti().add(costi!=null ? costi : new BigDecimal(0));
							
							BigDecimal flessibilita = vendorManagementView.getVo().getValutazioneFlessibilita();
							semestreView.getFlessibilita().add(flessibilita!=null ? flessibilita : new BigDecimal(0));
							
							BigDecimal tempi = vendorManagementView.getVo().getValutazioneTempi();
							semestreView.getTempi().add(tempi!=null ? tempi : new BigDecimal(0));
							
							BigDecimal reperibilita = vendorManagementView.getVo().getValutazioneReperibilita();
							semestreView.getReperibilita().add(reperibilita!=null ? reperibilita : new BigDecimal(0));
							
							BigDecimal totale = vendorManagementView.getVo().getValutazione();
							semestreView.getTotale().add(totale!=null ? totale : new BigDecimal(0));
						}
					}
				}
				
				int voti = semestreView.getVotazioni().size();
				
				if(numeroVotazioniPossibili == null){
					numeroVotazioniPossibili = new Long(0);
				}
				
				semestreView.calcolaPercentuale(numeroVotazioniPossibili, voti);
				tabellaSemestriView.getSemestre().add(semestreView);
			}
		}
		
		tabellaSemestriView.calcolaMediaSemestri();
		tabellaSemestriView.calcolaTotale();

		return tabellaSemestriView;
	}
}
