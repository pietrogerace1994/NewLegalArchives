package eng.la.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.la.mongo.service.DdsManagerTOREMOVE;


import eng.la.model.rest.RisultatoOperazioneRest;

@Controller("ddsController")
@SessionAttributes("ddsManagerView")
public class DdsController {
	
	static DdsManagerTOREMOVE ddsManager = null;
	
	private static final Logger logger = Logger.getLogger(DdsController.class);
	
	@RequestMapping(value = "/ddsController/uploadFileTest", produces="application/json" )
	public @ResponseBody RisultatoOperazioneRest uploadFileTest(HttpServletRequest request, @RequestParam("file") MultipartFile file) { 
		
		if(ddsManager == null){
			ddsManager = new DdsManagerTOREMOVE();
		}
		
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		try{
			String documentTitle = file.getOriginalFilename();
			InputStream is = file.getInputStream();
			
			String response = ddsManager.insertDocument("LEG_ARC", documentTitle, null, "AllegatoDocument", null, is);
			logger.info("Response from ddsManager: " + response);
			
		}catch(Exception e){
			logger.error("DdsController.uploadFileTest() - ERROR - " +e.getMessage());
			e.printStackTrace();
			return new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		}
		
		return risultato;
	}
	
}
