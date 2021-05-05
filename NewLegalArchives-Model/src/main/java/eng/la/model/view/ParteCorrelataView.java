package eng.la.model.view;

import java.util.Date;
import java.util.List;

import eng.la.model.ParteCorrelata;

public class ParteCorrelataView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	private ParteCorrelata vo;
	
	private boolean insertMode;
	private boolean editMode;
	private boolean deleteMode;
	
	
	
	// Sezione per la presentation.
	private Long parteCorrelataId;
	private List<TipoCorrelazioneView> listaTipoCorrelazione;
	private List<ParteCorrelataView> listaParteCorrelata;
	private Long nazioneId;
	private String nazioneCode;
	private Long tipoCorrelazioneId;
	private String tipoCorrelazioneCode;
	private String denominazione;
	private String codFiscale;
	private String partitaIva;
	private String familiare;
	private String rapporto;
	private String consiglieriSindaci;
	private String tabAttiva= "1";
	private String stato;
	private Date dataInserimento;
	private Date dataCancellazione;
	
	
	private Long parteCorrelataIdMod;
	private List<TipoCorrelazioneView> listaTipoCorrelazioneMod;
	private List<ParteCorrelataView> listaParteCorrelataMod;
	private Long nazioneIdMod;
	private String nazioneCodeMod;
	private Long tipoCorrelazioneIdMod;
	private String tipoCorrelazioneCodeMod;
	private String denominazioneMod;
	private String codFiscaleMod;
	private String partitaIvaMod;
	private String familiareMod;
	private String rapportoMod;
	private String consiglieriSindaciMod;
	private String jsonArrayParteCorrelataMod;
	
	private Long parteCorrelataIdVis;
	private List<TipoCorrelazioneView> listaTipoCorrelazioneVis;
	private List<ParteCorrelataView> listaParteCorrelataVis;
	private Long nazioneIdVis;
	private String nazioneCodeVis;
	private Long tipoCorrelazioneIdVis;
	private String tipoCorrelazioneCodeVis;
	private String denominazioneVis;
	private String codFiscaleVis;
	private String partitaIvaVis;
	private String familiareVis;
	private String rapportoVis;
	private String consiglieriSindaciVis;
	
	// ricerca
	private String dataValidita;
	private List<ParteCorrelataView> listaRicercaRisultati;
	private String listaRicercaRisultatiJson;
	private String stepMsg;
	private long ricercaParteCorrelataId;
	private RicercaParteCorrelataView ricercaParteCorrelataView;
	private byte[] reportBlog;
	private String resetWizard;
	private ParteCorrelataView pcMatch;
	private String listaRicercaRisultatiMatchJson;
	private String esitoRic;
	
	// storico
	private String dataInizio;
	private String dataFine;
	private String listaStoricoRisultatiJson;
	
	public List<TipoCorrelazioneView> getListaTipoCorrelazione() {
		return listaTipoCorrelazione;
	}

	public void setListaTipoCorrelazione(List<TipoCorrelazioneView> listaTipoCorrelazione) {
		this.listaTipoCorrelazione = listaTipoCorrelazione;
	}

	public Long getNazioneId() {
		return nazioneId;
	}

	public void setNazioneId(Long nazioneId) {
		this.nazioneId = nazioneId;
	}

	public String getNazioneCode() {
		return nazioneCode;
	}

	public void setNazioneCode(String nazioneCode) {
		this.nazioneCode = nazioneCode;
	}

	public Long getParteCorrelataId() {
		return parteCorrelataId;
	}

	public void setParteCorrelataId(Long parteCorrelataId) {
		this.parteCorrelataId = parteCorrelataId;
	}

	
	public Long getTipoCorrelazioneId() {
		return tipoCorrelazioneId;
	}
	
	public void setTipoCorrelazioneId(Long tipoCorrelazioneId) {
		this.tipoCorrelazioneId = tipoCorrelazioneId;
	}
	
	public String getTipoCorrelazioneCode() {
		return tipoCorrelazioneCode;
	}

	public void setTipoCorrelazioneCode(String tipoCorrelazioneCode) {
		this.tipoCorrelazioneCode = tipoCorrelazioneCode;
	}
	
	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getFamiliare() {
		return familiare;
	}

	public void setFamiliare(String familiare) {
		this.familiare = familiare;
	}

	public String getRapporto() {
		return rapporto;
	}

	public void setRapporto(String rapporto) {
		this.rapporto = rapporto;
	}

	public String getConsiglieriSindaci() {
		return consiglieriSindaci;
	}

	public void setConsiglieriSindaci(String consiglieriSindaci) {
		this.consiglieriSindaci = consiglieriSindaci;
	}

	// Parte Correlata.
	public void setVo(ParteCorrelata vo){
		this.vo = vo;
	}
	
	public ParteCorrelata getVo() {
		return this.vo;
	}

	public String getTabAttiva() {
		return tabAttiva;
	}

	public void setTabAttiva(String tabAttiva) {
		this.tabAttiva = tabAttiva;
	}

	public List<ParteCorrelataView> getListaParteCorrelata() {
		return listaParteCorrelata;
	}

	public void setListaParteCorrelata(List<ParteCorrelataView> listaParteCorrelata) {
		this.listaParteCorrelata = listaParteCorrelata;
	}

	public Long getParteCorrelataIdMod() {
		return parteCorrelataIdMod;
	}

	public void setParteCorrelataIdMod(Long parteCorrelataIdMod) {
		this.parteCorrelataIdMod = parteCorrelataIdMod;
	}

	public List<TipoCorrelazioneView> getListaTipoCorrelazioneMod() {
		return listaTipoCorrelazioneMod;
	}

	public void setListaTipoCorrelazioneMod(List<TipoCorrelazioneView> listaTipoCorrelazioneMod) {
		this.listaTipoCorrelazioneMod = listaTipoCorrelazioneMod;
	}

	public List<ParteCorrelataView> getListaParteCorrelataMod() {
		return listaParteCorrelataMod;
	}

	public void setListaParteCorrelataMod(List<ParteCorrelataView> listaParteCorrelataMod) {
		this.listaParteCorrelataMod = listaParteCorrelataMod;
	}

	public Long getNazioneIdMod() {
		return nazioneIdMod;
	}

	public void setNazioneIdMod(Long nazioneIdMod) {
		this.nazioneIdMod = nazioneIdMod;
	}

	public String getNazioneCodeMod() {
		return nazioneCodeMod;
	}

	public void setNazioneCodeMod(String nazioneCodeMod) {
		this.nazioneCodeMod = nazioneCodeMod;
	}

	public Long getTipoCorrelazioneIdMod() {
		return tipoCorrelazioneIdMod;
	}

	public void setTipoCorrelazioneIdMod(Long tipoCorrelazioneIdMod) {
		this.tipoCorrelazioneIdMod = tipoCorrelazioneIdMod;
	}

	public String getTipoCorrelazioneCodeMod() {
		return tipoCorrelazioneCodeMod;
	}

	public void setTipoCorrelazioneCodeMod(String tipoCorrelazioneCodeMod) {
		this.tipoCorrelazioneCodeMod = tipoCorrelazioneCodeMod;
	}

	public String getDenominazioneMod() {
		return denominazioneMod;
	}

	public void setDenominazioneMod(String denominazioneMod) {
		this.denominazioneMod = denominazioneMod;
	}

	public String getCodFiscaleMod() {
		return codFiscaleMod;
	}

	public void setCodFiscaleMod(String codFiscaleMod) {
		this.codFiscaleMod = codFiscaleMod;
	}
	
	public String getPartitaIvaMod() {
		return partitaIvaMod;
	}

	public void setPartitaIvaMod(String partitaIvaMod) {
		this.partitaIvaMod = partitaIvaMod;
	}

	public String getFamiliareMod() {
		return familiareMod;
	}

	public void setFamiliareMod(String familiareMod) {
		this.familiareMod = familiareMod;
	}

	public String getRapportoMod() {
		return rapportoMod;
	}

	public void setRapportoMod(String rapportoMod) {
		this.rapportoMod = rapportoMod;
	}

	public String getConsiglieriSindaciMod() {
		return consiglieriSindaciMod;
	}

	public void setConsiglieriSindaciMod(String consiglieriSindaciMod) {
		this.consiglieriSindaciMod = consiglieriSindaciMod;
	}

	public Long getParteCorrelataIdVis() {
		return parteCorrelataIdVis;
	}

	public void setParteCorrelataIdVis(Long parteCorrelataIdVis) {
		this.parteCorrelataIdVis = parteCorrelataIdVis;
	}

	public List<TipoCorrelazioneView> getListaTipoCorrelazioneVis() {
		return listaTipoCorrelazioneVis;
	}

	public void setListaTipoCorrelazioneVis(List<TipoCorrelazioneView> listaTipoCorrelazioneVis) {
		this.listaTipoCorrelazioneVis = listaTipoCorrelazioneVis;
	}

	public List<ParteCorrelataView> getListaParteCorrelataVis() {
		return listaParteCorrelataVis;
	}

	public void setListaParteCorrelataVis(List<ParteCorrelataView> listaParteCorrelataVis) {
		this.listaParteCorrelataVis = listaParteCorrelataVis;
	}

	public Long getNazioneIdVis() {
		return nazioneIdVis;
	}

	public void setNazioneIdVis(Long nazioneIdVis) {
		this.nazioneIdVis = nazioneIdVis;
	}

	public String getNazioneCodeVis() {
		return nazioneCodeVis;
	}

	public void setNazioneCodeVis(String nazioneCodeVis) {
		this.nazioneCodeVis = nazioneCodeVis;
	}

	public Long getTipoCorrelazioneIdVis() {
		return tipoCorrelazioneIdVis;
	}

	public void setTipoCorrelazioneIdVis(Long tipoCorrelazioneIdVis) {
		this.tipoCorrelazioneIdVis = tipoCorrelazioneIdVis;
	}

	public String getTipoCorrelazioneCodeVis() {
		return tipoCorrelazioneCodeVis;
	}

	public void setTipoCorrelazioneCodeVis(String tipoCorrelazioneCodeVis) {
		this.tipoCorrelazioneCodeVis = tipoCorrelazioneCodeVis;
	}

	public String getDenominazioneVis() {
		return denominazioneVis;
	}

	public void setDenominazioneVis(String denominazioneVis) {
		this.denominazioneVis = denominazioneVis;
	}

	public String getCodFiscaleVis() {
		return codFiscaleVis;
	}

	public void setCodFiscaleVis(String codFiscaleVis) {
		this.codFiscaleVis = codFiscaleVis;
	}
	
	public String getPartitaIvaVis() {
		return partitaIvaVis;
	}

	public void setPartitaIvaVis(String partitaIvaVis) {
		this.partitaIvaVis = partitaIvaVis;
	}

	public String getFamiliareVis() {
		return familiareVis;
	}

	public void setFamiliareVis(String familiareVis) {
		this.familiareVis = familiareVis;
	}

	public String getRapportoVis() {
		return rapportoVis;
	}

	public void setRapportoVis(String rapportoVis) {
		this.rapportoVis = rapportoVis;
	}

	public String getConsiglieriSindaciVis() {
		return consiglieriSindaciVis;
	}

	public void setConsiglieriSindaciVis(String consiglieriSindaciVis) {
		this.consiglieriSindaciVis = consiglieriSindaciVis;
	}

	public boolean isInsertMode() {
		return insertMode;
	}

	public void setInsertMode(boolean insertMode) {
		this.insertMode = insertMode;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	

	public List<ParteCorrelataView> getListaRicercaRisultati() {
		return listaRicercaRisultati;
	}

	public void setListaRicercaRisultati(List<ParteCorrelataView> listaRicercaRisultati) {
		this.listaRicercaRisultati = listaRicercaRisultati;
	}

	public String getDataValidita() {
		return dataValidita;
	}

	public void setDataValidita(String dataValidita) {
		this.dataValidita = dataValidita;
	}

	public String getListaRicercaRisultatiJson() {
		return listaRicercaRisultatiJson;
	}

	public void setListaRicercaRisultatiJson(String listaRicercaRisultatiJson) {
		this.listaRicercaRisultatiJson = listaRicercaRisultatiJson;
	}

	public String getStepMsg() {
		return stepMsg;
	}

	public void setStepMsg(String stepMsg) {
		this.stepMsg = stepMsg;
	}

	public long getRicercaParteCorrelataId() {
		return ricercaParteCorrelataId;
	}

	public void setRicercaParteCorrelataId(long ricercaParteCorrelataId) {
		this.ricercaParteCorrelataId = ricercaParteCorrelataId;
	}

	public RicercaParteCorrelataView getRicercaParteCorrelataView() {
		return ricercaParteCorrelataView;
	}

	public void setRicercaParteCorrelataView(RicercaParteCorrelataView ricercaParteCorrelataView) {
		this.ricercaParteCorrelataView = ricercaParteCorrelataView;
	}

	public byte[] getReportBlog() {
		return reportBlog;
	}

	public void setReportBlog(byte[] reportBlog) {
		this.reportBlog = reportBlog;
	}

	public String getResetWizard() {
		return resetWizard;
	}

	public void setResetWizard(String resetWizard) {
		this.resetWizard = resetWizard;
	}

	public String getListaStoricoRisultatiJson() {
		return listaStoricoRisultatiJson;
	}

	public void setListaStoricoRisultatiJson(String listaStoricoRisultatiJson) {
		this.listaStoricoRisultatiJson = listaStoricoRisultatiJson;
	}

	public String getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}

	public String getDataFine() {
		return dataFine;
	}

	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}

	public ParteCorrelataView getPcMatch() {
		return pcMatch;
	}

	public void setPcMatch(ParteCorrelataView pcMatch) {
		this.pcMatch = pcMatch;
	}

	public String getListaRicercaRisultatiMatchJson() {
		return listaRicercaRisultatiMatchJson;
	}

	public void setListaRicercaRisultatiMatchJson(String listaRicercaRisultatiMatchJson) {
		this.listaRicercaRisultatiMatchJson = listaRicercaRisultatiMatchJson;
	}

	public String getEsitoRic() {
		return esitoRic;
	}

	public void setEsitoRic(String esitoRic) {
		this.esitoRic = esitoRic;
	}

	public String getJsonArrayParteCorrelataMod() {
		return jsonArrayParteCorrelataMod;
	}

	public void setJsonArrayParteCorrelataMod(String jsonArrayParteCorrelataMod) {
		this.jsonArrayParteCorrelataMod = jsonArrayParteCorrelataMod;
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

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}



	
}