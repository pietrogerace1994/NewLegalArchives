package eng.la.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import eng.la.business.RubricaService;
import eng.la.model.Rubrica;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.RubricaView;
import eng.la.persistence.CostantiDAO;
import eng.la.presentation.validator.RubricaValidator;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("rubricaController")
public class RubricaController extends BaseController {
	private static final String MODEL_VIEW_NOME = "rubricaView";
	private static final String PAGINA_FORM_PATH = "presidioNormativo/formUtentiPresidioNormativo";
	private static final String PAGINA_AZIONI_RUBRICA = "presidioNormativo/azioniRubrica";
	private static final String PAGINA_AZIONI_CERCA_RUBRICA = "presidioNormativo/azioniCercaRubrica";
	
	@Autowired
	private RubricaService rubricaService;

	public void setRubricaService(RubricaService rubricaService) {
		this.rubricaService = rubricaService;
	}


	@RequestMapping("/presidionormativo/modifica")
	public String modificaRubrica(@RequestParam("id") long id, Model model, Locale locale,
			HttpServletRequest request) {
		
		// engsecurity VA - redirect
				//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				//htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
				
		RubricaView view = new RubricaView();

		try {
			RubricaView rubricaSalvato = (RubricaView) rubricaService.leggiRubrica(id);
			if (rubricaSalvato != null && rubricaSalvato.getVo() != null) {
				popolaFormDaVo(view, rubricaSalvato.getVo(), locale);

				super.caricaListe(view, locale);
			} else {
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}

		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_FORM_PATH;
	}

	private void popolaFormDaVo(RubricaView view, Rubrica vo, Locale locale)
			throws Throwable {
		view.setRubricaId(vo.getId());
		view.setNominativo(vo.getNominativo());
		view.setEmail(vo.getEmail());

	}
	
	@RequestMapping(value = "/presidionormativo/eliminaRubrica", produces = "application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaRubrica(@RequestParam("id") long id, Model model) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			//RubricaView rubricaView = rubricaService.leggiRubrica(id);
			rubricaService.cancellaRubrica(id);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	
	@RequestMapping(value = "/presidionormativo/caricaAzioniRubrica", method = RequestMethod.POST)
	public String caricaAzioniRubrica(@RequestParam("idRubrica") long idRubrica, HttpServletRequest req,
			Locale locale) {
		try {
			req.setAttribute("idRubrica", idRubrica);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if (req.getParameter("onlyContent") != null) {

			return PAGINA_AZIONI_RUBRICA;
		}

		return PAGINA_AZIONI_CERCA_RUBRICA;
	} 


	@RequestMapping(value ="/presidionormativo/crea", method = RequestMethod.GET )
	public String creaEmail(HttpServletRequest request, Model model, Locale locale) {
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		RubricaView view = new RubricaView();

		view.setVo(new Rubrica());

		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_FORM_PATH;
	}



	@RequestMapping(value = "/presidionormativo/salva", method = RequestMethod.POST)
	public String salvaRubrica(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated RubricaView rubricaView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		
		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if(code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) || code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO )){

				flagView = true;
			}
		}
		if(flagView){
		
			// engsecurity VA 
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			String token=request.getParameter("CSRFToken");
	
			try {
				
				if (rubricaView.getOp() != null && !rubricaView.getOp().equals("salvaRubrica")) {
					String ritorno = invocaMetodoDaReflection(rubricaView, bindingResult, locale, model, request, response,
							this);
	
					return ritorno == null ? PAGINA_FORM_PATH : ritorno;
				}
	
				if (bindingResult.hasErrors()) {
					model.addAttribute(MODEL_VIEW_NOME, rubricaView);
					return PAGINA_FORM_PATH;
				}
				
				preparaPerSalvataggio(rubricaView, bindingResult);
	
				if (bindingResult.hasErrors()) {
	
					return PAGINA_FORM_PATH;
				}
	
				if (rubricaView.getRubricaId() == null || rubricaView.getRubricaId() == 0) {
					rubricaService.salvaRubrica(rubricaView);
				
				} else {
					rubricaService.modificaRubrica(rubricaView);
	
				}
	
				//model.addAttribute("successMessage", "messaggio.operazione.ok");
				 
				return "redirect:crea.action?successMessage=messaggio.operazione.ok&CSRFToken="+token;
			} catch (Throwable e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "errore.generico");
				return "redirect:/errore.action";
			}
		}
		return "redirect:/errore.action";
	}

	private void preparaPerSalvataggio(RubricaView view, BindingResult bindingResult) throws Throwable {
		Rubrica vo = null;
		if (view.getRubricaId() != null) {
			RubricaView oldView = rubricaService.leggiRubrica( view.getRubricaId() );
			vo = oldView.getVo();
		  
					
		}else{ 
			vo = new Rubrica();  
		}

		vo.setNominativo(view.getNominativo());
		vo.setEmail(view.getEmail());
		 
		view.setVo(vo);
	}

	


	


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new RubricaValidator());
	}


	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
	
	}


	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		
	}

}
