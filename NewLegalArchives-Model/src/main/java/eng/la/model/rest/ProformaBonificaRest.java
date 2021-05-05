package eng.la.model.rest;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProformaBonificaRest {
	private long idProforma;
	private BigDecimal onorari;
	private Integer isCpaDisabled; 
	private BigDecimal speseNonImponibili;
	
	
	public long getIdProforma() {
		return idProforma;
	}
	public void setIdProforma(long idProforma) {
		this.idProforma = idProforma;
	}
	public BigDecimal getOnorari() {
		return onorari;
	}
	public void setOnorari(BigDecimal onorari) {
		this.onorari = onorari;
	}
	public Integer getIsCpaDisabled() {
		return isCpaDisabled;
	}
	public void setIsCpaDisabled(Integer isCpaDisabled) {
		this.isCpaDisabled = isCpaDisabled;
	}
	public BigDecimal getSpeseNonImponibili() {
		return speseNonImponibili;
	}
	public void setSpeseNonImponibili(BigDecimal speseNonImponibili) {
		this.speseNonImponibili = speseNonImponibili;
	}
	
	
	
	
	
	
}
