package eng.la.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eng.la.persistence.DocumentaleDdsCryptDAO;
import eng.la.persistence.DocumentaleDdsDAO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.ArchivioProtocolloService;
import eng.la.business.UtenteService;
import eng.la.model.ArchivioProtocollo;
import eng.la.model.Documento;
import eng.la.model.Fascicolo;
import eng.la.model.Utente;
import eng.la.model.rest.CollaboratoreRest;
import eng.la.model.rest.CollaboratoriRest;
import eng.la.model.rest.ProtocolloRest;
import eng.la.model.rest.RicercaProtocolloRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.ProtocolloView;
import eng.la.model.view.StatoProtocolloView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.DocumentaleCryptDAO;
import eng.la.persistence.DocumentaleDAO;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("protocolloController")
@SessionAttributes("protocolloView")
public class ProtocolloController extends BaseController {
	private static final String MODEL_VIEW_NOME = "protocolloView";
	private static final String PAGINA_FORM_PATH = "protocollo/formProtocollo";  
	private static final String PAGINA_AZIONI_CERCA_PROTOCOLLO = "protocollo/azioniCercaProtocollo";  
	private static final String PAGINA_AZIONI_PROTOCOLLO = "protocollo/azioniProtocollo"; 
	
	private static final Logger logger = Logger.getLogger(ProtocolloController.class);
	
	
  
	
	@Autowired
	UtenteService utenteService;
	
	public void setUtenteService(UtenteService utenteService) {
		this.utenteService = utenteService;
	}	
	
	@Autowired
	AnagraficaStatiTipiService anagraficaStatiTipiService;
	
	public void setAnagraficaStatiTipiService(AnagraficaStatiTipiService anagraficaStatiTipiService) {
		this.anagraficaStatiTipiService = anagraficaStatiTipiService;
	}

	@Autowired
	private DocumentaleDdsCryptDAO documentaleDdsCryptDAO;

	@Autowired
	private DocumentaleDdsDAO documentaleDdsDAO;

	@Autowired
	private DocumentaleCryptDAO documentaleCryptDAO;
	
	public void setDocumentaleCryptDao(DocumentaleCryptDAO dao) {
		this.documentaleCryptDAO = dao;
	}
	
	@Autowired
	ArchivioProtocolloService protocolloService;
	
	public void setProtocolloService(ArchivioProtocolloService protocolloService) {
		this.protocolloService = protocolloService;
	}
	
	@Autowired
	private DocumentaleDAO documentaleDAO;

	public void setDocumentaleDao(DocumentaleDAO dao) {
		this.documentaleDAO = dao;
	}
	
	@RequestMapping(value = "/protocollo/lasciaSuArchivioProtocollo", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody RisultatoOperazioneRest lasciaSuArchivioProtocollo(@RequestParam("idProtocollo") long idProtocollo,
			Model model, Locale locale) throws Throwable {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			
			protocolloService.lasciaSuArchivioProtocollo(idProtocollo, locale.getLanguage().toUpperCase());
			
			
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Exception e) {
			logger.error(e); 
		}
		return risultato;
	}
	
