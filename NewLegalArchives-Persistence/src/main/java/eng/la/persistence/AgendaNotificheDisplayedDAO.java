package eng.la.persistence;

import java.util.Date;
//import java.util.Date;
import java.util.List;

import eng.la.model.NotificheAgendaDisplayed;

public interface AgendaNotificheDisplayedDAO {
	
	public NotificheAgendaDisplayed addNotificaDisplayed(NotificheAgendaDisplayed vo) throws Throwable;
	public List<NotificheAgendaDisplayed> leggi(String matricolaUtente, String tipo) throws Throwable;
	public List<NotificheAgendaDisplayed> leggiLe(Date data) throws Throwable;
	void cancella(NotificheAgendaDisplayed vo) throws Throwable;
	List<NotificheAgendaDisplayed> leggiLe2(Date data) throws Throwable;
	List<NotificheAgendaDisplayed> leggiLe3(Date data) throws Throwable;
}