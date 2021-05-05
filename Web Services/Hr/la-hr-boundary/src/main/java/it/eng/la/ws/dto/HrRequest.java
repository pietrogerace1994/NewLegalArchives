package it.eng.la.ws.dto;

import java.util.List;

public class HrRequest {

	private String dataaggiornamento;
	private List<Dato> dati;

	public String getDataaggiornamento() {
		return dataaggiornamento;
	}

	public void setDataaggiornamento(String dataaggiornamento) {
		this.dataaggiornamento = dataaggiornamento;
	}

	public List<Dato> getDati() {
		return dati;
	}

	public void setDati(List<Dato> dati) {
		this.dati = dati;
	}

}
