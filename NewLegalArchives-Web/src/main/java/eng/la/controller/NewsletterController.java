package eng.la.controller;

import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.velocity.VelocityEngineUtils;
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
import eng.la.business.ArchivioProtocolloService;
import eng.la.business.EmailService;
import eng.la.business.MailinglistService;
import eng.la.business.NewsletterService;
import eng.la.business.RubricaService;
import eng.la.business.UtenteService;
import eng.la.business.jms.QueuePublisher;
import eng.la.model.Articolo;
import eng.la.model.CategoriaMailinglist;
import eng.la.model.Newsletter;
import eng.la.model.NewsletterEmail;
import eng.la.model.RNewsletterArticolo;
import eng.la.model.Utente;
import eng.la.model.mail.Categorie;
import eng.la.model.mail.Comunicazione;
import eng.la.model.rest.MailingListRest;
import eng.la.model.rest.NewsletterRest;
import eng.la.model.rest.RicercaNewsletterRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.EmailView;
import eng.la.model.view.NewsletterView;
import eng.la.model.view.RubricaView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.presentation.validator.NewsletterValidator;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("newsletterController")
public class NewsletterController extends BaseController {
	private static final String MODEL_VIEW_NOME = "newsletterView";
	private static final String PAGINA_FORM_PATH = "newsletter/formNewsletter";
	private static final String PAGINA_RICERCA_PATH = "newsletter/cercaNewsletter";
	private static final String PAGINA_AZIONI_CERCA_NEWSLETTER = "newsletter/azioniCercaNewsletter";
	private static final String PAGINA_AZIONI_NEWSLETTER = "newsletter/azioniNewsletter";

	@Autowired  
	private NewsletterService newsletterService;

	@Autowired  
	private MailinglistService mailinglistService;

	@Autowired  
	private EmailService emailService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private RubricaService rubricaService;

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	@Autowired
	private ArchivioProtocolloService archivioProtocolloService;

	@Autowired
	private VelocityEngine velocityEngine;


