package eng.la.controller;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eng.la.persistence.DocumentaleDdsCryptDAO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.json.JSONArray;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

//import com.filenet.api.collection.DocumentSet;
//import com.filenet.api.collection.PageIterator;
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.BeautyContestReplyService;
import eng.la.business.BeautyContestService;
import eng.la.business.MateriaService;
import eng.la.business.NazioneService;
import eng.la.business.ProfessionistaEsternoService;
import eng.la.business.UtenteService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.business.websocket.WebSocketPublisher;
import eng.la.business.workflow.BeautyContestWfService;
import eng.la.model.BeautyContest;
import eng.la.model.BeautyContestWf;
import eng.la.model.RBeautyContestMateria;
import eng.la.model.RBeautyContestProfessionistaEsterno;
import eng.la.model.rest.BeautyContestRest;
import eng.la.model.rest.Event;
import eng.la.model.rest.RicercaBeautyContestRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.rest.StepWFRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.BeautyContestReplyView;
import eng.la.model.view.BeautyContestView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.MateriaView;
import eng.la.model.view.NazioneView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.StatoBeautyContestView;
import eng.la.model.view.StatoIncaricoView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.DocumentaleCryptDAO;
import eng.la.presentation.validator.BeautyContestValidator;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("beautyContestController")
@SessionAttributes("beautyContestView")
public class BeautyContestController extends BaseController {
	private static final String MODEL_VIEW_NOME = "beautyContestView";
	private static final String PAGINA_FORM_PATH = "beautyContest/formBeautyContest";
	private static final String PAGINA_DETTAGLIO_PATH = "beautyContest/formBeautyContestDettaglio";
	private static final String PAGINA_DETTAGLIO_VINCITORE_PATH = "beautyContest/formBeautyContestDettaglioVincitore";
	private static final String PAGINA_DETTAGLIO_RISPOSTE_PATH = "beautyContest/formBeautyContestDettaglioRisposte";
	private static final String PAGINA_RICERCA_PATH = "beautyContest/cercaBeautyContest";
	private static final String PAGINA_AZIONI_BEAUTY_CONTEST= "beautyContest/azioniBeautyContest";
	private static final String PAGINA_AZIONI_CERCA_BEAUTY_CONTEST= "beautyContest/azioniCercaBeautyContest";
	private static final String PAGINA_VISUALIZZA_STAMPA ="beautyContest/visualizzaBeautyContestStampa";
	private static final String PAGINA_PERMESSI_BEAUTY_CONTEST = "beautyContest/permessiBeautyContest";
	private static final String PAGINA_NOTA_AGGIUDICAZIONE_FIRMATA = "beautyContest/notaAggiudicazioneFirmata";

	private static final Logger logger = Logger.getLogger(BeautyContestController.class);
	@Autowired
	private EmailNotificationService emailNotificationService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private BeautyContestService beautyContestService;

	@Autowired
	private BeautyContestWfService beautyContestWfService;

	@Autowired
	private BeautyContestReplyService beautyContestReplyService;

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	@Autowired
	private ProfessionistaEsternoService professionistaEsternoService;

	@Autowired
	private NazioneService nazioneService;

	@Autowired
	private MateriaService materiaService;


