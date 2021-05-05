package eng.la.controller.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.UtenteService;
import eng.la.business.workflow.ConfigurazioneService;
import eng.la.business.workflow.ConfigurazioneStepWfService;
import eng.la.business.workflow.StepWfService;
import eng.la.controller.BaseController;
import eng.la.model.Utente;
import eng.la.model.view.BaseView;
import eng.la.model.view.ConfigurazioneStepWfView;
import eng.la.model.view.ConfigurazioneView;
import eng.la.model.view.ConfigurazioneWorkflowView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("configurazioneWorkflowController")
@SessionAttributes("configurazioneWorkflowView")
public class ConfigurazioneWorkflowController extends BaseController {
	
	private static final String MODEL_VIEW_NOME = "configurazioneWorkflowView";
	private static final String PAGINA_FORM_CONFIGURAZIONE_WORKFLOW = "workflow/formConfigurazioneWorkflow";

	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private ConfigurazioneStepWfService configurazioneStepWfService;
	
	@Autowired
	private StepWfService stepWfService;
	
	@Autowired
	private ConfigurazioneService configurazioneService;
	
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
	}
	
	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
	
	@RequestMapping(value = "/configurazioneWorkflow/salva", method = RequestMethod.POST)
	public String salvaMateria(
			Locale locale, 
			Model model,
			@ModelAttribute(MODEL_VIEW_NOME) ConfigurazioneWorkflowView configurazioneWorkflowView, 
			BindingResult bindingResult,
			HttpServletRequest request, 
			HttpServletResponse response) {
		// engsecurity VA
		//HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		//htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
		
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if(code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) ){

				flagView = true;
			}
		}
		if(flagView){


		try {
			String matricolaAutorizzatore=configurazioneWorkflowView.getTopResponsabileMatricola();
			String matricolaApprovatore=configurazioneWorkflowView.getMatricolaApprovatore();
//			long idAutorizzatore=configurazioneWorkflowView.getIdAutorizzatore();
//			long idApprovatore=configurazioneWorkflowView.getIdApprovatore();
			String emailAutorizzatore=configurazioneWorkflowView.getEmailAutorizzatore();
			String emailApprovatore=configurazioneWorkflowView.getEmailApprovatore();
			
			//id 
			Long idAutorizzatoreRigaWf1=configurazioneWorkflowView.getIdAutorizzatoreRigaWf1();
			Long idAutorizzatoreRigaWf2=configurazioneWorkflowView.getIdAutorizzatoreRigaWf2();
			Long idAutorizzatoreRigaWf3=configurazioneWorkflowView.getIdAutorizzatoreRigaWf3();
			Long idAutorizzatoreRigaWf4=configurazioneWorkflowView.getIdAutorizzatoreRigaWf4();
			
			Long idApprovatoreRigaWf1=configurazioneWorkflowView.getIdApprovatoreRigaWf1();
			Long idApprovatoreRigaWf2=configurazioneWorkflowView.getIdApprovatoreRigaWf2();
			Long idApprovatoreRigaWf3=configurazioneWorkflowView.getIdApprovatoreRigaWf3();
			Long idApprovatoreRigaWf4=configurazioneWorkflowView.getIdApprovatoreRigaWf4();
				
			//salva
			configurazioneStepWfService.aggiornaMatricolaById(idAutorizzatoreRigaWf1,matricolaAutorizzatore);
			configurazioneStepWfService.aggiornaEmailById(idAutorizzatoreRigaWf1,emailAutorizzatore);
			configurazioneStepWfService.aggiornaMatricolaById(idAutorizzatoreRigaWf2,matricolaAutorizzatore);
			configurazioneStepWfService.aggiornaEmailById(idAutorizzatoreRigaWf2,emailAutorizzatore);
			configurazioneStepWfService.aggiornaMatricolaById(idAutorizzatoreRigaWf3,matricolaAutorizzatore);
			configurazioneStepWfService.aggiornaEmailById(idAutorizzatoreRigaWf3,emailAutorizzatore);
			configurazioneStepWfService.aggiornaMatricolaById(idAutorizzatoreRigaWf4,matricolaAutorizzatore);
			configurazioneStepWfService.aggiornaEmailById(idAutorizzatoreRigaWf4,emailAutorizzatore);
			
			configurazioneService.aggiornaMatricolaTopResponsabile(matricolaAutorizzatore);
			
			configurazioneStepWfService.aggiornaMatricolaById(idApprovatoreRigaWf1,matricolaApprovatore);
			configurazioneStepWfService.aggiornaEmailById(idApprovatoreRigaWf1,emailApprovatore);
			configurazioneStepWfService.aggiornaMatricolaById(idApprovatoreRigaWf2,matricolaApprovatore);
			configurazioneStepWfService.aggiornaEmailById(idApprovatoreRigaWf2,emailApprovatore);
			configurazioneStepWfService.aggiornaMatricolaById(idApprovatoreRigaWf3,matricolaApprovatore);
			configurazioneStepWfService.aggiornaEmailById(idApprovatoreRigaWf3,emailApprovatore);
			configurazioneStepWfService.aggiornaMatricolaById(idApprovatoreRigaWf4,matricolaApprovatore);
			configurazioneStepWfService.aggiornaEmailById(idApprovatoreRigaWf4,emailApprovatore);
			
			model.addAttribute("successMessage", "messaggio.operazione.ok");
		} catch (Throwable e) {
			//bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			return "redirect:/configurazioneWorkflow/configurazione.action";
		}
		return "redirect:/configurazioneWorkflow/configurazione.action";
		}
		else
			return null;
	}
	
	@RequestMapping(value = "/configurazioneWorkflow/configurazione", method = RequestMethod.GET)
	public String configurazioneWorkflow(Model model,Locale locale, HttpServletRequest request, HttpServletResponse response) {
		
		// engsecurity VA ??
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO) ||code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) ){

				flagView = true;
			}
		}
		if(flagView){

		ConfigurazioneWorkflowView configurazioneWorkflowView = new ConfigurazioneWorkflowView();
		try {
			// leggo Autorizzatore = TopResponsabile 
			ConfigurazioneView configurazioneView = configurazioneService.leggiConfigurazione(CostantiDAO.KEY_TOP_RESPONSABILE);
			String matricolaTopResponsabile=configurazioneView.getVo().getCdValue();
			UtenteView utenteTopResponsabileView = utenteService.leggiUtenteDaMatricola(matricolaTopResponsabile);
			configurazioneWorkflowView.setTopResponsabileNomeCognome(utenteTopResponsabileView.getVo().getNominativoUtil());
			configurazioneWorkflowView.setTopResponsabileMatricola(utenteTopResponsabileView.getVo().getMatricolaUtil());
			configurazioneWorkflowView.setTopResponsabileNomeCognomeSenzaVirgola(utenteTopResponsabileView.getVo().getNominativoUtil().replace(',', ' '));
			
			ConfigurazioneStepWfView autorizzatoreView=configurazioneStepWfService.leggiAutorizzatore();
			ConfigurazioneStepWfView approvatoreView=configurazioneStepWfService.leggiApprovatore();
			
			// leggo Stato Wf
			Long contaAutorizzatore = stepWfService.checkPendingWf(matricolaTopResponsabile);
			Long contaApprovatore = stepWfService.checkPendingWf(approvatoreView.getVo().getUtente().getMatricolaUtil());
			Boolean autorizzatoreStatoWf= contaAutorizzatore>0?true:false;
			Boolean approvatoreStatoWf= contaApprovatore>0?true:false;
			configurazioneWorkflowView.setAutorizzatoreStatoWf(autorizzatoreStatoWf);
			configurazioneWorkflowView.setApprovatoreStatoWf(approvatoreStatoWf);
			
			// id 
			Long idAutorizzatoreRigaWf1=configurazioneStepWfService.leggiAutorizzatoreRigaIdWf(1).getVo().getId();
			Long idAutorizzatoreRigaWf2=configurazioneStepWfService.leggiAutorizzatoreRigaIdWf(2).getVo().getId();
			Long idAutorizzatoreRigaWf3=configurazioneStepWfService.leggiAutorizzatoreRigaIdWf(3).getVo().getId();
			Long idAutorizzatoreRigaWf4=configurazioneStepWfService.leggiAutorizzatoreRigaIdWf(4).getVo().getId();
			configurazioneWorkflowView.setIdAutorizzatoreRigaWf1(idAutorizzatoreRigaWf1);
			configurazioneWorkflowView.setIdAutorizzatoreRigaWf2(idAutorizzatoreRigaWf2);
			configurazioneWorkflowView.setIdAutorizzatoreRigaWf3(idAutorizzatoreRigaWf3);
			configurazioneWorkflowView.setIdAutorizzatoreRigaWf4(idAutorizzatoreRigaWf4);
			
			Long idApprovatoreRigaWf1=configurazioneStepWfService.leggiApprovatoreRigaIdWf(1).getVo().getId();
			Long idApprovatoreRigaWf2=configurazioneStepWfService.leggiApprovatoreRigaIdWf(2).getVo().getId();
			Long idApprovatoreRigaWf3=configurazioneStepWfService.leggiApprovatoreRigaIdWf(3).getVo().getId();
			Long idApprovatoreRigaWf4=configurazioneStepWfService.leggiApprovatoreRigaIdWf(4).getVo().getId();
			configurazioneWorkflowView.setIdApprovatoreRigaWf1(idApprovatoreRigaWf1);
			configurazioneWorkflowView.setIdApprovatoreRigaWf2(idApprovatoreRigaWf2);
			configurazioneWorkflowView.setIdApprovatoreRigaWf3(idApprovatoreRigaWf3);
			configurazioneWorkflowView.setIdApprovatoreRigaWf4(idApprovatoreRigaWf4);
			
			configurazioneWorkflowView.setIdAutorizzatore(autorizzatoreView.getVo().getId());
			configurazioneWorkflowView.setIdApprovatore(approvatoreView.getVo().getId());
			
			// approvatore
			configurazioneWorkflowView.setApprovatoreNomeCognome(approvatoreView.getVo().getUtente().getNominativoUtil());
			configurazioneWorkflowView.setMatricolaApprovatore(approvatoreView.getVo().getUtente().getMatricolaUtil());
			configurazioneWorkflowView.setApprovatoreNomeCognomeSenzaVirgola(approvatoreView.getVo().getUtente().getNominativoUtil().replace(',', ' '));
			
			// Lista utenti
			List<UtenteView> utenti=utenteService.leggiUtenti(false);
			utenti=togliUtentiSenzaEmail(utenti);
			List<UtenteView> listaAutorizzatori=utenti; //togliAutorizzatore(utenti,matricolaTopResponsabile);
			List<UtenteView> listaApprovatori=utenti; //togliApprovatore(utenti,approvatoreView.getVo().getUtente().getMatricolaUtil());
			
			// Lista Autorizzatori
			configurazioneWorkflowView.setListaAutorizzatoriJson(convertListaUtentiToJson(listaAutorizzatori));
			configurazioneWorkflowView.setListaAutorizzatori(listaAutorizzatori);
			
			// Lista Approvatori
			configurazioneWorkflowView.setListaApprovatoriJson(convertListaUtentiToJson(listaApprovatori));
			configurazioneWorkflowView.setListaApprovatori(listaApprovatori);
		} 
		catch (Throwable e) {
			//e.printStackTrace();
			model.addAttribute(MODEL_VIEW_NOME, configurazioneWorkflowView);
			return PAGINA_FORM_CONFIGURAZIONE_WORKFLOW;
		}
		model.addAttribute(MODEL_VIEW_NOME, configurazioneWorkflowView);
		return PAGINA_FORM_CONFIGURAZIONE_WORKFLOW;
		
		}
		else
			return null;
	}
	
