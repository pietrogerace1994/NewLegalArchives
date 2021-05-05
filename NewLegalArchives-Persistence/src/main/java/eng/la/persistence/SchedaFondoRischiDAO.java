package eng.la.persistence;

import java.util.Date;
import java.util.List;

import eng.la.model.SchedaFondoRischi;
import eng.la.model.StoricoSchedaFondoRischi;

public interface SchedaFondoRischiDAO {
	public List<SchedaFondoRischi> leggi() throws Throwable;

	public List<SchedaFondoRischi> cerca(String dal, String al, String statoSchedaFondoRischiCode, String tipologiaSchedaCode, String rischioSoccombenzaCode, String nomeFascicolo, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	public Long conta(String dal, String al,String statoSchedaFondoRischiCode, String tipologiaSchedaCode, String rischioSoccombenzaCode, String nomeFascicolo) throws Throwable;
	
	public SchedaFondoRischi leggi(long id) throws Throwable;
	
	public SchedaFondoRischi inserisci(SchedaFondoRischi vo) throws Throwable;

	public void modifica(SchedaFondoRischi vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public String getNextNumeroSchedaFondoRischi() throws Throwable;

	public List<SchedaFondoRischi> getIncaricoDaIdFascicolo(long idFascicolo) throws Throwable;

	public List<SchedaFondoRischi> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner) throws Throwable;
	
	public StoricoSchedaFondoRischi inserisci(StoricoSchedaFondoRischi vo) throws Throwable;
	
	public List<SchedaFondoRischi> cerca(Date dal, Date al) throws Throwable;
	
	public List<StoricoSchedaFondoRischi> leggiVersioniPrecedenti(long idScheda) throws Throwable;
}

