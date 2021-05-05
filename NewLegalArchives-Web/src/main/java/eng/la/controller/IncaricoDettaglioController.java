package eng.la.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.FascicoloService;
import eng.la.business.IncaricoService;
import eng.la.business.ProcuratoreService;
import eng.la.business.ProcureService;
import eng.la.business.ProfessionistaEsternoService;
import eng.la.business.UtenteService;
import eng.la.business.workflow.ConfigurazioneService;
import eng.la.model.Acconti;
import eng.la.model.Bonus;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
import eng.la.model.LetteraIncarico;
import eng.la.model.NotaPropIncarico;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.Utente;
import eng.la.model.view.BaseView;
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
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.DocumentaleCryptDAO;
import eng.la.persistence.DocumentaleDAO;
import eng.la.persistence.DocumentaleDdsCryptDAO;
import eng.la.persistence.DocumentaleDdsDAO;
import eng.la.persistence.audit.AuditInterceptor;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("incaricoDettaglioController")
@SessionAttributes("incaricoDettaglioView")
public class IncaricoDettaglioController extends BaseController {
	private static final Logger logger = Logger.getLogger(IncaricoDettaglioController.class);
	private static final String MODEL_VIEW_NOME = "incaricoDettaglioView"; 
	private static final String PAGINA_DETTAGLIO_PATH = "incarico/formIncaricoDettaglio";    
	
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
	private ProcureService procureService;

	
	@RequestMapping("/incarico/dettaglio")
	public String dettaglioIncarico(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		IncaricoView view = new IncaricoView();
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		try {
			IncaricoView incaricoSalvato = (IncaricoView) incaricoService.leggi(id);
			if (incaricoSalvato != null && incaricoSalvato.getVo() != null) {
				popolaFormDaVoPerDettaglio(view, incaricoSalvato.getVo(), locale);
				
				super.caricaListePerDettaglio(view, locale);
				
				caricaListeProcure(view, incaricoSalvato.getVo());
				
				if(incaricoSalvato.getVo().getFascicolo().getTipologiaFascicolo().getCodGruppoLingua().equals(Costanti.TIPOLOGIA_FASCICOLO_NOTARILE_COD ))
					caricaListaProfessionisti(view, locale, Costanti.TIPOLOGIA_PROFESSIONISTA_NOTAIO_COD);
				else
					caricaListaProfessionisti(view, locale, Costanti.TIPOLOGIA_PROFESSIONISTA_LEGALE_ESTERNO_COD);
				

				AuditInterceptor auditInterceptor = (AuditInterceptor) SpringUtil.getBean("auditInterceptor"); 
				auditInterceptor.auditRead(incaricoSalvato.getVo());
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
	
	@RequestMapping("/incarico/dettaglioAll")
	public String dettaglioIncaricoAll(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		IncaricoView view = new IncaricoView();
		// engsecurity VA
				//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				//htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			IncaricoView incaricoSalvato = (IncaricoView) incaricoService.leggiTutti(id);
			if (incaricoSalvato != null && incaricoSalvato.getVo() != null) {
				popolaFormDaVoPerDettaglio(view, incaricoSalvato.getVo(), locale);
				
				super.caricaListePerDettaglio(view, locale);
				
				caricaListeProcure(view, incaricoSalvato.getVo());
				
				if(incaricoSalvato.getVo().getFascicolo().getTipologiaFascicolo().getCodGruppoLingua().equals(Costanti.TIPOLOGIA_FASCICOLO_NOTARILE_COD ))
					caricaListaProfessionisti(view, locale, Costanti.TIPOLOGIA_PROFESSIONISTA_NOTAIO_COD);
				else
					caricaListaProfessionisti(view, locale, Costanti.TIPOLOGIA_PROFESSIONISTA_LEGALE_ESTERNO_COD);
				

				AuditInterceptor auditInterceptor = (AuditInterceptor) SpringUtil.getBean("auditInterceptor"); 
				auditInterceptor.auditRead(incaricoSalvato.getVo());
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
	 
	private void popolaFormDaVoPerDettaglio(IncaricoView view, Incarico vo, Locale locale) throws Throwable { 
		String allegato = "";
		view.setIncaricoId(vo.getId());		
		view.setNomeIncarico(vo.getNomeIncarico());
		view.setCommento(vo.getCommento());
		if( vo.getDataAutorizzazione() != null ){
			view.setDataAutorizzazione(DateUtil.formattaData(vo.getDataAutorizzazione().getTime()));	
		}
		if( vo.getDataRichiestaAutorIncarico() != null ){
			view.setDataRichiestaAutorizzazione(DateUtil.formattaData(vo.getDataRichiestaAutorIncarico().getTime()));	
		} 
		
		StatoIncaricoView statoIncaricoView = anagraficaStatiTipiService.leggiStatoIncarico(vo.getStatoIncarico().getCodGruppoLingua(), locale.getLanguage().toUpperCase());		
		view.setStatoIncarico(statoIncaricoView.getVo().getDescrizione());
		view.setStatoIncaricoCode(statoIncaricoView.getVo().getCodGruppoLingua());
		
		FascicoloView fascicoloView = fascicoloService.leggi(vo.getFascicolo().getId(), FetchMode.JOIN);
		view.setFascicoloRiferimento( fascicoloView );
		view.setNomeFascicolo(vo.getFascicolo().getNome());
		
		boolean isPenale = fascicoloView.getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		view.setPenale(isPenale);
		
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
			view.setListaSocietaAddebitoAggiunteDescLet(societaAddebitoAggiunteDescLet);
		}
		/*TODO: GESTIRE COLLEGGIO ARBITRALE*/
		//view.setCollegioArbitrale(vo.getCollegioArbitrale()); 
		
		if( vo.getLetteraIncarico() != null ){
			allegato="  Lettera di incarico ";
			LetteraIncarico letteraIncarico = vo.getLetteraIncarico();
			view.setLetteraIncaricoId(letteraIncarico.getId());
			
			
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
			if( letteraIncarico.getDescrizione() != null ){
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
				
			if(letteraIncarico.getAcconti().size()!=0){	
				
				Set<Acconti> acconti = letteraIncarico.getAcconti();
				
				Acconti toRemove = new Acconti(); 
				
				for (Iterator<Acconti> iterator = acconti.iterator(); iterator.hasNext();) {
					Acconti acconti2 = iterator.next();
					if(!iterator.hasNext())
						toRemove= acconti2;
				}
	
				/**
				 * Non serve rimuovere l'ultimo acconto perch� adesso viene filtrato
				 * nella jsp
				 * @author MASSIMO CARUSO
				 */
//				acconti.remove(toRemove);
				
				view.setSaldoAnno(toRemove.getAnno());				
				
				
				view.setAccontiIn(acconti);
				view.setSizeAcconti(letteraIncarico.getAcconti().size());
			}
			else{
				view.setSizeAcconti(0);
			}
			
			view.setTimeout(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_POLLING_FILE_LETTERA_INCARICO).getVo().getCdValue());
			
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

		if(  vo.getListaRiferimento() != null){
			DocumentoView doc = new DocumentoView();
			doc.setContentType(vo.getListaRiferimento().getDocumento().getContentType());
			doc.setNomeFile(vo.getListaRiferimento().getDocumento().getNomeFile());
			doc.setNuovoDocumento(false);
			doc.setVo(vo.getListaRiferimento().getDocumento());			
			view.setListeRiferimentoDoc(doc);
			allegato+= "," + "   Liste Riferimento  ";
			
		}

		if( vo.getProcura() != null ){
			DocumentoView doc = new DocumentoView();
			doc.setContentType(vo.getProcura().getDocumento().getContentType());
			doc.setNomeFile(vo.getProcura().getDocumento().getNomeFile());
			doc.setNuovoDocumento(false);
			doc.setVo(vo.getProcura().getDocumento());			
			view.setProcuraDoc(doc);
			allegato=allegato + ", " + "   Procura ";
		}

		//LETTERA INCARICO FIRMATA //
		
		if( vo.getLetteraIncarico()!= null && vo.getLetteraIncarico().getDocumento_firmato()!=null  ){
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
			allegato=allegato + ", " + "  Verifica anticorruzione  ";
		}

		if(  vo.getVerificaPartiCorrelate() != null){
			DocumentoView doc = new DocumentoView();
			doc.setContentType(vo.getVerificaPartiCorrelate().getDocumento().getContentType());
			doc.setNomeFile(vo.getVerificaPartiCorrelate().getDocumento().getNomeFile());
			doc.setNuovoDocumento(false);
			doc.setVo(vo.getVerificaPartiCorrelate().getDocumento());			
			view.setVerificaPartiCorrelateDoc(doc);		
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
				compensoBonus+= " + "+bonus.getImporto().toString()+" "+tipoValuta.getVo().getNome()+" : "+bonus.getDescrizione();
			}

			view.setValoreIncarico(compensoBonus);
		}
		
		
		if( vo.getNotaPropIncarico() != null ){ 
			NotaPropIncarico notaPropIncarico = vo.getNotaPropIncarico();
			
			view.setTimeoutNot(configurazioneService.leggiConfigurazione(CostantiDAO.KEY_POLLING_FILE_NOTA_PROPOSTA_INCARICO).getVo().getCdValue());
			
			view.setNotaIncaricoId(notaPropIncarico.getId());
			if( notaPropIncarico.getDataPropIncarico() != null){
				view.setDataNotaProposta(DateUtil.formattaData( notaPropIncarico.getDataPropIncarico().getTime()));
			}
			view.setDescrizione(notaPropIncarico.getDescrizione());
			view.setOggetto(notaPropIncarico.getOggetto());
			view.setPratica(notaPropIncarico.getPratica());
			view.setNomeForo(notaPropIncarico.getNomeForo());
			view.setInfoCompensoNotaProp(notaPropIncarico.getInfoCompensoNP()); 
			
			
				
				
			view.setSceltaInfo(notaPropIncarico.getSceltaInfo());
				
			if(view.getSceltaInfo()==0){
					view.setInfoHandBook("Si precisa che, con riferimento agli allegati all'handbook, non sono state apportate modifiche");
			}
			else{
				view.setSceltaInfo(notaPropIncarico.getSceltaInfo());
				view.setInfoHandBook(notaPropIncarico.getInfoHandBook());
			}
	
			
			view.setInfoCorresponsioneCompenso(notaPropIncarico.getInfoCorresponsione());
			view.setInfoHandBook(notaPropIncarico.getInfoHandBook());
			view.setProposta(notaPropIncarico.getProposta());

			Fascicolo fasc = vo.getFascicolo();
			
			Utente ownerFascicolo = utenteService.leggiUtenteDaUserId(fasc.getLegaleInterno()).getVo();
			
			String owner = ownerFascicolo.getDescrUnitaUtil()!=null?ownerFascicolo.getDescrUnitaUtil():""+
			" - "+ownerFascicolo.getNominativoUtil()!=null?ownerFascicolo.getNominativoUtil():"";
			
			view.setProponente(owner);
			
			if(ownerFascicolo.getNominativoRespUtil()!=null){
				Utente responsabile = ownerFascicolo = utenteService.leggiUtenteDaMatricola(ownerFascicolo.getMatricolaRespUtil()).getVo();
				
				if(responsabile != null){
					
					view.setResponsabili(responsabile.getDescrUnitaUtil()!=null?responsabile.getDescrUnitaUtil():""
							+" - "+responsabile.getNominativoUtil()!=null?responsabile.getNominativoUtil():"");
				}
			}

		    List<UtenteView> responsabili = utenteService.leggiResponsabili(utenteService.leggiUtenteDaUserId(fasc.getLegaleInterno()).getVo().getMatricolaUtil());   
		    if (responsabili!=null) {
				for (UtenteView utenteb : responsabili) {
					if (utenteService.leggiSePrimoRiporto(utenteb)) {
						view.setApprovatore(utenteb.getVo().getDescrUnitaUtil() != null ? utenteb.getVo().getDescrUnitaUtil() : ""
								+ " - " + utenteb.getVo().getNominativoUtil() != null ? utenteb.getVo().getNominativoUtil() : "");
						break;
					}
				}
			}
			view.setDataNotaProposta(DateUtil.formattaData( notaPropIncarico.getDataPropIncarico().getTime()));
			
			if( notaPropIncarico.getValoreIncarico() != null ){
				//view.setValoreIncarico(NumberUtils.toInt(notaPropIncarico.getValoreIncarico()));
				view.setValoreIncarico((notaPropIncarico.getValoreIncarico()));
			}
		} 

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
						
						String uuid = documento.get_Id().toString();
						uuid = uuid.replace("{", "");
						uuid = uuid.replace("}", "");
						uuid = uuid.toLowerCase();
						
						docView.setUuid(uuid);
						notaPropostaFirmata = docView;
					}
				}
			}
			view.setLetteraFirmataDocNota(notaPropostaFirmata);
		}
		*/
		List<Document> documenti =isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaIncarico)
				: documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaIncarico);
		DocumentoView notaPropostaFirmata = null;
		if (documenti != null) {


				for (Document documento:documenti) {

					if( documento.getDocumentalClass().equals(FileNetClassNames.NOTA_PROPOSTA_INCARICO_DOCUMENT)){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.getContents().get(0).getContentsName());

						String uuid = documento.getId();
						uuid = uuid.replace("{", "");
						uuid = uuid.replace("}", "");

						docView.setUuid(uuid);
						notaPropostaFirmata = docView;
					}

			}
			view.setLetteraFirmataDocNota(notaPropostaFirmata);
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
		Folder cartellaIncarico = isPenale ? documentaleDdsCryptDAO.leggiCartella(nomeCartellaIncarico)
				: documentaleDdsDAO.leggiCartella(nomeCartellaIncarico);
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
						
						String uuid = documento.get_Id().toString();
						uuid = uuid.replace("{", "");
						uuid = uuid.replace("}", "");
						uuid = uuid.toLowerCase();
						
						docView.setUuid(uuid);
						letteraIncaricoFirmata = docView;
					}
				}
			}
			view.setLetteraFirmataDoc(letteraIncaricoFirmata);
		}
		*/
		DocumentoView letteraIncaricoFirmata = null;
		List<Document> documenti =  isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaIncarico)
				: documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaIncarico);
		if (documenti != null) {

				for (Document documento:documenti) {

					if( documento.getDocumentalClass().equals(FileNetClassNames.LETTERA_INCARICO_DOCUMENT)){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.getContents().get(0).getContentsName());

						String uuid = documento.getId();
						uuid = uuid.replace("{", "");
						uuid = uuid.replace("}", "");


						docView.setUuid(uuid);
						letteraIncaricoFirmata = docView;
					}
				}

			view.setLetteraFirmataDoc(letteraIncaricoFirmata);
		}
	}
	 
	private void caricaDocumentiGenericiFilenet(IncaricoView view, Incarico vo) throws Throwable {		
		String nomeCartellaIncarico = FileNetUtil.getIncaricoCartella(vo.getId(), vo.getFascicolo().getDataCreazione(), vo.getFascicolo().getNome(), vo.getNomeIncarico());
		boolean isPenale = vo.getFascicolo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaIncarico) : documentaleDAO.leggiCartella(nomeCartellaIncarico);
		*/
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");


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
		List<Document> documenti =  isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaIncarico) : documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaIncarico);
		List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
		if( documenti != null ){


				for( Document documento:documenti ){

					if( documento.getDocumentalClass().equals(FileNetClassNames.ALLEGATO_DOCUMENT) ){
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.getContents().get(0).getContentsName() );
						docView.setUuid(documento.getId());
						logger.debug ("@@DDS ____________uuuid" + docView.getUuid());
						listaDocumenti.add(docView);
					}
				}

			view.setListaAllegatiGenerici(listaDocumenti);
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
  
	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		List<ProcuratoreView> listaProcuratori = procuratoreService.leggi(true);
		IncaricoView incaricoView = (IncaricoView) view;
		incaricoView.setListaProcuratore(listaProcuratori);
		incaricoView.setListaTipoValuta(anagraficaStatiTipiService.leggiTipoValuta(false));
	}
	
	public void caricaListaProfessionisti(BaseView view, Locale locale, String tipologiaProfessionista) throws Throwable {
		IncaricoView incaricoView = (IncaricoView) view;
		List<ProfessionistaEsternoView> listaProfessionistaEsternoViews = professionistaEsternoService.leggiProfessionistiPerCategoria(tipologiaProfessionista, true);
		incaricoView.setListaProfessionista(listaProfessionistaEsternoViews);
	}
	
	public void caricaListeProcure(BaseView view, Incarico vo) throws Throwable {
		IncaricoView incaricoView = (IncaricoView) view;
		long idFascicolo = vo.getFascicolo().getId();
		List<ProcureView> listaProcure = procureService.leggiDaFascicolo(idFascicolo);
		incaricoView.setListaProcure(listaProcure);
	}

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		// TODO Auto-generated method stub
		
	}
 
}
