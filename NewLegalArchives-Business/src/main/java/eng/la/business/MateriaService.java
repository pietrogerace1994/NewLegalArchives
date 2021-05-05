package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.json.JSONArray;

import eng.la.model.view.MateriaView;
import eng.la.util.ListaPaginata;

public interface MateriaService {
	public List<MateriaView> leggi() throws Throwable;

	public MateriaView leggi(long id) throws Throwable;
	
	public MateriaView leggi(String codGruppoLingua, String codiceLingua) throws Throwable;
	
	public List<MateriaView> leggi(Locale locale) throws Throwable;

	public ListaPaginata<MateriaView> cerca(String nome, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable;
	
	public List<MateriaView> cerca(String nome ) throws Throwable;
	
	public Long conta(String nome ) throws Throwable;

	public MateriaView inserisci(MateriaView materiaView) throws Throwable;
	
	public void modifica(MateriaView materiaView) throws Throwable;

	public void cancella(MateriaView materiaView) throws Throwable;

	public List<MateriaView> leggiDaSettoreId(long id) throws Throwable;

	public MateriaView leggi(String codice, Locale locale, boolean tutti)throws Throwable;
	
	public JSONArray leggiAlberaturaMateria(long idSettoreGiuridico, Locale locale, boolean alberaturaAperta)throws Throwable;
	
	public JSONArray leggiAlberaturaMateriaTutte(Locale locale, boolean alberaturaAperta)throws Throwable;
	
	public JSONArray leggiAlberoMateriaOpEdit(long idSettoreGiuridico, Locale locale, boolean alberaturaAperta)
			throws Throwable;

	public JSONArray leggiAlberoMateriaOp(long idSettoreGiuridico, Locale locale, boolean alberaturaAperta) throws Throwable;

	public List<MateriaView> leggi(String codGruppoLingua) throws Throwable;

	JSONArray leggiAlberoMateriaOpVis(long idSettoreGiuridico, Locale locale, boolean alberaturaAperta)
			throws Throwable;
	
	public List<MateriaView> leggiDaBeautyContest(long id)throws Throwable;

}
