package eng.la.model.view;

import eng.la.model.Nazione;
import eng.la.model.TipoProfessionista;

@SuppressWarnings("all")
public class TipoProfessionistaView extends BaseView {

	private static final long serialVersionUID = 1L;
	private TipoProfessionista vo;

	
	public TipoProfessionistaView() {
	}
	
	public TipoProfessionista getVo() {
		return vo;
	}

	public void setVo(TipoProfessionista vo) {
		this.vo = vo;
	}

	
}
