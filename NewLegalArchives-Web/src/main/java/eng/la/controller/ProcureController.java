package eng.la.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eng.la.persistence.DocumentaleDAO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
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
import it.snam.ned.libs.dds.dtos.v2.Document;
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;

import eng.la.business.AttoService;
import eng.la.business.LivelloAttribuzioniIIService;
import eng.la.business.LivelloAttribuzioniIService;
import eng.la.business.PosizioneOrganizzativaService;
import eng.la.business.ProcureService;
import eng.la.business.ProfessionistaEsternoService;
import eng.la.business.SocietaService;
import eng.la.business.TipoProcureService;
import eng.la.model.LivelloAttribuzioniI;
import eng.la.model.LivelloAttribuzioniII;
import eng.la.model.PosizioneOrganizzativa;
import eng.la.model.Procure;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.Societa;
import eng.la.model.TipoProcure;
import eng.la.model.aggregate.ProcureAggregate;
import eng.la.model.filter.ProcureFilter;
import eng.la.model.rest.ProcureRest;
import eng.la.model.rest.RicercaProcureRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.LivelloAttribuzioniIIView;
import eng.la.model.view.LivelloAttribuzioniIView;
import eng.la.model.view.PosizioneOrganizzativaView;
import eng.la.model.view.ProcureView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.TipoProcureView;
import eng.la.model.view.UtenteView;
//@@DDS import eng.la.persistence.DocumentaleDAO;
import eng.la.persistence.DocumentaleDdsDAO;
import eng.la.presentation.validator.ProcureValidator;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("procureController")
@SessionAttributes("procureView")
public class ProcureController extends BaseController {
	private static final Logger logger = Logger.getLogger(ProcureController.class);
	private static final String MODEL_VIEW_NOME = "procureView";
	private static final String PAGINA_CREAZIONE = "procure/formCreazioneProcure";
	private static final String PAGINA_RICERCA = "procure/ricercaProcure";
	private static final String PAGINA_AZIONI_PROCURE = "procure/azioniProcure";
//	private static final String PAGINA_MODIFICA_PROCURE = "procure/modificaProcure";
	private static final String PAGINA_FORM_PATH = "procure/modificaProcure";
	private static final String PAGINA_FORM_PATH2 = "procure/modificaSoloRevocaProcure";
	private static final String PAGINA_PERMESSI_PROCURE = "procure/permessiProcure";
//	private static final String PAGINA_POPUP_RICERCA_PROCURE = "procure/popupRicercaProcure";


	
	
	// messaggio di conferma
//	private static final String SUCCESS_MSG ="successMsg";
//	private static final int ELEMENTI_PER_PAGINA_PC = 100;
//	private static final String REMOTE_USER_PARAM_NAME = "REMOTE_USER";
	
	
	@Autowired
	private ProfessionistaEsternoService professionistaEsternoService;

	public void setProfessionistaEsternoService(ProfessionistaEsternoService professionistaEsternoService) {
		this.professionistaEsternoService = professionistaEsternoService;
	}
	
	@Autowired
	private ProcureService procureService;

	@Autowired
	private TipoProcureService tipoProcureService;
	
	@Autowired
	private PosizioneOrganizzativaService posizioneOrganizzarivaService;
	
	@Autowired
	private LivelloAttribuzioniIService livelloAttribuzioniIService;
	
	@Autowired
	private LivelloAttribuzioniIIService livelloAttribuzioniIIService;
	
	@Autowired
	private AttoService attoService;
	
	/**
	 * Carica form creazione procure
	 * @param model modello dati
	 * @param locale country locale
	 * @return page on
	 */
	@RequestMapping("/procure/crea")
	public String creazioneProcure(Locale locale, Model model, HttpServletRequest request) {
		ProcureView procureView = new ProcureView();
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			procureView.setLocale(locale);
			this.caricaListe(procureView, locale);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		String noRemakeView = request.getParameter("noRemakeView");
		if(noRemakeView == null){
			model.addAttribute(MODEL_VIEW_NOME, procureView);
		}
		
    	return PAGINA_CREAZIONE;
	}
	
