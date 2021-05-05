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
import org.apache.log4j.Logger;

import eng.la.business.AffariSocietariService;
import eng.la.business.OrganoSocialeService;
import eng.la.business.ReportingService;
import eng.la.business.SocietaService;
import eng.la.model.AffariSocietari;
import eng.la.model.OrganoSociale;
import eng.la.model.TipoOrganoSociale;
import eng.la.model.aggregate.OrganoSocialeAggregate;
import eng.la.model.filter.OrganoSocialeFilter;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.rest.OrganoSocialeRest;
import eng.la.model.rest.RicercaOrganoSocialeRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.OrganoSocialeView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.UtenteView;
import eng.la.presentation.validator.OrganoSocialeValidator;
import eng.la.util.DateUtil;
import eng.la.util.DateUtil2;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("organoSocialeController")
@SessionAttributes("organoSocialeView")
public class OrganoSocialeController extends BaseController {
	
	private static final String MODEL_VIEW_NOME = "organoSocialeView";
	private static final String PAGINA_CREAZIONE = "organoSociale/formCreazioneOrganoSociale";
	private static final String PAGINA_RICERCA = "organoSociale/ricercaOrganoSociale";
	private static final String PAGINA_AZIONI_ORGANO_SOCIALE = "organoSociale/azioniOrganoSociale";
	private static final String PAGINA_FORM_PATH = "organoSociale/modificaOrganoSociale";  
	private static final String PAGINA_EXPORT = "organoSociale/exportOrganoSociale";
	
	private static final org.apache.log4j.Logger logger = Logger.getLogger(OrganoSocialeController.class);
	
	//  messaggio di conferma
	//	private static final String SUCCESS_MSG ="successMsg";
	//	private static final int ELEMENTI_PER_PAGINA_PC = 100;
	//	private static final String REMOTE_USER_PARAM_NAME = "REMOTE_USER";
	
	@Autowired
	private OrganoSocialeService organoSocialeService;
	
	@Autowired
	private AffariSocietariService affariSocietariService;
	
