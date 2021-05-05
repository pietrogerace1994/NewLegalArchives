package eng.la.business;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import eng.la.persistence.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//@@import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;

import eng.la.model.Fascicolo;
import eng.la.model.SchedaFondoRischi;
import eng.la.model.StoricoSchedaFondoRischi;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.SchedaFondoRischiView;
import eng.la.model.view.StoricoSchedaFondoRischiView;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("schedaFondoRischiService")
public class SchedaFondoRischiServiceImpl extends BaseService<SchedaFondoRischi,SchedaFondoRischiView> implements SchedaFondoRischiService {
	private static final Logger logger = Logger.getLogger(SchedaFondoRischiServiceImpl.class);
/*@@DDS
	@Autowired
	private DocumentaleDAO documentaleDAO;

	@Autowired
	private DocumentaleCryptDAO documentaleCryptDAO;
*/
	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;

	@Autowired
	private DocumentaleDdsCryptDAO documentaleDdsCryptDAO;

	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiDAO;
	
	@Autowired
	private SchedaFondoRischiDAO schedaFondoRischiDAO;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDAO;

	@Override
	public List<SchedaFondoRischiView> leggi() throws Throwable { 
		List<SchedaFondoRischi> lista = schedaFondoRischiDAO.leggi();
		List<SchedaFondoRischiView> listaRitorno = (List<SchedaFondoRischiView>) convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public List<SchedaFondoRischiView> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner) throws Throwable { 
		List<SchedaFondoRischi> lista = schedaFondoRischiDAO.leggiIncarichiAutorizzati(sortByFieldName,orderAscOrDesc, userIdOwner);
		List<SchedaFondoRischiView> listaRitorno = (List<SchedaFondoRischiView>) convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public SchedaFondoRischiView leggi(long id) throws Throwable {
		SchedaFondoRischi schedaFondoRischi = schedaFondoRischiDAO.leggi(id);
		return (SchedaFondoRischiView) convertiVoInView(schedaFondoRischi);
	}

	@Override
	public ListaPaginata<SchedaFondoRischiView> cerca(String dal, String al, String statoSchedaCode, String tipologiaScheda, String rischioSoccombenzaScheda, String nomeFascicolo , int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<SchedaFondoRischi> lista = schedaFondoRischiDAO.cerca(dal, al, statoSchedaCode, tipologiaScheda, rischioSoccombenzaScheda, nomeFascicolo, elementiPerPagina, numeroPagina,
				ordinamento, ordinamentoDirezione);
		List<SchedaFondoRischiView> listaView = (List<SchedaFondoRischiView>) convertiVoInView(lista);
		ListaPaginata<SchedaFondoRischiView> listaRitorno = new ListaPaginata<SchedaFondoRischiView>();
		Long conta = schedaFondoRischiDAO.conta(dal, al, statoSchedaCode, tipologiaScheda, rischioSoccombenzaScheda, nomeFascicolo);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}
	
	@Override
	public Long conta(String dal, String al, String statoSchedaFondoRischiCode, String tipologiaSchedaCode, String rischioSoccombenzaCode, String controparte, String nomeFascicolo  ) throws Throwable {
		return schedaFondoRischiDAO.conta(dal, al, statoSchedaFondoRischiCode, tipologiaSchedaCode, rischioSoccombenzaCode, nomeFascicolo);
	}
 
	@Override
	public List<SchedaFondoRischiView> cercaNelTrimestre(Date dal, Date al) throws Throwable {
		List<SchedaFondoRischi> lista = schedaFondoRischiDAO.cerca(dal, al);
		List<SchedaFondoRischiView> listaView = (List<SchedaFondoRischiView>) convertiVoInView(lista);
		return listaView;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public SchedaFondoRischiView inserisci(SchedaFondoRischiView schedaFondoRischiView) throws Throwable {
		schedaFondoRischiView.getVo().setDataCreazione(new Date());
		schedaFondoRischiView.getVo().setStatoSchedaFondoRischi(anagraficaStatiDAO.leggiStatoSchedaFondoRischi(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_BOZZA, schedaFondoRischiView.getLocale().getLanguage().toUpperCase()));
		SchedaFondoRischi schedaFondoRischi = schedaFondoRischiDAO.inserisci(schedaFondoRischiView.getVo());
		SessionFactory sessionFactory = (SessionFactory) SpringUtil.getBean("sessionFactory");
		sessionFactory.getCurrentSession().flush();
		
		SchedaFondoRischiView view = new SchedaFondoRischiView();
		view.setVo(schedaFondoRischi);
		 
		Fascicolo fascicolo = schedaFondoRischiView.getVo().getFascicolo();
		boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		
		String cartellaFascicolo = FileNetUtil.getFascicoloCartella(fascicolo.getDataCreazione(), fascicolo.getNome());
		
		String nomeCartella = schedaFondoRischi.getId() + "-" + DateUtil.getDatayyyymmddHHmmss(schedaFondoRischi.getDataCreazione().getTime());
		String uuid = UUID.randomUUID().toString();
		String nomeClasseCartella = FileNetClassNames.SCHEDA_FONDO_RISCHI_FOLDER; 
		Map<String, Object> proprietaCartella = new HashMap<String,Object>();
		long idSchedaFondoRischi = schedaFondoRischi.getId();
		proprietaCartella.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, Integer.parseInt(idSchedaFondoRischi+""));
		
		
		if( isPenale ){
			//@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaFascicolo);
			Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella(cartellaFascicolo);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + cartellaFascicolo);
			}
			
			//@@DDS documentaleCryptDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
			documentaleDdsCryptDAO.creaCartella(uuid, cartellaFascicolo + nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
		}else{
			//@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaFascicolo);
			Folder cartellaPadre = documentaleDdsDAO.leggiCartella(cartellaFascicolo);
			if( cartellaPadre == null ){
				throw new RuntimeException("Impossibile recuperare la cartella di destinazione " + cartellaFascicolo);
			}
			
			//@@DDS documentaleDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
			documentaleDdsDAO.creaCartella(uuid, cartellaFascicolo + nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
		}
		return view;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public StoricoSchedaFondoRischiView inserisciStorico(StoricoSchedaFondoRischiView storicoSchedaFondoRischiView) throws Throwable {
		StoricoSchedaFondoRischi storicoSchedaFondoRischi = schedaFondoRischiDAO.inserisci(storicoSchedaFondoRischiView.getVo());
		
		StoricoSchedaFondoRischiView view = new StoricoSchedaFondoRischiView();
		view.setVo(storicoSchedaFondoRischi);
		 
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(SchedaFondoRischiView schedaFondoRischiView) throws Throwable {	
		schedaFondoRischiView.getVo().setDataModifica(new Date()); 
		schedaFondoRischiDAO.modifica(schedaFondoRischiView.getVo());
		rimuoviAllegatiGenerici(schedaFondoRischiView);
		salvaAllegatiGenerici(schedaFondoRischiView);
	}

	private void salvaAllegatiGenerici(SchedaFondoRischiView schedaFondoRischiView) throws Throwable{
		if( schedaFondoRischiView.getListaAllegatiLegaleEsterno() != null ){
			List<DocumentoView> allegatiLegaleEsterno = schedaFondoRischiView.getListaAllegatiLegaleEsterno();
			for(DocumentoView documentoView : allegatiLegaleEsterno ){
				if( documentoView.isNuovoDocumento() ){
					
				    InputStream contenuto = null;
					try{
						boolean isPenale = schedaFondoRischiView.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
					    
						if( isPenale ){
							/*@@DDS Folder cartellaPadre = documentaleCryptDAO.leggiCartella( FileNetUtil.getSchedaFondoRischiCartella(schedaFondoRischiView.getVo().getId(), schedaFondoRischiView.getFascicoloRiferimento().getVo().getDataCreazione(),
									schedaFondoRischiView.getFascicoloRiferimento().getVo().getNome(), DateUtil.getDatayyyymmddHHmmss(schedaFondoRischiView.getVo().getDataCreazione().getTime())));
							contenuto = new FileInputStream(documentoView.getFile());
							documentaleCryptDAO.creaDocumento(null, documentoView.getNomeFile(), FileNetClassNames.ALLEGATO_DOCUMENT, documentoView.getContentType(), null, cartellaPadre.getFolderPath(), contenuto);
							*/
							Folder cartellaPadre = documentaleDdsCryptDAO.leggiCartella( FileNetUtil.getSchedaFondoRischiCartella(schedaFondoRischiView.getVo().getId(), schedaFondoRischiView.getFascicoloRiferimento().getVo().getDataCreazione(),
									schedaFondoRischiView.getFascicoloRiferimento().getVo().getNome(), DateUtil.getDatayyyymmddHHmmss(schedaFondoRischiView.getVo().getDataCreazione().getTime())));
							contenuto = new FileInputStream(documentoView.getFile());
							logger.info("@@DDS ------------------------ SchedaFondoRischiServiceImpl cartella Padre + " + cartellaPadre);
							documentaleDdsCryptDAO.creaDocumento(null, documentoView.getNomeFile(), FileNetClassNames.ALLEGATO_DOCUMENT, documentoView.getContentType(), null, cartellaPadre.getFolderPath(), contenuto);
						}else{
						    //@@DDS Folder cartellaPadre = documentaleDAO.leggiCartella( FileNetUtil.getSchedaFondoRischiCartella(schedaFondoRischiView.getVo().getId(), schedaFondoRischiView.getFascicoloRiferimento().getVo().getDataCreazione(),
						    //@@DDS		schedaFondoRischiView.getFascicoloRiferimento().getVo().getNome(), DateUtil.getDatayyyymmddHHmmss(schedaFondoRischiView.getVo().getDataCreazione().getTime())));
							Folder cartellaPadre = documentaleDdsDAO.leggiCartella( FileNetUtil.getSchedaFondoRischiCartella(schedaFondoRischiView.getVo().getId(), schedaFondoRischiView.getFascicoloRiferimento().getVo().getDataCreazione(),
									schedaFondoRischiView.getFascicoloRiferimento().getVo().getNome(), DateUtil.getDatayyyymmddHHmmss(schedaFondoRischiView.getVo().getDataCreazione().getTime())));
						    contenuto = new FileInputStream(documentoView.getFile());
					        //@@DDS documentaleDAO.creaDocumento(null, documentoView.getNomeFile(), FileNetClassNames.ALLEGATO_DOCUMENT, documentoView.getContentType(), null, cartellaPadre, contenuto);
							logger.info("@@DDS ------------------------ SchedaFondoRischiServiceImpl cartella Padre + " + cartellaPadre);
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

	private void rimuoviAllegatiGenerici(SchedaFondoRischiView schedaFondoRischiView) throws Throwable {
		if( schedaFondoRischiView.getAllegatiDaRimuovereUuid() != null ){
			
			boolean isPenale = schedaFondoRischiView.getFascicoloRiferimento().getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
			
			Set<String> uuidSet = schedaFondoRischiView.getAllegatiDaRimuovereUuid();
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

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(SchedaFondoRischiView schedaFondoRischiView) throws Throwable {
		schedaFondoRischiDAO.cancella(schedaFondoRischiView.getVo().getId());
	}
	
	public String leggiDelay() throws Throwable {
		String delayPFR = "";
		
		delayPFR = configurazioneDAO.leggiValore("DELAY_PFR");
		
		if(delayPFR.isEmpty()){
			delayPFR = "0";
		}
		return delayPFR;
	}

	@Override
	protected Class<SchedaFondoRischi> leggiClassVO() {
		return SchedaFondoRischi.class;
	}

	@Override
	protected Class<SchedaFondoRischiView> leggiClassView() {
		return SchedaFondoRischiView.class;
	}
}
