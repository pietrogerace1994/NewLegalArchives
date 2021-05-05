package eng.la.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.EmailService;
import eng.la.business.MailinglistService;
import eng.la.business.jms.QueuePublisher;
import eng.la.model.Articolo;
import eng.la.model.CategoriaMailinglist;
import eng.la.model.Documento;
import eng.la.model.MailinglistDettaglio;
import eng.la.model.rest.CategoriaMailinglistRest;
import eng.la.model.rest.EmailRest;
import eng.la.model.rest.MailingListRest;
import eng.la.model.rest.RicercaEmailRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.CategoriaMailinglistView;
import eng.la.model.view.EmailView;
import eng.la.model.view.FileView;
import eng.la.model.view.MailinglistView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.presentation.validator.EmailValidator;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("emailController")
public class EmailController extends BaseController {
	private static final String MODEL_VIEW_NOME = "emailView";
	private static final String PAGINA_FORM_PATH = "email/formEmail";
	private static final String PAGINA_DETTAGLIO_PATH = "email/formEmailDettaglio";
	private static final String PAGINA_RICERCA_PATH = "email/cercaEmail";
	private static final String PAGINA_AZIONI_CERCA_EMAIL = "email/azioniCercaEmail";
	private static final String PAGINA_AZIONI_EMAIL = "email/azioniEmail";

	@Autowired  
	private EmailService emailService;

	@Autowired  
	private MailinglistService mailinglistService;

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	@RequestMapping("/articolo/modifica")
	public String modificaEmail(@RequestParam("id") long id, Model model, Locale locale,
			HttpServletRequest request) {

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
			//removeCSRFToken(request);

			EmailView view = new EmailView();

			try {

				UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
				EmailView emailSalvata = (EmailView) emailService.leggiEmail(id);


				List<Documento> documenti = emailService.leggiDocumenti(id);



				if(documenti.size()!=0){
					List<FileView> filePresenti = new ArrayList<FileView>();

					for (Documento documento : documenti){
						FileView file = new FileView();

						file.setNome(documento.getNomeFile());
						file.setUuid(documento.getUuid());

						filePresenti.add(file);
					}

					view.setFilesPresenti(filePresenti);
				}

				if (emailSalvata != null && emailSalvata.getVo() != null) {
					popolaFormDaVo(view, emailSalvata.getVo(), locale, utenteView);

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
		return "redirect:/errore.action";
	}


	public String inviaMailingList(EmailView emailView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {

		try{
			EmailView emailSaved = emailService.leggiEmail(emailView.getEmailId());
			String[] arrRubricaInvioEmail = emailView.getArrRubricaInvioEmail();
			if( arrRubricaInvioEmail != null && arrRubricaInvioEmail.length >  0 ){
				for(String email : arrRubricaInvioEmail){
					MailingListRest rest = new MailingListRest();
					rest.setEmail(email);
					rest.setOggettoMail(emailSaved.getVo().getOggetto());
					rest.setContenutoMail(emailSaved.getVo().getContenutoBreve());
					try {
						if( emailSaved.getVo().getDataEmail() == null 
								|| emailSaved.getVo().getDataEmail().getTime() <= System.currentTimeMillis() ){
							QueuePublisher.getInstance().sendMessage(rest);
						}else{
							QueuePublisher.getInstance().sendMessage(rest, emailSaved.getVo().getDataEmail());
						}
					} catch (Throwable e) { 
						e.printStackTrace();
					}

				}
				model.addAttribute("successMessage", "messaggio.operazione.ok");
			}else
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
		}catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}

		return "redirect:dettaglio.action?id=" + emailView.getEmailId();
	}

	private void popolaFormDaVo(EmailView view, Articolo vo, Locale locale, UtenteView utenteConnesso)
			throws Throwable {

		view.setEmailId(vo.getId());
		view.setOggetto(vo.getOggetto());

		if( vo.getDataEmail() != null ){
			view.setDataEmail(DateUtil.formattaDataOraNoTrattino(vo.getDataEmail().getTime()));
		}

		if( vo.getDataCreazione() != null ){
			view.setDataCreazione(DateUtil.getDataDDMMYYYY(vo.getDataCreazione().getTime()));
		} 

		view.setContenutoBreve(vo.getContenutoBreve());
		view.setContenuto(vo.getContenuto());
		CategoriaMailinglistView categoriaMailinglistView = anagraficaStatiTipiService.leggiCategoria(vo.getCategoria().getCodGruppoLingua(), locale.getLanguage().toUpperCase(), false);
		view.setCategoriaCode(categoriaMailinglistView.getVo().getCodGruppoLingua());
		view.setSottoCategoriaCode(vo.getSottoCategoria() == null ? null : vo.getSottoCategoria().getCodGruppoLingua());

		view.setSottocategorie(anagraficaStatiTipiService.leggiCategorie(categoriaMailinglistView.getVo().getId(), false));

	}

	@RequestMapping(value = "/articolo/salva", method = RequestMethod.POST)
	public String salvaEmail(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated EmailView emailView, BindingResult bindingResult,
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

			try {


				if (emailView.getOp() != null && !emailView.getOp().equals("salvaEmail")) {
					String ritorno = invocaMetodoDaReflection(emailView, bindingResult, locale, model, request, response,
							this);

					return ritorno == null ? PAGINA_FORM_PATH : ritorno;
				}

				if (bindingResult.hasErrors()) {
					model.addAttribute(MODEL_VIEW_NOME, emailView);
					super.caricaListe(emailView, locale);
					return PAGINA_FORM_PATH;
				}

				preparaPerSalvataggio(emailView, bindingResult);

				if (bindingResult.hasErrors()) {
					model.addAttribute(MODEL_VIEW_NOME, emailView);
					super.caricaListe(emailView, locale);
					return PAGINA_FORM_PATH;
				}

				long emailId = 0;
				if (emailView.getEmailId() == null || emailView.getEmailId() == 0) {
					EmailView emailSalvata = emailService.salvaEmail(emailView);
					emailId = emailSalvata.getVo().getId();

				} else {
					emailService.modificaEmail(emailView);
					emailId = emailView.getVo().getId();
				}

				model.addAttribute("successMessage", "messaggio.operazione.ok");

				return "redirect:modifica.action?id=" + emailId+"&CSRFToken="+token;
			} catch (Throwable e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "errore.generico");
				return "redirect:/errore.action";
			}
		}
		return "redirect:/errore.action";
	}

