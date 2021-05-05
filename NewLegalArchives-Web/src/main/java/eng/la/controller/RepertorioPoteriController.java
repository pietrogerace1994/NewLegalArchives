package eng.la.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import eng.la.business.RepertorioPoteriService;
import eng.la.business.SocietaService;
import eng.la.model.CategoriaTessere;
import eng.la.model.RepertorioPoteri;
import eng.la.model.SubCategoriaTessere;
import eng.la.model.aggregate.RepertorioPoteriAggregate;
import eng.la.model.filter.RepertorioPoteriFilter;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.rest.RepertorioPoteriRest;
import eng.la.model.rest.RicercaRepertorioPoteriRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.RepertorioPoteriView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.UtenteView;
import eng.la.presentation.validator.RepertorioPoteriValidator;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("repertorioPoteriController")
@SessionAttributes("repertorioPoteriView")
public class RepertorioPoteriController extends BaseController {
	
	private static final String MODEL_VIEW_NOME = "repertorioPoteriView";
	private static final String PAGINA_CREAZIONE = "repertorioPoteri/formCreazioneRepertorioPoteri";
	private static final String PAGINA_RICERCA = "repertorioPoteri/ricercaRepertorioPoteri";
	private static final String PAGINA_AZIONI_PROCURE = "repertorioPoteri/azioniRepertorioPoteri";
//	private static final String PAGINA_MODIFICA_PROCURE = "repertorioPoteri/modificaRepertorioPoteri";
	private static final String PAGINA_FORM_PATH = "repertorioPoteri/modificaRepertorioPoteri";  
//	private static final String PAGINA_PERMESSI_PROCURE = "repertorioPoteri/permessiRepertorioPoteri";
//	private static final String PAGINA_POPUP_RICERCA_PROCURE = "repertorioPoteri/popupRicercaRepertorioPoteri";

	
	
	// messaggio di conferma
//	private static final String SUCCESS_MSG ="successMsg";
//	private static final int ELEMENTI_PER_PAGINA_PC = 100;
//	private static final String REMOTE_USER_PARAM_NAME = "REMOTE_USER";
	
	
	
	@Autowired
	private RepertorioPoteriService repertorioPoteriService;

	
	/**
	 * Carica form creazione repertorioPoteri
	 * @param model modello dati
	 * @param locale country locale
	 * @return page on
	 */
	@RequestMapping("/repertorioPoteri/crea")
	public String creazioneRepertorioPoteri(Locale locale, Model model, HttpServletRequest request) {
		RepertorioPoteriView repertorioPoteriView = new RepertorioPoteriView();
		
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			repertorioPoteriView.setLocale(locale);
			this.caricaListe(repertorioPoteriView, locale);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		String noRemakeView = request.getParameter("noRemakeView");
		if(noRemakeView == null){
			model.addAttribute(MODEL_VIEW_NOME, repertorioPoteriView);
		}
		
    	return PAGINA_CREAZIONE;
	}
	
