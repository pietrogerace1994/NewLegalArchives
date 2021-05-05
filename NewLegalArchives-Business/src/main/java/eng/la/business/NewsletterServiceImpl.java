package eng.la.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import eng.la.persistence.DocumentaleDdsDAO;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import eng.la.model.Articolo;
import eng.la.model.Documento;
import eng.la.model.Newsletter;
import eng.la.model.NewsletterEmail;
import eng.la.model.RNewsletterArticolo;
import eng.la.model.StatoNewsletter;
import eng.la.model.view.NewsletterView;
import eng.la.model.view.StatoNewsletterView;
//@@DDS import eng.la.persistence.DocumentaleDAO;
import eng.la.persistence.NewsletterDAO;
import eng.la.util.ListaPaginata;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("newsletterService")
public class NewsletterServiceImpl extends BaseService<Newsletter,NewsletterView> implements NewsletterService {
	private static final Logger logger = Logger.getLogger(NewsletterServiceImpl.class);
	@Autowired
	private NewsletterDAO dao;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailService emailService;

	@Autowired
	private MailinglistService mailinglistService;

/*@@DDS	@Autowired
	private DocumentaleDAO documentaleDAO;
*/
	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;

	@Override
	public NewsletterView leggiNewsletter(long id) throws Throwable {
		Newsletter vo = dao.leggiNewsletter(id);

		if (vo != null) {
			NewsletterView view = new NewsletterView();
			view.setVo(vo);
			return view;
		}
		return null;
	}

	public List<RNewsletterArticolo> leggiArticoli(long id)throws Throwable{
		List<RNewsletterArticolo> articoli =  dao.leggiArticoliDocumento(id);
		return articoli;
	}

	@Override
	@Transactional
	public NewsletterView salvaNewsletter(NewsletterView newsletterView) throws Throwable {

		Newsletter vo = newsletterView.getVo();
		Newsletter voOut = dao.salvaNewsletter(vo);

		for(String id : newsletterView.getArticoliAggiunti()){
			RNewsletterArticolo rnewsArt = new RNewsletterArticolo();
			rnewsArt.setArticolo(emailService.leggiEmail(Long.parseLong(id)).getVo());
			rnewsArt.setNewsletter(voOut);
			dao.aggiungiArticolo(rnewsArt);
		}
		
		for(String email : newsletterView.getEmail()){
			if(!email.isEmpty()){
				NewsletterEmail nemail = new NewsletterEmail();
				nemail.setNewsletter(voOut);
				nemail.setEmail(email);
				dao.aggiungiEmail(nemail);
			}
		}
		return (NewsletterView) convertiVoInView(voOut, leggiClassVO(), leggiClassView());  
	}


	@Override
	protected Class<Newsletter> leggiClassVO() { 
		return Newsletter.class;
	}

	@Override
	protected Class<NewsletterView> leggiClassView() {
		return NewsletterView.class;
	}

	@Override
	public void sendNewsletter(String from, String to, String object, String message) throws Throwable {
		MimeMessage msg = mailSender.createMimeMessage();
		msg.setFrom(new InternetAddress(from)); 
		msg.addRecipient( RecipientType.TO, new InternetAddress(to) ); 
		msg.setSubject( object ); 

		String html = message;
		msg.setContent( html, "text/html" );
		mailSender.send(msg);
	}
	
