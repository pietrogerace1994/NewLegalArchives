package eng.la.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import eng.la.persistence.*;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//@@DDS import com.filenet.api.core.Document;
import it.snam.ned.libs.dds.dtos.v2.Document;
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;

import eng.la.model.Documento;
import eng.la.model.Nazione;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.RProfDocumento;
import eng.la.model.RProfEstSpec;
import eng.la.model.RProfessionistaNazione;
import eng.la.model.StudioLegale;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.RProfDocumentoView;
import eng.la.model.view.RProfEstSpecView;
import eng.la.model.view.RProfessionistaNazioneView;
import eng.la.util.ListaPaginata;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("professionistaEsternoService")
public class ProfessionistaEsternoServiceImpl extends BaseService<ProfessionistaEsterno,ProfessionistaEsternoView> implements ProfessionistaEsternoService {
	
	@Autowired
	private ProfessionistaEsternoDAO professionistaEsternoDAO;

	public ProfessionistaEsternoDAO getProfessionistaEsternoDAO() {
		return professionistaEsternoDAO;
	}

	public void setProfessionistaEsternoDAO(ProfessionistaEsternoDAO professionistaEsternoDAO) {
		this.professionistaEsternoDAO = professionistaEsternoDAO;
	}
	/*@@DDS
	@Autowired
	private DocumentaleDAO documentaleDAO;
	public void setDocumentaleDao(DocumentaleDAO dao) {
		this.documentaleDAO = dao;
	}

	public DocumentaleDAO getDocumentaleDao() {
		return documentaleDAO;
	}
	*/
	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;

	public void setDocumentaleDdsDao(DocumentaleDdsDAO dao) {
		this.documentaleDdsDAO = dao;
	}

	public DocumentaleDdsDAO getDocumentaleDdsDao() {
		return documentaleDdsDAO;
	}
	
	@Autowired
	private DocumentoDAO documentoDAO;

	public void setDocumentoDao(DocumentoDAO dao) {
		this.documentoDAO = dao;
	}

	public DocumentoDAO getDocumentoDao() {
		return documentoDAO;
	}
	
	@Autowired
	private NazioneDAO nazioneDAO;

	public NazioneDAO getNazioneDAO() {
		return nazioneDAO;
	}

	public void setNazioneDAO(NazioneDAO nazioneDAO) {
		this.nazioneDAO = nazioneDAO;
	}
	
	@Autowired
	private StudioLegaleDAO studioLegaleDAO;

	public StudioLegaleDAO getStudioLegaleDAO() {
		return studioLegaleDAO;
	}