	@RequestMapping(value = "/protocollo/assegnaProtocollo", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody RisultatoOperazioneRest assegnaProtocollo(@RequestParam("idProt") long idProtocollo, 
			@RequestParam("assegnatario") String assegnatario, 
			@RequestParam("commento") String commento,
			Model model, Locale locale) throws Throwable {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {

			protocolloService.assegnaProtocollo(idProtocollo, assegnatario, commento, locale);
			

			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Exception e) {
			logger.error(e); 
		}
		return risultato;
	}
		
	@RequestMapping(value = "/protocollo/spostaSuFascicolo", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody RisultatoOperazioneRest spostaSuFascicolo(@RequestParam("idProtocollo") long idProtocollo, 
			@RequestParam("idFascicolo") long idFascicolo,
			Model model, Locale locale) throws Throwable {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			
			protocolloService.spostaSuFascicolo(idProtocollo, idFascicolo, locale);
			
			
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Exception e) {
			logger.error(e); 
		}
		return risultato;
	}
	
	
	@RequestMapping(value = "/protocollo/gestioneProtocollo", method = RequestMethod.GET)
	public String gestioneProtocollo(HttpServletRequest request, Model model, Locale locale) {
		
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			ProtocolloView view = new ProtocolloView();
			
			String filter = request.getParameter("filter");
			
			view.setFiltro(filter);
			
			
			List<StatoProtocolloView> listaStatoProtocollo = anagraficaStatiTipiService.leggiStatiProtocollo(locale.getLanguage().toUpperCase());
			
			view.setListaStatoProtocollo(listaStatoProtocollo);
			
			ArrayList<UtenteView> utenti = (ArrayList<UtenteView>) utenteService.leggiUtentiGestoriFascicoli();
			
			ArrayList<Utente> utentiVo = new ArrayList<Utente>();
			
			
			for(UtenteView utente : utenti){
				utentiVo.add(utente.getVo());
			}
			
			Collections.sort(utentiVo, new Comparator<Utente>() {
			    @Override
			    public int compare(Utente o1, Utente o2) {
			        return o1.getNominativoUtil().compareTo(o2.getNominativoUtil());
			    }
			});
			
			view.setUtentiIN(utentiVo);
			
			view.setUtentiOUT(utentiVo);
			
			model.addAttribute(MODEL_VIEW_NOME, view);
			return PAGINA_FORM_PATH;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}
	
	@RequestMapping(value = "/protocollo/caricaAzioniProtocollo", method = RequestMethod.POST)
	public String caricaAzioniProtocollo(@RequestParam("idProtocollo") long idProtocollo, HttpServletRequest req,
			Locale locale) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(req);
        //removeCSRFToken(request);
		try {
			// LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI SULL'INCARICO
			req.setAttribute("idProtocollo", idProtocollo);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if( req.getParameter("onlyContent") != null  ){
			return PAGINA_AZIONI_PROTOCOLLO;
		}
		return PAGINA_AZIONI_CERCA_PROTOCOLLO;
	}
	
	@RequestMapping(value = "/protocollo/getUnitaAppartenenza", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RisultatoOperazioneRest getUnitaAppartenenza(HttpServletRequest request, Locale locale, @RequestParam("matricola") String matricola) {
		
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {
			
			logger.debug("getUnitaAppartenenza per  "+matricola);
			
			UtenteView utente = utenteService.leggiUtenteDaMatricola(matricola);
			
			String unita;
			
			if(utente.getVo().getCodiceUnitaUtil()!=null)
				unita=utente.getVo().getCodiceUnitaUtil().substring(5);
			else
				unita="Nessuna";
			
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, unita);
		} catch (Exception e) {
			logger.error(e); 
			
			risultato  = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, e.toString());
			
			return risultato;
		} catch (Throwable e) {
			logger.error(e); 
			
			risultato  = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, e.toString());
			
			return risultato;
		}
		return risultato;
	}


	@RequestMapping(value = "/protocollo/ricerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaProtocolloRest cercaProtocollo(HttpServletRequest request, Locale locale) {


		try {  

			int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA : NumberUtils.toInt(request.getParameter("limit"));
			String offset = request.getParameter("offset");
			int numeroPagina = offset == null || offset.equals("0") ? 1
					: (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
			String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
			String tipo = request.getParameter("type") == null ? "IN" : request.getParameter("type");
			String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
			if( request.getParameter("sort") == null ){
				tipoOrdinamento = "DESC";
			}
			 
			String numeroProtocollo = request.getParameter("numeroProtocollo") == null ? "" : URLDecoder.decode(request.getParameter("numeroProtocollo"),"UTF-8");
			String statoProtocolloCode = request.getParameter("statoProtocolloCode") == null ? "" : request.getParameter("statoProtocolloCode");
			String nomeFascicolo = request.getParameter("nomeFascicolo") == null ? "" : URLDecoder.decode( request.getParameter("nomeFascicolo"),"UTF-8");
			
			String dal = request.getParameter("dal") == null ? "" : URLDecoder.decode(request.getParameter("dal"),"UTF-8");
			String al = request.getParameter("al") == null ? "" :  URLDecoder.decode(request.getParameter("al"),"UTF-8");
			
			
			
			UtenteView utenteConnesso = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			 
			ListaPaginata<ProtocolloView> lista = (ListaPaginata<ProtocolloView>) protocolloService.cerca(numeroProtocollo,dal, al,nomeFascicolo,numElementiPerPagina, numeroPagina, ordinamento, tipoOrdinamento, tipo, utenteConnesso, statoProtocolloCode);
			RicercaProtocolloRest ricercaModelRest = new RicercaProtocolloRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<ProtocolloRest> rows = new ArrayList<ProtocolloRest>();
			for (ProtocolloView view : lista) {
				ProtocolloRest protocolloRest = new ProtocolloRest();
				protocolloRest.setId(view.getVo().getId());
				protocolloRest.setNumeroProtocollo(view.getVo().getNumProtocollo());
				protocolloRest.setDataCreazione(DateUtil.formattaData(view.getVo().getDataInserimento().getTime()));
				protocolloRest.setOggetto(view.getVo().getOggetto());
				
				protocolloRest.setStato("<a href='javascript:void(0)' >"
						+ view.getVo().getStatoProtocollo().getDescrizione() + "</a>");
				
				if(view.getVo().getUnitaAppart()!=null)
					protocolloRest.setUnita(view.getVo().getUnitaAppart());
				if(view.getVo().getFascicoloAssociato()!=null)
					protocolloRest.setNomeFascicolo(view.getVo().getFascicoloAssociato().getNome());
				
				if(tipo.equalsIgnoreCase("IN")){
					protocolloRest.setMittente(view.getVo().getMittente());
					protocolloRest.setDestinatario(view.getVo().getDestinatario());
				}
				else if(tipo.equalsIgnoreCase("OUT")){
					protocolloRest.setMittente(view.getVo().getMittente());
					protocolloRest.setDestinatario(view.getVo().getDestinatario());
				}
				//aggiunta condizione per Atti protocollati da assegnare MASSIMO CARUSO
				else{
					protocolloRest.setMittente(view.getVo().getMittente());
					protocolloRest.setDestinatario(view.getVo().getDestinatario());
				}
				//FINE aggiunta condizione per Atti protocollati da assegnare MASSIMO CARUSO
				

//				String assegnatario="-"; 
//				if(view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("APAP")
//						||view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("APAP1")
//						||view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("AAUT")
//						||view.getVo().getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("APAP2")){
//					UtenteView utenteV=utenteService.leggiAssegnatarioWfIncarico(view.getVo().getId());
//					assegnatario=utenteV == null ? "N.D." : utenteV.getVo().getNominativoUtil();
//				}
				
				protocolloRest.setAzioni("<p id='containerAzioniRigaProtocollo" + view.getVo().getId() + "' title='"+"' alt='"+"' ></p>");
				rows.add(protocolloRest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	
	@RequestMapping(value = "/protocollo/caricaDocumentoProtocollo", method = RequestMethod.POST)
	public @ResponseBody String caricaDocumentoProtocollo(HttpServletRequest request, @RequestParam("file") MultipartFile file,  @RequestParam("id") String id, Locale locale) {
		try {
			
			System.out.println(id);
			
			File fileTmp = File.createTempFile("documentoProtocollo", "___" + file.getOriginalFilename());
			FileUtils.writeByteArrayToFile(fileTmp, file.getBytes());
			DocumentoView documentoView = new DocumentoView();
			documentoView.setFile(fileTmp);
			documentoView.setNomeFile(file.getOriginalFilename());
			documentoView.setContentType(file.getContentType());
			documentoView.setNuovoDocumento(true); 
			protocolloService.salvaDocumentoProtocollo(documentoView, Long.valueOf(id).longValue(), locale);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return "OK";
	}

	

	 
	@RequestMapping(value = "/protocollo/download" , method = RequestMethod.GET)
    public String downloadDoc(@RequestParam("id") String id , HttpServletRequest request,HttpServletResponse response, Model model, Locale locale) throws Throwable {
    
    		ArchivioProtocollo protocollo = protocolloService.leggi(Long.parseLong(id)).getVo();
    		
    		Documento document = protocollo.getDocumento();
    		
    		String contentType = document.getContentType();
    		String name = document.getNomeFile();
    		
    		
    		
    		ByteArrayInputStream is = null;
    		OutputStream os = null;
    		
    		// engsecurity VA
    		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
    		htmlActionSupport.checkCSRFToken(request);
            //removeCSRFToken(request);
    		
    		try{
    		
    			byte[] bytes = null;
    			
    			if(protocollo.getFascicoloAssociato()!=null){
    				Fascicolo fascicolo = protocollo.getFascicoloAssociato();
    			
    				boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
    			
    				if(isPenale){
						//bytes = documentaleCryptDAO.leggiContenutoDocumento(document.getUuid());
						bytes = documentaleDdsCryptDAO.leggiContenutoDocumento(document.getUuid());
					}
    				else{
    					bytes = documentaleDdsDAO.leggiContenutoDocumento(document.getUuid());
						//bytes = documentaleDAO.leggiContenutoDocumento(document.getUuid());
					}
    			}
    			else{
					//bytes = documentaleDAO.leggiContenutoDocumento(document.getUuid());
					bytes = documentaleDdsDAO.leggiContenutoDocumento(document.getUuid());
				}

    			response.setContentLength(bytes.length);
    			response.setContentType(contentType);
    			response.setHeader("Content-Disposition", "attachment;filename=" + name);
    			response.setHeader("Cache-control", "");
    			is = new ByteArrayInputStream(bytes);
    			os = response.getOutputStream();
    			IOUtils.copy(is, os);
    			
    		} catch (Throwable e) {
    			e.printStackTrace();
    		} finally {
    			IOUtils.closeQuietly(is);
    			IOUtils.closeQuietly(os);
    		}

		return null;
    }
	
	@RequestMapping(value = "/protocollo/getCollaboratori", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody CollaboratoriRest getCollaboratori(HttpServletRequest request, Locale locale,  @RequestParam("matricola") String matricola) throws Throwable {
		
		List<UtenteView> collaboratori = utenteService.leggiCollaboratori(matricola);
		
		CollaboratoriRest coli = new CollaboratoriRest();
		
		List<CollaboratoreRest> colist = new ArrayList<CollaboratoreRest>();
		
		if(collaboratori!=null){
			for(UtenteView collaboratore : collaboratori){
				CollaboratoreRest cola = new CollaboratoreRest();
				cola.setId(collaboratore.getVo().getCodiceUnitaUtil().substring(5, collaboratore.getVo().getCodiceUnitaUtil().length()));
				cola.setName(collaboratore.getVo().getNominativoUtil());
				cola.setValue(collaboratore.getVo().getMatricolaUtil());
				colist.add(cola);
			}
		}
		
		coli.setCollaboratore(colist);
		
		return coli;
		
	}
	
	
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		List<ProfessionistaEsternoView> listaProfessionistaEsternoViews = null;
		IncaricoView incaricoView = (IncaricoView) view;
		
		incaricoView.setListaProfessionista(listaProfessionistaEsternoViews);
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
	

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	 	//binder.setValidator(new IncaricoValidator());

	 	//binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
