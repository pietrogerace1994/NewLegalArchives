package eng.la.controller;

import java.io.IOException;
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

import eng.la.business.ParcellaService;
import eng.la.model.Controparte;
import eng.la.model.Nazione;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.Proforma;
import eng.la.model.Societa;
import eng.la.model.custom.ParcellaRow;
import eng.la.model.custom.ProformaRow;
import eng.la.model.rest.ParcellaRest;
import eng.la.model.rest.RicercaParcellaRest;
import eng.la.model.view.UtenteView;
import eng.la.util.Pagebol;
import eng.la.util.costants.Costanti;

@Controller("parcellaController")
public class ParcellaController {
	private static final String PAGINA_PARCELLA_INDEX= "parcella/parcellaIndex";
	//	private static final String PAGINA_PARCELLA_SOCIETA= "parcella/societa";
	private static final boolean PROCESSATE= true;
	private static final boolean NON_PROCESSARE= false;

	@Autowired
	private ParcellaService parcellaService;

	public void setParcellaService(ParcellaService parcellaService) {
		this.parcellaService = parcellaService;
	}



	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/parcella/index")
	public String index(HttpServletRequest request,HttpServletResponse respons, Model model, Locale locale) throws Throwable {

		UtenteView utenteView=null;

		// engsecurity VA
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);

