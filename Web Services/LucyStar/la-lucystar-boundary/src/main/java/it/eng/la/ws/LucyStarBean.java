package it.eng.la.ws;

import it.eng.la.ws.dto.Risultato;
import it.eng.la.ws.service.LucyStarService;
import it.eng.laws.entity.ContabilizzazioneEntity;

import javax.ejb.EJB;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.log4j.Logger;

@WebService(serviceName = "LucyStarService")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class LucyStarBean {

	@EJB
	LucyStarService service;
	
	protected final static Logger GENERAL_LOGGER = Logger.getLogger(LucyStarBean.class.getName());

    public Risultato inserisciContabilizzazione(@WebParam(name = "numeroProtFiscale") String numeroProtFiscale,
    		@WebParam(name = "dataRegistrazione") String dataRegistrazione,
    		@WebParam(name = "numeroFattura") String numeroFattura,
    		@WebParam(name = "dataFattura") String dataFattura,
    		@WebParam(name = "codiceFornitore") String codiceFornitore,
    		@WebParam(name = "sistemaMittente") String sistemaMittente,
    		@WebParam(name = "sistemaDestinatario") String sistemaDestinatario){
    	
    	GENERAL_LOGGER.debug("**** START INVOCAZIONE WS LUCYSTAR FLUSSO 5 ****");
    	
    	GENERAL_LOGGER.debug("numeroProtFiscale: "+numeroProtFiscale);
    	GENERAL_LOGGER.debug("dataRegistrazione: "+dataRegistrazione);
    	GENERAL_LOGGER.debug("numeroFattura: "+numeroFattura);
    	GENERAL_LOGGER.debug("dataFattura: "+dataFattura);
    	GENERAL_LOGGER.debug("codiceFornitore: "+codiceFornitore);
    	GENERAL_LOGGER.debug("sistemaMittente: "+sistemaMittente);
    	GENERAL_LOGGER.debug("sistemaDestinatario: "+sistemaDestinatario);
    	
    	Risultato response = new Risultato();
    	boolean check = true;
    	String returnMessage = "";
    	String text = "Errore: ";
    	
    	if(!sistemaMittente.equalsIgnoreCase("LucyStar")){
    		check = false;
        	returnMessage = "Sistema Mittente non riconosciuto.\n\n";
        	GENERAL_LOGGER.debug(returnMessage.substring(0,returnMessage.length()-2));
    	}

    	if(!sistemaDestinatario.equalsIgnoreCase("Legal Archives")){
    		check = false;
        	returnMessage = "Sistema Destinatario non riconosciuto.\n\n";
        	GENERAL_LOGGER.debug(returnMessage.substring(0,returnMessage.length()-2));
    	}
    	
    	if(check){
    		//qui invochiamo il servizio
    		ContabilizzazioneEntity c = new ContabilizzazioneEntity();
    		c.setCodiceFornitore(codiceFornitore);
    		c.setDataFattura(dataFattura);
    		c.setDataRegistrazione(dataRegistrazione);
    		c.setNumeroFattura(numeroFattura);
    		c.setNumeroProtFiscale(numeroProtFiscale);
    		
    		returnMessage = service.insert(c);
    	}
    	
    	response.setEsito(returnMessage.equalsIgnoreCase("")?"OK":"KO");
    	response.setMessaggio(returnMessage.equalsIgnoreCase("")?null:text+returnMessage);
    	
    	GENERAL_LOGGER.debug("**** END INVOCAZIONE WS LUCYSTAR FLUSSO 5 ****");
    	return response;
    	
    }  
}
