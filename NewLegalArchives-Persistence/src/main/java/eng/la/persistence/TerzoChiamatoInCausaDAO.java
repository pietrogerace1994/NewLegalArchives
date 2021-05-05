package eng.la.persistence;

import java.util.List;

import eng.la.model.TerzoChiamatoCausa;

public interface TerzoChiamatoInCausaDAO {

	public void inserisciTerzoChiamatoInCausa(TerzoChiamatoCausa terzoChiamatoCausa) throws Throwable;

	public List<TerzoChiamatoCausa> leggiPerAutocomplete(String term, String lingua)throws Throwable;

	public TerzoChiamatoCausa leggi(long id) throws Throwable;

}
