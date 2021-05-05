package eng.la.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.FetchMode;
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
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import eng.la.business.FascicoloService;
import eng.la.business.ReportingService;
import eng.la.business.UdienzaService;
import eng.la.model.Udienza;
import eng.la.model.Utente;
import eng.la.model.rest.RicercaUdienzaRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.rest.UdienzaRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.UdienzaView;
import eng.la.model.view.UtenteView;
import eng.la.presentation.validator.UdienzaValidator;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("udienzaController")
@SessionAttributes("udienzaView")
public class UdienzaController extends BaseController {
	private static final String MODEL_VIEW_NOME = "udienzaView";
	private static final String PAGINA_FORM_PATH = "udienza/formUdienza";  
	private static final String PAGINA_DETTAGLIO_PATH = "udienza/formUdienzaDettaglio";
	private static final String PAGINA_RICERCA_PATH = "udienza/cercaUdienza";
	private static final String PAGINA_AZIONI_CERCA_UDIENZA = "udienza/azioniCercaUdienza"; 
	private static final String PAGINA_AZIONI_UDIENZA = "udienza/azioniUdienza";
	private static final String PAGINA_VISUALIZZA_STAMPA ="udienza/visualizzaUdienzaStampa";

	@Autowired
	private FascicoloService fascicoloService;

	@Autowired
	private UdienzaService udienzaService;

	@Autowired
	ReportingService reportingService;


	@RequestMapping("/udienza/modifica")
	public String modificaUdienza(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		UdienzaView view = new UdienzaView();

		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			UdienzaView udienzaSalvata = (UdienzaView) udienzaService.leggi(id);
			if (udienzaSalvata != null && udienzaSalvata.getVo() != null) {
				popolaFormDaVo(view, udienzaSalvata.getVo(), locale, utenteView);

			} else {
				super.caricaListe(view, locale);
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}

		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action": PAGINA_FORM_PATH;
	}

