package eng.la.model.view;

import eng.la.model.AuditLog;

public class AuditLogView extends BaseView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AuditLog vo;
	
	public AuditLog getVo() {
		return vo;
	}
	
	public void setVo(AuditLog vo) {
		this.vo = vo;
	}
}
