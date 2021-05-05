package eng.la.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.FascicoloService;
import eng.la.business.IncaricoService;
import eng.la.business.ProformaService;
import eng.la.business.UtenteService;
import eng.la.model.Acconti;
import eng.la.model.Bonus;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
import eng.la.model.LetteraIncarico;
import eng.la.model.rest.IndexRest;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.IndexView;
import eng.la.model.view.ProformaView;
import eng.la.model.view.RepertorioStandardView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("indexController")
@SessionAttributes("indexView")
public class IndexController {

	private static final String MODEL_VIEW_NOME = "indexView";
	public static final Long	DEFAULT_FASCICOLI_RECENTI_NUM_RIGHE = new Long(5);

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private FascicoloService fascicoloService;

	@Autowired
	private IncaricoService incaricoService;

	@Autowired
	private ProformaService proformaService;


	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String goToHomePage(
			HttpServletRequest request,
			Model model, 
			Locale locale)
	{
		IndexView indexView = new IndexView();
		Boolean isFilenetOk;
		// engsecurity VA ?? index root
			//	HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			//	htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		try 
		{
			/*@@DDS  TODO CAPIRE SE SOSTITUIRE CON METODO PER TEST DDS
			isFilenetOk = fascicoloService.testFilenet();
			if(!isFilenetOk){
				return "notDisp";
			}
			*/
			CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
			if (currentSessionUtil != null && currentSessionUtil.getRolesCode().contains(CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE)) { 
				RepertorioStandardView repertorioStandardView = new RepertorioStandardView();
				
				model.addAttribute("RepertorioStandardView", repertorioStandardView);
				
				return "repertorioStandard/ricercaRepertorioStandard";
			}
			
			model.addAttribute(MODEL_VIEW_NOME, indexView);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			return "index";
		}
		return "index";		
	}

	@RequestMapping(value = "/index/loadFascicoliRecenti", method = RequestMethod.POST)
	public @ResponseBody IndexRest 
	loadFascicoliRecenti
	(
			@RequestParam(value="numRighe",required=false) Long numRighe,
			HttpServletRequest request
			) 
	{
		IndexRest indexRest = new IndexRest();
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);

