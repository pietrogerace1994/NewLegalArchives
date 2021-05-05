package eng.la.controller;
 
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import eng.la.business.EmailService;
import eng.la.business.NazioneService;
import eng.la.business.RubricaService;
import eng.la.business.SocietaService;
import eng.la.business.SpecializzazioneService;
import eng.la.model.view.BaseView;
import eng.la.model.view.EmailView;
import eng.la.model.view.NazioneView;
import eng.la.model.view.RubricaView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.SpecializzazioneView;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;

public abstract class BaseController implements Costanti{
	
	
	
	//PARAMETRO PER PAGINAZIONE ELEMENTI A VIDEO
	public static final int ELEMENTI_PER_PAGINA = 10; 
	
	public void caricaListe(BaseView view, Locale locale) throws Throwable{
		NazioneService nazioneService = (NazioneService) SpringUtil.getBean("nazioneService");
		SocietaService societaService = (SocietaService) SpringUtil.getBean("societaService");		
		SpecializzazioneService specializzazioneService = (SpecializzazioneService) SpringUtil.getBean("specializzazioneService");
		RubricaService rubricaService = (RubricaService) SpringUtil.getBean("rubricaService");
		EmailService emailService = (EmailService) SpringUtil.getBean("emailService");
		List<NazioneView> listaNazioni = nazioneService.leggi(locale, false); 
		List<SocietaView> listaSocieta = societaService.leggi(false);
		List<SpecializzazioneView> listaSpecializzazioni = specializzazioneService.leggi(locale);
		List<RubricaView> listaRubrica = rubricaService.leggiRubrica();
		List<EmailView> listaArticoli = (List<EmailView>) emailService.leggiEmail();
		view.setListaNazioni(listaNazioni);
		view.setListaSocieta(listaSocieta);
		view.setListaSpecializzazioni(listaSpecializzazioni);
		view.setListaRubrica(listaRubrica);
		view.setListaArticoli(listaArticoli);
		view.setLocale(locale);
		caricaListeOggetti(view, locale);
	}
	
 
	protected String invocaMetodoDaReflection(BaseView baseView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response, BaseController controller) throws Throwable {
		String op = baseView.getOp();
		Class<?>[] paramsType = new Class[]{baseView.getClass(), BindingResult.class, Locale.class, Model.class, HttpServletRequest.class, HttpServletResponse.class};
		Object[] paramsValue = new Object[]{baseView, bindingResult, locale, model, request, response};
		
		Method metodo = controller.getClass().getMethod(op, paramsType);
		String valoreRitorno = (String) metodo.invoke(controller, paramsValue);  
		return valoreRitorno;
	}
	
	public abstract void caricaListeOggetti(BaseView view, Locale locale) throws Throwable;


	public void caricaListePerDettaglio(BaseView view, Locale locale) throws Throwable {
		NazioneService nazioneService = (NazioneService) SpringUtil.getBean("nazioneService");
		SocietaService societaService = (SocietaService) SpringUtil.getBean("societaService");		
 
		List<NazioneView> listaNazioni = nazioneService.leggi(locale, true); 
		List<SocietaView> listaSocieta = societaService.leggi(true);
		view.setListaNazioni(listaNazioni);
		view.setListaSocieta(listaSocieta);
		view.setLocale(locale);
		caricaListeOggettiPerDettaglio(view, locale);
	}


	public abstract void caricaListeOggettiPerDettaglio(BaseView view, Locale locale)  throws Throwable;
}
