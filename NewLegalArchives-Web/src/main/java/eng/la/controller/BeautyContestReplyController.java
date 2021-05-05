package eng.la.controller;

import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

//@@DDS import com.filenet.api.core.Folder;
import eng.la.persistence.DocumentaleDdsCryptDAO;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import it.snam.ned.libs.dds.dtos.v2.Document;
//import com.filenet.api.collection.DocumentSet;
//import com.filenet.api.collection.PageIterator;
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;

import eng.la.business.BeautyContestReplyService;
import eng.la.model.BeautyContestReply;
import eng.la.model.view.BaseView;
import eng.la.model.view.BeautyContestReplyView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.DocumentaleCryptDAO;
import eng.la.presentation.validator.BeautyContestReplyValidator;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.filenet.model.FileNetUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("beautyContestReplyController")
@SessionAttributes("beautyContestReplyView")
public class BeautyContestReplyController extends BaseController {
	private static final String MODEL_VIEW_NOME_REPLY = "beautyContestReplyView";
	private static final String PAGINA_DETTAGLIO_REPLY_PATH = "beautyContest/formBeautyContestReplyDettaglio";
	private static final Logger logger = Logger.getLogger(BeautyContestReplyController.class);
	@Autowired
	private BeautyContestReplyService beautyContestReplyService;

	@RequestMapping("/beautyContestReply/dettaglioBeautyContestReply")
	public String dettaglioBeautyContestReply(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		BeautyContestReplyView view = new BeautyContestReplyView();

		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);

		try {
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
			BeautyContestReplyView beautyContestReplySalvato = (BeautyContestReplyView) beautyContestReplyService.leggi(id);
			if (beautyContestReplySalvato != null && beautyContestReplySalvato.getVo() != null) {
				popolaFormReplyDaVo(view, beautyContestReplySalvato.getVo(), locale, utenteView, true);

			} else {
				model.addAttribute("errorMessage", "errore.generico");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}
		model.addAttribute(MODEL_VIEW_NOME_REPLY, view);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_DETTAGLIO_REPLY_PATH;
	}

	private void popolaFormReplyDaVo(BeautyContestReplyView view, BeautyContestReply vo, Locale locale, UtenteView utenteConnesso, boolean allegati) throws Throwable {

		view.setBeautyContestReplyId(Long.valueOf(vo.getId()));
		view.setDescrizioneOffertaTecnica(StringEscapeUtils.unescapeHtml4(vo.getDescrizione()));

		if( vo.getDataInvio() != null ){
			view.setDataInvio(DateUtil.formattaData(vo.getDataInvio().getTime()));	
		}

		if( vo.getOfferta_economica() != null ){
			view.setOffertaEconomica(vo.getOfferta_economica());
		}

		if(allegati){
			caricaDocumentiGenericiFilenet(view, vo);
		}
	}

	private void caricaDocumentiGenericiFilenet(BeautyContestReplyView view, BeautyContestReply vo) throws Throwable
	{
		String nomeCartellaBeautyContest = FileNetUtil.getBeautyContestCartella(vo.getBeautyContest().getId(), vo.getBeautyContest().getDataEmissione());

		String nomeCartellaBeautyContestReply = nomeCartellaBeautyContest + "/" + vo.getId() + "-" + vo.getProfessionista().getId();

		/*DDS
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO)SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaBc = documentaleCryptDAO.leggiCartella(nomeCartellaBeautyContestReply);
		DocumentSet documenti = cartellaBc.get_ContainedDocuments();
		*/
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO)SpringUtil.getBean("documentaleDdsCryptDAO");
		//non serve Folder cartellaBc = documentaleDdsCryptDAO.leggiCartella(nomeCartellaBeautyContestReply);
		//@@DDS DocumentSet documenti = cartellaBc.get_ContainedDocuments();
		List<Document> documenti = documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaBeautyContestReply);

		logger.debug("@@DDS BeautyContestReplyController.caricaDocumentiGenericiFilenet TODO");
		List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
		if (documenti != null)
		{
			//@@DDS inizio commento
			/*TODO @@DDS
			PageIterator it = documenti.pageIterator();
			while (it.nextPage())
			{
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[])it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray)
				{
					DocumentImpl documento = (DocumentImpl)objDocumento;
					if (documento.get_ClassDescription().get_Name().equals("RispostaBCDocument"))
					{
						if (documento.get_Name().subSequence(0, 1).equals("T"))
						{
							DocumentoView docView = new DocumentoView();
							docView.setNomeFile(documento.get_Name().substring(2, documento.get_Name().length()));
							docView.setUuid(documento.get_Id().toString());
							listaDocumenti.add(docView);
							view.setOffertaTecnicaDoc(docView);
						}
						if (documento.get_Name().subSequence(0, 1).equals("E"))
						{
							DocumentoView docView = new DocumentoView();
							docView.setNomeFile(documento.get_Name().substring(2, documento.get_Name().length()));
							docView.setUuid(documento.get_Id().toString());
							listaDocumenti.add(docView);
							view.setOffertaEconomicaDoc(docView);
						}
					}
				}
			}*/

			for (Document documento : documenti)
			{
				if (documento.getDocumentalClass().equals("RispostaBCDocument"))
				{
					if (documento.getContents().get(0).getContentsName().subSequence(0, 1).equals("T"))
					{
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.getContents().get(0).getContentsName().substring(2, documento.getContents().get(0).getContentsName().length()));
						docView.setUuid(documento.getId());
						listaDocumenti.add(docView);
						view.setOffertaTecnicaDoc(docView);
					}
					if (documento.getContents().get(0).getContentsName().subSequence(0, 1).equals("E"))
					{
						DocumentoView docView = new DocumentoView();
						docView.setNomeFile(documento.getContents().get(0).getContentsName().substring(2, documento.getContents().get(0).getContentsName().length()));
						docView.setUuid(documento.getId());
						listaDocumenti.add(docView);
						view.setOffertaEconomicaDoc(docView);
					}
				}
			}
		}
	}

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new BeautyContestReplyValidator());
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
