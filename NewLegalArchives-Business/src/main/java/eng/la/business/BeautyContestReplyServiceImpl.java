package eng.la.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.BeautyContestReply;
import eng.la.model.view.BeautyContestReplyView;
import eng.la.persistence.BeautyContestReplyDAO;
import eng.la.persistence.DocumentoDAO;

@Service("beautyContestReplyService")
public class BeautyContestReplyServiceImpl extends BaseService<BeautyContestReply, BeautyContestReplyView> implements BeautyContestReplyService {

	
	@Autowired
	DocumentoDAO documentoDAO;
	
	@Autowired
	private BeautyContestReplyDAO beautyContestReplyDAO;


	@Override
	public BeautyContestReplyView leggi(long id) throws Throwable {
		BeautyContestReply beautyContestReply = beautyContestReplyDAO.leggi(id);
		return convertiVoInView(beautyContestReply);
	}
	
	@Override
	public List<BeautyContestReplyView> leggiElencoRisposteProfessionisti(long id) throws Throwable {
		List<BeautyContestReply> lista = beautyContestReplyDAO.leggiElencoRisposte(id);
		List<BeautyContestReplyView> listaView = convertiVoInView(lista);
		return listaView;
	}

	@Override
	protected Class<BeautyContestReply> leggiClassVO() {
		return BeautyContestReply.class;
	}

	@Override
	protected Class<BeautyContestReplyView> leggiClassView() {
		return BeautyContestReplyView.class;
	}
}
