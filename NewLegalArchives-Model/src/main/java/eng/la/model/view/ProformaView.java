package eng.la.model.view;

import java.util.List;
import java.util.Set;

import eng.la.model.Proforma;

public class ProformaView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Proforma vo;

	public Proforma getVo() {
		return vo;
	}

	public void setVo(Proforma vo) {
		this.vo = vo;
	}

	private FascicoloView fascicoloRiferimento;
	private Long fascicoloId;

	private IncaricoView incaricoRiferimento;
	private Long incaricoId;

	private DocumentoView schedaValutazioneDoc;

	private SchedaValutazioneView schedaValutazione;
	private Boolean calcolato;
	private Long proformaId;
	private String nome;
	private String numero;
	private String legaleInterno;
	private String unitaLegale;
	private String legaleInternoDesc;
	private String unitaLegaleDesc;
	private String professionistaEsterno;
	private List<SocietaView> listaSocietaAddebitoAggiunte;
	private List<TipoProformaView> listaTipoProforma;
	private List<TipoValutaView> listaTipoValuta;
	private String societaAddebitoScelta;
	private String dataInserimento;
	private String dataProcessamento;
	private Boolean processato;
	private String utenteProcessamento;
	private Boolean ultimo;
	private Boolean disableCPA;
	private String isCPADisabled;
	private Long valutaId;
	private Double diritti = 0.0;
	private Double onorari = 0.0;
	private Double speseImponibili = 0.0;
	private Double cpa = 0.0;
	private Double totaleImponibile = 0.0;
	private Double speseNonImponibili = 0.0;
	private Double totale = 0.0;
	private String note;
	private String esitoVerificaProforma;
	private String annoEsercizioFinanziario;
	private String centroDiCosto;
	
	
	private Long idTipoProforma; 
	private List<CentroDiCostoView> listaCentroDiCosto;
	private String voceDiConto;
	private List<VoceDiContoView> listaVoceDiConto;
	private String dataRichiestaAutorizzazione;
	private String dataAutorizzazione;
	private String dataComposizione;
	private String dataInvioAmministratore;
	private String autorizzatore;
	private String stato;
	private List<DocumentoView> listaAllegatiGenerici;
	private Set<String> allegatiDaRimuovereUuid;
	private String timeout;
	private boolean presentaAvviaWF;
	
	private String tipoProformaMod;
	
	private Double totaleForce;
	
	private DocumentoView schedaValutazioneFirmataDoc;
	private byte[] schedaValutazioneFirmataTemp;
	private String schedaValutazioneFirmataDaRimuovere;
	
	

	public byte[] getSchedaValutazioneFirmataTemp() {
		return schedaValutazioneFirmataTemp;
	}

	public void setSchedaValutazioneFirmataTemp(byte[] schedaValutazioneFirmataTemp) {
		this.schedaValutazioneFirmataTemp = schedaValutazioneFirmataTemp;
	}

	public Double getTotaleForce() {
		return totaleForce;
	}

	public void setTotaleForce(Double totaleForce) {
		this.totaleForce = totaleForce;
	}

	public String getIsCPADisabled() {
		return isCPADisabled;
	}

	public void setIsCPADisabled(String isCPADisabled) {
		this.isCPADisabled = isCPADisabled;
	}

	public String getTipoProformaMod() {
		return tipoProformaMod;
	}

	public void setTipoProformaMod(String tipoProformaMod) {
		this.tipoProformaMod = tipoProformaMod;
	}

	public Boolean getDisableCPA() {
		return disableCPA;
	}

	public void setDisableCPA(Boolean disableCPA) {
		this.disableCPA = disableCPA;
	}

	public Long getIdTipoProforma() {
		return idTipoProforma;
	}

	public void setIdTipoProforma(Long idTipoProforma) {
		this.idTipoProforma = idTipoProforma;
	}

	public boolean isPresentaAvviaWF() {
		return presentaAvviaWF;
	}

	public void setPresentaAvviaWF(boolean presentaAvviaWF) {
		this.presentaAvviaWF = presentaAvviaWF;
	}

	public FascicoloView getFascicoloRiferimento() {
		return fascicoloRiferimento;
	}

	public void setFascicoloRiferimento(FascicoloView fascicoloRiferimento) {
		this.fascicoloRiferimento = fascicoloRiferimento;
	}

	public DocumentoView getSchedaValutazioneDoc() {
		return schedaValutazioneDoc;
	}

	public void setSchedaValutazioneDoc(DocumentoView schedaValutazioneDoc) {
		this.schedaValutazioneDoc = schedaValutazioneDoc;
	}

	public SchedaValutazioneView getSchedaValutazione() {
		return schedaValutazione;
	}

	public void setSchedaValutazione(SchedaValutazioneView schedaValutazione) {
		this.schedaValutazione = schedaValutazione;
	}

	public String getLegaleInternoDesc() {
		return legaleInternoDesc;
	}

	public void setLegaleInternoDesc(String legaleInternoDesc) {
		this.legaleInternoDesc = legaleInternoDesc;
	}

	public String getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(String dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public Boolean getProcessato() {
		return processato;
	}

	public void setProcessato(Boolean processato) {
		this.processato = processato;
	}

	public Boolean getCalcolato() {
		return calcolato;
	}

	public void setCalcolato(Boolean calcolato) {
		this.calcolato = calcolato;
	}

	public String getUtenteProcessamento() {
		return utenteProcessamento;
	}

	public void setUtenteProcessamento(String utenteProcessamento) {
		this.utenteProcessamento = utenteProcessamento;
	}

	public String getUnitaLegaleDesc() {
		return unitaLegaleDesc;
	}

	public void setUnitaLegaleDesc(String unitaLegaleDesc) {
		this.unitaLegaleDesc = unitaLegaleDesc;
	}

	public List<TipoValutaView> getListaTipoValuta() {
		return listaTipoValuta;
	}

	public void setListaTipoValuta(List<TipoValutaView> listaTipoValuta) {
		this.listaTipoValuta = listaTipoValuta;
	}

	public String getProfessionistaEsterno() {
		return professionistaEsterno;
	}

	public void setProfessionistaEsterno(String professionistaEsterno) {
		this.professionistaEsterno = professionistaEsterno;
	}

	public String getLegaleInterno() {
		return legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}

	public String getUnitaLegale() {
		return unitaLegale;
	}

	public void setUnitaLegale(String unitaLegale) {
		this.unitaLegale = unitaLegale;
	}

	public Long getFascicoloId() {
		return fascicoloId;
	}

	public void setFascicoloId(Long fascicoloId) {
		this.fascicoloId = fascicoloId;
	}

	public IncaricoView getIncaricoRiferimento() {
		return incaricoRiferimento;
	}

	public void setIncaricoRiferimento(IncaricoView incaricoRiferimento) {
		this.incaricoRiferimento = incaricoRiferimento;
	}

	public Long getIncaricoId() {
		return incaricoId;
	}

	public void setIncaricoId(Long incaricoId) {
		this.incaricoId = incaricoId;
	}

	public Long getProformaId() {
		return proformaId;
	}

	public void setProformaId(Long proformaId) {
		this.proformaId = proformaId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<SocietaView> getListaSocietaAddebitoAggiunte() {
		return listaSocietaAddebitoAggiunte;
	}

	public void setListaSocietaAddebitoAggiunte(List<SocietaView> listaSocietaAddebitoAggiunte) {
		this.listaSocietaAddebitoAggiunte = listaSocietaAddebitoAggiunte;
	}

	public String getSocietaAddebitoScelta() {
		return societaAddebitoScelta;
	}

	public void setSocietaAddebitoScelta(String societaAddebitoScelta) {
		this.societaAddebitoScelta = societaAddebitoScelta;
	}

	public String getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Boolean getUltimo() {
		return ultimo;
	}

	public void setUltimo(Boolean ultimo) {
		this.ultimo = ultimo;
	}

	public Long getValutaId() {
		return valutaId;
	}

	public void setValutaId(Long valutaId) {
		this.valutaId = valutaId;
	}

	public Double getDiritti() {
		return diritti;
	}

	public void setDiritti(Double diritti) {
		this.diritti = diritti;
	}

	public Double getOnorari() {
		return onorari;
	}

	public void setOnorari(Double onorari) {
		this.onorari = onorari;
	}

	public Double getSpeseImponibili() {
		return speseImponibili;
	}

	public void setSpeseImponibili(Double speseImponibili) {
		this.speseImponibili = speseImponibili;
	}

	public Double getCpa() {
		return cpa;
	}

	public void setCpa(Double cpa) {
		this.cpa = cpa;
	}

	public Double getTotaleImponibile() {
		return totaleImponibile;
	}

	public void setTotaleImponibile(Double totaleImponibile) {
		this.totaleImponibile = totaleImponibile;
	}

	public Double getSpeseNonImponibili() {
		return speseNonImponibili;
	}

	public void setSpeseNonImponibili(Double speseNonImponibili) {
		this.speseNonImponibili = speseNonImponibili;
	}

	public Double getTotale() {
		return totale;
	}

	public void setTotale(Double totale) {
		this.totale = totale;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEsitoVerificaProforma() {
		return esitoVerificaProforma;
	}

	public void setEsitoVerificaProforma(String esitoVerificaProforma) {
		this.esitoVerificaProforma = esitoVerificaProforma;
	}

	public String getAnnoEsercizioFinanziario() {
		return annoEsercizioFinanziario;
	}

	public void setAnnoEsercizioFinanziario(String annoEsercizioFinanziario) {
		this.annoEsercizioFinanziario = annoEsercizioFinanziario;
	}

	public List<CentroDiCostoView> getListaCentroDiCosto() {
		return listaCentroDiCosto;
	}

	public void setListaCentroDiCosto(List<CentroDiCostoView> listaCentroDiCosto) {
		this.listaCentroDiCosto = listaCentroDiCosto;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public String getVoceDiConto() {
		return voceDiConto;
	}

	public void setVoceDiConto(String voceDiConto) {
		this.voceDiConto = voceDiConto;
	}

	public List<VoceDiContoView> getListaVoceDiConto() {
		return listaVoceDiConto;
	}

	public void setListaVoceDiConto(List<VoceDiContoView> listaVoceDiConto) {
		this.listaVoceDiConto = listaVoceDiConto;
	}

	public String getDataRichiestaAutorizzazione() {
		return dataRichiestaAutorizzazione;
	}

	public void setDataRichiestaAutorizzazione(String dataRichiestaAutorizzazione) {
		this.dataRichiestaAutorizzazione = dataRichiestaAutorizzazione;
	}

	public String getDataAutorizzazione() {
		return dataAutorizzazione;
	}

	public void setDataAutorizzazione(String dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public String getDataComposizione() {
		return dataComposizione;
	}

	public void setDataComposizione(String dataComposizione) {
		this.dataComposizione = dataComposizione;
	}

	public String getDataInvioAmministratore() {
		return dataInvioAmministratore;
	}

	public void setDataInvioAmministratore(String dataInvioAmministratore) {
		this.dataInvioAmministratore = dataInvioAmministratore;
	}

	public String getAutorizzatore() {
		return autorizzatore;
	}

	public void setAutorizzatore(String autorizzatore) {
		this.autorizzatore = autorizzatore;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public List<DocumentoView> getListaAllegatiGenerici() {
		return listaAllegatiGenerici;
	}

	public void setListaAllegatiGenerici(List<DocumentoView> listaAllegatiGenerici) {
		this.listaAllegatiGenerici = listaAllegatiGenerici;
	}

	public Set<String> getAllegatiDaRimuovereUuid() {
		return allegatiDaRimuovereUuid;
	}

	public void setAllegatiDaRimuovereUuid(Set<String> allegatiDaRimuovereUuid) {
		this.allegatiDaRimuovereUuid = allegatiDaRimuovereUuid;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public List<TipoProformaView> getListaTipoProforma() {
		return listaTipoProforma;
	}

	public void setListaTipoProforma(List<TipoProformaView> listaTipoProforma) {
		this.listaTipoProforma = listaTipoProforma;
	}

	public DocumentoView getSchedaValutazioneFirmataDoc() {
		return schedaValutazioneFirmataDoc;
	}

	public void setSchedaValutazioneFirmataDoc(DocumentoView schedaValutazioneFirmataDoc) {
		this.schedaValutazioneFirmataDoc = schedaValutazioneFirmataDoc;
	}

	public String getSchedaValutazioneFirmataDaRimuovere() {
		return schedaValutazioneFirmataDaRimuovere;
	}

	public void setSchedaValutazioneFirmataDaRimuovere(String schedaValutazioneFirmataDaRimuovere) {
		this.schedaValutazioneFirmataDaRimuovere = schedaValutazioneFirmataDaRimuovere;
	}


}
