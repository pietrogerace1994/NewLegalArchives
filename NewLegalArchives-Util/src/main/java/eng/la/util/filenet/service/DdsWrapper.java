/*
 * @author Luigi Nardiello
 */
package eng.la.util.filenet.service;
/*
import com.filenet.api.collection.ContentElementList;
import com.filenet.api.collection.RepositoryRowSet;
import com.filenet.api.constants.*;
import com.filenet.api.core.*;
import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.Id;
import com.filenet.apiimpl.query.RepositoryRowImpl;
import eng.la.util.filenet.infra.CEConnection;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetDocumentException;
import eng.la.util.filenet.model.FileNetFolderException;
*/
/*import com.filenet.api.collection.ContentElementList;
import com.filenet.api.collection.RepositoryRowSet;
import com.filenet.api.constants.*;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Factory;
import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.query.SearchSQL;
import com.filenet.apiimpl.query.RepositoryRowImpl;*/
import eng.la.util.filenet.infra.CEConnection;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetDocumentException;
import eng.la.util.filenet.model.FileNetFolderException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;
import it.snam.ned.libs.dds.dtos.v2.Document;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
// TODO: Auto-generated Javadoc

/**
 * Class FileNetWrapper.
 */
public class DdsWrapper implements IDdsWrapper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DdsWrapper.class);


	/** Attributo uri. */
	private String _uri = "";

	/**
	 * Ritorna il valore dell'attributo uri.
	 *
	 * @return FileNetWrapper
	 */
	public String get_uri() {
		return _uri;
	}

	/**
	 * Setta il valore dell'attributo uri.
	 *
	 * @param _uri
	 *            the uri
	 */
	public void set_uri(String _uri) {
		this._uri = _uri;
	}

	/** Attributo stanza. */
	private String _stanza = "";

	/**
	 * Ritorna il valore dell'attributo stanza.
	 *
	 * @return FileNetWrapper
	 */
	public String get_stanza() {
		return _stanza;
	}

	/**
	 * Setta il valore dell'attributo stanza.
	 *
	 * @param _stanza
	 *            the stanza
	 */
	public void set_stanza(String _stanza) {
		this._stanza = _stanza;
	}

	/** Attributo username. */
	private String _username = "";

	/**
	 * Ritorna il valore dell'attributo username.
	 *
	 * @return FileNetWrapper
	 */
	public String get_username() {
		return _username;
	}

	/**
	 * Setta il valore dell'attributo username.
	 *
	 * @param _username
	 *            the username
	 */
	public void set_username(String _username) {
		this._username = _username;
	}

	/** Attributo password. */
	private String _password = "";

	/**
	 * Ritorna il valore dell'attributo password.
	 *
	 * @return FileNetWrapper
	 */
	public String get_password() {
		return _password;
	}

	/**
	 * Setta il valore dell'attributo password.
	 *
	 * @param _password
	 *            the password
	 */
	public void set_password(String _password) {
		this._password = _password;
	}

	/** Attributo nome object store. */
	private String _nomeObjectStore = "";

	/**
	 * Ritorna il valore dell'attributo nome object store.
	 *
	 * @return FileNetWrapper
	 */
	public String get_nomeObjectStore() {
		return _nomeObjectStore;
	}

	/**
	 * Setta il valore dell'attributo nome object store.
	 *
	 * @param _nomeObjectStore
	 *            the nome object store
	 */
	public void set_nomeObjectStore(String _nomeObjectStore) {
		this._nomeObjectStore = _nomeObjectStore;
	}

	private String _nomeObjectStoreMongo = "";


	/** Attributo conn. */
	private CEConnection conn = null;

	/** Attributo object store. */
	//private ObjectStore objectStore = null;

	//private static DdsManager ddsManager = null;

	//private String ddsToken = "";

	/**
	 * Istanzia un nuovo oggetto FileNetWrapper.
	 *
	 * @param configurazione - contiene i parametri di configurazione della connessione.
	 *            filenet.url, filenet.username, filenet.password, filenet.osname, filenet.stanza.
	 *
	 */
	public DdsWrapper(Properties configurazione) throws Throwable{
		String url = configurazione.getProperty("filenet.url");
		String user = configurazione.getProperty("filenet.username");
		String passw = configurazione.getProperty("filenet.password");
		String stanza = configurazione.getProperty("filenet.stanza");
		String osname = configurazione.getProperty("filenet.osname");
		_uri = url;
		_stanza = stanza;
		_username = user;
		_password = passw;
		_nomeObjectStore = osname;
		initObjectStore();
	}

	/**
	 * Istanzia un nuovo oggetto FileNetWrapper.
	 *
	 * @param uri
	 *            the uri
	 * @param stanza
	 *            the stanza
	 * @param nomeObjectStore
	 *            the nome object store
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 */
	public DdsWrapper(String uri, String stanza, String nomeObjectStore, String username, String password) throws Throwable{
		_uri = uri;
		_stanza = stanza;
		_username = username;
		_password = password;
		_nomeObjectStore = nomeObjectStore;
		
		_nomeObjectStoreMongo = nomeObjectStore;
		
		initObjectStore();
	}

	/**
	 * Inizializzazione object store.
	 */
	private void initObjectStore() throws Throwable{
		if (logger.isDebugEnabled()) {
			logger.debug("InitObjectStore() - INIZIO");
			logger.debug("FilenetURL: " + get_uri());
		}
		
		boolean connectionAlreadyEstablished = true;
		try {
			// Controllo se la connessione � gi� stata stabilita, in tal caso
			// non lo faccio pi�
			/*if (conn == null || !conn.isConnected()) {
				logger.info("Establishing new FilenetConnection - INIZIO");
				conn = new CEConnection();
				conn.establishConnection(get_username(), get_password(), get_stanza(), get_uri());
				logger.info("Establishing new FilenetConnection - FINE");
				connectionAlreadyEstablished = false;
			}
			if(connectionAlreadyEstablished == false){
				logger.info("FilenetConnection has been created NEW right now");
			}else{
				logger.info("FilenetConnection was already instantiated");
			}*/
			
			// Lettura ObjectStore
			logger.info("Fetching ObjectStore: " + get_nomeObjectStore() + " - INIZIO");
			//@@DDS objectStore = conn.fetchOS(get_nomeObjectStore());
			//@@DDS objectStore.refresh();
			logger.info("Fetching ObjectStore: " + get_nomeObjectStore() + " - FINE");
		} catch (Exception e) {
			logger.error("InitObjectStore() - ERRORE", e); //$NON-NLS-1$

			e.printStackTrace();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("InitObjectStore() - FINE"); //$NON-NLS-1$
		}
	}

	@Override
	public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento,
			String mimeTypeDocumento, Map<String, Object> proprietaDocumento, Folder cartellaPadre, byte[] contenuto)
			throws Throwable {
		logger.debug("DdsWrapper.creaDocumento");
		InputStream is = new ByteArrayInputStream(contenuto);
		return creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento,
				cartellaPadre, is);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eng.la.util.filenet.service.IFileNetWrapper#creaDocumento(java.lang.
	 * String, java.lang.String, java.lang.String, java.lang.String,
	 * java.util.Map, com.filenet.api.core.Folder, java.io.InputStream)
	 */
	@Override
	public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento,
			String mimeTypeDocumento, Map<String, Object> proprietaDocumento, Folder cartellaPadre, InputStream is)
			throws Throwable {
		logger.debug("DdsWrapper.creaDocumento due");
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("creaDocumento(" + uuid + ", " + titoloDocumento + ", " + nomeClasseDocumento + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					+ mimeTypeDocumento + ", " + proprietaDocumento + ", " + cartellaPadre + ", " + is + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

		if (cartellaPadre == null) {
			throw new RuntimeException("La cartella padre non puo' essere null");
		}
		if (titoloDocumento == null || titoloDocumento.trim().length() == 0) {
			throw new RuntimeException("Il titolo del documento non puo' essere vuoto");
		}

		if (nomeClasseDocumento != null && nomeClasseDocumento.endsWith("Folder")) {
			throw new RuntimeException("nomeClasseDocumento deve essere di tipo Document");
		}
		// Genero il nuovo UUID da associare al nuovo documento se non � gi�
		// valorizzato
		uuid = uuid == null || uuid.trim().length() == 0 ? UUID.randomUUID().toString() : uuid;

		// se il nome della classe del documento non � valorizzato utilizzo
		// quello di default
		nomeClasseDocumento = nomeClasseDocumento == null || nomeClasseDocumento.trim().length() == 0
				? FileNetClassNames.GENERIC_DOCUMENT : nomeClasseDocumento;

		
		//TO-DO inserire metodo creazione documento su Mongo
		//ddsManager.insertDocument(_nomeObjectStoreMongo, titoloDocumento, proprietaDocumento, nomeClasseDocumento, null, is);
		
		try {
			Document documento = Factory.Document.createInstance(objectStore, nomeClasseDocumento, new Id(uuid));
			documento.getProperties().putValue("DocumentTitle", titoloDocumento);
			documento.set_MimeType(mimeTypeDocumento);
			if (proprietaDocumento != null && !proprietaDocumento.isEmpty()) {
				Set<String> keys = proprietaDocumento.keySet();
				for (String key : keys) {
					documento.getProperties().putObjectValue(key, proprietaDocumento.get(key));
				}
			}
			// Check in the document.
			ContentElementList contentElementList = Factory.ContentElement.createList();
			ContentTransfer contentTransferNew = Factory.ContentTransfer.createInstance();
			contentTransferNew.setCaptureSource(is);
			contentElementList.add(contentTransferNew);
			documento.set_ContentElements(contentElementList);

			ReferentialContainmentRelationship rcr = cartellaPadre.file(documento, AutoUniqueName.AUTO_UNIQUE,
					titoloDocumento, DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);

			documento.checkin(AutoClassify.AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
			documento.save(RefreshMode.REFRESH);
			rcr.save(RefreshMode.REFRESH);
			documento.refresh();
			Document returnDocument = leggiDocumentoUUID(uuid);
			if (logger.isDebugEnabled()) {
				logger.debug(
						"creaDocumento(String, String, String, String, Map<String,Object>, Folder, InputStream) - FINE - valore di ritorno=" //$NON-NLS-1$
								+ returnDocument);
			}*/
		Document returnDocument=null;

			return returnDocument;


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eng.la.util.filenet.service.IFileNetWrapper#creaCartella(java.lang.
	 * String, java.lang.String, java.lang.String, java.util.Map,
	 * com.filenet.api.core.Folder)
	 */
	@Override
	public Folder creaCartella(String uuid, String nomeCartella, String nomeClasseCartella,
			Map<String, Object> proprietaCartella, Folder cartellaPadre) throws Throwable {
		logger.debug("DdsWrapper.creaCartella");
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("creaCartella(" + uuid + ", " + nomeCartella + ", " + nomeClasseCartella + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					+ proprietaCartella + ", " + cartellaPadre + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (uuid == null || uuid.trim().length() == 0) {
			throw new RuntimeException("uuid non puo' essere vuoto");
		}

		if (cartellaPadre == null) {
			throw new RuntimeException("cartellaPadre non puo' essere vuoto");
		}

		if (nomeClasseCartella != null && nomeClasseCartella.endsWith("Document")) {
			throw new RuntimeException("nomeClasseCartella deve essere di tipo Folder");
		}

		nomeClasseCartella = nomeClasseCartella == null || nomeClasseCartella.trim().length() == 0
				? FileNetClassNames.GENERIC_FOLDER : nomeClasseCartella;

		try {
			Folder cartella = Factory.Folder.createInstance(objectStore, nomeClasseCartella, new Id(uuid));
			cartella.set_Parent(cartellaPadre);
			cartella.set_FolderName(nomeCartella);
 
			if (proprietaCartella != null && proprietaCartella.size() > 0) {
				Set<String> keys = proprietaCartella.keySet();				
				for (String key : keys) {
					cartella.getProperties().putObjectValue(key, proprietaCartella.get(key));
				}
			}

			cartella.save(RefreshMode.REFRESH);

			if (logger.isDebugEnabled()) {
				logger.debug(
						"creaCartella(String, String, String, Map<String,Object>, Folder) - FINE - valore di ritorno=" //$NON-NLS-1$
								+ cartella);
			}

		 */
		Folder cartella = null;
			return cartella;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eng.la.util.filenet.service.IFileNetWrapper#leggiDocumentoUUID(java.lang.
	 * String)
	 */
	@Override
	public Document leggiDocumentoUUID(String uuid) throws Throwable {
		logger.debug("DdsWrapper.leggiDocumentoUUID");
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("leggiDocumentoUUID(" + uuid + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (uuid == null || uuid.trim().length() == 0) {
			throw new RuntimeException("uuid non pu� essere null");
		}
		Document documento = null;
		try { 
			documento = Factory.Document.fetchInstance(objectStore, new Id(uuid), null);
		} catch (EngineRuntimeException e) {
			logger.error("leggiDocumentoUUID(String) - ERRORE", e); //$NON-NLS-1$

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("leggiDocumentoUUID(String) - FINE - valore di ritorno=" + documento); //$NON-NLS-1$
		}*/
		Document documento = null;
		return documento;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eng.la.util.filenet.service.IFileNetWrapper#leggiCartella(java.lang.
	 * String)
	 */
	@Override
	public Folder leggiCartella(String percorsoCartella) throws Throwable {
		logger.debug("DdsWrapper.leggiCartella");
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("leggiCartella(" + percorsoCartella + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Folder folder = null;

		try {
			folder = Factory.Folder.fetchInstance(objectStore, percorsoCartella, null);
		} catch (EngineRuntimeException e) {
			logger.error("leggiCartella(String) - ERRORE", e); //$NON-NLS-1$

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("leggiCartella(String) - FINE - valore di ritorno=" + folder); //$NON-NLS-1$
		}
		 */
		Folder folder = null;
		return folder;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eng.la.util.filenet.service.IFileNetWrapper#leggiCartellaUUID(java.lang.
	 * String)
	 */
	@Override
	public Folder leggiCartellaUUID(String uuid) throws Throwable {
		logger.debug("DdsWrapper.leggiCartellaUUID");
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("leggiCartellaUUID(" + uuid + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (uuid == null || uuid.trim().length() == 0) {
			throw new RuntimeException("uuid non pu� essere null");
		}
		Folder folder = null;
		try {
			folder = Factory.Folder.fetchInstance(objectStore, new Id(uuid), null);
		} catch (EngineRuntimeException e) {
			logger.error("leggiCartellaUUID(String) - ERRORE", e); //$NON-NLS-1$

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("leggiCartellaUUID(String) - FINE - valore di ritorno=" + folder); //$NON-NLS-1$
		}

		 */
		Folder folder = null;
		return folder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eng.la.util.filenet.service.IFileNetWrapper#eliminaCartella(java.lang.
	 * String)
	 */
	@Override
	public void eliminaCartella(String uuid) throws Throwable {
		logger.debug("DdsWrapper.elimina cartella + " + uuid);
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("eliminaCartella(" + uuid + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		uuid = uuid.replaceAll("\\{", "");
		uuid = uuid.replaceAll("\\}", "");
		Folder cartella = leggiCartellaUUID(uuid);
		if (cartella != null) {
			cartella.delete();
			cartella.save(RefreshMode.REFRESH);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("eliminaCartella(String) - FINE"); //$NON-NLS-1$
		}
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eng.la.util.filenet.service.IFileNetWrapper#eliminaDocumento(java.lang.
	 * String)
	 */
	@Override
	public void eliminaDocumento(String uuid) throws Throwable {
		logger.debug("DdsWrapper.eliminaDocumento " + uuid);
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("eliminaDocumento(" + uuid + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		uuid = uuid.replaceAll("\\{", "");
		uuid = uuid.replaceAll("\\}", "");
		Document documento = leggiDocumentoUUID(uuid);
		if (documento != null) {
			documento.delete();
			documento.save(RefreshMode.REFRESH);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("eliminaDocumento(String) - FINE"); //$NON-NLS-1$
		}

		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eng.la.util.filenet.service.IFileNetWrapper#eliminaDocumenti(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public void eliminaDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable {
		logger.debug("DdsWrapper.eliminaDocumenti");
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("eliminaDocumenti(" + idOggettoLa + ", " + nomeClasseDocumento + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		List<Document> listaDocumenti = leggiDocumenti(idOggettoLa, nomeClasseDocumento);
		if (listaDocumenti != null && listaDocumenti.size() > 0) {
			for (Document documento : listaDocumenti) {
				documento.delete();
			}
			objectStore.save(RefreshMode.REFRESH);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("eliminaDocumenti(String, String) - FINE"); //$NON-NLS-1$
		}

		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eng.la.util.filenet.service.IFileNetWrapper#leggiContenutoDocumento(java.
	 * lang.String)
	 */
	@Override
	public byte[] leggiContenutoDocumento(String uuid) throws Throwable {
		logger.debug("DdsWrapper.leggiContenutoDocumento");
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("leggiContenutoDocumento(" + uuid + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		InputStream is = null;
		ByteArrayOutputStream os = null;
		try {
			Document documento = leggiDocumentoUUID(uuid);
			is = documento.accessContentStream(0);
			os = new ByteArrayOutputStream();
			IOUtils.copy(is, os);
			byte[] bytes = os.toByteArray();

			if (logger.isDebugEnabled()) {
				logger.debug("leggiContenutoDocumento(String) - FINE - valore di ritorno=" + bytes); //$NON-NLS-1$
			}
			return bytes;
		} catch (Throwable e) {
			logger.error("leggiContenutoDocumento(String) - ERRORE", e); //$NON-NLS-1$

			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("leggiContenutoDocumento(String) - FINE - valore di ritorno=" + null); //$NON-NLS-1$
		}

		 */
		return null;
	}
	
	@Override
	public byte[] leggiContenutoDocumento(Document documento) throws Throwable {
		logger.debug("DdsWrapper.leggiContenutoDocumento");
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("leggiContenutoDocumento(" + documento + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		InputStream is = null;
		ByteArrayOutputStream os = null;
		try {
			//Document documento = leggiDocumentoUUID(uuid);
			is = documento.accessContentStream(0);
			os = new ByteArrayOutputStream();
			IOUtils.copy(is, os);
			byte[] bytes = os.toByteArray();

			if (logger.isDebugEnabled()) {
				logger.debug("leggiContenutoDocumento(String) - FINE - valore di ritorno=" + bytes); //$NON-NLS-1$
			}
			return bytes;
		} catch (Throwable e) {
			logger.error("leggiContenutoDocumento(String) - ERRORE", e); //$NON-NLS-1$

			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("leggiContenutoDocumento(String) - FINE - valore di ritorno=" + null); //$NON-NLS-1$
		}

		 */
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eng.la.util.filenet.service.IFileNetWrapper#leggiDocumenti(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public List<Document> leggiDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable {
		logger.debug("DdsWrapper.leggiDocumenti");
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("leggiDocumenti(" + idOggettoLa + ", " + nomeClasseDocumento + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		nomeClasseDocumento = nomeClasseDocumento == null || nomeClasseDocumento.trim().length() == 0
				? FileNetClassNames.GENERIC_DOCUMENT : nomeClasseDocumento;

		if (nomeClasseDocumento.endsWith("Folder")) {
			throw new RuntimeException("nomeClasseDocumento non pu� essere di tipo folder");
		}

		List<Document> listaDocumenti = null;
		String sql = "SELECT [This] FROM [" + nomeClasseDocumento + "] WHERE ([LAid] = " + idOggettoLa
				+ ") OPTIONS(TIMELIMIT 180)";
		SearchSQL searchSQL = new SearchSQL(sql);
		SearchScope searchScope = new SearchScope(objectStore);
		RepositoryRowSet rowSet = searchScope.fetchRows(searchSQL, null, null, new Boolean(true));
		if (rowSet != null && !rowSet.isEmpty()) {
			listaDocumenti = new ArrayList<Document>();
			Iterator iterator = rowSet.iterator();
			while (iterator.hasNext()) {
				RepositoryRowImpl row = (RepositoryRowImpl) iterator.next();
				Document documento = (Document) row.getProperties().getObjectValue("This");
				System.out.println(documento.getClassName() + " " + documento.get_Name());
				listaDocumenti.add(documento);
			}

		}

		if (logger.isDebugEnabled()) {
			logger.debug("leggiDocumenti(String, String) - FINE - valore di ritorno=" + listaDocumenti); //$NON-NLS-1$
		}

		 */
		List<Document> listaDocumenti = null;
		return listaDocumenti;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eng.la.util.filenet.service.IFileNetWrapper#leggiCartelle(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public List<Folder> leggiCartelle(String idOggettoLa, String nomeClasseCartella) throws Throwable {
		logger.debug("DdsWrapper.leggiCartella");
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("leggiCartelle(" + idOggettoLa + ", " + nomeClasseCartella + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		nomeClasseCartella = nomeClasseCartella == null || nomeClasseCartella.trim().length() == 0
				? FileNetClassNames.GENERIC_FOLDER : nomeClasseCartella;

		if (nomeClasseCartella.endsWith("Document")) {
			throw new RuntimeException("nomeClasseCartella non pu� essere di tipo document");
		}

		List<Folder> listaCartelle = null;
		String sql = "SELECT [This] FROM [" + nomeClasseCartella + "] WHERE ([LAid] = " + idOggettoLa
				+ ") OPTIONS(TIMELIMIT 180)";
		SearchSQL searchSQL = new SearchSQL(sql);
		SearchScope searchScope = new SearchScope(objectStore);
		RepositoryRowSet rowSet = searchScope.fetchRows(searchSQL, null, null, new Boolean(true));
		if (rowSet != null && !rowSet.isEmpty()) {
			listaCartelle = new ArrayList<Folder>();
			Iterator iterator = rowSet.iterator();
			while (iterator.hasNext()) {

				RepositoryRowImpl row = (RepositoryRowImpl) iterator.next();
				Folder cartella = (Folder) row.getProperties().getObjectValue("This");
				System.out.println(cartella.getClassName() + " " + cartella.get_Name());
				listaCartelle.add(cartella);
			}

		}

		if (logger.isDebugEnabled()) {
			logger.debug("leggiCartelle(String, String) - FINE - valore di ritorno=" + listaCartelle); //$NON-NLS-1$
		}

		 */
		List<Folder> listaCartelle = null;
		return listaCartelle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eng.la.util.filenet.service.IFileNetWrapper#modificaCartella(java.lang.
	 * String, java.lang.String, java.util.Map)
	 */
	@Override
	public Folder modificaCartella(String uuid, String nuovoNomeCartella, Map<String, Object> proprietaCartella)
			throws Throwable {
		logger.debug("DdsWrapper.modificaCartella");
		/*
		if (logger.isDebugEnabled()) {
			logger.debug(
					"modificaCartella(" + uuid + ", " + nuovoNomeCartella + ", " + proprietaCartella + ") - INIZIO"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

		if (uuid == null || uuid.trim().length() == 0) {
			throw new RuntimeException("uuid non pu� essere null");
		}

		Folder cartella = leggiCartellaUUID(uuid);
		if (nuovoNomeCartella != null && nuovoNomeCartella.trim().length() > 0) {
			cartella.set_FolderName(nuovoNomeCartella);
		}

		if (proprietaCartella != null && proprietaCartella.size() > 0) {
			Set<String> keys = proprietaCartella.keySet();
			for (String key : keys) {
				cartella.getProperties().putObjectValue(key, proprietaCartella.get(key));
			}
		}

		cartella.save(RefreshMode.REFRESH);

		Folder returnFolder = leggiCartellaUUID(uuid);
		if (logger.isDebugEnabled()) {
			logger.debug(
					"modificaCartella(String, String, Map<String,Object>) - FINE - valore di ritorno=" + returnFolder); //$NON-NLS-1$
		}

		 */
		Folder returnFolder = null;
		return returnFolder;
	}

}
