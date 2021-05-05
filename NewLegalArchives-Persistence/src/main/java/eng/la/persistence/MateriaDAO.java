package eng.la.persistence;

import java.util.List;

import eng.la.model.Materia;
import eng.la.model.RSettoreGiuridicoMateria;

public interface MateriaDAO {
	public List<Materia> leggi() throws Throwable;
	
	public List<Materia> leggi(String lingua) throws Throwable;

	public Materia leggi(long id) throws Throwable;

	public List<Materia> cerca(String nome) throws Throwable;

	public List<Materia> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public Materia inserisci(Materia vo) throws Throwable;
	
	public void modifica(Materia vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public List<Materia> leggiDaSettoreId(long id) throws Throwable;

	public Materia leggi(String codice, String lingua, boolean tutte) throws Throwable;

	public List<Materia> leggiMaterieRootDaSettoreId(long id, String lingua) throws Throwable;
	
	public List<Materia> leggiMaterieRootTutte(String lingua) throws Throwable;

	public List<Materia> leggiMaterieFiglie(long idPadre, long idSettoreGiuridico, String lingua) throws Throwable;
	
	public List<Materia> leggiMaterieFiglie(long idPadre, String lingua) throws Throwable;

	public List<String> getCodGruppoLinguaList() throws Throwable;

	RSettoreGiuridicoMateria inserisciRSettoreGiuridicoMateria(RSettoreGiuridicoMateria vo) throws Throwable;

	public List<Materia> leggiPerCodice(String codice) throws Throwable;
	
	public List<Materia> leggiDaBCId(long id) throws Throwable;
}
