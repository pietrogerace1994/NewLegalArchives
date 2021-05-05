package eng.la.business;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import eng.la.persistence.DocumentaleDdsCryptDAOImpl;
import eng.la.persistence.DocumentaleDdsDAO;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//@@DDS import com.filenet.api.core.Folder;

import eng.la.model.Articolo;
import eng.la.model.Documento;
import eng.la.model.DocumentoArticolo;
import eng.la.model.view.EmailView;
//@@DDS import eng.la.persistence.DocumentaleDAO;
import eng.la.persistence.DocumentoDAO;
import eng.la.persistence.EmailDAO;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("emailService")
public class EmailServiceImpl extends BaseService<Articolo, EmailView> implements EmailService {
	private static final Logger logger = Logger.getLogger(EmailServiceImpl.class);
	@Autowired
	private EmailDAO dao;

	@Autowired
	private JavaMailSender mailSender;

	/*@@DDS
	@Autowired
	private DocumentaleDAO documentaleDAO;
	*/
	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;

	@Autowired
	private DocumentoDAO documentoDAO;
	
	@Override
	public EmailView leggiEmail(long id) throws Throwable {
		Articolo vo = dao.leggiEmail(id);
		if (vo != null) {
			EmailView view = new EmailView();
			view.setVo(vo);
			return view;
		}
		return null;
	}

	@Override
	public Collection<EmailView> leggiEmail() throws Throwable {
		List<Articolo> lvo = dao.leggiEmail();
		if (lvo != null) {
			List<EmailView> lview = new ArrayList<EmailView>();
			for (Articolo vo : lvo) {
				EmailView view = new EmailView();
				view.setVo(vo);
				lview.add(view);
			}
			return lview;
		}
		return null;
	}

	@Override
	@Transactional
	public void modificaEmail(EmailView emailView) throws Throwable {
		
		dao.modificaEmail(emailView.getVo());
		logger.debug("@@DDS modificaEmail _______________");
		if(emailView.getDocumentiDaEliminare()!=null){
			for(String uuid : emailView.getDocumentiDaEliminare()){
				//@@DDS documentaleDAO.eliminaDocumento(uuid);
				documentaleDdsDAO.eliminaDocumento(uuid);
				documentoDAO.cancella(uuid);
				
				Documento doc = documentoDAO.leggi(uuid);
				
				dao.cancellaDocumento(doc.getId());
			}
		}
		if(emailView.getFiles().get(0).getSize()!=0)
			aggiungiDocumenti(DateUtil.toDate(emailView.getDataEmail()), emailView.getVo(), emailView.getFiles(), "ARTICOLO");
	}
 
	@Override
	@Transactional
	public void cancellaEmail(long id) throws Throwable {
		logger.debug("@@DDS cancellaEmail _______________");
		//@@DDS documentaleDAO.eliminaDocumenti(Long.toString(id), FileNetClassNames.PRESIDIO_NORMATIVO_DOCUMENT);
		documentaleDdsDAO.eliminaDocumenti(Long.toString(id), FileNetClassNames.PRESIDIO_NORMATIVO_DOCUMENT);
		
		List<DocumentoArticolo> doci = dao.leggiArticoloDocbyId(id);
		
		for(DocumentoArticolo doc : doci){
			
			documentoDAO.cancella(doc.getDocumento().getUuid());
			dao.cancellaDocumento(doc.getDocumento().getId());	
		}
		dao.cancellaEmail(id);
	}

	@Override
	public EmailView salvaEmail(EmailView emailView) throws Throwable {
		
		Articolo vo = dao.salvaEmail(emailView.getVo());
	
		if(emailView.getFiles().get(0).getSize()!=0)
			aggiungiDocumenti(DateUtil.toDate(emailView.getDataEmail()), vo, emailView.getFiles(), "ARTICOLO");

		return convertiVoInView(vo, leggiClassVO(), leggiClassView());  
	}
 

