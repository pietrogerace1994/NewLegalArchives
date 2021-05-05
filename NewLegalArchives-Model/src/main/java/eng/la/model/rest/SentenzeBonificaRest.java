package eng.la.model.rest;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SentenzeBonificaRest {
	private String numeroProtocollo;
	private String idEsitoAtto;
	private BigDecimal pagamentoDovuto;
	private BigDecimal speseCarico; 
	private BigDecimal speseFavore;
	
	
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public String getIdEsitoAtto() {
		return idEsitoAtto;
	}
	public void setIdEsitoAtto(String idEsitoAtto) {
		this.idEsitoAtto = idEsitoAtto;
	}
	public BigDecimal getPagamentoDovuto() {
		return pagamentoDovuto;
	}
	public void setPagamentoDovuto(BigDecimal pagamentoDovuto) {
		this.pagamentoDovuto = pagamentoDovuto;
	}
	public BigDecimal getSpeseCarico() {
		return speseCarico;
	}
	public void setSpeseCarico(BigDecimal speseCarico) {
		this.speseCarico = speseCarico;
	}
	public BigDecimal getSpeseFavore() {
		return speseFavore;
	}
	public void setSpeseFavore(BigDecimal speseFavore) {
		this.speseFavore = speseFavore;
	}
	
	
	
	
	
	
	
	
}
