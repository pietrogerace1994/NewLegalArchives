package eng.la.business;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import eng.la.persistence.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;

import eng.la.model.Autorizzazione;
import eng.la.model.Documento;
import eng.la.model.DocumentoProcure;
import eng.la.model.Fascicolo;
import eng.la.model.Procure;
import eng.la.model.TipoAutorizzazione;
import eng.la.model.Utente;
import eng.la.model.aggregate.ProcureAggregate;
import eng.la.model.filter.ProcureFilter;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.ProcureView;
import eng.la.util.ListaPaginata;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("procureService")
public class ProcureServiceImpl extends BaseService<Procure,ProcureView> implements ProcureService {
	private static final Logger logger = Logger.getLogger(ProcureServiceImpl.class);
	/*@@DDS @Autowired
	private DocumentaleDAO documentaleDAO;
	*/

	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;

	@Autowired
	private DocumentoDAO documentoDAO;
	
	@Autowired
	private ProcureDAO procureDAO;
	
	@Autowired
	private DocumentoProcureDAO documentoProcureDAO;
	
	@Autowired
	private AutorizzazioneDAO autorizzazioneDAO;
	
	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;
	
	@Autowired
	private UtenteDAO utenteDAO;
	
	@Autowired
	private FascicoloDAO fascicoloDAO;
	
