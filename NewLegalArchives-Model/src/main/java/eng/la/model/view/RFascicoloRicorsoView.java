package eng.la.model.view;

import eng.la.model.RFascicoloRicorso;

public class RFascicoloRicorsoView extends BaseView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RFascicoloRicorso vo;

	public RFascicoloRicorso getVo() {
		return vo;
	}

	public void setVo(RFascicoloRicorso vo) {
		this.vo = vo;
	}

	private String foro;
	private String note;
	private String numeroRegistroCausa;
	private String organoGiudicanteCode;
	private String ricorsoCode;

	public String getForo() {
		return foro;
	}

	public void setForo(String foro) {
		this.foro = foro;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumeroRegistroCausa() {
		return numeroRegistroCausa;
	}

	public void setNumeroRegistroCausa(String numeroRegistroCausa) {
		this.numeroRegistroCausa = numeroRegistroCausa;
	}

	public String getOrganoGiudicanteCode() {
		return organoGiudicanteCode;
	}

	public void setOrganoGiudicanteCode(String organoGiudicanteCode) {
		this.organoGiudicanteCode = organoGiudicanteCode;
	}

	public String getRicorsoCode() {
		return ricorsoCode;
	}

	public void setRicorsoCode(String ricorsoCode) {
		this.ricorsoCode = ricorsoCode;
	}

}