	@RequestMapping("/udienza/dettaglio")
	public String dettaglioUdienza(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		UdienzaView view = new UdienzaView();

		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			UdienzaView udienzaSalvata = (UdienzaView) udienzaService.leggi(id);
			if (udienzaSalvata != null && udienzaSalvata.getVo() != null) {
				popolaFormDaVo(view, udienzaSalvata.getVo(), locale, utenteView);

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

	private void popolaFormDaVo(UdienzaView view, Udienza vo, Locale locale, UtenteView utenteConnesso) throws Throwable {

		view.setUdienzaId(vo.getId());

		if( vo.getDataCreazione() != null ){
			view.setDataCreazione(DateUtil.formattaData(vo.getDataCreazione().getTime()));	
		}

		if( vo.getDataUdienza() != null ){
			view.setDataUdienza(DateUtil.formattaData(vo.getDataUdienza().getTime()));	
		}

		FascicoloView fascicoloView = fascicoloService.leggiTutti(vo.getFascicolo().getId(), FetchMode.JOIN);
		view.setFascicoloRiferimento( fascicoloView );
		boolean isPenale = fascicoloView.getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		view.setPenale(isPenale);

		view.setNomeFascicolo(vo.getFascicolo().getNome());

		view.setDescrizione(vo.getDescrizione());
	}

	@RequestMapping(value = "/udienza/salva", method = RequestMethod.POST)
	public String salvaUdienza(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated UdienzaView udienzaView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		String token=request.getParameter("CSRFToken");

		try {

			if (udienzaView.getOp() != null && !udienzaView.getOp().equals("salvaUdienza")) {
				
				String ritorno = invocaMetodoDaReflection(udienzaView, bindingResult, locale, model, request,response, this);

				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}
			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			preparaPerSalvataggio(udienzaView, bindingResult);

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			long udienzaId = 0;
			boolean isNew = false;
			if (udienzaView.getUdienzaId() == null || udienzaView.getUdienzaId() == 0) {
				UdienzaView udienzaSalvata = udienzaService.inserisci(udienzaView);
				udienzaId = udienzaSalvata.getVo().getId();
				isNew = true;
				
			} else {
				/** In caso di modifica  **/

				udienzaService.modifica(udienzaView);

				udienzaId = udienzaView.getVo().getId();
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
			if( isNew ){
				return "redirect:modifica.action?id=" + udienzaId+"&CSRFToken="+token;
			}
			return "redirect:dettaglio.action?id=" + udienzaId+"&CSRFToken="+token;
		} catch (Throwable e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}

	@RequestMapping(value="/udienza/eliminaUdienza", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaUdienza(@RequestParam("id") long id,
			HttpServletRequest request, HttpServletResponse response) {
		
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UdienzaView udienzaView = udienzaService.leggi(id);
			udienzaService.cancella(udienzaView);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	private void preparaPerSalvataggio(UdienzaView view, BindingResult bindingResult)throws Throwable {
		Udienza vo = new Udienza();
		UdienzaView oldUdienzaView = null;

		if (view.getUdienzaId() != null) {

			oldUdienzaView = udienzaService.leggi(view.getUdienzaId());
			vo.setDataCreazione(oldUdienzaView.getVo().getDataCreazione());
			vo.setId(view.getUdienzaId());
		}

		if( view.getDataUdienza() != null && DateUtil.isData(view.getDataUdienza())){
			vo.setDataUdienza(DateUtil.toDate(view.getDataUdienza()));	
		}

		vo.setFascicolo(view.getFascicoloRiferimento().getVo());

		vo.setDescrizione(view.getDescrizione());

		view.setVo(vo);
	}

	@RequestMapping("/udienza/crea")
	public String creaUdienza(HttpServletRequest request, Model model, Locale locale) {
		UdienzaView view = new UdienzaView();
		
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {
			if( request.getParameter("fascicoloId") != null ){
				long fasicoloId = NumberUtils.toLong(request.getParameter("fascicoloId"));
				FascicoloView fascicoloView = fascicoloService.leggi(fasicoloId,FetchMode.JOIN);
				view.setFascicoloRiferimento(fascicoloView);
				view.setNomeFascicolo(fascicoloView.getVo().getNome());
				view.setDataCreazione(DateUtil.formattaData(new Date().getTime()));
				super.caricaListe(view, locale);
			}
			
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		} 
		view.setVo(new Udienza()); 

		model.addAttribute(MODEL_VIEW_NOME, view);
		return  model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_FORM_PATH;
	}

	@RequestMapping(value = "/udienza/cercaUdienza", method = RequestMethod.GET)
	public String cercaUdienza(HttpServletRequest request, Model model, Locale locale) {
		
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {
			
			List<Utente> comboOwnerFascicoli=null;
			UtenteView utenteView = (UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			
			// SOLO UTENTE AMMINISTRATORE
			if(utenteView!=null && utenteView.isAmministratore()){
				comboOwnerFascicoli=udienzaService.getListaLegaleInternoOwnerFascicolo((long)0);//0=tutti
				model.addAttribute("legaleInterno", comboOwnerFascicoli); 
		    }
			
			UdienzaView view = new UdienzaView();
			model.addAttribute(MODEL_VIEW_NOME, view);
			return PAGINA_RICERCA_PATH;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/udienza/ricerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaUdienzaRest cercaUdienza(HttpServletRequest request, Locale locale) {

		try {

			int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA : NumberUtils.toInt(request.getParameter("limit"));
			String offset = request.getParameter("offset");
			int numeroPagina = offset == null || offset.equals("0") ? 1
					: (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
			String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
			String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
			if( request.getParameter("sort") == null ){
				tipoOrdinamento = "DESC";
			}

			String nomeFascicolo = request.getParameter("nomeFascicolo") == null ? "" : URLDecoder.decode( request.getParameter("nomeFascicolo"),"UTF-8");
			String dal = request.getParameter("dal") == null ? "" : URLDecoder.decode(request.getParameter("dal"),"UTF-8");
			String al = request.getParameter("al") == null ? "" :  URLDecoder.decode(request.getParameter("al"),"UTF-8");
			String legaleInterno=(request.getParameter("legaleInterno")!=null && !request.getParameter("legaleInterno").equals(""))?request.getParameter("legaleInterno"):"";

			ListaPaginata<UdienzaView> lista = (ListaPaginata<UdienzaView>) udienzaService.cerca( dal, al, nomeFascicolo, legaleInterno, numElementiPerPagina, numeroPagina,
					ordinamento, tipoOrdinamento);
			RicercaUdienzaRest ricercaModelRest = new RicercaUdienzaRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<UdienzaRest> rows = new ArrayList<UdienzaRest>();
			for (UdienzaView view : lista) {
				UdienzaRest udienzaRest = new UdienzaRest();
				udienzaRest.setId(view.getVo().getId());
				udienzaRest.setAnno(DateUtil.getAnno(view.getVo().getDataCreazione()) + "");
				udienzaRest.setDataCreazione(DateUtil.formattaData(view.getVo().getDataCreazione().getTime()));
				udienzaRest.setDataUdienza(DateUtil.formattaData(view.getVo().getDataUdienza().getTime()));
				udienzaRest.setNomeFascicolo(view.getVo().getFascicolo().getNome());
				
				List<Utente> owners=udienzaService.getListaLegaleInternoOwnerFascicolo(view.getVo().getFascicolo().getId());
				if(owners!=null && owners.size()>0){
					 Utente owner=owners.get(0);
					 udienzaRest.setOwnerFascicolo(owner.getNominativoUtil());
				}

				String assegnatario="-"; 

				udienzaRest.setAzioni("<p id='containerAzioniRigaUdienza" + view.getVo().getId() + "' title='"+assegnatario+"' alt='"+assegnatario+"' ></p>");
				rows.add(udienzaRest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/udienza/caricaAzioniUdienza", method = RequestMethod.POST)
	public String caricaAzioniUdienza(@RequestParam("idUdienza") long idUdienza, HttpServletRequest req,
			Locale locale) {
		// engsecurity VA -- in loading
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(req);
		//removeCSRFToken(request);

		try {

			// LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI
			// SULL'UDIENZA
			req.setAttribute("idUdienza", idUdienza);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if( req.getParameter("onlyContent") != null  ){
			return PAGINA_AZIONI_UDIENZA;
		}
		return PAGINA_AZIONI_CERCA_UDIENZA;
	}

	@RequestMapping(value ="/udienza/stampa", method = RequestMethod.GET)
	public String stampaUdienza(@RequestParam("id") long id,UdienzaView udienzaView,
			Model model, Locale locale, HttpServletRequest request) throws Throwable {
		// engsecurity VA 
		// HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		// htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);

		UdienzaView view = new UdienzaView();

		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			UdienzaView udienzaSalvata = (UdienzaView) udienzaService.leggi(id);
			if (udienzaSalvata != null && udienzaSalvata.getVo() != null) {
				popolaFormDaVo(view, udienzaSalvata.getVo(), locale, utenteView);

			} else {
				model.addAttribute("errorMessage", "errore.generico");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_VISUALIZZA_STAMPA;
	}

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new UdienzaValidator());
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}


}
