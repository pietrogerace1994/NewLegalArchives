package eng.la.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import eng.la.business.NazioneService;
import eng.la.business.SocietaService;
import eng.la.business.TipoSocietaService;
import eng.la.model.Societa;
import eng.la.model.rest.SocietaRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.NazioneView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.TipoSocietaView;
import eng.la.persistence.CostantiDAO;
import eng.la.presentation.validator.SocietaValidator;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("societaController")
@SessionAttributes("societaView")
public class SocietaController extends BaseController {

	private static final String MODEL_VIEW_NOME = "societaView";
	private static final String PAGINA_FORM_READ_PATH = "societa/formReadSocieta";
	private static final String PAGINA_FORM_EDIT_PATH = "societa/formEditSocieta";

	@Autowired
	@Qualifier("societaService")
	private SocietaService societaService;

	@Autowired
	@Qualifier("tipoSocietaService")
	private TipoSocietaService tipoSocietaService;

	@Autowired
	@Qualifier("nazioneService")
	private NazioneService nazioneService;

	@RequestMapping("/societa/visualizzaSocieta")
	public String visualizzaSocieta(HttpServletRequest request, Model model, Locale locale) {
		// engsecurity VA 
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		SocietaView societaView = new SocietaView();

		try {
			super.caricaListe(societaView, locale);
			societaView.setLocale(locale);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		societaView.setVo(new Societa());
		model.addAttribute(MODEL_VIEW_NOME, societaView);
		return PAGINA_FORM_READ_PATH;
	}

	@RequestMapping("/societa/gestioneSocieta")
	public String gestioneSocieta(HttpServletRequest request, Model model, Locale locale) {
		SocietaView societaView = new SocietaView();
		// engsecurity VA - redirect
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
		//removeCSRFToken(request);
		
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

			try {
				super.caricaListe(societaView, locale);
			} catch (Throwable e) {
				e.printStackTrace();
			}

			societaView.setVo(new Societa());
			model.addAttribute(MODEL_VIEW_NOME, societaView);
			return PAGINA_FORM_EDIT_PATH;
		}
		else
			return null;
	}

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		List<TipoSocietaView> listaTipoSocieta = tipoSocietaService.leggi(locale);
		SocietaView societaView = (SocietaView) view;
		societaView.setListaTipoSocieta(listaTipoSocieta);
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}

	@RequestMapping(value = "/societa/caricaDettaglioSocieta", method = RequestMethod.POST)
	public @ResponseBody SocietaRest caricaDettaglioSocieta(@RequestParam("id") Long id) {
		SocietaRest societaRest = new SocietaRest();



		try {
			SocietaView societaView = societaService.leggi(id);
			societaRest.setId(id);
			societaRest.setNome(societaView.getVo().getNome());
			societaRest.setRagioneSociale(societaView.getVo().getRagioneSociale());


			String emailAmministrazione = societaView.getVo().getEmailAmministrazione();
			List<String> emails = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(emailAmministrazione, "#;#");
			while( st.hasMoreTokens() ) {
				String tok = st.nextToken();
				emails.add(tok);
			}
			societaRest.setEmailAmministrazione( emails );

			societaRest.setIndirizzo(societaView.getVo().getIndirizzo());
			societaRest.setCap(societaView.getVo().getCap());
			societaRest.setCitta(societaView.getVo().getCitta());
			societaRest.setIdTipoSocieta(societaView.getVo().getTipoSocieta().getId());
			if( societaView.getVo().getNazione() != null )
				societaRest.setIdNazione(societaView.getVo().getNazione().getId());
			if( societaView.getVo().getNazione() != null )
				societaRest.setCodGruppoLingua(societaView.getVo().getNazione().getCodGruppoLingua());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return societaRest;
	}

	@RequestMapping(value = "/societa/salva", method = RequestMethod.POST)
	public String salvaSocieta(
			Locale locale, 
			Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated SocietaView societaView, 
			BindingResult bindingResult,
			HttpServletRequest request, 
			HttpServletResponse response) {
		// engsecurity VA 
		String token=request.getParameter("CSRFToken");
		
		
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		boolean flagView = false;
		List<String> rolesCode = currentSessionUtil.getRolesCode();

		for(String code : rolesCode){

			if( code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){

				flagView = true;
			}
		}
		if(flagView){

		try {
			if (societaView.getOp() != null && !societaView.getOp().equals("salvaSocieta")) {
				String ritorno = invocaMetodoDaReflection(societaView, bindingResult, locale, model, request,
						response, this);
				return ritorno == null ? PAGINA_FORM_EDIT_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {
				super.caricaListe(societaView, locale);
				return PAGINA_FORM_EDIT_PATH;
			}

			if (societaView.isInsertMode()) {

				Societa vo = societaView.getVo();
				if(vo == null)
					vo = new Societa();
				vo.setNome(societaView.getNomeIns());
				vo.setRagioneSociale(societaView.getRagioneSocialeIns());

				List<String> emails = societaView.getEmailAmministrazioneIns();
				vo.setEmailAmministrazione( emailsToString(emails) );

				vo.setIndirizzo(societaView.getIndirizzoIns());
				vo.setCap(societaView.getCapIns());
				vo.setCitta(societaView.getCittaIns());

				Long idTipoSocieta = societaView.getIdTipoSocietaIns();
				TipoSocietaView tipoSocietaView = tipoSocietaService.leggi(idTipoSocieta);
				vo.setTipoSocieta(tipoSocietaView.getVo());

				String nazioneCode = societaView.getNazioneCodeIns();
				List<NazioneView> listaNazioni = nazioneService.leggibyCodice(nazioneCode);
				if( listaNazioni != null && !listaNazioni.isEmpty() )
					vo.setNazione(listaNazioni.get(0).getVo());

				societaView.setVo(vo);
				societaService.inserisci(societaView);

			} else if(societaView.isDeleteMode()){
				societaService.cancella(societaView);
			} else if(societaView.isEditMode()) {

				// Carico vo
				Societa vo = societaView.getVo();
				if(vo == null)
					vo = new Societa();
				vo.setId(societaView.getIdSocieta());
				vo.setNome(societaView.getNome());
				vo.setRagioneSociale(societaView.getRagioneSociale());

				vo.setEmailAmministrazione( emailsToString( societaView.getEmailAmministrazione() ) );

				vo.setIndirizzo(societaView.getIndirizzo());
				vo.setCap(societaView.getCap());
				vo.setCitta(societaView.getCitta());

				Long idTipoSocieta = societaView.getIdTipoSocieta();
				TipoSocietaView tipoSocietaView = tipoSocietaService.leggi(idTipoSocieta);
				vo.setTipoSocieta(tipoSocietaView.getVo());

				String nazioneCode = societaView.getNazioneCode();
				List<NazioneView> listaNazioni = nazioneService.leggibyCodice(nazioneCode);
				if( listaNazioni != null && !listaNazioni.isEmpty() )
					vo.setNazione(listaNazioni.get(0).getVo());

				societaService.modifica(societaView);
			}

			model.addAttribute("successMessage", "messaggio.operazione.ok");
			return "redirect:gestioneSocieta.action?CSRFToken="+token;
		} catch (Throwable e) {
			bindingResult.addError(new ObjectError("erroreGenerico", "errore.generico"));
			return PAGINA_FORM_EDIT_PATH;
		}
		}
		else
			return null;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new SocietaValidator());
	}

	private String emailsToString( List<String> emails  ) {
		String emailAmministr = ""; 
		int i=0;
		for(; i<emails.size()-1; i++) {
			if( emails.get(i) != null ) {
				emailAmministr += emails.get(i) + ";";
			}
			if( emails.get(i) == null ) {
				continue;
			}
		}
		if( i < emails.size() )
			emailAmministr += emails.get(i);

		return emailAmministr;

	}

}