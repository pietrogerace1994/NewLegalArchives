package eng.la.model.view;

import eng.la.model.RicercaParteCorrelata;

public class RicercaParteCorrelataView extends BaseView {
	private static final long serialVersionUID = 1L;
	private RicercaParteCorrelata vo;
	
	public void setVo(RicercaParteCorrelata vo){
		this.vo = vo;
	}
	
	public RicercaParteCorrelata getVo() {
		return this.vo;
	}
}