	@Override
	protected Class<Articolo> leggiClassVO() { 
		return Articolo.class;
	}

	@Override
	protected Class<EmailView> leggiClassView() {
		return EmailView.class;
	}

	@Override
	public ListaPaginata<EmailView> cerca(String oggetto, String dal, String al, int elementiPerPagina,
			int numeroPagina, String ordinamento, String tipoOrdinamento, String contenutoBreve, String categoria) throws Throwable {
		List<Articolo> lista = dao.cerca(oggetto,  dal, al, elementiPerPagina, numeroPagina, ordinamento,
				tipoOrdinamento, contenutoBreve, categoria);
		
		
		List<EmailView> listaView = convertiVoInView(lista);
		
		System.out.println("SERVICE: "+listaView);
		
		ListaPaginata<EmailView> listaRitorno = new ListaPaginata<EmailView>();
		Long conta = dao.conta(oggetto, dal, al);
		
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(tipoOrdinamento);
		return listaRitorno;
	}

	@Override
	public void sendEmail(String from, String to, String object, String message) throws Throwable {
		MimeMessage msg = mailSender.createMimeMessage();
		msg.setFrom(new InternetAddress(from)); 
	    msg.addRecipient( RecipientType.TO, new InternetAddress(to) ); 
        msg.setSubject( object ); 

	    String html = message;
	    msg.setContent( html, "text/html" );
	    mailSender.send(msg);
	}
	
	public void imgUpload(MimeMultipart multipart,String fileName) throws MessagingException
	   {
	     
	     
	   }
	
	
	@Override
	public void sendEmailWithCID(String from, String to, String object, String message, List<String> urlImage, List<File> files) throws Throwable {
		
		
		MimeMessage msg = mailSender.createMimeMessage();
		msg.setFrom(new InternetAddress(from)); 
	    msg.addRecipient( RecipientType.TO, new InternetAddress(to) ); 
        msg.setSubject( object ); 

        MimeMultipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
	    String html = message;
	    messageBodyPart.setContent( html, "text/html" );
	    
	    multipart.addBodyPart(messageBodyPart);

	    //IMAGE
	    // second part (the image)
	    
	    for(File file : files)
        {
	    	
	    	messageBodyPart = new MimeBodyPart();
        
	    	DataSource fds = new FileDataSource(file);
	    	
        
	    	messageBodyPart.setDataHandler(new DataHandler(fds));
	    	messageBodyPart.setHeader("Content-ID", "<"+file.getName()+">");
	    	
	    	multipart.addBodyPart(messageBodyPart);
        
        }
        msg.setContent(multipart);
	    
	    mailSender.send(msg);
	}
	
	@Override
	public Documento aggiungiDocumento(Date dataCreazione,Long idEmail, MultipartFile file,String nomeFolder) throws Throwable {
		
		String uuid =""+UUID.randomUUID()+"";  
		String cartellaPadreEmail= FileNetUtil.getEmailCartellaPadre(dataCreazione); 
		
		String nomeCartella = nomeFolder.trim().toUpperCase()+"-"+idEmail;
		
		String nomeClasseCartella = FileNetClassNames.PRESIDIO_NORMATIVO_FOLDER;
		
		Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(idEmail+""));
		logger.debug("@@DDS aggiungiDocumento _______________");
		//@@DDS documentaleDAO.verificaCreaPercorsoCartella(cartellaPadreEmail);
		documentaleDdsDAO.verificaCreaPercorsoCartella(cartellaPadreEmail);

