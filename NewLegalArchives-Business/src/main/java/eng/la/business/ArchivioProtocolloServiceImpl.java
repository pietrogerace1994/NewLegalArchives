package eng.la.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

//@@DDS  import com.filenet.api.core.Folder;
//@@DDS import com.filenet.api.core.Document;
import eng.la.persistence.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.filenet.api.constants.RefreshMode;
//@@DDS import com.filenet.api.core.Document;
//@@DDS  import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;//@@DDS
import it.snam.ned.libs.dds.dtos.v2.Document; //@@DDS

import eng.la.model.ArchivioProtocollo;
import eng.la.model.Documento;
import eng.la.model.Fascicolo;
import eng.la.model.StatoProtocollo;
import eng.la.model.Utente;
import eng.la.model.mail.JSONMail;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.ProtocolloView;
import eng.la.model.view.UtenteView;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("archivioProtocolloService")
public class ArchivioProtocolloServiceImpl extends BaseService<ArchivioProtocollo, ProtocolloView> implements ArchivioProtocolloService {


	@Autowired
	private ArchivioProtocolloDAO archivioProtocolloDAO;
	
	@Autowired
	private FascicoloDAO fascicoloDAO;
	
	@Autowired
	private UtenteDAO utenteDAO;

	@Autowired
	private DocumentoDAO documentoDAO;

	/*@@DDS
	@Autowired
	private DocumentaleDAO documentaleDAO;
	public void setDocumentaleDao(DocumentaleDAO dao) {
		this.documentaleDAO = dao;
	}

	@Autowired
	private DocumentaleDdsDAO documentaleDAO;

	public void setDocumentaleDao(DocumentaleDAO dao) {
		this.documentaleDAO = dao;
	}


	@Autowired
	private DocumentaleCryptDAO documentaleCryptDAO;

	@Autowired
	private DocumentaleDdsCryptDAO documentaleDdsCryptDAO;

	public void setDocumentaleCryptDao(DocumentaleCryptDAO dao) {
		this.documentaleCryptDAO = dao;
	}
 */
	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;
	public void setDocumentaleDdsDao(DocumentaleDdsDAO daoDds) {
		this.documentaleDdsDAO = daoDds;
	}

	@Autowired
	private DocumentaleDdsCryptDAO documentaleDdsCryptDAO;

	public void setDocumentaleDdsCryptDao(DocumentaleDdsCryptDAO daoDds) {
		this.documentaleDdsCryptDAO = daoDds;
	}
	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	public void setAnagraficaStatiTipiService(AnagraficaStatiTipiService anagraficaStatiTipiService) {
		this.anagraficaStatiTipiService = anagraficaStatiTipiService;
	}

	@Override
	@Transactional
	public String generaNumeroProtocollo(Utente utente, String utenteS, String unitaAppart, String oggetto,String tipo, Utente utenteConnesso, Locale locale) throws Throwable {

		ArchivioProtocollo archivioProtocolloUno = new ArchivioProtocollo();
		
		ArchivioProtocollo archivioProtocolloDue = null;
		
		ArchivioProtocollo archivioProtocolloPrev = null;
		
		Integer progressivo;
		String numProtocollo;
		
		
		
		if(tipo.equalsIgnoreCase("in")){
			archivioProtocolloUno.setDestinatarioMat(utente);
			archivioProtocolloUno.setMittente(utenteS);
		}
		else if (tipo.equalsIgnoreCase("out")){
			archivioProtocolloUno.setMittenteMat(utente);
			archivioProtocolloUno.setDestinatario(utenteS);
			
		}
		
		archivioProtocolloUno.setUnitaAppart(unitaAppart);
		archivioProtocolloUno.setOggetto(oggetto);
		archivioProtocolloUno.setTipo(tipo);
		archivioProtocolloUno.setOwner(utenteConnesso);
		
		StatoProtocollo statoProtocollo = anagraficaStatiTipiService.leggiStatoProtocollo(Costanti.PROTOCOLLO_STATO_BOZZA, locale.getLanguage().toUpperCase()).getVo();
		
		archivioProtocolloUno.setStatoProtocollo(statoProtocollo);
		archivioProtocolloUno.setDataInserimento(new Date());
		
		archivioProtocolloPrev = archivioProtocolloDAO.getPrevArchivioProtocollo();
		
		archivioProtocolloDue = archivioProtocolloDAO.insertArchivioProtocolloFirst(archivioProtocolloUno);
		
		
		
		if(DateUtil.getAnno(archivioProtocolloPrev.getDataInserimento()) < DateUtil.getAnno(archivioProtocolloDue.getDataInserimento()))
			progressivo=1;
		else	
			progressivo = archivioProtocolloPrev.getProgressivo()+1;
		
		archivioProtocolloDue.setProgressivo(progressivo);
		
		numProtocollo = String.format("%04d", progressivo) 
						+"/"+ DateUtil.getAnnoAbbr(archivioProtocolloDue.getDataInserimento()) 
						+"/"+ utente.getSiglaUtente()
						+"/"+ tipo;
		
		archivioProtocolloDue.setNumProtocollo(numProtocollo);
		
		archivioProtocolloDAO.updateArchivioProtocollo(archivioProtocolloDue);
		
		return numProtocollo;
	}
	
