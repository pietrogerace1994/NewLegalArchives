package eng.la.controller;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.ProformaService;
import eng.la.business.RiassegnaService;
import eng.la.business.UtenteService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.business.workflow.FascicoloWfService;
import eng.la.business.workflow.IncaricoWfService;
import eng.la.business.workflow.ProformaWfService;
import eng.la.business.workflow.StepWfService;
import eng.la.model.Fascicolo;
import eng.la.model.SettoreGiuridico;
import eng.la.model.Societa;
import eng.la.model.TipologiaFascicolo;
import eng.la.model.Utente;
import eng.la.model.rest.RiassegnaRest;
import eng.la.model.rest.RicercaRiassegnaRest;
import eng.la.model.view.StatoFascicoloView;
import eng.la.model.view.UtenteView;
import eng.la.util.Pagebol;
import eng.la.util.costants.Costanti;
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("riassegnaController")
public class RiassegnaController {
	private static final String PAGINA_RIASSEGNA_INDEX= "riassegna/riassegna-fascicolo";

	@Autowired
	private RiassegnaService riassegnaService;
	@Autowired
	FascicoloWfService fascicoloWfService;
	@Autowired
	StepWfService stepWfService;
	@Autowired
	EmailNotificationService emailNotificationService; 
	
	@Autowired
	IncaricoWfService incaricoWfService;
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	ProformaService proformaService;
	
	@Autowired
	ProformaWfService proformaWfService;
	
	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	
	@RequestMapping(value = "/riassegna/index")
	public String index(HttpServletRequest request,HttpServletResponse respons, Model model, Locale locale) throws Throwable {
		
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		UtenteView utenteView=null;
		Utente utente=null;
		List<Utente> comboOwnerFascicoli=null;
		List<Utente> comboRiassegnazione=null;
		List<TipologiaFascicolo> tipologiaFascicolo=null;
		List<SettoreGiuridico> settoreGiuridico=null;
		List<Societa> societa=null;
		try{
		
		utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	
		String utenteProcessamento=utenteView.getVo().getUseridUtil();
		model.addAttribute("utenteProcessamento", utenteProcessamento); 
		
		// SOLO UTENTE AMMINISTRATORE
		if(utenteView!=null && utenteView.isAmministratore()){
			comboOwnerFascicoli=riassegnaService.getListaLegaleInternoOwnerFascicolo((long)0);//0=tutti
			model.addAttribute("legaleInterno", comboOwnerFascicoli); 
			comboRiassegnazione=riassegnaService.getListaUtentiNotAmmistrativiNotAmministratore();
			model.addAttribute("utentiRiassegnazione", comboRiassegnazione); 
			tipologiaFascicolo=riassegnaService.getListaTipologiaFascicolo();
			model.addAttribute("tipologiaFascicolo", tipologiaFascicolo);
			settoreGiuridico=riassegnaService.getListaSettoreGiuridico();
			model.addAttribute("settoreGiuridico", settoreGiuridico);
			societa=riassegnaService.getListaSocieta();
			model.addAttribute("listaSocieta", societa);
	    }
		
		
		// UTENTE GENERICO
		if(utenteView!=null && !utenteView.isAmministratore()){
			utente = utenteView.getVo();
			model.addAttribute("utenteNominativo", utente.getNominativoUtil());
			model.addAttribute("utenteMatricola", utente.getMatricolaUtil());
			//comboOwnerFascicoli=riassegnaService.getListaLegaleInternoOwnerFascicolo((long)0);//0=tutti
			//model.addAttribute("legaleInterno", comboOwnerFascicoli); 
			comboRiassegnazione=riassegnaService.getListaUtentiNotAmmistrativiNotAmministratore();
			model.addAttribute("utentiRiassegnazione", comboRiassegnazione); 
			tipologiaFascicolo=riassegnaService.getListaTipologiaFascicolo();
			model.addAttribute("tipologiaFascicolo", tipologiaFascicolo);
			settoreGiuridico=riassegnaService.getListaSettoreGiuridico();
			model.addAttribute("settoreGiuridico", settoreGiuridico);
			societa=riassegnaService.getListaSocieta();
			model.addAttribute("listaSocieta", societa);
	    }
		
	
		
		}catch(Exception e){ }
		
		return (utenteView!=null)? PAGINA_RIASSEGNA_INDEX:"redirect:/index.action";
		
	}
	
	
 
		 
	 
