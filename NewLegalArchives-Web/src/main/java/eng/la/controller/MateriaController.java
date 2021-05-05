package eng.la.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import eng.la.business.MateriaService;
import eng.la.business.SettoreGiuridicoService;
import eng.la.business.TipologiaFascicoloService;
import eng.la.model.Materia;
import eng.la.model.rest.MateriaRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.LinguaView;
import eng.la.model.view.MateriaView;
import eng.la.model.view.SettoreGiuridicoView;
import eng.la.model.view.TipologiaFascicoloView;
import eng.la.presentation.validator.MateriaValidator;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("materiaController")
@SessionAttributes("materiaView")
public class MateriaController extends BaseController {

	private static final String MODEL_VIEW_NOME = "materiaView";
	private static final String PAGINA_FORM_READ_PATH = "materia/formReadMateria";
	private static final String PAGINA_FORM_EDIT_PATH = "materia/formEditMateria";

	@Autowired
	@Qualifier("materiaService")
	private MateriaService materiaService;

	@Autowired
	@Qualifier("linguaService")
	private LinguaService linguaService;

	@Autowired
	@Qualifier("settoreGiuridicoService")
	private SettoreGiuridicoService settoreGiuridicoService;

	@Autowired
	@Qualifier("tipologiaFascicoloService")
	private TipologiaFascicoloService tipologiaFascicoloService;


	@RequestMapping("/materia/visualizzaMateria")
	public String visualizzaMateria(HttpServletRequest request, Model model, Locale locale) {
		MateriaView materiaView = new MateriaView();
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);

		try {
			super.caricaListe(materiaView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		materiaView.setVo( new Materia() );
		model.addAttribute(MODEL_VIEW_NOME, materiaView);
		return PAGINA_FORM_READ_PATH;
	}

	@RequestMapping("/materia/gestioneMateria")
	public String gestioneMateria(HttpServletRequest request, Model model, Locale locale) {
		MateriaView materiaView = new MateriaView();
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);

		try {
			materiaView.setLocaleStr(locale.getLanguage().toUpperCase());
			super.caricaListe(materiaView, locale);

		} catch (Throwable e) {
			e.printStackTrace();
		}

		materiaView.setVo(new Materia());
		model.addAttribute(MODEL_VIEW_NOME, materiaView);
		return PAGINA_FORM_EDIT_PATH;
	}

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		List<MateriaView> listaMateria = materiaService.leggi(locale);
		MateriaView materiaView = (MateriaView) view;
		materiaView.setListaMateria(listaMateria);

		List<LinguaView> listaLingua = linguaService.leggi();
		materiaView.setListaLingua(listaLingua);

		List<SettoreGiuridicoView> listaSettoreGiuridico = settoreGiuridicoService.leggi(locale);
		materiaView.setListaSettoreGiuridico(listaSettoreGiuridico);

