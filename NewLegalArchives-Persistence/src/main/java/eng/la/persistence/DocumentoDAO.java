package eng.la.persistence;

import eng.la.model.Articolo;
import eng.la.model.Documento;

public interface DocumentoDAO {

	public void cancellaDocumento(Documento documento) throws Throwable;

	public Documento creaDocumentoDB(String uuid, String classeDocumentale, String nomeFile, String contentType)
			throws Throwable;

	public Documento leggi(String uuid)throws Throwable;
	
	public Documento leggi(long id) throws Throwable;
	
	public void cancella(String uuid) throws Throwable;

	public void creaDocumentoArticolo(Documento doc, Articolo articolo) throws Throwable;
	
	/**
	 * Ricerca un documento per nome
	 * @author MASSIMO CARUSO
	 * @param nome il nome del documento da ricercare
	 * @return il primo documento trovato, null altrimenti
	 * @throws Throwable
	 */
	public Documento cercaDocumento(String nome) throws Throwable;
	
	/**
	 * Elimina la entry di un documento dal DB
	 * @author MASSIMO CARUSO
	 * @param id_documento l'id del documento da rimuovere
	 * @throws Throwable
	 */
	public void cancellaDocumentoDB(long id_documento) throws Throwable;
	
}
