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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eng.la.business.RubricaService;
import eng.la.model.rest.RicercaRubricaRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.rest.RubricaRest;
import eng.la.model.view.BaseView;
import eng.la.model.view.RubricaView;
import eng.la.util.ListaPaginata;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("rubricaRicercaController") 
public class RubricaRicercaController extends BaseController {
	private static final String MODEL_VIEW_NOME = "rubricaRicercaView"; 
	private static final String PAGINA_RICERCA_PATH = "rubrica/cercaRubrica";
	private static final String PAGINA_AZIONI_CERCA_RUBRICA = "rubrica/azioniCercaRubrica";
	private static final String PAGINA_AZIONI_RUBRICA = "rubrica/azioniRubrica";
 
	@Autowired
	private RubricaService rubricaService;

	public void setRubricaService(RubricaService rubricaService) {
		this.rubricaService = rubricaService;
	}
 

	@RequestMapping(value = "/rubrica/eliminaRubrica", produces = "application/json")
	public @ResponseBody RisultatoOperazioneRest eliminaRubrica(@RequestParam("id") long id, Model model) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			//RubricaView rubricaView = rubricaService.leggiRubrica(id);
			rubricaService.cancellaRubrica(id);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}


	@RequestMapping(value = "/rubrica/ricerca", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaRubricaRest cercaRubrica(HttpServletRequest request, Locale locale) {
		
		// engsecurity VA 
			//	HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
			//	htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA
					: NumberUtils.toInt(request.getParameter("limit"));
			String offset = request.getParameter("offset");
			int numeroPagina = offset == null || offset.equals("0") ? 1
					: (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
			String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
			String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
			String nominativo = request.getParameter("nominativo") == null ? ""
					: URLDecoder.decode(request.getParameter("nominativo"), "UTF-8");
			String email = request.getParameter("email") == null ? "" : request.getParameter("email");
			

			ListaPaginata<RubricaView> lista = (ListaPaginata<RubricaView>) rubricaService.cerca(nominativo, email, numElementiPerPagina, numeroPagina, ordinamento, tipoOrdinamento);
			RicercaRubricaRest ricercaModelRest = new RicercaRubricaRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<RubricaRest> rows = new ArrayList<RubricaRest>();
			for (RubricaView view : lista) {
				RubricaRest rubricaRest = new RubricaRest();
				rubricaRest.setId(view.getVo().getId());
				rubricaRest.setNominativo(view.getVo().getNominativo());
				rubricaRest.setEmail(view.getVo().getEmail());
				rubricaRest.setAzioni("<p id='containerAzioniRigaRubrica" + view.getVo().getId() + "'></p>");
				
				rows.add(rubricaRest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/rubrica/cerca", method = RequestMethod.GET)
	public String cercaRubrica(HttpServletRequest request, Model model, Locale locale) {
		
		// engsecurity VA 
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			RubricaView view = new RubricaView();
			model.addAttribute(MODEL_VIEW_NOME, view);
			return PAGINA_RICERCA_PATH;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/rubrica/caricaAzioniRubrica", method = RequestMethod.POST)
	public String caricaAzioniRubrica(@RequestParam("idRubrica") long idRubrica, HttpServletRequest req,
			Locale locale) {
		// engsecurity VA 
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(req);
		        //removeCSRFToken(request);
		
		try {

			// TODO: LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI
			// SUL PROFORMA
			req.setAttribute("idRubrica", idRubrica);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if (req.getParameter("onlyContent") != null) {

			return PAGINA_AZIONI_RUBRICA;
		}

		return PAGINA_AZIONI_CERCA_RUBRICA;
	} 

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}


	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
	}
 

}
