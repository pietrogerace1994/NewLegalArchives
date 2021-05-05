package eng.la.business;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

//@@DDS import com.filenet.api.core.Document;

import eng.la.model.Articolo;
import eng.la.model.Documento;
import eng.la.model.view.EmailView;
import eng.la.util.ListaPaginata;

public interface EmailService {
	public EmailView leggiEmail(long id) throws Throwable;

	public Collection<EmailView> leggiEmail() throws Throwable;

	public EmailView salvaEmail(EmailView emailView) throws Throwable;

	public void cancellaEmail(long id) throws Throwable;

	public void modificaEmail(EmailView emailView) throws Throwable;

	public void sendEmail(String from, String to, String object, String message)throws Throwable;

	public ListaPaginata<EmailView> cerca(String oggetto, String dal, String al, int numElementiPerPagina,
			int numeroPagina, String ordinamento, String tipoOrdinamento, String contenutoBreve, String comboCategoria) throws Throwable;

	public Documento aggiungiDocumento(Date dataCreazione, Long idAtto, MultipartFile file, String nomeFolder)
			throws Throwable;

	public void aggiungiDocumenti(Date dataCreazione, Articolo articolo, List<MultipartFile> files, String nomeFolder)
			throws Throwable;
	
	public List<Documento> leggiDocumenti(Long idEmail) throws Throwable;

	public void sendEmailWithCID(String from, String to, String object, String message, List<String> urlImage, List<File> list) throws Throwable;

	public void eliminaArticoliPerCategoria(long id) throws Throwable;
	
	
}