		try{

			utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	
			String utenteProcessamento=utenteView.getVo().getUseridUtil();
			model.addAttribute("utenteProcessamento", utenteProcessamento); 

			// SOLO UTENTE AMMINISTRATIVO
			if(utenteView!=null && utenteView.isAmministrativo()){

				List<Societa> societasProcessate=null;
				List<Societa> societasNonProcessare=null;

				model.addAttribute("classProcessato", ""); 
				model.addAttribute("classNonProcessato", "active"); 	

				if(request.getSession().getAttribute("PARCELLE_NON_PROCESSATE")!=null){
					societasProcessate=(List<Societa>)request.getSession().getAttribute("PARCELLE_PROCESSATE");
					societasNonProcessare=(List<Societa>)request.getSession().getAttribute("PARCELLE_NON_PROCESSATE");
				}
				if(request.getSession().getAttribute("PARCELLE_NON_PROCESSATE")==null){
					societasProcessate=parcellaService.getListaSocietaProformaProcessate(PROCESSATE);
					societasNonProcessare=parcellaService.getListaSocietaProformaProcessate(NON_PROCESSARE);
					request.getSession().setAttribute("PARCELLE_PROCESSATE", societasProcessate);
					request.getSession().setAttribute("PARCELLE_NON_PROCESSATE", societasNonProcessare);
				}

				//se trovo il paramtro (letta) aggiorno le liste per visualizzare le modifiche ai proforma processati
				if(request.getParameter("letta")!=null){
					request.getSession().removeAttribute("PARCELLE_PROCESSATE");
					request.getSession().removeAttribute("PARCELLE_NON_PROCESSATE");	
					societasProcessate=parcellaService.getListaSocietaProformaProcessate(PROCESSATE);
					societasNonProcessare=parcellaService.getListaSocietaProformaProcessate(NON_PROCESSARE);
					request.getSession().setAttribute("PARCELLE_PROCESSATE", societasProcessate);
					request.getSession().setAttribute("PARCELLE_NON_PROCESSATE", societasNonProcessare);	 
					model.addAttribute("classProcessato", "active"); 
					model.addAttribute("classNonProcessato", ""); 
				}


				//Download xls
				if(request.getParameter("export-proform")!=null){
					String export=request.getParameter("export-proform");
					export=export.trim();
					if(export.equalsIgnoreCase("processate")){
						try{
							List<ParcellaRow> parcellaRowsExport= getListParcellaRows(societasProcessate, PROCESSATE);
							parcellaService.exportExcell(parcellaRowsExport, respons, PROCESSATE);	

						}catch(IOException e){}
					}
					if(export.equalsIgnoreCase("nonprocessate")){
						try{
							List<ParcellaRow> parcellaRowsExport= getListParcellaRows(societasNonProcessare, NON_PROCESSARE);		
							parcellaService.exportExcell(parcellaRowsExport, respons, NON_PROCESSARE);	

						}catch(IOException e){}
					}
				}


				//CentroDiCostoService centroDiCostoService = (CentroDiCostoService) SpringUtil.getBean("centroDiCostoService");
				//VoceDiContoService voceDiContoService = (VoceDiContoService) SpringUtil.getBean("voceDiContoService");

				int numeroDiRowSocieta=5;
				// IL PARAMETRO numRow (numero societa da visulalizzare)  PER IL MOMENTO NON ESISTE ( prendera' sempre quello di defaul =5 )
				if(request.getParameter("numRow")!=null && request.getParameter("numRow").trim().length()>0){
					numeroDiRowSocieta=new Integer(request.getParameter("numRow")).intValue();
				}

				Pagebol<Societa> pagebolProcessate=new Pagebol<Societa>(societasProcessate,numeroDiRowSocieta);

				Pagebol<Societa> pagebolNonProcessate=new Pagebol<Societa>(societasNonProcessare,numeroDiRowSocieta);

				int numeroPaginaProcessate=1;
				int numeroPaginaNonProcessate=1;

				//PER I PROFORMA PROCESSATI
				if(request.getParameter("pCurr")!=null && !request.getParameter("pCurr").trim().equals("")){
					numeroPaginaProcessate=new Integer(request.getParameter("pCurr")).intValue();
					model.addAttribute("classProcessato", "active"); 
					model.addAttribute("classNonProcessato", ""); 	
				}
				//PER I PROFORMA NON-PROCESSATI
				if(request.getParameter("npCurr")!=null && !request.getParameter("npCurr").trim().equals("")){
					numeroPaginaNonProcessate=new Integer(request.getParameter("npCurr")).intValue();
					model.addAttribute("classProcessato", ""); 
					model.addAttribute("classNonProcessato", "active"); 	
				}


				List<Societa> socProcessate=pagebolProcessate.getPages(numeroPaginaProcessate);
				List<Societa> socNonProcessate=pagebolNonProcessate.getPages(numeroPaginaNonProcessate);


				//Navigatore SOCIETA-NON-PROCESSATE
				model.addAttribute("totaleNumeroPagineNP", pagebolNonProcessate.getTotalNumberPage()); 
				model.addAttribute("paginaCorrenteNP", pagebolNonProcessate.getPageCurrent());
				model.addAttribute("paginaFinaleNP", pagebolNonProcessate.getPageEnd()); 
				model.addAttribute("paginaInizialeNP", pagebolNonProcessate.getPageStart()); 

				//Navigatore SOCIETA-PROCESSATE
				model.addAttribute("totaleNumeroPagine", pagebolProcessate.getTotalNumberPage()); 
				model.addAttribute("paginaCorrente", pagebolProcessate.getPageCurrent());
				model.addAttribute("paginaFinale", pagebolProcessate.getPageEnd()); 
				model.addAttribute("paginaIniziale", pagebolProcessate.getPageStart()); 


				model.addAttribute("socProcessate", socProcessate); 
				model.addAttribute("socNonProcessate", socNonProcessate); 

				if(request.getParameter("pa")!=null && !request.getParameter("pa").trim().equals("")){
					model.addAttribute("visualizzaProformaId", request.getParameter("pa")); 
				}
			}
		}catch(Exception e){ }

