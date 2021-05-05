package eng.la.business;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import eng.la.persistence.*;
import it.snam.ned.libs.dds.dtos.v2.Document;
import org.apache.commons.io.IOUtils;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.PageIterator;
//@@DDS import com.filenet.api.core.Folder;
import com.filenet.apiimpl.core.DocumentImpl;
import com.filenet.apiimpl.core.EngineObjectImpl;*/
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;

import eng.la.model.AbstractEntity;
import eng.la.model.Autorizzazione;
import eng.la.model.BeautyContest;
import eng.la.model.Documento;
import eng.la.model.RBeautyContestMateria;
import eng.la.model.RBeautyContestProfessionistaEsterno;
import eng.la.model.TipoAutorizzazione;
import eng.la.model.Utente;
import eng.la.model.view.BeautyContestView;
import eng.la.model.view.DocumentoView;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("beautyContestService")
public class BeautyContestServiceImpl extends BaseService<BeautyContest,BeautyContestView> implements BeautyContestService {

	/*@@DDS
	@Autowired
	private DocumentaleCryptDAO documentaleCryptDAO;
*/
	@Autowired
	private DocumentaleDdsCryptDAO documentaleDdsCryptDAO;

	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;
	
	@Autowired
	private AutorizzazioneDAO autorizzazioneDAO;
	
	@Autowired
	private UtenteDAO utenteDAO;
	
	@Autowired
	DocumentoDAO documentoDAO;
	
	@Autowired
	private BeautyContestDAO beautyContestDAO;

	
	@Override
	public List<BeautyContestView> leggi() throws Throwable { 
		List<BeautyContest> lista = beautyContestDAO.leggi();
		List<BeautyContestView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	

	@Override
	public BeautyContestView leggi(long id) throws Throwable {
		BeautyContest beautyContest = beautyContestDAO.leggi(id);
		return convertiVoInView(beautyContest);
	}
	
	@Override
	public BeautyContestView leggi(long id, FetchMode fetchMode) throws Throwable {
		BeautyContest beautyContest = beautyContestDAO.leggi(id, fetchMode);
		return convertiVoInView(beautyContest);
	}
	
	
	@Override
	public BeautyContestView leggiTutti(long id) throws Throwable {
		BeautyContest beautyContest = beautyContestDAO.leggiTutti(id);
		return convertiVoInView(beautyContest);
	}

	@Override
	public ListaPaginata<BeautyContestView> cerca(String titolo, String dal, String al, String statoBeautyContestCode, String centroDiCosto, 
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<BeautyContest> lista = beautyContestDAO.cerca(titolo, dal, al, statoBeautyContestCode, centroDiCosto, elementiPerPagina, numeroPagina,
				ordinamento, ordinamentoDirezione);
		List<BeautyContestView> listaView = (List<BeautyContestView>) convertiVoInView(lista);
		ListaPaginata<BeautyContestView> listaRitorno = new ListaPaginata<BeautyContestView>();
		Long conta = beautyContestDAO.conta(titolo, dal, al, statoBeautyContestCode, centroDiCosto);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}


	@Override
	public Long conta(String titolo, String dal, String al, String statoBeautyContestCode, String centroDiCosto) throws Throwable {
		return beautyContestDAO.conta(titolo, dal, al, statoBeautyContestCode, centroDiCosto);
	}
 

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public BeautyContestView inserisci(BeautyContestView beautyContestView) throws Throwable {
		beautyContestView.getVo().setOperation(AbstractEntity.INSERT_OPERATION);
		beautyContestView.getVo().setOperationTimestamp(new Date());
		beautyContestView.getVo().setStatoBeautyContest(anagraficaStatiTipiDAO.leggiStatoBeautyContest(CostantiDAO.BEAUTY_CONTEST_STATO_BOZZA, beautyContestView.getLocale().getLanguage().toUpperCase()));
		beautyContestView.getVo().setDataEmissione(new Date());
		beautyContestView.getVo().setTitolo( "BeautyContest - " + beautyContestView.getVo().getTitolo());
		BeautyContest beautyContest = beautyContestDAO.inserisci(beautyContestView.getVo());
		
		salvaBeautyContestMateria(beautyContestView, beautyContest);
		salvaBeautyContestCandidati(beautyContestView, beautyContest);
		
		SessionFactory sessionFactory = (SessionFactory) SpringUtil.getBean("sessionFactory");
		sessionFactory.getCurrentSession().flush();
		
		BeautyContestView view = new BeautyContestView();
		view.setVo(beautyContest);
		 
		String cartellaBeautyContest = FileNetUtil.getCartellaPadreBeautyContest(beautyContest.getDataEmissione());
				
		String nomeCartella = beautyContest.getId()+"";
		String uuid = UUID.randomUUID().toString();
		String nomeClasseCartella = FileNetClassNames.BEAUTY_CONTEST_FOLDER; 
		Map<String, Object> proprietaCartella = new HashMap<String,Object>();
		long idBeautyContest = beautyContest.getId();
		proprietaCartella.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idBeautyContest+""));

