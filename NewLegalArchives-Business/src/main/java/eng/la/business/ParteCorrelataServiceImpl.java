package eng.la.business;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Font.FontFamily;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.GrayColor;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;

import eng.la.model.ParteCorrelata;
import eng.la.model.RicercaParteCorrelata;
import eng.la.model.view.ParteCorrelataView;
import eng.la.model.view.RicercaParteCorrelataView;
import eng.la.persistence.ParteCorrelataDAO;
import eng.la.util.ClassPathUtils;
import eng.la.util.ListaPaginata;
import eng.la.util.WriteExcell;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
/**
 * <h1>Classe di business ParteCorrelataService </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * 
 * @author 
 * @version 1.0
 * @since 2016-06-16
 */
@Service("parteCorrelataService")
public class ParteCorrelataServiceImpl extends BaseService<ParteCorrelata,ParteCorrelataView> implements ParteCorrelataService {
	@Autowired
	private ParteCorrelataDAO parteCorrelataDao;
	
	/**
	 * Metodo di set della istanza DAO passata come argomento, al corrispondente
	 * membro di classe.
	 * 
	 * @param dao oggetto della classe ParteCorrelataDAO
	 * @see ParteCorrelataDAO
	 */
	public void setParteCorrelataDao(ParteCorrelataDAO parteCorrelataDao) {
		this.parteCorrelataDao = parteCorrelataDao;
	}
	
	// metodi di lettura	
	@Override
	public List<ParteCorrelataView> cerca(ParteCorrelataView parteCorrelata) throws Throwable {
		return null;
	}
	
	public static final String SEARCH_PARTECORRELATA_JRXML_REPORT_XLS_FILE_NAME		= "PartiCorrelateAnagrafica.jrxml";
	public static final String SEARCH_PARTECORRELATA_JASPER_REPORT_XLS_FILE_NAME   	= "PartiCorrelateAnagrafica.jasper";
	public static final String SEARCH_PARTECORRELATA_JASPER_REPORT_XLS_TITLE  		= "REPORT PARTI CORRELATE";
	public static final String TUTTE_PARTECORRELATA_JRXML_REPORT_XLS_FILE_NAME		= "PartiCorrelateTutte.jrxml";
	public static final String TUTTE_PARTECORRELATA_JASPER_REPORT_XLS_FILE_NAME   	= "PartiCorrelateTutte.jasper";
	public static final String TUTTE_PARTECORRELATA_JASPER_REPORT_XLS_TITLE  		= "REPORT PARTI CORRELATE";

	/**
	 * Ricerca di parteCorrelata per denominazione, codFiscPIva.
	 * N.B: questo metodo esegue solo ricerca su tabella parteCorrelata ma, non storicizza
	 * la richiesta su tabella RICERCA_PRTE_CORRELATA.
	 *  
	 * @param denominazione chiave denominazione sociale.
	 * @param codFiscPIva codice fiscale o piva
	 * @return lista ParteCorrelataView
	 */
	@Override
	public List<ParteCorrelataView> cerca(String denominazione, String codFiscale, String partitaIva) throws Throwable {
		List<ParteCorrelata> lista = parteCorrelataDao.cerca(denominazione, codFiscale, partitaIva);
		List<ParteCorrelataView> listaRitorno = (List<ParteCorrelataView>)convertiVoInView(lista);
		return listaRitorno;
	}	
	
	@Override
	public List<ParteCorrelataView> ricerca(String denominazione, String codFiscale, String partitaIva) throws Throwable {
		List<ParteCorrelata> lista = parteCorrelataDao.ricerca(denominazione, codFiscale, partitaIva);
		List<ParteCorrelataView> listaRitorno = (List<ParteCorrelataView>)convertiVoInView(lista);
		return listaRitorno;
	}	