	@RequestMapping("/newsletter/modifica")
	public String modificaNewsletter(@RequestParam("id") long id, Model model, Locale locale,
			HttpServletRequest request) {
		// engsecurity VA - redirect
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		

		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) ||code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) ){

				flagView = true;
			}
		}
		if(flagView){

			NewsletterView view = new NewsletterView();

			List<Articolo> daRimuovere = new ArrayList<Articolo>();



			try {

				UtenteView utenteView = (UtenteView) request.getSession().getAttribute(UTENTE_CONNESSO_NOME_PARAMETRO);
				NewsletterView newsletterSalvata = (NewsletterView) newsletterService.leggiNewsletter(id);

				List<RNewsletterArticolo> articoli = newsletterService.leggiArticoli(id);

				List<NewsletterEmail> emails = newsletterService.leggiEmails(id);

				List<String> emailsTo = new ArrayList<String>();

				for (NewsletterEmail email : emails){
					String emailTo = email.getEmail();
					emailsTo.add(emailTo);
				}

				ArrayList<String> articoliAggiunti = new ArrayList<String>();

				for(RNewsletterArticolo articolo : articoli){
					String art = String.valueOf(articolo.getArticolo().getId());
					articoliAggiunti.add(art);
				}

				view.setArticoliAggiunti(articoliAggiunti);

				view.setEmail(emailsTo);

				if (newsletterSalvata != null && newsletterSalvata.getVo() != null) {
					popolaFormDaVo(view, newsletterSalvata.getVo(), locale, utenteView);

					super.caricaListe(view, locale);

					daRimuovere = newsletterService.leggiArticoliDocumento();

					List<EmailView> articoliTutti = view.getListaArticoli();

					List<EmailView> articoliDaSettare = new ArrayList<EmailView>();

					for(EmailView art:articoliTutti){
						if(!daRimuovere.contains(art.getVo()) || articoliAggiunti.contains(String.valueOf(art.getVo().getId())))
							articoliDaSettare.add(art);
					}


					view.setListaArticoli(articoliDaSettare);
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
		else 
			return null;
	}

	private void popolaFormDaVo(NewsletterView view, Newsletter vo, Locale locale, UtenteView utenteConnesso)
			throws Throwable {

		view.setNewsletterId(vo.getId());
		view.setNumero(vo.getNumero());
		view.setTitolo(vo.getTitolo());
		//view.setNumeroRomano(RomanNumber.toRoman(vo.getNumero()));
		if(vo.getHighLights()==null)
			view.setComboHigh(new Long(0));
		else
			view.setComboHigh(vo.getHighLights());

		if( vo.getDataCreazione() != null ){
			view.setDataCreazione(DateUtil.getDataDDMMYYYY(vo.getDataCreazione().getTime()));
		} 


		//		try {
		//			byte[] bytes = newsletterService.leggiCopertina(vo.getCopertina());
		//			
		//			String imgType = vo.getCopertina().getContentType();
		//			
		//			byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
		//			 
		//			String base64Encoded = new String(encodeBase64, "UTF-8");
		//			
		//			String urlImg = "data:"+imgType+";base64," + base64Encoded;
		//			
		//			view.setCopertinaMod(urlImg);
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}




	}

	@RequestMapping(value = "/newsletter/salva", method = RequestMethod.POST)
	public String salvaNewsletter(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated NewsletterView newsletterView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		String token=request.getParameter("CSRFToken");

		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) ||code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) ){

				flagView = true;
			}
		}
		if(flagView){

			try {


				if (newsletterView.getOp() != null && !newsletterView.getOp().equals("salvaNewsletter")) {
					String ritorno = invocaMetodoDaReflection(newsletterView, bindingResult, locale, model, request, response,
							this);

					return ritorno == null ? PAGINA_FORM_PATH : ritorno;
				}

				if (bindingResult.hasErrors()) {
					model.addAttribute(MODEL_VIEW_NOME, newsletterView);
					super.caricaListe(newsletterView, locale);
					return PAGINA_FORM_PATH;
				}

				preparaPerSalvataggio(newsletterView, bindingResult);

				if (bindingResult.hasErrors()) {
					model.addAttribute(MODEL_VIEW_NOME, newsletterView);
					super.caricaListe(newsletterView, locale);
					return PAGINA_FORM_PATH;
				}

				long newsletterId = 0;
				if (newsletterView.getNewsletterId() == null || newsletterView.getNewsletterId() == 0) {
					NewsletterView newsletterSalvata = newsletterService.salvaNewsletter(newsletterView);
					newsletterId = newsletterSalvata.getVo().getId();

				} else {
					newsletterService.modificaNewsletter(newsletterView);
					newsletterId = newsletterView.getVo().getId();
				}

				model.addAttribute("successMessage", "messaggio.operazione.ok");

				return "redirect:modifica.action?id=" + newsletterId+"&CSRFToken="+token;
			} catch (Throwable e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "errore.generico");
				return "redirect:/errore.action";
			}
		}
		else
			return null;

	}

	private void preparaPerSalvataggio(NewsletterView view, BindingResult bindingResult) throws Throwable {
		Newsletter vo = null;
		if (view.getNewsletterId() != null) {
			NewsletterView oldView = newsletterService.leggiNewsletter( view.getNewsletterId() );
			vo = oldView.getVo();  
		}else{ 
			vo = new Newsletter(); 
			vo.setStato(anagraficaStatiTipiService.leggiStatoNewsletter(CostantiDAO.NEWSLETTER_STATO_BOZZA, view.getLocale().getCountry().toUpperCase()).getVo());
		}

		if (StringUtils.isNotBlank(view.getDataCreazione())) {
			vo.setDataCreazione(DateUtil.toDateTime(view.getDataCreazione()));
		} 

		vo.setTitolo("Presidio Normativo - N° "+view.getNumero());
		vo.setNumero(view.getNumero());
		vo.setDataCreazione(new Date());

		if(view.getComboHigh()!=0)
			vo.setHighLights(view.getComboHigh());
		else
			vo.setHighLights(null);

		view.setVo(vo);
	}

	@RequestMapping("/newsletter/crea")
	public String creaNewsletter(HttpServletRequest request, Model model, Locale locale) {
		
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) ||code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) ){

				flagView = true;
			}
		}
		if(flagView){

			NewsletterView view = new NewsletterView();
			String newNumber ="";
			List<Articolo> daRimuovere = new ArrayList<Articolo>();

			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

			Utente utente = utenteView.getVo();

			// engsecurity VA
			HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			htmlActionSupport.checkCSRFToken(request);
			//removeCSRFToken(request);
			try {
				newNumber = archivioProtocolloService.generaNumeroProtocollo(utente, "UTENTI NEWSLETTER", utente.getCodiceUnitaUtil().substring(5), "NEWSLETTER", "OUT", utente, locale); 

				super.caricaListe(view, locale);

				daRimuovere = newsletterService.leggiArticoliDocumento();

			} catch (Throwable e) {
				model.addAttribute("errorMessage", "errore.generico");
				e.printStackTrace();
			}

			List<EmailView> articoli = view.getListaArticoli();

			List<EmailView> articoliDaSettare = new ArrayList<EmailView>();

			for(EmailView art:articoli){
				if(!daRimuovere.contains(art.getVo()))
					articoliDaSettare.add(art);
			}


			view.setListaArticoli(articoliDaSettare);
			view.setNumero(newNumber);
			//view.setNumero(newNumber);
			view.setTitolo("Presidio Normativo - N° "+newNumber);

			view.setVo(new Newsletter());

			model.addAttribute(MODEL_VIEW_NOME, view);
			return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_FORM_PATH;
		}
		else
			return null;
	}


	@RequestMapping(value="/newsletter/elimina", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaNewsletter(@RequestParam("id") long id, Locale locale) {

		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) ||code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) ){

				flagView = true;
			}
		}
		if(flagView){


			RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


			try {
				newsletterService.cancellaNewsletter(id, locale.getLanguage().toUpperCase());
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return risultato;
		}
		else
			return null;
	}

	@RequestMapping(value="/newsletter/attiva", produces="application/json")
	public @ResponseBody RisultatoOperazioneRest attivaNewsletter(@RequestParam("id") long id, Locale locale) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");



		try {
			newsletterService.attivaNewsletter(id, locale.getLanguage().toUpperCase());
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@RequestMapping(value = "/newsletter/cerca", method = RequestMethod.GET)
	public String cercaNewsletter(HttpServletRequest request, Model model, Locale locale) {

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		try {
			NewsletterView view = new NewsletterView();
			model.addAttribute(MODEL_VIEW_NOME, view);
			return PAGINA_RICERCA_PATH;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/newsletter/caricaAzioniNewsletter", method = RequestMethod.POST)
	public String caricaAzioniEmail(@RequestParam("idNewsletter") long idNewsletter, HttpServletRequest req,
			Locale locale) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(req);
		//removeCSRFToken(request);
		try {
			req.setAttribute("idNewsletter", idNewsletter);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if (req.getParameter("onlyContent") != null) {

			return PAGINA_AZIONI_NEWSLETTER;
		}

		return PAGINA_AZIONI_CERCA_NEWSLETTER;
	} 

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new NewsletterValidator());
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}

	@RequestMapping(value = "/newsletter/ricerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaNewsletterRest ricercaNewsletter(HttpServletRequest request, Locale locale) {

		try {

			int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA
					: NumberUtils.toInt(request.getParameter("limit"));
			String offset = request.getParameter("offset");
			int numeroPagina = offset == null || offset.equals("0") ? 1
					: (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
			String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
			String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");

			String dal = request.getParameter("dal") == null ? ""
					: URLDecoder.decode(request.getParameter("dal"), "UTF-8");

			String al = request.getParameter("al") == null ? ""
					: URLDecoder.decode(request.getParameter("al"), "UTF-8");

			String titolo = request.getParameter("titolo") == null ? ""
					: URLDecoder.decode(request.getParameter("titolo"), "UTF-8");

			String stato = request.getParameter("stato") == null ? ""
					: URLDecoder.decode(request.getParameter("stato"), "UTF-8");

			ListaPaginata<NewsletterView> lista = (ListaPaginata<NewsletterView>) newsletterService.cerca(dal, al, titolo, stato, numElementiPerPagina, numeroPagina, ordinamento,
					tipoOrdinamento);
			RicercaNewsletterRest ricercaModelRest = new RicercaNewsletterRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<NewsletterRest> rows = new ArrayList<NewsletterRest>();
			for (NewsletterView view : lista) {
				NewsletterRest rest = new NewsletterRest();
				rest.setId(view.getVo().getId());
				rest.setNumero(view.getVo().getNumero());
				rest.setTitolo(view.getVo().getTitolo());
				rest.setAnno(Integer.toString(DateUtil.getAnno(view.getVo().getDataCreazione())));
				rest.setStato(view.getVo().getStato().getDescrizione());
				rest.setAzioni("<p id='containerAzioniRigaNewsletter" + view.getVo().getId() + "'></p>");
				rows.add(rest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	public eng.la.model.mail.Newsletter creaAnteprimaNewsletter(long idNewsletter, String baseUrl) throws Throwable{
		eng.la.model.mail.Newsletter newsletter = new eng.la.model.mail.Newsletter();

		List<String> imgPath = new ArrayList<String>();

		Newsletter newsletters= newsletterService.leggiNewsletter(idNewsletter).getVo();

		List<Categorie> categorie = new ArrayList<Categorie>();

		newsletter.setTitolo(StringEscapeUtils.escapeHtml(newsletters.getTitolo()));
		newsletter.setNumero(newsletters.getNumero());
		newsletter.setAnno(Integer.toString(DateUtil.getAnno(newsletters.getDataCreazione())));

		List<RNewsletterArticolo> articoli = newsletterService.leggiArticoli(idNewsletter);

		List<CategoriaMailinglist> categoriaMailingList = mailinglistService.leggiCategorieMailingList();

		for(CategoriaMailinglist categoria : categoriaMailingList){
			Categorie cat = new Categorie();
			cat.setIdCategoria(categoria.getId());
			cat.setCategoria(StringEscapeUtils.escapeHtml(categoria.getNomeCategoria()));
			cat.setColoreCategoria(categoria.getColore());

			List<CategoriaMailinglist> sotto =  mailinglistService.leggiSottoCategoriaMailingList(categoria.getId());

			if(!sotto.isEmpty()){
				ArrayList<Long> sottoId = new ArrayList<Long>();	
				for(CategoriaMailinglist s:sotto){
					sottoId.add(s.getId());
				}
				cat.setCategorieFiglie(sottoId);
			}

			if(categoria.getIcon()!=null){
				cat.setIcon(categoria.getIcon().substring(4));
				imgPath.add(categoria.getIcon().substring(4));
			}

			imgPath.add("hightlights.png");

			List<Comunicazione> comi = new ArrayList<Comunicazione>();

			for(RNewsletterArticolo newsArticolo : articoli){
				Articolo articolo = emailService.leggiEmail(newsArticolo.getArticolo().getId()).getVo();

				if(articolo.getSottoCategoria()!=null){

					CategoriaMailinglist sottoCategoria = articolo.getSottoCategoria();

					List<CategoriaMailinglist> sottoCategorie = mailinglistService.leggiSottoCategoriaMailingList(cat.getIdCategoria());

					if(sottoCategorie.contains(sottoCategoria)){
						Comunicazione com = new Comunicazione();
						com.setId(articolo.getId());
						com.setData(articolo.getDataCreazione().toString());
						com.setTitolo(StringEscapeUtils.escapeHtml(articolo.getOggetto()));
						com.setAbstractCont(StringEscapeUtils.escapeHtml(articolo.getContenutoBreve()));

						com.setSottoCategoria(articolo.getSottoCategoria().getNomeCategoria());
						com.setSottocategoriaId(articolo.getSottoCategoria().getId());

						comi.add(com);
						if(newsletters.getHighLights()!=null){
							if(articolo.getId()==newsletters.getHighLights()){
								newsletter.setNewestTitle(StringEscapeUtils.escapeHtml(articolo.getOggetto()));
								newsletter.setNewestAbstract(StringEscapeUtils.escapeHtml(articolo.getContenutoBreve()));
							}
						}
					}

				} else if(articolo.getCategoria().getId()==categoria.getId()){
					Comunicazione com = new Comunicazione();
					com.setId(articolo.getId());
					com.setData(articolo.getDataCreazione().toString());
					com.setTitolo(StringEscapeUtils.escapeHtml(articolo.getOggetto()));
					com.setAbstractCont(StringEscapeUtils.escapeHtml(articolo.getContenutoBreve()));
					com.setCategoriaId(categoria.getId());
					comi.add(com);

					if(newsletters.getHighLights()!=null){
						if(articolo.getId()==newsletters.getHighLights()){
							newsletter.setNewestTitle(StringEscapeUtils.escapeHtml(articolo.getOggetto()));
							newsletter.setNewestAbstract(StringEscapeUtils.escapeHtml(articolo.getContenutoBreve()));
						}
					}
				}

			}
			cat.setComunicazioni(comi);

			if(!comi.isEmpty())
				categorie.add(cat);
		}
		
		Collections.reverse(categorie);


		newsletter.setCategorie(categorie);

		newsletter.setImgPath(imgPath);

		newsletter.setUrlCopertina(baseUrl+"/portal-news/portal/img/");

		newsletter.setUrlArticolo(baseUrl+"public/news/dettaglio.action?article=");

		return newsletter;
	}
	

	
	@RequestMapping(value = "/newsletter/anteprimaNewsletter", produces = "text/html", method = RequestMethod.GET)
	public @ResponseBody String anteprimaNewsletter(@RequestParam("id") long id, Model model, HttpServletRequest request) {
		eng.la.model.mail.Newsletter risultato = null;
		String msg = null;
		try {

			String baseUrl = request.getContextPath();

			risultato = creaAnteprimaNewsletter(id, baseUrl+"/");
			
			Map<String, Object> modelm = new HashMap<String, Object>();
			modelm.put("newsletter", risultato);

	       msg = VelocityEngineUtils.mergeTemplateIntoString(
	            velocityEngine, "templateMail/mailinglist/comunicazione.vm", "UTF-8", modelm);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	@RequestMapping(value = "/newsletter/inviaAnteprimaNewsletter", produces = "text/html", method = RequestMethod.GET)
	public @ResponseBody String inviaAnteprimaNewsletter(@RequestParam("id") long id, Model model, HttpServletRequest request) {
		try {
			eng.la.model.mail.Newsletter risultato = null;
			
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("/config.properties");

			Properties properties = new Properties();
			properties.load(is);

			String baseUrl = properties.getProperty("webUrl");

			risultato = creaAnteprimaNewsletter(id, baseUrl);
			
			Map<String, Object> modelm = new HashMap<String, Object>();
			modelm.put("newsletter", risultato);
			modelm.put("baseUrlLegalArchives", baseUrl);

	        String msg = VelocityEngineUtils.mergeTemplateIntoString(
	            velocityEngine, "templateMail/mailinglist/comunicazioneEmail.vm", "UTF-8", modelm);
	        
	        List<UtenteView> gestori =  utenteService.leggiGestoriPresidioNormativo();
	        
	        for(UtenteView gestore : gestori){
	        	
	        	
	        	String email = gestore.getVo().getEmailUtil();
	        	
	        	MailingListRest mailingListRest = new MailingListRest();
	        	mailingListRest.setContenutoMail(msg);
	        	mailingListRest.setImage(risultato.getImage());
	        	mailingListRest.setOggettoMail("ANTEPRIMA Presidio Normativo n° "+risultato.getNumero());
	        	mailingListRest.setEmail(email );

	        	QueuePublisher.getInstance().sendMessage(mailingListRest);
	        }
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "ok";
	}
	
	@RequestMapping(value = "/newsletter/inviaNewsletter", produces = "text/html", method = RequestMethod.GET)
	public @ResponseBody String inviaNewsletter(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {
		try {
			eng.la.model.mail.Newsletter risultato = null;

			InputStream is = this.getClass().getClassLoader().getResourceAsStream("/config.properties");
			
			Properties properties = new Properties();
			properties.load(is);
			
			String baseUrl = properties.getProperty("webUrl");
			
			risultato = creaAnteprimaNewsletter(id, baseUrl);

			List<RubricaView> utenti =  rubricaService.leggiRubrica();
			
			List<NewsletterEmail> emails = newsletterService.leggiEmails(id);

			for (RubricaView utente : utenti){
				
				eng.la.model.mail.Newsletter outputRisultato = new eng.la.model.mail.Newsletter();
				
				outputRisultato.setAnno(risultato.getAnno());
				outputRisultato.setImage(risultato.getImage());
				outputRisultato.setNumero(risultato.getNumero());
				outputRisultato.setNumeroRomano(risultato.getNumeroRomano());
				outputRisultato.setTitolo(risultato.getTitolo());
				outputRisultato.setUrlArticolo(risultato.getUrlArticolo());
				outputRisultato.setNewestTitle(risultato.getNewestTitle());
				outputRisultato.setNewestAbstract(risultato.getNewestAbstract());

				List<Categorie> categorie = risultato.getCategorie();

				List<Categorie> newRisultato = new ArrayList<Categorie>();

				List<Long> categorieUtente = mailinglistService.getCategorieUtente(utente.getVo().getId());

				
				for(Categorie categoria : categorie){
					
					Categorie catTemp = new Categorie();
					
					catTemp.setCategoria(categoria.getCategoria());
					catTemp.setCategorieFiglie(categoria.getCategorieFiglie());
					catTemp.setColoreCategoria(categoria.getColoreCategoria());
					catTemp.setIcon(categoria.getIcon());
					catTemp.setIdCategoria(categoria.getIdCategoria());
					
					if(categoria.getCategorieFiglie() != null){
						if(categorieUtente.contains(categoria.getIdCategoria()) || !Collections.disjoint(categorieUtente, categoria.getCategorieFiglie())){
							
							List<Comunicazione> comunicazioniUtente = new ArrayList<Comunicazione>();
							List<Comunicazione> comunicazioni = categoria.getComunicazioni();
							
							for(Comunicazione comunicazione : comunicazioni){
								
								if(comunicazione.getSottoCategoria() != null && !comunicazione.getSottoCategoria().isEmpty()){
									
									if(categorieUtente.contains(comunicazione.getSottocategoriaId())){
										
										comunicazioniUtente.add(comunicazione);
									}
								}
								else{
									
									if(categorieUtente.contains(comunicazione.getCategoriaId())){
										
										comunicazioniUtente.add(comunicazione);
									}
								}
							}
							
							if(!comunicazioniUtente.isEmpty()){
								
								catTemp.setComunicazioni(comunicazioniUtente);
								newRisultato.add(catTemp);
							}
						}
					}
					else{
						if(categorieUtente.contains(categoria.getIdCategoria())){
							
							if(categoria.getComunicazioni() != null && !categoria.getComunicazioni().isEmpty())
								newRisultato.add(categoria);
						}
					}
				}
				
				if(!newRisultato.isEmpty()){

					outputRisultato.setCategorie(newRisultato);

					Map<String, Object> modelm = new HashMap<String, Object>();
					modelm.put("newsletter", outputRisultato);
					modelm.put("baseUrlLegalArchives", baseUrl);

					String msg = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEngine, "templateMail/mailinglist/comunicazioneEmail.vm", "UTF-8", modelm);

					MailingListRest mailingListRest = new MailingListRest();
					mailingListRest.setContenutoMail(msg);
					mailingListRest.setImage(risultato.getImage());
					mailingListRest.setOggettoMail("Presidio Normativo n° "+risultato.getNumero());
					mailingListRest.setEmail(utente.getVo().getEmail() );

					QueuePublisher.getInstance().sendMessage(mailingListRest);
				}
			}
			
			for (NewsletterEmail email : emails){
				
				eng.la.model.mail.Newsletter outputRisultato = new eng.la.model.mail.Newsletter();
				
				outputRisultato.setAnno(risultato.getAnno());
				outputRisultato.setImage(risultato.getImage());
				outputRisultato.setNumero(risultato.getNumero());
				outputRisultato.setTitolo(risultato.getTitolo());
				outputRisultato.setCategorie(risultato.getCategorie());
				outputRisultato.setUrlArticolo(risultato.getUrlArticolo());
				outputRisultato.setNewestTitle(risultato.getNewestTitle());
				outputRisultato.setNewestAbstract(risultato.getNewestAbstract());


				Map<String, Object> modelm = new HashMap<String, Object>();

				modelm.put("newsletter", outputRisultato);
				modelm.put("baseUrlLegalArchives", baseUrl);

				String msg = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "templateMail/mailinglist/comunicazioneEmail.vm", "UTF-8", modelm);

				MailingListRest mailingListRest = new MailingListRest();
				mailingListRest.setContenutoMail(msg);
				mailingListRest.setImage(risultato.getImage());
				mailingListRest.setOggettoMail("Presidio Normativo n° "+risultato.getNumero());
				mailingListRest.setEmail(email.getEmail());

				QueuePublisher.getInstance().sendMessage(mailingListRest);
				}
			
			newsletterService.inviaNewsletter(id, locale.getLanguage().toUpperCase());

			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "ok";
	}

}
