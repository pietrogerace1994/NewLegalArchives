package eng.la.business;

import java.util.Date;
import java.util.List;

import eng.la.model.view.AgendaNotificheDisplayedView;

public interface AgendaNotificheDisplayedService {

	public AgendaNotificheDisplayedView addNotificaDisplayed(AgendaNotificheDisplayedView vo) throws Throwable;
	public List<AgendaNotificheDisplayedView> leggi(String matricolaUtente, String tipo) throws Throwable;
	public List<AgendaNotificheDisplayedView> leggiLe(Date data) throws Throwable;
	public void cancella(AgendaNotificheDisplayedView view) throws Throwable;
}
