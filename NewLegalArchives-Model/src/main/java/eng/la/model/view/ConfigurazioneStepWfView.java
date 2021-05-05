package eng.la.model.view;

import eng.la.model.ConfigurazioneStepWf;

public class ConfigurazioneStepWfView extends BaseView {
	private static final long serialVersionUID = 1L;
	private ConfigurazioneStepWf vo;
	
	// Configurazione Step Wf.
	public void setVo(ConfigurazioneStepWf vo){
		this.vo = vo;
	}
	
	public ConfigurazioneStepWf getVo() {
		return this.vo;
	}
}