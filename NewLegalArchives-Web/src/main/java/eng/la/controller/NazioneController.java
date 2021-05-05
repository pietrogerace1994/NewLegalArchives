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
import eng.la.business.NazioneService;
import eng.la.model.Nazione;
import eng.la.model.rest.NazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.LinguaView;
import eng.la.model.view.NazioneView;
import eng.la.presentation.validator.NazioneValidator;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("nazioneController")
@SessionAttributes("nazioneView")
public class NazioneController extends BaseController {

	private static final String MODEL_VIEW_NOME = "nazioneView";
	private static final String PAGINA_FORM_READ_PATH = "nazione/formReadNazione";
	private static final String PAGINA_FORM_EDIT_PATH = "nazione/formEditNazione";

	@Autowired
	private NazioneService nazioneService;

	public void setNazioneService(NazioneService nazioneService) {
		this.nazioneService = nazioneService;
	}

	@Autowired
	private LinguaService linguaService;

	public void setLinguaService(LinguaService linguaService) {
		this.linguaService = linguaService;
	}
	
	@RequestMapping("/nazione/gestioneNazioni")
	public String gestioneNazioni(HttpServletRequest request, Model model, Locale locale) {
		NazioneView nazioneView = new NazioneView();
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		try {
			super.caricaListe(nazioneView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		nazioneView.setVo(new Nazione());
		//UtenteView utenteConnesso = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		model.addAttribute(MODEL_VIEW_NOME, nazioneView);
		return PAGINA_FORM_EDIT_PATH;
	}
	
	@RequestMapping("/nazione/visualizzaNazioni")
	public String visualizzaNazioni(HttpServletRequest request, Model model, Locale locale) {
		NazioneView nazioneView = new NazioneView();
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);

		try {
			nazioneView.setLocale(locale);
			List<NazioneView> listaNazioni = nazioneService.leggiPartiCorrelate(locale, false);
			nazioneView.setListaNazioni(listaNazioni);
			caricaListeOggetti(nazioneView, locale);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		nazioneView.setVo(new Nazione());
		//UtenteView utenteConnesso = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		model.addAttribute(MODEL_VIEW_NOME, nazioneView);
		return PAGINA_FORM_READ_PATH;
	}

	@RequestMapping(value = "/nazione/salva", method = RequestMethod.POST)
	public String salvaNazione(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated NazioneView nazioneView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		String token=request.getParameter("CSRFToken");

		try {
			if (nazioneView.getOp() != null && !nazioneView.getOp().equals("salvaNazione")) {
				String ritorno = invocaMetodoDaReflection(nazioneView, bindingResult, locale, model, request,
						response, this);
				return ritorno == null ? PAGINA_FORM_EDIT_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				return PAGINA_FORM_EDIT_PATH;
			}

			if (nazioneView.isInsertMode()) {
				if(nazioneService.controlla(nazioneView)){
					model.addAttribute("errorMessage", "errore.nazioneEsistente");
					return "redirect:gestioneNazioni.action?CSRFToken="+token;
				}
				nazioneService.inserisci(nazioneView);
				
			} else if(nazioneView.isDeleteMode()){
				nazioneService.cancella(nazioneView);
			} else {
				nazioneService.modifica(nazioneView);
			}
			
			model.addAttribute("successMessage", "messaggio.operazione.ok");
			return "redirect:gestioneNazioni.action?CSRFToken="+token;
		} catch (Throwable e) {
			bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			return PAGINA_FORM_EDIT_PATH;
		}
	}

	@SuppressWarnings("all")
	public String selezionaNazione(NazioneView nazioneView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		String ritorno = PAGINA_FORM_READ_PATH;
		try {
			List<NazioneView> leggibyCodice = nazioneService.leggibyCodice(nazioneView.getNazioneCode());
			preparaView(nazioneView);
			
			if(nazioneView.isEditMode()){
				ritorno = PAGINA_FORM_EDIT_PATH;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return ritorno;
	}
	
	@RequestMapping(value = "/nazione/caricaDescrizioniNazione", method = RequestMethod.POST)
	public @ResponseBody NazioneRest caricaDescrizioniNazione(@RequestParam("code") String code) {
		NazioneRest nazRest = new NazioneRest();


		List<String> nazioneDesc = new ArrayList<String>();
		HashMap<String, String> nazioneHash = new HashMap<String, String>();
		try {
			List<NazioneView> leggibyCodice = nazioneService.leggibyCodice(code);
			for (int i = 0; i < leggibyCodice.size(); i++) {
				NazioneView nazioneView = (NazioneView) leggibyCodice.get(i);
				if(!nazioneHash.containsKey(nazioneView.getVo().getLang())){
					nazioneHash.put(nazioneView.getVo().getLang(), nazioneView.getVo().getDescrizione());
		    	}
				if(nazioneView.getVo().getLang().equalsIgnoreCase("IT")){
					nazRest.setSoloParteCorrelata(nazioneView.getVo().getSoloParteCorrelata().equalsIgnoreCase("T")?true:false);
					nazRest.setId(nazioneView.getVo().getId());
				}
			}
			
			List<LinguaView> lingua = linguaService.leggi();
			for (LinguaView linguaView : lingua) {
				String desc = "";
				if(nazioneHash.containsKey(linguaView.getVo().getLang())){
					desc = (String) nazioneHash.get(linguaView.getVo().getLang());
				} 
				nazioneDesc.add(desc);
			}
			nazRest.setNazioneDesc(nazioneDesc);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return nazRest;
	}
	
	@SuppressWarnings("all")
	public void preparaView(NazioneView nazioneView) throws Throwable {
		List<NazioneView> leggibyCodice = nazioneService.leggibyCodice(nazioneView.getNazioneCode());
		
		HashMap nazioneHash = nazioneView.getNazioneHash();
		nazioneHash.clear();
		
		for (int i = 0; i < leggibyCodice.size(); i++) {
			NazioneView nazioneView2 = (NazioneView) leggibyCodice.get(i);
			if(!nazioneHash.containsKey(nazioneView2.getVo().getLang())){
				nazioneHash.put(nazioneView2.getVo().getLang(), nazioneView2.getVo().getDescrizione());
	    	}
			if(nazioneView2.getVo().getLang().equalsIgnoreCase("IT")){
				nazioneView.setSoloParteCorrelata(nazioneView2.getVo().getSoloParteCorrelata().equalsIgnoreCase("T")?true:false);
				nazioneView.setNazioneId(nazioneView2.getVo().getId());
			}
		}
	}
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		List<LinguaView> listaLingua = linguaService.leggi();
		NazioneView nazioneView = (NazioneView) view;
		nazioneView.setListaLingua(listaLingua);
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new NazioneValidator());
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		
	}
}
