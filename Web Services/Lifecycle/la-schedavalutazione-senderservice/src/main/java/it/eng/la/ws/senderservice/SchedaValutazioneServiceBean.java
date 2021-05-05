package it.eng.la.ws.senderservice;

import it.eng.la.ws.CreatePDFImpl;
import it.eng.la.ws.CreatePDFService;
import it.eng.la.ws.Esito;
import it.eng.la.ws.entity.InvioDati;
import it.eng.la.ws.entity.Property;
import it.eng.la.ws.entity.SchedaValutazione;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

@Stateless
@SuppressWarnings("unchecked")
@TransactionManagement(value=TransactionManagementType.BEAN )
public class SchedaValutazioneServiceBean implements SchedaValutazioneSenderService
{
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);
	
	@PersistenceContext(unitName="SchedaValutazioneSender-PU")
	EntityManager emLa;
	
	@Resource
	TimerService timerService;
	
	@Resource
    UserTransaction tx;

	private static long INITIAL_DURATION =  2*60*1000;
	private static long INTERVAL_DURATION = 5*60*1000;
	protected final static Logger GENERAL_LOGGER = Logger.getLogger(SchedaValutazioneServiceBean.class.getName());
	
	
	public void sendFile()
	{
		String tipoDoc = "PDFSV";
		boolean trovato = true;
		List list = new ArrayList();
		
		if(getSemaforo(tipoDoc))
		{
			try
			{
				tx.begin();
				GENERAL_LOGGER.debug("**** START SEMAFORO VERDE -- INVIO DATI SchedaValutazione ****");
				
				/********Query Scheda Valutazione, (inviata=0)**********/
				List<SchedaValutazione> resultListLI = emLa.createNamedQuery("SchedaValutazione.findInviata").
						setHint("javax.persistence.cache.retrieveMode", "BYPASS").getResultList();
				
					if(resultListLI!=null && resultListLI.size()>0)
					{
						GENERAL_LOGGER.debug("Sono presenti "+resultListLI.size()+" dati....mi preparo per l'invio.");
						// invochiamo il servizio
						GENERAL_LOGGER.debug("Invochiamo il servizio");
						CreatePDFService service = new CreatePDFService();
						CreatePDFImpl fileWSPort = service.getCreatePDFPort();
						
						BindingProvider bindingProvider = (BindingProvider)fileWSPort;
						//SOAPBinding binding = (SOAPBinding) bindingProvider.getBinding();
						//binding.setMTOMEnabled(true);
						Map requestContext = bindingProvider.getRequestContext();
						
						String endPoint = get("ENDPOINT_FILE");
						GENERAL_LOGGER.debug("endPoint: "+endPoint);
						//String userName = get("USERNAME_FILE");
						//String password = get("PASSWORD_FILE");
	
						requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);
						
						Map<String, List<String>> headers = new HashMap<String, List<String>>();
						//authentication JAX-WS
						//headers.put("Username", Collections.singletonList(userName));
						//headers.put("Password", Collections.singletonList(password));
						requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
						
						//basic authentication HTTP
						//requestContext.put(BindingProvider.USERNAME_PROPERTY,userName);
						//requestContext.put(BindingProvider.PASSWORD_PROPERTY,password);						
								
						for (SchedaValutazione lt : resultListLI) 
						{							
							RisultatoEsito risultatoEsito = new RisultatoEsito();
							risultatoEsito.setStateid(lt.getId());
							risultatoEsito.setInviata(0); 
							
							//lt.setDataInvio(new Date());
								
							String lCXml1 = new String(lt.getLifecycleXml().getBytes("UTF-8"), "ISO-8859-1");
							
							byte[] bytes = lCXml1.getBytes();	
							try
							{
								Esito invokeFillPDFLifeCycle = fileWSPort.invokeFillPDFLifeCycle("xdpSchedaDiValutazionePDF", bytes);
								GENERAL_LOGGER.error("Result: "+invokeFillPDFLifeCycle.getResult());
								GENERAL_LOGGER.error("ResultDetails: "+invokeFillPDFLifeCycle.getResultDetails());
	
								if(invokeFillPDFLifeCycle.getResult().equals("KO"))
								{
									risultatoEsito.setInviata(-1); 
								}
								else
								{
									risultatoEsito.setrFile(invokeFillPDFLifeCycle.getFilePDF()); 
									risultatoEsito.setInviata(1); 
									risultatoEsito.setFileName("SchedaValutazione"+lt.getId()+".pdf");
								}
								GENERAL_LOGGER.debug("SchedaValutazione "+lt.getId()+" Flag Inviata : "+risultatoEsito.getInviata());
							}
							catch(Exception e)
							{
								GENERAL_LOGGER.error("Errore durante l'invocazione del servizio: ",e);
								if(risultatoEsito.getInviata() == 0)
								{ 
									risultatoEsito.setInviata(-1);
									GENERAL_LOGGER.debug("SchedaValutazione "+lt.getId()+" Flag Inviata : : "+risultatoEsito.getInviata());
								} 
							}
							finally
							{
								list.add(risultatoEsito);
							}
						}//for				
						GENERAL_LOGGER.debug("Fine invocazione del servizio");
					}
					else
					{
						trovato = false;
						GENERAL_LOGGER.debug("Non ci sono dati da poter inviare.");
					} 
				tx.commit();
			}
			catch(Exception e)
			{
				GENERAL_LOGGER.error("Errore durante l'invio ",e);
				try 
				{
					tx.rollback();
				} 
				catch (Exception e1) 
				{
					GENERAL_LOGGER.error("Errore durante il rollback ",e1);
				} 
				for (Iterator iterator = list.iterator(); iterator.hasNext();) 
				{
					RisultatoEsito esitoList1 = (RisultatoEsito) iterator.next();
		        	if(esitoList1.getInviata() == 0)
		        	{ 
		        		esitoList1.setInviata(-1);
		        		GENERAL_LOGGER.debug("SchedaValutazione "+esitoList1.getStateid()+" Flag Inviata : "+esitoList1.getInviata());
		        	}
				}
				//throw new RuntimeException(e);
			}
			finally
			{
				try 
				{
					if(trovato)
					{
						tx.begin();
						//indipendetemente da come va l'invocazione del servizio, devo aggiornare i record in lettera incarico
						for(Iterator iterator = list.iterator(); iterator.hasNext();) 
						{
							RisultatoEsito esitoList = (RisultatoEsito) iterator.next();
							List<SchedaValutazione> existIdList = emLa.createNamedQuery("SchedaValutazione.byid").
									setHint("javax.persistence.cache.retrieveMode", "BYPASS").
									setParameter("id", esitoList.getStateid()).getResultList();						
							GENERAL_LOGGER.debug("START update in SchedaValutazione");
							if(existIdList!=null && existIdList.size()>0)
							{
								for (SchedaValutazione existf : existIdList) 
								{
									existf.setInviata(esitoList.getInviata());
									existf.setDataInvio(new Date());
									GENERAL_LOGGER.debug("Aggiornamento in SchedaValutazione dello Stateid: "+esitoList.getStateid() +" - Inviata: "+esitoList.getInviata());
									if(esitoList.getInviata()!=-1)
									{
										byte myByte[]=esitoList.getrFile();
										GENERAL_LOGGER.debug("length in salvataggio: "+esitoList.getrFile().length);
										existf.setReturnFile(myByte);
										GENERAL_LOGGER.debug("nome file in salvataggio: "+esitoList.getFileName());
										existf.setReturnFileName(esitoList.getFileName());
									}
								    emLa.persist(existf);
								}
							}
						} 
						tx.commit();
					}			
					sbloccaSemaforo(tipoDoc);					
					GENERAL_LOGGER.debug("**** END SEMAFORO VERDE -- INVIO DATI SchedaValutazione ****");
				} 
				catch (Exception e) 
				{
					GENERAL_LOGGER.error("Errore durante il salvataggio in SchedaValutazione ",e);
					try 
					{
						tx.rollback();
					} 
					catch (Exception e1) 
					{
						GENERAL_LOGGER.error("Errore durante il rollback ",e1);
					} 
					sbloccaSemaforo(tipoDoc);
					throw new RuntimeException(e);
				} 	
			}
		}
		else
		{
			GENERAL_LOGGER.debug("**** SEMAFORO ROSSO -- INVIO DATI SchedaValutazione ****");
		}
	}
		
		@Override
	  //@TransactionAttribute(TransactionAttributeType.REQUIRED) 
		public void init() 
		{
			GENERAL_LOGGER.debug("Start creazione timerService");
			for (Object obj : timerService.getTimers())
			{
				Timer timer = (Timer) obj;
				String scheduled = (String) timer.getInfo();
				if(scheduled.equalsIgnoreCase("SchedaValutazioneTimer"))
				{
					timer.cancel();
					GENERAL_LOGGER.debug("Timer "+scheduled+" cancellato");
				}
			}
			
			timerService.createTimer((long)(INITIAL_DURATION),(long)(INTERVAL_DURATION), "SchedaValutazioneTimer");
			GENERAL_LOGGER.debug("Nuovo Timer SchedaValutazioneTimer creato ogni "+INTERVAL_DURATION+" millisecondi!");
		}
	
		@Timeout
		@Override
		@TransactionAttribute(TransactionAttributeType.REQUIRED) 
		public void doTask(Timer timer)
		{
			//GENERAL_LOGGER.debug("SCATTATO IL TIMEOUT ---> "+timer.getInfo().toString());
			try
			{
				emLa.clear();
				//Qui possiamo inserire un controllo orario in cui deve partire l'invio
				sendFile();
			}
			catch(Exception e)
			{
				GENERAL_LOGGER.error("Errore doTask:",e);
				throw new RuntimeException(e);
			}
		}
	
	/*	public static void printExec(Exception e)
	    {
	    	StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GENERAL_LOGGER.error("Exception: "+exceptionAsString);
	    }*/
	
