package eng.la.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("notAutController")
public class NotAutController {

	
	private static final String PAGINA_NOTAUT = "notAut";
	private static final String PAGINA_NOTDISP = "notDisp";

	@RequestMapping("/notAut")
	public String errore(Model model, Locale locale, HttpServletRequest request) {
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		return PAGINA_NOTAUT;
	}
	
	@RequestMapping("/notDisp")
	public String notdisp(Model model, Locale locale, HttpServletRequest request) {
		
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		return PAGINA_NOTDISP;
	}
}
