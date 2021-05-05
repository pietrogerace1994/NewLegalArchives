package eng.la.persistence;

import eng.la.model.DocumentoProgetto;

public interface DocumentoProgettoDAO {

	public DocumentoProgetto save(DocumentoProgetto vo) throws Throwable;

	public DocumentoProgetto read(long id) throws Throwable;

	public void deleteDocumentoProgetto(DocumentoProgetto documento) throws Throwable;
	
}
