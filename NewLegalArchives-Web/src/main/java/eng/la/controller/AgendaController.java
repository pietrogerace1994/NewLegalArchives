package eng.la.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.AgendaNotificheDisplayedService;
import eng.la.business.AutorizzazioneService;
import eng.la.business.EmailService;
import eng.la.business.EventoService;
import eng.la.business.FascicoloService;
import eng.la.business.ScadenzaService;
import eng.la.business.TipoScadenzaService;
import eng.la.business.UtenteService;
import eng.la.business.websocket.WebSocketPublisher;
import eng.la.model.Autorizzazione;
import eng.la.model.Evento;
import eng.la.model.Fascicolo;
import eng.la.model.NotificheAgendaDisplayed;
import eng.la.model.Scadenza;
import eng.la.model.TipoScadenza;
import eng.la.model.rest.AgendaRest;
import eng.la.model.rest.AgendaWebsocketRest;
import eng.la.model.rest.Event;
import eng.la.model.view.AgendaNotificheDisplayedView;
import eng.la.model.view.AutorizzazioneView;
import eng.la.model.view.EventoView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.ScadenzaView;
import eng.la.model.view.TipoScadenzaView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.DateUtil;
import eng.la.util.DateUtil2;
import eng.la.util.SpringUtil;
import eng.la.util.StringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("agendaController")
@SessionAttributes("agendaView")
public class AgendaController {

	@Autowired
	private FascicoloService fascicoloService;

	@Autowired
	private ScadenzaService scadenzaService;

	@Autowired
	private EventoService eventoService;

	@Autowired
	private TipoScadenzaService tipoScadenzaService;

	@Autowired
	private AutorizzazioneService autorizzazioneService;

	@Autowired
	private AgendaNotificheDisplayedService agendaNotificheDisplayedService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private EmailService emailService;

	private static final Logger logger = Logger.getLogger(AgendaController.class);

	@RequestMapping(value = "/agenda/loadUltimeNotifiche", method = RequestMethod.GET, produces = "application/json")
	public 
	@ResponseBody String loadUltimeNotifiche(HttpServletRequest request, Locale locale) {
		String ret = "";
		
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);

		try 
		{
			String tipologia=request.getParameter("type");
			cancellaNotificheDisplayedDelPassatoDalDB();

			// data inizio e data fine
			Date dataOggi = new Date();
			Date dataFine=calcolaDataFineDiFinestra(dataOggi);

			// utente connesso
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			String matricolaUtenteConnesso=utenteView.getVo().getMatricolaUtil();

			//utente is amministratore
			boolean utenteIsAdmin=utenteView.isAmministratore(); 

			List<EventoView> eventi_lettiDalDB = (List<EventoView>) eventoService.leggi(dataOggi,dataFine);
			List<ScadenzaView> scadenze_lettiDalDB = (List<ScadenzaView>) scadenzaService.leggi(dataOggi,dataFine);

			// filtro eventi e scadenze per autorizzazioni
			List<EventoView> eventi = filtroEventiPerAutorizzazioniLettura(eventi_lettiDalDB,utenteIsAdmin,matricolaUtenteConnesso, tipologia);
			List<ScadenzaView> scadenze = filtroScadenzePerAutorizzazioniLettura(scadenze_lettiDalDB,utenteIsAdmin,matricolaUtenteConnesso, tipologia);

			// displayed
			List<AgendaNotificheDisplayedView> listaDisplayedEventi=agendaNotificheDisplayedService.leggi(matricolaUtenteConnesso,"E");
			List<AgendaNotificheDisplayedView> listaDisplayedScadenze=agendaNotificheDisplayedService.leggi(matricolaUtenteConnesso,"S");

			// ---cerco scadenze---
			List<ScadenzaView> arrScadenzeTrovati=new ArrayList<ScadenzaView>(0);
			for(AgendaNotificheDisplayedView agendaNotificheDisplayedView : listaDisplayedScadenze) {

				//System.out.println("id scadenza displayed="+agendaNotificheDisplayedView.getVo().getIdScadenza());

				for(ScadenzaView scadenza: scadenze) {
					//System.out.println("id scadenza="+scadenza.getVo().getId());

					if(agendaNotificheDisplayedView.getVo().getTipo().equals("S") && agendaNotificheDisplayedView.getVo().getIdScadenza() == scadenza.getVo().getId() ) {
						arrScadenzeTrovati.add(scadenza);

						//System.out.println("id scadenza trovato="+scadenza.getVo().getId());
					}
				}

			}

			// ---cerco eventi---
			List<EventoView> arrEventiTrovati=new ArrayList<EventoView>(0);
			for(AgendaNotificheDisplayedView agendaNotificheDisplayedView : listaDisplayedEventi) {

				//System.out.println("id evento displayed="+agendaNotificheDisplayedView.getVo().getIdEvento());
				for(EventoView evento: eventi) {
					//System.out.println("id evento="+evento.getVo().getId());

					if(agendaNotificheDisplayedView.getVo().getTipo().equals("E") && agendaNotificheDisplayedView.getVo().getIdEvento() == evento.getVo().getId() ) {
						arrEventiTrovati.add(evento);

						//System.out.println("id evento trovato="+evento.getVo().getId());
					}
				}

			}

			List<JSONObject> jsonList = new ArrayList<JSONObject>(0);

			//evento to json
			for( EventoView e : eventi ){

				JSONObject jsonObject = new JSONObject();

				Evento vo = e.getVo();

				Long idFascicolo=vo.getFascicolo()!=null?vo.getFascicolo().getId():null;

				String fascicoloDescrizione=vo.getFascicolo()!=null?vo.getFascicolo().getNome():null;
				fascicoloDescrizione=org.apache.commons.lang3.StringEscapeUtils.escapeJson(fascicoloDescrizione);
				fascicoloDescrizione=eng.la.util.StringUtil.escapeJsonApostrofo(fascicoloDescrizione);

				String fascicoloNome=vo.getFascicolo()!=null?vo.getFascicolo().getNome():null;

				String oggetto = vo.getOggetto();
				oggetto=StringEscapeUtils.escapeJson(oggetto);
				oggetto=StringUtil.escapeJsonApostrofo(oggetto);

				String descrizione = vo.getDescrizione();
				descrizione=StringEscapeUtils.escapeJson(descrizione);
				descrizione=StringUtil.escapeJsonApostrofo(descrizione);

				jsonObject.put("id", vo.getId() );
				jsonObject.put("dataEvento", DateUtil.toString_ddMMyyyy(vo.getDataEvento()) );
				jsonObject.put("oggetto", oggetto );
				jsonObject.put("descrizione", descrizione );
				jsonObject.put("idFascicolo", idFascicolo );
				jsonObject.put("fascicoloNome", fascicoloNome );
				jsonObject.put("fascicoloDescrizione", fascicoloDescrizione );
				jsonObject.put("dataForOrdering", vo.getDataEvento() );

				//displayed
				boolean trovatoEventoDisplayed=false;
				for(EventoView eventoDisplayed: arrEventiTrovati) {
					if(eventoDisplayed.getVo().getId() == vo.getId() ) {
						trovatoEventoDisplayed=true;
						break;
					}
				}
				if(trovatoEventoDisplayed) {
					jsonObject.put("displayed", "Y" );
				}
				else {
					jsonObject.put("displayed", "N" );
				}

				jsonList.add(jsonObject); 

			}

			//scadenze to json
			for( ScadenzaView s : scadenze ){

				JSONObject jsonObject = new JSONObject();

				Scadenza vo = s.getVo();

				Long idFascicolo=vo.getFascicolo()!=null?vo.getFascicolo().getId():null;

				String fascicoloDescrizione=vo.getFascicolo()!=null?vo.getFascicolo().getDescrizione():null;
				fascicoloDescrizione=org.apache.commons.lang3.StringEscapeUtils.escapeJson(fascicoloDescrizione);
				fascicoloDescrizione=eng.la.util.StringUtil.escapeJsonApostrofo(fascicoloDescrizione);

				String fascicoloNome=vo.getFascicolo()!=null?vo.getFascicolo().getNome():null;

				String oggetto = vo.getOggetto();
				oggetto=StringEscapeUtils.escapeJson(oggetto);
				oggetto=StringUtil.escapeJsonApostrofo(oggetto);

				String descrizione = vo.getDescrizione();
				descrizione=StringEscapeUtils.escapeJson(descrizione);
				descrizione=StringUtil.escapeJsonApostrofo(descrizione);

				jsonObject.put("id", vo.getId() );
				jsonObject.put("tipoScadenzaId", vo.getTipoScadenza().getId() );
				jsonObject.put("tipoScadenzaCodGruppoLingua", vo.getTipoScadenza().getCodGruppoLingua() );
				jsonObject.put("oggetto", oggetto );
				jsonObject.put("descrizione", descrizione );
				jsonObject.put("dataScadenza", DateUtil.toString_ddMMyyyy(vo.getDataScadenza()) );
				jsonObject.put("idFascicolo", idFascicolo );
				jsonObject.put("fascicoloNome", fascicoloNome );
				jsonObject.put("fascicoloDescrizione", fascicoloDescrizione );
				jsonObject.put("dataForOrdering", vo.getDataScadenza() );

				//displayed
				boolean trovatoScadenzaDisplayed=false;
				for(ScadenzaView scadenzaDisplayed: arrScadenzeTrovati) {
					if(scadenzaDisplayed.getVo().getId() == vo.getId() ) {
						trovatoScadenzaDisplayed=true;
						break;
					}
				}
				if(trovatoScadenzaDisplayed) {
					jsonObject.put("displayed", "Y" );
				}
				else {
					jsonObject.put("displayed", "N" );
				}

				jsonList.add(jsonObject); 

			}

			//ordino
			Collections.sort(jsonList, Collections.reverseOrder(new JSONObjectComparator()));

			//merge
			JSONArray jsonArray = new JSONArray();
			for( int i=0; i<jsonList.size();i++ ) {
				JSONObject o=jsonList.get(i);
				jsonArray.put(o);
			}

			ret = jsonArray.toString();

		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
		return ret;
	}