	@RequestMapping(value = "/procure/salva", method = RequestMethod.POST)
	public String salvaProcure(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated ProcureView procureView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			String token=request.getParameter("CSRFToken");
			
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);


			if (procureView.getOp() != null && !procureView.getOp().equals("salvaProcure")) {
				String ritorno = invocaMetodoDaReflection(procureView, bindingResult, locale, model, request,
						response, this);

				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				if (procureView.getProcureId() == null || procureView.getProcureId() == 0) {
					return PAGINA_CREAZIONE;
				}
				return PAGINA_FORM_PATH;
			}

			preparaPerSalvataggio(procureView, bindingResult,utenteView);


			long procureId = 0;
			if (procureView.getProcureId() == null || procureView.getProcureId() == 0) {
				ProcureView procure = procureService.inserisci(procureView);
				procureId = procure.getVo().getId();
			} else {
				
				if(procureView.getVo().getFascicolo() != null){
					
					procureId = procureView.getVo().getId();
					
					if(procureView.getDataRevoca() !=null && !"".equals(procureView.getDataRevoca())){
						procureView.getVo().setFascicolo(null);
						ProcureView procure = procureService.inserisci(procureView);
						procureId = procure.getVo().getId();
					}
				}
				else{
					procureService.modifica(procureView);
					procureId = procureView.getVo().getId();
				}
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
			return "redirect:modifica.action?id=" + procureId+"&CSRFToken="+token;
			
		} catch (Throwable e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}
	
	private void preparaPerSalvataggio(ProcureView view, BindingResult bindingResult, UtenteView utenteView) throws Throwable {
		Procure vo = new Procure();
		
		if (view.getDataRevoca() !=null && !"".equals(view.getDataRevoca()) ) 
			vo.setDataRevoca(new SimpleDateFormat("dd/MM/yyyy").parse(view.getDataRevoca()));
		
		TipoProcure tp = new TipoProcure();
		tp.setId(new Long(view.getTipologia()));
		vo.setTipoProcure(tp);
		
		vo.setNomeProcuratore(view.getNomeProcuratore());
		
		vo.setNumeroRepertorio(view.getNumeroRepertorio());

		Societa s= new Societa();
		s.setId(view.getIdSocieta());
		vo.setSocieta(s);
		
		ProfessionistaEsterno pe = new ProfessionistaEsterno();
		pe.setId(view.getIdNotaio());
		vo.setNotaio(pe);
		
		vo.setUtente(view.getUtente());
		
		PosizioneOrganizzativa po = new PosizioneOrganizzativa();
		po.setId(new Long(view.getPosizioneOrganizzativa()));
		vo.setPosizioneOrganizzativa(po);
		
		LivelloAttribuzioniI laI = new LivelloAttribuzioniI();
		laI.setId(new Long(view.getLivelloAttribuzioniI()));
		vo.setLivelloAttribuzioniI(laI);
		
		LivelloAttribuzioniII laII = new LivelloAttribuzioniII();
		laII.setId(new Long(view.getLivelloAttribuzioniII()));
		vo.setLivelloAttribuzioniII(laII);

		if (view.getProcureId() != null) {
			ProcureView oldProcureView = procureService.leggi(view.getProcureId());
			vo.setId(view.getProcureId());
			vo.setDataConferimento(oldProcureView.getVo().getDataConferimento());
			vo.setLegaleInterno(oldProcureView.getVo().getLegaleInterno());
			if(oldProcureView.getVo().getFascicolo() != null){
				vo.setFascicolo(oldProcureView.getVo().getFascicolo());
			}

		}else{
			if (view.getDataConferimento() !=null && !"".equals(view.getDataConferimento()) ) 
				vo.setDataConferimento(new SimpleDateFormat("dd/MM/yyyy").parse(view.getDataConferimento()));
			vo.setLegaleInterno(utenteView.getVo().getUseridUtil());
		}
		view.setVo(vo);
	}



//	private void clearViewInsertFields(ProcureView procureView) {
//		procureView.setDataCreazione(null);
//		procureView.setDataRevoca(null);
//		procureView.setDataConferimento(null);
//		procureView.setNomeProcuratore(null);
//		procureView.setIdNotaio(null);
//		procureView.setIdSocieta(null);
//	}
	
