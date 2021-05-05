package eng.la.model.view;

import eng.la.model.Scadenza;

public class ScadenzaView extends BaseView {
	private static final long serialVersionUID = 1L;
	private Scadenza vo;
	
	public void setVo(Scadenza vo){
		this.vo = vo;
	}
	
	public Scadenza getVo() {
		return this.vo;
	}
	// qui aggiungere i campi aggiuntivi che non afferiscono all'entità
}