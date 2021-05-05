package eng.la.model.view;

import java.util.List;

import eng.la.model.CentroDiCosto;

public class CentroDiCostoView extends BaseView {

	private static final long serialVersionUID = 1L;

	private CentroDiCosto vo;
	
	private List<TipologiaFascicoloView> listaTipologiaFascicolo;
	private String tipologiaFascicoloCode;
	private List<SettoreGiuridicoView> listaSettoreGiuridico;
	private String settoreGiuridicoCode;
	private List<CentroDiCostoView> listaCentroDiCosto;
	
	private Long centroDiCostoId;
	private String unitaLegale;
	private String cdc;
	private String settoreGiuridico;
	private String tipologiaFascicolo;
	private String societa;
	
	public CentroDiCosto getVo() {
		return vo;
	}

	public void setVo(CentroDiCosto vo) {
		this.vo = vo;
	}

	public List<TipologiaFascicoloView> getListaTipologiaFascicolo() {
		return listaTipologiaFascicolo;
	}

	public void setListaTipologiaFascicolo(
			List<TipologiaFascicoloView> listaTipologiaFascicolo) {
		this.listaTipologiaFascicolo = listaTipologiaFascicolo;
	}

	public List<SettoreGiuridicoView> getListaSettoreGiuridico() {
		return listaSettoreGiuridico;
	}

	public void setListaSettoreGiuridico(
			List<SettoreGiuridicoView> listaSettoreGiuridico) {
		this.listaSettoreGiuridico = listaSettoreGiuridico;
	}

	public List<CentroDiCostoView> getListaCentroDiCosto() {
		return listaCentroDiCosto;
	}

	public void setListaCentroDiCosto(List<CentroDiCostoView> listaCentroDiCosto) {
		this.listaCentroDiCosto = listaCentroDiCosto;
	}

	public String getTipologiaFascicoloCode() {
		return tipologiaFascicoloCode;
	}

	public void setTipologiaFascicoloCode(String tipologiaFascicoloCode) {
		this.tipologiaFascicoloCode = tipologiaFascicoloCode;
	}

	public String getSettoreGiuridicoCode() {
		return settoreGiuridicoCode;
	}

	public void setSettoreGiuridicoCode(String settoreGiuridicoCode) {
		this.settoreGiuridicoCode = settoreGiuridicoCode;
	}

	public Long getCentroDiCostoId() {
		return centroDiCostoId;
	}

	public void setCentroDiCostoId(Long centroDiCostoId) {
		this.centroDiCostoId = centroDiCostoId;
	}

	public String getUnitaLegale() {
		return unitaLegale;
	}

	public void setUnitaLegale(String unitaLegale) {
		this.unitaLegale = unitaLegale;
	}

	public String getCdc() {
		return cdc;
	}

	public void setCdc(String cdc) {
		this.cdc = cdc;
	}

	public String getSettoreGiuridico() {
		return settoreGiuridico;
	}

	public void setSettoreGiuridico(String settoreGiuridico) {
		this.settoreGiuridico = settoreGiuridico;
	}

	public String getTipologiaFascicolo() {
		return tipologiaFascicolo;
	}

	public void setTipologiaFascicolo(String tipologiaFascicolo) {
		this.tipologiaFascicolo = tipologiaFascicolo;
	}

	public String getSocieta() {
		return societa;
	}

	public void setSocieta(String societa) {
		this.societa = societa;
	}

}
