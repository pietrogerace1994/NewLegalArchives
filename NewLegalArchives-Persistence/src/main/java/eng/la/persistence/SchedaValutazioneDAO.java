package eng.la.persistence;

//import java.util.Date;
import java.util.List;
import eng.la.model.SchedaValutazione;

public interface SchedaValutazioneDAO {
	public void cancella(long id) throws Throwable;
	public SchedaValutazione inserisci(SchedaValutazione vo) throws Throwable;
	public void modifica(SchedaValutazione vo) throws Throwable;
	
	public SchedaValutazione leggi(long id) throws Throwable;
	public List<SchedaValutazione> leggi() throws Throwable;	
	public List<SchedaValutazione> cerca(SchedaValutazione vo) throws Throwable;
}