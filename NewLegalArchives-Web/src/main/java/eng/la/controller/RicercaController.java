package eng.la.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

//@@DDS import com.filenet.api.core.Document;
//@@DDS import com.filenet.api.core.Folder;

import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;

import eng.la.business.AttoService;
import eng.la.business.AutorizzazioneService;
import eng.la.business.FascicoloService;
import eng.la.business.IncaricoService;
import eng.la.business.ProformaService;
import eng.la.business.PropertyService;
import eng.la.business.RicercaService;
import eng.la.business.UtenteService;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
import eng.la.model.Proforma;
import eng.la.model.rest.idol.Attribute;
import eng.la.model.rest.idol.Filter;
import eng.la.model.rest.idol.IdolRest;
import eng.la.model.rest.idol.IdolRestResponse;
import eng.la.model.rest.idol.RowView;
import eng.la.model.rest.idol.SearchRequestPayload;
import eng.la.model.rest.idol.SimpleFilter;
import eng.la.model.rest.idol.Sorter;
import eng.la.model.view.AttoView;
import eng.la.model.view.AutorizzazioneView;
import eng.la.model.view.CollegioArbitraleView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.ProformaView;
import eng.la.model.view.RicercaView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.util.StringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("ricercaController")
@SessionAttributes("ricercaView")
public class RicercaController implements Costanti {
	
	private static final Logger logger = Logger.getLogger(RicercaController.class);
	
	private static final String MODEL_VIEW_NOME = "ricercaView";

	@Autowired
	private FascicoloService fascicoloService;
	
	@Autowired
	private AttoService attoService;
	
	@Autowired
	private IncaricoService incaricoService;
	
	@Autowired
	private ProformaService proformaService;
	
	@Autowired
	private AutorizzazioneService autorizzazioneService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private RicercaService ricercaService;

	
	@RequestMapping(value = "/ricerca", method = RequestMethod.GET)
	public String goToRicerca(
		HttpServletRequest request,
		Model model, 
		Locale locale)
	{
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);

		RicercaView ricercaView = new RicercaView();
		
