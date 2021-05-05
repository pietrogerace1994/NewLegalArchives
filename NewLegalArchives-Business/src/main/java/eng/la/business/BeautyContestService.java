/*
 * @author Benedetto Giordano
 */
package eng.la.business;

import java.util.List;

import org.hibernate.FetchMode;

import eng.la.model.view.BeautyContestView;
import eng.la.util.ListaPaginata;

/**
 * Interface BeautyContestService.
 */
public interface BeautyContestService {

	/**
	 * Ritorna la lista di IncaricoView.
	 *
	 * @return List<IncaricoView>
	 * @throws Throwable
	 */
	public List<BeautyContestView> leggi() throws Throwable;

	/**
	 * Ritorna un BeautyContestView con id = @param id.
	 *
	 * @param id
	 * @return BeautyContestView
	 * @throws Throwable
	 */
	public BeautyContestView leggi(long id) throws Throwable;
	
	/**
	 * Ritorna un BeautyContestView con id = @param id.
	 *
	 * @param id
	 * @return BeautyContestView
	 * @throws Throwable
	 */
	public BeautyContestView leggi(long id, FetchMode fetchMode) throws Throwable;
	/**
	 * Ritorna un BeautyContestView con id = @param id.
	 *
	 * @param id
	 * @return BeautyContestView
	 * @throws Throwable
	 */
	public BeautyContestView leggiTutti(long id) throws Throwable;

	/**
	 * Cerca tra tutti i beauty contest quelli che rispondono ai criteri di filtro
	 * impostati e ritorna una ListaPaginata
	 *
	 */
	public ListaPaginata<BeautyContestView> cerca(String titolo, String dal, String al, String statoBeautyContestCode, String centroDiCosto, 
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	/**
	 * Cerca tra tutti i beauty contest quelli che rispondono ai criteri di filtro
	 * impostati e ritorna la count degli elementi trovati
	 *
	 * @return Long
	 * @throws Throwable
	 */
	public Long conta(String titolo, String dal, String al, String statoBeautyContestCode, String centroDiCosto) throws Throwable;

	/**
	 * Crea un nuovo BeautyContest.
	 *
	 * @param BeautyContestView
	 * @return BeautyContestService
	 * @throws Throwable
	 */
	public BeautyContestView inserisci(BeautyContestView beautyContestView) throws Throwable;

	/**
	 * Modifica un BeautyContest.
	 *
	 * @param beautyContestView
	 * @throws Throwable
	 */
	public void modifica(BeautyContestView beautyContestView) throws Throwable;

	/**
	 * Cancella il beauty contest.
	 *
	 * @param beautyContestView
	 * @throws Throwable
	 */
	public void cancella(BeautyContestView beautyContestView) throws Throwable;
	
	public void salvaPermessiBeautyContest(Long bcId, String[] permessiScrittura, String[] permessiLettura)throws Throwable;

}
