/*
 * @author Benedetto Giordano
 */
package eng.la.business;

import java.util.List;

import eng.la.model.view.BeautyContestReplyView;

/**
 * Interface BeautyContestReplyService.
 */
public interface BeautyContestReplyService {

	/**
	 * Ritorna un BeautyContestReplyView con id = @param id.
	 *
	 * @param id
	 * @return BeautyContestReplyView
	 * @throws Throwable
	 */
	public BeautyContestReplyView leggi(long id) throws Throwable;

	public List<BeautyContestReplyView> leggiElencoRisposteProfessionisti(long id) throws Throwable;
}
