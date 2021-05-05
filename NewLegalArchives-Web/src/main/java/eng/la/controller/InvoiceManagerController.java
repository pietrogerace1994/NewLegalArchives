package eng.la.controller;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eng.la.business.workflow.ProformaWfServiceImpl;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.custom.FatturaInvoceManager;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("invoiceManagerController")
@SessionAttributes("invoiceManagerView")
public class InvoiceManagerController{
	
	private static final String MODEL_VIEW_NOME = "invoiceManagerView";
	
	private static final org.apache.log4j.Logger logger = Logger.getLogger(InvoiceManagerController.class);
	
	@RequestMapping(value = "/invoiceManager/uploadFile", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest uploadFile( HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file) {  
		
		logger.info("Chiamata ricevuta per invoiceManager/uploadFile");
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
		
		List<FatturaInvoceManager> fatturaIMList = new ArrayList<FatturaInvoceManager>();
		try{
			String fileName = file.getOriginalFilename();
			logger.info("Processing file: " + fileName);
			
			InputStream input = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			
			XSSFSheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();
			
			//JSONArray jArray = new JSONArray();
			
			logger.info("Total rows to be processed: "+ firstSheet.getPhysicalNumberOfRows());
			
			//Skipping Header
			rowIterator.next();
			
			while(rowIterator.hasNext()){
				
				
				Row row = rowIterator.next();
				String numFattura;
				String dataFattura;
				String protIn;
				String codFornitore;
				String numFatturaLegal;
				
				DataFormatter formatter = new DataFormatter();
				
				if(null != row.getCell(CellReference.convertColStringToIndex("E"))){
					numFattura = formatter.formatCellValue(row.getCell(CellReference.convertColStringToIndex("E")));
				}else{
					numFattura = "";
					logger.info("numFattura is null");
					return new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.campo.nrdoc.obbligatorio");
				}
				if(null != row.getCell(CellReference.convertColStringToIndex("G"))){
					dataFattura = row.getCell(CellReference.convertColStringToIndex("G")).toString();
				}else{
					dataFattura = "";
					
				}
				if(null != row.getCell(CellReference.convertColStringToIndex("B"))){
					protIn = row.getCell(CellReference.convertColStringToIndex("B")).toString();
				}else{
					protIn = "";
					logger.info("protIn is null");
					return new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.campo.docid.obbligatorio");
				}
				if(null != row.getCell(CellReference.convertColStringToIndex("C"))){
					codFornitore = row.getCell(CellReference.convertColStringToIndex("C")).toString();
				}else{
					codFornitore = "";
				}
				if(null != row.getCell(CellReference.convertColStringToIndex("F"))){
					numFatturaLegal = formatter.formatCellValue(row.getCell(CellReference.convertColStringToIndex("F")));
				}else{
					numFatturaLegal = "";
					logger.info("numFatturaLegal is null");
					return new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.campo.numfatturalegal.obbligatorio");
				}
				
				/*JSONObject jo = new JSONObject();
				jo.put("numFattura", adjustItem(numFattura));
				jo.put("dataFattura", dataFattura);
				jo.put("protIn", adjustItem(protIn));
				jo.put("codFornitore", adjustItem(codFornitore));
				jo.put("numFatturaLegal", adjustItem(numFatturaLegal));
				jArray.put(jo);*/
				
				FatturaInvoceManager fatturaIM = new FatturaInvoceManager();
				fatturaIM.setCodFornitore(adjustItem(codFornitore));
				fatturaIM.setDataFattura(dataFattura);
				fatturaIM.setProtIn(adjustItem(protIn));
				fatturaIM.setNumFattura(adjustItem(numFattura));
				fatturaIM.setNumFatturaLegal(adjustItem(numFatturaLegal));
				
				fatturaIMList.add(fatturaIM);
				
			}
			
			logger.info("List<FatturaIMList> to be sent: " + fatturaIMList.toString());
			return inviaRichiestaInvoiceManager(fatturaIMList);
			
			
			/*
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<String> entity = new HttpEntity<>(jArray.toString(), headers);
			
			ResponseEntity<String> s = null;
			
			try{
				s = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);
				logger.info("Response from InvoiceManagerService: " + s.getStatusCode() +" | " + s.getBody());
				
				if(s.getStatusCode().value() >= 400){
					return new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.invoicemanagerservice.response");
				}
			}catch(Exception e){
				logger.info("Exception in invoking InvoiceManagerService. Message: " + e.getMessage());
				e.printStackTrace();
				return new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.invoicemanagerservice.request");
			}
			*/
			
			
			
		}catch(Exception e){
			logger.info("General Exception in InvoiceManagerController. Message: " + e.getMessage());
			e.printStackTrace();
			return new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.invoicemanagercontroller.general");
		}
		
	}

	
	private String adjustItem(String item) {
		if(item.contains(".")){
			String[] itemToReturnArray = item.split("\\.");
			return itemToReturnArray[0];
		}
		return item;
	}
	
	private RisultatoOperazioneRest inviaRichiestaInvoiceManager(List<FatturaInvoceManager> fatturaIMList){
		
		InputStream is = null;
		String endpoint = null;
		try {
			is = InvoiceManagerController.class.getResourceAsStream("/config.properties");
			Properties properties = new Properties();
			properties.load(is);
			endpoint = properties.getProperty("invoiceManager.link");
			logger.info("invoiceManager.link: " +endpoint);
		}
		catch (Exception e) {
			logger.info("Errore durante la lettura delle properties per endpoint service di invoiceManager: "+ e.getMessage());
			e.printStackTrace();
			return new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.retrieveproperty");
		}
		System.setProperty("javax.xml.soap.MessageFactory", "com.sun.xml.internal.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl");
		SOAPConnectionFactory connection_factory = null;
		
		SOAPConnection connection = null;
		SOAPMessage messageToSend = null;
		SOAPMessage messageToReceive = null;
		
		try{
			
			connection_factory = SOAPConnectionFactory.newInstance();
			
			connection = connection_factory.createConnection();
			messageToSend = generaMessaggioSOAP(fatturaIMList);
			logger.info("MessageToSend: \n"+ messageToSend.toString());
			
			messageToReceive = connection.call(messageToSend, endpoint);
				        
			connection.close();
			
			//verifica dell'esito dell'operazione
			SOAPBody received_body = messageToReceive.getSOAPBody();
			String received_body_string = received_body.toString();
			logger.info("ReceivedBody: \n" + received_body_string);
			
			
			if(!received_body_string.contains("<status>200</status>")){
				return new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.invoicemanagercontroller.general");
			}
			
		}catch(SOAPException e){
			System.out.println("Errore SOAP durante l'invio dati verso il servizio InvoiceManagerService.\n"+e.getMessage());
			e.printStackTrace();
			return new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "errore.invoicemanagercontroller.general");
		}
		return new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
	}


