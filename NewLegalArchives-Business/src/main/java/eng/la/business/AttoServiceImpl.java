package eng.la.business;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

//@@DDS import com.filenet.api.core.Document;
//@@DDS import com.filenet.api.core.Folder;
import eng.la.persistence.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//import com.filenet.api.constants.RefreshMode;
//@@DDS import com.filenet.api.core.Document;
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;//@@DDS
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;//@@DDS

import eng.la.model.AbstractEntity;
import eng.la.model.Atto;
import eng.la.model.CategoriaAtto;
import eng.la.model.Documento;
import eng.la.model.DocumentoProtCorrisp;
import eng.la.model.EsitoAtto;
import eng.la.model.Fascicolo;
import eng.la.model.Societa;
import eng.la.model.StatoAtto;
import eng.la.model.Utente;
import eng.la.model.custom.AllegatoMultipartFile;
import eng.la.model.mail.JSONMail;
import eng.la.model.view.AttoView;
import eng.la.util.DateUtil;
import eng.la.util.WriteExcell;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

/**
 * <h1>Classe di business AttoService </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * 
 * @author 
 * @version 1.0
 * @since 2016-06-16
 */
@Service("attoService")
public class AttoServiceImpl extends BaseService<Atto, AttoView> implements AttoService {
	private static final Logger logger = Logger.getLogger(AttoServiceImpl.class);
	@Autowired
	private AttoDAO attoDao;
	
	@Autowired
	private SocietaDAO societaDAO;
	
	@Autowired
	private FascicoloDAO fascicoloDAO;
	
	@Autowired
	private UtenteDAO utenteDAO;
	
	@Autowired
	private DocumentaleCryptDAO documentaleCryptDAO;
	@Autowired
	private DocumentaleDdsCryptDAO documentaleDdsCryptDAO;
	
	
	@Autowired
	private DocumentoDAO documentoDAO;
	
	/**
	 * Metodo di set della istanza DAO passata come argomento, al corrispondente
	 * membro di classe.
	 * 
	 * @param attoDao oggetto della classe AttoDAO
	 * @see AttoDAO
	 */
	public void setAttoDao(AttoDAO attoDao) {
		this.attoDao = attoDao;
	}
	
	
	public void setSocietaDAO(SocietaDAO societaDAO) {
		this.societaDAO = societaDAO;
	}
	
	public void setFascicoloDAO(FascicoloDAO fascicoloDAO) {
		this.fascicoloDAO = fascicoloDAO;
	}
	
	public void setUtenteDAO(UtenteDAO utenteDAO) {
		this.utenteDAO = utenteDAO;
	}
	
	public void setDocumentaleCryptDAO(DocumentaleCryptDAO documentaleCryptDAO) {
		this.documentaleCryptDAO = documentaleCryptDAO;
	}

	public void setDocumentaleDdsCryptDAO(DocumentaleDdsCryptDAO documentaleDdsCryptDAO) {
		this.documentaleDdsCryptDAO = documentaleDdsCryptDAO;
	}
	/**
	 * Elenca tutte le fatture presenti in base dati
	 * @return lista fatture
	 * @throws Throwable
	 */
	@Override
	public List<AttoView> leggi() throws Throwable {
		List<Atto> lista = attoDao.leggi();
		List<AttoView> listaRitorno = convertiVoInView(lista);		
		return listaRitorno;
	}
	
	@Override
	public List<AttoView> cerca(AttoView view) throws Throwable {
		return null;
	}
	
	@Override
	public List<AttoView> cerca(List<String> parole) throws Throwable {
		List<Atto> lista = attoDao.cerca(parole);
		List<AttoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	// metodi di scrittura
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		AttoView attoView = leggi(id);
		attoView.getVo().setOperation(AbstractEntity.DELETE_OPERATION);
		attoView.getVo().setOperationTimestamp(new Date());
		attoDao.cancella(id);
	}

	
	
