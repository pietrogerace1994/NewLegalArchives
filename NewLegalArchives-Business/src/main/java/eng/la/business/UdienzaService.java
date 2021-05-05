/*
 * @author Benedetto Giordano
 */
package eng.la.business;

import java.util.List;

import eng.la.model.Utente;
import eng.la.model.view.UdienzaView;
import eng.la.util.ListaPaginata;
/**
 * Interface UdienzaService.
 */
public interface UdienzaService {

	public List<UdienzaView> leggi() throws Throwable;

	public UdienzaView leggi(long id) throws Throwable;
	
	public ListaPaginata<UdienzaView> cerca(String dal, String al, String nomeFascicolo, String legaleInterno, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	public Long conta(String dal, String al, String nomeFascicolo, String legaleInterno) throws Throwable;

	public UdienzaView inserisci(UdienzaView udienzaView) throws Throwable;

	public void modifica(UdienzaView udienzaView) throws Throwable;

	public void cancella(UdienzaView udienzaView) throws Throwable;
	
	public List<Utente> getListaLegaleInternoOwnerFascicolo(long idFascicolo) throws Throwable;
}
