package eng.la.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.LinguaService;
import eng.la.business.SpecializzazioneService;
import eng.la.model.Specializzazione;
import eng.la.model.rest.SpecializzazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.LinguaView;
import eng.la.model.view.SpecializzazioneView;
import eng.la.presentation.validator.SpecializzazioneValidator;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("specializzazioneController")
@SessionAttributes("specializzazioneView")
public class SpecializzazioneController extends BaseController {

	private static final String MODEL_VIEW_NOME = "specializzazioneView";
	private static final String PAGINA_FORM_READ_PATH = "specializzazione/formReadSpecializzazione";
	private static final String PAGINA_FORM_EDIT_PATH = "specializzazione/formEditSpecializzazione";

	@Autowired
	private SpecializzazioneService specializzazioneService;

	public void setSpecializzazioneService(SpecializzazioneService specializzazioneService) {
		this.specializzazioneService = specializzazioneService;
	}

	@Autowired
	private LinguaService linguaService;

	public void setLinguaService(LinguaService linguaService) {
		this.linguaService = linguaService;
	}
	
	@RequestMapping("/specializzazione/gestioneSpecializzazioni")
	public String gestioneSpecializzazioni(HttpServletRequest request, Model model, Locale locale) {
		SpecializzazioneView specializzazioneView = new SpecializzazioneView();
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);

		try {
			super.caricaListe(specializzazioneView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		specializzazioneView.setVo(new Specializzazione());
		//UtenteView utenteConnesso = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		model.addAttribute(MODEL_VIEW_NOME, specializzazioneView);
		return PAGINA_FORM_EDIT_PATH;
	}
	
	@RequestMapping("/specializzazione/visualizzaSpecializzazioni")
	public String visualizzaSpecializzazioni(HttpServletRequest request, Model model, Locale locale) {
		SpecializzazioneView specializzazioneView = new SpecializzazioneView();

		// engsecurity VA 
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			super.caricaListe(specializzazioneView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		specializzazioneView.setVo(new Specializzazione());
		//UtenteView utenteConnesso = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		model.addAttribute(MODEL_VIEW_NOME, specializzazioneView);
		return PAGINA_FORM_READ_PATH;
	}

	@RequestMapping(value = "/specializzazione/salva", method = RequestMethod.POST)
	public String salvaSpecializzazione(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated SpecializzazioneView specializzazioneView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {

		// engsecurity VA 
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
				String token=request.getParameter("CSRFToken");
		try {
			if (specializzazioneView.getOp() != null && !specializzazioneView.getOp().equals("salvaSpecializzazione")) {
				String ritorno = invocaMetodoDaReflection(specializzazioneView, bindingResult, locale, model, request,
						response, this);
				return ritorno == null ? PAGINA_FORM_EDIT_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				return PAGINA_FORM_EDIT_PATH;
			}

			if (specializzazioneView.isInsertMode()) {
				if(specializzazioneService.controlla(specializzazioneView)){
					model.addAttribute("errorMessage", "errore.specializzazioneEsistente");
					return "redirect:gestioneSpecializzazioni.action?CSRFToken="+token;
				}
				specializzazioneService.inserisci(specializzazioneView);
				
			} else if(specializzazioneView.isDeleteMode()){
				specializzazioneService.cancella(specializzazioneView);
			} else {
				specializzazioneService.modifica(specializzazioneView);
			}
			
			model.addAttribute("successMessage", "messaggio.operazione.ok");
			return "redirect:gestioneSpecializzazioni.action?CSRFToken="+token;
		} catch (Throwable e) {
			bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			return PAGINA_FORM_EDIT_PATH;
		}
	}

	@SuppressWarnings("all")
	public String selezionaSpecializzazione(SpecializzazioneView specializzazioneView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		String ritorno = PAGINA_FORM_READ_PATH;
		try {
			List<SpecializzazioneView> leggibyCodice = specializzazioneService.leggibyCodice(specializzazioneView.getSpecializzazioneCode());
			preparaView(specializzazioneView);
			
			if(specializzazioneView.isEditMode()){
				ritorno = PAGINA_FORM_EDIT_PATH;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return ritorno;
	}
	
	@RequestMapping(value = "/specializzazione/caricaDescrizioniSpecializzazione", method = RequestMethod.POST)
	public @ResponseBody SpecializzazioneRest caricaDescrizioniSpecializzazione(@RequestParam("code") String code) {
		SpecializzazioneRest specRest = new SpecializzazioneRest();
		List<String> specializzazioneDesc = new ArrayList<String>();
		HashMap<String, String> specializzazioneHash = new HashMap<String, String>();


		try {
			List<SpecializzazioneView> leggibyCodice = specializzazioneService.leggibyCodice(code);
			for (int i = 0; i < leggibyCodice.size(); i++) {
				SpecializzazioneView specializzazioneView2 = (SpecializzazioneView) leggibyCodice.get(i);
				if(!specializzazioneHash.containsKey(specializzazioneView2.getVo().getLang())){
					specializzazioneHash.put(specializzazioneView2.getVo().getLang(), specializzazioneView2.getVo().getDescrizione());
		    	}
				if(specializzazioneView2.getVo().getLang().equalsIgnoreCase("IT")){
					specRest.setId(specializzazioneView2.getVo().getId());
				}
			}
			
			List<LinguaView> lingua = linguaService.leggi();
			for (LinguaView linguaView : lingua) {
				String desc = "";
				if(specializzazioneHash.containsKey(linguaView.getVo().getLang())){
					desc = (String) specializzazioneHash.get(linguaView.getVo().getLang());
				} 
				specializzazioneDesc.add(desc);
			}
			specRest.setSpecializzazioneDesc(specializzazioneDesc);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return specRest;
	}
	
	@SuppressWarnings("all")
	public void preparaView(SpecializzazioneView specializzazioneView) throws Throwable {
		List<SpecializzazioneView> leggibyCodice = specializzazioneService.leggibyCodice(specializzazioneView.getSpecializzazioneCode());
		
		HashMap specializzazioneHash = specializzazioneView.getSpecializzazioneHash();
		specializzazioneHash.clear();
		
		for (int i = 0; i < leggibyCodice.size(); i++) {
			SpecializzazioneView specializzazioneView2 = (SpecializzazioneView) leggibyCodice.get(i);
			if(!specializzazioneHash.containsKey(specializzazioneView2.getVo().getLang())){
				specializzazioneHash.put(specializzazioneView2.getVo().getLang(), specializzazioneView2.getVo().getDescrizione());
	    	}
			if(specializzazioneView2.getVo().getLang().equalsIgnoreCase("IT")){
				specializzazioneView.setSpecializzazioneId(specializzazioneView2.getVo().getId());
			}
		}
	}
	
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		List<LinguaView> listaLingua = linguaService.leggi();
		SpecializzazioneView specializzazioneView = (SpecializzazioneView) view;
		specializzazioneView.setListaLingua(listaLingua);
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new SpecializzazioneValidator());
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
}
