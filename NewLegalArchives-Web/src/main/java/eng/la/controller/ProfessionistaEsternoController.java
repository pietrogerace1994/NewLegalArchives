package eng.la.controller;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.FetchMode;
import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

//@@DDS import com.filenet.api.core.Document;
import it.snam.ned.libs.dds.dtos.v2.Document;

import eng.la.business.CategoriaContestService;
import eng.la.business.NazioneService;
import eng.la.business.ProfessionistaEsternoService;
import eng.la.business.SpecializzazioneService;
import eng.la.business.StatoEsitoValutazioneProfService;
import eng.la.business.StatoProfessionistaService;
import eng.la.business.StudioLegaleService;
import eng.la.business.TipoProfessionistaService;
import eng.la.business.UtenteService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.business.websocket.WebSocketPublisher;
import eng.la.business.workflow.ProfessionistaEsternoWfService;
import eng.la.model.CategoriaContest;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.ProfessionistaEsternoWf;
import eng.la.model.RProfEstSpec;
import eng.la.model.RProfessionistaNazione;
import eng.la.model.StatoEsitoValutazioneProf;
import eng.la.model.StatoProfessionista;
import eng.la.model.StudioLegale;
import eng.la.model.TipoProfessionista;
import eng.la.model.rest.DocumentoRest;
import eng.la.model.rest.Event;
import eng.la.model.rest.ProfEstRest;
import eng.la.model.rest.StepWFRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.CategoriaContestView;
import eng.la.model.view.GiudizioProfEstView;
import eng.la.model.view.NazioneView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.RProfDocumentoView;
import eng.la.model.view.RProfEstSpecView;
import eng.la.model.view.RProfessionistaNazioneView;
import eng.la.model.view.SpecializzazioneView;
import eng.la.model.view.StatoEsitoValutazioneProfView;
import eng.la.model.view.StatoProfessionistaView;
import eng.la.model.view.StudioLegaleView;
import eng.la.model.view.TipoProfessionistaView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.presentation.validator.ProfessionistaEsternoValidator;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("professionistaEsternoController")
@SessionAttributes("professionistaEsternoView")
public class ProfessionistaEsternoController extends BaseController {

	private static final String MODEL_VIEW_NOME = "professionistaEsternoView";
	private static final String PAGINA_FORM_PATH = "professionistaEsterno/formProfessionistaEsterno";
	private static final String PAGINA_FORM_READ_PATH = "professionistaEsterno/formReadProfEsterno";
	private static final String PAGINA_FORM_EDIT_PATH = "professionistaEsterno/formEditProfEsterno";

	@Autowired
	private ProfessionistaEsternoService professionistaEsternoService;
	
	@Autowired
	private TipoProfessionistaService tipoProfessionistaService;
	
	@Autowired
	private StatoEsitoValutazioneProfService statoEsitoValutazioneProfService;
	
	@Autowired
	private StatoProfessionistaService statoProfessionistaService;
	
	@Autowired
	private StudioLegaleService studioLegaleService;
	
	@Autowired
	private NazioneService nazioneService;
	
	@Autowired
	private SpecializzazioneService specializzazioneService;
	
	
	@Autowired
	private ProfessionistaEsternoWfService professionistaEsternoWfService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private CategoriaContestService categoriaContestService;
	
	
	@Autowired
	private EmailNotificationService emailNotificationService;

	public void setEmailNotificationService (EmailNotificationService emailNotificationService) {
		this.emailNotificationService = emailNotificationService;
	}
	
	@RequestMapping("/professionistaEsterno/crea")
	public String creaProfessionistaEsterno(HttpServletRequest request, Model model, Locale locale) {
		ProfessionistaEsternoView professionistaEsternoView = new ProfessionistaEsternoView();
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			super.caricaListe(professionistaEsternoView, locale);
			this.caricaListaGiudizi(professionistaEsternoView);
			this.isVendorManagement(professionistaEsternoView);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		professionistaEsternoView.setVo(new ProfessionistaEsterno());
		model.addAttribute(MODEL_VIEW_NOME, professionistaEsternoView);
		return PAGINA_FORM_PATH;
	}
	
