package eng.la.business.workflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.business.BaseService;
import eng.la.model.Configurazione;
import eng.la.model.view.ConfigurazioneView;
import eng.la.persistence.ConfigurazioneDAO;

@Service("configurazioneService")
public class ConfigurazioneServiceImpl extends BaseService implements ConfigurazioneService{
	
	@Autowired
	private ConfigurazioneDAO configurazioneDAO;
	
	// metodi extra
	@Override
	protected Class leggiClassVO() {
		return Configurazione.class;
	}

	@Override
	protected Class leggiClassView() {
		return Configurazione.class;
	}

	@Override
	public ConfigurazioneView leggiConfigurazione(String key) throws Throwable {
		String val=configurazioneDAO.leggiValore(key);
		Configurazione configurazione = new Configurazione();
		configurazione.setCdKey(key);
		configurazione.setCdValue(val);
		
		ConfigurazioneView view = new ConfigurazioneView();
		view.setVo(configurazione);
		return view;
	}

	@Override
	public void aggiornaMatricolaTopResponsabile(String matricolaAutorizzatore) throws Throwable {
		configurazioneDAO.aggiornaMatricolaTopResponsabile(matricolaAutorizzatore);
	}
	

}
