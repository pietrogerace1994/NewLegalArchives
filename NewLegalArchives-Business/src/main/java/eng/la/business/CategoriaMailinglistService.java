package eng.la.business;

import java.util.Locale;

import eng.la.model.view.CategoriaMailinglistView;

import eng.la.util.ListaPaginata;

public interface CategoriaMailinglistService {

	//public List<CategoriaMailinglistView> leggi(Locale locale) throws Throwable;
	
	//public CategoriaMailinglistView leggi(long id) throws Throwable;
	
	public CategoriaMailinglistView leggi(String codice, Locale locale) throws Throwable;
	
	public ListaPaginata<CategoriaMailinglistView> cercaCategorie(int numElementiPerPagina, int numeroPagina,
			String ordinamento) throws Throwable;

	public CategoriaMailinglistView salvaCategoria(CategoriaMailinglistView categoriaView) throws Throwable;

	public void modificaCategoria(CategoriaMailinglistView categoriaView) throws Throwable;

	public void cancellaCategoria(long id) throws Throwable;

	public Long findColor(String colore) throws Throwable;

	public boolean haFigli(long id) throws Throwable;
	
	
	
}
