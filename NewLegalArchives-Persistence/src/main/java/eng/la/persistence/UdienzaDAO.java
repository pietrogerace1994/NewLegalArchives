/*
 * @author Benedetto Giordano
 */
package eng.la.persistence;

import java.util.List;

import eng.la.model.Udienza;

public interface UdienzaDAO {
	public List<Udienza> leggi() throws Throwable;

	public List<Udienza> cerca(String dal, String al, String nomeFascicolo, String legaleInterno, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	public Long conta(String dal, String al, String nomeFascicolo, String legaleInterno) throws Throwable;
	
	public Udienza leggi(long id) throws Throwable;
	
	public Udienza inserisci(Udienza vo) throws Throwable;

	public void modifica(Udienza vo) throws Throwable;

	public void cancella(long id) throws Throwable;
}