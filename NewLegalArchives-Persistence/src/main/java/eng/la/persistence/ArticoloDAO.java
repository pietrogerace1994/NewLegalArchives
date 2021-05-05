package eng.la.persistence;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import eng.la.model.Articolo;

public interface ArticoloDAO {

	
	public Map<Long, List<Articolo>> findArticoli(String titolo,int anno,int elementiPerPagina, int paginaNumenro, String categoria, String sottocategoria) throws ParseException;
	public long countArticoli(String titolo,int anno, String categoria, String sottocategoria) throws ParseException;

}
