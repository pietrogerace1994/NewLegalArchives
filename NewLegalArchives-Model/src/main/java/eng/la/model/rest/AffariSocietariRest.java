package eng.la.model.rest;

public class AffariSocietariRest {
	
	private String id;
	private String denominazione;
	private String sedeLegale;
	private String codiceFiscale;
	private String partitaIva;
	private String denominazioneControllante;
	private String percentualeControllante;
	private String percentualeTerzi;
	private String azioni;
	private boolean cancellato;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getSedeLegale() {
		return sedeLegale;
	}
	public void setSedeLegale(String sedeLegale) {
		this.sedeLegale = sedeLegale;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getDenominazioneControllante() {
		return denominazioneControllante;
	}
	public void setDenominazioneControllante(String denominazioneControllante) {
		this.denominazioneControllante = denominazioneControllante;
	}
	public String getPercentualeControllante() {
		return percentualeControllante;
	}
	public void setPercentualeControllante(String percentualeControllante) {
		this.percentualeControllante = percentualeControllante;
	}
	public String getPercentualeTerzi() {
		return percentualeTerzi;
	}
	public void setPercentualeTerzi(String percentualeTerzi) {
		this.percentualeTerzi = percentualeTerzi;
	}
	public String getAzioni() {
		return azioni;
	}
	public void setAzioni(String azioni) {
		this.azioni = azioni;
	}
	public boolean isCancellato() {
		return cancellato;
	}
	public void setCancellato(boolean cancellato) {
		this.cancellato = cancellato;
	}
}
