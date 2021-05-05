package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.Documento;
import eng.la.model.DocumentoRepertorioStandard;
import eng.la.model.PosizioneOrganizzativa;
import eng.la.model.PrimoLivelloAttribuzioni;
import eng.la.model.RepertorioStandard;
import eng.la.model.SecondoLivelloAttribuzioni;
import eng.la.model.aggregate.RepertorioStandardAggregate;
import eng.la.model.filter.RepertorioStandardFilter;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.view.RepertorioStandardView;
import eng.la.util.ListaPaginata;

public interface RepertorioStandardService {
	public List<RepertorioStandardView> leggi() throws Throwable;
	
	public List<String> findSocieta() throws Throwable;

	public RepertorioStandardView leggi(long id) throws Throwable;

	public ListaPaginata<RepertorioStandardView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public List<RepertorioStandardView> cerca(String nome) throws Throwable;

	public RepertorioStandardView inserisci(RepertorioStandardView progettoView) throws Throwable;
	
	public RepertorioStandard inserisciRest(RepertorioStandardView repertorioStandardView) throws Throwable;

	public void modifica(RepertorioStandardView progettoView) throws Throwable;

	public void cancella(long idRepertorioStandard) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public RepertorioStandardView leggi(String codice, Locale locale) throws Throwable;

	public List<RepertorioStandardView> leggi(Locale locale) throws Throwable;

	public RepertorioStandardAggregate searchPagedRepertorioStandard(RepertorioStandardFilter filter) throws Throwable;

	public List<CodiceDescrizioneBean> leggiListaPosizioneOrganizzativa(Locale locale);

	public List<CodiceDescrizioneBean> leggiListaPrimoLivelloAttribuzioni(Locale locale);

	public List<CodiceDescrizioneBean> leggiListaSecondoLivelloAttribuzioni(Locale locale);

	public PrimoLivelloAttribuzioni findPrimoLivelloByPk(long id) throws Throwable;
	
	public SecondoLivelloAttribuzioni findSecondoLivelloByPk(long id) throws Throwable;
	
	public PosizioneOrganizzativa findPosizioneOrganizzByPk(long id) throws Throwable;
	
	public DocumentoRepertorioStandard addDocument(RepertorioStandard repertorio, Documento documento) throws Throwable;

	void salvaAllegatiGenerici(RepertorioStandardView repertorioView) throws Throwable;


}
