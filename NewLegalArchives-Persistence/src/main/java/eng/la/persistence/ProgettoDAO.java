package eng.la.persistence;

import java.util.List;

import eng.la.model.Progetto;
import eng.la.model.aggregate.ProgettoAggregate;
import eng.la.model.filter.ProgettoFilter;

public interface ProgettoDAO {

	public List<Progetto> leggi() throws Throwable;

	public List<Progetto> leggi(String lingua) throws Throwable;

	public Progetto leggi(String codice, String lingua) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public void cancella(long idProgetto) throws Throwable;

	public void modifica(Progetto vo) throws Throwable;

	public List<Progetto> cerca(String nome) throws Throwable;

	public Progetto inserisci(Progetto vo) throws Throwable;

	public List<Progetto> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public Progetto leggi(long id) throws Throwable;

	public ProgettoAggregate searchPagedProject(ProgettoFilter filter) throws Throwable;
	public Progetto leggiConPermessi(long id) throws Throwable;
}
