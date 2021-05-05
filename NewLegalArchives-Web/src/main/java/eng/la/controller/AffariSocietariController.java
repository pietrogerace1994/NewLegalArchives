package eng.la.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eng.la.persistence.DocumentaleDdsDAO;
import it.snam.ned.libs.dds.dtos.v2.Document;
import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

//import com.filenet.api.collection.DocumentSet;
//import com.filenet.api.collection.PageIterator;
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;

import eng.la.business.AffariSocietariService;
import eng.la.business.NazioneService;
import eng.la.model.AffariSocietari;
import eng.la.model.Nazione;
import eng.la.model.RSocietaAffari;
import eng.la.model.aggregate.AffariSocietariAggregate;
import eng.la.model.filter.AffariSocietariFilter;
import eng.la.model.rest.AffariSocietariRest;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.rest.RicercaAffariSocietariRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.AffariSocietariView;
import eng.la.model.view.BaseView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.NazioneView;
import eng.la.model.view.RSocietaAffariView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.DocumentaleDAO;
import eng.la.presentation.validator.AffariSocietariValidator;
import eng.la.util.DateUtil2;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("affariSocietariController")
@SessionAttributes("affariSocietariView")
public class AffariSocietariController extends BaseController {
	
	private static final String MODEL_VIEW_NOME = "affariSocietariView";
	private static final String PAGINA_CREAZIONE = "affariSocietari/formCreazioneAffariSocietari";
	private static final String PAGINA_RICERCA = "affariSocietari/ricercaAffariSocietari";
	private static final String PAGINA_AZIONI_AFFARI_SOCIETARI = "affariSocietari/azioniAffariSocietari";
	//	private static final String PAGINA_MODIFICA_AFFARI_SOCIETARI = "affariSocietari/modificaAffariSocietari";
	private static final String PAGINA_FORM_PATH = "affariSocietari/modificaAffariSocietari";  
	//	private static final String PAGINA_PERMESSI_AFFARI_SOCIETARI = "affariSocietari/permessiAffariSocietari";
	//	private static final String PAGINA_POPUP_RICERCA_AFFARI_SOCIETARI = "affariSocietari/popupRicercaAffariSocietari";

	
	
