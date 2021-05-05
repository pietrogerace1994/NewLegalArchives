package eng.la.model.custom;

import java.io.Serializable;

import eng.la.model.Controparte;
import eng.la.model.Nazione;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.Proforma;

public class ProformaRow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2768676739350032679L;
	public Proforma proformas;	
	public Nazione paese;
	public Controparte controparte;
	public ProfessionistaEsterno legaleEsterno;
	
	public Proforma getProformas() {
		return proformas;
	}
	public void setProformas(Proforma proformas) {
		this.proformas = proformas;
	}
	public Nazione getPaese() {
		return paese;
	}
	public void setPaese(Nazione paese) {
		this.paese = paese;
	}
	public Controparte getControparte() {
		return controparte;
	}
	public void setControparte(Controparte controparte) {
		this.controparte = controparte;
	}
	public ProfessionistaEsterno getLegaleEsterno() {
		return legaleEsterno;
	}
	public void setLegaleEsterno(ProfessionistaEsterno legaleEsterno) {
		this.legaleEsterno = legaleEsterno;
	}
	
}