	/**
	 * Crea ed inserisce un nuovo protocollo in archivio
	 * @author MASSIMO CARUSO
	 * @param user la user id dell'utente che vuole creare il protocollo
	 * @param tipo il tipo di protocollo (IN/OUT)
	 * @return il protocollo generato, null altrimenti
	 */
	@Override
	@Transactional
	public synchronized ArchivioProtocollo inserisciNuovoProtocolloInArchivio(String user, String tipo) throws Throwable{
		String numero_protocollo = null;
		ArchivioProtocollo protocollo = null;
		// acquisisco la data odierna
		Date data = new Date();
		
		StatoProtocollo daAssegnare = anagraficaStatiTipiService.leggiStatoProtocollo(Costanti.PROTOCOLLO_STATO_DA_ASSEGNARE, "IT").getVo();
		try{
			// acquisisco l'ultimo protocollo inserito
			ArchivioProtocollo last = archivioProtocolloDAO.getPrevArchivioProtocollo();
			// acquisisco l'ultimo numero di protocollo generato
			int nuovo_progressivo = last.getProgressivo() + 1;
			// acquisisco le informazioni sull'utente che ha inviato la richiesta
			Utente utente = utenteDAO.leggiUtenteDaUserId(user);
			// genero il numero di protocollo
			numero_protocollo = String.format("%04d", nuovo_progressivo) 
					+"/"+ DateUtil.getAnnoAbbr(data) 
					+"/"+ utente.getSiglaUtente()
					+"/"+ tipo;
			// creo il nuovo protocollo
			protocollo = new ArchivioProtocollo();
			protocollo.setNumProtocollo(numero_protocollo);
			protocollo.setTipo(tipo);
			protocollo.setProgressivo(nuovo_progressivo);
			protocollo.setDataInserimento(data);
			protocollo.setOwner(utente);
			protocollo.setStatoProtocollo(daAssegnare);
			protocollo.setUnitaAppart(utente.getCodiceUnitaAppart());

			// a seconda del tipo, assegno la matricola del mittente e del destinatario
			if(tipo.equalsIgnoreCase("IN")){
				protocollo.setDestinatarioMat(utente);
				protocollo.setDestinatario(utente.getNominativoUtil());
			}else{
				protocollo.setMittente(utente.getNominativoUtil());
				protocollo.setMittenteMat(utente);
			}
			archivioProtocolloDAO.insertArchivioProtocolloFirst(protocollo);
			
		}catch(Throwable e){
			e.printStackTrace();
			throw e;
		}
		
		return protocollo;
	}
	
