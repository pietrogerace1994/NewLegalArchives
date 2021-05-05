package eng.la.persistence;

import java.util.List;

import eng.la.model.Rubrica;

public interface RubricaDAO {
	public Rubrica leggiRubrica(long id) throws Throwable;

	public List<Rubrica> leggiRubrica() throws Throwable;

	public Rubrica salvaRubrica(Rubrica vo) throws Throwable;

	public void cancellaRubrica(long id) throws Throwable;

	public void modificaRubrica(Rubrica vo) throws Throwable;
	
	public List<Rubrica> cerca(String nominativo, String email, 
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione)
			throws Throwable;
	
	public Long conta(String nominativo, String email)
			throws Throwable;

}
