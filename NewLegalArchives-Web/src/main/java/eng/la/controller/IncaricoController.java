package eng.la.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eng.la.model.rest.TreeviewData;
import eng.la.persistence.*;
import it.snam.ned.libs.dds.dtos.v2.Document;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringEscapeUtils;
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
//import com.filenet.api.constants.RefreshMode;
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.DocumentoService;
import eng.la.business.FascicoloService;
import eng.la.business.IncaricoService;
import eng.la.business.NazioneService;
import eng.la.business.ProcuratoreService;
import eng.la.business.ProcureService;
import eng.la.business.ProfessionistaEsternoService;
import eng.la.business.SpecializzazioneService;
import eng.la.business.UtenteService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.business.workflow.ConfigurazioneService;
import eng.la.model.Acconti;
import eng.la.model.Bonus;
import eng.la.model.Documento;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
import eng.la.model.LetteraIncarico;
import eng.la.model.ListaRiferimento;
import eng.la.model.NotaPropIncarico;
import eng.la.model.Procura;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.RProfEstSpec;
import eng.la.model.RProfessionistaNazione;
import eng.la.model.StudioLegale;
import eng.la.model.Utente;
import eng.la.model.VerificaAnticorruzione;
import eng.la.model.VerificaPartiCorrelate;
import eng.la.model.custom.Numeri;
import eng.la.model.rest.FileNetExtractionRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.AccontoView;
import eng.la.model.view.BaseView;
import eng.la.model.view.BonusView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.NazioneView;
import eng.la.model.view.ProcuratoreView;
import eng.la.model.view.ProcureView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.SpecializzazioneView;
import eng.la.model.view.StatoIncaricoView;
import eng.la.model.view.TipoValutaView;
import eng.la.model.view.UtenteView;
//@@DDS import eng.la.persistence.DocumentaleCryptDAO;
//@@DDS import eng.la.persistence.DocumentaleDAO;
import eng.la.presentation.validator.IncaricoValidator;
import eng.la.util.ClassPathUtils;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.StringUtil;
import eng.la.util.WriteExcell;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller("incaricoController")
@SessionAttributes("incaricoView")
public class IncaricoController extends BaseController {
	private static final String MODEL_VIEW_NOME = "incaricoView";
	private static final String PAGINA_FORM_PATH = "incarico/formIncarico";  

	private static final Logger logger = Logger.getLogger(IncaricoController.class);
	
	
	// Costanti per la generazione del report.	
	public static final String LETTERA_INCARICO_JRXML_REPORT_FILE_NAME			= "LetteraIncarico.jrxml";
	public static final String LETTERA_INCARICO_JASPER_REPORT_FILE_NAME   		= "LetteraIncarico.jasper";
	public static final String LETTERA_INCARICO_JASPER_REPORT_TITLE  			= "LETTERA DI INCARICO";
	
	public static final String NOTA_INCARICO_JRXML_REPORT_FILE_NAME			= "NotaPropostaIncarico.jrxml";
	public static final String NOTA_INCARICO_JASPER_REPORT_FILE_NAME   		= "NotaPropostaIncarico.jasper";
	public static final String NOTA_INCARICO_JASPER_REPORT_TITLE  			= "NOTA PROPOSTA INCARICO";
	
	public static final String PARAMETER_SUBREPORTDIR       					= "SUBREPORT_DIR";
	public static final String PARAMETER_STYLEDIR           					= "STYLE_DIR";
	public static final String PARAMETER_IMAGERDIR          					= "IMAGE_DIR";
	public static final String PARAMETER_REPORTLOCALE       					= "REPORT_LOCALE";
	public static final String PARAMETER_REPORTTIMEZONE     					= "REPORT_TIME_ZONE";
	public static final String PARAMETER_LANGUAGE      	  						= "language";
	public static final String PARAMETER_COUNTRY 	      						= "country";

	@Autowired
	private EmailNotificationService emailNotificationService;

	@Autowired
	private FascicoloService fascicoloService;

	@Autowired
	private ConfigurazioneService configurazioneService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private ProfessionistaEsternoService professionistaEsternoService;

	@Autowired
	private IncaricoService incaricoService;

	@Autowired
	private ProcuratoreService procuratoreService;

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	@Autowired
	private NazioneService nazioneService;

	@Autowired
	private SpecializzazioneService specializzazioneService;

	@Autowired
	private ProcureService procureService;

	@Autowired
	private DocumentoService documentoService;


	@RequestMapping("/incarico/modifica")
	public String modificaIncarico(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		IncaricoView view = new IncaricoView();
		// engsecurity VA - redirect
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		logger.info ("@@DDS incarico/modifica __________________________");
		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			IncaricoView incaricoSalvato = (IncaricoView) incaricoService.leggi(id);
			if (incaricoSalvato != null && incaricoSalvato.getVo() != null) {
				caricaListeProcure(view, incaricoSalvato.getVo());
				popolaFormDaVo(view, incaricoSalvato.getVo(), locale, utenteView);

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

	@RequestMapping("/incarico/richiediProforma")
	public String respingiProforma(@RequestParam("id") long id, Model model, Locale locale,
			HttpServletRequest request) {

		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);

		UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
		// 07/06/2018 aggiunta token al link di redirect
		String token=request.getParameter("CSRFToken");
		try {
			emailNotificationService.inviaNotifica(CostantiDAO.RICHIEDIPROFORMA, CostantiDAO.INCARICO, id,
					request.getLocale().getLanguage().toUpperCase(),
					utenteView.getVo().getUseridUtil());
			model.addAttribute("successMessage", "messaggio.operazione.ok");
		} catch (Throwable e) {
			logger.error("Errore invio email richiedi proforma da incarico "+e);
			model.addAttribute("errorMessage", "errore.generico");
		}

		// 07/06/2018 aggiunta token al link di redirect
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : "redirect:/incarico/dettaglio.action?id="+id+"&CSRFToken="+token;
	}


	private void popolaFormDaVo(IncaricoView view, Incarico vo, Locale locale, UtenteView utenteConnesso) throws Throwable {
		String allegato = "";
		logger.info ("@@DDS popolaFormDaVo __________________________");
		view.setListaTipoValuta(anagraficaStatiTipiService.leggiTipoValuta(false));

		view.setIncaricoId(vo.getId());		
		view.setNomeIncarico(vo.getNomeIncarico());
		view.setCommento(vo.getCommento());
		if( vo.getDataAutorizzazione() != null ){
			view.setDataAutorizzazione(DateUtil.formattaData(vo.getDataAutorizzazione().getTime()));	
		}
		if( vo.getDataRichiestaAutorIncarico() != null ){
			view.setDataRichiestaAutorizzazione(DateUtil.formattaData(vo.getDataRichiestaAutorIncarico().getTime()));	
		} 

		FascicoloView fascicoloView = fascicoloService.leggi(vo.getFascicolo().getId(), FetchMode.JOIN);
		view.setFascicoloRiferimento( fascicoloView );
		boolean isPenale = fascicoloView.getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		view.setPenale(isPenale);

		String societaParteProcedimento = null;
		view.setNomeFascicolo(vo.getFascicolo().getNome());
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

				if(societa.getTipologiaSocieta().equals(SOCIETA_TIPOLOGIA_PARTE_PROCEDIMENTO)){
					societaParteProcedimento=societa.getSocieta().getRagioneSociale();
				}
			}

			view.setListaSocietaAddebitoAggiunteDesc(societaAddebitoAggiunteDesc);
			view.setListaSocietaAddebitoAggiunteDescLet(societaAddebitoAggiunteDescLet);
			view.setSocietaParteProcedimento(societaParteProcedimento);
		}
		StatoIncaricoView statoIncaricoView = anagraficaStatiTipiService.leggiStatoIncarico(vo.getStatoIncarico().getCodGruppoLingua(), locale.getLanguage().toUpperCase());		
		view.setStatoIncarico(statoIncaricoView.getVo().getDescrizione());
		view.setStatoIncaricoCode(statoIncaricoView.getVo().getCodGruppoLingua()); 

		/*TODO: GESTIRE COLLEGGIO ARBITRALE*/
		//view.setCollegioArbitrale(vo.getCollegioArbitrale()); 

		if( vo.getLetteraIncarico() != null ){
			LetteraIncarico letteraIncarico = vo.getLetteraIncarico();
			view.setLetteraIncaricoId(letteraIncarico.getId());
			allegato="  Lettera di incarico ";


			if( letteraIncarico.getDataProtocollo() != null ){
				view.setDataProtocollo(DateUtil.formattaData( letteraIncarico.getDataProtocollo().getTime()));
			}
			if( letteraIncarico.getMesiCompensoStragiudiziale() != null ){
				view.setMesiCompensoStragiudiziale( letteraIncarico.getMesiCompensoStragiudiziale().intValue() );
			}
			if( letteraIncarico.getOggetto() != null ){
				view.setOggettoProtocollo(letteraIncarico.getOggetto());
			}  
			if( letteraIncarico.getDescrizione() != null ){
				view.setDescrizioneProtocollo(letteraIncarico.getDescrizione());
			}
			if( letteraIncarico.getProtocollo() != null ){
				view.setProtocollo(letteraIncarico.getProtocollo());
			}
			if(letteraIncarico.getTipoValuta()!=null){
				view.setValutaId(letteraIncarico.getTipoValuta().getId());
			}
			if(letteraIncarico.getLegaleInterno()!=null){
				view.setUtenteConnesso(letteraIncarico.getLegaleInterno());
			}

			if(letteraIncarico.getInfoCompenso()!=null){
				view.setInfoCompenso(letteraIncarico.getInfoCompenso());
			}

			if(letteraIncarico.getCompenso()!=null){
				view.setCompenso(letteraIncarico.getCompenso());
			}

			if(letteraIncarico.getAttivitaStragiudizialePenale()!=null){
				view.setAttivita(letteraIncarico.getAttivitaStragiudizialePenale());
			}

			if(letteraIncarico.getQualifica()!=null){
				view.setQualifica(letteraIncarico.getQualifica());
			}

			view.setIsQuadro(letteraIncarico.isQuadro());

			if(letteraIncarico.getnAccordoQuadro()!=null){
				view.setNumQuadro(letteraIncarico.getnAccordoQuadro());
			}

			if(letteraIncarico.getProtocollo()!=null){
				view.setProtocollo(letteraIncarico.getProtocollo());
			}

			if(letteraIncarico.getBonus().size()!=0){
				view.setBonusIn(letteraIncarico.getBonus());
				view.setSizeBonus(letteraIncarico.getBonus().size());
			}
			else{
				view.setSizeBonus(0);
			}

			if(letteraIncarico.getTipoValuta()!=null)

				if(letteraIncarico.getAcconti().size()!=0){	

					Set<Acconti> acconti = letteraIncarico.getAcconti();

					Acconti toRemove = new Acconti(); 

					for (Iterator<Acconti> iterator = acconti.iterator(); iterator.hasNext();) {
						Acconti acconti2 = iterator.next();
						if(!iterator.hasNext())
							toRemove= acconti2;
					}

					/**
					 * Non � necessario rimuovere l'elemento
					 * perch� nella nuova logica l'anno di
					 * fine incarico � salvato nella entry con compenso
					 * pari a -1
					 * @author MASSIMO CARUSO
					 */
//					acconti.remove(toRemove);

					view.setSaldoAnno(toRemove.getAnno());				

					view.setAccontiIn(acconti);
					view.setSizeAcconti(letteraIncarico.getAcconti().size());

				}
				else{
					view.setSizeAcconti(0);
				}
			view.setTimeout(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_POLLING_FILE_LETTERA_INCARICO).getVo().getCdValue());

			if(!view.getStatoIncaricoCode().equals("B"))
				view.setDisabled(true);
			else
				view.setDisabled(false);


			String unitaApp = "";
			UtenteView responsabileTop = utenteService.leggiResponsabileTop();
			
			if(responsabileTop != null){
				
				Utente respTopVo = responsabileTop.getVo();
				
				if(respTopVo != null){
					
					if(respTopVo.getCodiceUnitaAppart() != null && !respTopVo.getCodiceUnitaAppart().isEmpty()){
						
						unitaApp = respTopVo.getCodiceUnitaAppart();
					}
					else if(respTopVo.getCodiceUnitaUtil() != null && !respTopVo.getCodiceUnitaUtil().isEmpty()){
						
						unitaApp = respTopVo.getCodiceUnitaUtil();
					}
				}
			}
			
			if(!unitaApp.isEmpty()){
				view.setUnitaOrganizzativa(unitaApp.substring(5,unitaApp.length()));
			} 
			else{
				view.setUnitaOrganizzativa("");
			}