	/*private void caricaMappaRubricaPerComunicazione(Map<Long, Set<String>> mappaEmailRubrica, String[] emailIdsArray) throws Throwable {
		for( String ids : emailIdsArray ){
			Set<String> setRubrica = new HashSet<String>();
			EmailView view = emailService.leggiEmail(NumberUtils.toLong(ids));
			if( view != null && view.getVo() != null){
				 CategoriaMailinglist categoria = view.getVo().getCategoria();
				 CategoriaMailinglist sottocategoria = view.getVo().getSottoCategoria();
				 Collection<MailinglistView> mailinglistaDaCategoria = mailinglistService.leggiMailinglist(categoria.getCodGruppoLingua());
				 Collection<MailinglistView> mailinglistaDaSottocategoria = sottocategoria != null ? mailinglistService.leggiMailinglist(sottocategoria.getCodGruppoLingua()) : null;
				  
				 if( mailinglistaDaCategoria != null && mailinglistaDaCategoria.size() > 0){
					 for(MailinglistView tmp : mailinglistaDaCategoria){
						 Set<MailinglistDettaglio> listaDettaglio = tmp.getVo().getMailinglistDettaglio();
						 if( listaDettaglio != null && listaDettaglio.size() > 0 ){
							 for(MailinglistDettaglio tmpDettaglio : listaDettaglio){ 
								 setRubrica.add(tmpDettaglio.getRubrica().getEmail());
							 }
						 }
					 }
				 }
				 
				 if( mailinglistaDaSottocategoria != null && mailinglistaDaSottocategoria.size() > 0){
					 for(MailinglistView tmp : mailinglistaDaSottocategoria){
						 Set<MailinglistDettaglio> listaDettaglio = tmp.getVo().getMailinglistDettaglio();
						 if( listaDettaglio != null && listaDettaglio.size() > 0 ){
							 for(MailinglistDettaglio tmpDettaglio : listaDettaglio){ 
								 setRubrica.add(tmpDettaglio.getRubrica().getEmail());
							 }
						 }
					 }
				 }
			}
			mappaEmailRubrica.put(NumberUtils.toLong(ids), setRubrica);
		}
	}*/
	
//		@Override
//		public void preparaInviaComunicazione(String[] newsletterIdsArray) throws Throwable {		
//			Map<Long, Set<String>> mappaNewsletterRubrica = new HashMap<Long, Set<String>>();
//			caricaMappaRubricaPerComunicazione(mappaNewsletterRubrica, newsletterIdsArray);
//			Set<String> setRubricaMerged = new HashSet<String>();
//			mergeRubricaSet(mappaNewsletterRubrica.values(), setRubricaMerged);
//			 
//			for( String rubricaNewsletter : setRubricaMerged ){
//				List<Comunicazione> newsletterComunicazione = new ArrayList<Comunicazione>();
//				for( String ids : newsletterIdsArray ){ 
//					//verifico se l'utente deve ricevere la mail corrente nella comunicazione
//					if( verificoMailComunicazioneUtente(rubricaNewsletter, ids, mappaNewsletterRubrica) ){
//						 NewsletterView view = leggiNewsletter(NumberUtils.toLong(ids));
//						 CategoriaMailinglist categoria = view.getVo().getCategoria();
//						 CategoriaMailinglist sottocategoria = view.getVo().getSottoCategoria();
//						 if( view != null && view.getVo() != null){
//							 Comunicazione c  = new Comunicazione();  
//							 c.setCategoria(categoria.getNomeCategoria()); 
//							 c.setSottocategoria(sottocategoria == null ? "" : sottocategoria.getNomeCategoria()); 
//							 c.setOggetto(view.getVo().getOggetto());
//							 c.setContenutoBreve(view.getVo().getContenutoBreve());
//							 c.setId(view.getVo().getId());
//							 newsletterComunicazione.add(c);
//						 }
//					}
//				} 
//				
//				Map model = new HashMap();
//	            model.put("newsletterList", newsletterComunicazione);
//	
//	            String message = VelocityEngineUtils.mergeTemplateIntoString(
//	                velocityEngine, "templateMail/mailinglist/comunicazione.vm", "UTF-8", model);
//	            
//	            MailingListRest mailingListRest = new MailingListRest();
//	            mailingListRest.setContenutoMail(message);
//	            mailingListRest.setOggettoMail("NEWSLETTER");
//	            mailingListRest.setNewsletter(rubricaNewsletter);
//	            
//	            QueuePublisher.getInstance().sendMessage(mailingListRest);
//			} 
//			 
//		}
		
//		@Override
//		public void preparaAnteprimaComunicazione(String newsletterIdsArray) throws Throwable {		
//			 
//			for( String rubricaNewsletter : setRubricaMerged ){
//				List<Comunicazione> newsletterComunicazione = new ArrayList<Comunicazione>();
//				for( String ids : newsletterIdsArray ){ 
//					//verifico se l'utente deve ricevere la mail corrente nella comunicazione
//					if( verificoMailComunicazioneUtente(rubricaNewsletter, ids, mappaNewsletterRubrica) ){
//						 NewsletterView view = leggiNewsletter(NumberUtils.toLong(ids));
//						 CategoriaMailinglist categoria = view.getVo().getCategoria();
//						 CategoriaMailinglist sottocategoria = view.getVo().getSottoCategoria();
//						 if( view != null && view.getVo() != null){
//							 Comunicazione c  = new Comunicazione();  
//							 c.setCategoria(categoria.getNomeCategoria()); 
//							 c.setSottocategoria(sottocategoria == null ? "" : sottocategoria.getNomeCategoria()); 
//							 c.setOggetto(view.getVo().getOggetto());
//							 c.setContenutoBreve(view.getVo().getContenutoBreve());
//							 c.setId(view.getVo().getId());
//							 newsletterComunicazione.add(c);
//						 }
//					}
//				} 
//				
//				Map model = new HashMap();
//	            model.put("newsletterList", newsletterComunicazione);
//	            model.put("copertina", value)
//	
//	            String message = VelocityEngineUtils.mergeTemplateIntoString(
//	                velocityEngine, "templateMail/mailinglist/comunicazione.vm", "UTF-8", model);
//	            
//	            MailingListRest mailingListRest = new MailingListRest();
//	            mailingListRest.setContenutoMail(message);
//	            mailingListRest.setOggettoMail("NEWSLETTER");
//	            mailingListRest.setNewsletter(rubricaNewsletter);
//	            
//	            QueuePublisher.getInstance().sendMessage(mailingListRest);
//			} 
//			 
//		}

