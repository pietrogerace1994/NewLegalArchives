package eng.la.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.CategoriaMailinglistService;
import eng.la.business.EmailService;
import eng.la.model.CategoriaMailinglist;
import eng.la.model.CategoriaMailinglistStyle;
import eng.la.model.rest.CategoriaMailinglistRest;
import eng.la.model.rest.RicercaCategoriaMailinglistRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.CategoriaMailinglistView;
import eng.la.persistence.CostantiDAO;
import eng.la.presentation.validator.CategoriaMailingListValidator;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("categoriaMailingListController")
@SessionAttributes("categoriaView")
public class CategoriaMailinglistController extends BaseController {
	private static final String MODEL_VIEW_NOME = "categoriaView";
//	private static final String PAGINA_FORM_PATH = "catMailingList/formUtentiPresidioNormativo";
	private static final String PAGINA_AZIONI_RUBRICA = "presidioNormativo/azioniCategoria";
	private static final String PAGINA_AZIONI_CERCA_RUBRICA = "presidioNormativo/azioniCercaCategoria";
	private static final String PAGINA_FORM_PATH_CATEGORIE = "presidioNormativo/formGestioneCategorie";
	
	@Autowired  
	private CategoriaMailinglistService categoriaMailinglistService;
	
	@Autowired  
	private EmailService emailService;
	 
	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;
	
	@RequestMapping("/catMailingList/cerca")
	public String cercaCategorie(HttpServletRequest request, Model model, Locale locale) {
		CategoriaMailinglistView view = new CategoriaMailinglistView();
		
		try {
			super.caricaListe(view, locale);
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
		}
		view.setVo(new CategoriaMailinglist());

		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_FORM_PATH_CATEGORIE;
	}
	