	/**
	 * Salva un documento (email) in FileNet e restituisce il suo ID.
	 * Durante l'operazione viene aggiornato il protocollo passato in input
	 * aggiungendo le informazioni contenute nel documento. 
	 * @author MASSIMO CARUSO
	 * @param root_folder il path in cui si trova il file
	 * @param email la email da salvare
	 * @param protocollo il protocollo a cui assegnare il file
	 * @return l'id del file inserito, -1 in caso di errore o file gi� inserito
	 * @throws Throwable
	 */
	@Transactional
	public synchronized long salvaDocumento(String root_folder,JSONMail email,ArchivioProtocollo protocollo)  throws Throwable{
		
		String uuid = ""+UUID.randomUUID();
		InputStream stream = null;
		Documento documento = null;
		long to_return = -1;
		
		// verifico che il documento non sia gi� presente
		documento = documentoDAO.cercaDocumento(email.getNomeFile());
		if(documento != null){
			return to_return;
		}
		// creo lo stream di lettura
		stream = new FileInputStream(root_folder+email.getNomeFile());
		// creo una nuova entry nella tabella Documento
		documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.ARCHIVIO_PROTOCOLLO_DOCUMENT, email.getNomeFile(), "application/vnd.ms-outlook");
			
		// genero le properties del file
		Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(protocollo.getId()+""));
			
		String cartellaPadreProtocollo = FileNetUtil.getProtocolloCartellaPadre(protocollo.getDataInserimento()); 

		String nomeCartella = protocollo.getNumProtocollo().replace("/", "-");

		String nomeClasseCartella = FileNetClassNames.ARCHIVIO_PROTOCOLLO_FOLDER;

		try{
			//@@DDS documentaleDAO.verificaCreaPercorsoCartella(cartellaPadreProtocollo);
			//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaPadreProtocollo);
			//@@DDS Folder cartellaPadreDoc = documentaleDAO.leggiCartella(cartellaPadreProtocollo+nomeCartella);
			documentaleDdsDAO.verificaCreaPercorsoCartella(cartellaPadreProtocollo);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaPadreProtocollo);
			Folder cartellaPadreDoc = documentaleDdsDAO.leggiCartella(cartellaPadreProtocollo+nomeCartella);
			if(cartellaPadreDoc == null){
				//@@DDS cartellaPadreDoc = documentaleDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
				cartellaPadreDoc = documentaleDdsDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
			}
		
			// aggiungo il file su FileNet
			//@@DDS documentaleDAO.creaDocumento(documento.getUuid(), email.getNomeFile(), FileNetClassNames.ARCHIVIO_PROTOCOLLO_DOCUMENT, "application/vnd.ms-outlook", proprietaDocumento, cartellaPadreDoc, stream);
			documentaleDdsDAO.creaDocumento(documento.getUuid(), email.getNomeFile(), FileNetClassNames.ARCHIVIO_PROTOCOLLO_DOCUMENT, "application/vnd.ms-outlook", proprietaDocumento, cartellaPadreDoc.getFolderPath(), stream);
		
		}catch(Throwable e){
			//@@DDS documentoDAO.cancellaDocumentoDB(documento.getId());
			documentoDAO.cancellaDocumentoDB(documento.getId());
			e.printStackTrace();
			return to_return;
		}
		
		// aggiorno il protocollo
		protocollo.setDocumento(documento);
		protocollo.setOggetto(email.getOggetto());
		if(protocollo.getTipo().equalsIgnoreCase("IN")){
			protocollo.setMittente(email.getFrom());
		}else{
			String to = "";
			String[] array_to = email.getTo();
			int size = array_to.length;
			
			for(int i= 0; i < size - 1; i++)
				to = to + array_to[i] + ",";
			
			to = to + array_to[size-1];
			protocollo.setDestinatario(to);
		}
		archivioProtocolloDAO.updateArchivioProtocollo(protocollo);
			
		// acquisisco l'id del documento
		to_return = documento.getId();	
		
		return to_return;
	}

	/**
	 * Rimuove il protocollo richiesto
	 * @author MASSIMO CARUSO
	 * @param id_protocollo l'id del protocollo da rimuovere
	 * @throws Throwable
	 */
	@Transactional
	public void rimuoviProtocollo(long id_protocollo) throws Throwable{
		archivioProtocolloDAO.removeArchivioProtocollo(id_protocollo);
	}
	
	@Override
	protected Class<ArchivioProtocollo> leggiClassVO() {
		return ArchivioProtocollo.class;
	}

	@Override
	protected Class<ProtocolloView> leggiClassView() {
		return ProtocolloView.class;
	}

	@Override
	public ListaPaginata<ProtocolloView> cerca(String numeroProtocollo, String dal, String al, String nomeFascicolo,
			int numElementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento, String tipo, UtenteView utenteConnesso, String statoProtocolloCode) throws Throwable {


		List<ArchivioProtocollo> lista = archivioProtocolloDAO.cerca(numeroProtocollo,  dal, al, nomeFascicolo, numElementiPerPagina, numeroPagina,
				ordinamento, tipoOrdinamento, tipo, utenteConnesso, statoProtocolloCode);
		List<ProtocolloView> listaView = convertiVoInView(lista);
		ListaPaginata<ProtocolloView> listaRitorno = new ListaPaginata<ProtocolloView>();
		Long conta = archivioProtocolloDAO.conta(numeroProtocollo, dal, al, nomeFascicolo, tipo, utenteConnesso, statoProtocolloCode);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(numElementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(tipoOrdinamento);
		return listaRitorno;
	}

	@Override
	public ProtocolloView leggi(long id) throws Throwable {
		ArchivioProtocollo protocollo = archivioProtocolloDAO.leggi(id);
		return (ProtocolloView) convertiVoInView(protocollo);
	}

	@Override
	public ProtocolloView leggi(String numProtocollo) throws Throwable {
		ArchivioProtocollo protocollo = archivioProtocolloDAO.leggi(numProtocollo);
		return (ProtocolloView) convertiVoInView(protocollo);
	}
	
	@Override
	@Transactional
	public void salvaDocumentoProtocollo(DocumentoView documentoView, Long idProtocollo, Locale locale) throws Throwable{
		
		ArchivioProtocollo protocollo = archivioProtocolloDAO.leggi(idProtocollo);

		InputStream contenuto = null;
		try{	
			
			
			Documento doc = null;

			String uuid =""+UUID.randomUUID()+"";  

			doc=documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.ARCHIVIO_PROTOCOLLO_DOCUMENT, documentoView.getNomeFile(), documentoView.getContentType());
			
			protocollo.setDocumento(doc);
		
			String cartellaPadreProtocollo= FileNetUtil.getProtocolloCartellaPadre(protocollo.getDataInserimento()); 

			String nomeCartella = protocollo.getNumProtocollo().replace("/", "-");

			String nomeClasseCartella = FileNetClassNames.ARCHIVIO_PROTOCOLLO_FOLDER;

			Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
			proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(protocollo.getId()+""));

			//@@DDS documentaleDAO.verificaCreaPercorsoCartella(cartellaPadreProtocollo);
			documentaleDdsDAO.verificaCreaPercorsoCartella(cartellaPadreProtocollo);

			//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaPadreProtocollo);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaPadreProtocollo);
			Folder cartellaPadreDoc;

			//@@DDS cartellaPadreDoc = documentaleDAO.leggiCartella(cartellaPadreProtocollo+nomeCartella);
			cartellaPadreDoc = documentaleDdsDAO.leggiCartella(cartellaPadreProtocollo+nomeCartella);

			if(cartellaPadreDoc == null){
				//@@DDS cartellaPadreDoc = documentaleDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
				cartellaPadreDoc = documentaleDdsDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
			}

			contenuto = new FileInputStream(documentoView.getFile());
			//@@DDS documentaleDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.ARCHIVIO_PROTOCOLLO_DOCUMENT, documentoView.getContentType(), proprietaDocumento, cartellaPadreDoc, contenuto);
			documentaleDdsDAO.creaDocumento(uuid, documentoView.getNomeFile(), FileNetClassNames.ARCHIVIO_PROTOCOLLO_DOCUMENT, documentoView.getContentType(), proprietaDocumento, cartellaPadreDoc.getFolderPath(), contenuto);
			
			
			StatoProtocollo statoProtocollo = anagraficaStatiTipiService.leggiStatoProtocollo(Costanti.PROTOCOLLO_STATO_DA_ASSEGNARE, locale.getLanguage().toUpperCase()).getVo();
			
			protocollo.setStatoProtocollo(statoProtocollo);
			
			archivioProtocolloDAO.updateArchivioProtocollo(protocollo);
		}
		catch(Throwable e){
			e.printStackTrace(); 
			throw e;
		}finally {
			IOUtils.closeQuietly(contenuto);
		}

	}

	@Override
	public void salvaDocumentoProtocolloDaUuid(String uuid, ArchivioProtocollo protocollo, Locale locale) throws Throwable{
		
		try{	
			
			//@@DDS Document documento = documentaleCryptDAO.leggiDocumentoUUID(uuid);
			//@@DDS String name = documento.get_Name();
			//@@DDS String contentType = documento.get_MimeType();
			Document documento = documentaleDdsCryptDAO.leggiDocumentoUUID(uuid);
			String name = documento.getContents().get(0).getContentsName();
			String contentType = documento.getContents().get(0).getContentsMimeType();
			//@@DDS byte[] contenuto = documentaleCryptDAO.leggiContenutoDocumento(documento);
			byte[] contenuto = documentaleDdsCryptDAO.leggiContenutoDocumento(uuid);
			//@@DDS String pathFolder = FileNetUtil.getPathFolder(documento);
			String pathFolder = documento.getFoldersParents().get(0);
			//documentaleCryptDAO.eliminaDocumento(uuid);
			documentaleDdsCryptDAO.eliminaDocumento(uuid);
			
			//bisogna eliminare anche la cartella contenitore
			//@@DDS Folder leggiCartella = documentaleCryptDAO.leggiCartella(pathFolder);
			Folder leggiCartella = documentaleDdsCryptDAO.leggiCartella(pathFolder);
			if (leggiCartella != null) {
				//@@DDS leggiCartella.delete();
				//@@DDS leggiCartella.save(RefreshMode.REFRESH);
				documentaleDdsCryptDAO.eliminaCartella(pathFolder);
			}
			
			Documento doc = null;

			doc=documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.ARCHIVIO_PROTOCOLLO_DOCUMENT, name, contentType);
			
			protocollo.setDocumento(doc);
		
			String cartellaPadreProtocollo= FileNetUtil.getProtocolloCartellaPadre(protocollo.getDataInserimento()); 

			String nomeCartella = protocollo.getNumProtocollo().replace("/", "-");

			String nomeClasseCartella = FileNetClassNames.ARCHIVIO_PROTOCOLLO_FOLDER;

			Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
			proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(protocollo.getId()+""));

			//@@DDS documentaleDAO.verificaCreaPercorsoCartella(cartellaPadreProtocollo);
			documentaleDdsDAO.verificaCreaPercorsoCartella(cartellaPadreProtocollo);

			//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaPadreProtocollo);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaPadreProtocollo);

			Folder cartellaPadreDoc;

			//@@DDS cartellaPadreDoc = documentaleDAO.leggiCartella(cartellaPadreProtocollo+nomeCartella);
			cartellaPadreDoc = documentaleDdsDAO.leggiCartella(cartellaPadreProtocollo+nomeCartella);

			if(cartellaPadreDoc == null){
				//@@DDS cartellaPadreDoc = documentaleDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
				cartellaPadreDoc = documentaleDdsDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaDocumento, cartellaPadre);
			}

			
			//@@DDS documentaleDAO.creaDocumento(uuid, name, FileNetClassNames.ARCHIVIO_PROTOCOLLO_DOCUMENT, contentType, proprietaDocumento, cartellaPadreDoc, contenuto);
			documentaleDdsDAO.creaDocumento(uuid, name, FileNetClassNames.ARCHIVIO_PROTOCOLLO_DOCUMENT, contentType, proprietaDocumento, cartellaPadreDoc.getFolderPath(), contenuto);
			
			StatoProtocollo statoProtocollo = anagraficaStatiTipiService.leggiStatoProtocollo(Costanti.PROTOCOLLO_STATO_DA_ASSEGNARE, locale.getLanguage().toUpperCase()).getVo();
			
			protocollo.setStatoProtocollo(statoProtocollo);
			
			archivioProtocolloDAO.updateArchivioProtocollo(protocollo);
		}
		catch(Throwable e){
			e.printStackTrace(); 
			throw e;
		}
	}
	
	
	@Override
	public List<ArchivioProtocollo> leggiProtocolliDaAssegnare() throws Throwable {
		return archivioProtocolloDAO.leggiProtocolliDaAssegnare();
	}
	
	@Override
	public List<ArchivioProtocollo> leggiProtocolliAssegnati(Utente utente) throws Throwable {
		return archivioProtocolloDAO.leggiProtocolliAssegnati(utente);
	}

	@Override
	public void assegnaProtocollo(long idProtocollo, String assegnatario, String commento, Locale locale) throws Throwable {
		
		ArchivioProtocollo protocollo = archivioProtocolloDAO.leggi(idProtocollo);
		
		Utente utente = utenteDAO.leggiUtenteDaMatricola(assegnatario);
		
		if(protocollo.getTipo().equalsIgnoreCase("IN"))
			protocollo.setDestinatarioMat(utente);
		else if(protocollo.getTipo().equalsIgnoreCase("OUT"))
			protocollo.setMittenteMat(utente);
		
		protocollo.setAssegnatario(utente);

		protocollo.setCommento(commento);
		
		StatoProtocollo statoProtocollo = anagraficaStatiTipiService.leggiStatoProtocollo(Costanti.PROTOCOLLO_STATO_ASSEGNATO, locale.getLanguage().toUpperCase()).getVo();
		
		protocollo.setStatoProtocollo(statoProtocollo);
		
		archivioProtocolloDAO.updateArchivioProtocollo(protocollo);
		
	}

	/**
	 * Cambia lo stato del protocollo in "ASSEGNATO".
	 * @author MASSIMO CARUSO
	 * @param idProtocollo l'id del protocollo da cambiare.
	 */
	@Override
	public void cambiaStatoAdAssegnatoProtocollo(long idProtocollo, String assegnatario) throws Throwable {
		ArchivioProtocollo protocollo = archivioProtocolloDAO.leggi(idProtocollo);
		StatoProtocollo statoProtocollo = anagraficaStatiTipiService.leggiStatoProtocollo("ASS", "IT").getVo();
		protocollo.setStatoProtocollo(statoProtocollo);
		Utente utente_assegnatario = utenteDAO.leggiUtenteDaMatricola(assegnatario);
		protocollo.setAssegnatario(utente_assegnatario);
		archivioProtocolloDAO.updateArchivioProtocollo(protocollo);
	}
	
	@Override
	public void spostaSuFascicolo(long idProtocollo, long idFascicolo, Locale locale) throws Throwable {
		
		Fascicolo fascicolo = fascicoloDAO.leggi(idFascicolo);
		
		ArchivioProtocollo protocollo = archivioProtocolloDAO.leggi(idProtocollo);
		
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);


		/* @@DDS Document documentoProt = documentaleDAO.leggiDocumentoUUID(protocollo.getDocumento().getUuid());

		
		String name = documentoProt.get_Name();
		String contentType = documentoProt.get_MimeType();
		byte[] contenuto = documentaleDAO.leggiContenutoDocumento(documentoProt);
		String pathFolder = FileNetUtil.getPathFolder(documentoProt);
		
		documentaleDAO.eliminaDocumento(protocollo.getDocumento().getUuid());
		*/

		Document documentoProt = documentaleDdsDAO.leggiDocumentoUUID(protocollo.getDocumento().getUuid());
		String name = documentoProt.getContents().get(0).getContentsName();
		String contentType = documentoProt.getContents().get(0).getContentsMimeType();
		byte[] contenuto = documentaleDdsDAO.leggiContenutoDocumento(protocollo.getDocumento().getUuid());
		String pathFolder = documentoProt.getFoldersParents().get(0);

		//@@DDS documentaleDAO.eliminaDocumento(protocollo.getDocumento().getUuid());
		documentaleDdsDAO.eliminaDocumento(protocollo.getDocumento().getUuid());

		//bisogna eliminare anche la cartella contenitore
		//@@DDS Folder leggiCartella = documentaleDAO.leggiCartella(pathFolder);
		Folder leggiCartella = documentaleDdsDAO.leggiCartella(pathFolder);
		if (leggiCartella != null) {
			//@@DDS leggiCartella.delete();
			//@@DDS leggiCartella.save(RefreshMode.REFRESH);
			documentaleDdsDAO.eliminaCartella(pathFolder);
		}
		
		String persorsoCartellaFascicolo = FileNetUtil.getFascicoloCartella(fascicolo.getDataCreazione(), fascicolo.getNome());	
		
		Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
		proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(fascicolo.getId()+""));
		
		Folder cartellaFascicolo;
		
		if(isPenale){
			//@@DDS cartellaFascicolo = documentaleCryptDAO.leggiCartella(persorsoCartellaFascicolo);
			cartellaFascicolo = documentaleDdsCryptDAO.leggiCartella(persorsoCartellaFascicolo);
			//@@DDS documentaleCryptDAO.creaDocumento(protocollo.getDocumento().getUuid(), name, FileNetClassNames.PROTOCOLLO_DOCUMENT, contentType, proprietaDocumento, cartellaFascicolo, contenuto);
			documentaleDdsCryptDAO.creaDocumento(protocollo.getDocumento().getUuid(), name, FileNetClassNames.PROTOCOLLO_DOCUMENT, contentType, proprietaDocumento, cartellaFascicolo.getFolderPath(), contenuto);
		}
		else{
			//@@DDS cartellaFascicolo = documentaleDAO.leggiCartella(persorsoCartellaFascicolo);
			cartellaFascicolo = documentaleDdsDAO.leggiCartella(persorsoCartellaFascicolo);
			//@@DDS documentaleDAO.creaDocumento(protocollo.getDocumento().getUuid(), name, FileNetClassNames.PROTOCOLLO_DOCUMENT, contentType, proprietaDocumento, cartellaFascicolo, contenuto);
			documentaleDdsDAO.creaDocumento(protocollo.getDocumento().getUuid(), name, FileNetClassNames.PROTOCOLLO_DOCUMENT, contentType, proprietaDocumento, cartellaFascicolo.getFolderPath(), contenuto);
		}
		
		documentoDAO.cancella(protocollo.getDocumento().getUuid());
		
		Documento doc=documentoDAO.creaDocumentoDB(protocollo.getDocumento().getUuid(), FileNetClassNames.PROTOCOLLO_DOCUMENT, name, contentType);
			
		protocollo.setDocumento(doc);
		
		protocollo.setFascicoloAssociato(fascicolo);
		
		archivioProtocolloDAO.updateArchivioProtocollo(protocollo);
		
	}

	@Override
	public void lasciaSuArchivioProtocollo(long idProtocollo, String language) throws Throwable {
		ArchivioProtocollo protocollo = archivioProtocolloDAO.leggi(idProtocollo);
		
		StatoProtocollo statoProtocollo = anagraficaStatiTipiService.leggiStatoProtocollo(Costanti.PROTOCOLLO_STATO_NON_TRASFERITO, language).getVo();
		
		protocollo.setStatoProtocollo(statoProtocollo);
		
		archivioProtocolloDAO.updateArchivioProtocollo(protocollo);	
	}

}
