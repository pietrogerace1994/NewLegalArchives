package eng.la.model.view;

import eng.la.model.SoggettoIndagato;

public class SoggettoIndagatoView extends BaseView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SoggettoIndagato vo;

	private String nomeSoggettoIndagato;

	private String tipoSoggettoIndagato;
	private String tipoSoggettoIndagatoCode;
	private boolean encrypted;

	public SoggettoIndagato getVo() {
		return vo;
	}

	public void setVo(SoggettoIndagato vo) {
		this.vo = vo;
	}

	public String getNomeSoggettoIndagato() {
		return nomeSoggettoIndagato;
	}

	public void setNomeSoggettoIndagato(String nomeSoggettoIndagato) {
		this.nomeSoggettoIndagato = nomeSoggettoIndagato;
	}

	public String getTipoSoggettoIndagato() {
		return tipoSoggettoIndagato;
	}

	public void setTipoSoggettoIndagato(String tipoSoggettoIndagato) {
		this.tipoSoggettoIndagato = tipoSoggettoIndagato;
	}

	public String getTipoSoggettoIndagatoCode() {
		return tipoSoggettoIndagatoCode;
	}

	public void setTipoSoggettoIndagatoCode(String tipoSoggettoIndagatoCode) {
		this.tipoSoggettoIndagatoCode = tipoSoggettoIndagatoCode;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

	 

}
