package it.eng.la.ws.senderservice;

import it.eng.la.ws.Freeform;
import it.eng.la.ws.InserisciCorredoContabile_Service;
import it.eng.la.ws.Item;
import it.eng.la.ws.LegalWS;
import it.eng.la.ws.Q1;
import it.eng.la.ws.Q3;
import it.eng.la.ws.Risultato;
import it.eng.la.ws.Row;
import it.eng.la.ws.entity.InvioDati;
import it.eng.la.ws.entity.InvioDatiWs;
import it.eng.la.ws.entity.Property;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

@Stateless
@SuppressWarnings("unchecked")
@TransactionManagement(value=TransactionManagementType.BEAN )
public class Lucy1LuSenderServiceBean implements Lucy1LuSenderService{
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);
	
	@PersistenceContext(unitName="Lucy1LuSender-PU")
	EntityManager em;
	
	@Resource
	TimerService timerService;
	
	@Resource
    UserTransaction tx;

	private static long INITIAL_DURATION =  2*60*1000;
	private static long INTERVAL_DURATION = 5*60*1000;
	
	protected final static Logger GENERAL_LOGGER = Logger.getLogger(Lucy1LuSenderServiceBean.class.getName());
	
	public void sendDocuments(){
		String tipoDoc = "1LU";
		String returnMessage = "";
		boolean trovato = true;
		List list = new ArrayList();
		
		if(getSemaforo(tipoDoc)){
			try{
				tx.begin();

				GENERAL_LOGGER.debug("**** START SEMAFORO VERDE -- INVIO DATI LUCYSTAR 1LU ****");
				
				List<InvioDatiWs> resultList = em.createNamedQuery("InvioDatiWs.findAll").
						setHint("javax.persistence.cache.retrieveMode", "BYPASS").getResultList();
				//resultList sarà il risultato della query su invio_dati_ws where invio_progressivo=0 or (id_stato=-1 and invio_progressivo<5)
				//cioè i dati nuovi e quelli che invece sono andati in errore per meno di 5 volte
				
				if(resultList!=null && resultList.size()>0){
					GENERAL_LOGGER.debug("Sono presenti "+resultList.size()+" dati....mi preparo per l'invio.");
					
					// invochiamo il servizio
					GENERAL_LOGGER.debug("invochiamo il servizio");
					InserisciCorredoContabile_Service service = new InserisciCorredoContabile_Service();
					LegalWS legalWSPort = service.getLegalWSEndpoint1();
					
					BindingProvider bindingProvider = (BindingProvider)legalWSPort;
//					SOAPBinding binding = (SOAPBinding) bindingProvider.getBinding();
//					binding.setMTOMEnabled(true);
					Map requestContext = bindingProvider.getRequestContext();
					
					String endPoint = get("ENDPOINT_LUCYSTAR");
					GENERAL_LOGGER.debug("endPoint: "+endPoint);
					String userName = get("USERNAME_LUCYSTAR");
					String password = get("PASSWORD_LUCYSTAR");
					
					requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);
//					requestContext.put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
//					requestContext.put(BindingProvider.SOAPACTION_URI_PROPERTY, "");
					
					//authentication JAX-WS
//					Map<String, List<String>> headers = new HashMap<String, List<String>>();
//					headers.put("Username", Collections.singletonList(userName));
//					headers.put("Password", Collections.singletonList(password));
//					requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
					
					//basic authentication HTTP
					requestContext.put(BindingProvider.USERNAME_PROPERTY, userName);
					requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);		
					
					for (InvioDatiWs invioDatiWs : resultList) {
						
						Esito esito = new Esito();
						esito.setIdProforma(invioDatiWs.getIdProforma());
						GENERAL_LOGGER.debug("IdProforma: "+invioDatiWs.getIdProforma().intValue());
						esito.setStateId(BigDecimal.valueOf(0));
						
						GregorianCalendar c = new GregorianCalendar();
						c.setTime(new Date());
						XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
						
						Freeform freeform = new Freeform();
			            freeform.setCodefornerp(invioDatiWs.getCodiceFornitore());
			            freeform.setDatadoc(getDateAsString(invioDatiWs.getDataDocumento()));
			            freeform.setNumdoc(invioDatiWs.getNumero());
			            //Creazione Q1
			            Q1 q1 = new Q1();
			            Item q1Item = new Item();
			            q1Item.setNome("ESERCIZIO");
			            q1Item.setValue(invioDatiWs.getEsercizio()==null?"":invioDatiWs.getEsercizio().toString());
			            q1.getItem().add(q1Item);
			            q1Item = new Item();
			            q1Item.setNome("PIVACLI");
			            q1Item.setValue(invioDatiWs.getPartitaIva());
			            q1.getItem().add(q1Item);
			            q1Item = new Item();
			            q1Item.setNome("DATAAUT");
			            q1Item.setValue(getDateAsString(invioDatiWs.getDataAutorizzazione()));
			            q1.getItem().add(q1Item);
			            q1Item = new Item();
			            q1Item.setNome("AUTORIZZATORE");
			            q1Item.setValue(invioDatiWs.getAutorizzatore());
			            q1.getItem().add(q1Item);
			            q1Item = new Item();
			            q1Item.setNome("IMPORTOAUT");
			            q1Item.setValue(invioDatiWs.getImportoAutorizzato().toString());
			            q1.getItem().add(q1Item);
			            q1Item = new Item();
			            q1Item.setNome("NOTE");
			            q1Item.setValue(invioDatiWs.getNote());
			            q1.getItem().add(q1Item);
			            freeform.setQ1(q1);
			            //Creazione Q3
			            Q3 q3 = new Q3();
			            //Riga 1
			            Row row = new Row();
			            Item item = new Item();
			            item.setNome("Conto");
			            item.setValue(invioDatiWs.getVoceDiConto());
			            row.getItem().add(item);
			            item = new Item();
			            item.setNome("C.d.C.");
			            item.setValue(invioDatiWs.getCentroDiCosto());
			            row.getItem().add(item);
			            q3.getRow().add(row);
			            
			            freeform.setQ3(q3);
						
						try{
							Risultato inserisciCorredoContabile = legalWSPort.inserisciCorredoContabile(freeform, date, "Legal Archives", "LucyStar");
							
							String a = inserisciCorredoContabile.getEsito()==null?"":inserisciCorredoContabile.getEsito();
							String b = inserisciCorredoContabile.getMessaggio()==null?"":inserisciCorredoContabile.getMessaggio();

							returnMessage = "Esito: "+a+" - Messaggio: "+b;
							GENERAL_LOGGER.debug(returnMessage);
							
							esito.setStateId(BigDecimal.valueOf(1));
							esito.setReturnMessage(returnMessage);
							
							GENERAL_LOGGER.debug(tipoDoc + " ---> "+returnMessage);
						} catch(Exception e){
							GENERAL_LOGGER.error("Errore durante l'invocazione del servizio: ",e);
							if(esito.getStateId().compareTo(BigDecimal.valueOf(0)) == 0){ 
								esito.setStateId(BigDecimal.valueOf(-1));
							}
						} finally{
							list.add(esito);
						}
					}
					
					GENERAL_LOGGER.debug("Fine invocazione del servizio");
					
				}else{
					trovato = false;
					GENERAL_LOGGER.debug(tipoDoc + " Non ci sono dati da poter inviare.");
				}
				 
				tx.commit();
			}catch(Exception e){
				GENERAL_LOGGER.error("Errore durante l'invio ",e);
				//sbloccaSemaforo(tipoDoc);
				try {
					tx.rollback();
				} catch (Exception e1) {
					GENERAL_LOGGER.error("Errore durante il rollback ",e1);
				} 
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Esito esitoList = (Esito) iterator.next();
		        	if(esitoList.getStateId().compareTo(BigDecimal.valueOf(0)) == 0){ 
		        		esitoList.setStateId(BigDecimal.valueOf(-1));
		        	}
				}
				//throw new RuntimeException(e);
			}finally{
				try {
					if(trovato){
						tx.begin();
						//indipendetemente da come va l'invocazione del servizio, devo aggiornare i record in invio_dati_ws
						for (Iterator iterator = list.iterator(); iterator.hasNext();) {
							Esito esitoList = (Esito) iterator.next();
							List<InvioDatiWs> existProformaList = em.createNamedQuery("InvioDatiWs.byidProforma").
									setHint("javax.persistence.cache.retrieveMode", "BYPASS").
									setParameter("idProforma", esitoList.getIdProforma()).getResultList();
							
							if(existProformaList!= null && existProformaList.size()>0){
								for (InvioDatiWs existProforma : existProformaList) {
									existProforma.setProgresent(existProforma.getProgresent().add(BigDecimal.valueOf(1)));
									GENERAL_LOGGER.debug("Aggiornamento in InvioDatiWs dell'idProforma: "+esitoList.getIdProforma() + " - Progresent: "+existProforma.getProgresent().intValue());
									if(existProforma.getProgresent().intValue()==1){
										existProforma.setSentdate(new java.util.Date());
									} else {
										existProforma.setResentdate(new java.util.Date());
									}
									existProforma.setStateid(esitoList.getStateId());
									existProforma.setReturnmessage(esitoList.getReturnMessage());
									em.persist(existProforma);
								}
							}
						}
						
						tx.commit();
					}
					sbloccaSemaforo(tipoDoc);
					
					GENERAL_LOGGER.debug("**** END SEMAFORO VERDE -- INVIO DATI LUCYSTAR 1LU ****");
				} catch (Exception e) {
					GENERAL_LOGGER.error("Errore durante il salvataggio in invio_dati_ws ",e);
					try {
						tx.rollback();
					} catch (Exception e1) {
						GENERAL_LOGGER.error("Errore durante il rollback ",e1);
					} 
					sbloccaSemaforo(tipoDoc);
					throw new RuntimeException(e);
				} 
			}
		}else{
			GENERAL_LOGGER.debug("**** SEMAFORO ROSSO -- INVIO DATI LUCYSTAR 1LU ****");
		}
	}

	@Override
	//@TransactionAttribute(TransactionAttributeType.REQUIRED) 
	public void init() {
		GENERAL_LOGGER.debug("Start creazione timerService");
		for (Object obj : timerService.getTimers()){
			Timer timer = (Timer) obj;
			String scheduled = (String) timer.getInfo();
			if(scheduled.equalsIgnoreCase("Lucy1LuTimer")){
				timer.cancel();
				GENERAL_LOGGER.debug("Timer "+scheduled+" cancellato");
			}
		}
		timerService.createTimer((long)(INITIAL_DURATION),(long)(INTERVAL_DURATION), "Lucy1LuTimer");
		GENERAL_LOGGER.debug("Nuovo Timer Lucy1LuTimer creato ogni "+INTERVAL_DURATION+" millisecondi!");
	}

	@Timeout
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED) 
	public void doTask(Timer timer) {
		//GENERAL_LOGGER.debug("SCATTATO IL TIMEOUT ---> "+timer.getInfo().toString());
		try{
			em.clear();
			//Qui possiamo inserire un controllo orario in cui deve partire l'invio
			sendDocuments();
			
		}catch(Exception e){
			GENERAL_LOGGER.error("Errore doTask:",e);
			throw new RuntimeException(e);
		}
	}
	
	public boolean getSemaforo(String tipoDoc) {
		//GENERAL_LOGGER.debug("getSemaforo ---> "+tipoDoc);
		boolean value = false;
		try{
			tx.begin();
			InvioDati c=em.find(InvioDati.class,tipoDoc);
			if(c.getDataEsecuzione()==null){
				value = true;
				java.util.Date a = new java.util.Date();
				GENERAL_LOGGER.debug("Semaforo "+tipoDoc+" settato a "+a);
				c.setDataEsecuzione(a);
				em.persist(c);
			}
			tx.commit();
		}catch(Exception e){
			GENERAL_LOGGER.error("getSemaforo Exception: "+e);	
		}
		return value;
	}

	public void sbloccaSemaforo(String tipoDoc) {
		try{
			Thread.sleep(60*1000);
			tx.begin();
			InvioDati c=em.find(InvioDati.class,tipoDoc);
			c.setDataEsecuzione(null);
			em.persist(c);
			tx.commit();
		}catch(Exception e){
			GENERAL_LOGGER.error("sbloccaSemaforo Exception: "+e);	
		}
		//GENERAL_LOGGER.debug("Semaforo "+tipoDoc+" settato a null");
	}
	
	public String get(String propertyName) {
		List<Property> retList = em.createNamedQuery("Property.findByKey").setParameter("key", propertyName).
				setHint("javax.persistence.cache.retrieveMode", "BYPASS").
				getResultList();
			Property prop = retList.iterator().next();
			return prop.getValue();
	}
	
    public static Date getDate(String date) throws ParseException {
        return new Date(dateFormat.parse(date).getTime());
    }
	
    public static String getDateAsString(Date date) {
        return dateFormat.format(date);
    }
    
}