		/*@@DDS
		documentaleCryptDAO.verificaCreaPercorsoCartella(cartellaBeautyContest);
		Folder cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaBeautyContest);
		
		documentaleCryptDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
		*/
		documentaleDdsCryptDAO.verificaCreaPercorsoCartella(cartellaBeautyContest);
		Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(cartellaBeautyContest);

		documentaleDdsCryptDAO.creaCartella(uuid, cartellaBeautyContest+ nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
		return view;
	}
	

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(BeautyContestView beautyContestView) throws Throwable {	
		beautyContestView.getVo().setOperation(AbstractEntity.UPDATE_OPERATION);
		beautyContestView.getVo().setOperationTimestamp(new Date());	
		beautyContestView.getVo().setDataModifica(new Date()); 
		beautyContestDAO.modifica(beautyContestView.getVo());
		
		BeautyContest beautyContest = beautyContestDAO.leggi(beautyContestView.getVo().getId());
		
		salvaBeautyContestMateria(beautyContestView, beautyContest);
		salvaBeautyContestCandidati(beautyContestView, beautyContest);
		
		rimuoviAllegati(beautyContestView);
		salvaAllegati(beautyContestView);
		
		rimuoviNotaAggiudicazioneFirmata(beautyContestView);
		salvaNotaAggiudicazioneFirmata(beautyContestView);
	}
	
	private void salvaBeautyContestMateria(BeautyContestView view, BeautyContest beautyContest) throws Throwable {
		if (view.getVo().getRBeautyContestMaterias() != null && view.getVo().getRBeautyContestMaterias().size() > 0) {
			beautyContestDAO.cancellaBeautyContestMaterie(beautyContest.getId());
			Collection<RBeautyContestMateria> lista = view.getVo().getRBeautyContestMaterias();
			for (RBeautyContestMateria vo : lista) {
				vo.setBeautyContest(beautyContest);
				beautyContestDAO.inserisciBeautyContestMateria(vo);
			}
		}
	}
	
	private void salvaBeautyContestCandidati(BeautyContestView view, BeautyContest beautyContest) throws Throwable {
		if (view.getVo().getRBeautyContestProfessionistaEsternos() != null && view.getVo().getRBeautyContestProfessionistaEsternos().size() > 0) {
			beautyContestDAO.cancellaBeautyContestProfessionistiEsterni(beautyContest.getId());
			Collection<RBeautyContestProfessionistaEsterno> lista = view.getVo().getRBeautyContestProfessionistaEsternos();
			for (RBeautyContestProfessionistaEsterno vo : lista) {
				vo.setBeautyContest(beautyContest);
				beautyContestDAO.inserisciBeautyContestProfessionistaEsterno(vo);
			}
		}
	}
	
	private void salvaAllegati(BeautyContestView beautyContestView) throws Throwable{
		if( beautyContestView.getListaAllegati() != null ){
			List<DocumentoView> allegati = beautyContestView.getListaAllegati();
			for(DocumentoView documentoView : allegati){
				if( documentoView.isNuovoDocumento() ){
				    InputStream contenuto = null;
					try{
				/*@@DDS
						Folder cartellaPadre = documentaleCryptDAO.leggiCartella( FileNetUtil.getBeautyContestCartella(beautyContestView.getVo().getId(), beautyContestView.getVo().getDataEmissione()));
						contenuto = new FileInputStream(documentoView.getFile());
						documentaleCryptDAO.creaDocumento(null, documentoView.getNomeFile(), FileNetClassNames.BEAUTY_CONTEST_DOCUMENT, documentoView.getContentType(), null, cartellaPadre, contenuto);
						*/
						Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella( FileNetUtil.getBeautyContestCartella(beautyContestView.getVo().getId(), beautyContestView.getVo().getDataEmissione()));
						contenuto = new FileInputStream(documentoView.getFile());
						documentaleDdsCryptDAO.creaDocumento(null, documentoView.getNomeFile(), FileNetClassNames.BEAUTY_CONTEST_DOCUMENT, documentoView.getContentType(), null, cartellaPadre.getFolderPath(), contenuto);
				   }catch(Throwable e){
					  e.printStackTrace(); 
					  throw e;
				   }finally {
				     IOUtils.closeQuietly(contenuto);
				   }
				
				}
			}
		}
	}

	private void rimuoviAllegati(BeautyContestView beautyContestView) throws Throwable {
		if( beautyContestView.getAllegatiDaRimuovereUuid() != null ){

			Set<String> uuidSet = beautyContestView.getAllegatiDaRimuovereUuid();
			for( String uuid : uuidSet ){
				//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
				documentaleDdsCryptDAO.eliminaDocumento(uuid);
			}
		}

	}
	