	/**
	 * Ricerca di parteCorrelata per denominazione, codFiscPIva e data.
	 *  
	 * @param denominazione chiave denominazione sociale.
	 * @param codFiscPIva codice fiscale o piva
	 * @param dataInput eventaule data di ricerca.
	 * @return lista ParteCorrelataView
	 */
	@Override
	public List<ParteCorrelataView> cerca(String denominazione, String codFiscale, String partitaIva, Date dataInput) throws Throwable {
		List<ParteCorrelata> lista = parteCorrelataDao.cerca(denominazione, codFiscale, partitaIva);
		List<ParteCorrelataView> listaRitorno = (List<ParteCorrelataView>)convertiVoInView(lista);
		return listaRitorno;
	}	

	/**
	 * Elenca tutte le parti correlate presenti in base dati
	 * <p>
	 * @return lista perti correlate
	 * @throws Throwable
	 */
	@Override
	public List<ParteCorrelataView> leggi() throws Throwable {
		List<ParteCorrelata> lista = parteCorrelataDao.leggi();
		List<ParteCorrelataView> listaRitorno = (List<ParteCorrelataView>) convertiVoInView(lista);		
		return listaRitorno;
	}
	
	@Override
	public List<ParteCorrelataView> leggiAttive() throws Throwable {
		List<ParteCorrelata> lista = parteCorrelataDao.leggi(true);
		List<ParteCorrelataView> listaRitorno = (List<ParteCorrelataView>) convertiVoInView(lista);		
		return listaRitorno;
	}
	
	
	/**
	 * Elenca tutte le parti correlate presenti nella base dati, paginata.
	 * <p>
	 * @param elementiPerPagina
	 * @param numeroPagina
	 * @param ordinamento
	 * @param ordinamentoDirezione
	 */
	@Override
	public ListaPaginata<ParteCorrelataView> leggi(int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<ParteCorrelata> lista = parteCorrelataDao.leggi(elementiPerPagina, numeroPagina, ordinamento,ordinamentoDirezione);
		List<ParteCorrelataView> listaView = (List<ParteCorrelataView>) convertiVoInView(lista);
		
		ListaPaginata<ParteCorrelataView> listaRitorno = new ListaPaginata<ParteCorrelataView>();
		Long conta = parteCorrelataDao.conta();
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		
		return listaRitorno;
	}
	
	@Override
	public ListaPaginata<ParteCorrelataView> leggiElenco(int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<ParteCorrelata> lista = parteCorrelataDao.leggiElenco(elementiPerPagina, numeroPagina, ordinamento,ordinamentoDirezione);
		List<ParteCorrelataView> listaView = (List<ParteCorrelataView>) convertiVoInView(lista);
		
		ListaPaginata<ParteCorrelataView> listaRitorno = new ListaPaginata<ParteCorrelataView>();
		Long conta = parteCorrelataDao.conta();
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		
		return listaRitorno;
	}
	
	/**
	 * Lettura parteCorrelata per l'id.
	 * <p>
	 * @param id
	 * @throws Throwable
	 */
	@Override
	public ParteCorrelataView leggi(long id) throws Throwable {
		ParteCorrelata parteCorrelata = parteCorrelataDao.leggi(id);
		return (ParteCorrelataView) convertiVoInView(parteCorrelata);
	}
	
