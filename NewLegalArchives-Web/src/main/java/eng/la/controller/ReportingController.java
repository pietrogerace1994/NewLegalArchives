package eng.la.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import eng.la.business.ReportingService;
import eng.la.business.UtenteService;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.SettoreGiuridico;
import eng.la.model.Societa;
import eng.la.model.StatoAtto;
import eng.la.model.StatoFascicolo;
import eng.la.model.StatoIncarico;
import eng.la.model.TipoContenzioso;
import eng.la.model.TipologiaFascicolo;
import eng.la.model.Utente;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.view.UtenteView;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("reportingController")
public class ReportingController {
	private static final String PAGINA_REPORTING_INDEX= "reporting/reportingIndex";
	
	
	@Autowired
	ReportingService reportingService;
	
	public void setReportingService(ReportingService reportingService) {
		this.reportingService = reportingService;
	}
	@Autowired
	UtenteService utenteService;

        public void setUtenteService(UtenteService utenteService) {
		this.utenteService = utenteService;
	}
	
	
	@RequestMapping(value = "/reporting/index", method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse respons, Model model, Locale locale) throws Throwable {
		UtenteView utenteView=null;
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		try{
			utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	
			
			String operatoreSegreteria="no";
			String all="no";
			if(utenteView!=null && 
					utenteView.isOperatoreSegreteria() && 
					!utenteView.isAmministratore() &&
					!utenteView.isLegaleInterno() &&
					!utenteView.isResponsabile()){
				operatoreSegreteria="si";
				 
			}
			//per Atto
			List<Societa> ltsSocieta=null; 
			List<Utente> ltsDestinatario=null; 
			List<StatoAtto> ltsStatoAtto=null;
		
			//per Fascicolo
			List<TipologiaFascicolo> ltsTipologiaFascicolo=null;
			List<SettoreGiuridico> ltsSettoreGiuridico=null;
			
			List<StatoFascicolo> ltsStatoFascicolo=null;
			List<Utente> ltsOwner=null; //legale interno owner
			List<TipoContenzioso> ltsTipoContenzioso=null;

			//per Incarichi
			List<ProfessionistaEsterno> ltsProfessionistaEsterno=null; //anagrafica degli avvocati in elenco
			List<StatoIncarico> statoIncarico=null;
			
			if(utenteView!=null && utenteView.isAmministratore() ||
					utenteView.isLegaleInterno() ||
					utenteView.isResponsabile() ||
					utenteView.isOperatoreSegreteria()){
				 	all="si";
				 	
				 
				 ltsSocieta=reportingService.getlistSocieta();
				 model.addAttribute("ltsSocieta", ltsSocieta);
				 ltsDestinatario=reportingService.getGcDestinatario();
				 model.addAttribute("ltsDestinatario", ltsDestinatario);
				 ltsStatoAtto=reportingService.getListaStatoAttoPerLingua(locale.getLanguage().toUpperCase());
				 model.addAttribute("ltsStatoAtto", ltsStatoAtto);
			
				 //FASCICOLO
				 ltsTipologiaFascicolo=reportingService.getListTipologiaFascicolo(locale.getLanguage().toUpperCase());
				 model.addAttribute("ltsTipologiaFascicolo", ltsTipologiaFascicolo);
				 ltsSettoreGiuridico=reportingService.getListSettoreGiuridico(locale.getLanguage().toUpperCase());
				 model.addAttribute("ltsSettoreGiuridico", ltsSettoreGiuridico);
				 ltsStatoFascicolo=reportingService.getListStatoFascicolo(locale.getLanguage().toUpperCase());
				 model.addAttribute("ltsStatoFascicolo", ltsStatoFascicolo);
				 ltsOwner=reportingService.getListOwner();
				 model.addAttribute("ltsOwner", ltsOwner);
				 ltsTipoContenzioso=reportingService.getListTipoContenzioso(locale.getLanguage().toUpperCase());
				 model.addAttribute("ltsTipoContenzioso", ltsTipoContenzioso);
				 
				 //INCARICHI
				 ltsProfessionistaEsterno=reportingService.getListProfessionistaEsterno();
				 model.addAttribute("ltsProfessionistaEsterno", ltsProfessionistaEsterno);
				 statoIncarico=reportingService.getListStatoIncarico(locale.getLanguage().toUpperCase());
				 model.addAttribute("statoIncarico", statoIncarico);
				 
				// statoProforma=reportingService.getListStatoProforma(locale.getLanguage().toUpperCase());
				// model.addAttribute("statoProforma", statoProforma);
				 
			}
			
			model.addAttribute("isAll", all);
			
			model.addAttribute("isSegreteria", operatoreSegreteria);
			
			
		}catch(Exception e){ }
	
	return PAGINA_REPORTING_INDEX;
	}
	