	@RequestMapping("/professionistaEsterno/visualizzaProfEst")
	public String visualizzaProfEst(HttpServletRequest request, Model model, Locale locale) {
		ProfessionistaEsternoView professionistaEsternoView = new ProfessionistaEsternoView();

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {
			List<ProfessionistaEsternoView> listaProfEst = professionistaEsternoService.leggi(false);
			professionistaEsternoView.setListaProfEst(listaProfEst);
			professionistaEsternoView.setLocale(locale);
			this.caricaListaGiudizi(professionistaEsternoView);
			this.isVendorManagement(professionistaEsternoView);
			convertAndSetProfessionistaEsternoToJsonArray(professionistaEsternoView);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		professionistaEsternoView.setVo(new ProfessionistaEsterno());
		model.addAttribute(MODEL_VIEW_NOME, professionistaEsternoView);
		return PAGINA_FORM_READ_PATH;
	}
	
	@RequestMapping(value = "/professionistaEsterno/caricaDescrizioniProfEst", method = RequestMethod.POST)
	public @ResponseBody ProfEstRest caricaDescrizioniProfEst(@RequestParam("id") String id, Locale locale) {
		ProfEstRest profEstRest = new ProfEstRest();


		try {
			ProfessionistaEsternoView view = professionistaEsternoService.leggi(NumberUtils.toLong(id));
			profEstRest.setId(view.getVo().getId());
			profEstRest.setNome(view.getVo().getNome());
			profEstRest.setCognome(view.getVo().getCognome());
			profEstRest.setCodiceFiscale(view.getVo().getCodiceFiscale());
			profEstRest.setFax(view.getVo().getFax());
			profEstRest.setTelefono(view.getVo().getTelefono());
			profEstRest.setMotivazioneRichiesta(view.getVo().getMotivazioneRichiesta());
			
			String emails = view.getVo().getEmail();
			if(emails!= null && !emails.equalsIgnoreCase("")){
				String[] split = emails.split(";");
				profEstRest.setEmail(split);
			}
			
			StatoEsitoValutazioneProf statoEsitoValutazioneProf = view.getVo().getStatoEsitoValutazioneProf();
			if(statoEsitoValutazioneProf!=null){
				StatoEsitoValutazioneProfView statoEsitoValutazioneProfView = 
						statoEsitoValutazioneProfService.leggi(statoEsitoValutazioneProf.getCodGruppoLingua(), locale);
				profEstRest.setStatoEsitoValutazioneProf(statoEsitoValutazioneProfView.getVo().getDescrizione());
			}
			
			StatoProfessionista statoProfessionista = view.getVo().getStatoProfessionista();
			if(statoProfessionista!=null){
				StatoProfessionistaView statoProfessionistaView = 
						statoProfessionistaService.leggi(statoProfessionista.getCodGruppoLingua(), locale);
				profEstRest.setStatoProfessionista(statoProfessionistaView.getVo().getDescrizione());
			}
			
			TipoProfessionista tipoProfessionista = view.getVo().getTipoProfessionista();
			if(tipoProfessionista!=null){
				TipoProfessionistaView tipoProfessionistaView = tipoProfessionistaService.leggi(tipoProfessionista.getCodGruppoLingua(), locale);
				profEstRest.setTipoProfessionista(tipoProfessionistaView.getVo().getDescrizione());
			}
			
			Integer giudizio = view.getVo().getGiudizio();
			if(giudizio != null){
				profEstRest.setGiudizio(giudizio);
			}
			
			CategoriaContest categoriaContest = view.getVo().getCategoriaContest();
			if(categoriaContest!=null){
				CategoriaContestView categoriaContestView = categoriaContestService.leggi(categoriaContest.getCodGruppoLingua(), locale);
				profEstRest.setCategoriaContest(categoriaContestView.getVo().getDescrizione());
			}
			
			StudioLegale studioLegale = view.getVo().getStudioLegale();
			if(studioLegale!=null){
				profEstRest.setStudioLegale(studioLegale.getDenominazione()+" "+studioLegale.getIndirizzo()+" "+studioLegale.getCap()+" "+studioLegale.getCitta());
				
				profEstRest.setStudioLegaleDenominazione(studioLegale.getDenominazione());
				profEstRest.setStudioLegaleIndirizzo(studioLegale.getIndirizzo());
				profEstRest.setStudioLegaleCap(studioLegale.getCap());
				profEstRest.setStudioLegaleCitta(studioLegale.getCitta());
				profEstRest.setStudioLegaleEmail(studioLegale.getEmail());
				profEstRest.setStudioLegaleTelefono(studioLegale.getTelefono());
				profEstRest.setStudioLegaleFax(studioLegale.getFax());
				profEstRest.setStudioLegaleNazioneCode(studioLegale.getNazione().getDescrizione());
				profEstRest.setStudioLegaleCodiceSap(studioLegale.getCodiceSap());
				profEstRest.setStudioLegalePartitaIva(studioLegale.getPartitaIva());

			}
			
			List<String> listNazioni = new ArrayList<String>();
			List<RProfessionistaNazioneView> leggiProfNazionebyId = professionistaEsternoService.leggiProfNazionebyId(NumberUtils.toLong(id));
			for (RProfessionistaNazioneView rView : leggiProfNazionebyId) {
				String codGruppoLingua = rView.getVo().getNazione().getCodGruppoLingua();
				NazioneView nazioneView = nazioneService.leggi(codGruppoLingua, locale, true);
				listNazioni.add(nazioneView.getVo().getDescrizione());
			}
			profEstRest.setNazioneDesc(listNazioni);
			
			List<String> listSpec = new ArrayList<String>();
			List<RProfEstSpecView> leggiProfSpecbyId = professionistaEsternoService.leggiProfSpecbyId(NumberUtils.toLong(id));
			for (RProfEstSpecView rView : leggiProfSpecbyId) {
				String codGruppoLingua = rView.getVo().getSpecializzazione().getCodGruppoLingua();
				SpecializzazioneView specializzazioneView = specializzazioneService.leggi(codGruppoLingua, locale);
				listSpec.add(specializzazioneView.getVo().getDescrizione());
			}
			profEstRest.setSpecDesc(listSpec);
			
			//List<String> listUuid = new ArrayList<String>();
			List<DocumentoRest> listdoc = new ArrayList<DocumentoRest>();
			List<RProfDocumentoView> leggiProfDocbyId = professionistaEsternoService.leggiProfDocbyId(NumberUtils.toLong(id));
			for (RProfDocumentoView rView : leggiProfDocbyId) {
				DocumentoRest doc = new DocumentoRest();
				doc.setUuid(rView.getVo().getDocumento().getUuid());
				doc.setNomefile(rView.getVo().getDocumento().getNomeFile());
				listdoc.add(doc);
				//listUuid.add(rView.getVo().getDocumento().getUuid());
			}
			//profEstRest.setFileUuid(listUuid);
			profEstRest.setDoc(listdoc);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return profEstRest;
	}
	
	@RequestMapping(value = "/professionistaEsterno/caricaDescrizioniEditProfEst", method = RequestMethod.POST)
	public @ResponseBody ProfEstRest caricaDescrizioniEditProfEst(@RequestParam("id") String id, Locale locale) {
		ProfEstRest profEstRest = new ProfEstRest();


		try {
			ProfessionistaEsternoView view = professionistaEsternoService.leggi(NumberUtils.toLong(id), FetchMode.JOIN);
			profEstRest.setId(view.getVo().getId());
			profEstRest.setNome(view.getVo().getNome());
			profEstRest.setCognome(view.getVo().getCognome());
			profEstRest.setCodiceFiscale(view.getVo().getCodiceFiscale());
			profEstRest.setFax(view.getVo().getFax());
			profEstRest.setTelefono(view.getVo().getTelefono());
			profEstRest.setMotivazioneRichiesta(view.getVo().getMotivazioneRichiesta());
			
			String emails = view.getVo().getEmail();
			if(emails!= null && !emails.equalsIgnoreCase("")){
				String[] split = emails.split(";");
				profEstRest.setEmail(split);
			}
			
			StatoEsitoValutazioneProf statoEsitoValutazioneProf = view.getVo().getStatoEsitoValutazioneProf();
			if(statoEsitoValutazioneProf!=null){
				StatoEsitoValutazioneProfView statoEsitoValutazioneProfView = 
						statoEsitoValutazioneProfService.leggi(statoEsitoValutazioneProf.getCodGruppoLingua(), locale);
				profEstRest.setStatoEsitoValutazioneProf(statoEsitoValutazioneProfView.getVo().getCodGruppoLingua());
			}
			
			StatoProfessionista statoProfessionista = view.getVo().getStatoProfessionista();
			if(statoProfessionista!=null){
				StatoProfessionistaView statoProfessionistaView = 
						statoProfessionistaService.leggi(statoProfessionista.getCodGruppoLingua(), locale);
				profEstRest.setStatoProfessionista(statoProfessionistaView.getVo().getCodGruppoLingua());
			}
			
			TipoProfessionista tipoProfessionista = view.getVo().getTipoProfessionista();
			if(tipoProfessionista!=null){
				TipoProfessionistaView tipoProfessionistaView = tipoProfessionistaService.leggi(tipoProfessionista.getCodGruppoLingua(), locale);
				profEstRest.setTipoProfessionista(tipoProfessionistaView.getVo().getCodGruppoLingua());
			}
			
			Integer giudizio = view.getVo().getGiudizio();
			if(giudizio!=null){
				profEstRest.setGiudizio(giudizio);
			}
			
			CategoriaContest categoriaContest = view.getVo().getCategoriaContest();
			if(categoriaContest!=null){
				CategoriaContestView categoriaContestView = categoriaContestService.leggi(categoriaContest.getCodGruppoLingua(), locale);
				profEstRest.setCategoriaContest(categoriaContestView.getVo().getCodGruppoLingua());
			}
			
			StudioLegale studioLegale = view.getVo().getStudioLegale();
			if(studioLegale!=null){
				profEstRest.setStudioLegale(String.valueOf(studioLegale.getId()));
				
				profEstRest.setStudioLegaleDenominazione(studioLegale.getDenominazione());
				profEstRest.setStudioLegaleIndirizzo(studioLegale.getIndirizzo());
				profEstRest.setStudioLegaleCap(studioLegale.getCap());
				profEstRest.setStudioLegaleCitta(studioLegale.getCitta());
				profEstRest.setStudioLegaleEmail(studioLegale.getEmail());
				profEstRest.setStudioLegaleTelefono(studioLegale.getTelefono());
				profEstRest.setStudioLegaleFax(studioLegale.getFax());
				profEstRest.setStudioLegaleNazioneCode(studioLegale.getNazione().getCodGruppoLingua());
				profEstRest.setStudioLegaleCodiceSap(studioLegale.getCodiceSap());
				profEstRest.setStudioLegalePartitaIva(studioLegale.getPartitaIva());
				
				
			}
			
			List<String> listNazioni = new ArrayList<String>();
			List<RProfessionistaNazioneView> leggiProfNazionebyId = professionistaEsternoService.leggiProfNazionebyId(NumberUtils.toLong(id));
			for (RProfessionistaNazioneView rView : leggiProfNazionebyId) {
				String codGruppoLingua = rView.getVo().getNazione().getCodGruppoLingua();
				NazioneView nazioneView = nazioneService.leggi(codGruppoLingua, locale, true);
				listNazioni.add(nazioneView.getVo().getCodGruppoLingua());
			}
			profEstRest.setNazioneDesc(listNazioni);
			
			List<String> listSpec = new ArrayList<String>();
			List<RProfEstSpecView> leggiProfSpecbyId = professionistaEsternoService.leggiProfSpecbyId(NumberUtils.toLong(id));
			for (RProfEstSpecView rView : leggiProfSpecbyId) {
				String codGruppoLingua = rView.getVo().getSpecializzazione().getCodGruppoLingua();
				SpecializzazioneView specializzazioneView = specializzazioneService.leggi(codGruppoLingua, locale);
				listSpec.add(specializzazioneView.getVo().getCodGruppoLingua());
			}
			profEstRest.setSpecDesc(listSpec);
			
			List<DocumentoRest> listdoc = new ArrayList<DocumentoRest>();
			List<RProfDocumentoView> leggiProfDocbyId = professionistaEsternoService.leggiProfDocbyId(NumberUtils.toLong(id));
			for (RProfDocumentoView rView : leggiProfDocbyId) {
				DocumentoRest doc = new DocumentoRest();
				doc.setUuid(rView.getVo().getDocumento().getUuid());
				doc.setNomefile(rView.getVo().getDocumento().getNomeFile());
				listdoc.add(doc);
				//listUuid.add(rView.getVo().getDocumento().getUuid());
			}
			//profEstRest.setFileUuid(listUuid);
			profEstRest.setDoc(listdoc);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return profEstRest;
	}

	@RequestMapping(value = "/professionistaEsterno/valorizzaDettaglioStudio", method = RequestMethod.POST)
	public @ResponseBody ProfEstRest valorizzaDettaglioStudio(@RequestParam("id") String id, Locale locale) {
		ProfEstRest profEstRest = new ProfEstRest();
		try {
			
			StudioLegaleView studioLegale = studioLegaleService.leggi(new Long(id));
			if(studioLegale.getVo()!=null){
				profEstRest.setStudioLegale(String.valueOf(studioLegale.getVo().getId()));
				
				profEstRest.setStudioLegaleDenominazione(studioLegale.getVo().getDenominazione());
				profEstRest.setStudioLegaleIndirizzo(studioLegale.getVo().getIndirizzo());
				profEstRest.setStudioLegaleCap(studioLegale.getVo().getCap());
				profEstRest.setStudioLegaleCitta(studioLegale.getVo().getCitta());
				profEstRest.setStudioLegaleEmail(studioLegale.getVo().getEmail());
				profEstRest.setStudioLegaleTelefono(studioLegale.getVo().getTelefono());
				profEstRest.setStudioLegaleFax(studioLegale.getVo().getFax());
				profEstRest.setStudioLegaleNazioneCode(studioLegale.getVo().getNazione().getCodGruppoLingua());
				profEstRest.setStudioLegaleCodiceSap(studioLegale.getVo().getCodiceSap());
				profEstRest.setStudioLegalePartitaIva(studioLegale.getVo().getPartitaIva());
				
				
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return profEstRest;
	}

	
    @RequestMapping(value = "/professionistaEsterno/download" , method = RequestMethod.GET)
    public String downloadDoc(@RequestParam("uuid") String uuid , HttpServletRequest request,HttpServletResponse response, Model model, Locale locale) throws Throwable {
    
    	// engsecurity VA
    			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
    			htmlActionSupport.checkCSRFToken(request);
    	        //removeCSRFToken(request); 
    	
    	if(uuid!=null && !uuid.equalsIgnoreCase("")){
    		/*@@DDS
    		Document document = professionistaEsternoService.leggiDocumentoUUID(uuid);
    		String contentType = document.get_MimeType();
    		String name = document.get_Name();
    		*/
			Document document = professionistaEsternoService.leggiDocumentoUUID(uuid);
			String contentType = document.getContents().get(0).getContentsMimeType();
			String name = document.getContents().get(0).getContentsName();
    		
    		ByteArrayInputStream is = null;
    		OutputStream os = null;
    		
    		
    		try{
    			//stream = document.accessContentStream(0); 
    			//outputStream = new ByteArrayOutputStream();     
    			//IOUtils.copy(stream, outputStream); 
    			
    			
    			byte[] bytes = professionistaEsternoService.leggiContenutoDocumentoUUID(uuid);
    			
    			response.setContentLength(bytes.length);
    			response.setContentType(contentType);
    			response.setHeader("Content-Disposition", "attachment;filename=" + name);
    			response.setHeader("Cache-control", "");
    			is = new ByteArrayInputStream(bytes);
    			os = response.getOutputStream();
    			IOUtils.copy(is, os);
    			
    		} catch (Throwable e) {
    			e.printStackTrace();
    		} finally {
    			IOUtils.closeQuietly(is);
    			IOUtils.closeQuietly(os);
    		}
    	}
		return null;
    }
	
	@RequestMapping(value = "/professionistaEsterno/salva", method = RequestMethod.POST)
	public String salvaProfessionistaEsterno(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated ProfessionistaEsternoView professionistaEsternoView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
				String token=request.getParameter("CSRFToken");
		
		String redirect = "redirect:crea.action?CSRFToken="+token;
		try {
			if (professionistaEsternoView.getOp() != null && !professionistaEsternoView.getOp().equals("salvaProfessionistaEsterno")) {
				String ritorno = invocaMetodoDaReflection(professionistaEsternoView, bindingResult, locale, model, request,
						response, this);
				return ritorno == null ? PAGINA_FORM_READ_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				if(professionistaEsternoView.isDeleteMode() || professionistaEsternoView.isEditMode()){
					return PAGINA_FORM_EDIT_PATH;
				} else {
					//checkMail(professionistaEsternoView);
					return PAGINA_FORM_PATH;
				}
			}
			
			preparaPerSalvataggio(professionistaEsternoView, bindingResult);  
			
			if (bindingResult.hasErrors()) {
				if(professionistaEsternoView.isDeleteMode() || professionistaEsternoView.isEditMode()){
					return PAGINA_FORM_EDIT_PATH;
				} else {
					return PAGINA_FORM_PATH;
				}
			}
			
			if(professionistaEsternoView.isDeleteMode()){
				professionistaEsternoService.cancella(professionistaEsternoView.getProfessionistaEsternoId().longValue());
				redirect = "redirect:gestioneProfEst.action?CSRFToken="+token;
			} else if (professionistaEsternoView.isEditMode()){
				professionistaEsternoService.modifica(professionistaEsternoView);
				redirect = "redirect:gestioneProfEst.action?CSRFToken="+token;
			} else {
				ProfessionistaEsternoView newProfessionistaView = professionistaEsternoService.inserisci(professionistaEsternoView);
				if(newProfessionistaView != null){
					
					if(newProfessionistaView.getVo().getStatoProfessionista().getCodGruppoLingua().equalsIgnoreCase("B")){
						
						//Avvio il workflow
						UtenteView utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	
						String userIdUtil="";
						userIdUtil=utenteView.getVo().getUseridUtil();
						boolean esito= professionistaEsternoWfService.avviaWorkflow(newProfessionistaView.getVo().getId(), userIdUtil);
						
						if(esito){
							
							UtenteView assegnatario = utenteService.leggiAssegnatarioWfProfessionistaEsterno(newProfessionistaView.getVo().getId());
							if(assegnatario != null){
								//invio la notifica
								//-------CONTROLLA UTENTE ASSENTE--------
								assegnatario =checkAssegnatarioPresente(assegnatario, newProfessionistaView);
								StepWFRest stepSuccessivo = new StepWFRest();
								stepSuccessivo.setId(0);
								Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
								WebSocketPublisher.getInstance().publishEvent(event); 
							}
							//invio la e-mail
							try{
								emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.PROFESSIONISTA_ESTERNO, newProfessionistaView.getVo().getId(), locale.getLanguage().toUpperCase(), userIdUtil);
							}
							catch (Exception e) { 
								System.out.println("Errore in invio e-mail"+ e);
							}
						}
					}
				}
			}
			
			model.addAttribute("successMessage", "messaggio.operazione.ok");
			return redirect;
		} catch (Throwable e) {
			bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			if(professionistaEsternoView.isDeleteMode() || professionistaEsternoView.isEditMode()){
				return PAGINA_FORM_EDIT_PATH;
			} else {
				return PAGINA_FORM_PATH;
			}
		}
	}
	
	//Controlla se l'assegnatatio � Assente -  Ritorna un assegnatario Presente se disponibile
		private UtenteView checkAssegnatarioPresente(UtenteView assegnatario,ProfessionistaEsternoView newProfessionistaView) throws Throwable{
		
			
			boolean esito=false;
			 
			if(assegnatario.getVo()!=null && assegnatario.getVo().getAssente().equals("T")){
			ProfessionistaEsternoWf wf = professionistaEsternoWfService.leggiWorkflowInCorsoNotView(newProfessionistaView.getVo().getId());
			if(wf!=null)
			esito=professionistaEsternoWfService.avanzaWorkflow(wf.getId(), assegnatario.getVo().getUseridUtil());
			if(esito){
				UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfProfessionistaEsterno(wf.getProfessionistaEsterno().getId());
				if(assegnatarioNew==null) return assegnatario;
				if(assegnatarioNew.getVo()!=null && assegnatarioNew.getVo().getAssente().equals("T")){
					if(!utenteService.isAssegnatarioManualeCorrenteStandard(wf.getId(),CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO)){	
						return	checkAssegnatarioPresente(assegnatarioNew,newProfessionistaView);	
					}
				}else{ return assegnatarioNew;}

			}//esito
			 
			}
		 
		 
			return assegnatario;
		}
	
	private void preparaPerSalvataggio(ProfessionistaEsternoView professionistaEsternoView, BindingResult bindingResult) throws Throwable {
		ProfessionistaEsterno professionistaEsterno = new ProfessionistaEsterno();
		Locale linguaItaliana = Locale.ITALIAN;
		
		professionistaEsterno.setNome(professionistaEsternoView.getNome() != null ? professionistaEsternoView.getNome().trim() : "" );
		professionistaEsterno.setCognome(professionistaEsternoView.getCognome() != null ? professionistaEsternoView.getCognome().trim() : "" );
		professionistaEsterno.setCodiceFiscale(professionistaEsternoView.getCodiceFiscale() != null ? professionistaEsternoView.getCodiceFiscale().trim() : "");
		String emails = "";
		for (String email : professionistaEsternoView.getEmail()) {
			if(email!=null){
				emails += email + ";";
			}
		}
		professionistaEsterno.setMotivazioneRichiesta(professionistaEsternoView.getMotivazioneRichiesta());

		String emailField = emails.equalsIgnoreCase("")? "" : emails.substring(0, emails.length()-1); 
		professionistaEsterno.setEmail(emailField);
		professionistaEsterno.setFax(professionistaEsternoView.getFax());
		professionistaEsterno.setTelefono(professionistaEsternoView.getTelefono());
		
		String tipoProfessionistaCode = professionistaEsternoView.getTipoProfessionistaCode();
		if(tipoProfessionistaCode!=null && !tipoProfessionistaCode.equalsIgnoreCase("")){
			TipoProfessionistaView tipoProfessionistaView = tipoProfessionistaService.leggi(tipoProfessionistaCode , linguaItaliana);
			professionistaEsterno.setTipoProfessionista(tipoProfessionistaView.getVo());
		}
		
		Integer giudizio = professionistaEsternoView.getGiudizio();
		if(giudizio!=null){
			professionistaEsterno.setGiudizio(giudizio);
		}
		
		String statoEsitoValutazioneProfCode = professionistaEsternoView.getStatoEsitoValutazioneProfCode();
		if(statoEsitoValutazioneProfCode!=null && !statoEsitoValutazioneProfCode.equalsIgnoreCase("")){
			StatoEsitoValutazioneProfView statoEsitoValutazioneProfView = statoEsitoValutazioneProfService.leggi(statoEsitoValutazioneProfCode, linguaItaliana);
			professionistaEsterno.setStatoEsitoValutazioneProf(statoEsitoValutazioneProfView.getVo());
		}
		
		String categoriaContestCode = professionistaEsternoView.getCategoriaContestCode();
		if(categoriaContestCode!=null && !categoriaContestCode.equalsIgnoreCase("")){
			CategoriaContestView categoriaContestView = categoriaContestService.leggi(categoriaContestCode , linguaItaliana);
			professionistaEsterno.setCategoriaContest(categoriaContestView.getVo());
		}
		
		//In fase di inserimento lo stato � sempre AUTORIZZATO
		//MS FIX 40
		StatoProfessionistaView statoProfessionistaView=null;
		if(professionistaEsternoView.isEditMode()){
			
			if(professionistaEsternoView.getProfessionistaEsternoId() != null){
				long idProfessionista = professionistaEsternoView.getProfessionistaEsternoId().longValue();
				//recupero il professionista esterno correlato
				ProfessionistaEsternoView professionistaEsternoViewDB = professionistaEsternoService.leggi(idProfessionista);
				
				if(professionistaEsternoViewDB != null){
					
					if(professionistaEsternoViewDB.getVo() != null){
						
						if(professionistaEsternoViewDB.getVo().getStatoProfessionista() != null){
							
							statoProfessionistaView = new StatoProfessionistaView();
							statoProfessionistaView.setVo(professionistaEsternoViewDB.getVo().getStatoProfessionista());
						}
					}
				}
			}
		}
		if(professionistaEsternoView.isInsertMode()){
			
			statoProfessionistaView = statoProfessionistaService.leggi("B", linguaItaliana);

			if(professionistaEsterno.getCategoriaContest() != null){
				
				if(professionistaEsterno.getCategoriaContest().getCodGruppoLingua() != null && !professionistaEsterno.getCategoriaContest().getCodGruppoLingua().isEmpty()){
					
					if(professionistaEsterno.getCategoriaContest().getCodGruppoLingua().equalsIgnoreCase("ONLY")){
						statoProfessionistaView = statoProfessionistaService.leggi("A", linguaItaliana);
						StatoEsitoValutazioneProfView statoEsitoValutazioneProfView = statoEsitoValutazioneProfService.leggi("TEVP_1", linguaItaliana);
						professionistaEsterno.setStatoEsitoValutazioneProf(statoEsitoValutazioneProfView.getVo());
					}
				}
			}
		}
		
		if(statoProfessionistaView!=null && statoProfessionistaView.getVo()!=null)
		professionistaEsterno.setStatoProfessionista(statoProfessionistaView.getVo());
		
		String[] nazioniAggiunte = professionistaEsternoView.getNazioniAggiunte();
		if (nazioniAggiunte != null	&& nazioniAggiunte.length > 0) {
			Set<RProfessionistaNazione> listaRProfessionistaNazione = new HashSet<RProfessionistaNazione>();
			for (String nazione : nazioniAggiunte) {
				NazioneView nazioneview = nazioneService.leggi(nazione, linguaItaliana, false);
				RProfessionistaNazione professionistaNazione = new RProfessionistaNazione();
				professionistaNazione.setNazione(nazioneview.getVo());
				listaRProfessionistaNazione.add(professionistaNazione);
			}
			professionistaEsterno.setRProfessionistaNaziones(listaRProfessionistaNazione);
		}
			
		String[] specializzazioniAggiunte = professionistaEsternoView.getSpecializzazioniAggiunte();
		if (specializzazioniAggiunte != null && specializzazioniAggiunte.length > 0) {
			Set<RProfEstSpec> listaRProfEstSpec = new HashSet<RProfEstSpec>();
			for (String specializzazione : specializzazioniAggiunte) {
				SpecializzazioneView specializzazioneView = specializzazioneService.leggi(specializzazione, linguaItaliana);
				RProfEstSpec professionistaSpec = new RProfEstSpec();
				professionistaSpec.setSpecializzazione(specializzazioneView.getVo());
				listaRProfEstSpec.add(professionistaSpec);
			}
			professionistaEsterno.setRProfEstSpecs(listaRProfEstSpec);
		}
		
		
		if((professionistaEsternoView.getTipoStudioLegale()!=null && professionistaEsternoView.getTipoStudioLegale().equalsIgnoreCase("0")) ||
				professionistaEsternoView.isEditMode()){
			String studioLegaleId = professionistaEsternoView.getStudioLegaleId();
			if(studioLegaleId!=null && !studioLegaleId.equalsIgnoreCase("")){
				StudioLegaleView studioLegaleView = studioLegaleService.leggi(NumberUtils.toLong(studioLegaleId));
				
				
				

				studioLegaleView.getVo().setDenominazione(professionistaEsternoView.getStudioLegaleDenominazione());
				studioLegaleView.getVo().setIndirizzo(professionistaEsternoView.getStudioLegaleIndirizzo());
				studioLegaleView.getVo().setCap(professionistaEsternoView.getStudioLegaleCap());
				studioLegaleView.getVo().setCitta(professionistaEsternoView.getStudioLegaleCitta());
				studioLegaleView.getVo().setEmail(professionistaEsternoView.getStudioLegaleEmail());
				studioLegaleView.getVo().setTelefono(professionistaEsternoView.getStudioLegaleTelefono());
				studioLegaleView.getVo().setFax(professionistaEsternoView.getStudioLegaleFax());
				
				NazioneView nazioneView = nazioneService.leggi(professionistaEsternoView.getStudioLegaleNazioneCode(), professionistaEsternoView.getLocale(), true);
				studioLegaleView.getVo().setNazione(nazioneView.getVo());
				studioLegaleView.getVo().setCodiceSap(professionistaEsternoView.getStudioLegaleCodiceSap());
				studioLegaleView.getVo().setPartitaIva(professionistaEsternoView.getStudioLegalePartitaIva());

				professionistaEsterno.setStudioLegale(studioLegaleView.getVo());
			}
		} 

		if(professionistaEsternoView.isEditMode() || professionistaEsternoView.isDeleteMode()){
			professionistaEsterno.setId(professionistaEsternoView.getProfessionistaEsternoId().longValue());
		}
		
		professionistaEsternoView.setVo(professionistaEsterno);
		
	}
	
	
	@RequestMapping("/professionistaEsterno/rimuoviAllegatoProfEst")
	public String rimuoviAllegatoProfEst(HttpServletRequest request, Model model, Locale locale) { 
		ProfessionistaEsternoView professionistaEsternoView = null;
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try { 
			professionistaEsternoView = (ProfessionistaEsternoView) request.getSession().getAttribute("professionistaEsternoView");
			professionistaEsternoView.setFileProfEst(null);  
			model.addAttribute(MODEL_VIEW_NOME, professionistaEsternoView);
			if(professionistaEsternoView.isDeleteMode() || professionistaEsternoView.isEditMode()){
				return PAGINA_FORM_EDIT_PATH;
			} else {
				return PAGINA_FORM_PATH;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} 
		if( professionistaEsternoView != null && ( professionistaEsternoView.isDeleteMode() || professionistaEsternoView.isEditMode() )){
			return PAGINA_FORM_EDIT_PATH;
		} else {
			return PAGINA_FORM_PATH;
		}
	}
	
	
	@RequestMapping("/professionistaEsterno/gestioneProfEst")
	public String gestioneProfEst(HttpServletRequest request, Model model, Locale locale) {
		
		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if(code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){
			ProfessionistaEsternoView professionistaEsternoView = new ProfessionistaEsternoView();
			// engsecurity VA - redirect
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
	        //removeCSRFToken(request);
			try {
				List<ProfessionistaEsternoView> listaProfEst = professionistaEsternoService.leggi(false);
				professionistaEsternoView.setListaProfEst(listaProfEst);
				super.caricaListe(professionistaEsternoView, locale);
				this.caricaListaGiudizi(professionistaEsternoView);
				this.isVendorManagement(professionistaEsternoView);
				convertAndSetProfessionistaEsternoToJsonArray(professionistaEsternoView);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			professionistaEsternoView.setVo(new ProfessionistaEsterno());
			model.addAttribute(MODEL_VIEW_NOME, professionistaEsternoView);
			return PAGINA_FORM_EDIT_PATH;
		}
		return "redirect:/errore.action";
	}
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		ProfessionistaEsternoView professionistaEsternoView = (ProfessionistaEsternoView) view;

		List<TipoProfessionistaView> listaTipoProfessionista = tipoProfessionistaService.leggi(locale);
		professionistaEsternoView.setListaTipoProfessionista(listaTipoProfessionista);
		
		List<StatoEsitoValutazioneProfView> listaStatoEsitoValutazioneProf = statoEsitoValutazioneProfService.leggi(locale);
		professionistaEsternoView.setListaStatoEsitoValutazioneProf(listaStatoEsitoValutazioneProf);
		
		List<StatoProfessionistaView> listaStatoProfessionista = statoProfessionistaService.leggi(locale);
		professionistaEsternoView.setListaStatoProfessionista(listaStatoProfessionista);
		
		List<StudioLegaleView> listaStudioLegale = studioLegaleService.leggi();
		professionistaEsternoView.setListaStudioLegale(listaStudioLegale);
		
		List<CategoriaContestView> listaCategoriaContest = categoriaContestService.leggi(locale);
		professionistaEsternoView.setListaCategoriaContest(listaCategoriaContest);
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ProfessionistaEsternoValidator());
		
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
	
	private void convertAndSetProfessionistaEsternoToJsonArray(ProfessionistaEsternoView professionistaEsternoView)  throws Throwable {
		List<ProfessionistaEsternoView> lista = professionistaEsternoView.getListaProfEst();
		JSONArray jsonArray = new JSONArray();
		if( lista != null && lista.size() > 0 ){
			for( ProfessionistaEsternoView view : lista ){
				JSONObject jsonObject = new JSONObject();
				
				ProfessionistaEsterno vo = view.getVo();
				jsonObject.put("id", vo.getId() );
				
				String nome = vo.getNome();
				
				if(nome != null && !nome.isEmpty()){
					
					nome = nome.replace("'"," ").replace("\"", " ");
					nome=nome.trim();
					nome=org.apache.commons.lang3.StringEscapeUtils.escapeJson(nome);
				}
				
				jsonObject.put("nome", nome);
				
				String cognome = vo.getCognome();
				
				if(cognome != null && !cognome.isEmpty()){
					
					cognome = cognome.replace("'"," ").replace("\"", " ");
					cognome=cognome.trim();
					cognome=org.apache.commons.lang3.StringEscapeUtils.escapeJson(cognome);
				}	
				
				jsonObject.put("cognome", cognome);
				
				String codiceFiscale = vo.getCodiceFiscale();
				
				if(codiceFiscale != null && !codiceFiscale.isEmpty()){
					
					codiceFiscale = codiceFiscale.replace("'"," ").replace("\"", " ");
					codiceFiscale=codiceFiscale.trim();
					codiceFiscale=org.apache.commons.lang3.StringEscapeUtils.escapeJson(codiceFiscale);
				}
				
				jsonObject.put("codiceFiscale", codiceFiscale); 
				
				jsonArray.put(jsonObject);
			}
		}
		
		if (jsonArray.length() > 0) {
			professionistaEsternoView.setJsonArrayProfessionistaEsterno(jsonArray.toString());
		} 
		else
			professionistaEsternoView.setJsonArrayProfessionistaEsterno(null);
	}
	
	public void caricaListaGiudizi(ProfessionistaEsternoView view){
		List<GiudizioProfEstView> giudizi = new ArrayList<GiudizioProfEstView>();
		GiudizioProfEstView ottimo = new GiudizioProfEstView();
		ottimo.setGiudizioVal(Integer.parseInt(CostantiDAO.VENDOR_GIUDIZIO_OTTIMO));
		ottimo.setGiudizioDesc(Costanti.VENDOR_GIUDIZIO_OTTIMO);
		
		GiudizioProfEstView buono = new GiudizioProfEstView();
		buono.setGiudizioVal(Integer.parseInt(CostantiDAO.VENDOR_GIUDIZIO_BUONO));
		buono.setGiudizioDesc(Costanti.VENDOR_GIUDIZIO_BUONO);
		
		GiudizioProfEstView discreto = new GiudizioProfEstView();
		discreto.setGiudizioVal(Integer.parseInt(CostantiDAO.VENDOR_GIUDIZIO_DISCRETO));
		discreto.setGiudizioDesc(Costanti.VENDOR_GIUDIZIO_DISCRETO);
		
		GiudizioProfEstView sufficiente = new GiudizioProfEstView();
		sufficiente.setGiudizioVal(Integer.parseInt(CostantiDAO.VENDOR_GIUDIZIO_SUFFICIENTE));
		sufficiente.setGiudizioDesc(Costanti.VENDOR_GIUDIZIO_SUFFICIENTE);
		
		GiudizioProfEstView mediocre = new GiudizioProfEstView();
		mediocre.setGiudizioVal(Integer.parseInt(CostantiDAO.VENDOR_GIUDIZIO_MEDIOCRE));
		mediocre.setGiudizioDesc(Costanti.VENDOR_GIUDIZIO_MEDIOCRE);
		
		GiudizioProfEstView insufficiente = new GiudizioProfEstView();
		insufficiente.setGiudizioVal(Integer.parseInt(CostantiDAO.VENDOR_GIUDIZIO_INSUFFICIENTE));
		insufficiente.setGiudizioDesc(Costanti.VENDOR_GIUDIZIO_INSUFFICIENTE);
		
		giudizi.add(ottimo);
		giudizi.add(buono);
		giudizi.add(discreto);
		giudizi.add(sufficiente);
		giudizi.add(mediocre);
		giudizi.add(insufficiente);
		
		view.setListaGiudizio(giudizi);
	}
	
	public void isVendorManagement(ProfessionistaEsternoView view){
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		List<String> roleCode = currentSessionUtil.getRolesCode();
		
		for(String code : roleCode){
			if( code.equals( CostantiDAO.GESTORE_VENDOR )){
				view.setVendorManagement("");
			 }
		}
	}
}
