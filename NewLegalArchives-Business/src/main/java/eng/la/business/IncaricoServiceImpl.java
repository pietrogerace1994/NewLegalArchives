package eng.la.business;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;

//@@DDS import com.filenet.api.collection.DocumentSet;
//@@DDS import com.filenet.api.core.Folder;
//import com.filenet.api.collection.DocumentSet;
import eng.la.persistence.*;
import it.snam.ned.libs.dds.dtos.v2.Document;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//@@DDS import com.filenet.api.collection.DocumentSet;
//import com.filenet.api.collection.PageIterator;
//@@DDS import com.filenet.api.core.Folder;
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;

import eng.la.model.AbstractEntity;
import eng.la.model.Documento;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
import eng.la.model.LetteraIncarico;
import eng.la.model.ListaRiferimento;
import eng.la.model.NotaPropIncarico;
import eng.la.model.Procura;
import eng.la.model.Procure;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.Societa;
import eng.la.model.VerificaAnticorruzione;
import eng.la.model.VerificaPartiCorrelate;
import eng.la.model.rest.CreaFascicoloIncaricoRest;
import eng.la.model.view.CollegioArbitraleView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.LetteraIncaricoView;
import eng.la.model.view.NotaPropIncaricoView;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("incaricoService")
public class IncaricoServiceImpl extends BaseService<Incarico,IncaricoView> implements IncaricoService {
	private static final Logger logger = Logger.getLogger(IncaricoServiceImpl.class);
	/*
	@Autowired
	private DocumentaleDAO documentaleDAO;

	public void setDocumentaleDao(DocumentaleDAO dao) {
		this.documentaleDAO = dao;
	}

	@Autowired
	private DocumentaleCryptDAO documentaleCryptDAO;

	public void setDocumentaleCryptDAO(DocumentaleCryptDAO dao) {
		this.documentaleCryptDAO = dao;
	}
*/
	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;

	public void setDocumentaleDdsDao(DocumentaleDdsDAO dao) {
		this.documentaleDdsDAO = dao;
	}

	@Autowired
	private DocumentaleDdsCryptDAO documentaleDdsCryptDAO;

	public void setDocumentaleDdsCryptDAO(DocumentaleDdsCryptDAO dao) {
		this.documentaleDdsCryptDAO = dao;
	}


	@Autowired
	private DocumentoDAO documentoDAO;
	
	
	public DocumentoDAO getDocumentoDAO() {
		return documentoDAO;
	}

	public void setDocumentoDAO(DocumentoDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	}

	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiDAO;

	public void setAnagraficaStatiDAO(AnagraficaStatiTipiDAO dao) {
		this.anagraficaStatiDAO = dao;
	}

	public AnagraficaStatiTipiDAO getAnagraficaStatiDAO() {
		return anagraficaStatiDAO;
	}
	
	@Autowired
	private FascicoloDAO fascicoloDAO;

	@Autowired
	private ProcureDAO procureDAO;
	
	@Autowired
	private IncaricoDAO incaricoDAO;

	public void setDao(IncaricoDAO dao) {
		this.incaricoDAO = dao;
	}

	public IncaricoDAO getDao() {
		return incaricoDAO;
	}

	@Autowired
	private LetteraIncaricoDAO letteraIncaricoDAO;

	public void setLetteraIncaricoDao(LetteraIncaricoDAO dao) {
		this.letteraIncaricoDAO = dao;
	}

	public LetteraIncaricoDAO getLetteraIncaricoDao() {
		return letteraIncaricoDAO;
	}

	@Autowired
	private NotaPropIncaricoDAO notaPropIncaricoDAO;

	public void setNotaPropIncaricoDao(NotaPropIncaricoDAO dao) {
		this.notaPropIncaricoDAO = dao;
	}

	public NotaPropIncaricoDAO getNotaPropIncaricoDao() {
		return notaPropIncaricoDAO;
	}

