package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.DocumentoProgetto;
import eng.la.model.Progetto;
import eng.la.model.aggregate.ProgettoAggregate;
import eng.la.model.filter.ProgettoFilter;
import eng.la.model.view.ProgettoView;
import eng.la.util.ListaPaginata;

public interface ProgettoService {
	public List<ProgettoView> leggi() throws Throwable;

	public ProgettoView leggi(long id) throws Throwable;

	public ListaPaginata<ProgettoView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public List<ProgettoView> cerca(String nome) throws Throwable;

	public ProgettoView inserisci(ProgettoView progettoView) throws Throwable;

	public void modifica(ProgettoView progettoView) throws Throwable;

	public void cancella(long idProgetto) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public ProgettoView leggi(String codice, Locale locale) throws Throwable;

	public List<ProgettoView> leggi(Locale locale) throws Throwable;

	public DocumentoProgetto addDocument(Progetto progetto, long documentoId) throws Throwable;

	public ProgettoAggregate searchPagedProject(ProgettoFilter filter) throws Throwable;

	public void salvaPermessiProgetto(Long fascicoloId, String[] permessiScritturaArray, String[] permessiLetturaArray) throws Throwable;
	
	public ProgettoView leggiConPermessi(long id) throws Throwable;
	
}