	@RequestMapping(value = "/catMailingList/ricerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaCategoriaMailinglistRest cercaCategorie(HttpServletRequest request, Locale locale) {
		try {
			int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA
					: NumberUtils.toInt(request.getParameter("limit"));
			String offset = request.getParameter("offset");
			int numeroPagina = offset == null || offset.equals("0") ? 1
					: (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
			String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
//			String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
			

			ListaPaginata<CategoriaMailinglistView> lista = (ListaPaginata<CategoriaMailinglistView>) categoriaMailinglistService.cercaCategorie(numElementiPerPagina, numeroPagina, ordinamento);
			RicercaCategoriaMailinglistRest ricercaModelRest = new RicercaCategoriaMailinglistRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<CategoriaMailinglistRest> rows = new ArrayList<CategoriaMailinglistRest>();
			for (CategoriaMailinglistView view : lista) {
				if(view.getVo().getCategoriaPadre()==null){
				CategoriaMailinglistRest catRest = new CategoriaMailinglistRest();
				catRest.setId(view.getVo().getId());
				catRest.setNomeCategoria(view.getVo().getNomeCategoria());
				catRest.setColore(view.getVo().getColore());
				catRest.setAzioni("<p id='containerAzioniRigaCategoria" + view.getVo().getId() + "'></p>");
				
				rows.add(catRest);
				
				if(categoriaMailinglistService.haFigli(view.getVo().getId())){
					List<CategoriaMailinglistView> figli = anagraficaStatiTipiService.leggiCategorie(view.getVo().getId(), false);
					
					for(CategoriaMailinglistView figlio:figli){
						CategoriaMailinglistRest catRestFiglio = new CategoriaMailinglistRest();
						catRestFiglio.setId(figlio.getVo().getId());
						catRestFiglio.setNomeCategoria(view.getVo().getNomeCategoria());
						catRestFiglio.setColore(view.getVo().getColore());
						catRestFiglio.setCategoriaFiglia(figlio.getVo().getNomeCategoria());
						catRestFiglio.setAzioni("<p id='containerAzioniRigaCategoria" + figlio.getVo().getId() + "'></p>");
						rows.add(catRestFiglio);
					}
				}
				else{
					
				}
				
				}
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	
	@RequestMapping("/catMailingList/crea")
	public String gestioneCategorie(HttpServletRequest request, Model model, Locale locale) {
		CategoriaMailinglistView view = new CategoriaMailinglistView();

		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) ||code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) ){

				flagView = true;
			}
		}
		if(flagView){


			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);
			//String token=request.getParameter("CSRFToken");

			try {
				super.caricaListe(view, locale);
			} catch (Throwable e) {
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}

			view.setIsSottoCategoria(false);
			view.setVo(new CategoriaMailinglist());

			model.addAttribute(MODEL_VIEW_NOME, view);
			return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_FORM_PATH_CATEGORIE;
		}
		else
			return null;
	}

	@RequestMapping("/catMailingList/modifica")
	public String modificaCategoria(@RequestParam("id") long id, Model model, Locale locale,
			HttpServletRequest request) {

		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) ||code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) ){

				flagView = true;
			}
		}
		if(flagView){

			CategoriaMailinglistView view = new CategoriaMailinglistView();

			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);
			//String token=request.getParameter("CSRFToken");

			try {
				CategoriaMailinglistView categoriaSalvato = (CategoriaMailinglistView) anagraficaStatiTipiService.leggiCategoria(id);
				if (categoriaSalvato != null && categoriaSalvato.getVo() != null) {
					popolaFormDaVo(view, categoriaSalvato.getVo(), locale);

					super.caricaListe(view, locale);
				} else {
					model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
				}
			} catch (Throwable e) {
				model.addAttribute("errorMessage", "errore.generico");
				e.printStackTrace();
			}

			model.addAttribute(MODEL_VIEW_NOME, view);
			return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_FORM_PATH_CATEGORIE;
		}
		else
			return null;
	}

	private void popolaFormDaVo(CategoriaMailinglistView view, CategoriaMailinglist vo, Locale locale)
			throws Throwable {
		view.setCategoriaId(vo.getId());
		view.setNomeCategoria(vo.getNomeCategoria());
		view.setOp("modifica");
		
		view.setHaFigli(categoriaMailinglistService.haFigli(vo.getId()));
		
		
		if(vo.getCategoriaPadre()!=null){
			view.setCategoriaPadre(vo.getCategoriaPadre().getId());
			view.setIsSottoCategoria(true);
		}
		else
			view.setIsSottoCategoria(false);
		
		
		Long color = categoriaMailinglistService.findColor(vo.getColore());
		
		view.setColorselector(color);

	}
	
	@RequestMapping(value = "/catMailingList/eliminaCategoria", produces = "application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaCategoria(@RequestParam("id") long id, Model model) {
		
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) ||code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) ){

				flagView = true;
			}
		}
		if(flagView){

			RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
			try {
				//RubricaView rubricaView = rubricaService.leggiRubrica(id);
				categoriaMailinglistService.cancellaCategoria(id);

				emailService.eliminaArticoliPerCategoria(id);

				List<CategoriaMailinglistView> sottoCategorie = anagraficaStatiTipiService.leggiCategorie(id, false);

				for(CategoriaMailinglistView cat : sottoCategorie){
					emailService.eliminaArticoliPerCategoria(cat.getVo().getId());
				}

				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return risultato;
		}
		else
			return null;
	}
	
	@RequestMapping(value = "/catMailingList/caricaAzioniCategoria", method = RequestMethod.POST)
	public String caricaAzioniCategoria(@RequestParam("idCat") long idCat, HttpServletRequest req,
			Locale locale) {
		try {
			req.setAttribute("idCat", idCat);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if (req.getParameter("onlyContent") != null) {

			return PAGINA_AZIONI_RUBRICA;
		}

		return PAGINA_AZIONI_CERCA_RUBRICA;
	} 



	@RequestMapping(value = "/catMailingList/salva", method = RequestMethod.POST)
	public String salvaCategoria(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated CategoriaMailinglistView categoriaView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {

		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) ||code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) ){

				flagView = true;
			}
		}
		if(flagView){


			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);
			String token=request.getParameter("CSRFToken");

			try {

				if (categoriaView.getOp() != null && !categoriaView.getOp().equals("salvaCategoria")) {
					String ritorno = invocaMetodoDaReflection(categoriaView, bindingResult, locale, model, request, response,
							this);

					return ritorno == null ? PAGINA_FORM_PATH_CATEGORIE : ritorno;
				}

				if (bindingResult.hasErrors()) {
					model.addAttribute(MODEL_VIEW_NOME, categoriaView);
					return PAGINA_FORM_PATH_CATEGORIE;
				}

				preparaPerSalvataggio(categoriaView, bindingResult);

				if (bindingResult.hasErrors()) {

					return PAGINA_FORM_PATH_CATEGORIE;
				}

				if (categoriaView.getCategoriaId() == null || categoriaView.getCategoriaId() == 0) {
					categoriaMailinglistService.salvaCategoria(categoriaView);

				} else {
					categoriaMailinglistService.modificaCategoria(categoriaView);

				}

				//model.addAttribute("successMessage", "messaggio.operazione.ok");

				return "redirect:crea.action?successMessage=messaggio.operazione.ok"+"&CSRFToken="+token;
			} catch (Throwable e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "errore.generico");
				return "redirect:/errore.action";
			}
		}
		else 
			return null;

	}

	private void preparaPerSalvataggio(CategoriaMailinglistView view, BindingResult bindingResult) throws Throwable {
		CategoriaMailinglist vo = null;
		
		Boolean isNew = true;
		
		if (view.getCategoriaId() != null) {
			CategoriaMailinglistView oldView = anagraficaStatiTipiService.leggiCategoria(view.getCategoriaId());
			vo = oldView.getVo();
			isNew=false;	
		}else{ 
			vo = new CategoriaMailinglist();  
		}

		vo.setNomeCategoria(view.getNomeCategoria());
		
		if(view.getCategoriaPadre()!=null && view.getIsSottoCategoria()!=null && view.getIsSottoCategoria()){
			vo.setCategoriaPadre(anagraficaStatiTipiService.leggiCategoria(view.getCategoriaPadre()).getVo());
			vo.setOrd(0);
		}
		else if(isNew){
			Long ord = anagraficaStatiTipiService.leggiCategoriaMailingListNextOrd();
			vo.setOrd(ord);
		}else{
			vo.setCategoriaPadre(null);
		}
		
		CategoriaMailinglistStyle style = anagraficaStatiTipiService.leggiCategoriaMailingListStyle(view.getColorselector());
		
		Long nextId = anagraficaStatiTipiService.leggiCategoriaMailingListNextId();
		
		vo.setColore(style.getColore());
		vo.setCss(style.getCss());
		
		if(isNew){
			vo.setCodGruppoLingua("CMA"+nextId);
			vo.setLang("IT");
			vo.setIcon("ico_generica.png");
		}
		 
		view.setVo(vo);
	}

	


	


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new CategoriaMailingListValidator());
	}


	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		CategoriaMailinglistView categoriaView = (CategoriaMailinglistView) view;
		categoriaView.setSottoCategorie(anagraficaStatiTipiService.leggiCategorie(locale.getLanguage().toUpperCase()));
		categoriaView.setStileCategorie(anagraficaStatiTipiService.leggiCategoriaMailingListStyle());
	}


	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		
	}

}