	@Override
	public List<IncaricoView> leggi() throws Throwable { 
		List<Incarico> lista = incaricoDAO.leggi();
		logger.info("@@DDS IncaricoServiceImpl ___________leggi");
		List<IncaricoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public List<IncaricoView> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner) throws Throwable { 
		List<Incarico> lista = incaricoDAO.leggiIncarichiAutorizzati(sortByFieldName,orderAscOrDesc, userIdOwner);
		List<IncaricoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public List<IncaricoView> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, List<String> userIdOwner) throws Throwable { 
		List<Incarico> lista = incaricoDAO.leggiIncarichiAutorizzati(sortByFieldName,orderAscOrDesc, userIdOwner);
		List<IncaricoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public List<IncaricoView> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner, String fascicoloId) throws Throwable { 
		List<Incarico> lista = incaricoDAO.leggiIncarichiAutorizzati(sortByFieldName,orderAscOrDesc, userIdOwner, fascicoloId);
		List<IncaricoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public Long contaIncarichiAutorizzati() throws Throwable { 
		Long conta = incaricoDAO.contaIncarichiAutorizzati();
		return conta;
	}
	
	@Override
	public List<Long> estraiListaFascicoli(Date begin, Date end) throws Throwable { 
		List<Long> listaIdFascicolo = incaricoDAO.estraiListaFascicoli(begin, end);
		return listaIdFascicolo;
	}
	
	
	
	@Override
	public Long contaIncarichiAutorizzati(Date begin, Date end) throws Throwable { 
		Long conta = incaricoDAO.contaIncarichiAutorizzati(begin, end);
		return conta;
	}
	
	
	@Override
	public Long contaIncarichiAutorizzati(Date begin, Date end, Long idProfEst) throws Throwable { 
		Long conta = incaricoDAO.contaIncarichiAutorizzati(begin, end, idProfEst);
		return conta;
	}

	@Override
	public IncaricoView leggi(long id) throws Throwable {
		Incarico incarico = incaricoDAO.leggi(id);
		return convertiVoInView(incarico);
	}
	
	 
	public IncaricoView leggiConPermessi(long id) throws Throwable {
		Incarico incarico = incaricoDAO.leggiConPermessi(id);
		return convertiVoInView(incarico);
	}
	
	
	@Override
	public IncaricoView leggiTutti(long id) throws Throwable {
		Incarico incarico = incaricoDAO.leggiTutti(id);
		return convertiVoInView(incarico);
	}

	@Override
	public ListaPaginata<IncaricoView> cerca(String nome, String commento, long professionistaEsternoId, String dal, String al, String statoIncaricoCode, String nomeFascicolo , int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<Incarico> lista = incaricoDAO.cerca(nome, commento,professionistaEsternoId, dal, al, statoIncaricoCode, nomeFascicolo, elementiPerPagina, numeroPagina,
				ordinamento, ordinamentoDirezione);
		List<IncaricoView> listaView = convertiVoInView(lista);
		ListaPaginata<IncaricoView> listaRitorno = new ListaPaginata<IncaricoView>();
		Long conta = incaricoDAO.conta(nome, commento,professionistaEsternoId, dal, al, statoIncaricoCode, nomeFascicolo);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	public ListaPaginata<CollegioArbitraleView> cercaArbitrale(String nome, String nominativoArbitroControparte, String indirizzoArbitroControparte, long professionistaEsternoId, String dal,
			String al, String statoIncaricoCode, String nomeFascicolo , int numElementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento) throws Throwable {
		List<Incarico> lista = incaricoDAO.cercaArbitrale(nome, nominativoArbitroControparte, indirizzoArbitroControparte,professionistaEsternoId, dal, al, statoIncaricoCode, nomeFascicolo, numElementiPerPagina, numeroPagina,
				ordinamento, tipoOrdinamento);
		List<CollegioArbitraleView> listaView = null;
		
		if( lista != null && lista.size() > 0){
			listaView = new ArrayList<CollegioArbitraleView>();
			for( Incarico vo : lista ){
				CollegioArbitraleView view = new CollegioArbitraleView();
				view.setVo(vo);					
				listaView.add(view);
			}
		}
		
		ListaPaginata<CollegioArbitraleView> listaRitorno = new ListaPaginata<CollegioArbitraleView>();
		Long conta = incaricoDAO.contaArbitrale(nome, professionistaEsternoId, dal, al, statoIncaricoCode, nomeFascicolo);
		if(listaView!=null)
			listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(numElementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(tipoOrdinamento);
		return listaRitorno;
	}
	
	@Override
	public List<CollegioArbitraleView> cercaArbitrale(List<String> parole) throws Throwable {
		List<Incarico> lista = incaricoDAO.cercaArbitrale(parole);
		
		List<CollegioArbitraleView> listaRitorno = null;
		if( lista != null && lista.size() > 0){
			listaRitorno = new ArrayList<CollegioArbitraleView>();
			for( Incarico vo : lista ){
				CollegioArbitraleView view = new CollegioArbitraleView();
				view.setVo(vo);					
				listaRitorno.add(view);
			}
		}
		
		return listaRitorno;
	}

	@Override
	public List<IncaricoView> cerca(String nome, long professionistaEsternoId) throws Throwable {
		List<Incarico> lista = incaricoDAO.cerca(nome, professionistaEsternoId);
		List<IncaricoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public List<IncaricoView> cerca(List<String> parole) throws Throwable {
		List<Incarico> lista = incaricoDAO.cerca(parole);
		List<IncaricoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public Long conta(String nome, long professionistaEsternoId, String dal, String al, String statoIncaricoCode, String nomeFascicolo  ) throws Throwable {
		return incaricoDAO.conta(nome, null,professionistaEsternoId, dal, al, statoIncaricoCode, nomeFascicolo);
	}
 
	@Override
	public Long contaArbitrale(String nome, long professionistaEsternoId, String dal, String al, String statoIncaricoCode, String nomeFascicolo ) throws Throwable {
		return incaricoDAO.contaArbitrale(nome, professionistaEsternoId, dal, al, statoIncaricoCode, nomeFascicolo); 
	}
	
	private void aggiungiProcura(Incarico incarico, InputStream contenutoProcura, String nomeFile, Documento documento ) throws Throwable {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl aggiungiProcura + ");
		//creo la procura su db e la associo all'incarico
		Procura procura = incaricoDAO.creaProcura(documento);
		 
		long idProcura = procura.getId();
		Fascicolo fascicolo = incarico.getFascicolo();
		incarico.setProcura(procura);
		
		String percorsoProcura =  FileNetUtil.getIncaricoCartella(incarico.getId(), fascicolo.getDataCreazione(), fascicolo.getNome(), incarico.getNomeIncarico());
		
		String titoloDocumento = nomeFile;
		
		if(nomeFile != null && !nomeFile.isEmpty()){
			
			if(!nomeFile.startsWith(CostantiFileNet.PREFIX_PROCURA_NAME)){
				
				titoloDocumento = CostantiFileNet.PREFIX_PROCURA_NAME + nomeFile;
			}
		}
		String uuid = documento.getUuid();
		String nomeClasseDocumento = FileNetClassNames.PROCURA_DOCUMENT;
		String mimeTypeDocumento = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(nomeFile);
		Map<String, Object> proprietaDocumento = new HashMap<String,Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID,  Integer.parseInt(idProcura+"") );
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
	
		if( isPenale ){
			//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(percorsoProcura);
			Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(percorsoProcura);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoProcura);
			}
			//@@DDS documentaleCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, contenutoProcura);
			documentaleDdsCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderPath(), contenutoProcura);
		}else{
			//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(percorsoProcura);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(percorsoProcura);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoProcura);
			}
			//@@DDS documentaleDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, contenutoProcura);
			documentaleDdsDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderName(), contenutoProcura);
		}
	}
  
	private void aggiungiLetteraFirmata(Incarico incarico, InputStream contenutoProcura, String nomeFile, Documento documento ) throws Throwable {
 
		Fascicolo fascicolo = incarico.getFascicolo();
		
		
		//incarico.getLetteraIncarico().setDocumento_firmato(documento);
		
		String percorsoLetteraFirmata =  FileNetUtil.getIncaricoCartella(incarico.getId(), fascicolo.getDataCreazione(), fascicolo.getNome(), incarico.getNomeIncarico());
		
		String titoloDocumento = nomeFile;
		
		if(nomeFile != null && !nomeFile.isEmpty()){
			
			if(!nomeFile.startsWith(CostantiFileNet.PREFIX_LETTERA_INCARICO)){
				
				titoloDocumento = CostantiFileNet.PREFIX_LETTERA_INCARICO + nomeFile;
			}
		}
		String uuid = documento.getUuid();
		String nomeClasseDocumento = FileNetClassNames.LETTERA_INCARICO_DOCUMENT;
		String mimeTypeDocumento = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(nomeFile);
		Map<String, Object> proprietaDocumento = new HashMap<String,Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID,  Integer.parseInt(incarico.getId()+"") );
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		logger.info("@@DDS ------------------------ IncaricoServiceImpl aggiungiLetteraFirmata + documento " + contenutoProcura);
		if( isPenale ){
			//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(percorsoLetteraFirmata);
			Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(percorsoLetteraFirmata);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoLetteraFirmata);
			}
			//@@DDS documentaleCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, contenutoProcura);
			documentaleDdsCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderPath(), contenutoProcura);
		}else{
			//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(percorsoLetteraFirmata);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(percorsoLetteraFirmata);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoLetteraFirmata);
			}
			//@@DDS documentaleDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, contenutoProcura);
			documentaleDdsDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderPath(), contenutoProcura);
		}
	}
	
	private void aggiungiNotaPropostaFirmata(Incarico incarico, InputStream is, String nomeFile, Documento documento) throws Throwable {
		Fascicolo fascicolo = incarico.getFascicolo();
		logger.info("@@DDS ------------------------ IncaricoServiceImpl aggiungiNotaPropostaFirmata + ");
		String percorsoNotaPropostaFirmata =  FileNetUtil.getIncaricoCartella(incarico.getId(), fascicolo.getDataCreazione(), fascicolo.getNome(), incarico.getNomeIncarico());
		
		String titoloDocumento = nomeFile;
		
		if(nomeFile != null && !nomeFile.isEmpty()){
			
			if(!nomeFile.startsWith(CostantiFileNet.PREFIX_NOTA_PROPOSTA)){
				
				titoloDocumento = CostantiFileNet.PREFIX_NOTA_PROPOSTA + nomeFile;
			}
		}
		String uuid = documento.getUuid();
		String nomeClasseDocumento = FileNetClassNames.NOTA_PROPOSTA_INCARICO_DOCUMENT;
		String mimeTypeDocumento = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(nomeFile);
		Map<String, Object> proprietaDocumento = new HashMap<String,Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID,  Integer.parseInt(incarico.getId()+"") );
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
	
		if( isPenale ){
			//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(percorsoNotaPropostaFirmata);
			Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(percorsoNotaPropostaFirmata);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoNotaPropostaFirmata);
			}
			//@@DDS documentaleCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, is);
			documentaleDdsCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderPath(), is);
		}else{
			//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(percorsoNotaPropostaFirmata);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(percorsoNotaPropostaFirmata);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoNotaPropostaFirmata);
			}
			//@@DDS documentaleDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, is);
			documentaleDdsDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderPath(), is);
		}
		
	}

  
	private void aggiungiVerificaAnticorruzione(Incarico incarico, InputStream contenutoVerificaAnticorruzione,
			String nomeFile, Documento documento ) throws Throwable {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl aggiungiVerificaAnticorruzione + ");
		//creo la verificaAnticorruzione su db e la associo all'incarico
		VerificaAnticorruzione verificaAnticorruzione = incaricoDAO.creaVerificaAnticorruzione(documento);
		incarico.setVerificaAnticorruzione(verificaAnticorruzione);

		long idVerificaAnticorruzione = verificaAnticorruzione.getId();
		Fascicolo fascicolo = incarico.getFascicolo(); 
		
		String percorsoVerificaAnticorruzione = FileNetUtil.getIncaricoCartella(incarico.getId(), fascicolo.getDataCreazione(), fascicolo.getNome(), incarico.getNomeIncarico());
		
		String titoloDocumento = nomeFile;
		
		if(nomeFile != null && !nomeFile.isEmpty()){
			
			if(!nomeFile.startsWith(CostantiFileNet.PREFIX_VERIFICA_ANTICORRUZIONE_NAME)){
				
				titoloDocumento = CostantiFileNet.PREFIX_VERIFICA_ANTICORRUZIONE_NAME + nomeFile;
			}
		}
		String uuid = documento.getUuid();
		String nomeClasseDocumento = FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT;
		String mimeTypeDocumento = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(nomeFile);
		Map<String, Object> proprietaDocumento = new HashMap<String,Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idVerificaAnticorruzione+""));
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		logger.info("@@DDS ------------------------ IncaricoServiceImpl aggiungiVerificaAnticorruzione + uuid " + uuid);
		if( isPenale ){
			//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(percorsoVerificaAnticorruzione);
			Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(percorsoVerificaAnticorruzione);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoVerificaAnticorruzione);
			}
			//@@DDS documentaleCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, contenutoVerificaAnticorruzione);
			documentaleDdsCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderPath(), contenutoVerificaAnticorruzione);
		}else{
			//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(percorsoVerificaAnticorruzione);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(percorsoVerificaAnticorruzione);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoVerificaAnticorruzione);
			}
			//@@DDS documentaleDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, contenutoVerificaAnticorruzione);
			documentaleDdsDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderPath(), contenutoVerificaAnticorruzione);
		}
	}
  
	private void aggiungiVerificaPartiCorrelate(Incarico incarico, InputStream contenutoVerificaPartiCorrelate,
			String nomeFile, Documento documento ) throws Throwable {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl aggiungiVerificaPartiCorrelate + ");
		//creo la verificaPartiCorrelate su db e la associo all'incarico
		VerificaPartiCorrelate verificaPartiCorrelate = incaricoDAO.creaVerificaPartiCorrelate(documento);
		incarico.setVerificaPartiCorrelate(verificaPartiCorrelate);
		long idVerificaPartiCorrelate = verificaPartiCorrelate.getId();
		Fascicolo fascicolo = incarico.getFascicolo(); 
		
		String percorsoVerificaPartiCorrelate = FileNetUtil.getIncaricoCartella(incarico.getId(), fascicolo.getDataCreazione(), fascicolo.getNome(), incarico.getNomeIncarico());
		
		String titoloDocumento = nomeFile;
		
		if(nomeFile != null && !nomeFile.isEmpty()){
			
			if(!nomeFile.startsWith(CostantiFileNet.PREFIX_VERIFICA_PARTICORRELATE_NAME)){
				
				titoloDocumento = CostantiFileNet.PREFIX_VERIFICA_PARTICORRELATE_NAME + nomeFile;
			}
		}
		String uuid = documento.getUuid();
		String nomeClasseDocumento = FileNetClassNames.VERIFICA_PARTI_CORRELATE_DOCUMENT;
		String mimeTypeDocumento = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(nomeFile);
		Map<String, Object> proprietaDocumento = new HashMap<String,Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idVerificaPartiCorrelate+""));
		
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		logger.debug("@@DDS path da utilizzare + percorsoVerificaPartiCorrelate" + percorsoVerificaPartiCorrelate);
		if( isPenale ){
			//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(percorsoVerificaPartiCorrelate);
			Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(percorsoVerificaPartiCorrelate);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoVerificaPartiCorrelate);
			}
			//@@DDS documentaleCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, contenutoVerificaPartiCorrelate);
			documentaleDdsCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderPath(), contenutoVerificaPartiCorrelate);
		}else{
			//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(percorsoVerificaPartiCorrelate);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(percorsoVerificaPartiCorrelate);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoVerificaPartiCorrelate);
			}
			//@@DDS documentaleDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, contenutoVerificaPartiCorrelate);
			documentaleDdsDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderPath(), contenutoVerificaPartiCorrelate);
		}
	}
	 
	private void aggiungiListeRiferimento(Incarico incarico, InputStream contenutoListeRiferimento, String nomeFile, Documento documento)
			throws Throwable {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl aggiungiListeRiferimento + ");
		//creo la verificaPartiCorrelate su db e la associo all'incarico
		ListaRiferimento listaRiferimento = incaricoDAO.creaListaRiferimento(documento);
		incarico.setListaRiferimento(listaRiferimento);
		long idListaRiferimento = listaRiferimento.getId();
		Fascicolo fascicolo = incarico.getFascicolo(); 
		
		String percorsoListaRiferimento = FileNetUtil.getIncaricoCartella(incarico.getId(), fascicolo.getDataCreazione(), fascicolo.getNome(), incarico.getNomeIncarico());
		
		String titoloDocumento = nomeFile;
		
		if(nomeFile != null && !nomeFile.isEmpty()){
			
			if(!nomeFile.startsWith(CostantiFileNet.PREFIX_LISTE_RIFERIMETO_NAME)){
				
				titoloDocumento = CostantiFileNet.PREFIX_LISTE_RIFERIMETO_NAME + nomeFile;
			}
		}
		String uuid = documento.getUuid();
		String nomeClasseDocumento = FileNetClassNames.LISTA_RIFERIMENTO_DOCUMENT;
		String mimeTypeDocumento = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(nomeFile);
		Map<String, Object> proprietaDocumento = new HashMap<String,Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idListaRiferimento+""));
		
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
	
		if( isPenale ){
			//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(percorsoListaRiferimento);
			Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(percorsoListaRiferimento);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoListaRiferimento);
			}
			//@@DDS documentaleCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, contenutoListeRiferimento);
			documentaleDdsCryptDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderPath(), contenutoListeRiferimento);
		}else{
			//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(percorsoListaRiferimento);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(percorsoListaRiferimento);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + percorsoListaRiferimento);
			}
			//@@DDS documentaleDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre, contenutoListeRiferimento);
			documentaleDdsDAO.creaDocumento(uuid, titoloDocumento, nomeClasseDocumento, mimeTypeDocumento, proprietaDocumento, cartellaPadre.getFolderPath(), contenutoListeRiferimento);
		}
		
	}
  

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public IncaricoView inserisci(IncaricoView incaricoView) throws Throwable {
		incaricoView.getVo().setOperation(AbstractEntity.INSERT_OPERATION);
		incaricoView.getVo().setOperationTimestamp(new Date());
		incaricoView.getVo().setStatoIncarico(anagraficaStatiDAO.leggiStatoIncarico(CostantiDAO.INCARICO_STATO_BOZZA, incaricoView.getLocale().getLanguage().toUpperCase()));
		incaricoView.getVo().setDataCreazione(new Date());
		incaricoView.getVo().setNomeIncarico( "Incarico - " 
															+ incaricoView.getVo().getProfessionistaEsterno().getCognome() + " " 
															+ incaricoView.getVo().getProfessionistaEsterno().getNome() 
															+ " - "+DateUtil.getDataDDMMYYYYtrattino(incaricoView.getVo().getOperationTimestamp().getTime()));
		Incarico incarico = incaricoDAO.inserisci(incaricoView.getVo());
		SessionFactory sessionFactory = (SessionFactory) SpringUtil.getBean("sessionFactory");
		sessionFactory.getCurrentSession().flush();
		
		IncaricoView view = new IncaricoView();
		view.setVo(incarico);
		 
		Fascicolo fascicolo = incaricoView.getVo().getFascicolo();
		
		String cartellaFascicolo = FileNetUtil.getFascicoloCartella(fascicolo.getDataCreazione(), fascicolo.getNome());
				
		String nomeCartella = cartellaFascicolo + incarico.getId()+"-"+incarico.getNomeIncarico().toUpperCase();
		String uuid = UUID.randomUUID().toString();
		String nomeClasseCartella = FileNetClassNames.INCARICO_FOLDER; 
		Map<String, Object> proprietaCartella = new HashMap<String,Object>();
		long idIncarico = incarico.getId();
		proprietaCartella.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idIncarico+""));
		
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		logger.info("@@DDS ------------------------ IncaricoServiceImpl inserisci + " + uuid);
		if( isPenale ){
			//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaFascicolo);
			Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(cartellaFascicolo);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + cartellaFascicolo);
			}

			//@@DDS documentaleCryptDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
			documentaleDdsCryptDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
		}else{
			//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaFascicolo);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaFascicolo);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + cartellaFascicolo);
			}

			//@@DDS documentaleDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
			documentaleDdsDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
		}
		
		return view;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public IncaricoView inserisciFascicoloEincarico(FascicoloView fascicoloView,IncaricoView incaricoView,CreaFascicoloIncaricoRest fi) throws Throwable {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl inserisciFascicoloEincarico + ");
		String numerofascicolo = fascicoloDAO.getNextNumeroFascicolo();
		fascicoloView.getVo().setNome(numerofascicolo);
		fascicoloView.getVo().setOperation(AbstractEntity.INSERT_OPERATION);
		fascicoloView.getVo().setOperationTimestamp(new Date());

		Fascicolo fascicolo = fascicoloDAO.apriFascicolo(fascicoloView.getVo());
		
		incaricoView.getVo().setFascicolo(fascicolo);
		

		
		
		incaricoView.getVo().setOperation(AbstractEntity.INSERT_OPERATION);
		incaricoView.getVo().setOperationTimestamp(new Date());
		Incarico incarico = incaricoDAO.inserisci(incaricoView.getVo());
		
		List<String> lsoc = fi.getSocieta();
		
		Set<RFascicoloSocieta> rfs = new HashSet<>();
		
		for (String idSoc : lsoc) {
			RFascicoloSocieta rfd = new RFascicoloSocieta();
			Societa soc = new Societa();
			soc.setId(new Long(idSoc));
			rfd.setSocieta(soc);
			rfd.setFascicolo(fascicolo);
			rfd.setTipologiaSocieta("A");
			rfs.add(rfd);
			fascicoloDAO.inserisciFascicoloSocieta(rfd);
		}
		
		List<String> lproc = fi.getProcure();
		
		
		for (String idproc : lproc) {
			Procure procure =procureDAO.leggi(new Long(idproc));
			procure.setFascicolo(fascicolo);
			procureDAO.modifica(procure);

		}
		
		SessionFactory sessionFactory = (SessionFactory) SpringUtil.getBean("sessionFactory");
		sessionFactory.getCurrentSession().flush();
		
		
		/** CREA CARTELLA FASCICOLO **/
		
		String cartellaPadreFascicolo = FileNetUtil.getFascicoloCartellaPadre(fascicolo.getDataCreazione());

		String nomeCartella = fascicolo.getNome().trim().toUpperCase();
		String uuid = UUID.randomUUID().toString();
		String nomeClasseCartella = FileNetClassNames.FASCICOLO_FOLDER;
		Map<String, Object> proprietaCartella = new HashMap<String, Object>();
		int idFascicolo = Integer.parseInt(fascicolo.getId() + "");
		proprietaCartella.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, idFascicolo);

		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua()
				.equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		//@@DDS Folder cartellaPadre = isPenale ? documentaleCryptDAO.leggiCartella(cartellaPadreFascicolo)
		//@@DDS		: documentaleDAO.leggiCartella(cartellaPadreFascicolo);
		Folder cartellaPadre = isPenale ? documentaleDdsCryptDAO.leggiCartella(cartellaPadreFascicolo)
				: documentaleDdsDAO.leggiCartella(cartellaPadreFascicolo);
		if (cartellaPadre == null) {
			if (isPenale) {
				/*@@DDS documentaleCryptDAO.verificaCreaPercorsoCartella(cartellaPadreFascicolo);
				cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaPadreFascicolo);
				 */
				documentaleDdsCryptDAO.verificaCreaPercorsoCartella(cartellaPadreFascicolo);
				cartellaPadre = documentaleDdsCryptDAO.leggiCartella(cartellaPadreFascicolo);
			} else {
				/* @@DDS documentaleDAO.verificaCreaPercorsoCartella(cartellaPadreFascicolo);
				cartellaPadre = documentaleDAO.leggiCartella(cartellaPadreFascicolo);
				 */
				documentaleDdsDAO.verificaCreaPercorsoCartella(cartellaPadreFascicolo);
				cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaPadreFascicolo);
			}
		}

		if (isPenale) {
			//@@DDS documentaleCryptDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
			documentaleDdsCryptDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
		} else {
			//@@DDS documentaleDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
			documentaleDdsDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
		}

		
		IncaricoView view = new IncaricoView();
		view.setVo(incarico);
		
		/** CREA CARTELLA INCARICO **/
		String cartellaFascicolo = FileNetUtil.getFascicoloCartella(fascicolo.getDataCreazione(), fascicolo.getNome());
		
		String nomeCartella2 = incarico.getId()+"-"+incarico.getNomeIncarico().toUpperCase();
		String uuid2 = UUID.randomUUID().toString();
		String nomeClasseCartella2 = FileNetClassNames.INCARICO_FOLDER; 
		Map<String, Object> proprietaCartella2 = new HashMap<String,Object>();
		long idIncarico = incarico.getId();
		proprietaCartella2.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idIncarico+""));
		
		if( isPenale ){
			//@@DDS Folder cartellaPadre2 = documentaleCryptDAO.leggiCartella(cartellaFascicolo);
			Folder cartellaPadre2 = documentaleDdsCryptDAO.leggiCartella(cartellaFascicolo);
			if( cartellaPadre2 == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + cartellaFascicolo);
			}
			
			//@@DDS documentaleCryptDAO.creaCartella(uuid2, nomeCartella2, nomeClasseCartella2, proprietaCartella2, cartellaPadre2);
			documentaleDdsCryptDAO.creaCartella(uuid2, nomeCartella2, nomeClasseCartella2, proprietaCartella2, cartellaPadre2);
		}else{
			//@@DDS Folder cartellaPadre2 = documentaleDAO.leggiCartella(cartellaFascicolo);
			Folder cartellaPadre2 = documentaleDdsDAO.leggiCartella(cartellaFascicolo);
			if( cartellaPadre2 == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + cartellaFascicolo);
			}

			//@@DDS documentaleDAO.creaCartella(uuid2, nomeCartella2, nomeClasseCartella2, proprietaCartella2, cartellaPadre2);
			documentaleDdsDAO.creaCartella(uuid2, nomeCartella2, nomeClasseCartella2, proprietaCartella2, cartellaPadre2);
		}
		
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(IncaricoView incaricoView) throws Throwable {	
		incaricoView.getVo().setOperation(AbstractEntity.UPDATE_OPERATION);
		incaricoView.getVo().setOperationTimestamp(new Date());	
		incaricoView.getVo().setDataModifica(new Date()); 
		
		salvaLetteraIncarico(incaricoView);
		
		salvaNotaPropostaIncarico(incaricoView);
		salvaListaRiferimento(incaricoView);
		salvaProcura(incaricoView);
		salvaVerificaAnticorruzione(incaricoView);
		salvaVerificaPartiCorrelate(incaricoView);
		incaricoDAO.modifica(incaricoView.getVo());
		rimuoviAllegatiGenerici(incaricoView);
		salvaAllegatiGenerici(incaricoView);
		
		rimuoviLetteraIncaricoFirmata(incaricoView);
		salvaLetteraIncaricoFirmata(incaricoView);
		
		rimuoviNotaPropostaFirmata(incaricoView);
		salvaNotaPropostaIncaricoFirmata(incaricoView);
	}
	
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modificaPerWS(IncaricoView incaricoView) throws Throwable {	
		incaricoView.getVo().setOperation(AbstractEntity.UPDATE_OPERATION);
		incaricoView.getVo().setOperationTimestamp(new Date());	
		incaricoView.getVo().setDataModifica(new Date()); 
		
		salvaLetteraIncarico(incaricoView);
		
		incaricoDAO.modifica(incaricoView.getVo());
	}
	
	private void rimuoviLetteraIncaricoFirmata(IncaricoView incaricoView) throws Throwable {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl rimuoviLetteraIncaricoFirmata + ");
		Incarico incarico = incaricoView.getVo();
		Fascicolo fascicolo = fascicoloDAO.leggi(incarico.getFascicolo().getId());
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		String nomeCartellaIncarico = FileNetUtil.getIncaricoCartella(incaricoView.getVo().getId(), fascicolo.getDataCreazione(),
				fascicolo.getNome(), incaricoView.getVo().getNomeIncarico());

		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaIncarico)
				: documentaleDAO.leggiCartella(nomeCartellaIncarico);
		*/
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleDdsCryptDAO.leggiCartella(nomeCartellaIncarico)
				: documentaleDdsDAO.leggiCartella(nomeCartellaIncarico);

		logger.debug("@@DDS rimuoviLetteraIncaricoFirmata____________ "+ cartellaIncarico);

		/*@@DDS - inizio commento

		DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();

		if (documenti != null) {
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.LETTERA_INCARICO_DOCUMENT)){
						String uuid = documento.get_Id().toString();
						if( isPenale ){
							documentaleCryptDAO.eliminaDocumento(uuid);
						}else{
							documentaleDAO.eliminaDocumento(uuid);
						}
					}
				}
			}
		}
		 */
		List<Document> documenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaIncarico)
				: documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaIncarico);
		if (documenti != null) {
				for (Document documento:documenti) {
					if( documento.getDocumentalClass().equals(FileNetClassNames.LETTERA_INCARICO_DOCUMENT)){
						String uuid = documento.getId().toString();
						if( isPenale ){
							documentaleDdsCryptDAO.eliminaDocumento(uuid);
						}else{
							documentaleDdsDAO.eliminaDocumento(uuid);
						}
					}
				}

		}
	}
	
	private void rimuoviNotaPropostaFirmata(IncaricoView incaricoView) throws Throwable {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl rimuoviNotaPropostaFirmata + ");
		Incarico incarico = incaricoView.getVo();
		Fascicolo fascicolo = fascicoloDAO.leggi(incarico.getFascicolo().getId());
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		String nomeCartellaIncarico = FileNetUtil.getIncaricoCartella(incaricoView.getVo().getId(), fascicolo.getDataCreazione(),
				fascicolo.getNome(), incaricoView.getVo().getNomeIncarico());

		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaIncarico)
				: documentaleDAO.leggiCartella(nomeCartellaIncarico);
		*/
		DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
		DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleDdsCryptDAO.leggiCartella(nomeCartellaIncarico)
				: documentaleDdsDAO.leggiCartella(nomeCartellaIncarico);

		//@@DDS - inizio commento
			/*
		DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();

		if (documenti != null) {
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					if( documento.get_ClassDescription().get_Name().equals(FileNetClassNames.NOTA_PROPOSTA_INCARICO_DOCUMENT)){
						String uuid = documento.get_Id().toString();
						if( isPenale ){
							documentaleCryptDAO.eliminaDocumento(uuid);
						}else{
							documentaleDAO.eliminaDocumento(uuid);
						}
					}
				}
			}
		}
		*/
		logger.debug("@@DDS rimuoviNotaPropostaFirmata "+ cartellaIncarico);
		List<Document> documenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaIncarico)
				: documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaIncarico);
		if (documenti != null) {
				for (Document documento : documenti) {
					if( documento.getDocumentalClass().equals(FileNetClassNames.NOTA_PROPOSTA_INCARICO_DOCUMENT)){
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



	
	private void salvaAllegatiGenerici(IncaricoView incaricoView) throws Throwable{
		logger.info("@@DDS ------------------------ IncaricoServiceImpl salvaAllegatiGenerici + ");
		if( incaricoView.getListaAllegatiGenerici() != null ){
			List<DocumentoView> allegatiGenerici = incaricoView.getListaAllegatiGenerici();
			for(DocumentoView documentoView : allegatiGenerici ){
				if( documentoView.isNuovoDocumento() ){
				    InputStream contenuto = null;
					try{
					boolean isPenale = incaricoView.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
				
					if( isPenale ){
						/*@@DDS
						Folder cartellaPadre = documentaleCryptDAO.leggiCartella( FileNetUtil.getIncaricoCartella(incaricoView.getVo().getId(), incaricoView.getFascicoloRiferimento().getVo().getDataCreazione(),
								   incaricoView.getFascicoloRiferimento().getVo().getNome(), incaricoView.getNomeIncarico()) );
						contenuto = new FileInputStream(documentoView.getFile());
						documentaleCryptDAO.creaDocumento(null, documentoView.getNomeFile(), FileNetClassNames.ALLEGATO_DOCUMENT,
						documentoView.getContentType(), null, cartellaPadre, contenuto);
						*/
						Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella( FileNetUtil.getIncaricoCartella(incaricoView.getVo().getId(), incaricoView.getFascicoloRiferimento().getVo().getDataCreazione(),
								incaricoView.getFascicoloRiferimento().getVo().getNome(), incaricoView.getNomeIncarico()) );
						contenuto = new FileInputStream(documentoView.getFile());
						documentaleDdsCryptDAO.creaDocumento(null, documentoView.getNomeFile(),
								FileNetClassNames.ALLEGATO_DOCUMENT, documentoView.getContentType(), null, cartellaPadre.getFolderPath(), contenuto);
					}else{
						/*@@DDS
					    Folder cartellaPadre = documentaleDAO.leggiCartella( FileNetUtil.getIncaricoCartella(incaricoView.getVo().getId(), incaricoView.getFascicoloRiferimento().getVo().getDataCreazione(),
								   incaricoView.getFascicoloRiferimento().getVo().getNome(), incaricoView.getNomeIncarico()) );
					    contenuto = new FileInputStream(documentoView.getFile());
				        documentaleDAO.creaDocumento(null, documentoView.getNomeFile(), FileNetClassNames.ALLEGATO_DOCUMENT, documentoView.getContentType(), null, cartellaPadre, contenuto);
				        */

						Folder cartellaPadre = documentaleDdsDAO.leggiCartella( FileNetUtil.getIncaricoCartella(incaricoView.getVo().getId(), incaricoView.getFascicoloRiferimento().getVo().getDataCreazione(),
								incaricoView.getFascicoloRiferimento().getVo().getNome(), incaricoView.getNomeIncarico()) );
						logger.info("@@DDS ------------------------ IncaricoServiceImpl cartella Padre + " + cartellaPadre);
						contenuto = new FileInputStream(documentoView.getFile());
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

	private void rimuoviAllegatiGenerici(IncaricoView incaricoView) throws Throwable {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl rimuoviAllegatiGenerici + ");
		if( incaricoView.getAllegatiDaRimuovereUuid() != null ){
			
			boolean isPenale = incaricoView.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		
			Set<String> uuidSet = incaricoView.getAllegatiDaRimuovereUuid();
			for( String uuid : uuidSet ){
				
				if( isPenale ){
					//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
					documentaleDdsCryptDAO.eliminaDocumento(uuid);
				}else{
					//@@DDS documentaleDAO.eliminaDocumento(uuid);
					documentaleDdsDAO.eliminaDocumento(uuid);
				}
			}
		}
			
	}

	private void salvaVerificaPartiCorrelate(IncaricoView incaricoView) throws Throwable {
		InputStream is = null;
		try{
			boolean isPenale = incaricoView.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		
			if( incaricoView.getVerificaPartiCorrelateDoc() != null ){
				if( incaricoView.getVerificaPartiCorrelateDoc().isNuovoDocumento() ){ 
					if ( incaricoView.getVerificaPartiCorrelate() != null ){				
						String uuid = incaricoView.getVerificaPartiCorrelate().getDocumento().getUuid();
						documentoDAO.cancellaDocumento(incaricoView.getVerificaPartiCorrelate().getDocumento());					
						incaricoDAO.cancellaVerificaPartiCorrelate(incaricoView.getVerificaPartiCorrelate());
						if( isPenale ){
							//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
							documentaleDdsCryptDAO.eliminaDocumento(uuid);
						}else{
							//@@DDS documentaleDAO.eliminaDocumento(uuid);
							documentaleDdsDAO.eliminaDocumento(uuid);
						} 
					}
					DocumentoView doc = incaricoView.getVerificaPartiCorrelateDoc();
					String uuid = UUID.randomUUID().toString();
					Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.VERIFICA_PARTI_CORRELATE_DOCUMENT, doc.getNomeFile(), doc.getContentType());				
					is = new FileInputStream(doc.getFile());
					aggiungiVerificaPartiCorrelate(incaricoView.getVo(), is, doc.getNomeFile(), documento);
				}
			}else{ 
				if ( incaricoView.getVerificaPartiCorrelate() != null ){				
					String uuid = incaricoView.getVerificaPartiCorrelate().getDocumento().getUuid();
					documentoDAO.cancellaDocumento(incaricoView.getVerificaPartiCorrelate().getDocumento());					
					incaricoDAO.cancellaVerificaPartiCorrelate(incaricoView.getVerificaPartiCorrelate());
					if( isPenale ){
						//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
						documentaleDdsCryptDAO.eliminaDocumento(uuid);
					}else{
						//@@DDS documentaleDAO.eliminaDocumento(uuid);
						documentaleDdsDAO.eliminaDocumento(uuid);
					}
				}
			}
		}finally{
			IOUtils.closeQuietly(is);
		}
	}

	private void salvaVerificaAnticorruzione(IncaricoView incaricoView)  throws Throwable {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl salvaVerificaAnticorruzione + ");
		InputStream is = null;
		try{
			boolean isPenale = incaricoView.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		
			if( incaricoView.getVerificaAnticorruzioneDoc() != null ){
				if( incaricoView.getVerificaAnticorruzioneDoc().isNuovoDocumento() ){ 
					if ( incaricoView.getVerificaAnticorruzione() != null ){				
						String uuid = incaricoView.getVerificaAnticorruzione().getDocumento().getUuid();
						documentoDAO.cancellaDocumento(incaricoView.getVerificaAnticorruzione().getDocumento());
						incaricoDAO.cancellaVerificaAnticorruzione(incaricoView.getVerificaAnticorruzione());
						if( isPenale ){
							//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
							documentaleDdsCryptDAO.eliminaDocumento(uuid);
						}else{
							//@@DDS documentaleDAO.eliminaDocumento(uuid);
							documentaleDdsDAO.eliminaDocumento(uuid);
						}
					}
					DocumentoView doc = incaricoView.getVerificaAnticorruzioneDoc();
					String uuid = UUID.randomUUID().toString();
					logger.debug("@@DDS - ________________ UUID + "+ uuid);
					uuid = uuid.toUpperCase(Locale.ROOT);
					Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.VERIFICA_ANTICORRUZIONE_DOCUMENT, doc.getNomeFile(), doc.getContentType());				
					is = new FileInputStream(doc.getFile());
					aggiungiVerificaAnticorruzione(incaricoView.getVo(), is, doc.getNomeFile(), documento);
				}
			}else{ 
				if ( incaricoView.getVerificaAnticorruzione() != null ){				
					String uuid = incaricoView.getVerificaAnticorruzione().getDocumento().getUuid();
					documentoDAO.cancellaDocumento(incaricoView.getVerificaAnticorruzione().getDocumento());
					incaricoDAO.cancellaVerificaAnticorruzione(incaricoView.getVerificaAnticorruzione());
					if( isPenale ){
						//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
						documentaleDdsCryptDAO.eliminaDocumento(uuid);
					}else{
						//@@DDS documentaleDAO.eliminaDocumento(uuid);
						documentaleDdsDAO.eliminaDocumento(uuid);
					}
				}
				
			}
		}finally{
			IOUtils.closeQuietly(is);
		}
	}

	private void salvaProcura(IncaricoView incaricoView) throws Throwable  {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl salvaProcura + ");
		InputStream is = null;
		try{
			boolean isPenale = incaricoView.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		
			if( incaricoView.getProcuraDoc() != null ){
				if( incaricoView.getProcuraDoc().isNuovoDocumento() ){
					if ( incaricoView.getProcura() != null ){				
						String uuid = incaricoView.getProcura().getDocumento().getUuid();
						documentoDAO.cancellaDocumento(incaricoView.getProcura().getDocumento());
						incaricoDAO.cancellaProcura(incaricoView.getProcura());			
						if( isPenale ){
							//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
							documentaleDdsCryptDAO.eliminaDocumento(uuid);
						}else{
							//@@DDS documentaleDAO.eliminaDocumento(uuid);
							documentaleDdsDAO.eliminaDocumento(uuid);
						}
					}
					DocumentoView doc = incaricoView.getProcuraDoc();
					String uuid = UUID.randomUUID().toString();
					Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.PROCURA_DOCUMENT, doc.getNomeFile(), doc.getContentType());				
					is = new FileInputStream(doc.getFile());
					aggiungiProcura(incaricoView.getVo(), is, doc.getNomeFile(), documento);
				}
			}else{ 
				if ( incaricoView.getProcura() != null ){				
					String uuid = incaricoView.getProcura().getDocumento().getUuid();
					documentoDAO.cancellaDocumento(incaricoView.getProcura().getDocumento());
					incaricoDAO.cancellaProcura(incaricoView.getProcura());			
					if( isPenale ){
						//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
						documentaleDdsCryptDAO.eliminaDocumento(uuid);
					}else{
						//@@DDS documentaleDAO.eliminaDocumento(uuid);
						documentaleDdsDAO.eliminaDocumento(uuid);
					}
				}
			}
		}finally{
			IOUtils.closeQuietly(is);
		}
	}

	private void salvaLetteraIncaricoFirmata(IncaricoView incaricoView) throws Throwable  {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl salvaLetteraIncaricoFirmata + ");
		InputStream is = null;
		try{
			boolean isPenale = incaricoView.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
			logger.info("@@DDS ------------------------ letterafirmata > " + incaricoView.getLetteraFirmataDoc());
			if( incaricoView.getLetteraFirmataDoc() != null ){
				//if( incaricoView.getLetteraFirmataDoc().isNuovoDocumento() ){
					if ( incaricoView.getLetteraFirmata() != null ){				
						String uuid = incaricoView.getLetteraFirmata().getUuid();
						documentoDAO.cancellaDocumento(incaricoView.getLetteraFirmata());
						//incaricoDAO.cancellaProcura(incaricoView.getProcura());			
						if( isPenale ){
							//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
							documentaleDdsCryptDAO.eliminaDocumento(uuid);
						}else{
							//@@DDS documentaleDAO.eliminaDocumento(uuid);
							documentaleDdsDAO.eliminaDocumento(uuid);
						}
					}
					DocumentoView doc = incaricoView.getLetteraFirmataDoc();
				logger.info("@@DDS ------------------------ doc > " + doc);
					String uuid = UUID.randomUUID().toString();
					Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.LETTERA_INCARICO_DOCUMENT, doc.getNomeFile(), doc.getContentType());				
					is = new FileInputStream(doc.getFile());
					aggiungiLetteraFirmata(incaricoView.getVo(), is, doc.getNomeFile(), documento);
				//}
			}
		}finally{
			IOUtils.closeQuietly(is);
		}
	}
	
	private void salvaNotaPropostaIncaricoFirmata(IncaricoView incaricoView) throws Throwable {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl salvaNotaPropostaIncaricoFirmata + ");
		InputStream is = null;
		try{
			boolean isPenale = incaricoView.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		
			if( incaricoView.getLetteraFirmataDocNota() != null ){
				//if( incaricoView.getLetteraFirmataDocNota().isNuovoDocumento() ){
					if ( incaricoView.getLetteraFirmataNota() != null ){				
						String uuid = incaricoView.getLetteraFirmataNota().getUuid();
						documentoDAO.cancellaDocumento(incaricoView.getLetteraFirmataNota());
						//incaricoDAO.cancellaProcura(incaricoView.getProcura());			
						if( isPenale ){
							//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
							documentaleDdsCryptDAO.eliminaDocumento(uuid);
						}else{
							//@@DDS documentaleDAO.eliminaDocumento(uuid);
							documentaleDdsDAO.eliminaDocumento(uuid);
						}
					}
					DocumentoView doc = incaricoView.getLetteraFirmataDocNota();
					String uuid = UUID.randomUUID().toString();
					Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.NOTA_PROPOSTA_INCARICO_DOCUMENT, doc.getNomeFile(), doc.getContentType());				
					is = new FileInputStream(doc.getFile());
					aggiungiNotaPropostaFirmata(incaricoView.getVo(), is, doc.getNomeFile(), documento);
				//}
			}
		}finally{
			IOUtils.closeQuietly(is);
		}
		
	}

	private void salvaListaRiferimento(IncaricoView incaricoView) throws Throwable {
		logger.info("@@DDS ------------------------ IncaricoServiceImpl salvaListaRiferimento + ");
		InputStream is = null;
		try{
			boolean isPenale = incaricoView.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		
			if( incaricoView.getListeRiferimentoDoc() != null ){
				if( incaricoView.getListeRiferimentoDoc().isNuovoDocumento() ){
					if ( incaricoView.getListeRiferimento() != null ){				
						String uuid = incaricoView.getListeRiferimento().getDocumento().getUuid();
						documentoDAO.cancellaDocumento(incaricoView.getListeRiferimento().getDocumento());
						incaricoDAO.cancellaListaRiferimento(incaricoView.getListeRiferimento());	
						if( isPenale ){
							//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
							documentaleDdsCryptDAO.eliminaDocumento(uuid);
						}else{
							//@@DDS documentaleDAO.eliminaDocumento(uuid);
							documentaleDdsDAO.eliminaDocumento(uuid);
						}
					}
					
					DocumentoView doc = incaricoView.getListeRiferimentoDoc();
					String uuid = UUID.randomUUID().toString();
					Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.LISTA_RIFERIMENTO_DOCUMENT, doc.getNomeFile(), doc.getContentType());				
					is = new FileInputStream(doc.getFile());
					aggiungiListeRiferimento(incaricoView.getVo(), is, doc.getNomeFile(), documento);
				}
			}else{ 
				if ( incaricoView.getListeRiferimento() != null ){				
					String uuid = incaricoView.getListeRiferimento().getDocumento().getUuid();
					documentoDAO.cancellaDocumento(incaricoView.getListeRiferimento().getDocumento());
					incaricoDAO.cancellaListaRiferimento(incaricoView.getListeRiferimento());	
					if( isPenale ){
						//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
						documentaleDdsCryptDAO.eliminaDocumento(uuid);
					}else{
						//@@DDS documentaleDAO.eliminaDocumento(uuid);
						documentaleDdsDAO.eliminaDocumento(uuid);
					}
				}
			}
		}finally{
			IOUtils.closeQuietly(is);
		}
	}

	private void salvaLetteraIncarico(IncaricoView incaricoView) throws Throwable { 
		LetteraIncarico letteraIncarico = incaricoView.getVo().getLetteraIncarico();
		if( letteraIncarico != null ){
			if( letteraIncarico.getId() == 0 ){
				letteraIncarico = incaricoDAO.inserisciLetteraIncarico(letteraIncarico);
				incaricoView.getVo().setLetteraIncarico(letteraIncarico);
			}else{
				incaricoDAO.aggiornaLetteraIncarico(letteraIncarico);
			}
		}
	}
	
	/**
	 * Genera un numero di protocollo valido da associare alla lettera di incarico
	 * Il numero di protocollo � generato come una strunga del tipo NNNN/AA/UU dove: 
	 * NNNN � l'ultimo numero +1 a 4 cifre generato nell ultimo anno per l'utente corrente
	 * AA � l'anno di creazione della lettera
	 * UU � la sigla dell'utente corrente
	 * @param sigla la sigla rappresentante l'utente corrente
	 * @param nominativo il nominativo dell'utente corrente
	 * @author MASSIMO CARUSO
	 * @return il numero di protocollo generato
	 * @throws Throwable in caso di errore
	 */
	@Override
	public String generaNumeroProtocolloLetteraIncarico(String sigla, String nominativo) throws Throwable{
		
		String to_return = "";
		
		int last = letteraIncaricoDAO.getUltimoNumeroProtocollo(nominativo) + 1;
		
		Date actual = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yy");
        int year = Integer.parseInt(df.format(actual));
		
        to_return = String.format("%04d", last)+"/" +year+"/"+sigla;
		
		return to_return;
	}

	private void salvaNotaPropostaIncarico(IncaricoView incaricoView) throws Throwable {		
		NotaPropIncarico notaPropIncarico = incaricoView.getVo().getNotaPropIncarico();
		if( notaPropIncarico != null ){
			if( notaPropIncarico.getId() == 0 ){ 
				notaPropIncarico = incaricoDAO.inserisciNotaPropostaIncarico(notaPropIncarico);
				incaricoView.getVo().setNotaPropIncarico(notaPropIncarico);
			}else{ 
				incaricoDAO.aggiornaNotaPropostaIncarico(notaPropIncarico);
			}
		}
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(IncaricoView incaricoView) throws Throwable {
		incaricoView.getVo().setOperation(AbstractEntity.DELETE_OPERATION);
		incaricoView.getVo().setOperationTimestamp(new Date());
		incaricoDAO.cancella(incaricoView.getVo().getId());
	} 

	@Override
	public CollegioArbitraleView inserisciCollegioArbitrale(CollegioArbitraleView view) throws Throwable {

		view.getVo().setOperation(AbstractEntity.INSERT_OPERATION);
		view.getVo().setOperationTimestamp(new Date());
		view.getVo().setStatoIncarico(anagraficaStatiDAO.leggiStatoIncarico(CostantiDAO.INCARICO_STATO_BOZZA, Locale.ITALIAN.getLanguage().toUpperCase()));
		view.getVo().setDataCreazione(new Date());
		view.getVo().setNomeCollegioArbitrale( view.getVo().getIncarico().getNomeIncarico() + " - (Arbitro di parte SANM) " + view.getVo().getProfessionistaEsterno().getCognome()
				+ " " + view.getVo().getProfessionistaEsterno().getNome() );
		
		Incarico vo = incaricoDAO.inserisciCollegioArbitrale(view.getVo());
		CollegioArbitraleView collegioArbitraleView = new CollegioArbitraleView();
		collegioArbitraleView.setVo(vo);
		return collegioArbitraleView;
	}

	@Override
	public CollegioArbitraleView leggiCollegioArbitrale(long id) throws Throwable {
		Incarico vo = incaricoDAO.leggiCollegioArbitrale(id);
		CollegioArbitraleView collegioArbitraleView = new CollegioArbitraleView();
		collegioArbitraleView.setVo(vo);
		return collegioArbitraleView;
	}

	@Override
	public void cancellaCollegioArbitrale(CollegioArbitraleView view) throws Throwable {
		view.getVo().setOperation(AbstractEntity.DELETE_OPERATION);
		view.getVo().setOperationTimestamp(new Date());
		incaricoDAO.cancellaCollegioArbitrale(view.getVo().getId());
	}

	@Override
	public void modificaCollegioArbitrale(CollegioArbitraleView view) throws Throwable {
		view.getVo().setOperation(AbstractEntity.UPDATE_OPERATION);
		view.getVo().setOperationTimestamp(new Date());
		incaricoDAO.modificaCollegioArbitrale(view.getVo());
	}
	
	/**
	 * Metodo di lettura dell'incarico associato
	 * <p>
	 * @param id: identificativo del proforma
	 * @return oggetto Incarico.
	 * @exception Throwable
	 */
	public IncaricoView leggiIncaricoAssociatoAProforma(long id) throws Throwable{
		Incarico incarico = incaricoDAO.leggiIncaricoAssociatoAProforma(id);
		return convertiVoInView(incarico);
	}
	
	@Override
	public List<LetteraIncaricoView> leggiLettereIncaricoPerId(List<Long> listaIdLettera) throws Throwable {
		List<LetteraIncarico> lista = letteraIncaricoDAO.leggiLettereIncaricoPerId(listaIdLettera);
		
		if( lista != null ){
			List<LetteraIncaricoView> listaRitorno = new ArrayList<LetteraIncaricoView>();
			for( LetteraIncarico vo : lista ){
				LetteraIncaricoView view = new LetteraIncaricoView();
				view.setVo(vo);
				listaRitorno.add(view);
			}
			return listaRitorno;
		}
		return null;
	}
	
	@Override
	public LetteraIncarico leggiLetteraIncarico(Long id) throws Throwable {
		LetteraIncarico lettera = letteraIncaricoDAO.leggiLetteraIncarico(id);
		return lettera;
	}

	@Override
	public void deleteAccontiBonus(LetteraIncarico letteraIncarico) throws Throwable {
		incaricoDAO.cancellaBonusAcconti(letteraIncarico);
		
	}

	@Override
	public List<IncaricoView> leggiIncarichiAutorizzati(Long idProfessionistaEsterno) throws Throwable { 
		List<Incarico> lista = incaricoDAO.leggiIncarichiAutorizzati(idProfessionistaEsterno);
		List<IncaricoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public void deleteBonusAcconti(Long letteraIncaricoId) throws Throwable {
		incaricoDAO.deleteBonus(letteraIncaricoId);
		incaricoDAO.deleteAcconti(letteraIncaricoId);
	}
	
	@Override
	public ListaPaginata<IncaricoView> leggiIncarichiAutorizzati(Date begin, Date end, String userIdOwner, boolean isGestoreVendor,
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable { 
		List<Incarico> lista = incaricoDAO.leggiIncarichiAutorizzati(begin, end, userIdOwner, isGestoreVendor,
				elementiPerPagina, numeroPagina, ordinamento, ordinamentoDirezione);
		
		List<IncaricoView> listaView = (List<IncaricoView>) convertiVoInView(lista);
		ListaPaginata<IncaricoView> listaRitorno = new ListaPaginata<IncaricoView>();
		Long conta = incaricoDAO.contaIncarichiAutorizzati(begin, end, userIdOwner, isGestoreVendor);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}
	
	@Override
	public IncaricoView rinviaVotazione(String idIncarico) throws Throwable { 
		Long id = Long.parseLong(idIncarico);
		Incarico incaricoConVotazioneRinviata = incaricoDAO.rinviaVotazione(id);
		IncaricoView incaricoView = convertiVoInView(incaricoConVotazioneRinviata);
		return incaricoView;
		
	}
	
	@Override
	public Long getEuroValuta() throws Throwable {
		return incaricoDAO.getEuroValuta();
	}

	@Override
	public Integer checkFile(long id) throws Throwable {
		return incaricoDAO.checkFile(id);
	}

	@Override
	public Integer checkStatusInviata(long id) throws Throwable {
		return incaricoDAO.checkStatusInviata(id);
	}
	
	@Override
	public Integer checkStatusInviataNot(long id) throws Throwable {
		return incaricoDAO.checkStatusInviataNot(id);
	}

	@Override
	public List<NotaPropIncaricoView> leggiNotaPropostaIncaricoPerId(List<Long> listaIdNotaProposta) throws Throwable {
		List<NotaPropIncarico> lista = notaPropIncaricoDAO.leggiNotaPropostaIncaricoPerId(listaIdNotaProposta);

		if( lista != null ){
			List<NotaPropIncaricoView> listaRitorno = new ArrayList<NotaPropIncaricoView>();
			for( NotaPropIncarico vo : lista ){
				NotaPropIncaricoView view = new NotaPropIncaricoView();
				view.setVo(vo);
				listaRitorno.add(view);
			}
			return listaRitorno;
		}
		return null;
	}

	@Override
	public NotaPropIncarico leggiNotaPropostaIncarico(Long id) throws Throwable {
		NotaPropIncarico notaProposta = notaPropIncaricoDAO.leggiNotaPropostaIncarico(id);
		return notaProposta;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public String creaFascicoloEIncarico(FascicoloView fascicoloView, IncaricoView incaricoView) throws Throwable {
		Fascicolo f = fascicoloDAO.apriFascicolo(fascicoloView.getVo());
		incaricoView.getVo().getFascicolo().setId(f.getId());
		incaricoDAO.inserisci(incaricoView.getVo());
		return "ok";
	}

	public FascicoloDAO getFascicoloDAO() {
		return fascicoloDAO;
	}

	public void setFascicoloDAO(FascicoloDAO fascicoloDAO) {
		this.fascicoloDAO = fascicoloDAO;
	}

	@Override
	public Integer checkFileNot(long id) throws Throwable {
		return incaricoDAO.checkFileNot(id);
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public IncaricoView aggiornaIncaricoConListaRiferimento(IncaricoView incaricoView, Documento documento) throws Throwable {	
		
		ListaRiferimento listaRiferimento = incaricoDAO.creaListaRiferimento(documento);
		incaricoView.getVo().setListaRiferimento(listaRiferimento);

		incaricoDAO.modifica(incaricoView.getVo());
		
		return incaricoView;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public IncaricoView aggiornaIncaricoConPartiCorrelate(IncaricoView incaricoView, Documento documento) throws Throwable {	
		
		VerificaPartiCorrelate verificaPartiCorrelate = incaricoDAO.creaVerificaPartiCorrelate(documento);
		incaricoView.getVo().setVerificaPartiCorrelate(verificaPartiCorrelate);

		incaricoDAO.modifica(incaricoView.getVo());
		
		return incaricoView;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public IncaricoView aggiornaIncaricoConVerificaAnticorruzione(IncaricoView incaricoView, Documento documento) throws Throwable {	
		
		VerificaAnticorruzione verificaAnticorruzione = incaricoDAO.creaVerificaAnticorruzione(documento);
		incaricoView.getVo().setVerificaAnticorruzione(verificaAnticorruzione);

		incaricoDAO.modifica(incaricoView.getVo());
		
		return incaricoView;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public IncaricoView aggiornaIncaricoConProcura(IncaricoView incaricoView, Documento documento) throws Throwable {	
		
		Procura procura = incaricoDAO.creaProcura(documento);
		incaricoView.getVo().setProcura(procura);
		
		incaricoDAO.modifica(incaricoView.getVo());
		
		return incaricoView;
	}
	
	@Override
	protected Class<Incarico> leggiClassVO() {
		return Incarico.class;
	}

	@Override
	protected Class<IncaricoView> leggiClassView() {
		return IncaricoView.class;
	}
	
}
