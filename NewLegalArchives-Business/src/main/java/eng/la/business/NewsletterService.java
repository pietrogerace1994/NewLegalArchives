package eng.la.business;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

//@@DDS import com.filenet.api.core.Document;

import eng.la.model.Articolo;
import eng.la.model.Documento;
import eng.la.model.NewsletterEmail;
import eng.la.model.RNewsletterArticolo;
import eng.la.model.view.CategoriaMailinglistView;
import eng.la.model.view.NewsletterView;
import eng.la.model.view.StatoNewsletterView;
import eng.la.util.ListaPaginata;

public interface NewsletterService {
	
	public NewsletterView leggiNewsletter(long id) throws Throwable;

	public Collection<NewsletterView> leggiNewsletter() throws Throwable;

	public NewsletterView salvaNewsletter(NewsletterView newsletterView) throws Throwable;

	public void cancellaNewsletter(long id , String lingua) throws Throwable;

	public void modificaNewsletter(NewsletterView newsletterView) throws Throwable;

	public void sendNewsletter(String from, String to, String object, String message)throws Throwable;

	public ListaPaginata<NewsletterView> cerca(String numero, String anno, String titolo, String stato, int numElementiPerPagina,
			int numeroPagina, String ordinamento, String tipoOrdinamento) throws Throwable;

	public void preparaInviaComunicazione(String[] newsletterIdsArray) throws Throwable;

	public void aggiungiDocumento(Date dataCreazione, Long idAtto, MultipartFile file, String nomeFolder, String uuid)
			throws Throwable;

	public void aggiungiDocumenti(Date dataCreazione, Articolo articolo, List<MultipartFile> files, String nomeFolder)
			throws Throwable;
	
	public List<Documento> leggiDocumenti(Long idNewsletter) throws Throwable;

	public byte[] leggiCopertina(Documento copertina) throws Throwable;
	
	public List<RNewsletterArticolo> leggiArticoli(long id)throws Throwable;
	
	public List<StatoNewsletterView> listaStatoNewsletter(String lingua) throws Throwable;

	public void preparaAnteprimaComunicazione(String newsletterIdsArray) throws Throwable;

	public void attivaNewsletter(long id, String lingua) throws Throwable;
	
	public int getNewNumber() throws Throwable;

	public void inviaNewsletter(long id, String upperCase) throws Throwable;

	public List<NewsletterEmail> leggiEmails(long id) throws Throwable;

	public List<Articolo> leggiArticoliDocumento() throws Throwable;
}
