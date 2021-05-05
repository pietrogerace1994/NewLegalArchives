package eng.la.business;

import eng.la.model.*;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.ProformaView;
import eng.la.persistence.*;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;
import it.snam.ned.libs.dds.dtos.v2.Document;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

//@@DDS import com.filenet.api.core.Folder;
import org.apache.commons.io.IOUtils;
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

import it.snam.ned.libs.dds.dtos.v2.folder.Folder;//@@DDS

import eng.la.model.AbstractEntity;
import eng.la.model.Documento;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
import eng.la.model.Proforma;
import eng.la.model.RIncaricoProformaSocieta;
import eng.la.model.RProformaFattura;
import eng.la.model.SchedaValutazione;
import eng.la.model.StatoProforma;
import eng.la.model.TipoProforma;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.ProformaView;
import eng.la.model.view.TipoProformaView;
import eng.la.persistence.AnagraficaStatiTipiDAO;
import eng.la.persistence.DocumentaleCryptDAO;
import eng.la.persistence.DocumentaleDAO;
import eng.la.persistence.DocumentoDAO;
import eng.la.persistence.FascicoloDAO;
import eng.la.persistence.IncaricoDAO;
import eng.la.persistence.ProformaDAO;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("proformaService")
public class ProformaServiceImpl extends BaseService<Proforma, ProformaView> implements ProformaService {
	private static final Logger logger = Logger.getLogger(ProformaServiceImpl.class);

	@Autowired
	private DocumentaleDAO documentaleDAO;

	public void setDocumentaleDao(DocumentaleDAO dao) {
		this.documentaleDAO = dao;
	}

	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;

	public void setDocumentaleDdsDao(DocumentaleDdsDAO dao) {
		this.documentaleDdsDAO = dao;
	}
	
	@Autowired
	private DocumentaleCryptDAO documentaleCryptDAO;

	public void setDocumentaleCryptDAO(DocumentaleCryptDAO dao) {
		this.documentaleCryptDAO = dao;
	}

	@Autowired
	private DocumentaleDdsCryptDAO documentaleDdsCryptDAO;

	public void setDocumentaleDdsCryptDAO(DocumentaleDdsCryptDAO dao) {
		this.documentaleDdsCryptDAO = dao;
	}
	
	@Autowired
	private DocumentoDAO documentoDAO;

	public void setDocumentoDAO(DocumentoDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	} 
	
	@Autowired
	private FascicoloDAO fascicoloDAO;
 
	public void setFascicoloDAO(FascicoloDAO fascicoloDAO) {
		this.fascicoloDAO = fascicoloDAO;
	}
	
	@Autowired
	private ProformaDAO proformaDAO;

	public void setDao(ProformaDAO dao) {
		this.proformaDAO = dao;
	}

	public ProformaDAO getDao() {
		return proformaDAO;
	} 

	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO ;
	
	public void setAnagraficaStatiTipiDAO(AnagraficaStatiTipiDAO anagraficaStatiTipiDAO) {
		this.anagraficaStatiTipiDAO = anagraficaStatiTipiDAO;
	}

	public AnagraficaStatiTipiDAO getAnagraficaStatiTipiDAO() {
		return anagraficaStatiTipiDAO;
	}
	
	@Autowired
	private IncaricoDAO incaricoDAO;

	public void setIncaricoDAO(IncaricoDAO incaricoDAO) {
		this.incaricoDAO = incaricoDAO;
	}
	
	public IncaricoDAO getIncaricoDAO() {
		return incaricoDAO;
	}

