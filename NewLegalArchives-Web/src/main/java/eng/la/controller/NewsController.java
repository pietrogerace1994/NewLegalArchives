package eng.la.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eng.la.business.NewsService;
import eng.la.model.Articolo;
import eng.la.model.CategoriaMailinglist;
import eng.la.model.Documento;
import eng.la.model.Newsletter;
import eng.la.model.custom.NewsArticlePageHome;
import eng.la.model.custom.NewsletterArticleCustom;
import eng.la.model.custom.NewsletterArticleListCustom;
import eng.la.model.custom.NewsletterCustom;

@Controller("newsController")
public class NewsController {

	private static final String PAGINA_HOME="public-news/news-home";
	private static final String PAGINA_DETTAGLIO="public-news/news-dettaglio";
	private static final String PAGINA_ARCHIVIO="public-news/news-archivio";
	private static final String PAGINA_ARCHIVIO_ARTICOLI="public-news/news-archivio-articoli";
	private static final String PAGINA_ARCHIVIO_FILTRATO="public-news/news-archivio-filtrato";
	private static final String PAGINA_ARCHIVIO_ARTICOLI_FILTRATO="public-news/news-archivio-articoli-filtrato";
	private static final String PAGINA_ARTICLES_NEWSLETTER="public-news/news-articles-newsletter";
	private static final int ELEMENTI_PER_PAGINA=5;
	
	private static final Logger logger = Logger.getLogger(NewsController.class);
	
	@Autowired
	NewsService newsService;
	
	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
	
	
	@RequestMapping("/public/news/home")
	public String newsHome(Model model, Locale locale,
			HttpServletRequest request) throws Throwable {
		try{
			
		logger.debug("/public/news/home");
			
		List<CategoriaMailinglist> categoriaPadre = newsService.getCategoriaPadre(locale.getLanguage().toString().toUpperCase());
		
		logger.debug(categoriaPadre);
		
		model.addAttribute("ltsCategoriaPadre", categoriaPadre);	
		List<NewsArticlePageHome> articlePageHomes = newsService.getNewsArticlePageHome(locale.getLanguage().toString().toUpperCase());
		
		logger.debug(articlePageHomes);
	
		List<Articolo> articoloSlider=new ArrayList<Articolo>();
		
		for(NewsArticlePageHome h:articlePageHomes){
			
			if(h.getArticoloHighlights()!=null){
				
				articoloSlider.add(h.getArticoloHighlights());
			}
		}
		
		logger.debug(articoloSlider);
		 
		model.addAttribute("ltsArticoloSlider",articoloSlider);
		model.addAttribute("ltsArticlePageHome",articlePageHomes);
		
		}catch(Exception e){
			
			logger.error("Eccezione /public/news/home "+e);
		}
		
		return PAGINA_HOME;
	}
	
	
	@RequestMapping("/public/news/dettaglio")
	public String newsOpenArticle(@RequestParam("article") long article, Model model, Locale locale,
			HttpServletRequest request) throws Throwable {
		try{
		
			logger.debug("/public/news/dettaglio");
			
		List<CategoriaMailinglist> categoriaPadre = newsService.getCategoriaPadre(locale.getLanguage().toString().toUpperCase()); 
		model.addAttribute("ltsCategoriaPadre", categoriaPadre);
		
		logger.debug(categoriaPadre);
		
		Articolo articolo= newsService.getArticolo(article);
		model.addAttribute("articolo", articolo);
		
		logger.debug(categoriaPadre);
		
		Newsletter newsletter = newsService.getNewsletterFromArticoli(article);
		model.addAttribute("newsletter", newsletter);
		
		logger.debug(newsletter);
		
		List<Documento> allegati=newsService.leggiAllegati(article);
		model.addAttribute("allegati", allegati);
		
		}catch(Exception e){
			
			logger.error("Eccezione /public/news/dettaglio "+e);
		}
		
		return PAGINA_DETTAGLIO;
	}
	