	/**
	 * Lettura atto per l'id.
	 * <p>
	 * @param id
	 * @throws Throwable
	 */
	@Override
	public AttoView leggi(long id) throws Throwable {
		Atto atto = attoDao.leggi(id);
		return (AttoView) convertiVoInView(atto);
	}
	
	/**
	 * Esegue inserimento di una atto in base dati.
	 * <p>
	 * @param attoView
	 * @return attoView ritorna l'occorenza inserita.
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public AttoView inserisci(AttoView attoView) throws Throwable {
		attoView.getVo().setOperation(AbstractEntity.INSERT_OPERATION);
		attoView.getVo().setOperationTimestamp(new Date());
		Atto atto = attoDao.inserisci(attoView.getVo());
		AttoView view = new AttoView();
		view.setVo(atto);
		return view;
	}

	/**
	 * Esegue la modifca di una determinata occorrenza.
	 * <p>
	 * @param attoView
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void modifica(AttoView attoView) throws Throwable {
		attoView.getVo().setOperation(AbstractEntity.UPDATE_OPERATION);
		attoView.getVo().setOperationTimestamp(new Date());
		attoDao.modifica(attoView.getVo());
	}
	
	// metodi extra
	
	// 2016-07-01:
	// Questo metodo forse giusto che sia sollo nel servizio fascicolo e non qui
	/**
	 * Esegue la creazione di una determinata occorrenza.
	 * <p>
	 * @param eventoView
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void creaFascicolo(AttoView eventoView) throws Throwable {
		// TO-DO
	}

	/**
	 * Esegue lo start del WF di una determinata occorrenza.
	 * <p>
	 * @param
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void startWf() throws Throwable {
		// TO-DO
	}
	
	@Override
	protected Class<Atto> leggiClassVO() { 
		return Atto.class;
	}

	@Override
	protected Class<AttoView> leggiClassView() { 
		return AttoView.class;
	}
/*
	@Override
	public List<AttoCustom> getListaAtti() throws Throwable {
		List<AttoCustom> attos=(List<AttoCustom>)attoDao.listaAtti();
		return attos;
	}
	*/
	
	@Override
	public Atto getAtto(long id) throws Throwable {
		Atto atto=(Atto)attoDao.leggi(id);
		return atto;
	}
	
	public Atto getAttoConPemessi(long id) throws Throwable {
		Atto atto=(Atto)attoDao.leggiConPermessi(id);
		return atto;
	}
	
	@Override
	public List<Atto> getListaAtti() throws Throwable {
		List<Atto> attos=(List<Atto>)attoDao.leggi();
		return attos;
	}
	
	public List<Societa> getlistSocieta() throws Throwable{
		return (List<Societa>)societaDAO.leggi(false);
	}
	
	public List<Societa> getlistSocieta(boolean tutte) throws Throwable{
		return (List<Societa>)societaDAO.leggi(tutte);
	}
	
	public List<CategoriaAtto> listaCategoriaAtto(String lingua) throws Throwable{
		List<CategoriaAtto> categorie=(List<CategoriaAtto>)attoDao.listaCategoriaAtto(lingua);
		return categorie;
		
	}
	
	@Override
	public List<EsitoAtto> listaEsitoAtto(String lingua) throws Throwable{
		List<EsitoAtto> categorie=(List<EsitoAtto>)attoDao.listaEsitoAtto(lingua);
		return categorie;
		
	}
	
	@Override
	public CategoriaAtto getCategoria(long id) throws Throwable {
		CategoriaAtto categoriaAtto=(CategoriaAtto)attoDao.getCategoria(id);
		return categoriaAtto;
	}
	
	@Override
	public EsitoAtto getEsito(long id) throws Throwable {
		EsitoAtto esitoAtto=(EsitoAtto)attoDao.getEsito(id);
		return esitoAtto;
	}
	
	@Override
	public EsitoAtto getEsitoByCode(String code, String lingua) throws Throwable {
		EsitoAtto esitoAtto=(EsitoAtto)attoDao.getEsitoByCode(code, lingua);
		return esitoAtto;
	}
	
