package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BeautyContestRest {
	private long id;
	private String titolo;
	private String dataEmissione;
	private String dataChiusura; 
	private String unitaLegale;
	private String cdc;
	private String stato;
	private String azioni;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(String dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public String getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(String dataChiusura) {
		this.dataChiusura = dataChiusura;
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

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getAzioni() {
		return azioni;
	}

	public void setAzioni(String azioni) {
		this.azioni = azioni;
	}

}