			view.setSnamretegasWebsite(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_SNAMRETEGAS_WEBSITE).getVo().getCdValue());

			view.setSnamWebsite(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_SNAM_WEBSITE).getVo().getCdValue());

			view.setEmailSegnalazioni(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_EMAIL_SEGNALAZIONI).getVo().getCdValue());

			view.setPieDiPaginaSnam(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_EMAIL_SEGNALAZIONI).getVo().getCdValue());
		}
		//NUOVA LETTERA INCARICO
		else{

			view.setSizeBonus(0);

			view.setSizeAcconti(0);

			view.setValutaId(incaricoService.getEuroValuta());

			view.setCompenso(new BigDecimal(0));

			view.setInfoCompenso("<p>Il Compenso si intende comprensivo di diritti, "
					+ "onorari e spese generali di studio ed al netto di eventuali "
					+ "compensi anticipati al domiciliatario od a consulenti tecnici "
					+ "oltre che al netto di IVA, CPA, "
					+ "Contributo Unificato e delle spese vive eventualmente resesi necessarie (biglietti aerei, taxi et similia) "
					+ "di cui dovr� essere data evidenza documentale.</p>");

			view.setDataProtocollo(DateUtil.formattaData(new java.util.Date().getTime()));

			String unitaApp = "";
			UtenteView responsabileTop = utenteService.leggiResponsabileTop();
			
			if(responsabileTop != null){
				
				Utente respTopVo = responsabileTop.getVo();
				
				if(respTopVo != null){
					
					if(respTopVo.getCodiceUnitaAppart() != null && !respTopVo.getCodiceUnitaAppart().isEmpty()){
						
						unitaApp = respTopVo.getCodiceUnitaAppart();
					}
					else if(respTopVo.getCodiceUnitaUtil() != null && !respTopVo.getCodiceUnitaUtil().isEmpty()){
						
						unitaApp = respTopVo.getCodiceUnitaUtil();
					}
				}
			}
			
			if(!unitaApp.isEmpty()){
				view.setUnitaOrganizzativa(unitaApp.substring(5,unitaApp.length()));
			} 
			else{
				view.setUnitaOrganizzativa("");
			}

			Utente utente = utenteConnesso.getVo();

			Numeri numeri = utenteService.getNumeriDaUserProfile(utente.getUseridUtil());

			String out = utente.getNominativoUtil()+" (";


			out+= "tel: "+numeri.getNumTelefono();

			out+=	" - fax: "+numeri.getNumFax();

			out+=" - email: "+utente.getEmailUtil()+")";

			view.setUtenteConnesso(out);

			view.setPieDiPaginaSnam(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_PIE_DI_PAGINA).getVo().getCdValue());

			view.setSnamretegasWebsite(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_SNAMRETEGAS_WEBSITE).getVo().getCdValue());

			view.setSnamWebsite(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_SNAM_WEBSITE).getVo().getCdValue());

			view.setEmailSegnalazioni(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_EMAIL_SEGNALAZIONI).getVo().getCdValue());
		
		
			/**
			 * Aggiunta automatica numero di protocollo per lettera di incarico
			 * @author MASSIMO CARUSO
			 */
			//view.setProtocollo(incaricoService.generaNumeroProtocolloLetteraIncarico(utente.getSiglaUtente(),utente.getNominativoUtil()));
			
			
		}

		if(  vo.getListaRiferimento() != null){
			DocumentoView doc = new DocumentoView();
			doc.setContentType(vo.getListaRiferimento().getDocumento().getContentType());
			doc.setNomeFile(vo.getListaRiferimento().getDocumento().getNomeFile());
			doc.setNuovoDocumento(false);
			doc.setVo(vo.getListaRiferimento().getDocumento());		
			view.setListeRiferimentoDoc(doc);

			view.setListeRiferimento(vo.getListaRiferimento());
			allegato+= "," + "   Liste Riferimento  ";
		} 

		if( vo.getProcura() != null ){
			DocumentoView doc = new DocumentoView();
			doc.setContentType(vo.getProcura().getDocumento().getContentType());
			doc.setNomeFile(vo.getProcura().getDocumento().getNomeFile());
			doc.setNuovoDocumento(false);
			doc.setVo(vo.getProcura().getDocumento());			
			view.setProcuraDoc(doc);

			view.setProcura(vo.getProcura());
			allegato=allegato + ", " + "   Procura ";
		}

		//LETTERA INCARICO FIRMATA //

		if( vo.getLetteraIncarico()!= null && vo.getLetteraIncarico().getDocumento_firmato()!=null  ){
			logger.debug("Lettera incarico diversa da null");
			DocumentoView doc = new DocumentoView();
			doc.setContentType(vo.getLetteraIncarico().getDocumento_firmato().getContentType());
			doc.setNomeFile(vo.getLetteraIncarico().getDocumento_firmato().getNomeFile());
			doc.setNuovoDocumento(false);
			doc.setVo(vo.getLetteraIncarico().getDocumento_firmato());			
			view.setLetteraFirmataDoc(doc);

			view.setLetteraFirmata(vo.getLetteraIncarico().getDocumento_firmato());
		}

		//NOTA FIRMATA //

		if( vo.getNotaPropIncarico()!= null && vo.getNotaPropIncarico().getDocumento_firmato()!=null  ){
			logger.debug("Nota proposta incarico diversa da null");
			DocumentoView doc = new DocumentoView();
			doc.setContentType(vo.getNotaPropIncarico().getDocumento_firmato().getContentType());
			doc.setNomeFile(vo.getNotaPropIncarico().getDocumento_firmato().getNomeFile());
			doc.setNuovoDocumento(false);
			doc.setVo(vo.getNotaPropIncarico().getDocumento_firmato());			
			view.setLetteraFirmataDocNota(doc);

			view.setLetteraFirmataNota(vo.getNotaPropIncarico().getDocumento_firmato());
		}

		if( vo.getVerificaAnticorruzione() != null){
			DocumentoView doc = new DocumentoView();
			doc.setContentType(vo.getVerificaAnticorruzione().getDocumento().getContentType());
			doc.setNomeFile(vo.getVerificaAnticorruzione().getDocumento().getNomeFile());
			doc.setNuovoDocumento(false);
			doc.setVo(vo.getVerificaAnticorruzione().getDocumento());			
			view.setVerificaAnticorruzioneDoc(doc);

			view.setVerificaAnticorruzione(vo.getVerificaAnticorruzione());
			allegato=allegato + ", " + "  Verifica anticorruzione  ";
		}

		if(  vo.getVerificaPartiCorrelate() != null){
			DocumentoView doc = new DocumentoView();
			doc.setContentType(vo.getVerificaPartiCorrelate().getDocumento().getContentType());
			doc.setNomeFile(vo.getVerificaPartiCorrelate().getDocumento().getNomeFile());
			doc.setNuovoDocumento(false);
			doc.setVo(vo.getVerificaPartiCorrelate().getDocumento());			
			view.setVerificaPartiCorrelateDoc(doc);	

			view.setVerificaPartiCorrelate(vo.getVerificaPartiCorrelate());
			allegato=allegato + ", " + "  Verifica parti correlate  ";
		}

		if( vo.getNazione()!= null ){
			view.setNazioneCode(vo.getNazione().getCodGruppoLingua());
			NazioneView nazioneSelezionata = new NazioneView();
			nazioneSelezionata.setVo(vo.getNazione());
			view.setNazione(nazioneSelezionata);
		}

		if( vo.getSpecializzazione()!= null ){
			view.setSpecializzazioneCode(vo.getSpecializzazione().getCodGruppoLingua());
			SpecializzazioneView specializzazioneSelezionata = new SpecializzazioneView();
			specializzazioneSelezionata.setVo(vo.getSpecializzazione());
			view.setSpecializzazione(specializzazioneSelezionata);
		}

		if( vo.getProfessionistaEsterno() != null ){
			view.setProfessionistaId(vo.getProfessionistaEsterno().getId());
			ProfessionistaEsternoView professionistaSelezionato = new ProfessionistaEsternoView();
			professionistaSelezionato.setVo(vo.getProfessionistaEsterno());
			view.setProfessionistaSelezionato(professionistaSelezionato);
		} 

		view.setAllegato(allegato);

		Fascicolo fasc = vo.getFascicolo();

		Utente ownerFascicolo = utenteService.leggiUtenteDaUserId(fasc.getLegaleInterno()).getVo();

		String owner = ownerFascicolo.getDescrUnitaUtil()+" - "+ownerFascicolo.getNominativoUtil();

		view.setProponente(owner);

		if(ownerFascicolo.getNominativoRespUtil()!=null){
			Utente responsabile = ownerFascicolo = utenteService.leggiUtenteDaMatricola(ownerFascicolo.getMatricolaRespUtil()).getVo();
			if(responsabile!=null){
				String respDescrizioneUnitaUnit = responsabile.getDescrUnitaUtil() == null?"":responsabile.getDescrUnitaUtil();
				String respNominativoUtil = responsabile.getNominativoUtil() == null?"":responsabile.getNominativoUtil();
				view.setResponsabili(respDescrizioneUnitaUnit+" - "+respNominativoUtil);
			}
		}

		List<UtenteView> responsabili = utenteService.leggiResponsabili(utenteService.leggiUtenteDaUserId(fasc.getLegaleInterno()).getVo().getMatricolaUtil());   

		if(responsabili!=null){
			for(UtenteView utente:responsabili){
				if(utenteService.leggiSePrimoRiporto(utente)){
					view.setApprovatore(utente.getVo().getDescrUnitaUtil()+" - "+utente.getVo().getNominativoUtil());
					break;
				}
			}
		}

		UtenteView top = utenteService.leggiResponsabileTop();

		view.setResponsabileTop(top.getVo().getNominativoUtil());


		String compensoBonus = "";
		LetteraIncarico letteraIncarico = vo.getLetteraIncarico(); 

		TipoValutaView tipoValuta = null;
		try {
			tipoValuta = anagraficaStatiTipiService.leggiTipoValuta(view.getValutaId());
		} catch (Throwable e) {
			e.printStackTrace();
		}


		if(letteraIncarico!=null){
			Set<Bonus> setbonus =letteraIncarico.getBonus(); 

			if(view.getCompenso()!=null){
				compensoBonus+=view.getCompenso().toString()+" "+tipoValuta.getVo().getNome();
			}
			for(Bonus bonus : setbonus){
				if(bonus.getImporto().intValue()!=0)
					compensoBonus+= " + "+bonus.getImporto().toString()+" "+tipoValuta.getVo().getNome()+" : "+bonus.getDescrizione();
			}

			view.setValoreIncarico(compensoBonus);
		}


		if( vo.getNotaPropIncarico() != null ){ 



			NotaPropIncarico notaPropIncarico = vo.getNotaPropIncarico();
			view.setNotaIncaricoId( notaPropIncarico.getId());

			view.setTimeoutNot(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_POLLING_FILE_NOTA_PROPOSTA_INCARICO).getVo().getCdValue());

			if(notaPropIncarico.getNomeForo()!=null){
				view.setNomeForo(notaPropIncarico.getNomeForo());
			}

			if(notaPropIncarico.getInfoCompensoNP()!=null){
				view.setInfoCompensoNotaProp(notaPropIncarico.getInfoCompensoNP());
			}

			if(notaPropIncarico.getSceltaInfo()!=null){
				view.setSceltaInfo(notaPropIncarico.getSceltaInfo());
				
				if(view.getSceltaInfo()==0){
					view.setInfoHandBook("Si precisa che, con riferimento agli allegati all'handbook, non sono state apportate modifiche");
				}
				else{
					view.setSceltaInfo(notaPropIncarico.getSceltaInfo());
					view.setInfoHandBook(notaPropIncarico.getInfoHandBook());
				}
			}

			if(notaPropIncarico.getInfoCorresponsione()!=null){
				view.setInfoCorresponsioneCompenso(notaPropIncarico.getInfoCorresponsione());
			}

			if(notaPropIncarico.getDataPropIncarico()!=null){
				view.setDataNotaProposta(DateUtil.formattaData( notaPropIncarico.getDataPropIncarico().getTime()));
			}

			if( notaPropIncarico.getValoreIncarico() != null ){
				view.setValoreIncarico((notaPropIncarico.getValoreIncarico()));	
			}

			view.setDataNotaProposta(DateUtil.formattaData(new java.util.Date().getTime()));			
			view.setDescrizione(notaPropIncarico.getDescrizione());
			view.setOggetto(notaPropIncarico.getOggetto());
			view.setPratica(notaPropIncarico.getPratica());
			view.setNomeFascicolo(vo.getFascicolo().getNome());
			//			UtenteView utente = utenteService.leggiUtenteDaUserId(notaPropIncarico.getProponente());



			view.setAllegato(allegato);
			view.setProposta(notaPropIncarico.getProposta());

		}else{

			view.setInfoCompensoNotaProp("<p> I. &egrave;  adeguata alla complessit&agrave; della tematica oggetto dell'incarico* , <br>  "
					+ "II.	&egrave; adeguata alla tempistica concordata per l'esecuzione dell'incarico *  <br>"
					+ "III.	sono presenti eventuali scostamenti rispetto al Budget assegnato*</p> ");
			view.setInfoCorresponsioneCompenso(new String("Modalità di corresponsione del compenso".getBytes("ISO-8859-15"), "UTF-8"));
			view.setDataNotaProposta(DateUtil.formattaData(new java.util.Date().getTime()));
			view.setNomeFascicolo(vo.getFascicolo().getNome());




			view.setAllegato(allegato); 		
		}
		logger.info ("@@DDS ora chiamo caricaDocumentiGenericiFilenet __________________________");
		caricaDocumentiGenericiFilenet(view, vo);
		caricaLetteraIncaricoFirmata(view, vo, vo.getFascicolo());
		caricaNotaPropostaIncaricoFirmata(view, vo, vo.getFascicolo());
	}

	private void caricaNotaPropostaIncaricoFirmata(IncaricoView view, Incarico vo, Fascicolo fascicolo) throws Throwable {
		String nomeCartellaIncarico = FileNetUtil.getIncaricoCartella(vo.getId(), fascicolo.getDataCreazione(),
				fascicolo.getNome(), vo.getNomeIncarico());
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaIncarico)
				: documentaleDAO.leggiCartella(nomeCartellaIncarico);
		*/
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleDdsCryptDAO.leggiCartella(nomeCartellaIncarico)
				: documentaleDdsDAO.leggiCartella(nomeCartellaIncarico);
		logger.info ("@@DDS caricaNotaPropostaIncaricoFirmata __________________________");

		/*@@DDS inizio commento
		DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();
		DocumentoView notaPropostaFirmata = null;
		if (documenti != null) {
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.NOTA_PROPOSTA_INCARICO_DOCUMENT)){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.get_Name());

						File fileTmp = File.createTempFile("notaPropostaFirmata", "___" + documento.get_Name() );

						String uuid = documento.get_Id().toString();
						uuid = uuid.replace("{", "");
						uuid = uuid.replace("}", "");
						uuid = uuid.toLowerCase();

						if(isPenale)
							FileUtils.writeByteArrayToFile(fileTmp, documentaleCryptDAO.leggiContenutoDocumento(uuid));
						else
							FileUtils.writeByteArrayToFile(fileTmp, documentaleDAO.leggiContenutoDocumento(uuid));

						docView.setFile(fileTmp);
						docView.setContentType(documento.get_MimeType());

						docView.setUuid(uuid);
						notaPropostaFirmata = docView;
					}
				}
			}
			view.setLetteraFirmataDocNota(notaPropostaFirmata);
		}

		 */
		List<Document> listaDocumenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaIncarico): documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaIncarico);
		DocumentoView notaPropostaFirmata = null;
		if (listaDocumenti != null) {

			for (Document documento : listaDocumenti) {

				if( documento.getDocumentalClass().equals(FileNetClassNames.NOTA_PROPOSTA_INCARICO_DOCUMENT)){
					DocumentoView docView = new DocumentoView();
					docView.setNomeFile(documento.getContents().get(0).getContentsName());

					File fileTmp = File.createTempFile("notaPropostaFirmata", "___" + documento.getContents().get(0).getContentsName() );

					String uuid = documento.getId();
					uuid = uuid.replace("{", "");
					uuid = uuid.replace("}", "");


					if(isPenale)
						FileUtils.writeByteArrayToFile(fileTmp, documentaleDdsCryptDAO.leggiContenutoDocumento(uuid));
					else
						FileUtils.writeByteArrayToFile(fileTmp, documentaleDdsDAO.leggiContenutoDocumento(uuid));

					docView.setFile(fileTmp);
					docView.setContentType(documento.getContents().get(0).getContentsMimeType());

					docView.setUuid(uuid);
					notaPropostaFirmata = docView;
				}
			}
			view.setLetteraFirmataDocNota(notaPropostaFirmata);
		} else {
			logger.info(" La lista vuota :  nessun documento");
		}
	}

	private void caricaLetteraIncaricoFirmata(IncaricoView view, Incarico vo, Fascicolo fascicolo) throws Throwable {
		String nomeCartellaIncarico = FileNetUtil.getIncaricoCartella(vo.getId(), fascicolo.getDataCreazione(),
				fascicolo.getNome(), vo.getNomeIncarico());
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaIncarico)
				: documentaleDAO.leggiCartella(nomeCartellaIncarico);
		*/
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
		logger.info ("@@DDS caricaLetteraIncaricoFirmata __________________________");
		/*@@DDS inizio commento
		DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();
		DocumentoView letteraIncaricoFirmata = null;
		if (documenti != null) {
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.LETTERA_INCARICO_DOCUMENT)){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.get_Name());
						File fileTmp = File.createTempFile("letteraFirmata", "___" + documento.get_Name() );
						String uuid = documento.get_Id().toString();
						uuid = uuid.replace("{", "");
						uuid = uuid.replace("}", "");
						uuid = uuid.toLowerCase();

						if(isPenale)
							FileUtils.writeByteArrayToFile(fileTmp, documentaleCryptDAO.leggiContenutoDocumento(uuid));
						else
							FileUtils.writeByteArrayToFile(fileTmp, documentaleDAO.leggiContenutoDocumento(uuid));

						docView.setFile(fileTmp);
						docView.setContentType(documento.get_MimeType());

						docView.setUuid(uuid);
						letteraIncaricoFirmata = docView;
					}
				}
			}
			view.setLetteraFirmataDoc(letteraIncaricoFirmata);
		}

		 */
		List<Document> listaDocumenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaIncarico): documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaIncarico);
		DocumentoView letteraIncaricoFirmata = null;
		if (listaDocumenti != null) {

			for (Document documento : listaDocumenti) {

				if( documento.getDocumentalClass().equals(FileNetClassNames.LETTERA_INCARICO_DOCUMENT)){
					DocumentoView docView = new DocumentoView();
					docView.setNomeFile(documento.getContents().get(0).getContentsName());
					File fileTmp = File.createTempFile("letteraFirmata", "___" + documento.getContents().get(0).getContentsName() );
					String uuid = documento.getId();
					uuid = uuid.replace("{", "");
					uuid = uuid.replace("}", "");


					if(isPenale)
						FileUtils.writeByteArrayToFile(fileTmp, documentaleDdsCryptDAO.leggiContenutoDocumento(uuid));
					else
						FileUtils.writeByteArrayToFile(fileTmp, documentaleDdsDAO.leggiContenutoDocumento(uuid));

					docView.setFile(fileTmp);
					docView.setContentType(documento.getContents().get(0).getContentsMimeType());

					docView.setUuid(uuid);
					letteraIncaricoFirmata = docView;
				}
			}
			view.setLetteraFirmataDoc(letteraIncaricoFirmata);
		} else {
			logger.info(" La lista vuota :  nessun documento");
		}
	}

	private void caricaDocumentiGenericiFilenet(IncaricoView view, Incarico vo) throws Throwable {		
		String nomeCartellaIncarico = FileNetUtil.getIncaricoCartella(vo.getId(), vo.getFascicolo().getDataCreazione(), vo.getFascicolo().getNome(), vo.getNomeIncarico());
		boolean isPenale = vo.getFascicolo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO"); 
		Folder cartellaIncarico = isPenale? documentaleCryptDAO.leggiCartella(nomeCartellaIncarico) : documentaleDAO.leggiCartella(nomeCartellaIncarico);
		*/
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");

		logger.info ("@@DDS caricaDocumentiGenericiFilenet __________________________");
		/*@@DDS inizio commento
		DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();
		List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
		if( documenti != null ){
			PageIterator it = documenti.pageIterator();
			while(it.nextPage()){
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for( EngineObjectImpl objDocumento : documentiArray ){
					DocumentImpl documento = (DocumentImpl)objDocumento;
					if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.ALLEGATO_DOCUMENT) ){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.get_Name() );
						docView.setUuid(documento.get_Id().toString());
						listaDocumenti.add(docView);
					}
				}
			}
			view.setListaAllegatiGenerici(listaDocumenti);
		}

		 */
		List<Document> listaDoc = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaIncarico): documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaIncarico);
		List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
		if (listaDoc != null) {

			for (Document documento : listaDoc) {

				if( documento.getDocumentalClass().equals(FileNetClassNames.ALLEGATO_DOCUMENT) ){
					DocumentoView docView = new DocumentoView();
					docView.setNomeFile(documento.getContents().get(0).getContentsName() );
					docView.setUuid(documento.getId());
					logger.debug ("@@DDS ____________uuuid" + docView.getUuid());
					listaDocumenti.add(docView);
				}
			}
			view.setListaAllegatiGenerici(listaDocumenti);
		} else {
			logger.info(" La lista vuota :  nessun documento");
		}
	}

	/*private boolean isDefaultIncaricoDocument(String nomeFile) {
		if( nomeFile.startsWith( CostantiFileNet.PREFIX_LISTE_RIFERIMETO_NAME )
				|| nomeFile.startsWith( CostantiFileNet.PREFIX_PROCURA_NAME ) 
				|| nomeFile.startsWith( CostantiFileNet.PREFIX_VERIFICA_ANTICORRUZIONE_NAME )
				|| nomeFile.startsWith( CostantiFileNet.PREFIX_VERIFICA_PARTICORRELATE_NAME )
				|| nomeFile.startsWith( CostantiFileNet.PREFIX_LETTERA_INCARICO )
				|| nomeFile.startsWith( CostantiFileNet.PREFIX_NOTA_PROPOSTA)){
			return true;
		}
		return false;
	}*/

	@RequestMapping(value = "/incarico/salva", method = RequestMethod.POST)
	public String salvaIncarico(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated IncaricoView incaricoView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		String token=request.getParameter("CSRFToken");

		try {

			if (incaricoView.getOp() != null && !incaricoView.getOp().equals("salvaIncarico")) {
				String ritorno = invocaMetodoDaReflection(incaricoView, bindingResult, locale, model, request,
						response, this);

				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			/**
			 * Rimuovo acconti e bonus della lettera di incarico
			 * @author MASSIMO CARUSO
			 */
			if(incaricoView.getLetteraIncaricoId() != null && incaricoView.getLetteraIncaricoId() != 0)
				incaricoService.deleteBonusAcconti(incaricoView.getLetteraIncaricoId());
			logger.info("###### Bind : " + bindingResult);
			preparaPerSalvataggio(incaricoView, bindingResult);

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			long incaricoId = 0;
			boolean isNew = false;
			if (incaricoView.getIncaricoId() == null || incaricoView.getIncaricoId() == 0) {
				IncaricoView incaricoSalvato = incaricoService.inserisci(incaricoView);
				incaricoId = incaricoSalvato.getVo().getId();
				isNew = true;
			} else {
				//07/06/2018 da rimuovere il seguente metodo per bug bonus
				//incaricoService.deleteBonusAcconti(incaricoView.getLetteraIncaricoId());
				incaricoService.modifica(incaricoView);
				incaricoId = incaricoView.getVo().getId();
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
			if( isNew ){
				logger.debug("________________________________redirect:modifica.action?id=" + incaricoId);
				return "redirect:modifica.action?id=" + incaricoId+"&CSRFToken="+token;
			}
			return "redirect:dettaglio.action?id=" + incaricoId+"&CSRFToken="+token;
		} catch (Throwable e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}
	/*
	@RequestMapping("/incarico/elimina")
	public @ResponseBody RisultatoOperazioneRest eliminaIncarico(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			IncaricoView incaricoView = incaricoService.leggi(id);
			if(incaricoView != null){
				incaricoService.cancella(incaricoView);
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}*/

	private void preparaPerSalvataggio(IncaricoView view, BindingResult bindingResult)throws Throwable {
		Incarico vo = new Incarico();
		IncaricoView oldIncaricoView = null;
		byte[] out = null;
		JasperPrint jasperPrint = null;
		
		
		if (view.getIncaricoId() != null) {
			oldIncaricoView = incaricoService.leggi(view.getIncaricoId());

			vo.setId(view.getIncaricoId());
			vo.setNomeIncarico(view.getNomeIncarico());
			vo.setStatoIncarico(oldIncaricoView.getVo().getStatoIncarico());
			vo.setDataCreazione(oldIncaricoView.getVo().getDataCreazione());
		}

		vo.setCommento(view.getCommento());
		if( view.getDataAutorizzazione() != null && DateUtil.isData(view.getDataAutorizzazione())){
			vo.setDataAutorizzazione(DateUtil.toDate(view.getDataAutorizzazione()));	
		}
		if( view.getDataRichiestaAutorizzazione() != null && DateUtil.isData(view.getDataRichiestaAutorizzazione())){
			vo.setDataRichiestaAutorIncarico(DateUtil.toDate(view.getDataRichiestaAutorizzazione()));	
		} 

		vo.setFascicolo(view.getFascicoloRiferimento().getVo());


		vo.setCollegioArbitrale(CostantiDAO.FALSE_CHAR+"");



		/*LETTERA INCARICO*/
		if(  StringUtils.isNotBlank(view.getProtocollo()) &&  StringUtils.isNotBlank(view.getOggettoProtocollo()) ){

			LetteraIncarico letteraIncarico = vo.getLetteraIncarico() == null ? new LetteraIncarico() : vo.getLetteraIncarico();

			letteraIncarico.setId( view.getLetteraIncaricoId() == null ? 0 : view.getLetteraIncaricoId() );

			LetteraIncarico letteraOld = null;

			if(letteraIncarico.getId()!=0){

				letteraOld = incaricoService.leggiLetteraIncarico(letteraIncarico.getId());
			}

			if( view.getDataProtocollo() != null && DateUtil.isData(view.getDataProtocollo())){
				letteraIncarico.setDataProtocollo(DateUtil.toDate(view.getDataProtocollo()));
			}
			if( view.getOggettoProtocollo() != null && !view.getOggettoProtocollo().isEmpty()){

				String oggettoProtocolloCharDecode = StringUtil.escapeMsWord(view.getOggettoProtocollo());
				letteraIncarico.setOggetto(oggettoProtocolloCharDecode);
				view.setOggettoProtocollo(oggettoProtocolloCharDecode);
			}

			if( view.getProtocollo() != null && !view.getProtocollo().isEmpty()){

				String protocolloCharDecode = StringUtil.escapeMsWord(view.getProtocollo());
				letteraIncarico.setProtocollo(protocolloCharDecode);
				view.setProtocollo(protocolloCharDecode);
			}

			letteraIncarico.setQuadro(view.getIsQuadro());

			if(view.getNumQuadro() != null && !view.getNumQuadro().isEmpty()){

				String numQuadroCharDecode = StringUtil.escapeMsWord(view.getNumQuadro());
				letteraIncarico.setnAccordoQuadro(numQuadroCharDecode);
				view.setNumQuadro(numQuadroCharDecode);
			}

			if(view.getAttivita() != null && !view.getAttivita().isEmpty()){

				String attivitaCharDecode = StringUtil.escapeMsWord(view.getAttivita());
				letteraIncarico.setAttivitaStragiudizialePenale(attivitaCharDecode);
				view.setAttivita(attivitaCharDecode);
			}

			if(view.getUtenteConnesso()!=null && !view.getUtenteConnesso().isEmpty()){

				String utenteConnessoCharDecode = StringUtil.escapeMsWord(view.getUtenteConnesso());
				letteraIncarico.setLegaleInterno(utenteConnessoCharDecode);
				view.setUtenteConnesso(utenteConnessoCharDecode);
			}

			if(view.getQualifica()!=null && !view.getQualifica().isEmpty()){
				String qualificaCharDecode = StringUtil.escapeMsWord(view.getQualifica());
				letteraIncarico.setQualifica(qualificaCharDecode);
				view.setQualifica(qualificaCharDecode);
			}

			if(view.getInfoCompenso()!=null && !view.getInfoCompenso().isEmpty()){
				String infoCompensoCharDecode = StringUtil.escapeMsWord(view.getInfoCompenso());
				letteraIncarico.setInfoCompenso(infoCompensoCharDecode);
				view.setInfoCompenso(infoCompensoCharDecode);
			}

			if(view.getCompenso()!=null){
				letteraIncarico.setCompenso(view.getCompenso());
			}

			if(view.getValutaId()!=null){
				TipoValutaView tipoValuta = anagraficaStatiTipiService.leggiTipoValuta(view.getValutaId());
				letteraIncarico.setTipoValuta(tipoValuta.getVo());
			}

			if(view.getBonus() != null && !view.getBonus().isEmpty()){
				for(BonusView bonus:view.getBonus()){
					/**
					 * Aggiunta controllo bonus vuoti 
					 * @author MASSIMO CARUSO
					 **/
					if(bonus.getImporto()!= null && (bonus.getImporto().compareTo(new BigDecimal(0))>0)){
						Bonus bon = new Bonus();
						bon.setImporto(bonus.getImporto());
						bon.setDescrizione(bonus.getDescrizione());
						letteraIncarico.addBonus(bon);
					}
				}
			}

			if(view.getAcconto() != null && !view.getAcconto().isEmpty()){
				for(AccontoView acconti:view.getAcconto()){
					/**
					 * Aggiunta controllo acconti vuoti 
					 * @author MASSIMO CARUSO
					 **/
					if(acconti.getImporto()!= null && (acconti.getImporto().compareTo(new BigDecimal(0))>0)){	
						Acconti acc = new Acconti();
						acc.setImporto(acconti.getImporto());
						acc.setAnno(acconti.getAnno());
						letteraIncarico.addAcconti(acc);
					}
				}
			}
			

			/**
			 * Salvo l'anno di fine incarico come
			 * acconto dove l'importo � -1
			 */
			Acconti acc = new Acconti();
			acc.setImporto(new BigDecimal(-1));
			acc.setAnno(view.getSaldoAnno());
			letteraIncarico.addAcconti(acc);


			if(view.getStatoIncaricoCode().equals("B") || letteraOld==null){
//				String lifecycle = getLifecycleFile(view);
//				letteraIncarico.setLifecycleXml(lifecycle);
//				letteraIncarico.setDataInvio(null);
//				letteraIncarico.setInviata(0);
//				letteraIncarico.setReturnFile(null);
//				letteraIncarico.setReturnFileName(null);
				
				String path  = "/jasper/";
				String pathReport  = path + LETTERA_INCARICO_JASPER_REPORT_FILE_NAME;
				logger.info("---> Path report : " + pathReport);
		        JasperReport jasperReport = (JasperReport) JRLoader.loadObject( this.getClass().getClassLoader().getResourceAsStream(  pathReport ));
				
		        Locale locale = new Locale("it", "IT"); // DEFAULT
		        URL resourceStylePath = ClassPathUtils.getResource(this.getClass(), "/jasper/");
				
				Map<String,Object> reportParameters = getLetterParametersForPdf(view, locale, resourceStylePath);
				
				List<IncaricoView> lista = new ArrayList<IncaricoView>();
				jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameters, new JRBeanCollectionDataSource(lista));      
		    	out = JasperExportManager.exportReportToPdf(jasperPrint);        	
		    	System.out.println("Bytes: " + out.length);
		    	
		    	letteraIncarico.setLifecycleXml("");
		    	letteraIncarico.setDataInvio(new Date());
		    	letteraIncarico.setInviata(1);
		    	
		    	Blob blob = new javax.sql.rowset.serial.SerialBlob(out);
		    	letteraIncarico.setReturnFile(blob);
				letteraIncarico.setReturnFileName("LetteraIncarico.pdf");
				
			}
			else{
				letteraIncarico.setLifecycleXml(letteraOld.getLifecycleXml());
				letteraIncarico.setDataInvio(letteraOld.getDataInvio());
				letteraIncarico.setInviata(letteraOld.getInviata());
				letteraIncarico.setReturnFile(letteraOld.getReturnFile());
				letteraIncarico.setReturnFileName(letteraOld.getReturnFileName());
			}

			//LETTERA INCARICO FIRMATA

			if( view.getLetteraFirmataDoc() != null && view.getLetteraFirmataDoc().isNuovoDocumento() && view.getLetteraFirmataDoc().getFile() != null ){
				Documento documento = new Documento();
				documento.setUuid(UUID.randomUUID().toString());
				documento.setContentType( view.getLetteraFirmataDoc().getContentType());
				documento.setNomeFile(view.getLetteraFirmataDoc().getNomeFile());
				documento.setClasseDocumentale( FileNetClassNames.LETTERA_INCARICO_DOCUMENT );
				letteraIncarico.setDocumento_firmato(documento);

			}else if( view.getLetteraFirmataDoc() != null && view.getLetteraFirmataDoc().getVo() != null){

				letteraIncarico.setDocumento_firmato(oldIncaricoView.getVo().getLetteraIncarico().getDocumento_firmato() );
			}

			vo.setLetteraIncarico(letteraIncarico);
		}

		if( view.getListeRiferimentoDoc() != null && view.getListeRiferimentoDoc().isNuovoDocumento() && view.getListeRiferimentoDoc().getFile() != null ){
			ListaRiferimento listaRiferimento = new ListaRiferimento();
			Documento documento = new Documento();
			documento.setUuid(UUID.randomUUID().toString());
			documento.setContentType( view.getListeRiferimentoDoc().getContentType());
			documento.setNomeFile(view.getListeRiferimentoDoc().getNomeFile());
			documento.setClasseDocumentale( FileNetClassNames.LISTA_RIFERIMENTO_DOCUMENT );
			listaRiferimento.setDocumento(documento);
			vo.setListaRiferimento( listaRiferimento );

		}else if( view.getListeRiferimentoDoc() != null && view.getListeRiferimentoDoc().getVo() != null){ 
			vo.setListaRiferimento(  oldIncaricoView.getVo().getListaRiferimento() );
		}

		if( view.getProcuraDoc() != null && view.getProcuraDoc().isNuovoDocumento() && view.getProcuraDoc().getFile() != null ){
			Procura procura = new Procura();
			Documento documento = new Documento();
			documento.setUuid(UUID.randomUUID().toString());
			logger.debug ("@@DDS ____________uuuid" + documento.getUuid());
			documento.setContentType( view.getProcuraDoc().getContentType());
			documento.setNomeFile(view.getProcuraDoc().getNomeFile());
			documento.setClasseDocumentale( FileNetClassNames.PROCURA_DOCUMENT );
			procura.setDocumento(documento); 
			vo.setProcura(procura);

		}else if( view.getProcuraDoc() != null && view.getProcuraDoc().getVo() != null){

			vo.setProcura( oldIncaricoView.getVo().getProcura() );
		}

		if( view.getVerificaAnticorruzioneDoc() != null && view.getVerificaAnticorruzioneDoc().isNuovoDocumento() && view.getVerificaAnticorruzioneDoc().getFile() != null ){
			VerificaAnticorruzione verificaAnticorruzione = new VerificaAnticorruzione();
			Documento documento = new Documento();
			documento.setUuid(UUID.randomUUID().toString());
			documento.setContentType( view.getVerificaAnticorruzioneDoc().getContentType());
			documento.setNomeFile(view.getVerificaAnticorruzioneDoc().getNomeFile());
			documento.setClasseDocumentale( FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT );
			verificaAnticorruzione.setDocumento(documento);  
			vo.setVerificaAnticorruzione(verificaAnticorruzione);

		}else if( view.getVerificaAnticorruzioneDoc() != null && view.getVerificaAnticorruzioneDoc().getVo() != null){

			vo.setVerificaAnticorruzione( oldIncaricoView.getVo().getVerificaAnticorruzione() );
		}

		if( view.getVerificaPartiCorrelateDoc() != null && view.getVerificaPartiCorrelateDoc().isNuovoDocumento() && view.getVerificaPartiCorrelateDoc().getFile() != null ){
			VerificaPartiCorrelate verificaPartiCorrelate = new VerificaPartiCorrelate();
			Documento documento = new Documento();
			documento.setUuid(UUID.randomUUID().toString());
			documento.setContentType( view.getVerificaPartiCorrelateDoc().getContentType());
			documento.setNomeFile(view.getVerificaPartiCorrelateDoc().getNomeFile());
			documento.setClasseDocumentale( FileNetClassNames.VERIFICA_PARTI_CORRELATE_DOCUMENT );
			verificaPartiCorrelate.setDocumento(documento);  
			vo.setVerificaPartiCorrelate(new VerificaPartiCorrelate());		
		}else if( view.getVerificaPartiCorrelateDoc() != null && view.getVerificaPartiCorrelateDoc().getVo() != null){

			vo.setVerificaPartiCorrelate( oldIncaricoView.getVo().getVerificaPartiCorrelate() );
		}

		Locale linguaItaliana = Locale.ITALIAN;

		if (view.getNazioneCode() != null && view.getNazioneCode().trim().length() > 0) {
			NazioneView nazioneView = nazioneService.leggi(view.getNazioneCode(), linguaItaliana, false);
			vo.setNazione(nazioneView.getVo());
		}

		if (view.getSpecializzazioneCode() != null && view.getSpecializzazioneCode().trim().length() > 0) {
			SpecializzazioneView specializzazioneView = specializzazioneService.leggi(view.getSpecializzazioneCode(), linguaItaliana);
			vo.setSpecializzazione(specializzazioneView.getVo());
		}

		if( view.getProfessionistaId() != null && view.getProfessionistaId() > 0 ){
			ProfessionistaEsternoView professionistaEsternoView = professionistaEsternoService.leggi(view.getProfessionistaId());
			vo.setProfessionistaEsterno(professionistaEsternoView.getVo());
		} 


		/*NOTA PROPOSTA INCARICO*/
		if(StringUtils.isNotBlank(view.getDescrizione())){

			NotaPropIncarico notaPropIncarico = vo.getNotaPropIncarico() == null ? new NotaPropIncarico() : vo.getNotaPropIncarico();
			notaPropIncarico.setId( view.getNotaIncaricoId() == null ? 0 : view.getNotaIncaricoId() );

			NotaPropIncarico notaPropostaIncaricoOld = null;	
			if(notaPropIncarico.getId()!=0){
				notaPropostaIncaricoOld = incaricoService.leggiNotaPropostaIncarico(notaPropIncarico.getId());
			}

			if(!view.getStatoIncarico().equals("A")){
//				String lifecycleXmlNP = getLifecycleFileNotaProposta(view);
//
//				notaPropIncarico.setLifecycleXml(lifecycleXmlNP);
//				notaPropIncarico.setDataInvio(null);
//				notaPropIncarico.setInviata(0);
//				notaPropIncarico.setReturnFile(null);
//				notaPropIncarico.setReturnFileName(null);
				
				
				String path  = "/jasper/";
				String pathReport  = path + NOTA_INCARICO_JASPER_REPORT_FILE_NAME;   
		        JasperReport jasperReport = (JasperReport) JRLoader.loadObject( this.getClass().getClassLoader().getResourceAsStream(  pathReport ));
				
		        Locale locale = new Locale("it", "IT"); // DEFAULT
		        URL resourceStylePath = ClassPathUtils.getResource(this.getClass(), "/jasper/");
				
				Map<String,Object> reportParameters = getNoteParametersForPdf(view, locale, resourceStylePath);
				
				List<IncaricoView> lista = new ArrayList<IncaricoView>();
				jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameters, new JRBeanCollectionDataSource(lista));      
		    	out = JasperExportManager.exportReportToPdf(jasperPrint);        	
		    	System.out.println("Bytes: " + out.length);
		    	
		    	notaPropIncarico.setLifecycleXml("");
		    	notaPropIncarico.setDataInvio(new Date());
		    	notaPropIncarico.setInviata(1);
		    	
		    	Blob blob = new javax.sql.rowset.serial.SerialBlob(out);
		    	notaPropIncarico.setReturnFile(blob);
		    	notaPropIncarico.setReturnFileName("NotaProposta.pdf");
			}
			else{
				notaPropIncarico.setLifecycleXml(notaPropostaIncaricoOld.getLifecycleXml());
				notaPropIncarico.setDataInvio(notaPropostaIncaricoOld.getDataInvio());
				notaPropIncarico.setReturnFile(notaPropostaIncaricoOld.getReturnFile());
				notaPropIncarico.setReturnFileName(notaPropostaIncaricoOld.getReturnFileName());
			}			

			if(view.getDescrizione()!=null && !view.getDescrizione().isEmpty()){
				String descrizioneCharDecode = StringUtil.escapeMsWord(view.getDescrizione());
				notaPropIncarico.setDescrizione(descrizioneCharDecode);
				view.setDescrizione(descrizioneCharDecode);
			}

			if(view.getOggetto()!=null && !view.getOggetto().isEmpty()){
				String oggettoCharDecode = StringUtil.escapeMsWord(view.getOggetto());
				notaPropIncarico.setOggetto(oggettoCharDecode);
				view.setOggetto(oggettoCharDecode);
			}

			if(view.getPratica()!=null && !view.getPratica().isEmpty()){
				String praticaCharDecode = StringUtil.escapeMsWord(view.getPratica());
				notaPropIncarico.setPratica(praticaCharDecode);
				view.setPratica(praticaCharDecode);
			}

			if(view.getProponente()!=null && !view.getProponente().isEmpty()){
				String proponenteCharDecode = StringUtil.escapeMsWord(view.getProponente());
				notaPropIncarico.setProponente(proponenteCharDecode);
				view.setProponente(proponenteCharDecode);
			}

			if(view.getProposta()!=null && !view.getProposta().isEmpty()){
				String propostaCharDecode = StringUtil.escapeMsWord(view.getProposta());
				notaPropIncarico.setProposta(propostaCharDecode);
				view.setProposta(propostaCharDecode);
			}

			if(view.getDataNotaProposta()!=null && !view.getDataNotaProposta().isEmpty()){

				String dataNotaPropostaCharDecode = StringUtil.escapeMsWord(view.getDataNotaProposta());
				notaPropIncarico.setDataPropIncarico(DateUtil.toDate(dataNotaPropostaCharDecode));
				view.setDataNotaProposta(dataNotaPropostaCharDecode);
			}

			if(view.getNomeForo()!=null && !view.getNomeForo().isEmpty()){
				String nomeForoCharDecode = StringUtil.escapeMsWord(view.getNomeForo());
				notaPropIncarico.setNomeForo(nomeForoCharDecode);
				view.setNomeForo(nomeForoCharDecode);
			}

			if(view.getInfoCompensoNotaProp()!=null && !view.getInfoCompensoNotaProp().isEmpty()){
				String notaPropIncaricoCharDecode = StringUtil.escapeMsWord(view.getInfoCompensoNotaProp());
				notaPropIncarico.setInfoCompensoNP(notaPropIncaricoCharDecode);
				view.setInfoCompensoNotaProp(notaPropIncaricoCharDecode);
			}

			if(view.getInfoCorresponsioneCompenso()!=null && !view.getInfoCorresponsioneCompenso().isEmpty()){
				String infoCorresponsioneCompensoCharDecode = StringUtil.escapeMsWord(view.getInfoCorresponsioneCompenso());
				notaPropIncarico.setInfoCorresponsione(infoCorresponsioneCompensoCharDecode);
				view.setInfoCorresponsioneCompenso(infoCorresponsioneCompensoCharDecode);
			}

			if(view.getSceltaInfo()!=null){
				notaPropIncarico.setSceltaInfo(view.getSceltaInfo());
			}			

			if(view.getInfoHandBook()!=null && !view.getInfoHandBook().isEmpty()){
				if(view.getSceltaInfo()==0){
					notaPropIncarico.setInfoHandBook("Si precisa che, con riferimento agli allegati all'handbook, non sono state apportate modifiche");
				}
				else{
					String infoHandBookCharDecode = StringUtil.escapeMsWord(view.getInfoHandBook());
					notaPropIncarico.setInfoHandBook(infoHandBookCharDecode);
					view.setInfoHandBook(infoHandBookCharDecode);
				}	
			}

			if(view.getResponsabili()!=null && !view.getResponsabili().isEmpty()){
				String responsabiliCharDecode = StringUtil.escapeMsWord(view.getResponsabili());
				notaPropIncarico.setResponsabili(responsabiliCharDecode);
				view.setResponsabili(responsabiliCharDecode);
			}

			if(view.getApprovatore()!=null && !view.getApprovatore().isEmpty()){
				String approvatoreCharDecode = StringUtil.escapeMsWord(view.getApprovatore());
				notaPropIncarico.setApprovatore(approvatoreCharDecode);
				view.setApprovatore(approvatoreCharDecode);
			}

			if(view.getAutorizzatore()!=null && !view.getAutorizzatore().isEmpty()){
				String autorizzatoreCharDecode = StringUtil.escapeMsWord(view.getAutorizzatore());
				notaPropIncarico.setAutorizzatore(autorizzatoreCharDecode);
				view.setAutorizzatore(autorizzatoreCharDecode);
			}

			if( view.getValoreIncarico() != null && !view.getValoreIncarico().isEmpty()){
				String valoreIncaricoCharDecode = StringUtil.escapeMsWord(view.getValoreIncarico());
				notaPropIncarico.setValoreIncarico(valoreIncaricoCharDecode);
				view.setValoreIncarico(valoreIncaricoCharDecode);
			}


			//NOTA PROPOSTA INCARICO FIRMATA

			if( view.getLetteraFirmataDocNota() != null && view.getLetteraFirmataDocNota().isNuovoDocumento() && view.getLetteraFirmataDocNota().getFile() != null ){
				Documento documento = new Documento();
				documento.setUuid(UUID.randomUUID().toString());
				documento.setContentType( view.getLetteraFirmataDocNota().getContentType());
				documento.setNomeFile(view.getLetteraFirmataDocNota().getNomeFile());
				documento.setClasseDocumentale( FileNetClassNames.NOTA_PROPOSTA_INCARICO_DOCUMENT );
				notaPropIncarico.setDocumento_firmato(documento);

			}else if( view.getLetteraFirmataDocNota() != null && view.getLetteraFirmataDocNota().getVo() != null){

				notaPropIncarico.setDocumento_firmato(oldIncaricoView.getVo().getNotaPropIncarico().getDocumento_firmato() );
			}
			vo.setNotaPropIncarico(notaPropIncarico);
		}
		view.setVo(vo);
	}


	/*private String getLifecycleFile(IncaricoView view) {

		String out = "";

		Element root = new Element("LetteraIncaricoPDF");

		Document doc = new Document();

		String suffix = "id_";

		String div = "<div xmlns=\"http://www.w3.org/1999/xhtml\">";

		String chiudiDiv ="</div>";

		Element unozero = new Element(suffix+"0");
		Element duezero = new Element(suffix+"00");
		Element trezero = new Element(suffix+"000");

		Element uno = new Element(suffix+"1");
		Element due = new Element(suffix+"2");
		Element tre = new Element(suffix+"3");
		Element quattro = new Element(suffix+"4");
		Element cinque = new Element(suffix+"5");
		Element sei = new Element(suffix+"6");
		Element sette = new Element(suffix+"7");
		Element otto = new Element(suffix+"8");
		Element ottoBis = new Element(suffix+"8bis");
		Element nove = new Element(suffix+"9");
		Element dieci = new Element(suffix+"10");
		Element undici = new Element(suffix+"11");
		Element dodici = new Element(suffix+"12");
		Element tredici = new Element(suffix+"13");
		Element trediciBis = new Element(suffix+"13bis");
		Element quattordici = new Element(suffix+"14");
		Element quindici = new Element(suffix+"15");
		Element sedici = new Element(suffix+"16");
		Element diciasette = new Element(suffix+"17");
		Element diciotto = new Element(suffix+"18");
		Element dicianove = new Element(suffix+"19");
		Element venti = new Element(suffix+"20");
		Element ventuno = new Element(suffix+"21");
		Element ventidue = new Element(suffix+"22");
		Element ventitre = new Element(suffix+"23");


		String tipologia = view.getFascicoloRiferimento().getVo().getTipologiaFascicolo().getCodGruppoLingua();
		String settore = view.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua();



		if(view.getListaSocietaAddebitoAggiunteDescLet().size()>1){
			unozero.addContent("");
			duezero.addContent(view.getSnamretegasWebsite());
			trezero.addContent(view.getPieDiPaginaSnam());
		}
		else if (view.getListaSocietaAddebitoAggiunteDescLet().size()==1){
			SocietaView societa = view.getListaSocietaAddebitoAggiunteDescLet().get(0);
			if(societa.getRagioneSociale().equalsIgnoreCase("Snam S.p.A."))
				unozero.addContent("");
			else
				unozero.addContent(societa.getRagioneSociale());
			duezero.addContent(societa.getSitoWeb());
			trezero.addContent(societa.getPieDiPagina());
		}

		uno.addContent(view.getDataProtocollo());

		due.addContent(view.getUnitaOrganizzativa());

		tre.addContent(view.getProtocollo());

		if(tipologia.equals("TFSC_4")){
			quattro.addContent("Dott./Dott.ssa");
			sette.addContent("Dott./Dott.ssa");
		}
		else{
			quattro.addContent("Avv.");
			sette.addContent("Avv.");
		}

		ProfessionistaEsterno prof = view.getProfessionistaSelezionato().getVo();
		StudioLegale studio = prof.getStudioLegale();

		String cinqueAdd = prof.getNome()==null?"":prof.getNome()+
				" "+prof.getCognome()==null?"":prof.getCognome()+"<br/>";

		if(studio!=null)
			cinqueAdd +=studio.getDenominazione()==null?"":studio.getDenominazione()+" "
					+studio.getIndirizzo()==null?"":studio.getIndirizzo()+" "
							+studio.getCap()==null?"":studio.getCap()+" "
									+studio.getCitta()==null?"":studio.getCitta();

		cinque.addContent(div+cinqueAdd+chiudiDiv);

		sei.addContent(div+"<b>"+view.getOggettoProtocollo()+"</b>"+chiudiDiv);

		if(tipologia.equals("TFSC_2") || tipologia.equals("TFSC_4") || settore.equals("TSTT_2")){
			otto.addContent("facendo seguito alle conversazioni e intese intercorse,");
		}

		if(view.getIsQuadro())
			ottoBis.addContent(StringEscapeUtils.unescapeHtml4(div+"<br/>con espresso richiamo all'accordo quadro con Lei sottoscritto n."+view.getNumQuadro()+chiudiDiv));

		String societaAddebito = ""+div;


		for(String soc: view.getListaSocietaAddebitoAggiunteDesc()){
			societaAddebito+=soc+"<br/>";
		}

		societaAddebito+=chiudiDiv;

		nove.addContent(StringEscapeUtils.unescapeHtml4(societaAddebito));

		if(view.getAttivita()!=null)
			dieci.addContent(StringEscapeUtils.unescapeHtml4(div+view.getAttivita()+chiudiDiv));
		else{
			dieci.addContent("");
		}

		undici.addContent(view.getUtenteConnesso());

		if(tipologia.equals("TFSC_1") && !settore.equals("TSTT_2")){
			dodici.addContent(StringEscapeUtils.unescapeHtml4("Inoltre, qualsiasi atto processuale, "
					+ "comunicazione e/o documento da depositare avanti ad Autorit� giudiziarie dovr� essere preventivamente "
					+ "e con congruo anticipo sottoposto al legale interno. Parimenti, copia di ogni documento "
					+ "o atto prodotto in giudizio e rilevante ai fini della tutela della posizione della Societ� "
					+ "dovr� essere tempestivamente inviato al legale interno. Infine, eventuali proposte relative "
					+ "ad accordi transattivi dovranno essere immediatamente portate a conoscenza del legale interno "
					+ "e da quest'ultimo espressamente approvate."));
		}

		tredici.addContent(StringEscapeUtils.unescapeHtml4(div+view.getInfoCompenso()+chiudiDiv));

		List<BonusView> bonus = view.getBonus();
		List<AccontoView> acconti = view.getAcconto();

		//GRIGLIA COMPENSI

		String addToTrediciBis = div;

		addToTrediciBis+="Il  Compenso sar� di  Euro  "+view.getCompenso()+"<br/>";

		if(bonus.size()!=0){
			addToTrediciBis+="In caso di esito favorevole verr� altres� riconosciuto un Bonus pari a:"+"<br/>";
			addToTrediciBis+="<ul type='disc'>";
			for (BonusView bon : bonus){
				addToTrediciBis+="<li>"+bon.getImporto()+" Euro : "+bon.getDescrizione()+"; </li>";
			}
			addToTrediciBis+="</ul><br/>";
		}

		addToTrediciBis+="Il Compenso sar� corrisposto quanto: ";

		for(AccontoView acconto:acconti){
			addToTrediciBis+="ad "+acconto.getImporto()+"Euro a fine dell'anno "+acconto.getAnno()+", ";
		}

		addToTrediciBis+="ed il saldo residuo di "+view.getSaldoImporto()+" Euro, eventualmente incrementato del Bonus legittimato dall'intervenuto esito favorevole/parzialmente favorevole della causa, all'esito della stessa.<br/>"+chiudiDiv;

		trediciBis.addContent(addToTrediciBis);

		//GRIGLIA COMPENSI



		if(tipologia.equals("TFSC_1") && !settore.equals("TSTT_2")){
			quattordici.addContent("Vi precisiamo inoltre che i compensi per la domiciliazione, come per altre consulenze (periti, ecc.) che dovessero, "
					+ "con l'approvazione del legale interno, rendersi necessarie, dovranno essere anticipati dal Vostro studio e inseriti, "
					+ "previa puntuale e specifica indicazione, nella parcella immediatamente successiva.");
		}

		String societaAddebitoDesLet = ""+div;


		for(SocietaView soc: view.getListaSocietaAddebitoAggiunteDescLet()){
			societaAddebitoDesLet+=soc.getNome()+"<br/>";
		}

		quindici.addContent(societaAddebitoDesLet+chiudiDiv);

		if(tipologia.equals("TFSC_1") && !settore.equals("TSTT_2")){
			sedici.addContent("L'adempimento dell'incarico vale quale integrale accettazione dei contenuti della presente, "
					+ "anche per conto dell'eventuale domiciliatario, "
					+ "cos�; come il Vostro impegno a rispettare rigorosamente il Codice Deontologico forense, "
					+ "cos� come le regole etiche da applicarsi nelle attivit� professionali contenute "
					+ "in ogni altro eventuale codice professionale o raccolta di regole applicabili alla Sua professione");
		}
		else if(tipologia.equals("TFSC_2") || settore.equals("TSTT_2")){
			sedici.addContent("L'accettazione del presente incarico vale quale integrale accettazione dei contenuti in esso indicati, "
					+ "cos� come il Suo impegno a rispettare rigorosamente il Codice Deontologico forense, "
					+ "cos� come le regole etiche da applicarsi nelle attivit� professionali contenute in "
					+ "ogni altro eventuale codice professionale o raccolta di regole applicabili alla Sua professione");
		}
		else if(tipologia.equals("TFSC_4")){
			sedici.addContent("l'adempimento dell'incarico vale quale integrale accettazione dei contenuti della presente, "
					+ "cos� come le regole etiche da applicarsi nelle attivit� professionali contenute "
					+ "in ogni altro eventuale codice professionale o raccolta di regole applicabili alla Sua professione");
		}

		if(view.getListaSocietaAddebitoAggiunteDescLet().size()>1){
			diciasette.addContent("Snam S.p.A.");
			diciotto.addContent(view.getSnamretegasWebsite());
		}
		else{
			diciasette.addContent(view.getListaSocietaAddebitoAggiunteDescLet().get(0).getRagioneSociale());
			diciotto.addContent(view.getListaSocietaAddebitoAggiunteDescLet().get(0).getSitoWeb());
		}

		dicianove.addContent(view.getSnamretegasWebsite());

		venti.addContent(view.getEmailSegnalazioni());

		if(tipologia.equals("TFSC_1") && !settore.equals("TSTT_2")){
			String outUno = "Qualora si rendesse necessaria la nomina di un domiciliatario, la sua individuazione dovr� "
					+ "rispondere ai principi del Codice Etico di ";
			if(view.getListaSocietaAddebitoAggiunteDescLet().size()>1){
				outUno+="Snam S.p.A.";
			}
			else{
				outUno+=view.getListaSocietaAddebitoAggiunteDescLet().get(0).getRagioneSociale();
			}
			outUno+=", ai principi deontologici nonch� ai principi contenuti nella clausola 7) che precede";
			ventuno.addContent(outUno);
		}

		ventidue.addContent(view.getQualifica());

		ventitre.addContent(studio==null?"":studio.getDenominazione());



		root.addContent(unozero);
		root.addContent(duezero);
		root.addContent(trezero);
		root.addContent(uno);
		root.addContent(due);
		root.addContent(tre);
		root.addContent(quattro);
		root.addContent(cinque);
		root.addContent(sei);
		root.addContent(sette);
		root.addContent(otto);
		root.addContent(ottoBis);
		root.addContent(nove);

		root.addContent(dieci);
		root.addContent(undici);
		root.addContent(dodici);
		root.addContent(tredici);
		root.addContent(trediciBis);
		root.addContent(quattordici);
		root.addContent(quindici);
		root.addContent(sedici);

		root.addContent(diciasette);
		root.addContent(diciotto);
		root.addContent(dicianove);
		root.addContent(venti);
		root.addContent(ventuno);
		root.addContent(ventidue);
		root.addContent(ventitre);

		doc.setRootElement(root);

		//XMLOutputter outter = new XMLOutputter();

		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat()) {
			@Override
			public String escapeElementEntities(String str) {
				return str;
			}
		};



		//outter.setFormat(Format.getPrettyFormat());

		out = outputter.outputString(doc);

		//out = XML.toJSONObject(outter.outputString(doc)).toString();

		return out;
	}*/
	
	
	private Map<String,Object> getLetterParametersForPdf(IncaricoView view, Locale locale, URL resourceStylePath) {
		
		Map<String,Object> reportParameters = new HashMap<>();
        reportParameters.put("SUBREPORT_DIR", "/jasper/"); 
	    reportParameters.put("reportTitle", LETTERA_INCARICO_JASPER_REPORT_TITLE);
	    reportParameters.put(PARAMETER_SUBREPORTDIR,   "/jasper/"); //assolutamente necesario altrimenti non funzionano i sottoreport
		reportParameters.put(PARAMETER_STYLEDIR,       "/jasper/"); //assolutamente necesario altrimenti non funzionano gli stili
	    reportParameters.put(PARAMETER_IMAGERDIR,      "/jasper/"); //assolutamente necesario altrimenti non funzionano gli stili
	    reportParameters.put(JRParameter.REPORT_LOCALE,   locale);     //assolutamente necesario altrimenti non funzionano le formattazioni internazionali
	    reportParameters.put(PARAMETER_LANGUAGE,       locale.getLanguage());
	    reportParameters.put(PARAMETER_COUNTRY,        locale.getLanguage().toUpperCase());

		String tipologia = view.getFascicoloRiferimento().getVo().getTipologiaFascicolo().getCodGruppoLingua();
		String settore = view.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua();



		if(view.getListaSocietaAddebitoAggiunteDescLet().size()>1){
			reportParameters.put("0","");
			reportParameters.put("00",view.getSnamretegasWebsite());
			reportParameters.put("000",view.getPieDiPaginaSnam());
		}
		else if (view.getListaSocietaAddebitoAggiunteDescLet().size()==1){
			SocietaView societa = view.getListaSocietaAddebitoAggiunteDescLet().get(0);
			if(societa.getRagioneSociale().equalsIgnoreCase("Snam S.p.A."))
				
				reportParameters.put("0","");
			else
				reportParameters.put("0",societa.getRagioneSociale());

			reportParameters.put("00",societa.getSitoWeb());
			reportParameters.put("000",societa.getPieDiPagina());
		}
		
		reportParameters.put("1",view.getDataProtocollo());
		reportParameters.put("2",view.getUnitaOrganizzativa());
		reportParameters.put("3",view.getProtocollo());

		if(tipologia.equals("TFSC_4")){
			reportParameters.put("4", "Dott./Dott.ssa");
			reportParameters.put("7", "Dott./Dott.ssa");
		}
		else{
			reportParameters.put("4", "Avv.");
			reportParameters.put("7", "Avvocato");
		}

		ProfessionistaEsterno prof = view.getProfessionistaSelezionato().getVo();
		StudioLegale studio = prof.getStudioLegale();
		
		String cinqueAdd = "";
		cinqueAdd += prof.getCognomeNome() + "<br/>";

		if(studio!=null){
			
			cinqueAdd +=studio.getDenominazione()!=null?"":studio.getDenominazione()+"<br/>";
			cinqueAdd +=studio.getIndirizzo()==null?"":studio.getIndirizzo()+"<br/>";
			cinqueAdd +=studio.getCap()==null?"":studio.getCap()+" ";
			cinqueAdd +=studio.getCitta()==null?"":studio.getCitta();
		}

		reportParameters.put("5", cinqueAdd);

		reportParameters.put("6", view.getOggettoProtocollo());

		if(tipologia.equals("TFSC_2") || tipologia.equals("TFSC_4") || settore.equals("TSTT_2")){
			
			reportParameters.put("8", "facendo seguito alle conversazioni e intese intercorse,");
		}else{
			reportParameters.put("8", "");
		}

		if(view.getIsQuadro())
			reportParameters.put("8bis", "con espresso richiamo all'accordo quadro con Lei sottoscritto n."+view.getNumQuadro());
		else
			reportParameters.put("8bis", "");
		

		String societaAddebito = "";

		for(String soc: view.getListaSocietaAddebitoAggiunteDesc()){
			societaAddebito+=soc+" ";
		}
		reportParameters.put("9", societaAddebito);

		if(view.getAttivita()!=null)
			reportParameters.put("10", view.getAttivita());
		else{
			reportParameters.put("10", "");
		}
		
		reportParameters.put("11", view.getUtenteConnesso());

		if(tipologia.equals("TFSC_1") && !settore.equals("TSTT_2")){
			
			reportParameters.put("12", "Inoltre, qualsiasi atto processuale, "
					+ "comunicazione e/o documento da depositare avanti ad Autorit� giudiziarie dovr� essere preventivamente "
					+ "e con congruo anticipo sottoposto al legale interno. Parimenti, copia di ogni documento "
					+ "o atto prodotto in giudizio e rilevante ai fini della tutela della posizione della Societ� "
					+ "dovr� essere tempestivamente inviato al legale interno. Infine, eventuali proposte relative "
					+ "ad accordi transattivi dovranno essere immediatamente portate a conoscenza del legale interno "
					+ "e da quest'ultimo espressamente approvate.");
		}else{
			reportParameters.put("12", "");
		}
		
		reportParameters.put("13", view.getInfoCompenso());

		List<BonusView> bonus = view.getBonus();
		List<AccontoView> acconti = view.getAcconto();

		//GRIGLIA COMPENSI

		String addToTrediciBis = "";

		addToTrediciBis+="Il Compenso sar� di Euro "+view.getCompenso()+"<br>";

		if(bonus.size()!=0){
			addToTrediciBis+="In caso di esito favorevole verr� altres� riconosciuto un Bonus pari a:"+"<br>";
			addToTrediciBis+="<ul type='disc'>";
			for (BonusView bon : bonus){
				addToTrediciBis+="<li>"+bon.getImporto()+" Euro : "+bon.getDescrizione()+";</li>";
			}
			addToTrediciBis+="</ul><br>";
		}

		addToTrediciBis+="Il Compenso sar� corrisposto quanto: ";

		if (acconti!=null) {
			for (AccontoView acconto : acconti) {
				addToTrediciBis += "ad " + acconto.getImporto() + "Euro a fine dell'anno " + acconto.getAnno() + ", ";
			}
		}
		addToTrediciBis+="ed il saldo residuo di "+view.getSaldoImporto()+" Euro, eventualmente incrementato del Bonus legittimato dall'intervenuto esito favorevole/parzialmente favorevole della causa, all'esito della stessa.";

		reportParameters.put("13bis", addToTrediciBis);

		//GRIGLIA COMPENSI

		if(tipologia.equals("TFSC_1") && !settore.equals("TSTT_2")){
			reportParameters.put("14", "Vi precisiamo inoltre che i compensi per la domiciliazione, come per altre consulenze (periti, ecc.) che dovessero, "
					+ "con l'approvazione del legale interno, rendersi necessarie, dovranno essere anticipati dal Vostro studio e inseriti, "
					+ "previa puntuale e specifica indicazione, nella parcella immediatamente successiva.");
		}else{
			reportParameters.put("14", "");
		}

		String societaAddebitoDesLet = "";


		for(SocietaView soc: view.getListaSocietaAddebitoAggiunteDescLet()){
			societaAddebitoDesLet+=soc.getNome()+" ";
		}
		
		reportParameters.put("15", societaAddebitoDesLet);

		if(tipologia.equals("TFSC_1") && !settore.equals("TSTT_2")){
			
			reportParameters.put("16", "L'adempimento dell'incarico vale quale integrale accettazione dei contenuti della presente, "
					+ "anche per conto dell'eventuale domiciliatario, "
					+ "cos�; come il Vostro impegno a rispettare rigorosamente il Codice Deontologico forense, "
					+ "cos� come le regole etiche da applicarsi nelle attivit� professionali contenute "
					+ "in ogni altro eventuale codice professionale o raccolta di regole applicabili alla Sua professione");
		}
		else if(tipologia.equals("TFSC_2") || settore.equals("TSTT_2")){
			
			reportParameters.put("16", "L'accettazione del presente incarico vale quale integrale accettazione dei contenuti in esso indicati, "
					+ "cos� come il Suo impegno a rispettare rigorosamente il Codice Deontologico forense, "
					+ "cos� come le regole etiche da applicarsi nelle attivit� professionali contenute in "
					+ "ogni altro eventuale codice professionale o raccolta di regole applicabili alla Sua professione");
		}
		else if(tipologia.equals("TFSC_4")){
			
			reportParameters.put("16", "l'adempimento dell'incarico vale quale integrale accettazione dei contenuti della presente, "
					+ "cos� come le regole etiche da applicarsi nelle attivit� professionali contenute "
					+ "in ogni altro eventuale codice professionale o raccolta di regole applicabili alla Sua professione");
		}else{
			
			reportParameters.put("16", "");
		}

		if(view.getListaSocietaAddebitoAggiunteDescLet().size()>1){
			
			reportParameters.put("17", "Snam S.p.A.");
			reportParameters.put("18", view.getSnamretegasWebsite());
		}
		else{
			
			reportParameters.put("17", view.getListaSocietaAddebitoAggiunteDescLet().get(0).getRagioneSociale());
			reportParameters.put("18", view.getListaSocietaAddebitoAggiunteDescLet().get(0).getSitoWeb());
		}
		
		reportParameters.put("19", view.getSnamretegasWebsite());
		reportParameters.put("20", view.getEmailSegnalazioni());
		

		if(tipologia.equals("TFSC_1") && !settore.equals("TSTT_2")){
			String outUno = "Qualora si rendesse necessaria la nomina di un domiciliatario, la sua individuazione dovr� "
					+ "rispondere ai principi del Codice Etico di ";
			if(view.getListaSocietaAddebitoAggiunteDescLet().size()>1){
				outUno+="Snam S.p.A.";
			}
			else{
				outUno+=view.getListaSocietaAddebitoAggiunteDescLet().get(0).getRagioneSociale();
			}
			outUno+=", ai principi deontologici nonch� ai principi contenuti nella clausola 7) che precede";
			
			reportParameters.put("21", outUno);
		}
		else{
			reportParameters.put("21", "");
		}
		
		reportParameters.put("22", view.getQualifica());
		reportParameters.put("23", studio==null?"":studio.getDenominazione());

		return reportParameters;
	}




	/*private String getLifecycleFileNotaProposta(IncaricoView view) {

		String out = "";

		Element root = new Element("NotaPropostaIncaricoPDF");

		Document doc = new Document();

		String suffix = "id_";

		String div = "<div xmlns=\"http://www.w3.org/1999/xhtml\">";

		String chiudiDiv ="</div>";

		Element uno = new Element(suffix+"1");
		Element due = new Element(suffix+"2");
		Element tre = new Element(suffix+"3");
		Element treBis = new Element(suffix+"3bis");
		Element quattro = new Element(suffix+"4");
		Element cinque = new Element(suffix+"5");
		Element sei = new Element(suffix+"6");
		Element sette = new Element(suffix+"7");
		Element setteBis = new Element(suffix+"7bis");
		Element otto = new Element(suffix+"8");
		Element ottoBis = new Element(suffix+"8bis");
		Element ottoTer = new Element(suffix+"8ter");
		Element ottoQuater = new Element(suffix+"8quater");
		Element nove = new Element(suffix+"9");
		Element dieci = new Element(suffix+"10");
		Element undici = new Element(suffix+"11");
		Element dodici = new Element(suffix+"12");
		Element tredici = new Element(suffix+"13");

		String tipologia = view.getFascicoloRiferimento().getVo().getTipologiaFascicolo().getCodGruppoLingua();
		//String settore = view.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua();

		uno.addContent(view.getResponsabileTop());

		due.addContent(view.getNomeFascicolo() + " - " + view.getPratica()+ " - " + view.getSocietaParteProcedimento());


		if(view.getValoreIncarico()!=null && view.getValoreIncarico()!=""){
			tre.addContent(view.getValoreIncarico().split("\\+", 2)[0]);
			try{
				treBis.addContent(" + "+view.getValoreIncarico().split("\\+", 2)[1]);
			}
			catch (ArrayIndexOutOfBoundsException e) {
			}
		}
		else{
			tre.addContent("");
			treBis.addContent("");
		}

		quattro.addContent(view.getOggetto());

		cinque.addContent(view.getDescrizione());

		if(tipologia.equals("TFSC_4")){
			sei.addContent("Avv.");
		}
		else{
			sei.addContent("Dott./Dott.ssa");
		}

		ProfessionistaEsterno prof = view.getProfessionistaSelezionato().getVo();
		StudioLegale studio = prof.getStudioLegale();

		String setteAdd = prof.getNome()==null?"":prof.getNome()+
				" "+prof.getCognome()==null?"":prof.getCognome()+"<br/>";

		if(studio!=null)
			setteAdd +=studio.getDenominazione()==null?"":studio.getDenominazione()+" "
					+studio.getIndirizzo()==null?"":studio.getIndirizzo()+" "
							+studio.getCap()==null?"":studio.getCap()+" "
									+studio.getCitta()==null?"":studio.getCitta();

		sette.addContent(div+setteAdd+chiudiDiv);

		setteBis.addContent(view.getNomeForo());

		otto.addContent(view.getProposta());

		ottoBis.addContent(StringEscapeUtils.unescapeHtml4(div+view.getInfoCompensoNotaProp()+chiudiDiv));

		ottoTer.addContent(view.getInfoCorresponsioneCompenso());


		if(view.getSceltaInfo()==0){
			ottoQuater.addContent("Si precisa che, con riferimento agli allegati all'handbook, non sono state apportate modifiche");
		}else
			ottoQuater.addContent("Si precisa che, con riferimento agli allegati all'handbook, sono state apportate modifiche che sono:"+view.getInfoHandBook());

		nove.addContent(view.getAllegato());

		dieci.addContent(view.getDataNotaProposta());

		undici.addContent(view.getProponente());

		dodici.addContent(view.getResponsabili()!=null?view.getResponsabili():"");

		tredici.addContent(view.getApprovatore()!=null?view.getApprovatore():"");

		root.addContent(uno);
		root.addContent(due);
		root.addContent(tre);
		root.addContent(treBis);
		root.addContent(quattro);
		root.addContent(cinque);
		root.addContent(sei);
		root.addContent(sette);
		root.addContent(setteBis);
		root.addContent(otto);
		root.addContent(ottoBis);
		root.addContent(ottoTer);
		root.addContent(ottoQuater);
		root.addContent(nove);
		root.addContent(dieci);
		root.addContent(undici);
		root.addContent(dodici);
		root.addContent(tredici);

		doc.setRootElement(root);

		//XMLOutputter outter = new XMLOutputter();

		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat()) {
			@Override
			public String escapeElementEntities(String str) {
				return str;
			}
		};

		//outter.setFormat(Format.getPrettyFormat());

		out = outputter.outputString(doc);

		//out = XML.toJSONObject(outter.outputString(doc)).toString();

		return out;
	}*/
	
	private Map<String,Object> getNoteParametersForPdf(IncaricoView view, Locale locale, URL resourceStylePath) {
		
		Map<String,Object> reportParameters = new HashMap<>();
        reportParameters.put("SUBREPORT_DIR", "/jasper/"); 
	    reportParameters.put("reportTitle", NOTA_INCARICO_JASPER_REPORT_TITLE);
	    reportParameters.put(PARAMETER_SUBREPORTDIR,   "/jasper/"); //assolutamente necesario altrimenti non funzionano i sottoreport
		reportParameters.put(PARAMETER_STYLEDIR,       "/jasper/"); //assolutamente necesario altrimenti non funzionano gli stili
	    reportParameters.put(PARAMETER_IMAGERDIR,      "/jasper/"); //assolutamente necesario altrimenti non funzionano gli stili
	    reportParameters.put(JRParameter.REPORT_LOCALE,   locale);     //assolutamente necesario altrimenti non funzionano le formattazioni internazionali
	    reportParameters.put(PARAMETER_LANGUAGE,       locale.getLanguage());
	    reportParameters.put(PARAMETER_COUNTRY,        locale.getLanguage().toUpperCase());
	    
	    String div="<div>";
	    String chiudiDiv="</div>";

		String tipologia = view.getFascicoloRiferimento().getVo().getTipologiaFascicolo().getCodGruppoLingua();
		
		reportParameters.put("1",view.getResponsabileTop());
		reportParameters.put("2",view.getNomeFascicolo() + " - " + view.getPratica()+ " - " + view.getSocietaParteProcedimento());

		if(view.getValoreIncarico()!=null && view.getValoreIncarico()!=""){
			
			reportParameters.put("3",view.getValoreIncarico().split("\\+", 2)[0]);
			try{
				reportParameters.put("3bis"," + "+view.getValoreIncarico().split("\\+", 2)[1]);
			}
			catch (ArrayIndexOutOfBoundsException e) {
				reportParameters.put("3bis","");
			}
		}
		else{
			reportParameters.put("3","");
			reportParameters.put("3bis","");
		}
		
		reportParameters.put("4",view.getOggetto());
		reportParameters.put("5",view.getDescrizione());
		

		if(tipologia.equals("TFSC_4")){
			reportParameters.put("6","Dott./Dott.ssa");
		}
		else{
			reportParameters.put("6","Avv.");
		}

		ProfessionistaEsterno prof = view.getProfessionistaSelezionato().getVo();
		StudioLegale studio = prof.getStudioLegale();
		
		String setteAdd = "";
		setteAdd += prof.getCognomeNome() + "<br/>";

		if(studio!=null){
			
			setteAdd +=studio.getDenominazione()==null?"":studio.getDenominazione()+"<br/>";
			setteAdd +=studio.getIndirizzo()==null?"":studio.getIndirizzo()+"<br/>";
			setteAdd +=studio.getCap()==null?"":studio.getCap()+" ";
			setteAdd +=studio.getCitta()==null?"":studio.getCitta();
		}
		
		reportParameters.put("7",setteAdd);
		reportParameters.put("7bis",view.getNomeForo());
		reportParameters.put("8",view.getProposta());
		reportParameters.put("8bis",StringEscapeUtils.unescapeHtml4(div+view.getInfoCompensoNotaProp()+chiudiDiv));
		reportParameters.put("8ter",view.getInfoCorresponsioneCompenso());

		if(view.getSceltaInfo()==0){
			reportParameters.put("8quater","Si precisa che, con riferimento agli allegati all'handbook, non sono state apportate modifiche");
		}else
			reportParameters.put("8quater","Si precisa che, con riferimento agli allegati all'handbook, sono state apportate modifiche che sono:"+view.getInfoHandBook());
		
		reportParameters.put("9",view.getAllegato());
		reportParameters.put("10",view.getDataNotaProposta());
		reportParameters.put("11",view.getProponente());
		reportParameters.put("12",view.getResponsabili()!=null?view.getResponsabili():"");
		reportParameters.put("13",view.getApprovatore()!=null?view.getApprovatore():"");

		return reportParameters;
	}



	@RequestMapping("/incarico/crea")
	public String creaIncarico(HttpServletRequest request, Model model, Locale locale) {
		IncaricoView view = new IncaricoView();
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {
			if( request.getParameter("fascicoloId") != null ){
				long fasicoloId = NumberUtils.toLong(request.getParameter("fascicoloId"));
				FascicoloView fascicoloView = fascicoloService.leggi(fasicoloId,FetchMode.JOIN);
				view.setFascicoloRiferimento(fascicoloView);
				view.setNomeFascicolo(fascicoloView.getVo().getNome());
				super.caricaListe(view, locale);

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

			} 
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		} 

		view.setVo(new Incarico()); 

		model.addAttribute(MODEL_VIEW_NOME, view);
		return  model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_FORM_PATH;
	}


	@RequestMapping(value = "/incarico/uploadProcura", method = RequestMethod.POST)
	public String uploadProcura( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		try {   
			String incaricoId = request.getParameter("idIncarico"); 
			if( incaricoId == null  ){
				throw new RuntimeException("incaricoId non possono essere null");
			}

			File fileTmp = File.createTempFile("procura", "___" + file.getOriginalFilename() );
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			incaricoView.setProcuraDoc(documentoView); 

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "incarico/procura";
	}

	@RequestMapping(value = "/incarico/uploadletteraincaricofirmata", method = RequestMethod.POST)
	public String uploadLetteraFirmata( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);  
		try {   
			String incaricoId = request.getParameter("idIncarico"); 
			if( incaricoId == null  ){
				throw new RuntimeException("incaricoId non possono essere null");
			}

			File fileTmp = File.createTempFile("letteraFirmata", "___" + file.getOriginalFilename() );
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			incaricoView.setLetteraFirmataDoc(documentoView); 

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "incarico/letteraIncaricoFirmata";
	}

	@RequestMapping(value = "/incarico/uploadnotapropostafirmata", method = RequestMethod.POST)
	public String uploadnotapropostafirmata( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		try {   
			String incaricoId = request.getParameter("idIncarico"); 
			if( incaricoId == null  ){
				throw new RuntimeException("incaricoId non possono essere null");
			}

			File fileTmp = File.createTempFile("notaPropostaFirmata", "___" + file.getOriginalFilename() );
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			incaricoView.setLetteraFirmataDocNota(documentoView); 

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "incarico/notaPropostaFirmata";
	}

	@RequestMapping(value = "/incarico/uploadVerificaAnticorruzione", method = RequestMethod.POST)
	public String uploadVerificaAnticorruzione( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		try {   
			String incaricoId = request.getParameter("idIncarico"); 
			if( incaricoId == null  ){
				throw new RuntimeException("incaricoId non possono essere null");
			}
			File fileTmp = File.createTempFile("verificaAnticorruzione", "___" + file.getOriginalFilename() );
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			incaricoView.setVerificaAnticorruzioneDoc(documentoView); 

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "incarico/verificaAnticorruzione";
	}


	@RequestMapping(value = "/incarico/uploadVerificaPartiCorrelate", method = RequestMethod.POST)
	public String uploadVerificaPartiCorrelate( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {   
			String incaricoId = request.getParameter("idIncarico"); 
			if( incaricoId == null  ){
				throw new RuntimeException("incaricoId non possono essere null");
			}

			File fileTmp = File.createTempFile("verificaPartiCorrelate", "___" + file.getOriginalFilename() );
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			incaricoView.setVerificaPartiCorrelateDoc(documentoView); 

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "incarico/verificaPartiCorrelate";
	}  

	@RequestMapping(value = "/incarico/uploadListeRiferimento", method = RequestMethod.POST)
	public String uploadListeRiferimento( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {   
			String incaricoId = request.getParameter("idIncarico"); 
			if( incaricoId == null  ){
				throw new RuntimeException("incaricoId non possono essere null");
			}

			File fileTmp = File.createTempFile("listeRiferimento", "___" + file.getOriginalFilename() );
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			incaricoView.setListeRiferimentoDoc(documentoView); 
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "incarico/listeRiferimento";
	}

	@RequestMapping(value = "/incarico/uploadAllegatoGenerico", method = RequestMethod.POST)
	public String uploadAllegatoGenerico( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		// engsecurity VA
		//		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//		htmlActionSupport.checkCSRFToken(request);
		logger.debug("@@DDS Upload allegato generico + file " + file);
		try {   
			String incaricoId = request.getParameter("idIncarico"); 
			if( incaricoId == null  ){
				throw new RuntimeException("incaricoId non possono essere null");
			}

			File fileTmp = File.createTempFile("allegatoGenerico", "___" + file.getOriginalFilename() );
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();

			documentoView.setUuid("" +( incaricoView.getListaAllegatiGenerici() == null || incaricoView.getListaAllegatiGenerici().size() == 0 ?  1 : incaricoView.getListaAllegatiGenerici().size()+1));
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			List<DocumentoView> allegatiGenerici = incaricoView.getListaAllegatiGenerici()==null?new ArrayList<DocumentoView>():incaricoView.getListaAllegatiGenerici();
			allegatiGenerici.add(documentoView);
			incaricoView.setListaAllegatiGenerici(allegatiGenerici);
			logger.debug("@@DDS Upload allegato generico + allegatiGenerici " + allegatiGenerici.size());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "incarico/allegatiGenerici";
	}


	@RequestMapping(value = "/incarico/rimuoviAllegatoGenerico", method = RequestMethod.POST)
	public String rimuoviAllegatoGenerico( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {   
			String uuid = request.getParameter("uuid"); 
			if( uuid == null  ){
				throw new RuntimeException("uuid non pu� essere null");
			}

			List<DocumentoView> allegatiGenericiOld = incaricoView.getListaAllegatiGenerici();
			List<DocumentoView> allegatiGenerici = new ArrayList<DocumentoView>();
			allegatiGenerici.addAll(allegatiGenericiOld);
			Set<String> allegatiDaRimuovere = incaricoView.getAllegatiDaRimuovereUuid() == null ? new HashSet<String>():incaricoView.getAllegatiDaRimuovereUuid();
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
			incaricoView.setListaAllegatiGenerici(allegatiGenerici);
			incaricoView.setAllegatiDaRimuovereUuid(allegatiDaRimuovere);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "incarico/allegatiGenerici";
	}

	@RequestMapping(value = "/incarico/rimuoviListeRiferimento", method = RequestMethod.POST)
	public String rimuoviListeRiferimento( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
//		try {   
//			String incaricoId = request.getParameter("idIncarico"); 
//			if( incaricoId == null  ){
//				throw new RuntimeException("incaricoId non possono essere null");
//			}

			incaricoView.setListeRiferimentoDoc(null); 
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
		return "incarico/listeRiferimento";
	}



	@RequestMapping(value = "/incarico/rimuoviProcura", method = RequestMethod.POST)
	public String rimuoviProcura( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
//		try {   
//			String incaricoId = request.getParameter("idIncarico"); 
//			if( incaricoId == null  ){
//				throw new RuntimeException("incaricoId non possono essere null");
//			}

			incaricoView.setProcuraDoc(null); 

//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
		return "incarico/procura";
	}

	@RequestMapping(value = "/incarico/rimuoviLetteraFirmata", method = RequestMethod.POST)
	public String rimuoviLetteraFirmata( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		try {   
			NumberUtils.toLong( request.getParameter("idIncarico")); 

			incaricoView.setLetteraFirmataDoc(null); 

			//request.getSession().removeAttribute("schedaValutazioneProforma");

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "incarico/letteraIncaricoFirmata";
	}

	@RequestMapping(value = "/incarico/rimuoviNotaPropostaFirmata", method = RequestMethod.POST)
	public String rimuoviNotaPropostaFirmata( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		try {   
			NumberUtils.toLong( request.getParameter("idIncarico")); 

			incaricoView.setLetteraFirmataDocNota(null); 

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "incarico/notaPropostaFirmata";
	}


	@RequestMapping(value = "/incarico/rimuoviVerificaAnticorruzione", method = RequestMethod.POST)
	public String rimuoviVerificaAnticorruzione( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
//		try {   
//			String incaricoId = request.getParameter("idIncarico"); 
//			if( incaricoId == null  ){
//				throw new RuntimeException("incaricoId non possono essere null");
//			}
			incaricoView.setVerificaAnticorruzioneDoc(null); 

//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
		return "incarico/verificaAnticorruzione";
	}


	@RequestMapping(value = "/incarico/rimuoviVerificaPartiCorrelate", method = RequestMethod.POST)
	public String rimuoviVerificaPartiCorrelate( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) IncaricoView incaricoView ) {  
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
//		try {   
//			String incaricoId = request.getParameter("idIncarico"); 
//			if( incaricoId == null  ){
//				throw new RuntimeException("incaricoId non possono essere null");
//			}
			incaricoView.setVerificaPartiCorrelateDoc(null); 

//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
		return "incarico/verificaPartiCorrelate";
	} 


	@RequestMapping(value = "/incarico/downloadLetteraIncarico" , method = RequestMethod.GET)
	public String downloadDoc(@RequestParam("id") String id , HttpServletRequest request,HttpServletResponse response, Model model, Locale locale) throws Throwable {

		// engsecurity VA
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		if(id!=null && !id.equalsIgnoreCase("")){
			LetteraIncarico lettera = incaricoService.leggiLetteraIncarico(Long.parseLong(id));

			lettera.getReturnFile();

			String contentType = "application/pdf";
			String name = lettera.getReturnFileName();

			OutputStream os = null;


			try{

				response.setContentLength((int) lettera.getReturnFile().length());
				response.setContentType(contentType);
				response.setHeader("Content-Disposition", "attachment;filename=" + name);
				response.setHeader("Cache-control", "");

				os = response.getOutputStream();
				IOUtils.copy(lettera.getReturnFile().getBinaryStream(), os);

			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(os);
			}
		}
		return null;
	}

	@RequestMapping(value = "/incarico/downloadNotaProposta" , method = RequestMethod.GET)
	public String downloadDocNot(@RequestParam("id") String id , HttpServletRequest request,HttpServletResponse response, Model model, Locale locale) throws Throwable {

		// engsecurity VA
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		if(id!=null && !id.equalsIgnoreCase("")){
			NotaPropIncarico not = incaricoService.leggiNotaPropostaIncarico(Long.parseLong(id));

			not.getReturnFile();

			String contentType = "application/pdf";
			String name = not.getReturnFileName();

			OutputStream os = null;


			try{

				response.setContentLength((int) not.getReturnFile().length());
				response.setContentType(contentType);
				response.setHeader("Content-Disposition", "attachment;filename=" + name);
				response.setHeader("Cache-control", "");

				os = response.getOutputStream();
				IOUtils.copy(not.getReturnFile().getBinaryStream(), os);

			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(os);
			}
		}
		return null;
	}


	@RequestMapping(value = "/incarico/checkfile", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody RisultatoOperazioneRest checkFile(@RequestParam("id") long id, Model model) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {

			logger.debug("Chiamata polling checkFile per Lettera D'Incarico: "+id);

			Integer checkStatus = incaricoService.checkStatusInviata(id);

			if(checkStatus==-1){
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.WARN, "messaggio.warn");
				return risultato;
			}
			else{	
				Integer check  = incaricoService.checkFile(id);

				if(check!=0)
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@RequestMapping(value = "/incarico/checkfileNotaProposta", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody RisultatoOperazioneRest checkfileNotaProposta(@RequestParam("id") long id, Model model) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {

			logger.debug("Chiamata polling checkFile per Nota Proposta Incarico: "+id);

			Integer checkStatus = incaricoService.checkStatusInviataNot(id);

			if(checkStatus==-1){
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.WARN, "messaggio.warn");
				return risultato;
			}
			else{	
				Integer check  = incaricoService.checkFileNot(id);

				if(check!=0)
					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}


	@RequestMapping(value = "/incarico/cercaProfessionistaEsterno", method = RequestMethod.POST)
	public @ResponseBody String cercaProfessionistaEsterno(HttpServletRequest request, Locale locale) {


		try {
			String nazione=request.getParameter("nazione");
			String specializzazione =request.getParameter("spec");

			List<ProfessionistaEsternoView> profEsterni = professionistaEsternoService.leggiNazioneSpecializzazione(false);

			if(profEsterni != null){

				JSONArray jsonArray = new JSONArray();

				for(ProfessionistaEsternoView pe : profEsterni){
					boolean okNazione = false;
					boolean okSpecializzazione = false;

					if(pe.getVo() != null){

						if(pe.getVo().getRProfessionistaNaziones() != null){

							for(RProfessionistaNazione n : pe.getVo().getRProfessionistaNaziones()){

								if(n.getNazione() != null){

									if(n.getNazione().getCodGruppoLingua().equals(nazione)){

										okNazione = true;
										break;
									}
								}
							}
						}
						if(pe.getVo().getRProfEstSpecs() != null){

							for(RProfEstSpec s : pe.getVo().getRProfEstSpecs()){

								if(s.getSpecializzazione() != null){

									if(s.getSpecializzazione().getCodGruppoLingua().equals(specializzazione)){

										okSpecializzazione = true;
										break;
									}
								}
							}
						}
					}

					if(okNazione && okSpecializzazione){

						JSONObject jsonObject = new JSONObject();
						jsonObject.put("id", pe.getVo().getId());
						if(pe.getVo().getStudioLegale() != null){
							jsonObject.put("denominazione", pe.getVo().getStudioLegale().getDenominazione()!= null ? pe.getVo().getStudioLegale().getDenominazione() : "");
						}else{
							jsonObject.put("denominazione", "");
						}
						jsonObject.put("cognomeNome", pe.getVo().getCognomeNome()!=null ? pe.getVo().getCognomeNome() : "");
						jsonArray.put(jsonObject);
					}

				}
				return jsonArray.toString();
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		List<ProcuratoreView> listaProcuratori = procuratoreService.leggi(false);
		List<ProfessionistaEsternoView> listaProfessionistaEsternoViews = null;
		IncaricoView incaricoView = (IncaricoView) view;
		incaricoView.setListaProcuratore(listaProcuratori);
		incaricoView.setListaTipoValuta(anagraficaStatiTipiService.leggiTipoValuta(false));
		if(incaricoView.getFascicoloRiferimento().getVo().getTipologiaFascicolo().getCodGruppoLingua().equals(Costanti.TIPOLOGIA_FASCICOLO_NOTARILE_COD ))
			listaProfessionistaEsternoViews = professionistaEsternoService.leggiProfessionistiPerCategoria(Costanti.TIPOLOGIA_PROFESSIONISTA_NOTAIO_COD, false);
		else
			listaProfessionistaEsternoViews = professionistaEsternoService.leggiProfessionistiPerCategoria(Costanti.TIPOLOGIA_PROFESSIONISTA_LEGALE_ESTERNO_COD, false );
		incaricoView.setListaProfessionista(listaProfessionistaEsternoViews);
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		List<ProcuratoreView> listaProcuratori = procuratoreService.leggi(true);
		IncaricoView incaricoView = (IncaricoView) view;
		incaricoView.setListaProcuratore(listaProcuratori);
	}

	public void caricaListeProcure(BaseView view, Incarico vo) throws Throwable {
		IncaricoView incaricoView = (IncaricoView) view;
		long idFascicolo = vo.getFascicolo().getId();
		List<ProcureView> listaProcure = procureService.leggiDaFascicolo(idFascicolo);
		incaricoView.setListaProcure(listaProcure);
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new IncaricoValidator());

		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(value = "/incarico/checkFilenetProduction", method = RequestMethod.GET)
	public String cercaIncarichi(HttpServletRequest request, Model model, Locale locale) {

		try {
			IncaricoView view = new IncaricoView();
			model.addAttribute(MODEL_VIEW_NOME, view);
			return "incarico/checkProductionFilenet";
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/incarico/exportFileFromFilenet")
	public @ResponseBody RisultatoOperazioneRest exportFileFromFilenet( HttpServletRequest request, HttpServletResponse response) {  
		logger.debug("@@DDS --------------------- exportFileFromFilenet");
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {   

			InputStream input = this.getClass().getClassLoader().getResourceAsStream("/esportazioni/esportazione.txt");

			BufferedReader br = new BufferedReader(new InputStreamReader(input));

			String line = br.readLine();

			Map<String, FileNetExtractionRest> result = new HashMap<String, FileNetExtractionRest>();

			while(line != null){

				String incaricoInfo = line;

				if(incaricoInfo != null && !incaricoInfo.isEmpty()){

					logger.debug("riga letta (" + incaricoInfo + ")");

					String idIncarico="";
					String nomeIncarico ="";
					String dataCreazioneFascicolo ="";
					String dataAutorizzazione="";
					String nomeFascicolo ="";
					String owner="";
					String penale ="";
					List<String> files = null;

					StringTokenizer strt = new StringTokenizer(incaricoInfo, ",");

					int numToken = strt.countTokens();

					if(numToken == 7){

						idIncarico = strt.nextToken();
						nomeIncarico = strt.nextToken();
						dataCreazioneFascicolo = strt.nextToken();
						dataAutorizzazione = strt.nextToken();
						nomeFascicolo = strt.nextToken();
						penale = strt.nextToken();
						owner = strt.nextToken();

						Date dataCF = DateUtil.toDateFromDB(dataCreazioneFascicolo);
						Long idI = Long.parseLong(idIncarico);

						String nomeCartellaIncarico = FileNetUtil.getIncaricoCartella(idI, dataCF, nomeFascicolo, nomeIncarico);

						boolean isPenale = false;

						if(penale.equals("TSTT_2"))
							isPenale = true;
						/*@@DDS
						DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
						DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
						Folder cartellaIncarico = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaIncarico)
								: documentaleDAO.leggiCartella(nomeCartellaIncarico);
						*/
						DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
						DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
						Folder cartellaIncarico = isPenale ? documentaleDdsCryptDAO.leggiCartella(nomeCartellaIncarico)
								: documentaleDdsDAO.leggiCartella(nomeCartellaIncarico);
						logger.info ("@@DDS exportFileFromFilenet __________________________");
						if(cartellaIncarico != null){
							List<Document> documenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaIncarico)
									: documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaIncarico);
							if (documenti != null) {
								for (Document documento:documenti){
									if( documento.getDocumentalClass().equals(FileNetClassNames.ALLEGATO_DOCUMENT)){
										String nomeFile = documento.getContents().get(0).getContentsName();

										if(nomeFile.contains("liste")
												|| nomeFile.contains("Liste")
												|| nomeFile.contains("LISTE")
												|| nomeFile.contains("riferimento")
												|| nomeFile.contains("Riferimento")
												|| nomeFile.contains("RIFERIMENTO")
												|| nomeFile.contains("parti")
												|| nomeFile.contains("Parti")
												|| nomeFile.contains("PARTI")
												|| nomeFile.contains("correlate")
												|| nomeFile.contains("Correlate")
												|| nomeFile.contains("CORRELATE")
												|| nomeFile.contains("anticorruzione")
												|| nomeFile.contains("Anticorruzione")
												|| nomeFile.contains("ANTICORRUZIONE")
												|| nomeFile.contains("diligence")
												|| nomeFile.contains("Diligence")
												|| nomeFile.contains("DILIGENCE")
												|| nomeFile.contains("procura")
												|| nomeFile.contains("Procura")
												|| nomeFile.contains("PROCURA")
												|| nomeFile.contains("nota")
												|| nomeFile.contains("Nota")
												|| nomeFile.contains("NOTA")
												|| nomeFile.contains("proposta")
												|| nomeFile.contains("Proposta")
												|| nomeFile.contains("PROPOSTA")
												|| nomeFile.contains("dufac")
												|| nomeFile.contains("Dufac")
												|| nomeFile.contains("DUFAC")
												|| nomeFile.contains("leanc")
												|| nomeFile.contains("Leanc")
												|| nomeFile.contains("LEANC")
												|| nomeFile.endsWith(".zip")
												|| nomeFile.endsWith(".ZIP")){

											if(files != null){
												files.add(nomeFile);
											}
											else{
												files = new ArrayList<String>();
												files.add(nomeFile);
											}
										}
										else if(nomeFile.contains("verifica")
												|| nomeFile.contains("Verifica")
												|| nomeFile.contains("VERIFICA")){

											if(files != null){
												files.add(nomeFile);
											}
											else{
												files = new ArrayList<String>();
												files.add(nomeFile);
											}
										}
									}
								}
							}
							/*@@DDS inizio commento
							DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();

							if (documenti != null) {
								PageIterator it = documenti.pageIterator();
								while (it.nextPage()) {
									EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
									for (EngineObjectImpl objDocumento : documentiArray) {
										DocumentImpl documento = (DocumentImpl) objDocumento;
										if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.ALLEGATO_DOCUMENT)){
											String nomeFile = documento.get_Name();

											if(nomeFile.contains("liste")
													|| nomeFile.contains("Liste")
													|| nomeFile.contains("LISTE")
													|| nomeFile.contains("riferimento")
													|| nomeFile.contains("Riferimento")
													|| nomeFile.contains("RIFERIMENTO")
													|| nomeFile.contains("parti")
													|| nomeFile.contains("Parti")
													|| nomeFile.contains("PARTI")
													|| nomeFile.contains("correlate")
													|| nomeFile.contains("Correlate")
													|| nomeFile.contains("CORRELATE")
													|| nomeFile.contains("anticorruzione")
													|| nomeFile.contains("Anticorruzione")
													|| nomeFile.contains("ANTICORRUZIONE")
													|| nomeFile.contains("diligence")
													|| nomeFile.contains("Diligence")
													|| nomeFile.contains("DILIGENCE")
													|| nomeFile.contains("procura")
													|| nomeFile.contains("Procura")
													|| nomeFile.contains("PROCURA")
													|| nomeFile.contains("nota")
													|| nomeFile.contains("Nota")
													|| nomeFile.contains("NOTA")
													|| nomeFile.contains("proposta")
													|| nomeFile.contains("Proposta")
													|| nomeFile.contains("PROPOSTA")
													|| nomeFile.contains("dufac")
													|| nomeFile.contains("Dufac")
													|| nomeFile.contains("DUFAC")
													|| nomeFile.contains("leanc")
													|| nomeFile.contains("Leanc")
													|| nomeFile.contains("LEANC")
													|| nomeFile.endsWith(".zip")
													|| nomeFile.endsWith(".ZIP")){

												if(files != null){
													files.add(nomeFile);
												}
												else{
													files = new ArrayList<String>();
													files.add(nomeFile);
												}
											}
											else if(nomeFile.contains("verifica")
													|| nomeFile.contains("Verifica")
													|| nomeFile.contains("VERIFICA")){

												if(files != null){
													files.add(nomeFile);
												}
												else{
													files = new ArrayList<String>();
													files.add(nomeFile);
												}
											}
										}
									}
								}
							}
							*/
						}

						FileNetExtractionRest info = new FileNetExtractionRest();
						info.setNomeIncarico(nomeIncarico);
						info.setNomeFascicolo(nomeFascicolo);
						info.setOwner(owner);
						info.setDataAutorizzazione(dataAutorizzazione);
						info.setFiles(files);

						int count = 0;
						if(files != null && !files.isEmpty()){
							count = files.size();
						}
						info.setConteggio(Integer.toString(count));

						result.put(idIncarico, info);
					}
				}

				line = br.readLine();
			}
			exportMapToExel(result, response);

		} catch (Throwable e) {
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "exportFileFromFilenet.errore.generico");
			e.printStackTrace();
		}
		return risultato;
	}

	private void exportMapToExel(Map<String, FileNetExtractionRest> result, HttpServletResponse respons) throws IOException {

		WriteExcell excell= new WriteExcell();

		String[] lang=new String[16];

		lang[0]="ID_INCARICO";
		lang[1]="NOME_INCARICO";
		lang[2]="FASCICOLO";
		lang[3]="OWNER";
		lang[4]="DATA_AUTORIZZAZIONE";
		lang[5]="N_DOCUMENTI_DA_SPOSTARE"; 
		lang[6]="NOME_DOCUMENTO_1";
		lang[7]="NOME_DOCUMENTO_2";
		lang[8]="NOME_DOCUMENTO_3";
		lang[9]="NOME_DOCUMENTO_4";
		lang[10]="NOME_DOCUMENTO_5";
		lang[11]="NOME_DOCUMENTO_6";
		lang[12]="NOME_DOCUMENTO_7";
		lang[13]="NOME_DOCUMENTO_8";
		lang[14]="NOME_DOCUMENTO_9";
		lang[15]="NOME_DOCUMENTO_10";

		excell.addHeader(lang[0], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[1], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[2], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[3], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[4], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[5], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[6], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[7], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[8], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[9], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[10], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[11], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[12], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[13], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[14], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[15], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);

		if(result!=null  && !result.isEmpty()){

			for(Entry<String, FileNetExtractionRest> entry : result.entrySet()){

				Vector<String> row = new Vector<String>();

				row.add(entry.getKey());

				FileNetExtractionRest info = entry.getValue();

				row.add(info.getNomeIncarico());
				row.add(info.getNomeFascicolo());
				row.add(info.getOwner());
				row.add(info.getDataAutorizzazione());
				row.add(info.getConteggio());

				List<String> documenti = info.getFiles();
				if(documenti!= null && !documenti.isEmpty()){

					for(String nomeDoc : documenti){
						row.add(nomeDoc);
					}
				}
				excell.addRowBody(row);
			}
			excell.setNomeFile("Report.xls");
			excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 45);
			excell.write(respons);
		}
	}

	@RequestMapping(value = "/incarico/changeClassFileOnFilenet", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest changeClassFileOnFilenet( HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file) {  

		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {   

			InputStream input = file.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(input));

			String line = br.readLine();

			while(line != null){

				String incaricoInfo = line;

				if(incaricoInfo != null && !incaricoInfo.isEmpty()){

					String idIncarico="";
					String nomeIncarico ="";
					String dataCreazioneFascicolo ="";
					String dataAutorizzazione="";
					String nomeFascicolo ="";
					String owner="";
					String penale ="";
					StringTokenizer strt = new StringTokenizer(incaricoInfo, ",");

					int numToken = strt.countTokens();

					if(numToken == 7){

						idIncarico = strt.nextToken();
						nomeIncarico = strt.nextToken();
						dataCreazioneFascicolo = strt.nextToken();
						dataAutorizzazione = strt.nextToken();
						nomeFascicolo = strt.nextToken();
						penale = strt.nextToken();
						owner = strt.nextToken();

						logger.debug("riga letta (" + idIncarico + " - " + nomeIncarico + " - " + dataCreazioneFascicolo +
								" - " + dataAutorizzazione + " - " + nomeFascicolo + " - " + penale + " - " + owner + ")");

						Date dataCF = DateUtil.toDateFromDB(dataCreazioneFascicolo);
						Long idI = Long.parseLong(idIncarico);

						String nomeCartellaIncarico = FileNetUtil.getIncaricoCartella(idI, dataCF, nomeFascicolo, nomeIncarico);

						boolean isPenale = false;

						if(penale.equals("TSTT_2"))
							isPenale = true;

						/*@@DDS
						DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
						DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
						Folder cartellaIncarico = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaIncarico)
								: documentaleDAO.leggiCartella(nomeCartellaIncarico);
						*/
						DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
						DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
						Folder cartellaIncarico = isPenale ? documentaleDdsCryptDAO.leggiCartella(nomeCartellaIncarico)
								: documentaleDdsDAO.leggiCartella(nomeCartellaIncarico);
						if(cartellaIncarico != null){
							logger.info ("@@DDS changeClassFileOnFilenet __________________________");
							/*@@DDS inizio commento
							DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();

							if (documenti != null) {

								IncaricoView incaricoView = incaricoService.leggi(idI);
								int countListaRiferimento = 0;
								int countVerifPartiCorrelate = 0;
								int countVerifAnticorruzione = 0;
								int countProcura = 0;
								int countNotaPropostaInc = 0;

								if(incaricoView != null){

									if(incaricoView.getVo() != null){

										PageIterator it = documenti.pageIterator();
										while (it.nextPage()) {
											EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();

											for (EngineObjectImpl objDocumento : documentiArray) {

												DocumentImpl documento = (DocumentImpl) objDocumento;
												if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.ALLEGATO_DOCUMENT)){

													String uuid = documento.get_Id().toString();
													uuid = uuid.replace("{", "");
													uuid = uuid.replace("}", "");
													uuid = uuid.toLowerCase();

													String nomeFile = documento.get_Name();

													if(nomeFile.contains("liste") || nomeFile.contains("Liste") || nomeFile.contains("LISTE")
															|| nomeFile.contains("riferimento") || nomeFile.contains("Riferimento") || nomeFile.contains("RIFERIMENTO")){

														if(countListaRiferimento == 0){

															documento.changeClass(FileNetClassNames.LISTA_RIFERIMENTO_DOCUMENT);
															documento.save(RefreshMode.REFRESH);

															DocumentoView documentoDB = documentoService.creaDocumentoDB(uuid, FileNetClassNames.LISTA_RIFERIMENTO_DOCUMENT, documento.get_Name(), documento.get_MimeType());

															if(documentoDB != null){

																if(documentoDB.getVo() != null){

																	incaricoView = incaricoService.aggiornaIncaricoConListaRiferimento(incaricoView, documentoDB.getVo());
																}
															}
															countListaRiferimento ++;
														}
													}
													else if(nomeFile.contains("parti") || nomeFile.contains("Parti") || nomeFile.contains("PARTI")
															|| nomeFile.contains("correlate") || nomeFile.contains("Correlate") || nomeFile.contains("CORRELATE")){

														if(countVerifPartiCorrelate == 0){

															documento.changeClass(FileNetClassNames.VERIFICA_PARTI_CORRELATE_DOCUMENT);
															documento.save(RefreshMode.REFRESH);

															DocumentoView documentoDB = documentoService.creaDocumentoDB(uuid, FileNetClassNames.VERIFICA_PARTI_CORRELATE_DOCUMENT, documento.get_Name(), documento.get_MimeType());

															if(documentoDB != null){

																if(documentoDB.getVo() != null){

																	incaricoView = incaricoService.aggiornaIncaricoConPartiCorrelate(incaricoView, documentoDB.getVo());
																}
															}
															countVerifPartiCorrelate ++;
														}
													}
													else if(nomeFile.contains("anticorruzione") || nomeFile.contains("Anticorruzione") || nomeFile.contains("ANTICORRUZIONE")
															|| nomeFile.contains("diligence") || nomeFile.contains("Diligence") || nomeFile.contains("DILIGENCE")
															|| nomeFile.contains("dufac") || nomeFile.contains("Dufac") || nomeFile.contains("DUFAC")
															|| nomeFile.contains("leanc") || nomeFile.contains("Leanc") || nomeFile.contains("LEANC")){

														if(countVerifAnticorruzione == 0){

															documento.changeClass(FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT);
															documento.save(RefreshMode.REFRESH);

															DocumentoView documentoDB = documentoService.creaDocumentoDB(uuid, FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT, documento.get_Name(), documento.get_MimeType());

															if(documentoDB != null){

																if(documentoDB.getVo() != null){

																	incaricoView = incaricoService.aggiornaIncaricoConVerificaAnticorruzione(incaricoView, documentoDB.getVo());
																}
															}
															countVerifAnticorruzione ++;
														}
													}
													else if(nomeFile.contains("procura") || nomeFile.contains("Procura") || nomeFile.contains("PROCURA")){

														if(countProcura == 0){

															documento.changeClass(FileNetClassNames.PROCURA_DOCUMENT);
															documento.save(RefreshMode.REFRESH);

															DocumentoView documentoDB = documentoService.creaDocumentoDB(uuid, FileNetClassNames.PROCURA_DOCUMENT, documento.get_Name(), documento.get_MimeType());

															if(documentoDB != null){

																if(documentoDB.getVo() != null){

																	incaricoView = incaricoService.aggiornaIncaricoConProcura(incaricoView, documentoDB.getVo());
																}
															}
															countProcura ++;
														}
													}
													else if(nomeFile.contains("nota") || nomeFile.contains("Nota") || nomeFile.contains("NOTA")
															|| nomeFile.contains("proposta") || nomeFile.contains("Proposta") || nomeFile.contains("PROPOSTA")){

														if(countNotaPropostaInc == 0){

															documento.changeClass(FileNetClassNames.NOTA_PROPOSTA_INCARICO_DOCUMENT);
															documento.save(RefreshMode.REFRESH);

															countNotaPropostaInc++;
														}
													}
													else if((nomeFile.contains("verifica") || nomeFile.contains("Verifica") || nomeFile.contains("VERIFICA"))
															&&
															!(nomeFile.contains("anticorruzione") || nomeFile.contains("Anticorruzione") || nomeFile.contains("ANTICORRUZIONE")
																	|| nomeFile.contains("diligence") || nomeFile.contains("Diligence") || nomeFile.contains("DILIGENCE")
																	|| nomeFile.contains("dufac") || nomeFile.contains("Dufac") || nomeFile.contains("DUFAC")
																	|| nomeFile.contains("leanc") || nomeFile.contains("Leanc") || nomeFile.contains("LEANC")
																	|| nomeFile.contains("parti") || nomeFile.contains("Parti") || nomeFile.contains("PARTI")
																	|| nomeFile.contains("correlate") || nomeFile.contains("Correlate") || nomeFile.contains("CORRELATE"))){

														if(countVerifAnticorruzione == 0){

															documento.changeClass(FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT);
															documento.save(RefreshMode.REFRESH);

															DocumentoView documentoDB = documentoService.creaDocumentoDB(uuid, FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT, documento.get_Name(), documento.get_MimeType());

															if(documentoDB != null){

																if(documentoDB.getVo() != null){

																	incaricoView = incaricoService.aggiornaIncaricoConVerificaAnticorruzione(incaricoView, documentoDB.getVo());
																}
															}
															countVerifAnticorruzione ++;
														}
													}
												}
											}
										}
									}
								}
							}
							*/
							//@@DDS inizio sostituzione
							List<Document> documenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaIncarico)
									: documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaIncarico);

							if (documenti != null) {

								IncaricoView incaricoView = incaricoService.leggi(idI);
								int countListaRiferimento = 0;
								int countVerifPartiCorrelate = 0;
								int countVerifAnticorruzione = 0;
								int countProcura = 0;
								int countNotaPropostaInc = 0;

								if(incaricoView != null){

									if(incaricoView.getVo() != null){


										for (Document documento:documenti){

												if( documento.getDocumentalClass().equals(FileNetClassNames.ALLEGATO_DOCUMENT)){

													String uuid = documento.getId();
													uuid = uuid.replace("{", "");
													uuid = uuid.replace("}", "");

													String nomeFile = documento.getContents().get(0).getContentsName();

													if(nomeFile.contains("liste") || nomeFile.contains("Liste") || nomeFile.contains("LISTE")
															|| nomeFile.contains("riferimento") || nomeFile.contains("Riferimento") || nomeFile.contains("RIFERIMENTO")){

														if(countListaRiferimento == 0){
															//@@DDS TODO capire come sostituire il change class e il save
															//@@DDS TODO documento.changeClass(FileNetClassNames.LISTA_RIFERIMENTO_DOCUMENT);
															//@@DDS TODO documento.save(RefreshMode.REFRESH);

															DocumentoView documentoDB = documentoService.creaDocumentoDB(uuid, FileNetClassNames.LISTA_RIFERIMENTO_DOCUMENT,
																	documento.getContents().get(0).getContentsName(), documento.getContents().get(0).getContentsMimeType());

															if(documentoDB != null){

																if(documentoDB.getVo() != null){

																	incaricoView = incaricoService.aggiornaIncaricoConListaRiferimento(incaricoView, documentoDB.getVo());
																}
															}
															countListaRiferimento ++;
														}
													}
													else if(nomeFile.contains("parti") || nomeFile.contains("Parti") || nomeFile.contains("PARTI")
															|| nomeFile.contains("correlate") || nomeFile.contains("Correlate") || nomeFile.contains("CORRELATE")){

														if(countVerifPartiCorrelate == 0){
															//@@DDS TODO capire come sostituire il change class e il save
															//@@DDS TODO documento.changeClass(FileNetClassNames.VERIFICA_PARTI_CORRELATE_DOCUMENT);
															//@@DDS TODO documento.save(RefreshMode.REFRESH);

															DocumentoView documentoDB = documentoService.creaDocumentoDB(uuid, FileNetClassNames.VERIFICA_PARTI_CORRELATE_DOCUMENT,
																	documento.getContents().get(0).getContentsName(), documento.getContents().get(0).getContentsMimeType());

															if(documentoDB != null){

																if(documentoDB.getVo() != null){

																	incaricoView = incaricoService.aggiornaIncaricoConPartiCorrelate(incaricoView, documentoDB.getVo());
																}
															}
															countVerifPartiCorrelate ++;
														}
													}
													else if(nomeFile.contains("anticorruzione") || nomeFile.contains("Anticorruzione") || nomeFile.contains("ANTICORRUZIONE")
															|| nomeFile.contains("diligence") || nomeFile.contains("Diligence") || nomeFile.contains("DILIGENCE")
															|| nomeFile.contains("dufac") || nomeFile.contains("Dufac") || nomeFile.contains("DUFAC")
															|| nomeFile.contains("leanc") || nomeFile.contains("Leanc") || nomeFile.contains("LEANC")){

														if(countVerifAnticorruzione == 0){
															//@@DDS TODO capire come sostituire il change class e il save
															//@@DDS TODO documento.changeClass(FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT);
															//@@DDS TODO documento.save(RefreshMode.REFRESH);

															DocumentoView documentoDB = documentoService.creaDocumentoDB(uuid, FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT, documento.getContents().get(0).getContentsName(), documento.getContents().get(0).getContentsMimeType());

															if(documentoDB != null){

																if(documentoDB.getVo() != null){

																	incaricoView = incaricoService.aggiornaIncaricoConVerificaAnticorruzione(incaricoView, documentoDB.getVo());
																}
															}
															countVerifAnticorruzione ++;
														}
													}
													else if(nomeFile.contains("procura") || nomeFile.contains("Procura") || nomeFile.contains("PROCURA")){

														if(countProcura == 0){
															//@@DDS TODO capire come sostituire il change class e il save
															//@@DDS TODO documento.changeClass(FileNetClassNames.PROCURA_DOCUMENT);
															//@@DDS TODO documento.save(RefreshMode.REFRESH);

															DocumentoView documentoDB = documentoService.creaDocumentoDB(uuid, FileNetClassNames.PROCURA_DOCUMENT, documento.getContents().get(0).getContentsName(), documento.getContents().get(0).getContentsMimeType());

															if(documentoDB != null){

																if(documentoDB.getVo() != null){

																	incaricoView = incaricoService.aggiornaIncaricoConProcura(incaricoView, documentoDB.getVo());
																}
															}
															countProcura ++;
														}
													}
													else if(nomeFile.contains("nota") || nomeFile.contains("Nota") || nomeFile.contains("NOTA")
															|| nomeFile.contains("proposta") || nomeFile.contains("Proposta") || nomeFile.contains("PROPOSTA")){

														if(countNotaPropostaInc == 0){
															//@@DDS TODO capire come sostituire il change class e il save
															//@@DDS TODO documento.changeClass(FileNetClassNames.NOTA_PROPOSTA_INCARICO_DOCUMENT);
															//@@DDS TODO documento.save(RefreshMode.REFRESH);

															countNotaPropostaInc++;
														}
													}
													else if((nomeFile.contains("verifica") || nomeFile.contains("Verifica") || nomeFile.contains("VERIFICA"))
															&&
															!(nomeFile.contains("anticorruzione") || nomeFile.contains("Anticorruzione") || nomeFile.contains("ANTICORRUZIONE")
																	|| nomeFile.contains("diligence") || nomeFile.contains("Diligence") || nomeFile.contains("DILIGENCE")
																	|| nomeFile.contains("dufac") || nomeFile.contains("Dufac") || nomeFile.contains("DUFAC")
																	|| nomeFile.contains("leanc") || nomeFile.contains("Leanc") || nomeFile.contains("LEANC")
																	|| nomeFile.contains("parti") || nomeFile.contains("Parti") || nomeFile.contains("PARTI")
																	|| nomeFile.contains("correlate") || nomeFile.contains("Correlate") || nomeFile.contains("CORRELATE"))){

														if(countVerifAnticorruzione == 0){
															//@@DDS TODO capire come sostituire il change class e il save
															//@@DDS TODO documento.changeClass(FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT);
															//@@DDS TODO documento.save(RefreshMode.REFRESH);

															DocumentoView documentoDB = documentoService.creaDocumentoDB(uuid, FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT, documento.getContents().get(0).getContentsName(), documento.getContents().get(0).getContentsMimeType());

															if(documentoDB != null){

																if(documentoDB.getVo() != null){

																	incaricoView = incaricoService.aggiornaIncaricoConVerificaAnticorruzione(incaricoView, documentoDB.getVo());
																}
															}
															countVerifAnticorruzione ++;
														}
													}
												}

										}
									}
								}
							}

						}
					}
				}
				line = br.readLine();
			}
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "exportFileFromFilenet.errore.generico");
			e.printStackTrace();
		}
		return risultato;
	}
}
