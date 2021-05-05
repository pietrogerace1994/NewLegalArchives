package eng.la.persistence;

import java.util.List;

import eng.la.model.RepertorioPoteri;
import eng.la.model.aggregate.RepertorioPoteriAggregate;
import eng.la.model.filter.RepertorioPoteriFilter;;

public interface RepertorioPoteriDAO {

	public List<RepertorioPoteri> leggi() throws Throwable;

	public List<RepertorioPoteri> leggi(String lingua) throws Throwable;

	public RepertorioPoteri leggi(String codice, String lingua) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public void cancella(long idRepertorioPoteri) throws Throwable;

	public void modifica(RepertorioPoteri vo) throws Throwable;

	public List<RepertorioPoteri> cerca(String nome) throws Throwable;

	public RepertorioPoteri inserisci(RepertorioPoteri vo) throws Throwable;

	public List<RepertorioPoteri> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public RepertorioPoteri leggi(long id) throws Throwable;

	public RepertorioPoteriAggregate searchPagedRepertorioPoteri(RepertorioPoteriFilter filter) throws Throwable;

	public List<String> getCodGruppoLinguaList(String lingua) throws Throwable;

}
