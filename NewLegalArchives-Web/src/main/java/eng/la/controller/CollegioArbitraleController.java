package eng.la.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eng.la.business.IncaricoService;
import eng.la.business.ProfessionistaEsternoService;
import eng.la.model.Incarico;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.CollegioArbitraleView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.persistence.audit.AuditInterceptor;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("collegioArbitraleController")
public class CollegioArbitraleController extends BaseController{
	
	private static final String MODEL_VIEW_NOME = "collegioArbitraleView";
	private static final String PAGINA_FORM_PATH = "incarico/formCollegioArbitrale";
	private static final String PAGINA_DETTAGLIO_PATH = "incarico/formCollegioArbitraleDettaglio";

	@Autowired
	private ProfessionistaEsternoService professionistaEsternoService;
	
	@Autowired
	private IncaricoService incaricoService;

	@RequestMapping("/incarico/creaCollegioArbitrale")
	public String creaIncarico(HttpServletRequest request, Model model, Locale locale) {
		CollegioArbitraleView view = new CollegioArbitraleView();
		
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			super.caricaListe(view, locale);
			if( request.getParameter("incaricoId") != null ){
				long incaricoId = NumberUtils.toLong(request.getParameter("incaricoId"));
				IncaricoView incaricoView = incaricoService.leggi(incaricoId); 
				
				if (incaricoView.getVo() != null) {
					if( incaricoView.getVo().getCollegioArbitrale().equals(FALSE_CHAR)
							&& incaricoView.getVo().getStatoIncarico().getCodGruppoLingua().equals(INCARICO_STATO_AUTORIZZATO) 
							&& incaricoView.getVo().getFascicolo().getTipologiaFascicolo().getCodGruppoLingua().equals(TIPOLOGIA_FASCICOLO_GIUDIZIALE_COD) 
						 	&& incaricoView.getVo().getFascicolo().getSettoreGiuridico().getCodGruppoLingua().equals(SETTORE_GIURIDICO_ARBITRALE_CODE)
						){
						view.setIncaricoRiferimento( incaricoView );
						view.setIncaricoRiferimentoId(incaricoId);
					}else{
						model.addAttribute("errorMessage", "errore.non.puoi.creare.arbitrale.su.incarico");
					}
				}else{

					model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
				}

			} else{

				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		} 
		
		view.setVo(new Incarico()); 
		 
		model.addAttribute(MODEL_VIEW_NOME, view);
		return  model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_FORM_PATH;
	} 

