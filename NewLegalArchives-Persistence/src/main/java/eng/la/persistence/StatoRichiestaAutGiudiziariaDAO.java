package eng.la.persistence;

import java.util.List;

import eng.la.model.StatoRichAutGiud;

public interface StatoRichiestaAutGiudiziariaDAO {

	public List<StatoRichAutGiud> leggi(String lingua) throws Throwable;

	public StatoRichAutGiud readStatoRichAutGiudByFilter(String lingua, String codGruppoLingua) throws Throwable;
	  
}
