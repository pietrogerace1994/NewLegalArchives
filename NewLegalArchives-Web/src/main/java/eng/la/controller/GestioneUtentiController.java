package eng.la.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eng.la.business.UtenteService;
import eng.la.model.rest.GestioneUtenteRest;
import eng.la.model.rest.RicercaGestioneUtenteRest;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.TipologiaFascicoloView;
import eng.la.model.view.UtenteView;
import eng.la.util.Pagebol;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("gestioneUtentiController")
public class GestioneUtentiController {
	private static final String PAGINA_LISTA_UTENTI = "gestioneUtenti/gestioneIndex";
	//DARIO ********************************************************************************
	private static final String PAGINA_LISTA_ASSEGNATARI = "gestioneUtenti/lista_assegnatari";
	//***************************************************************************************
	@Autowired
	private UtenteService utenteService;	

	public void setUtenteService(UtenteService utenteService) {
		this.utenteService = utenteService;
	}


	@RequestMapping(value = "/gestioneUtenti/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model, Locale locale) throws Throwable {

		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		UtenteView utenteView=null;

		try{

			utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	
			String utenteProcessamento=utenteView.getVo().getUseridUtil();
			model.addAttribute("utenteProcessamento", utenteProcessamento); 

			// SOLO UTENTE AMMINISTRATORE
			if(utenteView!=null && utenteView.isAmministratore()){
				return PAGINA_LISTA_UTENTI;

			}

		}catch(Exception e){}


		return PAGINA_LISTA_UTENTI;
	}




	@RequestMapping(value = "/gestioneUtenti/cercautenti",method = RequestMethod.GET,produces = "application/json")
	public @ResponseBody RicercaGestioneUtenteRest cercaUtenti( HttpServletRequest request, Model model, Locale locale) throws Throwable {

		Integer rowCount =new Integer((request.getParameter("rowCount")!=null && !request.getParameter("rowCount").equals(""))?request.getParameter("rowCount"):"10");
		if(rowCount==null || (rowCount!=null && rowCount.intValue()==-1))
			rowCount=new Integer(100000);
		if(rowCount==null || (rowCount!=null && rowCount.intValue()<10))
			rowCount=new Integer(10);
		Integer current= new Integer((request.getParameter("current")!=null && !request.getParameter("current").equals(""))?request.getParameter("current"):"1");
		if(current==null || (current!=null && current.intValue()<0))
			current=new Integer(1);
		UtenteView utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	

		RicercaGestioneUtenteRest ricercaGestioneUtenteRest=new RicercaGestioneUtenteRest();
		List<GestioneUtenteRest> utentiRest=null;
		// SOLO UTENTE AMMINISTRATORE
		if(utenteView!=null && utenteView.isAmministratore()){

			List<UtenteView> utentiVs= utenteService.leggiUtenti(false);	
			utentiRest= new ArrayList<GestioneUtenteRest>();
			if(utentiVs!=null){

				for(UtenteView ut:utentiVs){
					if(ut.getVo()!=null){
						GestioneUtenteRest g= new GestioneUtenteRest();
						g.setUserid(ut.getVo().getUseridUtil());
						g.setNominativo(ut.getVo().getNominativoUtil());
						g.setAssente(ut.getVo().getAssente());
						g.setAzione(ut.getVo().getUseridUtil());
						utentiRest.add(g);
					}
				}


			}

			Pagebol<GestioneUtenteRest> pagebolUtentiRow=new Pagebol<GestioneUtenteRest>(utentiRest,rowCount.intValue());
			List<GestioneUtenteRest> utentiPagRest=pagebolUtentiRow.getPages(current.intValue());
			ricercaGestioneUtenteRest.setCurrent(current.longValue());
			ricercaGestioneUtenteRest.setRowCount(rowCount.longValue());
			ricercaGestioneUtenteRest.setTotal(utentiRest.size());
			ricercaGestioneUtenteRest.setRows(utentiPagRest);
		}
		return ricercaGestioneUtenteRest;
	}

	@RequestMapping(value = "/gestioneUtenti/isAssente",method = RequestMethod.POST,produces="text/html")
	public @ResponseBody String isAssente(@RequestParam("userid") String userid,@RequestParam("assente") String assente,HttpServletRequest request, Model model, Locale locale) throws Throwable {

		UtenteView utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	

		// SOLO UTENTE AMMINISTRATORE
		if(utenteView!=null && utenteView.isAmministratore()){	

			if(utenteService.modificaPresenzaUtente(userid, assente)!=null)
				return "OK";
			else
				return "KO";

		}
		return "UTENTE-NON-AUTORIZZATO";
	}
	
	//DARIO ********************************************************************************
	@RequestMapping(value = "/gestioneUtenti/cercaAssegnatari", method = RequestMethod.GET)
	public String cercaResponsabili(HttpServletRequest request, Model model, Locale locale) {

		try {
			
			UtenteView utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			
			List<UtenteView> assegnatari = utenteService.leggiAssegnatari(utenteView);
			model.addAttribute("assegnatari",assegnatari);
			
			
			
			return PAGINA_LISTA_ASSEGNATARI;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}
	//*********************************************************************************************************
	
}