		return (utenteView!=null&&utenteView.isAmministrativo())? PAGINA_PARCELLA_INDEX:"redirect:/index.action";
	}


	private List<ParcellaRow> getListParcellaRows(List<Societa> societas,boolean isProcessato) throws Throwable{
		List<ParcellaRow> parcellaRows= new ArrayList<ParcellaRow>();

		try{

			if(societas!=null){	

				for(Societa sc:societas){
					ParcellaRow row= new ParcellaRow();

					List<Proforma> proformas=parcellaService.getListaProformaPerSocieta(isProcessato, sc.getId());
					List<ProformaRow> proformasRow= new ArrayList<ProformaRow>();

					for(Proforma p:proformas){

						ProformaRow profRow= new ProformaRow();
						profRow.setProformas(p);	 


						Controparte controparte=new Controparte();
						List<Controparte> contropartes=parcellaService.getListaProformaControparte(p.getId());
						if(contropartes!=null)
							controparte=contropartes.get(0);
						List<ProfessionistaEsterno> esternos=parcellaService.getListaProformaProfessionistaEsterno(p.getId());
						ProfessionistaEsterno legaleEsterno=new ProfessionistaEsterno();
						if(esternos!=null)
							legaleEsterno=esternos.get(0);
						List<Nazione> naziones = parcellaService.getListaProformaNazione(p.getId());	
						Nazione nazione=new Nazione();
						if(naziones!=null)
							nazione=naziones.get(0);

						profRow.setControparte(controparte);
						profRow.setPaese(nazione);
						profRow.setLegaleEsterno(legaleEsterno);
						proformasRow.add(profRow);
					}


					row.setSocieta(sc);
					row.setProformasRow(proformasRow);
					parcellaRows.add(row);	 

				}


			}
		}catch(Exception e){ }

		return parcellaRows;

	}




	@RequestMapping(value = "/parcella/cercaparcella",method = RequestMethod.GET,produces = "application/json")
	public @ResponseBody RicercaParcellaRest cercaParcella( HttpServletRequest request, Model model, Locale locale) throws Throwable {

		// rowCount -current  -- searchPhrase
		Integer rowCount =new Integer((request.getParameter("rowCount")!=null && !request.getParameter("rowCount").equals(""))?request.getParameter("rowCount"):"10");
		if(rowCount==null || (rowCount!=null && rowCount.intValue()<10))
			rowCount=new Integer(10);
		Integer current= new Integer((request.getParameter("current")!=null && !request.getParameter("current").equals(""))?request.getParameter("current"):"1");
		if(current==null || (current!=null && current.intValue()<0))
			current=new Integer(1);
		UtenteView utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	

		RicercaParcellaRest ricercaAttoRest=new RicercaParcellaRest();

		// SOLO UTENTE AMMINISTRATIVO
		if(utenteView!=null && utenteView.isAmministrativo()){


			Boolean isProcessato = new Boolean((request.getParameter("isProcessato")!=null && !request.getParameter("isProcessato").equals(""))?request.getParameter("isProcessato"):"false");


			long idSocieta=new Long((request.getParameter("idSoc")!=null && !request.getParameter("idSoc").equals(""))?request.getParameter("idSoc"):"0").longValue(); 

			String columnSort=null,sort=null;

			if(request.getParameter("sort[totale]")!=null){
				columnSort="totale_autorizzato";
				sort=checkSort(request.getParameter("sort[totale]")); 
			}
			if(request.getParameter("sort[valuta]")!=null){
				columnSort="valuta";
				sort=checkSort(request.getParameter("sort[valuta]")); 
			}
			if(request.getParameter("sort[datainserimento]")!=null){
				columnSort="DATA_INSERIMENTO";
				sort=checkSort(request.getParameter("sort[datainserimento]")); 
			}
			if(request.getParameter("sort[dataautorizzazione]")!=null){
				columnSort="DATA_AUTORIZZAZIONE";
				sort=checkSort(request.getParameter("sort[dataautorizzazione]")); 
			}
			if(request.getParameter("sort[titolo]")!=null){
				columnSort="titolo";
				sort=checkSort(request.getParameter("sort[titolo]")); 
			}
			if(request.getParameter("sort[centrodicosto]")!=null){
				columnSort="CENTRO_DI_COSTO";
				sort=checkSort(request.getParameter("sort[centrodicosto]")); 
			}

			if(request.getParameter("sort[voceconto]")!=null){
				columnSort="VOCE_DI_CONTO";
				sort=checkSort(request.getParameter("sort[voceconto]")); 
			}
			if(request.getParameter("sort[controparte]")!=null){
				columnSort="controparte";
				sort=checkSort(request.getParameter("sort[controparte]")); 
			}
			if(request.getParameter("sort[legaleesterno]")!=null){
				columnSort="legaleEsterno";
				sort=checkSort(request.getParameter("sort[legaleesterno]")); 
			}
			if(request.getParameter("sort[paese]")!=null){
				columnSort="paese";
				sort=checkSort(request.getParameter("sort[paese]")); 
			}


			//NUOVO per ordinamento
			List<ParcellaRest> proformaRest=parcellaService.getParcellaRest(isProcessato.booleanValue(), idSocieta, columnSort, sort);

			Pagebol<ParcellaRest> proformaRow=new Pagebol<ParcellaRest>(proformaRest,rowCount.intValue());


			List<ParcellaRest> parcellaRests=proformaRow.getPages(current.intValue());
			ricercaAttoRest.setCurrent(current.longValue());
			ricercaAttoRest.setRowCount(rowCount.longValue());
			ricercaAttoRest.setTotal(proformaRest.size());
			ricercaAttoRest.setRows(parcellaRests);

			// FINE
			/*Vecchio
		List<Proforma> proformas=parcellaService.getListaProformaPerSocieta(isProcessato.booleanValue(), idSocieta);
		Pagebol<Proforma> proformaRow=new Pagebol<Proforma>(proformas,rowCount.intValue());

		List<ParcellaRest> parcellaRests=convertUtilRest(proformaRow.getPages(current.intValue()));
		ricercaAttoRest.setCurrent(current.longValue());
		ricercaAttoRest.setRowCount(rowCount.longValue());
		ricercaAttoRest.setTotal(proformas.size());
		ricercaAttoRest.setRows(parcellaRests);
			 */
		}
		return ricercaAttoRest;
	}

	private String checkSort(String sort){
		if(sort==null || sort.equals(""))
			return "asc";
		else if(sort.equalsIgnoreCase("asc"))
			return "asc";
		else if(sort.equalsIgnoreCase("desc"))
			return "desc";
		else
			return "asc";
	}

	/*private List<ParcellaRest> convertUtilRest(List<Proforma> proformas) throws Throwable{
		List<ParcellaRest> lRest=new ArrayList<ParcellaRest>();
		if(proformas==null){
			return lRest;
		}

		try{

			for(Proforma p: proformas){
				ParcellaRest r= new ParcellaRest();
				r.setId(p.getId());
				r.setTitolo(p.getNomeProforma());
				r.setCentrodicosto(p.getCentroDiCosto());

				r.setDataautorizzazione(p.getDataAutorizzazione().toString());
				r.setDatainserimento(p.getDataInserimento().toString());


				r.setTotale(p.getTotaleAutorizzato().toString());
				r.setValuta(p.getTipoValuta().getNome());
				r.setVoceconto(p.getVoceDiConto());

				Controparte controparte=new Controparte();
				List<Controparte> contropartes=parcellaService.getListaProformaControparte(p.getId());
				if(contropartes!=null){
					controparte=contropartes.get(0);
					r.setControparte(controparte.getNome());
				}

				List<ProfessionistaEsterno> esternos=parcellaService.getListaProformaProfessionistaEsterno(p.getId());
				ProfessionistaEsterno legaleEsterno=new ProfessionistaEsterno();
				if(esternos!=null){
					legaleEsterno=esternos.get(0);
					r.setLegaleesterno(legaleEsterno.getNome() +" "+legaleEsterno.getCognome());
				}
				List<Nazione> naziones = parcellaService.getListaProformaNazione(p.getId());	
				Nazione nazione=new Nazione();
				if(naziones!=null){
					nazione=naziones.get(0);
					r.setPaese(nazione.getDescrizione());
				}
				lRest.add(r);
			}

		}catch(Exception e){ }

		return lRest;

	}*/


	@RequestMapping(value = "/parcella/letta",method = RequestMethod.POST,produces="text/html")
	public @ResponseBody String letta(@RequestParam("idProf") long idProf,HttpServletRequest request, Model model, Locale locale) throws Throwable {


		UtenteView utenteView=(UtenteView)request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);	
		String utenteProcessamento=utenteView.getVo().getUseridUtil();

		// SOLO UTENTE AMMINISTRATIVO
		if(utenteView!=null && utenteView.isAmministrativo()){	

			if(parcellaService.setProformaLetto(idProf, utenteProcessamento)!=null)
				return "OK";
			else
				return "KO";

		}
		return "UTENTE-NON-AUTORIZZATO";

	}

}
