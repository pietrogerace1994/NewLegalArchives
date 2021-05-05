package eng.la.business;

import eng.la.model.Documento;
import eng.la.model.view.DocumentoView;

public interface DocumentoService {
	
	public DocumentoView leggi(long id) throws Throwable;
	
	public DocumentoView leggi(String uuid) throws Throwable;

	public void cancellaDocumento(Documento documento) throws Throwable;

	public DocumentoView creaDocumentoDB(String uuid, String classeDocumentale, String nomeFile, String contentType)
			throws Throwable;

}