	private void preparaPerSalvataggio(EmailView view, BindingResult bindingResult) throws Throwable {
		Articolo vo = null;
		if (view.getEmailId() != null) {
			EmailView oldView = emailService.leggiEmail( view.getEmailId() );
			vo = oldView.getVo();  
		}else{ 
			vo = new Articolo();  
		}

		if (StringUtils.isNotBlank(view.getDataEmail())) {
			vo.setDataEmail(DateUtil.toDateTime(view.getDataEmail()));
		} 
		vo.setContenuto(view.getContenuto());
		vo.setContenutoBreve(view.getContenutoBreve());
		vo.setOggetto(view.getOggetto());
		vo.setDataCreazione(new Date());
		CategoriaMailinglistView categoriaMailinglistView = anagraficaStatiTipiService.leggiCategoria(view.getCategoriaCode(), Locale.ITALIAN.getLanguage().toUpperCase(), false);
		vo.setCategoria(categoriaMailinglistView.getVo());
		if( StringUtils.isNotBlank( view.getSottoCategoriaCode() )){
			CategoriaMailinglistView sottocategoriaMailinglistView = anagraficaStatiTipiService.leggiCategoria(view.getSottoCategoriaCode(), Locale.ITALIAN.getLanguage().toUpperCase(), false);
			vo.setSottoCategoria(sottocategoriaMailinglistView.getVo());
		}

		view.setVo(vo);
	}

	@RequestMapping("/articolo/crea")
	public String creaEmail(HttpServletRequest request, Model model, Locale locale) {

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

			EmailView view = new EmailView();
			// engsecurity VA 
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);

			try {
				super.caricaListe(view, locale);

			} catch (Throwable e) {
				model.addAttribute("errorMessage", "errore.generico");
				e.printStackTrace();
			}

			view.setVo(new Articolo());

