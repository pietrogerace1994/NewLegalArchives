package eng.la.model.view;

import eng.la.model.PersonaOffesa;

public class PersonaOffesaView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PersonaOffesa vo;

	private String nomePersonaOffesa;
	private String tipoPersonaOffesa;
	private String tipoPersonaOffesaCode;
	private boolean encrypted;

	public PersonaOffesa getVo() {
		return vo;
	}

	public void setVo(PersonaOffesa vo) {
		this.vo = vo;
	}

	public String getNomePersonaOffesa() {
		return nomePersonaOffesa;
	}

	public void setNomePersonaOffesa(String nomePersonaOffesa) {
		this.nomePersonaOffesa = nomePersonaOffesa;
	}

	public String getTipoPersonaOffesa() {
		return tipoPersonaOffesa;
	}

	public void setTipoPersonaOffesa(String tipoPersonaOffesa) {
		this.tipoPersonaOffesa = tipoPersonaOffesa;
	}

	public String getTipoPersonaOffesaCode() {
		return tipoPersonaOffesaCode;
	}

	public void setTipoPersonaOffesaCode(String tipoPersonaOffesaCode) {
		this.tipoPersonaOffesaCode = tipoPersonaOffesaCode;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}
 
}
