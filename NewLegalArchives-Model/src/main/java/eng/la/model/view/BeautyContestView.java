package eng.la.model.view;

import java.util.List;
import java.util.Set;

import eng.la.model.BeautyContest;

public class BeautyContestView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BeautyContest vo;

	public BeautyContest getVo() {
		return vo;
	}

	public void setVo(BeautyContest vo) {
		this.vo = vo;
	}
	
	/* CAMPI FORM PRINCIPALE SCHEDA_FONDO_RISCHI */
	private Long beautyContestId;
	private String titolo;
	private String dataEmissione;
	private String dataChiusura;
	private String dataAutorizzazione;
	private String dataRichiestaAutorizzazione;
	private Long vincitoreId;
	private ProfessionistaEsternoView vincitoreSelezionato;
	private String idVincitoreSelezionato;
	private String statoBeautyContest;
	private String statoBeautyContestCode;
	private String descrizioneSow;
	private List<ProfessionistaEsternoView> listaProfessionistiEsterni;
	private String[] partecipantiAggiunti;
	private List<ProfessionistaEsternoView> listaPartecipantiAggiunti;
	private List<DocumentoView> listaAllegati;
	private Set<String> allegatiDaRimuovereUuid;
	private List<StatoBeautyContestView> listaStatoBeautyContest;
	private String unitaLegale;
	private String cdc;
	private String incaricoAccordoQuadro;
	private String[] materie;
	private List<MateriaView> listaMaterie;
	private List<String> listaMaterieAggiunteDesc;
	private String jsonAlberaturaMaterie; 
	private String nazioneCode;
	private String nazioneDesc;
	private String legaleInterno;
	private String isAutorizzato;
	private DocumentoView notaAggiudicazioneFirmataDoc;
	private String notaAggiudicazioneFirmataDaRimuovere;
	private List<BeautyContestReplyView> listaBeautyContestReplyView;
	private Boolean mailVincitore;
	
	
	
	public Long getBeautyContestId() {
		return beautyContestId;
	}

	public void setBeautyContestId(Long beautyContestId) {
		this.beautyContestId = beautyContestId;
	}


	public List<ProfessionistaEsternoView> getListaProfessionistiEsterni() {
		return listaProfessionistiEsterni;
	}
	
	public void setListaProfessionistiEsterni(List<ProfessionistaEsternoView> listaProfessionistiEsterni) {
		this.listaProfessionistiEsterni = listaProfessionistiEsterni;
	}
	

	public Set<String> getAllegatiDaRimuovereUuid() {
		return allegatiDaRimuovereUuid;
	}

	public void setAllegatiDaRimuovereUuid(Set<String> allegatiDaRimuovereUuid) {
		this.allegatiDaRimuovereUuid = allegatiDaRimuovereUuid;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(String dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public String getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(String dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public String getDataAutorizzazione() {
		return dataAutorizzazione;
	}

	public void setDataAutorizzazione(String dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public String getDataRichiestaAutorizzazione() {
		return dataRichiestaAutorizzazione;
	}

	public void setDataRichiestaAutorizzazione(String dataRichiestaAutorizzazione) {
		this.dataRichiestaAutorizzazione = dataRichiestaAutorizzazione;
	}

	public Long getVincitoreId() {
		return vincitoreId;
	}

	public void setVincitoreId(Long vincitoreId) {
		this.vincitoreId = vincitoreId;
	}

	public ProfessionistaEsternoView getVincitoreSelezionato() {
		return vincitoreSelezionato;
	}

	public void setVincitoreSelezionato(ProfessionistaEsternoView vincitoreSelezionato) {
		this.vincitoreSelezionato = vincitoreSelezionato;
	}

	public String getStatoBeautyContest() {
		return statoBeautyContest;
	}

	public void setStatoBeautyContest(String statoBeautyContest) {
		this.statoBeautyContest = statoBeautyContest;
	}

	public String getStatoBeautyContestCode() {
		return statoBeautyContestCode;
	}

	public void setStatoBeautyContestCode(String statoBeautyContestCode) {
		this.statoBeautyContestCode = statoBeautyContestCode;
	}

	public List<String> getListaMaterieAggiunteDesc() {
		return listaMaterieAggiunteDesc;
	}

	public void setListaMaterieAggiunteDesc(List<String> listaMaterieAggiunteDesc) {
		this.listaMaterieAggiunteDesc = listaMaterieAggiunteDesc;
	}

	public String getDescrizioneSow() {
		return descrizioneSow;
	}

	public void setDescrizioneSow(String descrizioneSow) {
		this.descrizioneSow = descrizioneSow;
	}

	public List<DocumentoView> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<DocumentoView> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	public List<StatoBeautyContestView> getListaStatoBeautyContest() {
		return listaStatoBeautyContest;
	}

	public void setListaStatoBeautyContest(List<StatoBeautyContestView> listaStatoBeautyContest) {
		this.listaStatoBeautyContest = listaStatoBeautyContest;
	}

	public List<MateriaView> getListaMaterie() {
		return listaMaterie;
	}

	public void setListaMaterie(List<MateriaView> listaMaterie) {
		this.listaMaterie = listaMaterie;
	}


	public String getUnitaLegale() {
		return unitaLegale;
	}

	public void setUnitaLegale(String unitaLegale) {
		this.unitaLegale = unitaLegale;
	}

	public String getCdc() {
		return cdc;
	}

	public void setCdc(String cdc) {
		this.cdc = cdc;
	}

	public String getIncaricoAccordoQuadro() {
		return incaricoAccordoQuadro;
	}

	public void setIncaricoAccordoQuadro(String incaricoAccordoQuadro) {
		this.incaricoAccordoQuadro = incaricoAccordoQuadro;
	}
	
	public String[] getMaterie() {
		return materie;
	}

	public void setMaterie(String[] materie) {
		this.materie = materie;
	}
	
	public String getJsonAlberaturaMaterie() {
		return jsonAlberaturaMaterie;
	}

	public void setJsonAlberaturaMaterie(String jsonAlberaturaMaterie) {
		this.jsonAlberaturaMaterie = jsonAlberaturaMaterie;
	}
	
	public String getNazioneCode() {
		return nazioneCode;
	}

	public void setNazioneCode(String nazioneCode) {
		this.nazioneCode = nazioneCode;
	}

	public String[] getPartecipantiAggiunti() {
		return partecipantiAggiunti;
	}

	public void setPartecipantiAggiunti(String[] partecipantiAggiunti) {
		this.partecipantiAggiunti = partecipantiAggiunti;
	}

	public String getLegaleInterno() {
		return legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}

	public String getIsAutorizzato() {
		return isAutorizzato;
	}

	public void setIsAutorizzato(String isAutorizzato) {
		this.isAutorizzato = isAutorizzato;
	}

	public List<ProfessionistaEsternoView> getListaPartecipantiAggiunti() {
		return listaPartecipantiAggiunti;
	}

	public void setListaPartecipantiAggiunti(List<ProfessionistaEsternoView> listaPartecipantiAggiunti) {
		this.listaPartecipantiAggiunti = listaPartecipantiAggiunti;
	}

	public String getIdVincitoreSelezionato() {
		return idVincitoreSelezionato;
	}

	public void setIdVincitoreSelezionato(String idVincitoreSelezionato) {
		this.idVincitoreSelezionato = idVincitoreSelezionato;
	}

	public DocumentoView getNotaAggiudicazioneFirmataDoc() {
		return notaAggiudicazioneFirmataDoc;
	}

	public void setNotaAggiudicazioneFirmataDoc(DocumentoView notaAutorizzazioneFirmataDoc) {
		this.notaAggiudicazioneFirmataDoc = notaAutorizzazioneFirmataDoc;
	}

	public String getNotaAggiudicazioneFirmataDaRimuovere() {
		return notaAggiudicazioneFirmataDaRimuovere;
	}

	public void setNotaAggiudicazioneFirmataDaRimuovere(String notaAutorizzazioneFirmataDaRimuovere) {
		this.notaAggiudicazioneFirmataDaRimuovere = notaAutorizzazioneFirmataDaRimuovere;
	}

	public String getNazioneDesc() {
		return nazioneDesc;
	}

	public void setNazioneDesc(String nazioneDesc) {
		this.nazioneDesc = nazioneDesc;
	}

	public List<BeautyContestReplyView> getListaBeautyContestReplyView() {
		return listaBeautyContestReplyView;
	}

	public void setListaBeautyContestReplyView(List<BeautyContestReplyView> listaBeautyContestReplyView) {
		this.listaBeautyContestReplyView = listaBeautyContestReplyView;
	}

	public Boolean getMailVincitore() {
		return mailVincitore;
	}

	public void setMailVincitore(Boolean mailVincitore) {
		this.mailVincitore = mailVincitore;
	}

}
