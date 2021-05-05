package eng.la.model.view;

import eng.la.model.CategoriaContest;

@SuppressWarnings("all")
public class CategoriaContestView extends BaseView {

	private static final long serialVersionUID = 1L;
	private CategoriaContest vo;
	
	public CategoriaContestView() {
	}
	
	public CategoriaContest getVo() {
		return vo;
	}

	public void setVo(CategoriaContest vo) {
		this.vo = vo;
	}
}
