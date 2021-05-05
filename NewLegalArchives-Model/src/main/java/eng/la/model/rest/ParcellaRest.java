package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ParcellaRest {

	private long id;
	private String titolo;
	private String centrodicosto;
	private String voceconto;
	private String paese;
	private String controparte;
	private String legaleesterno;
	private String totale;
	private String valuta;
	private String datainserimento;
	private String dataautorizzazione;
	
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
	public String getCentrodicosto() {
		return centrodicosto;
	}
	public void setCentrodicosto(String centrodicosto) {
		this.centrodicosto = centrodicosto;
	}
	public String getVoceconto() {
		return voceconto;
	}
	public void setVoceconto(String voceconto) {
		this.voceconto = voceconto;
	}
	public String getPaese() {
		return paese;
	}
	public void setPaese(String paese) {
		this.paese = paese;
	}
	public String getControparte() {
		return controparte;
	}
	public void setControparte(String controparte) {
		this.controparte = controparte;
	}
	public String getLegaleesterno() {
		return legaleesterno;
	}
	public void setLegaleesterno(String legaleesterno) {
		this.legaleesterno = legaleesterno;
	}
	public String getTotale() {
		return totale;
	}
	public void setTotale(String totale) {
		this.totale = totale;
	}
	public String getValuta() {
		return valuta;
	}
	public void setValuta(String valuta) {
		this.valuta = valuta;
	}
	public String getDatainserimento() {
		return datainserimento;
	}
	public void setDatainserimento(String datainserimento) {
		this.datainserimento = datainserimento;
	}
	public String getDataautorizzazione() {
		return dataautorizzazione;
	}
	public void setDataautorizzazione(String dataautorizzazione) {
		this.dataautorizzazione = dataautorizzazione;
	}
	
	
	
}


