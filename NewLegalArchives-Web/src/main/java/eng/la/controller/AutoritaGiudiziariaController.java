package eng.la.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import eng.la.persistence.DocumentaleDdsDAO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

//@@DDS import com.filenet.api.core.Folder;

import eng.la.business.RichiestaAutoritaGiudiziariaService;
import eng.la.business.SocietaService;
import eng.la.business.StatoRichiestaAutGiudiziariaService;
import eng.la.business.TipoRichiestaAutGiudiziariaService;
import eng.la.model.Documento;
import eng.la.model.RichAutGiud;
import eng.la.model.Societa;
import eng.la.model.StatoRichAutGiud;
import eng.la.model.TipologiaRichiesta;
import eng.la.model.rest.AutoritaGiudiziariaRest;
import eng.la.model.view.AutoritaGiudiziariaView;
import eng.la.model.view.BaseView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.StatoRichAutGiudView;
import eng.la.model.view.TipologiaRichiestaView;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.DocumentaleDAO;
import eng.la.persistence.DocumentoDAO;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("autoritaGiudiziariaController")
@SessionAttributes("autoritaGiudiziariaView")
public class AutoritaGiudiziariaController extends BaseController {

	private static final String MODEL_VIEW_NOME = "autoritaGiudiziariaView";
	private static final String PAGINA_CREAZIONE = "autoritaGiudiziaria/formCreazioneAutoritaGiudiziaria";

	// messaggio di conferma
	//	private static final String SUCCESS_MSG ="successMsg";
	//	private static final int ELEMENTI_PER_PAGINA_PC = 100;
	//	private static final String REMOTE_USER__NAME = "REMOTE_USER";

/*@DDS	@Autowired
	private DocumentaleDAO documentaleDAO;
	public void setDocumentaleDAO(DocumentaleDAO documentaleDAO) {
		this.documentaleDAO = documentaleDAO;
	}
*/

	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;
	public void setDocumentaleDdsDAO(DocumentaleDdsDAO documentaleDdsDAO) {
		this.documentaleDdsDAO = documentaleDdsDAO;
	}

	@Autowired
	private DocumentoDAO documentoDAO;
	public void setDocumentoDAO(DocumentoDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	}


	@Autowired
	private RichiestaAutoritaGiudiziariaService richiestaAutoritaGiudiziariaService;
	public void setRichiestaAutoritaGiudiziariaService(RichiestaAutoritaGiudiziariaService richiestaAutoritaGiudiziariaService) {
		this.richiestaAutoritaGiudiziariaService = richiestaAutoritaGiudiziariaService;
	}

	@Autowired
	private TipoRichiestaAutGiudiziariaService tipoRichiestaAutGiudiziariaService;
	public void setTipoRichiestaAutGiudiziariaService(TipoRichiestaAutGiudiziariaService tipoRichiestaAutGiudiziariaService) {
		this.tipoRichiestaAutGiudiziariaService = tipoRichiestaAutGiudiziariaService;
	}

	@Autowired
	private StatoRichiestaAutGiudiziariaService statoRichiestaAutGiudiziariaService;
	public void setStatoRichiestaAutGiudiziariaService(StatoRichiestaAutGiudiziariaService statoRichiestaAutGiudiziariaService) {
		this.statoRichiestaAutGiudiziariaService = statoRichiestaAutGiudiziariaService;
	}

	@Autowired
	private	SocietaService societaService;
	public void setSocietaService(SocietaService societaService) {
		this.societaService = societaService;
	}



