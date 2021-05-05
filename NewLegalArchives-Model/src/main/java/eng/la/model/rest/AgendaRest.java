package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AgendaRest {
	private String jsonArrayFascicoli;
	private String jsonArrayEventi;
	private String jsonArrayScadenze;
	private String jsonArrayListaTipoScadenza;
	private String jsonArrayNotificheScandezeDaInviareOggi;
	private String jsonArrayNotificheEventiDaInviareOggi;
	private String dataScandenzaStr;
	
	public String getJsonArrayFascicoli() {
		return jsonArrayFascicoli;
	}

	public void setJsonArrayFascicoli(String jsonArrayFascicoli) {
		this.jsonArrayFascicoli = jsonArrayFascicoli;
	}

	public String getJsonArrayEventi() {
		return jsonArrayEventi;
	}

	public void setJsonArrayEventi(String jsonArrayEventi) {
		this.jsonArrayEventi = jsonArrayEventi;
	}

	public String getJsonArrayScadenze() {
		return jsonArrayScadenze;
	}

	public void setJsonArrayScadenze(String jsonArrayScadenze) {
		this.jsonArrayScadenze = jsonArrayScadenze;
	}

	public String getJsonArrayListaTipoScadenza() {
		return jsonArrayListaTipoScadenza;
	}

	public void setJsonArrayListaTipoScadenza(String jsonArrayListaTipoScadenza) {
		this.jsonArrayListaTipoScadenza = jsonArrayListaTipoScadenza;
	}

	public String getJsonArrayNotificheScandezeDaInviareOggi() {
		return jsonArrayNotificheScandezeDaInviareOggi;
	}

	public void setJsonArrayNotificheScandezeDaInviareOggi(String jsonArrayNotificheScandezeDaInviareOggi) {
		this.jsonArrayNotificheScandezeDaInviareOggi = jsonArrayNotificheScandezeDaInviareOggi;
	}

	public String getJsonArrayNotificheEventiDaInviareOggi() {
		return jsonArrayNotificheEventiDaInviareOggi;
	}

	public void setJsonArrayNotificheEventiDaInviareOggi(String jsonArrayNotificheEventiDaInviareOggi) {
		this.jsonArrayNotificheEventiDaInviareOggi = jsonArrayNotificheEventiDaInviareOggi;
	}

	public String getDataScandenzaStr() {
		return dataScandenzaStr;
	}

	public void setDataScandenzaStr(String dataScandenzaStr) {
		this.dataScandenzaStr = dataScandenzaStr;
	}


	
}