	/*private boolean verificoMailComunicazioneUtente(String rubricaNewsletter, String ids, Map<Long, Set<String>> mappaNewsletterRubrica) {
		Set<String> setRubrica = mappaNewsletterRubrica.get(NumberUtils.toLong(ids));
		return setRubrica.contains(rubricaNewsletter);
	}

	private void mergeRubricaSet(Collection<Set<String>> values, Set<String> setRubricaMerged) { 
		for(Set<String> setRubrica : values){
			setRubricaMerged.addAll(setRubrica);
		}
	}*/






	@Override
	public void aggiungiDocumento(Date dataCreazione,Long idNewsletter, MultipartFile file,String nomeFolder, String uuid) throws Throwable {


		String cartellaPadreNewsletter= FileNetUtil.getCopertinaCartellaPadre(dataCreazione); 

		String nomeCartella = idNewsletter+"-"+nomeFolder.trim().toUpperCase();

		String nomeClasseCartella = FileNetClassNames.COPERTINA_NEWSLETTER_FOLDER;

		Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(idNewsletter+""));

		//@@DDS documentaleDAO.verificaCreaPercorsoCartella(cartellaPadreNewsletter);
		documentaleDdsDAO.verificaCreaPercorsoCartella(cartellaPadreNewsletter);

		//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaPadreNewsletter);
		Folder cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaPadreNewsletter);

		Folder cartellaPadreDoc;

		//@@DDS cartellaPadreDoc = documentaleDAO.leggiCartella(cartellaPadreNewsletter+nomeCartella);
		cartellaPadreDoc = documentaleDdsDAO.leggiCartella(cartellaPadreNewsletter+nomeCartella);
		logger.info("@@DDS NewsletterServiceImpl " + cartellaPadre);
		if(cartellaPadreDoc == null){
			//@@DDS cartellaPadreDoc = documentaleDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
			cartellaPadreDoc = documentaleDdsDAO.creaCartella(uuid, cartellaPadre+nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
		}

		byte[] contenuto = file.getBytes();
		//@@DDS documentaleDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.COPERTINA_DOCUMENT, file.getContentType(), proprietaDocumento, cartellaPadreDoc, contenuto);
		documentaleDdsDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.COPERTINA_DOCUMENT, file.getContentType(), proprietaDocumento, cartellaPadreDoc.getFolderPath(), contenuto);
	}


	//	@Override
	//	public List<Documento> leggiDocumenti(Long idNewsletter) throws Throwable {
	//		List<Documento> toReturn = new ArrayList<Documento>();
	//		
	//		//Articolo articolo = dao.leggiNewsletter(idNewsletter);
	//		
	//		List<DocumentoArticolo> docArt = dao.leggiArticoloDocbyId(idNewsletter);
	//		
	//		for(DocumentoArticolo doc : docArt){
	//				toReturn.add(doc.getDocumento());
	//		}
	//		
	//		
	//		return toReturn;
	//	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modificaNewsletter(NewsletterView newsletterView) throws Throwable {

		dao.modificaNewsletter(newsletterView.getVo());

