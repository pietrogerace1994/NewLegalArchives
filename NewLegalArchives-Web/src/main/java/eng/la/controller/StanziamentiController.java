package eng.la.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eng.la.business.ReportingService;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("stanziamentiController")
public class StanziamentiController {

	private static final String PAGINA_STANZIAMENTI_INDEX = "stanziamenti/stanziamentiIndex";


	@Autowired
	ReportingService reportingService;

	public void setReportingService(ReportingService reportingService) {
		this.reportingService = reportingService;
	}


	@RequestMapping(value = "/stanziamenti/index")
	public String stanziamentiIndex(HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) throws Throwable {
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);

		return PAGINA_STANZIAMENTI_INDEX;

	}

	@RequestMapping(value = "/stanziamenti/export-report", method=RequestMethod.POST)
	public String exportReport(HttpServletRequest request, HttpServletResponse respons, Model model,
			Locale locale) {
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		int annoFinanziario=0;
		if(request.getParameter("annoFinanziario")!=null && !request.getParameter("annoFinanziario").trim().equals("")){
			try{
				annoFinanziario=Integer.parseInt(request.getParameter("annoFinanziario").trim());

			}catch(NumberFormatException e){
				model.addAttribute("errore", "errore.stanziamenti.data");	
			}


			try {

				if(!reportingService.exportExcellStanziamenti(annoFinanziario, respons, locale.getLanguage().toUpperCase())){
					model.addAttribute("listRespons", "errore.stanziamenti.respons");	
					return PAGINA_STANZIAMENTI_INDEX; 
				}
				return null;

			} catch (Throwable e) {
				model.addAttribute("errore", "errore.stanziamenti.generic");
				return PAGINA_STANZIAMENTI_INDEX; 

			}


		}else{
			model.addAttribute("errore", "errore.stanziamenti.data");	
		}



		return PAGINA_STANZIAMENTI_INDEX;
	} 

}