	@Override
	public List<ProcureView> leggi() throws Throwable {
		List<Procure> lista = procureDAO.leggi();
		List<ProcureView> listaRitorno = (List<ProcureView>)convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public ProcureView leggi(long id) throws Throwable {
		Procure procure = procureDAO.leggi(id);
		return (ProcureView) convertiVoInView(procure);
	}

	@Override
	public ListaPaginata<ProcureView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		List<Procure> lista = procureDAO.cerca(nome, elementiPerPagina, numeroPagina, ordinamento,
				ordinamentoDirezione);
		List<ProcureView> listaView = (List<ProcureView>)convertiVoInView(lista);
		ListaPaginata<ProcureView> listaRitorno = new ListaPaginata<ProcureView>();
		Long conta = procureDAO.conta(nome);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	public List<ProcureView> cerca(String nome) throws Throwable {
		List<Procure> lista = procureDAO.cerca(nome);
		List<ProcureView> listaRitorno = (List<ProcureView>)convertiVoInView(lista);
		return listaRitorno;
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public ProcureView inserisci(ProcureView procureView) throws Throwable {
		Procure procure = procureDAO.inserisci(procureView.getVo());
		salvaAllegatiGenerici(procureView);
		ProcureView view = new ProcureView();
		view.setVo(procure);
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void modifica(ProcureView procureView) throws Throwable {
		procureDAO.modifica(procureView.getVo());
		rimuoviAllegatiGenerici(procureView);
		salvaAllegatiGenerici(procureView);

	}

	public void salvaAllegatiGenerici(ProcureView procureView) throws Throwable {
		if(procureView.getVo() == null){
			throw new RuntimeException("Procure non trovato");
		}

		if( procureView.getListaAllegatiGenerici() != null ){
			
			List<DocumentoView> allegatiGenerici = procureView.getListaAllegatiGenerici();


			for(DocumentoView documentoView : allegatiGenerici ) {
				if( documentoView.isNuovoDocumento() ){
				    InputStream contenuto = null;
					try{
						
						String uuid = UUID.randomUUID().toString();
						Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.PROCURE_DOCUMENT, 
								documentoView.getNomeFile(), documentoView.getContentType());
						
						Procure procureSaved = null;
						DocumentoProcure documentoProcure = addDocument(procureView.getVo(), documento);
						procureSaved = documentoProcure != null ? documentoProcure.getProcure() : null;
				
					    contenuto = new FileInputStream(documentoView.getFile());
					    
					    Map<String, Object> documentProperty = new HashMap<String, Object>();
						documentProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(procureSaved.getId() + ""));
						Folder cartellaPadre = getProcureParentFolder(procureSaved.getId(), procureView.getVo().getDataConferimento());
						//documentaleDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.PROCURE_DOCUMENT, documentoView.getContentType(),	documentProperty, cartellaPadre, contenuto);
						documentaleDdsDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.PROCURE_DOCUMENT, documentoView.getContentType(),	documentProperty, cartellaPadre.getFolderPath(), contenuto);
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
	
	@Transactional(rollbackFor = Throwable.class, propagation=Propagation.REQUIRED)
	public void salvaAllegatiGenericiConferite(ProcureView procureView) throws Throwable {
		if(procureView.getVo() == null){
			throw new RuntimeException("Procure non trovato");
		}
		
		if( procureView.getListaAllegatiGenerici() != null ){
			
			List<DocumentoView> allegatiGenerici = procureView.getListaAllegatiGenerici();
			Procure procure = procureDAO.inserisci(procureView.getVo());
			for(DocumentoView documentoView : allegatiGenerici ) {
				if( documentoView.isNuovoDocumento() ){
				    InputStream contenuto = null;
					try{
						String uuid = UUID.randomUUID().toString();
						Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.PROCURE_DOCUMENT, 
								documentoView.getNomeFile(), documentoView.getContentType());
						contenuto = new FileInputStream(documentoView.getFile());
					    DocumentoProcure documentoProcure = new DocumentoProcure();
					    documentoProcure.setProcure(procure);
					    documentoProcure.setDocumento(documento);
					    documentoProcureDAO.save(documentoProcure);
						Map<String, Object> documentProperty = new HashMap<String, Object>();
						documentProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(procure.getId() + ""));
						Folder cartellaPadre = getProcureParentFolder(procure.getId(), procureView.getVo().getDataConferimento());
				        //@@DDS documentaleDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.PROCURE_DOCUMENT, documentoView.getContentType(),	documentProperty, cartellaPadre, contenuto);
						documentaleDdsDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.PROCURE_DOCUMENT, documentoView.getContentType(),	documentProperty, cartellaPadre.getFolderPath(), contenuto);
						
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
	
	
	private Folder getProcureParentFolder(Long idProcure, Date dataApertura) throws Throwable {
		if(idProcure != null && dataApertura != null){
			String parentFolderName = FileNetUtil.getProcureParentFolder(dataApertura);
			String folderName = idProcure + "-PROCURE";
			String uuid = UUID.randomUUID().toString();
			String folderClassName = FileNetClassNames.PROCURE_FOLDER; 
			Map<String, Object> folderProperty = new HashMap<String,Object>();
			folderProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idProcure+""));
			
			//@@DDS Folder parentFolder = documentaleDAO.leggiCartella(parentFolderName);
			Folder parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			if(parentFolder == null){ 
				/*@@DDS documentaleDAO.verificaCreaPercorsoCartella(parentFolderName);
				parentFolder = documentaleDAO.leggiCartella(parentFolderName);
				*/
				documentaleDdsDAO.verificaCreaPercorsoCartella(parentFolderName);
				parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			}
			
			String procureFolderName = idProcure + "-PROCURE";
			procureFolderName = parentFolderName + procureFolderName + "/";
			//@@DDS Folder procureFolder = documentaleDAO.leggiCartella(procureFolderName);
			Folder procureFolder = documentaleDdsDAO.leggiCartella(procureFolderName);
			logger.info("@@DDS ___________ ProcureServiceImpl " + parentFolder);
			if(procureFolder == null){
				/*@@DDS documentaleDAO.creaCartella(uuid, folderName, folderClassName, folderProperty, parentFolder);
				procureFolder = documentaleDAO.leggiCartella(procureFolderName);
				*/
				documentaleDdsDAO.creaCartella(uuid, parentFolder+folderName, folderClassName, folderProperty, parentFolder);
				procureFolder = documentaleDdsDAO.leggiCartella(procureFolderName);
			}
			
			return procureFolder;
		}
		return null;
	}
	
	private void rimuoviAllegatiGenerici(ProcureView procureView) throws Throwable {
		if( procureView.getAllegatiDaRimuovereUuid() != null ){
			
		
			Set<String> uuidSet = procureView.getAllegatiDaRimuovereUuid();
			for( String uuid : uuidSet ){
				//@@DDS documentaleDAO.eliminaDocumento(uuid);
				documentaleDdsDAO.eliminaDocumento(uuid);
			}
		}
			
	}


	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void cancella(long idProcure) throws Throwable {
		procureDAO.cancella(idProcure);
	}

	@Override
	public Long conta(String nome) throws Throwable { 
		return procureDAO.conta(nome);
	}

	@Override
	public ProcureView leggi(String codice, Locale locale) throws Throwable {
		Procure procure = procureDAO.leggi(codice, locale.getLanguage().toUpperCase());
		ProcureView procureView = new ProcureView();
		procureView.setVo(procure);
		return procureView;
	}

	@Override
	public List<ProcureView> leggi(Locale locale) throws Throwable {
		List<Procure> lista = procureDAO.leggi(locale.getLanguage().toUpperCase());
		List<ProcureView> listaRitorno = (List<ProcureView>) convertiVoInView(lista);
		return listaRitorno;
	}
	
	public DocumentoProcure addDocument(Procure procure, Documento documento) throws Throwable {
		procure = procureDAO.inserisci(procure);
		DocumentoProcure documentoProcure = new DocumentoProcure();
		documentoProcure.setDocumento(documento);
		documentoProcure.setProcure(procure);
		documentoProcure = documentoProcureDAO.save(documentoProcure);
		return documentoProcure;
	}
	

	@Override
	protected Class<Procure> leggiClassVO() {
		return Procure.class;
	}

	@Override
	protected Class<ProcureView> leggiClassView() {
		return ProcureView.class;
	}


	@Override
	public ProcureAggregate searchPagedProcure(ProcureFilter filter) throws Throwable {
		return procureDAO.searchPagedProcure(filter);
	}


	@Override
	public void salvaPermessiProcure(Long procureId, String[] permessiScrittura,
			String[] permessiLettura) throws Throwable {
		Procure procure = procureDAO.leggi(procureId);
		autorizzazioneDAO.cancellaAutorizzazioni(procureId, Costanti.TIPO_ENTITA_PROCURE,
				procure.getLegaleInterno());

		if (permessiScrittura != null) {
			for (String permessoUserId : permessiScrittura) {
				Autorizzazione autorizzazione = new Autorizzazione();
				autorizzazione.setIdEntita(procureId);
				TipoAutorizzazione tipoAutorizzazione = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
						CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA, Locale.ITALIAN.getLanguage().toUpperCase());
				autorizzazione.setTipoAutorizzazione(tipoAutorizzazione);
				autorizzazione.setNomeClasse(Costanti.TIPO_ENTITA_PROCURE);
				Utente utente = utenteDAO.leggiUtenteDaUserId(permessoUserId);
				autorizzazione.setUtente(utente);
				autorizzazioneDAO.inserisci(autorizzazione);
			}
		}

		if (permessiLettura != null) {
			for (String permessoUserId : permessiLettura) {
				Autorizzazione autorizzazione = new Autorizzazione();
				autorizzazione.setIdEntita(procureId);
				TipoAutorizzazione tipoAutorizzazione = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
						CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_LETTURA, Locale.ITALIAN.getLanguage().toUpperCase());
				autorizzazione.setTipoAutorizzazione(tipoAutorizzazione);
				autorizzazione.setNomeClasse(Costanti.TIPO_ENTITA_PROCURE);
				Utente utente = utenteDAO.leggiUtenteDaUserId(permessoUserId);
				autorizzazione.setUtente(utente);
				autorizzazioneDAO.inserisci(autorizzazione);
			}
		}
	}
	
	
	@Override
	public void associaProcureAFascicolo(Long procureId, Long fascicoloId) throws Throwable {
		Procure procure = procureDAO.leggi(procureId);
		Fascicolo fascicolo = fascicoloDAO.leggi(fascicoloId);
		
		procure.setFascicolo(fascicolo);
				
		procureDAO.modifica(procure);
		ProcureView view = new ProcureView();
		view.setVo(procure);
		rimuoviAllegatiGenerici(view);
		salvaAllegatiGenerici(view);
	}
	
	@Override
	public List<ProcureView> leggiDaFascicolo(long idFascicolo) throws Throwable {
		List<Procure> lista = procureDAO.leggiDaFascicolo(idFascicolo);
		List<ProcureView> listaRitorno = (List<ProcureView>) convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public boolean checkProcuraSocieta(Long idProcura) throws Throwable {
		ProcureView procuraSelezionata = this.leggi(idProcura);
		boolean  rs =  true;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (procuraSelezionata!=null && procuraSelezionata.getVo().getSocieta().getDataCancellazione() != null) {
			if ("31/12/2999".equalsIgnoreCase(df.format(procuraSelezionata.getVo().getDataCancellazione()))) {
				rs = false;
			}
		}
		return rs;
	}

}
