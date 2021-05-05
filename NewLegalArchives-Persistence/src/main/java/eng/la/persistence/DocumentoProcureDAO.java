package eng.la.persistence;

import eng.la.model.DocumentoProcure;

public interface DocumentoProcureDAO {

	public DocumentoProcure save(DocumentoProcure vo) throws Throwable;

	public DocumentoProcure read(long id) throws Throwable;

	public void deleteDocumentoProcure(DocumentoProcure documento) throws Throwable;
	
}
