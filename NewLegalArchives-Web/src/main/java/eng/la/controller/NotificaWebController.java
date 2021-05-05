package eng.la.controller;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eng.la.business.NotificaWebService;
import eng.la.model.view.BaseView;

@Controller("notificaWebController")
public class NotificaWebController extends BaseController {
	
	@Autowired
	private NotificaWebService notificaWebService;
	
	@RequestMapping(value = "/notificaWeb/marcaNotificaWebLetta", method = RequestMethod.POST)
	public 
	 @ResponseBody void marcaNotificaWebLetta(HttpServletRequest request, Locale locale) {


		try 
		{
			Long id=Long.valueOf(request.getParameter("id")!=null?request.getParameter("id"):null);
			
			notificaWebService.marcaNotificaWebLetta(id,new Date());
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
	}
}
