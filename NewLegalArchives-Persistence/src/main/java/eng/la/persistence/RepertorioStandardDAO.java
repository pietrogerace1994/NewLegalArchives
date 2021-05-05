package eng.la.persistence;

import java.util.List;

import eng.la.model.RepertorioStandard;
import eng.la.model.aggregate.RepertorioStandardAggregate;
import eng.la.model.filter.RepertorioStandardFilter;;

public interface RepertorioStandardDAO {

	public List<RepertorioStandard> leggi() throws Throwable;
	
	public List<String> findSocieta() throws Throwable;

	public List<RepertorioStandard> leggi(String lingua) throws Throwable;

	public RepertorioStandard leggi(String codice, String lingua) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public void cancella(long idRepertorioStandard) throws Throwable;

	public void modifica(RepertorioStandard vo) throws Throwable;

	public List<RepertorioStandard> cerca(String nome) throws Throwable;

	public RepertorioStandard inserisci(RepertorioStandard vo) throws Throwable;

	public List<RepertorioStandard> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public RepertorioStandard leggi(long id) throws Throwable;

	public RepertorioStandardAggregate searchPagedRepertorioStandard(RepertorioStandardFilter filter) throws Throwable;

	public List<String> getCodGruppoLinguaList(String lingua) throws Throwable;

}
