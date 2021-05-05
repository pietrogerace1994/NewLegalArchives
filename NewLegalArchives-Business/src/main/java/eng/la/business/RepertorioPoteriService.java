package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.aggregate.RepertorioPoteriAggregate;
import eng.la.model.filter.RepertorioPoteriFilter;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.view.RepertorioPoteriView;
import eng.la.util.ListaPaginata;

public interface RepertorioPoteriService {
	public List<RepertorioPoteriView> leggi() throws Throwable;

	public RepertorioPoteriView leggi(long id) throws Throwable;

	public ListaPaginata<RepertorioPoteriView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public List<RepertorioPoteriView> cerca(String nome) throws Throwable;

	public RepertorioPoteriView inserisci(RepertorioPoteriView progettoView) throws Throwable;

	public void modifica(RepertorioPoteriView progettoView) throws Throwable;

	public void cancella(long idRepertorioPoteri) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public RepertorioPoteriView leggi(String codice, Locale locale) throws Throwable;

	public List<RepertorioPoteriView> leggi(Locale locale) throws Throwable;

	public RepertorioPoteriAggregate searchPagedRepertorioPoteri(RepertorioPoteriFilter filter) throws Throwable;

	public List<CodiceDescrizioneBean> leggiListaCategorie(Locale locale);

	public List<CodiceDescrizioneBean> leggiListaSubCategorie(Locale locale);


}
