package it.eng.la.ws;

import it.eng.la.ws.dto.Esito;
import it.eng.la.ws.service.TibcoPecService;
import it.eng.laws.entity.TibcoPecEntity;

import javax.ejb.EJB;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.log4j.Logger;

@WebService(serviceName = "TibcoPecService")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class TibcoPecBean 
{
	@EJB
	TibcoPecService service;
	
	protected final static Logger GENERAL_LOGGER = Logger.getLogger(TibcoPecBean.class.getName());

    public Esito notificaPECMail(
    		 					 @WebParam(name = "UUId") String UUId,
    		 					 @WebParam(name = "LaId") String LaId,
    		 					 @WebParam(name = "PECMittente") String PECMittente,
    							 @WebParam(name = "PECDestinatario") String PECDestinatario,
					    		 @WebParam(name = "PECOggetto") String PECOggetto,
					    		 @WebParam(name = "PECDataRicezione") String PECDataRicezione)
    {
    	GENERAL_LOGGER.debug("**** START WS TibcoPec STEP 5 ****");
    	
    	GENERAL_LOGGER.debug("PECMittente: "+PECMittente);
    	GENERAL_LOGGER.debug("PECDestinatario: "+PECDestinatario);
    	GENERAL_LOGGER.debug("UUId: "+UUId);
    	GENERAL_LOGGER.debug("LaId: "+LaId);
    	GENERAL_LOGGER.debug("pecOggetto: "+PECOggetto);
    	GENERAL_LOGGER.debug("pecDataRicezione: "+PECDataRicezione);

    	Esito response = new Esito();
    	boolean check = true;
    	String returnMessage = "";
    	String text = "Errore: ";
    	
    		//qui invochiamo il servizio
    		TibcoPecEntity c = new TibcoPecEntity();
    		c.setUUId(UUId);
    		c.setPECDestinatario(PECDestinatario);
    		c.setPECMittente(PECMittente);
    		c.setLaId(LaId);
    		c.setPECOggetto(PECOggetto);
    		c.setPECDataRicezione(PECDataRicezione);
    		returnMessage = service.insert(c);
    	
    	response.setEsito(returnMessage.equalsIgnoreCase("")?"OK":"KO");
    	response.setMessaggio(returnMessage.equalsIgnoreCase("")?null:text+returnMessage);
    	
    	GENERAL_LOGGER.debug("**** END WS TibcoPec STEP 5 ****");
    	return response;
    }  
}
