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
import eng.la.business.TipoProcureService;
import eng.la.model.TipoProcure;
import eng.la.model.rest.TipoProcureRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.LinguaView;
import eng.la.model.view.TipoProcureView;
import eng.la.presentation.validator.TipoProcureValidator;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("tipoProcureController")
@SessionAttributes("tipoProcureView")
public class TipoProcureController extends BaseController {

	private static final String MODEL_VIEW_NOME = "tipoProcureView";
	private static final String PAGINA_FORM_READ_PATH = "tipoProcure/formReadTipoProcure";
	private static final String PAGINA_FORM_EDIT_PATH = "tipoProcure/formEditTipoProcure";

	@Autowired
	private TipoProcureService tipoProcureService;

	public void setTipoProcureService(TipoProcureService tipoProcureService) {
		this.tipoProcureService = tipoProcureService;
	}

	@Autowired
	private LinguaService linguaService;

	public void setLinguaService(LinguaService linguaService) {
		this.linguaService = linguaService;
	}
	
	@RequestMapping("/tipoProcure/gestioneTipoProcure")
	public String gestioneTipoProcure(HttpServletRequest request, Model model, Locale locale) {
		TipoProcureView tipoProcureView = new TipoProcureView();
		// engsecurity VA - redirect
				//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				//htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);

		try {
			List<TipoProcureView> lista = tipoProcureService.leggi(locale);
			tipoProcureView.setListaTipoProcure(lista);
			super.caricaListe(tipoProcureView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		tipoProcureView.setVo(new TipoProcure());
		model.addAttribute(MODEL_VIEW_NOME, tipoProcureView);
		return PAGINA_FORM_EDIT_PATH;
	}
	
	@RequestMapping("/tipoProcure/visualizzaTipoProcure")
	public String visualizzaTipoProcure(HttpServletRequest request, Model model, Locale locale) {
		TipoProcureView tipoProcureView = new TipoProcureView();

		// engsecurity VA 
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			List<TipoProcureView> lista = tipoProcureService.leggi(locale);
			tipoProcureView.setListaTipoProcure(lista);
			tipoProcureView.setLocale(locale);
			caricaListeOggetti(tipoProcureView, locale);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		tipoProcureView.setVo(new TipoProcure());
		//UtenteView utenteConnesso = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		model.addAttribute(MODEL_VIEW_NOME, tipoProcureView);
		return PAGINA_FORM_READ_PATH;
	}

	@RequestMapping(value = "/tipoProcure/salva", method = RequestMethod.POST)
	public String salvaTipoProcure(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated TipoProcureView tipoProcureView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA 
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
				String token=request.getParameter("CSRFToken");

		try {
			if (tipoProcureView.getOp() != null && !tipoProcureView.getOp().equals("salvaTipoProcure")) {
				String ritorno = invocaMetodoDaReflection(tipoProcureView, bindingResult, locale, model, request,
						response, this);
				return ritorno == null ? PAGINA_FORM_EDIT_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				return PAGINA_FORM_EDIT_PATH;
			}

			if (tipoProcureView.isInsertMode()) {
//				if(tipoProcureService.controlla(tipoProcureView)){
//					model.addAttribute("errorMessage", "errore.tipoProcureEsistente");
//					return "redirect:gestioneTipoProcure.action";
//				}
				tipoProcureService.inserisci(tipoProcureView);
				
			} else if(tipoProcureView.isDeleteMode()){
				tipoProcureService.cancella(tipoProcureView);
			} else {
				tipoProcureService.modifica(tipoProcureView);
			}
			
			model.addAttribute("successMessage", "messaggio.operazione.ok");
			return "redirect:gestioneTipoProcure.action?CSRFToken="+token;
		} catch (Throwable e) {
			bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			return PAGINA_FORM_EDIT_PATH;
		}
	}

	@SuppressWarnings("all")
	public String selezionaTipoProcure(TipoProcureView tipoProcureView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		String ritorno = PAGINA_FORM_READ_PATH;
		try {
			List<TipoProcureView> leggibyCodice = tipoProcureService.leggibyCodice(tipoProcureView.getTipoProcureCode());
			preparaView(tipoProcureView);
			
			if(tipoProcureView.isEditMode()){
				ritorno = PAGINA_FORM_EDIT_PATH;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return ritorno;
	}
	
	@RequestMapping(value = "/tipoProcure/caricaDescrizioniTipoProcure", method = RequestMethod.POST)
	public @ResponseBody TipoProcureRest caricaDescrizioniTipoProcure(@RequestParam("code") String code) {
		TipoProcureRest nazRest = new TipoProcureRest();
		List<String> tipoProcureDesc = new ArrayList<String>();
		HashMap<String, String> tipoProcureHash = new HashMap<String, String>();


		try {
			List<TipoProcureView> leggibyCodice = tipoProcureService.leggibyCodice(code);
			for (int i = 0; i < leggibyCodice.size(); i++) {
				TipoProcureView tipoProcureView = (TipoProcureView) leggibyCodice.get(i);
				if(!tipoProcureHash.containsKey(tipoProcureView.getVo().getLang())){
					tipoProcureHash.put(tipoProcureView.getVo().getLang(), tipoProcureView.getVo().getDescrizione());
		    	}

			}
			
			List<LinguaView> lingua = linguaService.leggi();
			for (LinguaView linguaView : lingua) {
				String desc = "";
				if(tipoProcureHash.containsKey(linguaView.getVo().getLang())){
					desc = (String) tipoProcureHash.get(linguaView.getVo().getLang());
				} 
				tipoProcureDesc.add(desc);
			}
			nazRest.setTipoProcureDesc(tipoProcureDesc);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return nazRest;
	}
	
	
	@SuppressWarnings("all")
	public void preparaView(TipoProcureView tipoProcureView) throws Throwable {
		List<TipoProcureView> leggibyCodice = tipoProcureService.leggibyCodice(tipoProcureView.getTipoProcureCode());
		
		HashMap tipoProcureHash = tipoProcureView.getTipoProcureHash();
		tipoProcureHash.clear();
		
		for (int i = 0; i < leggibyCodice.size(); i++) {
			TipoProcureView tipoProcureView2 = (TipoProcureView) leggibyCodice.get(i);
			if(!tipoProcureHash.containsKey(tipoProcureView2.getVo().getLang())){
				tipoProcureHash.put(tipoProcureView2.getVo().getLang(), tipoProcureView2.getVo().getDescrizione());
	    	}
		}
	}
	
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		List<LinguaView> listaLingua = linguaService.leggi();
		TipoProcureView tipoProcureView = (TipoProcureView) view;
		tipoProcureView.setListaLingua(listaLingua);
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new TipoProcureValidator());
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
}