	@Autowired
	ReportingService reportingService;
	/**
	 * Carica form creazione organoSociale
	 * @param model modello dati
	 * @param locale country locale
	 * @return page on
	 */
	@RequestMapping("/organoSociale/crea")
	public String creazioneOrganoSociale(Locale locale, Model model, HttpServletRequest request) {
		OrganoSocialeView organoSocialeView = new OrganoSocialeView();
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {
			organoSocialeView.setLocale(locale);
			this.caricaListe(organoSocialeView, locale);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		String noRemakeView = request.getParameter("noRemakeView");
		if(noRemakeView == null){
			model.addAttribute(MODEL_VIEW_NOME, organoSocialeView);
		}
		
    	return PAGINA_CREAZIONE;
	}
	
	@RequestMapping(value = "/organoSociale/salva", method = RequestMethod.POST)
	public String salvaOrganoSociale(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated OrganoSocialeView organoSocialeView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		String token=request.getParameter("CSRFToken");

		try {
			
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);


			if (organoSocialeView.getOp() != null && !organoSocialeView.getOp().equals("salvaOrganoSociale")) {
				String ritorno = invocaMetodoDaReflection(organoSocialeView, bindingResult, locale, model, request,
						response, this);

				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				if (organoSocialeView.getOrganoSocialeId() == null || organoSocialeView.getOrganoSocialeId() == 0) {
					return PAGINA_CREAZIONE;
				}
				return PAGINA_FORM_PATH;
			}

			preparaPerSalvataggio(organoSocialeView, bindingResult,utenteView);


			long organoSocialeId = 0;
			if (organoSocialeView.getOrganoSocialeId() == null || organoSocialeView.getOrganoSocialeId() == 0) {
				OrganoSocialeView organoSociale = organoSocialeService.inserisci(organoSocialeView);
				organoSocialeId = organoSociale.getVo().getId();
			} else {
				organoSocialeService.modifica(organoSocialeView);
				organoSocialeId = organoSocialeView.getVo().getId();
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");

			return "redirect:modifica.action?id=" + organoSocialeId+"&CSRFToken="+token;

		} catch (Throwable e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}
	
	private Date convertDateForVo(String data){
		if (data == null || data.isEmpty()) return null;
		return DateUtil2.convert_StringToDate("dd/MM/yyyy", data);
	}
	
	private String convertDateForForm(Date data){
		if (data == null) return "";
		return DateUtil2.convert_DateToString("dd/MM/yyyy", data);
	}
	
	private void preparaPerSalvataggio(OrganoSocialeView view, BindingResult bindingResult, UtenteView utenteView) throws Throwable {
		OrganoSociale vo = new OrganoSociale();

		if (view.getOrganoSocialeId() != null) {
//			OrganoSocialeView oldOrganoSocialeView = organoSocialeService.leggi(view.getOrganoSocialeId());

			vo.setId(view.getOrganoSocialeId());
			vo.setSocietaAffari(new AffariSocietari(view.getIdSocietaAffari()));
			vo.setTipoOrganoSociale(new TipoOrganoSociale(view.getTipoOrganoSociale()));
			vo.setCognome(view.getCognome());
			vo.setNome(view.getNome());
			vo.setCarica(view.getCarica());
			vo.setDataNomina(convertDateForVo(view.getDataAccettazioneCarica()));
			vo.setDataCessazione(convertDateForVo(view.getDataCessazione()));
			vo.setDataScadenza(convertDateForVo(view.getDataScadenza()));	
			vo.setDataAccettazioneCarica(convertDateForVo(view.getDataAccettazioneCarica()));	
			vo.setEmolumento(view.getEmolumento());
			vo.setDataNascita(convertDateForVo(view.getDataNascita()));	
			vo.setCodiceFiscale(view.getCodiceFiscale());
			vo.setNote(view.getNote());
			vo.setLuogoNascita(view.getLuogoNascita());

		}else{
			vo.setSocietaAffari(new AffariSocietari(view.getIdSocietaAffari()));
			vo.setTipoOrganoSociale(new TipoOrganoSociale(view.getTipoOrganoSociale()));
			vo.setCognome(view.getCognome());
			vo.setNome(view.getNome());
			vo.setCarica(view.getCarica());
			vo.setDataNomina(convertDateForVo(view.getDataAccettazioneCarica()));
			vo.setDataCessazione(convertDateForVo(view.getDataCessazione()));
			vo.setDataScadenza(convertDateForVo(view.getDataScadenza()));	
			vo.setDataAccettazioneCarica(convertDateForVo(view.getDataAccettazioneCarica()));	
			vo.setEmolumento(view.getEmolumento());
			vo.setDataNascita(convertDateForVo(view.getDataNascita()));	
			vo.setCodiceFiscale(view.getCodiceFiscale());
			vo.setNote(view.getNote());
			vo.setLuogoNascita(view.getLuogoNascita());
		}
		
		view.setVo(vo);
	}

	@Override	
	public void caricaListe(BaseView view, Locale locale) throws Throwable{
		SocietaService societaService = (SocietaService) SpringUtil.getBean("societaService");		
		List<SocietaView> listaSocieta = societaService.leggi(false);
		view.setListaSocieta(listaSocieta);
		caricaListeOggetti(view, locale);
	}
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		OrganoSocialeView organoSocialeView = (OrganoSocialeView) view;
		List<CodiceDescrizioneBean> listaSocietaAffari = organoSocialeService.leggiListaSocietaAffari();
		organoSocialeView.setListaSocietaAffari(listaSocietaAffari);
		List<CodiceDescrizioneBean> listaOrganoSociale = organoSocialeService.leggiOrganiSociali(locale);
		organoSocialeView.setListaOrganoSociale(listaOrganoSociale);
	}
	
	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}

	
	
