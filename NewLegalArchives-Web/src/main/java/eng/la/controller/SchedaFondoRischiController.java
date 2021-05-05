package eng.la.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eng.la.business.mail.EmailNotificationServiceImpl;
import eng.la.persistence.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.json.JSONArray;
import org.json.JSONObject;
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
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.FascicoloService;
import eng.la.business.ReportingService;
import eng.la.business.RischioSoccombenzaService;
import eng.la.business.SchedaFondoRischiService;
import eng.la.business.StoricoSchedaFondoRischiService;
import eng.la.business.TipologiaSchedaFrService;
import eng.la.business.UtenteService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.business.websocket.WebSocketPublisher;
import eng.la.business.workflow.SchedaFondoRischiWfService;
import eng.la.model.Controparte;
import eng.la.model.Incarico;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.RFascicoloGiudizio;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.SchedaFondoRischi;
import eng.la.model.SchedaFondoRischiWf;
import eng.la.model.StoricoSchedaFondoRischi;
import eng.la.model.rest.Event;
import eng.la.model.rest.RicercaSchedaFondoRischiRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.rest.SchedaFondoRischiRest;
import eng.la.model.rest.StepWFRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.RischioSoccombenzaView;
import eng.la.model.view.SchedaFondoRischiView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.StatoSchedaFondoRischiView;
import eng.la.model.view.StoricoSchedaFondoRischiView;
import eng.la.model.view.TipologiaSchedaFrView;
import eng.la.model.view.UtenteView;
import eng.la.presentation.validator.SchedaFondoRischiValidator;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.DateUtil;
import eng.la.util.DateUtil2;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("schedaFondoRischiController")
@SessionAttributes("schedaFondoRischiView")
public class SchedaFondoRischiController extends BaseController {
	private static final Logger logger = Logger.getLogger(SchedaFondoRischiController.class);
	private static final String MODEL_VIEW_NOME = "schedaFondoRischiView";
	private static final String PAGINA_FORM_PATH = "schedaFondoRischi/formSchedaFondoRischi";  
	private static final String PAGINA_DETTAGLIO_PATH = "schedaFondoRischi/formSchedaFondoRischiDettaglio";
	private static final String PAGINA_RICERCA_PATH = "schedaFondoRischi/cercaSchedaFondoRischi";
	private static final String PAGINA_FORM_PFR_PATH = "schedaFondoRischi/formPFR";
	private static final String PAGINA_AZIONI_CERCA_SCHEDA_FONDO_RISCHI = "schedaFondoRischi/azioniCercaSchedaFondoRischi"; 
	private static final String PAGINA_AZIONI_SCHEDA_FONDO_RISCHI = "schedaFondoRischi/azioniSchedaFondoRischi";
	private static final String PAGINA_AZIONI_COLORATE_CERCA_SCHEDA_FONDO_RISCHI = "schedaFondoRischi/azioniColorateCercaSchedaFondoRischi"; 
	private static final String PAGINA_AZIONI_COLORATE_SCHEDA_FONDO_RISCHI = "schedaFondoRischi/azioniColorateSchedaFondoRischi";
	private static final String PAGINA_VISUALIZZA_STAMPA ="schedaFondoRischi/visualizzaSchedaFondoRischiStampa";
	private static final String PAGINA_VISUALIZZA_STAMPA_MODIFICHE ="schedaFondoRischi/visualizzaSchedaFondoRischiStampaModifiche";

	@Autowired
	private FascicoloService fascicoloService;

	@Autowired
	private TipologiaSchedaFrService tipologiaSchedaFrService;

	@Autowired
	private RischioSoccombenzaService rischioSoccombenzaService;

	@Autowired
	private SchedaFondoRischiService schedaFondoRischiService;

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	@Autowired
	private SchedaFondoRischiWfService schedaFondoRischiWfService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private EmailNotificationService emailNotificationService;

	@Autowired
	ReportingService reportingService;
	
	@Autowired
	private StoricoSchedaFondoRischiService storicoSchedaFondoRischiService;

