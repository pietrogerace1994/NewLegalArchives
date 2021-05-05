package eng.la.persistence;

import java.util.List;

import eng.la.model.NotificaWeb;

public interface NotificaWebDAO {

	public void inserisci(NotificaWeb notificaWeb) throws Throwable;
	public void aggiorna(NotificaWeb notificaWeb) throws Throwable;
	public List<NotificaWeb> leggi(String matricola) throws Throwable;
	public NotificaWeb leggiById(Long id) throws Throwable;
	public List<NotificaWeb> leggiPerInvioMail(String matricola, String keyMessage) throws Throwable;
}
