package eng.la.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.CentroDiCostoService;
import eng.la.business.SettoreGiuridicoService;
import eng.la.business.TipologiaFascicoloService;
import eng.la.model.CentroDiCosto;
import eng.la.model.SettoreGiuridico;
import eng.la.model.Societa;
import eng.la.model.TipologiaFascicolo;
import eng.la.model.rest.CentroDiCostoRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.CentroDiCostoView;
import eng.la.model.view.SettoreGiuridicoView;
import eng.la.model.view.TipologiaFascicoloView;
import eng.la.presentation.validator.CentroDiCostoValidator;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("centroDiCostoController")
@SessionAttributes("centroDiCostoView")
public class CentroDiCostoController extends BaseController {

	private static final String MODEL_VIEW_NOME = "centroDiCostoView";
//	private static final String PAGINA_FORM_PATH = "centroDiCosto/formCentroDiCosto";
	private static final String PAGINA_FORM_READ_PATH = "centroDiCosto/formReadCentroDiCosto";
//	private static final String PAGINA_FORM_EDIT_PATH = "centroDiCosto/formEditCentroDiCosto";

	@Autowired
	private CentroDiCostoService centroDiCostoService;
	
	@Autowired
	private TipologiaFascicoloService tipologiaFascicoloService;
	
	@Autowired
	private SettoreGiuridicoService settoreGiuridicoService;

	@RequestMapping("/centroDiCosto/visualizzaCentroDiCosto")
	public String visualizzaCentroDiCosto(HttpServletRequest request, Model model, Locale locale) {
		CentroDiCostoView centroDiCostoView = new CentroDiCostoView();

		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			List<CentroDiCostoView> listaCentroDiCosto = centroDiCostoService.leggi();
			centroDiCostoView.setListaCentroDiCosto(listaCentroDiCosto);
			super.caricaListe(centroDiCostoView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		centroDiCostoView.setVo(new CentroDiCosto());
		model.addAttribute(MODEL_VIEW_NOME, centroDiCostoView);
		return PAGINA_FORM_READ_PATH;
	}
	
	@RequestMapping(value = "/centroDiCosto/caricaDescrizioniCdc", method = RequestMethod.POST)
	public @ResponseBody CentroDiCostoRest caricaDescrizioniCdc(@RequestParam("id") String id, Locale locale
			, HttpServletRequest request) {
		CentroDiCostoRest centroDiCostoRest = new CentroDiCostoRest();


		try {
			
			CentroDiCostoView view = centroDiCostoService.leggi(NumberUtils.toLong(id));
			
			centroDiCostoRest.setId(view.getVo().getId());
			centroDiCostoRest.setUnitaLegale(view.getVo().getUnitaLegale());
			centroDiCostoRest.setCdc(view.getVo().getCdc().toString());
			
			SettoreGiuridico settoreGiuridico = view.getVo().getSettoreGiuridico();
			if(settoreGiuridico!=null){
				SettoreGiuridicoView settoreGiuridicoView = settoreGiuridicoService.leggi(settoreGiuridico.getCodGruppoLingua(), locale, false);
				centroDiCostoRest.setSettoreGiuridico(settoreGiuridicoView.getVo().getNome());
			}
	
			TipologiaFascicolo tipologiaFascicolo = view.getVo().getTipologiaFascicolo();
			if(tipologiaFascicolo!=null){
				TipologiaFascicoloView tipologiaFascicoloView = tipologiaFascicoloService.leggi(tipologiaFascicolo.getCodGruppoLingua(), locale, false);
				centroDiCostoRest.setTipologiaFascicolo(tipologiaFascicoloView.getVo().getDescrizione());
			}
			
			Societa societa = view.getVo().getSocieta();
			if(societa!=null){
				centroDiCostoRest.setSocieta(societa.getRagioneSociale());
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return centroDiCostoRest;
	}
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		CentroDiCostoView centroDiCostoView = (CentroDiCostoView) view;

		List<SettoreGiuridicoView> listaSettoreGiuridico = settoreGiuridicoService.leggi(locale);
		centroDiCostoView.setListaSettoreGiuridico(listaSettoreGiuridico);
		
		List<TipologiaFascicoloView> listaTipologiaFascicolo = tipologiaFascicoloService.leggi(locale, false);
		centroDiCostoView.setListaTipologiaFascicolo(listaTipologiaFascicolo);
		
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new CentroDiCostoValidator());
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		// TODO Auto-generated method stub
		
	}
}
