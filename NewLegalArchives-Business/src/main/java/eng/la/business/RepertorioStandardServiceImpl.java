package eng.la.business;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
import eng.la.model.Documento;
import eng.la.model.DocumentoRepertorioStandard;
import eng.la.model.PosizioneOrganizzativa;
import eng.la.model.PrimoLivelloAttribuzioni;
import eng.la.model.RepertorioStandard;
import eng.la.model.SecondoLivelloAttribuzioni;
import eng.la.model.aggregate.RepertorioStandardAggregate;
import eng.la.model.filter.RepertorioStandardFilter;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.RepertorioStandardView;
import eng.la.util.ListaPaginata;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("repertorioStandardService")
public class RepertorioStandardServiceImpl extends BaseService<RepertorioStandard,RepertorioStandardView> implements RepertorioStandardService {
	private static final Logger logger = Logger.getLogger(RepertorioStandardServiceImpl.class);
/*
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
	private DocumentoRepertorioStandardDAO documentoRepertorioStandardDAO;
	public void setDocumentoRepertorioStandardDAO(DocumentoRepertorioStandardDAO documentoRepertorioStandardDAO) {
		this.documentoRepertorioStandardDAO = documentoRepertorioStandardDAO;
	}
	
	
	@Autowired
	private RepertorioStandardDAO repertorioStandardDAO;
	public void setDao(RepertorioStandardDAO dao) {
		this.repertorioStandardDAO = dao;
	}
	
	@Autowired
	private PosizioneOrganizzativaDAO posizioneOrganizzativaDAO;
	public void setPosizioneOrganizzativaDAO(PosizioneOrganizzativaDAO dao) {
		this.posizioneOrganizzativaDAO = dao;
	}
	
	@Autowired
	private PrimoLivelloAttribuzioniDAO primoLivelloAttribuzioniDAO;
	public void setPrimoLivelloAttribuzioniDAO(PrimoLivelloAttribuzioniDAO dao) {
		this.primoLivelloAttribuzioniDAO = dao;
	}
	
	@Autowired
	private SecondoLivelloAttribuzioniDAO secondoLivelloAttribuzioniDAO;
	public void setSecondoLivelloAttribuzioniDAO(SecondoLivelloAttribuzioniDAO dao) {
		this.secondoLivelloAttribuzioniDAO = dao;
	}
	
	@Override
	public List<RepertorioStandardView> leggi() throws Throwable {
		List<RepertorioStandard> lista = repertorioStandardDAO.leggi();
		List<RepertorioStandardView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public List<String> findSocieta() throws Throwable {
		return repertorioStandardDAO.findSocieta();
	}
	
	
	@Override
	public RepertorioStandardView leggi(long id) throws Throwable {
		RepertorioStandard repertorioStandard = repertorioStandardDAO.leggi(id);
		return (RepertorioStandardView) convertiVoInView(repertorioStandard);
	}

	@Override
	public ListaPaginata<RepertorioStandardView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		List<RepertorioStandard> lista = repertorioStandardDAO.cerca(nome, elementiPerPagina, numeroPagina, ordinamento,
				ordinamentoDirezione);
		List<RepertorioStandardView> listaView = convertiVoInView(lista);
		ListaPaginata<RepertorioStandardView> listaRitorno = new ListaPaginata<RepertorioStandardView>();
		Long conta = repertorioStandardDAO.conta(nome);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	public List<RepertorioStandardView> cerca(String nome) throws Throwable {
		List<RepertorioStandard> lista = repertorioStandardDAO.cerca(nome);
		List<RepertorioStandardView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public RepertorioStandardView inserisci(RepertorioStandardView repertorioStandardView) throws Throwable {
		
		int max=0;
		List<String> codGruppoLinguaList = repertorioStandardDAO.getCodGruppoLinguaList(repertorioStandardView.getVo().getLingua());
		for (int i = 0; i < codGruppoLinguaList.size(); i++) {
			String cod = codGruppoLinguaList.get(i);
			if (cod != null && cod.contains("_")) {
			String[] parts = cod.split("_");
			int intValue = Integer.valueOf(parts[1]).intValue();
			if(intValue > max){
				max = intValue;
				}
			}
		}
		String codice = "RSTD_"+String.valueOf(max + 1);
		repertorioStandardView.getVo().setCodGruppoLingua(codice);
		
		RepertorioStandard repertorioStandard = repertorioStandardDAO.inserisci(repertorioStandardView.getVo());
		repertorioStandardView.setVo(repertorioStandard);
		salvaAllegatiGenerici(repertorioStandardView);
		RepertorioStandardView view = new RepertorioStandardView();
		view.setVo(repertorioStandard);
		return view;
	}
	
	
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public RepertorioStandard inserisciRest(RepertorioStandardView repertorioStandardView) throws Throwable {
		int max=0;
		List<String> codGruppoLinguaList = repertorioStandardDAO.getCodGruppoLinguaList(repertorioStandardView.getVo().getLingua());
		for (int i = 0; i < codGruppoLinguaList.size(); i++) {
			String cod = codGruppoLinguaList.get(i);
			String[] parts = cod.split("_");
			int intValue = Integer.valueOf(parts[1]).intValue();
			if(intValue > max){
				max = intValue;
			}
		}
		String codice = "RSTD_"+String.valueOf(max + 1);
		repertorioStandardView.getVo().setCodGruppoLingua(codice);
		
		RepertorioStandard repertorioStandard = repertorioStandardDAO.inserisci(repertorioStandardView.getVo());
		return repertorioStandard;
	}
	

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void modifica(RepertorioStandardView repertorioStandardView) throws Throwable {
		
		repertorioStandardDAO.modifica(repertorioStandardView.getVo());
		rimuoviAllegatiGenerici(repertorioStandardView);
		salvaAllegatiGenerici(repertorioStandardView);
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void cancella(long idRepertorioStandard) throws Throwable {
		repertorioStandardDAO.cancella(idRepertorioStandard);
		DocumentoRepertorioStandard drs= documentoRepertorioStandardDAO.leggiPerIdRepertorio(idRepertorioStandard);
		rimuoviAllegatiGenerici(drs.getDocumento().getUuid());
	}

	@Override
	public Long conta(String nome) throws Throwable { 
		return repertorioStandardDAO.conta(nome);
	}

	@Override
	public RepertorioStandardView leggi(String codice, Locale locale) throws Throwable {
		RepertorioStandard repertorioStandard = repertorioStandardDAO.leggi(codice, locale.getLanguage().toUpperCase());
		RepertorioStandardView repertorioStandardView = new RepertorioStandardView();
		repertorioStandardView.setVo(repertorioStandard);
		return repertorioStandardView;
	}

	@Override
	public List<RepertorioStandardView> leggi(Locale locale) throws Throwable {
		List<RepertorioStandard> lista = repertorioStandardDAO.leggi(locale.getLanguage().toUpperCase());
		List<RepertorioStandardView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	protected Class<RepertorioStandard> leggiClassVO() {
		return RepertorioStandard.class;
	}

	@Override
	protected Class<RepertorioStandardView> leggiClassView() {
		return RepertorioStandardView.class;
	}


	@Override
	public RepertorioStandardAggregate searchPagedRepertorioStandard(RepertorioStandardFilter filter) throws Throwable {
		return repertorioStandardDAO.searchPagedRepertorioStandard(filter);
	}


	@Override
	public List<CodiceDescrizioneBean> leggiListaPosizioneOrganizzativa(Locale locale) {
		List<CodiceDescrizioneBean> listaBean = new ArrayList<>();
		try {
			List<PosizioneOrganizzativa> lista = posizioneOrganizzativaDAO.leggi(locale.getLanguage().toUpperCase());
			
			for (Iterator<PosizioneOrganizzativa> iterator = lista.iterator(); iterator.hasNext();) {
				PosizioneOrganizzativa po = iterator.next();
				
				CodiceDescrizioneBean bean = new CodiceDescrizioneBean();
				bean.setId(po.getId());
				bean.setDescrizione(po.getDescrizione());
				listaBean.add(bean);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return listaBean;
	}


	@Override
	public List<CodiceDescrizioneBean> leggiListaPrimoLivelloAttribuzioni(Locale locale) {
		List<CodiceDescrizioneBean> listaBean = new ArrayList<>();
		try {
			List<PrimoLivelloAttribuzioni> lista = primoLivelloAttribuzioniDAO.leggi(locale.getLanguage().toUpperCase());
			
			for (Iterator<PrimoLivelloAttribuzioni> iterator = lista.iterator(); iterator.hasNext();) {
				PrimoLivelloAttribuzioni pl = (PrimoLivelloAttribuzioni) iterator.next();
				
				CodiceDescrizioneBean bean = new CodiceDescrizioneBean();
				bean.setId(pl.getId());
				bean.setDescrizione(pl.getDescrizione());
				listaBean.add(bean);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return listaBean;
	}


	@Override
	public List<CodiceDescrizioneBean> leggiListaSecondoLivelloAttribuzioni(Locale locale) {
		List<CodiceDescrizioneBean> listaBean = new ArrayList<>();
		try {
			List<SecondoLivelloAttribuzioni> lista = secondoLivelloAttribuzioniDAO.leggi(locale.getLanguage().toUpperCase());
			
			for (Iterator<SecondoLivelloAttribuzioni> iterator = lista.iterator(); iterator.hasNext();) {
				SecondoLivelloAttribuzioni sl = (SecondoLivelloAttribuzioni) iterator.next();
				
				CodiceDescrizioneBean bean = new CodiceDescrizioneBean();
				bean.setId(sl.getId());
				bean.setDescrizione(sl.getDescrizione());
				listaBean.add(bean);
				
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return listaBean;
	}
	
	@Override
	public void salvaAllegatiGenerici(RepertorioStandardView repertorioView) throws Throwable {
		if(repertorioView.getVo() == null){
			throw new RuntimeException("RepertorioStandard non trovato");
		}

		if( repertorioView.getAllegato() != null ){
			
			List<DocumentoView> allegatiGenerici = repertorioView.getAllegato();

			for(DocumentoView documentoView : allegatiGenerici ){
				if( documentoView.isNuovoDocumento() ){
				    InputStream contenuto = null;
					try{
						
						String uuid = UUID.randomUUID().toString();
						Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.REPERTORIO_STANDARD_DOCUMENT, 
								documentoView.getNomeFile(), documentoView.getContentType());
						
						RepertorioStandard repertorioSaved = null;
						 DocumentoRepertorioStandard documentoRepertorioStandard = addDocument(repertorioView.getVo(), documento);
						repertorioSaved = documentoRepertorioStandard != null ? documentoRepertorioStandard.getRepertorio() : null;
				
					    contenuto = new FileInputStream(documentoView.getFile());
					    
					    Map<String, Object> documentProperty = new HashMap<String, Object>();
						documentProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(repertorioView.getVo().getId() + ""));
						//@@DDS Folder cartellaPadre = getRepertorioStandardParentFolder(repertorioSaved.getId(), repertorioView.getVo().getDataCreazione());
						//@@DDS documentaleDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.REPERTORIO_STANDARD_DOCUMENT, documentoView.getContentType(),	documentProperty, cartellaPadre, contenuto);
						Folder cartellaPadre = getRepertorioStandardParentFolder(repertorioSaved.getId(), repertorioView.getVo().getDataCreazione());
						logger.debug ("@@DDS RepertorioStandadService cartellaPadre" + cartellaPadre);
						documentaleDdsDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.REPERTORIO_STANDARD_DOCUMENT, documentoView.getContentType(),	documentProperty, cartellaPadre.getFolderPath(), contenuto);
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
	
	private Folder getRepertorioStandardParentFolder(Long idRepertorioStandard, Date dataApertura) throws Throwable {
		if(idRepertorioStandard != null && dataApertura != null){
			String parentFolderName = FileNetUtil.getRepertorioStandardParentFolder(dataApertura);
			String folderName = idRepertorioStandard + "-REPERTORIO_STANDARD";
			String uuid = UUID.randomUUID().toString();
			String folderClassName = FileNetClassNames.REPERTORIO_STANDARD_FOLDER; 
			Map<String, Object> folderProperty = new HashMap<String,Object>();
			folderProperty.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idRepertorioStandard+""));
			
			//@@DDS Folder parentFolder = documentaleDAO.leggiCartella(parentFolderName);
			Folder parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			if(parentFolder == null){ 
				//@@DDS documentaleDAO.verificaCreaPercorsoCartella(parentFolderName);
				//@@DDS parentFolder = documentaleDAO.leggiCartella(parentFolderName);
				documentaleDdsDAO.verificaCreaPercorsoCartella(parentFolderName);
				parentFolder = documentaleDdsDAO.leggiCartella(parentFolderName);
			}
			
			String repertorioFolderName = idRepertorioStandard + "-REPERTORIO_STANDARD";
			repertorioFolderName = parentFolderName + repertorioFolderName + "/";
			//@@DDS Folder repertorioFolder = documentaleDAO.leggiCartella(repertorioFolderName);
			Folder repertorioFolder = documentaleDdsDAO.leggiCartella(repertorioFolderName);
			logger.info("@@DDS ___________ RepertorioStandardServiceImpl " + parentFolder);
			if(repertorioFolder == null){
				//@@DDS documentaleDAO.creaCartella(uuid, folderName, folderClassName, folderProperty, parentFolder);
				//@@DDS repertorioFolder = documentaleDAO.leggiCartella(repertorioFolderName);
				documentaleDdsDAO.creaCartella(uuid, parentFolder + folderName, folderClassName, folderProperty, parentFolder);
				repertorioFolder = documentaleDdsDAO.leggiCartella(repertorioFolderName);
			}
			
			return repertorioFolder;
		}
		return null;
	}
	
	private void rimuoviAllegatiGenerici(RepertorioStandardView repertorioView) throws Throwable {
		if( repertorioView.getAllegatoDaRimuovereUuid() != null ){
			
		
			String uuidSet = repertorioView.getAllegatoDaRimuovereUuid();
			//@@DDS documentaleDAO.eliminaDocumento(uuidSet);
			documentaleDdsDAO.eliminaDocumento(uuidSet);
		}
			
	}
	
	private void rimuoviAllegatiGenerici(String uuid) throws Throwable {
		if (uuid != null && !uuid.isEmpty())
			//@@DDS documentaleDAO.eliminaDocumento(uuid);
			documentaleDdsDAO.eliminaDocumento(uuid);
	}
	
	@Override
	public DocumentoRepertorioStandard addDocument(RepertorioStandard repertorio, Documento documento) throws Throwable {
		int max=0;
		List<String> codGruppoLinguaList = repertorioStandardDAO.getCodGruppoLinguaList(repertorio.getLingua());
		for (int i = 0; i < codGruppoLinguaList.size(); i++) {
			String cod = codGruppoLinguaList.get(i);
			if (cod != null && cod.contains("_")) {
			String[] parts = cod.split("_");
			int intValue = Integer.valueOf(parts[1]).intValue();
			if(intValue > max){
				max = intValue;
			}
			}
		}
		String codice = "RSTD_"+String.valueOf(max + 1);
		repertorio.setCodGruppoLingua(codice);
		RepertorioStandard voSalvata = this.repertorioStandardDAO.inserisci(repertorio);
		DocumentoRepertorioStandard documentoReportorio = new DocumentoRepertorioStandard();
		documentoReportorio.setDataCancellazione(null);
		documentoReportorio.setDocumento(documento);
		documentoReportorio.setRepertorio(voSalvata);
		DocumentoRepertorioStandard dc = this.documentoRepertorioStandardDAO.save(documentoReportorio);
		return dc;
	}
	
	@Override
	public PrimoLivelloAttribuzioni findPrimoLivelloByPk(long id) throws Throwable {
		return this.primoLivelloAttribuzioniDAO.leggi(id);
	}

	@Override
	public SecondoLivelloAttribuzioni findSecondoLivelloByPk(long id) throws Throwable {
		return this.secondoLivelloAttribuzioniDAO.leggi(id);
	}
	
	@Override
	public PosizioneOrganizzativa findPosizioneOrganizzByPk(long id) throws Throwable {
		return this.posizioneOrganizzativaDAO.leggi(id);
	}

}
