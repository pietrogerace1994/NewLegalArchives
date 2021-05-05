package eng.la.business.workflow;

import eng.la.model.view.ConfigurazioneView;

public interface ConfigurazioneService {
	
	public ConfigurazioneView leggiConfigurazione(String key) throws Throwable;

	public void aggiornaMatricolaTopResponsabile(String matricolaAutorizzatore) throws Throwable;

}
