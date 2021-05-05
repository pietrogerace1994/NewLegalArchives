package eng.la.persistence;

import java.util.List;

import eng.la.model.Articolo;
import eng.la.model.CategoriaMailinglist;
import eng.la.model.Newsletter;
import eng.la.model.NewsletterEmail;
import eng.la.model.RNewsletterArticolo;
import eng.la.model.StatoNewsletter;

public interface NewsletterDAO {
	public Newsletter leggiNewsletter(long id) throws Throwable;

	public List<Newsletter> leggiNewsletter() throws Throwable;

	public Newsletter salvaNewsletter(Newsletter vo) throws Throwable;

	public void cancellaNewsletter(long id, String lingua) throws Throwable;

	public void modificaNewsletter(Newsletter vo) throws Throwable;

	public List<Newsletter> cerca(String numero, String anno, String titolo, String stato, int elementiPerPagina, int numeroPagina,
			String ordinamento, String tipoOrdinamento) throws Throwable;

	public Long conta(String titolo) throws Throwable;

	//public List<DocumentoNewsletter> leggiNewsletterDocbyId(long id) throws Throwable;

	public void cancellaDocumento(long documentoId) throws Throwable;

	public RNewsletterArticolo aggiungiArticolo(RNewsletterArticolo rnewsArt) throws Throwable;

	public List<RNewsletterArticolo> leggiArticoliDocumento(long id) throws Throwable;

	public List<StatoNewsletter> listaStatoNewsletter(String lingua) throws Throwable;

	public void attivaNewsletter(Newsletter news) throws Throwable;

	public StatoNewsletter getStatoAttivo(String lingua) throws Throwable;
	
	public Integer getNewNumber() throws Throwable;

	public StatoNewsletter getStatoInviata(String upperCase) throws Throwable;

	public void inviaNewsletter(Newsletter news) throws Throwable;

	public List<CategoriaMailinglist> cercaCategorie(int numElementiPerPagina, int numeroPagina, String ordinamento) throws Throwable;

	public Long contaCategorie() throws Throwable;
	
	public CategoriaMailinglist leggiCategoria(long id) throws Throwable;

	public CategoriaMailinglist salvaCategoria(CategoriaMailinglist vo) throws Throwable;

	public void modificaCategoria(CategoriaMailinglist vo) throws Throwable;

	public void cancellaCategoria(long id) throws Throwable;

	public Long findColor(String colore) throws Throwable;

	public NewsletterEmail aggiungiEmail(NewsletterEmail nemail) throws Throwable;

	public List<NewsletterEmail> leggiEmails(long id) throws Throwable;

	public void cancellaFigliCategoria(long id) throws Throwable;

	public Long contaFigliCategoria(long id) throws Throwable;

	public List<Articolo> leggiArticoliDocumento() throws Throwable;

	public void rimuoviArticolo(RNewsletterArticolo articolo) throws Throwable;
	
}
