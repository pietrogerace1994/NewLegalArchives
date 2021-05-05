package eng.la.controller;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eng.la.persistence.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.CentroDiCostoService;
import eng.la.business.FascicoloService;
import eng.la.business.IncaricoService;
import eng.la.business.ProfessionistaEsternoService;
import eng.la.business.ProformaService;
import eng.la.business.SocietaService;
import eng.la.business.UtenteService;
import eng.la.business.VoceDiContoService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.business.workflow.ConfigurazioneService;
import eng.la.model.Bonus;
import eng.la.model.Controparte;
import eng.la.model.Documento;
import eng.la.model.Fascicolo;
import eng.la.model.Proforma;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.RIncaricoProformaSocieta;
import eng.la.model.SchedaValutazione;
import eng.la.model.Societa;
import eng.la.model.TipoProforma;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.CentroDiCostoView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.ProformaView;
import eng.la.model.view.SchedaValutazioneView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.StatoProformaView;
import eng.la.model.view.TipoValutaView;
import eng.la.model.view.UtenteView;
import eng.la.model.view.VoceDiContoView;
import eng.la.presentation.validator.ProformaValidator;
import eng.la.util.ClassPathUtils;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.StringUtil;
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

@Controller("proformaController")
@SessionAttributes("proformaView")
public class ProformaController extends BaseController {
	private static final String MODEL_VIEW_NOME = "proformaView";
	private static final String PAGINA_FORM_PATH = "proforma/formProforma";

	private static final Logger logger = Logger.getLogger(ProformaController.class);

	@Autowired
	private ConfigurazioneService configurazioneService;

	@Autowired
	private FascicoloService fascicoloService;

	@Autowired
	private SocietaService societaService;

	@Autowired
	private ProfessionistaEsternoService professionistaEsternoService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private ProformaService proformaService;

	@Autowired
	private IncaricoService incaricoService;

	@Autowired
	private CentroDiCostoService centroDiCostoService;

	@Autowired
	private VoceDiContoService voceDiContoService;

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	@Autowired
	private EmailNotificationService emailNotificationService;
	
	public static final String SCHEDA_VALUTAZIONE_JRXML_REPORT_FILE_NAME		= "SchedaValutazione.jrxml";
	public static final String SCHEDA_VALUTAZIONE_JASPER_REPORT_FILE_NAME   	= "SchedaValutazione.jasper";
	public static final String SCHEDA_VALUTAZIONE_JASPER_REPORT_TITLE  			= "SCHEDA VALUTAZIONE";
	
	public static final String PARAMETER_SUBREPORTDIR       					= "SUBREPORT_DIR";
	public static final String PARAMETER_STYLEDIR           					= "STYLE_DIR";
	public static final String PARAMETER_IMAGERDIR          					= "IMAGE_DIR";
	public static final String PARAMETER_REPORTLOCALE       					= "REPORT_LOCALE";
	public static final String PARAMETER_REPORTTIMEZONE     					= "REPORT_TIME_ZONE";
	public static final String PARAMETER_LANGUAGE      	  						= "language";
	public static final String PARAMETER_COUNTRY 	      						= "country";