	private Date calcolaDataFineDiFinestra(Date dataOggi) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dataOggi);
		cal.add(Calendar.DAY_OF_MONTH, Costanti.AGENDA_NUMERO_GIORNI_FINESTRA); 
		Date dataFine=cal.getTime();
		return dataFine;
	}

	private void cancellaNotificheDisplayedDelPassatoDalDB() throws Throwable {
		Date dataOggi = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dataOggi);
		cal.add(Calendar.DAY_OF_MONTH, -1); 
		Date dataMenuUnGiorno=cal.getTime();

		List<AgendaNotificheDisplayedView> lista=agendaNotificheDisplayedService.leggiLe(dataMenuUnGiorno);

		for(AgendaNotificheDisplayedView v:lista) {
			agendaNotificheDisplayedService.cancella(v);
		}
	}

	@RequestMapping(value = "/agenda/salvaEvento", method = RequestMethod.POST)
	public 
	@ResponseBody AgendaRest salvaEvento(HttpServletRequest request, Locale locale) {
		AgendaRest agendaRest = new AgendaRest();


		try 
		{
			//campi
			Long idfascicolo=Long.valueOf(request.getParameter("idfascicolo"));
			String data_req=request.getParameter("data");
			String oggetto_req=request.getParameter("oggetto");
			String descrizione_req=request.getParameter("descrizione");
			String delayAvviso_req=request.getParameter("delayAvviso");
			String frequenzaAvviso_req=request.getParameter("frequenzaAvviso");

			//converto date
			Date dataEvento = DateUtil.toDate_ddMMyyyy(data_req);

			//vo 
			Evento vo = new Evento();

			//id fascicolo
			Fascicolo fascicolo = new Fascicolo();
			fascicolo.setId(idfascicolo);
			vo.setFascicolo(fascicolo);

			vo.setLang("IT");
			vo.setDataEvento(dataEvento);
			vo.setOggetto(oggetto_req);
			vo.setDescrizione(descrizione_req);
			vo.setDelayAvviso( new BigDecimal( Long.valueOf(delayAvviso_req) ) );
			vo.setFrequenzaAvviso( new BigDecimal( Long.valueOf(frequenzaAvviso_req) ) );

			//inserisco
			EventoView eventoView = new EventoView();
			eventoView.setVo(vo);
			EventoView retEventoView=eventoService.inserisci(eventoView);

			//creo evento websocket
			List<AutorizzazioneView> autorizzazioni = autorizzazioneService.leggiAutorizzazioni(idfascicolo, CostantiDAO.NOME_CLASSE_FASCICOLO);
			for(AutorizzazioneView autorizzazione:autorizzazioni) {
				AgendaWebsocketRest agendaWebsocketRest = new AgendaWebsocketRest();
				logger.info("@@DDS agenda controller " + idfascicolo);
				FascicoloView fascicoloView=fascicoloService.leggi(idfascicolo);
				agendaWebsocketRest.setIdFascicolo(idfascicolo);
				agendaWebsocketRest.setNomeFascicolo(fascicoloView.getVo().getNome());
				agendaWebsocketRest.setDescrizioneFascicolo(fascicoloView.getVo().getDescrizione());
				agendaWebsocketRest.setOggetto(oggetto_req);
				agendaWebsocketRest.setDescrizione(descrizione_req);
				agendaWebsocketRest.setDataEvento(data_req);
				agendaWebsocketRest.setId(retEventoView.getVo().getId());

				Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_CALENDARIO, agendaWebsocketRest, autorizzazione.getVo().getUtente().getUseridUtil());
				WebSocketPublisher.getInstance().publishEvent(event); 
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return agendaRest;
	}

	@RequestMapping(value = "/agenda/salvaScadenza", method = RequestMethod.POST)
	public 
	@ResponseBody AgendaRest salvaScadenza(HttpServletRequest request, Locale locale) {
		AgendaRest agendaRest = new AgendaRest();


		try 
		{
			//campi
			String idfascicolo_req=request.getParameter("idfascicolo");
			String datainserimento_req=request.getParameter("datainserimento");
			String tempoadisposizione_req=request.getParameter("tempoadisposizione");
			String tipoScadenza_req=request.getParameter("tipo");
			String oggetto_req=request.getParameter("oggetto");
			String descrizione_req=request.getParameter("descrizione");
			String delayavviso_req=request.getParameter("delayavviso");
			String frequenzaavviso_req=request.getParameter("frequenzaavviso");
			String datascandenza_req=request.getParameter("datascandenza");

			//converto tipi
			Long idfascicolo=Long.valueOf(idfascicolo_req);

			Date dataInserimento=null;
			if(datainserimento_req!=null & !datainserimento_req.isEmpty()) {
				dataInserimento=DateUtil.toDate_ddMMyyyy(datainserimento_req);
			}

			BigDecimal tempoadisposizione=null;
			if(tempoadisposizione_req!=null & !tempoadisposizione_req.isEmpty()) {
				tempoadisposizione=BigDecimal.valueOf(Long.valueOf(tempoadisposizione_req));	
			}

			Long tipoScadenza=null;
			if(tipoScadenza_req!=null & !tipoScadenza_req.isEmpty()) {
				tipoScadenza=Long.valueOf(tipoScadenza_req);
			}

			BigDecimal delayavviso=null;
			if(delayavviso_req!=null & !delayavviso_req.isEmpty()) {
				delayavviso=BigDecimal.valueOf(Long.valueOf(delayavviso_req));
			}

			BigDecimal frequenzaavviso=null;
			if(frequenzaavviso_req!=null & !frequenzaavviso_req.isEmpty()) {
				frequenzaavviso=BigDecimal.valueOf(Long.valueOf(frequenzaavviso_req));
			}

			String oggetto=null;
			if(oggetto_req!=null & !oggetto_req.isEmpty()) {
				oggetto=oggetto_req;
			}

			String descrizione=null;
			if(descrizione_req!=null & !descrizione_req.isEmpty()) {
				descrizione=descrizione_req;
			}

			Date dataScandenza=null;
			if(datascandenza_req!=null & !datascandenza_req.isEmpty()) {
				dataScandenza=DateUtil2.convert_StringToDate(null, datascandenza_req);
			}

			//vo 
			Scadenza vo = new Scadenza();

			vo.setDataInserimento(dataInserimento);
			vo.setTempoADisposizione( tempoadisposizione );
			vo.setOggetto(oggetto);
			vo.setDescrizione(descrizione);
			vo.setDelayAvviso(delayavviso);
			vo.setFrequenzaAvviso(frequenzaavviso);
			vo.setDataScadenza(dataScandenza);

			//id fascicolo
			FascicoloView fascicoloView=fascicoloService.leggi(idfascicolo);
			Fascicolo fascicolo = fascicoloView.getVo();
			vo.setFascicolo(fascicolo);

			//id TipoScadenza
			TipoScadenza tipoScadenzaVo = null;
			if(tipoScadenza!=null && tipoScadenza>=0) {
				TipoScadenzaView tipoScadenzaView=tipoScadenzaService.leggi(tipoScadenza);
				tipoScadenzaVo=tipoScadenzaView.getVo();
			}				
			vo.setTipoScadenza(tipoScadenzaVo);

			vo.setLang("IT");

			//inserisco
			ScadenzaView scadenzaView = new ScadenzaView();
			scadenzaView.setVo(vo);
			ScadenzaView retScadenzaView=scadenzaService.inserisci(scadenzaView);

			//creo evento websocket
			List<AutorizzazioneView> autorizzazioni = autorizzazioneService.leggiAutorizzazioni(idfascicolo, CostantiDAO.NOME_CLASSE_FASCICOLO);
			for(AutorizzazioneView autorizzazione:autorizzazioni) {
				AgendaWebsocketRest agendaWebsocketRest = new AgendaWebsocketRest();

				FascicoloView fascicoloVw=fascicoloService.leggi(idfascicolo);
				agendaWebsocketRest.setIdFascicolo(idfascicolo);
				agendaWebsocketRest.setNomeFascicolo(fascicoloVw.getVo().getNome());
				agendaWebsocketRest.setDescrizioneFascicolo(fascicoloVw.getVo().getDescrizione());
				agendaWebsocketRest.setOggetto(oggetto_req);
				agendaWebsocketRest.setDescrizione(descrizione_req);
				agendaWebsocketRest.setDataScadenza(datascandenza_req);
				agendaWebsocketRest.setId(retScadenzaView.getVo().getId());


				Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_CALENDARIO, agendaWebsocketRest, autorizzazione.getVo().getUtente().getUseridUtil());
				WebSocketPublisher.getInstance().publishEvent(event); 
			}

			try{
				// timer mail
				Date dataInvioEmail = DateUtil2.addGiorni(dataScandenza, delayavviso.negate().intValue());

				String userIdOwnerFascicolo=fascicolo.getLegaleInterno();
				UtenteView utenteView=utenteService.leggiUtenteDaUserId(userIdOwnerFascicolo);
				String emailFascicolo=utenteView.getVo().getEmailUtil();

				MyTimerTask myTimerTask = new MyTimerTask();
				myTimerTask.setEmailTo(emailFascicolo);
				myTimerTask.setIdScadenza(retScadenzaView.getVo().getId());
				myTimerTask.setDataScadenza(datascandenza_req);
				myTimerTask.setOggettoScadenza(oggetto);
				myTimerTask.setDescrizioneScadenza(descrizione);
				Timer timer = new Timer(true);
				long periodoMillisec = frequenzaavviso.longValue() * 60 * 60 * 1000;
				timer.schedule(myTimerTask, dataInvioEmail, periodoMillisec);	
			}catch (Exception e) {
				logger.error("Errore nell'invio della mail di notifica salvataggio scadenza : ", e);
			}


		} catch (Throwable e) {
			logger.error("Errore nel salvataggio scadenza : ", e);
		}
		return agendaRest;
	}

	@RequestMapping(value = "/agenda/loadEventi", method = RequestMethod.GET, produces = "application/json")
	public 
	@ResponseBody AgendaRest loadEventi(HttpServletRequest request, Locale locale) {
		AgendaRest agendaRest = new AgendaRest();


		try 
		{
			List<EventoView> eventi = (List<EventoView>) eventoService.leggi();

			if(eventi==null || eventi.isEmpty() || eventi.size()==0) {
				agendaRest.setJsonArrayEventi(null);
			}
			else 
			{
				JSONArray jsonArray = new JSONArray();

				for( EventoView view : eventi ){
					JSONObject jsonObject = new JSONObject();

					Evento vo = view.getVo();

					Long idFascicolo=vo.getFascicolo()!=null?vo.getFascicolo().getId():null;

					String oggetto = vo.getOggetto();
					oggetto=StringEscapeUtils.escapeJson(oggetto);
					oggetto=StringUtil.escapeJsonApostrofo(oggetto);

					String descrizione = vo.getDescrizione();
					descrizione=StringEscapeUtils.escapeJson(descrizione);
					descrizione=StringUtil.escapeJsonApostrofo(descrizione);

					Long delayAvviso=null;
					if(vo.getDelayAvviso()!=null) {
						delayAvviso= vo.getDelayAvviso().longValue();
					}

					Long frequenzaAvviso=null;
					if(vo.getFrequenzaAvviso()!=null) {
						frequenzaAvviso= vo.getFrequenzaAvviso().longValue();
					}

					jsonObject.put("id", vo.getId() );
					jsonObject.put("dataEvento", vo.getDataEvento() );
					jsonObject.put("delayAvviso",  delayAvviso );
					jsonObject.put("frequenzaAvviso", frequenzaAvviso );
					jsonObject.put("oggetto", oggetto );
					jsonObject.put("descrizione", descrizione );
					jsonObject.put("idFascicolo", idFascicolo );

					jsonArray.put(jsonObject); 
				}

				if (jsonArray != null) {
					agendaRest.setJsonArrayEventi(jsonArray.toString());
				} 
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return agendaRest;
	}

	@RequestMapping(value = "/agenda/loadScadenze", method = RequestMethod.GET, produces = "application/json")
	public 
	@ResponseBody AgendaRest loadScadenze(HttpServletRequest request, Locale locale) {
		AgendaRest agendaRest = new AgendaRest();


		try 
		{		
			List<ScadenzaView> scadenze = (List<ScadenzaView>) scadenzaService.leggi();

			if(scadenze==null || scadenze.isEmpty() || scadenze.size()==0) {
				agendaRest.setJsonArrayScadenze(null);
			}
			else 
			{
				JSONArray jsonArray = new JSONArray();

				for( ScadenzaView view : scadenze ){
					JSONObject jsonObject = new JSONObject();

					Scadenza vo = view.getVo();
					jsonObject.put("id", vo.getId() );
					jsonObject.put("dataInserimento", vo.getDataInserimento() );
					jsonObject.put("tempoAdisposizione", vo.getTempoADisposizione() );
					jsonObject.put("tipoScadenzaId", vo.getTipoScadenza().getId() );
					jsonObject.put("tipoScadenzaCodGruppoLingua", vo.getTipoScadenza().getCodGruppoLingua() );
					jsonObject.put("oggetto", vo.getOggetto() );
					jsonObject.put("descrizione", vo.getDescrizione() );
					jsonObject.put("delayAvviso", vo.getDelayAvviso() );
					jsonObject.put("frequenzaAvviso", vo.getFrequenzaAvviso() );
					jsonObject.put("dataScadenza", vo.getDataScadenza() );
					jsonObject.put("idFascicolo", vo.getFascicolo()!=null?vo.getFascicolo().getId():null );

					jsonArray.put(jsonObject); 
				}

				if (jsonArray != null) {
					agendaRest.setJsonArrayScadenze(jsonArray.toString());
				} 
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return agendaRest;
	}

	@RequestMapping(value = "/agenda/loadListaTipoScadenza", method = RequestMethod.GET, produces = "application/json")
	public 
	@ResponseBody AgendaRest loadListaTipoScadenza(HttpServletRequest request, Locale locale) {
		AgendaRest agendaRest = new AgendaRest();

		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		try 
		{
			List<TipoScadenzaView> lista = tipoScadenzaService.leggi(locale);	

			if(lista==null || lista.isEmpty() || lista.size()==0) {
				agendaRest.setJsonArrayListaTipoScadenza(null);
			}
			else 
			{
				JSONArray jsonArray = new JSONArray();

				for( TipoScadenzaView view : lista ){
					JSONObject jsonObject = new JSONObject();

					TipoScadenza vo = view.getVo();
					jsonObject.put("id", vo.getId() );
					jsonObject.put("nome", vo.getNome() );
					jsonObject.put("codGruppoLingua", vo.getCodGruppoLingua() );
					jsonArray.put(jsonObject); 
				}

				if (jsonArray != null) {
					agendaRest.setJsonArrayListaTipoScadenza(jsonArray.toString());
				} 
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return agendaRest;
	}

	@RequestMapping(value = "/agenda/loadEventoById", method = RequestMethod.GET, produces = "application/json")
	public 
	@ResponseBody String loadEventoById(HttpServletRequest request, Locale locale) {


		try 
		{
			String idEvento_req=request.getParameter("idEvento");
			Long idEvento = Long.valueOf(idEvento_req);

			EventoView eventoView = (EventoView) eventoService.leggi(idEvento);

			JSONObject jsonObject = new JSONObject();

			jsonObject.put("idEvento", eventoView.getVo() );
			jsonObject.put("eventoOggetto", eventoView.getVo().getOggetto() );
			jsonObject.put("eventoDescrizione", eventoView.getVo().getDescrizione() );
			jsonObject.put("eventoData", DateUtil2.convert_DateToString(null, eventoView.getVo().getDataEvento()) );
			jsonObject.put("idFascicolo", eventoView.getVo().getFascicolo().getId() );
			jsonObject.put("fascicoloNome", eventoView.getVo().getFascicolo().getNome() );

			return jsonObject.toString();
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/agenda/loadScadenzaById", method = RequestMethod.GET, produces = "application/json")
	public 
	@ResponseBody String loadScadenzaById(HttpServletRequest request, Locale locale) {


		try 
		{
			String idScadenza_req=request.getParameter("idScadenza");
			Long idScadenza = Long.valueOf(idScadenza_req);

			ScadenzaView scadenzaView = (ScadenzaView) scadenzaService.leggi(idScadenza);

			JSONObject jsonObject = new JSONObject();

			jsonObject.put("idScadenza", scadenzaView.getVo() );
			jsonObject.put("scadenzaOggetto", scadenzaView.getVo().getOggetto() );
			jsonObject.put("scadenzaDescrizione", scadenzaView.getVo().getDescrizione() );
			jsonObject.put("scadenzaData", DateUtil2.convert_DateToString(null, scadenzaView.getVo().getDataScadenza()) );
			jsonObject.put("idFascicolo", scadenzaView.getVo().getFascicolo().getId() );
			jsonObject.put("fascicoloNome", scadenzaView.getVo().getFascicolo().getNome() );

			return jsonObject.toString();
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/agenda/loadEventSources", method = RequestMethod.GET, produces = "application/json")
	public 
	@ResponseBody String loadEventSources(HttpServletRequest request, Locale locale) {
		
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);

		try 
		{
			String start_req=request.getParameter("start");
			String end_req=request.getParameter("end");
			String tipologia=request.getParameter("type");

			Date start=DateUtil.toDate("yyyy-MM-dd",start_req);
			Date end=DateUtil.toDate("yyyy-MM-dd",end_req);
			
			if(tipologia.equals("undefined")){
				tipologia = null;
			}

			List<EventoView> eventi_lettiDalDB = (List<EventoView>) eventoService.leggi(start,end);
			List<ScadenzaView> scadenze_lettiDalDB = (List<ScadenzaView>) scadenzaService.leggi(start,end);

			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			String matricolaUtenteConnesso=utenteView.getVo().getMatricolaUtil();

			//utente is amministratore
			boolean utenteIsAdmin=utenteView.isAmministratore(); 

			// filtro eventi e scadenze per autorizzazioni
			List<EventoView> eventi = filtroEventiPerAutorizzazioniLettura(eventi_lettiDalDB,utenteIsAdmin,matricolaUtenteConnesso, tipologia);
			List<ScadenzaView> scadenze = filtroScadenzePerAutorizzazioniLettura(scadenze_lettiDalDB,utenteIsAdmin,matricolaUtenteConnesso, tipologia);

			List<EventSource> eventSources = new ArrayList<EventSource>(0); 

			for(int i=0; i< eventi.size(); i++)
			{
				EventoView eventoView=eventi.get(i);
				Evento evento=eventoView.getVo();

				EventSource es = new EventSource();
				es.setId(evento.getId());
				es.setTitle(evento.getOggetto());
				es.setStart(evento.getDataEvento());
				es.setClassName("bgm-green");
				eventSources.add(es);
			}

			for(int i=0; i< scadenze.size(); i++)
			{
				ScadenzaView scadenzaView=scadenze.get(i);
				Scadenza scadenza=scadenzaView.getVo();

				EventSource es = new EventSource();
				es.setId(scadenza.getId());
				es.setTitle(scadenza.getOggetto());
				es.setStart(scadenza.getDataScadenza());
				if(scadenza.getTipoScadenza().getCodGruppoLingua().equals("TS_1")) {
					es.setClassName("bgm-red");
				}
				else if(scadenza.getTipoScadenza().getCodGruppoLingua().equals("TS_2")) {
					es.setClassName("bgm-red");
				}
				eventSources.add(es);
			}	



			JSONArray jsonArray = new JSONArray();
			for( EventSource es : eventSources ){
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("id", es.getId() );
				jsonObject.put("title", es.getTitle() );
				jsonObject.put("start", es.getStart() );
				jsonObject.put("end", es.getEnd() );
				jsonObject.put("className", es.getClassName() );

				jsonArray.put(jsonObject); 
			}

			return jsonArray.toString();
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/agenda/getNotificheDaInviareOggi", method = RequestMethod.POST)
	public 
	@ResponseBody AgendaRest getNotificheDaInviareOggi(HttpServletRequest request, Locale locale) {
		AgendaRest agendaRest = new AgendaRest();



		try 
		{
			List<ScadenzaView> scadenze = (List<ScadenzaView>) scadenzaService.leggi();
			List<EventoView> eventi = (List<EventoView>) eventoService.leggi();

			List<EventoView> notificheEventiDaInviareOggi = new ArrayList<EventoView>(0);
			List<ScadenzaView> notificheScandezeDaInviareOggi = new ArrayList<ScadenzaView>(0);
			JSONArray jsonArrayNotificheEventiDaInviareOggi = new JSONArray();
			JSONArray jsonArrayNotificheScandezeDaInviareOggi = new JSONArray();

			Date dataOggi = new Date();

			for(int i=0; i< eventi.size(); i++)
			{
				EventoView eventoView=eventi.get(i);
				Evento evento=eventoView.getVo();
				Date dataEvento=evento.getDataEvento();
				long diffGiorni = DateUtil.calcDiffGiorni(dataOggi,dataEvento);
				long delayAvviso = evento.getDelayAvviso().longValue();

				if(diffGiorni==delayAvviso) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", evento.getId());
					jsonObject.put("idFascicolo", evento.getFascicolo()==null?evento.getFascicolo().getId():null );
					jsonObject.put("oggetto", evento.getOggetto() );
					jsonObject.put("descrizione", evento.getDescrizione() );
					jsonArrayNotificheEventiDaInviareOggi.put(jsonObject);

					notificheEventiDaInviareOggi.add(eventoView);
				}
			}


			for(int i=0; i< scadenze.size(); i++)
			{
				ScadenzaView scadenzaView=scadenze.get(i);
				Scadenza scadenza=scadenzaView.getVo();

				Date dataScadenza=scadenza.getDataScadenza();

				long diffGiorni = DateUtil.calcDiffGiorni(dataOggi,dataScadenza);
				long delayAvviso = scadenza.getDelayAvviso().longValue();
				if(diffGiorni==delayAvviso) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", scadenza.getId());
					jsonObject.put("idFascicolo", scadenza.getFascicolo()==null?scadenza.getFascicolo().getId():null );
					jsonObject.put("oggetto", scadenza.getOggetto() );
					jsonObject.put("descrizione", scadenza.getDescrizione() );
					jsonArrayNotificheScandezeDaInviareOggi.put(jsonObject);

					notificheScandezeDaInviareOggi.add(scadenzaView);
				}
			}

			agendaRest.setJsonArrayNotificheEventiDaInviareOggi(jsonArrayNotificheEventiDaInviareOggi.toString());
			agendaRest.setJsonArrayNotificheScandezeDaInviareOggi(jsonArrayNotificheScandezeDaInviareOggi.toString());

		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}

		return agendaRest;
	}

	@RequestMapping(value = "/agenda/setNotificaLetta", method = RequestMethod.POST)
	public 
	@ResponseBody void setNotificaLetta(HttpServletRequest request, Locale locale) {


		try 
		{
			Long idevento=Long.valueOf(request.getParameter("idevento")!=null?request.getParameter("idevento"):null);
			Long idscadenza=Long.valueOf(request.getParameter("idscadenza")!=null?request.getParameter("idscadenza"):null);
			String tipo=request.getParameter("tipo");

			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			String matricola=utenteView.getVo().getMatricolaUtil();

			AgendaNotificheDisplayedView agendaNotificheDisplayedView=new AgendaNotificheDisplayedView();
			NotificheAgendaDisplayed vo=new NotificheAgendaDisplayed();
			vo.setMatricolaUtente(matricola);
			vo.setIdEvento(idevento);
			vo.setIdScadenza(idscadenza);
			vo.setTipo(tipo);
			vo.setDataDisplayed(new Date());
			agendaNotificheDisplayedView.setVo(vo);
			agendaNotificheDisplayedService.addNotificaDisplayed(agendaNotificheDisplayedView);
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private List<EventoView> filtraEventiPerFascicoloOwner(String matricolaUser, List<EventoView> listaIn) throws Throwable {
		List<EventoView> listaOut = new ArrayList<EventoView>();
		for(EventoView evView: listaIn) {
			Evento vo = evView.getVo();

			Fascicolo fascicolo=vo.getFascicolo();
			String userIdOwnerFascicolo=fascicolo.getLegaleInterno();
			UtenteView utenteView=utenteService.leggiUtenteDaUserId(userIdOwnerFascicolo);
			String matrFascicolo=utenteView.getVo().getMatricolaUtil();
			boolean isOwner=matricolaUser.equals(matrFascicolo);
			if(isOwner) {
				listaOut.add(evView);
			}

		}
		return listaOut;
	}

	@SuppressWarnings("unused")
	private List<ScadenzaView> filtraScadenzePerFascicoloOwner(String matricolaUser, List<ScadenzaView> listaIn) throws Throwable {
		List<ScadenzaView> listaOut = new ArrayList<ScadenzaView>();
		for(ScadenzaView scadView: listaIn) {
			Scadenza vo = scadView.getVo();

			Fascicolo fascicolo=vo.getFascicolo();
			String userIdOwnerFascicolo=fascicolo.getLegaleInterno();
			UtenteView utenteView=utenteService.leggiUtenteDaUserId(userIdOwnerFascicolo);
			String matrFascicolo=utenteView.getVo().getMatricolaUtil();
			boolean isOwner=matricolaUser.equals(matrFascicolo);
			if(isOwner) {
				listaOut.add(scadView);
			}

		}
		return listaOut;
	}

	@RequestMapping(value = "/agenda/calcolaDataScadenza", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public 
	@ResponseBody AgendaRest calcolaDataScadenza(HttpServletRequest request, Locale locale) {
		AgendaRest agendaRest = new AgendaRest();


		try 
		{
			String dataInserimento_req=request.getParameter("dataInserimento");
			String tempoAdisposizione_req=request.getParameter("tempoAdisposizione");
			String when = request.getParameter("when");

			Long tempoAdisposizione=null;
			if(tempoAdisposizione_req!=null & !tempoAdisposizione_req.isEmpty()) {
				tempoAdisposizione=Long.valueOf(tempoAdisposizione_req);
			}

			Date dataInserimento=null;
			if(dataInserimento_req!=null & !dataInserimento_req.isEmpty()) {
				dataInserimento=DateUtil2.convert_StringToDate(null, dataInserimento_req);
			}
			
			
			Date dataScandenza=null;
			
			//calcolo data scadenza
			if(when != null && !when.isEmpty()){
				
				if("1".equals(when)){
					
					dataScandenza=DateUtil2.addGiorni(dataInserimento,tempoAdisposizione.intValue());
				}
				else{
					dataScandenza=DateUtil2.addGiorni(dataInserimento,-tempoAdisposizione.intValue());
				}
			}
			else{
				dataScandenza=DateUtil2.addGiorni(dataInserimento,tempoAdisposizione.intValue());
			}

			while(isFest(dataScandenza)){
				dataScandenza=DateUtil2.addGiorni(dataScandenza,-1);
			}

			String dataScandenzaStr=DateUtil2.convert_DateToString(null, dataScandenza);
			agendaRest.setDataScandenzaStr(dataScandenzaStr);
		} 
		catch (Throwable e) {
			e.printStackTrace();
		}
		return agendaRest;
	}

	@RequestMapping(value = "/agenda/cancellaEvento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public 
	@ResponseBody AgendaRest cancellaEvento(HttpServletRequest request, Locale locale) {
		AgendaRest agendaRest = new AgendaRest();


		try 
		{
			String id_req=request.getParameter("id");

			Long id=null;
			if(id_req!=null & !id_req.isEmpty()) {
				id=Long.valueOf(id_req);
			}

			eventoService.cancella(id);
		} 
		catch (Throwable e) {
			e.printStackTrace();
		}
		return agendaRest;
	}

	@RequestMapping(value = "/agenda/cancellaScadenza", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public 
	@ResponseBody AgendaRest cancellaScadenza(HttpServletRequest request, Locale locale) {
		AgendaRest agendaRest = new AgendaRest();


		try 
		{
			String id_req=request.getParameter("id");

			Long id=null;
			if(id_req!=null & !id_req.isEmpty()) {
				id=Long.valueOf(id_req);
			}

			scadenzaService.cancella(id);
		} 
		catch (Throwable e) {
			e.printStackTrace();
		}
		return agendaRest;
	}


	@RequestMapping(value = "/agenda/caricaTipologieAgendaDisponibili", method = RequestMethod.POST)
	public @ResponseBody String caricaTipologieAgendaDisponibili(HttpServletRequest request, Locale locale) {


		try {  

			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");

			Map<String, String> tipologieDisponibili = new LinkedHashMap<String, String>();

			if(currentSessionUtil.getRolesCode() != null && !currentSessionUtil.getRolesCode().isEmpty()){
				boolean isResponsabile = false;

				for( String code : currentSessionUtil.getRolesCode()){
					if(code.equals( CostantiDAO.GRUPPO_RESPONSABILE )){
						isResponsabile = true;
					}
				}
				/** Cerco tutti i legali interni di cui l'utente connesso e' responsabile **/
				if(isResponsabile){

					tipologieDisponibili.put("default", "I miei eventi/scadenze");
					tipologieDisponibili.put("all", "Tutti");

					List<UtenteView> listaCollaboratoriLegaliInterni = utenteService.leggiCollaboratoriLegaliInterni(utenteView.getVo().getMatricolaUtil());
					
					if(listaCollaboratoriLegaliInterni != null && !listaCollaboratoriLegaliInterni.isEmpty()){
						
						for(UtenteView collaboratoreLegaleInterno: listaCollaboratoriLegaliInterni){
							
							if(collaboratoreLegaleInterno.getVo() != null){
								
								if(collaboratoreLegaleInterno.getVo().getNominativoAppart() != null && !collaboratoreLegaleInterno.getVo().getNominativoAppart().isEmpty()){
									
									if(collaboratoreLegaleInterno.getVo().getMatricolaUtil() != null && !collaboratoreLegaleInterno.getVo().getMatricolaAppart().isEmpty()){
										
										String nominativoAppartenenza = collaboratoreLegaleInterno.getVo().getNominativoAppart();
										nominativoAppartenenza = nominativoAppartenenza.replace(",", "");
										tipologieDisponibili.put(collaboratoreLegaleInterno.getVo().getMatricolaUtil(), nominativoAppartenenza);
									}
								}
							}
						}
					}
					
					JSONArray jsonArray = new JSONArray();

					for (Map.Entry<String, String> entry : tipologieDisponibili.entrySet()){
						JSONObject jsonObject = new JSONObject();
						
						String tipologia = entry.getValue();
						tipologia=tipologia.replace("\'", " ");
						
						jsonObject.put("tipologia", tipologia);
						jsonObject.put("value", entry.getKey());
						jsonArray.put(jsonObject);
					}
					return jsonArray.toString();
				}
			}
			else{
				return null;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}





	@SuppressWarnings("unused")
	private class EventSource  {

		public EventSource()  { }

		private Long id;
		private String title;
		private Date start;
		private Date end;
		private String className;

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}

		public Date getStart() {
			return start;
		}
		public void setStart(Date start) {
			this.start = start;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public Date getEnd() {
			return end;
		}
		public void setEnd(Date end) {
			this.end = end;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}


	}

	@SuppressWarnings("serial")
	private class JSONObjectComparator implements Comparator<JSONObject>, Serializable {

		@Override
		public int compare(JSONObject o1, JSONObject o2) {
			int ret=-1;

			Date data_o1= null;
			try {
				data_o1 = (Date) o1.get("dataForOrdering");
			}
			catch(JSONException e) 
			{
				e.printStackTrace();
			}

			Date data_o2 = null;
			try {
				data_o2 = (Date) o2.get("dataForOrdering");
			}
			catch(JSONException e) 
			{
				e.printStackTrace();
			}

			if(data_o1!=null && data_o2!=null) {
				ret=data_o1.compareTo(data_o2);
			}

			return ret;
		}

	}

	private class MyTimerTask extends TimerTask  {

		private Long idScadenza;
		private String oggettoScadenza;
		private String descrizioneScadenza;
		private String dataScadenza;
		private String emailTo;

		@Override
		public void run() {
			// System.out.println("Timer task started at:"+new Date());

			//invio email
			// System.out.println("invio email: idScadenza="+this.idScadenza);

			String from = "test@test.it";
			String to = getEmailTo();
			String oggetto = "Avviso Scadenza il "+getDataScadenza();
			String contenuto = "Hai una scadenza il "+getDataScadenza() +"<br><br>";
			contenuto+= "Oggetto: "+getDescrizioneScadenza() +"<br>";
			contenuto+= "Descrizione: "+getDescrizioneScadenza();

			try {
				emailService.sendEmail(from, to, oggetto, contenuto);
			} catch (Throwable e) {
				e.printStackTrace();
			}	


			// System.out.println("Timer task finished at:"+new Date());
		}


		@SuppressWarnings("unused")
		public String getOggettoScadenza() {
			return oggettoScadenza;
		}

		public void setOggettoScadenza(String oggettoScadenza) {
			this.oggettoScadenza = oggettoScadenza;
		}

		public String getDescrizioneScadenza() {
			return descrizioneScadenza;
		}

		public void setDescrizioneScadenza(String descrizioneScadenza) {
			this.descrizioneScadenza = descrizioneScadenza;
		}

		public String getDataScadenza() {
			return dataScadenza;
		}

		public void setDataScadenza(String dataScadenza) {
			this.dataScadenza = dataScadenza;
		}


		@SuppressWarnings("unused")
		public Long getIdScadenza() {
			return idScadenza;
		}


		public void setIdScadenza(Long idScadenza) {
			this.idScadenza = idScadenza;
		}


		public String getEmailTo() {
			return emailTo;
		}


		public void setEmailTo(String emailTo) {
			this.emailTo = emailTo;
		}


	}

	private List<EventoView> filtroEventiPerAutorizzazioniLettura(List<EventoView> eventiIn,boolean utenteIsAdmin,String matricolaUtenteConnesso, String tipologia) throws Throwable {
		List<EventoView> eventiOut = new ArrayList<EventoView>();
		// eventi - filtro per autorizzazioni di LETTURA 
		for(EventoView ev:eventiIn) {

			Long idFascicolo=ev.getVo().getFascicolo().getId();

			String userIdOwnerFascicolo=ev.getVo().getFascicolo().getLegaleInterno();
			UtenteView utenteViewFascicolo=utenteService.leggiUtenteDaUserId(userIdOwnerFascicolo);
			String matricolaOwnerFascicolo=utenteViewFascicolo.getVo().getMatricolaUtil();

			// amministratore
			if(utenteIsAdmin) {
				eventiOut.add(ev);
			}
			else{
				if(tipologia != null && !tipologia.isEmpty()){

					/** Visualizza solo gli eventi di cui l'utente connesso e' owner del fascicolo **/
					if("default".equals(tipologia)){

						if(matricolaUtenteConnesso.equals(matricolaOwnerFascicolo)) {
							eventiOut.add(ev);
						}
					}	
					/** Visualizza tutti gli eventi (sia quelli di cui l'utente connesso e' owner del fascicolo, sia quelli dei suoi sottoposti) **/
					else if("all".equals(tipologia)){

						if(matricolaUtenteConnesso.equals(matricolaOwnerFascicolo)) {
							eventiOut.add(ev);
						}
						else{
							List<AutorizzazioneView> listaAutView=autorizzazioneService.leggiAutorizzazioni2(idFascicolo, CostantiDAO.NOME_CLASSE_FASCICOLO);
							for(AutorizzazioneView av: listaAutView) {

								Autorizzazione a=av.getVo();
								boolean add=false;

								// responsabile: controllo se l'utente connesso sta nella catena dei resposanbili che hanno l'autorizzazione lettura
								if( a.getTipoAutorizzazione().getCodGruppoLingua().equals(CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_LETTURA) || a.getTipoAutorizzazione().getCodGruppoLingua().equals(CostantiDAO.TIPO_AUTORIZZAZIONE_GRUPPO_LETTURA) ) {
									String forRespMatricola=a.getUtenteForResp()==null?null:a.getUtenteForResp().getMatricolaUtil();
									if(forRespMatricola != null) {
										List<UtenteView> listaResponsabili=utenteService.leggiResponsabili(forRespMatricola);

										if(listaResponsabili != null && !listaResponsabili.isEmpty()){

											for(UtenteView uv: listaResponsabili) {
												if(matricolaUtenteConnesso.equals(uv.getVo().getMatricolaUtil())) {
													add=true;
													break;
												}
											}
										}
									}
								}
								if(add) {
									eventiOut.add(ev);
								}
							}
						}
					}
					/** Visualizza gli eventi del legale sottoposto inserito in input **/
					else{
						if(tipologia.equals(matricolaOwnerFascicolo)) {
							eventiOut.add(ev);
						}
					}
				}
				else{
					if(matricolaUtenteConnesso.equals(matricolaOwnerFascicolo)) {
						eventiOut.add(ev);
					}
				}
			}
		}
		return eventiOut;
	}

	private List<ScadenzaView> filtroScadenzePerAutorizzazioniLettura(List<ScadenzaView> scadenzeIn,boolean utenteIsAdmin,String matricolaUtenteConnesso, String tipologia) throws Throwable {
		List<ScadenzaView> scadenzeOut = new ArrayList<ScadenzaView>();

		// scadenze - filtro per autorizzazioni di LETTURA 
		for(ScadenzaView sv: scadenzeIn) {

			Long idFascicolo=sv.getVo().getFascicolo().getId();

			String userIdOwnerFascicolo=sv.getVo().getFascicolo().getLegaleInterno();
			UtenteView utenteViewFascicolo=utenteService.leggiUtenteDaUserId(userIdOwnerFascicolo);
			String matricolaOwnerFascicolo=utenteViewFascicolo.getVo().getMatricolaUtil();

			// amministratore
			if(utenteIsAdmin) {
				scadenzeOut.add(sv);
			}
			else{
				if(tipologia != null && !tipologia.isEmpty()){

					/** Visualizza solo gli eventi di cui l'utente connesso e' owner del fascicolo **/
					if("default".equals(tipologia)){
						if(matricolaUtenteConnesso.equals(matricolaOwnerFascicolo)) {
							scadenzeOut.add(sv);
						}
					}
					/** Visualizza tutti gli eventi (sia quelli di cui l'utente connesso e' owner del fascicolo, sia quelli dei suoi sottoposti) **/
					else if("all".equals(tipologia)){
						if(matricolaUtenteConnesso.equals(matricolaOwnerFascicolo)) {
							scadenzeOut.add(sv);
						}
						else{
							List<AutorizzazioneView> listaAutView=autorizzazioneService.leggiAutorizzazioni2(idFascicolo, CostantiDAO.NOME_CLASSE_FASCICOLO);
							for(AutorizzazioneView av: listaAutView) {
								Autorizzazione a=av.getVo();

								//System.out.println("TipoAutorizzazione="+a.getTipoAutorizzazione().getCodGruppoLingua());

								boolean add=false;

								// responsabile: controllo se l'utente connesso sta nella catena dei resposanbili che hanno l'autorizzazione lettura
								if( a.getTipoAutorizzazione().getCodGruppoLingua().equals(CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_LETTURA) || a.getTipoAutorizzazione().getCodGruppoLingua().equals(CostantiDAO.TIPO_AUTORIZZAZIONE_GRUPPO_LETTURA) ) {
									String forRespMatricola=a.getUtenteForResp()==null?null:a.getUtenteForResp().getMatricolaUtil();
									if(forRespMatricola != null) {
										List<UtenteView> listaResponsabili=utenteService.leggiResponsabili(forRespMatricola);
										for(UtenteView uv: listaResponsabili) {
											if(matricolaUtenteConnesso.equals(uv.getVo().getMatricolaUtil())) {
												add=true;
												break;
											}
										}
									}
								}

								if(add) {
									scadenzeOut.add(sv);
								}
							}
						}
					}
					/** Visualizza gli eventi del legale sottoposto inserito in input **/
					else{
						if(tipologia.equals(matricolaOwnerFascicolo)) {
							scadenzeOut.add(sv);
						}
					}
				}
				else{
					if(matricolaUtenteConnesso.equals(matricolaOwnerFascicolo)) {
						scadenzeOut.add(sv);
					}
				}
			}
		}
		return scadenzeOut;
	}


	protected Date pasqua(int anno) {
		// RITORNA DATA DELLA PASQUA fra il 1753 e il 2500
		int Ap, Bp, Cp, Dp, Ep, Fp, Mp;
		if (anno < 100) anno = 1900 + anno;
		Ap = anno % 19;
		Bp = anno % 4;
		Cp = anno % 7;
		Dp = (19*Ap + 24) % 30;
		Fp = 0; // correzione per secoli
		if (anno<2500) Fp=3;
		if (anno<2300) Fp=2;
		if (anno<2200) Fp=1;
		if (anno<2100) Fp=0;
		if (anno<1900) Fp=6;
		if (anno<1800) Fp=5;
		if (anno<1700) Fp=4;
		Ep = (2*Bp + 4*Cp + 6*Dp + Fp + 5) % 7;
		Ep = 22 + Dp + Ep;
		Mp = 3;
		if (Ep>31) {
			Mp = 4;
			Ep = Ep - 31;
		}
		return (DateUtil.toDate("" + Ep + "/" + Mp + "/" + anno));
	}

	boolean isFest(Date data) {
		boolean s,d,p,f;
		// FISSI
		String ff = " 0101 0106 0425 0501 0602 0815 1101 1208 1225 1226 ";

		Calendar c = Calendar.getInstance();
		c.setTime(data);

		// sabato
		s = (c.get(Calendar.DAY_OF_WEEK)==7);
		// domenica
		d = (c.get(Calendar.DAY_OF_WEEK)==1);
		// pasquetta
		Date pp = pasqua(c.get(Calendar.YEAR));
		Date qq = (DateUtil2.addGiorni(data, -1));
		p = (date2str(qq).equals(date2str(pp)));

		// data in stringa
		String ss = date2str(data);
		f = (ff.indexOf(ss.substring(4))>0);
		return (d || s || p || f);
	}

	String date2str(Date dd) {

		String result = DateUtil.getDataYYYYMMDD(dd.getTime());
		return result.replace("-", "");
	}



}
