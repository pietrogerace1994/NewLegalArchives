package eng.la.persistence;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import org.springframework.stereotype.Component;

/*import com.filenet.api.core.Document;
import com.filenet.api.core.Folder;*/

import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.service.FileNetWrapper;

@Component(value="documentaleCryptDAO")
public class DocumentaleCryptDAOImpl implements DocumentaleCryptDAO {
	 
	private String url;
	private String username;
	private String password;
	private String stanza;
	private String osname;


	 
	public DocumentaleCryptDAOImpl(String url, String username, String password, String stanza, String osname) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.stanza = stanza;
		this.osname = osname;
	}
 
	
	/*@Override
	public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento,
			String mimeTypeDocumento, Map<String, Object> proprietaDocumento, Folder cartellaPadre, byte[] contenuto)
			throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		return wrapper.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento,
				cartellaPadre, contenuto);
	}

	@Override
	public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento,
			String mimeTypeDocumento, Map<String, Object> proprietaDocumento, Folder cartellaPadre, InputStream is)
			throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		return wrapper.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento,
				cartellaPadre, is);
	}

	@Override
	public Folder creaCartella(String uuid, String nomeCartella, String nomeClasseCartella,
			Map<String, Object> proprietaCartella, Folder cartellaPadre) throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		return wrapper.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
	}

	@Override
	public Document leggiDocumentoUUID(String uuid) throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		return wrapper.leggiDocumentoUUID(uuid);
	}

	@Override
	public Folder leggiCartella(String percorsoCartella) throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		return wrapper.leggiCartella(percorsoCartella);
	}

	@Override
	public Folder leggiCartellaUUID(String uuid) throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		return wrapper.leggiCartellaUUID(uuid);
	}

	@Override
	public void eliminaCartella(String uuid) throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		wrapper.eliminaCartella(uuid);
	}

	@Override
	public void eliminaDocumento(String uuid) throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		wrapper.eliminaDocumento(uuid);
	}

	@Override
	public void eliminaDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		wrapper.eliminaDocumenti(idOggettoLa, nomeClasseDocumento);
	}

	@Override
	public byte[] leggiContenutoDocumento(String uuid) throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		return wrapper.leggiContenutoDocumento(uuid);
	}

	@Override
	public List<Document> leggiDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		return wrapper.leggiDocumenti(idOggettoLa, nomeClasseDocumento);
	}

	@Override
	public List<Folder> leggiCartelle(String idOggettoLa, String nomeClasseCartella) throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		return wrapper.leggiCartelle(idOggettoLa, nomeClasseCartella);
	}

	@Override
	public Folder modificaCartella(String uuid, String nuovoNomeCartella, Map<String, Object> proprietaCartella)
			throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		return wrapper.modificaCartella(uuid, nuovoNomeCartella, proprietaCartella);
	}
	
	@Override
	public void verificaCreaPercorsoCartella(String cartellaPadreFascicolo) throws Throwable {
		StringTokenizer tokenizer = new StringTokenizer(cartellaPadreFascicolo, "\\/");
		Folder cartellaPadre = leggiCartella("/");
		String path = "/";
		while( tokenizer.hasMoreTokens() ){
			String token = tokenizer.nextToken();
			path += token + "/"; 
			Folder folder = leggiCartella(path);
			if( folder == null ){
				String uuid = UUID.randomUUID().toString();
				cartellaPadre = creaCartella(uuid, token, FileNetClassNames.GENERIC_FOLDER, null, cartellaPadre);
			}else{
				cartellaPadre = folder;
			}
		}
	}


	@Override
	public byte[] leggiContenutoDocumento(Document documento) throws Throwable {
		FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, username, password);
		return wrapper.leggiContenutoDocumento(documento);
	}
	*/
}
