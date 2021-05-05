package eng.la.business;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.Articolo;
import eng.la.model.CategoriaMailinglist;
import eng.la.model.Documento;
import eng.la.model.Newsletter;
import eng.la.model.custom.NewsArticlePageHome;
import eng.la.model.custom.NewsletterArticleCustom;
import eng.la.model.custom.NewsletterArticleListCustom;
import eng.la.model.custom.NewsletterCustom;
import eng.la.persistence.ArticoloDAO;
import eng.la.persistence.NewsDao;

@Service("newsService")
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsDao newsDao;
	@Autowired
	private ArticoloDAO articoloDAO;

	@Override
	public List<CategoriaMailinglist> getCategoriaPadre(String lingua) throws Throwable {

		return newsDao.getCategoriaPadre(lingua);
	}

	@Override
	public List<CategoriaMailinglist> getSottoCategoria(long idCategoria, String lingua) throws Throwable {

		return newsDao.getSottoCategoria(idCategoria, lingua);
	}

	@Override
	public Long conta(String titolo) throws Throwable {

		return newsDao.conta(titolo);
	}

	@Override
	public NewsletterCustom getAllNewsLetter(String titolo,int anno, int elementiPerPagina, int paginaNumenro) throws Throwable {
		return newsDao.getAllNewsLetter(titolo,anno, elementiPerPagina, paginaNumenro);
	}

	@Override
	public NewsletterCustom getNewsLetterFromCategory(long idCategory,int anno, int elementiPerPagina, int numeroPagina)
			throws Throwable {

		return newsDao.getNewsLetterFromCategory(idCategory, anno,elementiPerPagina, numeroPagina);
	}

	@Override
	public NewsletterArticleListCustom getArticleFromNewsletterId(long id, int elementiPerPagina, int paginaNumenro)
			throws Throwable {
		return newsDao.getArticleFromNewsletterId(id, elementiPerPagina, paginaNumenro);
	}

	@Override
	public List<NewsArticlePageHome> getNewsArticlePageHome(String lingua) throws Throwable {
		 
		return newsDao.getNewsArticlePageHome(lingua);
	}
	
	@Override
	public Articolo getArticolo(long idArticolo) throws Throwable {
		 
		return newsDao.getArticolo(idArticolo);
	}
	
	@Override
	public Newsletter getNewsletterFromArticoli(long idArticle) throws Throwable {
		 
		return newsDao.getNewsletterFromArticoli(idArticle);
	}
	
	@Override
	public List<NewsletterArticleCustom> getNewsletterArticleFormCategory(long idCategory,int anno, int elementiPerPagina,
			int numeroPagina) throws Throwable {
		 
		return newsDao.getNewsletterArticleFormCategory(idCategory,anno, elementiPerPagina, numeroPagina);
	}
	
	@Override
	public Collection<Integer> getAnniDisponibili() throws Throwable {
	 
		return newsDao.getAnniDisponibili();
	}
	
	@Override
	public List<NewsletterArticleCustom> getNewsletterArticleFormCategorySottoCategory(long idCategory,int anno,
			long idSottoCategory, int elementiPerPagina, int numeroPagina) throws Throwable {
		 
		return newsDao.getNewsletterArticleFormCategorySottoCategory(idCategory, anno,idSottoCategory, elementiPerPagina, numeroPagina);
	}

	@Override
	public NewsletterArticleListCustom getNewsletterArticleFormCategory_(long idCategory, int anno,
			int elementiPerPagina, int numeroPagina) throws Throwable {
		 
		return newsDao.getNewsletterArticleFormCategory_(idCategory, anno, elementiPerPagina, numeroPagina);
	}
	
	@Override
	public NewsletterArticleListCustom getNewsletterArticleFormCategorySottoCategory_(long idCategory, int anno,
			long idSottoCategory, int elementiPerPagina, int numeroPagina) throws Throwable {
		 
		return newsDao.getNewsletterArticleFormCategorySottoCategory_(idCategory, anno, idSottoCategory, elementiPerPagina, numeroPagina);
	}
	@Override
	public List<Documento> leggiAllegati(long idArticolo) throws Throwable {
		 
		return newsDao.leggiAllegati(idArticolo);
	}
	
	
	@Override
	public long countArticoli(String titolo, int anno) throws ParseException {
		return this.articoloDAO.countArticoli(titolo, anno, null, null);
	}
	
	@Override
	public Map<Long, List<Articolo>> findArticolo(String titolo,int anno,int elementiPerPagina, int paginaNumenro) throws ParseException {
		return this.articoloDAO.findArticoli(titolo, anno, elementiPerPagina, paginaNumenro, null,null);	
	}

	@Override
	public Map<Long, List<Articolo>> findArticolo(String titolo, int anno, int elementiPerPagina, int paginaNumenro,
			String categoria, String sottocategoria) throws ParseException {
		return this.articoloDAO.findArticoli(titolo, anno, elementiPerPagina, paginaNumenro, categoria, sottocategoria);
	}

	@Override
	public long countArticoli(String titolo, int anno, String categoria, String sottocategoria) throws ParseException {
		return this.articoloDAO.countArticoli(titolo, anno, categoria, sottocategoria);
	}
	
	
	
	
}
