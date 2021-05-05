package eng.la.model.view;

import eng.la.model.Evento;

public class EventoView extends BaseView {
	private static final long serialVersionUID = 1L;
	private Evento vo;
	
	public void setVo(Evento vo){
		this.vo = vo;
	}
	
	public Evento getVo() {
		return this.vo;
	}
	// qui aggiungere i campi aggiuntivi che non afferiscono all'entità
	
}