//		if(newsletterView.getCopertina().getSize()!=0){
//			String oldUuid = newsletterView.getVo().getCopertina().getUuid();
//			String uuid =""+UUID.randomUUID()+"";
//			documentaleDAO.eliminaDocumento(oldUuid);
//			documentoDAO.cancella(oldUuid);
//			aggiungiDocumento(new Date(), newsletterView.getVo().getId(), newsletterView.getCopertina(), "NEWSLETTER", uuid);
//			Documento doc=documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.COPERTINA_DOCUMENT, newsletterView.getCopertina().getOriginalFilename(), newsletterView.getCopertina().getContentType());
//			newsletterView.getVo().setCopertina(doc);
//		}

		mailinglistService.cancellaArticoli(newsletterView.getVo().getId());

		if(newsletterView.getArticoliAggiunti()!=null){
			for(String id : newsletterView.getArticoliAggiunti()){
				RNewsletterArticolo rnewsArt = new RNewsletterArticolo();
				rnewsArt.setArticolo(emailService.leggiEmail(Long.parseLong(id)).getVo());
				rnewsArt.setNewsletter(newsletterView.getVo());
				dao.aggiungiArticolo(rnewsArt);
			}
		}
		
		mailinglistService.cancellaEmail(newsletterView.getVo().getId());

		if(newsletterView.getEmail()!=null){
			for(String email : newsletterView.getEmail()){
				if(!email.isEmpty()){
				NewsletterEmail nemail = new NewsletterEmail();
				nemail.setNewsletter(newsletterView.getVo());
				nemail.setEmail(email);
				dao.aggiungiEmail(nemail);
				}
			}
		}
	}

	@Override
	public void aggiungiDocumenti(Date dataCreazione, Articolo articolo, List<MultipartFile> files, String nomeFolder)
			throws Throwable {
	}

	@Override
	public Collection<NewsletterView> leggiNewsletter() throws Throwable {
		return null;
	}


	@Override
	public void cancellaNewsletter(long id , String lingua) throws Throwable {
		dao.cancellaNewsletter(id, lingua);
		List<RNewsletterArticolo> articoli = dao.leggiArticoliDocumento(id);
		
		for(RNewsletterArticolo articolo:articoli){
			dao.rimuoviArticolo(articolo);
		}
	}


	@Override
	public ListaPaginata<NewsletterView> cerca(String dal, String al, String titolo, String stato, int numElementiPerPagina,
			int numeroPagina, String ordinamento, String tipoOrdinamento) throws Throwable {
		List<Newsletter> lista = dao.cerca(dal, al, titolo, stato, numElementiPerPagina, numeroPagina, ordinamento,
				tipoOrdinamento);
		List<NewsletterView> listaView = convertiVoInView(lista);

		ListaPaginata<NewsletterView> listaRitorno = new ListaPaginata<NewsletterView>();
		Long conta = dao.conta(titolo);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(numElementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(tipoOrdinamento);
		return listaRitorno;
	}


	@Override
	public byte[] leggiCopertina(Documento copertina) throws Throwable {
		//@@DDS byte[] image =  documentaleDAO.leggiContenutoDocumento(copertina.getUuid());
		byte[] image =  documentaleDdsDAO.leggiContenutoDocumento(copertina.getUuid());
		return image;
	}

	@Override
	public List<Documento> leggiDocumenti(Long idNewsletter) throws Throwable {
		return null;
	}

	@Override
	public List<StatoNewsletterView> listaStatoNewsletter(String lingua) throws Throwable{
		List<StatoNewsletter> lvo= dao.listaStatoNewsletter(lingua);

		if (lvo != null) {
			List<StatoNewsletterView> lview = new ArrayList<StatoNewsletterView>();
			for (StatoNewsletter vo : lvo) {
				StatoNewsletterView view = new StatoNewsletterView();
				view.setVo(vo);
				lview.add(view);
			}

			return lview;
		}
		return null;

	}

	@Override
	public void preparaInviaComunicazione(String[] newsletterIdsArray) throws Throwable {
	}

	@Override
	public void preparaAnteprimaComunicazione(String newsletterIdsArray) throws Throwable {
	}

	@Override
	public void attivaNewsletter(long id, String lingua) throws Throwable {
		
		NewsletterView newsletter = leggiNewsletter(id);
		
		Newsletter news = newsletter.getVo();
		
		StatoNewsletter stato  = dao.getStatoAttivo(lingua);
		
		news.setStato(stato);
		
		dao.attivaNewsletter(news);
	}

	@Override
	public int getNewNumber() throws Throwable {
		return dao.getNewNumber()+1;
	}

	@Override
	public void inviaNewsletter(long id, String upperCase) throws Throwable {
		
		NewsletterView newsletter = leggiNewsletter(id);
		
		Newsletter news = newsletter.getVo();
		
		StatoNewsletter stato  = dao.getStatoInviata(upperCase);
		
		news.setStato(stato);
		
		dao.inviaNewsletter(news);
		
	}

	@Override
	public List<NewsletterEmail> leggiEmails(long id) throws Throwable {
		List<NewsletterEmail> articoli =  dao.leggiEmails(id);
		return articoli;
	}

	@Override
	public List<Articolo> leggiArticoliDocumento() throws Throwable {
		return dao.leggiArticoliDocumento();
	}

	

}
