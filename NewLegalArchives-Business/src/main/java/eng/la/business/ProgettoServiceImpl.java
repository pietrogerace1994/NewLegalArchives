package eng.la.business;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import eng.la.business.mail.EmailNotificationServiceImpl;
import eng.la.persistence.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;

import eng.la.model.Autorizzazione;
import eng.la.model.Documento;
import eng.la.model.DocumentoProgetto;
import eng.la.model.Progetto;
import eng.la.model.TipoAutorizzazione;
import eng.la.model.Utente;
import eng.la.model.aggregate.ProgettoAggregate;
import eng.la.model.filter.ProgettoFilter;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.ProgettoView;
import eng.la.util.ListaPaginata;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("progettoService")
public class ProgettoServiceImpl extends BaseService<Progetto,ProgettoView> implements ProgettoService {
	private static final Logger logger = Logger.getLogger(ProgettoServiceImpl.class);
	/*@@DDS
	@Autowired
	private DocumentaleDAO documentaleDAO;

	public void setDocumentaleDao(DocumentaleDAO dao) {
		this.documentaleDAO = dao;
	}
	*/
	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;

	public void setDocumentaleDdsDao(DocumentaleDdsDAO dao) {
		this.documentaleDdsDAO = dao;
	}

	@Autowired
	private DocumentoDAO documentoDAO;
	public void setDocumentoDAO(DocumentoDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	}
	
	@Autowired
	private ProgettoDAO progettoDAO;
	public void setDao(ProgettoDAO dao) {
		this.progettoDAO = dao;
	}
	
	@Autowired
	private DocumentoProgettoDAO documentoProgettoDAO;
	public void setProgettoDAO(ProgettoDAO progettoDAO) {
		this.progettoDAO = progettoDAO;
	}
	
	@Autowired
	private AutorizzazioneDAO autorizzazioneDAO;
	
	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;
	
	@Autowired
	private UtenteDAO utenteDAO;
	
	@Override
	public List<ProgettoView> leggi() throws Throwable {
		List<Progetto> lista = progettoDAO.leggi();
		List<ProgettoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public ProgettoView leggi(long id) throws Throwable {
		Progetto progetto = progettoDAO.leggi(id);
		return (ProgettoView) convertiVoInView(progetto);
	}
	
	public ProgettoView leggiConPermessi(long id) throws Throwable {
		Progetto progetto = progettoDAO.leggiConPermessi(id);
		return (ProgettoView) convertiVoInView(progetto);
	}
	
	@Override
	public ListaPaginata<ProgettoView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		List<Progetto> lista = progettoDAO.cerca(nome, elementiPerPagina, numeroPagina, ordinamento,
				ordinamentoDirezione);
		List<ProgettoView> listaView = convertiVoInView(lista);
		ListaPaginata<ProgettoView> listaRitorno = new ListaPaginata<ProgettoView>();
		Long conta = progettoDAO.conta(nome);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	public List<ProgettoView> cerca(String nome) throws Throwable {
		List<Progetto> lista = progettoDAO.cerca(nome);
		List<ProgettoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public ProgettoView inserisci(ProgettoView progettoView) throws Throwable {
		Progetto progetto = progettoDAO.inserisci(progettoView.getVo());
		salvaAllegatiGenerici(progettoView);
		ProgettoView view = new ProgettoView();
		view.setVo(progetto);
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void modifica(ProgettoView progettoView) throws Throwable {
		
		progettoDAO.modifica(progettoView.getVo());
		rimuoviAllegatiGenerici(progettoView);
		salvaAllegatiGenerici(progettoView);

	}

	private void salvaAllegatiGenerici(ProgettoView progettoView) throws Throwable {
		if(progettoView.getVo() == null){
			throw new RuntimeException("Progetto non trovato");
		}

		if( progettoView.getListaAllegatiGenerici() != null ){
			
			List<DocumentoView> allegatiGenerici = progettoView.getListaAllegatiGenerici();
			Folder cartellaPadre = getProgettoParentFolder(progettoView.getVo().getId(), progettoView.getVo().getDataCreazione());
			logger.info ("@@DDS ___________ cartella padre " + cartellaPadre);
			for(DocumentoView documentoView : allegatiGenerici ){
				if( documentoView.isNuovoDocumento() ){
				    InputStream contenuto = null;
					try{
						
						String uuid = UUID.randomUUID().toString();
						Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.PROGETTO_DOCUMENT, 
								documentoView.getNomeFile(), documentoView.getContentType());
						
						addDocument(progettoView.getVo(), documento.getId());
				
					    contenuto = new FileInputStream(documentoView.getFile());
					    
					    Map<String, Object> documentProperty = new HashMap<String, Object>();
						documentProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(progettoView.getVo().getId() + ""));
						//@@DDS documentaleDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.PROGETTO_DOCUMENT, documentoView.getContentType(),	documentProperty, cartellaPadre, contenuto);
						documentaleDdsDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.PROGETTO_DOCUMENT, documentoView.getContentType(),	documentProperty, cartellaPadre.getFolderPath(), contenuto);
				   }catch(Throwable e){
					  e.printStackTrace(); 
					  throw e;
				   }finally {
				     IOUtils.closeQuietly(contenuto);
				   }
				}
			}
		}
	}
	