	@RequestMapping(value = "/reporting/usersAlesocr")
	public @ResponseBody RisultatoOperazioneRest usersAlesocr(HttpServletRequest request,HttpServletResponse respons, Locale locale) throws Throwable {
		
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		
		try{
			
			List<UtenteView> utentiAlesocr = utenteService.leggiUtenti(false);
			
			if(utentiAlesocr != null && !utentiAlesocr.isEmpty()){
				
				reportingService.exportReportAlesocr(utentiAlesocr,respons,locale.getLanguage().toUpperCase());
			}
		}catch(Exception e){
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "schedaFondoRischi.errore.generico");
			e.printStackTrace();
		}
		return risultato;
	}
	
	
	@RequestMapping(value = "/reporting/export-atto", method=RequestMethod.POST)
	public String exportAtto(HttpServletRequest request,HttpServletResponse respons, Model model, Locale locale) throws Throwable {
		UtenteView utenteView=null;
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		try{
			utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	

			if(utenteView!=null && utenteView.isAmministratore() ||
					utenteView.isLegaleInterno() ||
					utenteView.isResponsabile() ||
					utenteView.isOperatoreSegreteria()){
				 	
							String tipoAttoGiudiziario=null,
							attoStatoAtto=null,
							data_notifica_da=null,
							data_notifica_a=null,
							associatoAfascicolo=null;
							String[] societa=null;
							String[] destinatario=null;
							
						Object[] params= new Object[7];
						//MS
						if(request.getParameterValues("societa")!=null && request.getParameterValues("societa").length>0){
							societa=(String[])request.getParameterValues("societa");
						}
						
					if(request.getParameter("attoSocieta")!=null && !request.getParameter("attoSocieta").trim().equals("")){
						//attoSocieta=request.getParameter("attoSocieta");
						//params[0]=attoSocieta;
					}else{ params[0]=null;}
					//MS
					if(request.getParameterValues("destinatario")!=null && request.getParameterValues("destinatario").length>0){
						destinatario=(String[])request.getParameterValues("destinatario");
					}
					if(request.getParameter("attoDestinatario")!=null && !request.getParameter("attoDestinatario").trim().equals("")){
						//attoDestinatario=request.getParameter("attoDestinatario");
						//params[1]=attoDestinatario;
					}else{ params[1]=null;}	
					if(request.getParameter("tipoAttoGiudiziario")!=null && !request.getParameter("tipoAttoGiudiziario").trim().equals("")){
						tipoAttoGiudiziario=request.getParameter("tipoAttoGiudiziario");
						params[2]=tipoAttoGiudiziario;
					}else{ params[2]=null;}
					if(request.getParameter("attoStatoAtto")!=null && !request.getParameter("attoStatoAtto").trim().equals("")){
						attoStatoAtto=request.getParameter("attoStatoAtto");
						params[3]=attoStatoAtto;
					}else{ params[3]=null;}	
					if(request.getParameter("data_notifica_da")!=null && !request.getParameter("data_notifica_da").trim().equals("")){
						data_notifica_da=request.getParameter("data_notifica_da");
						params[4]=data_notifica_da;
					}else{ params[4]=null;}								
					if(request.getParameter("data_notifica_a")!=null && !request.getParameter("data_notifica_a").trim().equals("")){
						data_notifica_a=request.getParameter("data_notifica_a");
						params[5]=data_notifica_a;
					}else{ params[5]=null;}					 
					if(request.getParameter("associatoAfascicolo")!=null && !request.getParameter("associatoAfascicolo").trim().equals("")){
						associatoAfascicolo=request.getParameter("associatoAfascicolo");
						params[6]=associatoAfascicolo;
					}else{ params[6]=null;}			
					
					List<Long> longSocieta=new ArrayList<Long>(1);
					 params[0]=null; 
					if(societa!=null){
						for(String s:societa){
							if(s!=null && !s.equals("")){
							longSocieta.add(new Long(s).longValue());
							}
						}
						 
					} 
					
					if(longSocieta.size()>0)
						params[0]=longSocieta;
					
					
					List<String> destinatarioList=new ArrayList<String>(1);
					 params[1]=null;
					if(destinatario!=null){
						for(String s:destinatario){
							if(s!=null && !s.equals("")){
								destinatarioList.add(s);
							}
						}
						 
					} 
					
					if(destinatarioList.size()>0)
						params[1]=destinatarioList;
					
					
					reportingService.exportExcellAtto(params, respons,locale.getLanguage().toUpperCase());
					
			}
			
			 
			
			
		}catch(Exception e){}
	
	return index(request, respons, model, locale);
	}
	
	
	
	@RequestMapping(value = "/reporting/export-fascicolo", method=RequestMethod.POST)
	public String exportFascicolo(HttpServletRequest request,HttpServletResponse respons, Model model, Locale locale) throws Throwable {
		UtenteView utenteView=null;
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		try{
			utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	

			if(utenteView!=null && utenteView.isAmministratore() ||
					utenteView.isLegaleInterno() ||
					utenteView.isResponsabile() ||
					utenteView.isOperatoreSegreteria()){
				 
				 	
				String  fascicoloTipologiaFascicolo=null,
						fascicoloSettoreGiuridico=null,
						fascicoloStatoFascicolo=null,
						fascicolo_data_creazione_da=null,
						fascicolo_data_creazione_a=null,
						tipoContenzioso=null;
							
						String[] societaFasc=null;
						String[] ownerFasc=null;	
						Object[] params= new Object[8];
						
					if(request.getParameter("fascicoloTipologiaFascicolo")!=null && !request.getParameter("fascicoloTipologiaFascicolo").trim().equals("")){
						fascicoloTipologiaFascicolo=request.getParameter("fascicoloTipologiaFascicolo");
						params[0]=fascicoloTipologiaFascicolo;
					}else{ params[0]=null;}
					if(request.getParameter("fascicoloSettoreGiuridico")!=null && !request.getParameter("fascicoloSettoreGiuridico").trim().equals("")){
						fascicoloSettoreGiuridico=request.getParameter("fascicoloSettoreGiuridico");
						params[1]=fascicoloSettoreGiuridico;
					}else{ params[1]=null;}	
					if(request.getParameterValues("fascicoloSocieta")!=null && request.getParameterValues("fascicoloSocieta").length>0){
						societaFasc=request.getParameterValues("fascicoloSocieta");
						//params[2]=societaFasc;
					}else{ params[2]=null;}
					if(request.getParameter("fascicoloStatoFascicolo")!=null && !request.getParameter("fascicoloStatoFascicolo").trim().equals("")){
						fascicoloStatoFascicolo=request.getParameter("fascicoloStatoFascicolo");
						params[3]=fascicoloStatoFascicolo;
					}else{ params[3]=null;}	
					if(request.getParameter("fascicolo_data_creazione_da")!=null && !request.getParameter("fascicolo_data_creazione_da").trim().equals("")){
						fascicolo_data_creazione_da=request.getParameter("fascicolo_data_creazione_da");
						params[4]=fascicolo_data_creazione_da;
					}else{ params[4]=null;}								
					if(request.getParameter("fascicolo_data_creazione_a")!=null && !request.getParameter("fascicolo_data_creazione_a").trim().equals("")){
						fascicolo_data_creazione_a=request.getParameter("fascicolo_data_creazione_a");
						params[5]=fascicolo_data_creazione_a;
					}else{ params[5]=null;}					 
					if(request.getParameterValues("fascicoloOwner")!=null && request.getParameterValues("fascicoloOwner").length>0){
						ownerFasc=(String[])request.getParameterValues("fascicoloOwner");
						//params[6]=ownerFasc;
					}else{ params[6]=null;}			
					if(request.getParameter("tipoContenzioso")!=null && !request.getParameter("tipoContenzioso").trim().equals("")){
						tipoContenzioso=request.getParameter("tipoContenzioso");
						params[7]=tipoContenzioso;
					}else{ params[7]=null;}		
					
					
					List<Long> longSocieta=new ArrayList<Long>(1);
					params[2]=null;
					if(societaFasc!=null){
						for(String s:societaFasc){
							if(s!=null && !s.equals("")){
							longSocieta.add(new Long(s).longValue());
							}
						}
					}
					
					if(longSocieta.size()>0)
						params[2]=longSocieta;
					
					
					List<String> ownerList=new ArrayList<String>(1);
					params[6]=null;
					if(ownerFasc!=null){
						for(String s:ownerFasc){
							if(s!=null && !s.equals("")){
								ownerList.add(s);
							}
						}
					} 
					
					if(ownerList.size()>0)
						params[6]=ownerList;
					 
					
					reportingService.exportExcellFascicoli(params, respons,locale.getLanguage().toUpperCase());
					return null;
			}
			
			 
			
			
		}catch(Exception e){}
	
	return index(request, respons, model, locale);
	}
	
	
	
	
	
	@RequestMapping(value = "/reporting/export-proforma", method=RequestMethod.POST)
	public String exportProforma(HttpServletRequest request,HttpServletResponse respons, Model model, Locale locale) throws Throwable {
		UtenteView utenteView=null;
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		try{
			utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	
		
			if(utenteView!=null && utenteView.isAmministratore() ||
					utenteView.isLegaleInterno() ||
					utenteView.isResponsabile() ||
					utenteView.isOperatoreSegreteria()){
				 
				 	
				 String data_autorizzazione_da=null,
						data_autorizzazione_a=null,
						proformaSettoreGiuridico=null,
			 			proformaProfessionistaEsterno=null;
							
				    String[] arrySocieta=null;
					String[] arryOwner=null;			
						Object[] params= new Object[6];
						
					if(request.getParameterValues("proformaSocieta")!=null){
						arrySocieta=request.getParameterValues("proformaSocieta");
						//params[0]=arrySocieta;
					}else{ params[0]=null;}
					if(request.getParameter("data_autorizzazione_da")!=null && !request.getParameter("data_autorizzazione_da").trim().equals("")){
						data_autorizzazione_da=request.getParameter("data_autorizzazione_da");
						params[1]=data_autorizzazione_da;
					}else{ params[1]=null;}	
					if(request.getParameter("data_autorizzazione_a")!=null && !request.getParameter("data_autorizzazione_a").trim().equals("")){
						data_autorizzazione_a=request.getParameter("data_autorizzazione_a");
						params[2]=data_autorizzazione_a;
					}else{ params[2]=null;}
					if(request.getParameter("proformaSettoreGiuridico")!=null && !request.getParameter("proformaSettoreGiuridico").trim().equals("")){
						proformaSettoreGiuridico=request.getParameter("proformaSettoreGiuridico");
						params[3]=proformaSettoreGiuridico;
					}else{ params[3]=null;}	
					if(request.getParameterValues("proformaOwner")!=null){
						arryOwner=request.getParameterValues("proformaOwner");
						//params[4]=arryOwner;
					}else{ params[4]=null;}	
					if(request.getParameter("proformaProfessionistaEsterno")!=null && !request.getParameter("proformaProfessionistaEsterno").trim().equals("")){
						proformaProfessionistaEsterno=request.getParameter("proformaProfessionistaEsterno");
						params[5]=proformaProfessionistaEsterno;
					}else{ params[5]=null;}
					
					List<Long> longSocieta=new ArrayList<Long>(1);
					params[0]=null;
					if(arrySocieta!=null){
						for(String s:arrySocieta){
							if(s!=null && !s.equals("")){
							longSocieta.add(new Long(s).longValue());
							}
						}
					}
					
					if(longSocieta.size()>0)
						params[0]=longSocieta;
					
					List<String> ownerList=new ArrayList<String>(1);
					params[4]=null;
					if(arryOwner!=null){
						for(String s:arryOwner){
							if(s!=null && !s.equals("")){
								ownerList.add(s);
							}
						}
					} 
					
					if(ownerList.size()>0)
						params[4]=ownerList;
					
					reportingService.exportExcellProforma(params, respons,locale.getLanguage().toUpperCase());
			}
		}catch(Exception e){}
	
	return index(request, respons, model, locale);
	}
	
	@RequestMapping(value = "/reporting/export-incarico", method=RequestMethod.POST)
	public String exportIncarico(HttpServletRequest request,HttpServletResponse respons, Model model, Locale locale) throws Throwable {
		UtenteView utenteView=null;
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try{
			utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	
		
 
			if(utenteView!=null && utenteView.isAmministratore() ||
					utenteView.isLegaleInterno() ||
					utenteView.isResponsabile() ||
					utenteView.isOperatoreSegreteria()){
				 
				 	
				 String  data_creazione_da=null,
						data_creazione_a=null,
						incaricoProfessionistaEsterno=null,
								statoIncarico=null;
							
							
						String[] params= new String[4];
						
					if(request.getParameter("data_creazione_da")!=null && !request.getParameter("data_creazione_da").trim().equals("")){
						data_creazione_da=request.getParameter("data_creazione_da");
						params[0]=data_creazione_da;
					}else{ params[0]=null;}
					if(request.getParameter("data_creazione_a")!=null && !request.getParameter("data_creazione_a").trim().equals("")){
						data_creazione_a=request.getParameter("data_creazione_a");
						params[1]=data_creazione_a;
					}else{ params[1]=null;}	
					if(request.getParameter("incaricoProfessionistaEsterno")!=null && !request.getParameter("incaricoProfessionistaEsterno").trim().equals("")){
						incaricoProfessionistaEsterno=request.getParameter("incaricoProfessionistaEsterno");
						params[2]=incaricoProfessionistaEsterno;
					}else{ params[2]=null;}
					if(request.getParameter("statoIncarico")!=null && !request.getParameter("statoIncarico").trim().equals("")){
						statoIncarico=request.getParameter("statoIncarico");
						params[3]=statoIncarico;
					}else{ params[3]=null;}	
					 					
					 	
					
					reportingService.exportExcellIncarichi(params, respons,locale.getLanguage().toUpperCase());
					
			}
			
			 
			
			
		}catch(Exception e){}
	
	return index(request, respons, model, locale);
	}
}
