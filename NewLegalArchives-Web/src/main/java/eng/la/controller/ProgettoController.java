package eng.la.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eng.la.persistence.DocumentaleDAO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

//import com.filenet.api.collection.DocumentSet;
//import com.filenet.api.collection.PageIterator;
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;

import eng.la.business.NotificaWebService;
import eng.la.business.ProgettoService;
import eng.la.model.Fascicolo;
import eng.la.model.NotificaWeb;
import eng.la.model.Progetto;
import eng.la.model.aggregate.ProgettoAggregate;
import eng.la.model.filter.ProgettoFilter;
import eng.la.model.rest.ProgettoRest;
import eng.la.model.rest.RicercaProgettoRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.NotificaWebView;
import eng.la.model.view.ProgettoView;
import eng.la.model.view.UtenteView;
//@@DDS import eng.la.persistence.DocumentaleDAO;
import eng.la.persistence.DocumentaleDdsDAO;
import eng.la.persistence.FascicoloDAO;
import eng.la.presentation.validator.ProgettoValidator;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetUtil;
// engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("progettoController")
@SessionAttributes("progettoView")
public class ProgettoController extends BaseController {
	
	private static final String MODEL_VIEW_NOME = "progettoView";
	private static final String PAGINA_CREAZIONE = "progetto/formCreazioneProgetto";
	private static final String PAGINA_RICERCA = "progetto/ricercaProgetto";
	private static final String PAGINA_AZIONI_PROGETTO = "progetto/azioniProgetto";
//	private static final String PAGINA_MODIFICA_PROGETTO = "progetto/modificaProgetto";
	private static final String PAGINA_FORM_PATH = "progetto/modificaProgetto";  
	private static final String PAGINA_PERMESSI_PROGETTO = "progetto/permessiProgetto";
	private static final String PAGINA_POPUP_RICERCA_PROGETTO = "progetto/popupRicercaProgetto";

	
	
	// messaggio di conferma
//	private static final String SUCCESS_MSG ="successMsg";
//	private static final int ELEMENTI_PER_PAGINA_PC = 100;
//	private static final String REMOTE_USER_PARAM_NAME = "REMOTE_USER";
	
	@Autowired
	private NotificaWebService notificaWebService;

	@Autowired
	private FascicoloDAO fascicoloDAO;

	@Autowired
	private ProgettoService progettoService;

