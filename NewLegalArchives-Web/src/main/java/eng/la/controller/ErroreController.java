package eng.la.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("erroreController")
public class ErroreController {

	
	private static final String PAGINA_ERRORE = "errore";

	@RequestMapping("/errore")
	public String errore(Model model, Locale locale, HttpServletRequest request) {
		// engsecurity VA
				//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				//htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		
		return PAGINA_ERRORE;
	}
}
