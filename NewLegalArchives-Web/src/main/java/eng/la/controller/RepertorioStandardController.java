package eng.la.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;

import eng.la.business.RepertorioStandardService;
import eng.la.business.SocietaService;
import eng.la.model.PosizioneOrganizzativa;
import eng.la.model.PrimoLivelloAttribuzioni;
import eng.la.model.RepertorioStandard;
import eng.la.model.SecondoLivelloAttribuzioni;
import eng.la.model.Societa;
import eng.la.model.Utente;
import eng.la.model.aggregate.RepertorioStandardAggregate;
import eng.la.model.filter.RepertorioStandardFilter;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.rest.RepertorioStandardRest;
import eng.la.model.rest.RicercaRepertorioStandardRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.RepertorioStandardView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.UtenteView;
//@@DDS import eng.la.persistence.DocumentaleDAO;
import eng.la.persistence.DocumentaleDdsDAO;
import eng.la.presentation.validator.RepertorioStandardValidator;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("repertorioStandardController")
@SessionAttributes("repertorioStandardView")
public class RepertorioStandardController extends BaseController {
	
	private static final String MODEL_VIEW_NOME = "repertorioStandardView";
	private static final String PAGINA_CREAZIONE = "repertorioStandard/formCreazioneRepertorioStandard";
	private static final String PAGINA_RICERCA = "repertorioStandard/ricercaRepertorioStandard";
	private static final String PAGINA_AZIONI_REPERTORIO_STANDARD = "repertorioStandard/azioniRepertorioStandard";
//	private static final String PAGINA_MODIFICA_REPERTORIO_STANDARD = "repertorioStandard/modificaRepertorioStandard";
	private static final String PAGINA_FORM_PATH = "repertorioStandard/modificaRepertorioStandard";  
//	private static final String PAGINA_PERMESSI_REPERTORIO_STANDARD = "repertorioStandard/permessiRepertorioStandard";
//	private static final String PAGINA_POPUP_RICERCA_REPERTORIO_STANDARD = "repertorioStandard/popupRicercaRepertorioStandard";

	
	
	// messaggio di conferma
//	private static final String SUCCESS_MSG ="successMsg";
//	private static final int ELEMENTI_PER_PAGINA_PC = 100;
//	private static final String REMOTE_USER_PARAM_NAME = "REMOTE_USER";
	
	
	@Autowired
	private RepertorioStandardService repertorioStandardService;

	
	/**
	 * Carica form creazione repertorioStandard
	 * @param model modello dati
	 * @param locale country locale
	 * @return page on
	 */
	@RequestMapping("/repertorioStandard/crea")
	public String creazioneRepertorioStandard(Locale locale, Model model, HttpServletRequest request) {
		RepertorioStandardView repertorioStandardView = new RepertorioStandardView();
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			repertorioStandardView.setLocale(locale);
			this.caricaListe(repertorioStandardView, locale);
			repertorioStandardView.setLstSocieta(repertorioStandardService.findSocieta());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		String noRemakeView = request.getParameter("noRemakeView");
		if(noRemakeView == null){
			model.addAttribute(MODEL_VIEW_NOME, repertorioStandardView);
		}
		
    	return PAGINA_CREAZIONE;
	}
	