	@RequestMapping("/public/news/archivio")
	public String newsOpenArchivio(Model model, Locale locale,
			HttpServletRequest request) throws Throwable {
		
		try{
			
		logger.debug("/public/news/archivio");
			
		List<CategoriaMailinglist> categoriaPadre  = newsService.getCategoriaPadre(locale.getLanguage().toString().toUpperCase());
		
		logger.debug(categoriaPadre);
		
		model.addAttribute("ltsCategoriaPadre", categoriaPadre);
		int anno=0;	
		Collection<Integer>collectionAnni=newsService.getAnniDisponibili();
		
		
		logger.debug("Anni disponibili "+collectionAnni);
		
		if(request.getParameter("cbanno")!=null && !request.getParameter("cbanno").equals("") && !request.getParameter("cbanno").equals("0"))
			anno=new Integer(request.getParameter("cbanno")).intValue();
		else if(collectionAnni!=null && collectionAnni.size()>0)
			anno = ((Integer)collectionAnni.iterator().next()).intValue();
		else if(anno==0)
			anno = Calendar.getInstance().get(Calendar.YEAR);
		
		logger.debug("Anno "+anno);
		
		String search="";
		if(request.getParameter("cbsearch")!=null && !request.getParameter("cbsearch").trim().equals(""))
		 search=request.getParameter("cbsearch").trim();
		
		logger.debug("search "+search);
		
		int pagina=1;
		if(request.getParameter("cbpage")!=null && !request.getParameter("cbpage").trim().equals(""))
			pagina=new Integer(request.getParameter("cbpage").trim()).intValue();
		
		logger.debug("pagina "+pagina);
		
		
		NewsletterCustom newsletterCustom=newsService.getAllNewsLetter(search,anno, ELEMENTI_PER_PAGINA, pagina);
		
		logger.debug(newsletterCustom);
		
		List<Newsletter> newsletters=newsletterCustom.getLstNewsletter();
		
		logger.debug(newsletters);
		
		model.addAttribute("totalePagina", Math.ceil(new Double(newsletterCustom.getTotale()>0?newsletterCustom.getTotale():1)/new Double(ELEMENTI_PER_PAGINA)));
		 
		
		model.addAttribute("paginaSelezionata", pagina);
		model.addAttribute("newsletterCustom", newsletterCustom);
		model.addAttribute("ltsNewsletter", newsletters);
		 
		model.addAttribute("ltsAnni", collectionAnni);
		model.addAttribute("annoSelezionato", new Integer(0));
		model.addAttribute("sottoCategoriaSelezionata", 0);
		model.addAttribute("idCatPadre", 0);
		
	}catch(Exception e){
		logger.error("Eccezione /public/news/archivio "+e);
		
	}
	
		return PAGINA_ARCHIVIO;
	}
	