		try 
		{
			model.addAttribute(MODEL_VIEW_NOME, ricercaView);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			return "ricerca/formRicerca";
		}
		return "ricerca/formRicerca";		
	}
	
	@RequestMapping(value = "/ricerca", method = RequestMethod.POST)
	public String goToRicercaPost(HttpServletRequest request,Model model,Locale locale)
	{
		logger.debug("@@DDS ______________________ RICERCA__INIZIO____________");
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		String testo = request.getParameter("testo");
		String oggetto = request.getParameter("oggetto");
		
		RicercaView ricercaView = new RicercaView();
		ricercaView.setOggetto(oggetto);
		
		try 
		{
			// T = Tutto
			// F = Fascicolo
			// A = Atto
			// I = Incarico
			// C = Proforma
			List<String> parole = estraiParole(testo);
			
			if(oggetto.equals("T")) {
				
				IdolRestResponse idolResponse = consumeIdol(parole);
				List<FascicoloView> retFascicoli=cercaFascicoli(parole);
				List<AttoView> retAtti=cercaAtti(parole);
				List<IncaricoView> retIncarichi=cercaIncarichi(testo,parole);
				List<CollegioArbitraleView> retCollegioArbitrale=cercaCollegioArbitrale(parole);
				List<ProformaView> retCosti=cercaCosti(testo,parole);
				
				long nFascicoli=0;
				long nAtti=0;
				long nIncarichi=0;
				long nCollegioArbitrale=0;
				long nCosti=0;
				long nfile=0;
				
				if(retFascicoli!=null)
					nFascicoli=(long)retFascicoli.size();
				if(retAtti!=null)
					nAtti=(long)retAtti.size();
				if(retIncarichi!=null)
					nIncarichi=(long)retIncarichi.size();
				if(retCollegioArbitrale!=null)
					nCollegioArbitrale=(long)retCollegioArbitrale.size();
				if(retCosti!=null)
					nCosti=(long)retCosti.size();
				
				ricercaView.setNumFascicoliTroviati(nFascicoli);
				ricercaView.setNumAttiTroviati(nAtti);
				ricercaView.setNumIncarichiTroviati(nIncarichi);
				ricercaView.setNumCollegioArbitraleTroviati(nCollegioArbitrale);
				ricercaView.setNumCostiTroviati(nCosti);
				
				String jarrFascicoli=convertFascicoliToJsonArray(retFascicoli);
				String jarrAtti=convertAttiToJsonArray(retAtti);
				String jarrIncarichi=convertIncarichiToJsonArray(retIncarichi);
				String jarrCollegioArbitrale=convertCollegioArbitraleToJsonArray(retCollegioArbitrale);
				String jarrCosti=convertCostiToJsonArray(retCosti);
				HashMap<String, Object> map = convertFileToJsonArray(idolResponse.getSearchResponsePayload()!=null?idolResponse.getSearchResponsePayload().getRowView():null, oggetto);
				String jarrFile = null;
				
				if(map!=null){
					jarrFile = (String) map.get("jsonArray");
					nfile = (int) map.get("length");
				}

				ricercaView.setNumFileTroviati(nfile);
				logger.debug("@@DDS ______________________ RICERCA_____file trovati_________" + nfile);
				ricercaView.setJsonArrayFascicolo(jarrFascicoli);
				ricercaView.setJsonArrayAtto(jarrAtti);
				ricercaView.setJsonArrayIncarico(jarrIncarichi);
				ricercaView.setJsonArrayCollegioArbitrale(jarrCollegioArbitrale);
				ricercaView.setJsonArrayCosti(jarrCosti);
				ricercaView.setJsonArrayFile(jarrFile);
			}
			else if(oggetto.equals("F")) {
				List<FascicoloView> retFascicoli=cercaFascicoli(parole);
				String jarrFascicoli=convertFascicoliToJsonArray(retFascicoli);
				ricercaView.setJsonArrayFascicolo(jarrFascicoli);
				long nFascicoli=0;
				if(retFascicoli!=null)
					nFascicoli=(long)retFascicoli.size();
				ricercaView.setNumFascicoliTroviati(nFascicoli);
				
				IdolRestResponse idolResponse = consumeIdol(parole);
				long nfile=0;
				HashMap<String, Object> map = convertFileToJsonArray(idolResponse.getSearchResponsePayload()!=null?idolResponse.getSearchResponsePayload().getRowView():null, oggetto);
				String jarrFile = null;
				
				if(map!=null){
					jarrFile = (String) map.get("jsonArray");
					nfile = (int) map.get("length");
				}
				
				ricercaView.setNumFileTroviati(nfile);
				ricercaView.setJsonArrayFile(jarrFile);
				
			}
			else if(oggetto.equals("A")) {
				List<AttoView> retAtti=cercaAtti(parole);
				String jarrAtti=convertAttiToJsonArray(retAtti);
				ricercaView.setJsonArrayAtto(jarrAtti);
				long nAtti=0;
				if(retAtti!=null)
					nAtti=(long)retAtti.size();
				ricercaView.setNumAttiTroviati(nAtti);
				
				IdolRestResponse idolResponse = consumeIdol(parole);
				long nfile=0;
				HashMap<String, Object> map = convertFileToJsonArray(idolResponse.getSearchResponsePayload()!=null?idolResponse.getSearchResponsePayload().getRowView():null, oggetto);
				String jarrFile = null;
				
				if(map!=null){
					jarrFile = (String) map.get("jsonArray");
					nfile = (int) map.get("length");
				}
				
				ricercaView.setNumFileTroviati(nfile);
				ricercaView.setJsonArrayFile(jarrFile);
			}
			else if(oggetto.equals("I")) {
				List<IncaricoView> retIncarichi=cercaIncarichi(testo,parole);
				String jarrIncarichi=convertIncarichiToJsonArray(retIncarichi);
				ricercaView.setJsonArrayIncarico(jarrIncarichi);
				long nIncarichi=0;
				if(retIncarichi!=null)
					nIncarichi=(long)retIncarichi.size();
				ricercaView.setNumIncarichiTroviati(nIncarichi);
				
				List<CollegioArbitraleView> retCollegioArbitrale=cercaCollegioArbitrale(parole);
				String jarrCollegioArbitrale=convertCollegioArbitraleToJsonArray(retCollegioArbitrale);
				ricercaView.setJsonArrayCollegioArbitrale(jarrCollegioArbitrale);
				long nCollegioArbitrale=0;
				if(retCollegioArbitrale!=null)
					nCollegioArbitrale=(long)retCollegioArbitrale.size();
				ricercaView.setNumCollegioArbitraleTroviati(nCollegioArbitrale);
				
				IdolRestResponse idolResponse = consumeIdol(parole);
				long nfile=0;
				HashMap<String, Object> map = convertFileToJsonArray(idolResponse.getSearchResponsePayload()!=null?idolResponse.getSearchResponsePayload().getRowView():null, oggetto);
				String jarrFile = null;
				
				if(map!=null){
					jarrFile = (String) map.get("jsonArray");
					nfile = (int) map.get("length");
				}
				
				ricercaView.setNumFileTroviati(nfile);
				ricercaView.setJsonArrayFile(jarrFile);
			}
			else if(oggetto.equals("C")) {
				List<ProformaView> retCosti=cercaCosti(testo,parole);
				String jarrCosti=convertCostiToJsonArray(retCosti);
				ricercaView.setJsonArrayCosti(jarrCosti);
				long nCosti=0;
				if(retCosti!=null)
					nCosti=(long)retCosti.size();
				ricercaView.setNumCostiTroviati(nCosti);
				
				IdolRestResponse idolResponse = consumeIdol(parole);
				long nfile=0;
				HashMap<String, Object> map = convertFileToJsonArray(idolResponse.getSearchResponsePayload()!=null?idolResponse.getSearchResponsePayload().getRowView():null, oggetto);
				String jarrFile = null;
				
				if(map!=null){
					jarrFile = (String) map.get("jsonArray");
					nfile = (int) map.get("length");
				}
				
				ricercaView.setNumFileTroviati(nfile);
				ricercaView.setJsonArrayFile(jarrFile);
			}
			
			model.addAttribute(MODEL_VIEW_NOME, ricercaView);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			return "ricerca/formRicerca";
		}
		return "ricerca/formRicerca";		
	}
	
	private String convertFascicoliToJsonArray(List<FascicoloView> lista)  throws Throwable {
		JSONArray jsonArray = new JSONArray();
			if( lista != null && lista.size() > 0 ){
				for( FascicoloView view : lista ){
					JSONObject jsonObject = new JSONObject();
					
					Fascicolo vo = view.getVo();
					jsonObject.put("id", vo.getId() );
					
					String nome = vo.getNome();
					nome=StringEscapeUtils.escapeJson(nome);
					nome=replaceApostrofo(nome);
					jsonObject.put("nome", nome);
					
					if(vo.getStatoFascicolo()!=null)
						jsonObject.put("stato", vo.getStatoFascicolo().getDescrizione() );
					else
						jsonObject.put("stato", "" );
					
					if(vo.getDataCreazione()!=null) {
						Date data = vo.getDataCreazione();
						String strAnno = new SimpleDateFormat("yyyy").format(data);
						jsonObject.put("anno", strAnno );
					}
					else
						jsonObject.put("anno", "" );
					
					
					//owner
					String userIdOwnerFascicolo=vo.getLegaleInterno();
					UtenteView utenteView2=utenteService.leggiUtenteDaUserId(userIdOwnerFascicolo);
					String nominativoUtil=utenteView2.getVo().getNominativoUtil();
					String nominativoUtil_json=StringUtil.escapeJson(nominativoUtil);
					jsonObject.put("owner", nominativoUtil_json );
					
					/*
					List<AutorizzazioneView> listaAut=autorizzazioneService.leggiAutorizzazioni(vo.getId(), CostantiDAO.NOME_CLASSE_FASCICOLO);
					String nominativoUtil="";
					for(int x=0; x<listaAut.size(); x++) {
						AutorizzazioneView av=listaAut.get(x);
						if(av.getVo().getTipoAutorizzazione().getCodGruppoLingua().equals(CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA)) {
							String matricolaUtil=av.getVo().getUtente().getMatricolaUtil();
							UtenteView utenteView= utenteService.leggiUtenteDaMatricola(matricolaUtil);
							nominativoUtil=utenteView.getVo().getNominativoUtil();
							break;
						}
					}
					jsonObject.put("owner", nominativoUtil );
					*/
					
					
					jsonArray.put(jsonObject); 
			}
		}
		
		if (jsonArray.length() > 0) {
			return jsonArray.toString();
		} 
		else
			return null;
	}

	private String convertAttiToJsonArray(List<AttoView> lista)  throws Throwable {
		JSONArray jsonArray = new JSONArray();
			if( lista != null && lista.size() > 0 ){
				for( AttoView view : lista ){
					JSONObject jsonObject = new JSONObject();
					
					jsonObject.put("id", view.getVo().getId() );
					
					String numeroProtocollo = view.getVo().getNumeroProtocollo();
					numeroProtocollo=StringEscapeUtils.escapeJson(numeroProtocollo);
					numeroProtocollo=replaceApostrofo(numeroProtocollo);
					
					jsonObject.put("numeroProtocollo", numeroProtocollo);
					jsonArray.put(jsonObject); 
				}
			}
			
		
		if (jsonArray.length() > 0) {
			return jsonArray.toString();
		} 
		else
			return null;
	}
	
	private String convertIncarichiToJsonArray(List<IncaricoView> lista)  throws Throwable {
		JSONArray jsonArray = new JSONArray();
		
			
			if( lista != null && lista.size() > 0 ){
				for( IncaricoView view : lista ){
					JSONObject jsonObject = new JSONObject();
					
					Incarico vo = view.getVo();
					jsonObject.put("id", vo.getId() );
					
					String nome = view.getVo().getNomeIncarico();
					nome=StringEscapeUtils.escapeJson(nome);
					nome=replaceApostrofo(nome);
					
					jsonObject.put("nome", nome);
					
					jsonArray.put(jsonObject); 
				}
			}
		
		
		if (jsonArray.length() > 0) {
			return jsonArray.toString();
		} 
		else
			return null;
	}
	
	private String convertCostiToJsonArray(List<ProformaView> lista) throws Throwable {
		JSONArray jsonArray = new JSONArray();
		
			if( lista != null && lista.size() > 0 ){
				for( ProformaView view : lista ){
					JSONObject jsonObject = new JSONObject();
					
					Proforma vo = view.getVo();
					jsonObject.put("id", vo.getId() );
					
					String nome = view.getVo().getNomeProforma();
					nome=StringEscapeUtils.escapeJson(nome);
					nome=replaceApostrofo(nome);
					
					jsonObject.put("nome", nome );
					
					jsonArray.put(jsonObject); 
				}
			}
			
		
		if (jsonArray.length() > 0) {
			return jsonArray.toString();
		} 
		else
			return null;
	}
	
	private String convertCollegioArbitraleToJsonArray(List<CollegioArbitraleView> lista) throws Throwable {
		JSONArray jsonArray = new JSONArray();
		
			
			if( lista != null && lista.size() > 0 ){
				for( CollegioArbitraleView view : lista ){
					JSONObject jsonObject = new JSONObject();
					
					Incarico vo = view.getVo();
					jsonObject.put("id", vo.getId() );
					
					String nome = view.getVo().getNomeIncarico();
					nome=StringEscapeUtils.escapeJson(nome);
					nome=replaceApostrofo(nome);
					
					jsonObject.put("nome", nome );
					
					jsonArray.put(jsonObject); 
				}
			}
		
		
		if (jsonArray.length() > 0) {
			return jsonArray.toString();
		} 
		else
			return null;
	}

	private HashMap<String, Object> convertFileToJsonArray(List<RowView> lista, String oggetto) throws Throwable {
		JSONArray jsonArray = new JSONArray();
			if( lista != null && lista.size() > 0 ){
				for(RowView view : lista ){
					boolean crypt = false;
					String filenetClass = null;
					String filenetFolder = null;
					JSONObject jsonObject = new JSONObject();
					
					List<Attribute> attribute = view.getAttribute();
					for (Attribute att : attribute) {
						if(att.getKey().equalsIgnoreCase("ID")){
							String uuid = att.getValue();
							uuid = uuid.replaceAll("\\{", "").replaceAll("\\}", "");
							jsonObject.put("id", uuid);
						}else if(att.getKey().equalsIgnoreCase("NAME")){
							String nome = att.getValue();
							nome=StringEscapeUtils.escapeJson(nome);
							nome=replaceApostrofo(nome);
							jsonObject.put("nome", nome);
						}else if(att.getKey().equalsIgnoreCase("FILENETOBJECTSTORE")){
							String os = att.getValue();
							if(os.toUpperCase().indexOf("CRYPT") >-1){
								crypt = true;
								jsonObject.put("isPenale", "1");
							} else {
								crypt = false;
								jsonObject.put("isPenale", "0");
							}
						}else if(att.getKey().equalsIgnoreCase("FILENETCLASS")){
							filenetClass = att.getValue();
						}else if(att.getKey().equalsIgnoreCase("FILENETP8FOLDERNAME")){
							filenetFolder = att.getValue();
						}  
					}
					
					if(jsonObject.has("id") && jsonObject.has("isPenale") && jsonObject.has("nome") &&
							filenetClass!= null && filenetFolder!=null){
						boolean autorizzato = false;
						String cartella = "";
						HashMap<String, Object> map = new HashMap<String, Object>();
						
						String uuid = jsonObject.getString("id");

						if(!crypt){
							map = checkAuth(uuid, filenetFolder, filenetClass, oggetto);
						} else {
							map = checkAuthCrypt(uuid, filenetFolder, filenetClass, oggetto);
						}
						
						if(map!=null){
							Boolean auth = (Boolean) map.get("autorizzato");  
							autorizzato = auth.booleanValue();
							cartella = (String) map.get("cartella");
						}
						
						if(autorizzato){
							jsonObject.put("cartella", cartella);
							jsonArray.put(jsonObject); 
						}
						
					} else {
						logger.debug("Non sono presenti tutte le informazioni quindi non lo inserisco nei risultati!");
					}
				}
				
			}
			
		
		if (jsonArray.length() > 0) {
			
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("jsonArray",jsonArray.toString());
			m.put("length",jsonArray.length());
			
			return m;
		} 
		else
			return null;
	}
	
	
	private List<FascicoloView> cercaFascicoli(List<String> parole)  throws Throwable {
		for(int i=0; i<parole.size();i++) {
			String p=parole.get(i);
			if(p.equals("-"))
				parole.remove(i);
		}
		List<FascicoloView> lista = null;
		lista = (List<FascicoloView>) fascicoloService.cerca(parole);
		if (lista != null && lista.size() > 0) {
			return lista;
		} 
		else
			return null;
	}
	
	private List<AttoView> cercaAtti(List<String> parole)  throws Throwable {
		List<AttoView> lista = null;
		
		lista = attoService.cerca(parole);
		
		if (lista != null && lista.size() > 0) {
			return lista;
		} 
		else
			return null;
	}
	
	private List<IncaricoView> cercaIncarichi(String testoCompleto,List<String> parole)  throws Throwable {
		//se il testo contiene un "-" allora la ricerca � puntuale per nome incarico
		boolean trovato=false;
		for(String p: parole) {
			if(p.equals("-"))
				trovato=true;
		}
		if(trovato) {
			parole = new ArrayList<String>();
			parole.add(testoCompleto);
		}
		
		List<IncaricoView> lista = null;
		lista = incaricoService.cerca(parole);
		if (lista != null && lista.size() > 0) {
			return lista;
		} 
		else
			return null;
	}
	
	private List<CollegioArbitraleView> cercaCollegioArbitrale(List<String> parole)  throws Throwable {
		List<CollegioArbitraleView> lista = null;
			
			lista = incaricoService.cercaArbitrale(parole);
		if (lista != null && lista.size() > 0) {
			return lista;
		} 
		else
			return null;
	}
	
	private List<ProformaView> cercaCosti(String testoCompleto,List<String> parole) throws Throwable {
		//se il testo contiene un "-" allora la ricerca � puntuale per nome incarico
		boolean trovato=false;
		for(String p: parole) {
			if(p.equals("-"))
				trovato=true;
		}
		if(trovato) {
			parole = new ArrayList<String>();
			parole.add(testoCompleto);
		}
		
		List<ProformaView> lista = null;
		
		lista = (List<ProformaView>) proformaService.cerca(parole); 
		
		if (lista != null && lista.size() > 0) {
			return lista;
		} 
		else
			return null;
	}
	
	private List<String> estraiParole(String testo) {
		List<String> toks = new ArrayList<String>();;
		if(testo!=null) {
			StringTokenizer tokenizer = new StringTokenizer(testo, " ");
			while( tokenizer.hasMoreTokens() ){
				String token = tokenizer.nextToken();
				toks.add(token);
			}
		}
		return toks;
	}
	
	private String replaceApostrofo(String str) {
		StringBuffer nuovo= new StringBuffer();
		for(int i=0; i<str.length(); i++) {
			if( str.charAt(i) == '\'' ) 
				nuovo.append("&#39;");
			else
				nuovo.append(str.charAt(i));
		}
			
		return nuovo.toString();
	}
	
	private IdolRest getJson(List<String> parole) throws Throwable {
		
		//Filtro sulle parole inserite
		SimpleFilter simpleFilter = new SimpleFilter();
		simpleFilter.setKey("text");
		simpleFilter.setOperator(null);
		simpleFilter.setValue(parole);
		
		Filter filter = new Filter();
		filter.setComplexFilter(null);
		filter.setSimpleFilter(simpleFilter);

		//Filtro sul database
		SimpleFilter simpleFilter2 = new SimpleFilter();
		simpleFilter2.setKey("databasematch");
		simpleFilter2.setOperator("IN");
		simpleFilter2.setValue(Arrays.asList("fn_LEG_ARC", "fn_LEG_ARC_CRYPT"));
		
		Filter filter2 = new Filter();
		filter2.setComplexFilter(null);
		filter2.setSimpleFilter(simpleFilter2);
		
		//Filtro sull'object store - LEG_ARC e LEG_ARC_CRYPT 
		SimpleFilter simpleFilter3 = new SimpleFilter();
		simpleFilter3.setKey("FILENETOBJECTSTORE");
		simpleFilter3.setOperator("STRING");
		simpleFilter3.setValue(Arrays.asList("LEG_ARC"));
		
		Filter filter3 = new Filter();
		filter3.setComplexFilter(null);
		filter3.setSimpleFilter(simpleFilter3);
		
		List<Filter> list = new ArrayList<Filter>();
		list.add(filter);
		list.add(filter2);
		list.add(filter3);
		
		SearchRequestPayload searchRequestPayload = new SearchRequestPayload();
		searchRequestPayload.setFilter(list);
		searchRequestPayload.setPaging(null);
		
		Sorter sorter = new Sorter();
		sorter.setKeySort(Arrays.asList("NAME:alphabetical"));
		searchRequestPayload.setSorter(sorter);
		
		IdolRest rest = new IdolRest();
		rest.setHeader(null);
		rest.setSearchRequestPayload(searchRequestPayload);
		
		return rest;
	}
	
	private IdolRestResponse consumeIdol(List<String> parole) {
		
		try {
			
			String uri = propertyService.getValue("ENDPOINT_IDOL");
			logger.debug("ENDPOINT_IDOL: "+uri);
			String user = propertyService.getValue("USER_IDOL");
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("OAM_REMOTE_USER", user);
			
			HttpEntity<IdolRest> entity = new HttpEntity<IdolRest>(getJson(parole), headers);
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<IdolRestResponse> result = restTemplate.exchange(uri, HttpMethod.POST, entity, IdolRestResponse.class);
			
			return result.getBody();
			
		} catch(Throwable e){
			logger.error("consumeIdol ", e);
			return null;
		}
	}
	
	private HashMap<String, Object> checkAuth(String uuid, String filenetFolder, String filenetClass, String oggetto) {
		boolean autorizzato = false;
		logger.debug("documento libero - oggetto: "+oggetto);
		logger.debug("documento libero - filenetClass: "+filenetClass);
		logger.debug("documento libero - filenetFolder: "+filenetFolder);
		String cartella = "";
		HashMap<String, Object> m = new HashMap<String, Object>();
		
		try {
				
            if(filenetClass.equalsIgnoreCase(FileNetClassNames.PROFESSIONISTA_ESTERNO_DOCUMENT)){
            	if(oggetto.equalsIgnoreCase("T")){
            		autorizzato = true;
            		cartella = CostantiDAO.PROFESSIONISTA_ESTERNO.toUpperCase();
            	}
            } else {
            	
            	Folder folder = ricercaService.leggiCartella(filenetFolder);

				//@@DDS Integer folderLaid = FileNetUtil.getLaid(folder);

				Integer folderLaid = Integer.parseInt((String) folder.getCutsomAttributesAsMap().get("LAid"));
				//@@DDS String folderClassName = folder.getClassName();
				String folderClassName = folder.getFolderClass();
            	
            	logger.debug("@@DDS  _______________________ folderClassName: "+folderClassName);
				logger.debug("@@DDS  _______________________  folderLaid: "+folderLaid);
            	
            	if(folderClassName.equalsIgnoreCase(FileNetClassNames.FASCICOLO_FOLDER) && 
            			(oggetto.equalsIgnoreCase("T") || oggetto.equalsIgnoreCase("F"))){
            		AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(folderLaid.longValue(), CostantiDAO.FASCICOLO.toUpperCase());
            		
            		if(auth.getVo()!=null){
            			autorizzato = true;
            			cartella = CostantiDAO.FASCICOLO.toUpperCase();
            		}
            		
            	} else if(folderClassName.equalsIgnoreCase(FileNetClassNames.PROFORMA_FOLDER) && 
            			(oggetto.equalsIgnoreCase("T") || oggetto.equalsIgnoreCase("C"))){
            		AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(folderLaid.longValue(), CostantiDAO.PROFORMA.toUpperCase());
            		
            		if(auth.getVo()!=null){
            			autorizzato = true;
            			cartella = CostantiDAO.PROFORMA.toUpperCase();
            		}
            	} else if(folderClassName.equalsIgnoreCase(FileNetClassNames.INCARICO_FOLDER) && 
            			(oggetto.equalsIgnoreCase("T") || oggetto.equalsIgnoreCase("I"))){
            		AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(folderLaid.longValue(), CostantiDAO.INCARICO.toUpperCase());
            		
            		if(auth.getVo()!=null){
            			autorizzato = true;
            			cartella = CostantiDAO.INCARICO.toUpperCase();
            		}
            	} else if(folderClassName.equalsIgnoreCase(FileNetClassNames.PROGETTO_FOLDER) && 
            			(oggetto.equalsIgnoreCase("T"))){
            		AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(folderLaid.longValue(), CostantiDAO.PROGETTO.toUpperCase());
            		
            		if(auth.getVo()!=null){
            			autorizzato = true;
            			cartella = CostantiDAO.PROGETTO.toUpperCase();
            		}
            	}else if(folderClassName.equalsIgnoreCase(FileNetClassNames.SCHEDA_FONDO_RISCHI_FOLDER) && 
            			(oggetto.equalsIgnoreCase("T") || oggetto.equalsIgnoreCase("I"))){
            		AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(folderLaid.longValue(), CostantiDAO.SCHEDA_FONDO_RISCHI.toUpperCase());
            		
            		if(auth.getVo()!=null){
            			autorizzato = true;
            			cartella = CostantiDAO.SCHEDA_FONDO_RISCHI.toUpperCase();
            		}
            	} 
            }
	        
			m.put("autorizzato", Boolean.valueOf(autorizzato));
			m.put("cartella", cartella);
            
		} catch(Throwable e)	{
			logger.error("checkAuth: ", e);
		}
		
		return m;
	}

	private HashMap<String, Object> checkAuthCrypt(String uuid, String filenetFolder, String filenetClass, String oggetto) {

		boolean autorizzato = false;
		logger.debug("documento criptato - oggetto: "+oggetto);
		logger.debug("documento criptato - filenetClass: "+filenetClass);
		logger.debug("documento criptato - filenetFolder: "+filenetFolder);
		String cartella = "";
		HashMap<String, Object> m = new HashMap<String, Object>();
		
		try {

			if(filenetClass.equalsIgnoreCase(FileNetClassNames.ATTO_DOCUMENT)){
				
				if(oggetto.equalsIgnoreCase("T") || oggetto.equalsIgnoreCase("A")){
					
					Document leggiDocumentoCryptUUID = ricercaService.leggiDocumentoCryptUUID(uuid);
					
					//@@DDSS Integer laid = FileNetUtil.getLaid(leggiDocumentoCryptUUID);
					Integer laid = (Integer) leggiDocumentoCryptUUID.getCutsomAttributesAsMap().get("LAid"); ////@@DDSS
					logger.debug("@@DDS ___________________ document laid: "+laid);
					
					if(laid!=null){
						AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(laid.longValue(), CostantiDAO.ATTO.toUpperCase());
						
						if(auth.getVo()!=null){
							autorizzato = true;
							cartella = CostantiDAO.ATTO.toUpperCase();
						}
					} else {
						Folder folder = ricercaService.leggiCartellaCrypt(filenetFolder);

						//@@DDS Integer folderLaid = FileNetUtil.getLaid(folder);
						Integer folderLaid = (Integer) folder.getCutsomAttributesAsMap().get("LAid"); //@@DDS
						logger.debug("@@DDS ___________________ folderLaid: "+folderLaid);
						//@@DDS String folderClassName = folder.getClassName();
						String folderClassName = folder.getFolderClass();
						
						logger.debug("folderClassName: "+folderClassName);
						logger.debug("folderLaid: "+folderLaid);
						
						AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(folderLaid.longValue(), CostantiDAO.ATTO.toUpperCase());
						
						if(auth.getVo()!=null){
							autorizzato = true;
							cartella = CostantiDAO.ATTO.toUpperCase();
						}
						
					}
				}
				
			} else {
				
				Folder folder = ricercaService.leggiCartellaCrypt(filenetFolder);
				
				//@@DDS Integer folderLaid = FileNetUtil.getLaid(folder);
				Integer folderLaid = (Integer) folder.getCutsomAttributesAsMap().get("LAid"); //@@DDS
				logger.debug("@@DDS ___________________ folderLaid: "+folderLaid);
				//@@DDS String folderClassName = folder.getClassName();
				String folderClassName = folder.getFolderClass();
				
				if(folderClassName.equalsIgnoreCase(FileNetClassNames.FASCICOLO_FOLDER) && 
	        			(oggetto.equalsIgnoreCase("T") || oggetto.equalsIgnoreCase("F"))){
					AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(folderLaid.longValue(), CostantiDAO.FASCICOLO.toUpperCase());
					
					if(auth.getVo()!=null){
						autorizzato = true;
						cartella = CostantiDAO.FASCICOLO.toUpperCase();
					}
					
				} else if(folderClassName.equalsIgnoreCase(FileNetClassNames.PROFORMA_FOLDER) && 
	        			(oggetto.equalsIgnoreCase("T") || oggetto.equalsIgnoreCase("C"))){
					AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(folderLaid.longValue(), CostantiDAO.PROFORMA.toUpperCase());
					
					if(auth.getVo()!=null){
						autorizzato = true;
						cartella = CostantiDAO.PROFORMA.toUpperCase();
					}
				} else if(folderClassName.equalsIgnoreCase(FileNetClassNames.INCARICO_FOLDER) && 
	        			(oggetto.equalsIgnoreCase("T") || oggetto.equalsIgnoreCase("I"))){
					AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(folderLaid.longValue(), CostantiDAO.INCARICO.toUpperCase());
					
					if(auth.getVo()!=null){
						autorizzato = true;
						cartella = CostantiDAO.INCARICO.toUpperCase();
					}
				} else if(folderClassName.equalsIgnoreCase(FileNetClassNames.ATTO_FOLDER) && 
	        			(oggetto.equalsIgnoreCase("T") || oggetto.equalsIgnoreCase("A"))){
					AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(folderLaid.longValue(), CostantiDAO.ATTO.toUpperCase());
					
					if(auth.getVo()!=null){
						autorizzato = true;
						cartella = CostantiDAO.ATTO.toUpperCase();
					}
				} else if(folderClassName.equalsIgnoreCase(FileNetClassNames.PROGETTO_FOLDER) && 
	        			(oggetto.equalsIgnoreCase("T"))){
            		AutorizzazioneView auth = autorizzazioneService.leggiAutorizzazioneUtenteCorrente(folderLaid.longValue(), CostantiDAO.PROGETTO.toUpperCase());
            		
            		if(auth.getVo()!=null){
            			autorizzato = true;
            			cartella = CostantiDAO.PROGETTO.toUpperCase();
            		}
            	}
			}
			
			m.put("autorizzato", Boolean.valueOf(autorizzato));
			m.put("cartella", cartella);
			
			
		} catch(Throwable e)	{
			logger.error("checkAuthCrypt: ", e);
		}
		
		return m;
	}
	
	
}
