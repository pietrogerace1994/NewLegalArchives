package eng.la.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.NazioneService;
import eng.la.business.ParteCorrelataService;
import eng.la.business.RicercaParteCorrelataService;
import eng.la.business.UtenteService;
import eng.la.model.ParteCorrelata;
import eng.la.model.RicercaParteCorrelata;
import eng.la.model.rest.ParteCorrelataRest;
import eng.la.model.rest.RicercaParteCorrelataRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.NazioneView;
import eng.la.model.view.ParteCorrelataView;
import eng.la.model.view.RicercaParteCorrelataView;
import eng.la.model.view.TipoCorrelazioneView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.presentation.validator.ParteCorrelataValidator;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("parteCorrelataController")
@SessionAttributes("parteCorrelataView")
public class ParteCorrelataController extends BaseController {

	private static final String MODEL_VIEW_NOME = "parteCorrelataView";
	private static final String PAGINA_FORM_PATH = "parteCorrelata/formParteCorrelata";
	private static final String PAGINA_GESTIONE_PATH = "parteCorrelata/formGestioneParteCorrelata";
	private static final String PAGINA_ELENCO_SIMPLE_2_PATH = "parteCorrelata/elencoPartiCorrelateSimple2";
	private static final String PAGINA_UPLOAD_PATH = "parteCorrelata/uploadMassivoPartiCorrelate";
	private static final int ELEMENTI_PER_PAGINA_PC = 100;

	private static final Logger logger = Logger.getLogger(ParteCorrelataController.class);


	@Autowired
	private ParteCorrelataService parteCorrelataService;
	public void setParteCorrelataService(ParteCorrelataService parteCorrelataService) {
		this.parteCorrelataService = parteCorrelataService;
	}

	@Autowired
	private NazioneService nazioneService;
	public void setNazioneService(NazioneService nazioneService) {
		this.nazioneService = nazioneService;
	}

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;
	public void setAnagraficaStatiTipiService(AnagraficaStatiTipiService anagraficaStatiTipiService) {
		this.anagraficaStatiTipiService = anagraficaStatiTipiService;
	}

	@Autowired
	private RicercaParteCorrelataService ricercaParteCorrelataService;
	public void setRicercaParteCorrelataService(RicercaParteCorrelataService ricercaParteCorrelataService) {
		this.ricercaParteCorrelataService = ricercaParteCorrelataService;
	}

	@Autowired
	private UtenteService utenteService;
	public void setUtenteService(UtenteService utenteService) {
		this.utenteService = utenteService;
	}

	/**
	 * Carica la lista oggetti delle parti correlate.
	 * 
	 * @param view
	 * @param locale
	 */
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		List<TipoCorrelazioneView> listaTipoCorrelazione = anagraficaStatiTipiService.leggiTipoCorrelazione(locale.getCountry());
		List<NazioneView> listaNazioni = nazioneService.leggi(locale);
		// si instanzia la view delle parti correlate... e si impostano
		// le liste che dovranno essere incapsulate.
		ParteCorrelataView parteCorrelataView = (ParteCorrelataView) view;
		parteCorrelataView.setListaTipoCorrelazione(listaTipoCorrelazione);
		parteCorrelataView.setListaNazioni(listaNazioni);