	/**
	 * CR Varianti 3 - 3.3.5.	Miglioramento della ricerca articoli pubblicati (Presidio Normativo)
	 * @param model
	 * @param locale
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping("/public/news/archivio-articoli")
	public String openArchivioArticoli(Model model, Locale locale, HttpServletRequest request) throws Throwable {
		String methodName = "openArchivioArticoli() ";
		logger.info(methodName + " begin");
		try {
			List<CategoriaMailinglist> categoriaPadre = newsService
					.getCategoriaPadre(locale.getLanguage().toString().toUpperCase());
			model.addAttribute("ltsCategoriaPadre", categoriaPadre);
			int anno = 0;
			Collection<Integer> collectionAnni = newsService.getAnniDisponibili();
			logger.debug(methodName + "Anni disponibili " + collectionAnni);

			if (request.getParameter("cbanno") != null && !request.getParameter("cbanno").equals("")
					&& !request.getParameter("cbanno").equals("0"))
				anno = new Integer(request.getParameter("cbanno")).intValue();
			else if (collectionAnni != null && collectionAnni.size() > 0)
				anno = ((Integer) collectionAnni.iterator().next()).intValue();
			else if (anno == 0)
				anno = Calendar.getInstance().get(Calendar.YEAR);

			logger.debug(methodName + "Anno " + anno);
			String search = "";
			if (request.getParameter("cbsearch") != null && !request.getParameter("cbsearch").trim().equals(""))
				search = request.getParameter("cbsearch").trim();
			logger.debug(methodName + "search " + search);
			String categoria = null;
			if (request.getParameter("categoria") != null && !request.getParameter("categoria").trim().equals("") && !request.getParameter("categoria").trim().equals("0") && !request.getParameter("categoria").trim().equals("undefined"))
				categoria = request.getParameter("categoria").trim();
			logger.debug(methodName + "categoria " + categoria);
			String sottocategoria = null;
			if (request.getParameter("sottocategoria") != null && !request.getParameter("sottocategoria").trim().equals("") && !request.getParameter("sottocategoria").trim().equals("0") && !request.getParameter("sottocategoria").trim().equals("undefined"))
				sottocategoria = request.getParameter("sottocategoria").trim();
			logger.debug(methodName + "sottocategoria " + sottocategoria);
			int pagina = 1;
			if (request.getParameter("cbpage") != null && !request.getParameter("cbpage").trim().equals(""))
				pagina = new Integer(request.getParameter("cbpage").trim()).intValue();
			logger.debug(methodName + "pagina " + pagina);
			Map<Long, List<Articolo>> listArticoli = newsService.findArticolo(search, anno, ELEMENTI_PER_PAGINA,
					pagina, categoria, sottocategoria);
			List<Articolo> articoli = null;
			long totale = 0;
			for (Map.Entry<Long, List<Articolo>> entry : listArticoli.entrySet()) {
				articoli = entry.getValue();
				totale = entry.getKey();
			}
			if (categoria != null && !categoria.equals("")) {
			List<CategoriaMailinglist> sottoCategorie = newsService.getSottoCategoria(Long.parseLong(categoria),locale.getLanguage().toString().toUpperCase());
			logger.debug("sottoCategorie: "+sottoCategorie);
			model.addAttribute("ltsSottoCategorie", sottoCategorie);
			}
			logger.debug(methodName + "articoli " + articoli);
			model.addAttribute("totalePagina",
					Math.ceil(new Double(totale > 0 ? totale : 1) / new Double(ELEMENTI_PER_PAGINA)));

			model.addAttribute("paginaSelezionata", pagina);
			model.addAttribute("totale", totale);
			model.addAttribute("ltsArticoli", articoli);
			model.addAttribute("articoliCustom", listArticoli);
			model.addAttribute("ltsAnni", collectionAnni);
			model.addAttribute("annoSelezionato", anno);
			model.addAttribute("sottoCategoriaSelezionata", sottocategoria);
			model.addAttribute("idCatPadre", categoria);
			model.addAttribute("testoCercato", search);

		} catch (Exception e) {
			logger.error("Eccezione /public/news/archivio-articoli " + e);

		}
		logger.info(methodName + "end");
		return PAGINA_ARCHIVIO_ARTICOLI;
	}
	
	
	
	@RequestMapping(value="/public/news/archivio-filtrato",method=RequestMethod.POST)
	public String newsOpenArchivioFiltrato(@RequestParam("archivio") long archivio, Model model, Locale locale,
			HttpServletRequest request) throws Throwable {
		 try{
		
		logger.debug("/public/news/archivio-filtrato");
			 
		List<CategoriaMailinglist> categoriaPadre = newsService.getCategoriaPadre(locale.getLanguage().toString().toUpperCase());
		model.addAttribute("ltsCategoriaPadre", categoriaPadre);
		
		logger.debug("Categorie: "+categoriaPadre);
		
		List<CategoriaMailinglist> sottoCategorie = newsService.getSottoCategoria(archivio,locale.getLanguage().toString().toUpperCase());
		
		logger.debug("sottoCategorie: "+sottoCategorie);
		
		model.addAttribute("ltsSottoCategorie", sottoCategorie);
		model.addAttribute("idCatPadre", archivio);
		model.addAttribute("annoSelezionato", request.getParameter("archanno")!=null && !request.getParameter("archanno").equals("") ?new Integer(request.getParameter("archanno")):new Integer(0));
		 
		//----------
		int anno=0;	
		Collection<Integer>collectionAnni=newsService.getAnniDisponibili();
		
		logger.debug("Anni disponibili "+collectionAnni);
		
		if(request.getParameter("archanno")!=null && !request.getParameter("archanno").equals("") && !request.getParameter("archanno").equals("0"))
			anno=new Integer(request.getParameter("archanno")).intValue();
		else if(collectionAnni!=null && collectionAnni.size()>0)
			anno = ((Integer)collectionAnni.iterator().next()).intValue();
		else if(anno==0)
		anno = Calendar.getInstance().get(Calendar.YEAR);
		
		logger.debug("Anno "+anno);
		
		
		int pagina=1;
		if(request.getParameter("cbpage")!=null && !request.getParameter("cbpage").trim().equals(""))
			pagina=new Integer(request.getParameter("cbpage").trim()).intValue();
		
		logger.debug("pagina "+pagina);
		
		NewsletterArticleListCustom customList= newsService.getNewsletterArticleFormCategory_(archivio,anno, ELEMENTI_PER_PAGINA, pagina);
		
		logger.debug(customList);
		
		List<NewsletterArticleCustom> newsletterArticleCustoms=customList.getLstNewsletterArticleCustom();
		
		logger.debug(newsletterArticleCustoms);
		
		//List<NewsletterArticleCustom> newsletterArticleCustoms=newsService.getNewsletterArticleFormCategory(archivio,anno, 5, pagina);
		model.addAttribute("ltsNewsletterArticles", newsletterArticleCustoms);
		
		model.addAttribute("totalePagina", Math.ceil(new Double(customList.getTotale()>0?customList.getTotale():1)/new Double(ELEMENTI_PER_PAGINA)));
		 
		model.addAttribute("paginaSelezionata", pagina);
		model.addAttribute("ltsAnni", collectionAnni);
		model.addAttribute("sottoCategoriaSelezionata", 0);
		
		 }catch(Exception e){
			 
			 logger.error("Eccezione /public/news/archivio-filtrato "+e);
		 }
		 
		return PAGINA_ARCHIVIO_FILTRATO;
	
	}
	
	
	/**
	 * CR Varianti 3 - 3.3.5.	Miglioramento della ricerca articoli pubblicati (Presidio Normativo)
	 * @param archivio
	 * @param model
	 * @param locale
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/public/news/archivio-articoli-filtrato", method = RequestMethod.POST)
	public String newsOpenArchivioArticoliFiltrato(@RequestParam("archivio") long archivio, Model model, Locale locale,
			HttpServletRequest request) throws Throwable {
		String methodName="newsOpenArchivioArticoliFiltrato() ";
		logger.info(methodName+" begin");
		try {
			logger.debug(methodName+ "/public/news/archivio-filtrato");
			List<CategoriaMailinglist> categoriaPadre = newsService
					.getCategoriaPadre(locale.getLanguage().toString().toUpperCase());
			model.addAttribute("ltsCategoriaPadre", categoriaPadre);
			logger.debug(methodName+ "Categorie: " + categoriaPadre);
			List<CategoriaMailinglist> sottoCategorie = newsService.getSottoCategoria(archivio,
					locale.getLanguage().toString().toUpperCase());
			logger.debug(methodName+ "sottoCategorie: " + sottoCategorie);
			model.addAttribute("ltsSottoCategorie", sottoCategorie);
			model.addAttribute("idCatPadre", archivio);
			model.addAttribute("annoSelezionato",
					request.getParameter("archanno") != null && !request.getParameter("archanno").equals("")
							? new Integer(request.getParameter("archanno")) : new Integer(0));
			// ----------
			int anno = 0;
			Collection<Integer> collectionAnni = newsService.getAnniDisponibili();
			logger.debug(methodName+"Anni disponibili " + collectionAnni);
			if (request.getParameter("archanno") != null && !request.getParameter("archanno").equals("")
					&& !request.getParameter("archanno").equals("0"))
				anno = new Integer(request.getParameter("archanno")).intValue();
			else if (collectionAnni != null && collectionAnni.size() > 0)
				anno = ((Integer) collectionAnni.iterator().next()).intValue();
			else if (anno == 0)
				anno = Calendar.getInstance().get(Calendar.YEAR);
			int pagina = 1;
			if (request.getParameter("cbpage") != null && !request.getParameter("cbpage").trim().equals(""))
				pagina = new Integer(request.getParameter("cbpage").trim()).intValue();
			
			String search=null;
			if(request.getParameter("cbsearch")!=null && !request.getParameter("cbsearch").trim().equals(""))
			 search=request.getParameter("cbsearch").trim();
			
			Map<Long, List<Articolo>> mapArticoli = newsService.findArticolo(search, anno, ELEMENTI_PER_PAGINA, pagina,
					String.valueOf(archivio), null);
			List<Articolo> listaArticoli = null;
			long totPagina = 0;
			for (Map.Entry<Long, List<Articolo>> entry : mapArticoli.entrySet()) {
				listaArticoli = entry.getValue();
				totPagina = entry.getKey();
			}
			model.addAttribute("listaArticoli", listaArticoli);
			model.addAttribute("totalePagina",
					Math.ceil(new Double(totPagina > 0 ? totPagina : 1) / new Double(ELEMENTI_PER_PAGINA)));
			model.addAttribute("paginaSelezionata", pagina);
			model.addAttribute("ltsAnni", collectionAnni);
			model.addAttribute("sottoCategoriaSelezionata", 0);
			if (search != null)
			model.addAttribute("testoCercato", search);

		} catch (Exception e) {

			logger.error(methodName+"Eccezione /public/news/archivio-articoli-filtrato " + e);
		}
		logger.info(methodName+"end");
		return PAGINA_ARCHIVIO_ARTICOLI_FILTRATO;

	}
	
	
	
	
	@RequestMapping(value="/public/news/archivio-filtrato-category",method=RequestMethod.POST)
	public String newsOpenArchivioFiltratoSottoCategory(@RequestParam("archivio") long archivio,@RequestParam("archsottocategoria") long archsottocategoria, Model model, Locale locale,
			HttpServletRequest request) throws Throwable {
		
		try{
			
		logger.debug("/public/news/archivio-filtrato-category");
			
		List<CategoriaMailinglist> categoriaPadre = newsService.getCategoriaPadre(locale.getLanguage().toString().toUpperCase());
		model.addAttribute("ltsCategoriaPadre", categoriaPadre);
		
		logger.debug("Categorie: "+categoriaPadre);
		
		List<CategoriaMailinglist> sottoCategorie = newsService.getSottoCategoria(archivio,locale.getLanguage().toString().toUpperCase());
		model.addAttribute("ltsSottoCategorie", sottoCategorie);
		
		logger.debug("sottoCategorie: "+sottoCategorie);
		
		model.addAttribute("idCatPadre", archivio);
		model.addAttribute("annoSelezionato", request.getParameter("archanno")!=null?new Integer(request.getParameter("archanno")):new Integer(0));
		
		//--
		int anno=0;	
		Collection<Integer>collectionAnni=newsService.getAnniDisponibili();
		
		logger.debug("Anni disponibili "+collectionAnni);
		
		if(request.getParameter("archanno")!=null && !request.getParameter("archanno").equals("") && !request.getParameter("archanno").equals("0"))
			anno=new Integer(request.getParameter("archanno")).intValue();
		else if(collectionAnni!=null && collectionAnni.size()>0)
			anno = ((Integer)collectionAnni.iterator().next()).intValue();
		else if(anno==0)
		anno = Calendar.getInstance().get(Calendar.YEAR);
		
		logger.debug("Anno "+anno);
		
		int pagina=1;
		if(request.getParameter("cbpage")!=null && !request.getParameter("cbpage").trim().equals(""))
			pagina=new Integer(request.getParameter("cbpage").trim()).intValue();
		
		logger.debug("pagina "+pagina);
		
		NewsletterArticleListCustom customList= newsService.getNewsletterArticleFormCategorySottoCategory_(archivio,anno,archsottocategoria, ELEMENTI_PER_PAGINA, pagina);
		List<NewsletterArticleCustom> newsletterArticleCustoms=customList.getLstNewsletterArticleCustom();
		
		logger.debug(newsletterArticleCustoms);
		
		
		
		//List<NewsletterArticleCustom> newsletterArticleCustoms=newsService.getNewsletterArticleFormCategorySottoCategory(archivio,anno,archsottocategoria, 1000, 1);
		
		model.addAttribute("ltsNewsletterArticles", newsletterArticleCustoms);
		model.addAttribute("totalePagina", Math.ceil(new Double(customList.getTotale()>0?customList.getTotale():1)/new Double(ELEMENTI_PER_PAGINA)));
		model.addAttribute("paginaSelezionata", pagina);
		model.addAttribute("ltsAnni", collectionAnni);
		model.addAttribute("sottoCategoriaSelezionata", archsottocategoria);
		
		}catch(Exception e){
			
			 logger.error("Eccezione /public/news/archivio-filtrato-category "+e);
		}
		
		return PAGINA_ARCHIVIO_FILTRATO;
	}
	
	
	/**
	 * CR Varianti 3 - 3.3.5.	Miglioramento della ricerca articoli pubblicati (Presidio Normativo)
	 * @param archivio
	 * @param archsottocategoria
	 * @param model
	 * @param locale
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/public/news/archivio-filtrato-articoli-category", method = RequestMethod.POST)
	public String newsOpenArchivioFiltratoArticoliSottoCategory(@RequestParam("archivio") long archivio,
			@RequestParam("archsottocategoria") long archsottocategoria, Model model, Locale locale,
			HttpServletRequest request) throws Throwable {
		String methodName="newsOpenArchivioFiltratoArticoliSottoCategory() ";
		logger.info(methodName+" begin");
		try {

			logger.debug(methodName+"/public/news/archivio-filtrato-articoli-category");

			List<CategoriaMailinglist> categoriaPadre = newsService
					.getCategoriaPadre(locale.getLanguage().toString().toUpperCase());
			model.addAttribute("ltsCategoriaPadre", categoriaPadre);

			logger.debug(methodName+"Categorie: " + categoriaPadre);

			List<CategoriaMailinglist> sottoCategorie = newsService.getSottoCategoria(archivio,
					locale.getLanguage().toString().toUpperCase());
			model.addAttribute("ltsSottoCategorie", sottoCategorie);

			logger.debug(methodName+"sottoCategorie: " + sottoCategorie);

			model.addAttribute("idCatPadre", archivio);
			model.addAttribute("annoSelezionato", request.getParameter("archanno") != null
					? new Integer(request.getParameter("archanno")) : new Integer(0));

			// --
			int anno = 0;
			Collection<Integer> collectionAnni = newsService.getAnniDisponibili();

			logger.debug(methodName+"Anni disponibili " + collectionAnni);

			if (request.getParameter("archanno") != null && !request.getParameter("archanno").equals("")
					&& !request.getParameter("archanno").equals("0"))
				anno = new Integer(request.getParameter("archanno")).intValue();
			else if (collectionAnni != null && collectionAnni.size() > 0)
				anno = ((Integer) collectionAnni.iterator().next()).intValue();
			else if (anno == 0)
				anno = Calendar.getInstance().get(Calendar.YEAR);

			logger.debug(methodName+"Anno " + anno);

			int pagina = 1;
			if (request.getParameter("cbpage") != null && !request.getParameter("cbpage").trim().equals(""))
				pagina = new Integer(request.getParameter("cbpage").trim()).intValue();

			logger.debug(methodName+"pagina " + pagina);

			String search=null;
			if(request.getParameter("cbsearch")!=null && !request.getParameter("cbsearch").trim().equals(""))
			 search=request.getParameter("cbsearch").trim();
			
			
			Map<Long, List<Articolo>> mapArticoli = newsService.findArticolo(search, anno, ELEMENTI_PER_PAGINA, pagina,
					String.valueOf(archivio), String.valueOf(archsottocategoria));

			List<Articolo> listaArticoli = null;
			long totPagina = 0;
			for (Map.Entry<Long, List<Articolo>> entry : mapArticoli.entrySet()) {
				listaArticoli = entry.getValue();
				totPagina = entry.getKey();
			}
			model.addAttribute("listaArticoli", listaArticoli);
			model.addAttribute("totalePagina",
					Math.ceil(new Double(totPagina > 0 ? totPagina : 1) / new Double(ELEMENTI_PER_PAGINA)));
			model.addAttribute("paginaSelezionata", pagina);
			model.addAttribute("ltsAnni", collectionAnni);
			model.addAttribute("sottoCategoriaSelezionata", archsottocategoria);
			model.addAttribute("annoSelezionato", anno);
			if (search != null)
				model.addAttribute("testoCercato", search);
		} catch (Exception e) {

			logger.error(methodName+"Eccezione /public/news/archivio-filtrato-articoli-category " + e);
		}
		logger.info(methodName+"end");
		return PAGINA_ARCHIVIO_ARTICOLI_FILTRATO;
	}
	
	
	
	@RequestMapping(value="/public/news/articles-newsletter",method=RequestMethod.POST)
	public String articleFromNewsletter(@RequestParam("newsletterid") long newsletterid, Model model, Locale locale,
			HttpServletRequest request) throws Throwable {
		
		try{
			
		logger.debug("/public/news/articles-newsletter");	
			
		List<CategoriaMailinglist> categoriaPadre = newsService.getCategoriaPadre(locale.getLanguage().toString().toUpperCase());
		model.addAttribute("ltsCategoriaPadre", categoriaPadre);
		
		logger.debug("Categorie: "+categoriaPadre);
		
		int pagina=1;
		if(request.getParameter("cbpage")!=null && !request.getParameter("cbpage").trim().equals(""))
			pagina=new Integer(request.getParameter("cbpage").trim()).intValue();
		
		
		logger.debug("pagina "+pagina);
		
		 NewsletterArticleListCustom articleListCustom= newsService.getArticleFromNewsletterId(newsletterid, ELEMENTI_PER_PAGINA, pagina);
		 model.addAttribute("articleListCustom", articleListCustom);
		 
		 logger.debug(articleListCustom);
		 
		//List<CategoriaMailinglist> sottoCategorie = newsService.getSottoCategoria(archivio,locale.getLanguage().toString().toUpperCase());
		//model.addAttribute("ltsSottoCategorie", sottoCategorie);
		
		model.addAttribute("totalePagina", Math.ceil(new Double(articleListCustom.getTotale()>0?articleListCustom.getTotale():1)/new Double(ELEMENTI_PER_PAGINA)));
		model.addAttribute("paginaSelezionata", pagina);
		
		model.addAttribute("idCatPadre", 0);
		Collection<Integer>collectionAnni=newsService.getAnniDisponibili();
		model.addAttribute("ltsAnni", collectionAnni);
		model.addAttribute("annoSelezionato", new Integer(0));
		model.addAttribute("sottoCategoriaSelezionata", 0);
		model.addAttribute("newsletterid", newsletterid);
	
	}catch(Exception e){
		
		logger.error("Eccezione /public/news/articles-newsletter "+e);
	}
	
		return PAGINA_ARTICLES_NEWSLETTER;
	}
}
