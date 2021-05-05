package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.Documento;
import eng.la.model.DocumentoProcure;
import eng.la.model.Procure;
import eng.la.model.aggregate.ProcureAggregate;
import eng.la.model.filter.ProcureFilter;
import eng.la.model.view.ProcureView;
import eng.la.util.ListaPaginata;

public interface ProcureService {
	public List<ProcureView> leggi() throws Throwable;

	public ProcureView leggi(long id) throws Throwable;

	public ListaPaginata<ProcureView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public List<ProcureView> cerca(String nome) throws Throwable;

	public ProcureView inserisci(ProcureView progettoView) throws Throwable;

	public void modifica(ProcureView progettoView) throws Throwable;

	public void cancella(long idProcure) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public ProcureView leggi(String codice, Locale locale) throws Throwable;

	public List<ProcureView> leggi(Locale locale) throws Throwable;

	public DocumentoProcure addDocument(Procure progetto, Documento documento) throws Throwable;

	public ProcureAggregate searchPagedProcure(ProcureFilter filter) throws Throwable;

	public void salvaPermessiProcure(Long fascicoloId, String[] permessiScritturaArray, String[] permessiLetturaArray) throws Throwable;
	
	public void associaProcureAFascicolo(Long procureId, Long fascicoloId) throws Throwable;
	
	public List<ProcureView> leggiDaFascicolo(long idFascicolo) throws Throwable;
	
	public void salvaAllegatiGenerici(ProcureView procureView) throws Throwable;
	
	public void salvaAllegatiGenericiConferite(ProcureView procureView) throws Throwable;
	
	
	public boolean checkProcuraSocieta(Long idProcura) throws Throwable;
	

}
