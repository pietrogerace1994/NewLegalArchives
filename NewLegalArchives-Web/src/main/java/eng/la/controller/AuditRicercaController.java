package eng.la.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eng.la.business.AuditService;
import eng.la.business.UtenteService;
import eng.la.model.rest.AuditRest;
import eng.la.model.rest.RiassegnaRest;
import eng.la.model.rest.RicercaAuditRest;
import eng.la.model.view.AuditLogView;
import eng.la.model.view.BaseView;
import eng.la.model.view.UtenteView;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("auditRicercaController") 
public class AuditRicercaController extends BaseController { 
	private static final String PAGINA_RICERCA_PATH = "audit/cercaAuditLog"; 

	@Autowired
	private UtenteService utenteService;
	
	@RequestMapping(value = "/audit/ricerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaAuditRest ricerca(HttpServletRequest request, Locale locale) {


		try {
			int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA
					: NumberUtils.toInt(request.getParameter("limit"));
			String offset = request.getParameter("offset");
			int numeroPagina = offset == null || offset.equals("0") ? 1
					: (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
			String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
			String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
			String tipoEntita = request.getParameter("tipoEntita") == null ? ""
					: URLDecoder.decode(request.getParameter("tipoEntita"), "UTF-8");
			String userId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
			 
			String dal = request.getParameter("dal") == null ? ""
					: URLDecoder.decode(request.getParameter("dal"), "UTF-8");
			String al = request.getParameter("al") == null ? ""
					: URLDecoder.decode(request.getParameter("al"), "UTF-8");
			AuditService auditService = (AuditService)SpringUtil.getBean("auditService");
			ListaPaginata<AuditLogView> lista = (ListaPaginata<AuditLogView>) auditService.cerca(tipoEntita, userId,
					dal, al, numElementiPerPagina, numeroPagina, ordinamento,
					tipoOrdinamento);
			RicercaAuditRest ricercaModelRest = new RicercaAuditRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<AuditRest> rows = new ArrayList<AuditRest>();
			for (AuditLogView view : lista) {
				AuditRest auditRest = new AuditRest();
				auditRest.setDataOra( DateUtil.formattaDataOra(view.getVo().getDataOra().getTime()));
				auditRest.setNomeServer(view.getVo().getNomeServer());
				auditRest.setNomeApp(view.getVo().getNomeApp());
				auditRest.setOperazione(parseOperazione(view.getVo().getOperazione()));
				String nomeOggetto = view.getVo().getNomeOggetto();
				nomeOggetto = nomeOggetto.contains("\\") ? nomeOggetto.substring(nomeOggetto.indexOf("\\")+1, nomeOggetto.length()) : nomeOggetto;
				auditRest.setNomeOggetto(nomeOggetto);
				auditRest.setUserId(parseUserId(view.getVo().getUserId()));
				auditRest.setOpzionale(view.getVo().getOpzionale());
				auditRest.setValoreOld(view.getVo().getValoreOld());
				auditRest.setValoreNew(view.getVo().getValoreNew());
				auditRest.setNote(view.getVo().getNote());
				 
				rows.add(auditRest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	private String parseUserId(String userId) {
		try {
			UtenteView utenteView = utenteService.leggiUtenteDaUserId(userId);
			if( utenteView.getVo() != null ){
				return utenteView.getVo().getNominativoUtil();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	private String parseOperazione(String operazione) {
		if( operazione.equalsIgnoreCase("UPDATE") ){
			return "AGGIORNAMENTO";
		}else if( operazione.equalsIgnoreCase("INSERT") ){
			return "CREAZIONE";
		}else if( operazione.equalsIgnoreCase("DELETE") ){
			return "CANCELLAZIONE";
		}else if( operazione.equalsIgnoreCase("READ") ){
			return "VISUALIZZAZIONE";
		}else{
			return operazione;
		}
	}

	@RequestMapping(value = "/audit/cerca", method = RequestMethod.GET)
	public String cercaProforma(HttpServletRequest request, Model model, Locale locale) {
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {  
			return PAGINA_RICERCA_PATH;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}
 

	@RequestMapping(value = "/audit/caricaComboUserId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<RiassegnaRest> caricaComboUserId(HttpServletRequest request, Locale locale) {


		try {
			 List<RiassegnaRest> list = new ArrayList<RiassegnaRest>();
			 List<UtenteView> utenti= utenteService.leggiUtenti();
			 if(utenti!=null && utenti.size()>0){
				 for( UtenteView view : utenti ){ 
					 RiassegnaRest rest = new RiassegnaRest();
					 rest.setOwner(view.getVo().getNominativoUtil());
					 rest.setIdOwner(view.getVo().getUseridUtil());
					 list.add(rest);
				 }
				 
			 } 
			 return list;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	
	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}


	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		
	}
 

}