	private SOAPMessage generaMessaggioSOAP(List<FatturaInvoceManager> fattureIMList) {
		
		String soapenv = null;
		String namespace = null;
		String uri = null;
		String service = null;
		MessageFactory message_factory = null;
		SOAPMessage message = null;
		SOAPPart part = null;
		SOAPEnvelope envelope = null;
		SOAPHeader header = null;
		SOAPBody body = null;
		SOAPElement root = null;

		
		try{
			
			soapenv = "soapenv";
			service = "alignInvoices";
			namespace = "ser";
			uri = "http://services.snam.ibm.com/";
			message_factory = MessageFactory.newInstance("SOAP 1.1 Protocol");
			message = message_factory.createMessage();
			part = message.getSOAPPart();
			
			envelope = part.getEnvelope();
			envelope.removeNamespaceDeclaration(envelope.getPrefix());
	        envelope.setPrefix("soapenv");
			envelope.addNamespaceDeclaration(namespace, uri);
			
			header = envelope.getHeader();
			header.removeNamespaceDeclaration(header.getPrefix());
			header.setPrefix("soapenv");
			
			body = envelope.getBody();
			body.removeNamespaceDeclaration(body.getPrefix());
			body.setPrefix("soapenv");
			
			root = body.addChildElement(service, namespace);
			
			for(FatturaInvoceManager fattura: fattureIMList){
				
				logger.info("Adding  to SOAPRequest fattura: " + fattura.toString());
				SOAPElement arg = root.addChildElement("arg0");
				
				SOAPElement codFornitore = arg.addChildElement("codFornitore");
				SOAPElement dataFattura = arg.addChildElement("dataFattura");
				SOAPElement numFattura = arg.addChildElement("numFattura");
				SOAPElement numFatturaLegal = arg.addChildElement("numFatturaLegal");
				SOAPElement protIn = arg.addChildElement("protIn");
				
				codFornitore.addTextNode(fattura.getCodFornitore());
				dataFattura.addTextNode(fattura.getDataFattura());
				numFattura.addTextNode(fattura.getNumFattura());
				numFatturaLegal.addTextNode(fattura.getNumFatturaLegal());
				protIn.addTextNode(fattura.getProtIn());
				
			}
			
			message.saveChanges();	

		}catch(SOAPException e){
			System.out.println("Errore durante la generazione del messaggio SOAP di richiesta del servizio InvoiceManager.\n"+e.getMessage());
			e.printStackTrace();
			return null;
		
		}
		return message;
	
	}
	

}
