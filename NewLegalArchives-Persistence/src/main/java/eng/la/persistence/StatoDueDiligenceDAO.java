package eng.la.persistence;

import java.util.List;

import eng.la.model.StatoDueDiligence;

public interface StatoDueDiligenceDAO {

	public List<StatoDueDiligence> leggi(String lingua) throws Throwable;

	public StatoDueDiligence readStatoDueDiligenceByFilter(String lingua, String codGruppoLingua) throws Throwable;
	  
}
