package eng.la.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.CategoriaMailinglistService;
import eng.la.business.MailinglistService;
import eng.la.business.RubricaService;
import eng.la.model.CategoriaMailinglist;
import eng.la.model.Mailinglist;
import eng.la.model.MailinglistDettaglio;
import eng.la.model.rest.MailingListEditRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.CategoriaMailinglistView;
import eng.la.model.view.MailinglistDettaglioView;
import eng.la.model.view.MailinglistView;
import eng.la.model.view.RubricaView;
import eng.la.persistence.CostantiDAO;
import eng.la.presentation.validator.MailingListValidator;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("mailingListController")
@SessionAttributes("mailingListView")
public class MailingListController extends BaseController {

	private static final String MODEL_VIEW_NOME = "mailingListView";
	private static final String PAGINA_FORM_PATH = "presidioNormativo/formMailingListPresidioNormativo";
	private static final String PAGINA_FORM_EDIT_PATH = "presidioNormativo/formMailingListPresidioNormativo";
	//	private static final String PAGINA_FORM =  "presidionormativo/mailingList.action";

	@Autowired
	private MailinglistService mailingListService;

	@Autowired
	private CategoriaMailinglistService categoriaMailingListService;

	@Autowired
	private RubricaService rubricaService;

	@RequestMapping("/presidionormativo/mailingList")
	public String creaMailingList(HttpServletRequest request, Model model, Locale locale) {

		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			MailinglistView mailingListView = new MailinglistView();
			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);
			try {
				List<MailinglistView> listaMailingList = (List<MailinglistView>) mailingListService.leggiMailinglist();
				mailingListView.setListaMailingListView(listaMailingList);
				super.caricaListe(mailingListView, locale);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			mailingListView.setVo(new Mailinglist());
			model.addAttribute(MODEL_VIEW_NOME, mailingListView);
			return PAGINA_FORM_PATH;
		}
		return "redirect:/errore.action";
	}


	@RequestMapping(value = "/mailingList/caricaMailingListEdit", method = RequestMethod.POST)
	public @ResponseBody MailingListEditRest caricaMailingListEdit(@RequestParam("id") String id, Locale locale) {

		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			MailingListEditRest mailingListRest = new MailingListEditRest();
			try {
				MailinglistView view = mailingListService.leggi(NumberUtils.toLong(id), FetchMode.JOIN);

				mailingListRest.setId(view.getVo().getId());
				mailingListRest.setNome(view.getVo().getNome());

				CategoriaMailinglist categoriaMailingList = view.getVo().getCategoriaMailinglist();
				if(categoriaMailingList!=null){
					CategoriaMailinglistView categoriaMailingListView = categoriaMailingListService.leggi(categoriaMailingList.getCodGruppoLingua(), locale);

					if(categoriaMailingListView.getVo().getCategoriaPadre()!=null){
						mailingListRest.setCategoria(categoriaMailingListView.getVo().getCategoriaPadre().getCodGruppoLingua());
						mailingListRest.setSottoCategoria(categoriaMailingListView.getVo().getCodGruppoLingua());
					}
					else
						mailingListRest.setCategoria(categoriaMailingListView.getVo().getCodGruppoLingua());
				}

				List<Long> listRubricaDesc = new ArrayList<Long>();
				List<MailinglistDettaglioView> leggiMailingListDettaglioById = mailingListService.leggiMailingListDettagliobyId(NumberUtils.toLong(id));
				for (MailinglistDettaglioView rView : leggiMailingListDettaglioById) {
					Long idRubrica = rView.getVo().getRubrica().getId();
					RubricaView rubricaView = rubricaService.leggiRubrica(idRubrica);
					if(rubricaView!=null)
						listRubricaDesc.add(rubricaView.getVo().getId());
				}
				mailingListRest.setRubricaDesc(listRubricaDesc);

			} catch (Throwable e) {
				e.printStackTrace();
			}
			return mailingListRest;
		}
		return null;
	}


	@RequestMapping(value = "/mailingList/salva", method = RequestMethod.POST)
	public String salvaMailingList(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated MailinglistView mailingListView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			String token=request.getParameter("CSRFToken");
			String redirect = "redirect:/presidionormativo/mailingList.action?CSRFToken="+token;
			try {
				if (mailingListView.getOp() != null && !mailingListView.getOp().equals("salvaMailingList")) {
					String ritorno = invocaMetodoDaReflection(mailingListView, bindingResult, locale, model, request,
							response, this);
					return ritorno == null ? PAGINA_FORM_EDIT_PATH : ritorno;
				}

				if (bindingResult.hasErrors()) {
					if(mailingListView.isDeleteMode() || mailingListView.isEditMode()){
						return PAGINA_FORM_EDIT_PATH;
					} else {
						//checkMail(professionistaEsternoView);
						return PAGINA_FORM_PATH;
					}
				}

				preparaPerSalvataggio(mailingListView, bindingResult, locale);

				if (bindingResult.hasErrors()) {
					if(mailingListView.isDeleteMode() || mailingListView.isEditMode()){
						return PAGINA_FORM_EDIT_PATH;
					} else {
						return PAGINA_FORM_PATH;
					}
				}

				if(mailingListView.isDeleteMode()){
					mailingListService.cancellaMailinglist(mailingListView.getMailingListId().longValue());
					//redirect = "redirect:mailingList.action";
				} else if (mailingListView.isEditMode()){
					mailingListService.modifica(mailingListView);
					//redirect = "redirect:mailingList.action";
				} else {
					mailingListService.salvaMailinglist(mailingListView);

				}

				model.addAttribute("successMessage", "messaggio.operazione.ok");
				return redirect;
			} catch (Throwable e) {
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
				if(mailingListView.isDeleteMode() || mailingListView.isEditMode()){
					return PAGINA_FORM_EDIT_PATH;
				} else {
					return PAGINA_FORM_PATH;
				}
			}
		}
		return "redirect:/errore.action";
	}


	private void preparaPerSalvataggio(MailinglistView mailingListView, BindingResult bindingResult, Locale locale) throws Throwable {

		String categoriaCode = "";

		String sottoCategoriaCode = "";

		String[] nominativiAggiunti;

		Mailinglist mailingList = null;
		if (mailingListView.getMailingListId() != null && mailingListView.getMailingListId()!= 0) {
			MailinglistView oldView = mailingListService.leggi(mailingListView.getMailingListId());
			mailingList = oldView.getVo();

			mailingList.setNome(mailingListView.getNomeMod());

			categoriaCode = mailingListView.getCategoriaCodeMod();

			sottoCategoriaCode = mailingListView.getSottoCategoriaCodeMod();

			nominativiAggiunti = mailingListView.getNominativiAggiuntiMod();


		}else{ 
			mailingList = new Mailinglist();  

			mailingList.setNome(mailingListView.getNome());

			categoriaCode = mailingListView.getCategoriaCode();

			sottoCategoriaCode = mailingListView.getSottoCategoriaCode();

			nominativiAggiunti = mailingListView.getNominativiAggiunti();
		}


		if(sottoCategoriaCode!=null && !sottoCategoriaCode.equalsIgnoreCase("")){
			CategoriaMailinglistView categoriaMailingListView = categoriaMailingListService.leggi(sottoCategoriaCode , locale);
			mailingList.setCategoriaMailinglist(categoriaMailingListView.getVo());
		}
		else if(categoriaCode!=null && !categoriaCode.equalsIgnoreCase("")){
			CategoriaMailinglistView categoriaMailingListView = categoriaMailingListService.leggi(categoriaCode , locale);
			mailingList.setCategoriaMailinglist(categoriaMailingListView.getVo());
		}


		if (nominativiAggiunti != null	&& nominativiAggiunti.length > 0) {
			Set<MailinglistDettaglio> listaDettaglioMailingList = new HashSet<MailinglistDettaglio>();
			for (String nominativo : nominativiAggiunti) {

				RubricaView rubricaView = rubricaService.leggiRubrica(Long.valueOf(nominativo));

				MailinglistDettaglio dettaglioMailingList = new MailinglistDettaglio();


				dettaglioMailingList.setRubrica(rubricaView.getVo());

				listaDettaglioMailingList.add(dettaglioMailingList);
			}
			mailingList.setMailinglistDettaglio(listaDettaglioMailingList);
		}

		mailingListView.setVo(mailingList);

	}





	@RequestMapping("/mailingList/gestioneMailingList")
	public String gestioneMailingList(HttpServletRequest request, Model model, Locale locale) {

		//utente
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			MailinglistView mailingListView = new MailinglistView();
			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);
			try {
				List<MailinglistView> listaMailingList = (List<MailinglistView>) mailingListService.leggiMailinglist();
				mailingListView.setListaMailingListView(listaMailingList);
				super.caricaListe(mailingListView, locale);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			mailingListView.setVo(new Mailinglist());
			model.addAttribute(MODEL_VIEW_NOME, mailingListView);
			return PAGINA_FORM_EDIT_PATH;
		}
		return "redirect:/errore.action";
	}



	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		MailinglistView mailingListView = (MailinglistView) view;

		List<CategoriaMailinglistView> listaCategoriaMailingList = mailingListService.listaCategoriaMailinglist(locale.getLanguage().toUpperCase());

		mailingListView.setListaCategoriaMailingList(listaCategoriaMailingList);


	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new MailingListValidator());
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
}
