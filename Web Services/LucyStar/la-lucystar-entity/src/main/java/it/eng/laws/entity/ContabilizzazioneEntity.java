package it.eng.laws.entity;

import java.io.Serializable;

public class ContabilizzazioneEntity implements Serializable {

	private static final long serialVersionUID = -1631531811760496928L;
	
	private String numeroProtFiscale;
	private String dataRegistrazione;
	private String numeroFattura;
	private String dataFattura;
	private String codiceFornitore;
	private String sistemaMittente;
	private String sistemaDestinatario;

	public ContabilizzazioneEntity() {
		super();
	}

	public String getNumeroProtFiscale() {
		return numeroProtFiscale;
	}

	public void setNumeroProtFiscale(String numeroProtFiscale) {
		this.numeroProtFiscale = numeroProtFiscale;
	}

	public String getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getNumeroFattura() {
		return numeroFattura;
	}

	public void setNumeroFattura(String numeroFattura) {
		this.numeroFattura = numeroFattura;
	}

	public String getDataFattura() {
		return dataFattura;
	}

	public void setDataFattura(String dataFattura) {
		this.dataFattura = dataFattura;
	}

	public String getCodiceFornitore() {
		return codiceFornitore;
	}

	public void setCodiceFornitore(String codiceFornitore) {
		this.codiceFornitore = codiceFornitore;
	}

	public String getSistemaMittente() {
		return sistemaMittente;
	}

	public void setSistemaMittente(String sistemaMittente) {
		this.sistemaMittente = sistemaMittente;
	}

	public String getSistemaDestinatario() {
		return sistemaDestinatario;
	}

	public void setSistemaDestinatario(String sistemaDestinatario) {
		this.sistemaDestinatario = sistemaDestinatario;
	}

}
