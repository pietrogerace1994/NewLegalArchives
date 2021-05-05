package eng.la.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;

//import com.filenet.apiimpl.core.EngineObjectImpl;
import eng.la.business.*;
import eng.la.persistence.*;
import it.snam.ned.libs.dds.dtos.v2.Document;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

//import com.filenet.api.collection.DocumentSet;
//import com.filenet.api.collection.PageIterator;
//@@DDS import com.filenet.apiimpl.core.DocumentImpl;
//@@DDS import com.filenet.apiimpl.core.EngineObjectImpl;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;


import eng.la.business.workflow.ConfigurazioneService;
import eng.la.model.Bonus;
import eng.la.model.Controparte;
import eng.la.model.Fascicolo;
import eng.la.model.Proforma;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.RIncaricoProformaSocieta;
import eng.la.model.Societa;
import eng.la.model.view.BaseView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.ProformaView;
import eng.la.model.view.SchedaValutazioneView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.StatoProformaView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.audit.AuditInterceptor;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("proformaDettaglioController")
@SessionAttributes("proformaDettaglioView")
public class ProformaDettaglioController extends BaseController {
	private static final String MODEL_VIEW_NOME = "proformaDettaglioView";
	private static final String PAGINA_DETTAGLIO_PATH = "proforma/formProformaDettaglio";
	private static final Logger logger = Logger.getLogger(ProformaDettaglioController.class);
	@Autowired
	private FascicoloService fascicoloService;
	
	@Autowired
	private ConfigurazioneService configurazioneService;

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
	
	private void caricaDocumentiGenericiFilenet(ProformaView view, Proforma vo, Fascicolo fascicolo) throws Throwable {
		String nomeCartellaProforma = FileNetUtil.getProformaCartella(vo.getId(), fascicolo.getDataCreazione(),
				fascicolo.getNome(), vo.getNomeProforma());
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaProforma)
				: documentaleDAO.leggiCartella(nomeCartellaProforma);

		DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();
		 */
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleDdsCryptDAO.leggiCartella(nomeCartellaProforma)
				: documentaleDdsDAO.leggiCartella(nomeCartellaProforma);

