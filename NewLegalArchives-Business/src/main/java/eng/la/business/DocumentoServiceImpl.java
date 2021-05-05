package eng.la.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.Documento;
import eng.la.model.view.DocumentoView;
import eng.la.persistence.DocumentoDAO;

@Service("documentoService")
public class DocumentoServiceImpl extends BaseService<Documento,DocumentoView> implements DocumentoService {

	@Autowired
	private DocumentoDAO documentoDAO;

	public DocumentoDAO getDocumentoDAO() {
		return documentoDAO;
	}

	public void setDocumentoDAO(DocumentoDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	} 
	
	public void cancellaDocumento(Documento documento) throws Throwable {
		documentoDAO.cancellaDocumento(documento);
	}

	public DocumentoView creaDocumentoDB(String uuid, String classeDocumentale, String nomeFile, String contentType)
			throws Throwable {
		Documento vo = documentoDAO.creaDocumentoDB(uuid, classeDocumentale, nomeFile, contentType);
		return  convertiVoInView(vo);
	}
	
	public DocumentoView leggi(String uuid)
			throws Throwable {
		Documento vo = documentoDAO.leggi(uuid);
		return convertiVoInView(vo);
	}

	@Override
	public DocumentoView leggi(long id) throws Throwable {
		Documento vo = documentoDAO.leggi(id);
		return convertiVoInView(vo);
	}
	
	@Override
	protected Class<Documento> leggiClassVO() {
		return Documento.class;
	}

	@Override
	protected Class<DocumentoView> leggiClassView() {
		return DocumentoView.class;
	}
}
