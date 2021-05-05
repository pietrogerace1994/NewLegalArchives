package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CentroDiCostoRest {

	private long id;
	private String unitaLegale;
	private String cdc;
	private String settoreGiuridico;
	private String tipologiaFascicolo;
	private String societa;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getUnitaLegale() {
		return unitaLegale;
	}

	public void setUnitaLegale(String unitaLegale) {
		this.unitaLegale = unitaLegale;
	}

}