		//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaPadreEmail);
		Folder cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaPadreEmail);
		
		Folder cartellaPadreDoc;
		
		//@@DDS cartellaPadreDoc = documentaleDAO.leggiCartella(cartellaPadreEmail+nomeCartella);
		cartellaPadreDoc = documentaleDdsDAO.leggiCartella(cartellaPadreEmail+nomeCartella);
		
		if(cartellaPadreDoc == null){
			//@@DDS cartellaPadreDoc = documentaleDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
			cartellaPadreDoc = documentaleDdsDAO.creaCartella(uuid, cartellaPadre+nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
		}

		byte[] contenuto = file.getBytes();
		//@@DDS documentaleDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.ARTICOLO_DOCUMENT, file.getContentType(), proprietaDocumento, cartellaPadreDoc, contenuto);
		documentaleDdsDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.ARTICOLO_DOCUMENT, file.getContentType(), proprietaDocumento, cartellaPadreDoc.getFolderPath(), contenuto);
    
		//inserisce la ciave in tabella documento
		Documento doc=documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.ARTICOLO_DOCUMENT, file.getOriginalFilename(), file.getContentType());
		
		return doc;
	}
	
	@Override
	public void aggiungiDocumenti(Date dataCreazione,Articolo articolo, List<MultipartFile> files,String nomeFolder) throws Throwable {
		
		String uuid =""+UUID.randomUUID()+"";  
		String cartellaPadreEmail= FileNetUtil.getEmailCartellaPadre(dataCreazione); 
		
		String nomeCartella = articolo.getId()+"-"+nomeFolder.trim().toUpperCase();
		
		String nomeClasseCartella = FileNetClassNames.PRESIDIO_NORMATIVO_FOLDER;
		
		Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(articolo.getId()+""));
		logger.debug("@@DDS aggiungiDocumenti _______________"+ cartellaPadreEmail);
		//@@DDS documentaleDAO.verificaCreaPercorsoCartella(cartellaPadreEmail);
		documentaleDdsDAO.verificaCreaPercorsoCartella(cartellaPadreEmail);
		
		//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaPadreEmail);
		Folder cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaPadreEmail);
		
		Folder cartellaPadreDoc=null;
		
		try {
			//@@DDS cartellaPadreDoc = documentaleDAO.leggiCartella(cartellaPadreEmail+nomeCartella);
			cartellaPadreDoc = documentaleDdsDAO.leggiCartella(cartellaPadreEmail+nomeCartella);
		} catch (Exception e) {
			
		}
		
		if(cartellaPadreDoc == null){
			//@@DDS cartellaPadreDoc = documentaleDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
			cartellaPadreDoc = documentaleDdsDAO.creaCartella(uuid, cartellaPadreEmail+nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
		}
		
		for(MultipartFile file : files){
			
			if(!file.isEmpty()){
			
				String uuid2 =""+UUID.randomUUID()+"";

				byte[] contenuto = file.getBytes();
				//@@DDS documentaleDAO.creaDocumento(uuid2, file.getOriginalFilename(), FileNetClassNames.ARTICOLO_DOCUMENT, file.getContentType(), proprietaDocumento, cartellaPadreDoc, contenuto);
				documentaleDdsDAO.creaDocumento(uuid2, file.getOriginalFilename(), FileNetClassNames.ARTICOLO_DOCUMENT, file.getContentType(), proprietaDocumento, cartellaPadreDoc.getFolderPath(), contenuto);
    
				//inserisce la ciave in tabella documento
				Documento doc=documentoDAO.creaDocumentoDB(uuid2, FileNetClassNames.ARTICOLO_DOCUMENT, file.getOriginalFilename(), file.getContentType());
				
				documentoDAO.creaDocumentoArticolo(doc, articolo);
			}
		}
		
	}

	@Override
	public List<Documento> leggiDocumenti(Long idEmail) throws Throwable {
		List<Documento> toReturn = new ArrayList<Documento>();
		
		//Articolo articolo = dao.leggiEmail(idEmail);
		
		List<DocumentoArticolo> docArt = dao.leggiArticoloDocbyId(idEmail);
		
		for(DocumentoArticolo doc : docArt){
				toReturn.add(doc.getDocumento());
		}
		
		
		return toReturn;
	}

	@Override
	public void eliminaArticoliPerCategoria(long id) throws Throwable {
		dao.eliminaArticoliPerCategoria(id);
		
	}
}
