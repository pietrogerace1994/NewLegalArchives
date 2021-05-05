package eng.la.model.view;

import eng.la.model.ParteCivile;

public class ParteCivileView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ParteCivile vo;
	private String nomeParteCivile;
	private String tipoParteCivile;
	private String tipoParteCivileCode;
	private boolean encrypted;

	public ParteCivile getVo() {
		return vo;
	}

	public void setVo(ParteCivile vo) {
		this.vo = vo;
	}

	public String getNomeParteCivile() {
		return nomeParteCivile;
	}

	public void setNomeParteCivile(String nomeParteCivile) {
		this.nomeParteCivile = nomeParteCivile;
	}

	public String getTipoParteCivile() {
		return tipoParteCivile;
	}

	public void setTipoParteCivile(String tipoParteCivile) {
		this.tipoParteCivile = tipoParteCivile;
	}

	public String getTipoParteCivileCode() {
		return tipoParteCivileCode;
	}

	public void setTipoParteCivileCode(String tipoParteCivileCode) {
		this.tipoParteCivileCode = tipoParteCivileCode;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}
 
}