	@RequestMapping("/incarico/eliminaCollegioArbitrale")
	public @ResponseBody RisultatoOperazioneRest eliminaCollegioArbitrale(@RequestParam("id") long id
			, HttpServletRequest request
			) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {
			CollegioArbitraleView view = incaricoService.leggiCollegioArbitrale(id);
			if(view != null){
				incaricoService.cancellaCollegioArbitrale(view);
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	
	
	@RequestMapping("/incarico/dettaglioCollegioArbitrale")
	public String dettaglioCollegioArbitrale(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		CollegioArbitraleView view = new CollegioArbitraleView();
		
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		
		try { 
			view = (CollegioArbitraleView) incaricoService.leggiCollegioArbitrale(id);
			if (view != null && view.getVo() != null) {
				popolaFormDaVO(view, view.getVo(), locale);
				
				super.caricaListePerDettaglio(view, locale);

				AuditInterceptor auditInterceptor = (AuditInterceptor) SpringUtil.getBean("auditInterceptor"); 
				auditInterceptor.auditRead(view.getVo());
			} else {
				model.addAttribute("errorMessage", "errore.generico");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_DETTAGLIO_PATH;
	}
	
	
	@RequestMapping("/incarico/modificaCollegioArbitrale")
	public String modificaCollegioArbitrale(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		CollegioArbitraleView view = new CollegioArbitraleView();
		// engsecurity VA ??
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try { 
			view = (CollegioArbitraleView) incaricoService.leggiCollegioArbitrale(id);
			if (view != null && view.getVo() != null) { 
				super.caricaListe(view, locale);
				popolaFormDaVO(view, view.getVo(), locale);
			} else {
				model.addAttribute("errorMessage", "errore.generico");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_FORM_PATH;
	}
	
	
	private void popolaFormDaVO(CollegioArbitraleView view, Incarico vo, Locale locale)throws Throwable{
		
		if( vo.getProfessionistaEsterno() != null ){
			view.setArbitroDiParteId(vo.getProfessionistaEsterno().getId());
			ProfessionistaEsternoView arbitroDiParte = new ProfessionistaEsternoView();
			arbitroDiParte.setVo(vo.getProfessionistaEsterno());
			view.setArbitroDiParte(arbitroDiParte);
		}
		
		if( vo.getIncarico() != null ){		
			IncaricoView incaricoRiferimento = new IncaricoView();
			incaricoRiferimento.setVo(vo.getIncarico());
			view.setIncaricoRiferimento(incaricoRiferimento);
			view.setIncaricoRiferimentoId(incaricoRiferimento.getVo().getId() );
			
		}
		
		view.setStatoCollegioArbitrale(vo.getStatoIncarico().getDescrizione());
		view.setStatoCollegioArbitraleCode(vo.getStatoIncarico().getCodGruppoLingua());	
		view.setDataAutorizzazione( vo.getDataAutorizzazione()==null?null:DateUtil.formattaData(vo.getDataAutorizzazione().getTime()));
		view.setDataRichiestaAutorizzazione( vo.getDataRichiestaAutorIncarico() == null ? null : DateUtil.formattaData(vo.getDataRichiestaAutorIncarico().getTime()));
	 
		
		view.setCollegioArbitraleId(vo.getId());
		
		view.setNomeCollegioArbitrale(vo.getNomeCollegioArbitrale());			
		
		view.setDenominazioneStudioArbitroControparte(vo.getDenominazStudioControparte());
		view.setDenominazioneStudioArbitroPresidente(vo.getDenomStudioArbitroPresiden());
		view.setDenominazioneStudioArbitroSegretario(vo.getDenominStudioArbitroSegret());
		
		view.setNominativoArbitroControparte(vo.getNominativoArbitroControparte());
		view.setNominativoArbitroPresidente(vo.getNominativoPresidente() );
		view.setNominativoArbitroSegretario(vo.getNominativoSegretario());
		
		view.setIndirizzoArbitroControparte(vo.getIndirizzoArbitroControparte());
		view.setIndirizzoArbitroPresidente(vo.getIndirizzoArbitroPresidente());
		view.setIndirizzoArbitroSegretario(vo.getIndirizzoArbitroSegretario());
	
	}
	
	private void preparaPerSalvataggio(CollegioArbitraleView view, BindingResult bindingResult)throws Throwable {
		Incarico vo = new Incarico();
		Incarico oldIncaricoCollegioArbitrale = null;
		if (view.getCollegioArbitraleId() != null) {
			oldIncaricoCollegioArbitrale = incaricoService.leggiCollegioArbitrale(view.getCollegioArbitraleId()).getVo();

			vo.setId(view.getCollegioArbitraleId());
			vo.setNomeCollegioArbitrale(oldIncaricoCollegioArbitrale.getNomeCollegioArbitrale());
			vo.setStatoIncarico(oldIncaricoCollegioArbitrale.getStatoIncarico());
			vo.setDataCreazione(oldIncaricoCollegioArbitrale.getDataCreazione());
			vo.setDataAutorizzazione( oldIncaricoCollegioArbitrale.getDataAutorizzazione() );
			vo.setDataRichiestaAutorIncarico( oldIncaricoCollegioArbitrale.getDataRichiestaAutorIncarico() );
		}

		if( view.getArbitroDiParteId() != null ){
			ProfessionistaEsternoView arbitroDiParte = professionistaEsternoService.leggi(view.getArbitroDiParteId());
			vo.setProfessionistaEsterno(arbitroDiParte.getVo());
		} 
		
		vo.setCollegioArbitrale(TRUE_CHAR);
		vo.setDenominazStudioControparte( view.getDenominazioneStudioArbitroControparte());
		vo.setDenominStudioArbitroSegret( view.getDenominazioneStudioArbitroSegretario());
		vo.setDenomStudioArbitroPresiden(view.getDenominazioneStudioArbitroPresidente());
		
		vo.setNominativoArbitroControparte(view.getNominativoArbitroControparte() );
		vo.setNominativoPresidente(view.getNominativoArbitroPresidente() );
		vo.setNominativoSegretario(view.getNominativoArbitroSegretario());
		
		vo.setIndirizzoArbitroControparte(view.getIndirizzoArbitroControparte());
		vo.setIndirizzoArbitroPresidente(view.getIndirizzoArbitroPresidente());
		vo.setIndirizzoArbitroSegretario(view.getIndirizzoArbitroSegretario());
		IncaricoView incaricoRiferimento = incaricoService.leggi(view.getIncaricoRiferimentoId());
		vo.setIncarico(incaricoRiferimento.getVo());
		vo.setNomeIncarico(incaricoRiferimento.getVo().getNomeIncarico());
		vo.setFascicolo(incaricoRiferimento.getVo().getFascicolo());
		view.setVo(vo);
	}

	

	@RequestMapping(value = "/incarico/salvaCollegioArbitrale", method = RequestMethod.POST)
	public String salvaIncarico(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated CollegioArbitraleView view, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		 
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
				String token=request.getParameter("CSRFToken");
		try {

			if (view.getOp() != null && !view.getOp().equals("salvaCollegioArbitrale")) {
				String ritorno = invocaMetodoDaReflection(view, bindingResult, locale, model, request,
						response, this);

				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			preparaPerSalvataggio(view, bindingResult);

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			long collegioArbitraleId = 0; 
			if (view.getCollegioArbitraleId() == null || view.getCollegioArbitraleId() == 0) {
				CollegioArbitraleView collegioArbitraleSaved = incaricoService.inserisciCollegioArbitrale(view);
				collegioArbitraleId = collegioArbitraleSaved.getVo().getId();
				 
			} else {
				incaricoService.modificaCollegioArbitrale(view);
				collegioArbitraleId = view.getVo().getId();
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
			 
			return "redirect:dettaglioCollegioArbitrale.action?id=" + collegioArbitraleId+"&CSRFToken="+token;
		} catch (Throwable e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}  
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		CollegioArbitraleView collegioArbitraleView = (CollegioArbitraleView) view;
		List<ProfessionistaEsternoView> listaProfessionistaEsternoViews = professionistaEsternoService.leggiProfessionistiPerCategoria(Costanti.TIPOLOGIA_PROFESSIONISTA_ARBITRO_COD,false);
		collegioArbitraleView.setListaProfessionista(listaProfessionistaEsternoViews);
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		CollegioArbitraleView collegioArbitraleView = (CollegioArbitraleView) view;
		List<ProfessionistaEsternoView> listaProfessionistaEsternoViews = professionistaEsternoService.leggiProfessionistiPerCategoria(Costanti.TIPOLOGIA_PROFESSIONISTA_ARBITRO_COD,true);
		collegioArbitraleView.setListaProfessionista(listaProfessionistaEsternoViews);
	}

}