	@RequestMapping("/proforma/respingi")
	public String respingiProforma(@RequestParam("id") long id, Model model, Locale locale,
			HttpServletRequest request) {
		ProformaView view = new ProformaView();

		// engsecurity VA
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		String token=request.getParameter("CSRFToken")!=null?request.getParameter("CSRFToken"):"";
		try {

			String motivazione="";
			if(request.getParameter("motivazione")!=null){
				motivazione=request.getParameter("motivazione");
			}

			CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");	
			currentSessionUtil.setMotivazione(motivazione);

			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			ProformaView proformaSalvato = (ProformaView) proformaService.leggiConPermessi(id);// proformaService.leggi(id);

			if (proformaSalvato != null && proformaSalvato.getVo() != null) {
				//bozza respinta
				StatoProformaView statoProformaView = anagraficaStatiTipiService
						.leggiStatoProforma("BR", "IT");
				proformaSalvato.getVo().setStatoProforma(statoProformaView.getVo());
				proformaService.modifica(proformaSalvato);
				super.caricaListe(view, locale);
				// invio la e-mail
				try {
					emailNotificationService.inviaNotifica(CostantiDAO.RESPINTO, CostantiDAO.PROFORMA, id,
							request.getLocale().getLanguage().toUpperCase(),
							utenteView.getVo().getUseridUtil());
				} catch (Exception e) {
					System.out.println("Errore in invio e-mail" + e);
				}
			} else {
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}


		return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : "redirect:/proforma/dettaglio.action?id="+id+"&CSRFToken="+token;
	}

	@RequestMapping("/proforma/modifica")
	public String modificaProforma(@RequestParam("id") long id, Model model, Locale locale,
			HttpServletRequest request) {
		ProformaView view = new ProformaView();
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {

			request.getSession().removeAttribute("listaAllegatiGenericiProforma"); 
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			ProformaView proformaSalvato = (ProformaView) proformaService.leggiConPermessi(id);// proformaService.leggi(id);
			if (proformaSalvato != null && proformaSalvato.getVo() != null && proformaSalvato.getVo().getStatoProforma().getCodGruppoLingua().equals("B")) {
				view.setVo(proformaSalvato.getVo());
				popolaFormDaVo(view, proformaSalvato.getVo(), locale, utenteView);

				super.caricaListe(view, locale);
			} else {
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}

		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_FORM_PATH;
	}

	private void popolaFormDaVo(ProformaView view, Proforma vo, Locale locale, UtenteView utenteConnesso)
			throws Throwable {
		view.setProformaId(vo.getId());
		view.setNumero(vo.getNumero()!=null?vo.getNumero():"");

		String centroDiCosto = vo.getCentroDiCosto();
		String voceDiConto = vo.getVoceDiConto();

		if (vo.getRIncaricoProformaSocietas() != null && vo.getRIncaricoProformaSocietas().size() > 0) {
			RIncaricoProformaSocieta rIncaricoProformaSocieta = vo.getRIncaricoProformaSocietas().iterator().next();
			IncaricoView incarico = incaricoService.leggi(rIncaricoProformaSocieta.getIncarico().getId());
			view.setIncaricoId(incarico.getVo().getId());
			view.setIncaricoRiferimento(incarico);
			view.setFascicoloId(incarico.getVo().getFascicolo().getId());
			FascicoloView fascicoloView = fascicoloService.leggi(incarico.getVo().getFascicolo().getId(), FetchMode.JOIN);
			view.setFascicoloRiferimento(fascicoloView);

			if(centroDiCosto == null){
				centroDiCosto = fascicoloView.getVo().getCentroDiCosto();
			}

			if(voceDiConto == null){
				voceDiConto = fascicoloView.getVo().getVoceDiConto();
			}

			boolean isPenale = fascicoloView.getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
			view.setPenale(isPenale);

			Societa societa = rIncaricoProformaSocieta.getSocieta();
			view.setSocietaAddebitoScelta(societa.getId() + "");
			if( vo.getUtenteProcessamento() != null && vo.getUtenteProcessamento().trim().length() >  0 ){
				UtenteView utenteProcess = utenteService.leggiUtenteDaUserId(vo.getUtenteProcessamento());
				view.setUtenteProcessamento(utenteProcess.getCodiceDescrizioneUtente());	
			}

			if( vo.getDataProcessamento() != null ){
				view.setDataProcessamento(DateUtil.getDataDDMMYYYY(vo.getDataProcessamento().getTime()));
			}

			if( vo.getProcessato() != null ){
				view.setProcessato(  vo.getProcessato().equals(TRUE_CHAR) ?true:false);
			}
			view.setLegaleInterno(fascicoloView.getVo().getLegaleInterno());
			UtenteView utenteOwnerFascicolo = utenteService
					.leggiUtenteDaUserId(fascicoloView.getVo().getLegaleInterno());
			if (utenteOwnerFascicolo == null || utenteOwnerFascicolo.getVo() == null) {
				view.setLegaleInterno(fascicoloView.getVo().getLegaleInterno());
				view.setUnitaLegale("");
				view.setLegaleInternoDesc(fascicoloView.getVo().getLegaleInterno());
				view.setUnitaLegaleDesc("");
			} else {
				view.setLegaleInternoDesc(utenteOwnerFascicolo.getCodiceDescrizioneUtente());
				view.setUnitaLegaleDesc(utenteOwnerFascicolo.getCodiceDescrizioneUnitaLegale());
				view.setLegaleInterno(utenteOwnerFascicolo.getVo().getUseridUtil());
				view.setUnitaLegale(utenteOwnerFascicolo.getVo().getCodiceUnitaUtil());
			}


			view.setDisableCPA(vo.isCPADisabled());

			if(vo.isCPADisabled())
				view.setIsCPADisabled("true");
			else view.setIsCPADisabled("false");

			view.setListaTipoProforma(proformaService.getListaTipoProforma(locale.getLanguage().toUpperCase()));

			view.setIdTipoProforma(vo.getTipoProforma().getId());

			view.setTipoProformaMod(vo.getTipoProforma().getCodGruppoLingua());

			ProfessionistaEsternoView professionistaEsternoView = professionistaEsternoService
					.leggi(incarico.getVo().getProfessionistaEsterno().getId(), true);
			view.setProfessionistaEsterno(professionistaEsternoView.getCognomeNome());

			//SCHEDA VALUTAZIONE//

			SchedaValutazioneView schedaValutazioneView = new SchedaValutazioneView();

			Fascicolo fascicolo = fascicoloView.getVo();

			String pratControparte="";

			pratControparte=fascicolo.getNome();

			for(Controparte controparte : fascicolo.getContropartes()){
				pratControparte+=" / "+controparte.getNome();
			}

			schedaValutazioneView.setPraticaControparte(pratControparte);

			schedaValutazioneView.setAutorita(fascicolo.getAutoritaEmanante());

			if(fascicolo.getValoreCausaPratica()!=null)
				schedaValutazioneView.setValorecontroversia(fascicolo.getValoreCausaPratica().setScale(2).toPlainString());

			String valoreIncarico="";

			if(incarico.getVo().getLetteraIncarico()!=null && incarico.getVo().getLetteraIncarico().getCompenso()!=null){

				valoreIncarico = incarico.getVo().getLetteraIncarico().getCompenso().toString();

				for(Bonus bonus : incarico.getVo().getLetteraIncarico().getBonus()){
					valoreIncarico+=" + "+ bonus.getImporto().toString()+" "+bonus.getDescrizione();
				}

				schedaValutazioneView.setBonus(incarico.getVo().getLetteraIncarico().getBonus());
			}

			schedaValutazioneView.setValoreincarico(valoreIncarico);

			schedaValutazioneView.setParcellan(view.getNumero());

			schedaValutazioneView.setDataemissione(DateUtil.formattaData(vo.getDataInserimento().getTime()));

			schedaValutazioneView.setAvvocatostudio(professionistaEsternoView.getVo().getCognomeNome()+" / "
					+professionistaEsternoView.getVo().getStudioLegale()!=null?professionistaEsternoView.getVo().getStudioLegale().getDenominazione():"");

			schedaValutazioneView.setDataScheda(DateUtil.formattaData(new Date().getTime()));

			UtenteView user = utenteService.leggiUtenteDaUserId(fascicolo.getLegaleInterno());

			schedaValutazioneView.setUnitaLegaleOwner(user.getCodiceDescrizioneUnitaLegale()+" - "+user.getVo().getNominativoUtil());

			if (vo.getSchedaValutazione() != null) {

				schedaValutazioneView.setOptparcella(vo.getSchedaValutazione().getSoloOggLettInc());
				schedaValutazioneView.setOptlettera(vo.getSchedaValutazione().getCongruoRispLettInc());
				schedaValutazioneView.setOptparametri(vo.getSchedaValutazione().getCongruoRispParFor());
				schedaValutazioneView.setOptlegge(vo.getSchedaValutazione().getCongruoRispLegge());
				schedaValutazioneView.setNote(vo.getSchedaValutazione().getNote());

				if(vo.getSchedaValutazione().getAutorita() != null && !vo.getSchedaValutazione().getAutorita().isEmpty()){

					schedaValutazioneView.setAutorita(vo.getSchedaValutazione().getAutorita());
				}

				if(vo.getSchedaValutazione().getValoreIncarico() != null && !vo.getSchedaValutazione().getValoreIncarico().isEmpty()){

					schedaValutazioneView.setValoreincarico(vo.getSchedaValutazione().getValoreIncarico());
				}

				schedaValutazioneView.setDataScheda(DateUtil.formattaData(vo.getSchedaValutazione().getDataCreazione().getTime()));

				view.setTimeout(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_POLLING_FILE_SCHEDA_VALUTAZIONE).getVo().getCdValue());
				view.setPresentaAvviaWF(true);

			}
			else{
				schedaValutazioneView.setDataScheda(DateUtil.formattaData(new Date().getTime()));
			}

			schedaValutazioneView.setVo(vo.getSchedaValutazione());
			view.setSchedaValutazione(schedaValutazioneView);

			//SCHEDA VALUTAZIONE//

			if (fascicoloView.getVo().getRFascicoloSocietas() != null) {
				Collection<RFascicoloSocieta> listaFascicoloSocieta = fascicoloView.getVo().getRFascicoloSocietas();
				List<SocietaView> societaAddebitoAggiunte = new ArrayList<SocietaView>();

				for (RFascicoloSocieta soc  : listaFascicoloSocieta) {

					if (soc.getTipologiaSocieta().equals(SOCIETA_TIPOLOGIA_ADDEBITO)
							&& societa.getId() == soc.getSocieta().getId() ) {
						SocietaView societaView = new SocietaView();
						societaView.setVo(soc.getSocieta());
						societaAddebitoAggiunte.add(societaView);
					}

				}

				view.setListaSocietaAddebitoAggiunte(societaAddebitoAggiunte);
			}
			caricaDocumentiGenericiFilenet(view, vo, fascicoloView.getVo());
			caricaSchedaValutazioneFirmata(view, vo, fascicoloView.getVo());
		}

		view.setCentroDiCosto(centroDiCosto);
		view.setVoceDiConto(voceDiConto);
		view.setNome(vo.getNomeProforma());
		view.setNote(vo.getNote());
		view.setAnnoEsercizioFinanziario(vo.getAnnoEsercizioFinanziario() + "");

		if (vo.getDataAutorizzazione() != null) {
			view.setDataAutorizzazione(DateUtil.formattaData(vo.getDataAutorizzazione().getTime()));
		}
		if (vo.getDataRichAutorizzazione() != null) {
			view.setDataRichiestaAutorizzazione(DateUtil.formattaData(vo.getDataRichAutorizzazione().getTime()));
		}
		if (vo.getDataComposizione() != null) {
			view.setDataComposizione(DateUtil.formattaData(vo.getDataComposizione().getTime()));
		}
		if (vo.getDataInserimento() != null) {
			view.setDataInserimento(DateUtil.formattaData(vo.getDataInserimento().getTime()));
		}
		if (vo.getDataInvioAmministrativo() != null) {
			view.setDataInvioAmministratore(DateUtil.formattaData(vo.getDataInvioAmministrativo().getTime()));
		}

		StatoProformaView statoView = anagraficaStatiTipiService
				.leggiStatoProforma(vo.getStatoProforma().getCodGruppoLingua(), locale.getLanguage().toUpperCase());
		view.setStato(statoView.getVo().getDescrizione());

		view.setAutorizzatore(vo.getAutorizzatore());
		if (vo.getCpa() != null) {
			view.setCpa(vo.getCpa().doubleValue());
		}
		if (vo.getDiritti() != null) {
			view.setDiritti(vo.getDiritti().doubleValue());
		}
		view.setEsitoVerificaProforma(vo.getEsitoVerificaProforma());
		if (vo.getOnorari() != null) {
			view.setOnorari(vo.getOnorari().doubleValue());
		}
		if (vo.getSpeseImponibili() != null) {
			view.setSpeseImponibili(vo.getSpeseImponibili().doubleValue());
		}
		if (vo.getSpeseNonImponibili() != null) {
			view.setSpeseNonImponibili(vo.getSpeseNonImponibili().doubleValue());
		}
		if (vo.getTipoValuta() != null) {
			view.setValutaId(vo.getTipoValuta().getId());
		}
		if (vo.getTotaleAutorizzato() != null) {
			view.setTotale(vo.getTotaleAutorizzato().doubleValue());
		}

		Double totaleImponibile = (vo.getOnorari() == null ? new BigDecimal(0) : vo.getOnorari())
				.add(vo.getDiritti() == null ? new BigDecimal(0) : vo.getDiritti())
				.add(vo.getCpa() == null ? new BigDecimal(0) : vo.getCpa())
				.add(vo.getSpeseImponibili() == null ? new BigDecimal(0) : vo.getSpeseImponibili())
				.doubleValue();

		view.setTotaleImponibile(totaleImponibile);
		view.setUltimo(vo.getUltimo().equals(TRUE_CHAR) ? true : false);


		if(vo.getTotaleAutorizzato()!= null && vo.getTotaleAutorizzato() != new BigDecimal(0) && totaleImponibile==0)
			view.setTotaleForce(vo.getTotaleAutorizzato().doubleValue());

	}

	private void caricaDocumentiGenericiFilenet(ProformaView view, Proforma vo, Fascicolo fascicolo) throws Throwable {
		String nomeCartellaProforma = FileNetUtil.getProformaCartella(vo.getId(), fascicolo.getDataCreazione(),
				fascicolo.getNome(), vo.getNomeProforma());
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaProforma)
				: documentaleDAO.leggiCartella(nomeCartellaProforma);
		 */
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");

		logger.info ("@@DDS caricaDocumentiGenericiFilenet __________________________");
		/*@@DDS inizio commento
		DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();
		List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
		if (documenti != null) {
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.ALLEGATO_DOCUMENT)){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.get_Name());
						docView.setUuid(documento.get_Id().toString());
						listaDocumenti.add(docView);
					}
				}
			}
			view.setListaAllegatiGenerici(listaDocumenti);
			view.setPresentaAvviaWF(true && view.isPresentaAvviaWF());

		}
		 */
		List<Document> documenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaProforma)
				: documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaProforma);
		List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
		if (documenti != null) {

				for (Document documento:documenti) {

					if( documento.getDocumentalClass().equals(FileNetClassNames.ALLEGATO_DOCUMENT)){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.getContents().get(0).getContentsName());
						docView.setUuid(documento.getId());
						listaDocumenti.add(docView);
					}
				}

			view.setListaAllegatiGenerici(listaDocumenti);
			view.setPresentaAvviaWF(true && view.isPresentaAvviaWF());

		}
	}

	private void caricaSchedaValutazioneFirmata(ProformaView view, Proforma vo, Fascicolo fascicolo) throws Throwable {
		String nomeCartellaProforma = FileNetUtil.getProformaCartella(vo.getId(), fascicolo.getDataCreazione(),
				fascicolo.getNome(), vo.getNomeProforma());
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaProforma = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaProforma)
				: documentaleDAO.leggiCartella(nomeCartellaProforma);
