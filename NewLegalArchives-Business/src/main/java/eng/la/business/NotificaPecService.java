package eng.la.business;

import java.util.Date;
import java.util.List;

import eng.la.model.view.NotificaPecView;

public interface NotificaPecService {
	
	public List<NotificaPecView> leggi(String matricola)throws Throwable;
	public void inserisci(Long id) throws Throwable;
	public void rifiuta(Long id, String motivo) throws Throwable;
	public void cancellaAltre(Long id, Long idUtentePec) throws Throwable;
	public void trasforma(Long id) throws Throwable;
	public void marcaNotificaPecLetta(Long id, Date date) throws Throwable;
}
