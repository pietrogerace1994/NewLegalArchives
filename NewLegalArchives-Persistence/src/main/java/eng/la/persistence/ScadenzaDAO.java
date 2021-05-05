package eng.la.persistence;

import java.util.Date;
//import java.util.Date;
import java.util.List;
import eng.la.model.Scadenza;

public interface ScadenzaDAO {
	public void cancella(long id) throws Throwable;
	public Scadenza inserisci(Scadenza vo) throws Throwable;
	public void modifica(Scadenza vo) throws Throwable;
	
	public Scadenza leggi(long id) throws Throwable;
	public List<Scadenza> leggi() throws Throwable;	
	public List<Scadenza> cerca(Scadenza vo) throws Throwable;
	
	public List<Scadenza> leggi(Date inizio, Date fine) throws Throwable;
}