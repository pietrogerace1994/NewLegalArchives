package eng.la.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.UtentePec;
import eng.la.model.view.UtentePecView;
import eng.la.persistence.UtentePecDAO;

/**
 * <h1>Classe di business UtentePecServiceImpl</h1> Classe preposta alla
 * gestione delle operazione di lettura sulla base dati attraverso l'uso delle
 * classi DAO di pertinenza all'operazione.
 * <p>
 * 
 * @author
 * @version 1.0
 * @since 2016-07-01
 */
@Service("utentePecService")
public class UtentePecServiceImpl extends BaseService<UtentePec,UtentePecView> implements UtentePecService {

	private static final Logger logger = Logger.getLogger(UtentePecServiceImpl.class);

	@Autowired
	private UtentePecDAO utentePecDAO;

	public UtentePecDAO getUtentePecDAO() {
		return utentePecDAO;
	}

	@Override
	public List<UtentePecView> leggiPecMail(String userId) throws Throwable {
		List<UtentePec> lista = utentePecDAO.leggi(userId);

		if (lista == null)
			return new ArrayList<>();

		List<UtentePecView> listaRitorno = convertiVoInView(lista);
		for (UtentePecView utentePecView : listaRitorno) {
			if(utentePecView.getVo().getUUId()!=null){
				utentePecView.getVo().setUUId(utentePecView.getVo().getUUId().replaceAll("\\{", "").replaceAll("\\}", ""));
			}
		}
		return listaRitorno;
	}

	@Override
	public UtentePecView leggi(Long id) throws Throwable {
		UtentePec utentePec = utentePecDAO.leggi(id);
		return (UtentePecView) convertiVoInView(utentePec);
	}
	
	@Override
	public void riportaPec(Long id) throws Throwable {
		logger.debug("riportaPec id: " + id);
		utentePecDAO.riportaPec(id);
	}
	
	@Override
	public void annullaPec(Long id) throws Throwable {
		logger.debug("annullaPec id: " + id);
		utentePecDAO.annullaPec(id);
	}

	@Override
	public void trasformaPec(Long id) throws Throwable {
		logger.debug("trasformaPec id: " + id);
		utentePecDAO.trasformaPec(id);
	}

	@Override
	public void spostProtPec(Long id) throws Throwable {
		logger.debug("spostProtPec id: " + id);
		utentePecDAO.spostProtPec(id);
	}
	@Override
	public void inviaAltriUffPec(Long id, String utenteAltriUff, String emailAltriUff) throws Throwable {
		logger.debug("inviaAltriUffPec id: " + id+" utenteAltriUff: "+utenteAltriUff+" emailAltriUff: "+emailAltriUff);
		utentePecDAO.inviaAltriUffPec(id, utenteAltriUff, emailAltriUff);
	}
	
	@Override
	protected Class<UtentePec> leggiClassVO() {
		return UtentePec.class;
	}

	@Override
	protected Class<UtentePecView> leggiClassView() {
		return UtentePecView.class;
	}

}
