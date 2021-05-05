package eng.la.business;

import java.util.ArrayList;
import java.util.List;

import eng.la.persistence.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

//@@DDS import com..Document;
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;

import eng.la.model.Autorizzazione;
import eng.la.model.Fascicolo;
import eng.la.model.Utente;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetUtil;

@Service("ricercaService")
public class RicercaServiceImpl implements RicercaService {

	private static final Logger logger = Logger.getLogger(RicercaServiceImpl.class);
	/*@@DDS
	@Autowired
	private DocumentaleDAO documentaleDAO;

	public void setDocumentaleDao(DocumentaleDAO dao) {
		this.documentaleDAO = dao;
	}

	public DocumentaleDAO getDocumentaleDao() {
		return documentaleDAO;
	}

	@Autowired
	private DocumentaleCryptDAO documentaleCryptDAO;

	public void setDocumentaleCryptDao(DocumentaleCryptDAO dao) {
		this.documentaleCryptDAO = dao;
	}

	public DocumentaleCryptDAO getDocumentaleCryptDao() {
		return documentaleCryptDAO;
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
	private DocumentaleDdsCryptDAO documentaleDdsCryptDAO;

	public void setDocumentaleDdsCryptDao(DocumentaleDdsCryptDAO dao) {
		this.documentaleDdsCryptDAO = dao;
	}

	public DocumentaleDdsCryptDAO getDocumentaleDdsCryptDao() {
		return documentaleDdsCryptDAO;
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
	private FascicoloDAO fascicoloDAO;

	public void setDao(FascicoloDAO dao) {
		this.fascicoloDAO = dao;
	}

	public FascicoloDAO getDao() {
		return fascicoloDAO;
	}
	
	@Autowired
	private AutorizzazioneDAO autorizzazioneDAO;
	
	public AutorizzazioneDAO getAutorizzazioneDAO() {
		return autorizzazioneDAO;
	}
	
	@Autowired
	private UtenteDAO utenteDAO;
	
	public UtenteDAO getUtenteDAO() {
		return utenteDAO;
	}
	
	@Override
	public Document leggiDocumentoUUID(String uuid) throws Throwable {
		return documentaleDdsDAO.leggiDocumentoUUID(uuid);
	}

	@Override
	public Document leggiDocumentoCryptUUID(String uuid) throws Throwable {
		return documentaleDdsCryptDAO.leggiDocumentoUUID(uuid);
	}
	
	@Override
	public Folder leggiCartella(String percorsoCartella) throws Throwable {
		return documentaleDdsDAO.leggiCartella(percorsoCartella);
	}
	
	@Override
	public Folder leggiCartellaCrypt(String percorsoCartella) throws Throwable {
		return documentaleDdsCryptDAO.leggiCartella(percorsoCartella);
	}
	
	@Async 
	public void bonificaFascicoliAclAnno(Integer anno) throws Throwable {
		ArrayList<Long> okid = new ArrayList<>();
		ArrayList<Long> koid = new ArrayList<>();
		try {
			List<Fascicolo> listFascicoli = fascicoloDAO.cerca(anno, null);
			logger.debug("Trovati "+listFascicoli.size()+" Fascicoli da bonificare!");
			
			for (Fascicolo fascicolo : listFascicoli) {
				long id = fascicolo.getId();

				try {
					
					bonificaFascicoliAcl(fascicolo);
					okid.add(id);
					
				} catch (Throwable e) {
					koid.add(id);
					logger.error(e); 
				}
			}
			
		} catch (Throwable e) {
			logger.error(e); 
		}
		
		 logger.debug(okid.toString() + " Eseguite con successo: "+okid.size()+" - Eseguite senza successo: "+koid.size()+" "+koid);
		
	}
	
	private void bonificaFascicoliAcl(Fascicolo fascicolo) throws Throwable {
		
		long id = fascicolo.getId();
		logger.debug("INIZIO * Bonifica ACL per id fascicolo: "+id);
		
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		String nomeCartellaFascicolo = FileNetUtil.getFascicoloCartella(fascicolo.getDataCreazione(),
				fascicolo.getNome());
		Folder cartellaFascicolo = isPenale ? leggiCartellaCrypt(nomeCartellaFascicolo)
				: leggiCartella(nomeCartellaFascicolo);
		List<Autorizzazione> autorizzazioni = autorizzazioneDAO.leggiAutorizzazioni2(fascicolo.getId(), CostantiDAO.NOME_CLASSE_FASCICOLO);
		for(Autorizzazione av: autorizzazioni) {
			Autorizzazione a = av;
			
			//logger.debug("TipoAutorizzazione = "+a.getTipoAutorizzazione().getCodGruppoLingua());
			
			if(a.getTipoAutorizzazione().getCodGruppoLingua().equals(CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_LETTURA)){
				
				String matricolaUtil = a.getUtente().getMatricolaUtil();
				Utente uv = utenteDAO.leggiUtenteDaMatricola(matricolaUtil);

				logger.debug("@@DDS RicercaServiceImpl.bonificaFascicoliAcl TODO - " + uv.getUseridUtil());
				//TODO @@DDS FileNetUtil.addAclFolder(true, uv.getUseridUtil(), cartellaFascicolo, false);
				
			} else if(a.getTipoAutorizzazione().getCodGruppoLingua().equals(CostantiDAO.TIPO_AUTORIZZAZIONE_GRUPPO_LETTURA)){
				
				String forRespMatricola=a.getUtenteForResp()==null?null:a.getUtenteForResp().getMatricolaUtil();
				String gruppoUtente = a.getGruppoUtente()==null?null:a.getGruppoUtente().getCodice();
				
				if(forRespMatricola != null) {
					List<Utente> listaResponsabili=utenteDAO.leggiResponsabili(forRespMatricola);
					if(listaResponsabili != null && !listaResponsabili.isEmpty()){
						for(Utente uv: listaResponsabili) {
							logger.debug("@@DDS RicercaServiceImpl.bonificaFascicoliAcl TODO - " + uv.getUseridUtil());
							//TODO @@DDS
							//TODO @@DDSFileNetUtil.addAclFolder(true, uv.getUseridUtil(), cartellaFascicolo, false);
							
						}
					}
				} else if(gruppoUtente!=null){
					logger.debug("@@DDS RicercaServiceImpl.bonificaFascicoliAcl TODO - " + gruppoUtente);
					//TODO @@DDS
					//TODO @@DDSFileNetUtil.addAclFolder(false, gruppoUtente, cartellaFascicolo, false);
				}
				
			} else if(a.getTipoAutorizzazione().getCodGruppoLingua().equals(CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA)){
				
				String matricolaUtil = a.getUtente().getMatricolaUtil();
				Utente uv = utenteDAO.leggiUtenteDaMatricola(matricolaUtil);
				//TODO @@DDS
				//TODO @@DDS FileNetUtil.addAclFolder(true, uv.getUseridUtil(), cartellaFascicolo, true);
				
			} else if(a.getTipoAutorizzazione().getCodGruppoLingua().equals(CostantiDAO.TIPO_AUTORIZZAZIONE_GRUPPO_SCRITTURA)){
				
				String forRespMatricola=a.getUtenteForResp()==null?null:a.getUtenteForResp().getMatricolaUtil();
				String gruppoUtente = a.getGruppoUtente()==null?null:a.getGruppoUtente().getCodice();
				
				if(forRespMatricola != null) {
					List<Utente> listaResponsabili=utenteDAO.leggiResponsabili(forRespMatricola);
					if(listaResponsabili != null && !listaResponsabili.isEmpty()){
						for(Utente uv: listaResponsabili) {
							//TODO @@DDS
							//TODO @@DDS FileNetUtil.addAclFolder(true, uv.getUseridUtil(), cartellaFascicolo, true);
							
						}
					}
				} else if(gruppoUtente!=null){
					//TODO @@DDS
					//TODO @@DDS FileNetUtil.addAclFolder(false, gruppoUtente, cartellaFascicolo, true);
				}
				
			}
			
		}
		
		//ereditariet� document
		//TODO @@DDS
		//TODO @@DDSFileNetUtil.addAclFile(cartellaFascicolo);
		
		logger.debug("FINE * Bonifica ACL per id fascicolo: "+id);
		
	}
	
}
