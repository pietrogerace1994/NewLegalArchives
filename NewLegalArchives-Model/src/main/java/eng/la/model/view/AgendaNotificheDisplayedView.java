package eng.la.model.view;

import eng.la.model.NotificheAgendaDisplayed;


public class AgendaNotificheDisplayedView extends BaseView {
	private static final long serialVersionUID = 1L;
	private NotificheAgendaDisplayed vo;
	
	public AgendaNotificheDisplayedView() {
		super();
	}
	public NotificheAgendaDisplayed getVo() {
		return vo;
	}
	public void setVo(NotificheAgendaDisplayed vo) {
		this.vo = vo;
	}
	
	
}