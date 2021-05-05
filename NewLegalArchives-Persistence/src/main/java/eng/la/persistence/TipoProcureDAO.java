package eng.la.persistence;

import java.util.List;

import org.springframework.aop.ThrowsAdvice;

import eng.la.model.TipoProcure;

public interface TipoProcureDAO {
	
	public List<TipoProcure> leggi(String lingua) throws Throwable;
	
	public TipoProcure leggi(long id) throws Throwable;

	public List<TipoProcure> leggibyCodice(String tipoProcureCode);

	public List<String> getCodGruppoLinguaList();

	public TipoProcure inserisci(TipoProcure vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public void modifica(TipoProcure vo) throws Throwable;

	public boolean controlla(String descIt) throws Throwable;
	
	public TipoProcure leggiNotDataCancellazione(long id) throws Throwable;
	
	public List<TipoProcure> leggiListNotDataCancellazione(String lingua) throws Throwable;

}
