package eng.la.persistence;

import java.util.List;

import eng.la.model.NotificaPec;

public interface NotificaPecDAO {

	public List<NotificaPec> leggi(String matricola) throws Throwable;
	public void inserisci(NotificaPec notificaWeb) throws Throwable;
	public void aggiorna(NotificaPec notificaWeb) throws Throwable;
	public void cancellaAltre(Long id, Long idUtentePec) throws Throwable;
	public NotificaPec leggiById(Long id) throws Throwable;
}