*/
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");


		/*@@DDS inizio commento
		DocumentSet documenti = cartellaProforma.get_ContainedDocuments();
		DocumentoView schedaValutazioneFirmata = null;
		if (documenti != null) {
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT)){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.get_Name());
						File fileTmp = File.createTempFile("schedaValutazioneFirmata", "___" + documento.get_Name() );

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
						schedaValutazioneFirmata = docView;
					}
				}
			}
			view.setSchedaValutazioneFirmataDoc(schedaValutazioneFirmata);
		}
		 */
		List<Document> documenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaProforma)
				: documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaProforma);
		DocumentoView schedaValutazioneFirmata = null;
		if (documenti != null) {


				for (Document documento:documenti) {

					if( documento.getDocumentalClass().equals(FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT)){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.getContents().get(0).getContentsName());
						File fileTmp = File.createTempFile("schedaValutazioneFirmata", "___" + documento.getContents().get(0).getContentsName() );

						String uuid = documento.getId().toString();
						uuid = uuid.replace("{", "");
						uuid = uuid.replace("}", "");


						if(isPenale)
							FileUtils.writeByteArrayToFile(fileTmp, documentaleDdsCryptDAO.leggiContenutoDocumento(uuid));
						else
							FileUtils.writeByteArrayToFile(fileTmp, documentaleDdsDAO.leggiContenutoDocumento(uuid));

						docView.setFile(fileTmp);
						docView.setContentType(documento.getContents().get(0).getContentsMimeType());
						docView.setUuid(uuid);
						logger.debug ("@@DDS ____________uuuid" + docView.getUuid());
						schedaValutazioneFirmata = docView;
					}
				}

			view.setSchedaValutazioneFirmataDoc(schedaValutazioneFirmata);
		}
	}


	@RequestMapping(value = "/proforma/salva", method = RequestMethod.POST)
	public String salvaProforma(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated ProformaView proformaView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		String token=request.getParameter("CSRFToken");

		try {


			if (proformaView.getOp() != null && !proformaView.getOp().equals("salvaProforma")) {
				String ritorno = invocaMetodoDaReflection(proformaView, bindingResult, locale, model, request, response,
						this);

				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				model.addAttribute(MODEL_VIEW_NOME, proformaView);
				return PAGINA_FORM_PATH;
			}

			if (proformaView.getProformaId()!= null && proformaView.getProformaId().intValue()>0 && proformaView.getVo()!=null) {
				ProformaView proformaSalvato = proformaService.leggiConPermessi(proformaView.getVo().getId());
				if(proformaSalvato==null || proformaSalvato!=null && proformaView.getVo()==null){

					model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
					return "redirect:/errore.action";

				}
			}

			preparaPerSalvataggio(proformaView, bindingResult);

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			long proformaId = 0;
			boolean isNew = false;
			if (proformaView.getProformaId() == null || proformaView.getProformaId() == 0) {
				ProformaView proformaSalvato = proformaService.inserisci(proformaView);
				proformaId = proformaSalvato.getVo().getId();
				isNew = true;


			} else {
				proformaService.modifica(proformaView);
				proformaId = proformaView.getVo().getId();
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
			if (isNew) {
				return "redirect:modifica.action?id=" + proformaId+"&CSRFToken="+token;
			}
			return "redirect:dettaglio.action?id=" + proformaId+"&CSRFToken="+token;
		} catch (Throwable e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}

	private void preparaPerSalvataggio(ProformaView view, BindingResult bindingResult) throws Throwable {
		Proforma vo = null;
		if (view.getProformaId() != null) {
			ProformaView oldProformaView = proformaService.leggi(view.getProformaId());
			vo = oldProformaView.getVo();
			vo.setDataComposizione(DateUtil.toDate(view.getDataComposizione()));
			vo.setDataInserimento(DateUtil.toDate(view.getDataInserimento()));

		}else{ 
			vo = new Proforma(); 
			vo.setNumero(view.getNumero()); 
			vo.setProcessato(FALSE_CHAR);
			vo.setEsitoVerificaProforma(TRUE_CHAR);
			vo.setDaProfEsterno(FALSE_CHAR);
			vo.setDataComposizione(new Date());
			vo.setDataInserimento(new Date());
		}

		if (StringUtils.isNotBlank(view.getAnnoEsercizioFinanziario())) {
			vo.setAnnoEsercizioFinanziario(NumberUtils.createBigDecimal(view.getAnnoEsercizioFinanziario()));
		}

		vo.setAutorizzatore(view.getAutorizzatore());
		vo.setCentroDiCosto(view.getCentroDiCosto());
		if (view.getCpa() != null) {
			vo.setCpa(NumberUtils.createBigDecimal(view.getCpa() + ""));
		}
		if (view.getDiritti() != null) {
			vo.setDiritti(NumberUtils.createBigDecimal(view.getDiritti() + ""));
		} 
		vo.setLang("IT");
		vo.setNote(view.getNote());
		vo.setCPADisabled(view.getDisableCPA());

		TipoProforma tipoProforma = proformaService.leggiTipoProforma(view.getIdTipoProforma());

		vo.setTipoProforma(tipoProforma);

		if (view.getOnorari() != null) {
			vo.setOnorari(NumberUtils.createBigDecimal(view.getOnorari() + ""));
		}

		Set<RIncaricoProformaSocieta> rIncaricoProformaSocietas = new HashSet<RIncaricoProformaSocieta>();
		if (view.getSocietaAddebitoScelta() != null) {
			SocietaView societaView = societaService.leggi(NumberUtils.toLong(view.getSocietaAddebitoScelta()));
			IncaricoView incaricoView = incaricoService.leggi(view.getIncaricoId());
			RIncaricoProformaSocieta incaricoProformaSocieta = new RIncaricoProformaSocieta();
			incaricoProformaSocieta.setIncarico(incaricoView.getVo());
			incaricoProformaSocieta.setSocieta(societaView.getVo());
			vo.setRIncaricoProformaSocietas(rIncaricoProformaSocietas);
			ProfessionistaEsternoView professionistaEsternoView = new ProfessionistaEsternoView();
			professionistaEsternoView.setVo(incaricoView.getVo().getProfessionistaEsterno());

			if (view.getProformaId() == null || view.getProformaId().longValue() == 0 ) {				
				vo.setNomeProforma(
						"Proforma - " + professionistaEsternoView.getCognomeNome() + " - " + view.getDataInserimento());
			}

			rIncaricoProformaSocietas.add(incaricoProformaSocieta);
			vo.setRIncaricoProformaSocietas(rIncaricoProformaSocietas);

			if( view.getSchedaValutazioneDoc() != null && view.getSchedaValutazioneDoc().isNuovoDocumento() && view.getSchedaValutazioneDoc().getFile() != null){
				SchedaValutazione schedaValutazione = new SchedaValutazione();
				Documento documento = new Documento();
				//documento.setUuid();
				documento.setContentType( view.getSchedaValutazioneDoc().getContentType());
				documento.setNomeFile(view.getSchedaValutazioneDoc().getNomeFile());
				documento.setClasseDocumentale( FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT );
				schedaValutazione.setDocumento(documento); 

				UtenteView utente = utenteService.leggiUtenteDaUserId( incaricoView.getVo().getFascicolo().getLegaleInterno() );				
				schedaValutazione.setLegaleInterno(utente.getVo().getUseridUtil());
				schedaValutazione.setUnitaLegale(utente.getVo().getCodiceUnitaUtil());
				schedaValutazione.setDataCreazione(new Date());
				schedaValutazione.setLang("IT");
				vo.setSchedaValutazione(schedaValutazione);
			} 
		}

		if (view.getSpeseImponibili() != null) {
			vo.setSpeseImponibili(NumberUtils.createBigDecimal(view.getSpeseImponibili() + ""));
		}

		if (view.getSpeseNonImponibili() != null) {
			vo.setSpeseNonImponibili(NumberUtils.createBigDecimal(view.getSpeseNonImponibili() + ""));
		}

		if (view.getValutaId() != null) {
			TipoValutaView tipoValuta = anagraficaStatiTipiService.leggiTipoValuta(view.getValutaId());
			vo.setTipoValuta(tipoValuta.getVo());
		}
		if (view.getTotale() != null) {
			vo.setTotaleAutorizzato(NumberUtils.createBigDecimal(view.getTotale() + ""));
		}
		if( view.getTotaleImponibile() != null ){ 
			vo.setTotaleImponibile(NumberUtils.createBigDecimal(view.getTotaleImponibile()+""));
		}
		vo.setUltimo(view.getUltimo() ? TRUE_CHAR : FALSE_CHAR);
		vo.setVoceDiConto(view.getVoceDiConto());
		vo.setCentroDiCosto(view.getCentroDiCosto());


		if(view.getSchedaValutazione().getOptparcella()!=null 
				&& view.getSchedaValutazione().getOptlettera()!=null
				&& view.getSchedaValutazione().getOptparametri()!=null
				&& view.getSchedaValutazione().getOptlegge()!=null)
		{
			SchedaValutazioneView schedaView = view.getSchedaValutazione();
			SchedaValutazione scheda = new SchedaValutazione();

			scheda.setSoloOggLettInc(schedaView.getOptparcella()!=null ?schedaView.getOptparcella() : "0" );
			scheda.setCongruoRispLettInc(schedaView.getOptlettera()!=null ?schedaView.getOptlettera() : "0" );
			scheda.setCongruoRispParFor(schedaView.getOptparametri()!=null ?schedaView.getOptparametri() : "0" );
			scheda.setCongruoRispLegge(schedaView.getOptlegge()!=null ?schedaView.getOptlegge() : "0" );

			if(schedaView.getNote() != null && !schedaView.getNote().isEmpty()){

				String noteCharDecode = StringUtil.escapeMsWord(schedaView.getNote());
				scheda.setNote(noteCharDecode);
				schedaView.setNote(noteCharDecode);
			}

			if(schedaView.getAutorita() != null && !schedaView.getAutorita().isEmpty()){

				String autoritaCharDecode = StringUtil.escapeMsWord(schedaView.getAutorita());
				scheda.setAutorita(autoritaCharDecode);
				schedaView.setAutorita(autoritaCharDecode);
			}

			if(schedaView.getValoreincarico() != null && !schedaView.getValoreincarico().isEmpty()){

				String valoreIncaricoCharDecode = StringUtil.escapeMsWord(schedaView.getValoreincarico());
				scheda.setValoreIncarico(valoreIncaricoCharDecode);
				schedaView.setValoreIncarico(valoreIncaricoCharDecode);
			}

			scheda.setDataCreazione(DateUtil.toDate(schedaView.getDataScheda()));

			IncaricoView incaricoView = incaricoService.leggi(view.getIncaricoId());
			UtenteView utente = utenteService.leggiUtenteDaUserId( incaricoView.getVo().getFascicolo().getLegaleInterno() );
			scheda.setLegaleInterno(utente.getVo().getUseridUtil());
			scheda.setUnitaLegale(utente.getVo().getCodiceUnitaUtil());
			scheda.setLang("IT");

			if(view.getVo().getSchedaValutazione()!=null)
				scheda.setId(view.getVo().getSchedaValutazione().getId());

//			scheda.setLifecycleXml(getLifecycleFile(view));
//			scheda.setDataInvio(null);
//			scheda.setInviata(0);
//			scheda.setReturnFile(null);
//			scheda .setReturnFile(null);
			
			byte[] out = null;
			JasperPrint jasperPrint = null;
			String path  = "/jasper/";
			String pathReport  = path + SCHEDA_VALUTAZIONE_JASPER_REPORT_FILE_NAME;   
	        JasperReport jasperReport = (JasperReport) JRLoader.loadObject( this.getClass().getClassLoader().getResourceAsStream(  pathReport ));
			
	        Locale locale = new Locale("it", "IT"); // DEFAULT
	        URL resourceStylePath = ClassPathUtils.getResource(this.getClass(), "/jasper/");
			
			Map<String,Object> reportParameters = getSchedaParametersForPdf(view, locale, resourceStylePath);
			
			List<IncaricoView> lista = new ArrayList<IncaricoView>();
			jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameters, new JRBeanCollectionDataSource(lista));      
	    	out = JasperExportManager.exportReportToPdf(jasperPrint);        	
	    	System.out.println("Bytes: " + out.length);
	    	
	    	scheda.setLifecycleXml("");
	    	scheda.setDataInvio(new Date());
	    	scheda.setInviata(1);
	    	
	    	Blob blob = new javax.sql.rowset.serial.SerialBlob(out);
	    	scheda.setReturnFile(blob);
	    	scheda.setReturnFileName("SchedaValutazione.pdf");
			
			vo.setSchedaValutazione(scheda);
		}
		view.setVo(vo);
	}

	@RequestMapping("/proforma/crea")
	public String creaProforma(HttpServletRequest request, Model model, Locale locale) {
		ProformaView view = new ProformaView();

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {
			super.caricaListe(view, locale);
			if (request.getParameter("incaricoId") != null) {
				long incaricoId = NumberUtils.toLong(request.getParameter("incaricoId"));
				IncaricoView incaricoView = incaricoService.leggi(incaricoId);
				view.setIncaricoId(incaricoView.getVo().getId());
				view.setIncaricoRiferimento(incaricoView);
				ProfessionistaEsternoView professionistaEsternoView = professionistaEsternoService
						.leggi(incaricoView.getVo().getProfessionistaEsterno().getId(), true);
				view.setProfessionistaEsterno(professionistaEsternoView.getCognomeNome());
				view.setFascicoloId(incaricoView.getVo().getFascicolo().getId());
				view.setDataInserimento(DateUtil.getDataDDMMYYYY(System.currentTimeMillis()));
				FascicoloView fascicoloView = fascicoloService.leggi(incaricoView.getVo().getFascicolo().getId(),
						FetchMode.JOIN);
				view.setFascicoloRiferimento(fascicoloView);

				view.setCentroDiCosto(fascicoloView.getVo().getCentroDiCosto());
				view.setVoceDiConto(fascicoloView.getVo().getVoceDiConto());

				UtenteView utenteConnesso = (UtenteView) request.getSession()
						.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
				if (utenteConnesso != null) {
					view.setUnitaLegaleDesc(utenteConnesso.getCodiceDescrizioneUnitaLegale());
					view.setLegaleInternoDesc(utenteConnesso.getVo().getUseridUtil()); 
					view.setUnitaLegale(utenteConnesso.getVo().getCodiceUnitaUtil());
					view.setLegaleInterno(utenteConnesso.getVo().getUseridUtil());
				}

				if (fascicoloView.getVo().getRFascicoloSocietas() != null) {
					Collection<RFascicoloSocieta> listaFascicoloSocieta = fascicoloView.getVo().getRFascicoloSocietas();
					List<SocietaView> societaAddebitoAggiunte = new ArrayList<SocietaView>();

					for (RFascicoloSocieta societa : listaFascicoloSocieta) {

						if (societa.getTipologiaSocieta().equals(SOCIETA_TIPOLOGIA_ADDEBITO)) {
							SocietaView societaView = new SocietaView();
							societaView.setVo(societa.getSocieta());
							societaAddebitoAggiunte.add(societaView);
						}

					}

					view.setListaSocietaAddebitoAggiunte(societaAddebitoAggiunte);
				}

				view.setListaTipoProforma(proformaService.getListaTipoProforma(locale.getLanguage().toUpperCase()));
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}

		view.setVo(new Proforma());

		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_FORM_PATH;
	}


	@RequestMapping(value = "/proforma/uploadSchedaValutazione", method = RequestMethod.POST)
	public String uploadSchedaValutazione(HttpServletRequest request, @RequestParam("file") MultipartFile file,
			@ModelAttribute(MODEL_VIEW_NOME) ProformaView proformaView) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		
		try {
			NumberUtils.toLong(request.getParameter("idProforma"));

			File fileTmp = File.createTempFile("proforma", "___" + file.getOriginalFilename());
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			proformaView.setSchedaValutazioneDoc(documentoView); 
			request.getSession().removeAttribute("schedaValutazioneProforma");

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "proforma/schedaValutazione";
	}

	@RequestMapping(value = "/proforma/rimuoviSchedaValutazione", method = RequestMethod.POST)
	public String rimuoviSchedaValutazione(HttpServletRequest request,
			@ModelAttribute(MODEL_VIEW_NOME) ProformaView proformaView) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {
			NumberUtils.toLong(request.getParameter("idProforma"));

			proformaView.setSchedaValutazioneDoc(null);
			request.getSession().removeAttribute("schedaValutazioneProforma");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "proforma/schedaValutazione";
	}

	@RequestMapping(value = "/proforma/uploadAllegatoGenerico", method = RequestMethod.POST)
	public String uploadAllegatoGenerico(HttpServletRequest request, @RequestParam("file") MultipartFile file,
			@ModelAttribute(MODEL_VIEW_NOME) ProformaView proformaView) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		
		try {
			NumberUtils.toLong(request.getParameter("idProforma"));

			File fileTmp = File.createTempFile("allegatoGenerico", "___" + file.getOriginalFilename());
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true); 
			documentoView.setUuid("" +( proformaView.getListaAllegatiGenerici() == null || proformaView.getListaAllegatiGenerici().size() == 0 ?  1 : proformaView.getListaAllegatiGenerici().size()+1));

			List<DocumentoView> allegatiGenerici = proformaView.getListaAllegatiGenerici() == null ? new ArrayList<DocumentoView>(): proformaView.getListaAllegatiGenerici();
			allegatiGenerici.add(documentoView);
			proformaView.setListaAllegatiGenerici(allegatiGenerici);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "proforma/allegatiGenerici";
	}

	@RequestMapping(value = "/proforma/rimuoviAllegatoGenerico", method = RequestMethod.POST)
	public String rimuoviAllegatoGenerico(HttpServletRequest request,
			@ModelAttribute(MODEL_VIEW_NOME) ProformaView proformaView) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {
			String uuid = request.getParameter("uuid");
			if (uuid == null) {
				throw new RuntimeException("uuid non pu� essere null");
			}

			List<DocumentoView> allegatiGenericiOld = proformaView.getListaAllegatiGenerici();

			List<DocumentoView> allegatiGenerici = new ArrayList<DocumentoView>();
			allegatiGenerici.addAll(allegatiGenericiOld);
			Set<String> allegatiDaRimuovere = proformaView.getAllegatiDaRimuovereUuid() == null ? new HashSet<String>()
					: proformaView.getAllegatiDaRimuovereUuid();
			if (allegatiGenericiOld != null) {
				for (DocumentoView docView : allegatiGenericiOld) {
					if (docView.getUuid() != null && docView.getUuid().contains(uuid) ) {
						if( docView.getUuid().length() >= 30 ){
							allegatiDaRimuovere.add(uuid);
						}
						allegatiGenerici.remove(docView);
						break;
					}
				}
			}
			proformaView.setListaAllegatiGenerici(allegatiGenerici);
			proformaView.setAllegatiDaRimuovereUuid(allegatiDaRimuovere);  
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "proforma/allegatiGenerici";
	}


	@RequestMapping(value = "/proforma/selezionaSocieta", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String selezionaSocieta(HttpServletRequest request, Locale locale,
			@ModelAttribute(MODEL_VIEW_NOME) ProformaView proformaView) {


		try {
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArrayCDC = new JSONArray();
			JSONArray jsonArrayVDC = new JSONArray();
			if( request.getParameter("idSocieta") != null && request.getParameter("idSocieta").trim().length() > 0 && 
					request.getParameter("settoreGiuridicoId") != null && request.getParameter("settoreGiuridicoId").trim().length() > 0 &&
					request.getParameter("tipologiaFascicoloId") != null && request.getParameter("tipologiaFascicoloId").trim().length() > 0 
					){
				String unitaLegale = request.getParameter("unitaLegale");
				long societaId = NumberUtils.toLong(request.getParameter("idSocieta"));
				long settoreGiuridicoId = NumberUtils.toLong(request.getParameter("settoreGiuridicoId"));
				long tipologiaFascicoloId = NumberUtils.toLong(request.getParameter("tipologiaFascicoloId"));
				List<CentroDiCostoView> listaCDC = centroDiCostoService.leggiDaUnitaLegaleSettoreTipologiaFascicolo(unitaLegale, societaId, settoreGiuridicoId, tipologiaFascicoloId);
				List<VoceDiContoView> listaVDC = voceDiContoService.leggiDaUnitaLegaleSettoreTipologiaFascicolo(unitaLegale, settoreGiuridicoId, tipologiaFascicoloId);
				proformaView.setListaCentroDiCosto(listaCDC);
				proformaView.setListaVoceDiConto(listaVDC);

				for(CentroDiCostoView cdc : listaCDC){
					JSONObject objCDC = new JSONObject();
					objCDC.put("cdc", cdc.getVo().getCdc());
					objCDC.put("descrizione", cdc.getVo().getDescrizione());
					objCDC.put("id", cdc.getVo().getId()+"");
					jsonArrayCDC.put(objCDC);
				}

				for(VoceDiContoView vdc : listaVDC){
					JSONObject objVDC = new JSONObject();
					objVDC.put("vdc", vdc.getVo().getVdc());
					objVDC.put("descrizione", vdc.getVo().getDescrizione());
					objVDC.put("id", vdc.getVo().getId()+"");
					jsonArrayVDC.put(objVDC);
				}

				jsonObject.put("VDC", jsonArrayVDC);
				jsonObject.put("CDC", jsonArrayCDC);
				return jsonObject.toString();
			}
		}catch(Throwable e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		ProformaView proformaView = (ProformaView) view;
		proformaView.setListaTipoValuta(anagraficaStatiTipiService.leggiTipoValuta(false));
		if( proformaView.getSocietaAddebitoScelta() != null && proformaView.getSocietaAddebitoScelta().trim().length() > 0 ){
			String unitaLegale = proformaView.getUnitaLegale();
			long societaId = NumberUtils.toLong(proformaView.getSocietaAddebitoScelta());
			long settoreGiuridicoId = proformaView.getFascicoloRiferimento().getVo().getSettoreGiuridico().getId();
			long tipologiaFascicoloId = proformaView.getFascicoloRiferimento().getVo().getTipologiaFascicolo().getId();
			proformaView.setListaCentroDiCosto(centroDiCostoService.leggiDaUnitaLegaleSettoreTipologiaFascicolo(unitaLegale, societaId, settoreGiuridicoId, tipologiaFascicoloId));
			proformaView.setListaVoceDiConto(voceDiContoService.leggiDaUnitaLegaleSettoreTipologiaFascicolo(unitaLegale, settoreGiuridicoId, tipologiaFascicoloId));
		}
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ProformaValidator());
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	/*private String getLifecycleFile(ProformaView view) {

		String out = "";

		Element root = new Element("SchedaValutazionePDF");

		Document doc = new Document();

		String suffix = "id_";

		Element uno = new Element(suffix+"1");
		Element due = new Element(suffix+"2");
		Element tre = new Element(suffix+"3");
		Element quattro = new Element(suffix+"4");
		Element cinque = new Element(suffix+"5");
		Element sei = new Element(suffix+"6");
		Element sette = new Element(suffix+"7");
		Element otto = new Element(suffix+"8");
		Element nove = new Element(suffix+"9");
		Element dieci = new Element(suffix+"10");
		Element undici = new Element(suffix+"11");
		Element dodiciuno = new Element(suffix+"12a");
		Element dodicidue = new Element(suffix+"12b");
		Element dodicitre = new Element(suffix+"12c");
		Element dodiciquattro = new Element(suffix+"12d");
		Element dodicicinque = new Element(suffix+"12e");
		Element dodicisei = new Element(suffix+"12f");
		Element dodicisette = new Element(suffix+"12g");

		Element tredici = new Element(suffix+"13");
		Element quattordici = new Element(suffix+"14");
		Element quindici = new Element(suffix+"15");

		TipoValutaView tipoValuta = null;
		try {
			tipoValuta = anagraficaStatiTipiService.leggiTipoValuta(view.getValutaId());
		} catch (Throwable e) {
			e.printStackTrace();
		}

		String valuta = tipoValuta.getVo().getNome();

		SchedaValutazioneView scheda = view.getSchedaValutazione();


		uno.addContent(scheda.getPraticaControparte());

		due.addContent(scheda.getAutorita());

		if(scheda.getValorecontroversia()!=null)
			tre.addContent(scheda.getValorecontroversia()+" "+valuta);

		String valoreIncarico="";

		if(scheda.getValoreIncarico()!=null){
			valoreIncarico+=scheda.getValoreIncarico()+" "+valuta;
		}
		if(scheda.getBonus()!=null){
			for(Bonus bonus : scheda.getBonus()){
				valoreIncarico+= " + "+bonus.getImporto().toString()+" "+valuta+" : "+bonus.getDescrizione();
			}
		}
		
		quattro.addContent(valoreIncarico);

		cinque.addContent(view.getNumero());

		sei.addContent(view.getDataInserimento());

		sette.addContent(scheda.getAvvocatostudio());

		if(scheda.getOptparcella().equals("1"))
			otto.addContent("S�");
		else
			otto.addContent("No");

		if(scheda.getOptlettera().equals("1"))
			nove.addContent("S�");
		else
			nove.addContent("No");

		if(scheda.getOptparametri().equals("1"))
			dieci.addContent("S�");
		else
			dieci.addContent("No");

		if(scheda.getOptlegge().equals("1"))
			undici.addContent("S�");
		else
			undici.addContent("No");

		dodiciuno.addContent(view.getDiritti().toString()+" "+valuta);
		dodicidue.addContent(view.getOnorari().toString()+" "+valuta);
		dodicitre.addContent(view.getSpeseImponibili().toString()+" "+valuta);
		dodiciquattro.addContent(view.getCpa().toString()+" "+valuta);
		dodicicinque.addContent(view.getTotaleImponibile().toString()+" "+valuta);
		dodicisei.addContent(view.getSpeseNonImponibili().toString()+" "+valuta);
		dodicisette.addContent(view.getTotale().toString()+" "+valuta);

		tredici.addContent(scheda.getNote());

		quattordici.addContent(scheda.getDataScheda());
		quindici.addContent(scheda.getUnitaLegaleOwner());

		root.addContent(uno);
		root.addContent(due);
		root.addContent(tre);
		root.addContent(quattro);
		root.addContent(cinque);
		root.addContent(sei);
		root.addContent(sette);
		root.addContent(otto);
		root.addContent(nove);

		root.addContent(dieci);
		root.addContent(undici);
		root.addContent(dodiciuno);
		root.addContent(dodicidue);
		root.addContent(dodicitre);
		root.addContent(dodiciquattro);
		root.addContent(dodicicinque);
		root.addContent(dodicisei);
		root.addContent(dodicisette);
		root.addContent(tredici);
		root.addContent(quattordici);
		root.addContent(quindici);

		doc.setRootElement(root);

		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat()) {
			@Override
			public String escapeElementEntities(String str) {
				return str;
			}
		};

		out = outputter.outputString(doc);
		return out;
	}*/
	
	private Map<String,Object> getSchedaParametersForPdf(ProformaView view, Locale locale, URL resourceStylePath) {
		
		Map<String,Object> reportParameters = new HashMap<>();
        reportParameters.put("SUBREPORT_DIR", "/jasper/"); 
	    reportParameters.put("reportTitle", SCHEDA_VALUTAZIONE_JASPER_REPORT_TITLE);
	    reportParameters.put(PARAMETER_SUBREPORTDIR,   "/jasper/"); //assolutamente necesario altrimenti non funzionano i sottoreport
		reportParameters.put(PARAMETER_STYLEDIR,       "/jasper/"); //assolutamente necesario altrimenti non funzionano gli stili
	    reportParameters.put(PARAMETER_IMAGERDIR,      "/jasper/"); //assolutamente necesario altrimenti non funzionano gli stili
	    reportParameters.put(JRParameter.REPORT_LOCALE,   locale);     //assolutamente necesario altrimenti non funzionano le formattazioni internazionali
	    reportParameters.put(PARAMETER_LANGUAGE,       locale.getLanguage());
	    reportParameters.put(PARAMETER_COUNTRY,        locale.getLanguage().toUpperCase());
		
		TipoValutaView tipoValuta = null;
		try {
			tipoValuta = anagraficaStatiTipiService.leggiTipoValuta(view.getValutaId());
		} catch (Throwable e) {
			e.printStackTrace();
		}

		String valuta = tipoValuta.getVo().getNome();
		SchedaValutazioneView scheda = view.getSchedaValutazione();

		reportParameters.put("1",scheda.getPraticaControparte());
		reportParameters.put("2",scheda.getAutorita());

		if(scheda.getValorecontroversia()!=null)
			reportParameters.put("3",scheda.getValorecontroversia()+" "+valuta);
		else
			reportParameters.put("3","");

		String valoreIncarico="";

		if(scheda.getValoreIncarico()!=null){
			valoreIncarico+=scheda.getValoreIncarico()+" "+valuta;
		}
		if(scheda.getBonus()!=null){
			for(Bonus bonus : scheda.getBonus()){
				valoreIncarico+= " + "+bonus.getImporto().toString()+" "+valuta+" : "+bonus.getDescrizione();
			}
		}
		
		reportParameters.put("4",valoreIncarico);
		reportParameters.put("5",view.getNumero());
		reportParameters.put("6",view.getDataInserimento());
		reportParameters.put("7",scheda.getAvvocatostudio());

		if(scheda.getOptparcella().equals("1"))
			reportParameters.put("8","S�");
		else
			reportParameters.put("8","No");

		if(scheda.getOptlettera().equals("1"))
			reportParameters.put("9","S�");
		else
			reportParameters.put("8","No");

		if(scheda.getOptparametri().equals("1"))
			reportParameters.put("10","S�");
		else
			reportParameters.put("10","No");

		if(scheda.getOptlegge().equals("1"))
			reportParameters.put("11","S�");
		else
			reportParameters.put("11","No");
		
		reportParameters.put("12",view.getDiritti().toString()+" "+valuta);
		reportParameters.put("12due",view.getOnorari().toString()+" "+valuta);
		reportParameters.put("12tre",view.getSpeseImponibili().toString()+" "+valuta);
		reportParameters.put("12quattro",view.getCpa().toString()+" "+valuta);
		reportParameters.put("12cinque",view.getTotaleImponibile().toString()+" "+valuta);
		reportParameters.put("12sei",view.getSpeseNonImponibili().toString()+" "+valuta);
		reportParameters.put("12sette",view.getTotale().toString()+" "+valuta);
		
		reportParameters.put("13",scheda.getNote());
		reportParameters.put("14",scheda.getDataScheda());
		reportParameters.put("15",scheda.getUnitaLegaleOwner());

		return reportParameters;
	}

	@RequestMapping(value = "/proforma/checkfile", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody RisultatoOperazioneRest checkFile(@RequestParam("id") long id, Model model) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		
		try {
			ProformaView proforma = proformaService.leggi(id);

			SchedaValutazione scheda = proforma.getVo().getSchedaValutazione();

			logger.debug("Chiamata polling checkFile per Proforma: "+id+" - SchedaValutazione: "+scheda.getId());

			Integer checkStatus = proformaService.checkStatusInviata(scheda.getId());

			if(checkStatus==-1){
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.WARN, "messaggio.warn");
				return risultato;
			}
			else{	
				Integer check  = proformaService.checkFile(scheda.getId());

				if(check!=0){

					risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK,  "messaggio.ok");
				}
			}
		} catch (Throwable e) {
			logger.error("Errore check file per Scheda Valutazione: "+id);
			e.printStackTrace();
		}
		return risultato;
	}

	@RequestMapping(value = "/proforma/downloadSchedaValutazione" , method = RequestMethod.GET)
	public String downloadDoc(@RequestParam("id") String id , HttpServletRequest request,HttpServletResponse response, Model model, Locale locale) throws Throwable {

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		if(id!=null && !id.equalsIgnoreCase("")){
			ProformaView proforma = proformaService.leggi(Long.parseLong(id));
			SchedaValutazione scheda = null;

			if(proforma.getVo().getSchedaValutazione()!=null)
				scheda = proforma.getVo().getSchedaValutazione();

			scheda.getReturnFile();

			String contentType = "application/pdf";
			String name = scheda.getReturnFileName();

			OutputStream os = null;
			try{

				response.setContentLength((int) scheda.getReturnFile().length());
				response.setContentType(contentType);
				response.setHeader("Content-Disposition", "attachment;filename=" + name);
				response.setHeader("Cache-control", "");

				os = response.getOutputStream();
				IOUtils.copy(scheda.getReturnFile().getBinaryStream(), os);

			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(os);
			}
		}
		return null;
	}

	@RequestMapping(value = "/proforma/uploadSchedaValutazioneFirmata", method = RequestMethod.POST)
	public String uploadSchedaValutazioneFirmata( HttpServletRequest request, Model model, @RequestParam("file") MultipartFile file, 
			@ModelAttribute(MODEL_VIEW_NOME) ProformaView proformaView ) {  
		
		try {   
			NumberUtils.toLong(request.getParameter("idProforma")); 

			File fileTmp = File.createTempFile("schedaValutazioneFirmata", "___" + file.getOriginalFilename() );
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true);
			proformaView.setSchedaValutazioneFirmataDoc(documentoView); 

		} catch (Throwable e) {
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, proformaView);
		return "proforma/schedaValutazione";
	}

	@RequestMapping(value = "/proforma/rimuoviSchedaValutazioneFirmata", method = RequestMethod.POST)
	public String rimuoviProcura( HttpServletRequest request, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) ProformaView proformaView ) {  
		try {   
			NumberUtils.toLong(request.getParameter("idProforma")); 

			proformaView.setSchedaValutazioneFirmataDoc(null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, proformaView);
		return "proforma/schedaValutazione";
	}
}
