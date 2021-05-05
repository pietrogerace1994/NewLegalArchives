package eng.la.model.view;

import eng.la.model.ResponsabileCivile;

public class ResponsabileCivileView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResponsabileCivile vo;
	private String nomeResponsabileCivile;
	private String tipoResponsabileCivile;
	private String tipoResponsabileCivileCode;
	private boolean encrypted;

	public ResponsabileCivile getVo() {
		return vo;
	}

	public void setVo(ResponsabileCivile vo) {
		this.vo = vo;
	}

	public String getNomeResponsabileCivile() {
		return nomeResponsabileCivile;
	}

	public void setNomeResponsabileCivile(String nomeResponsabileCivile) {
		this.nomeResponsabileCivile = nomeResponsabileCivile;
	}

	public String getTipoResponsabileCivile() {
		return tipoResponsabileCivile;
	}

	public void setTipoResponsabileCivile(String tipoResponsabileCivile) {
		this.tipoResponsabileCivile = tipoResponsabileCivile;
	}

	public String getTipoResponsabileCivileCode() {
		return tipoResponsabileCivileCode;
	}

	public void setTipoResponsabileCivileCode(String tipoResponsabileCivileCode) {
		this.tipoResponsabileCivileCode = tipoResponsabileCivileCode;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}


}