	/**
	 * Camcellazione logica occorenza.
	 * <p>
	 * @param id
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		parteCorrelataDao.cancella(id);
	}
	
	/**
	 * Esegue inserimento di una parteCorrelata in base dati.
	 * <p>
	 * @param parteCorrelataView 
	 * @return parteCorrelataView ritorna l'occorenza inserita.
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public ParteCorrelataView inserisci(ParteCorrelataView parteCorrelataView) throws Throwable {
		ParteCorrelata parteCorrelata = parteCorrelataDao.inserisci(parteCorrelataView.getVo());
		ParteCorrelataView view = new ParteCorrelataView();
		view.setVo(parteCorrelata);
		return view;
	}
	
	/**
	 * Esegue la modifca di una determinata occorrenza.
	 * @param parteCorrelataView
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(ParteCorrelataView parteCorrelataView) throws Throwable {

		parteCorrelataDao.cancella(parteCorrelataView.getParteCorrelataIdMod());
		parteCorrelataDao.inserisci(parteCorrelataView.getVo());
	}

	@Override
	protected Class<ParteCorrelata> leggiClassVO() { 
		return ParteCorrelata.class;
	}

	@Override
	protected Class<ParteCorrelataView> leggiClassView() { 
		return ParteCorrelataView.class;
	}
	
	
	/**
	 * Questo metodo esegue : 
	 * 		1 - la ricerca su parti correlate per similitudine.
	 * 		2 - generazione report PDF con i dati della ricerca effettuata.
	 * 		3 - scrittura su tabella RICERCA_PARTE_CORRELATA dei dati di ricerca e il blob
	 * 			del documento generato.
	 * @param isUtenteSS
	 * @param denominazione
	 * @param codFiscPIva
	 * @param data 
	 *
	 * @return HashMap hashRet HashMap contente i dati di ritorno (partiCorList;esitoRicerca; e... )
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public HashMap<String, Object> cerca(Boolean isUtenteSS,String denominazione,String codFiscale,String partitaIva,Date data) throws Throwable {
		
		String nomeUtenteVisualizzato = "Utente di test..."; 
		HashMap<String, Object> hashRet = parteCorrelataDao.cerca(nomeUtenteVisualizzato, isUtenteSS, denominazione, codFiscale, partitaIva, data);
		
		@SuppressWarnings("unchecked")
		List<ParteCorrelata> lista = (List<ParteCorrelata>)hashRet.get("partiCorList");		
		
		// 1 - generazione report
		Boolean esitoRicerca = (Boolean) hashRet.get("esitoRicerca");
		
		// 2 - scrittura su tabella RICERCA_PARTE_CORRELATA.
		RicercaParteCorrelata ricercaParteCorrelata = new RicercaParteCorrelata();
		ricercaParteCorrelata.setDenominazioneIn(denominazione); 					// denominazioneIn
		ricercaParteCorrelata.setCodFiscale(codFiscale); 							// codFiscale
		ricercaParteCorrelata.setPartitaIva(partitaIva); 							// partitaIva
		ricercaParteCorrelata.setDataIn(data); 										// dataIn data inserita da operatore.
		ricercaParteCorrelata.setDataRicerca(new Date()); 							// data esecuzione ricerca.
		ricercaParteCorrelata.setEsito(esitoRicerca==Boolean.TRUE?"P":"N"); 		// esito della ricerca
		ricercaParteCorrelata.setUserRicerca(nomeUtenteVisualizzato); 				// nome utente.
		
		// aggiunge alla hashRet istanza di ricercaParteCorrelata con l'id valorizzato.
		RicercaParteCorrelataView ricercaParteCorrelataView = new RicercaParteCorrelataView();
		hashRet.put("ricercaParteCorrelataView",ricercaParteCorrelataView );
		
		// siccome si ritorna una ParteCorrelataView, si sostituisce il valore
		// corrispondente alla chiave della HasMap : partiCorList, di tipo ParteCorrelata, appunto
		// con una lista di ParteCorrelataView.
		List<ParteCorrelataView> listaRitorno = (List<ParteCorrelataView>)convertiVoInView(lista);
		hashRet.put("partiCorList",listaRitorno); // sovrascrittura
		
		hashRet.put("esitoRicerca",ricercaParteCorrelata.getEsito());
		return hashRet;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public HashMap<String, Object> cercaPartiCorrelate(Boolean isUtenteSS,String denominazione,
									String codFiscale,String partitaIva,Date data) throws Throwable {
		
		String nomeUtenteVisualizzato = "Utente di test...";
		HashMap<String, Object> hashRet = parteCorrelataDao.cerca(nomeUtenteVisualizzato, isUtenteSS, 
																  denominazione, codFiscale, partitaIva, data);
		
		@SuppressWarnings("unchecked")
		List<ParteCorrelata> lista = (List<ParteCorrelata>)hashRet.get("partiCorList");		
		
		// 1 - generazione report
		Boolean esitoRicerca = (Boolean) hashRet.get("esitoRicerca");
		
		// 2 - scrittura su tabella RICERCA_PARTE_CORRELATA.
		RicercaParteCorrelata ricercaParteCorrelata = new RicercaParteCorrelata();
		ricercaParteCorrelata.setDenominazioneIn(denominazione); 					// denominazioneIn
		ricercaParteCorrelata.setCodFiscale(codFiscale); 							// codFiscale
		ricercaParteCorrelata.setPartitaIva(partitaIva); 							// partitaIva
		ricercaParteCorrelata.setDataIn(data); 										// dataIn data inserita da operatore.
		ricercaParteCorrelata.setDataRicerca(new Date()); 							// data esecuzione ricerca.
		ricercaParteCorrelata.setEsito(esitoRicerca==Boolean.TRUE?"P":"N"); 		// esito della ricerca
		ricercaParteCorrelata.setUserRicerca(nomeUtenteVisualizzato); 				// nome utente.
				
		// aggiunge alla hashRet istanza di ricercaParteCorrelata con l'id valorizzato.
		RicercaParteCorrelataView ricercaParteCorrelataView = new RicercaParteCorrelataView();
		hashRet.put("ricercaParteCorrelataView",ricercaParteCorrelataView );
		
		// siccome si ritorna una ParteCorrelataView, si sostituisce il valore
		// corrispondente alla chiave della HasMap : partiCorList, di tipo ParteCorrelata, appunto
		// con una lista di ParteCorrelataView.
		List<ParteCorrelataView> listaRitorno = convertiVoInView(lista);
		hashRet.put("partiCorList",listaRitorno); // sovrascrittura
		hashRet.put("esitoRicerca",ricercaParteCorrelata.getEsito());
		return hashRet;
	}
	
	//---- Generazione report ----//
	
	// Costanti per la generazione del report.	
	public static final String SEARCH_PARTECORRELATA_JRXML_REPORT_FILE_NAME		= "PartiCorrelateRicerca3.jrxml";
	public static final String SEARCH_PARTECORRELATA_JASPER_REPORT_FILE_NAME   	= "PartiCorrelateRicerca3.jasper";
	public static final String SEARCH_PARTECORRELATA_JASPER_REPORT_TITLE  		= "PARTI CORRELATE";
	public static final String PARAMETER_SUBREPORTDIR       					= "SUBREPORT_DIR";
	public static final String PARAMETER_STYLEDIR           					= "STYLE_DIR";
	public static final String PARAMETER_IMAGERDIR          					= "IMAGE_DIR";
	public static final String PARAMETER_REPORTLOCALE       					= "REPORT_LOCALE";
	public static final String PARAMETER_REPORTTIMEZONE     					= "REPORT_TIME_ZONE";
	public static final String PARAMETER_RECORDFOUND        					= "recordFound";
	public static final String PARAMETER_USER									= "user";
	public static final String PARAMETER_LANGUAGE      	  						= "language";
	public static final String PARAMETER_COUNTRY 	      						= "country";
	public static final String PARAMETER_IS_GUEST_USER      					= "isGuestUser";
	public static final String PARAMETER_IS_UTENTE_SS       					= "isUtenteSS";
	
	public void generaReportRicercaPdf(HttpServletResponse response, ParteCorrelataView parteCorrelataView) throws IOException {
		
	}
	
	public void generazioneReportRicercaPDF2(HttpServletResponse response, HttpServletRequest request, ParteCorrelataView pc, 
												List<ParteCorrelataView> pcList,Boolean esitoRicerca,String denominazioneInput,
												String codFiscaleInput, String partitaIvaInput,Date dataInput,String nomeUtenteVisualizzato,
												Boolean isUtenteSS ) throws Throwable {
		byte[] out = null;
		JasperPrint jasperPrint = null;
		
		String codFiscPIvaInput = "";
		
		if(codFiscaleInput != null || partitaIvaInput!= null){
			
			if(codFiscaleInput != null && partitaIvaInput != null){
				
				codFiscPIvaInput += codFiscaleInput + " / " + partitaIvaInput;
				
			}
			else if(codFiscaleInput != null){
				
				codFiscPIvaInput += codFiscaleInput;
			}
			else{
				codFiscPIvaInput += partitaIvaInput;
			}
		}
		
		List<ParteCorrelata> lista = new ArrayList<ParteCorrelata>();
		for (ParteCorrelataView parteCorrelataView : pcList) {
		
			ParteCorrelata parteCorrelata = new ParteCorrelata();
			parteCorrelata.setId( parteCorrelataView.getVo().getId() );
			parteCorrelata.setDenominazione( parteCorrelataView.getVo().getDenominazione() );
			parteCorrelata.setCodFiscale( parteCorrelataView.getVo().getCodFiscale() );
			parteCorrelata.setPartitaIva( parteCorrelataView.getVo().getPartitaIva() );
			if(parteCorrelataView.getVo().getNazione()!=null)
				parteCorrelata.setNazione( parteCorrelataView.getVo().getNazione() );
			if(parteCorrelataView.getVo().getTipoCorrelazione()!=null)
				parteCorrelata.setTipoCorrelazione( parteCorrelataView.getVo().getTipoCorrelazione() );
			parteCorrelata.setRapporto( parteCorrelataView.getVo().getRapporto() );
			parteCorrelata.setFamiliare( parteCorrelataView.getVo().getFamiliare() );
			parteCorrelata.setConsiglieriSindaci( parteCorrelataView.getVo().getConsiglieriSindaci() );
			parteCorrelata.setDataInserimento( parteCorrelataView.getVo().getDataInserimento() );
			
			lista.add(parteCorrelata);
		}
		String path  = "/reports/";
		String pathReport  = "/reports/" + SEARCH_PARTECORRELATA_JASPER_REPORT_FILE_NAME;   
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject( ParteCorrelataServiceImpl.class.getResourceAsStream(  pathReport ));
        
        //-----------------------------------------------------------------------
        // s'impostano il set di parametri necessari alla generazione del report
        //-----------------------------------------------------------------------
        Map<String,Object> reportParameters = new HashMap<>();
        reportParameters.put("SUBREPORT_DIR", path); 
	    reportParameters.put("reportTitle", SEARCH_PARTECORRELATA_JASPER_REPORT_TITLE);
	    reportParameters.put("recordFound", String.valueOf(pcList.size()) );
	    reportParameters.put("denominazioneInput", denominazioneInput );
	    reportParameters.put("codfiscPivaInput", codFiscPIvaInput );
	    reportParameters.put("dataInput", dataInput);
	    reportParameters.put("esito", (esitoRicerca!=null) ? esitoRicerca : Boolean.FALSE);
	    
	    Locale locale = request.getLocale();
	    
	    reportParameters.put(PARAMETER_USER,           nomeUtenteVisualizzato ); 
	    reportParameters.put(JRParameter.REPORT_LOCALE,   locale);     //assolutamente necesario altrimenti non funzionano le formattazioni internazionali
	    reportParameters.put(PARAMETER_RECORDFOUND,    "" + lista.size() );
	    reportParameters.put(PARAMETER_LANGUAGE,       locale.getLanguage());
	    reportParameters.put(PARAMETER_COUNTRY,        locale.getLanguage().toUpperCase());
		reportParameters.put(PARAMETER_IS_UTENTE_SS,   isUtenteSS); // flag per visualizzare i campi per la SS
	    reportParameters.put(PARAMETER_IS_GUEST_USER,  !isUtenteSS);
	    
	    //------------------------------------------------------------------------------
         
        jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameters, new JRBeanCollectionDataSource(lista));      
    	out = JasperExportManager.exportReportToPdf(jasperPrint);        	
    	System.out.println("Bytes: " + out.length);
         
    	String nomeFiles = "RicercaPartiCorrelate.pdf";
	 	
    	if(response!=null) {
		 	response.setHeader("Content-Disposition", "attachment; filename=" + nomeFiles);
	    	response.setHeader("Pragma", "no-cache");
	    	response.setDateHeader("Expires", 0);
	    	response.setContentType("application/pdf");
	    	response.getOutputStream().write(out);
	    	response.getOutputStream().flush();
			response.getOutputStream().close();
			response.flushBuffer();
    	}
    	
		pc.setReportBlog( new byte[out.length] );
		for(int i=0;i<out.length;i++)
			pc.getReportBlog()[i] = out[i];

	}
	
	public void generazioneReportRicercaPDF1(HttpServletResponse response, List<ParteCorrelataView> pcList,Boolean esitoRicerca,
												String denominazioneInput,String codFiscaleInput, String partitaIvaInput, Date dataInput,
												String nomeUtenteVisualizzato,Boolean isUtenteSS ) throws Throwable {
			byte[] out = null;
			JasperPrint jasperPrint = null;
			
			String codFiscPIvaInput = "";
			
			if(codFiscaleInput != null || partitaIvaInput!= null){
				
				if(codFiscaleInput != null && partitaIvaInput != null){
					
					codFiscPIvaInput += codFiscaleInput + " / " + partitaIvaInput;
					
				}
				else if(codFiscaleInput != null){
					
					codFiscPIvaInput += codFiscaleInput;
				}
				else{
					codFiscPIvaInput += partitaIvaInput;
				}
			}
			
			URL  resources = ClassPathUtils.getResource(this.getClass(), "/jasper/");
			JasperCompileManager.compileReportToFile(resources.getPath() + SEARCH_PARTECORRELATA_JRXML_REPORT_FILE_NAME);
	    	URL resource_jasper = ClassPathUtils.getResource(this.getClass(), "/jasper/" + SEARCH_PARTECORRELATA_JASPER_REPORT_FILE_NAME);
	        
	        System.out.println("resource: " + resource_jasper);
	        
	        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(resource_jasper);
	        
	        //-----------------------------------------------------------------------
	        // s'impostano il set di parametri necessari alla generazione del report
	        //-----------------------------------------------------------------------
	        Map<String,Object> reportParameters = new HashMap<>();
		    reportParameters.put("reportTitle", SEARCH_PARTECORRELATA_JASPER_REPORT_TITLE);
		    reportParameters.put("recordFound", String.valueOf(pcList.size()) );
		    reportParameters.put("denominazioneInput", denominazioneInput );
		    reportParameters.put("codfiscPivaInput", codFiscPIvaInput );
		    reportParameters.put("dataInput", dataInput);
		    reportParameters.put("esito", (esitoRicerca!=null) ? esitoRicerca : Boolean.FALSE);
		    
		    Locale locale = new Locale("it", "IT"); // DEFAULT
		    URL resourceStylePath = ClassPathUtils.getResource(this.getClass(), "/jasper/");
		    
		    reportParameters.put(PARAMETER_SUBREPORTDIR,   "/jasper/"); //assolutamente necesario altrimenti non funzionano i sottoreport
		    reportParameters.put(PARAMETER_USER,           nomeUtenteVisualizzato );
			reportParameters.put(PARAMETER_STYLEDIR,       /*"/jasper/"*/resourceStylePath.getPath() /*"resources/jasper/"*/); //assolutamente necesario altrimenti non funzionano gli stili
		    reportParameters.put(PARAMETER_IMAGERDIR,      /*"/jasper/"*/resourceStylePath.getPath()); //assolutamente necesario altrimenti non funzionano gli stili
		    reportParameters.put(JRParameter.REPORT_LOCALE,   locale);     //assolutamente necesario altrimenti non funzionano le formattazioni internazionali
		    reportParameters.put(PARAMETER_RECORDFOUND,    "" + pcList.size() );
		    reportParameters.put(PARAMETER_LANGUAGE,       locale.getLanguage());
		    reportParameters.put(PARAMETER_COUNTRY,        locale.getLanguage().toUpperCase());
			reportParameters.put(PARAMETER_IS_UTENTE_SS,   isUtenteSS); // flag per visualizzare i campi per la SS
		    reportParameters.put(PARAMETER_IS_GUEST_USER,  !isUtenteSS);
		    
		    //------------------------------------------------------------------------------
	        
	        jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameters, new JRBeanCollectionDataSource(pcList));            		
	    	out = JasperExportManager.exportReportToPdf(jasperPrint);        	
	    	String nomeFiles = "RicercaPartiCorrelate.pdf";
		 	
		 	response.setHeader("Content-Disposition", "attachment; filename=" + nomeFiles);
	    	response.setHeader("Pragma", "no-cache");
	    	response.setDateHeader("Expires", 0);
	    	response.setContentType("application/pdf");
	    	response.getOutputStream().write(out);
	    	response.getOutputStream().flush();
			response.getOutputStream().close();
			response.flushBuffer();
		}

	/**
	 * Metodo preposto alla generazione del report XLS
	 * <p>
	 * @param tipoReport può assumere valore "T=Tutti";"A=Attive";"N=No Attive"
	 * 					 default Tutti con valore a null.
	 */
	@Override
	public byte[] generaReportXLS(String tipoEstrazione) throws Throwable {
		
		String nomeUtenteVisualizzato = "Utente...";
		byte[] out = null; JasperPrint jasperPrint = null;
		
		URL  resources = ClassPathUtils.getResource(this.getClass(), "/jasper/");
    	JasperCompileManager.compileReportToFile(resources.getPath() + SEARCH_PARTECORRELATA_JRXML_REPORT_XLS_FILE_NAME);
    	URL resource_jasper = ClassPathUtils.getResource(this.getClass(), "/jasper/" + SEARCH_PARTECORRELATA_JASPER_REPORT_XLS_FILE_NAME);
        
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(resource_jasper);
        List<ParteCorrelata> pcList = new ArrayList<ParteCorrelata>();
        
        // selezione tipo di estrazione.
        if (tipoEstrazione == ESTRAI_TUTTE)
        	pcList = parteCorrelataDao.leggi();
        else if (tipoEstrazione == ESTRAI_ATTIVE)
        	pcList = parteCorrelataDao.leggi(true);
        else if (tipoEstrazione == ESTRAI_NON_ATTIVE)
        	pcList = parteCorrelataDao.leggi(false);
        else
        	new Throwable("La selezione tipoEstrazione non è valida. "
        			+ "Utilizzare costanti : ESTRAI_TUTTE;ESTRAI_ATTIVE;ESTRAI_NON_ATTIVE.");
        
        //-----------------------------------------------------------------------
        // s'impostano il set di parametri necessari alla generazione del report
        //-----------------------------------------------------------------------
        Map<String,Object> reportParameters = new HashMap<>();
	    reportParameters.put("reportTitle", SEARCH_PARTECORRELATA_JASPER_REPORT_TITLE);
	    reportParameters.put("recordFound", String.valueOf(pcList.size()) );
	    
	    Locale locale = new Locale("it", "IT"); // DEFAULT
	    URL resourceStylePath = ClassPathUtils.getResource(this.getClass(), "/jasper/");
	    
	    reportParameters.put(PARAMETER_SUBREPORTDIR,   "/jasper/"); //assolutamente necesario altrimenti non funzionano i sottoreport
	    reportParameters.put(PARAMETER_USER,           nomeUtenteVisualizzato );
		reportParameters.put(PARAMETER_STYLEDIR,       /*"/jasper/"*/resourceStylePath.getPath() /*"resources/jasper/"*/); //assolutamente necesario altrimenti non funzionano gli stili
	    reportParameters.put(PARAMETER_IMAGERDIR,      /*"/jasper/"*/resourceStylePath.getPath()); //assolutamente necesario altrimenti non funzionano gli stili
	    reportParameters.put(JRParameter.REPORT_LOCALE,   locale);     //assolutamente necesario altrimenti non funzionano le formattazioni internazionali
	    reportParameters.put(PARAMETER_RECORDFOUND,    "" + pcList.size() );
	    reportParameters.put(PARAMETER_LANGUAGE,       locale.getLanguage());
	    reportParameters.put(PARAMETER_COUNTRY,        locale.getLanguage().toUpperCase());
	    
	    //------------------------------------------------------------------------------
        
        jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameters, new JRBeanCollectionDataSource(pcList));            		
    	
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	JRXlsExporter xlsExporter = new JRXlsExporter();

        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
        xlsReportConfiguration.setOnePagePerSheet(false);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        xlsReportConfiguration.setDetectCellType(false);
        xlsReportConfiguration.setWhitePageBackground(false);
        xlsExporter.setConfiguration(xlsReportConfiguration);
        xlsExporter.exportReport();
    	
        out = baos.toByteArray();
        return out;
	}
	
	/**
	 * Metodo preposto alla generazione del report XLS
	 *
	 * @param tipoReport può assumere valore "T=Tutti";"A=Attive";"N=No Attive"
	 * 					 default Tutti con valore a null.
	 */
	@Override
	public void generaReportExcel(String tipoEstrazione, HttpServletResponse response) throws Throwable {
		
		List<ParteCorrelata> lista = null;
		String filePostfix = "";
		if(tipoEstrazione.equals("T")) {
			lista = parteCorrelataDao.leggi();
			filePostfix="Tutte";
		}
		else if(tipoEstrazione.equals("A")) {
			lista = parteCorrelataDao.leggi(true);
			filePostfix="Attive";
		}
		else if(tipoEstrazione.equals("N")) {
			lista = parteCorrelataDao.leggi(false);
			filePostfix="NonAttive";
		}
		
		WriteExcell excell= new WriteExcell();
		
		excell.addHeader("Denominazione", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Cod.Fiscale", WriteExcell.TYPECELL_STRING);
		excell.addHeader("P. IVA", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Stato", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Tipo Correlazione", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Rapporto", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Consiglieri Sindaci", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Nazione", WriteExcell.TYPECELL_STRING);
		excell.addHeader("Data Creazione", WriteExcell.TYPECELL_DATE, excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader("Data Fine", WriteExcell.TYPECELL_DATE, excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		
		
		for(ParteCorrelata pc: lista){
			
			Vector<Object> row = new Vector<Object>();
			
			row.add(pc.getDenominazione()!=null?pc.getDenominazione():new String(""));
			
			row.add(pc.getCodFiscale()!=null?pc.getCodFiscale():new String(""));
			
			row.add(pc.getPartitaIva()!=null?pc.getPartitaIva():new String(""));
			
			if(pc.getDataCancellazione()==null)
				row.add("attivo");
			else
				row.add("");
			
			if(pc.getTipoCorrelazione()!=null)
				row.add(pc.getTipoCorrelazione().getDescrizione()!=null?pc.getTipoCorrelazione().getDescrizione():new String(""));
			else
				row.add("");
			
			row.add(pc.getRapporto()!=null?pc.getRapporto():new String(""));
			
			row.add(pc.getConsiglieriSindaci()!=null?pc.getConsiglieriSindaci():new String(""));
			
			if(pc.getNazione()!=null)
				row.add(pc.getNazione().getDescrizione()!=null?pc.getNazione().getDescrizione():new String(""));
			else
				row.add("");
			
			row.add(pc.getDataInserimento()!=null?pc.getDataInserimento():null);
			
			row.add(pc.getDataCancellazione()!=null?pc.getDataCancellazione():null);
			
			excell.addRowBody(row);
		}
		excell.setNomeFile("PartiCorrelate"+filePostfix+".xls");
	 	
		excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 45);
	 	excell.write(response);
	}
	
	/**
	 * Lettura parteCorrelata per tipoEstrazione.
	 * 
	 * @param id
	 * @throws Throwable
	 */
	@Override
	public List<ParteCorrelataView> leggi(String tipoEstrazione) throws Throwable {
		List<ParteCorrelata> lista = new ArrayList<>();
		// selezione tipo di estrazione.
        if (tipoEstrazione == ESTRAI_TUTTE)
        	lista = parteCorrelataDao.leggi();
        else if (tipoEstrazione == ESTRAI_ATTIVE)
        	lista = parteCorrelataDao.leggi(true);
        else if (tipoEstrazione == ESTRAI_NON_ATTIVE)
        	lista = parteCorrelataDao.leggi(false);
        else
        	new Throwable("La selezione tipoEstrazione non è valida. "
        			+ "Utilizzare costanti : ESTRAI_TUTTE;ESTRAI_ATTIVE;ESTRAI_NON_ATTIVE.");
        
		@SuppressWarnings("unchecked")
		List<ParteCorrelataView> listaRitorno = (List<ParteCorrelataView>) convertiVoInView(lista);		
		return listaRitorno;
	}	
}