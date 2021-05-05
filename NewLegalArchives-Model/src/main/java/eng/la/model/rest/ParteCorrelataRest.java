package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ParteCorrelataRest {
	private long id;
	private String denominazione;
	private String codFiscale;
	private String partitaIva;
	private Long nazioneId;
	private String nazioneCodGruppoLingua;
	private Long tipoCorrelazioneId;
	private String tipoCorrelazioneCodGruppoLingua;
	private String consiglieriSindaci;
	private String rapporto;
	private String familiare; 
	private String dataInserimento;
	private String nazione;
	private String tipoCorrelazione;
	private String stato;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Long getNazioneId() {
		return nazioneId;
	}

	public void setNazioneId(Long nazioneId) {
		this.nazioneId = nazioneId;
	}

	public Long getTipoCorrelazioneId() {
		return tipoCorrelazioneId;
	}

	public void setTipoCorrelazioneId(Long tipoCorrelazioneId) {
		this.tipoCorrelazioneId = tipoCorrelazioneId;
	}

	public String getConsiglieriSindaci() {
		return consiglieriSindaci;
	}

	public void setConsiglieriSindaci(String consiglieriSindaci) {
		this.consiglieriSindaci = consiglieriSindaci;
	}

	public String getRapporto() {
		return rapporto;
	}

	public void setRapporto(String rapporto) {
		this.rapporto = rapporto;
	}

	public String getFamiliare() {
		return familiare;
	}

	public void setFamiliare(String familiare) {
		this.familiare = familiare;
	}

	public String getNazioneCodGruppoLingua() {
		return nazioneCodGruppoLingua;
	}

	public void setNazioneCodGruppoLingua(String nazioneCodGruppoLingua) {
		this.nazioneCodGruppoLingua = nazioneCodGruppoLingua;
	}

	public String getTipoCorrelazioneCodGruppoLingua() {
		return tipoCorrelazioneCodGruppoLingua;
	}

	public void setTipoCorrelazioneCodGruppoLingua(String tipoCorrelazioneCodGruppoLingua) {
		this.tipoCorrelazioneCodGruppoLingua = tipoCorrelazioneCodGruppoLingua;
	}


	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getTipoCorrelazione() {
		return tipoCorrelazione;
	}

	public void setTipoCorrelazione(String tipoCorrelazione) {
		this.tipoCorrelazione = tipoCorrelazione;
	}

	public String getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	
}
