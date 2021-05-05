package eng.la.persistence;

import eng.la.model.DocumentoRepertorioStandard;

public interface DocumentoRepertorioStandardDAO {

	public DocumentoRepertorioStandard save(DocumentoRepertorioStandard vo) throws Throwable;

	public DocumentoRepertorioStandard read(long id) throws Throwable;

	public void deleteDocumentoRepertorioStandard(DocumentoRepertorioStandard documento) throws Throwable;

	public DocumentoRepertorioStandard leggiPerIdRepertorio(long idRepertorioStandard);
	
}