	@Override
	public List<ProformaView> leggi() throws Throwable {
		List<Proforma> lista = proformaDAO.leggi();
		List<ProformaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public ProformaView leggi(long id) throws Throwable {
		Proforma proforma = proformaDAO.leggi(id);
		return (ProformaView) convertiVoInView(proforma);
	}

	@Override
	public ListaPaginata<ProformaView> cerca(String nome, String statocode,  String nomeFascicolo, String nomeIncarico, long societaAddebito, String dal, String al, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione,String fattura,String contabilizzato) throws Throwable {
		List<Proforma> lista = proformaDAO.cerca(nome, statocode, nomeFascicolo, nomeIncarico, societaAddebito, dal, al, elementiPerPagina, numeroPagina, ordinamento,
				ordinamentoDirezione, fattura,contabilizzato);
		List<ProformaView> listaView = convertiVoInView(lista);
		ListaPaginata<ProformaView> listaRitorno = new ListaPaginata<ProformaView>();
		Long conta = proformaDAO.conta(nome, statocode, nomeFascicolo, nomeIncarico, societaAddebito, dal, al, fattura,contabilizzato);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	public List<ProformaView> cerca(String nome) throws Throwable {
		List<Proforma> lista = proformaDAO.cerca(nome);
		List<ProformaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public List<ProformaView> cerca(List<String> parole) throws Throwable {
		List<Proforma> lista = proformaDAO.cerca(parole);
		List<ProformaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public Long conta(String nome, String statocode, String nomeFascicolo, String nomeIncarico, String dal, String al,String fattura,String contabilizzato) throws Throwable {
		return proformaDAO.conta(nome, statocode, nomeFascicolo, nomeIncarico, -1, dal, al, fattura,contabilizzato);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public ProformaView inserisci(ProformaView proformaView) throws Throwable {
		logger.info("@@DDS ------------------------  proforma inserisci + ");
		proformaView.getVo().setOperation(AbstractEntity.INSERT_OPERATION);
		proformaView.getVo().setOperationTimestamp(new Date());
		StatoProforma statoProforma = anagraficaStatiTipiDAO.leggiStatoProforma(Costanti.PROFORMA_STATO_BOZZA, Locale.ITALIAN.getLanguage().toUpperCase());
		proformaView.getVo().setStatoProforma(statoProforma); 
		
		Proforma proforma = proformaDAO.inserisci(proformaView.getVo()); 
		salvaSocietaProfoma(proformaView, proforma);
		SessionFactory sessionFactory = (SessionFactory) SpringUtil.getBean("sessionFactory");
		sessionFactory.getCurrentSession().flush(); 
		
		ProformaView view = new ProformaView();
		view.setVo(proforma);
		 
		Incarico incarico = incaricoDAO.leggi(view.getVo().getRIncaricoProformaSocietas().iterator().next().getIncarico().getId());
		
		Fascicolo fascicolo = incarico.getFascicolo();
		
		String cartellaFascicolo = FileNetUtil.getFascicoloCartella(fascicolo.getDataCreazione(), fascicolo.getNome());
		
		ProfessionistaEsternoView professionistaEsternoView = new ProfessionistaEsternoView();
		professionistaEsternoView.setVo(incarico.getProfessionistaEsterno());
		String nomeCartella = (proforma.getId()+"-Proforma - " + professionistaEsternoView.getCognomeNome() + " - " + DateUtil.getDataDDMMYYYY(proforma.getDataInserimento().getTime())).toUpperCase();
		nomeCartella = nomeCartella.replaceAll("\\/", "-");
		String uuid = UUID.randomUUID().toString();
		String nomeClasseCartella = FileNetClassNames.PROFORMA_FOLDER;
		Map<String, Object> proprietaCartella = new HashMap<String,Object>();
		long idProforma = proforma.getId();
		proprietaCartella.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idProforma+""));
		
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
	
		if( isPenale ){
			//@@DDS com.filenet.api.core.Folder cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaFascicolo);
			Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(cartellaFascicolo);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + cartellaFascicolo);
			}

			//@@DDS documentaleCryptDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
			documentaleDdsCryptDAO.creaCartella(uuid, cartellaFascicolo+nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
		}else{
			//@@DDS com.filenet.api.core.Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaFascicolo);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaFascicolo);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + cartellaFascicolo);
			}

			//@@DDS documentaleDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
			documentaleDdsDAO.creaCartella(uuid, cartellaFascicolo+nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
		}
		
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(ProformaView proformaView) throws Throwable {
		proformaView.getVo().setOperation(AbstractEntity.UPDATE_OPERATION);
		proformaView.getVo().setOperationTimestamp(new Date());
		//Documento documento = salvaAllegatoSchedaValutazione(proformaView);
		if( proformaView.getVo().getSchedaValutazione() != null ){
			if( proformaView.getVo().getSchedaValutazione().getId() == 0){
				SchedaValutazione schedaValutazione = proformaDAO.salvaSchedaValutazione(proformaView.getVo().getSchedaValutazione());
				proformaView.getVo().setSchedaValutazione(schedaValutazione);
			}
			else{
				SchedaValutazione schedaValutazione = proformaDAO.aggiornaSchedaValutazione(proformaView.getVo().getSchedaValutazione());
				proformaView.getVo().setSchedaValutazione(schedaValutazione);
			}
		}
		proformaView.getVo().setDataUltimaModifica(new Date());
		proformaDAO.modifica(proformaView.getVo());
		
		salvaSocietaProfoma(proformaView, proformaView.getVo());
 
		rimuoviAllegatiGenerici(proformaView);
		
		salvaAllegatiGenerici(proformaView); 
		
		rimuoviSchedaValutazioneFirmata(proformaView);
		salvaSchedaValutazioneFirmata(proformaView);
		
		


	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modificaPerWS(ProformaView proformaView) throws Throwable {
		proformaView.getVo().setOperation(AbstractEntity.UPDATE_OPERATION);
		proformaView.getVo().setOperationTimestamp(new Date());
		
		proformaView.getVo().setDataUltimaModifica(new Date());
		proformaDAO.modifica(proformaView.getVo());
		
	}
	
	
	
	private void salvaSocietaProfoma(ProformaView proformaView, Proforma vo) throws Throwable {
		if( vo.getId() > 0){ 
			 proformaDAO.cancellaSocietaProforma(vo.getId());
			 
			 SessionFactory sessionFactory = (SessionFactory) SpringUtil.getBean("sessionFactory");
			 sessionFactory.getCurrentSession().flush(); 
				
			 if( proformaView.getVo().getRIncaricoProformaSocietas() != null && proformaView.getVo().getRIncaricoProformaSocietas().size() > 0 ) {
				 RIncaricoProformaSocieta incaricoProformaSocieta = proformaView.getVo().getRIncaricoProformaSocietas().iterator().next();
				 incaricoProformaSocieta.setProforma(vo);
				 proformaDAO.salvaSocietaProfoma(incaricoProformaSocieta);
			 }
		}
	}

	/*private Documento salvaAllegatoSchedaValutazione(ProformaView proformaView) throws Throwable {
		InputStream is = null;
		try{ 
			RIncaricoProformaSocieta incaricoProformaSocieta = proformaView.getVo().getRIncaricoProformaSocietas().iterator().next();
			Incarico incarico = incaricoProformaSocieta.getIncarico();
			Fascicolo fascicolo = fascicoloDAO.leggi(incarico.getFascicolo().getId());
			boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		
			Proforma proforma = proformaView.getVo();
			if( proformaView.getSchedaValutazioneDoc() != null ){
				if( proformaView.getSchedaValutazioneDoc().isNuovoDocumento() ){ 
					if ( proforma.getSchedaValutazione() != null && proforma.getSchedaValutazione().getId() > 0){				
						String uuid = proforma.getSchedaValutazione().getDocumento().getUuid(); 
						SchedaValutazione schedaValutazione = proforma.getSchedaValutazione();
						proforma.setSchedaValutazione(null);
						proformaDAO.modifica(proforma);
						proformaDAO.cancellaSchedaValutazione(schedaValutazione.getId());
						documentoDAO.cancellaDocumento(documentoDAO.leggi(uuid));					
						if( isPenale ){
							documentaleCryptDAO.eliminaDocumento(uuid);
						}else{
							documentaleDAO.eliminaDocumento(uuid);
						} 
					}
					DocumentoView doc = proformaView.getSchedaValutazioneDoc();
					String uuid = UUID.randomUUID().toString();
					Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT, doc.getNomeFile(), doc.getContentType());	
					
					is = new FileInputStream(doc.getFile());

					proformaView.getVo().getSchedaValutazione().setDocumento(documento);
					proformaDAO.salvaSchedaValutazione(proformaView.getVo().getSchedaValutazione());		
					
					if( isPenale ){
						String cartellaFascicolo = FileNetUtil.getFascicoloCartella(fascicolo.getDataCreazione(), fascicolo.getNome());
						ProfessionistaEsternoView professionistaEsternoView = new ProfessionistaEsternoView();
						professionistaEsternoView.setVo(incarico.getProfessionistaEsterno());
						String nomeCartella = (proforma.getId()+"-Proforma - " + professionistaEsternoView.getCognomeNome() + " - " + DateUtil.getDataDDMMYYYY(proforma.getDataInserimento().getTime())).toUpperCase();
						nomeCartella = nomeCartella.replaceAll("\\/", "-");
												
						Folder cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaFascicolo+nomeCartella);
								
						Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
						proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(proformaView.getVo().getId()+""));
						documentaleCryptDAO.creaDocumento(uuid, documento.getNomeFile(), FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT, doc.getContentType(),
								proprietaDocumento, cartellaPadre, is);
					}else{
						String cartellaFascicolo = FileNetUtil.getFascicoloCartella(fascicolo.getDataCreazione(), fascicolo.getNome());
						ProfessionistaEsternoView professionistaEsternoView = new ProfessionistaEsternoView();
						professionistaEsternoView.setVo(incarico.getProfessionistaEsterno());
						String nomeCartella = (proforma.getId()+"-Proforma - " + professionistaEsternoView.getCognomeNome() + " - " + DateUtil.getDataDDMMYYYY(proforma.getDataInserimento().getTime())).toUpperCase();
						nomeCartella = nomeCartella.replaceAll("\\/", "-");
												
						Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaFascicolo+nomeCartella);
								
						Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
						proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(proformaView.getVo().getId()+""));
						documentaleDAO.creaDocumento(uuid, documento.getNomeFile(), FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT, doc.getContentType(),
								proprietaDocumento, cartellaPadre, is);
						
					}
					return documento;
				}
			}else{ 
				if ( proforma.getSchedaValutazione() != null && proforma.getSchedaValutazione().getId() > 0){		
					SchedaValutazione schedaValutazione = proforma.getSchedaValutazione();
					String uuid = proforma.getSchedaValutazione().getDocumento().getUuid();
					proforma.setSchedaValutazione(null);
					proformaDAO.modifica(proforma);
					proformaDAO.cancellaSchedaValutazione(schedaValutazione.getId());
					documentoDAO.cancellaDocumento(documentoDAO.leggi(uuid)); 
					if( isPenale ){
						documentaleCryptDAO.eliminaDocumento(uuid);
					}else{
						documentaleDAO.eliminaDocumento(uuid);
					} 
				}
				return null;
			}
		}finally{
			IOUtils.closeQuietly(is);
		}
		return null;
	}*/
 

	private void salvaAllegatiGenerici(ProformaView proformaView) throws Throwable{
		if( proformaView.getListaAllegatiGenerici() != null ){
			List<DocumentoView> allegatiGenerici = proformaView.getListaAllegatiGenerici();
			for(DocumentoView documentoView : allegatiGenerici ){
				if( documentoView.isNuovoDocumento() ){
				    InputStream contenuto = null;
					try{
						RIncaricoProformaSocieta incaricoProformaSocieta = proformaView.getVo().getRIncaricoProformaSocietas().iterator().next();
						Incarico incarico = incaricoProformaSocieta.getIncarico();
						Fascicolo fascicolo = fascicoloDAO.leggi(incarico.getFascicolo().getId());
						boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
							
					if( isPenale ){
						
						//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella( FileNetUtil.getProformaCartella(proformaView.getVo().getId(), fascicolo.getDataCreazione(),
						//@@DDS 		fascicolo.getNome(), proformaView.getNome() ));
						Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella( FileNetUtil.getProformaCartella(proformaView.getVo().getId(), fascicolo.getDataCreazione(),
								fascicolo.getNome(), proformaView.getNome() ));
						contenuto = new FileInputStream(documentoView.getFile());
						//@@DDS documentaleCryptDAO.creaDocumento(null, documentoView.getNomeFile(), FileNetClassNames.ALLEGATO_DOCUMENT, documentoView.getContentType(), null, cartellaPadre, contenuto);
						documentaleDdsCryptDAO.creaDocumento(null, documentoView.getNomeFile(), FileNetClassNames.ALLEGATO_DOCUMENT, documentoView.getContentType(), null, cartellaPadre.getFolderPath(), contenuto);
					}else{
						//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella( FileNetUtil.getProformaCartella(proformaView.getVo().getId(), fascicolo.getDataCreazione(),
						//@@DDS 		fascicolo.getNome(), proformaView.getNome() ));
						Folder cartellaPadre = documentaleDdsDAO.leggiCartella( FileNetUtil.getProformaCartella(proformaView.getVo().getId(), fascicolo.getDataCreazione(),
						 		fascicolo.getNome(), proformaView.getNome() ));
					    contenuto = new FileInputStream(documentoView.getFile());
						//@@DDS documentaleDAO.creaDocumento(null, documentoView.getNomeFile(), FileNetClassNames.ALLEGATO_DOCUMENT, documentoView.getContentType(), null, cartellaPadre, contenuto);
						documentaleDdsDAO.creaDocumento(null, documentoView.getNomeFile(), FileNetClassNames.ALLEGATO_DOCUMENT, documentoView.getContentType(), null, cartellaPadre.getFolderPath(), contenuto);
					}
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
	
	private void rimuoviAllegatiGenerici(ProformaView proformaView) throws Throwable {
		if( proformaView.getAllegatiDaRimuovereUuid() != null ){
			RIncaricoProformaSocieta incaricoProformaSocieta = proformaView.getVo().getRIncaricoProformaSocietas().iterator().next();
			Incarico incarico = incaricoProformaSocieta.getIncarico();
			Fascicolo fascicolo = fascicoloDAO.leggi(incarico.getFascicolo().getId());
			boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		
			Set<String> uuidSet = proformaView.getAllegatiDaRimuovereUuid();
			for( String uuid : uuidSet ){
				
				if( isPenale ){
					documentaleDdsCryptDAO.eliminaDocumento(uuid);
					//documentaleCryptDAO.eliminaDocumento(uuid);
				}else{
					//documentaleDAO.eliminaDocumento(uuid);
					documentaleDdsDAO.eliminaDocumento(uuid);
				}
			}
		}
			
	}
	
	
	private void salvaSchedaValutazioneFirmata(ProformaView proformaView) throws Throwable{

		if( proformaView.getSchedaValutazioneFirmataDoc() != null ){

			DocumentoView documentoView = proformaView.getSchedaValutazioneFirmataDoc();
			//if( documentoView.isNuovoDocumento() ){

				InputStream contenuto = null;
				try{
					RIncaricoProformaSocieta incaricoProformaSocieta = proformaView.getVo().getRIncaricoProformaSocietas().iterator().next();
					Incarico incarico = incaricoProformaSocieta.getIncarico();
					Fascicolo fascicolo = fascicoloDAO.leggi(incarico.getFascicolo().getId());
					boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
					
					String uuid = UUID.randomUUID().toString();
					Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT, documentoView.getNomeFile(), documentoView.getContentType());
					
					if( isPenale ){

						//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella( FileNetUtil.getProformaCartella(proformaView.getVo().getId(), fascicolo.getDataCreazione(),
						//@@DDS 		fascicolo.getNome(), proformaView.getNome() ));
						Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella( FileNetUtil.getProformaCartella(proformaView.getVo().getId(), fascicolo.getDataCreazione(),
								fascicolo.getNome(), proformaView.getNome() ));
							contenuto = new FileInputStream(documentoView.getFile());
						
						Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
						proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(proformaView.getVo().getId()+""));
						//@@DDS documentaleCryptDAO.creaDocumento(uuid, documento.getNomeFile(), FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT, documentoView.getContentType(), proprietaDocumento, cartellaPadre, contenuto);
						documentaleDdsCryptDAO.creaDocumento(uuid, documento.getNomeFile(), FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT, documentoView.getContentType(), proprietaDocumento, cartellaPadre.getFolderPath(), contenuto);
					}else{
						//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella( FileNetUtil.getProformaCartella(proformaView.getVo().getId(), fascicolo.getDataCreazione(),
						//@@DDS 		fascicolo.getNome(), proformaView.getNome() ));
						Folder cartellaPadre = documentaleDdsDAO.leggiCartella( FileNetUtil.getProformaCartella(proformaView.getVo().getId(), fascicolo.getDataCreazione(),
								fascicolo.getNome(), proformaView.getNome() ));
							contenuto = new FileInputStream(documentoView.getFile());
						
						Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
						proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(proformaView.getVo().getId()+""));

						//@@DDS documentaleDAO.creaDocumento(uuid, documento.getNomeFile(), FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT, documentoView.getContentType(), proprietaDocumento, cartellaPadre, contenuto);
						documentaleDdsDAO.creaDocumento(uuid, documento.getNomeFile(), FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT, documentoView.getContentType(), proprietaDocumento, cartellaPadre.getFolderPath(), contenuto);
					}
				}catch(Throwable e){
					e.printStackTrace(); 
					throw e;
				}finally {
					IOUtils.closeQuietly(contenuto);
				}

			//}
		}
	}
	
	private void rimuoviSchedaValutazioneFirmata(ProformaView proformaView) throws Throwable {

		RIncaricoProformaSocieta incaricoProformaSocieta = proformaView.getVo().getRIncaricoProformaSocietas().iterator().next();
		Incarico incarico = incaricoProformaSocieta.getIncarico();
		Fascicolo fascicolo = fascicoloDAO.leggi(incarico.getFascicolo().getId());
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		String nomeCartellaProforma = FileNetUtil.getProformaCartella(proformaView.getVo().getId(), fascicolo.getDataCreazione(),
				fascicolo.getNome(), proformaView.getVo().getNomeProforma());

		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		//@@DDS Folder cartellaProforma = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaProforma)
		//@@DDS 		: documentaleDAO.leggiCartella(nomeCartellaProforma);

		Folder cartellaProforma = isPenale ? documentaleDdsCryptDAO.leggiCartella(nomeCartellaProforma)
				: documentaleDdsDAO.leggiCartella(nomeCartellaProforma);

		//@@DDS DocumentSet documenti = cartellaProforma.get_ContainedDocuments();
		List<Document> documenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaProforma)
				: documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaProforma);

		logger.debug("@@DDS rimuoviSchedaValutazioneFirmata CARTELLA PROFORMA "+ cartellaProforma);
		/*@@DDS inizio commento
		 if (documenti != null) {
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT)){
						String uuid = documento.get_Id().toString();
						if( isPenale ){
							documentaleCryptDAO.eliminaDocumento(uuid);
						}else{
							documentaleDAO.eliminaDocumento(uuid);
						}
					}
				}
			}
		}*/
		if (documenti != null) {
				for (Document documento : documenti) {
					if( documento.getDocumentalClass().equals(FileNetClassNames.SCHEDA_VALUTAZIONE_DOCUMENT)){
						String uuid = documento.getId();
						if( isPenale ){
							documentaleDdsCryptDAO.eliminaDocumento(uuid);
						}else{
							documentaleDdsDAO.eliminaDocumento(uuid);
						}
					}
				}
		}
	}
	
	/**
	 * Metodo di lettura dei proforma associati
	 * <p>
	 * @param id: identificativo dell'incarico
	 * @return Lista oggetti proforma.
	 * @exception Throwable
	 */
	public List<ProformaView> leggiProformaAssociatiAIncarico(long id) throws Throwable{
		List<Proforma> lista = proformaDAO.leggiProformaAssociatiAIncarico(id);
		List<ProformaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	/**
	 * Metodo di lettura dei proforma associati
	 * <p>
	 * @param idIncarichi: lista degli id degli incarichi
	 * @return Lista oggetti proforma.
	 * @exception Throwable
	 */
	public List<ProformaView> leggiProformaAssociatiAIncarico(List<Long> idIncarichi) throws Throwable{
		List<Proforma> lista = proformaDAO.leggiProformaAssociatiAIncarico(idIncarichi);
		List<ProformaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(ProformaView proformaView) throws Throwable {
		proformaView.getVo().setOperation(AbstractEntity.DELETE_OPERATION);
		proformaView.getVo().setOperationTimestamp(new Date());
		proformaDAO.cancella(proformaView.getVo().getId());
	}

	@Override
	public RProformaFattura getRProformaFattura(long idProforma) throws Throwable {
		 
		return proformaDAO.getRProformaFattura(idProforma);
	}
	 @Override
	public ProformaView leggiConPermessi(long id) throws Throwable {
		 Proforma proforma= proformaDAO.leggiConPermessi(id);
		return (ProformaView) convertiVoInView(proforma);
	}
	
	@Override
	protected Class<Proforma> leggiClassVO() {
		return Proforma.class;
	}

	@Override
	protected Class<ProformaView> leggiClassView() {
		return ProformaView.class;
	}
	
	@Override
	public Integer checkStatusInviata(long id) throws Throwable {
		return proformaDAO.checkStatusInviata(id);
	}
	
	@Override
	public Integer checkFile(long id) throws Throwable {
		return proformaDAO.checkFile(id);
	}

	@Override
	public List<TipoProformaView> getListaTipoProforma(String lingua) throws Throwable {
		return proformaDAO.getListaTipoProforma(lingua);
	}

	@Override
	public TipoProforma leggiTipoProforma(Long idTipoProforma) throws Throwable {
		return proformaDAO.leggiTipoProforma(idTipoProforma);
	}


	
}