	@RequestMapping(value = "/organoSociale/ricerca")
	public String ricerca(OrganoSocialeView organoSocialeView, HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		if (organoSocialeView == null)
			organoSocialeView = new OrganoSocialeView();

		try {
			organoSocialeView.setLocale(locale);
			this.caricaListe(organoSocialeView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		model.addAttribute("organoSocialeView", organoSocialeView);
		return PAGINA_RICERCA;
	}
	
	// EVO Export organi sociali RIDP6U9
	@RequestMapping(value = "/organoSociale/export")
	public String export(OrganoSocialeView organoSocialeView, HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		if (organoSocialeView == null)
			organoSocialeView = new OrganoSocialeView();

		try {
			organoSocialeView.setLocale(locale);
			this.caricaListe(organoSocialeView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		model.addAttribute("organoSocialeView", organoSocialeView);
		return PAGINA_EXPORT;
	}
	
	@RequestMapping(value = "/organoSociale/cerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaOrganoSocialeRest cercaOrganoSociale(OrganoSocialeView organoSocialeView, HttpServletRequest request, Model model,
			Locale locale) throws Throwable {


		
		if (organoSocialeView == null){
			organoSocialeView = new OrganoSocialeView(); 
		}
		
		String lingua = locale.getLanguage().toUpperCase();
		int numElementiPerPagina = request.getParameter("limit") == null ? 108 : NumberUtils.toInt(request.getParameter("limit"));
		String offset = request.getParameter("offset");
		int numeroPagina = offset == null || offset.equals("0") ? 1 : (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
		


		List<OrganoSociale> organoSocialeList = null;
		OrganoSocialeFilter filter = new OrganoSocialeFilter();
		filter.setNumeroPagina(numeroPagina);
		filter.setNumElementiPerPagina(numElementiPerPagina);
		
		
		String cognome = request.getParameter("cognome");
		if (cognome != null) {
			filter.setCognome(cognome);
		}
		String nome = request.getParameter("nome");
		if (nome != null) {
			filter.setNome(nome);
		}
		String incarica = request.getParameter("incarica");
		if (incarica != null) {
			filter.setIncarica(incarica);
		}
		String idSocietaAffari = request.getParameter("idSocietaAffari");
		if (idSocietaAffari != null && !idSocietaAffari.isEmpty()) {
			filter.setIdSocietaAffari(new Long(idSocietaAffari));
		}
		String tipoOrganoSociale = request.getParameter("tipoOrganoSociale");
		if (tipoOrganoSociale != null && !tipoOrganoSociale.isEmpty()) {
			filter.setTipoOrganoSociale(new Long(tipoOrganoSociale));
		}
		
		String gruppoSnam = request.getParameter("gruppoSnam");
		if (gruppoSnam != null) {
			filter.setGruppoSnam(gruppoSnam);
		}
		
		String order = request.getParameter("order");
		if (order != null) {
			filter.setOrder(!order.trim().equals("") ? order : "desc");
		}
		
		String sortBy = request.getParameter("sort");
		if (sortBy != null && !sortBy.equals("idSocieta")) {
			filter.setSortBy(!sortBy.trim().equals("") ? sortBy : "societaAffari");
		}else{
			filter.setSortBy("societaAffari");
		}
		
		System.out.println(order + " | " + sortBy);
		
		try {
			
			List<Long> listaSNAM_SRG_GNL_STOGIT = affariSocietariService.getListaSNAM_SRG_GNL_STOGIT();
			
			OrganoSocialeAggregate aggregate = organoSocialeService.searchPagedOrganoSociale(filter, listaSNAM_SRG_GNL_STOGIT);
			organoSocialeList = aggregate != null ? aggregate.getList() : null;
			RicercaOrganoSocialeRest ricercaOrganoSocialeRest = new RicercaOrganoSocialeRest();
			ricercaOrganoSocialeRest.setTotal(aggregate != null ? aggregate.getNumeroTotaleElementi() : 0);
			ricercaOrganoSocialeRest.setRows(convertOrganoSocialeToRest(organoSocialeList, lingua));
			
			return ricercaOrganoSocialeRest;

		} catch (Exception e) {
			return new RicercaOrganoSocialeRest();
		}

	}
	

	
	private List<OrganoSocialeRest> convertOrganoSocialeToRest(List<OrganoSociale> list, String lingua) throws Throwable {
		List<OrganoSocialeRest> out = new ArrayList<OrganoSocialeRest>();
		try{
			for (OrganoSociale organoSociale : list) {
				OrganoSocialeRest organoSocialeRest = new OrganoSocialeRest();
				organoSocialeRest.setId(organoSociale.getId());
				organoSocialeRest.setIdSocieta(organoSociale.getSocietaAffari().getDenominazione());			    
				organoSocialeRest.setTipoOrganoSociale(organoSociale.getTipoOrganoSociale().getDescrizione());
				
				String cognome = organoSociale.getCognome()!=null?organoSociale.getCognome():" ";
				String nome = organoSociale.getNome()!=null?organoSociale.getNome():"";
	
				organoSocialeRest.setNominativo(cognome + " " + nome); 
				organoSocialeRest.setCarica(organoSociale.getCarica());			   		
				organoSocialeRest.setDataNomina(convertDateForForm(organoSociale.getDataNomina()));				
				organoSocialeRest.setDataCessazione(convertDateForForm(organoSociale.getDataCessazione()));			
				organoSocialeRest.setDataScadenza(convertDateForForm(organoSociale.getDataScadenza()));			
				organoSocialeRest.setDataAccettazioneCarica(convertDateForForm(organoSociale.getDataAccettazioneCarica()));
				organoSocialeRest.setEmolumento(organoSociale.getEmolumento() != null ? organoSociale.getEmolumento() + "" : "");
				organoSocialeRest.setDataCancellazione(convertDateForForm(organoSociale.getDataCancellazione()));
				
				String titleAlt = "-";
				organoSocialeRest.setAzioni("<p id='action-organosociale-" + organoSociale.getId() + "' alt='" + titleAlt + "' title='" + titleAlt + "'></p>");
				out.add(organoSocialeRest);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return out;
	}
	
	@RequestMapping(value = "/organoSociale/caricaAzioniOrganoSociale", method = RequestMethod.POST)
	public String caricaAzioniOrganoSociale(@RequestParam("idOrganoSociale") long idOrganoSociale, HttpServletRequest req, Locale locale) {

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(req);
        //removeCSRFToken(request);
		
			req.setAttribute("modifica", "yes");
			req.setAttribute("idOrganoSociale", idOrganoSociale);
		return PAGINA_AZIONI_ORGANO_SOCIALE;
	}
	
	
	@RequestMapping(value="/organoSociale/eliminaOrganoSociale", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaOrganoSociale(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {
			OrganoSocialeView view = new OrganoSocialeView();
			OrganoSocialeView organoSocialeSalvato = (OrganoSocialeView) organoSocialeService.leggi(id);
			if (organoSocialeSalvato != null && organoSocialeSalvato.getVo() != null) {
				popolaFormDaVo(view, organoSocialeSalvato.getVo());
				organoSocialeSalvato.getVo().setDataCancellazione(new Date());

				
				view.setVo(organoSocialeSalvato.getVo());
				organoSocialeService.modifica(view);
				
			} 
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	
	@RequestMapping(value="/organoSociale/exportOrganoSociale", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest exportOrganoSociale(HttpServletResponse response, Locale localLang) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {
			reportingService.exportExcellOrganiSociali(response, "IT");
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	
	private void popolaFormDaVo(OrganoSocialeView view, OrganoSociale vo) throws Throwable {
		view.setId(vo.getId());
		view.setOrganoSocialeId(vo.getId());
		view.setIdSocietaAffari(vo.getSocietaAffari().getId());
		view.setTipoOrganoSociale(vo.getTipoOrganoSociale().getId());
		view.setCognome(vo.getCognome());
		view.setNome(vo.getNome());
		view.setCarica(vo.getCarica());
		view.setDataNomina(convertDateForForm(vo.getDataAccettazioneCarica()));
		view.setDataCessazione(convertDateForForm(vo.getDataCessazione()));
		view.setDataScadenza(convertDateForForm(vo.getDataScadenza()));	
		view.setDataAccettazioneCarica(convertDateForForm(vo.getDataAccettazioneCarica()));	
		view.setEmolumento(vo.getEmolumento());
		view.setDataNascita(convertDateForForm(vo.getDataNascita()));	
		view.setCodiceFiscale(vo.getCodiceFiscale());
		view.setNote(vo.getNote());
		view.setLuogoNascita(vo.getLuogoNascita());

	}
	
	@RequestMapping(value= "/organoSociale/modifica",method = RequestMethod.GET)
	public String modificaOrganoSociale(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		OrganoSocialeView view = new OrganoSocialeView();
		// engsecurity VA - redirect
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		try {
			OrganoSocialeView organoSocialeSalvato = (OrganoSocialeView) organoSocialeService.leggi(id);
			if (organoSocialeSalvato != null && organoSocialeSalvato.getVo() != null) {
				popolaFormDaVo(view, organoSocialeSalvato.getVo());
				
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
	
	@RequestMapping(value= "/organoSociale/visualizza",method = RequestMethod.GET)
	public String visualizzaOrganoSociale(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		// engsecurity VA
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		request.setAttribute("disableInVis", "true");
		return modificaOrganoSociale(id,model,locale,request);
	}




	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new OrganoSocialeValidator());
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}


	private void generateExcelReport(List<OrganoSocialeView> organoSocialeViewList, HttpServletResponse response){
		
		
	}

	
}