		logger.debug("@@DDS caricaDocumentiGenericiFilenet TODO");
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

		}

		 FINE*/
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

		}
	}
	
	private void caricaSchedaValutazioneFirmata(ProformaView view, Proforma vo, Fascicolo fascicolo) throws Throwable {
		String nomeCartellaProforma = FileNetUtil.getProformaCartella(vo.getId(), fascicolo.getDataCreazione(),
				fascicolo.getNome(), vo.getNomeProforma());
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		/*
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
						
						String uuid = documento.get_Id().toString();
						uuid = uuid.replace("{", "");
						uuid = uuid.replace("}", "");
						uuid = uuid.toLowerCase();
						
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

						String uuid = documento.getId();
						uuid = uuid.replace("{", "");
						uuid = uuid.replace("}", "");

						docView.setUuid(uuid);
						schedaValutazioneFirmata = docView;
					}
				}


			view.setSchedaValutazioneFirmataDoc(schedaValutazioneFirmata);
		}
	}
	

	@RequestMapping("/proforma/dettaglio")
	public String dettaglioProforma(@RequestParam("id") long id, Model model, Locale locale,
			HttpServletRequest request) {
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		
		ProformaView view = new ProformaView();

		try {
			ProformaView proformaSalvato =null;// proformaService.leggi(id);
			if(request.getParameter("stampa")!=null)
				proformaSalvato = proformaService.leggi(id);
			else
				proformaSalvato = proformaService.leggiConPermessi(id);	
			
			if (proformaSalvato != null && proformaSalvato.getVo() != null) {
				view.setVo(proformaSalvato.getVo());
				popolaFormDaVoPerDettaglio(view, proformaSalvato.getVo(), locale);
				
				super.caricaListePerDettaglio(view, locale);

				AuditInterceptor auditInterceptor = (AuditInterceptor) SpringUtil.getBean("auditInterceptor"); 
				auditInterceptor.auditRead(proformaSalvato.getVo());
			} else {
				model.addAttribute("errorMessage", "errore.generico");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}
		
		String open_Pagina=PAGINA_DETTAGLIO_PATH;
		if(request.getParameter("stampa")!=null){
			open_Pagina="proforma/formProformaDettaglioStampa";
		}
		
		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : open_Pagina;
	}

	private void popolaFormDaVoPerDettaglio(ProformaView view, Proforma vo, Locale locale) throws Throwable {
		view.setProformaId(vo.getId());
		view.setNumero(vo.getNumero()!=null?vo.getNumero():"");
		if (vo.getRIncaricoProformaSocietas() != null && vo.getRIncaricoProformaSocietas().size() > 0) {
			RIncaricoProformaSocieta rIncaricoProformaSocieta = vo.getRIncaricoProformaSocietas().iterator().next();
			IncaricoView incarico = incaricoService.leggi(rIncaricoProformaSocieta.getIncarico().getId());
			view.setIncaricoId(incarico.getVo().getId());
			view.setIncaricoRiferimento(incarico);
			view.setFascicoloId(incarico.getVo().getFascicolo().getId());
			FascicoloView fascicoloView = fascicoloService.leggi(incarico.getVo().getFascicolo().getId(), FetchMode.JOIN);
			view.setFascicoloRiferimento(fascicoloView);
			boolean isPenale = fascicoloView.getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
			view.setPenale(isPenale);
			Societa societa = rIncaricoProformaSocieta.getSocieta();
			view.setSocietaAddebitoScelta(societa.getId() + "");
			view.setLegaleInterno(fascicoloView.getVo().getLegaleInterno());
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

			
			if (fascicoloView.getVo().getRFascicoloSocietas() != null) {
				Collection<RFascicoloSocieta> listaFascicoloSocieta = fascicoloView.getVo().getRFascicoloSocietas();
				List<SocietaView> societaAddebitoAggiunte = new ArrayList<SocietaView>();

				for (RFascicoloSocieta soc  : listaFascicoloSocieta) {
					
					if (soc.getTipologiaSocieta().equals(SOCIETA_TIPOLOGIA_ADDEBITO) && soc.getSocieta().getId() == societa.getId() ) {
						SocietaView societaView = new SocietaView();
						societaView.setVo(soc.getSocieta());
						societaAddebitoAggiunte.add(societaView);
						break;
					}

				}

				view.setListaSocietaAddebitoAggiunte(societaAddebitoAggiunte);
			}
			
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
			}
			
			schedaValutazioneView.setValoreincarico(valoreIncarico);
			
			schedaValutazioneView.setParcellan(view.getNumero());
			
			schedaValutazioneView.setDataemissione(DateUtil.formattaData(vo.getDataInserimento().getTime()));
			
			schedaValutazioneView.setAvvocatostudio(professionistaEsternoView.getVo().getCognomeNome()+" / "+professionistaEsternoView.getVo().getStudioLegale().getDenominazione());
			
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
				
					schedaValutazioneView.setValoreIncarico(vo.getSchedaValutazione().getValoreIncarico());
				}
				
				schedaValutazioneView.setDataScheda(DateUtil.formattaData(vo.getSchedaValutazione().getDataCreazione().getTime()));
				view.setTimeout(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_POLLING_FILE_SCHEDA_VALUTAZIONE).getVo().getCdValue());
				
			}
			else{
				schedaValutazioneView.setDataScheda(DateUtil.formattaData(new Date().getTime()));
			}
			
			schedaValutazioneView.setVo(vo.getSchedaValutazione());
			view.setSchedaValutazione(schedaValutazioneView);
			
			//SCHEDA VALUTAZIONE//
			caricaDocumentiGenericiFilenet(view, vo, fascicoloView.getVo());
			caricaSchedaValutazioneFirmata(view, vo, fascicoloView.getVo());
		}

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
		view.setCentroDiCosto(vo.getCentroDiCosto());
		view.setVoceDiConto(vo.getVoceDiConto());
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
		ProformaView proformaView = (ProformaView) view;
		proformaView.setListaTipoValuta(anagraficaStatiTipiService.leggiTipoValuta(true));

	}

}