	@Override
	public CategoriaAtto getCategoriaPreLingua(String lang, String cod_gruppo_lingua) throws Throwable {
		CategoriaAtto categoriaAtto=(CategoriaAtto)attoDao.getCategoriaPreLingua(lang, cod_gruppo_lingua);
		return categoriaAtto;
	}
	
	@Override
		public Fascicolo getFascicolo(long id) throws Throwable {
			Fascicolo fascicolo =(Fascicolo)fascicoloDAO.leggi(id);
			return fascicolo;
		}	
	
	@Override
	public Societa getSocieta(long id) throws Throwable {
		Societa societa =(Societa)societaDAO.leggi(id);
		return societa;
	}
	
	@Override 
	public StatoAtto getStatoAtto(long id) throws Throwable {
		StatoAtto statoAtto=(StatoAtto)attoDao.getStatoAtto(id);
		return statoAtto;
	}
	
	@Override 
	public List<Utente> getGcDestinatario() throws Throwable{
		
		List<Utente> utentes=(List<Utente>) utenteDAO.getUtentiGC();
		
		return utentes;
		
	}
	
	@Override 
	public Utente leggiUtenteDaUserId(String userId) throws Throwable {
		return utenteDAO.leggiUtenteDaUserId(userId);
	}
	
	public Atto getAttoPerNumeroProtocollo()  throws Throwable{
		return attoDao.getAttoPerNumeroProtocollo();
	}
	
	@Override
	public Documento aggiungiDocumento(Date dataCreazione,Long idAtto, MultipartFile file,String nomeFolder) throws Throwable {

		String uuid =""+UUID.randomUUID()+"";  
		String cartellaPadreAtto= FileNetUtil.getAttoCartella(dataCreazione, nomeFolder); 
		Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(idAtto+""));
		logger.info("@@DDS ------------------------ AttoServiceImpl aggiungiDocumento cartellaPadreAtto + " + cartellaPadreAtto);
		//@@DDS documentaleCryptDAO.verificaCreaPercorsoCartella(cartellaPadreAtto);
		documentaleDdsCryptDAO.verificaCreaPercorsoCartella(cartellaPadreAtto);
		//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaPadreAtto);
		Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(cartellaPadreAtto);