	private Folder getProgettoParentFolder(Long idProgetto, Date dataApertura) throws Throwable {
		if(idProgetto != null && dataApertura != null){
			String parentFolderName = FileNetUtil.getProgettoParentFolder(dataApertura);
			String folderName = idProgetto + "-PROGETTO";
			String uuid = UUID.randomUUID().toString();
			String folderClassName = FileNetClassNames.PROGETTO_FOLDER; 
			Map<String, Object> folderProperty = new HashMap<String,Object>();
			folderProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idProgetto+""));
			
			//@@DDS Folder parentFolder = documentaleDAO.leggiCartella(parentFolderName);
			Folder parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			if(parentFolder == null){
				/*@@DDS
				documentaleDAO.verificaCreaPercorsoCartella(parentFolderName);		
				parentFolder = documentaleDAO.leggiCartella(parentFolderName);
				 */
				documentaleDdsDAO.verificaCreaPercorsoCartella(parentFolderName);
				parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			}
			
			String progettoFolderName = idProgetto + "-PROGETTO";
			progettoFolderName = parentFolderName + progettoFolderName + "/";
			//@@DDS Folder progettoFolder = documentaleDAO.leggiCartella(progettoFolderName);
			Folder progettoFolder = documentaleDdsDAO.leggiCartella(progettoFolderName);
			if(progettoFolder == null){
				/*@@DDS
				documentaleDAO.creaCartella(uuid, folderName, folderClassName, folderProperty, parentFolder);
				progettoFolder = documentaleDAO.leggiCartella(progettoFolderName);
				*/
				documentaleDdsDAO.creaCartella(uuid, progettoFolderName, folderClassName, folderProperty, parentFolder);
				progettoFolder = documentaleDdsDAO.leggiCartella(progettoFolderName);
			}
			
			return progettoFolder;
		}
		return null;
	}
	
	private void rimuoviAllegatiGenerici(ProgettoView progettoView) throws Throwable {
		if( progettoView.getAllegatiDaRimuovereUuid() != null ){
			
		
			Set<String> uuidSet = progettoView.getAllegatiDaRimuovereUuid();
			for( String uuid : uuidSet ){
				//@@DDS documentaleDAO.eliminaDocumento(uuid);
				documentaleDdsDAO.eliminaDocumento(uuid);
			}
		}
			
	}


	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void cancella(long idProgetto) throws Throwable {
		progettoDAO.cancella(idProgetto);
	}

	@Override
	public Long conta(String nome) throws Throwable { 
		return progettoDAO.conta(nome);
	}

	@Override
	public ProgettoView leggi(String codice, Locale locale) throws Throwable {
		Progetto progetto = progettoDAO.leggi(codice, locale.getLanguage().toUpperCase());
		ProgettoView progettoView = new ProgettoView();
		progettoView.setVo(progetto);
		return progettoView;
	}

	@Override
	public List<ProgettoView> leggi(Locale locale) throws Throwable {
		List<Progetto> lista = progettoDAO.leggi(locale.getLanguage().toUpperCase());
		List<ProgettoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public DocumentoProgetto addDocument(Progetto progetto, long documentoId) throws Throwable {
		Documento documento = new Documento();
		documento.setId(documentoId);
		DocumentoProgetto documentoProgetto = new DocumentoProgetto();
		documentoProgetto.setDocumento(documento);
		documentoProgetto.setProgetto(progetto);
		
		documentoProgetto = documentoProgettoDAO.save(documentoProgetto);
		return documentoProgetto;
	}
	

	@Override
	protected Class<Progetto> leggiClassVO() {
		return Progetto.class;
	}

	@Override
	protected Class<ProgettoView> leggiClassView() {
		return ProgettoView.class;
	}


	@Override
	public ProgettoAggregate searchPagedProject(ProgettoFilter filter) throws Throwable {
		return progettoDAO.searchPagedProject(filter);
	}


	@Override
	public void salvaPermessiProgetto(Long progettoId, String[] permessiScrittura,
			String[] permessiLettura) throws Throwable {
		Progetto progetto = progettoDAO.leggi(progettoId);
		autorizzazioneDAO.cancellaAutorizzazioni(progettoId, Costanti.TIPO_ENTITA_PROGETTO,
				progetto.getLegaleInterno());

		if (permessiScrittura != null) {
			for (String permessoUserId : permessiScrittura) {
				Autorizzazione autorizzazione = new Autorizzazione();
				autorizzazione.setIdEntita(progettoId);
				TipoAutorizzazione tipoAutorizzazione = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
						CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA, Locale.ITALIAN.getLanguage().toUpperCase());
				autorizzazione.setTipoAutorizzazione(tipoAutorizzazione);
				autorizzazione.setNomeClasse(Costanti.TIPO_ENTITA_PROGETTO);
				Utente utente = utenteDAO.leggiUtenteDaUserId(permessoUserId);
				autorizzazione.setUtente(utente);
				autorizzazioneDAO.inserisci(autorizzazione);
			}
		}

		if (permessiLettura != null) {
			for (String permessoUserId : permessiLettura) {
				Autorizzazione autorizzazione = new Autorizzazione();
				autorizzazione.setIdEntita(progettoId);
				TipoAutorizzazione tipoAutorizzazione = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
						CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_LETTURA, Locale.ITALIAN.getLanguage().toUpperCase());
				autorizzazione.setTipoAutorizzazione(tipoAutorizzazione);
				autorizzazione.setNomeClasse(Costanti.TIPO_ENTITA_PROGETTO);
				Utente utente = utenteDAO.leggiUtenteDaUserId(permessoUserId);
				autorizzazione.setUtente(utente);
				autorizzazioneDAO.inserisci(autorizzazione);
			}
		}
	}

}