	/**
	 * Carica form creazione autorit� giudiziaria
	 * @ model modello dati
	 * @param locale country locale
	 * @return page on
	 */
	@RequestMapping("/autoritaGiudiziaria/creazioneAutoritaGiudiziaria")
	public String gestioneParteCorrelata(Locale locale, Model model, HttpServletRequest request) {

		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if(code.equals( CostantiDAO.GESTORE_ARCHIVIO_AUTORITA_GIUDIZIARIA) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			AutoritaGiudiziariaView autoritaGiudiziariaView = new AutoritaGiudiziariaView();
			// engsecurity VA - redirect
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);
			//String token=request.getParameter("CSRFToken");
			try {
				autoritaGiudiziariaView.setLocale(locale);
				this.caricaListeOggetti(autoritaGiudiziariaView, locale);
				convertAndSetAutoritaGiudiziariaToJsonArray(autoritaGiudiziariaView);
			} catch (Throwable e) {
				e.printStackTrace();
			}

			String noRemakeView = request.getParameter("noRemakeView");
			if(noRemakeView == null){
				model.addAttribute(MODEL_VIEW_NOME, autoritaGiudiziariaView);
			}

			return PAGINA_CREAZIONE;
		}
		return "redirect:/errore.action";	
	}


	/**
	 * Salvataggio dei dati della form nel dbase ( inserimento / modifica ).
	 * <p>
	 * @param autoritaGiudiziariaView
	 * @return
	 */
	@RequestMapping(value = "/autoritaGiudiziaria/salva", method=RequestMethod.POST)
	public String salvaAutoritaGiudiziaria(Locale locale, Model model, 
			@ModelAttribute(MODEL_VIEW_NOME) @Validated AutoritaGiudiziariaView autoritaGiudiziariaView,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) { 
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		String token=request.getParameter("CSRFToken");
		try{
			if( autoritaGiudiziariaView.getOp() != null && !autoritaGiudiziariaView.getOp().equals("salvaAutoritaGiudiziaria") ){
				String ritorno = invocaMetodoDaReflection(autoritaGiudiziariaView, bindingResult, locale, model, request, response, this);
				return ritorno == null ? PAGINA_CREAZIONE : ritorno;
			}

			if( bindingResult.hasErrors() ){	
				if (autoritaGiudiziariaView.isInsertMode()) {
					autoritaGiudiziariaView.setTabAttiva("1");
				} else if (autoritaGiudiziariaView.isEditMode() || autoritaGiudiziariaView.isDeleteMode()) { 
					autoritaGiudiziariaView.setTabAttiva("2");
				}
				return PAGINA_CREAZIONE;
			}

			if (autoritaGiudiziariaView.isInsertMode()) {
				System.out.println("####### ...esegue salvataggio... #######");
				makeVoForSave(autoritaGiudiziariaView);
				RichAutGiud richAutGiud = richiestaAutoritaGiudiziariaService.save(autoritaGiudiziariaView.getVo());

				MultipartFile file = autoritaGiudiziariaView.getFileRichiestaInfoRepartoLegale();
				if(file != null && richAutGiud != null && file.getOriginalFilename() != null && 
						!file.getOriginalFilename().isEmpty()){
					addFilenetRichAutGiudFolder(richAutGiud.getId(), richAutGiud.getDataInserimento());
					addRichAutGiudStepDocument(richAutGiud, file, 1);
				}

				clearViewInsertFields(autoritaGiudiziariaView);
				autoritaGiudiziariaView.setTabAttiva("1");

			} else if (autoritaGiudiziariaView.isEditMode()) {
				System.out.println("####### ...esegue modifica... #######");
				makeVoForUpdate(autoritaGiudiziariaView);
				RichAutGiud richAutGiud = richiestaAutoritaGiudiziariaService.update(autoritaGiudiziariaView.getVo());

				MultipartFile file = autoritaGiudiziariaView.getFileRichiestaInfoRepartoLegaleMod();
				if(file != null && richAutGiud != null && file.getOriginalFilename() != null && 
						!file.getOriginalFilename().isEmpty()){
					addFilenetRichAutGiudFolder(richAutGiud.getId(), richAutGiud.getDataInserimento());
					addRichAutGiudStepDocument(richAutGiud, file, 1);
				}

				MultipartFile fileStep2 = autoritaGiudiziariaView.getFileRichiestaInfoUnitaInterneMod();
				if(fileStep2 != null && richAutGiud != null && fileStep2.getOriginalFilename() != null && 
						!fileStep2.getOriginalFilename().isEmpty()){
					RichAutGiud richAutGiudSaved = addRichAutGiudStepDocument(richAutGiud, fileStep2, 2);
					StatoRichAutGiud statoRichAutGiud = statoRichiestaAutGiudiziariaService.readStatoRichAutGiudByFilter(locale, "ST_RIC_2");
					richAutGiudSaved.setStatoRichAutGiud(statoRichAutGiud);
					richiestaAutoritaGiudiziariaService.update(richAutGiudSaved);
				}

				MultipartFile fileStep3 = autoritaGiudiziariaView.getFileLetteraTrasmissioneMod();
				if(fileStep3 != null && richAutGiud != null && fileStep3.getOriginalFilename() != null && 
						!fileStep3.getOriginalFilename().isEmpty()){
					RichAutGiud richAutGiudSaved = addRichAutGiudStepDocument(richAutGiud, fileStep3, 3);
					StatoRichAutGiud statoRichAutGiud = statoRichiestaAutGiudiziariaService.readStatoRichAutGiudByFilter(locale, "ST_RIC_3");
					richAutGiudSaved.setStatoRichAutGiud(statoRichAutGiud);
					richiestaAutoritaGiudiziariaService.update(richAutGiudSaved);
				}

				//autoritaGiudiziariaView = richiestaAutoritaGiudiziariaService.readRichAutGiud(autoritaGiudiziariaView.getAutoritaGiudiziariaIdMod());
				// model.addAttribute("idAutGiud", autoritaGiudiziariaView.getAutoritaGiudiziariaIdMod());
				autoritaGiudiziariaView.setTabAttiva("2");

			} else if (autoritaGiudiziariaView.isDeleteMode()) {
				System.out.println("####### ...esegue cancellazione... #######");
				if(autoritaGiudiziariaView.getAutoritaGiudiziariaIdMod() != null)
					richiestaAutoritaGiudiziariaService.delete(autoritaGiudiziariaView.getAutoritaGiudiziariaIdMod());
				autoritaGiudiziariaView.setTabAttiva("2");
			}

			List<AutoritaGiudiziariaView> richAutGiudList = richiestaAutoritaGiudiziariaService.searchRichAutGiud();
			autoritaGiudiziariaView.setAutoritaGiudiziariaViewList(richAutGiudList);
			convertAndSetAutoritaGiudiziariaToJsonArray(autoritaGiudiziariaView);
			model.addAttribute("successMessage", "messaggio.operazione.ok");

			System.out.println("##### salvaParteCorrelata - end #####");

			return "redirect:creazioneAutoritaGiudiziaria.action?noRemakeView=true&CSRFToken="+token;

		}catch(Throwable e){
			e.printStackTrace();
			bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			return PAGINA_CREAZIONE;
		}
	}

	@RequestMapping(value = "/autoritaGiudiziaria/loadRichiestaAutGiudiziaria", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody AutoritaGiudiziariaRest loadRichiestaAutGiudiziaria(@RequestParam("id") Long id
			, HttpServletRequest request) {

		AutoritaGiudiziariaRest autoritaGiudiziariaRest = new AutoritaGiudiziariaRest();
		try {
			AutoritaGiudiziariaView autoritaGiudiziariaView = richiestaAutoritaGiudiziariaService.readRichAutGiud(id);
			if (autoritaGiudiziariaView != null) {
				autoritaGiudiziariaRest.setId(autoritaGiudiziariaView.getVo().getId());
				autoritaGiudiziariaRest.setAutoritaGiudiziaria(autoritaGiudiziariaView.getVo().getAutoritaGiudiziaria());
				autoritaGiudiziariaRest.setOggetto(autoritaGiudiziariaView.getVo().getOggetto());
				autoritaGiudiziariaRest.setFornitore(autoritaGiudiziariaView.getVo().getFornitore());

				Date dataInserimento = autoritaGiudiziariaView.getVo().getDataInserimento();
				Date dataRicezione = autoritaGiudiziariaView.getVo().getDataRicezione();

				if(dataInserimento != null){
					autoritaGiudiziariaRest.setDataInserimento(new SimpleDateFormat("dd/MM/yyyy").format(dataInserimento));
				}
				if(dataRicezione != null){
					autoritaGiudiziariaRest.setDataRicezione(new SimpleDateFormat("dd/MM/yyyy").format(dataRicezione));
				}

				if(autoritaGiudiziariaView.getVo().getStatoRichAutGiud() != null){
					autoritaGiudiziariaRest.setStatoRichAutGiudId(autoritaGiudiziariaView.getVo().getStatoRichAutGiud().getId());
				}
				if(autoritaGiudiziariaView.getVo().getTipologiaRichiesta() != null){
					autoritaGiudiziariaRest.setTipologiaRichiestaId(autoritaGiudiziariaView.getVo().getTipologiaRichiesta().getId());
				}
				if(autoritaGiudiziariaView.getVo().getDocumentoStep1() != null){
					autoritaGiudiziariaRest.setNomeFileStep1(autoritaGiudiziariaView.getVo().getDocumentoStep1().getNomeFile());
					autoritaGiudiziariaRest.setIdFileStep1(autoritaGiudiziariaView.getVo().getDocumentoStep1().getUuid());
				}
				if(autoritaGiudiziariaView.getVo().getDocumentoStep2() != null){
					autoritaGiudiziariaRest.setNomeFileStep2(autoritaGiudiziariaView.getVo().getDocumentoStep2().getNomeFile());
					autoritaGiudiziariaRest.setIdFileStep2(autoritaGiudiziariaView.getVo().getDocumentoStep2().getUuid());
				}
				if(autoritaGiudiziariaView.getVo().getDocumentoStep3() != null){
					autoritaGiudiziariaRest.setNomeFileStep3(autoritaGiudiziariaView.getVo().getDocumentoStep3().getNomeFile());
					autoritaGiudiziariaRest.setIdFileStep3(autoritaGiudiziariaView.getVo().getDocumentoStep3().getUuid());
				}
				if(autoritaGiudiziariaView.getVo().getStatoRichAutGiud() != null){
					autoritaGiudiziariaRest.setStatoCodGruppoLingua(autoritaGiudiziariaView.getVo().getStatoRichAutGiud().getCodGruppoLingua());
				}
				if(autoritaGiudiziariaView.getVo().getSocieta() != null){
					autoritaGiudiziariaRest.setIdSocieta(autoritaGiudiziariaView.getVo().getSocieta().getId());
				}

			}
		} catch (Throwable e) { e.printStackTrace(); }

		return autoritaGiudiziariaRest;
	}

	private void makeVoForSave(AutoritaGiudiziariaView autoritaGiudiziariaView) {
		RichAutGiud vo = new RichAutGiud();

		vo.setAutoritaGiudiziaria(autoritaGiudiziariaView.getAutoritaGiudiziaria());
		vo.setFornitore(autoritaGiudiziariaView.getFornitore());
		try {
			vo.setDataInserimento(new SimpleDateFormat("dd/MM/yyyy").parse(autoritaGiudiziariaView.getDataInserimento()));
			vo.setDataRicezione(new SimpleDateFormat("dd/MM/yyyy").parse(autoritaGiudiziariaView.getDataRicezione()));
		} catch (ParseException e) { }

		vo.setOggetto(autoritaGiudiziariaView.getOggetto());

		Societa societa = new Societa();
		societa.setId(autoritaGiudiziariaView.getIdSocieta());
		vo.setSocieta(societa);

		TipologiaRichiesta tipologiaRichiesta = new TipologiaRichiesta();
		tipologiaRichiesta.setId(autoritaGiudiziariaView.getTipologiaRichiestaCode());
		vo.setTipologiaRichiesta(tipologiaRichiesta);

		StatoRichAutGiud statoRichAutGiud = new StatoRichAutGiud();
		statoRichAutGiud.setId(autoritaGiudiziariaView.getStatoRichiestaCode());
		vo.setStatoRichAutGiud(statoRichAutGiud);

		autoritaGiudiziariaView.setVo(vo);
	}

	private void makeVoForUpdate(AutoritaGiudiziariaView autoritaGiudiziariaView) throws Throwable {
		RichAutGiud vo = richiestaAutoritaGiudiziariaService.readRichAutGiud(autoritaGiudiziariaView.getAutoritaGiudiziariaIdMod()).getVo();
		vo.setId(autoritaGiudiziariaView.getAutoritaGiudiziariaIdMod());
		vo.setAutoritaGiudiziaria(autoritaGiudiziariaView.getAutoritaGiudiziariaMod());
		vo.setFornitore(autoritaGiudiziariaView.getFornitoreMod());

		try {
			vo.setDataInserimento(new SimpleDateFormat("dd/MM/yyyy").parse(autoritaGiudiziariaView.getDataInserimentoMod()));
			vo.setDataRicezione(new SimpleDateFormat("dd/MM/yyyy").parse(autoritaGiudiziariaView.getDataRicezioneMod()));
		} catch (ParseException e) { }

		vo.setOggetto(autoritaGiudiziariaView.getOggettoMod());

		TipologiaRichiesta tipologiaRichiesta = new TipologiaRichiesta();
		tipologiaRichiesta.setId(autoritaGiudiziariaView.getTipologiaRichiestaCodeMod());
		vo.setTipologiaRichiesta(tipologiaRichiesta);

		StatoRichAutGiud statoRichAutGiud = new StatoRichAutGiud();
		statoRichAutGiud.setId(autoritaGiudiziariaView.getStatoRichiestaCodeMod());
		vo.setStatoRichAutGiud(statoRichAutGiud);

		Societa societa = new Societa();
		societa.setId(autoritaGiudiziariaView.getIdSocietaMod());
		vo.setSocieta(societa);

		autoritaGiudiziariaView.setVo(vo);
	}

	private void convertAndSetAutoritaGiudiziariaToJsonArray(AutoritaGiudiziariaView autoritaGiudiziariaView)  throws Throwable {
		List<AutoritaGiudiziariaView> autGiudList = autoritaGiudiziariaView.getAutoritaGiudiziariaViewList();
		JSONArray jsonArray = new JSONArray();
		if( autGiudList != null && autGiudList.size() > 0 ){
			for(AutoritaGiudiziariaView view : autGiudList){
				JSONObject jsonObject = new JSONObject();

				RichAutGiud vo = view.getVo();
				jsonObject.put("id", vo.getId());

				String denominazione = vo.getAutoritaGiudiziaria().replace("'"," ").replace("\"", " ");
				denominazione = denominazione.trim();
				denominazione = org.apache.commons.lang3.StringEscapeUtils.escapeJson(denominazione);

				jsonObject.put("denominazione", denominazione + " - "+ new SimpleDateFormat("dd/MM/yyyy").format(vo.getDataInserimento()));
				jsonArray.put(jsonObject); 
			}
		}

		if (jsonArray.length() > 0) {
			autoritaGiudiziariaView.setJsonArrayAutoritaGiudiziariaMod(jsonArray.toString());
		} 
		else { autoritaGiudiziariaView.setJsonArrayAutoritaGiudiziariaMod(null); };
	}

	private void clearViewInsertFields(AutoritaGiudiziariaView autoritaGiudiziariaView) {
		autoritaGiudiziariaView.setAutoritaGiudiziaria("");
		autoritaGiudiziariaView.setDataInserimento(null);
		autoritaGiudiziariaView.setDataRicezione(null);
		autoritaGiudiziariaView.setOggetto("");
		autoritaGiudiziariaView.setTipologiaRichiestaCode(null);
		autoritaGiudiziariaView.setStatoRichiestaCode(null);
		autoritaGiudiziariaView.setIdSocieta(null);
		autoritaGiudiziariaView.setFornitore(null);
	}

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		AutoritaGiudiziariaView autoritaGiudiziariaView = (AutoritaGiudiziariaView) view;

		List<SocietaView> societaList = societaService.leggi(false);
		autoritaGiudiziariaView.setSocietaList(societaList);

		List<TipologiaRichiestaView> tipologiaRichiestaViewList = tipoRichiestaAutGiudiziariaService.leggi(locale);
		autoritaGiudiziariaView.setTipologiaRichiestaList(tipologiaRichiestaViewList);

		List<StatoRichAutGiudView> statoRichAutGiudViewList = statoRichiestaAutGiudiziariaService.leggi(locale);
		autoritaGiudiziariaView.setStatoRichAutGiudList(statoRichAutGiudViewList);

		List<AutoritaGiudiziariaView> richAutGiudList = richiestaAutoritaGiudiziariaService.searchRichAutGiud();



		autoritaGiudiziariaView.setAutoritaGiudiziariaViewList(richAutGiudList);
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {

	}

	private void addFilenetRichAutGiudFolder(Long idRichAutGiud, Date dataInserimento) throws Throwable {
		if(idRichAutGiud != null && dataInserimento != null){
			String parentFolderName = FileNetUtil.getRichiestaAutGiudParentFolder(dataInserimento);
			String folderName = idRichAutGiud + "-RICHIESTA-AUTORITA-GIUDIZIARIA";
			String uuid = UUID.randomUUID().toString();
			String folderClassName = FileNetClassNames.RICHAUTORITAGIUD_FOLDER; 
			Map<String, Object> folderProperty = new HashMap<String,Object>();
			folderProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idRichAutGiud+""));

			//@@DDS Folder parentFolder = documentaleDAO.leggiCartella(parentFolderName);
			Folder parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			if(parentFolder == null){ 
				/*@@DDS documentaleDAO.verificaCreaPercorsoCartella(parentFolderName); // verifica e crea se non esiste ARCHIVI->2016->11
				parentFolder = documentaleDAO.leggiCartella(parentFolderName);
				 */
				documentaleDdsDAO.verificaCreaPercorsoCartella(parentFolderName); // verifica e crea se non esiste ARCHIVI->2016->11
				parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			}

			String folderRichGiudName = idRichAutGiud + "-RICHIESTA-AUTORITA-GIUDIZIARIA";
			folderRichGiudName = parentFolderName + folderRichGiudName + "/";
			//@@DDS Folder folderRichGiud = documentaleDAO.leggiCartella(folderRichGiudName);
			Folder folderRichGiud = documentaleDdsDAO.leggiCartella(folderRichGiudName);
			if(folderRichGiud == null){
				//@@DDS documentaleDAO.creaCartella(uuid, folderName, folderClassName, folderProperty, parentFolder);
				documentaleDdsDAO.creaCartella(uuid, folderName, folderClassName, folderProperty, parentFolder);
			}
		}
	}

	public RichAutGiud addRichAutGiudStepDocument(RichAutGiud richAutGiud, MultipartFile file, int step) throws Throwable {
		if(richAutGiud == null){
			throw new RuntimeException("Richiesta Autorita Giudiziaria non trovata");
		}

		String uuid = UUID.randomUUID().toString();
		Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.RICHAUTORITAGIUD_DOCUMENT, 
				file.getOriginalFilename(), file.getContentType());

		RichAutGiud richAutGiudSaved = null;
		if(step == 1){
			richAutGiudSaved = richiestaAutoritaGiudiziariaService.addStep1Document(richAutGiud, documento.getId());
		}else if(step == 2){
			richAutGiudSaved = richiestaAutoritaGiudiziariaService.addStep2Document(richAutGiud, documento.getId());
		}else if(step == 3){
			richAutGiudSaved = richiestaAutoritaGiudiziariaService.addStep3Document(richAutGiud, documento.getId());
		}

		String parentFolderName = FileNetUtil.getRichiestaAutGiudParentFolder(richAutGiud.getDataInserimento());
		String folderName = richAutGiud.getId() + "-RICHIESTA-AUTORITA-GIUDIZIARIA";
		folderName = parentFolderName + folderName + "/";
		Map<String, Object> documentProperty = new HashMap<String, Object>();
		documentProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(richAutGiud.getId()+""));
		//@@DDS Folder richGiudFolder = documentaleDAO.leggiCartella(folderName);
		Folder richGiudFolder = documentaleDdsDAO.leggiCartella(folderName);
		byte[] fileContent = file.getBytes();
		//@@DDS documentaleDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.RICHAUTORITAGIUD_DOCUMENT, file.getContentType(),
		//@@DDS		documentProperty, richGiudFolder, fileContent);
		documentaleDdsDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.RICHAUTORITAGIUD_DOCUMENT, file.getContentType(),
				documentProperty, richGiudFolder.getFolderPath(), fileContent);

		return richAutGiudSaved;
	}

	/* PAGINA DI RICERCA*/
	@RequestMapping("/autoritaGiudiziaria/ricercaAutoritaGiudiziaria")
	public String ricercaAutoritaGiudiziaria(Model model, Locale locale,HttpServletRequest request, HttpServletResponse response) {

		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if(code.equals( CostantiDAO.GESTORE_ARCHIVIO_AUTORITA_GIUDIZIARIA) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){
			// engsecurity VA ??
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);
			AutoritaGiudiziariaView autoritaGiudiziariaView = new AutoritaGiudiziariaView();
			try {
				super.caricaListe(autoritaGiudiziariaView, locale);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			model.addAttribute(MODEL_VIEW_NOME, autoritaGiudiziariaView);

			return "autoritaGiudiziaria/formRicercaAutoritaGiudiziaria";
		}
		return "redirect:/errore.action";
	}

	@RequestMapping(value = "/autoritaGiudiziaria/ricerca", method=RequestMethod.POST)
	public String ricerca(
			Locale locale, 
			Model model, 
			@ModelAttribute(MODEL_VIEW_NOME) @Validated AutoritaGiudiziariaView autoritaGiudiziariaView,
			BindingResult bindingResult, 
			HttpServletRequest request, 
			HttpServletResponse response) { 	
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);


		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if(code.equals( CostantiDAO.GESTORE_ARCHIVIO_AUTORITA_GIUDIZIARIA) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){
			try {
				if( bindingResult.hasErrors() ){	
					return "autoritaGiudiziaria/formRicercaAutoritaGiudiziaria";
				}

				List<AutoritaGiudiziariaView> out = richiestaAutoritaGiudiziariaService.searchRichAutGiudByFilter(autoritaGiudiziariaView);
				autoritaGiudiziariaView.setAutoritaGiudiziariaSearch(out);

				//utente
				//						UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
				//						String nomeUtenteVisualizzato = utenteView.getVo().getNominativoUtil();

				// continua ricerca			
				JSONArray jsonArray = new JSONArray();
				for( AutoritaGiudiziariaView ele : out ){
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("denominazione", ele.getVo().getAutoritaGiudiziaria() == null 
							? "" : ele.getVo().getAutoritaGiudiziaria().replace("'"," ").replace("\"", " "));
					jsonObject.put("oggetto", ele.getVo().getOggetto() == null 
							? "" : ele.getVo().getOggetto().replace("'"," ").replace("\"", " "));

					if(ele.getVo().getTipologiaRichiesta() != null)
						jsonObject.put("tipologiaRichiesta", ele.getVo().getTipologiaRichiesta().getNome().replace("'"," ").replace("\"", " "));
					else
						jsonObject.put("tipologiaRichiesta", "");

					if(ele.getVo().getStatoRichAutGiud() != null)
						jsonObject.put("statoRichiesta", ele.getVo().getStatoRichAutGiud().getDescrizione().replace("'"," ").replace("\"", " "));
					else
						jsonObject.put("statoRichiesta", "");

					if (ele.getVo().getDocumentoStep1() != null)
						jsonObject.put("documentoStep1","<div><label class='waves-effect' style='padding-right: 5px;'>"+ele.getVo().getDocumentoStep1().getNomeFile().replace("'", " ").replace("\"", " ")+"</label><a href='/NewLegalArchives/download?onlyfn=0&isp=0&uuid="
								+ ele.getVo().getDocumentoStep1().getUuid()
								+ "' class='bg   waves-effect waves-circle waves-float btn-circle-mini' style='background-color: #d9d9d9;' target='_BLANK'>"
								+ "<i class='zmdi zmdi-download icon-mini'></i></a></div>");
					else
						jsonObject.put("documentoStep1", "");

					if (ele.getVo().getDocumentoStep2() != null)
						jsonObject.put("documentoStep2","<div><label class='waves-effect' style='padding-right: 5px;'>"+ele.getVo().getDocumentoStep2().getNomeFile().replace("'", " ").replace("\"", " ")+"</label><a href='/NewLegalArchives/download?onlyfn=0&isp=0&uuid="
								+ ele.getVo().getDocumentoStep2().getUuid()
								+ "' class='bg   waves-effect waves-circle waves-float btn-circle-mini' style='background-color: #d9d9d9;' target='_BLANK'>"
								+ "<i class='zmdi zmdi-download icon-mini'></i></a></div>");
					else
						jsonObject.put("documentoStep2", "");

					if (ele.getVo().getDocumentoStep3() != null)
						jsonObject.put("documentoStep3","<div><label class='waves-effect' style='padding-right: 5px;'>"+ele.getVo().getDocumentoStep3().getNomeFile().replace("'", " ").replace("\"", " ")+"</label><a href='/NewLegalArchives/download?onlyfn=0&isp=0&uuid="
								+ ele.getVo().getDocumentoStep3().getUuid()
								+ "' class='bg   waves-effect waves-circle waves-float btn-circle-mini' style='background-color: #d9d9d9;' target='_BLANK'>"
								+ "<i class='zmdi zmdi-download icon-mini'></i></a></div>");
					else
						jsonObject.put("documentoStep3", "");

					if(ele.getVo().getSocieta() != null)
						jsonObject.put("societa", ele.getVo().getSocieta().getNome().replace("'"," ").replace("\"", " "));
					else
						jsonObject.put("societa", "");

					Date dataIns = ele.getVo().getDataInserimento();
					String formatDataIns = "";
					if(dataIns != null) {
						formatDataIns = new SimpleDateFormat("dd/MM/yyyy").format(dataIns);
					}
					jsonObject.put("dataInserimento", formatDataIns);

					jsonObject.put("fornitore", ele.getVo().getFornitore());

					jsonArray.put(jsonObject);
				}

				autoritaGiudiziariaView.setAutoritaGiudiziariaSearchJson(jsonArray.toString());

				return "autoritaGiudiziaria/formRicercaAutoritaGiudiziaria";

			} catch(Throwable e) {
				e.printStackTrace();
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico" + e.getStackTrace().toString() + " " + e.getMessage()));
				return "autoritaGiudiziaria/formRicercaAutoritaGiudiziaria";
			}
		}
		return "redirect:/errore.action";	
	}


}



