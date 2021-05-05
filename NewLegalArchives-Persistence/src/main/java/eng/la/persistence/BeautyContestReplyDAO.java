package eng.la.persistence;

import java.util.List;

import eng.la.model.BeautyContestReply;

public interface BeautyContestReplyDAO {

	public BeautyContestReply leggi(long id) throws Throwable;

	public List<BeautyContestReply> leggiElencoRisposte(long id) throws Throwable;
	
}

