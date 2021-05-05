package eng.la.model.view;

import eng.la.model.Controparte;

public class ControparteView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controparte vo;

	public Controparte getVo() {
		return vo;
	}

	public void setVo(Controparte vo) {
		this.vo = vo;
	}

	private String nomeControparte;

	private String tipoControparte;
	private String tipoControparteCode;

	public String getNomeControparte() {
		return nomeControparte;
	}

	public void setNomeControparte(String nomeControparte) {
		this.nomeControparte = nomeControparte;
	}
 

	public String getTipoControparteCode() {
		return tipoControparteCode;
	}

	public void setTipoControparteCode(String tipoControparteCode) {
		this.tipoControparteCode = tipoControparteCode;
	}

	public String getTipoControparte() {
		return tipoControparte;
	}

	public void setTipoControparte(String tipoControparte) {
		this.tipoControparte = tipoControparte;
	}

}