	// messaggio di conferma
	//	private static final String SUCCESS_MSG ="successMsg";
	//	private static final int ELEMENTI_PER_PAGINA_PC = 100;
	//	private static final String REMOTE_USER_PARAM_NAME = "REMOTE_USER";
	
	
	@Autowired
	private AffariSocietariService affariSocietariService;

	
	/**
	 * Carica form creazione affariSocietari
	 * @param model modello dati
	 * @param locale country locale
	 * @return page on
	 */
	@RequestMapping("/affariSocietari/crea")
	public String creazioneAffariSocietari(Locale locale, Model model, HttpServletRequest request) {
		AffariSocietariView affariSocietariView = new AffariSocietariView();
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {
			affariSocietariView.setLocale(locale);
			this.caricaListe(affariSocietariView, locale);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		String noRemakeView = request.getParameter("noRemakeView");
		if(noRemakeView == null){
			model.addAttribute(MODEL_VIEW_NOME, affariSocietariView);
		}
		
    	return PAGINA_CREAZIONE;
	}
	
	@RequestMapping(value = "/affariSocietari/salva", method = RequestMethod.POST)
	public String salvaAffariSocietari(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated AffariSocietariView affariSocietariView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		String token=request.getParameter("CSRFToken");
		try {
			
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);


			if (affariSocietariView.getOp() != null && !affariSocietariView.getOp().equals("salvaAffariSocietari")) {
				String ritorno = invocaMetodoDaReflection(affariSocietariView, bindingResult, locale, model, request,
						response, this);

				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				if (affariSocietariView.getAffariSocietariId() == null || affariSocietariView.getAffariSocietariId() == 0) {
					return PAGINA_CREAZIONE;
				}
				return PAGINA_FORM_PATH;
			}

			preparaPerSalvataggio(affariSocietariView, bindingResult,utenteView);


			long affariSocietariId = 0;
			if (affariSocietariView.getAffariSocietariId() == null || affariSocietariView.getAffariSocietariId() == 0) {
				
				AffariSocietariView affariSocietari = affariSocietariService.inserisci(affariSocietariView);
				affariSocietariId = affariSocietari.getVo().getId();
			} 
			else {
				affariSocietariId = affariSocietariService.modifica(affariSocietariView);
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
//			if( isNew ){
				return "redirect:modifica.action?id=" + affariSocietariId+"&CSRFToken="+token;
//			}
//			return "redirect:dettaglio.action?id=" + affariSocietariId;
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
	
	private void preparaPerSalvataggio(AffariSocietariView view, BindingResult bindingResult, UtenteView utenteView) throws Throwable {
		AffariSocietari vo = new AffariSocietari();

			if (view.getAffariSocietariId() != null) {
				AffariSocietariView oldAffariSocietariView = affariSocietariService.leggi(view.getAffariSocietariId());
				vo.setId(view.getAffariSocietariId());
				vo.setDataCancellazione(oldAffariSocietariView.getVo().getDataCancellazione());
			}

		if (view.getSociAggiunti() != null
				&& view.getSociAggiunti().length > 0) {
			RSocietaAffariView[] array = view.getSociAggiunti();
			Set<RSocietaAffari> rsocietaAffaris = new HashSet<RSocietaAffari>();
			for(RSocietaAffariView rSocietaAffariView : array ){
				RSocietaAffari rSocietaAffari = new RSocietaAffari();
				
				Long idSocietaSocio = Long.parseLong(rSocietaAffariView.getIdSocietaSocio());
				AffariSocietariView affariSocietariSocioView = affariSocietariService.leggi(idSocietaSocio);
				Long percentualeSocio = Long.parseLong(rSocietaAffariView.getPercentualeSocio());
				
				rSocietaAffari.setSocietaSocio(affariSocietariSocioView.getVo());
				rSocietaAffari.setPercentualeSocio(percentualeSocio);
				rsocietaAffaris.add(rSocietaAffari);
			}
			vo.setRSocietaAffaris(rsocietaAffaris);
		}


			vo.setCodiceSocieta(view.getCodiceSocieta());
			vo.setDenominazione(view.getDenominazione());
			vo.setCapitaleSottoscritto(view.getCapitaleSottoscritto());
			vo.setCapitaleSociale(view.getCapitaleSociale());
			vo.setDataCostituzione(convertDateForVo(view.getDataCostituzione()));
			vo.setDataUscita(convertDateForVo(view.getDataUscita()));
			vo.setDenominazioneBreve(view.getDenominazioneBreve());
			vo.setSiglaFormaGiuridica(view.getSiglaFormaGiuridica());
			vo.setFormaGiuridica(view.getFormaGiuridica());
			Nazione n = new Nazione();
					n.setId(view.getIdNazione());
			vo.setNazione(n);
			vo.setSiglaStatoProvincia(view.getSiglaStatoProvincia());
			vo.setSiglaProvincia(view.getSiglaProvincia());
			vo.setItaliaEstero(view.getItaliaEstero());
			vo.setUeExstraue(view.getUeExstraue());
			vo.setSedeLegale(view.getSedeLegale());
			vo.setIndirizzo(view.getIndirizzo());
			vo.setCap(view.getCap());
			vo.setCodiceFiscale(view.getCodiceFiscale());
			vo.setPartitaIva(view.getPartitaIva());
			vo.setDataRea(convertDateForVo(view.getDataRea()));
			vo.setNumeroRea(view.getNumeroRea());
			vo.setComuneRea(view.getComuneRea());
			vo.setSiglaProvinciaRea(view.getSiglaProvinciaRea());
			vo.setProvinciaRea(view.getProvinciaRea());
			vo.setQuotazione(view.getQuotazione());
			vo.setInLiquidazione(view.getInLiquidazione());
			vo.setModelloDiGovernance(view.getModelloDiGovernance());
			vo.setnComponentiCDA(view.getnComponentiCDA());
			vo.setnComponentiCollegioSindacale(view.getnComponentiCollegioSindacale());
			vo.setnComponentiOdv(view.getnComponentiOdv());
			vo.setCodiceNazione(view.getCodiceNazione());
			vo.setSocietaDiRevisione(view.getSocietaDiRevisione());
			vo.setDataIncarico(convertDateForVo(view.getDataIncarico()));
			vo.setIdControllante(view.getIdControllante());
			vo.setPercentualeControllante(view.getPercentualeControllante());
			vo.setPercentualeTerzi(view.getPercentualeTerzi());
			vo.setDataCostituzione(convertDateForVo(view.getDataCostituzione()));
			
		view.setVo(vo);
	}


	@Override	
	public void caricaListe(BaseView view, Locale locale) throws Throwable{
		NazioneService nazioneService = (NazioneService) SpringUtil.getBean("nazioneService");
		List<NazioneView> listaNazioni = nazioneService.leggi(locale, false); 
		view.setListaNazioni(listaNazioni);
		caricaListeOggetti(view, locale);
	}
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		AffariSocietariView affariSocietariView = (AffariSocietariView) view;
		List<CodiceDescrizioneBean> listaSocietaControllanti = affariSocietariService.leggiListaSocietaControllanti();
		affariSocietariView.setListaSocietaControllanti(listaSocietaControllanti);
	}
	
	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
	

	
	
	@RequestMapping(value = "/affariSocietari/ricerca")
	public String ricerca(AffariSocietariView affariSocietariView, HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		if (affariSocietariView == null)
			affariSocietariView = new AffariSocietariView();

		try {
			affariSocietariView.setLocale(locale);
			this.caricaListe(affariSocietariView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		model.addAttribute("affariSocietariView", affariSocietariView);
		return PAGINA_RICERCA;
	}
	
	@RequestMapping(value = "/affariSocietari/cerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaAffariSocietariRest cercaAffariSocietari(AffariSocietariView affariSocietariView, HttpServletRequest request, Model model,
			Locale locale) throws Throwable {

		if (affariSocietariView == null){
			affariSocietariView = new AffariSocietariView(); 
		}
		
		String lingua = locale.getLanguage().toUpperCase();
		int numElementiPerPagina = request.getParameter("limit") == null ? 108 : NumberUtils.toInt(request.getParameter("limit"));
		String offset = request.getParameter("offset");
		int numeroPagina = offset == null || offset.equals("0") ? 1 : (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
		


		List<AffariSocietari> affariSocietariList = null;
		AffariSocietariFilter filter = new AffariSocietariFilter();
		filter.setNumeroPagina(numeroPagina);
		filter.setNumElementiPerPagina(numElementiPerPagina);
		
	
		String denominazione = request.getParameter("denominazione");
		if (denominazione != null) {
			filter.setDenominazione(denominazione);
		}

		
		String idNazione = request.getParameter("idNazione");
		if (idNazione != null && !idNazione.isEmpty()) {
			filter.setIdNazione(new Long(idNazione));
		}
		
		String quotazione = request.getParameter("quotazione");
		if (quotazione != null) {
			filter.setQuotazione(quotazione);
		}
		
		String modelloDiGovernance = request.getParameter("modelloDiGovernance");
		if (modelloDiGovernance != null) {
			filter.setModelloDiGovernance(modelloDiGovernance);
		}
		
		String storico = request.getParameter("storico");
		if (storico != null) {
			filter.setStorico(storico);
		}
		
		String gruppoSnam = request.getParameter("gruppoSnam");
		if (gruppoSnam != null) {
			filter.setGruppoSnam(gruppoSnam);
		}
		
		String order = request.getParameter("order");
		if (order != null) {
			filter.setOrder(!order.trim().equals("") ? order : "desc");
		}
		
		try {
			List<Long> listaSNAM_SRG_GNL_STOGIT = affariSocietariService.getListaSNAM_SRG_GNL_STOGIT();
			
			AffariSocietariAggregate aggregate = affariSocietariService.searchPagedAffariSocietari(filter, listaSNAM_SRG_GNL_STOGIT);
			affariSocietariList = aggregate != null ? aggregate.getList() : null;
			RicercaAffariSocietariRest ricercaAffariSocietariRest = new RicercaAffariSocietariRest();
			ricercaAffariSocietariRest.setTotal(aggregate != null ? aggregate.getNumeroTotaleElementi() : 0);
			ricercaAffariSocietariRest.setRows(convertAffariSocietariToRest(affariSocietariList, lingua));
			
			return ricercaAffariSocietariRest;

		} catch (Exception e) {
			return new RicercaAffariSocietariRest();
		}
	}
	
	private List<AffariSocietariRest> convertAffariSocietariToRest(List<AffariSocietari> list, String lingua) throws Throwable {
		List<AffariSocietariRest> out = new ArrayList<AffariSocietariRest>();
		
		
		for (AffariSocietari affariSocietari : list) {
			AffariSocietariRest affariSocietariRest = new AffariSocietariRest();

			affariSocietariRest.setDenominazione(affariSocietari.getDenominazione());
			affariSocietariRest.setSedeLegale(affariSocietari.getSedeLegale());
			affariSocietariRest.setCodiceFiscale(affariSocietari.getCodiceFiscale());
			affariSocietariRest.setPartitaIva(affariSocietari.getPartitaIva());
			
			if(affariSocietari.getRSocietaAffaris() != null){
				Collection<RSocietaAffari> societaAffaris = affariSocietari.getRSocietaAffaris();
				
				String denominazioneControllante = "";
				Long percentualeControllante = new Long(0);
				
				for( RSocietaAffari rSocietaAffari : societaAffaris ){
					
					if(rSocietaAffari.getSocietaSocio() != null){

						if(rSocietaAffari.getSocietaSocio().getDenominazione() != null){
							
							denominazioneControllante += rSocietaAffari.getSocietaSocio().getDenominazione() + "; ";
						}
					}
					
					if(rSocietaAffari.getPercentualeSocio() != null){
						percentualeControllante += rSocietaAffari.getPercentualeSocio();
					}
				}  
				affariSocietariRest.setDenominazioneControllante(denominazioneControllante);
				affariSocietariRest.setPercentualeControllante(percentualeControllante+"%");
			}
			affariSocietariRest.setPercentualeTerzi(affariSocietari.getPercentualeTerzi()+"%");
			affariSocietariRest.setId(affariSocietari.getId()+"");
			affariSocietariRest.setCancellato(affariSocietari.getDataCancellazione()==null);

			
			String titleAlt = "-";
			affariSocietariRest.setAzioni("<p id='action-affarisocietari-" + affariSocietari.getId() + "' alt='" + titleAlt + "' title='" + titleAlt + "'></p>");
			out.add(affariSocietariRest);
		}
		
		return out;
	}
	
	@RequestMapping(value = "/affariSocietari/caricaAzioniAffariSocietari", method = RequestMethod.POST)
	public String caricaAzioniAffariSocietari(@RequestParam("idAffariSocietari") long idAffariSocietari,@RequestParam("cancellato") boolean cancellato, HttpServletRequest req, Locale locale) {
          
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(req);
        //removeCSRFToken(request);
			req.setAttribute("modifica", "yes");
			req.setAttribute("idAffariSocietari", idAffariSocietari);
			req.setAttribute("cancellato", cancellato);
		return PAGINA_AZIONI_AFFARI_SOCIETARI;
	}
	
	
	@RequestMapping(value="/affariSocietari/eliminaAffariSocietari", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaAffariSocietari(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			AffariSocietariView view = new AffariSocietariView();
			AffariSocietariView affariSocietariSalvato = (AffariSocietariView) affariSocietariService.leggi(id);
			if (affariSocietariSalvato != null && affariSocietariSalvato.getVo() != null) {
				popolaFormDaVo(view, affariSocietariSalvato.getVo());
				affariSocietariSalvato.getVo().setDataUscita(new Date());

				
				String daRimuovere = (view.getAllegato()!=null && !view.getAllegato().isEmpty())==true?view.getAllegato().get(0).getUuid():"";
				
				view.setAllegatoDaRimuovereUuid(daRimuovere);
				view.setAllegato(null);
				view.setVo(affariSocietariSalvato.getVo());
				affariSocietariService.modifica(view);
				
			} 
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	

	private String convertDateForForm(Date data){
		if (data == null) return "";
		return DateUtil2.convert_DateToString("dd/MM/yyyy", data);
	}
	
	private void popolaFormDaVo(AffariSocietariView view, AffariSocietari vo) throws Throwable {
		view.setId(vo.getId());
		view.setAffariSocietariId(vo.getId());
		view.setCodiceSocieta(vo.getCodiceSocieta());
		view.setDenominazione(vo.getDenominazione());
		view.setCapitaleSottoscritto(vo.getCapitaleSottoscritto());
		view.setCapitaleSociale(vo.getCapitaleSociale());
		view.setDataCostituzione(convertDateForForm(vo.getDataCostituzione()));
		view.setDataUscita(convertDateForForm(vo.getDataUscita()));
		view.setDenominazioneBreve(vo.getDenominazioneBreve());
		view.setSiglaFormaGiuridica(vo.getSiglaFormaGiuridica());
		view.setFormaGiuridica(vo.getFormaGiuridica());
		view.setIdNazione(vo.getNazione().getId());
		view.setSiglaStatoProvincia(vo.getSiglaStatoProvincia());
		view.setSiglaProvincia(vo.getSiglaProvincia());
		view.setItaliaEstero(vo.getItaliaEstero());
		view.setUeExstraue(vo.getUeExstraue());
		view.setSedeLegale(vo.getSedeLegale());
		view.setIndirizzo(vo.getIndirizzo());
		view.setCap(vo.getCap());
		view.setCodiceFiscale(vo.getCodiceFiscale());
		view.setPartitaIva(vo.getPartitaIva());
		view.setDataRea(convertDateForForm(vo.getDataRea()));
		view.setNumeroRea(vo.getNumeroRea());
		view.setComuneRea(vo.getComuneRea());
		view.setSiglaProvinciaRea(vo.getSiglaProvinciaRea());
		view.setProvinciaRea(vo.getProvinciaRea());
		view.setQuotazione(vo.getQuotazione());
		view.setInLiquidazione(vo.getInLiquidazione());
		view.setModelloDiGovernance(vo.getModelloDiGovernance());
		view.setnComponentiCDA(vo.getnComponentiCDA());
		view.setnComponentiCollegioSindacale(vo.getnComponentiCollegioSindacale());
		view.setnComponentiOdv(vo.getnComponentiOdv());
		view.setCodiceNazione(vo.getCodiceNazione());
		view.setSocietaDiRevisione(vo.getSocietaDiRevisione());
		view.setDataIncarico(convertDateForForm(vo.getDataIncarico()));
		
		if(vo.getRSocietaAffaris() != null ){
			Collection<RSocietaAffari> societaAffaris = vo.getRSocietaAffaris();
			
			List<RSocietaAffariView> listaRSocietaAffari = new ArrayList<RSocietaAffariView>();
			RSocietaAffariView[] array = new RSocietaAffariView[societaAffaris.size()];
			for( RSocietaAffari rSocietaAffari : societaAffaris ){
				
				RSocietaAffariView rsaview = new RSocietaAffariView(); 
				rsaview.setVo(rSocietaAffari);  
				rsaview.setIdSocietaSocio(rSocietaAffari.getSocietaSocio().getId() + "");
				rsaview.setDescrizioneSocietaSocio(rSocietaAffari.getSocietaSocio().getDenominazione());
				rsaview.setPercentualeSocio(rSocietaAffari.getPercentualeSocio() + "");
				listaRSocietaAffari.add(rsaview);
			}  
			listaRSocietaAffari.toArray(array);
			view.setSociAggiunti(array);
		}
		
		view.setPercentualeTerzi(vo.getPercentualeTerzi());
		view.setDataCostituzione(convertDateForForm(vo.getDataCostituzione()));
		caricaDocumentiGenericiFilenet(view,vo);

	}
	
	@RequestMapping(value= "/affariSocietari/modifica",method = RequestMethod.GET)
	public String modificaAffariSocietari(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		AffariSocietariView view = new AffariSocietariView();
		
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {
			AffariSocietariView affariSocietariSalvato = (AffariSocietariView) affariSocietariService.leggi(id);
			if (affariSocietariSalvato != null && affariSocietariSalvato.getVo() != null) {
				popolaFormDaVo(view, affariSocietariSalvato.getVo());
				
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
	
	@RequestMapping(value= "/affariSocietari/visualizza",method = RequestMethod.GET)
	public String visualizzaAffariSocietari(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		request.setAttribute("disableInVis", "true");
		return modificaAffariSocietari(id,model,locale,request);
	}


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new AffariSocietariValidator());
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(value = "/affariSocietari/uploadAllegatoGenerico", method = RequestMethod.POST)
	public String uploadAllegatoGenerico( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) AffariSocietariView view ) {  
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {   
			Long affariSocietariId = NumberUtils.toLong(request.getParameter("idAffariSocietari")); 

			if( affariSocietariId != null  ){
		    
		    File fileTmp = File.createTempFile("allegatoGenerico", "___" + file.getOriginalFilename() );
		    FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
		    DocumentoView documentoView = new DocumentoView();
		    
		    documentoView.setUuid("" +( view.getAllegato() == null || view.getAllegato().size() == 0 ?  1 : view.getAllegato().size()+1));
		    documentoView.setFile(fileTmp);
		    documentoView.setNomeFile(file.getOriginalFilename());
		    documentoView.setContentType(file.getContentType());
		    documentoView.setNuovoDocumento(true);
		    List<DocumentoView> allegatiGenerici = view.getAllegato()==null?new ArrayList<DocumentoView>():view.getAllegato();
		    allegatiGenerici.add(documentoView);
		    view.setAllegato(allegatiGenerici);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "affariSocietari/allegatiGenerici";
	}
	
	
	@RequestMapping(value = "/affariSocietari/rimuoviAllegatoGenerico", method = RequestMethod.POST)
	public String rimuoviAllegatoGenerico( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) AffariSocietariView view ) {  
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {   
			String uuid = request.getParameter("uuid"); 
		    if( uuid == null  ){
				throw new RuntimeException("uuid non pu� essere null");
			}
		     
		    List<DocumentoView> allegatiGenericiOld = view.getAllegato();
		    List<DocumentoView> allegatiGenerici = new ArrayList<DocumentoView>();
		    allegatiGenerici.addAll(allegatiGenericiOld);
		    List<String> allegatiDaRimuovere = new ArrayList<>();
		    if( allegatiGenericiOld != null){
		    	for(DocumentoView docView: allegatiGenericiOld){
		    		if( docView.getUuid().contains(uuid) ){
		    			if( docView.getUuid().length() >= 30 ){
							allegatiDaRimuovere.add(uuid);
						}
		    			allegatiGenerici.remove(docView);
		    			break;
		    		}
		    	}
		    }
		    view.setAllegato(allegatiGenerici);
		    view.setAllegatoDaRimuovereUuid(allegatiDaRimuovere.get(0));
		    
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "affariSocietari/allegatiGenerici";
	}
	
	private void caricaDocumentiGenericiFilenet(AffariSocietariView view, AffariSocietari vo) {

		//@@DDS DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		String parentFolderName = FileNetUtil.getAffariSocietariParentFolder(vo.getDataCostituzione());
		String affariSocietariFolderName = vo.getId() + "-AFFARI_SOCIETARI";
		affariSocietariFolderName = parentFolderName + affariSocietariFolderName + "/";
		Folder affariSocietariFolder = null;
		try {
			//@@DDS affariSocietariFolder = documentaleDAO.leggiCartella(affariSocietariFolderName);
			affariSocietariFolder = documentaleDdsDAO.leggiCartella(affariSocietariFolderName);
			if (affariSocietariFolder != null) {
			/*@@DDS inizio commento
			DocumentSet documenti = affariSocietariFolder.get_ContainedDocuments();

			List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
			if (documenti != null) {
				PageIterator it = documenti.pageIterator();
				while (it.nextPage()) {
					EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
					for (EngineObjectImpl objDocumento : documentiArray) {
						DocumentImpl documento = (DocumentImpl) objDocumento;
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.get_Name());
						docView.setUuid(documento.get_Id().toString());
						listaDocumenti.add(docView);
					}
				}
				view.setAllegato(listaDocumenti);

			}
*/
				List<Document> documenti =documentaleDdsDAO.leggiDocumentiCartella(affariSocietariFolderName);

				List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
				if (documenti != null) {

						for (Document documento:documenti) {

							DocumentoView docView = new DocumentoView();
							docView.setNomeFile(documento.getContents().get(0).getContentsName());
							docView.setUuid(documento.getId());
							listaDocumenti.add(docView);
						}

					view.setAllegato(listaDocumenti);

				}
			}
		} catch (Throwable e) {
			// potrebbe essere corretto che la cartella non esista poiche' in inserimento gli allegati non sono obbligatori
			e.printStackTrace();
		}


	}


	public String aggiungiDemoninazionePercentualeSocio(AffariSocietariView affariSocietariView, BindingResult bindingResult, Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {

		String paginaDiRitorno = "";
		
		try {
			
			String idAffariSocieta = affariSocietariView.getrSocietaAffariIdSocieta();
			String percentualeAffariSocieta =  affariSocietariView.getrSocietaAffariPercentuale();
			String descrizioneAffariSocieta =  affariSocietariView.getrSocietaAffariDescrizione();
			String fromCreation = affariSocietariView.getFromCreation();

			
			if(fromCreation.equals("true")){
				paginaDiRitorno = PAGINA_CREAZIONE;
			}else{
				paginaDiRitorno = PAGINA_FORM_PATH;
			}
			
			RSocietaAffariView rSocietaAffariView = new RSocietaAffariView();


			rSocietaAffariView.setIdSocietaSocio(idAffariSocieta);
			rSocietaAffariView.setPercentualeSocio(percentualeAffariSocieta);
			rSocietaAffariView.setDescrizioneSocietaSocio(descrizioneAffariSocieta);

			RSocietaAffariView[] array = affariSocietariView.getSociAggiunti();
			if( array != null && array.length > 0 ){
				List<RSocietaAffariView> lista = new ArrayList<RSocietaAffariView>();
				lista.addAll(Arrays.asList(array));
				lista.add(rSocietaAffariView);
				array = new RSocietaAffariView[lista.size()];
				lista.toArray(array);
			}else{
				array = new RSocietaAffariView[]{rSocietaAffariView};
			}

			affariSocietariView.setSociAggiunti(array);

			Long percentualeTerzi = calcolaPercentualeTerzi(affariSocietariView);
			affariSocietariView.setPercentualeTerzi(percentualeTerzi);
			
		} catch (Throwable e) {

			e.printStackTrace();
		}
		request.setAttribute("anchorName", "anchorSoci");

			return paginaDiRitorno;
	}
	
	public String rimuoviDemoninazionePercentualeSocio(AffariSocietariView affariSocietariView, BindingResult bindingResult, Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
	 
			String idDaRimuovere = affariSocietariView.getIdDaRimuovere();
			String paginaDiRitorno = "";
			String fromCreation = affariSocietariView.getFromCreation();

			if(fromCreation.equals("true")){
				paginaDiRitorno = PAGINA_CREAZIONE;
			}else{
				paginaDiRitorno = PAGINA_FORM_PATH;
			}

			
			try {
				if (affariSocietariView.getSociAggiunti() != null && affariSocietariView.getSociAggiunti().length > 0
						&& idDaRimuovere != null) { 
					RSocietaAffariView[] array = affariSocietariView.getSociAggiunti();
					List<RSocietaAffariView> lista = new ArrayList<RSocietaAffariView>();
					
					for (RSocietaAffariView view : array) {
						
						if(!view.getIdSocietaSocio().equals(idDaRimuovere))
							lista.add(view);
					}

					array = new RSocietaAffariView[lista.size()];
					lista.toArray(array);
					affariSocietariView.setSociAggiunti(array);
					
					Long percentualeTerzi = calcolaPercentualeTerzi(affariSocietariView);
					affariSocietariView.setPercentualeTerzi(percentualeTerzi);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			request.setAttribute("anchorName", "anchorSoci");
			return paginaDiRitorno;
	}
	
	
	private Long calcolaPercentualeTerzi(AffariSocietariView affariSocietariView){
		Long result = new Long(0);
		
		if(affariSocietariView != null){
			
			if(affariSocietariView.getSociAggiunti() != null){
				
				if(affariSocietariView.getSociAggiunti().length > 0){
					
					long percentualeOccupata = 0;
					
					for(int i = 0; i< affariSocietariView.getSociAggiunti().length; i++){
						
						if(affariSocietariView.getSociAggiunti()[i].getPercentualeSocio() != null)
							percentualeOccupata += Long.parseLong(affariSocietariView.getSociAggiunti()[i].getPercentualeSocio());
					}
					
					result = 100 - percentualeOccupata;
				}
				else{
					result = new Long(0);
				}
			}
			else{
				result = new Long(0);
			}
		}
		
		return result;
	}
	
	
	


	
}