	@RequestMapping(value = "/repertorioPoteri/salva", method = RequestMethod.POST)
	public String salvaRepertorioPoteri(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated RepertorioPoteriView repertorioPoteriView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
				String token=request.getParameter("CSRFToken");

		try {
			
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);


			if (repertorioPoteriView.getOp() != null && !repertorioPoteriView.getOp().equals("salvaRepertorioPoteri")) {
				String ritorno = invocaMetodoDaReflection(repertorioPoteriView, bindingResult, locale, model, request,
						response, this);

				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				if (repertorioPoteriView.getRepertorioPoteriId() == null || repertorioPoteriView.getRepertorioPoteriId() == 0) {
					return PAGINA_CREAZIONE;
				}
				return PAGINA_FORM_PATH;
			}

			preparaPerSalvataggio(repertorioPoteriView, bindingResult,utenteView);


			long repertorioPoteriId = 0;
			if (repertorioPoteriView.getRepertorioPoteriId() == null || repertorioPoteriView.getRepertorioPoteriId() == 0) {
				RepertorioPoteriView repertorioPoteri = repertorioPoteriService.inserisci(repertorioPoteriView);
				repertorioPoteriId = repertorioPoteri.getVo().getId();
			} else {
				repertorioPoteriService.modifica(repertorioPoteriView);
				repertorioPoteriId = repertorioPoteriView.getVo().getId();
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
//			if( isNew ){
				return "redirect:modifica.action?id=" + repertorioPoteriId+"&CSRFToken="+token;
//			}
//			return "redirect:dettaglio.action?id=" + repertorioPoteriId;
		} catch (Throwable e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}
	
	private void preparaPerSalvataggio(RepertorioPoteriView view, BindingResult bindingResult, UtenteView utenteView) throws Throwable {
		RepertorioPoteri vo = new RepertorioPoteri();

		if (view.getRepertorioPoteriId() != null) {
			RepertorioPoteriView oldRepertorioPoteriView = repertorioPoteriService.leggi(view.getRepertorioPoteriId());

			vo.setId(view.getRepertorioPoteriId());
			vo.setCodice(view.getCodice());
			CategoriaTessere ct = new CategoriaTessere();
			ct.setId(new Long(view.getIdCategoria()));
			vo.setCategoria(ct);
			SubCategoriaTessere sct = new SubCategoriaTessere();
			sct.setId(new Long(view.getIdSubcategoria()));
			vo.setSubcategoria(sct);
			vo.setDescrizione(view.getDescrizione());
			vo.setTesto(view.getTesto());
			vo.setLingua(oldRepertorioPoteriView.getVo().getLingua());
			vo.setCodGruppoLingua(oldRepertorioPoteriView.getVo().getCodGruppoLingua());

		}else{
			vo.setCodice(view.getCodice());
			CategoriaTessere ct = new CategoriaTessere();
			ct.setId(new Long(view.getIdCategoria()));
			vo.setCategoria(ct);
			SubCategoriaTessere sct = new SubCategoriaTessere();
			sct.setId(new Long(view.getIdSubcategoria()));
			vo.setSubcategoria(sct);
			vo.setDescrizione(view.getDescrizione());
			vo.setLingua(utenteView.getLocale().getLanguage().split("_")[1].toUpperCase());
			vo.setTesto(view.getTesto());
		}
		
		view.setVo(vo);
	}

//	private void clearViewInsertFields(RepertorioPoteriView repertorioPoteriView) {
//		repertorioPoteriView.setDataCancellazione(null);
//		repertorioPoteriView.setDescrizione(null);
//		repertorioPoteriView.setTesto(null);
//		repertorioPoteriView.setIdSubcategoria(null);
//		repertorioPoteriView.setIdCategoria(null);
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
		RepertorioPoteriView repertorioPoteriView = (RepertorioPoteriView) view;
		List<CodiceDescrizioneBean> listaCategorie = repertorioPoteriService.leggiListaCategorie(locale);
		repertorioPoteriView.setListaCategorie(listaCategorie);
		List<CodiceDescrizioneBean> listaSubCategorie = repertorioPoteriService.leggiListaSubCategorie(locale);
		repertorioPoteriView.setListaSubCategorie(listaSubCategorie);
	}
	
	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
	
//	private void makeVoForSave(RepertorioPoteriView repertorioPoteriView) {
//		 RepertorioPoteri vo = new RepertorioPoteri();
//
//		 CategoriaTessere ct = new CategoriaTessere();
//		 ct.setId(new Long(repertorioPoteriView.getIdCategoria()));
//		 vo.setCategoria(ct);
//
//		 SubCategoriaTessere sct = new SubCategoriaTessere();
//		 sct.setId(new Long(repertorioPoteriView.getIdSubcategoria()));
//		 vo.setSubcategoria(sct);
//		 
//		 vo.setCodGruppoLingua(repertorioPoteriView.getCodGruppoLingua());
//		 vo.setLingua(repertorioPoteriView.getLingua());
//		 
//		 vo.setCodice(repertorioPoteriView.getCodice());
//		 vo.setDescrizione(repertorioPoteriView.getDescrizione());
//		 vo.setTesto(repertorioPoteriView.getTesto());
//		 
//
//		 repertorioPoteriView.setVo(vo);
//	}
	
	
	@RequestMapping(value = "/repertorioPoteri/ricerca")
	public String ricerca(RepertorioPoteriView repertorioPoteriView, HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		if (repertorioPoteriView == null)
			repertorioPoteriView = new RepertorioPoteriView();

		try {
			repertorioPoteriView.setLocale(locale);
			this.caricaListe(repertorioPoteriView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		model.addAttribute("repertorioPoteriView", repertorioPoteriView);
		return PAGINA_RICERCA;
	}
	
	@RequestMapping(value = "/repertorioPoteri/cerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaRepertorioPoteriRest cercaRepertorioPoteri(RepertorioPoteriView repertorioPoteriView, HttpServletRequest request, Model model,
			Locale locale) throws Throwable {


		
		if (repertorioPoteriView == null){
			repertorioPoteriView = new RepertorioPoteriView(); 
		}
		
		String lingua = locale.getLanguage().toUpperCase();
		int numElementiPerPagina = request.getParameter("limit") == null ? 108 : NumberUtils.toInt(request.getParameter("limit"));
		String offset = request.getParameter("offset");
		int numeroPagina = offset == null || offset.equals("0") ? 1 : (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
		


		List<RepertorioPoteri> repertorioPoteriList = null;
		RepertorioPoteriFilter filter = new RepertorioPoteriFilter();
		filter.setNumeroPagina(numeroPagina);
		filter.setNumElementiPerPagina(numElementiPerPagina);
		
		filter.setLingua(lingua);
		
		String codice = request.getParameter("codice");
		if (codice != null) {
			filter.setCodice(codice);
		}
		String descrizione = request.getParameter("descrizione");
		if (descrizione != null) {
			filter.setDescrizione(descrizione);
		}
		String testo = request.getParameter("testo");
		if (testo != null) {
			filter.setTesto(testo);
		}
		String idCategoria = request.getParameter("idCategoria");
		if (idCategoria != null && !idCategoria.isEmpty()) {
			filter.setIdCategoria(new Long(idCategoria));
		}
		String idSubCategoria = request.getParameter("idSubcategoria");
		if (idSubCategoria != null && !idSubCategoria.isEmpty()) {
			filter.setIdSubcategoria(new Long(idSubCategoria));
		}
		
		String order = request.getParameter("order");
		if (order != null) {
			filter.setOrder(!order.trim().equals("") ? order : "desc");
		}
		
		try {
			RepertorioPoteriAggregate aggregate = repertorioPoteriService.searchPagedRepertorioPoteri(filter);
			repertorioPoteriList = aggregate != null ? aggregate.getList() : null;
			RicercaRepertorioPoteriRest ricercaRepertorioPoteriRest = new RicercaRepertorioPoteriRest();
			ricercaRepertorioPoteriRest.setTotal(aggregate != null ? aggregate.getNumeroTotaleElementi() : 0);
			ricercaRepertorioPoteriRest.setRows(convertRepertorioPoteriToRest(repertorioPoteriList, lingua));
			
			return ricercaRepertorioPoteriRest;

		} catch (Exception e) {
			return new RicercaRepertorioPoteriRest();
		}

	}
	

	
	private List<RepertorioPoteriRest> convertRepertorioPoteriToRest(List<RepertorioPoteri> list, String lingua) throws Throwable {
		List<RepertorioPoteriRest> out = new ArrayList<RepertorioPoteriRest>();
		
		
		for (RepertorioPoteri repertorioPoteri : list) {
			RepertorioPoteriRest repertorioPoteriRest = new RepertorioPoteriRest();
			
			repertorioPoteriRest.setId(repertorioPoteri.getId());
			repertorioPoteriRest.setCodice(repertorioPoteri.getDescrizione());
			repertorioPoteriRest.setDescrizione(repertorioPoteri.getDescrizione());
			
			String testo = repertorioPoteri.getTesto();
			if (testo!=null && !testo.isEmpty() && testo.length()>100){
				repertorioPoteriRest.setTesto(testo.substring(0, 100));
			}else{
				repertorioPoteriRest.setTesto(testo);
			}
			if (repertorioPoteri.getCategoria()!=null)
				repertorioPoteriRest.setCategoria(repertorioPoteri.getCategoria().getDescrizione());
			if (repertorioPoteri.getSubcategoria()!=null)
				repertorioPoteriRest.setSubcategoria(repertorioPoteri.getSubcategoria().getDescrizione());
			
			String titleAlt = "-";
			repertorioPoteriRest.setAzioni("<p id='action-repertoriopoteri-" + repertorioPoteri.getId() + "' alt='" + titleAlt + "' title='" + titleAlt + "'></p>");
			out.add(repertorioPoteriRest);
		}
		
		return out;
	}
	
	@RequestMapping(value = "/repertorioPoteri/caricaAzioniRepertorioPoteri", method = RequestMethod.POST)
	public String caricaAzioniRepertorioPoteri(@RequestParam("idRepertorioPoteri") long idRepertorioPoteri, HttpServletRequest req, Locale locale) {

		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(req);
		        //removeCSRFToken(request);
			
			req.setAttribute("modifica", "yes");
			req.setAttribute("idRepertorioPoteri", idRepertorioPoteri);
		return PAGINA_AZIONI_PROCURE;
	}
	
	
	@RequestMapping(value="/repertorioPoteri/eliminaRepertorioPoteri", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaRepertorioPoteri(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			RepertorioPoteriView view = new RepertorioPoteriView();
			RepertorioPoteriView repertorioPoteriSalvato = (RepertorioPoteriView) repertorioPoteriService.leggi(id);
			if (repertorioPoteriSalvato != null && repertorioPoteriSalvato.getVo() != null) {
				popolaFormDaVo(view, repertorioPoteriSalvato.getVo());
				repertorioPoteriSalvato.getVo().setDataCancellazione(new Date());

				
				view.setVo(repertorioPoteriSalvato.getVo());
				repertorioPoteriService.modifica(view);
				
			} 
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	

	
	private void popolaFormDaVo(RepertorioPoteriView view, RepertorioPoteri vo) throws Throwable {
		view.setId(vo.getId());
		view.setRepertorioPoteriId(vo.getId());
		view.setCodice(vo.getDescrizione());
		view.setDescrizione(vo.getDescrizione());
		view.setTesto(vo.getTesto());
		view.setIdCategoria(vo.getCategoria().getId());
		view.setIdSubcategoria(vo.getSubcategoria().getId());

	}
	
	@RequestMapping(value= "/repertorioPoteri/modifica",method = RequestMethod.GET)
	public String modificaRepertorioPoteri(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		RepertorioPoteriView view = new RepertorioPoteriView();
		// engsecurity VA - redirect
				//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				//htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		try {
			RepertorioPoteriView repertorioPoteriSalvato = (RepertorioPoteriView) repertorioPoteriService.leggi(id);
			if (repertorioPoteriSalvato != null && repertorioPoteriSalvato.getVo() != null) {
				popolaFormDaVo(view, repertorioPoteriSalvato.getVo());
				
				this.caricaListe(view, locale);
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
	
	@RequestMapping(value= "/repertorioPoteri/visualizza",method = RequestMethod.GET)
	public String visualizzaRepertorioPoteri(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		request.setAttribute("disableInVis", "true");
		return modificaRepertorioPoteri(id,model,locale,request);
	}




	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new RepertorioPoteriValidator());
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}




	
}



