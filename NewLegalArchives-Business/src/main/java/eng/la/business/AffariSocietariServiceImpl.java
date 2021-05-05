package eng.la.business;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

//@@DDS import com.filenet.api.core.Folder;
import eng.la.persistence.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;

import eng.la.model.AffariSocietari;
import eng.la.model.Documento;
import eng.la.model.DocumentoAffariSocietari;
import eng.la.model.RSocietaAffari;
import eng.la.model.aggregate.AffariSocietariAggregate;
import eng.la.model.filter.AffariSocietariFilter;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.view.AffariSocietariView;
import eng.la.model.view.DocumentoView;
import eng.la.util.ListaPaginata;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("affariSocietariService")
public class AffariSocietariServiceImpl extends BaseService implements AffariSocietariService {
	private static final Logger logger = Logger.getLogger(AffariSocietariServiceImpl.class);

/*@@DDS
	@Autowired
	private DocumentaleDAO documentaleDAO;

	public void setDocumentaleDao(DocumentaleDAO dao) {
		this.documentaleDAO = dao;
	}
*/
	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;

	public void setDocumentaleDdsDao(DocumentaleDdsDAO dao) {
		this.documentaleDdsDAO = dao;
	}

	@Autowired
	private DocumentoDAO documentoDAO;
	public void setDocumentoDAO(DocumentoDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	}
	
	
	@Autowired
	private DocumentoAffariSocietariDAO documentoAffariSocietariDAO;
	public void setDocumentoAffariSocietariDAO(DocumentoAffariSocietariDAO documentoAffariSocietariDAO) {
		this.documentoAffariSocietariDAO = documentoAffariSocietariDAO;
	}
	
	
	@Autowired
	private AffariSocietariDAO affariSocietariDAO;
	public void setDao(AffariSocietariDAO dao) {
		this.affariSocietariDAO = dao;
	}

	
	@Override
	public List<AffariSocietariView> leggi() throws Throwable {
		List<AffariSocietari> lista = affariSocietariDAO.leggi();
		@SuppressWarnings("unchecked")
		List<AffariSocietariView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public AffariSocietariView leggi(long id) throws Throwable {
		AffariSocietari affariSocietari = affariSocietariDAO.leggi(id);
		return (AffariSocietariView) convertiVoInView(affariSocietari);
	}

	@Override
	public ListaPaginata<AffariSocietariView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		List<AffariSocietari> lista = affariSocietariDAO.cerca(nome, elementiPerPagina, numeroPagina, ordinamento,
				ordinamentoDirezione);
		@SuppressWarnings("unchecked")
		List<AffariSocietariView> listaView = convertiVoInView(lista);
		ListaPaginata<AffariSocietariView> listaRitorno = new ListaPaginata<AffariSocietariView>();
		Long conta = affariSocietariDAO.conta(nome);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	public List<AffariSocietariView> cerca(String nome) throws Throwable {
		List<AffariSocietari> lista = affariSocietariDAO.cerca(nome);
		@SuppressWarnings("unchecked")
		List<AffariSocietariView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public AffariSocietariView inserisci(AffariSocietariView affariSocietariView) throws Throwable {
		
		AffariSocietari affariSocietari = affariSocietariDAO.inserisci(affariSocietariView.getVo());
		affariSocietariView.setVo(affariSocietari);
		salvaSocietaAffariSoci(affariSocietariView, affariSocietari);
		salvaAllegatiGenerici(affariSocietariView);
		AffariSocietariView view = new AffariSocietariView();
		view.setVo(affariSocietari);
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public long modifica(AffariSocietariView affariSocietariView) throws Throwable {
		
		affariSocietariView.getVo().setDataCancellazione(new Date());
		affariSocietariDAO.modifica(affariSocietariView.getVo());
		
		AffariSocietari newAS = affariSocietariDAO.inserisci(clonaVo(affariSocietariView.getVo()));
		
		affariSocietariView.setVo(newAS);
		salvaSocietaAffariSoci(affariSocietariView, newAS);
		salvaAllegatiGenerici(affariSocietariView);
		
		return newAS.getId();

	}
	
	private void salvaSocietaAffariSoci(AffariSocietariView view, AffariSocietari affariSocietari) throws Throwable {
		if (view.getVo().getRSocietaAffaris() != null && view.getVo().getRSocietaAffaris().size() > 0) {
			affariSocietariDAO.cancellaRSocietaAffari(affariSocietari.getId());
			Collection<RSocietaAffari> lista = view.getVo().getRSocietaAffaris();
			for (RSocietaAffari vo : lista) {
				vo.setSocietaAffari(affariSocietari);
				affariSocietariDAO.inserisciRSocietaAffari(vo);
			}
		}
	}
	
	private AffariSocietari clonaVo(AffariSocietari vo){
		AffariSocietari newVo = new AffariSocietari();
		newVo.setCodiceSocieta(vo.getCodiceSocieta());
		newVo.setDenominazione(vo.getDenominazione());
		newVo.setCapitaleSottoscritto(vo.getCapitaleSottoscritto());
		newVo.setCapitaleSociale(vo.getCapitaleSociale());
		newVo.setDataCostituzione(vo.getDataCostituzione());
		newVo.setDataUscita(vo.getDataUscita());
		newVo.setDenominazioneBreve(vo.getDenominazioneBreve());
		newVo.setSiglaFormaGiuridica(vo.getSiglaFormaGiuridica());
		newVo.setFormaGiuridica(vo.getFormaGiuridica());
		newVo.setNazione(vo.getNazione());
		newVo.setSiglaStatoProvincia(vo.getSiglaStatoProvincia());
		newVo.setSiglaProvincia(vo.getSiglaProvincia());
		newVo.setItaliaEstero(vo.getItaliaEstero());
		newVo.setUeExstraue(vo.getUeExstraue());
		newVo.setSedeLegale(vo.getSedeLegale());
		newVo.setIndirizzo(vo.getIndirizzo());
		newVo.setCap(vo.getCap());
		newVo.setCodiceFiscale(vo.getCodiceFiscale());
		newVo.setPartitaIva(vo.getPartitaIva());
		newVo.setDataRea(vo.getDataRea());
		newVo.setNumeroRea(vo.getNumeroRea());
		newVo.setComuneRea(vo.getComuneRea());
		newVo.setSiglaProvinciaRea(vo.getSiglaProvinciaRea());
		newVo.setProvinciaRea(vo.getProvinciaRea());
		newVo.setQuotazione(vo.getQuotazione());
		newVo.setInLiquidazione(vo.getInLiquidazione());
		newVo.setModelloDiGovernance(vo.getModelloDiGovernance());
		newVo.setnComponentiCDA(vo.getnComponentiCDA());
		newVo.setnComponentiCollegioSindacale(vo.getnComponentiCollegioSindacale());
		newVo.setnComponentiOdv(vo.getnComponentiOdv());
		newVo.setCodiceNazione(vo.getCodiceNazione());
		newVo.setSocietaDiRevisione(vo.getSocietaDiRevisione());
		newVo.setDataIncarico(vo.getDataIncarico());
		newVo.setIdControllante(vo.getIdControllante());
		newVo.setPercentualeControllante(vo.getPercentualeControllante());
		newVo.setPercentualeTerzi(vo.getPercentualeTerzi());
		newVo.setDataCostituzione(vo.getDataCostituzione());
		newVo.setRSocietaAffaris(vo.getRSocietaAffaris());

		return newVo;
	}



	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void cancella(long idAffariSocietari) throws Throwable {
		affariSocietariDAO.cancella(idAffariSocietari);
		DocumentoAffariSocietari drs= documentoAffariSocietariDAO.leggiPerIdAffariSocietari(idAffariSocietari);
		rimuoviAllegatiGenerici(drs.getDocumento().getUuid());
	}

	@Override
	public Long conta(String nome) throws Throwable { 
		return affariSocietariDAO.conta(nome);
	}

	@Override
	public AffariSocietariView leggi(String codice, Locale locale) throws Throwable {
		AffariSocietari affariSocietari = affariSocietariDAO.leggi(codice, locale.getLanguage().toUpperCase());
		AffariSocietariView affariSocietariView = new AffariSocietariView();
		affariSocietariView.setVo(affariSocietari);
		return affariSocietariView;
	}

	@Override
	public List<AffariSocietariView> leggi(Locale locale) throws Throwable {
		List<AffariSocietari> lista = affariSocietariDAO.leggi(locale.getLanguage().toUpperCase());
		@SuppressWarnings("unchecked")
		List<AffariSocietariView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	protected Class<AffariSocietari> leggiClassVO() {
		return AffariSocietari.class;
	}

	@Override
	protected Class<AffariSocietariView> leggiClassView() {
		return AffariSocietariView.class;
	}


	@Override
	public AffariSocietariAggregate searchPagedAffariSocietari(AffariSocietariFilter filter, List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable {
		return affariSocietariDAO.searchPagedAffariSocietari(filter, listaSNAM_SRG_GNL_STOGIT);
	}


	private void salvaAllegatiGenerici(AffariSocietariView repertorioView) throws Throwable {
		if(repertorioView.getVo() == null){
			throw new RuntimeException("AffariSocietari non trovato");
		}

		if( repertorioView.getAllegato() != null ){
			
			List<DocumentoView> allegatiGenerici = repertorioView.getAllegato();
			//@@DDS Folder cartellaPadre = getAffariSocietariParentFolder(repertorioView.getVo().getId(), repertorioView.getVo().getDataCostituzione());
			Folder cartellaPadre = getAffariSocietariParentFolder(repertorioView.getVo().getId(), repertorioView.getVo().getDataCostituzione());
			for(DocumentoView documentoView : allegatiGenerici ){
				if( documentoView.isNuovoDocumento() ){
				    InputStream contenuto = null;
					try{
						
						String uuid = UUID.randomUUID().toString();
						Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.AFFARI_SOCIETARI_DOCUMENT, 
								documentoView.getNomeFile(), documentoView.getContentType());
						
						@SuppressWarnings("unused")
						AffariSocietari repertorioSaved = null;
						 DocumentoAffariSocietari documentoAffariSocietari = addDocument(repertorioView.getVo(), documento.getId());
						repertorioSaved = documentoAffariSocietari != null ? documentoAffariSocietari.getAffariSocietari() : null;

				
					    contenuto = new FileInputStream(documentoView.getFile());
					    
					    Map<String, Object> documentProperty = new HashMap<String, Object>();
						documentProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(repertorioView.getVo().getId() + ""));
						
						//@@DDS documentaleDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.AFFARI_SOCIETARI_DOCUMENT, documentoView.getContentType(),	documentProperty, cartellaPadre, contenuto);
						documentaleDdsDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.AFFARI_SOCIETARI_DOCUMENT, documentoView.getContentType(),	documentProperty, cartellaPadre.getFolderPath(), contenuto);
				        
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
	
	private Folder getAffariSocietariParentFolder(Long idAffariSocietari, Date dataApertura) throws Throwable {
		if(idAffariSocietari != null && dataApertura != null){
			String parentFolderName = FileNetUtil.getAffariSocietariParentFolder(dataApertura);
			String folderName = idAffariSocietari + "-AFFARI_SOCIETARI";
			String uuid = UUID.randomUUID().toString();
			String folderClassName = FileNetClassNames.AFFARI_SOCIETARI_FOLDER; 
			Map<String, Object> folderProperty = new HashMap<String,Object>();
			folderProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idAffariSocietari+""));
			
			//@@DDS Folder parentFolder = documentaleDAO.leggiCartella(parentFolderName);
			Folder parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			if(parentFolder == null){ 
				/*DDS documentaleDAO.verificaCreaPercorsoCartella(parentFolderName);
				parentFolder = documentaleDAO.leggiCartella(parentFolderName);
				 */
				documentaleDdsDAO.verificaCreaPercorsoCartella(parentFolderName);
				parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			}
			
			String repertorioFolderName = idAffariSocietari + "-AFFARI_SOCIETARI";
			repertorioFolderName = parentFolderName + repertorioFolderName + "/";
			Folder repertorioFolder = documentaleDdsDAO.leggiCartella(repertorioFolderName); //@@DDS documentaleDAO.leggiCartella(repertorioFolderName);
			logger.info("@@DDS ___________ AffariSocietari" + parentFolder);
			if(repertorioFolder == null){
/*DDS				documentaleDAO.creaCartella(uuid, folderName, folderClassName, folderProperty, parentFolder);
				repertorioFolder = documentaleDAO.leggiCartella(repertorioFolderName);
*/
				documentaleDdsDAO.creaCartella(uuid, parentFolder+folderName, folderClassName, folderProperty, parentFolder);
				repertorioFolder = documentaleDdsDAO.leggiCartella(repertorioFolderName);
			}
			
			return repertorioFolder;
		}
		return null;
	}
	
	private void rimuoviAllegatiGenerici(String uuid) throws Throwable {
		if (uuid != null && !uuid.isEmpty())
			//@@DDS documentaleDAO.eliminaDocumento(uuid);
			documentaleDdsDAO.eliminaDocumento(uuid);
	}
	
	@Override
	public DocumentoAffariSocietari addDocument(AffariSocietari repertorio, long documentoId) throws Throwable {
		Documento documento = new Documento();
		documento.setId(documentoId);
		DocumentoAffariSocietari documentoAffariSocietari = new DocumentoAffariSocietari();
		documentoAffariSocietari.setDocumento(documento);
		documentoAffariSocietari.setAffariSocietari(repertorio);
		
		documentoAffariSocietari = documentoAffariSocietariDAO.save(documentoAffariSocietari);
		return documentoAffariSocietari;
	}


	@Override
	public List<CodiceDescrizioneBean> leggiListaSocietaControllanti() throws Throwable {
		List<AffariSocietari> lista = affariSocietariDAO.leggi();
		List<CodiceDescrizioneBean> listaControllanti = new ArrayList<CodiceDescrizioneBean>();
		for (Iterator<AffariSocietari> iterator = lista.iterator(); iterator.hasNext();) {
			AffariSocietari affariSocietari = (AffariSocietari) iterator.next();
			CodiceDescrizioneBean cd = new CodiceDescrizioneBean();
			cd.setId(affariSocietari.getId());
			cd.setDescrizione(affariSocietari.getDenominazione());
			listaControllanti.add(cd);
		}
		return listaControllanti;
	}
	
	@Override
	public List<Long> getListaSNAM_SRG_GNL_STOGIT() throws Throwable {
		List<AffariSocietari> lista = affariSocietariDAO.getListaSNAM_SRG_GNL_STOGIT();
		
		List<Long> listaId = new ArrayList<Long>();
		for (AffariSocietari affariSocietari : lista) {
			listaId.add(affariSocietari.getId());
		}
		return listaId;
	}
}