		List<ParteCorrelataView> listaParteCorrelata = parteCorrelataService.leggiAttive();
		parteCorrelataView.setListaParteCorrelata(listaParteCorrelata);
	}

	/**
	 * Lista parti correlate paginata.
	 * Il valore è fisso a 10, per il momento.
	 * @param model modello dei dati
	 * @param locale country locale.
	 * @return
	 */
	// siccome questa lista ritornerebbe tutte le parti correlate non
	// passerei il model ccome argomento.
	@RequestMapping(value = "/parteCorrelata/elenco", method = RequestMethod.GET)
	public String listaPartiCorrelate(Model model,Locale locale
			,HttpServletRequest request, 
			HttpServletResponse response) {

		// engsecurity VA ??
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		ParteCorrelataView parteCorrelataView = new ParteCorrelataView();
		try {
			model.addAttribute("listaPartiCorrelate", parteCorrelataService.leggi(10, 1, null, null));

		} catch (Throwable e) {
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, parteCorrelataView);
		return PAGINA_ELENCO_SIMPLE_2_PATH;
	}



	@RequestMapping(value = "/parteCorrelata/elencoData", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaParteCorrelataRest elencoData(HttpServletRequest request, Locale locale) { 

		// PARAMS
		String offset = request.getParameter("offset");
		String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
		String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
		int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA_PC : NumberUtils.toInt(request.getParameter("limit"));
		int numeroPagina = offset == null || offset.equals("0") ? 1
				: (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;

		RicercaParteCorrelataRest ricercaParteCorrelataRest = new RicercaParteCorrelataRest();
		ListaPaginata<ParteCorrelataView> lista = null;
		try {
			lista = parteCorrelataService.leggiElenco(numElementiPerPagina, numeroPagina, ordinamento, tipoOrdinamento);
		} catch (Throwable e1) {
			e1.printStackTrace();
			return null;
		}

		List<ParteCorrelataRest> rows = new ArrayList<ParteCorrelataRest>();
		for (ParteCorrelataView parteCorrelataView : lista) {

			ParteCorrelataRest parteCorrelataRest = new ParteCorrelataRest();
			parteCorrelataRest.setId(parteCorrelataView.getVo().getId());
			parteCorrelataRest.setDenominazione(parteCorrelataView.getVo().getDenominazione());
			parteCorrelataRest.setCodFiscale(parteCorrelataView.getVo().getCodFiscale());
			parteCorrelataRest.setPartitaIva(parteCorrelataView.getVo().getPartitaIva());
			if(parteCorrelataView.getVo().getNazione()!=null)
				parteCorrelataRest.setNazione(parteCorrelataView.getVo().getNazione().getDescrizione());
			if(parteCorrelataView.getVo().getTipoCorrelazione()!=null)
				parteCorrelataRest.setTipoCorrelazione(parteCorrelataView.getVo().getTipoCorrelazione().getDescrizione());
			parteCorrelataRest.setRapporto(parteCorrelataView.getVo().getRapporto());
			parteCorrelataRest.setFamiliare(parteCorrelataView.getVo().getFamiliare());
			parteCorrelataRest.setConsiglieriSindaci(parteCorrelataView.getVo().getConsiglieriSindaci());

			parteCorrelataRest.setDataInserimento( new SimpleDateFormat("dd/MM/yyyy").format(parteCorrelataView.getVo().getDataInserimento()));


			rows.add(parteCorrelataRest);
		}

		ricercaParteCorrelataRest.setRows(rows);
		ricercaParteCorrelataRest.setTotal(lista.getNumeroTotaleElementi());


		return ricercaParteCorrelataRest;
	}

	@RequestMapping(value = "/parteCorrelata/elencoData2", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaParteCorrelataRest elencoData2(HttpServletRequest request, Locale locale) { 

		// engsecurity VA json
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);

		// PARAMS
		//		String offset = null; // request.getParameter("offset");
		String ordinamento = null; //request.getParameter("sort") == null ? "id" : request.getParameter("sort");
		String tipoOrdinamento = null; //request.getParameter("order") == null ? "ASC" : request.getParameter("order");
		int numElementiPerPagina = 0; //request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA_PC : NumberUtils.toInt(request.getParameter("limit"));
		int numeroPagina = 0; //offset == null || offset.equals("0") ? 1 : (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;

		RicercaParteCorrelataRest ricercaParteCorrelataRest = new RicercaParteCorrelataRest();
		ListaPaginata<ParteCorrelataView> lista = null;
		try {
			lista = parteCorrelataService.leggiElenco(numElementiPerPagina, numeroPagina, ordinamento, tipoOrdinamento);
		} catch (Throwable e1) {
			e1.printStackTrace();
			return null;
		}

		List<ParteCorrelataRest> rows = new ArrayList<ParteCorrelataRest>();
		for (ParteCorrelataView parteCorrelataView : lista) {

			ParteCorrelataRest parteCorrelataRest = new ParteCorrelataRest();
			parteCorrelataRest.setId(parteCorrelataView.getVo().getId());
			parteCorrelataRest.setDenominazione(parteCorrelataView.getVo().getDenominazione());
			parteCorrelataRest.setCodFiscale(parteCorrelataView.getVo().getCodFiscale());
			parteCorrelataRest.setPartitaIva(parteCorrelataView.getVo().getPartitaIva());
			if(parteCorrelataView.getVo().getNazione()!=null)
				parteCorrelataRest.setNazione(parteCorrelataView.getVo().getNazione().getDescrizione());
			if(parteCorrelataView.getVo().getTipoCorrelazione()!=null)
				parteCorrelataRest.setTipoCorrelazione(parteCorrelataView.getVo().getTipoCorrelazione().getDescrizione());
			parteCorrelataRest.setRapporto(parteCorrelataView.getVo().getRapporto());
			parteCorrelataRest.setFamiliare(parteCorrelataView.getVo().getFamiliare());
			parteCorrelataRest.setConsiglieriSindaci(parteCorrelataView.getVo().getConsiglieriSindaci());
			parteCorrelataRest.setStato(parteCorrelataView.getVo().getDataCancellazione()!= null ? "non attiva" : "attiva");

			parteCorrelataRest.setDataInserimento( new SimpleDateFormat("dd/MM/yyyy").format(parteCorrelataView.getVo().getDataInserimento()));
			rows.add(parteCorrelataRest);
		}

		ricercaParteCorrelataRest.setRows(rows);
		ricercaParteCorrelataRest.setTotal(lista.getNumeroTotaleElementi());

		return ricercaParteCorrelataRest;
	}

	@RequestMapping(value="/public/parteCorrelata/parteCorrelataAll", method=RequestMethod.GET )
	public String parteCorrelataAll(Model model, Locale locale
			,HttpServletRequest request, 
			HttpServletResponse response) {

		ParteCorrelataView parteCorrelataView = new ParteCorrelataView();
		parteCorrelataView.setStepMsg("0");

		model.addAttribute(MODEL_VIEW_NOME, parteCorrelataView);

		return "parteCorrelata/formRicercaParteCorrelataAll";
	}

	/**
	 * Carica form crezione parti correlate.
	 * @param model modello dati
	 * @param locale country locale
	 * @return page on
	 */
	// funzione di creazione
	@RequestMapping(value="/parteCorrelata/gestioneParteCorrelata", method=RequestMethod.GET)
	public String gestioneParteCorrelata(Model model, Locale locale
			,HttpServletRequest request, 
			HttpServletResponse response) {

		// engsecurity VA  - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PC ) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			ParteCorrelataView parteCorrelataView = new ParteCorrelataView();
			try {
				parteCorrelataView.setLocale(locale);
				this.caricaListeOggetti(parteCorrelataView, locale);

				convertAndSetParteCorrelataToJsonArray(parteCorrelataView);


			} catch (Throwable e) {
				e.printStackTrace();
			}
			model.addAttribute(MODEL_VIEW_NOME, parteCorrelataView);

			//return PAGINA_FORM_PATH;
			return "parteCorrelata/formGestioneParteCorrelata";
		}
		return "redirect:/errore.action";
	}

	@RequestMapping("/parteCorrelata/storicoParteCorrelata")
	public String storicoParteCorrelata(Model model, Locale locale
			,HttpServletRequest request, 
			HttpServletResponse response) {

		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PC ) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){
			ParteCorrelataView parteCorrelataView = new ParteCorrelataView();

			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);

			try {
				super.caricaListe(parteCorrelataView, locale);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			model.addAttribute(MODEL_VIEW_NOME, parteCorrelataView);

			return "parteCorrelata/formStoricoParteCorrelata";
		}
		return "redirect:/errore.action";
	}


	@RequestMapping("/parteCorrelata/ricercaParteCorrelata")
	public String ricercaParteCorrelata(Model model, Locale locale
			,HttpServletRequest request, 
			HttpServletResponse response) {

		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PC ) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);

			ParteCorrelataView parteCorrelataView = new ParteCorrelataView();
			parteCorrelataView.setStepMsg("0");
			try {
				super.caricaListe(parteCorrelataView, locale);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			model.addAttribute(MODEL_VIEW_NOME, parteCorrelataView);

			return "parteCorrelata/formRicercaParteCorrelata";
		}
		return "redirect:/errore.action";
	}


	@RequestMapping(value = "/parteCorrelata/storico", method=RequestMethod.POST)
	public String storico(
			Locale locale, 
			Model model, 
			@ModelAttribute(MODEL_VIEW_NOME) @Validated ParteCorrelataView parteCorrelataView,
			BindingResult bindingResult, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	{
		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PC ) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);

			try {

				if( bindingResult.hasErrors() ){	
					return "parteCorrelata/formStoricoParteCorrelata";
				}

				String dataInizioStr = parteCorrelataView.getDataInizio();
				Date dataInizio = null;
				if(dataInizioStr!=null && !dataInizioStr.isEmpty())
				{
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					dataInizio = simpleDateFormat.parse(dataInizioStr);
				}

				String dataFineStr = parteCorrelataView.getDataFine();
				Date dataFine = null;
				if(dataFineStr!=null && !dataFineStr.isEmpty())
				{
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					dataFine = simpleDateFormat.parse(dataFineStr);
				}

				List<RicercaParteCorrelataView> lista_ritorno = ricercaParteCorrelataService.leggi(dataInizio,dataFine);

				// continua ricerca			
				JSONArray jsonArray = new JSONArray();

				for( RicercaParteCorrelataView ele : lista_ritorno ){
					JSONObject jsonObject = new JSONObject();
					RicercaParteCorrelata vo = ele.getVo();



					jsonObject.put("utenteRicerca", vo.getUserRicerca()==null?"":vo.getUserRicerca());
					jsonObject.put("esito", vo.getEsito()==null?"":vo.getEsito());

					if(vo.getEsito()!=null) {

						if(vo.getEsito().equals("P")) {
							jsonObject.put("esitoLungo", "Parte Correlata");
						}
						else if(vo.getEsito().equals("N")) {
							jsonObject.put("esitoLungo", "Parte Non Correlata");
						}

					}
					else {
						jsonObject.put("esitoLungo", "");
					}


					Date dataRic = vo.getDataIn();
					String formatDataRic = null;
					if(dataRic!=null) {
						formatDataRic = new SimpleDateFormat("dd/MM/yyyy").format(dataRic);
					}
					else
						formatDataRic = "";
					jsonObject.put("dataRicerca", formatDataRic);

					jsonObject.put("denominazione", vo.getDenominazioneIn()==null?"":vo.getDenominazioneIn());

					String repo= "<div class=\\\"onclickReport\\\" onclick=\\\"scaricaReportStorico(  "+vo.getId()+" ); return false; \\\"> <i class=\\\"zmdi zmdi-collection-pdf\\\"></i> </div>";

					jsonObject.put("report", repo  );		

					jsonArray.put(jsonObject);

				}
				parteCorrelataView.setListaStoricoRisultatiJson(jsonArray.toString());

				return "parteCorrelata/formStoricoParteCorrelata";
			} catch(Throwable e) {
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
				return "parteCorrelata/formStoricoParteCorrelata";
			}
		}
		return "redirect:/errore.action";
	}

	@RequestMapping(value = "/parteCorrelata/ricerca", method=RequestMethod.POST)
	public String ricerca(
			Locale locale, 
			Model model, 
			@ModelAttribute(MODEL_VIEW_NOME) @Validated ParteCorrelataView parteCorrelataView,
			BindingResult bindingResult, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	{

		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PC ) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);

			try {
				if( bindingResult.hasErrors() ){	
					return "parteCorrelata/formRicercaParteCorrelata";
				}

				String dataValidStr = parteCorrelataView.getDataValidita();
				Date dataValid = null;
				if(dataValidStr!=null && !dataValidStr.isEmpty())
				{
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					dataValid = simpleDateFormat.parse(dataValidStr);
				}
				HashMap<String, Object> hashmap =  parteCorrelataService.cercaPartiCorrelate(
						false,
						parteCorrelataView.getDenominazione(),
						parteCorrelataView.getCodFiscale(),
						parteCorrelataView.getPartitaIva(),
						dataValid);

				@SuppressWarnings("unchecked")
				List<ParteCorrelataView> ret_listaRitorno = (List<ParteCorrelataView>) hashmap.get("partiCorList"); 
				String esitoRic=(String)hashmap.get("esitoRicerca");
				parteCorrelataView.setEsitoRic(esitoRic);

				if(esitoRic!=null && esitoRic.equals("N")) {
					parteCorrelataView.setListaRicercaRisultati(ret_listaRitorno);
				}
				else if(esitoRic!=null && esitoRic.equals("P")) {
					parteCorrelataView.setPcMatch( ret_listaRitorno.get( ret_listaRitorno.size()-1 ) );
					ret_listaRitorno.remove(ret_listaRitorno.size()-1);
					parteCorrelataView.setListaRicercaRisultati(ret_listaRitorno);
				}

				// genera report
				List<ParteCorrelataView> listaReport = null;
				if(esitoRic!=null && esitoRic.equals("N")) {
					listaReport = parteCorrelataView.getListaRicercaRisultati();
				}
				else if(esitoRic!=null && esitoRic.equals("P")) {
					ParteCorrelataView pcview = parteCorrelataView.getPcMatch();
					listaReport = new ArrayList<ParteCorrelataView>();
					listaReport.add(pcview);
				}

				boolean esitoRicerca = false;
				if(esitoRic!=null) {
					if(esitoRic.equals("P"))
						esitoRicerca=true;
					else if(esitoRic.equals("N"))
						esitoRicerca=false;
				}

				//utente
				UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

				String denominazione = parteCorrelataView.getDenominazione();
				String codFiscale = parteCorrelataView.getCodFiscale();
				String partitaIva = parteCorrelataView.getPartitaIva();
				Date data = new Date();
				String nomeUtenteVisualizzato = utenteView.getVo().getNominativoUtil();

				boolean isUtenteSS = false;
				parteCorrelataService.generazioneReportRicercaPDF2(null,request, parteCorrelataView, listaReport, esitoRicerca, denominazione, codFiscale, partitaIva, data, nomeUtenteVisualizzato, isUtenteSS);

				// salva ricerca
				RicercaParteCorrelataView rpcView = new RicercaParteCorrelataView();
				RicercaParteCorrelata rpcVo = new RicercaParteCorrelata();
				rpcVo.setDenominazioneIn(parteCorrelataView.getDenominazione());
				rpcVo.setCodFiscale(parteCorrelataView.getCodFiscale());
				rpcVo.setPartitaIva(parteCorrelataView.getPartitaIva());
				SimpleDateFormat formatterDataIn = new SimpleDateFormat("dd/MM/yyyy");
				if(parteCorrelataView.getDataValidita() != null && !parteCorrelataView.getDataValidita().equals("") )
					rpcVo.setDataIn( formatterDataIn.parse( parteCorrelataView.getDataValidita() ) );
				else
					rpcVo.setDataIn( null );
				rpcVo.setDataRicerca(new Date());
				rpcVo.setEsito(esitoRic);
				rpcVo.setUserRicerca(nomeUtenteVisualizzato);
				rpcVo.setReport(parteCorrelataView.getReportBlog());
				rpcView.setVo(rpcVo);
				rpcView = ricercaParteCorrelataService.inserisci(rpcView);
				parteCorrelataView.setRicercaParteCorrelataId(rpcView.getVo().getId());
				parteCorrelataView.setRicercaParteCorrelataView(rpcView);

				// continua ricerca			
				JSONArray jsonArray = new JSONArray();
				for( ParteCorrelataView ele : ret_listaRitorno ){
					JSONObject jsonObject = new JSONObject();

					String denominazioneJson = ele.getVo().getDenominazione()==null?"":ele.getVo().getDenominazione();

					if(!denominazioneJson.isEmpty()){
						if(denominazioneJson.indexOf("\'") > 0){
							denominazioneJson = denominazioneJson.replace("\'", "");
						}
					}
					jsonObject.put("denominazione", denominazioneJson);
					jsonObject.put("codFiscale", ele.getVo().getCodFiscale()==null?"":ele.getVo().getCodFiscale());
					jsonObject.put("partitaIva", ele.getVo().getPartitaIva()==null?"":ele.getVo().getPartitaIva());

					if(ele.getVo().getNazione()!=null)
						jsonObject.put("nazione", ele.getVo().getNazione().getDescrizione());
					else
						jsonObject.put("nazione", "");

					if(ele.getVo().getTipoCorrelazione()!=null){
						String tipoCorrelazioneJson = ele.getVo().getTipoCorrelazione().getDescrizione()==null?"":ele.getVo().getTipoCorrelazione().getDescrizione();

						if(!tipoCorrelazioneJson.isEmpty()){
							if(tipoCorrelazioneJson.indexOf("\'") > 0){
								tipoCorrelazioneJson = tipoCorrelazioneJson.replace("\'", "");
							}
						}
						jsonObject.put("tipoCorrelazione", tipoCorrelazioneJson);
					}
					else
						jsonObject.put("tipoCorrelazione", "");

					jsonObject.put("rapporto",  ele.getVo().getRapporto()==null?"":ele.getVo().getRapporto());
					jsonObject.put("familiare", ele.getVo().getFamiliare()==null?"":ele.getVo().getFamiliare());
					jsonObject.put("consiglieriSindaci", ele.getVo().getConsiglieriSindaci()==null?"":ele.getVo().getFamiliare());

					Date dataIns = ele.getVo().getDataInserimento();
					String formatDataIns = null;
					if(dataIns!=null) {
						formatDataIns = new SimpleDateFormat("dd/MM/yyyy").format(dataIns);
					}
					else
						formatDataIns = "";
					jsonObject.put("dataInserimento", formatDataIns);


					jsonArray.put(jsonObject);

				}

				parteCorrelataView.setListaRicercaRisultatiJson(jsonArray.toString());

				// Match Json		
				JSONArray jsonArrayMatch = new JSONArray();
				if( parteCorrelataView.getPcMatch() != null ) {
					ParteCorrelataView ele = parteCorrelataView.getPcMatch();
					JSONObject jsonObject = new JSONObject();


					String denominazioneJson = ele.getVo().getDenominazione()==null?"":ele.getVo().getDenominazione();

					if(!denominazioneJson.isEmpty()){
						if(denominazioneJson.indexOf("\'") > 0){
							denominazioneJson = denominazioneJson.replace("\'", "");
						}
					}

					jsonObject.put("denominazione", denominazioneJson);
					jsonObject.put("codFiscale", ele.getVo().getCodFiscale()==null?"":ele.getVo().getCodFiscale());
					jsonObject.put("codPartitaIva", ele.getVo().getPartitaIva()==null?"":ele.getVo().getPartitaIva());

					if(ele.getVo().getNazione()!=null)
						jsonObject.put("nazione", ele.getVo().getNazione().getDescrizione());
					else
						jsonObject.put("nazione", "");

					if(ele.getVo().getTipoCorrelazione()!=null){
						String tipoCorrelazioneJson = ele.getVo().getTipoCorrelazione().getDescrizione()==null?"":ele.getVo().getTipoCorrelazione().getDescrizione();

						if(!tipoCorrelazioneJson.isEmpty()){
							if(tipoCorrelazioneJson.indexOf("\'") > 0){
								tipoCorrelazioneJson = tipoCorrelazioneJson.replace("\'", "");
							}
						}
						jsonObject.put("tipoCorrelazione", tipoCorrelazioneJson);
					}
					else
						jsonObject.put("tipoCorrelazione", "");

					jsonObject.put("rapporto",  ele.getVo().getRapporto()==null?"":ele.getVo().getRapporto());
					jsonObject.put("familiare", ele.getVo().getFamiliare()==null?"":ele.getVo().getFamiliare());
					jsonObject.put("consiglieriSindaci", ele.getVo().getConsiglieriSindaci()==null?"":ele.getVo().getFamiliare());

					Date dataIns = ele.getVo().getDataInserimento();
					String formatDataIns = null;
					if(dataIns!=null) {
						formatDataIns = new SimpleDateFormat("dd/MM/yyyy").format(dataIns);
					}
					else
						formatDataIns = "";
					jsonObject.put("dataInserimento", formatDataIns);

					jsonArrayMatch.put(jsonObject);

				}

				parteCorrelataView.setListaRicercaRisultatiMatchJson(jsonArrayMatch.toString());


				if(esitoRic!=null && esitoRic.equals("N"))
					parteCorrelataView.setStepMsg("1");
				else if(esitoRic!=null && esitoRic.equals("P"))
					parteCorrelataView.setStepMsg("2");

				return "parteCorrelata/formRicercaParteCorrelata";
			} catch(Throwable e) {
				e.printStackTrace();
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"+e.getStackTrace().toString()+" "+e.getMessage()));
				return "parteCorrelata/formRicercaParteCorrelata";
			}
		}
		return "redirect:/errore.action";

	}


	@RequestMapping(value = "/public/parteCorrelata/ricercaAll", method=RequestMethod.POST)
	public String ricercaAll(
			Locale locale, 
			Model model, 
			@ModelAttribute(MODEL_VIEW_NOME) @Validated ParteCorrelataView parteCorrelataView,
			BindingResult bindingResult, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	{

		try {

			if( bindingResult.hasErrors() ){	

				return "parteCorrelata/formRicercaParteCorrelataAll";
			}

			String dataValidStr = parteCorrelataView.getDataValidita();
			Date dataValid = null;
			if(dataValidStr!=null && !dataValidStr.isEmpty())
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				dataValid = simpleDateFormat.parse(dataValidStr);
			}
			HashMap<String, Object> hashmap =  parteCorrelataService.cercaPartiCorrelate(
					false,
					parteCorrelataView.getDenominazione(),
					parteCorrelataView.getCodFiscale(),
					parteCorrelataView.getPartitaIva(),
					dataValid);

			@SuppressWarnings("unchecked")
			List<ParteCorrelataView> ret_listaRitorno = (List<ParteCorrelataView>) hashmap.get("partiCorList"); 
			String esitoRic=(String)hashmap.get("esitoRicerca");
			parteCorrelataView.setEsitoRic(esitoRic);
			//
			if(esitoRic!=null && esitoRic.equals("N")) {
				parteCorrelataView.setListaRicercaRisultati(ret_listaRitorno);
			}
			else if(esitoRic!=null && esitoRic.equals("P")) {
				parteCorrelataView.setPcMatch( ret_listaRitorno.get( ret_listaRitorno.size()-1 ) );
				ret_listaRitorno.remove(ret_listaRitorno.size()-1);
				parteCorrelataView.setListaRicercaRisultati(ret_listaRitorno);
			}

			// genera report
			List<ParteCorrelataView> listaReport = null;
			if(esitoRic!=null && esitoRic.equals("N")) {
				listaReport = parteCorrelataView.getListaRicercaRisultati();
			}
			else if(esitoRic!=null && esitoRic.equals("P")) {
				ParteCorrelataView pcview = parteCorrelataView.getPcMatch();
				listaReport = new ArrayList<ParteCorrelataView>();
				listaReport.add(pcview);
			}

			boolean esitoRicerca = false;
			if(esitoRic!=null) {
				if(esitoRic.equals("P"))
					esitoRicerca=true;
				else if(esitoRic.equals("N"))
					esitoRicerca=false;
			}

			String denominazione = parteCorrelataView.getDenominazione();
			String codFiscale = parteCorrelataView.getCodFiscale();
			String partitaIva = parteCorrelataView.getPartitaIva();
			Date data = new Date();
			String nomeUtenteVisualizzato = utenteService.leggiNominativoExternalUserId((String)request.getSession().getAttribute(Costanti.EXTERNAL_USER_ID));

			boolean isUtenteSS = false;
			parteCorrelataService.generazioneReportRicercaPDF2(null,request, parteCorrelataView, listaReport, esitoRicerca, denominazione, codFiscale, partitaIva, data, nomeUtenteVisualizzato, isUtenteSS);


			// salva ricerca
			RicercaParteCorrelataView rpcView = new RicercaParteCorrelataView();
			RicercaParteCorrelata rpcVo = new RicercaParteCorrelata();
			rpcVo.setDenominazioneIn(parteCorrelataView.getDenominazione());
			rpcVo.setCodFiscale(parteCorrelataView.getCodFiscale());
			rpcVo.setPartitaIva(parteCorrelataView.getPartitaIva());
			SimpleDateFormat formatterDataIn = new SimpleDateFormat("dd/MM/yyyy");
			if(parteCorrelataView.getDataValidita() != null && !parteCorrelataView.getDataValidita().equals("") )
				rpcVo.setDataIn( formatterDataIn.parse( parteCorrelataView.getDataValidita() ) );
			else
				rpcVo.setDataIn( null );
			rpcVo.setDataRicerca(new Date());
			rpcVo.setEsito(esitoRic);
			rpcVo.setUserRicerca(nomeUtenteVisualizzato);
			rpcVo.setReport(parteCorrelataView.getReportBlog());
			rpcView.setVo(rpcVo);
			rpcView = ricercaParteCorrelataService.inserisci(rpcView);
			parteCorrelataView.setRicercaParteCorrelataId(rpcView.getVo().getId());
			parteCorrelataView.setRicercaParteCorrelataView(rpcView);

			// continua ricerca			
			JSONArray jsonArray = new JSONArray();
			for( ParteCorrelataView ele : ret_listaRitorno ){
				JSONObject jsonObject = new JSONObject();

				String denominazioneJson = ele.getVo().getDenominazione()==null?"":ele.getVo().getDenominazione();

				if(!denominazioneJson.isEmpty()){
					if(denominazioneJson.indexOf("\'") > 0){
						denominazioneJson = denominazioneJson.replace("\'", "");
					}
					if(denominazioneJson.indexOf("\"") > 0){
						denominazioneJson = denominazioneJson.replace("\"", "");
					}
				}

				jsonObject.put("denominazione", denominazioneJson);

				jsonObject.put("codFiscale", ele.getVo().getCodFiscale()==null?"":ele.getVo().getCodFiscale());
				jsonObject.put("partitaIva", ele.getVo().getPartitaIva()==null?"":ele.getVo().getPartitaIva());

				if(ele.getVo().getNazione()!=null)
					jsonObject.put("nazione", ele.getVo().getNazione().getDescrizione());
				else
					jsonObject.put("nazione", "");

				if(ele.getVo().getTipoCorrelazione()!=null){
					String tipoCorrelazioneJson = ele.getVo().getTipoCorrelazione().getDescrizione()==null?"":ele.getVo().getTipoCorrelazione().getDescrizione();

					if(!tipoCorrelazioneJson.isEmpty()){
						if(tipoCorrelazioneJson.indexOf("\'") > 0){
							tipoCorrelazioneJson = tipoCorrelazioneJson.replace("\'", "");
						}
						if(tipoCorrelazioneJson.indexOf("\"") > 0){
							tipoCorrelazioneJson = tipoCorrelazioneJson.replace("\"", "");
						}
					}
					jsonObject.put("tipoCorrelazione", tipoCorrelazioneJson);
				}
				else
					jsonObject.put("tipoCorrelazione", "");

				jsonObject.put("rapporto",  ele.getVo().getRapporto()==null?"":ele.getVo().getRapporto());
				jsonObject.put("familiare", ele.getVo().getFamiliare()==null?"":ele.getVo().getFamiliare());
				jsonObject.put("consiglieriSindaci", ele.getVo().getConsiglieriSindaci()==null?"":ele.getVo().getFamiliare());

				Date dataIns = ele.getVo().getDataInserimento();
				String formatDataIns = null;
				if(dataIns!=null) {
					formatDataIns = new SimpleDateFormat("dd/MM/yyyy").format(dataIns);
				}
				else
					formatDataIns = "";
				jsonObject.put("dataInserimento", formatDataIns);


				jsonArray.put(jsonObject);

			}

			parteCorrelataView.setListaRicercaRisultatiJson(jsonArray.toString());

			// Match Json		
			JSONArray jsonArrayMatch = new JSONArray();
			if( parteCorrelataView.getPcMatch() != null ) {
				ParteCorrelataView ele = parteCorrelataView.getPcMatch();
				JSONObject jsonObject = new JSONObject();

				String denominazioneJson = ele.getVo().getDenominazione()==null?"":ele.getVo().getDenominazione();

				if(!denominazioneJson.isEmpty()){
					if(denominazioneJson.indexOf("\'") > 0){
						denominazioneJson = denominazioneJson.replace("\'", "");
					}
					if(denominazioneJson.indexOf("\"") > 0){
						denominazioneJson = denominazioneJson.replace("\"", "");
					}
				}

				jsonObject.put("denominazione", denominazioneJson);

				jsonObject.put("codFiscale", ele.getVo().getCodFiscale()==null?"":ele.getVo().getCodFiscale());
				jsonObject.put("partitaIva", ele.getVo().getPartitaIva()==null?"":ele.getVo().getPartitaIva());

				if(ele.getVo().getNazione()!=null)
					jsonObject.put("nazione", ele.getVo().getNazione().getDescrizione());
				else
					jsonObject.put("nazione", "");

				if(ele.getVo().getTipoCorrelazione()!=null){

					String tipoCorrelazioneJson = ele.getVo().getTipoCorrelazione().getDescrizione()==null?"":ele.getVo().getTipoCorrelazione().getDescrizione();

					if(!tipoCorrelazioneJson.isEmpty()){
						if(tipoCorrelazioneJson.indexOf("\'") > 0){
							tipoCorrelazioneJson = tipoCorrelazioneJson.replace("\'", "");
						}
						if(tipoCorrelazioneJson.indexOf("\"") > 0){
							tipoCorrelazioneJson = tipoCorrelazioneJson.replace("\"", "");
						}
					}
					jsonObject.put("tipoCorrelazione", tipoCorrelazioneJson);
				}
				else
					jsonObject.put("tipoCorrelazione", "");

				jsonObject.put("rapporto",  ele.getVo().getRapporto()==null?"":ele.getVo().getRapporto());
				jsonObject.put("familiare", ele.getVo().getFamiliare()==null?"":ele.getVo().getFamiliare());
				jsonObject.put("consiglieriSindaci", ele.getVo().getConsiglieriSindaci()==null?"":ele.getVo().getFamiliare());

				Date dataIns = ele.getVo().getDataInserimento();
				String formatDataIns = null;
				if(dataIns!=null) {
					formatDataIns = new SimpleDateFormat("dd/MM/yyyy").format(dataIns);
				}
				else
					formatDataIns = "";
				jsonObject.put("dataInserimento", formatDataIns);

				jsonArrayMatch.put(jsonObject);

			}

			parteCorrelataView.setListaRicercaRisultatiMatchJson(jsonArrayMatch.toString());


			if(esitoRic!=null && esitoRic.equals("N"))
				parteCorrelataView.setStepMsg("1");
			else if(esitoRic!=null && esitoRic.equals("P"))
				parteCorrelataView.setStepMsg("2");

			return "parteCorrelata/formRicercaParteCorrelataAll";
		} catch(Throwable e) {
			e.printStackTrace();
			bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"+e.getStackTrace().toString()+" "+e.getMessage()));
			return "parteCorrelata/formRicercaParteCorrelataAll";
		}
	}

	/**
	 * Carica form crezione parti correlate.
	 * @param model modello dati
	 * @param locale country locale
	 * @return page 
	 */
	// funzione di creazione
	@RequestMapping("/parteCorrelata/crea")
	public String creaParteCorrelata(Model model, Locale locale) {

		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PC ) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){
			ParteCorrelataView parteCorrelataView = new ParteCorrelataView();
			try {
				super.caricaListe(parteCorrelataView, locale);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			model.addAttribute(MODEL_VIEW_NOME, parteCorrelataView);

			return PAGINA_FORM_PATH;
		}
		return "redirect:/errore.action";
	}


	/**
	 * Caricamento form di modifica della parte correlata.
	 * <p> 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/parteCorrelata/modifica")
	public String modificaParteCorrelata(@RequestParam("id") long id, Model model , Locale locale ) {
		ParteCorrelataView parteCorrelataView = new ParteCorrelataView();
		try {
			parteCorrelataView=parteCorrelataService.leggi(id);
			super.caricaListe(parteCorrelataView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, parteCorrelataView);
		return PAGINA_FORM_PATH;
	}



	@RequestMapping("/parteCorrelata/caricaParteCorrelata")
	public @ResponseBody ParteCorrelataRest caricaParteCorrelata(
			@RequestParam("id") Long id) {
		ParteCorrelataRest parteCorrelataRest = new ParteCorrelataRest();

		try {
			ParteCorrelataView parteCorrelataView = parteCorrelataService.leggi(id);
			if (parteCorrelataView != null) {
				parteCorrelataRest.setId(parteCorrelataView.getVo().getId());
				parteCorrelataRest.setDenominazione(parteCorrelataView.getVo().getDenominazione());
				parteCorrelataRest.setCodFiscale(parteCorrelataView.getVo().getCodFiscale());
				parteCorrelataRest.setPartitaIva(parteCorrelataView.getVo().getPartitaIva());
				if(parteCorrelataView.getVo().getNazione() != null) 
					parteCorrelataRest.setNazioneId(parteCorrelataView.getVo().getNazione().getId());
				if(parteCorrelataView.getVo().getNazione() != null) 
					parteCorrelataRest.setNazioneCodGruppoLingua(parteCorrelataView.getVo().getNazione().getCodGruppoLingua());
				if(parteCorrelataView.getVo().getTipoCorrelazione()!=null)
					parteCorrelataRest.setTipoCorrelazioneId(parteCorrelataView.getVo().getTipoCorrelazione().getId());
				if(parteCorrelataView.getVo().getTipoCorrelazione()!=null)
					parteCorrelataRest.setTipoCorrelazioneCodGruppoLingua(parteCorrelataView.getVo().getTipoCorrelazione().getCodGruppoLingua());
				parteCorrelataRest.setFamiliare(parteCorrelataView.getVo().getFamiliare());
				parteCorrelataRest.setConsiglieriSindaci(parteCorrelataView.getVo().getConsiglieriSindaci());
				parteCorrelataRest.setRapporto(parteCorrelataView.getVo().getRapporto());
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return parteCorrelataRest;
	}

	/**
	 * Salvataggio dei dati della form nel dbase ( inserimento / modifica ).
	 * 
	 * @param parteCorrelataView
	 * @return
	 */
	@RequestMapping(value = "/parteCorrelata/salva", method=RequestMethod.POST)
	public String salvaParteCorrelata(Locale locale, Model model, @ModelAttribute(MODEL_VIEW_NOME) @Validated ParteCorrelataView parteCorrelataView,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) { 

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		String token=request.getParameter("CSRFToken");

		try{

			if( parteCorrelataView.getOp() != null && !parteCorrelataView.getOp().equals("salvaParteCorrelata") ){
				String ritorno = invocaMetodoDaReflection( parteCorrelataView, bindingResult, locale, model, request, response, this );
				return ritorno == null ? PAGINA_GESTIONE_PATH : ritorno;
			}

			if( bindingResult.hasErrors() ){	
				if (parteCorrelataView.isInsertMode()) {
					parteCorrelataView.setTabAttiva("1");
				} else if (parteCorrelataView.isEditMode()) { 
					parteCorrelataView.setTabAttiva("2");
				} else if (parteCorrelataView.isDeleteMode()) {
					parteCorrelataView.setTabAttiva("2");
				}
				return PAGINA_GESTIONE_PATH;
			}

			if (parteCorrelataView.isInsertMode()) {
				System.out.println("####### ...esegue salvataggio... #######");
				preparaPerSalvataggio(parteCorrelataView,bindingResult);
				parteCorrelataService.inserisci(parteCorrelataView);
				parteCorrelataView.setDenominazione("");
				parteCorrelataView.setCodFiscale("");
				parteCorrelataView.setPartitaIva("");
				parteCorrelataView.setNazioneCode("");
				parteCorrelataView.setNazioneId(new Long(0));
				parteCorrelataView.setTipoCorrelazioneCode("");
				parteCorrelataView.setTipoCorrelazioneId(new Long(0));
				parteCorrelataView.setRapporto("");
				parteCorrelataView.setConsiglieriSindaci("");
				parteCorrelataView.setTabAttiva("1");
			} else if (parteCorrelataView.isEditMode()) { 
				System.out.println("####### ...esegue modifica... #######");
				preparaPerModifica(parteCorrelataView,bindingResult);
				parteCorrelataService.modifica(parteCorrelataView);
				parteCorrelataView.setTabAttiva("2");
			} else if (parteCorrelataView.isDeleteMode()) {
				System.out.println("####### ...esegue cancellazione... #######");
				if(parteCorrelataView.getParteCorrelataIdMod() != null)
					parteCorrelataService.cancella(parteCorrelataView.getParteCorrelataIdMod());
				parteCorrelataView.setTabAttiva("2");
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");


			System.out.println("##### salvaParteCorrelata - end #####");


			return "redirect:gestioneParteCorrelata.action?CSRFToken="+token;
		}catch(Throwable e){
			e.printStackTrace();
			bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			return PAGINA_GESTIONE_PATH;
		}
	}


	/**
	 * Metodo di cancellazione
	 * 
	 * @param id l'occorrenza da cancellare
	 * @return
	 */
	@RequestMapping("/parteCorrelata/elimina/{id}")
	public String eliminaParteCorrelata(@PathVariable("id") long id) {
		try {
			parteCorrelataService.cancella(id);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "redirect:parteCorrelata/elencoPartiCorrelate";
	}


	@RequestMapping(value = "/parteCorrelata/download",method = RequestMethod.GET)
	public String downloadFile(
			@RequestParam("tipoEstrazione") String tipoEstrazione , 
			HttpServletRequest request,
			HttpServletResponse response, 
			Model model, 
			Locale locale) throws Throwable 
	{
		// engsecurity VA -- loading
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		parteCorrelataService.generaReportExcel(tipoEstrazione,response);
		return PAGINA_GESTIONE_PATH;		
	}

	@RequestMapping(value = "/public/parteCorrelata/downloadRicerca",method = RequestMethod.GET)
	public String downloadRicercaPublic(
			HttpServletRequest request,
			HttpServletResponse response, 
			Model model, 
			@ModelAttribute(MODEL_VIEW_NOME) @Validated ParteCorrelataView parteCorrelataView,
			Locale locale) throws Throwable 
	{

		RicercaParteCorrelataView ricercaParteCorrelataView = ricercaParteCorrelataService.leggi(parteCorrelataView.getRicercaParteCorrelataId());
		byte[] out = ricercaParteCorrelataView.getVo().getReport();

		String nomeFiles = "RicercaPartiCorrelate.pdf";

		response.setHeader("Content-Disposition", "attachment; filename=" + nomeFiles);
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/pdf");
		response.setContentLength(out.length);
		response.getOutputStream().write(out);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		response.flushBuffer();

		return PAGINA_GESTIONE_PATH;		
	}

	@RequestMapping(value = "/parteCorrelata/downloadRicerca",method = RequestMethod.GET)
	public String downloadRicerca(
			HttpServletRequest request,
			HttpServletResponse response, 
			Model model, 
			@ModelAttribute(MODEL_VIEW_NOME) @Validated ParteCorrelataView parteCorrelataView,
			Locale locale) throws Throwable 
	{

		RicercaParteCorrelataView ricercaParteCorrelataView = ricercaParteCorrelataService.leggi(parteCorrelataView.getRicercaParteCorrelataId());
		byte[] out = ricercaParteCorrelataView.getVo().getReport();

		String nomeFiles = "RicercaPartiCorrelate.pdf";

		response.setHeader("Content-Disposition", "attachment; filename=" + nomeFiles);
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/pdf");
		response.setContentLength(out.length);
		response.getOutputStream().write(out);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		response.flushBuffer();

		return PAGINA_GESTIONE_PATH;		
	}

	@RequestMapping(value = "/parteCorrelata/downloadStorico",method = RequestMethod.GET)
	public String downloadStorico(
			HttpServletRequest request,
			HttpServletResponse response, 
			Model model, 
			@ModelAttribute(MODEL_VIEW_NOME) @Validated ParteCorrelataView parteCorrelataView,
			@RequestParam("id") Long id ,
			Locale locale) throws Throwable 
	{
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		RicercaParteCorrelataView ricercaParteCorrelataView = ricercaParteCorrelataService.leggi(id);
		byte[] out = ricercaParteCorrelataView.getVo().getReport();

		String nomeFiles = "RicercaPartiCorrelate.pdf";

		response.setHeader("Content-Disposition", "attachment; filename=" + nomeFiles);
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/pdf");
		response.getOutputStream().write(out);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		response.flushBuffer();

		return PAGINA_GESTIONE_PATH;
	}

	// metodi private a supporto
	private void preparaPerSalvataggio(ParteCorrelataView parteCorrelataView, BindingResult bindingResult) throws Throwable {		
		System.out.println("##### preparaSalvataggio - start #####");

		ParteCorrelata parteCorrelata = new ParteCorrelata();
		parteCorrelata.setDenominazione(parteCorrelataView.getDenominazione());
		parteCorrelata.setCodFiscale(parteCorrelataView.getCodFiscale());
		parteCorrelata.setPartitaIva(parteCorrelataView.getPartitaIva());

		// Tipo Correlazione
		TipoCorrelazioneView tipoCorrelazioneView = 
				anagraficaStatiTipiService.leggiTipoCorrelazione(parteCorrelataView.getTipoCorrelazioneCode(), 
						parteCorrelataView.getLocale().getLanguage().toUpperCase());

		parteCorrelata.setTipoCorrelazione(tipoCorrelazioneView.getVo());
		// Nazione
		NazioneView nazioneView = nazioneService.leggi(parteCorrelataView.getNazioneCode(),parteCorrelataView.getLocale(), false);
		parteCorrelata.setNazione(nazioneView.getVo());
		parteCorrelata.setConsiglieriSindaci(parteCorrelataView.getConsiglieriSindaci());
		parteCorrelata.setFamiliare(parteCorrelataView.getFamiliare());
		parteCorrelata.setRapporto(parteCorrelataView.getRapporto());		
		parteCorrelataView.setVo(parteCorrelata);

		System.out.println("##### preparaSalvataggio - end #####"); 
	}

	// metodi private a supporto
	private void preparaPerModifica(ParteCorrelataView parteCorrelataView, BindingResult bindingResult) throws Throwable {		
		System.out.println("##### preparaModifica - start #####");

		ParteCorrelata vo = new ParteCorrelata();
		vo.setId(parteCorrelataView.getParteCorrelataIdMod());
		vo.setDenominazione(parteCorrelataView.getDenominazioneMod());
		vo.setCodFiscale(parteCorrelataView.getCodFiscaleMod());
		vo.setPartitaIva(parteCorrelataView.getPartitaIvaMod());

		// Tipo Correlazione
		TipoCorrelazioneView tipoCorrelazioneView = 
				anagraficaStatiTipiService.leggiTipoCorrelazione(parteCorrelataView.getTipoCorrelazioneCodeMod(), 
						parteCorrelataView.getLocale().getLanguage().toUpperCase());

		vo.setTipoCorrelazione(tipoCorrelazioneView.getVo());
		// Nazione
		NazioneView nazioneView = nazioneService.leggi(parteCorrelataView.getNazioneCodeMod(),parteCorrelataView.getLocale(), false);
		vo.setNazione(nazioneView.getVo());
		vo.setConsiglieriSindaci(parteCorrelataView.getConsiglieriSindaciMod());
		vo.setFamiliare(parteCorrelataView.getFamiliareMod());
		vo.setRapporto(parteCorrelataView.getRapportoMod());
		vo.setDataInserimento(new Date());
		parteCorrelataView.setVo(vo);

		System.out.println("##### preparaModifica - end #####"); 
	}

	/**
	 * Carica form crezione parti correlate.
	 * @param model modello dati
	 * @param locale country locale
	 * @return page 
	 */
	// funzione di creazione
	@RequestMapping("/parteCorrelata/uploadMassivoPartiCorrelate")
	public String uploadMassivoPartiCorrelate(Model model, Locale locale,HttpServletRequest request, 
			HttpServletResponse response) {

		// engsecurity VA  - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PC ) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){
			ParteCorrelataView parteCorrelataView = new ParteCorrelataView();
			try {
				super.caricaListe(parteCorrelataView, locale);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			model.addAttribute(MODEL_VIEW_NOME, parteCorrelataView);

			return PAGINA_UPLOAD_PATH;
		}
		return "redirect:/errore.action";
	}

	@RequestMapping(value = "/parteCorrelata/uploadFile", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest uploadFile( HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file) {  

		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {

			String fileName = file.getOriginalFilename();

			if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")){

				InputStream input = file.getInputStream();
				Workbook workbook = null;

				if(fileName.endsWith(".xls")){

					workbook = new HSSFWorkbook(input);
				}
				else{

					workbook = new XSSFWorkbook(input);
				}

				Sheet firstSheet = workbook.getSheetAt(0);
				Iterator<Row> iterator = firstSheet.iterator();
				List<ParteCorrelataView> partiCorrelateExcell = new ArrayList<ParteCorrelataView>();
				List<TipoCorrelazioneView> tipiCorrelazione = anagraficaStatiTipiService.leggiTipoCorrelazione();
				List<NazioneView> nazioni = nazioneService.leggi();

				while(iterator.hasNext()){

					Row nextRow = iterator.next();

					if(nextRow.getRowNum() != 0){

						Iterator<Cell> cellIterator = nextRow.cellIterator();
						ParteCorrelata pc = new ParteCorrelata();

						while (cellIterator.hasNext()) {

							Cell cell = cellIterator.next();
							int columnIndex = cell.getColumnIndex();

							switch (columnIndex) {
							case 0:
								pc.setDenominazione(getCellValue(cell) + "");
								break;
							case 1:
								pc.setCodFiscale(null);
								
								String codiceFiscale = "";
								
								if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
									
									codiceFiscale = NumberToTextConverter.toText(cell.getNumericCellValue());
								}
								
								else
									codiceFiscale = getCellValue(cell) + "";
								
								if(!codiceFiscale.isEmpty() && !codiceFiscale.equals("null"))
									pc.setCodFiscale(codiceFiscale);
								
								break;
							case 2:
								pc.setPartitaIva(null);
								String partitaIva = "";
								
								if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) 
								    partitaIva = NumberToTextConverter.toText(cell.getNumericCellValue());
								
								else
									partitaIva = getCellValue(cell) + "";
								
								if(!partitaIva.isEmpty() && !partitaIva.equals("null"))
									pc.setPartitaIva(partitaIva);
								
								break;
							case 4:
								String tipoCorrelazioneDesc = getCellValue(cell) + "";

								if(tipoCorrelazioneDesc != null && !tipoCorrelazioneDesc.isEmpty()){

									for(TipoCorrelazioneView tcw : tipiCorrelazione){

										if(tcw.getVo().getDescrizione().equals(tipoCorrelazioneDesc)){

											pc.setTipoCorrelazione(tcw.getVo());
											break;
										}
									}
								}
								else{
									pc.setTipoCorrelazione(null);
								}
								break;
							case 5:
								pc.setRapporto(null);
								
								String rapporto = getCellValue(cell) + "";
								
								if(!rapporto.isEmpty())
									pc.setRapporto(getCellValue(cell) + "");
								
								break;
							case 6:
								pc.setConsiglieriSindaci(null);
								
								String consiglieri = getCellValue(cell) + "";
								
								if(!consiglieri.isEmpty())
									pc.setConsiglieriSindaci(consiglieri);
								
								break;
							case 7:
								String nazioneDesc = getCellValue(cell) + "";

								if(nazioneDesc != null && !nazioneDesc.isEmpty()){

									for(NazioneView nw : nazioni){

										if(nw.getVo().getDescrizione().equals(nazioneDesc)){

											pc.setNazione(nw.getVo());
											break;
										}
									}
								}
								else{
									pc.setNazione(null);
								}
								break;
							case 8:
								
								if(getCellValue(cell) instanceof String){

									pc.setDataInserimento(null);
								}else{
									if(getCellValue(cell) != null)
										pc.setDataInserimento(DateUtil.getJavaDate((double)getCellValue(cell)));
									else
										pc.setDataInserimento(null);
								}
								break;
							case 9:
								
								if(getCellValue(cell) instanceof String){

									pc.setDataCancellazione(null);
								}
								else{
									if(getCellValue(cell) != null)
										pc.setDataCancellazione(DateUtil.getJavaDate((double)getCellValue(cell)));
									else
										pc.setDataCancellazione(null);
								}
								break;
							}
						}
						if((pc.getDenominazione() != null && pc.getTipoCorrelazione() != null)
								|| (pc.getDenominazione() != null && pc.getDataInserimento() != null)){

							ParteCorrelataView pcw = new ParteCorrelataView();
							pcw.setVo(pc);
							partiCorrelateExcell.add(pcw);
						}
						else{
							return new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.campi.partecorrelata.obbligatori");
						}
					}
				}
				List<ParteCorrelataView> partiCorrelateDB = parteCorrelataService.leggiAttive();

				Map<String, ParteCorrelataView> mapExcell = generaMappaDaLista(partiCorrelateExcell);
				Map<String, ParteCorrelataView> mapDB = generaMappaDaLista(partiCorrelateDB);

				if(!mapExcell.isEmpty()){

					for(Map.Entry<String, ParteCorrelataView> entry : mapExcell.entrySet()){

						String key = entry.getKey();
						ParteCorrelataView value = entry.getValue();

						if(mapDB.containsKey(key)){

							if(!equalsPC(value, mapDB.get(key))){

								try{
									preparaPerModifica(value, mapDB.get(key));
									parteCorrelataService.modifica(value);
									logger.info("MODIFICO la seguente riga:"
											+ value.getVo().getDenominazione() + " "
											+ value.getVo().getCodFiscale() + " "
											+ value.getVo().getPartitaIva() + " "
											+ value.getVo().getTipoCorrelazione() + " "
											+ value.getVo().getRapporto() + " "
											+ value.getVo().getConsiglieriSindaci() + " "
											+ value.getVo().getNazione() + " "
											+ value.getVo().getDataInserimento() + " "
											+ value.getVo().getDataCancellazione() + " ");
								}
								catch(Exception e){
									logger.error("Errore nella modifica della riga: " 
											+ value.getVo().getDenominazione() + " "
											+ value.getVo().getCodFiscale() + " "
											+ value.getVo().getPartitaIva() + " "
											+ value.getVo().getTipoCorrelazione()+ " "
											+ value.getVo().getRapporto() + " "
											+ value.getVo().getConsiglieriSindaci() + " "
											+ value.getVo().getNazione() + " "
											+ value.getVo().getDataInserimento() + " "
											+ value.getVo().getDataCancellazione() + " "
											+ e);
								}
							}
							mapDB.remove(key);
						}
						else{
							try{
								parteCorrelataService.inserisci(value);
								logger.info("INSERISCO la seguente riga:"
										+ value.getVo().getDenominazione() + " "
										+ value.getVo().getCodFiscale() + " "
										+ value.getVo().getPartitaIva() + " "
										+ value.getVo().getTipoCorrelazione() + " "
										+ value.getVo().getRapporto() + " "
										+ value.getVo().getConsiglieriSindaci() + " "
										+ value.getVo().getNazione() + " "
										+ value.getVo().getDataInserimento() + " "
										+ value.getVo().getDataCancellazione() + " ");
							}
							catch(Exception e){

								logger.error("Errore nelsalvataggio della nuova riga: " 
										+ value.getVo().getDenominazione() + " "
										+ value.getVo().getCodFiscale() + " "
										+ value.getVo().getPartitaIva() + " "
										+ value.getVo().getTipoCorrelazione() + " "
										+ value.getVo().getRapporto() + " "
										+ value.getVo().getConsiglieriSindaci() + " "
										+ value.getVo().getNazione() + " "
										+ value.getVo().getDataInserimento() + " "
										+ value.getVo().getDataCancellazione() + " "
										+ e);
							}
						}
					}

					for(Map.Entry<String, ParteCorrelataView> entry : mapDB.entrySet()){

						parteCorrelataService.cancella(entry.getValue().getVo().getId());
						logger.info("CANCELLO la seguente riga:"
								+ entry.getValue().getVo().getDenominazione() + " "
								+ entry.getValue().getVo().getCodFiscale() + " "
								+ entry.getValue().getVo().getPartitaIva() + " "
								+ entry.getValue().getVo().getTipoCorrelazione() + " "
								+ entry.getValue().getVo().getRapporto() + " "
								+ entry.getValue().getVo().getConsiglieriSindaci() + " "
								+ entry.getValue().getVo().getNazione() + " "
								+ entry.getValue().getVo().getDataInserimento() + " "
								+ entry.getValue().getVo().getDataCancellazione() + " ");
					}
				}
				else{
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.file.vuoto");
				}
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			}
			else{

				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.campo.file.tipologia");
			}


		} catch (Throwable e) {
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
			e.printStackTrace();
		}
		return risultato;
	}

	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		}
		return null;
	}

	private Map<String, ParteCorrelataView> generaMappaDaLista(List<ParteCorrelataView> lista){

		Map<String, ParteCorrelataView> hashMap = new HashMap<String, ParteCorrelataView>();

		if(lista != null && !lista.isEmpty()){

			for(ParteCorrelataView pc: lista){

				if(pc.getVo().getDataCancellazione() == null){
					
					if(pc.getVo().getDataInserimento() == null){
						
						pc.getVo().setDataInserimento(new Date());
					}

					if(pc.getVo().getDenominazione() != null){

						if(!pc.getVo().getDenominazione().isEmpty()){

							String key = pc.getVo().getDenominazione() + "-" + eng.la.util.DateUtil.formattaDataCompatta(pc.getVo().getDataInserimento().getTime());
							hashMap.put(key, pc);
						}
					}
				}
			}
		}
		return hashMap;
	}

	private boolean equalsPC(ParteCorrelataView pc1, ParteCorrelataView pc2){
		boolean equals = false;

		if(pc1 != null && pc2 != null){

			if(Objects.equals(pc1.getVo().getCodFiscale(), pc2.getVo().getCodFiscale())){

				if(Objects.equals(pc1.getVo().getPartitaIva(), pc2.getVo().getPartitaIva())){
					
					if(pc1.getVo().getTipoCorrelazione() != null && pc2.getVo().getTipoCorrelazione() != null){
						
						if(Objects.equals(pc1.getVo().getTipoCorrelazione().getCodGruppoLingua(), pc2.getVo().getTipoCorrelazione().getCodGruppoLingua())){
							
							if(Objects.equals(pc1.getVo().getRapporto(), pc2.getVo().getRapporto())){
								
								if(Objects.equals(pc1.getVo().getConsiglieriSindaci(), pc2.getVo().getConsiglieriSindaci())){
									
									if(pc1.getVo().getNazione() != null && pc2.getVo().getNazione() != null){
										
										if(Objects.equals(pc1.getVo().getNazione().getCodGruppoLingua(), pc2.getVo().getNazione().getCodGruppoLingua())){
											
											return true;
										}
									}
									else{
										if(pc1.getVo().getNazione() == null && pc2.getVo().getNazione() == null){
											
											return true;
										}
									}
								}
							}
						}
					}
					else if(pc1.getVo().getTipoCorrelazione() == null && pc2.getVo().getTipoCorrelazione() == null){
						
						if(Objects.equals(pc1.getVo().getRapporto(), pc2.getVo().getRapporto())){
							
							if(Objects.equals(pc1.getVo().getConsiglieriSindaci(), pc2.getVo().getConsiglieriSindaci())){
								
								if(pc1.getVo().getNazione() != null && pc2.getVo().getNazione() != null){
									
									if(Objects.equals(pc1.getVo().getNazione().getCodGruppoLingua(), pc2.getVo().getNazione().getCodGruppoLingua())){
										
										return true;
									}
								}
								else{
									if(pc1.getVo().getNazione() == null && pc2.getVo().getNazione() == null){
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		return equals;
	}


	// metodi private a supporto
	private void preparaPerModifica(ParteCorrelataView pcExcell, ParteCorrelataView pcDB) throws Throwable {		

		ParteCorrelata vo = new ParteCorrelata();
		vo.setId(pcDB.getVo().getId());
		vo.setDenominazione(pcDB.getVo().getDenominazione());
		vo.setCodFiscale(pcExcell.getVo().getCodFiscale() != null ? pcExcell.getVo().getCodFiscale() : "" );
		vo.setPartitaIva(pcExcell.getVo().getPartitaIva() != null ? pcExcell.getVo().getPartitaIva() : "");
		vo.setTipoCorrelazione(pcExcell.getVo().getTipoCorrelazione() != null ? pcExcell.getVo().getTipoCorrelazione() : null);
		vo.setNazione(pcExcell.getVo().getNazione() != null ? pcExcell.getVo().getNazione() : null);
		vo.setConsiglieriSindaci(pcExcell.getVo().getConsiglieriSindaci() != null ? pcExcell.getVo().getConsiglieriSindaci() : "");
		vo.setRapporto(pcExcell.getVo().getRapporto() != null ? pcExcell.getVo().getRapporto() : "");
		vo.setDataInserimento(new Date());
		pcExcell.setVo(vo);
		pcExcell.setParteCorrelataIdMod(pcDB.getVo().getId());
	}


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ParteCorrelataValidator());
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {

	}

	private void convertAndSetParteCorrelataToJsonArray(ParteCorrelataView parteCorrelataView)  throws Throwable {
		List<ParteCorrelataView> lista=parteCorrelataView.getListaParteCorrelata();
		JSONArray jsonArray = new JSONArray();
		if( lista != null && lista.size() > 0 ){
			for( ParteCorrelataView view : lista ){
				JSONObject jsonObject = new JSONObject();

				ParteCorrelata vo = view.getVo();
				jsonObject.put("id", vo.getId() );

				String denominazione = vo.getDenominazione().replace("'"," ").replace("\"", " ");
				denominazione=denominazione.trim();
				denominazione=org.apache.commons.lang3.StringEscapeUtils.escapeJson(denominazione);

				jsonObject.put("denominazione", denominazione);

				jsonArray.put(jsonObject); 
			}
		}

		if (jsonArray.length() > 0) {
			parteCorrelataView.setJsonArrayParteCorrelataMod(jsonArray.toString());
		} 
		else
			parteCorrelataView.setJsonArrayParteCorrelataMod(null);
	}

}