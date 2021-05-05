package eng.la.persistence;

import eng.la.model.DocumentoDueDiligence;

public interface DocumentoDueDiligenceDAO {

	public DocumentoDueDiligence save(DocumentoDueDiligence vo) throws Throwable;

	public DocumentoDueDiligence read(long id) throws Throwable;

	public void deleteDocumentoDueDiligence(DocumentoDueDiligence documento) throws Throwable;
	
}