	@RequestMapping(value = "/riassegna/ricercaAssegnaFascicoli",method = RequestMethod.GET,produces = "application/json")
	public @ResponseBody RicercaRiassegnaRest ricercaFascicoli( HttpServletRequest request, Model model, Locale locale) throws Throwable {


		try {
			Integer rowCount =new Integer((request.getParameter("rowCount")!=null && !request.getParameter("rowCount").equals(""))?request.getParameter("rowCount"):"10");
			if(rowCount==null || (rowCount!=null && rowCount.intValue()<10))
				rowCount=new Integer(10);
			Integer current= new Integer((request.getParameter("current")!=null && !request.getParameter("current").equals(""))?request.getParameter("current"):"1");
			if(current==null || (current!=null && current.intValue()<0))
				current=new Integer(1);
			
			String legaleInterno=(request.getParameter("legaleInterno")!=null && !request.getParameter("legaleInterno").equals(""))?request.getParameter("legaleInterno"):"0";
			
			String societa=(request.getParameter("societa")!=null && !request.getParameter("societa").equals(""))?request.getParameter("societa"):"0";
			
			String settoreGiuridico=(request.getParameter("settoreGiuridico")!=null && !request.getParameter("settoreGiuridico").equals(""))?request.getParameter("settoreGiuridico"):"0";
			
			String tipologiaFascicolo=(request.getParameter("tipologiaFascicolo")!=null && !request.getParameter("tipologiaFascicolo").equals(""))?request.getParameter("tipologiaFascicolo"):"0";
			
			String nomeFascicolo=(request.getParameter("nomeFascicolo")!=null && !request.getParameter("nomeFascicolo").equals(""))?request.getParameter("nomeFascicolo"):"0";
			
			societa=societa.equals("0")?null:societa;
			settoreGiuridico=settoreGiuridico.equals("0")?null:settoreGiuridico;
			tipologiaFascicolo=tipologiaFascicolo.equals("0")?null:tipologiaFascicolo;
			nomeFascicolo=nomeFascicolo.equals("0")?null:nomeFascicolo;
			
			
			RicercaRiassegnaRest riassegnaRest= new RicercaRiassegnaRest();
			 
			List<BigDecimal> listIdFascicoli=riassegnaService.getIDFascicoliXRiassegna(legaleInterno, societa, tipologiaFascicolo, settoreGiuridico, nomeFascicolo);
			 
			Pagebol<BigDecimal> fascicolosID=new Pagebol<BigDecimal>(listIdFascicoli,rowCount.intValue());
 
			List<BigDecimal> fascicoliID=fascicolosID.getPages(current.intValue());
			
			List<Fascicolo> fascicoliList= new ArrayList<Fascicolo>();
			for(BigDecimal b:fascicoliID){
				
				Fascicolo fasc=riassegnaService.getFascicoli(b.longValue());
				if(fasc!=null)
				fascicoliList.add(fasc);
				
			}
			
			
			
			RiassegnaRest rest=  null;
			List<RiassegnaRest> listRest= new ArrayList<RiassegnaRest>();
			if(fascicoliList!=null){
				for(Fascicolo f: fascicoliList){	
					 rest= new RiassegnaRest();
					 rest.setId(f.getId());
					 rest.setNumeroFascicolo(f.getNome());
					 StatoFascicoloView statoFascicoloView = anagraficaStatiTipiService.leggiStatiFascicolo(
							 f.getStatoFascicolo().getCodGruppoLingua(), locale.getLanguage().toUpperCase());
					 rest.setStato(statoFascicoloView.getVo().getDescrizione());
					 List<Utente> owners=riassegnaService.getListaLegaleInternoOwnerFascicolo(f.getId());
					 if(owners!=null && owners.size()>0){
						 Utente owner=owners.get(0);
						 rest.setOwner(owner.getNominativoUtil());
						 rest.setIdOwner(owner.getMatricolaUtil());
					}
					 listRest.add(rest);
				 
				}
				riassegnaRest.setRowCount(rowCount.longValue());
				riassegnaRest.setTotal(listIdFascicoli!=null?(long)listIdFascicoli.size():(long)0);
				riassegnaRest.setCurrent(current.longValue());
				riassegnaRest.setRows(listRest);
			}
			return riassegnaRest;
		} catch (Exception e) {
			RicercaRiassegnaRest riassegnaRest= new RicercaRiassegnaRest();
			return riassegnaRest;
		}
	
	}
 
	 
	
	@RequestMapping(value = "/riassegna/riassegna",method = RequestMethod.POST,produces="text/html")
	public @ResponseBody String riassegna(@RequestParam("idFas") String idFas,@RequestParam("owner") String owner,HttpServletRequest request, Model model, Locale locale) throws Throwable {
		// engsecurity VA 
				//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				//htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		String toReturn = "";
		try{
			toReturn = riassegnaService.riassegnazioneMultipla(idFas, owner, request, model, locale);
			model.addAttribute("successMessage", "messaggio.operazione.ok");
		}catch (Exception e) {
			model.addAttribute("errorMessage", "errore.generico");
		}
		return toReturn;
		 
	}
	 
}
