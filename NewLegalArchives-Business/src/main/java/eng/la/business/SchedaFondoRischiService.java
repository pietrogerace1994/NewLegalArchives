/*
 * @author Benedetto Giordano
 */
package eng.la.business;

import java.util.Date;
import java.util.List;

import eng.la.model.view.SchedaFondoRischiView;
import eng.la.model.view.StoricoSchedaFondoRischiView;
import eng.la.util.ListaPaginata;
/**
 * Interface SchedaFondoRischiService.
 */
public interface SchedaFondoRischiService {

	public List<SchedaFondoRischiView> leggi() throws Throwable;

	public SchedaFondoRischiView leggi(long id) throws Throwable;
	
	public ListaPaginata<SchedaFondoRischiView> cerca(String dal, String al, String statoSchedaFondoRischiCode, String tipologiaSchedaCode, String rischioSoccombenzaCode, String nomeFascicolo, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	public Long conta(String dal, String al, String statoSchedaFondoRischiCode, String tipologiaSchedaCode, String rischioSoccombenzaCode, String controparte, String nomeFascicolo  ) throws Throwable;

	public SchedaFondoRischiView inserisci(SchedaFondoRischiView schedaFondoRischiView) throws Throwable;

	public void modifica(SchedaFondoRischiView schedaFondoRischiView) throws Throwable;

	public void cancella(SchedaFondoRischiView schedaFondoRischiView) throws Throwable;

	public List<SchedaFondoRischiView> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner) throws Throwable;
	
	public StoricoSchedaFondoRischiView inserisciStorico(StoricoSchedaFondoRischiView storicoSchedaFondoRischiView) throws Throwable;
	
	public List<SchedaFondoRischiView> cercaNelTrimestre(Date da, Date al) throws Throwable;
	
	public String leggiDelay() throws Throwable;
	
}
