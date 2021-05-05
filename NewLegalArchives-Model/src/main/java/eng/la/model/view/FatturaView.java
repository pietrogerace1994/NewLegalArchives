package eng.la.model.view;

import eng.la.model.Fattura;

public class FatturaView extends BaseView {
	private static final long serialVersionUID = 1L;
	private Fattura vo;
	
	public void setVo(Fattura vo){
		this.vo = vo;
	}
	
	public Fattura getVo() {
		return this.vo;
	}
	
	// qui aggiungere i campi aggiuntivi che non afferiscono all'entità
	
}