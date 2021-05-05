package eng.la.model.view;

import eng.la.model.StatoProfessionista;

@SuppressWarnings("all")
public class StatoProfessionistaView extends BaseView {

	private static final long serialVersionUID = 1L;
	private StatoProfessionista vo;

	
	public StatoProfessionistaView() {
	}
	
	public StatoProfessionista getVo() {
		return vo;
	}

	public void setVo(StatoProfessionista vo) {
		this.vo = vo;
	}

	
}