		try 
		{
			List<FascicoloView> listaUltimiFascicoli = (List<FascicoloView>) fascicoloService.cercaUltimiFascicoli(numRighe);

			if(listaUltimiFascicoli==null || listaUltimiFascicoli.isEmpty()) {
				listaUltimiFascicoli = null;
			}
			else 
			{
				JSONArray jsonArray = new JSONArray();
				if( listaUltimiFascicoli != null && listaUltimiFascicoli.size() > 0 ){
					for( FascicoloView fv : listaUltimiFascicoli ){
						JSONObject jsonObject = new JSONObject();

						Fascicolo vo = fv.getVo();
						jsonObject.put("id", vo.getId() );
						jsonObject.put("nome", vo.getNome() );
						jsonObject.put("descrizione", vo.getDescrizione() );

						jsonArray.put(jsonObject); 
					}

				}

				if (jsonArray != null) {
					indexRest.setJsonArrayUltimiFascicoli(jsonArray.toString());
				} 
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return indexRest;
	}

	@RequestMapping(value = "/index/caricaStatisticheBudget", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String caricaStatisticheBudget(@RequestParam(value="tipo",required=false) Integer tipo,HttpServletRequest request, Locale locale) {


		try 
		{
			// Utente connesso
			UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			String userIdOwner = utenteView.getVo().getUseridUtil();
			List<IncaricoView> listaIncarichi = null;
			Date now = new Date();
			int anno = DateUtil.getAnno(now);

			if(tipo == 1){

				/** Leggo gli incarichi autorizzati dove lo USER_ID e' quello dell' owner dei fascicoli associati**/
				listaIncarichi = incaricoService.leggiIncarichiAutorizzati("", "", userIdOwner);
			}
			else{
				/** Leggo gli incarichi autorizzati dove lo USER_ID e' quello dell' owner del fascicolo, il cui id � identificato dal valore della variabile tipo**/
				listaIncarichi = incaricoService.leggiIncarichiAutorizzati(null, null, userIdOwner, tipo.toString());
			}

			List<Long> listaIdIncarichi = new ArrayList<Long>();
			List<LetteraIncarico> listaLettereIncarico = new ArrayList<LetteraIncarico>();

			/** Estraggo la lista degli id degli incarichi e delle lettere di incarico **/
			estraiListaIdIncarichi(listaIncarichi, listaIdIncarichi);

			for(IncaricoView incaricoView : listaIncarichi){

				if(incaricoView.getVo().getLetteraIncarico() != null){
					listaLettereIncarico.add(incaricoView.getVo().getLetteraIncarico());
				}
			}

			BigDecimal sommatoriaTotaleAutorizzatoProforma = new BigDecimal(0);
			BigDecimal sommatoriaImportoIncaricoAnnoCorrente = new BigDecimal(0);

			/** Con le liste degli id degli incarichi leggo i proforma associati e calcolo la somma del totale autorizzato **/
			if(!listaIdIncarichi.isEmpty()){

				List<ProformaView> proformaList = proformaService.leggiProformaAssociatiAIncarico(listaIdIncarichi);

				if(proformaList != null && !proformaList.isEmpty()){

					for(ProformaView proformaView : proformaList){
						
						if(proformaView.getVo().getStatoProforma() != null){
							
							if(proformaView.getVo().getStatoProforma().getCodGruppoLingua() != null){
								
								if(proformaView.getVo().getStatoProforma().getCodGruppoLingua().equals(CostantiDAO.PROFORMA_STATO_AUTORIZZATO)){
									
									if(proformaView.getVo().getAnnoEsercizioFinanziario() != null){
										
										if(proformaView.getVo().getAnnoEsercizioFinanziario().compareTo(new BigDecimal(anno)) == 0){
											
											BigDecimal totaleAutorizzatoProforma = proformaView.getVo().getTotaleAutorizzato();
											if(totaleAutorizzatoProforma != null){
												
												sommatoriaTotaleAutorizzatoProforma = sommatoriaTotaleAutorizzatoProforma.add(totaleAutorizzatoProforma);
											}
										}
									}
								}
							}
							
						}

					}
				}
			}
			/** Con le liste degli id delle lettere leggo i bonus e gli acconti associati e calcolo la somma dell'importo **/
			if(!listaLettereIncarico.isEmpty()){

				for(LetteraIncarico letteraIncarico : listaLettereIncarico){

					Set<Acconti> acconti = letteraIncarico.getAcconti();

					for(Acconti acconto: acconti){

						String annoString = ""+anno;
						if(acconto.getAnno().equals(annoString)){

							if(acconto.getImporto() != null){

								sommatoriaImportoIncaricoAnnoCorrente = sommatoriaImportoIncaricoAnnoCorrente.add(acconto.getImporto());
							}

						}
					}

					/** Il bonus si somma solo se l'anno corrente � quello di fine causa **/
					boolean isAnnoFineCausa = controllaAnnoFineCausa(acconti, anno);

					/** Se l'anno corrente coincide con quello di fine causa alla sommatoria verra'
					 *  aggiunto il max tra i bonus associati alla lettera di incarico **/
					if(isAnnoFineCausa){

						Set<Bonus> bonus = letteraIncarico.getBonus();

						BigDecimal importoBonus = new BigDecimal(0);
						BigDecimal maxImportoBonus = estraiMaxImportoBonus(bonus);

						if(maxImportoBonus.compareTo(importoBonus) == 1){
							sommatoriaImportoIncaricoAnnoCorrente = sommatoriaImportoIncaricoAnnoCorrente.add(maxImportoBonus);
						}
					}
				}
			}
			/** Effettuo il calcolo della perfentuale e creo il json da passare al javascript per la creazione del grafico **/
			BigDecimal residuo = new BigDecimal(0);
			BigDecimal usufruito = new BigDecimal(0);
			BigDecimal zero = new BigDecimal(0);


			if(sommatoriaImportoIncaricoAnnoCorrente.compareTo(zero) == 1){
				
				usufruito = usufruito.add(sommatoriaTotaleAutorizzatoProforma).setScale(2, BigDecimal.ROUND_HALF_UP);
				residuo = residuo.add(sommatoriaImportoIncaricoAnnoCorrente.subtract(sommatoriaTotaleAutorizzatoProforma)).setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			
			if(usufruito.compareTo(zero) == 0){
				if(residuo.compareTo(zero) == 0){
					return null;
				}
			}

			String localization = locale.getLanguage() == null ? "" :  locale.getLanguage().toUpperCase();
			JSONArray jsonArray = new JSONArray();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", residuo.longValue());
			jsonObject.put("color", "#03A9F4");
			
			String residuoString = "Residuo";
			if(localization != null && !localization.isEmpty()){
				
				if(!localization.equals("IT"))
					residuoString = "Remaining";
			}
			
			jsonObject.put("label", residuoString );

			jsonArray.put(jsonObject);

			JSONObject jsonObject2 = new JSONObject();

			jsonObject2.put("data", usufruito.longValue());
			jsonObject2.put("color", "#F44336");
			
			String consuntivatoString = "Consuntivato";
			if(localization != null && !localization.isEmpty()){
				
				if(!localization.equals("IT"))
					consuntivatoString = "Actual Purchases";
			}
			jsonObject2.put("label", consuntivatoString );

			jsonArray.put(jsonObject2);
			return jsonArray.toString();

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/index/caricaStatisticheBudgetResponsabile", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String caricaStatisticheBudgetResponsabile(HttpServletRequest request, Locale locale) {


		try 
		{
			// Utente connesso
			UtenteView utenteConnesso = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
			Date now = new Date();
			int anno = DateUtil.getAnno(now);

			/** Se l'utente connesso non e' responsabile allora il metodo ritorna null **/
			if (utenteConnesso != null && currentSessionUtil != null ) {
				String userIdOwner = utenteConnesso.getVo().getUseridUtil();

				if(currentSessionUtil.getRolesCode() != null && !currentSessionUtil.getRolesCode().isEmpty()){

					boolean isResponsabile = false;

					for( String code : currentSessionUtil.getRolesCode()){
						if(code.equals( CostantiDAO.GRUPPO_RESPONSABILE )){
							isResponsabile = true;
						}
					}

					/** Cerco tutti i legali interni di cui l'utente connesso e' responsabile **/
					if(isResponsabile){
						List<UtenteView> listaCollaboratoriLegaliInterni = utenteService.leggiCollaboratoriLegaliInterni(utenteConnesso.getVo().getMatricolaUtil());

						List<String> listaUserIdCollaboratoriLegaliInterni = estraiListaUserId(listaCollaboratoriLegaliInterni);

						/** Alla lista, anche se vuota, si aggiunge anche la USER_ID dell'utente connesso **/
						if(listaUserIdCollaboratoriLegaliInterni == null){
							listaUserIdCollaboratoriLegaliInterni = new ArrayList<String>();
						}

						listaUserIdCollaboratoriLegaliInterni.add(userIdOwner);

						/** Leggo gli incarichi autorizzati dove le USER_ID estratte sono quelle degli owner dei fascicoli associati**/
						List<IncaricoView> listaIncarichi = incaricoService.leggiIncarichiAutorizzati(null, null, listaUserIdCollaboratoriLegaliInterni);

						List<Long> listaIdIncarichi = new ArrayList<Long>();
						List<LetteraIncarico> listaLettereIncarico = new ArrayList<LetteraIncarico>();

						/** Estraggo la lista degli id degli incarichi e delle lettere di incarico **/
						estraiListaIdIncarichi(listaIncarichi, listaIdIncarichi);

						for(IncaricoView incaricoView : listaIncarichi){

							if(incaricoView.getVo().getLetteraIncarico() != null){
								listaLettereIncarico.add(incaricoView.getVo().getLetteraIncarico());
							}
						}

						BigDecimal sommatoriaTotaleAutorizzatoProforma = new BigDecimal(0);
						BigDecimal sommatoriaImportoIncaricoAnnoCorrente = new BigDecimal(0);

						/** Con le liste degli id degli incarichi leggo i proforma associati e calcolo la somma del totale autorizzato **/
						if(!listaIdIncarichi.isEmpty()){

							List<ProformaView> proformaList = proformaService.leggiProformaAssociatiAIncarico(listaIdIncarichi);

							if(proformaList != null && !proformaList.isEmpty()){

								for(ProformaView proformaView : proformaList){
									
									if(proformaView.getVo().getStatoProforma() != null){
										
										if(proformaView.getVo().getStatoProforma().getCodGruppoLingua() != null){
											
											if(proformaView.getVo().getStatoProforma().getCodGruppoLingua().equals(CostantiDAO.PROFORMA_STATO_AUTORIZZATO)){
												
												if(proformaView.getVo().getAnnoEsercizioFinanziario() != null){
													
													if(proformaView.getVo().getAnnoEsercizioFinanziario().compareTo(new BigDecimal(anno)) == 0){
														
														BigDecimal totaleAutorizzatoProforma = proformaView.getVo().getTotaleAutorizzato();
														if(totaleAutorizzatoProforma != null){
															
															sommatoriaTotaleAutorizzatoProforma = sommatoriaTotaleAutorizzatoProforma.add(totaleAutorizzatoProforma);
														}
													}
												}
											}
										}
										
									}
								}
							}
						}

						/** Con le liste degli id delle lettere leggo i bonus e gli acconti associati e calcolo la somma dell'importo **/
						if(!listaLettereIncarico.isEmpty()){

							for(LetteraIncarico letteraIncarico : listaLettereIncarico){

								Set<Acconti> acconti = letteraIncarico.getAcconti();

								for(Acconti acconto: acconti){

									String annoString = ""+anno;
									if(acconto.getAnno().equals(annoString)){

										if(acconto.getImporto() != null){

											sommatoriaImportoIncaricoAnnoCorrente = sommatoriaImportoIncaricoAnnoCorrente.add(acconto.getImporto());
										}

									}
								}

								/** Il bonus si somma solo se l'anno corrente � quello di fine causa **/
								boolean isAnnoFineCausa = controllaAnnoFineCausa(acconti, anno);

								/** Se l'anno corrente coincide con quello di fine causa alla sommatoria verra'
								 *  aggiunto il max tra i bonus associati alla lettera di incarico **/
								if(isAnnoFineCausa){

									Set<Bonus> bonus = letteraIncarico.getBonus();

									BigDecimal importoBonus = new BigDecimal(0);
									BigDecimal maxImportoBonus = estraiMaxImportoBonus(bonus);

									if(maxImportoBonus.compareTo(importoBonus) == 1){
										sommatoriaImportoIncaricoAnnoCorrente = sommatoriaImportoIncaricoAnnoCorrente.add(maxImportoBonus);
									}
								}
							}
						}

						/** Effettuo il calcolo della perfentuale e creo il json da passare al javascript per la creazione del grafico **/
						BigDecimal residuo = new BigDecimal(0);
						BigDecimal usufruito = new BigDecimal(0);
						BigDecimal zero = new BigDecimal(0);


						if(sommatoriaImportoIncaricoAnnoCorrente.compareTo(zero) == 1){
							
							usufruito = usufruito.add(sommatoriaTotaleAutorizzatoProforma).setScale(2, BigDecimal.ROUND_HALF_UP);
							residuo = residuo.add(sommatoriaImportoIncaricoAnnoCorrente.subtract(sommatoriaTotaleAutorizzatoProforma)).setScale(2, BigDecimal.ROUND_HALF_UP);
						}
						
						if(usufruito.compareTo(zero) == 0){
							if(residuo.compareTo(zero) == 0){
								return null;
							}
						}
						
						String localization = locale.getLanguage() == null ? "" :  locale.getLanguage().toUpperCase();
						JSONArray jsonArray = new JSONArray();

						JSONObject jsonObject = new JSONObject();
						jsonObject.put("data", residuo.longValue());
						jsonObject.put("color", "#03A9F4");
						
						String residuoString = "Residuo";
						if(localization != null && !localization.isEmpty()){
							
							if(!localization.equals("IT"))
								residuoString = "Remaining";
						}
						
						jsonObject.put("label", residuoString );

						jsonArray.put(jsonObject);

						JSONObject jsonObject2 = new JSONObject();

						jsonObject2.put("data", usufruito.longValue());
						jsonObject2.put("color", "#F44336");


						String consuntivatoString = "Consuntivato";
						if(localization != null && !localization.isEmpty()){
							
							if(!localization.equals("IT"))
								consuntivatoString = "Actual Purchases";
						}
						jsonObject2.put("label", consuntivatoString );

						jsonArray.put(jsonObject2);
						return jsonArray.toString();
					}
					else{
						return null;
					}
				}
				else{
					return null;
				}
			}
			else{
				return null;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void estraiListaIdIncarichi(List<IncaricoView> listaIncarichi, List<Long> listaIdIncarichi){

		if(listaIncarichi != null && !listaIncarichi.isEmpty()){

			for(IncaricoView incaricoView : listaIncarichi){

				Incarico incarico = incaricoView.getVo();

				if(incarico != null){

					listaIdIncarichi.add(incarico.getId());
				}
			}
		}
	}


	protected List<String> estraiListaUserId(List<UtenteView> listaCollaboratoriLegaliInterni){
		List<String> listaUserId = null;

		if(listaCollaboratoriLegaliInterni != null && !listaCollaboratoriLegaliInterni.isEmpty()){
			listaUserId = new ArrayList<String>();

			for(UtenteView utenteView : listaCollaboratoriLegaliInterni){
				listaUserId.add(utenteView.getVo().getUseridUtil());
			}
		}
		return listaUserId;
	}

	protected boolean controllaAnnoFineCausa(Set<Acconti> acconti, int annoCorrente){
		boolean isAnnoFineCausa = false;

		if(acconti != null && !acconti.isEmpty()){

			int annoMax = 0;

			for(Acconti acconto : acconti){

				int anno = Integer.parseInt(acconto.getAnno());

				if(anno > annoMax){
					annoMax = anno;
				}
			}
			if(annoMax == annoCorrente){
				isAnnoFineCausa = true;
			}
		}
		return isAnnoFineCausa;
	}

	protected BigDecimal estraiMaxImportoBonus(Set<Bonus> bonus){
		BigDecimal result = new BigDecimal(0);

		if(bonus != null && !bonus.isEmpty()){

			for(Bonus sbonus : bonus){

				if(sbonus.getImporto() != null){

					if(sbonus.getImporto().compareTo(result) == 1){
						result = sbonus.getImporto();
					}
				}
			}
		}
		return result;
	}
}