	private void salvaNotaAggiudicazioneFirmata(BeautyContestView beautyContestView) throws Throwable{

		if( beautyContestView.getNotaAggiudicazioneFirmataDoc() != null ){

			DocumentoView documentoView = beautyContestView.getNotaAggiudicazioneFirmataDoc();
			if( documentoView.isNuovoDocumento() ){

				InputStream contenuto = null;
				try{
					String uuid = UUID.randomUUID().toString();
					Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.NOTA_AGGIUDICAZIONE_BEAUTY_CONTEST_DOCUMENT, documentoView.getNomeFile(), documentoView.getContentType());
					
					//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella( FileNetUtil.getBeautyContestCartella(beautyContestView.getVo().getId(), beautyContestView.getVo().getDataEmissione()));
					Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella( FileNetUtil.getBeautyContestCartella(beautyContestView.getVo().getId(), beautyContestView.getVo().getDataEmissione()));
						contenuto = new FileInputStream(documentoView.getFile());
						
						Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
						proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(beautyContestView.getVo().getId()+""));
						
						//@@DDS documentaleCryptDAO.creaDocumento(uuid, documento.getNomeFile(), FileNetClassNames.NOTA_AGGIUDICAZIONE_BEAUTY_CONTEST_DOCUMENT, documentoView.getContentType(), proprietaDocumento, cartellaPadre, contenuto);
					    documentaleDdsCryptDAO.creaDocumento(uuid, documento.getNomeFile(), FileNetClassNames.NOTA_AGGIUDICAZIONE_BEAUTY_CONTEST_DOCUMENT, documentoView.getContentType(), proprietaDocumento, cartellaPadre.getFolderPath(), contenuto);
				}catch(Throwable e){
					e.printStackTrace(); 
					throw e;
				}finally {
					IOUtils.closeQuietly(contenuto);
				}

			}
		}
	}
	
	private void rimuoviNotaAggiudicazioneFirmata(BeautyContestView beautyContestView) throws Throwable {

		String nomeCartellaBeautyContest = FileNetUtil.getBeautyContestCartella(beautyContestView.getVo().getId(), beautyContestView.getVo().getDataEmissione());
		/*@@DDS
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaProforma = documentaleCryptDAO.leggiCartella(nomeCartellaBeautyContest);
		*/
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");


		/*@@DDS inizio commento
		DocumentSet documenti = cartellaProforma.get_ContainedDocuments();

		if (documenti != null) {
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.NOTA_AGGIUDICAZIONE_BEAUTY_CONTEST_DOCUMENT)){
						String uuid = documento.get_Id().toString();
						documentaleCryptDAO.eliminaDocumento(uuid);
					}
				}
			}
		}

		 */
		List<Document> documenti = documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaBeautyContest);

		if (documenti != null) {
				for (Document documento:documenti) {

					if( documento.getDocumentalClass().equals(FileNetClassNames.NOTA_AGGIUDICAZIONE_BEAUTY_CONTEST_DOCUMENT)){
						String uuid = documento.getId().toString();
						documentaleDdsCryptDAO.eliminaDocumento(uuid);
					}
				}

		}
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(BeautyContestView beautyContestView) throws Throwable {
		beautyContestView.getVo().setOperation(AbstractEntity.DELETE_OPERATION);
		beautyContestView.getVo().setOperationTimestamp(new Date());
		beautyContestDAO.cancella(beautyContestView.getVo().getId());
	} 
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void salvaPermessiBeautyContest(Long bcId, String[] permessiScrittura, String[] permessiLettura)
			throws Throwable {
		BeautyContest beautyContest = beautyContestDAO.leggi(bcId, FetchMode.JOIN);
		autorizzazioneDAO.cancellaAutorizzazioni(bcId, Costanti.TIPO_ENTITA_BEAUTYCONTEST, beautyContest.getLegaleInterno());

		if (permessiScrittura != null) {
			for (String permessoUserId : permessiScrittura) {
				Autorizzazione autorizzazione = new Autorizzazione();
				autorizzazione.setIdEntita(bcId);
				TipoAutorizzazione tipoAutorizzazione = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
						CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA, Locale.ITALIAN.getLanguage().toUpperCase());
				autorizzazione.setTipoAutorizzazione(tipoAutorizzazione);
				autorizzazione.setNomeClasse(Costanti.TIPO_ENTITA_BEAUTYCONTEST);
				Utente utente = utenteDAO.leggiUtenteDaUserId(permessoUserId);
				autorizzazione.setUtente(utente);
				autorizzazioneDAO.inserisci(autorizzazione);
			}
		}

		if (permessiLettura != null) {
			for (String permessoUserId : permessiLettura) {
				Autorizzazione autorizzazione = new Autorizzazione();
				autorizzazione.setIdEntita(bcId);
				TipoAutorizzazione tipoAutorizzazione = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
						CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_LETTURA, Locale.ITALIAN.getLanguage().toUpperCase());
				autorizzazione.setTipoAutorizzazione(tipoAutorizzazione);
				autorizzazione.setNomeClasse(Costanti.TIPO_ENTITA_BEAUTYCONTEST);
				Utente utente = utenteDAO.leggiUtenteDaUserId(permessoUserId);
				autorizzazione.setUtente(utente);
				autorizzazioneDAO.inserisci(autorizzazione);
			}
		}
	}

	@Override
	protected Class<BeautyContest> leggiClassVO() {
		return BeautyContest.class;
	}

	@Override
	protected Class<BeautyContestView> leggiClassView() {
		return BeautyContestView.class;
	}
}