	@Override	
	public void caricaListe(BaseView view, Locale locale) throws Throwable{
		SocietaService societaService = (SocietaService) SpringUtil.getBean("societaService");		
		List<SocietaView> listaSocieta = societaService.leggi(false);
		view.setListaSocieta(listaSocieta);
		caricaListeOggetti(view, locale);
	}
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		ProcureView procureView = (ProcureView) view;
		List<ProfessionistaEsternoView> listaProfessionistaEsternoViews = professionistaEsternoService.leggiProfessionistiPerCategoria(Costanti.TIPOLOGIA_PROFESSIONISTA_NOTAIO_COD, false);
		procureView.setListaProfessionista(listaProfessionistaEsternoViews);
		List<TipoProcureView> listaTipoProcure = tipoProcureService.leggi(locale);
		procureView.setListaTipologie(listaTipoProcure);
		List<PosizioneOrganizzativaView> listaPosizioneOrganizzativaView = posizioneOrganizzarivaService.leggi(locale);
		procureView.setListaPosizioneOrganizzativa(listaPosizioneOrganizzativaView);
		List<LivelloAttribuzioniIView> listaLivelloAttribuzioniIView = livelloAttribuzioniIService.leggi(locale);
		procureView.setListaLivelloAttribuzioniI(listaLivelloAttribuzioniIView);
		List<LivelloAttribuzioniIIView> listaLivelloAttribuzioniIIView = livelloAttribuzioniIIService.leggi(locale);
		procureView.setListaLivelloAttribuzioniII(listaLivelloAttribuzioniIIView);
	}
	
	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
	
