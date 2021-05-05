/*
 * @author Luigi Nardiello
 */
package eng.la.util.filenet.service;

import it.snam.ned.libs.dds.dtos.v2.Document;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc

/**
 *  Interface IFileNetWrapper.
 */
public interface IDdsWrapper {
 
 
	/**
	 * Crea un nuovo documento sul documentale rispettando i seguenti parametri di input.
	 *
	 * @param uuid UUID  
	 * @param titoloDocumento titolo del documento
	 * @param nomeClasseDocumento Classe documentale di appartenenza
	 * @param mimeTypeDocumento mime-type del documento   
	 * @param proprietaDocumento elenco attributi documento  
	 * @param cartellaPadre cartella di destinazione   
	 * @param contenuto byte[] cotenuto del documento    
	 * @return Document
	 * @throws Throwable 
	 */
	public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento,
			String mimeTypeDocumento, Map<String, Object> proprietaDocumento, Folder cartellaPadre,
			byte[] contenuto) throws Throwable;
	
	/**
	 * Crea un nuovo documento sul documentale rispettando i seguenti parametri di input.
	 *
	 * @param uuid UUID 
	 * @param titoloDocumento titolo del documento  
	 * @param nomeClasseDocumento Classe documentale di appartenenza 
	 * @param mimeTypeDocumento mime-type del documento  
	 * @param proprietaDocumento elenco attributi documento 
	 * @param cartellaPadre cartella di destinazione 
	 * @param contenuto InputStream cotenuto del documento  
	 * @return Document
	 * @throws Throwable  
	 */
	public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento,
			String mimeTypeDocumento, Map<String, Object> proprietaDocumento, Folder cartellaPadre,
			InputStream contenuto) throws Throwable;

	/**
	 * Crea una nuova cartella sul documentale rispettando i seguenti parametri di input.
	 *
	 * @param uuid  UUID  
	 * @param nomeCartella nome della cartella  
	 * @param nomeClasseCartella Classe documentale di appartenenza  
	 * @param proprietaCartella elenco attributi cartella  
	 * @param cartellaPadre cartella di destinazione   
	 * @return Folder
	 * @throws Throwable  
	 */
	public Folder creaCartella(String uuid, String nomeCartella, String nomeClasseCartella,
			Map<String, Object> proprietaCartella, Folder cartellaPadre) throws Throwable;

	/**
	 * Restituisce un oggetto Document identificato dal parametro di input UUID  
	 *
	 * @param uuid  UUID 
	 * @return Document
	 * @throws Throwable the throwable
	 */
	public Document leggiDocumentoUUID(String uuid) throws Throwable;

	/**
	 * Restituisce una lista di oggetti Document identificati dai parametri di input. 
	 *
	 * @param idOggettoLa  identificativo dell'oggetto LEGAL_ARCHIVES a cui afferisce il documento
	 * @param nomeClasseDocumento  Classe documentale di appartenenza  
	 * @return List<Document> 
	 * @throws Throwable  
	 */
	public List<Document> leggiDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable;

	/**
	 *  Restituisce una lista di oggetti Folder identificati dai parametri di input. 
	 *
	 * @param idOggettoLa  identificativo dell'oggetto LEGAL_ARCHIVES a cui afferisce la cartella
	 * @param nomeClasseCartella  Classe documentale di appartenenza  
	 * @return List<Folder> 
	 * @throws Throwable 
	 */
	public List<Folder> leggiCartelle(String idOggettoLa, String nomeClasseCartella) throws Throwable;

	/**
	 *  Restituisce un oggetto di tipo Folder identificato dal parametro di input percorsoCartella. 
	 *
	 * @param percorsoCartella  percorso della cartella da ricercare  
	 * @return Folder
	 * @throws Throwable 
	 */
	public Folder leggiCartella(String percorsoCartella) throws Throwable;

	/**
	 * Restituisce un oggetto di tipo Folder identificato dal parametro di input UUID. 
	 *
	 * @param uuid  UUId  
	 * @return Folder
	 * @throws Throwable  
	 */
	public Folder leggiCartellaUUID(String uuid) throws Throwable;

	/**
	 * Elimina la cartella identificata dal parametro di input UUID.
	 *
	 * @param uuid  UUID 
	 * @throws Throwable  
	 */
	public void eliminaCartella(String uuid) throws Throwable;

	/**
	 * Elimina il documento identificato dal parametro di input UUID.
	 *
	 * @param uuid  UUID 
	 * @throws Throwable  
	 */
	public void eliminaDocumento(String uuid) throws Throwable;

	/**
	 * Elimina i documenti identificati dai parametri di input.
	 *
	 * @param idOggettoLa  identificativo dell'oggetto LEGAL_ARCHIVES a cui afferiscono i documenti
	 * @param nomeClasseDocumento  Classe documentale di appartenenza    
	 * @throws Throwable 
	 */
	public void eliminaDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable;

	/**
	 * Leggi contenuto del documento identificato dal parametro di input UUID.
	 *
	 * @param uuid  UUID 
	 * @return byte[]  
	 * @throws Throwable  
	 */
	public byte[] leggiContenutoDocumento(String uuid) throws Throwable;
 
	/**
	 * Modifica nome e property della cartella identificata dal parametro di input UUID.
	 *
	 * @param uuid  UUID  
	 * @param nuovoNomeCartella  nuovo nome cartella  
	 * @param proprietaCartella  elenco attributi cartella 
	 * @return Folder
	 * @throws Throwable  
	 */
	public Folder modificaCartella(String uuid, String nuovoNomeCartella, 
			Map<String, Object> proprietaCartella ) throws Throwable;

	public byte[] leggiContenutoDocumento(Document documento) throws Throwable;
 
}
