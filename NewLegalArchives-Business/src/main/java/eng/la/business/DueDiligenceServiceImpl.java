package eng.la.business;


import java.util.Iterator;
import java.util.List;
import java.util.Set;

//import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.Documento;
import eng.la.model.DocumentoDueDiligence;
import eng.la.model.DueDiligence;
import eng.la.model.view.DueDiligenceView;
import eng.la.persistence.DocumentoDAO;
import eng.la.persistence.DocumentoDueDiligenceDAO;
import eng.la.persistence.DueDiligenceDAO;

/**
 * <h1>Classe di business DueDiligenceService </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * 
 * @author 
 * @version 1.0
 * @since 2016-06-16
 */
@Service("dueDiligenceService")
public class DueDiligenceServiceImpl extends BaseService<DueDiligence,DueDiligenceView> implements DueDiligenceService {
	
	@Autowired
	private DueDiligenceDAO dueDilingenceDao;
	
	@Autowired
	private DocumentoDueDiligenceDAO documentoDueDiligenceDAO;
	
	@Autowired
	private DocumentoDAO documentoDAO;
	
	/**
	 * Metodo di set della istanza DAO passata come argomento, al corrispondente
	 * membro di classe.
	 * 
	 * @param dao oggetto della classe DueDiligenceDAO
	 * @see DueDiligenceDAO
	 */
	public void setDueDiligenceDao(DueDiligenceDAO dueDilingenceDao) {
		this.dueDilingenceDao = dueDilingenceDao;
	}
	
	/**
	 * Metodo per operazione di scrittura multipla, in trasazione. 
	 * I dati sono presenti nell'oggeto List come istanze di model passato come argomento.
	 * 
	 * @param lists oggetto lista con oggetti model. 
	 */
	
	/**
	 * Elenca tutte le parti correlate presenti in base dati
	 * @return lista dueDiligence
	 * @throws Throwable
	 */
	@Override
	public List<DueDiligenceView> leggi() throws Throwable {
		List<DueDiligence> lista = dueDilingenceDao.leggi(true);
		List<DueDiligenceView> listaRitorno = convertiVoInView(lista);		
		return listaRitorno;
	}
	
	// metodi di scrittura
	
	/**
	 * Cancellazione di una occorrenza.
	 * @param id 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		dueDilingenceDao.cancella(id);
	}
	
	@Override
	public void deleteDueDiligence(long id) throws Throwable {
		DueDiligence vo = dueDilingenceDao.deleteDueDiligence(id);
		
		if(vo != null){
			Documento documentoStep1 = vo.getDocumentoStep1();
			Set<DocumentoDueDiligence> documentoDueDiligences = vo.getDocumentoDueDiligences();
			Documento documentoStep3 = vo.getDocumentoStep3();
			
			if(documentoStep1 != null){
			  documentoDAO.cancellaDocumento(documentoStep1);
			}
			if(documentoDueDiligences != null && !documentoDueDiligences.isEmpty()){
				Iterator<DocumentoDueDiligence> iter = documentoDueDiligences.iterator();
				while(iter.hasNext()){
					DocumentoDueDiligence elem = iter.next();
					documentoDueDiligenceDAO.deleteDocumentoDueDiligence(elem);
				}
				
			}
			if(documentoStep3 != null){
				documentoDAO.cancellaDocumento(documentoStep3);
			}
			
		}
	}
	
	@Override
	public List<DueDiligenceView> cerca(DueDiligenceView view) throws Throwable {
		return null;
	}

	/**
	 * Lettura dueDiligence per l'id.
	 * @param id
	 * @throws Throwable
	 */
	@Override
	public DueDiligenceView leggi(long id) throws Throwable {
		DueDiligence dueDiligence = dueDilingenceDao.leggi(id);
		return convertiVoInView(dueDiligence);
	}
	
