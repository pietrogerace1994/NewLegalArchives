package eng.la.model.view;

import eng.la.model.Configurazione;

public class ConfigurazioneView extends BaseView {
	private static final long serialVersionUID = 1L;
	private Configurazione vo;
	
	public void setVo(Configurazione vo){
		this.vo = vo;
	}
	
	public Configurazione getVo() {
		return this.vo;
	}
}