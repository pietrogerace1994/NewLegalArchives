package eng.la.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;

import eng.la.business.DueDiligenceService;
import eng.la.business.ProfessionistaEsternoService;
import eng.la.business.StatoDueDiligenceService;
import eng.la.model.Documento;
import eng.la.model.DocumentoDueDiligence;
import eng.la.model.DueDiligence;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.StatoDueDiligence;
import eng.la.model.rest.DocumentoDueDiligenceRest;
import eng.la.model.rest.DueDiligenceRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.DueDiligenceView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.StatoDueDiligenceView;
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

@Controller("dueDiligenceController")
@SessionAttributes("dueDiligenceView")
public class DueDiligenceController extends BaseController {

	private static final String MODEL_VIEW_NOME = "dueDiligenceView";
	private static final String PAGINA_CREAZIONE = "dueDiligence/formCreazioneDueDiligence";

	// messaggio di conferma
	//	private static final String SUCCESS_MSG ="successMsg";
	//	private static final int ELEMENTI_PER_PAGINA_PC = 100;
	//	private static final String REMOTE_USER_PARAM_NAME = "REMOTE_USER";
/*@@DDS
	@Autowired
	private DocumentaleDAO documentaleDAO;
*/
	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;

	@Autowired
	private DocumentoDAO documentoDAO;

	@Autowired
	private DueDiligenceService dueDiligenceService;

	@Autowired
	private StatoDueDiligenceService statoDueDiligenceService;

	@Autowired
	private ProfessionistaEsternoService professionistaEsternoService;