		List<TipologiaFascicoloView> listaTipologiaFascicolo = tipologiaFascicoloService.leggi(locale, false);
		materiaView.setListaTipologiaFascicolo(listaTipologiaFascicolo);
		materiaView.setJsonAlberaturaMaterie(null);
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}

	@RequestMapping(value = "/materia/caricaDettaglioMateria", method = RequestMethod.POST)
	public @ResponseBody MateriaRest caricaDettaglioMateria(
			@RequestParam("codGruppoLingua") String codGruppoLingua,
			@RequestParam("localeStr") String localeStr ) {
		MateriaRest materiaRest = new MateriaRest();



		try {
			MateriaView materiaView = materiaService.leggi(codGruppoLingua,localeStr);
			materiaRest.setId(materiaView.getVo().getId());
			materiaRest.setNome(materiaView.getVo().getNome());
			if( materiaView.getVo().getMateriaPadre() != null )
				materiaRest.setIdSottoMateria( materiaView.getVo().getMateriaPadre().getId() );
			if( materiaView.getVo().getMateriaPadre() != null )
				materiaRest.setSottoMateriaNome( materiaView.getVo().getMateriaPadre().getNome() );

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return materiaRest;
	}

	@RequestMapping(value = "/materia/caricaSettoreGiuridico", method = RequestMethod.POST)
	public @ResponseBody MateriaRest caricaTipologiaFascicolo(
			@RequestParam("tipologiaFascicoloId") Long tipologiaFascicoloId) {
		MateriaRest materiaRest = new MateriaRest();



		try {
			List<SettoreGiuridicoView> listaSettoreGiuridico = settoreGiuridicoService.leggiPerTipologiaId(tipologiaFascicoloId);
			materiaRest.setListaSettoreGiuridico(listaSettoreGiuridico);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return materiaRest;
	}

	@RequestMapping(value = "/materia/caricaMateriaNome", method = RequestMethod.POST)
	public @ResponseBody MateriaRest caricaMateriaNome(
			@RequestParam("codGruppoLingua") String codGruppoLingua) {
		MateriaRest materiaRest = new MateriaRest();



		try {
			List<MateriaView> materiaViews = materiaService.leggi(codGruppoLingua);
			if (materiaViews != null) {

				List<Materia> vos = new ArrayList<Materia>(); 
				for(MateriaView materiaView : materiaViews) {
					Materia vo = new Materia();
					vo.setId(materiaView.getVo().getId());
					vo.setNome(materiaView.getVo().getNome() );
					vo.setCodGruppoLingua(materiaView.getVo().getCodGruppoLingua());
					vo.setLang(materiaView.getVo().getLang());
					if(materiaView.getVo().getMateriaPadre()!=null)
						vo.setIdMateriaPadre(""+materiaView.getVo().getMateriaPadre().getId());
					vos.add(vo);
				}
				materiaRest.setVos(vos);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return materiaRest;
	}

	@RequestMapping(value = "/materia/caricaAlberaturaMaterie", method = RequestMethod.POST)
	public @ResponseBody MateriaRest caricaAlberaturaMaterie(
			@RequestParam("settoreGiuridicoId") Long settoreGiuridicoId) {
		MateriaRest materiaRest = new MateriaRest();



		try {
			JSONArray jsonArray = materiaService.leggiAlberoMateriaOp(settoreGiuridicoId.longValue(), new Locale("it","IT"), true);
			if (jsonArray != null) {
				materiaRest.setJsonAlberaturaMaterie(jsonArray.toString());
			} else {
				materiaRest.setJsonAlberaturaMaterie(null);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return materiaRest;
	}

	@RequestMapping(value = "/materia/caricaAlberaturaMaterieEdit", method = RequestMethod.POST)
	public @ResponseBody MateriaRest caricaAlberaturaMaterieEdit(
			@RequestParam("settoreGiuridicoId") Long settoreGiuridicoId) {
		MateriaRest materiaRest = new MateriaRest();


		try {
			JSONArray jsonArray = materiaService.leggiAlberoMateriaOpEdit(settoreGiuridicoId.longValue(), new Locale("it","IT"), true);
			if (jsonArray != null) {
				materiaRest.setJsonAlberaturaMaterie(jsonArray.toString());
			} else {
				materiaRest.setJsonAlberaturaMaterie(null);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return materiaRest;
	}

	@RequestMapping(value = "/materia/caricaAlberaturaMaterieVis", method = RequestMethod.POST)
	public @ResponseBody MateriaRest caricaAlberaturaMaterieVis(
			@RequestParam("settoreGiuridicoId") Long settoreGiuridicoId) {
		MateriaRest materiaRest = new MateriaRest();



		try {
			JSONArray jsonArray = materiaService.leggiAlberoMateriaOpVis(settoreGiuridicoId.longValue(), new Locale("it","IT"), true);
			if (jsonArray != null) {
				materiaRest.setJsonAlberaturaMaterie(jsonArray.toString());
			} else {
				materiaRest.setJsonAlberaturaMaterie(null);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return materiaRest;
	}

	@RequestMapping(value = "/materia/caricaMateriaByLingua", method = RequestMethod.POST)
	public @ResponseBody MateriaRest caricaMateriaByLingua( @RequestParam("codGruppoLingua") String codGruppoLingua, @RequestParam("codiceLingua") String codiceLingua) {
		MateriaRest materiaRest = new MateriaRest();


		try {
			MateriaView materiaView = materiaService.leggi(codGruppoLingua,codiceLingua);
			materiaRest.setId(materiaView.getVo().getId());
			materiaRest.setNome(materiaView.getVo().getNome());
			if( materiaView.getVo().getMateriaPadre() != null )
				materiaRest.setIdSottoMateria( materiaView.getVo().getMateriaPadre().getId() );
			if( materiaView.getVo().getMateriaPadre() != null )
				materiaRest.setSottoMateriaNome( materiaView.getVo().getMateriaPadre().getNome() );

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return materiaRest;
	}

	@RequestMapping(value = "/materia/salva", method = RequestMethod.POST)
	public String salvaMateria(
			Locale locale, 
			Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated MateriaView materiaView, 
			BindingResult bindingResult,
			HttpServletRequest request, 
			HttpServletResponse response) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		String token=request.getParameter("CSRFToken");

		try {
			if (materiaView.getOp() != null && !materiaView.getOp().equals("salvaMateria")) {
				String ritorno = invocaMetodoDaReflection(materiaView, bindingResult, locale, model, request,
						response, this);
				return ritorno == null ? PAGINA_FORM_EDIT_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				return PAGINA_FORM_EDIT_PATH;
			}

			if (materiaView.isInsertMode()) {

				materiaService.inserisci(materiaView);

			} else if(materiaView.isDeleteMode()){
				materiaService.cancella(materiaView);
			} else if(materiaView.isEditMode()) {
				materiaService.modifica(materiaView);
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
			return "redirect:gestioneMateria.action?CSRFToken="+token;
		} catch (Throwable e) {
			bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			return PAGINA_FORM_EDIT_PATH;
		}
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new MateriaValidator());
	}




}