	/**
	 * elenco delle occorrenze ordinate per la data di chiusura.
	 * @param ordinaDataChiusura
	 */
	@Override
	public List<DueDiligenceView> leggi(char ordinaDataChiusura) throws Throwable {
		List<DueDiligence> lista  = dueDilingenceDao.leggi(ordinaDataChiusura);
		List<DueDiligenceView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	/**
	 * Ritorna l'elenco delle occorrenze ordinate in ASC
	 */
	@Override
	public List<DueDiligenceView> leggiOrdASC() throws Throwable {
		return leggi(DueDiligenceDAO.ASC);
	}

	/**
	 * Ritorna l'elenco delle occorrenze ordinate in DESC
	 */
	@Override
	public List<DueDiligenceView> leggiOrdDESC() throws Throwable {
		return leggi(DueDiligenceDAO.DESC);
	}

	/**
	 * Esegue inserimento di una dueDilingence in base dati.
	 * @param dueDiligenceView 
	 * @return dueDiligenceView ritorna l'occorenza inserita.
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public DueDiligenceView inserisci(DueDiligenceView dueDiligenceView) throws Throwable {
		DueDiligence dueDiligence = dueDilingenceDao.inserisci(dueDiligenceView.getVo());
		DueDiligenceView view = new DueDiligenceView();
		view.setVo(dueDiligence);
		return view;
	}

	/**
	 * Esegue la modifca di una determinata occorrenza
	 * @param dueDiligenceView
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void modifica(DueDiligenceView dueDiligenceView) throws Throwable {
		dueDilingenceDao.modifica(dueDiligenceView.getVo());
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void modificaVo(DueDiligence dueDiligence) throws Throwable {
		dueDilingenceDao.modifica(dueDiligence);
	}

	
	/**
	 * Inserisce occorrenza
	 * 
	 * @param dueDiligenceView
	 * @param documento array di byte
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public DueDiligenceView inserisci(DueDiligenceView dueDiligenceView,byte[] documento) throws Throwable {
		DueDiligence dueDiligence = dueDilingenceDao.inserisci(dueDiligenceView.getVo());
		DueDiligenceView view = new DueDiligenceView();
		view.setVo(dueDiligence);
		// qui codice per invio su filenet
		System.out.println(documento.length);
		return view;
	}

	/**
	 * Inserisce occorrenza
	 * (parte filenet da implementare )
	 * 
	 * @param dueDiligenceView
	 * @param documento array di byte
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void modifica(DueDiligenceView dueDiligenceView, byte[] documento) throws Throwable {
		dueDilingenceDao.modifica(dueDiligenceView.getVo());
		// qui codice per invio su filenet
		System.out.println(documento.length);
	}
	
	/**
	 * Ritorna elenco degli anni, per professionista esterno.
	 * <p>
	 * @param idProfEsterno
	 */
	@Override
	public List<Object> elencoAnni(long idProfEsterno) throws Throwable {
		return dueDilingenceDao.elencoAnni(idProfEsterno);
	}

	/**
	 * Elenco delle occorrenze per professionista esterno e per anno.
	 * <p>
	 * @param idProfEsterno
	 * @param anno 
	 */
	@Override
	public List<DueDiligenceView> elenco(long idProfEsterno, int anno) throws Throwable {
		List<DueDiligence> lista = dueDilingenceDao.elenco(idProfEsterno, anno);
		return convertiVoInView(lista);
	}
	
	@Override
	public DueDiligence addStep1Document(DueDiligence dueDiligence, long documentoStep1Id) throws Throwable {
		Documento documentoStep1 = new Documento();
		documentoStep1.setId(documentoStep1Id);
		DueDiligence dueDiligenceRead = dueDilingenceDao.leggi(dueDiligence.getId());
		dueDiligenceRead.setDocumentoStep1(documentoStep1);
		dueDilingenceDao.modifica(dueDiligenceRead);
		return dueDiligenceRead;
	}
	
	@Override
	public DocumentoDueDiligence addStep2Document(DueDiligence dueDiligence, long documentoStep2Id) throws Throwable {
		Documento documentoStep2 = new Documento();
		documentoStep2.setId(documentoStep2Id);
		DocumentoDueDiligence documentoDueDiligence = new DocumentoDueDiligence();
		documentoDueDiligence.setDocumento(documentoStep2);
		documentoDueDiligence.setDueDiligence(dueDiligence);
		
		documentoDueDiligence = documentoDueDiligenceDAO.save(documentoDueDiligence);
		return documentoDueDiligence;
	}
	
	@Override
	public void deleteStep2Document(long documentoDueDiligenceId) throws Throwable {
	
		DocumentoDueDiligence docDueDiligenceRead = documentoDueDiligenceDAO.read(documentoDueDiligenceId);
		if(docDueDiligenceRead != null){
			 Documento documento = docDueDiligenceRead.getDocumento();
			 documentoDAO.cancellaDocumento(documento);
			 
			 documentoDueDiligenceDAO.deleteDocumentoDueDiligence(docDueDiligenceRead);
		}
	}
	
	@Override
	public DueDiligence addStep3Document(DueDiligence dueDiligence, long documentoStep3Id) throws Throwable {
		Documento documentoStep3 = new Documento();
		documentoStep3.setId(documentoStep3Id);
		DueDiligence dueDiligenceRead = dueDilingenceDao.leggi(dueDiligence.getId());
		dueDiligenceRead.setDocumentoStep3(documentoStep3);
		dueDilingenceDao.modifica(dueDiligenceRead);
		return dueDiligenceRead;
	}
	
	@Override
	public List<DueDiligenceView> searchDueDiligenceByFilter(DueDiligenceView dueDiligenceView) throws Throwable {
		List<DueDiligence> list = dueDilingenceDao.searchRichAutGiudByFilter(dueDiligenceView);
		List<DueDiligenceView> convertedList = convertiVoInView(list);		
		return convertedList;
	}

	@Override
	protected Class<DueDiligence> leggiClassVO() { 
		return DueDiligence.class;
	}

	@Override
	protected Class<DueDiligenceView> leggiClassView() { 
		return DueDiligenceView.class;
	}
}