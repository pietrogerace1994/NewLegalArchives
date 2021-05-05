package eng.la.persistence;

import java.util.List;

import eng.la.model.Procure;
import eng.la.model.aggregate.ProcureAggregate;
import eng.la.model.filter.ProcureFilter;

public interface ProcureDAO {

	public List<Procure> leggi() throws Throwable;

	public List<Procure> leggi(String lingua) throws Throwable;

	public Procure leggi(String codice, String lingua) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public void cancella(long idProcure) throws Throwable;

	public void modifica(Procure vo) throws Throwable;

	public List<Procure> cerca(String nome) throws Throwable;

	public Procure inserisci(Procure vo) throws Throwable;

	public List<Procure> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public Procure leggi(long id) throws Throwable;

	public ProcureAggregate searchPagedProcure(ProcureFilter filter) throws Throwable;
	
	public List<Procure> leggiDaFascicolo(long idFascicolo) throws Throwable;

}