	/**
	 * Carica form creazione autorit� giudiziaria
	 * @param model modello dati
	 * @param locale country locale
	 * @return page on
	 */
	@RequestMapping("/duediligence/creazioneDueDiligence")
	public String gestioneParteCorrelata(Locale locale, Model model, HttpServletRequest request) {
		DueDiligenceView dueDiligenceView = new DueDiligenceView();
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_DUEDILIGENCE) ||code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) ){

				flagView = true;
			}
		}
		if(flagView){

			try {
				dueDiligenceView.setLocale(locale);
				this.caricaListeOggetti(dueDiligenceView, locale);
				convertAndSetDueDiligenceToJsonArray(dueDiligenceView);
			} catch (Throwable e) {
				e.printStackTrace();
			}

			String noRemakeView = request.getParameter("noRemakeView");
			if(noRemakeView == null){
				model.addAttribute(MODEL_VIEW_NOME, dueDiligenceView);
			}

			return PAGINA_CREAZIONE;
		}
		else 
			return "redirect:/errore.action";
	}

	private void convertAndSetDueDiligenceToJsonArray(DueDiligenceView dueDiligenceView)  throws Throwable {
		List<DueDiligenceView> autGiudList = dueDiligenceView.getDueDiligenceViewList();
		JSONArray jsonArray = new JSONArray();
		if( autGiudList != null && autGiudList.size() > 0 ){
			for(DueDiligenceView view : autGiudList){
				JSONObject jsonObject = new JSONObject();

				DueDiligence vo = view.getVo();
				jsonObject.put("id", vo.getId());

				String denominazione = vo.getProfessionistaEsterno().getCognomeNome().replace("'"," ").replace("\"", " ");
				denominazione = denominazione.trim();
				denominazione = org.apache.commons.lang3.StringEscapeUtils.escapeJson(denominazione);

				jsonObject.put("denominazione", denominazione);
				jsonArray.put(jsonObject); 
			}
		}

		if (jsonArray.length() > 0) {
			dueDiligenceView.setJsonArrayDueDiligenceMod(jsonArray.toString());
		} 
		else { dueDiligenceView.setJsonArrayDueDiligenceMod(null); };
	}

	/**
	 * Salvataggio dei dati della form nel dbase ( inserimento / modifica ).
	 * <p>
	 * @param dueDiligenceView
	 * @return
	 */
	@RequestMapping(value = "/duediligence/salva", method=RequestMethod.POST)
	public String salvaDueDiligence(Locale locale, Model model, 
			@ModelAttribute(MODEL_VIEW_NOME) @Validated DueDiligenceView dueDiligenceView,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) { 
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		String token=request.getParameter("CSRFToken");
		try{
			if( dueDiligenceView.getOp() != null && !dueDiligenceView.getOp().equals("salvaDueDiligence") ){
				String ritorno = invocaMetodoDaReflection(dueDiligenceView, bindingResult, locale, model, request, response, this);
				return ritorno == null ? PAGINA_CREAZIONE : ritorno;
			}

			if( bindingResult.hasErrors() ){	
				if (dueDiligenceView.isInsertMode()) {
					dueDiligenceView.setTabAttiva("1");
				} else if (dueDiligenceView.isEditMode() || dueDiligenceView.isDeleteMode()) { 
					dueDiligenceView.setTabAttiva("2");
				}
				return PAGINA_CREAZIONE;
			}

			if (dueDiligenceView.isInsertMode()) {
				makeVoForSave(dueDiligenceView);
				DueDiligenceView dueDiligenceViewSaved = dueDiligenceService.inserisci(dueDiligenceView);
				DueDiligence dueDiligence = dueDiligenceViewSaved != null ? dueDiligenceViewSaved.getVo() : null;

				MultipartFile file = dueDiligenceView.getFileAssegnazione();
				if(file != null && dueDiligence != null && file.getOriginalFilename() != null && 
						!file.getOriginalFilename().isEmpty()){
					getDueDiligenceParentFolder(dueDiligence.getId(), dueDiligence.getDataApertura());
					addDueDiligenceStepDocument(dueDiligence, file, 1);
				}

				clearViewInsertFields(dueDiligenceView);
				dueDiligenceView.setTabAttiva("1");
			} 
			else if (dueDiligenceView.isEditMode()) {
				System.out.println("####### ...esegue modifica... #######");
				makeVoForUpdate(dueDiligenceView);
				DueDiligence dueDiligence = dueDiligenceView.getVo();
				dueDiligenceService.modifica(dueDiligenceView);

				MultipartFile file = dueDiligenceView.getFileAssegnazioneMod();
				if(file != null && dueDiligence != null && file.getOriginalFilename() != null && 
						!file.getOriginalFilename().isEmpty()){
					getDueDiligenceParentFolder(dueDiligence.getId(), dueDiligence.getDataApertura());
					addDueDiligenceStepDocument(dueDiligence, file, 1);
				}

				MultipartFile fileStep2 = dueDiligenceView.getFileVerificaMod();
				if(fileStep2 != null && dueDiligence != null && fileStep2.getOriginalFilename() != null && 
						!fileStep2.getOriginalFilename().isEmpty()){
					addDueDiligenceStepDocument(dueDiligence, fileStep2, 2);
					//					StatoDueDiligence statoDueDiligence = statoDueDiligenceService.readStatoDueDiligenceByFilter(locale, "ST_DUE_2");
					//					dueDiligenceSaved.setStatoDueDiligence(statoDueDiligence);
					//					dueDiligenceService.modificaVo(dueDiligenceSaved);
				}

				String[] documentIdList = dueDiligenceView.getDocumentiVerificaToDelete();
				if(documentIdList != null && documentIdList.length > 0){
					for (String documentId : documentIdList) {
						dueDiligenceService.deleteStep2Document(new Long(documentId));
					}
				}

				MultipartFile fileStep3 = dueDiligenceView.getFileEsitoVerificaMod();
				if(fileStep3 != null && dueDiligence != null && fileStep3.getOriginalFilename() != null && 
						!fileStep3.getOriginalFilename().isEmpty()){
					DueDiligence dueDiligenceSaved = addDueDiligenceStepDocument(dueDiligence, fileStep3, 3);
					StatoDueDiligence statoDueDiligence = statoDueDiligenceService.readStatoDueDiligenceByFilter(locale, "ST_DUE_3");
					dueDiligenceSaved.setStatoDueDiligence(statoDueDiligence);
					dueDiligenceService.modificaVo(dueDiligenceSaved);
				}

				dueDiligenceView.setTabAttiva("2");

			} else if (dueDiligenceView.isDeleteMode()) {
				System.out.println("####### ...esegue cancellazione... #######");
				if(dueDiligenceView.getDueDiligenceIdMod() != null)
					dueDiligenceService.deleteDueDiligence(dueDiligenceView.getDueDiligenceIdMod());
				dueDiligenceView.setTabAttiva("2");
			}

			List<DueDiligenceView> dueDiligenceViewList = dueDiligenceService.leggi();
			dueDiligenceView.setDueDiligenceViewList(dueDiligenceViewList);
			convertAndSetDueDiligenceToJsonArray(dueDiligenceView);
			model.addAttribute("successMessage", "messaggio.operazione.ok");

			return "redirect:creazioneDueDiligence.action?noRemakeView=true&CSRFToken="+token;
		}catch(Throwable e){
			e.printStackTrace();
			bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			return PAGINA_CREAZIONE;
		}
	}

	private void clearViewInsertFields(DueDiligenceView dueDiligenceView) {
		dueDiligenceView.setDataApertura(null);
		dueDiligenceView.setDataChiusura(null);
		dueDiligenceView.setProfessionistaCode(null);
		dueDiligenceView.setStatoDueDiligenceCode(null);
	}

	private void getDueDiligenceParentFolder(Long idDueDiligence, Date dataApertura) throws Throwable {
		if(idDueDiligence != null && dataApertura != null){
			String parentFolderName = FileNetUtil.getDueDiligenceParentFolder(dataApertura);
			String folderName = idDueDiligence + "-VERIFICA-LEGALE-ANTICORRUZIONE";
			String uuid = UUID.randomUUID().toString();
			String folderClassName = FileNetClassNames.ATTIVITA_LEGATE_ANTICORRUZIONE_FOLDER; 
			Map<String, Object> folderProperty = new HashMap<String,Object>();
			folderProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idDueDiligence+""));

			//@@DDS Folder parentFolder = documentaleDAO.leggiCartella(parentFolderName);
			Folder parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			if(parentFolder == null){
				/* @@DDS
				documentaleDAO.verificaCreaPercorsoCartella(parentFolderName);		
				parentFolder = documentaleDAO.leggiCartella(parentFolderName);
				*/
				documentaleDdsDAO.verificaCreaPercorsoCartella(parentFolderName);
				parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			}

			String dueDiligenceFolderName = idDueDiligence + "-VERIFICA-LEGALE-ANTICORRUZIONE";
			dueDiligenceFolderName = parentFolderName + dueDiligenceFolderName + "/";
			//@@DDS Folder dueDiligenceFolder = documentaleDAO.leggiCartella(dueDiligenceFolderName);
			Folder dueDiligenceFolder = documentaleDdsDAO.leggiCartella(dueDiligenceFolderName);
			if(dueDiligenceFolder == null){
				//@@DDS documentaleDAO.creaCartella(uuid, folderName, folderClassName, folderProperty, parentFolder);
				documentaleDdsDAO.creaCartella(uuid, folderName, folderClassName, folderProperty, parentFolder);
			}
		}
	}

	public DueDiligence addDueDiligenceStepDocument(DueDiligence dueDiligence, MultipartFile file, int step) throws Throwable {
		if(dueDiligence == null){
			throw new RuntimeException("Richiesta Autorita Giudiziaria non trovata");
		}

		String uuid = UUID.randomUUID().toString();
		Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT, 
				file.getOriginalFilename(), file.getContentType());

		DueDiligence dueDiligenceSaved = null;
		if(step == 1){
			dueDiligenceSaved = dueDiligenceService.addStep1Document(dueDiligence, documento.getId());
		}else if(step == 2){
			DocumentoDueDiligence documentoDueDiligence = dueDiligenceService.addStep2Document(dueDiligence, documento.getId());
			dueDiligenceSaved = documentoDueDiligence != null ? documentoDueDiligence.getDueDiligence() : null;
		}else if(step == 3){
			dueDiligenceSaved = dueDiligenceService.addStep3Document(dueDiligence, documento.getId());
		}

		String parentFolderName = FileNetUtil.getDueDiligenceParentFolder(dueDiligence.getDataApertura());
		String folderName = dueDiligence.getId() + "-VERIFICA-LEGALE-ANTICORRUZIONE";
		folderName = parentFolderName + folderName + "/";
		Map<String, Object> documentProperty = new HashMap<String, Object>();
		documentProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(dueDiligence.getId() + ""));
		//@@DDS Folder dueDiligenceFolder = documentaleDAO.leggiCartella(folderName);
		Folder dueDiligenceFolder = documentaleDdsDAO.leggiCartella(folderName);
		byte[] fileContent = file.getBytes();
		//@@DDS documentaleDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT, file.getContentType(),
		//@@DDS		documentProperty, dueDiligenceFolder, fileContent);
		documentaleDdsDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT, file.getContentType(),
				documentProperty, dueDiligenceFolder.getFolderPath(), fileContent);
		return dueDiligenceSaved;
	}

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		DueDiligenceView dueDiligenceView = (DueDiligenceView) view;

		List<StatoDueDiligenceView> statoDueDiligenceViewList = statoDueDiligenceService.leggi(locale);
		dueDiligenceView.setStatoDueDiligenceList(statoDueDiligenceViewList);

		List<ProfessionistaEsternoView> professionistaEsternoViewList = professionistaEsternoService.leggi(false);
		dueDiligenceView.setProfessionistaEsternoList(professionistaEsternoViewList);

		List<DueDiligenceView> dueDiligenceViewList = dueDiligenceService.leggi();
		dueDiligenceView.setDueDiligenceViewList(dueDiligenceViewList);
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {

	}

	private void makeVoForSave(DueDiligenceView dueDiligenceView) {
		DueDiligence vo = new DueDiligence();

		try {
			vo.setDataApertura(new SimpleDateFormat("dd/MM/yyyy").parse(dueDiligenceView.getDataApertura()));
			vo.setDataChiusura(new SimpleDateFormat("dd/MM/yyyy").parse(dueDiligenceView.getDataChiusura()));
		} catch (ParseException e) { }

		ProfessionistaEsterno professionistaEsterno = new ProfessionistaEsterno();
		professionistaEsterno.setId(dueDiligenceView.getProfessionistaCode());
		vo.setProfessionistaEsterno(professionistaEsterno);

		StatoDueDiligence statoDueDiligence = new StatoDueDiligence();
		statoDueDiligence.setId(dueDiligenceView.getStatoDueDiligenceCode());
		vo.setStatoDueDiligence(statoDueDiligence);

		dueDiligenceView.setVo(vo);
	}

	@RequestMapping(value = "/duediligence/loadDueDiligence", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody DueDiligenceRest loadDueDiligence(@RequestParam("id") Long id,
			HttpServletRequest request ,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated DueDiligenceView dueDiligenceViewCurrent) {


		DueDiligenceRest dueDiligenceRest = new DueDiligenceRest();
		try {
			DueDiligenceView dueDiligenceView = dueDiligenceService.leggi(id);
			if (dueDiligenceView != null) {
				dueDiligenceRest.setId(dueDiligenceView.getVo().getId());

				Date dataApertura = dueDiligenceView.getVo().getDataApertura();
				Date dataChiusura = dueDiligenceView.getVo().getDataChiusura();

				if(dataApertura != null){
					dueDiligenceRest.setDataApertura(new SimpleDateFormat("dd/MM/yyyy").format(dataApertura));
				}
				if(dataChiusura != null){
					dueDiligenceRest.setDataChiusura(new SimpleDateFormat("dd/MM/yyyy").format(dataChiusura));
				}

				if(dueDiligenceView.getVo().getStatoDueDiligence() != null){
					dueDiligenceRest.setStatoDueDiligenceId(dueDiligenceView.getVo().getStatoDueDiligence().getId());
				}
				if(dueDiligenceView.getVo().getProfessionistaEsterno() != null){
					dueDiligenceRest.setProfessionistaId(dueDiligenceView.getVo().getProfessionistaEsterno().getId());
				}
				if(dueDiligenceView.getVo().getDocumentoStep1() != null){
					//					dueDiligenceRest.setNomeFileStep1(dueDiligenceView.getVo().getDocumentoStep1().getNomeFile());

					dueDiligenceRest.setNomeFileStep1("<div class='form-group'><label style='padding-right: 5px;'>"+dueDiligenceView.getVo().getDocumentoStep1().getNomeFile().replace("'", " ").replace("\"", " ")+"</label><a href='/NewLegalArchives/download?onlyfn=0&isp=0&uuid="
							+ dueDiligenceView.getVo().getDocumentoStep1().getUuid()
							+ "' class='bg   waves-effect waves-circle waves-float btn-circle-mini' style='background-color: #d9d9d9;' target='_BLANK'>"
							+ "<i class='zmdi zmdi-download icon-mini'></i></a></div>");
				}
				Set<DocumentoDueDiligence> documentoStep2Set = dueDiligenceView.getVo().getDocumentoDueDiligences();
				if(documentoStep2Set != null && !documentoStep2Set.isEmpty()){
					ArrayList<DocumentoDueDiligenceRest> array = new ArrayList<DocumentoDueDiligenceRest>();
					Iterator<DocumentoDueDiligence> iter = documentoStep2Set.iterator();
					while(iter.hasNext()){
						DocumentoDueDiligence elem = iter.next();
						DocumentoDueDiligenceRest restObject = new DocumentoDueDiligenceRest();
						restObject.setId(elem.getId());
						restObject.setIdDocumento(elem.getDocumento() != null ? elem.getDocumento().getId() : null);
						restObject.setIdDueDiligence(elem.getDueDiligence() != null ? elem.getDueDiligence().getId() : null);
						restObject.setNomeFile(elem.getDocumento() != null ? elem.getDocumento().getNomeFile() : null);
						if (restObject.getNomeFile()!= null){
							restObject.setNomeFile("<div><label style='padding-right: 5px;'>"+elem.getDocumento().getNomeFile().replace("'", " ").replace("\"", " ")+"</label><a href='/NewLegalArchives/download?onlyfn=0&isp=0&uuid="
									+ elem.getDocumento().getUuid()
									+ "' class='bg   waves-effect waves-circle waves-float btn-circle-mini' style='background-color: #d9d9d9;' target='_BLANK'>"
									+ "<i class='zmdi zmdi-download icon-mini'></i></a></div>");
						}
						array.add(restObject);
					}
					dueDiligenceRest.setFileStep2List(array);
					dueDiligenceViewCurrent.setListaDocumentiVerifica(array);
				}
				if(dueDiligenceView.getVo().getDocumentoStep3() != null){
					//					dueDiligenceRest.setNomeFileStep3(dueDiligenceView.getVo().getDocumentoStep3().getNomeFile());
					dueDiligenceRest.setNomeFileStep3("<div class='form-group'><label style='padding-right: 5px;'>"+dueDiligenceView.getVo().getDocumentoStep3().getNomeFile().replace("'", " ").replace("\"", " ")+"</label><a href='/NewLegalArchives/download?onlyfn=0&isp=0&uuid="
							+ dueDiligenceView.getVo().getDocumentoStep3().getUuid()
							+ "' class='bg   waves-effect waves-circle waves-float btn-circle-mini' style='background-color: #d9d9d9;' target='_BLANK'>"
							+ "<i class='zmdi zmdi-download icon-mini'></i></a></div>");
				}
				if(dueDiligenceView.getVo().getStatoDueDiligence() != null){
					dueDiligenceRest.setStatoCodGruppoLingua(dueDiligenceView.getVo().getStatoDueDiligence().getCodGruppoLingua());
				}
			}
		} catch (Throwable e) { e.printStackTrace(); }

		return dueDiligenceRest;
	}

	private void makeVoForUpdate(DueDiligenceView dueDiligenceView) throws Throwable {
		DueDiligence vo = dueDiligenceService.leggi(dueDiligenceView.getDueDiligenceIdMod()).getVo();
		vo.setId(dueDiligenceView.getDueDiligenceIdMod());
		try {
			vo.setDataApertura(new SimpleDateFormat("dd/MM/yyyy").parse(dueDiligenceView.getDataAperturaMod()));
			vo.setDataChiusura(new SimpleDateFormat("dd/MM/yyyy").parse(dueDiligenceView.getDataChiusuraMod()));
		} catch (ParseException e) { }

		ProfessionistaEsterno professionistaEsterno = new ProfessionistaEsterno();
		professionistaEsterno.setId(dueDiligenceView.getProfessionistaCodeMod());
		vo.setProfessionistaEsterno(professionistaEsterno);

		StatoDueDiligence statoDueDiligence = new StatoDueDiligence();
		statoDueDiligence.setId(dueDiligenceView.getStatoDueDiligenceCodeMod());
		vo.setStatoDueDiligence(statoDueDiligence);

		dueDiligenceView.setVo(vo);
	}

	@RequestMapping("/duediligence/ricercaDueDiligence")
	public String ricercaDueDiligence(Model model, Locale locale, HttpServletRequest request, HttpServletResponse response) {
		
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		
		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if(code.equals( CostantiDAO.GESTORE_ARCHIVIO_DUEDILIGENCE) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			DueDiligenceView dueDiligenceView = new DueDiligenceView();
			// engsecurity VA ??
			//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			//htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);
			try {
				super.caricaListe(dueDiligenceView, locale);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			model.addAttribute(MODEL_VIEW_NOME, dueDiligenceView);

			return "dueDiligence/formRicercaDueDiligence";
		}
		return "redirect:/errore.action";
	}

	@RequestMapping(value = "/duediligence/ricerca", method=RequestMethod.POST)
	public String ricerca(
			Locale locale, 
			Model model, 
			@ModelAttribute(MODEL_VIEW_NOME) @Validated DueDiligenceView dueDiligenceView,
			BindingResult bindingResult, 
			HttpServletRequest request, 
			HttpServletResponse response) { 


		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if(code.equals( CostantiDAO.GESTORE_ARCHIVIO_DUEDILIGENCE) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);
			try {
				if( bindingResult.hasErrors() ){	
					return "dueDiligence/formRicercaDueDiligence";
				}

				List<DueDiligenceView> out = dueDiligenceService.searchDueDiligenceByFilter(dueDiligenceView);
				dueDiligenceView.setDueDiligenceSearch(out);

				JSONArray jsonArray = new JSONArray();
				for( DueDiligenceView ele : out ){

					JSONObject jsonObject = new JSONObject();
					jsonObject.put("denominazione", ele.getVo().getProfessionistaEsterno() == null 
							? "" : ele.getVo().getProfessionistaEsterno().getCognomeNome().replace("'"," ").replace("\"", " "));

					if(ele.getVo().getStatoDueDiligence() != null)
						jsonObject.put("statoVerifica", ele.getVo().getStatoDueDiligence().getDescrizione().replace("'"," ").replace("\"", " "));
					else
						jsonObject.put("statoVerifica", "");


					Date dataIns = ele.getVo().getDataApertura();
					String formatDataIns = "";
					if(dataIns != null) {
						formatDataIns = new SimpleDateFormat("dd/MM/yyyy").format(dataIns);
					}
					jsonObject.put("dataApertura", formatDataIns);

					Date dataChiusura = ele.getVo().getDataChiusura();
					String formatDataChiusura = "";
					if(dataChiusura != null) {
						formatDataChiusura = new SimpleDateFormat("dd/MM/yyyy").format(dataChiusura);
					}
					jsonObject.put("dataChiusura", formatDataChiusura);


					if(ele.getVo().getDocumentoStep1() != null)
						jsonObject.put("documentoStep1","<div><label class='waves-effect' style='padding-right: 5px;'>"+ele.getVo().getDocumentoStep1().getNomeFile().replace("'", " ").replace("\"", " ")+"</label><a href='/NewLegalArchives/download?onlyfn=0&isp=0&uuid="
								+ ele.getVo().getDocumentoStep1().getUuid()
								+ "' class='bg   waves-effect waves-circle waves-float btn-circle-mini' style='background-color: #d9d9d9;' target='_BLANK'>"
								+ "<i class='zmdi zmdi-download icon-mini'></i></a></div>");
					else
						jsonObject.put("documentoStep1", "");

					Set<DocumentoDueDiligence> documentoDueDiligences = ele.getVo().getDocumentoDueDiligences();
					Iterator<DocumentoDueDiligence> iter = documentoDueDiligences.iterator();
					String documentoStep2 = "<table style='background-color: transparent;'><tbody>";
					while(iter.hasNext()){
						DocumentoDueDiligence doc = iter.next();
						documentoStep2 += "<tr><div><label class='waves-effect' style='padding-right: 5px;'>"+doc.getDocumento().getNomeFile().replace("'", " ").replace("\"", " ")+"</label><a href='/NewLegalArchives/download?onlyfn=0&isp=0&uuid="
								+ doc.getDocumento().getUuid()
								+ "' class='bg   waves-effect waves-circle waves-float btn-circle-mini' style='background-color: #d9d9d9;' target='_BLANK'>"
								+ "<i class='zmdi zmdi-download icon-mini'></i></a></div></tr>";

					}
					jsonObject.put("documentoStep2", documentoStep2+"</tbody></table>");


					if(ele.getVo().getDocumentoStep3() != null)
						jsonObject.put("documentoStep3","<div><label class='waves-effect' style='padding-right: 5px;'>"+ele.getVo().getDocumentoStep3().getNomeFile().replace("'", " ").replace("\"", " ")+"</label><a href='/NewLegalArchives/download?onlyfn=0&isp=0&uuid="
								+ ele.getVo().getDocumentoStep3().getUuid()
								+ "' class='bg   waves-effect waves-circle waves-float btn-circle-mini' style='background-color: #d9d9d9;' target='_BLANK'>"
								+ "<i class='zmdi zmdi-download icon-mini'></i></a></div>");
					else
						jsonObject.put("documentoStep3", "");

					jsonArray.put(jsonObject);
				}

				dueDiligenceView.setDueDiligenceSearchJson(jsonArray.toString());
				return "dueDiligence/formRicercaDueDiligence";

			} catch(Throwable e) {
				e.printStackTrace();
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico" + e.getStackTrace().toString() + " " + e.getMessage()));
				return "dueDiligence/formRicercaDueDiligence";
			}
		}
		return "redirect:/errore.action";
	}

}



