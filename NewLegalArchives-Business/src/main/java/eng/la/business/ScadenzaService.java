package eng.la.business;

import java.util.Date;
import java.util.List;

import eng.la.model.view.ScadenzaView;

public interface ScadenzaService {
	public void cancella(long id) throws Throwable;
	public ScadenzaView inserisci(ScadenzaView evento) throws Throwable;
	public void modifica(ScadenzaView scadenza) throws Throwable;	
	public List<ScadenzaView> cerca(ScadenzaView view) throws Throwable;
	public ScadenzaView leggi(long id) throws Throwable;
	public List<ScadenzaView> leggi() throws Throwable;
	// extra
	public List<ScadenzaView> leggi(Date inizio,Date fine) throws Throwable;
}