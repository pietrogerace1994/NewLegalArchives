package eng.la.model.view;

import eng.la.model.RSocietaAffari;

public class RSocietaAffariView extends BaseView {

	private static final long serialVersionUID = 1L;
	private RSocietaAffari vo;

	public RSocietaAffari getVo() {
		return vo;
	}

	public void setVo(RSocietaAffari vo) {
		this.vo = vo;
	}
	
	private String idSocietaSocio;
	private String descrizioneSocietaSocio;
	private String percentualeSocio;

	public String getIdSocietaSocio() {
		return idSocietaSocio;
	}

	public void setIdSocietaSocio(String idSocietaSocio) {
		this.idSocietaSocio = idSocietaSocio;
	}

	public String getPercentualeSocio() {
		return percentualeSocio;
	}

	public void setPercentualeSocio(String percentualeSocio) {
		this.percentualeSocio = percentualeSocio;
	}

	public String getDescrizioneSocietaSocio() {
		return descrizioneSocietaSocio;
	}

	public void setDescrizioneSocietaSocio(String descrizioneSocietaSocio) {
		this.descrizioneSocietaSocio = descrizioneSocietaSocio;
	}
}
