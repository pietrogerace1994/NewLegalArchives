package eng.la.persistence;

import java.util.Date;
//import java.util.Date;
import java.util.List;
import eng.la.model.Evento;

public interface EventoDAO {
	public void cancella(long id) throws Throwable;
	public Evento inserisci(Evento vo) throws Throwable;
	public void modifica(Evento vo) throws Throwable;
	
	public Evento leggi(long id) throws Throwable;
	public List<Evento> leggi() throws Throwable;	
	public List<Evento> cerca(Evento vo) throws Throwable;
	public List<Evento> leggi(Date inizio, Date fine) throws Throwable;
}