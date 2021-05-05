package eng.la.model.view;

import java.util.Set;

import eng.la.model.Bonus;
import eng.la.model.SchedaValutazione;

public class SchedaValutazioneView extends BaseView {
	private static final long serialVersionUID = 1L;
	private SchedaValutazione vo;
	private String praticaControparte;
	private String autorita;
	private String valorecontroversia;
	private String valoreincarico;
	private String parcellan;
	private String dataemissione;
	private String avvocatostudio;
	private String dataScheda; 
	private String unitaLegaleOwner;
	private String optparcella;
	private String optlettera;
	private String optparametri;
	private String optlegge;
	private String note;
	private String valoreIncarico;
	private Set<Bonus> bonus;
	
	
	
	

	public String getValoreIncarico() {
		return valoreIncarico;
	}

	public void setValoreIncarico(String valoreIncarico) {
		this.valoreIncarico = valoreIncarico;
	}

	public Set<Bonus> getBonus() {
		return bonus;
	}

	public void setBonus(Set<Bonus> bonus) {
		this.bonus = bonus;
	}

	public void setVo(SchedaValutazione vo){
		this.vo = vo;
	}
	
	public SchedaValutazione getVo() {
		return this.vo;
	}

	public String getPraticaControparte() {
		return praticaControparte;
	}

	public void setPraticaControparte(String praticaControparte) {
		this.praticaControparte = praticaControparte;
	}

	public String getAutorita() {
		return autorita;
	}

	public void setAutorita(String autorita) {
		this.autorita = autorita;
	}

	public String getValorecontroversia() {
		return valorecontroversia;
	}

	public void setValorecontroversia(String valorecontroversia) {
		this.valorecontroversia = valorecontroversia;
	}

	public String getValoreincarico() {
		return valoreincarico;
	}

	public void setValoreincarico(String valoreincarico) {
		this.valoreincarico = valoreincarico;
	}

	public String getParcellan() {
		return parcellan;
	}

	public void setParcellan(String parcellan) {
		this.parcellan = parcellan;
	}

	public String getDataemissione() {
		return dataemissione;
	}

	public void setDataemissione(String dataemissione) {
		this.dataemissione = dataemissione;
	}

	public String getAvvocatostudio() {
		return avvocatostudio;
	}

	public void setAvvocatostudio(String avvocatostudio) {
		this.avvocatostudio = avvocatostudio;
	}

	public String getDataScheda() {
		return dataScheda;
	}

	public void setDataScheda(String dataScheda) {
		this.dataScheda = dataScheda;
	}

	public String getUnitaLegaleOwner() {
		return unitaLegaleOwner;
	}

	public void setUnitaLegaleOwner(String unitaLegaleOwner) {
		this.unitaLegaleOwner = unitaLegaleOwner;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOptparcella() {
		return optparcella;
	}

	public void setOptparcella(String optparcella) {
		this.optparcella = optparcella;
	}

	public String getOptlettera() {
		return optlettera;
	}

	public void setOptlettera(String optlettera) {
		this.optlettera = optlettera;
	}

	public String getOptparametri() {
		return optparametri;
	}

	public void setOptparametri(String optparametri) {
		this.optparametri = optparametri;
	}

	public String getOptlegge() {
		return optlegge;
	}

	public void setOptlegge(String optlegge) {
		this.optlegge = optlegge;
	}
	
	
	
	
	// qui aggiungere i campi aggiuntivi che non afferiscono all'entità
}