			model.addAttribute(MODEL_VIEW_NOME, view);
			return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_FORM_PATH;
		}
		return "redirect:/errore.action";
	}


	@RequestMapping(value="/articolo/selezionaCategoria", produces="application/json")
	public @ResponseBody List<CategoriaMailinglistRest> selezionaCategoria(@RequestParam("codice") String codice, Locale locale) {

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

			List<CategoriaMailinglistRest> risultato = new ArrayList<CategoriaMailinglistRest>();

			try {
				CategoriaMailinglistView categoria = anagraficaStatiTipiService.leggiCategoria(codice, locale.getLanguage().toUpperCase(), false);
				List<CategoriaMailinglistView> sottocategorie = anagraficaStatiTipiService.leggiCategorie(categoria.getVo().getId(), false);
				if( sottocategorie != null && sottocategorie.size() > 0){
					for(CategoriaMailinglistView sottocategoria : sottocategorie){
						CategoriaMailinglistRest rest = new CategoriaMailinglistRest();
						rest.setId(sottocategoria.getVo().getId());
						rest.setCodice(sottocategoria.getVo().getCodGruppoLingua());
						rest.setNomeCategoria(sottocategoria.getVo().getNomeCategoria());
						risultato.add(rest);
					}
				}
			} catch (Throwable e) { 
				e.printStackTrace();
			}
			return risultato;
		}
		return null;
	}



	@RequestMapping(value="/articolo/caricaMailingList", produces="application/json")
	public @ResponseBody List<MailingListRest> caricaMailingList(@RequestParam("idemail") long idemail, Locale locale) {

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

			List<MailingListRest> risultato = new ArrayList<MailingListRest>();
			Map<String,MailingListRest> risultatoTmp = new HashMap<String,MailingListRest>();


			try {
				EmailView emailView = emailService.leggiEmail(idemail); 
				CategoriaMailinglist categoria = emailView.getVo().getCategoria();
				CategoriaMailinglist sottocategoria = emailView.getVo().getSottoCategoria();
				Collection<MailinglistView> mailinglistaDaCategoria = mailinglistService.leggiMailinglist(categoria.getCodGruppoLingua());
				Collection<MailinglistView> mailinglistaDaSottocategoria = sottocategoria != null ? mailinglistService.leggiMailinglist(sottocategoria.getCodGruppoLingua()) : null;

				if( mailinglistaDaCategoria != null && mailinglistaDaCategoria.size() > 0){
					for(MailinglistView tmp : mailinglistaDaCategoria){
						Set<MailinglistDettaglio> listaDettaglio = tmp.getVo().getMailinglistDettaglio();
						if( listaDettaglio != null && listaDettaglio.size() > 0 ){
							for(MailinglistDettaglio tmpDettaglio : listaDettaglio){
								MailingListRest rest = new MailingListRest();
								rest.setNominativo(tmpDettaglio.getRubrica().getNominativo());
								rest.setEmail(tmpDettaglio.getRubrica().getEmail());
								rest.setIdrubrica(tmpDettaglio.getRubrica().getId());
								rest.setCategoria(tmp.getVo().getCategoriaMailinglist().getNomeCategoria());

								risultatoTmp.put(tmpDettaglio.getRubrica().getEmail(), rest);
							}
						}
					}
				}

				if( mailinglistaDaSottocategoria != null && mailinglistaDaSottocategoria.size() > 0){
					for(MailinglistView tmp : mailinglistaDaSottocategoria){
						Set<MailinglistDettaglio> listaDettaglio = tmp.getVo().getMailinglistDettaglio();
						if( listaDettaglio != null && listaDettaglio.size() > 0 ){
							for(MailinglistDettaglio tmpDettaglio : listaDettaglio){
								MailingListRest rest = new MailingListRest();
								rest.setNominativo(tmpDettaglio.getRubrica().getNominativo());
								rest.setEmail(tmpDettaglio.getRubrica().getEmail());
								rest.setIdrubrica(tmpDettaglio.getRubrica().getId());
								rest.setCategoria(tmp.getVo().getCategoriaMailinglist().getNomeCategoria()); 

								risultatoTmp.put(tmpDettaglio.getRubrica().getEmail(), rest);
							}
						}
					}
				}

				risultato.addAll(risultatoTmp.values());
			} catch (Throwable e) { 
				e.printStackTrace();
			}
			return risultato;
		}
		return null;
	}

	@RequestMapping("/articolo/dettaglio")
	public String dettaglioEmail(@RequestParam("id") long id, Model model, Locale locale,
			HttpServletRequest request) {

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

			//engsecurity VA
			//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			//htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);
			EmailView view = new EmailView();

			try {
				EmailView emailSalvata = emailService.leggiEmail(id);
				if (emailSalvata != null && emailSalvata.getVo() != null) {
					popolaFormDaVoPerDettaglio(view, emailSalvata.getVo(), locale);

					super.caricaListePerDettaglio(view, locale);

				} else {
					model.addAttribute("errorMessage", "errore.generico");
				}
			} catch (Throwable e) {
				model.addAttribute("errorMessage", "errore.generico");
				e.printStackTrace();
			}
			model.addAttribute(MODEL_VIEW_NOME, view);
			return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_DETTAGLIO_PATH;
		}
		return "redirect:/errore.action";
	}


	private void popolaFormDaVoPerDettaglio(EmailView view, Articolo vo, Locale locale) throws Throwable {
		view.setEmailId(vo.getId());
		view.setOggetto(vo.getOggetto());

		if( vo.getDataEmail() != null ){
			view.setDataEmail(DateUtil.formattaDataOraNoTrattino(vo.getDataEmail().getTime()));
		}

		if( vo.getDataCreazione() != null ){
			view.setDataCreazione(DateUtil.formattaDataOraNoTrattino(vo.getDataCreazione().getTime()));
		} 

		view.setContenuto(vo.getContenuto()); 
		view.setContenutoBreve(vo.getContenutoBreve());
		CategoriaMailinglistView categoriaMailinglistView = anagraficaStatiTipiService.leggiCategoria(vo.getCategoria().getCodGruppoLingua(), locale.getLanguage().toUpperCase(), true);
		view.setCategoriaCode(categoriaMailinglistView.getVo().getCodGruppoLingua());
		view.setSottoCategoriaCode(vo.getSottoCategoria() == null ? null : vo.getSottoCategoria().getCodGruppoLingua());

		view.setSottocategorie(anagraficaStatiTipiService.leggiCategorie(categoriaMailinglistView.getVo().getId(), true));
	}

	@RequestMapping(value = "/articolo/preparaInviaComunicazione", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RisultatoOperazioneRest preparaInviaComunicazione(HttpServletRequest request, Locale locale) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

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

			try {
				//String[] emailIdsArray = request.getParameterValues("emailIdsArray");
				//emailService.preparaInviaComunicazione(emailIdsArray);
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return risultato;
		}
		return null;
	}

	@RequestMapping(value="/articolo/elimina", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaEmail(@RequestParam("id") long id
			, HttpServletRequest request) {

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

			RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


			try {
				emailService.cancellaEmail(id);
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return risultato;
		}
		return null;
	}



	@RequestMapping(value = "/articolo/ricerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaEmailRest ricercaEmail(HttpServletRequest request, Locale locale) {

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
			try {
				int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA
						: NumberUtils.toInt(request.getParameter("limit"));
				String offset = request.getParameter("offset");
				int numeroPagina = offset == null || offset.equals("0") ? 1
						: (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
				String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
				String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
				String oggetto = request.getParameter("oggetto") == null ? ""
						: URLDecoder.decode(request.getParameter("oggetto"), "UTF-8");

				String dal = request.getParameter("dal") == null ? ""
						: URLDecoder.decode(request.getParameter("dal"), "UTF-8");
				String al = request.getParameter("al") == null ? ""
						: URLDecoder.decode(request.getParameter("al"), "UTF-8");

				String contenutoBreve = request.getParameter("contenutoBreve") == null ? ""
						: URLDecoder.decode(request.getParameter("contenutoBreve"), "UTF-8");

				String comboCategoria = request.getParameter("categoria") == null ? ""
						: URLDecoder.decode(request.getParameter("categoria"), "UTF-8");

				ListaPaginata<EmailView> lista = (ListaPaginata<EmailView>) emailService.cerca(oggetto, dal, al, numElementiPerPagina, numeroPagina, ordinamento,
						tipoOrdinamento, contenutoBreve, comboCategoria);
				RicercaEmailRest ricercaModelRest = new RicercaEmailRest();
				ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
				List<EmailRest> rows = new ArrayList<EmailRest>();
				for (EmailView view : lista) {
					EmailRest rest = new EmailRest();
					rest.setId(view.getVo().getId());
					rest.setOggetto(view.getVo().getOggetto());
					rest.setContenutoBreve(view.getVo().getContenutoBreve());
					rest.setCategoria(view.getVo().getCategoria().getNomeCategoria());
					rest.setColore(view.getVo().getCategoria().getColore());
					rest.setSottoCategoria(view.getVo().getSottoCategoria() != null ? view.getVo().getSottoCategoria().getNomeCategoria() : "");
					rest.setDataOra(DateUtil.formattaDataOra(view.getVo().getDataCreazione().getTime()));
					rest.setAzioni("<p id='containerAzioniRigaEmail" + view.getVo().getId() + "'></p>");
					rows.add(rest);
				}
				ricercaModelRest.setRows(rows);
				return ricercaModelRest;
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}

	@RequestMapping(value = "/articolo/cerca", method = RequestMethod.GET)
	public String cercaEmail(HttpServletRequest request, Model model, Locale locale) {

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
			//removeCSRFToken(request);
			try {
				EmailView view = new EmailView();
				model.addAttribute(MODEL_VIEW_NOME, view);
				return PAGINA_RICERCA_PATH;
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;

	}

	@RequestMapping(value = "/presidionormativo/caricaAzioniEmail", method = RequestMethod.POST)
	public String caricaAzioniEmail(@RequestParam("idEmail") long idEmail, HttpServletRequest req,
			Locale locale) {

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
			htmlActionSupport.checkCSRFToken(req);
			//removeCSRFToken(request);
			try {
				req.setAttribute("idEmail", idEmail);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			if (req.getParameter("onlyContent") != null) {

				return PAGINA_AZIONI_EMAIL;
			}

			return PAGINA_AZIONI_CERCA_EMAIL;
		}
		return "redirect:/errore.action";
	} 

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		EmailView emailView = (EmailView) view;
		emailView.setCategorie(anagraficaStatiTipiService.leggiCategorie(locale.getLanguage().toUpperCase()));
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		EmailView emailView = (EmailView) view;
		emailView.setCategorie(anagraficaStatiTipiService.leggiCategorie(locale.getLanguage().toUpperCase())); 
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new EmailValidator());

	}

}