/*		private static class Authenticator extends javax.mail.Authenticator 
		{
		    private PasswordAuthentication authentication;
		
		    public Authenticator(String username, String password) 
		    {
		    	authentication = new PasswordAuthentication(username, password);
		    }
		
		    protected PasswordAuthentication getPasswordAuthentication() 
		    {
		    	return authentication;
		    }
		}
*/
		public boolean getSemaforo(String tipoDoc) 
		{
			//GENERAL_LOGGER.debug("getSemaforo ---> "+tipoDoc);
			boolean value = false;
			try
			{
				tx.begin();
				InvioDati c=emLa.find(InvioDati.class,tipoDoc);
				if(c.getDataEsecuzione()==null)
				{
					value = true;
					java.util.Date a = new java.util.Date();
					GENERAL_LOGGER.debug("Semaforo "+tipoDoc+" settato a "+a);
					c.setDataEsecuzione(a);
					emLa.persist(c);
				}
				tx.commit();
			}
			catch(Exception e)
			{
				GENERAL_LOGGER.error("getSemaforo Exception: "+e);	
			}
			return value;
		}

		public void sbloccaSemaforo(String tipoDoc) 
		{
			try
			{
				Thread.sleep(60*1000);
				tx.begin();
				InvioDati c=emLa.find(InvioDati.class,tipoDoc);
				c.setDataEsecuzione(null);
				emLa.persist(c);
				tx.commit();
			}
			catch(Exception e)
			{
				GENERAL_LOGGER.error("sbloccaSemaforo Exception: "+e);	
			}
			//GENERAL_LOGGER.debug("Semaforo "+tipoDoc+" settato a null");
		}
		
		public String get(String propertyName) 
		{
			List<Property> retList = emLa.createNamedQuery("Property.findByKey").setParameter("key", propertyName).
					setHint("javax.persistence.cache.retrieveMode", "BYPASS").
					getResultList();
			Property prop = retList.iterator().next();
			return prop.getValue();
		}
		
	    public static Date getDate(String date) throws ParseException 
	    {
	        return new Date(dateFormat.parse(date).getTime());
	    }
		
	    public static String getDateAsString(Date date) 
	    {
	        return dateFormat.format(date);
	    }
}