//	private List<ConfigurazioneStepWfView> getApprovatori(List<ConfigurazioneStepWfView> lista) {
//		List<ConfigurazioneStepWfView> listaRet = new ArrayList<ConfigurazioneStepWfView>();
//		for(int i=0; i<lista.size(); i++) {
//			ConfigurazioneStepWfView view = lista.get(i);
//			ConfigurazioneStepWf vo = view.getVo();
//			if(vo.getCodGruppoLingua().equals("WF_INC_6")) {
//				listaRet.add(view);
//			}
//		}
//		return listaRet;
//	}
//	
//	private List<UtenteView> togliAutorizzatore(List<UtenteView> lista, String matricolaTopResponsabile) {
//		List<UtenteView> ret = new ArrayList<UtenteView>(0);
//		for(int i=0; i<lista.size(); i++) {
//			if( ! lista.get(i).getVo().getMatricolaUtil().equals(matricolaTopResponsabile) ){				
//				ret.add(lista.get(i));
//			}
//		}
//		return ret;
//	}
//	
//	private List<UtenteView> togliApprovatore(List<UtenteView> lista, String matricola) {
//		List<UtenteView> ret = new ArrayList<UtenteView>(0);
//		for(int i=0; i<lista.size(); i++) {
//			if( ! lista.get(i).getVo().getMatricolaUtil().equals(matricola) ){				
//				ret.add(lista.get(i));
//			}
//		}
//		return ret;
//	}


	private List<UtenteView> togliUtentiSenzaEmail(List<UtenteView> utenti) {
		List<UtenteView> ret = new ArrayList<UtenteView>(0);
		
		for(int i=0; i<utenti.size(); i++) {
			if(utenti.get(i).getVo().getEmailUtil() != null) {
				ret.add(utenti.get(i));
			}
		}
		
		return ret;
	}
	
	
	private String convertListaUtentiToJson(List<UtenteView> lista)  throws Throwable {
		JSONArray jsonArray = new JSONArray();
			if( lista != null && lista.size() > 0 ){
				for( UtenteView view : lista ){
					JSONObject jsonObject = new JSONObject();
					
					Utente vo = view.getVo();
					
					String nominativoutil = vo.getNominativoUtil();
					nominativoutil=StringEscapeUtils.escapeJson(nominativoutil);
					nominativoutil=replaceApostrofo(nominativoutil);
					jsonObject.put("nominativoutil", nominativoutil);
					
					String matricolautil = vo.getMatricolaUtil();
					matricolautil=StringEscapeUtils.escapeJson(matricolautil);
					matricolautil=replaceApostrofo(matricolautil);
					jsonObject.put("matricolautil", matricolautil);
					
					String emailutil = vo.getEmailUtil();
					emailutil=StringEscapeUtils.escapeJson(emailutil);
					emailutil=replaceApostrofo(emailutil);
					jsonObject.put("emailutil", emailutil);
					
					jsonArray.put(jsonObject); 
				}
			}
		
		if (jsonArray.length() > 0) {
			return jsonArray.toString();
		} 
		else
			return null;
	}
	
	private String replaceApostrofo(String str) {
		if(str==null) return null;
		StringBuffer nuovo= new StringBuffer();
		for(int i=0; i<str.length(); i++) {
			if( str.charAt(i) == '\'' ) 
				nuovo.append("&#39;");
			else
				nuovo.append(str.charAt(i));
		}
			
		return nuovo.toString();
	}
	
}
