package eng.la.model.view;

import eng.la.model.GruppoUtente;

public class GruppoUtenteView extends BaseView {
	private static final long serialVersionUID = 1L;
	private GruppoUtente vo;
	
	// Gruppo Utente.
	public void setVo(GruppoUtente vo){
		this.vo = vo;
	}
	
	public GruppoUtente getVo() {
		return this.vo;
	}
}