	public void setStudioLegaleDAO(StudioLegaleDAO studioLegaleDAO) {
		this.studioLegaleDAO = studioLegaleDAO;
	}
	
	
	@Override
	public List<ProfessionistaEsternoView> leggi(boolean tutti) throws Throwable {
		List<ProfessionistaEsterno> lista = professionistaEsternoDAO.leggi(tutti);
		List<ProfessionistaEsternoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	/**
	 * Ritorna un oggetto ProfessionistaEsternoView riportando anche 
	 * le informazioni sulle Nazioni e Specializzazioni.
	 * 
	 * @param  tutti
	 * @return ProfessionistaEsternoView con la classe entity
	 *         ProfessionistaEsterno incapsulata
	 * @throws Throwable
	 */
	@Override
	public List<ProfessionistaEsternoView> leggiNazioneSpecializzazione(boolean tutti) throws Throwable {
		List<ProfessionistaEsterno> lista = professionistaEsternoDAO.leggiNazioneSpecializzazione(tutti);
		List<ProfessionistaEsternoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	/**
	 * Ritorna l'elenco dei professionisti esterni di una data tipologia.
	 * 
	 * @param tipologiaProfessionista tipologia dei professionisti
	 * @param tutti booleano per indicare se recuerare anche quelli con validita ultimata
	 * @return lista di oggetti ProfessionistaEsternoView con la classe entity
	 *         ProfessionistaEsterno incapsulata
	 * @throws Throwable
	 */
	@Override
	public List<ProfessionistaEsternoView> leggiProfessionistiPerCategoria(String tipologiaProfessionista, boolean tutti) throws Throwable {
		List<ProfessionistaEsterno> lista = professionistaEsternoDAO.leggiProfessionistiPerCategoria(tipologiaProfessionista, tutti);
		List<ProfessionistaEsternoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	/**
	 * Ritorna un oggetto ProfessionistaEsternoView.
	 * 
	 * @param id
	 * @return ProfessionistaEsternoView con la classe entity
	 *         ProfessionistaEsterno incapsulata
	 * @throws Throwable
	 */
	@Override
	public ProfessionistaEsternoView leggi(long id) throws Throwable {
		ProfessionistaEsterno professionistaEsterno = professionistaEsternoDAO.leggi(id);
		return (ProfessionistaEsternoView) convertiVoInView(professionistaEsterno);
	}
	
	/**
	 * Ritorna una lista di ProfessionistaEsternoView in base ai parametri di
	 * ricerca impostati. Sui parametri di tipo string viene effettuata una like
	 * insentitive anywhere
	 * 
	 * @param cognome
	 * @param nome
	 * @param studioLegaleId
	 * @return List<ProfessionistaEsternoView>
	 * @throws Throwable
	 */
	@Override
	public List<ProfessionistaEsternoView> cerca(String cognome, String nome, long studioLegaleId) throws Throwable {
		List<ProfessionistaEsterno> lista = professionistaEsternoDAO.cerca(cognome, nome, studioLegaleId);
		List<ProfessionistaEsternoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;

	}
	
	@Override
	public List<RProfessionistaNazioneView> leggiProfNazionebyId(long id) throws Throwable {
		List<RProfessionistaNazione> lista = professionistaEsternoDAO.leggiProfNazionebyId(id);
		
		if(lista != null){
			List<RProfessionistaNazioneView> listaRitorno = new ArrayList<RProfessionistaNazioneView>();
			for( RProfessionistaNazione vo : lista ){
				RProfessionistaNazioneView view = new RProfessionistaNazioneView();
				view.setVo(vo);
				listaRitorno.add(view);
			}
			return listaRitorno;
		}
		return null;
	}
	
	@Override
	public List<RProfEstSpecView> leggiProfSpecbyId(long id) throws Throwable {
		List<RProfEstSpec> lista = professionistaEsternoDAO.leggiProfSpecbyId(id);
		
		if(lista != null){
			List<RProfEstSpecView> listaRitorno = new ArrayList<RProfEstSpecView>();
			for( RProfEstSpec vo : lista ){
				RProfEstSpecView view = new RProfEstSpecView();
				view.setVo(vo);
				listaRitorno.add(view);
			}
			return listaRitorno;
		}
		return null;
	}
	
	@Override
	public List<RProfDocumentoView> leggiProfDocbyId(long id) throws Throwable {
		List<RProfDocumento> lista = professionistaEsternoDAO.leggiProfDocbyId(id);
		
		if(lista != null){
			List<RProfDocumentoView> listaRitorno = new ArrayList<RProfDocumentoView>();
			for( RProfDocumento vo : lista ){
				RProfDocumentoView view = new RProfDocumentoView();
				view.setVo(vo);
				listaRitorno.add(view);
			}
			return listaRitorno;
		}
		return null;
	}
	
	@Override
	public ProfessionistaEsternoView leggi(long id, FetchMode fetchMode) throws Throwable {
		ProfessionistaEsterno vo = professionistaEsternoDAO.leggi(id, fetchMode); 
		return (ProfessionistaEsternoView) convertiVoInView(vo);
	}
	
	/**
	 * Ritorna una lista paginata di ProfessionistaEsternoView in base ai parametri di
	 * ricerca impostati. Sui parametri di tipo string viene effettuata una like
	 * insentitive anywhere
	 * 
	 * @param cognome
	 * @param nome
	 * @param studioLegaleId
	 * @param elementiPerPagina
	 * @param numeroPagina
	 * @param ordinamento
	 * @param ordinamentoDirezione
	 * @return ListaPaginata<ProfessionistaEsternoView>
	 * @throws Throwable
	 */
	@Override
	public ListaPaginata<ProfessionistaEsternoView> cerca(String cognome, String nome, long studioLegaleId,
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione )
			throws Throwable {
		List<ProfessionistaEsterno> lista = professionistaEsternoDAO.cerca(cognome, nome, studioLegaleId, elementiPerPagina, numeroPagina, 
				ordinamento, ordinamentoDirezione );
		List<ProfessionistaEsternoView> listaView = convertiVoInView(lista);
		ListaPaginata<ProfessionistaEsternoView> listaRitorno = new ListaPaginata<ProfessionistaEsternoView>();
		Long conta = professionistaEsternoDAO.conta(cognome, nome, studioLegaleId); 
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta); 
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno; 
	}
	
	/**
	 * ritorna in numero di record presenti sul db in base ai criteri di filtro impostati
	 * @param cognome
	 * @param nome
	 * @param studioLegaleId 
	 * @return Integer
	 * @throws Throwable
	 * */
	@Override
	public Long conta(String cognome, String nome, long studioLegaleId) throws Throwable { 
		return professionistaEsternoDAO.conta(cognome, nome, studioLegaleId);
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public ProfessionistaEsternoView inserisci(ProfessionistaEsternoView professionistaEsternoView) throws Throwable {
		
		if(professionistaEsternoView.getTipoStudioLegale().equalsIgnoreCase("1")){
			salvaStudioLegale(professionistaEsternoView);
		}
		
		ProfessionistaEsterno professionistaEsterno = professionistaEsternoDAO.inserisci(professionistaEsternoView.getVo());
		
		salvaProfessionistaEsternoNazione(professionistaEsternoView, professionistaEsterno);
		salvaProfessionistaEsternoSpec(professionistaEsternoView, professionistaEsterno);
		
		if(professionistaEsternoView.getFileProfEst()!=null && 
				professionistaEsternoView.getFileProfEst().getSize()>0){
			aggiungiDocumento(professionistaEsterno.getId(), professionistaEsternoView.getFileProfEst());
		}
		
		ProfessionistaEsternoView view = new ProfessionistaEsternoView();
		view.setVo(professionistaEsterno);
		return view;
	}
	
	public void aggiungiDocumento(Long professionistaEsternoId, MultipartFile file) throws Throwable {
		ProfessionistaEsternoView view = leggi(professionistaEsternoId);
		
		if( view == null || view.getVo() == null ){
			throw new RuntimeException("Professionista Esterno con id:" + professionistaEsternoId + " non trovato");
		}
		
		String uuid = UUID.randomUUID().toString();
		Documento documento = documentoDAO.creaDocumentoDB( uuid, FileNetClassNames.PROFESSIONISTA_ESTERNO_DOCUMENT, file.getOriginalFilename(), file.getContentType());
		RProfDocumento doc = professionistaEsternoDAO.aggiungiDocumento(professionistaEsternoId, documento.getId());
		String cartellaProfessionistaEsterno = FileNetUtil.getProfessionistaEsternoCartella(view.getVo().getNome(), view.getVo().getCognome(), view.getVo().getCodiceFiscale());
		
		Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(doc.getId()+""));
		
		//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaProfessionistaEsterno);
		Folder cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaProfessionistaEsterno);
		
		if(cartellaPadre == null){
			//@@DDS documentaleDAO.verificaCreaPercorsoCartella(cartellaProfessionistaEsterno);
			//@@DDS cartellaPadre = documentaleDAO.leggiCartella(cartellaProfessionistaEsterno);
			documentaleDdsDAO.verificaCreaPercorsoCartella(cartellaProfessionistaEsterno);
			cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaProfessionistaEsterno);
		}
		
		byte[] contenuto = file.getBytes();
		//@@DDS documentaleDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.PROFESSIONISTA_ESTERNO_DOCUMENT, file.getContentType(), proprietaDocumento, cartellaPadre, contenuto);
		documentaleDdsDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.PROFESSIONISTA_ESTERNO_DOCUMENT, file.getContentType(), proprietaDocumento, cartellaPadre.getFolderPath(), contenuto);
	} 
	
	private void salvaStudioLegale(ProfessionistaEsternoView view) throws Throwable {
		StudioLegale studioLegale = new StudioLegale();
		studioLegale.setCap(view.getStudioLegaleCap());
		studioLegale.setCitta(view.getStudioLegaleCitta());
		studioLegale.setCodiceSap(view.getStudioLegaleCodiceSap());
		studioLegale.setDenominazione(view.getStudioLegaleDenominazione());
		studioLegale.setEmail(view.getStudioLegaleEmail());
		studioLegale.setFax(view.getStudioLegaleFax());
		studioLegale.setIndirizzo(view.getStudioLegaleIndirizzo());
		studioLegale.setPartitaIva(view.getStudioLegalePartitaIva());
		studioLegale.setTelefono(view.getStudioLegaleTelefono());
		Nazione nazione = nazioneDAO.leggi(view.getStudioLegaleNazioneCode(), Locale.ITALIAN.getLanguage().toUpperCase(), false);
		studioLegale.setNazione(nazione);
		StudioLegale studioLegaleInserito = studioLegaleDAO.inserisci(studioLegale);
		view.getVo().setStudioLegale(studioLegaleInserito);
	}
	
	private void modificaStudioLegale(ProfessionistaEsternoView view) throws Throwable {
		StudioLegale studioLegale = new StudioLegale();
		studioLegale.setCap(view.getStudioLegaleCap());
		studioLegale.setCitta(view.getStudioLegaleCitta());
		studioLegale.setCodiceSap(view.getStudioLegaleCodiceSap());
		studioLegale.setDenominazione(view.getStudioLegaleDenominazione());
		studioLegale.setEmail(view.getStudioLegaleEmail());
		studioLegale.setFax(view.getStudioLegaleFax());
		studioLegale.setIndirizzo(view.getStudioLegaleIndirizzo());
		studioLegale.setPartitaIva(view.getStudioLegalePartitaIva());
		studioLegale.setTelefono(view.getStudioLegaleTelefono());
		Nazione nazione = nazioneDAO.leggi(view.getStudioLegaleNazioneCode(), Locale.ITALIAN.getLanguage().toUpperCase(), false);
		studioLegale.setNazione(nazione);
		studioLegale.setId(new Long(view.getStudioLegaleId()));
		StudioLegale studioLegaleInserito = studioLegaleDAO.modifica(studioLegale);
		view.getVo().setStudioLegale(studioLegaleInserito);
	}
	
	private void salvaProfessionistaEsternoNazione(ProfessionistaEsternoView view, ProfessionistaEsterno professionistaEsterno) throws Throwable {
		if( view.getVo().getRProfessionistaNaziones()!= null && view.getVo().getRProfessionistaNaziones().size() > 0 ){
			professionistaEsternoDAO.cancellaProfessionistaEsternoNazione(professionistaEsterno.getId());
			Collection<RProfessionistaNazione> listaProfEsternoNazione = view.getVo().getRProfessionistaNaziones();
			for( RProfessionistaNazione profEsternoNazione : listaProfEsternoNazione ){
				profEsternoNazione.setProfessionistaEsterno(professionistaEsterno);
				professionistaEsternoDAO.inserisciProfessionistaEsternoNazione(profEsternoNazione); 
			}
		} 
	}
	
	private void salvaProfessionistaEsternoSpec(ProfessionistaEsternoView view, ProfessionistaEsterno professionistaEsterno) throws Throwable {
		if( view.getVo().getRProfEstSpecs()!= null && view.getVo().getRProfEstSpecs().size() > 0 ){
			professionistaEsternoDAO.cancellaProfessionistaEsternoSpec(professionistaEsterno.getId());
			Collection<RProfEstSpec> listaProfEsternoSpec = view.getVo().getRProfEstSpecs();
			for( RProfEstSpec profEsternoSpec : listaProfEsternoSpec ){
				profEsternoSpec.setProfessionistaEsterno(professionistaEsterno);
				professionistaEsternoDAO.inserisciProfessionistaEsternoSpec(profEsternoSpec); 
			}
		} 
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(ProfessionistaEsternoView professionistaEsternoView) throws Throwable {
		
		modificaStudioLegale(professionistaEsternoView);
		
		professionistaEsternoDAO.modifica(professionistaEsternoView.getVo());
		
		if(professionistaEsternoView.getFileProfEst()!=null && 
				professionistaEsternoView.getFileProfEst().getSize()>0){
			aggiungiDocumento(professionistaEsternoView.getVo().getId(), professionistaEsternoView.getFileProfEst());
		}
		
		if (professionistaEsternoView.getDocumentiDaEliminare() != null
				&& professionistaEsternoView.getDocumentiDaEliminare().length > 0) {
			String[] document = professionistaEsternoView.getDocumentiDaEliminare();
			for (String uuid : document) {
				cancellaDocumento(uuid);
				documentoDAO.cancella(uuid);			
				//@@DDS documentaleDAO.eliminaDocumento(uuid);
				documentaleDdsDAO.eliminaDocumento(uuid);
			}
		}
		
		ProfessionistaEsterno professionistaEsterno = professionistaEsternoDAO.leggi(professionistaEsternoView.getVo().getId());
		salvaProfessionistaEsternoNazione(professionistaEsternoView, professionistaEsterno);
		salvaProfessionistaEsternoSpec(professionistaEsternoView, professionistaEsterno);
		

		
	}

	/**
	 * Effettua la cancellazione dell'entity ProfessionistaEsterno incapsulata nel bean ProfessionistaEsternoView
	 * @param professionistaEsternoView
	 * @throws Throwable
	 */
	@Override
	public void cancella(ProfessionistaEsternoView professionistaEsternoView) throws Throwable {
		professionistaEsternoDAO.cancella(professionistaEsternoView.getVo().getId());
	}

	public void cancellaDocumento(String uuid) throws Throwable {
		Documento vo = documentoDAO.leggi(uuid);
		professionistaEsternoDAO.cancellaDocumento(vo.getId());
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		professionistaEsternoDAO.cancella(id);
		professionistaEsternoDAO.cancellaLogicProfessionistaEsternoNazione(id);
		professionistaEsternoDAO.cancellaLogicProfessionistaEsternoSpec(id);
	}
	
	@Override
	public ProfessionistaEsternoView leggi(long id, boolean tutti) throws Throwable { 
		ProfessionistaEsterno professionistaEsterno = professionistaEsternoDAO.leggi(id, tutti);
		return (ProfessionistaEsternoView) convertiVoInView(professionistaEsterno);
	}
	
	@Override
	public Document leggiDocumentoUUID(String uuid) throws Throwable {
		//@@DDS return documentaleDAO.leggiDocumentoUUID(uuid);
		return documentaleDdsDAO.leggiDocumentoUUID(uuid);
	}
	
	@Override
	public byte[] leggiContenutoDocumentoUUID(String uuid) throws Throwable {
		//@@DDS byte[] docuByte= documentaleDAO.leggiContenutoDocumento(uuid);
		byte[] docuByte= documentaleDdsDAO.leggiContenutoDocumento(uuid);
		
	return docuByte;
	}
	
	@Override
	public List<ProfessionistaEsternoView> leggiProfessionistiAbilitatiBeautyContest() throws Throwable {
		List<ProfessionistaEsterno> lista = professionistaEsternoDAO.leggiAbilitatiContest();
		List<ProfessionistaEsternoView> listaRitorno = (List<ProfessionistaEsternoView>)convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	protected Class<ProfessionistaEsterno> leggiClassVO() {
		return ProfessionistaEsterno.class;
	}

	@Override
	protected Class<ProfessionistaEsternoView> leggiClassView() {
		return ProfessionistaEsternoView.class;
	} 

}
