package eng.la.persistence;

//import java.util.Date;
import java.util.List;
import eng.la.model.Fattura;

public interface FatturaDAO {
	public void cancella(long id) throws Throwable;
	public Fattura inserisci(Fattura vo) throws Throwable;
	public void modifica(Fattura vo) throws Throwable;
	
	public Fattura leggi(long id) throws Throwable;
	public List<Fattura> leggi() throws Throwable;	
	public List<Fattura> cerca(Fattura vo) throws Throwable;
}
