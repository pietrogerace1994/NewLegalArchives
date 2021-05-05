package eng.la.business;

import java.util.Date;
import java.util.List;

import eng.la.model.view.EventoView;

public interface EventoService {
	public void cancella(long id) throws Throwable;
	public EventoView inserisci(EventoView evento) throws Throwable;
	public void modifica(EventoView evento) throws Throwable;	
	public List<EventoView> cerca(EventoView view) throws Throwable;
	public EventoView leggi(long id) throws Throwable;
	public List<EventoView> leggi() throws Throwable;
	// extra
	public List<EventoView> leggi(Date inizio, Date fine) throws Throwable;
}