	@RequestMapping(value = "/repertorioStandard/salva", method = RequestMethod.POST)
	public String salvaRepertorioStandard(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated RepertorioStandardView repertorioStandardView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			String token=request.getParameter("CSRFToken");

		try {
			
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);


			if (repertorioStandardView.getOp() != null && !repertorioStandardView.getOp().equals("salvaRepertorioStandard")) {
				String ritorno = invocaMetodoDaReflection(repertorioStandardView, bindingResult, locale, model, request,
						response, this);

				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				if (repertorioStandardView.getRepertorioStandardId() == null || repertorioStandardView.getRepertorioStandardId() == 0) {
					return PAGINA_CREAZIONE;
				}
				return PAGINA_FORM_PATH;
			}

			preparaPerSalvataggio(repertorioStandardView, bindingResult,utenteView);


			long repertorioStandardId = 0;
			if (repertorioStandardView.getRepertorioStandardId() == null || repertorioStandardView.getRepertorioStandardId() == 0) {
				RepertorioStandardView repertorioStandard = repertorioStandardService.inserisci(repertorioStandardView);
				repertorioStandardId = repertorioStandard.getVo().getId();
			} else {
				repertorioStandardService.modifica(repertorioStandardView);
				repertorioStandardId = repertorioStandardView.getVo().getId();
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
				return "redirect:modifica.action?id=" + repertorioStandardId+"&CSRFToken="+token;
		} catch (Throwable e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}
	
	private void preparaPerSalvataggio(RepertorioStandardView view, BindingResult bindingResult, UtenteView utenteView) throws Throwable {
		RepertorioStandard vo = new RepertorioStandard();

		if (view.getRepertorioStandardId() != null) {
			RepertorioStandardView oldRepertorioStandardView = repertorioStandardService.leggi(view.getRepertorioStandardId());

			vo.setId(view.getRepertorioStandardId());
			vo.setNome(view.getNome());
			Utente utente = new Utente();
			utente.setMatricolaUtil(utenteView.getVo().getMatricolaUtil());
			vo.setUtente(utente);
			if (view.getIdPrimoLivelloAttribuzioni()!=null){
				PrimoLivelloAttribuzioni pla = new PrimoLivelloAttribuzioni();
				pla.setId(new Long(view.getIdPrimoLivelloAttribuzioni()));
				vo.setPrimoLivelloAttribuzioni(pla);
			}

			if (view.getIdSecondoLivelloAttribuzioni()!=null){
				SecondoLivelloAttribuzioni sla = new SecondoLivelloAttribuzioni();
				sla.setId(new Long(view.getIdSecondoLivelloAttribuzioni()));
				vo.setSecondoLivelloAttribuzioni(sla);
			}

			if (view.getIdPosizioneOrganizzativa()!=null){
				vo.setPosizioneOrganizzativa(new PosizioneOrganizzativa(view.getIdPosizioneOrganizzativa()));
			}

//			Societa s = new Societa();
//			s.setId(view.getIdSocieta());
//			vo.setSocieta(s);
			vo.setSocieta(view.getSocietaSelezionata());
			vo.setDataModifica(new Date());
			vo.setNota(view.getNota());

			vo.setLingua(oldRepertorioStandardView.getVo().getLingua());
			vo.setCodGruppoLingua(oldRepertorioStandardView.getVo().getCodGruppoLingua());
			vo.setDataCreazione(oldRepertorioStandardView.getVo().getDataCreazione());


		}else{
			vo.setNome(view.getNome());
			Utente utente = new Utente();
			utente.setMatricolaUtil(utenteView.getVo().getMatricolaUtil());
			vo.setUtente(utente);
			
			if (view.getIdPrimoLivelloAttribuzioni()!=null){
				PrimoLivelloAttribuzioni pla = new PrimoLivelloAttribuzioni();
				pla.setId(new Long(view.getIdPrimoLivelloAttribuzioni()));
				vo.setPrimoLivelloAttribuzioni(pla);
			}

			if (view.getIdSecondoLivelloAttribuzioni()!=null){
				SecondoLivelloAttribuzioni sla = new SecondoLivelloAttribuzioni();
				sla.setId(new Long(view.getIdSecondoLivelloAttribuzioni()));
				vo.setSecondoLivelloAttribuzioni(sla);
			}

			if (view.getIdPosizioneOrganizzativa()!=null){
				vo.setPosizioneOrganizzativa(new PosizioneOrganizzativa(view.getIdPosizioneOrganizzativa()));
			}
//			Societa s = new Societa();
//			s.setId(view.getIdSocieta());
//			vo.setSocieta(s);
			vo.setSocieta(view.getSocietaSelezionata());
			vo.setDataModifica(new Date());
			vo.setDataCreazione(new Date());
			vo.setNota(view.getNota());

			vo.setLingua(utenteView.getLocale().getLanguage().split("_")[1].toUpperCase());
		}
		
		view.setVo(vo);
	}



//	private void clearViewInsertFields(RepertorioStandardView repertorioStandardView) {
//		repertorioStandardView.setDataCancellazione(null);
//		repertorioStandardView.setNome(null);
//		repertorioStandardView.setNota(null);
//		repertorioStandardView.setDataModifica(null);
//		repertorioStandardView.setDataCreazione(null);
//		repertorioStandardView.setIdPosizioneOrganizzativa(null);
//		repertorioStandardView.setIdPrimoLivelloAttribuzioni(null);
//		repertorioStandardView.setIdSocieta(null);
//		repertorioStandardView.setIdSecondoLivelloAttribuzioni(null);
//	}
	
	@Override	
	public void caricaListe(BaseView view, Locale locale) throws Throwable{
		SocietaService societaService = (SocietaService) SpringUtil.getBean("societaService");		
		List<SocietaView> listaSocieta = societaService.leggi(false);
		view.setListaSocieta(listaSocieta);
		caricaListeOggetti(view, locale);
	}
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		RepertorioStandardView repertorioStandardView = (RepertorioStandardView) view;
		List<CodiceDescrizioneBean> listaPosizioneOrganizzativa = repertorioStandardService.leggiListaPosizioneOrganizzativa(locale);
		repertorioStandardView.setListaPosizioneOrganizzativa(listaPosizioneOrganizzativa);
		List<CodiceDescrizioneBean> listaPrimoLivelloAttribuzioni = repertorioStandardService.leggiListaPrimoLivelloAttribuzioni(locale);
		repertorioStandardView.setListaPrimoLivelloAttribuzioni(listaPrimoLivelloAttribuzioni);
		List<CodiceDescrizioneBean> listaSecondoLivelloAttribuzioni = repertorioStandardService.leggiListaSecondoLivelloAttribuzioni(locale);
		repertorioStandardView.setListaSecondoLivelloAttribuzioni(listaSecondoLivelloAttribuzioni);
	}
	
	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
	
	
	@RequestMapping(value = "/repertorioStandard/ricerca")
	public String ricerca(RepertorioStandardView repertorioStandardView, HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		if (repertorioStandardView == null)
			repertorioStandardView = new RepertorioStandardView();

		try {
			repertorioStandardView.setLocale(locale);
			this.caricaListe(repertorioStandardView, locale);
			repertorioStandardView.setLstSocieta(repertorioStandardService.findSocieta());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		model.addAttribute("repertorioStandardView", repertorioStandardView);
		return PAGINA_RICERCA;
	}
	
	@RequestMapping(value = "/repertorioStandard/cerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaRepertorioStandardRest cercaRepertorioStandard(HttpServletRequest request, Model model,
			Locale locale) throws Throwable {


		
//		if (repertorioStandardView == null){
//			repertorioStandardView = new RepertorioStandardView(); 
//		}
		
		String lingua = locale.getLanguage().toUpperCase();
		int numElementiPerPagina = request.getParameter("limit") == null ? 108 : NumberUtils.toInt(request.getParameter("limit"));
		String offset = request.getParameter("offset");
		int numeroPagina = offset == null || offset.equals("0") ? 1 : (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
		


		List<RepertorioStandard> repertorioStandardList = null;
		RepertorioStandardFilter filter = new RepertorioStandardFilter();
		filter.setNumeroPagina(numeroPagina);
		filter.setNumElementiPerPagina(numElementiPerPagina);
		
		filter.setLingua(lingua);
		
		String nome = request.getParameter("nome");
		if (nome != null && !nome.equalsIgnoreCase("")) {
			filter.setNome(nome);
		}
		String nota = request.getParameter("nota");
		if (nota != null && !nota.equalsIgnoreCase("")) {
			filter.setNota(nota);
		}
		String idPosizioneOrganizzativa = request.getParameter("idPosizioneOrganizzativa");
		if (idPosizioneOrganizzativa != null && !idPosizioneOrganizzativa.isEmpty() && !idPosizioneOrganizzativa.equalsIgnoreCase("0")) {
			filter.setPosizioneOrganizzativa(new Long(idPosizioneOrganizzativa));
		}
		String idPrimoLivelloAttribuzioni = request.getParameter("idPrimoLivelloAttribuzioni");
		if (idPrimoLivelloAttribuzioni != null && !idPrimoLivelloAttribuzioni.isEmpty() && !idPrimoLivelloAttribuzioni.equalsIgnoreCase("0")) {
			filter.setPrimoLivelloAttribuzioni(new Long(idPrimoLivelloAttribuzioni));
		}
		String idSecondoLivelloAttribuzioni = request.getParameter("idSecondoLivelloAttribuzioni");
		if (idSecondoLivelloAttribuzioni != null && !idSecondoLivelloAttribuzioni.isEmpty() && !idSecondoLivelloAttribuzioni.equalsIgnoreCase("0")) {
			filter.setSecondoLivelloAttribuzioni(new Long(idSecondoLivelloAttribuzioni));
		}
		
		String idsocieta = request.getParameter("idSocieta");
		if (idsocieta != null && !idsocieta.isEmpty() && !idsocieta.equalsIgnoreCase("0")) {
			filter.setSocieta(idsocieta);
		}
		
		String order = request.getParameter("order");
		if (order != null) {
			filter.setOrder(!order.trim().equals("") ? order : "desc");
		}
		
		try {
			RepertorioStandardAggregate aggregate = repertorioStandardService.searchPagedRepertorioStandard(filter);
			repertorioStandardList = aggregate != null ? aggregate.getList() : null;
			RicercaRepertorioStandardRest ricercaRepertorioStandardRest = new RicercaRepertorioStandardRest();
			ricercaRepertorioStandardRest.setTotal(aggregate != null ? aggregate.getNumeroTotaleElementi() : 0);
			ricercaRepertorioStandardRest.setRows(convertRepertorioStandardToRest(repertorioStandardList, lingua));
			
			return ricercaRepertorioStandardRest;

		} catch (Exception e) {
			return new RicercaRepertorioStandardRest();
		}

	}
	

	
	private List<RepertorioStandardRest> convertRepertorioStandardToRest(List<RepertorioStandard> list, String lingua) throws Throwable {
		List<RepertorioStandardRest> out = new ArrayList<RepertorioStandardRest>();
		
		
		for (RepertorioStandard repertorioStandard : list) {
			RepertorioStandardRest repertorioStandardRest = new RepertorioStandardRest();
			
			repertorioStandardRest.setId(repertorioStandard.getId());
			repertorioStandardRest.setNome(repertorioStandard.getNome());
			repertorioStandardRest.setNota(repertorioStandard.getNota());

			repertorioStandardRest.setSocieta(repertorioStandard.getSocieta());
			if (repertorioStandard.getPosizioneOrganizzativa()!=null)
				repertorioStandardRest.setPosizioneOrganizzativa(repertorioStandard.getPosizioneOrganizzativa().getDescrizione());
			if (repertorioStandard.getPrimoLivelloAttribuzioni()!=null)
				repertorioStandardRest.setPrimoLivelloAttribuzioni(repertorioStandard.getPrimoLivelloAttribuzioni().getDescrizione());
			if (repertorioStandard.getSecondoLivelloAttribuzioni()!=null)
				repertorioStandardRest.setSecondoLivelloAttribuzioni(repertorioStandard.getSecondoLivelloAttribuzioni().getDescrizione());
			if (repertorioStandard.getUtente()!=null)
				repertorioStandardRest.setUtente(repertorioStandard.getUtente().getNominativoUtil());
			
			String titleAlt = "-";
			repertorioStandardRest.setAzioni("<p id='action-repertoriostandard-" + repertorioStandard.getId() + "' alt='" + titleAlt + "' title='" + titleAlt + "'></p>");
			out.add(repertorioStandardRest);
		}
		
		return out;
	}
	
	@RequestMapping(value = "/repertorioStandard/caricaAzioniRepertorioStandard", method = RequestMethod.POST)
	public String caricaAzioniRepertorioStandard(@RequestParam("idRepertorioStandard") long idRepertorioStandard, HttpServletRequest req, Locale locale) {

		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(req);
		        //removeCSRFToken(request);
			req.setAttribute("modifica", "yes");
			req.setAttribute("idRepertorioStandard", idRepertorioStandard);
		return PAGINA_AZIONI_REPERTORIO_STANDARD;
	}
	
	
	@RequestMapping(value="/repertorioStandard/eliminaRepertorioStandard", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaRepertorioStandard(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			RepertorioStandardView view = new RepertorioStandardView();
			RepertorioStandardView repertorioStandardSalvato = (RepertorioStandardView) repertorioStandardService.leggi(id);
			if (repertorioStandardSalvato != null && repertorioStandardSalvato.getVo() != null) {
				popolaFormDaVo(view, repertorioStandardSalvato.getVo());
				repertorioStandardSalvato.getVo().setDataCancellazione(new Date());

				
				String daRimuovere = (view.getAllegato()!=null && !view.getAllegato().isEmpty())==true?view.getAllegato().get(0).getUuid():"";
				
				view.setAllegatoDaRimuovereUuid(daRimuovere);
				view.setAllegato(null);
				view.setVo(repertorioStandardSalvato.getVo());
				repertorioStandardService.modifica(view);
				
			} 
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	

	
	private void popolaFormDaVo(RepertorioStandardView view, RepertorioStandard vo) throws Throwable {
		view.setId(vo.getId());
		view.setRepertorioStandardId(vo.getId());
		view.setNome(vo.getNome());
		view.setNota(vo.getNota());
		if (vo.getPosizioneOrganizzativa()!=null)
			view.setIdPosizioneOrganizzativa(vo.getPosizioneOrganizzativa().getId());
		view.setSocietaSelezionata(vo.getSocieta());
		if (vo.getPrimoLivelloAttribuzioni()!=null)
			view.setIdPrimoLivelloAttribuzioni(vo.getPrimoLivelloAttribuzioni().getId());
		if (vo.getSecondoLivelloAttribuzioni()!=null)
			view.setIdSecondoLivelloAttribuzioni(vo.getSecondoLivelloAttribuzioni().getId());
		caricaDocumentiGenericiFilenet(view, vo);

	}
	
	@RequestMapping(value= "/repertorioStandard/modifica",method = RequestMethod.GET)
	public String modificaRepertorioStandard(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		RepertorioStandardView view = new RepertorioStandardView();
		// engsecurity VA - redirect
				//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				//htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		try {
			RepertorioStandardView repertorioStandardSalvato = (RepertorioStandardView) repertorioStandardService.leggi(id);
			if (repertorioStandardSalvato != null && repertorioStandardSalvato.getVo() != null) {
				popolaFormDaVo(view, repertorioStandardSalvato.getVo());
				
				this.caricaListe(view, locale);
				view.setLstSocieta(repertorioStandardService.findSocieta());
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
	
	@RequestMapping(value= "/repertorioStandard/visualizza",method = RequestMethod.GET)
	public String visualizzaRepertorioStandard(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		request.setAttribute("disableInVis", "true");
		return modificaRepertorioStandard(id,model,locale,request);
	}




	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new RepertorioStandardValidator());
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(value = "/repertorioStandard/uploadAllegatoGenerico", method = RequestMethod.POST)
	public String uploadAllegatoGenerico( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) RepertorioStandardView view ) {  
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {   
			String repertorioStandardId = request.getParameter("idRepertorioStandard"); 
		    if( repertorioStandardId == null  ){
				throw new RuntimeException("idRepertorioStandard non possono essere null");
			}
		    File fileTmp = File.createTempFile("allegatoGenerico", "___" + file.getOriginalFilename() );
		    FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
		    DocumentoView documentoView = new DocumentoView();
		    
		    documentoView.setUuid("" +( view.getAllegato() == null || view.getAllegato().size() == 0 ?  1 : view.getAllegato().size()+1));
		    documentoView.setFile(fileTmp);
		    documentoView.setNomeFile(file.getOriginalFilename());
		    documentoView.setContentType(file.getContentType());
		    documentoView.setNuovoDocumento(true);
		    List<DocumentoView> allegatiGenerici = view.getAllegato()==null?new ArrayList<DocumentoView>():view.getAllegato();
		    allegatiGenerici.add(documentoView);
		    view.setAllegato(allegatiGenerici);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "repertorioStandard/allegatiGenerici";
	}
	
	
	@RequestMapping(value = "/repertorioStandard/rimuoviAllegatoGenerico", method = RequestMethod.POST)
	public String rimuoviAllegatoGenerico( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) RepertorioStandardView view ) {  
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {   
			String uuid = request.getParameter("uuid"); 
		    if( uuid == null  ){
				throw new RuntimeException("uuid non pu� essere null");
			}
		     
		    List<DocumentoView> allegatiGenericiOld = view.getAllegato();
		    List<DocumentoView> allegatiGenerici = new ArrayList<DocumentoView>();
		    allegatiGenerici.addAll(allegatiGenericiOld);
		    List<String> allegatiDaRimuovere = new ArrayList<>();
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
		    view.setAllegato(allegatiGenerici);
		    view.setAllegatoDaRimuovereUuid(allegatiDaRimuovere.get(0));
		    
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "repertorioStandard/allegatiGenerici";
	}
	
	private void caricaDocumentiGenericiFilenet(RepertorioStandardView view, RepertorioStandard vo) {

		//@@DDS DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		String parentFolderName = FileNetUtil.getRepertorioStandardParentFolder(vo.getDataCreazione());
		String repertorioFolderName = vo.getId() + "-REPERTORIO_STANDARD";
		repertorioFolderName = parentFolderName + repertorioFolderName + "/";
		Folder repertorioFolder = null;
		try {
			//@@DDS repertorioFolder = documentaleDAO.leggiCartella(repertorioFolderName);
			repertorioFolder = documentaleDdsDAO.leggiCartella(repertorioFolderName);


		if (repertorioFolder != null) {
			/*@@DDS inizio commento
			DocumentSet documenti = repertorioFolder.get_ContainedDocuments();
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
				view.setAllegato(listaDocumenti);

			}
			*/
			List<Document> documenti = documentaleDdsDAO.leggiDocumentiCartella(repertorioFolderName);
			List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
			if (documenti != null) {

					for (Document documento:documenti) {

						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.getContents().get(0).getContentsName());
						docView.setUuid(documento.getId().toString());
						listaDocumenti.add(docView);
					}

				view.setAllegato(listaDocumenti);

			}

		}
		} catch (Throwable e) {
			// potrebbe essere corretto che la cartella non esista poiche' in inserimento gli allegati non sono obbligatori
			e.printStackTrace();
		}

	}


	
}



