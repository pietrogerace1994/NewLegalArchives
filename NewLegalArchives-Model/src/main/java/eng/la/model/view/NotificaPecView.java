package eng.la.model.view;

import eng.la.model.NotificaPec;

public class NotificaPecView extends BaseView {
	private static final long serialVersionUID = 1L;

	private String txtDestinatarioOp;
	private String txtOggettoOp;
	private String txtMittenteOp;
	private String txtUuidOp;
	private String noteOp;
	private String idUtentePec;
	private String idNotifica;

	private NotificaPec vo;

	public NotificaPec getVo() {
		return vo;
	}

	public void setVo(NotificaPec vo) {
		this.vo = vo;
	}

	public String getIdUtentePec() {
		return idUtentePec;
	}

	public void setIdUtentePec(String idUtentePec) {
		this.idUtentePec = idUtentePec;
	}

	public String getIdNotifica() {
		return idNotifica;
	}

	public void setIdNotifica(String idNotifica) {
		this.idNotifica = idNotifica;
	}

	public String getNoteOp() {
		return noteOp;
	}

	public void setNoteOp(String noteOp) {
		this.noteOp = noteOp;
	}

	public String getTxtDestinatarioOp() {
		return txtDestinatarioOp;
	}

	public void setTxtDestinatarioOp(String txtDestinatarioOp) {
		this.txtDestinatarioOp = txtDestinatarioOp;
	}

	public String getTxtOggettoOp() {
		return txtOggettoOp;
	}

	public void setTxtOggettoOp(String txtOggettoOp) {
		this.txtOggettoOp = txtOggettoOp;
	}

	public String getTxtMittenteOp() {
		return txtMittenteOp;
	}

	public void setTxtMittenteOp(String txtMittenteOp) {
		this.txtMittenteOp = txtMittenteOp;
	}

	public String getTxtUuidOp() {
		return txtUuidOp;
	}

	public void setTxtUuidOp(String txtUuidOp) {
		this.txtUuidOp = txtUuidOp;
	}

}
