package it.eng.la.ws;

import it.eng.la.ws.dto.Dato;
import it.eng.la.ws.dto.HrRequest;
import it.eng.la.ws.dto.Response;
import it.eng.la.ws.entity.Item;
import it.eng.la.ws.service.HrService;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.log4j.Logger;

@WebService(serviceName = "HrService")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class HrBean {

	@EJB
	HrService service;
	
	protected final static Logger GENERAL_LOGGER = Logger.getLogger(HrBean.class.getName());

    public Response aggiornaUtente(@WebParam(name = "hrRequest") HrRequest hrRequest){
    	
    	GENERAL_LOGGER.debug("**** START INVOCAZIONE WS HR ****");
    	
    	Response response = new Response();
    	boolean check = true;
    	String returnMessage = "";
    	String text = "Errore: ";
    	
    	String dataAggiornamento = hrRequest.getDataaggiornamento();
    	if(dataAggiornamento==null || dataAggiornamento.equalsIgnoreCase("")){
    		check = false;
        	returnMessage = "Data di aggiornamento non valorizzata.\n\n";
        	GENERAL_LOGGER.debug(returnMessage.substring(0,returnMessage.length()-2));
    	}
    	
    	List<Dato> dati = hrRequest.getDati();
    	List<Item> items = new ArrayList<Item>();
    	if(dati==null || dati.size()==0){
    		check = false;
        	returnMessage = "Dati non presenti.\n\n";
        	GENERAL_LOGGER.debug(returnMessage.substring(0,returnMessage.length()-2));
    	} else {
    		for (Dato dato : dati) {
    			Item item = new Item();
    			item.setLabel(dato.getLabel());
    			item.setValore(dato.getValore());
    			items.add(item);
			}
    	}
    	
    	if(check){
    		returnMessage = service.insert(dataAggiornamento, items);
    	}
    	
    	response.setEsito(returnMessage.equalsIgnoreCase("")?"OK":"KO");
    	response.setDescrizione(returnMessage.equalsIgnoreCase("")?null:text+returnMessage);
    	
    	GENERAL_LOGGER.debug("**** END INVOCAZIONE WS HR ****");
    	return response;
    	
    }  
}