	@RequestMapping("/beautyContest/modifica")
	public String modificaBeautyContest(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		BeautyContestView view = new BeautyContestView();
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			BeautyContestView beautyContestSalvato = (BeautyContestView) beautyContestService.leggi(id, FetchMode.JOIN);
			if (beautyContestSalvato != null && beautyContestSalvato.getVo() != null) {
				super.caricaListe(view, locale);
				popolaFormDaVo(view, beautyContestSalvato.getVo(), locale, utenteView, true);

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

	@RequestMapping("/beautyContest/dettaglio")
	public String dettaglioBeautyContest(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		BeautyContestView view = new BeautyContestView();

		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			BeautyContestView beautyContestSalvato = (BeautyContestView) beautyContestService.leggi(id, FetchMode.JOIN);
			if (beautyContestSalvato != null && beautyContestSalvato.getVo() != null) {
				super.caricaListePerDettaglio(view, locale);
				popolaFormDaVo(view, beautyContestSalvato.getVo(), locale, utenteView, true);

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

	@RequestMapping("/beautyContest/dettaglioConVincitore")
	public String dettaglioBeautyContestConVincitore(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		BeautyContestView view = new BeautyContestView();

		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			BeautyContestView beautyContestSalvato = (BeautyContestView) beautyContestService.leggi(id, FetchMode.JOIN);
			if (beautyContestSalvato != null && beautyContestSalvato.getVo() != null) {
				super.caricaListePerDettaglio(view, locale);
				popolaFormDaVo(view, beautyContestSalvato.getVo(), locale, utenteView, true);

			} else {
				model.addAttribute("errorMessage", "errore.generico");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, view);
		request.setAttribute("anchorName", "anchorDettaglioVincitore");
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_DETTAGLIO_VINCITORE_PATH;
	}

	@RequestMapping("/beautyContest/dettaglioConElencoRisposte")
	public String dettaglioBeautyContestConElencoRisposte(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		BeautyContestView view = new BeautyContestView();

		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			BeautyContestView beautyContestSalvato = (BeautyContestView) beautyContestService.leggi(id, FetchMode.JOIN);
			if (beautyContestSalvato != null && beautyContestSalvato.getVo() != null) {
				super.caricaListePerDettaglio(view, locale);
				popolaFormDaVo(view, beautyContestSalvato.getVo(), locale, utenteView, true);

			} else {
				model.addAttribute("errorMessage", "errore.generico");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, view);
		request.setAttribute("anchorName", "anchorDettaglioRisposte");
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_DETTAGLIO_RISPOSTE_PATH;
	}


	private void popolaFormDaVo(BeautyContestView view, BeautyContest vo, Locale locale, UtenteView utenteConnesso, boolean allegati) throws Throwable {

		view.setBeautyContestId(vo.getId());		
		view.setTitolo(vo.getTitolo());
		if( vo.getDataAutorizzazione() != null ){
			view.setDataAutorizzazione(DateUtil.formattaData(vo.getDataAutorizzazione().getTime()));	
		}
		if( vo.getDataRichiestaAutorBc() != null ){
			view.setDataRichiestaAutorizzazione(DateUtil.formattaData(vo.getDataRichiestaAutorBc().getTime()));	
		}
		if( vo.getDataChiusura() != null ){
			view.setDataChiusura(DateUtil.formattaData(vo.getDataChiusura().getTime()));	
		}

		UtenteView utenteOwner = utenteService.leggiUtenteDaUserId(vo.getLegaleInterno());
		if( utenteOwner == null || utenteOwner.getVo() == null ){
			view.setLegaleInterno(vo.getLegaleInterno());
			view.setUnitaLegale("");
		}else{ 
			view.setLegaleInterno(utenteOwner.getVo().getUseridUtil());
			view.setUnitaLegale(utenteOwner.getCodiceDescrizioneUnitaLegale());
		}

		view.setUnitaLegale(utenteConnesso.getVo().getDescrUnitaUtil());
		view.setCdc(vo.getCentroDiCosto());
		view.setIncaricoAccordoQuadro(vo.getIncarico_accordoQuadro());
		view.setDescrizioneSow(vo.getDescrizione_sow());

		StatoBeautyContestView statoBeautyContestView = anagraficaStatiTipiService.leggiStatoBeautyContest(vo.getStatoBeautyContest().getCodGruppoLingua(), locale.getLanguage().toUpperCase());		
		view.setStatoBeautyContest(statoBeautyContestView.getVo().getDescrizione());
		view.setStatoBeautyContestCode(statoBeautyContestView.getVo().getCodGruppoLingua()); 

		if(vo.getStatoBeautyContest().getCodGruppoLingua().equals(CostantiDAO.BEAUTY_CONTEST_STATO_AUTORIZZATO)){
			view.setIsAutorizzato("true");
		}else{
			view.setIsAutorizzato("false");
		}


		if (vo.getRBeautyContestMaterias() != null) {
			Collection<RBeautyContestMateria> listaBcMaterie = vo.getRBeautyContestMaterias();
			String[] materie = null;
			List<String> materieCode = new ArrayList<String>();
			List<String> materieAggiunteDesc = new ArrayList<String>();
			for (RBeautyContestMateria bcMateria : listaBcMaterie) {
				materieCode.add(bcMateria.getMateria().getCodGruppoLingua());
				MateriaView materiaView = materiaService.leggi(bcMateria.getMateria().getCodGruppoLingua(), locale, false);
				materieAggiunteDesc.add(materiaView.getVo().getNome());
			}
			materie = new String[materieCode.size()];
			materieCode.toArray(materie);
			view.setMaterie(materie);
			view.setListaMaterieAggiunteDesc(materieAggiunteDesc);
		}

		if (vo.getRBeautyContestProfessionistaEsternos() != null) {
			Collection<RBeautyContestProfessionistaEsterno> listaProfessionisti = vo.getRBeautyContestProfessionistaEsternos();
			String[] professionistiIds = null;
			List<String> professionistiIdsLista = new ArrayList<String>();
			List<String> professionistiAggiunteDesc = new ArrayList<String>();
			List<ProfessionistaEsternoView> listaPartecipanti = new ArrayList<ProfessionistaEsternoView>();

			for (RBeautyContestProfessionistaEsterno professionista : listaProfessionisti) {
				ProfessionistaEsternoView professionistaEsternoView = new ProfessionistaEsternoView();
				professionistaEsternoView.setVo(professionista.getProfessionistaEsterno());
				listaPartecipanti.add(professionistaEsternoView);
				professionistiIdsLista.add(professionista.getProfessionistaEsterno().getId() + "");
				professionistiAggiunteDesc.add(professionista.getProfessionistaEsterno().getCognomeNome());
			}
			professionistiIds = new String[professionistiIdsLista.size()];

			professionistiIdsLista.toArray(professionistiIds);
			view.setPartecipantiAggiunti(professionistiIds);
			view.setListaPartecipantiAggiunti(listaPartecipanti);
		}

		if (vo.getNazione() != null) {
			view.setNazioneCode(vo.getNazione().getCodGruppoLingua());
			view.setNazioneDesc(vo.getNazione().getDescrizione());
		}

		if( vo.getVincitore() != null ){
			view.setVincitoreId(vo.getVincitore().getId());
			ProfessionistaEsternoView professionistaSelezionato = new ProfessionistaEsternoView();
			professionistaSelezionato.setVo(vo.getVincitore());
			view.setVincitoreSelezionato(professionistaSelezionato);
		}

		List<BeautyContestReplyView> elencoRisposteProfessionisti = null;
		elencoRisposteProfessionisti = beautyContestReplyService.leggiElencoRisposteProfessionisti(vo.getId());
		view.setListaBeautyContestReplyView(elencoRisposteProfessionisti);

		if(allegati){
			caricaDocumentiGenericiFilenet(view, vo);
			caricaNotaAggiudicazioneFirmata(view, vo);
		}
	}

	private void caricaDocumentiGenericiFilenet(BeautyContestView view, BeautyContest vo) throws Throwable {		

		String nomeCartellaBeautyContest = FileNetUtil.getBeautyContestCartella(vo.getId(), vo.getDataEmissione());

		/*@@DDS
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO"); 
		Folder cartellaBc = documentaleCryptDAO.leggiCartella(nomeCartellaBeautyContest);
		*/
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");


		/*@@DDS inizio commento
		DocumentSet documenti = cartellaBc.get_ContainedDocuments();
		List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
		if( documenti != null ){
			PageIterator it = documenti.pageIterator();
			while(it.nextPage()){
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for( EngineObjectImpl objDocumento : documentiArray ){
					DocumentImpl documento = (DocumentImpl)objDocumento;
					if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.BEAUTY_CONTEST_DOCUMENT)){
						//if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.FASCICOLO_DOCUMENT)){

						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.get_Name() );
						docView.setUuid(documento.get_Id().toString());
						listaDocumenti.add(docView);
					}
				}
			}
			view.setListaAllegati(listaDocumenti);
		}
		 */
		logger.debug("@@DDS BeautyContestController.caricaDocumentiGenericiFilenet TODO");
		List<Document> documenti = documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaBeautyContest);
		List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
		if( documenti != null ){

			for (Document documento:documenti){

				if( documento.getDocumentalClass().equals(FileNetClassNames.BEAUTY_CONTEST_DOCUMENT)){
					//if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.FASCICOLO_DOCUMENT)){

					DocumentoView docView = new DocumentoView();
					docView.setNomeFile(documento.getContents().get(0).getContentsName() );
					docView.setUuid(documento.getId());
					listaDocumenti.add(docView);
				}

			}
			view.setListaAllegati(listaDocumenti);
		}
	}

	private void caricaNotaAggiudicazioneFirmata(BeautyContestView view, BeautyContest vo) throws Throwable {

		String nomeCartellaBeautyContest = FileNetUtil.getBeautyContestCartella(vo.getId(), vo.getDataEmissione());
		/*
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaBc = documentaleCryptDAO.leggiCartella(nomeCartellaBeautyContest);
		*/
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
		Folder cartellaBc = documentaleDdsCryptDAO.leggiCartella(nomeCartellaBeautyContest);

		/*@@DDS inizio commento
		DocumentSet documenti = cartellaBc.get_ContainedDocuments();
		DocumentoView notaAggiudicazioneFirmata = null;
		if (documenti != null) {
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					//if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT)){
					if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.NOTA_AGGIUDICAZIONE_BEAUTY_CONTEST_DOCUMENT)){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.get_Name());

						String uuid = documento.get_Id().toString();
						uuid = uuid.replace("{", "");
						uuid = uuid.replace("}", "");
						uuid = uuid.toLowerCase();

						docView.setUuid(uuid);
						notaAggiudicazioneFirmata = docView;
					}
				}
			}
			view.setNotaAggiudicazioneFirmataDoc(notaAggiudicazioneFirmata);
		}

		 */
		DocumentoView notaAggiudicazioneFirmata = null;
		List<Document> documenti = documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaBeautyContest);
		if (documenti != null) {
				for (Document documento:documenti) {

					//if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT)){
					if( documento.getDocumentalClass().equals(FileNetClassNames.NOTA_AGGIUDICAZIONE_BEAUTY_CONTEST_DOCUMENT)){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.getContents().get(0).getContentsName());

						String uuid = documento.getId();
						uuid = uuid.replace("{", "");
						uuid = uuid.replace("}", "");

						docView.setUuid(uuid);
						notaAggiudicazioneFirmata = docView;
					}

			}
			view.setNotaAggiudicazioneFirmataDoc(notaAggiudicazioneFirmata);
		}
	}

	@RequestMapping(value = "/beautyContest/salva", method = RequestMethod.POST)
	public String salvaBeautyContest(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated BeautyContestView beautyContestView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		String token=request.getParameter("CSRFToken");

		try {

			if (beautyContestView.getOp() != null && !beautyContestView.getOp().equals("salvaBeautyContest")) {
				String ritorno = invocaMetodoDaReflection(beautyContestView, bindingResult, locale, model, request,response, this);
				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}
			
			beautyContestView.setMailVincitore(Boolean.FALSE);
			preparaPerSalvataggio(beautyContestView, bindingResult);

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			long beautyContestId = 0;
			boolean isNew = false;
			if (beautyContestView.getBeautyContestId() == null || beautyContestView.getBeautyContestId() == 0) {
				BeautyContestView beautyContestSalvato = beautyContestService.inserisci(beautyContestView);
				beautyContestId = beautyContestSalvato.getVo().getId();
				isNew = true;
			} else {
				beautyContestService.modifica(beautyContestView);
				
				beautyContestId = beautyContestView.getVo().getId();
				
				if(beautyContestView.getMailVincitore()){
					
					if(beautyContestView.getVo().getVincitore() != null){
							
						emailNotificationService.inviaNotificaBCVincitoreSelezionato(locale.getLanguage().toUpperCase(), beautyContestId);
					}
				}
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
			if( isNew ){
				return "redirect:modifica.action?id=" + beautyContestId+"&CSRFToken="+token;
			}
			return "redirect:dettaglio.action?id=" + beautyContestId+"&CSRFToken="+token;
		} catch (Throwable e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}

	@RequestMapping("/beautyContest/elimina")
	public @ResponseBody RisultatoOperazioneRest eliminaBeautyContest(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			BeautyContestView beautyContestView = beautyContestService.leggi(id, FetchMode.JOIN);
			if(beautyContestView != null){
				beautyContestService.cancella(beautyContestView);
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	private void preparaPerSalvataggio(BeautyContestView view, BindingResult bindingResult)throws Throwable {
		BeautyContest vo = new BeautyContest();
		BeautyContestView oldBeautyContestView = null;
		if (view.getBeautyContestId() != null) {
			oldBeautyContestView = beautyContestService.leggi(view.getBeautyContestId());

			vo.setId(view.getBeautyContestId());
			vo.setStatoBeautyContest(oldBeautyContestView.getVo().getStatoBeautyContest());
			vo.setDataEmissione(oldBeautyContestView.getVo().getDataEmissione());
		}

		vo.setTitolo(view.getTitolo());
		vo.setDescrizione_sow(view.getDescrizioneSow());

		if( view.getDataAutorizzazione() != null && DateUtil.isData(view.getDataAutorizzazione())){
			vo.setDataAutorizzazione(DateUtil.toDate(view.getDataAutorizzazione()));	
		}

		if( view.getDataRichiestaAutorizzazione() != null && DateUtil.isData(view.getDataRichiestaAutorizzazione())){
			vo.setDataRichiestaAutorBc(DateUtil.toDate(view.getDataRichiestaAutorizzazione()));	
		} 

		if( view.getDataChiusura() != null && DateUtil.isData(view.getDataChiusura())){
			vo.setDataChiusura(DateUtil.toDate(view.getDataChiusura()));	
		}

		vo.setLegaleInterno(view.getLegaleInterno());

		vo.setCentroDiCosto(view.getCdc());

		vo.setIncarico_accordoQuadro(view.getIncaricoAccordoQuadro());

		Locale linguaItaliana = Locale.ITALIAN;

		if (view.getMaterie() != null && view.getMaterie().length > 0) {
			Set<RBeautyContestMateria> listaBcMaterie = new HashSet<RBeautyContestMateria>();
			String[] materieCodes = view.getMaterie();
			for (String codice : materieCodes) {
				RBeautyContestMateria bcMateria = new RBeautyContestMateria();
				MateriaView materiaView = materiaService.leggi(codice, linguaItaliana, false);
				bcMateria.setMateria(materiaView.getVo());
				listaBcMaterie.add(bcMateria);
			}
			vo.setRBeautyContestMaterias(listaBcMaterie);
		}

		if (view.getPartecipantiAggiunti() != null && view.getPartecipantiAggiunti().length > 0) {
			Set<RBeautyContestProfessionistaEsterno> listaBcPartecipanti = new HashSet<RBeautyContestProfessionistaEsterno>();
			String[] partecipantiCodes = view.getPartecipantiAggiunti();
			for (String codice : partecipantiCodes) {
				RBeautyContestProfessionistaEsterno bcPartecipante = new RBeautyContestProfessionistaEsterno();
				ProfessionistaEsternoView partecipanteView = professionistaEsternoService.leggi(Long.parseLong(codice));
				bcPartecipante.setProfessionistaEsterno(partecipanteView.getVo());
				listaBcPartecipanti.add(bcPartecipante);
			}
			vo.setRBeautyContestProfessionistaEsternos(listaBcPartecipanti);
		}

		if (view.getNazioneCode() != null && view.getNazioneCode().trim().length() > 0) {
			NazioneView nazioneView = nazioneService.leggi(view.getNazioneCode(), linguaItaliana, false);
			vo.setNazione(nazioneView.getVo());
		}

		if( view.getVincitoreId() != null && view.getVincitoreId() > 0 ){
			ProfessionistaEsternoView professionistaEsternoView = professionistaEsternoService.leggi(view.getVincitoreId());
			vo.setVincitore(professionistaEsternoView.getVo());
			
			if(oldBeautyContestView.getVo().getVincitore() == null){
				view.setMailVincitore(Boolean.TRUE);
			}
		} 
		view.setVo(vo);
	}

	@RequestMapping("/beautyContest/crea")
	public String creaBeautyContest(HttpServletRequest request, Model model, Locale locale) {
		BeautyContestView view = new BeautyContestView();
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		try {
			UtenteView utenteConnesso = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if (utenteConnesso != null) { 
				view.setUnitaLegale(utenteConnesso.getCodiceDescrizioneUnitaLegale());
				view.setLegaleInterno(utenteConnesso.getVo().getUseridUtil());
			}
			view.setDataEmissione(DateUtil.formattaData(new Date().getTime()));
			super.caricaListe(view, locale);

		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		} 

		view.setVo(new BeautyContest()); 

		model.addAttribute(MODEL_VIEW_NOME, view);
		return  model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_FORM_PATH;
	}

	@RequestMapping(value = "/beautyContest/uploadAllegato", method = RequestMethod.POST)
	public String uploadAllegato( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) BeautyContestView beautyContestView ) {  
		// engsecurity VA
		//		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//		htmlActionSupport.checkCSRFToken(request);
		logger.debug("@@DDS _______________ BeautyContest uploadAllegato" + file.getOriginalFilename());
		try {   
			String beautyContestId = request.getParameter("idBeautyContest"); 
			if( beautyContestId == null  ){
				throw new RuntimeException("beautyContestId non deve essere null");
			}

			File fileTmp = File.createTempFile("allegatoGenerico", "___" + file.getOriginalFilename() );
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();

			documentoView.setUuid("" +( beautyContestView.getListaAllegati() == null || beautyContestView.getListaAllegati().size() == 0 ?  1 : beautyContestView.getListaAllegati().size()+1));
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			List<DocumentoView> allegatiGenerici = beautyContestView.getListaAllegati()==null?new ArrayList<DocumentoView>():beautyContestView.getListaAllegati();
			allegatiGenerici.add(documentoView);
			logger.debug("@@DDS _______________ BeautyContest nomefile" + documentoView.getNomeFile());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "beautyContest/allegatiGenerici";
	}

	@RequestMapping(value = "/beautyContest/rimuoviAllegato", method = RequestMethod.POST)
	public String rimuoviAllegatoGenerico( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) BeautyContestView beautyContestView ) {  
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {   
			String uuid = request.getParameter("uuid"); 
			if( uuid == null  ){
				throw new RuntimeException("uuid non pu� essere null");
			}

			List<DocumentoView> allegatiGenericiOld = beautyContestView.getListaAllegati();
			List<DocumentoView> allegatiGenerici = new ArrayList<DocumentoView>();
			allegatiGenerici.addAll(allegatiGenericiOld);
			Set<String> allegatiDaRimuovere = beautyContestView.getAllegatiDaRimuovereUuid() == null ? new HashSet<String>():beautyContestView.getAllegatiDaRimuovereUuid();
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
			beautyContestView.setListaAllegati(allegatiGenerici);
			beautyContestView.setAllegatiDaRimuovereUuid(allegatiDaRimuovere);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "beautyContest/allegatiGenerici";
	}

	@RequestMapping(value = "/beautyContest/cerca", method = RequestMethod.GET)
	public String cercaBeautyContest(HttpServletRequest request, Model model, Locale locale) {
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {
			BeautyContestView view = new BeautyContestView();
			view.setListaStatoBeautyContest(anagraficaStatiTipiService.leggiStatiBeautyContest(locale.getLanguage().toUpperCase()));
			model.addAttribute(MODEL_VIEW_NOME, view);
			return PAGINA_RICERCA_PATH;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/beautyContest/ricerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaBeautyContestRest cercaBeauty(HttpServletRequest request, Locale locale) {
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
			String titolo = request.getParameter("titolo") == null ? "" : URLDecoder.decode(request.getParameter("titolo"),"UTF-8");
			String statoBeautyContestCode = request.getParameter("statoBeautyContestCode") == null ? "" : request.getParameter("statoBeautyContestCode");
			String dal = request.getParameter("dal") == null ? "" : URLDecoder.decode(request.getParameter("dal"),"UTF-8");
			String al = request.getParameter("al") == null ? "" :  URLDecoder.decode(request.getParameter("al"),"UTF-8");
			String centroDiCosto = request.getParameter("centroDiCosto") == null ? "" : URLDecoder.decode( request.getParameter("centroDiCosto"),"UTF-8"); 

			ListaPaginata<BeautyContestView> lista = beautyContestService.cerca(titolo, dal, al, statoBeautyContestCode, centroDiCosto,
					numElementiPerPagina, numeroPagina,ordinamento, tipoOrdinamento);
			RicercaBeautyContestRest ricercaModelRest = new RicercaBeautyContestRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<BeautyContestRest> rows = new ArrayList<BeautyContestRest>();
			for (BeautyContestView view : lista) {

				BeautyContestRest beautyContestRest = new BeautyContestRest();
				beautyContestRest.setId(view.getVo().getId());
				beautyContestRest.setTitolo(view.getVo().getTitolo());
				beautyContestRest.setDataEmissione(DateUtil.getDataDDMMYYYY(view.getVo().getDataEmissione().getTime()));
				beautyContestRest.setDataChiusura(DateUtil.getDataDDMMYYYY(view.getVo().getDataChiusura().getTime()));

				UtenteView utenteOwner = utenteService.leggiUtenteDaUserId(view.getVo().getLegaleInterno());
				if( utenteOwner == null || utenteOwner.getVo() == null ){
					beautyContestRest.setUnitaLegale("");
				}else{ 
					beautyContestRest.setUnitaLegale(utenteOwner.getCodiceDescrizioneUnitaLegale());
				}

				beautyContestRest.setCdc(view.getVo().getCentroDiCosto());
				StatoIncaricoView statoIncaricoView = anagraficaStatiTipiService.leggiStatoIncarico(view.getVo().getStatoBeautyContest().getCodGruppoLingua(), locale.getLanguage().toUpperCase());
				beautyContestRest.setStato(statoIncaricoView.getVo().getDescrizione());

				String assegnatario="-"; 
				if(view.getVo().getStatoBeautyContest().getCodGruppoLingua().equalsIgnoreCase("APAP")
						||view.getVo().getStatoBeautyContest().getCodGruppoLingua().equalsIgnoreCase("APAP1")
						||view.getVo().getStatoBeautyContest().getCodGruppoLingua().equalsIgnoreCase("AAUT")
						||view.getVo().getStatoBeautyContest().getCodGruppoLingua().equalsIgnoreCase("APAP2")){
					UtenteView utenteV=utenteService.leggiAssegnatarioWfBeautyContest(view.getVo().getId());
					assegnatario=utenteV == null ? "N.D." : utenteV.getVo().getNominativoUtil();
				}

				beautyContestRest.setAzioni("<p id='containerAzioniRigaBc" + view.getVo().getId() + "' title='"+assegnatario+"' alt='"+assegnatario+"' ></p>");
				rows.add(beautyContestRest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/beautyContest/caricaAzioni", method = RequestMethod.POST)
	public String caricaAzioniSchedaFr(@RequestParam("idBc") long idBc, HttpServletRequest req,
			Locale locale) {
		// engsecurity VA -- in loading
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(req);
		//removeCSRFToken(request);

		try {
			req.setAttribute("idBc", idBc);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if( req.getParameter("onlyContent") != null  ){
			return PAGINA_AZIONI_BEAUTY_CONTEST;
		}
		return PAGINA_AZIONI_CERCA_BEAUTY_CONTEST;
	}

	@RequestMapping(value ="/beautyContest/stampa", method = RequestMethod.GET)
	public String stampaBeautyContest(@RequestParam("id") long id,BeautyContestView beautyContestView,
			Model model, Locale locale, HttpServletRequest request) throws Throwable {
		// engsecurity VA 
		// HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		// htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);

		BeautyContestView view = new BeautyContestView();

		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			BeautyContestView viewSalvata = (BeautyContestView) beautyContestService.leggi(id, FetchMode.JOIN);
			if (viewSalvata != null && viewSalvata.getVo() != null) {
				popolaFormDaVo(view, viewSalvata.getVo(), locale, utenteView, false);

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

	@RequestMapping(value = "/beautyContest/caricaGrigliaPermessiBeautyContest", method = RequestMethod.POST)
	public String caricaGrigliaPermessiBeautyContest(@RequestParam("id") long idBc, HttpServletRequest req,
			Locale locale) {
		try {

			req.setAttribute("idBc", idBc);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return PAGINA_PERMESSI_BEAUTY_CONTEST;
	}

	@RequestMapping(value = "/beautyContest/estendiPermessiBeautyContest", method = RequestMethod.POST, produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest  estendiPermessiBeautyContest( HttpServletRequest request  ) { 
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		try {   
			Long bcId = NumberUtils.toLong(request.getParameter("idBc"));
			String[] permessiScritturaArray = request.getParameterValues("permessoScrittura");
			String[] permessiLetturaArray = request.getParameterValues("permessoLettura");  

			beautyContestService.salvaPermessiBeautyContest(bcId, permessiScritturaArray, permessiLetturaArray);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@RequestMapping(value="/beautyContest/avviaWorkFlowBc", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest avviaWorkFlowBeautyContest(@RequestParam("id") long id, @RequestParam("matricola_dest") String matricola_dest,
			HttpServletRequest request) {
		//DARIO AGGIUNTO @RequestParam("matricola_dest") String matricola_dest
		
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if( utenteView != null ){
				BeautyContestView beautyContestView = beautyContestService.leggi(id, FetchMode.JOIN);
				if( beautyContestView.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_BOZZA)){

					//DARIO C **************************************************************************************************
					//boolean ret = beautyContestWfService.avviaWorkflow(id, utenteView.getVo().getUseridUtil());
					boolean ret = beautyContestWfService.avviaWorkflow(id, utenteView.getVo().getUseridUtil(),matricola_dest);
					//**********************************************************************************************************
					
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
					if(ret){

						//invio la notifica
						StepWFRest stepSuccessivo = new StepWFRest();
						stepSuccessivo.setId(0);

						
						UtenteView assegnatario = utenteService.leggiAssegnatarioWfBeautyContest(id);
						
						if(assegnatario != null){

							//MS controllo Assegnatario Assente
							assegnatario=checkAssegnatarioPresente(assegnatario, id);

							Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(event); 

						}
						//invio la e-mail
						try{
							//DARIO C ************************************************************************************************************************************************************************
							//emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.BEAUTY_CONTEST, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil());
							emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.BEAUTY_CONTEST, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil(),matricola_dest);
							//*******************************************************************************************************************************************************************************
						}
						catch (Exception e) { 
							System.out.println("Errore in invio e-mail"+ e);
						}
					}
				}else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.beautyContest.non.valido");
				}
			}else{
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@RequestMapping(value="/beautyContest/riportaInBozzaWorkFlowBeautyContest", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest riportaInBozzaWorkFlowBeautyContest(@RequestParam("id") long id, HttpServletRequest request) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if( utenteView != null ){
				BeautyContestView view = beautyContestService.leggi(id, FetchMode.JOIN);
				if( !view.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_BOZZA) ){ 
					//recupero l'eventuale utente corrente del workflow per notificargli la rimozione del bc
					UtenteView assegnatario = utenteService.leggiAssegnatarioWfBeautyContest(id);

					boolean avviato = beautyContestWfService.riportaInBozzaWorkflow(id, utenteView.getVo().getUseridUtil());
					if( avviato ){
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
						if(assegnatario != null){
							//invio la notifica
							StepWFRest stepSuccessivo = new StepWFRest();
							stepSuccessivo.setId(0);
							Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(event); 
						}
					}else{
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.WARN, "messaggio.ko");
					}
				}else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.beautyContest.non.valido");
				}
			}else{
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}

			try{
				emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.BEAUTY_CONTEST, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil());
			}
			catch (Exception e) { 
				System.out.println("Errore in invio e-mail"+ e);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@RequestMapping(value="/beautyContest/arretraWorkFlowBeautyContest", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest arretraWorkFlowBeautyContest(@RequestParam("id") long id, HttpServletRequest request) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if( utenteView != null ){
				BeautyContestView view = beautyContestService.leggi(id, FetchMode.JOIN);
				if( !view.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_BOZZA) && 
						!view.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_AUTORIZZATO) ){ 
					//recupero l'eventuale utente corrente del workflow per notificargli la rimozione del bc
					UtenteView assegnatarioOld = utenteService.leggiAssegnatarioWfBeautyContest(id);
					boolean discard = beautyContestWfService.discardStep(id, utenteView.getVo().getUseridUtil());
					if( discard ){
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
						//recupero l'eventuale nuovo utente corrente del workflow per notificargli l'assegnazione del bc
						UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfBeautyContest(id);
						if(assegnatarioOld != null){
							//invio la notifica
							StepWFRest stepPrecedente = new StepWFRest();
							stepPrecedente.setId(0);
							Event eventOld = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepPrecedente, assegnatarioOld.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(eventOld); 
						}
						if(assegnatarioNew != null){
							//invio la notifica
							StepWFRest stepSuccessivo = new StepWFRest();
							stepSuccessivo.setId(0);
							Event eventNew = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatarioNew.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(eventNew); 
						}
					}else{
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.WARN, "messaggio.ko");
					}
				}else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.beautyContest.non.valido");
				}
			}else{
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	//Controlla se l'assegnatatio � Assente -  Ritorna un assegnatario Presente se disponibile
	private UtenteView checkAssegnatarioPresente(UtenteView assegnatario,long idEntita) throws Throwable{

		boolean esito=false;

		if(assegnatario.getVo()!=null && assegnatario.getVo().getAssente().equals("T")){
			BeautyContestWf wf = beautyContestWfService.leggiWorkflowInCorsoNotView(idEntita);
			if(wf!=null)
				esito=beautyContestWfService.avanzaWorkflow(wf.getId(), assegnatario.getVo().getUseridUtil());
			if(esito){
				UtenteView assegnatarioNew = utenteService.leggiAssegnatarioWfBeautyContest(idEntita);
				if(assegnatarioNew==null) return assegnatario;
				if(assegnatarioNew.getVo()!=null && assegnatarioNew.getVo().getAssente().equals("T")){
					if(!utenteService.isAssegnatarioManualeCorrenteStandard(wf.getId(),CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST)){	
						return	checkAssegnatarioPresente(assegnatarioNew,idEntita);	
					}
				}else{ 
					return assegnatarioNew;
				}
			}
		}
		return assegnatario;
	}

	public String selezionaVincitore(BeautyContestView beautyContestView, BindingResult bindingResult, Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {

		String paginaDiRitorno = PAGINA_FORM_PATH;

		try {
			String idVincitoreSelezionato = beautyContestView.getIdVincitoreSelezionato();

			if(idVincitoreSelezionato != null && !idVincitoreSelezionato.isEmpty()){

				beautyContestView.setVincitoreId(NumberUtils.toLong(idVincitoreSelezionato));
			}else{
				beautyContestView.setVincitoreId(null);
			}

		} catch (Throwable e) {

			e.printStackTrace();
		}
		request.setAttribute("anchorName", "anchorVincitore");
		return paginaDiRitorno;
	}

	@RequestMapping(value = "/beautyContest/uploadNotaAggiudicazioneFirmata", method = RequestMethod.POST)
	public String uploadNotaAggiudicazioneFirmata( HttpServletRequest request, Model model, @RequestParam("file") MultipartFile file, 
			@ModelAttribute(MODEL_VIEW_NOME) BeautyContestView beautyContestView ) {  

		try {   
			String beautyContestId = request.getParameter("idBc"); 
			if( beautyContestId == null  ){
				throw new RuntimeException("beautyContestId non deve essere null");
			}

			File fileTmp = File.createTempFile("notaAggiudicazioneFirmata", "___" + file.getOriginalFilename() );
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			beautyContestView.setNotaAggiudicazioneFirmataDoc(documentoView); 

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return PAGINA_NOTA_AGGIUDICAZIONE_FIRMATA;
	}

	@RequestMapping(value = "/beautyContest/rimuoviNotaAggiudicazioneFirmata", method = RequestMethod.POST)
	public String rimuoviNotaAggiudicazioneFirmata( HttpServletRequest request, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) BeautyContestView beautyContestView ) {  
		try {   
			NumberUtils.toLong(request.getParameter("idBc")); 

			beautyContestView.setNotaAggiudicazioneFirmataDoc(null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return PAGINA_NOTA_AGGIUDICAZIONE_FIRMATA;
	}

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		BeautyContestView beautyContestView = (BeautyContestView) view;
		List<ProfessionistaEsternoView> listaProfessionistaEsternoViews = null;
		listaProfessionistaEsternoViews = professionistaEsternoService.leggiProfessionistiAbilitatiBeautyContest();
		beautyContestView.setListaProfessionistiEsterni(listaProfessionistaEsternoViews);

		JSONArray jsonArray = materiaService.leggiAlberaturaMateriaTutte(locale, true);
		if (jsonArray != null) {
			beautyContestView.setJsonAlberaturaMaterie(jsonArray.toString());
		} else {
			beautyContestView.setJsonAlberaturaMaterie(null);
		}
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		BeautyContestView beautyContestView = (BeautyContestView) view;
		List<ProfessionistaEsternoView> listaProfessionistaEsternoViews = null;
		listaProfessionistaEsternoViews = professionistaEsternoService.leggiProfessionistiAbilitatiBeautyContest();
		beautyContestView.setListaProfessionistiEsterni(listaProfessionistaEsternoViews);
	}


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new BeautyContestValidator());

		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