	@RequestMapping("/schedaFondoRischi/modifica")
	public String modificaSchedaFondoRischi(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		SchedaFondoRischiView view = new SchedaFondoRischiView();

		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);

		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			SchedaFondoRischiView schedaFondoRischiSalvata = (SchedaFondoRischiView) schedaFondoRischiService.leggi(id);
			if (schedaFondoRischiSalvata != null && schedaFondoRischiSalvata.getVo() != null) {
				super.caricaListe(view, locale);
				popolaFormDaVo(view, schedaFondoRischiSalvata.getVo(), locale, utenteView, true);

			} else {
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}
		logger.debug("@@DDS modificaSchedaFondoRischi " );
		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action": PAGINA_FORM_PATH;
	}

	@RequestMapping("/schedaFondoRischi/dettaglio")
	public String dettaglioSchedaFondoRischi(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		SchedaFondoRischiView view = new SchedaFondoRischiView();

		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			SchedaFondoRischiView schedaFondoRischiSalvata = (SchedaFondoRischiView) schedaFondoRischiService.leggi(id);
			if (schedaFondoRischiSalvata != null && schedaFondoRischiSalvata.getVo() != null) {
				popolaFormDaVo(view, schedaFondoRischiSalvata.getVo(), locale, utenteView, true);

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

	private void popolaFormDaVo(SchedaFondoRischiView view, SchedaFondoRischi vo, Locale locale, UtenteView utenteConnesso, boolean alleagti) throws Throwable {

		view.setSchedaFondoRischiId(vo.getId());

		if( vo.getDataCreazione() != null ){
			view.setDataCreazione(DateUtil.formattaData(vo.getDataCreazione().getTime()));	
		}

		if( vo.getTipologiaSchedaFr()!= null ){
			view.setTipologiaSchedaFondoRischiCode(vo.getTipologiaSchedaFr().getCodGruppoLingua());
			TipologiaSchedaFrView tipologiaSelezionata = new TipologiaSchedaFrView();
			tipologiaSelezionata.setVo(vo.getTipologiaSchedaFr());
			view.setTipologiaSchedaSelezionata(tipologiaSelezionata);
		}


		FascicoloView fascicoloView = fascicoloService.leggiTutti(vo.getFascicolo().getId(), FetchMode.JOIN);
		view.setFascicoloRiferimento( fascicoloView );
		boolean isPenale = fascicoloView.getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		view.setPenale(isPenale);

		view.setNomeFascicolo(vo.getFascicolo().getNome());

		if (fascicoloView.getVo().getContropartes() != null) {
			Collection<Controparte> listaControparte = fascicoloView.getVo().getContropartes();					
			List<String> controparteDesc = new ArrayList<String>();

			for (Controparte controparte : listaControparte) {

				controparteDesc.add(controparte.getNome() != null ? controparte.getNome() : "");
			}
			view.setListaControparteDesc(controparteDesc);
		}
		else if(fascicoloView.getVo().getTitolo() != null){
			List<String> controparteDesc = new ArrayList<String>();
			controparteDesc.add(fascicoloView.getVo().getTitolo());
			view.setListaControparteDesc(controparteDesc);
		}

		if (fascicoloView.getVo().getRFascicoloSocietas() != null) {
			Collection<RFascicoloSocieta> listaFascicoloSocieta = fascicoloView.getVo().getRFascicoloSocietas();					
			List<String> societaAddebitoAggiunteDesc = new ArrayList<String>();
			List<SocietaView> societaAddebitoAggiunteDescLet= new ArrayList<SocietaView>();

			for (RFascicoloSocieta societa : listaFascicoloSocieta) {

				if (societa.getTipologiaSocieta().equals(SOCIETA_TIPOLOGIA_ADDEBITO)) {
					societaAddebitoAggiunteDesc.add(societa.getSocieta().getNome());
					SocietaView soc = new SocietaView();
					soc.setNome(societa.getSocieta().getNome()+" - "+societa.getSocieta().getIndirizzo()+
							" - "+societa.getSocieta().getCap()+" - "+societa.getSocieta().getCitta()+""
							+ " - CF: "+societa.getSocieta().getCodiceFiscale()
							+ " - PIVA: "+societa.getSocieta().getPartitaIva()
							);
					soc.setRagioneSociale(societa.getSocieta().getRagioneSociale());
					soc.setIdSocieta(societa.getId());
					soc.setSitoWeb(societa.getSocieta().getSitoWeb());
					soc.setPieDiPagina(societa.getSocieta().getPieDiPagina());
					societaAddebitoAggiunteDescLet.add(soc);
				}
			}

			view.setListaSocietaAddebitoAggiunteDesc(societaAddebitoAggiunteDesc);
			view.setListaSocieta(societaAddebitoAggiunteDescLet);
		}

		StatoSchedaFondoRischiView statoSchedaFondoRischiView = anagraficaStatiTipiService.leggiStatoSchedaFondoRischi(vo.getStatoSchedaFondoRischi().getCodGruppoLingua(), locale.getLanguage().toUpperCase());		
		view.setStatoSchedaFondoRischi(statoSchedaFondoRischiView.getVo().getDescrizione());
		view.setStatoSchedaFondoRischiCode(statoSchedaFondoRischiView.getVo().getCodGruppoLingua());

		if(view.getStatoSchedaFondoRischiCode().equals("A"))
			view.setDisabled(true);
		else
			view.setDisabled(false);

		if (fascicoloView.getVo().getRFascicoloGiudizios() != null) {
			Collection<RFascicoloGiudizio> listaFascicoloGiudizio = fascicoloView.getVo().getRFascicoloGiudizios();					
			List<String> giudiziDesc = new ArrayList<String>();
			List<String> organoGiudicanteDesc = new ArrayList<String>();

			for (RFascicoloGiudizio giudizio : listaFascicoloGiudizio) {

				giudiziDesc.add(giudizio.getGiudizio() != null ? giudizio.getGiudizio().getDescrizione() : "");
				organoGiudicanteDesc.add(giudizio.getOrganoGiudicante() != null ? giudizio.getOrganoGiudicante().getNome() : "");
			}
			view.setListaGiudizioDesc(giudiziDesc);
			view.setListaOrganoGiudicanteDesc(organoGiudicanteDesc);
		}

		view.setValoreDomanda(vo.getValoreDomanda().doubleValue());


		/** Professionisti Esterni associati agli incarichi del fascicolo di riferimento **/
		if (fascicoloView.getVo().getIncaricos() != null) {
			Collection<Incarico> listaIncarico = fascicoloView.getVo().getIncaricos();
			List<ProfessionistaEsternoView> listaProfessionistiEsterni = new ArrayList<ProfessionistaEsternoView>();

			for(Incarico incarico: listaIncarico){

				if(incarico != null){

					ProfessionistaEsterno profEst = incarico.getProfessionistaEsterno();

					if(profEst != null){
						
						boolean exists = false;
						for(ProfessionistaEsternoView professionistaEsternoView : listaProfessionistiEsterni){
							
							if(!Objects.equals(professionistaEsternoView.getVo().getCognomeNome(), profEst.getCognomeNome())){
								exists = true;
								break;
							}
						}
						if(!exists){
							ProfessionistaEsternoView profEstView = new ProfessionistaEsternoView();
							profEstView.setVo(profEst);
							listaProfessionistiEsterni.add(profEstView);
						}
					}
				}
			}
			view.setListaProfessionistiEsterni(listaProfessionistiEsterni);
		}

		view.setTestoEsplicativo(vo.getTestoEsplicativo());

		if( vo.getDataAutorizzazione() != null ){
			view.setDataAutorizzazione(DateUtil.formattaData(vo.getDataAutorizzazione().getTime()));	
		}

		if( vo.getDataRichiestaAutorScheda() != null ){
			view.setDataRichiestaAutorizzazione(DateUtil.formattaData(vo.getDataRichiestaAutorScheda().getTime()));	
		} 

		if( vo.getRischioSoccombenza()!= null ){
			view.setRischioSoccombenzaCode(vo.getRischioSoccombenza().getCodGruppoLingua());
			RischioSoccombenzaView rischioSoccombenzaSelezionata = new RischioSoccombenzaView();
			rischioSoccombenzaSelezionata.setVo(vo.getRischioSoccombenza());
			view.setRischioSoccombenzaSelezionato(rischioSoccombenzaSelezionata);
		}

		if(vo.getCoperturaAssicurativa() != null){

			if(vo.getCoperturaAssicurativa().intValue() == 0){
				view.setCoperturaAssicurativaFlag(1);
			}
			else{
				view.setCoperturaAssicurativaFlag(1);
				view.setCoperturaAssicurativa(vo.getCoperturaAssicurativa().doubleValue());
			}
		}
		else{
			view.setCoperturaAssicurativaFlag(0);
		}

		if(vo.getManleva() != null){

			if(vo.getManleva().intValue() == 0){
				view.setManlevaFlag(1);
			}
			else{
				view.setManlevaFlag(1);
				view.setManleva(vo.getManleva().doubleValue());
			}
		}
		else{
			view.setManlevaFlag(0);
		}

		if(vo.getCommessaDiInvestimento() != null){

			if(vo.getCommessaDiInvestimento().intValue() == 0){
				view.setCommessaDiInvestimentoFlag(1);
			}
			else{
				view.setCommessaDiInvestimentoFlag(1);
				view.setCommessaDiInvestimento(vo.getCommessaDiInvestimento().doubleValue());
			}
		}
		else{
			view.setCommessaDiInvestimentoFlag(0);
		}

		view.setPassivitaStimata(vo.getPassivitaStimata().doubleValue());

		view.setMotivazione(vo.getMotivazione());

		if(alleagti){
			caricaDocumentiProfessionistaEsternoFilenet(view, vo);
		}
	}

	private void caricaDocumentiProfessionistaEsternoFilenet(SchedaFondoRischiView view, SchedaFondoRischi vo) throws Throwable {		
		String nomeCartellaScheda = FileNetUtil.getSchedaFondoRischiCartella(vo.getId(), vo.getFascicolo().getDataCreazione(), vo.getFascicolo().getNome(), DateUtil.getDatayyyymmddHHmmss(vo.getDataCreazione().getTime()));
		boolean isPenale = vo.getFascicolo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		logger.info ("@@DDS caricaDocumentiProfessionistaEsternoFilenet __________________________");
		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaSchedaFondoRischi = isPenale? documentaleCryptDAO.leggiCartella(nomeCartellaScheda) : documentaleDAO.leggiCartella(nomeCartellaScheda);
		*/
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
		Folder cartellaSchedaFondoRischi = isPenale? documentaleDdsCryptDAO.leggiCartella(nomeCartellaScheda) : documentaleDdsDAO.leggiCartella(nomeCartellaScheda);

		/*@@DDS inizio commento
		DocumentSet documenti = cartellaSchedaFondoRischi.get_ContainedDocuments();
		List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
		if( documenti != null ){
			PageIterator it = documenti.pageIterator();
			while(it.nextPage()){
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for( EngineObjectImpl objDocumento : documentiArray ){
					DocumentImpl documento = (DocumentImpl)objDocumento;
					DocumentoView docView = new DocumentoView();
					docView.setNomeFile(documento.get_Name() );
					docView.setUuid(documento.get_Id().toString());
					listaDocumenti.add(docView);
				}
			}
			view.setListaAllegatiLegaleEsterno(listaDocumenti);

		}

		 */
		List<Document> documenti = isPenale? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaScheda) : documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaScheda);
		List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
		if( documenti != null ){
				for( Document documento:documenti){

					DocumentoView docView = new DocumentoView();
					docView.setNomeFile(documento.getContents().get(0).getContentsName() );
					docView.setUuid(documento.getId());
					listaDocumenti.add(docView);
				}

			view.setListaAllegatiLegaleEsterno(listaDocumenti);

		}
		logger.info ("@@DDS listaDocumenti __________________________" + view);
	}

	@RequestMapping(value = "/schedaFondoRischi/salva", method = RequestMethod.POST)
	public String salvaSchedaFondoRischi(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated SchedaFondoRischiView schedaFondoRischiView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		String token=request.getParameter("CSRFToken");

		try {

			if (schedaFondoRischiView.getOp() != null && !schedaFondoRischiView.getOp().equals("salvaSchedaFondoRischi")) {
				String ritorno = invocaMetodoDaReflection(schedaFondoRischiView, bindingResult, locale, model, request,
						response, this);

				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			preparaPerSalvataggio(schedaFondoRischiView, bindingResult);

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			long schedaFondoRischiId = 0;
			boolean isNew = false;
			if (schedaFondoRischiView.getSchedaFondoRischiId() == null || schedaFondoRischiView.getSchedaFondoRischiId() == 0) {
				SchedaFondoRischiView schedaFondoRischiSalvata = schedaFondoRischiService.inserisci(schedaFondoRischiView);
				schedaFondoRischiId = schedaFondoRischiSalvata.getVo().getId();

				/** Aggiorna il fascicolo di riferimento con la scheda fondo rischi appena creata e lo setta a rilevante**/
				long idFascicolo = schedaFondoRischiSalvata.getVo().getFascicolo().getId();
				FascicoloView fascicoloView = fascicoloService.leggi(idFascicolo);
				fascicoloView.getVo().setSchedaFondoRischi(schedaFondoRischiSalvata.getVo());
				fascicoloView.getVo().setRilevante(TRUE_CHAR);
				fascicoloService.aggiornaFascicolo(fascicoloView);

				isNew = true;
			} else {

				/** Controlla che siano state effettuate modifiche, altrimenti non effettua alcuna operazione **/
				schedaFondoRischiId = schedaFondoRischiView.getVo().getId();
				SchedaFondoRischiView schedaDB = schedaFondoRischiService.leggi(schedaFondoRischiView.getSchedaFondoRischiId());

				boolean modificata = confrontaSchedeFondoRischi(schedaDB, schedaFondoRischiView);

				if(modificata){

					/** In caso di modifica nel trimestre successivo si salva nello storico la versione attuale prima di modificarla **/

					Date dataRiferimento = schedaFondoRischiView.getVo().getDataModifica();

					if(dataRiferimento == null){
						dataRiferimento = schedaFondoRischiView.getVo().getDataCreazione();
					}

					if(dataRiferimento != null){

						boolean stessoTrimestre = DateUtil2.stessoTrimestre(dataRiferimento, new Date());

						if(!stessoTrimestre){
							
							String delayPFR = schedaFondoRischiService.leggiDelay();
							int delay = Integer.parseInt(delayPFR);
							
							Date nowDelayed = DateUtil2.subtractGiorni(new Date(), delay);
							boolean stessoTrimestreDelayed = DateUtil2.stessoTrimestre(dataRiferimento, nowDelayed);
							
							if(!stessoTrimestreDelayed){
								
								StoricoSchedaFondoRischiView storicoSchedaFondoRischiView = new StoricoSchedaFondoRischiView();
								preparaPerSalvataggioStorico(storicoSchedaFondoRischiView, schedaFondoRischiView.getSchedaFondoRischiId());
								
								if(storicoSchedaFondoRischiView.getVo() != null){
									schedaFondoRischiService.inserisciStorico(storicoSchedaFondoRischiView);
								}
							}
						}
					}

					schedaFondoRischiService.modifica(schedaFondoRischiView);

					if(schedaFondoRischiView.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO)){

						UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

						if(utenteView != null){

							schedaFondoRischiWfService.riportaInBozzaWorkflow(schedaFondoRischiId, utenteView.getVo().getUseridUtil());
						}
					}
				}
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
			if( isNew ){
				return "redirect:modifica.action?id=" + schedaFondoRischiId+"&CSRFToken="+token;
			}
			return "redirect:dettaglio.action?id=" + schedaFondoRischiId+"&CSRFToken="+token;
		} catch (Throwable e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}

	@RequestMapping(value="/schedaFondoRischi/eliminaSchedaFondoRischi", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaSchedaFondoRischi(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			SchedaFondoRischiView schedaFondoRischiView = schedaFondoRischiService.leggi(id);
			schedaFondoRischiService.cancella(schedaFondoRischiView);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");

			/** Aggiorna il fascicolo di riferimento settandolo a non rilevante **/
			long idFascicolo = schedaFondoRischiView.getVo().getFascicolo().getId();
			FascicoloView fascicoloView = fascicoloService.leggi(idFascicolo);
			fascicoloView.getVo().setRilevante(FALSE_CHAR);
			fascicoloService.aggiornaFascicolo(fascicoloView);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	private void preparaPerSalvataggio(SchedaFondoRischiView view, BindingResult bindingResult)throws Throwable {
		SchedaFondoRischi vo = new SchedaFondoRischi();
		SchedaFondoRischiView oldSchedaFondoRischiView = null;

		if (view.getSchedaFondoRischiId() != null) {

			oldSchedaFondoRischiView = schedaFondoRischiService.leggi(view.getSchedaFondoRischiId());
			vo.setDataCreazione(oldSchedaFondoRischiView.getVo().getDataCreazione());
			vo.setStatoSchedaFondoRischi(oldSchedaFondoRischiView.getVo().getStatoSchedaFondoRischi());
			vo.setId(view.getSchedaFondoRischiId());
		}

		if( view.getDataAutorizzazione() != null && DateUtil.isData(view.getDataAutorizzazione())){
			vo.setDataAutorizzazione(DateUtil.toDate(view.getDataAutorizzazione()));	
		}

		if( view.getDataRichiestaAutorizzazione() != null && DateUtil.isData(view.getDataRichiestaAutorizzazione())){
			vo.setDataRichiestaAutorScheda(DateUtil.toDate(view.getDataRichiestaAutorizzazione()));	
		}

		Locale linguaItaliana = Locale.ITALIAN;

		if (view.getTipologiaSchedaFondoRischiCode() != null && view.getTipologiaSchedaFondoRischiCode().trim().length() > 0) {
			TipologiaSchedaFrView tipologiaSchedaFrView = tipologiaSchedaFrService.leggi(view.getTipologiaSchedaFondoRischiCode(), linguaItaliana);
			vo.setTipologiaSchedaFr(tipologiaSchedaFrView.getVo());
		}

		//vo.setValoreDomanda(new BigDecimal(view.getValoreDomanda()));
		vo.setValoreDomanda(new BigDecimal(view.getValoreDomanda()));
		//vo.setValoreDomanda(new DecimalFormat(view.getValoreDomanda()));
		/*NumberFormat formatter = new DecimalFormat("##########.##");  
		String f = formatter.format(view.getValoreDomanda()); 
		vo.setValoreDomanda(f);*/
		
		
		vo.setFascicolo(view.getFascicoloRiferimento().getVo());

		vo.setTestoEsplicativo(view.getTestoEsplicativo());

		if (view.getRischioSoccombenzaCode() != null && view.getRischioSoccombenzaCode().trim().length() > 0) {
			RischioSoccombenzaView rischioSoccombenzaView = rischioSoccombenzaService.leggi(view.getRischioSoccombenzaCode(), linguaItaliana);
			vo.setRischioSoccombenza(rischioSoccombenzaView.getVo());
		}

		if(view.getCoperturaAssicurativaFlag() == 1){
			if(view.getCoperturaAssicurativa() != null)
				vo.setCoperturaAssicurativa(new BigDecimal(view.getCoperturaAssicurativa()));
			else
				vo.setCoperturaAssicurativa(new BigDecimal(0));
		}else
			vo.setCoperturaAssicurativa(null);

		if(view.getManlevaFlag() == 1){
			if(view.getManleva() != null)
				vo.setManleva(new BigDecimal(view.getManleva()));
			else
				vo.setManleva(new BigDecimal(0));
		}else
			vo.setManleva(null);

		if(view.getCommessaDiInvestimentoFlag() == 1){
			if(view.getCommessaDiInvestimento() != null)
				vo.setCommessaDiInvestimento(new BigDecimal(view.getCommessaDiInvestimento()));
			else
				vo.setCommessaDiInvestimento(new BigDecimal(0));
		}else
			vo.setCommessaDiInvestimento(null);

		vo.setPassivitaStimata(new BigDecimal(view.getPassivitaStimata()));

		vo.setMotivazione(view.getMotivazione());


		view.setVo(vo);
	}

	private void preparaPerSalvataggioStorico(StoricoSchedaFondoRischiView storicoSchedaFondoRischiView, Long idSchedaFondoRischiOriginale)throws Throwable {

		StoricoSchedaFondoRischi vo = new StoricoSchedaFondoRischi();
		SchedaFondoRischiView originalSchedaFondoRischiView = null;

		if( idSchedaFondoRischiOriginale != null ){
			originalSchedaFondoRischiView = schedaFondoRischiService.leggi(idSchedaFondoRischiOriginale);

			if(originalSchedaFondoRischiView != null){

				if(originalSchedaFondoRischiView.getVo() != null){

					caricaDocumentiProfessionistaEsternoFilenet(originalSchedaFondoRischiView, originalSchedaFondoRischiView.getVo());

					vo.setDataModifica(new Date());
					vo.setSchedaFondoRischi(originalSchedaFondoRischiView.getVo());
					vo.setTipologiaSchedaFr(originalSchedaFondoRischiView.getVo().getTipologiaSchedaFr());
					vo.setRischioSoccombenza(originalSchedaFondoRischiView.getVo().getRischioSoccombenza());
					vo.setStatoSchedaFondoRischi(originalSchedaFondoRischiView.getVo().getStatoSchedaFondoRischi());
					vo.setCommessaDiInvestimento(originalSchedaFondoRischiView.getVo().getCommessaDiInvestimento());
					vo.setCoperturaAssicurativa(originalSchedaFondoRischiView.getVo().getCoperturaAssicurativa());
					vo.setDataAutorizzazione(originalSchedaFondoRischiView.getVo().getDataAutorizzazione());
					vo.setDataCancellazione(originalSchedaFondoRischiView.getVo().getDataCancellazione());
					vo.setDataRichiestaAutorScheda(originalSchedaFondoRischiView.getVo().getDataRichiestaAutorScheda());
					vo.setManleva(originalSchedaFondoRischiView.getVo().getManleva());
					vo.setMotivazione(originalSchedaFondoRischiView.getVo().getMotivazione());
					vo.setPassivitaStimata(originalSchedaFondoRischiView.getVo().getPassivitaStimata());
					vo.setTestoEsplicativo(originalSchedaFondoRischiView.getVo().getTestoEsplicativo());
					vo.setValoreDomanda(originalSchedaFondoRischiView.getVo().getValoreDomanda());

					if(originalSchedaFondoRischiView.getListaAllegatiLegaleEsterno() != null 
							&& !originalSchedaFondoRischiView.getListaAllegatiLegaleEsterno().isEmpty()){

						// EVO STORICO SCHEDA FONDO RISCHI
						/*String allegati = "";
						for(DocumentoView doc : originalSchedaFondoRischiView.getListaAllegatiLegaleEsterno()){
							allegati += doc.getNomeFile() + " ";
						}*/
						
						String allegati = "Allegati_Legale_Esterno.noextension";
						vo.setAllegato(allegati);
					}
				}
			}
		}
		storicoSchedaFondoRischiView.setVo(vo);
	}

	@RequestMapping("/schedaFondoRischi/crea")
	public String creaSchedaFondoRischi(HttpServletRequest request, Model model, Locale locale) {
		SchedaFondoRischiView view = new SchedaFondoRischiView();
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {
			if( request.getParameter("fascicoloId") != null ){
				long fasicoloId = NumberUtils.toLong(request.getParameter("fascicoloId"));
				FascicoloView fascicoloView = fascicoloService.leggi(fasicoloId,FetchMode.JOIN);
				view.setFascicoloRiferimento(fascicoloView);
				view.setNomeFascicolo(fascicoloView.getVo().getNome());
				view.setDataCreazione(DateUtil.formattaData(new Date().getTime()));
				super.caricaListe(view, locale);


				/** Professionisti Esterni associati agli incarichi del fascicolo di riferimento **/
				if (fascicoloView.getVo().getIncaricos() != null) {
					Collection<Incarico> listaIncarico = fascicoloView.getVo().getIncaricos();
					List<ProfessionistaEsternoView> listaProfessionistiEsterni = new ArrayList<ProfessionistaEsternoView>();

					for(Incarico incarico: listaIncarico){

						if(incarico != null){

							ProfessionistaEsterno profEst = incarico.getProfessionistaEsterno();

							if(profEst != null){
								
								boolean exists = false;
								for(ProfessionistaEsternoView professionistaEsternoView : listaProfessionistiEsterni){
									
									if(!Objects.equals(professionistaEsternoView.getVo().getCognomeNome(), profEst.getCognomeNome())){
										exists = true;
										break;
									}
								}
								if(!exists){
									ProfessionistaEsternoView profEstView = new ProfessionistaEsternoView();
									profEstView.setVo(profEst);
									listaProfessionistiEsterni.add(profEstView);
								}
							}
						}
					}
					view.setListaProfessionistiEsterni(listaProfessionistiEsterni);
				}

				if (fascicoloView.getVo().getContropartes() != null) {
					Collection<Controparte> listaControparte = fascicoloView.getVo().getContropartes();					
					List<String> controparteDesc = new ArrayList<String>();

					for (Controparte controparte : listaControparte) {

						controparteDesc.add(controparte.getNome() != null ? controparte.getNome() : "");
					}
					view.setListaControparteDesc(controparteDesc);
				}
				else if(fascicoloView.getVo().getTitolo() != null){
					List<String> controparteDesc = new ArrayList<String>();
					controparteDesc.add(fascicoloView.getVo().getTitolo());
					view.setListaControparteDesc(controparteDesc);
				}

				/** Societ� associate al fascicolo di riferimento **/
				if (fascicoloView.getVo().getRFascicoloSocietas() != null) {
					Collection<RFascicoloSocieta> listaFascicoloSocieta = fascicoloView.getVo().getRFascicoloSocietas();					
					List<String> societaAddebitoAggiunteDesc = new ArrayList<String>();

					for (RFascicoloSocieta societa : listaFascicoloSocieta) {

						if (societa.getTipologiaSocieta().equals(SOCIETA_TIPOLOGIA_ADDEBITO)) {
							societaAddebitoAggiunteDesc.add(societa.getSocieta().getNome());
						}
					}
					view.setListaSocietaAddebitoAggiunteDesc(societaAddebitoAggiunteDesc);
				}

				/** Giudizio e Organo Giudicante associati al fascicolo di riferimento **/
				if (fascicoloView.getVo().getRFascicoloGiudizios() != null) {
					Collection<RFascicoloGiudizio> listaFascicoloGiudizio = fascicoloView.getVo().getRFascicoloGiudizios();					
					List<String> listaGiudizio = new ArrayList<String>();
					List<String> listaOrganoGiudicante = new ArrayList<String>();

					for (RFascicoloGiudizio giudizio : listaFascicoloGiudizio) {
						listaGiudizio.add(giudizio.getGiudizio().getDescrizione());

						if(giudizio.getOrganoGiudicante() != null){
							listaOrganoGiudicante.add(giudizio.getOrganoGiudicante().getNome());
						}
					}
					view.setListaGiudizioDesc(listaGiudizio);
					view.setListaOrganoGiudicanteDesc(listaOrganoGiudicante);
				}
			} 
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		} 

		view.setVo(new SchedaFondoRischi()); 

		model.addAttribute(MODEL_VIEW_NOME, view);
		return  model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_FORM_PATH;
	}


	@RequestMapping(value = "/schedaFondoRischi/uploadAllegatoProfessionista", method = RequestMethod.POST)
	public String uploadAllegatoGenerico( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) SchedaFondoRischiView schedaFondoRischiView ) {  
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {
			logger.debug("@@DDS upload " + file.getOriginalFilename() );
			File fileTmp = File.createTempFile("allegatoProfessionista", "___" + file.getOriginalFilename() );
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();

			documentoView.setUuid("" +( schedaFondoRischiView.getListaAllegatiLegaleEsterno() == null || schedaFondoRischiView.getListaAllegatiLegaleEsterno().size() == 0 ?  1 : schedaFondoRischiView.getListaAllegatiLegaleEsterno().size()+1));
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			List<DocumentoView> allegatiProfessionista = schedaFondoRischiView.getListaAllegatiLegaleEsterno()==null?new ArrayList<DocumentoView>():schedaFondoRischiView.getListaAllegatiLegaleEsterno();
			allegatiProfessionista.add(documentoView);
			schedaFondoRischiView.setListaAllegatiLegaleEsterno(allegatiProfessionista);
			logger.debug("@@DDS Upload allegato generico + allegatiGenerici " + allegatiProfessionista.size());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "schedaFondoRischi/allegatiProfessionista";
	}


	@RequestMapping(value = "/schedaFondoRischi/rimuoviAllegatoProfessionista", method = RequestMethod.POST)
	public String rimuoviAllegatoProfessionista( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) SchedaFondoRischiView schedaFondoRischiView ) {  
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {   
			String uuid = request.getParameter("uuid"); 
			if( uuid == null  ){
				throw new RuntimeException("uuid non pu� essere null");
			}

			List<DocumentoView> allegatiGenericiOld = schedaFondoRischiView.getListaAllegatiLegaleEsterno();
			List<DocumentoView> allegatiGenerici = new ArrayList<DocumentoView>();
			allegatiGenerici.addAll(allegatiGenericiOld);
			Set<String> allegatiDaRimuovere = schedaFondoRischiView.getAllegatiDaRimuovereUuid() == null ? new HashSet<String>():schedaFondoRischiView.getAllegatiDaRimuovereUuid();
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
			schedaFondoRischiView.setListaAllegatiLegaleEsterno(allegatiGenerici);
			schedaFondoRischiView.setAllegatiDaRimuovereUuid(allegatiDaRimuovere);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "schedaFondoRischi/allegatiProfessionista";
	}

	@RequestMapping(value="/schedaFondoRischi/avviaWorkFlowScheda", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest avviaWorkFlowScheda(@RequestParam("id") long id, @RequestParam("matricola_dest") String matricola_dest, 
			HttpServletRequest request) {
		//DARIO AGGIUNTO @RequestParam("matricola_dest") String matricola_dest
		
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if( utenteView != null ){
				SchedaFondoRischiView view = schedaFondoRischiService.leggi(id);
				if( view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_BOZZA)){

					//DARIO C **************************************************************************************************
					//boolean ret = schedaFondoRischiWfService.avviaWorkflow(id, utenteView.getVo().getUseridUtil());
					boolean ret = schedaFondoRischiWfService.avviaWorkflow(id, utenteView.getVo().getUseridUtil(),matricola_dest);
					//**********************************************************************************************************
					
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
					if(ret){
						//invio la notifica

						StepWFRest stepSuccessivo = new StepWFRest();
						stepSuccessivo.setId(0);

						
						List<UtenteView> assegnatari = utenteService.leggiAssegnatarioWfSchedaFondoRischi(id);
						
						if(assegnatari != null && !assegnatari.isEmpty()){

							//MS controllo Assegnatario Assente
							assegnatari=checkAssegnatarioPresente(assegnatari, id);

							for(UtenteView assegnatario : assegnatari){

								Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
								WebSocketPublisher.getInstance().publishEvent(event); 
							}

						}
						//invio la e-mail
						try{
							//DARIO C ********************************************************************************************************************************************************************************
							//emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.SCHEDA_FONDO_RISCHI, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil());
							emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.SCHEDA_FONDO_RISCHI, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil(),matricola_dest);
							//****************************************************************************************************************************************************************************************
						}
						catch (Exception e) { 
							System.out.println("Errore in invio e-mail"+ e);
						}
					}
				}else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.schedaFondoRischi.non.valido");
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
	private List<UtenteView> checkAssegnatarioPresente(List<UtenteView> assegnatari,long idEntita) throws Throwable{
		boolean esito=false;
		List<UtenteView> result = new ArrayList<UtenteView>();

		for(UtenteView assegnatario : assegnatari){

			if(assegnatario.getVo()!=null && assegnatario.getVo().getAssente().equals("F")){
				result.add(assegnatario);
			}
		}

		if(result.isEmpty()){
			SchedaFondoRischiWf wf = schedaFondoRischiWfService.leggiWorkflowInCorsoNotView(idEntita);
			if(wf!=null)
				esito=schedaFondoRischiWfService.avanzaWorkflow(wf.getId(), assegnatari.get(0).getVo().getUseridUtil());

			if(esito){

				List<UtenteView> assegnatariNew = utenteService.leggiAssegnatarioWfSchedaFondoRischi(idEntita);
				if(assegnatariNew==null) 
					return result;

				if(assegnatariNew != null && !assegnatariNew.isEmpty()){

					for(UtenteView assegnatarioNew : assegnatariNew){

						if(assegnatarioNew.getVo()!=null && assegnatarioNew.getVo().getAssente().equals("F")){
							result.add(assegnatarioNew);
						}
					}

					if(result.isEmpty()){
						if(!utenteService.isAssegnatarioManualeCorrenteStandard(wf.getId(),CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI)){	
							return	checkAssegnatarioPresente(assegnatariNew,idEntita);	
						}
					}else{ 
						return result;
					}
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "/schedaFondoRischi/visualizzaPFR", method = RequestMethod.GET)
	public String visualizzaPFR(HttpServletRequest request, Model model, Locale locale) {

		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);

		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			try {
				SchedaFondoRischiView view = new SchedaFondoRischiView();
				view.setListaStatoSchedaFondoRischi(anagraficaStatiTipiService.leggiStatiSchedaFondoRischi(locale.getLanguage().toUpperCase()));
				view.setListaTipologiaScheda(tipologiaSchedaFrService.leggi(locale));
				view.setListaRischioSoccombenza(rischioSoccombenzaService.leggi(locale));
				model.addAttribute(MODEL_VIEW_NOME, view);
				return PAGINA_FORM_PFR_PATH;
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return null;
		}
		else {
			return null;
		}

	}

	@RequestMapping(value = "/schedaFondoRischi/cercaPFR", method = RequestMethod.GET)
	public String cercaPFR(HttpServletRequest request, Model model, Locale locale) {

		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {
			SchedaFondoRischiView view = new SchedaFondoRischiView();
			view.setListaStatoSchedaFondoRischi(anagraficaStatiTipiService.leggiStatiSchedaFondoRischi(locale.getLanguage().toUpperCase()));
			view.setListaTipologiaScheda(tipologiaSchedaFrService.leggi(locale));
			view.setListaRischioSoccombenza(rischioSoccombenzaService.leggi(locale));
			model.addAttribute(MODEL_VIEW_NOME, view);
			return PAGINA_RICERCA_PATH;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/schedaFondoRischi/ricerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaSchedaFondoRischiRest cercaSchedaFondoRischi(HttpServletRequest request, Locale locale) {


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
			
			if(!nomeFascicolo.isEmpty())
				nomeFascicolo = nomeFascicolo.trim();
			
			String statoSchedaCode = request.getParameter("statoSchedaFondoRischiCode") == null ? "" : request.getParameter("statoSchedaFondoRischiCode");
			String tipologiaCode = request.getParameter("tipologiaCode") == null ? "" : request.getParameter("tipologiaCode");
			String rischioSoccombenzaCode = request.getParameter("rischioSoccombenzaCode") == null ? "" : request.getParameter("rischioSoccombenzaCode");
			String dal = request.getParameter("dal") == null ? "" : URLDecoder.decode(request.getParameter("dal"),"UTF-8");
			String al = request.getParameter("al") == null ? "" :  URLDecoder.decode(request.getParameter("al"),"UTF-8");

			ListaPaginata<SchedaFondoRischiView> lista = (ListaPaginata<SchedaFondoRischiView>) schedaFondoRischiService.cerca( dal, al, statoSchedaCode, tipologiaCode, rischioSoccombenzaCode, nomeFascicolo, numElementiPerPagina, numeroPagina,
					ordinamento, tipoOrdinamento);
			RicercaSchedaFondoRischiRest ricercaModelRest = new RicercaSchedaFondoRischiRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<SchedaFondoRischiRest> rows = new ArrayList<SchedaFondoRischiRest>();
			for (SchedaFondoRischiView view : lista) {
				SchedaFondoRischiRest schedaFondoRischiRest = new SchedaFondoRischiRest();
				schedaFondoRischiRest.setId(view.getVo().getId());
				schedaFondoRischiRest.setAnno(DateUtil.getAnno(view.getVo().getDataCreazione()) + "");
				schedaFondoRischiRest.setDataCreazione(DateUtil.formattaData(view.getVo().getDataCreazione().getTime()));
				schedaFondoRischiRest.setNomeFascicolo(view.getVo().getFascicolo().getNome());

				long idFascicolo = view.getVo().getFascicolo().getId();
				FascicoloView fascicoloView = fascicoloService.leggiTutti(idFascicolo, FetchMode.JOIN);

				schedaFondoRischiRest.setControparte("");

				if(fascicoloView.getVo().getContropartes() != null && !fascicoloView.getVo().getContropartes().isEmpty()){

					String controparti = "";

					if(fascicoloView.getVo().getContropartes().size() == 1){

						for(Controparte contro : fascicoloView.getVo().getContropartes()){
							controparti += contro.getNome();
						}
					}else{

						for(Controparte contro : fascicoloView.getVo().getContropartes()){
							controparti += contro.getNome() + ", ";
						}
					}
					schedaFondoRischiRest.setControparte(controparti);
				}

				StatoSchedaFondoRischiView statoSchedaFondoRischiView = anagraficaStatiTipiService.leggiStatoSchedaFondoRischi(view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua(), locale.getLanguage().toUpperCase());
				schedaFondoRischiRest.setStato(statoSchedaFondoRischiView.getVo().getDescrizione());

				TipologiaSchedaFrView tipologiaSchedaFrView = tipologiaSchedaFrService.leggi(view.getVo().getTipologiaSchedaFr().getCodGruppoLingua(), locale);
				schedaFondoRischiRest.setTipologia(tipologiaSchedaFrView.getVo().getDescrizione());

				RischioSoccombenzaView rischioSoccombenzaView = rischioSoccombenzaService.leggi(view.getVo().getRischioSoccombenza().getCodGruppoLingua(), locale);
				schedaFondoRischiRest.setRischioSoccombenza(rischioSoccombenzaView.getVo().getDescrizione());

				String assegnatario="-"; 
				if(view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equalsIgnoreCase("APAP")
						||view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equalsIgnoreCase("APAP1")
						||view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equalsIgnoreCase("AAUT")
						||view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equalsIgnoreCase("APAP2")){
					List<UtenteView> utentiV=utenteService.leggiAssegnatarioWfSchedaFondoRischi(view.getVo().getId());

					if(utentiV != null && !utentiV.isEmpty()){

						UtenteView utenteV = utentiV.get(0);
						assegnatario=utenteV == null ? "N.D." : utenteV.getVo().getNominativoUtil();
					}
				}

				schedaFondoRischiRest.setAzioni("<p id='containerAzioniRigaScheda" + view.getVo().getId() + "' title='"+assegnatario+"' alt='"+assegnatario+"' ></p>");
				rows.add(schedaFondoRischiRest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/schedaFondoRischi/caricaAzioniSchedaFr", method = RequestMethod.POST)
	public String caricaAzioniSchedaFr(@RequestParam("idScheda") long idScheda, HttpServletRequest req,
			Locale locale) {
		// engsecurity VA -- in loading
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(req);
		//removeCSRFToken(request);

		try {

			// LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI
			// SULL'INCARICO
			req.setAttribute("idScheda", idScheda);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if( req.getParameter("onlyContent") != null  ){
			return PAGINA_AZIONI_SCHEDA_FONDO_RISCHI;
		}
		return PAGINA_AZIONI_CERCA_SCHEDA_FONDO_RISCHI;
	}

	@RequestMapping(value = "/schedaFondoRischi/ricercaPFR", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaSchedaFondoRischiRest ricercaPFR(@RequestParam("trimestre") String trimestre, HttpServletRequest request, Locale locale) {

		try {  

			String limit_req=request.getParameter("limit");
			String offset_req = request.getParameter("offset");
			Long limit = Long.valueOf(limit_req);
			Long offset = Long.valueOf(offset_req);

			String trimestreSelezionato = request.getParameter("trimestreSelezionato") == null ? "" : URLDecoder.decode( request.getParameter("trimestreSelezionato"),"UTF-8");

			/** Estraggo le date di inizio e fine del trimestre selezionato **/
			List<Date> listaDateTrimestre = null;

			if(!trimestreSelezionato.isEmpty()){
				listaDateTrimestre = DateUtil2.estraiIntervalloDateTrimestre(trimestreSelezionato);
			}
			else{
				listaDateTrimestre = DateUtil2.estraiIntervalloDateTrimestre(trimestre);
			}

			Date dataInizio = listaDateTrimestre.get(0);
			Date dataFine = listaDateTrimestre.get(1);
			
			String delayPFR = schedaFondoRischiService.leggiDelay();
			int delay = Integer.parseInt(delayPFR);
			
			Date dataInizioDelayed = DateUtil2.addGiorni(dataInizio, delay);
			Date dataFineDelayed = DateUtil2.addGiorni(dataFine, delay);

			List<SchedaFondoRischiView> lista = schedaFondoRischiService.cercaNelTrimestre(dataInizioDelayed, dataFineDelayed);

			RicercaSchedaFondoRischiRest ricercaModelRest = new RicercaSchedaFondoRischiRest();
			ricercaModelRest.setTotal(lista.size());
			List<SchedaFondoRischiRest> rows = new ArrayList<SchedaFondoRischiRest>();
			for (SchedaFondoRischiView view : lista) {
				SchedaFondoRischiRest schedaFondoRischiRest = new SchedaFondoRischiRest();
				schedaFondoRischiRest.setId(view.getVo().getId());
				schedaFondoRischiRest.setAnno(DateUtil.getAnno(view.getVo().getDataCreazione()) + "");
				schedaFondoRischiRest.setDataCreazione(DateUtil.formattaData(view.getVo().getDataCreazione().getTime()));
				schedaFondoRischiRest.setNomeFascicolo(view.getVo().getFascicolo().getNome());

				long idFascicolo = view.getVo().getFascicolo().getId();
				FascicoloView fascicoloView = fascicoloService.leggiTutti(idFascicolo, FetchMode.JOIN);

				schedaFondoRischiRest.setControparte("");

				if(fascicoloView.getVo().getContropartes() != null && !fascicoloView.getVo().getContropartes().isEmpty()){

					String controparti = "";

					if(fascicoloView.getVo().getContropartes().size() == 1){

						for(Controparte contro : fascicoloView.getVo().getContropartes()){
							controparti += contro.getNome();
						}
					}else{

						for(Controparte contro : fascicoloView.getVo().getContropartes()){
							controparti += contro.getNome() + ", ";
						}
					}
					schedaFondoRischiRest.setControparte(controparti);
				}

				StatoSchedaFondoRischiView statoSchedaFondoRischiView = anagraficaStatiTipiService.leggiStatoSchedaFondoRischi(view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua(), locale.getLanguage().toUpperCase());
				schedaFondoRischiRest.setStato(statoSchedaFondoRischiView.getVo().getDescrizione());

				TipologiaSchedaFrView tipologiaSchedaFrView = tipologiaSchedaFrService.leggi(view.getVo().getTipologiaSchedaFr().getCodGruppoLingua(), locale);
				schedaFondoRischiRest.setTipologia(tipologiaSchedaFrView.getVo().getDescrizione());

				RischioSoccombenzaView rischioSoccombenzaView = rischioSoccombenzaService.leggi(view.getVo().getRischioSoccombenza().getCodGruppoLingua(), locale);
				schedaFondoRischiRest.setRischioSoccombenza(rischioSoccombenzaView.getVo().getDescrizione());

				String assegnatario="-"; 
				if(view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equalsIgnoreCase("APAP")
						||view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equalsIgnoreCase("APAP1")
						||view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equalsIgnoreCase("AAUT")
						||view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equalsIgnoreCase("APAP2")){
					List<UtenteView> utentiV=utenteService.leggiAssegnatarioWfSchedaFondoRischi(view.getVo().getId());

					if(utentiV != null && !utentiV.isEmpty()){
						UtenteView utenteV = utentiV.get(0);
						assegnatario=utenteV == null ? "N.D." : utenteV.getVo().getNominativoUtil();
					}
				}

				schedaFondoRischiRest.setAzioni("<p id='containerAzioniRigaScheda" + view.getVo().getId() + "' title='"+assegnatario+"' alt='"+assegnatario+"' ></p>");
				rows.add(schedaFondoRischiRest);
			}

			List<SchedaFondoRischiRest> rowsFinale = new ArrayList<SchedaFondoRischiRest>(0);
			for(int i=offset.intValue();i<offset.intValue()+limit;i++) {
				try {
					SchedaFondoRischiRest view=rows.get(i);
					rowsFinale.add(view);
				} catch(IndexOutOfBoundsException e) { 
					e.getMessage(); 
				}
			}

			ricercaModelRest.setRows(rowsFinale);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/schedaFondoRischi/caricaAzioniSchedaFrColore", method = RequestMethod.POST)
	public String caricaAzioniSchedaFrColore(@RequestParam("idScheda") long idScheda, @RequestParam("trimestre") String trimestre, HttpServletRequest req,
			Locale locale) {
		// engsecurity VA -- In Loading
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(req);
		//removeCSRFToken(request);
		try {
			req.setAttribute("idScheda", idScheda);
			req.setAttribute("coloreScheda", getColoreScheda(req, idScheda, trimestre) );
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if( req.getParameter("onlyContent") != null  ){
			return PAGINA_AZIONI_COLORATE_SCHEDA_FONDO_RISCHI;
		}
		return PAGINA_AZIONI_COLORATE_CERCA_SCHEDA_FONDO_RISCHI;
	}

	private String getColoreScheda(HttpServletRequest req, Long idScheda, String trimestre) {
		String coloreScheda=null;
		try { 

			SchedaFondoRischiView view = schedaFondoRischiService.leggi(idScheda);
			String delayPFR = schedaFondoRischiService.leggiDelay();
			int delay = Integer.parseInt(delayPFR);

			if(view != null && view.getVo() != null) {

				SchedaFondoRischi scheda = view.getVo();

				/** La ricerca restituisce solo schede eliminate nel trimestre dato in input, 
				 *  quindi se c'� la data di cancellazione vuol dire che � stata cancellata nel trimestre selezionato
				 *  e sar� di colore NERO **/
				if(scheda.getDataCancellazione() != null){
					coloreScheda = "Black";
				}
				else if(scheda.getDataCreazione() != null){

					/** Se � stata creata nel trimestre selezionato sar� di colore BLU**/
					if(DateUtil2.estraiTrimestreDelayedDaData(scheda.getDataCreazione(), delay).equals(trimestre)){

						coloreScheda = "Blue";
					}
					/** Se � stata creata prima del semestre selezionato 
					 * (dopo non � possibile dato che la ricerca non li restituisce) **/
					else{
						if(scheda.getDataModifica() != null){
							
							/** Se la scheda � stata modificata e NON creata nel trimestre selezionato, sar� di colore ROSSO **/
							if(DateUtil2.estraiTrimestreDelayedDaData(scheda.getDataModifica(), delay).equals(trimestre)){

								coloreScheda = "Red";
							}else{
								/** Se la scheda NON � stata modificata e NON creata nel trimestre selezionato, sar� di colore VERDE **/
								coloreScheda = "Green";
							}

						}
						/** Se la scheda non � sta MAI modificata e NON creata nel trimestre selezionato, sar� sempre di colre VERDE **/
						else{
							coloreScheda = "Green";
						}
					}
				}
			}
		} 
		catch (Throwable e) {
			e.printStackTrace();
		}
		return coloreScheda;
	}

	@RequestMapping(value="/schedaFondoRischi/riportaInBozzaWorkFlowScheda", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest riportaInBozzaWorkFlowScheda(@RequestParam("id") long id, HttpServletRequest request) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if( utenteView != null ){
				SchedaFondoRischiView view = schedaFondoRischiService.leggi(id);
				if( !view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_BOZZA) ){ 
					//recupero l'eventuale utente corrente del workflow per notificargli la rimozione dell'incarico
					List<UtenteView> assegnatari = utenteService.leggiAssegnatarioWfSchedaFondoRischi(id);

					boolean avviato = schedaFondoRischiWfService.riportaInBozzaWorkflow(id, utenteView.getVo().getUseridUtil());
					if( avviato ){
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
						if(assegnatari != null && !assegnatari.isEmpty()){
							//invio la notifica
							StepWFRest stepSuccessivo = new StepWFRest();
							stepSuccessivo.setId(0);

							for(UtenteView assegnatario : assegnatari){

								Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
								WebSocketPublisher.getInstance().publishEvent(event); 
							}
						}
					}else{
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.WARN, "messaggio.ko");
					}
				}else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.schedaFondoRischi.non.valido");
				}
			}else{
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}

			try{
				emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.SCHEDA_FONDO_RISCHI, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil());
			}
			catch (Exception e) { 
				System.out.println("Errore in invio e-mail"+ e);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@RequestMapping(value="/schedaFondoRischi/arretraWorkFlowScheda", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest arretraWorkFlowScheda(@RequestParam("id") long id, HttpServletRequest request) {


		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			if( utenteView != null ){
				SchedaFondoRischiView view = schedaFondoRischiService.leggi(id);
				if( !view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_BOZZA) && 
						!view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO) ){ 
					//recupero l'eventuale utente corrente del workflow per notificargli la rimozione dell'incarico
					List<UtenteView> assegnatariOld = utenteService.leggiAssegnatarioWfSchedaFondoRischi(id);
					boolean discard = schedaFondoRischiWfService.discardStep(id, utenteView.getVo().getUseridUtil());
					if( discard ){
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
						//recupero l'eventuale nuovo utente corrente del workflow per notificargli l'assegnazione dell'incarico
						List<UtenteView> assegnatariNew = utenteService.leggiAssegnatarioWfSchedaFondoRischi(id);
						if(assegnatariOld != null){
							//invio la notifica
							StepWFRest stepPrecedente = new StepWFRest();
							stepPrecedente.setId(0);

							for(UtenteView  assegnatarioOld : assegnatariOld){

								Event eventOld = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepPrecedente, assegnatarioOld.getVo().getUseridUtil());
								WebSocketPublisher.getInstance().publishEvent(eventOld); 
							}

						}
						if(assegnatariNew != null){
							//invio la notifica
							StepWFRest stepSuccessivo = new StepWFRest();
							stepSuccessivo.setId(0);

							for(UtenteView assegnatarioNew: assegnatariNew){

								Event eventNew = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatarioNew.getVo().getUseridUtil());
								WebSocketPublisher.getInstance().publishEvent(eventNew); 
							}
						}
					}else{
						risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.WARN, "messaggio.ko");
					}
				}else {
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.schedaFondoRischi.non.valido");
				}
			}else{
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unauthorized");
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}


	@RequestMapping(value ="/schedaFondoRischi/stampa", method = RequestMethod.GET)
	public String stampaSchedaFondoRischi(@RequestParam("id") long id,SchedaFondoRischiView schedaFondoRischiView,
			Model model, Locale locale, HttpServletRequest request) throws Throwable {

		SchedaFondoRischiView view = new SchedaFondoRischiView();

		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			SchedaFondoRischiView schedaFondoRischiSalvata = (SchedaFondoRischiView) schedaFondoRischiService.leggi(id);
			if (schedaFondoRischiSalvata != null && schedaFondoRischiSalvata.getVo() != null) {
				popolaFormDaVo(view, schedaFondoRischiSalvata.getVo(), locale, utenteView, false);

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
	
	@RequestMapping(value ="/schedaFondoRischi/stampaSchedaModifiche", method = RequestMethod.GET)
	public String stampaSchedaModifiche(@RequestParam("id") long id, SchedaFondoRischiView schedaFondoRischiView, 
			Model model, Locale locale, HttpServletRequest request) throws Throwable {
		
		SchedaFondoRischiView sfrView = new SchedaFondoRischiView();
		
		try {
			SchedaFondoRischiView schedaFondoRischiSalvata = (SchedaFondoRischiView) schedaFondoRischiService.leggi(id);
			List<StoricoSchedaFondoRischiView> versioniPrecedenti = storicoSchedaFondoRischiService.leggiVersioniPrecedenti(id);
			StoricoSchedaFondoRischiView storico = null;

			if(versioniPrecedenti != null && !versioniPrecedenti.isEmpty()){

				storico = versioniPrecedenti.get(0);

				for(StoricoSchedaFondoRischiView view : versioniPrecedenti){

					if(view.getVo().getDataModifica().after(storico.getVo().getDataModifica())){

						storico = view;
					}
				}
			}
			if(storico != null){
				
				popolaFormConModifiche(sfrView, schedaFondoRischiSalvata.getVo(), storico.getVo(), locale);
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, sfrView);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_VISUALIZZA_STAMPA_MODIFICHE;
	}
	
	private void popolaFormConModifiche(SchedaFondoRischiView view, SchedaFondoRischi vo, StoricoSchedaFondoRischi svo, Locale locale) throws Throwable {

		view.setSchedaFondoRischiId(vo.getId());

		FascicoloView fascicoloView = fascicoloService.leggiTutti(vo.getFascicolo().getId(), FetchMode.JOIN);
		
		// FASCICOLO DI RIFERIMENTO
		view.setNomeFascicolo(vo.getFascicolo().getNome());

		// ORGANO GIUDICANTE
		if (fascicoloView.getVo().getRFascicoloGiudizios() != null) {
			Collection<RFascicoloGiudizio> listaFascicoloGiudizio = fascicoloView.getVo().getRFascicoloGiudizios();					
			List<String> giudiziDesc = new ArrayList<String>();
			List<String> organoGiudicanteDesc = new ArrayList<String>();

			for (RFascicoloGiudizio giudizio : listaFascicoloGiudizio) {

				giudiziDesc.add(giudizio.getGiudizio() != null ? giudizio.getGiudizio().getDescrizione() : "");
				organoGiudicanteDesc.add(giudizio.getOrganoGiudicante() != null ? giudizio.getOrganoGiudicante().getNome() : "");
			}
			view.setListaGiudizioDesc(giudiziDesc);
			view.setListaOrganoGiudicanteDesc(organoGiudicanteDesc);
		}

		// VALORE DOMANDA
		String valoreDomanda = vo.getValoreDomanda() == null ? "" : "" + vo.getValoreDomanda().floatValue();
		view.setValoreDomanda(vo.getValoreDomanda().doubleValue());
		String valoreDomandaPrecedente = svo.getValoreDomanda() == null ? "" : "" + vo.getValoreDomanda().floatValue();
		
		if(!valoreDomanda.equals(valoreDomandaPrecedente))
			view.setValoreDomandaPrecedente(svo.getValoreDomanda().doubleValue());

		// PROFESSIONISTA ESTERNO
		if (fascicoloView.getVo().getIncaricos() != null) {
			Collection<Incarico> listaIncarico = fascicoloView.getVo().getIncaricos();
			List<ProfessionistaEsternoView> listaProfessionistiEsterni = new ArrayList<ProfessionistaEsternoView>();

			for(Incarico incarico: listaIncarico){

				if(incarico != null){

					ProfessionistaEsterno profEst = incarico.getProfessionistaEsterno();

					if(profEst != null){
						
						boolean exists = false;
						for(ProfessionistaEsternoView professionistaEsternoView : listaProfessionistiEsterni){
							
							if(!Objects.equals(professionistaEsternoView.getVo().getCognomeNome(), profEst.getCognomeNome())){
								exists = true;
								break;
							}
						}
						if(!exists){
							ProfessionistaEsternoView profEstView = new ProfessionistaEsternoView();
							profEstView.setVo(profEst);
							listaProfessionistiEsterni.add(profEstView);
						}
					}
				}
			}
			view.setListaProfessionistiEsterni(listaProfessionistiEsterni);
		}
		
		// TESTO ESPLICATIVO
		view.setTestoEsplicativo(vo.getTestoEsplicativo());
		String testoEsplicativo = vo.getTestoEsplicativo() == null ? "" : "" + vo.getTestoEsplicativo();
		String testoEsplicativoPrecedente = svo.getTestoEsplicativo() == null ? "" : "" + svo.getTestoEsplicativo();
		
		if(!testoEsplicativo.equals(testoEsplicativoPrecedente))
			view.setTestoEsplicativoPrecedente(svo.getTestoEsplicativo());

		// RISCHIO SOCCOMBENZA
		String rischioSoccombenza = "";
		String rischioSoccombenzaPrecedente = "";
		
		if( vo.getRischioSoccombenza()!= null ){
			
			rischioSoccombenza = vo.getRischioSoccombenza().getDescrizione()==null ? "" : vo.getRischioSoccombenza().getDescrizione();
			view.setRischioSoccombenzaDesc(rischioSoccombenza);
		}
		if( svo.getRischioSoccombenza()!= null ){
			
			rischioSoccombenzaPrecedente = svo.getRischioSoccombenza().getDescrizione()==null ? "" : svo.getRischioSoccombenza().getDescrizione();
		}
		if(!rischioSoccombenza.equals(rischioSoccombenzaPrecedente))
			view.setRischioSoccombenzaPrecDesc(rischioSoccombenzaPrecedente);
		
		// COPERTURA ASSICURATIVA
		String coperturaAssicurativaDesc = "";
		String coperturaAssicurativaPrecDesc = "";
		
		if(vo.getCoperturaAssicurativa() != null){
			
			coperturaAssicurativaDesc = "" + vo.getCoperturaAssicurativa().floatValue();
			view.setCoperturaAssicurativaDesc(coperturaAssicurativaDesc);
		}
		if(svo.getCoperturaAssicurativa() != null){
			
			coperturaAssicurativaPrecDesc = "" + svo.getCoperturaAssicurativa().floatValue();
		}
		if(!coperturaAssicurativaDesc.equals(coperturaAssicurativaPrecDesc))
			view.setCoperturaAssicurativaPrecDesc(coperturaAssicurativaPrecDesc);
		
		// MANLEVA
		String manlevaDesc = "";
		String manlevaPrecDesc = "";
		
		if(vo.getManleva() != null){
			
			manlevaDesc = "" + vo.getManleva().floatValue();
			view.setManlevaDesc(manlevaDesc);
		}
		if(svo.getManleva() != null){
			
			manlevaPrecDesc = "" + svo.getManleva().floatValue();
		}
		
		if(!manlevaDesc.equals(manlevaPrecDesc))
			view.setManlevaPrecDesc(manlevaPrecDesc);
		
		// COMMESSA DI INVESTIMENTO
		String commessaInvestimentoDesc = "";
		String commessaInvestimentoPrecDesc = "";
		
		if(vo.getCommessaDiInvestimento() != null){
			
			commessaInvestimentoDesc = "" + vo.getCommessaDiInvestimento().floatValue();
			view.setCommessaInvestimentoDesc(commessaInvestimentoDesc);
		}
		if(svo.getCommessaDiInvestimento() != null){
			
			commessaInvestimentoPrecDesc = "" + svo.getCommessaDiInvestimento().floatValue();
		}
		if(!commessaInvestimentoDesc.equals(commessaInvestimentoPrecDesc))
			view.setCommessaInvestimentoPrecDesc(commessaInvestimentoPrecDesc);
		
		// PASSIVITA STIMATA
		String passivitaStimataDesc = "";
		String passivitaStimataPrecDesc = "";
		
		if(vo.getPassivitaStimata() != null){
			passivitaStimataDesc = "" + vo.getPassivitaStimata().floatValue();
			view.setPassivitaStimataDesc(passivitaStimataDesc);
		}
		
		if(svo.getPassivitaStimata() != null){
			passivitaStimataPrecDesc = "" + svo.getPassivitaStimata().floatValue();
		}
		if(!passivitaStimataDesc.equals(passivitaStimataPrecDesc))
			view.setPassivitaStimataPrecDesc(passivitaStimataPrecDesc);
		
		// MOTIVAZIONE
		String motivazione = vo.getMotivazione() == null ? "" : "" + vo.getMotivazione();
		view.setMotivazione(vo.getMotivazione());
		String motivazionePrec = svo.getMotivazione() == null ? "" : "" + svo.getMotivazione();
		
		if(!motivazione.equals(motivazionePrec))
			view.setMotivazionePrecedente(svo.getMotivazione());
	}

	@RequestMapping(value = "/schedaFondoRischi/calcolaTrimestreCorrente", method = RequestMethod.POST)
	public @ResponseBody String calcolaTrimestreCorrente(HttpServletRequest request, Locale locale) {

		try {  
			String result = "";
			
			String delayPFR = schedaFondoRischiService.leggiDelay();
			int delay = Integer.parseInt(delayPFR);
			
			Date now = new Date();
			Date nowDelayed = DateUtil2.addGiorni(now, -delay);
			result = DateUtil2.estraiTrimestreDaData(nowDelayed);
			return result;
		} 
		catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/schedaFondoRischi/caricaTrimestriDisponibili", method = RequestMethod.POST)
	public @ResponseBody String caricaTrimestriDisponibili(HttpServletRequest request, Locale locale) {


		try {  

			String trimestreCorrente = "";
			List<String> trimestriDisponibili = new ArrayList<String>();

			String delayPFR = schedaFondoRischiService.leggiDelay();
			int delay = Integer.parseInt(delayPFR);
			
			Date now = new Date();
			Date nowDelayed = DateUtil2.addGiorni(now, -delay);
			
			trimestreCorrente = DateUtil2.estraiTrimestreDaData(nowDelayed);
			trimestriDisponibili.add(trimestreCorrente);

			if(!trimestreCorrente.equals("I - 2017")){

				while(!trimestreCorrente.equals("I - 2017")){

					if(trimestreCorrente.indexOf("IV") >-1){
						trimestreCorrente = trimestreCorrente.replace("IV", "III");
						trimestriDisponibili.add(trimestreCorrente);
					}
					else if(trimestreCorrente.indexOf("III") >-1){
						trimestreCorrente = trimestreCorrente.replace("III", "II");
						trimestriDisponibili.add(trimestreCorrente);
					}
					else if(trimestreCorrente.indexOf("II") >-1){
						trimestreCorrente = trimestreCorrente.replace("II", "I");
						trimestriDisponibili.add(trimestreCorrente);
					}
					else{
						int anno = Integer.parseInt(trimestreCorrente.substring(trimestreCorrente.indexOf("- ") + 2));
						anno = anno - 1;
						trimestreCorrente = "IV - " + anno;
						trimestriDisponibili.add(trimestreCorrente);
					}
				}
			}

			JSONArray jsonArray = new JSONArray();

			for(String trimestre : trimestriDisponibili){

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("trimestre", trimestre);
				jsonArray.put(jsonObject);
			}
			return jsonArray.toString();

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value ="/schedaFondoRischi/generaPFR", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest generaPFRFase1(@RequestParam("trimestre") String trimestre, Locale locale, HttpServletRequest request, HttpServletResponse response) throws Throwable {

		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		try {
			String delayPFR = schedaFondoRischiService.leggiDelay();
			int delay = Integer.parseInt(delayPFR);
			
			/** Estraggo le date di inizio e fine del trimestre selezionato **/
			List<Date> listaDateTrimestre = DateUtil2.estraiIntervalloDateTrimestre(trimestre);

			Date dataInizio = listaDateTrimestre.get(0);
			Date dataFine = listaDateTrimestre.get(1);
			
			Date dataInizioDelayed = DateUtil2.addGiorni(dataInizio, delay);
			Date dataFineDelayed = DateUtil2.addGiorni(dataFine, delay);

			List<SchedaFondoRischiView> lista = schedaFondoRischiService.cercaNelTrimestre(dataInizioDelayed, dataFineDelayed);

			if (lista != null && !lista.isEmpty()) {

				boolean esisteAlmenoUnAutorizzato = false;
				for(SchedaFondoRischiView scheda : lista){

					if(scheda.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO)){
						esisteAlmenoUnAutorizzato = true;
						break;
					}
				}
				if(!esisteAlmenoUnAutorizzato){
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.schedaFondoRischi.non.autorizzato");
				}else{
					
					reportingService.generaPFRFase1(lista, trimestre, response, locale.getLanguage().toUpperCase(), delay);
					return null;
				}

			} else {
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "schedaFondoRischi.non.presente");
			}
		} catch (Throwable e) {
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "schedaFondoRischi.errore.generico");
			e.printStackTrace();
		}
		return risultato;
	}

	@RequestMapping(value ="/schedaFondoRischi/generaPFRModifiche", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest generaPFRFase2(@RequestParam("trimestre") String trimestre, Locale locale, HttpServletRequest request, HttpServletResponse response) throws Throwable {

		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		try {
			String delayPFR = schedaFondoRischiService.leggiDelay();
			int delay = Integer.parseInt(delayPFR);
			
			/** Estraggo le date di inizio e fine del trimestre selezionato **/
			List<Date> listaDateTrimestre = DateUtil2.estraiIntervalloDateTrimestre(trimestre);

			Date dataInizio = listaDateTrimestre.get(0);
			Date dataFine = listaDateTrimestre.get(1);
			
			Date dataInizioDelayed = DateUtil2.addGiorni(dataInizio, delay);
			Date dataFineDelayed = DateUtil2.addGiorni(dataFine, delay);

			List<SchedaFondoRischiView> lista = schedaFondoRischiService.cercaNelTrimestre(dataInizioDelayed, dataFineDelayed);

			if (lista != null && !lista.isEmpty()) {

				boolean esisteAlmenoUnAutorizzato = false;
				for(SchedaFondoRischiView scheda : lista){

					if(scheda.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO)){
						esisteAlmenoUnAutorizzato = true;
						break;
					}
				}
				if(!esisteAlmenoUnAutorizzato){
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.schedaFondoRischi.non.autorizzato");
				}else{
					reportingService.generaPFRFase2(lista, trimestre, response, locale.getLanguage().toUpperCase(), delay);
					return null;
				}

			} else {
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "schedaFondoRischi.non.presente");
			}
		} catch (Throwable e) {
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "schedaFondoRischi.errore.generico");
			e.printStackTrace();
		}
		return risultato;
	}

	protected boolean confrontaSchedeFondoRischi(SchedaFondoRischiView scheda1, SchedaFondoRischiView scheda2){

		boolean modificata = false;

		SchedaFondoRischi schedaDB = scheda1.getVo();
		SchedaFondoRischi schedaDaModificare = scheda2.getVo();

		if(schedaDB.getValoreDomanda() != null && schedaDaModificare.getValoreDomanda() != null){

			if(!schedaDB.getValoreDomanda().equals(schedaDaModificare.getValoreDomanda()))
				modificata = true;
		}

		if(schedaDB.getTestoEsplicativo() != null && schedaDaModificare.getTestoEsplicativo() != null){

			if(!schedaDB.getTestoEsplicativo().equals(schedaDaModificare.getTestoEsplicativo()))
				modificata = true;
		}

		if(schedaDB.getCoperturaAssicurativa() != null && schedaDaModificare.getCoperturaAssicurativa() != null){

			if(!schedaDB.getCoperturaAssicurativa().equals(schedaDaModificare.getCoperturaAssicurativa()))
				modificata = true;
		}

		if(schedaDB.getManleva() != null && schedaDaModificare.getManleva() != null){

			if(!schedaDB.getManleva().equals(schedaDaModificare.getManleva()))
				modificata = true;
		}

		if(schedaDB.getCommessaDiInvestimento() != null && schedaDaModificare.getCommessaDiInvestimento() != null){

			if(!schedaDB.getCommessaDiInvestimento().equals(schedaDaModificare.getCommessaDiInvestimento()))
				modificata = true;
		}

		if(schedaDB.getPassivitaStimata() != null && schedaDaModificare.getPassivitaStimata() != null){

			if(!schedaDB.getPassivitaStimata().equals(schedaDaModificare.getPassivitaStimata()))
				modificata = true;
		}

		if(schedaDB.getMotivazione() != null && schedaDaModificare.getMotivazione() != null){

			if(!schedaDB.getMotivazione().equals(schedaDaModificare.getMotivazione()))
				modificata = true;
		}

		try {
			caricaDocumentiProfessionistaEsternoFilenet(scheda1, schedaDB);
		} catch (Throwable e) {
			scheda1.setListaAllegatiLegaleEsterno(null);
		}

		if(scheda1.getListaAllegatiLegaleEsterno() != null && scheda2.getListaAllegatiLegaleEsterno() != null){

			if(scheda1.getListaAllegatiLegaleEsterno().size() != scheda2.getListaAllegatiLegaleEsterno().size()){

				modificata = true;
			}
		}
		return modificata;
	}


	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		List<TipologiaSchedaFrView> listaTipologiaSchedaFr = tipologiaSchedaFrService.leggi(locale);
		List<RischioSoccombenzaView> listaRischioSoccombenza = rischioSoccombenzaService.leggi(locale);
		SchedaFondoRischiView schedaFondoRischiView = (SchedaFondoRischiView) view;
		schedaFondoRischiView.setListaTipologiaScheda(listaTipologiaSchedaFr);
		schedaFondoRischiView.setListaRischioSoccombenza(listaRischioSoccombenza);
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new SchedaFondoRischiValidator());

		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}


}