	/**
	 * Carica form creazione progetto
	 * @param model modello dati
	 * @param locale country locale
	 * @return page on
	 */
	@RequestMapping(value="/progetto/crea", method=RequestMethod.GET)
	public String creazioneProgetto(Locale locale, Model model, HttpServletRequest request) {
		ProgettoView progettoView = new ProgettoView();
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			progettoView.setLocale(locale);
			super.caricaListe(progettoView, locale);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		String noRemakeView = request.getParameter("noRemakeView");
		if(noRemakeView == null){
			model.addAttribute(MODEL_VIEW_NOME, progettoView);
		}
		
    	return PAGINA_CREAZIONE;
	}
	
//	/**
//	 * <p>
//	 * @param progettoView
//	 * @return
//	 */
//	@RequestMapping(value = "/progetto/salva", method=RequestMethod.POST)
//	public String salvaProgetto(Locale locale, Model model, 
//			@ModelAttribute(MODEL_VIEW_NOME) @Validated ProgettoView progettoView,
//			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) { 
//		
//		try{
//		    if( bindingResult.hasErrors() ){
//				return PAGINA_CREAZIONE;
//			}
//			
//			makeVoForSave(progettoView);
//			ProgettoView progettoViewSaved = progettoService.inserisci(progettoView);
//			Progetto progetto = progettoViewSaved != null ? progettoViewSaved.getVo() : null;
//			
//			List<MultipartFile> files = progettoView.getFiles();
//			getProgettoParentFolder(progetto.getId(), progetto.getDataCreazione());
//			
//			for (Iterator<MultipartFile> iterator = files.iterator(); iterator.hasNext();) {
//				MultipartFile file = (MultipartFile) iterator.next();
//				if(file != null && progetto != null && file.getOriginalFilename() != null && 
//						!file.getOriginalFilename().isEmpty()){
//					Progetto progettoSaved = addProgettoDocument(progetto, file);
//				}
//			}
//			
//			
//			
//			clearViewInsertFields(progettoView);
//			
//			model.addAttribute("successMessage", "messaggio.operazione.ok");
//			return "redirect:crea.action?noRemakeView=true";
//			
//		}catch(Throwable e){
//			e.printStackTrace();
//			bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
//			return PAGINA_CREAZIONE;
//		}
//	}
	
	
	@RequestMapping(value = "/progetto/salva", method = RequestMethod.POST)
	public String salvaProgetto(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated ProgettoView progettoView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
				String token=request.getParameter("CSRFToken");
		try {
			
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);


			if (progettoView.getOp() != null && !progettoView.getOp().equals("salvaProgetto")) {
				String ritorno = invocaMetodoDaReflection(progettoView, bindingResult, locale, model, request,
						response, this);

				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			preparaPerSalvataggio(progettoView, bindingResult,utenteView);

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			long progettoId = 0;
			if (progettoView.getProgettoId() == null || progettoView.getProgettoId() == 0) {
				ProgettoView progetto = progettoService.inserisci(progettoView);
				progettoId = progetto.getVo().getId();
			} else {
				progettoService.modifica(progettoView);
				progettoId = progettoView.getVo().getId();
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
				return "redirect:modifica.action?id=" + progettoId+"&CSRFToken="+token;
		} catch (Throwable e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}
	
	private void preparaPerSalvataggio(ProgettoView view, BindingResult bindingResult, UtenteView utenteView) throws Throwable {
		Progetto vo = new Progetto();

		if (view.getProgettoId() != null) {
			ProgettoView oldProgettoView = progettoService.leggi(view.getProgettoId());

			vo.setId(view.getProgettoId());
			vo.setDataCreazione(oldProgettoView.getVo().getDataCreazione());
			vo.setDataChiusura(DateUtil.toDateTime(view.getDataChiusura()));
			vo.setNome(view.getNome());
			vo.setOggetto(view.getOggetto());
			vo.setDescrizione(view.getDescrizione());
		}else{
			if (view.getDataCreazione() !=null && !"".equals(view.getDataCreazione()) ) 
				vo.setDataCreazione(new SimpleDateFormat("dd/MM/yyyy").parse(view.getDataCreazione()));
			if (view.getDataChiusura() !=null && !"".equals(view.getDataChiusura()) ) 
				vo.setDataChiusura(new SimpleDateFormat("dd/MM/yyyy").parse(view.getDataChiusura()));
			vo.setNome(view.getNome());
			vo.setOggetto(view.getOggetto());
			vo.setDescrizione(view.getDescrizione());
			vo.setLegaleInterno(utenteView.getVo().getUseridUtil());
		}
		
		view.setVo(vo);
	}

//	private void clearViewInsertFields(ProgettoView progettoView) {
//		progettoView.setDataCreazione(null);
//		progettoView.setDataChiusura(null);
//		progettoView.setNome(null);
//		progettoView.setOggetto(null);
//		progettoView.setDescrizione(null);
//	}
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
	}
	
	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
	
//	private void makeVoForSave(ProgettoView progettoView) {
//		 Progetto vo = new Progetto();
//		 try {
//			 vo.setDataCreazione(new SimpleDateFormat("dd/MM/yyyy").parse(progettoView.getDataCreazione()));
//			 vo.setDataChiusura(new SimpleDateFormat("dd/MM/yyyy").parse(progettoView.getDataChiusura()));
//		 } catch (ParseException e) { }
//		 
//		 vo.setOggetto(progettoView.getOggetto());
//		 vo.setNome(progettoView.getNome());
//		 vo.setDescrizione(progettoView.getDescrizione());
//		 
//		 progettoView.setVo(vo);
//	}
	
//	private void getProgettoParentFolder(Long idProgetto, Date dataApertura) throws Throwable {
//		if(idProgetto != null && dataApertura != null){
//			String parentFolderName = FileNetUtil.getProgettoParentFolder(dataApertura);
//			String folderName = idProgetto + "-PROGETTO";
//			String uuid = UUID.randomUUID().toString();
//			String folderClassName = FileNetClassNames.PROGETTO_FOLDER; 
//			Map<String, Object> folderProperty = new HashMap<String,Object>();
//			folderProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idProgetto+""));
//			
//			Folder parentFolder = documentaleDAO.leggiCartella(parentFolderName);
//			if(parentFolder == null){ 
//				documentaleDAO.verificaCreaPercorsoCartella(parentFolderName);		
//				parentFolder = documentaleDAO.leggiCartella(parentFolderName);
//			}
//			
//			String progettoFolderName = idProgetto + "-PROGETTO";
//			progettoFolderName = parentFolderName + progettoFolderName + "/";
//			Folder progettoFolder = documentaleDAO.leggiCartella(progettoFolderName);
//			if(progettoFolder == null){
//				documentaleDAO.creaCartella(uuid, folderName, folderClassName, folderProperty, parentFolder);
//			}
//		}
//	}
	
	@RequestMapping(value = "/progetto/ricerca", method=RequestMethod.GET)
	public String ricerca(ProgettoView progettoView, HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {
			
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		if (progettoView == null)
			progettoView = new ProgettoView();

		try {
			progettoView.setLocale(locale);
			this.caricaListe(progettoView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		model.addAttribute("progettoView", progettoView);
		return PAGINA_RICERCA;
	}
	
	@RequestMapping(value = "/progetto/popupricerca", method = RequestMethod.GET)
	public String popupricerca(@RequestParam("idFascicolo") String idFascicolo,ProgettoView progettoView, HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {
		
		// engsecurity VA -- in loading
				//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				//htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		if (progettoView == null)
			progettoView = new ProgettoView();

		try {
			progettoView.setLocale(locale);
			this.caricaListeOggetti(progettoView, locale);
			Fascicolo f = fascicoloDAO.leggi(new Long(idFascicolo).longValue());
			
			request.setAttribute("nomeProgettoAssociato",f.getProgetto()!=null?f.getProgetto().getNome():"nonAssociato");
		} catch (Throwable e) {
			e.printStackTrace();
		}
//		String nomeProgetto="";
//		if (!request.getParameter("nomeProgetto").equals("nonAssociato")){
//			nomeProgetto = request.getParameter("nomeProgetto");
//		}
		
		request.setAttribute("idFascicoloDaAssociare",idFascicolo);

		model.addAttribute("progettoView", progettoView);
		return 	PAGINA_POPUP_RICERCA_PROGETTO;
	}
	 
	@RequestMapping(value = "/progetto/cerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaProgettoRest cercaProgetto(ProgettoView progettoView, HttpServletRequest request, Model model,
			Locale locale) throws Throwable {
		
		if (progettoView == null){
			progettoView = new ProgettoView(); 
		}
		
		String lingua = locale.getLanguage().toUpperCase();
		int numElementiPerPagina = request.getParameter("limit") == null ? 108 : NumberUtils.toInt(request.getParameter("limit"));
		String offset = request.getParameter("offset");
		int numeroPagina = offset == null || offset.equals("0") ? 1 : (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;

		List<Progetto> projectList = null;
		ProgettoFilter filter = new ProgettoFilter();
		filter.setNumeroPagina(numeroPagina);
		filter.setNumElementiPerPagina(numElementiPerPagina);
		
		String dataCreateDal = request.getParameter("dataCreateDal");
		if (dataCreateDal != null && !dataCreateDal.trim().equals("0") && !dataCreateDal.trim().equals("")) {
			filter.setDataCreazioneDal(dataCreateDal.trim().length() > 1 && DateUtil.isData(dataCreateDal) ? 
					DateUtil.toDate(dataCreateDal) : null);
		}
		String dataCreateAl = request.getParameter("dataCreateAl");
		if (dataCreateAl != null && !dataCreateAl.trim().equals("0") && !dataCreateAl.trim().equals("")) {
			filter.setDataCreazioneAl(dataCreateAl.trim().length() > 1 && DateUtil.isData(dataCreateAl) ? 
					DateUtil.toDate(dataCreateAl) : null);
		}
		
		String dataCloseDal = request.getParameter("dataCloseDal");
		if (dataCloseDal != null && !dataCloseDal.trim().equals("0") && !dataCloseDal.trim().equals("")) {
			filter.setDataCreazioneDal(dataCloseDal.trim().length() > 1 && DateUtil.isData(dataCloseDal) ? 
					DateUtil.toDate(dataCloseDal) : null);
		}
		String dataCloseAl = request.getParameter("dataCloseAl");
		if (dataCloseAl != null && !dataCloseAl.trim().equals("0") && !dataCloseAl.trim().equals("")) {
			filter.setDataCreazioneAl(dataCloseAl.trim().length() > 1 && DateUtil.isData(dataCloseAl) ? 
					DateUtil.toDate(dataCloseAl) : null);
		}

		String nome = request.getParameter("nome");
		if (nome != null && !nome.trim().equals("0") && !nome.trim().equals("")) {
			filter.setNome(nome);
		}
		
		String descr = request.getParameter("descr");
		if (descr != null && !descr.trim().equals("0") && !descr.trim().equals("")) {
			filter.setDescrizione(descr);
		}
		
		String oggetto = request.getParameter("oggetto");
		if (oggetto != null && !oggetto.trim().equals("0") && !oggetto.trim().equals("")) {
			filter.setOggetto(oggetto);
		}
		
		String order = request.getParameter("order");
		if (order != null) {
			filter.setOrder(!order.trim().equals("") ? order : "desc");
		}
		
		try {
			ProgettoAggregate aggregate = progettoService.searchPagedProject(filter);
			projectList = aggregate != null ? aggregate.getList() : null;
			RicercaProgettoRest ricercaProgettoRest = new RicercaProgettoRest();
			ricercaProgettoRest.setTotal(aggregate != null ? aggregate.getNumeroTotaleElementi() : 0);
			ricercaProgettoRest.setRows(convertProjectToRest(projectList, lingua,request.getParameter("idFascicolo")));
			
			String idFascicolo = request.getParameter("idFascicolo");
			if (idFascicolo!=null && !idFascicolo.trim().isEmpty() && !idFascicolo.equals("undefined") && (new Long(idFascicolo).longValue()>0)){
				Fascicolo f = fascicoloDAO.leggi(new Long(idFascicolo).longValue());
				
				request.setAttribute("nomeProgettoAssociato",f.getProgetto()!=null?f.getProgetto().getNome():"nonAssociato");
			}

			return ricercaProgettoRest;

		} catch (Exception e) {
			return new RicercaProgettoRest();
		}

	}
	
	private List<ProgettoRest> convertProjectToRest(List<Progetto> list, String lingua, String idFascicolo) throws Throwable {
		List<ProgettoRest> out = new ArrayList<ProgettoRest>();
		
		String idProgettoAssociato="";
		if (idFascicolo!=null && !idFascicolo.isEmpty() && !idFascicolo.equals("undefined")){
			Fascicolo idFascicoloAssociato = fascicoloDAO.leggi(new Long(idFascicolo));
			idProgettoAssociato = idFascicoloAssociato.getProgetto()!=null?idFascicoloAssociato.getProgetto().getId()+"":"";
		}
		
		
		
		for (Progetto project : list) {
			ProgettoRest progettoRest = new ProgettoRest();
			
			progettoRest.setDataCreazione(new SimpleDateFormat("dd/MM/yyyy").format(project.getDataCreazione()));
			progettoRest.setDataChiusura(project.getDataChiusura()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(project.getDataChiusura()));
			progettoRest.setId(project.getId());
			progettoRest.setNome(project.getNome());
			progettoRest.setDescrizione(project.getDescrizione().length() > 20 ? project.getDescrizione().substring(0, 20) + "..." : project.getDescrizione());
			progettoRest.setOggetto(project.getOggetto());
			
			if(project.getDataChiusura() != null){
				progettoRest.setStato("CHIUSO");
				if(project.getDataChiusura().after(new Date())){
					progettoRest.setStato("APERTO");
				}
			}else {
				progettoRest.setStato("APERTO");
			}
			
			String titleAlt = "-";
			progettoRest.setAzioni("<p id='action-progetto-" + project.getId() + "' alt='" + titleAlt + "' title='" + titleAlt + "'></p>");
			if ((""+project.getId()).equals(idProgettoAssociato)){
				progettoRest.setRadio("<div><input value='" + project.getId() + "' type='radio' name='optradio' checked></div>");
			}else{
				progettoRest.setRadio("<div><input value='" + project.getId() + "' type='radio' name='optradio'></div>");
			}
			
			out.add(progettoRest);
		}
		
		return out;
	}
	
	@RequestMapping(value = "/progetto/caricaAzioniProgetto", method = RequestMethod.POST)
	public String caricaAzioniProgetto(@RequestParam("idProgetto") long idProgetto,@RequestParam("nomeProgetto") String nomeProgetto, HttpServletRequest req, Locale locale) {
		
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

			req.setAttribute("modifica", "not");

			req.setAttribute("idProgetto", idProgetto);
			req.setAttribute("nomeProgetto", nomeProgetto);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return PAGINA_AZIONI_PROGETTO;
	}
	
	
	@RequestMapping(value="/progetto/eliminaProgetto", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaProgetto(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		
		/*// engsecurity VA ????? json
					HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
					htmlActionSupport.checkCSRFToken(request);
			        //removeCSRFToken(request);
*/		
		try {
			
			
			ProgettoView view = new ProgettoView();
			ProgettoView progettoSalvato = (ProgettoView) progettoService.leggi(id);
			if (progettoSalvato != null && progettoSalvato.getVo() != null) {
				popolaFormDaVo(view, progettoSalvato.getVo());
				progettoSalvato.getVo().setDataCancellazione(new Date());
				
				List<DocumentoView> daRimuovere = view.getListaAllegatiGenerici();
				Set<String> setDaRimuovere = new HashSet<>();
				if (daRimuovere!=null && !daRimuovere.isEmpty()){
					for (Iterator<DocumentoView> iterator = daRimuovere.iterator(); iterator.hasNext();) {
						DocumentoView documentoView = (DocumentoView) iterator.next();
						setDaRimuovere.add(documentoView.getUuid());
					}
				}
				
				view.setAllegatiDaRimuovereUuid(setDaRimuovere);
				view.setListaAllegatiGenerici(null);
				view.setVo(progettoSalvato.getVo());
				progettoService.modifica(view);
				
			} 
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	
	@RequestMapping(value = "/progetto/estendiPermessiProgetto", method = RequestMethod.POST, produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest  estendiPermessiProgetto( HttpServletRequest request  ) { 
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		
		// engsecurity VA - json
					//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
					//htmlActionSupport.checkCSRFToken(request);
			        //removeCSRFToken(request);
		try {   
			String id = request.getParameter("idProgetto");
			String[] permessiScritturaArray = request.getParameterValues("permessoScrittura");
			String[] permessiLetturaArray = request.getParameterValues("permessoLettura");  
			
			if( id == null ){
				throw new RuntimeException("FascicoloId non possono essere null");
			}
			
			Long progettoId = NumberUtils.toLong(id); 
			progettoService.salvaPermessiProgetto(progettoId, permessiScritturaArray, permessiLetturaArray);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			try {
				UtenteView utenteConnesso = (UtenteView) request.getSession()
						.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

				NotificaWebView notificaWeb = new NotificaWebView();
				ProgettoView prog = progettoService.leggi(progettoId);				
				NotificaWeb notifica = new NotificaWeb();
				notifica.setDataNotifica(new Date());
				notifica.setKeyMessage("progetto.permessi.estesi");
				notifica.setJsonParam(prog.getVo().getNome());
				notifica.setMatricolaMitt(utenteConnesso.getVo());
				notificaWeb.setVo(notifica);
				
				notificaWebService.inserisciEstensionePermessi(notificaWeb,permessiScritturaArray);
			} catch (Exception e) {
				System.out.println("Errore in invio notifica" + e);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	
	private void popolaFormDaVo(ProgettoView view, Progetto vo) throws Throwable {
		view.setDataCreazione(DateUtil.formattaData(vo.getDataCreazione().getTime()));
		view.setDataChiusura(vo.getDataChiusura()==null?null:DateUtil.formattaData(vo.getDataChiusura().getTime()));
		view.setNome(vo.getNome());
		view.setOggetto(vo.getOggetto());
		view.setDescrizione(vo.getDescrizione());
		view.setProgettoId(vo.getId());

		caricaDocumentiGenericiFilenet(view, vo);
	}

	private void caricaDocumentiGenericiFilenet(ProgettoView view, Progetto vo) {

		//@@DDS DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		String parentFolderName = FileNetUtil.getProgettoParentFolder(vo.getDataCreazione());
		String progettoFolderName = vo.getId() + "-PROGETTO";
		progettoFolderName = parentFolderName + progettoFolderName + "/";
		Folder progettoFolder = null;
		try {
			//@@DDS progettoFolder = documentaleDAO.leggiCartella(progettoFolderName);
			progettoFolder = documentaleDdsDAO.leggiCartella(progettoFolderName);
			if (progettoFolder != null) {
			/*@@DDS inizio commento
			DocumentSet documenti = progettoFolder.get_ContainedDocuments();
			List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
			if (documenti != null) {
				PageIterator it = documenti.pageIterator();
				while (it.nextPage()) {
					EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
					for (EngineObjectImpl objDocumento : documentiArray) {
						DocumentImpl documento = (DocumentImpl) objDocumento;
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.get_Name());
						docView.setUuid(documento.get_Id().toString());
						listaDocumenti.add(docView);
					}
				}
				view.setListaAllegatiGenerici(listaDocumenti);

			}
			*/
				List<Document> documenti = documentaleDdsDAO.leggiDocumentiCartella(progettoFolderName);
				List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
				if (documenti != null) {

					for (Document documento:documenti) {

						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.getContents().get(0).getContentsName());
						docView.setUuid(documento.getId().toString());
						listaDocumenti.add(docView);
					}

					view.setListaAllegatiGenerici(listaDocumenti);

				}
			}
		} catch (Throwable e) {
			// potrebbe essere corretto che la cartella non esista poiche' in inserimento gli allegati non sono obbligatori
			e.printStackTrace();
		}


	}
	
	
	@RequestMapping(value= "/progetto/modifica",method = RequestMethod.GET)
	public String modificaProgetto(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		ProgettoView view = new ProgettoView();
		// engsecurity VA
					HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
					htmlActionSupport.checkCSRFToken(request);
			        //removeCSRFToken(request);
		try {
			ProgettoView progettoSalvato = (ProgettoView) progettoService.leggi(id);
			if (progettoSalvato != null && progettoSalvato.getVo() != null) {
				popolaFormDaVo(view, progettoSalvato.getVo());
				
				super.caricaListe(view, locale);
			} else {
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}

		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action": PAGINA_FORM_PATH;
	}
	
	@RequestMapping(value= "/progetto/visualizza",method = RequestMethod.GET)
	public String visualizzaProgetto(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		
		// engsecurity VA
				//	HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				//	htmlActionSupport.checkCSRFToken(request);
			        //removeCSRFToken(request);
		
		request.setAttribute("disableInVis", "true");
		return modificaProgetto(id,model,locale,request);
	}
	
	@RequestMapping(value = "/progetto/uploadAllegatoGenerico", method = RequestMethod.POST)
	public String uploadAllegatoGenerico( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) ProgettoView view ) {  
		
		// engsecurity VA
				//	HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				//	htmlActionSupport.checkCSRFToken(request);
			        //removeCSRFToken(request);
		try {   
			String progettoId = request.getParameter("idProgetto"); 
		    if( progettoId == null  ){
				throw new RuntimeException("incaricoId non possono essere null");
			}
		    File fileTmp = File.createTempFile("allegatoGenerico", "___" + file.getOriginalFilename() );
		    FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
		    DocumentoView documentoView = new DocumentoView();
		    
		    documentoView.setUuid("" +( view.getListaAllegatiGenerici() == null || view.getListaAllegatiGenerici().size() == 0 ?  1 : view.getListaAllegatiGenerici().size()+1));
		    documentoView.setFile(fileTmp);
		    documentoView.setNomeFile(file.getOriginalFilename());
		    documentoView.setContentType(file.getContentType());
		    documentoView.setNuovoDocumento(true);
		    List<DocumentoView> allegatiGenerici = view.getListaAllegatiGenerici()==null?new ArrayList<DocumentoView>():view.getListaAllegatiGenerici();
		    allegatiGenerici.add(documentoView);
		    view.setListaAllegatiGenerici(allegatiGenerici);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "progetto/allegatiGenerici";
	}
	
	
	@RequestMapping(value = "/progetto/rimuoviAllegatoGenerico", method = RequestMethod.POST)
	public String rimuoviAllegatoGenerico( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) ProgettoView view ) {  
		
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {   
			String uuid = request.getParameter("uuid"); 
		    if( uuid == null  ){
				throw new RuntimeException("uuid non pu� essere null");
			}
		     
		    List<DocumentoView> allegatiGenericiOld = view.getListaAllegatiGenerici();
		    List<DocumentoView> allegatiGenerici = new ArrayList<DocumentoView>();
		    allegatiGenerici.addAll(allegatiGenericiOld);
		    Set<String> allegatiDaRimuovere = view.getAllegatiDaRimuovereUuid() == null ? new HashSet<String>():view.getAllegatiDaRimuovereUuid();
		    if( allegatiGenericiOld != null){
		    	for(DocumentoView docView: allegatiGenericiOld){
		    		if( docView.getUuid().contains(uuid) ){
		    			if( docView.getUuid().length() >= 30 ){
							allegatiDaRimuovere.add(uuid);
						}
		    			allegatiGenerici.remove(docView);
		    			break;
		    		}
		    	}
		    }
		    view.setListaAllegatiGenerici(allegatiGenerici);
		    view.setAllegatiDaRimuovereUuid(allegatiDaRimuovere);
		    
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "progetto/allegatiGenerici";
	}
	
	@RequestMapping(value = "/progetto/caricaGrigliaPermessiProgetto", method = RequestMethod.POST)
	public String caricaGrigliaPermessiProgetto(@RequestParam("id") long idProgetto, HttpServletRequest req,
			Locale locale) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(req);
        //removeCSRFToken(request);
		try {

			// TODO: LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI
			// SUL Progetto
			req.setAttribute("idProgetto", idProgetto);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return PAGINA_PERMESSI_PROGETTO;
	}

	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ProgettoValidator());

		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	
}