//	private void makeVoForSave(ProcureView procureView) {
//		 Procure vo = new Procure();
//		 try {
//			 vo.setDataConferimento(new SimpleDateFormat("dd/MM/yyyy").parse(procureView.getDataConferimento()));
//			 vo.setDataRevoca(new SimpleDateFormat("dd/MM/yyyy").parse(procureView.getDataRevoca()));
//		 } catch (ParseException e) { }
//		 
//		 vo.setNumeroRepertorio(procureView.getNumeroRepertorio());
//		 vo.setNomeProcuratore(procureView.getNomeProcuratore());
//		 
//		 procureView.setVo(vo);
//	}
	
	@RequestMapping(value = "/procure/ricerca")
	public String ricerca(ProcureView procureView, HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		if (procureView == null)
			procureView = new ProcureView();

		try {
			procureView.setLocale(locale);
			this.caricaListe(procureView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		model.addAttribute("procureView", procureView);
		return PAGINA_RICERCA;
	}
	
	
	@RequestMapping(value = "/procure/cerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaProcureRest cercaProcure(ProcureView procureView, HttpServletRequest request, Model model,
			Locale locale) throws Throwable {


		
		if (procureView == null){
			procureView = new ProcureView(); 
		}
		
		String lingua = locale.getLanguage().toUpperCase();
		int numElementiPerPagina = request.getParameter("limit") == null ? 108 : NumberUtils.toInt(request.getParameter("limit"));
		String offset = request.getParameter("offset");
		int numeroPagina = offset == null || offset.equals("0") ? 1 : (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;

		List<Procure> procureList = null;
		ProcureFilter filter = new ProcureFilter();
		filter.setNumeroPagina(numeroPagina);
		filter.setNumElementiPerPagina(numElementiPerPagina);
		
		String dataCreateDal = request.getParameter("dataConferimentoDal");
		if (dataCreateDal != null && !dataCreateDal.trim().equals("0") && !dataCreateDal.trim().equals("")) {
			filter.setDataConferimentoDal(dataCreateDal.trim().length() > 1 && DateUtil.isData(dataCreateDal) ? 
					DateUtil.toDate(dataCreateDal) : null);
		}
		String dataCreateAl = request.getParameter("dataConferimentoAl");
		if (dataCreateAl != null && !dataCreateAl.trim().equals("0") && !dataCreateAl.trim().equals("")) {
			filter.setDataConferimentoAl(dataCreateAl.trim().length() > 1 && DateUtil.isData(dataCreateAl) ? 
					DateUtil.toDate(dataCreateAl) : null);
		}
		
		String dataRevocaDal = request.getParameter("dataRevocaDal");
		if (dataRevocaDal != null && !dataRevocaDal.trim().equals("0") && !dataRevocaDal.trim().equals("")) {
			filter.setDataRevocaDal(dataRevocaDal.trim().length() > 1 && DateUtil.isData(dataRevocaDal) ? 
					DateUtil.toDate(dataRevocaDal) : null);
		}
		String dataCloseAl = request.getParameter("dataRevocaAl");
		if (dataCloseAl != null && !dataCloseAl.trim().equals("0") && !dataCloseAl.trim().equals("")) {
			filter.setDataRevocaAl(dataCloseAl.trim().length() > 1 && DateUtil.isData(dataCloseAl) ? 
					DateUtil.toDate(dataCloseAl) : null);
		}

		String nome = request.getParameter("nomeProcuratore");
		if (nome != null && !nome.trim().equals("0") && !nome.trim().equals("")) {
			filter.setNomeProcuratore(nome);
		}

		String numeroRepertorio = request.getParameter("numeroRepertorio");
		if (numeroRepertorio != null && !numeroRepertorio.trim().equals("0") && !numeroRepertorio.trim().equals("")) {
			filter.setNumeroRepertorio(numeroRepertorio);
		}
		
		String tipologia = request.getParameter("tipologia");
		if (tipologia != null && !tipologia.trim().equals("0") && !tipologia.trim().equals("")) {
			filter.setTipologia(new Long(tipologia));
		}

		String idSocieta = request.getParameter("idSocieta");
		if (idSocieta != null && !idSocieta.trim().equals("0") && !idSocieta.trim().equals("")) {
			filter.setIdSocieta(new Long(idSocieta));
		}
		
		String idNotaio = request.getParameter("idNotaio");
		if (idNotaio != null && !idNotaio.trim().equals("0") && !idNotaio.trim().equals("")) {
			filter.setIdNotaio(new Long(idNotaio));
		}

		String order = request.getParameter("order");
		if (order != null) {
			filter.setOrder(!order.trim().equals("") ? order : "desc");
		}
		
		String utente = request.getParameter("utente");
		if (utente != null && !utente.trim().equals("0") && !utente.trim().equals("")) {
			filter.setUtente(utente);
		}
		
		String idPosizioneOrganizzativa = request.getParameter("idPosizioneOrganizzativa");
		if (idPosizioneOrganizzativa != null && !idPosizioneOrganizzativa.trim().equals("0") && !idPosizioneOrganizzativa.trim().equals("")) {
			filter.setPosizioneOrganizzativa(new Long(idPosizioneOrganizzativa));
		}
		
		String idLivelloAttribuzioniI = request.getParameter("idLivelloAttribuzioniI");
		if (idLivelloAttribuzioniI != null && !idLivelloAttribuzioniI.trim().equals("0") && !idLivelloAttribuzioniI.trim().equals("")) {
			filter.setLivelloAttribuzioniI(new Long(idLivelloAttribuzioniI));
		}
		
		String idLivelloAttribuzioniII = request.getParameter("idLivelloAttribuzioniII");
		if (idLivelloAttribuzioniII != null && !idLivelloAttribuzioniII.trim().equals("0") && !idLivelloAttribuzioniII.trim().equals("")) {
			filter.setLivelloAttribuzioniII(new Long(idLivelloAttribuzioniII));
		}
		
		try {
			ProcureAggregate aggregate = procureService.searchPagedProcure(filter);
			procureList = aggregate != null ? aggregate.getList() : null;
			RicercaProcureRest ricercaProcureRest = new RicercaProcureRest();
			ricercaProcureRest.setTotal(aggregate != null ? aggregate.getNumeroTotaleElementi() : 0);
			ricercaProcureRest.setRows(convertProcureToRest(procureList, lingua));
			
			return ricercaProcureRest;

		} catch (Exception e) {
			return new RicercaProcureRest();
		}

	}
	
	
	private List<ProcureRest> convertProcureToRest(List<Procure> list, String lingua) throws Throwable {
		List<ProcureRest> out = new ArrayList<ProcureRest>();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		for (Procure procure : list) {
			ProcureRest procureRest = new ProcureRest();
			
			procureRest.setDataConferimento(new SimpleDateFormat("dd/MM/yyyy").format(procure.getDataConferimento()));
			procureRest.setDataRevoca(procure.getDataRevoca()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(procure.getDataRevoca()));
			procureRest.setId(procure.getId());
			procureRest.setNomeProcuratore(procure.getNomeProcuratore());
			procureRest.setNumeroRepertorio(procure.getNumeroRepertorio());
			procureRest.setTipologia(procure.getTipoProcure().getDescrizione());
			procureRest.setIdSocieta(procure.getSocieta().getRagioneSociale());
			procureRest.setIdNotaio(procure.getNotaio().getCognomeNome());
			procureRest.setIdFascicolo(procure.getFascicolo()==null?"":procure.getFascicolo().getNome());
			procureRest.setUtente(procure.getUtente());
			procureRest.setPosizioneOrganizzativa(procure.getPosizioneOrganizzativa()==null?"":procure.getPosizioneOrganizzativa().getDescrizione());
			procureRest.setLivelloAttribuzioneI(procure.getLivelloAttribuzioniI()==null?"":procure.getLivelloAttribuzioniI().getDescrizione());
			procureRest.setLivelloAttribuzioneII(procure.getLivelloAttribuzioniII()==null?"":procure.getLivelloAttribuzioniII().getDescrizione());
			boolean checkRs = false;
			if (procure.getSocieta().getDataCancellazione() != null) {
				String tempData = df.format(procure.getSocieta().getDataCancellazione());
				if (tempData.equalsIgnoreCase("31/12/2999")) {
					checkRs = true;
				}
			}
			String titleAlt = "-";
			procureRest.setAzioni("<p id='action-procure-" + procure.getId() + "' alt='" + titleAlt + "' title='" + titleAlt + "'></p>");
			if (procureRest.getIdFascicolo().isEmpty()) {
				if (checkRs) {
					procureRest.setCheck("<div><input data-procure='"+procure.getId()+"' data-societa='"+procure.getSocieta().getId()+"' data-notaio='"+procure.getNotaio().getId()+"' type='checkbox' id='action-check-"+ procure.getId() +"' value=" + procure.getId() + " onchange='addListaProcure(this)'  disabled></div>");					
				} else {
					procureRest.setCheck("<div><input data-procure='"+procure.getId()+"' data-societa='"+procure.getSocieta().getId()+"' data-notaio='"+procure.getNotaio().getId()+"' type='checkbox' id='action-check-"+ procure.getId() +"' value=" + procure.getId() + " onchange='addListaProcure(this)' ></div>");					
				}
			}else {
				procureRest.setCheck("<div><input data-procure='"+procure.getId()+"' data-societa='"+procure.getSocieta().getId()+"' data-notaio='"+procure.getNotaio().getId()+"' type='checkbox' id='action-check-"+ procure.getId() +"' value=" + procure.getId() + " onchange='addListaProcure(this)' checked disabled></div>");
			}
			out.add(procureRest);
		}
		
		return out;
	}
	
	@RequestMapping(value = "/procure/caricaAzioniProcure", method = RequestMethod.POST)
	public String caricaAzioniProcure(@RequestParam("idProcure") long idProcure,@RequestParam("nomeProcure") String nomeProcure, HttpServletRequest req, Locale locale) {

		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(req);
		        //removeCSRFToken(request);
//		try {
//			UtenteView utenteView = (UtenteView) req.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
//			if (utenteView != null && utenteView.isAmministratore()) {
//				req.setAttribute("amministratore", "yes");
//			} else {
//				req.setAttribute("amministratore", "not");
//			}
//
//			AutorizzazioneView autorizzazioneView = null;
//
//			if (autorizzazioneView != null && autorizzazioneView.getVo() != null
//					&& autorizzazioneView.getVo().getTipoAutorizzazione() != null				
//					&& autorizzazioneView.getVo().getTipoAutorizzazione().getCodGruppoLingua().contains("W")) {  //leg_arc
//				req.setAttribute("modifica", "yes");
//			} else {
//				req.setAttribute("modifica", "not");
//			}
			
			req.setAttribute("modifica", "yes");
			req.setAttribute("idProcure", idProcure);
			req.setAttribute("nomeProcure", nomeProcure);

//
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
		return PAGINA_AZIONI_PROCURE;
	}
	
	
	@RequestMapping(value="/procure/eliminaProcure", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaProcure(@RequestParam("id") long id) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		try {
			ProcureView view = new ProcureView();
			ProcureView procureSalvato = (ProcureView) procureService.leggi(id);
			if (procureSalvato != null && procureSalvato.getVo() != null) {
				popolaFormDaVo(view, procureSalvato.getVo());
				procureSalvato.getVo().setDataCancellazione(new Date());
				
				List<DocumentoView> daRimuovere = view.getListaAllegatiGenerici();
				Set<String> setDaRimuovere = new HashSet<>();
				if (daRimuovere!=null && !daRimuovere.isEmpty()){
					for (Iterator<DocumentoView> iterator = daRimuovere.iterator(); iterator.hasNext();) {
						DocumentoView documentoView = iterator.next();
						setDaRimuovere.add(documentoView.getUuid());
					}
				}
				view.setAllegatiDaRimuovereUuid(setDaRimuovere);
				view.setListaAllegatiGenerici(null);
				view.setVo(procureSalvato.getVo());
				procureService.modifica(view);
				
			} 
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	
	@RequestMapping(value = "/procure/estendiPermessiProcure", method = RequestMethod.POST, produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest  estendiPermessiProcure( HttpServletRequest request  ) { 
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {   
			String id = request.getParameter("idProcure");
			String[] permessiScritturaArray = request.getParameterValues("permessoScrittura");
			String[] permessiLetturaArray = request.getParameterValues("permessoLettura");  
			
			if( id == null ){
				throw new RuntimeException("ProcureId non possono essere null");
			}
			Long procureId = NumberUtils.toLong(id);  
			procureService.salvaPermessiProcure(procureId, permessiScritturaArray, permessiLetturaArray);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			try {
//				UtenteView utenteConnesso = (UtenteView) request.getSession()
//						.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
//				NotificaWebView notificaWeb = new NotificaWebView();
//				ProcureView prog = procureService.leggi(procureId);				
//				NotificaWeb notifica = new NotificaWeb();
//				notifica.setDataNotifica(new Date());
//				notifica.setKeyMessage("procure.permessi.estesi");
//				notifica.setJsonParam(prog.getVo().getNome());
//				notifica.setMatricolaMitt(utenteConnesso.getVo());
//				notificaWeb.setVo(notifica);
//				notificaWebService.inserisciEstensionePermessi(notificaWeb,permessiScritturaArray);
			} catch (Exception e) {
				System.out.println("Errore in invio notifica" + e);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}
	
	
	@RequestMapping(value = "/procure/associaProcureAFascicolo", method = RequestMethod.POST, produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest  associaProcureAFascicolo( HttpServletRequest request  ) { 
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {   
			Long procureId = NumberUtils.toLong(request.getParameter("idProcure"));
			Long fascicoloId = NumberUtils.toLong(request.getParameter("idFascicolo"));
			
			procureService.associaProcureAFascicolo(procureId, fascicoloId);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			
		} catch (Throwable e) {
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		}
		return risultato;
	}

	
	private void popolaFormDaVo(ProcureView view, Procure vo) throws Throwable {
		view.setDataConferimento(DateUtil.formattaData(vo.getDataConferimento().getTime()));
		view.setDataRevoca(vo.getDataRevoca()==null?null:DateUtil.formattaData(vo.getDataRevoca().getTime()));
		view.setNomeProcuratore(vo.getNomeProcuratore());
		view.setNumeroRepertorio(vo.getNumeroRepertorio());
		view.setIdNotaio(vo.getNotaio().getId());
		view.setIdSocieta(vo.getSocieta().getId());
		view.setProcureId(vo.getId());
		view.setTipologia(vo.getTipoProcure().getId()+"");
		view.setUtente(vo.getUtente());
		view.setPosizioneOrganizzativa(vo.getPosizioneOrganizzativa() == null?"":vo.getPosizioneOrganizzativa().getId()+"");
		view.setLivelloAttribuzioniI(vo.getLivelloAttribuzioniI() == null?"":vo.getLivelloAttribuzioniI().getId()+"");
		view.setLivelloAttribuzioniII(vo.getLivelloAttribuzioniII() == null?"":vo.getLivelloAttribuzioniII().getId()+"");

		caricaDocumentiGenericiFilenet(view, vo);
	}

	private void caricaDocumentiGenericiFilenet(ProcureView view, Procure vo) {

		//@@DDS DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		String parentFolderName = FileNetUtil.getProcureParentFolder(vo.getDataConferimento());
		String procureFolderName = vo.getId() + "-PROCURE";
		procureFolderName = parentFolderName + procureFolderName + "/";
		logger.debug("@@DDS --------------------- ProcuraController caricaDocumentiGenericiFilenet + docId" + procureFolderName);
		Folder procureFolder = null;
		try {
			//@@DDS procureFolder = documentaleDAO.leggiCartella(procureFolderName);
			procureFolder = documentaleDdsDAO.leggiCartella(procureFolderName);
			if (procureFolder != null) {
			/*@@DDS inizio commento
			DocumentSet documenti = procureFolder.get_ContainedDocuments();
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
				view.setListaAllegatiGenerici(listaDocumenti);
			}
			*/

				List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
				List<Document> documenti = documentaleDdsDAO.leggiDocumentiCartella(procureFolderName);
				if (documenti != null) {

						for (Document documento:documenti) {
							logger.debug("@@DDS --------------------- ProcuraController caricaDocumentiGenericiFilenet + docId" + procureFolderName);
							DocumentoView docView = new DocumentoView();
							docView.setNomeFile(documento.getContents().get(0).getContentsName());
							docView.setUuid(documento.getId());
							listaDocumenti.add(docView);
						}

					view.setListaAllegatiGenerici(listaDocumenti);
				}
			}
		} catch (Throwable e) {
			// potrebbe essere corretto che la cartella non esista poiche' in inserimento gli allegati non sono obbligatori
			e.printStackTrace();
		}

	}
	
	
	@RequestMapping(value= "/procure/modifica",method = RequestMethod.GET)
	public String modificaProcure(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		ProcureView view = new ProcureView();
		String pageRedirectAction = PAGINA_FORM_PATH;
		// engsecurity VA - redirect
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {
			ProcureView procureSalvato = (ProcureView) procureService.leggi(id);
			if (procureSalvato != null && procureSalvato.getVo() != null) {
				popolaFormDaVo(view, procureSalvato.getVo());
				
				this.caricaListe(view, locale);
				
				if(procureSalvato.getVo().getFascicolo() != null){
					pageRedirectAction = PAGINA_FORM_PATH2;
				}
			} else {
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action": pageRedirectAction;
	}
	
	@RequestMapping(value= "/procure/visualizza",method = RequestMethod.GET)
	public String visualizzaProcure(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		
		// engsecurity VA
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		request.setAttribute("disableInVis", "true");
		return modificaProcure(id,model,locale,request);
	}
	
	@RequestMapping(value = "/procure/uploadAllegatoGenerico", method = RequestMethod.POST)
	public String uploadAllegatoGenerico( HttpServletRequest request, @RequestParam("file") MultipartFile file, @ModelAttribute(MODEL_VIEW_NOME) ProcureView view ) {  
		
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {   
			String procureId = request.getParameter("idProcure"); 
		    if( procureId == null  ){
				throw new RuntimeException("incaricoId non possono essere null");
			}
		    
		    File fileTmp = File.createTempFile("allegatoGenerico", "___" + file.getOriginalFilename() );
		    FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
		    DocumentoView documentoView = new DocumentoView();
		    
		    documentoView.setUuid("" +( view.getListaAllegatiGenerici() == null || view.getListaAllegatiGenerici().size() == 0 ?  1 : view.getListaAllegatiGenerici().size()+1));
		    documentoView.setFile(fileTmp);
		    documentoView.setNomeFile(file.getOriginalFilename());
		    documentoView.setContentType(file.getContentType());
		    documentoView.setNuovoDocumento(true);
		    List<DocumentoView> allegatiGenerici = view.getListaAllegatiGenerici()==null?new ArrayList<DocumentoView>():view.getListaAllegatiGenerici();
		    allegatiGenerici.add(documentoView);
		    view.setListaAllegatiGenerici(allegatiGenerici);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "procure/allegatiGenerici";
	}
	
	
	@RequestMapping(value = "/procure/rimuoviAllegatoGenerico", method = RequestMethod.POST)
	public String rimuoviAllegatoGenerico( HttpServletRequest request, @ModelAttribute(MODEL_VIEW_NOME) ProcureView view ) {  
		
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		try {   
			String uuid = request.getParameter("uuid"); 
		    if( uuid == null  ){
				throw new RuntimeException("uuid non pu� essere null");
			}
		    List<DocumentoView> allegatiGenericiOld = view.getListaAllegatiGenerici();
		    List<DocumentoView> allegatiGenerici = new ArrayList<DocumentoView>();
		    allegatiGenerici.addAll(allegatiGenericiOld);
		    Set<String> allegatiDaRimuovere = view.getAllegatiDaRimuovereUuid() == null ? new HashSet<String>():view.getAllegatiDaRimuovereUuid();
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
		    view.setListaAllegatiGenerici(allegatiGenerici);
		    view.setAllegatiDaRimuovereUuid(allegatiDaRimuovere);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "procure/allegatiGenerici";
	}
	
	@RequestMapping(value = "/procure/caricaGrigliaPermessiProcure", method = RequestMethod.POST)
	public String caricaGrigliaPermessiProcure(@RequestParam("id") long idProcure, HttpServletRequest req,
			Locale locale) {
		// engsecurity VA
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(req);
        //removeCSRFToken(request);
		try {
			// TODO: LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI
			// SUL Procure
			req.setAttribute("idProcure", idProcure);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return PAGINA_PERMESSI_PROCURE;
	}

	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ProcureValidator());
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	
	
}



