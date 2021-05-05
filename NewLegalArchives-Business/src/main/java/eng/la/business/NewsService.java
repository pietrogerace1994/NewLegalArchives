package eng.la.business;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import eng.la.model.Articolo;
import eng.la.model.CategoriaMailinglist;
import eng.la.model.Documento;
import eng.la.model.Newsletter;
import eng.la.model.custom.NewsArticlePageHome;
import eng.la.model.custom.NewsletterArticleCustom;
import eng.la.model.custom.NewsletterArticleListCustom;
import eng.la.model.custom.NewsletterCustom;

public interface NewsService {

List<CategoriaMailinglist> getCategoriaPadre(String lingua)throws Throwable;
List<CategoriaMailinglist> getSottoCategoria(long idCategoria ,String lingua)throws Throwable;
public Long conta(String titolo) throws Throwable;
public NewsletterCustom getAllNewsLetter(String titolo,int anno,int elementiPerPagina, int paginaNumenro) throws Throwable;
public NewsletterCustom getNewsLetterFromCategory(long idCategory,int anno,int elementiPerPagina, int numeroPagina) throws Throwable;
public NewsletterArticleListCustom getArticleFromNewsletterId(long id,int elementiPerPagina, int paginaNumenro) throws Throwable;
public List<NewsArticlePageHome> getNewsArticlePageHome(String lingua) throws Throwable ;
public Articolo getArticolo(long idArticolo) throws Throwable;
public Newsletter getNewsletterFromArticoli(long idArticle) throws Throwable;
public List<NewsletterArticleCustom> getNewsletterArticleFormCategory(long idCategory,int anno,int elementiPerPagina, int numeroPagina) throws Throwable;
public Collection<Integer> getAnniDisponibili() throws Throwable;
public List<NewsletterArticleCustom> getNewsletterArticleFormCategorySottoCategory(long idCategory,int anno,long idSottoCategory,int elementiPerPagina, int numeroPagina) throws Throwable;

public NewsletterArticleListCustom getNewsletterArticleFormCategory_(long idCategory,int anno,int elementiPerPagina, int numeroPagina) throws Throwable;
public NewsletterArticleListCustom getNewsletterArticleFormCategorySottoCategory_(long idCategory,int anno,long idSottoCategory,int elementiPerPagina, int numeroPagina) throws Throwable;
public List<Documento> leggiAllegati(long idArticolo) throws Throwable;

public long countArticoli(String titolo, int anno) throws ParseException;
public Map<Long, List<Articolo>> findArticolo(String titolo,int anno,int elementiPerPagina, int paginaNumenro) throws ParseException;
public Map<Long, List<Articolo>> findArticolo(String titolo,int anno,int elementiPerPagina, int paginaNumenro, String categoria, String sottocategoria) throws ParseException;
public long countArticoli(String titolo,int anno, String categoria, String sottocategoria) throws ParseException;
}
