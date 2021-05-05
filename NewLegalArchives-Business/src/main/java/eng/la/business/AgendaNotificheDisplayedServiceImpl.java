package eng.la.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.NotificheAgendaDisplayed;
import eng.la.model.view.AgendaNotificheDisplayedView;
import eng.la.persistence.AgendaNotificheDisplayedDAO;

@Service("agendaNotificheDisplayedService")
public class AgendaNotificheDisplayedServiceImpl extends BaseService<NotificheAgendaDisplayed, AgendaNotificheDisplayedView> implements AgendaNotificheDisplayedService {

	@Autowired
	private AgendaNotificheDisplayedDAO agendaNotificheDisplayedDAO;
	
	@Override
	public AgendaNotificheDisplayedView addNotificaDisplayed(AgendaNotificheDisplayedView agendaNotificheDisplayedView) throws Throwable {
		NotificheAgendaDisplayed accessiNotifiche = agendaNotificheDisplayedDAO.addNotificaDisplayed(agendaNotificheDisplayedView.getVo());
		AgendaNotificheDisplayedView view = new AgendaNotificheDisplayedView();
		view.setVo(accessiNotifiche);
		return view;
	}
	
	@Override
	public List<AgendaNotificheDisplayedView> leggi(String matricolaUtente, String tipo) throws Throwable {
		List<NotificheAgendaDisplayed> lista = agendaNotificheDisplayedDAO.leggi(matricolaUtente,tipo);
		List<AgendaNotificheDisplayedView> listaview = convertiVoInView(lista);
		return listaview;
	}
	
	@Override
	public List<AgendaNotificheDisplayedView> leggiLe(Date data) throws Throwable {
		List<NotificheAgendaDisplayed> lista2 = agendaNotificheDisplayedDAO.leggiLe2(data);
		List<AgendaNotificheDisplayedView> listaview = convertiVoInView(lista2);
		return listaview;
	}
	
	@Override
	public void cancella(AgendaNotificheDisplayedView view) throws Throwable {
		agendaNotificheDisplayedDAO.cancella(view.getVo());
	}
	
	
	@Override
	protected Class<NotificheAgendaDisplayed> leggiClassVO() {
		return NotificheAgendaDisplayed.class;
	}

	@Override
	protected Class<AgendaNotificheDisplayedView> leggiClassView() {
		return AgendaNotificheDisplayedView.class;
	}
  
}