		byte[] contenuto = file.getBytes();
		//@@DDS documentaleCryptDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.ATTO_DOCUMENT, file.getContentType(), proprietaDocumento, cartellaPadre, contenuto);
		documentaleDdsCryptDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.ATTO_DOCUMENT, file.getContentType(), proprietaDocumento, cartellaPadreAtto, contenuto);
    
		//inserisce la ciave in tabella documento
		Documento doc=documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.ATTO_DOCUMENT, file.getOriginalFilename(), file.getContentType());
		
		return doc;
	}
	
	@Override
	public Documento aggiungiDocumentoDaUuid(Date dataCreazione,Long idAtto, String uuid,String nomeFolder) throws Throwable {
		
		//@@DDS Document documento = documentaleCryptDAO.leggiDocumentoUUID(uuid);
		Document documento = documentaleDdsCryptDAO.leggiDocumentoUUID(uuid);
		//@@DDS String name = documento.get_Name();
		//@@DDS String contentType = documento.get_MimeType();
		String name = documento.getContents().get(0).getContentsName();
		String contentType = documento.getContents().get(0).getContentsMimeType();
		//@@DDS byte[] contenuto = documentaleCryptDAO.leggiContenutoDocumento(documento);
		byte[] contenuto = documentaleDdsCryptDAO.leggiContenutoDocumento(uuid);;
		//@@DDS String pathFolder = FileNetUtil.getPathFolder(documento);
		String pathFolder = documento.getFoldersParents().get(0);
		
		//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
		documentaleDdsCryptDAO.eliminaDocumento(uuid);

		//bisogna eliminare anche la cartella contenitore
		//@@DDS Folder leggiCartella = documentaleCryptDAO.leggiCartella(pathFolder);
		Folder leggiCartella = documentaleDdsCryptDAO.leggiCartella(pathFolder);
		if (leggiCartella != null) {
			//@@DDS leggiCartella.delete();
			//@@DDS leggiCartella.save(RefreshMode.REFRESH);
			documentaleDdsCryptDAO.eliminaCartella(pathFolder);
		}
		
		String cartellaPadreAtto= FileNetUtil.getAttoCartella(dataCreazione, nomeFolder); 
		Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(idAtto+""));

		documentaleDdsCryptDAO.verificaCreaPercorsoCartella(cartellaPadreAtto);
		//documentaleCryptDAO.verificaCreaPercorsoCartella(cartellaPadreAtto);
		
		//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaPadreAtto);
		Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(cartellaPadreAtto);

		//@@DDS documentaleCryptDAO.creaDocumento(uuid, name, FileNetClassNames.ATTO_DOCUMENT, contentType, proprietaDocumento, cartellaPadre, contenuto);
		documentaleDdsCryptDAO.creaDocumento(uuid, name, FileNetClassNames.ATTO_DOCUMENT, contentType, proprietaDocumento, cartellaPadre.getFolderPath(), contenuto);
    
		//inserisce la ciave in tabella documento
		Documento doc=documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.ATTO_DOCUMENT, name, contentType);
		
		return doc;
	}
	
	@Override
	public Document leggiDocumentoUUID(String uuid) throws Throwable {
		//DDS Document document= documentaleCryptDAO.leggiDocumentoUUID(uuid);
		Document document= documentaleDdsCryptDAO.leggiDocumentoUUID(uuid);
	return document;
	}
	
	public byte[] leggiContenutoDocumentoUUID(String uuid) throws Throwable {
		//@@DDS byte[] docuByte= documentaleCryptDAO.leggiContenutoDocumento(uuid);
		byte[] docuByte= documentaleDdsCryptDAO.leggiContenutoDocumento(uuid);
		
	return docuByte;
	}
	
	/**
	 * Aggiunta del flag per gestire gli atti da validare
	 * @author MASSIMO CARUSO
	 */
	@Override
	public List<Atto> getCercaAtti(String dal, String al, String numeroProtocollo, long idCategoriaAtto,
			long idSocieta, String tipoAtto,int elementiPerPagina, int numeroPagina,String order, boolean flagAltriUffici, boolean flagValida) throws Throwable {

		return attoDao.getCercaAtti(dal, al, numeroProtocollo, idCategoriaAtto, idSocieta, tipoAtto, elementiPerPagina, numeroPagina, order, flagAltriUffici, flagValida);
	}
	
	/**
	 * Aggiunta del flag per gestire gli atti da validare
	 * @author MASSIMO CARUSO
	 */
	@Override
	public Long countAtti(String dal, String al, String numeroProtocollo, long idCategoriaAtto, long idSocieta,
			String tipoAtto,  boolean flagAltriUffici, boolean flagValida) throws Throwable {
		return attoDao.contaAllSerch(dal, al, numeroProtocollo, idCategoriaAtto, idSocieta, tipoAtto, flagAltriUffici,flagValida);
	}
	
	@Override
	public void exportExcell(List<Atto> atti, HttpServletResponse respons) throws IOException {
		WriteExcell excell= new WriteExcell();
	
		excell.addHeader("Numero Atto", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Categoria Atto", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Data Notifica", WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader("Societa'/Divisione Interessata", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Foro Competente", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Destinatario", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Tipo Atto Giudiziario", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Note", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Creato Da", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Data Creazione", WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader("Data Ultima Modifica", WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader("Data Udienza", WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader("Stato",WriteExcell.TYPECELL_STRING);
		excell.addHeader("Rilevanza",WriteExcell.TYPECELL_STRING);
		excell.addHeader("Utente Inv altri Uff",WriteExcell.TYPECELL_STRING);
		excell.addHeader("Ul Inv Altri Uff",WriteExcell.TYPECELL_STRING);
		excell.addHeader("Email Inv Altri Uff",WriteExcell.TYPECELL_STRING);
		
	
	
		for(Atto a:atti){
			Vector<Object> row = new Vector<Object>();
			row.add(a.getNumeroProtocollo()!=null?a.getNumeroProtocollo():new String(""));
			if(a.getCategoriaAtto()!=null){
				row.add(a.getCategoriaAtto().getNome()!=null?a.getCategoriaAtto().getNome():new String(""));	
			}else{
				row.add(new String(""));
			
			}
			row.add(a.getDataNotifica()!=null?a.getDataNotifica():null);
		
			if(a.getSocieta()!=null){
				row.add(a.getSocieta().getRagioneSociale()!=null?a.getSocieta().getRagioneSociale():new String(""));
			}else{
				row.add(new String(""));
			}
			row.add(a.getForoCompetente()!=null?a.getForoCompetente():new String(""));
			row.add(a.getDestinatario()!=null?a.getDestinatario():new String(""));
			row.add(a.getTipoAtto()!=null?a.getTipoAtto():new String(""));
			row.add(a.getNote()!=null?a.getNote():new String(""));
		
			if(a.getCreatoDa()!=null && !a.getCreatoDa().trim().equals("")){
				try {
					String utenteName=((Utente)utenteDAO.leggiUtenteDaUserId(a.getCreatoDa())).getNominativoUtil();
					row.add(utenteName!=null?utenteName:new String(""));
				} catch (Throwable e) {
					row.add(new String(""));
			 
				}
			}else{row.add(new String(""));}

			row.add(a.getDataCreazione()!=null?a.getDataCreazione():null);
			row.add(a.getDataUltimaModifica()!=null?a.getDataUltimaModifica():null);
			row.add(a.getDataUdienza()!=null?a.getDataUdienza():null);
			if(a.getStatoAtto()!=null){
				row.add(a.getStatoAtto().getDescrizione()!=null?a.getStatoAtto().getDescrizione():new String(""));	
			}else{
				row.add(new String(""));
			
			}
		 
			row.add(a.getRilevante()!=null?a.getRilevante():new String(""));
			row.add(a.getUtenteInvioAltriUffici()!=null?a.getUtenteInvioAltriUffici():new String(""));
			row.add(a.getUnitaLegInvioAltriUffici()!=null?a.getUnitaLegInvioAltriUffici():new String(""));
			row.add(a.getEmailInvioAltriUffici()!=null?a.getEmailInvioAltriUffici():new String(""));
			excell.addRowBody(row);
		
		}
		excell.setNomeFile("Ricerca-Atti.xls");
 	
		excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 45);
 	
		excell.write(respons);
	}
	
	@Override
	public StatoAtto getStatoAttoPerLingua(String lang, String cod_gruppo_lingua) throws Throwable {
		 
		return attoDao.getStatoAttoPerLingua(lang, cod_gruppo_lingua);
	}
	
	@Override
	public List<Fascicolo> cercaFascicolo(String nome,int elementiPerPagina,int numeroPagina) throws Throwable {
		return fascicoloDAO.cercaAllFascicoli(nome, elementiPerPagina, numeroPagina, "nome", "ASC");
		 
	}
	
	public Long countFascicolo(String nome) throws Throwable {
		return fascicoloDAO.contaAllSerch(nome);
		 
	}


	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void aggiungiDocumento(Long attoId, Long categoriaId, List<MultipartFile> files) throws Throwable {
		AttoView attoView = leggi(attoId);
		if (attoView == null || attoView.getVo() == null) {
			throw new RuntimeException("Atto con id:" + attoId + " non trovato");
		}
		
		Hashtable<String, Boolean> documentiInseritiFileNet = new Hashtable<String, Boolean>();

		try
		{
			for(MultipartFile file : files)
			{
				String uuid = UUID.randomUUID().toString();
				String cartellaPadreAtto = FileNetUtil.getAttoCartella(attoView.getVo().getDataCreazione(),
						attoView.getVo().getNumeroProtocollo());

				//@@DDS documentaleCryptDAO.verificaCreaPercorsoCartella(cartellaPadreAtto);
				documentaleDdsCryptDAO.verificaCreaPercorsoCartella(cartellaPadreAtto);
				//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaPadreAtto);
				Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(cartellaPadreAtto);
				
				String nomeClasseDocumentale = "";

				Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
				if (file instanceof AllegatoMultipartFile) {
					nomeClasseDocumentale = FileNetClassNames.EMAIL_DOCUMENT;
					documentoDAO.creaDocumentoDB(uuid, nomeClasseDocumentale,file.getOriginalFilename(), file.getContentType());

					AllegatoMultipartFile allegatoMultipartFile = (AllegatoMultipartFile) file;
					List<String> ccList = new ArrayList<String>();
					List<String> toList = new ArrayList<String>();

					if( allegatoMultipartFile.getTo() != null ){
						for (String to : allegatoMultipartFile.getTo()) {
							toList.add(to);
						}
						proprietaDocumento.put("To", toList);
					}

					if( allegatoMultipartFile.getCc() != null ){
						for (String cc : allegatoMultipartFile.getCc()) {
							ccList.add(cc);
						}
						proprietaDocumento.put("CarbonCopy", ccList);
					}

					proprietaDocumento.put("From", allegatoMultipartFile.getFrom());
					proprietaDocumento.put("EmailSubject", allegatoMultipartFile.getOggetto());
					logger.info("Data invio " + allegatoMultipartFile.getDataInvio());
					proprietaDocumento.put("SentOn", DateUtil.toDateForDDS(allegatoMultipartFile.getDataInvio()));
					logger.info("Data ricezione " + allegatoMultipartFile.getDataRicezione());
					proprietaDocumento.put("ReceivedOn",  DateUtil.toDateForDDS(allegatoMultipartFile.getDataRicezione()));
				} else {
					nomeClasseDocumentale = FileNetClassNames.PROTOCOLLO_DOCUMENT;
					Documento documento = documentoDAO.creaDocumentoDB(uuid, nomeClasseDocumentale,
							file.getOriginalFilename(), file.getContentType());
					DocumentoProtCorrisp doc = fascicoloDAO.aggiungiDocumento(attoId, categoriaId, documento.getId());

					proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(doc.getId() + ""));

				}

				
				
				byte[] contenuto = file.getBytes();
				//@@DDS documentaleCryptDAO.creaDocumento(uuid, file.getOriginalFilename(), nomeClasseDocumentale,
				//@@DDS		file.getContentType(), proprietaDocumento, cartellaPadre, contenuto);
				logger.info("Proprietà documento : " + proprietaDocumento);
				documentaleDdsCryptDAO.creaDocumento(uuid, file.getOriginalFilename(), nomeClasseDocumentale,
						file.getContentType(), proprietaDocumento, cartellaPadre.getFolderPath(), contenuto);
				documentiInseritiFileNet.put(uuid,false);
			}
		}catch(Exception e)
		{
			for (String uuid:documentiInseritiFileNet.keySet()) 
				//@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
				documentaleDdsCryptDAO.eliminaDocumento(uuid);
			throw e;
		}
	}


	@Override
	public Atto getAttoPerNumeroProtocollo(String numeroProtocollo) throws Throwable {
		Atto atto = attoDao.getAttoPerNumeroProtocollo(numeroProtocollo);
		return atto;
	}
	
	
	/**
	 * Crea ed inserisce un nuovo atto in archivio.
	 * L'atto sar� visibile solo alla segreteria fino a quando non verr�
	 * validato.
	 * @author MASSIMO CARUSO
	 * @return l'atto generato, null altrimenti
	 * @throws Throwable
	 */
	@Override
	public synchronized Atto inserisciNuovoAttoInArchivio(String assegnatario, String idDocumento) throws Throwable{
		
		Atto atto = null;
		StatoAtto daValidare = attoDao.getStatoAttoPerLingua("IT", "VAL");
		try{
			
			// acquisisco l'atto riservato
			atto = attoDao.getAttoRiservatoByIdDocumento(idDocumento);
			//cambio lo stato in Validare
			atto.setStatoAtto(daValidare);
			//setto il destinatario
			atto.setDestinatario(assegnatario);
			
			attoDao.modifica(atto);
			
		}catch(Throwable e){
			e.printStackTrace();
			throw e;
		}
		
		return atto;
	}
	
	/**
	 * Salva un documento (email) in FileNet e restituisce il suo ID.
	 * Durante l'operazione viene aggiornato l'atto passato in input
	 * aggiungendo le informazioni contenute nel documento. 
	 * @author MASSIMO CARUSO
	 * @param root_folder il path in cui si trova il file
	 * @param email la email da salvare
	 * @param atto l'atto a cui assegnare il file
	 * @return l'id del file inserito, -1 in caso di errore o file gi� inserito
	 * @throws Throwable
	 */
	@Override
	public synchronized long salvaDocumento(String root_folder,JSONMail email,Atto atto)  throws Throwable{
		
		String uuid = ""+UUID.randomUUID();
		InputStream stream = null;
		Documento documento = null;
		Date actual = new Date();
		long to_return = -1;
		
		// verifico che il documento non sia gi� presente
		documento = documentoDAO.cercaDocumento(email.getNomeFile());
		if(documento != null){
			return to_return;
		}
		// creo lo stream di lettura
		stream = new FileInputStream(root_folder+email.getNomeFile());
		
		// creo una nuova entry nella tabella Documento
		documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.ATTO_DOCUMENT, email.getNomeFile(), "application/vnd.ms-outlook");
		try{
			// creo le properties del documento
			Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
			proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, (int)atto.getId());
		
			// individuo il path in cui salvare il documento
			String cartellaPadreAtto = FileNetUtil.getAttoCartella(actual, atto.getNumeroProtocollo());

			
			//@@DDS documentaleCryptDAO.verificaCreaPercorsoCartella(cartellaPadreAtto);
			documentaleDdsCryptDAO.verificaCreaPercorsoCartella(cartellaPadreAtto);
			//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaPadreAtto);
			Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(cartellaPadreAtto);
			// aggiungo il file su FileNet
			//@@DDS documentaleCryptDAO.creaDocumento(documento.getUuid(), email.getNomeFile(), FileNetClassNames.ATTO_DOCUMENT, "application/vnd.ms-outlook", proprietaDocumento, cartellaPadre, stream);
			documentaleDdsCryptDAO.creaDocumento(documento.getUuid(), email.getNomeFile(), FileNetClassNames.ATTO_DOCUMENT, "application/vnd.ms-outlook", proprietaDocumento, cartellaPadre.getFolderPath(), stream);
			
		}catch(Throwable e){
			documentoDAO.cancellaDocumentoDB(documento.getId());
			e.printStackTrace();
			return to_return;
		}
		
		// associo il documento all'atto
		atto.setDocumento(documento);
		
		// aggiorno l'atto sul DB
		attoDao.modifica(atto);
		
		// acquisisco l'id del documento da restituire
		to_return = documento.getId();
		
		return to_return;
	}
	
	/**
	 * Rimuove l'atto richiesto
	 * @author MASSIMO CARUSO
	 * @param id_atto l'id dell'atto da rimuovere
	 * @throws Throwable
	 */
	@Override
	public void rimuoviAtto(long id_atto) throws Throwable{
		attoDao.removeAtto(id_atto);
	}
	
	/**
	 * Metodo di supporto per la genreazione del numero di protocollo
	 * da utilizzare per il nuovo atto.
	 * @param ultimo l'ultimo numero di protocollo presente nel DB.
	 * @return il numero di protocollo generato
	 */
	private String generaNuovoNumeroProtocollo(String ultimo){
		String[] splitted = ultimo.split("/");
		Date actual = new Date();
		int actual_year = actual.getYear() + 1900;
		int passed_year = Integer.parseInt(splitted[2]);
		int number = 1;
		if(actual_year == passed_year){
			number = Integer.parseInt(splitted[1]) + 1;
		}
		return "Snam/"+number+"/"+actual_year;
		
	}
	
}
