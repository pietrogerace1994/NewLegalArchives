package eng.la.model.view;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import eng.la.model.RichAutGiud;

public class AutoritaGiudiziariaView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	private RichAutGiud vo;
	
	private boolean insertMode;
	private boolean editMode;
	private boolean deleteMode;
	private String tabAttiva = "1";
	
	// Campi mapping Model Modify
	private Long autoritaGiudiziariaId;
	private Long autoritaGiudiziariaIdMod; // id autorita giudiziaria in modifica
	private List<AutoritaGiudiziariaView> autoritaGiudiziariaViewList; // lista filtro ricerca
	private String jsonArrayAutoritaGiudiziariaMod; // lista filtro ricerca
	private String autoritaGiudiziariaMod;
	private String dataInserimentoMod;
	private String dataRicezioneMod;
	private String oggettoMod;
	private Long tipologiaRichiestaCodeMod;
	private Long idSocietaMod;
	private Long statoRichiestaCodeMod;
	private MultipartFile fileRichiestaInfoRepartoLegaleMod;
	private MultipartFile fileRichiestaInfoUnitaInterneMod;
	private MultipartFile fileLetteraTrasmissioneMod;
	
	// Campi mapping Model Insert
	private String autoritaGiudiziaria;
	private String dataInserimento;
	private String dataRicezione;
	private String oggetto;
	private Long tipologiaRichiestaCode;
	private Long idSocieta;
	private Long statoRichiestaCode;
	private MultipartFile fileRichiestaInfoRepartoLegale;
	
	
	// Caricamento combo per View
	private List<SocietaView> societaList;
	private List<TipologiaRichiestaView> tipologiaRichiestaList;
	private List<StatoRichAutGiudView> statoRichAutGiudList;
	
	
	// Parte di Ricerca
	private String stepMsg;
	private String resetWizard;
	private String annoRichiesta;
	private List<AutoritaGiudiziariaView> autoritaGiudiziariaSearch;
	private String autoritaGiudiziariaSearchJson;
	
	private String fornitore;
	private String fornitoreMod;

	public RichAutGiud getVo() {
		return vo;
	}
	public void setVo(RichAutGiud vo) {
		this.vo = vo;
	}
	
	public String getAutoritaGiudiziaria() {
		return autoritaGiudiziaria;
	}
	public void setAutoritaGiudiziaria(String autoritaGiudiziaria) {
		this.autoritaGiudiziaria = autoritaGiudiziaria;
	}
	
	public String getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	
	public String getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(String dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public List<TipologiaRichiestaView> getTipologiaRichiestaList() {
		return tipologiaRichiestaList;
	}
	public void setTipologiaRichiestaList(List<TipologiaRichiestaView> tipologiaRichiestaList) {
		this.tipologiaRichiestaList = tipologiaRichiestaList;
	}
	public List<SocietaView> getSocietaList() {
		return societaList;
	}
	public void setSocietaList(List<SocietaView> societaList) {
		this.societaList = societaList;
	}
	public List<StatoRichAutGiudView> getStatoRichAutGiudList() {
		return statoRichAutGiudList;
	}
	public void setStatoRichAutGiudList(List<StatoRichAutGiudView> statoRichAutGiudList) {
		this.statoRichAutGiudList = statoRichAutGiudList;
	}
	public Long getTipologiaRichiestaCode() {
		return tipologiaRichiestaCode;
	}
	public void setTipologiaRichiestaCode(Long tipologiaRichiestaCode) {
		this.tipologiaRichiestaCode = tipologiaRichiestaCode;
	}
	public Long getStatoRichiestaCode() {
		return statoRichiestaCode;
	}
	public void setStatoRichiestaCode(Long statoRichiestaCode) {
		this.statoRichiestaCode = statoRichiestaCode;
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
	
	public String getTabAttiva() {
		return tabAttiva;
	}
	public void setTabAttiva(String tabAttiva) {
		this.tabAttiva = tabAttiva;
	}
	public Long getAutoritaGiudiziariaId() {
		return autoritaGiudiziariaId;
	}
	public void setAutoritaGiudiziariaId(Long autoritaGiudiziariaId) {
		this.autoritaGiudiziariaId = autoritaGiudiziariaId;
	}
	public Long getAutoritaGiudiziariaIdMod() {
		return autoritaGiudiziariaIdMod;
	}
	public void setAutoritaGiudiziariaIdMod(Long autoritaGiudiziariaIdMod) {
		this.autoritaGiudiziariaIdMod = autoritaGiudiziariaIdMod;
	}
	public List<AutoritaGiudiziariaView> getAutoritaGiudiziariaViewList() {
		return autoritaGiudiziariaViewList;
	}
	public void setAutoritaGiudiziariaViewList(List<AutoritaGiudiziariaView> autoritaGiudiziariaViewList) {
		this.autoritaGiudiziariaViewList = autoritaGiudiziariaViewList;
	}
	public String getJsonArrayAutoritaGiudiziariaMod() {
		return jsonArrayAutoritaGiudiziariaMod;
	}
	public void setJsonArrayAutoritaGiudiziariaMod(String jsonArrayAutoritaGiudiziariaMod) {
		this.jsonArrayAutoritaGiudiziariaMod = jsonArrayAutoritaGiudiziariaMod;
	}
	public String getAutoritaGiudiziariaMod() {
		return autoritaGiudiziariaMod;
	}
	public void setAutoritaGiudiziariaMod(String autoritaGiudiziariaMod) {
		this.autoritaGiudiziariaMod = autoritaGiudiziariaMod;
	}
	public String getDataInserimentoMod() {
		return dataInserimentoMod;
	}
	public void setDataInserimentoMod(String dataInserimentoMod) {
		this.dataInserimentoMod = dataInserimentoMod;
	}
	public String getDataRicezioneMod() {
		return dataRicezioneMod;
	}
	public void setDataRicezioneMod(String dataRicezioneMod) {
		this.dataRicezioneMod = dataRicezioneMod;
	}
	public String getOggettoMod() {
		return oggettoMod;
	}
	public void setOggettoMod(String oggettoMod) {
		this.oggettoMod = oggettoMod;
	}
	public Long getTipologiaRichiestaCodeMod() {
		return tipologiaRichiestaCodeMod;
	}
	public void setTipologiaRichiestaCodeMod(Long tipologiaRichiestaCodeMod) {
		this.tipologiaRichiestaCodeMod = tipologiaRichiestaCodeMod;
	}
	public Long getIdSocietaMod() {
		return idSocietaMod;
	}
	public void setIdSocietaMod(Long idSocietaMod) {
		this.idSocietaMod = idSocietaMod;
	}
	public Long getStatoRichiestaCodeMod() {
		return statoRichiestaCodeMod;
	}
	public void setStatoRichiestaCodeMod(Long statoRichiestaCodeMod) {
		this.statoRichiestaCodeMod = statoRichiestaCodeMod;
	}
	public MultipartFile getFileRichiestaInfoRepartoLegale() {
		return fileRichiestaInfoRepartoLegale;
	}
	public void setFileRichiestaInfoRepartoLegale(MultipartFile fileRichiestaInfoRepartoLegale) {
		this.fileRichiestaInfoRepartoLegale = fileRichiestaInfoRepartoLegale;
	}
	public MultipartFile getFileRichiestaInfoRepartoLegaleMod() {
		return fileRichiestaInfoRepartoLegaleMod;
	}
	public void setFileRichiestaInfoRepartoLegaleMod(MultipartFile fileRichiestaInfoRepartoLegaleMod) {
		this.fileRichiestaInfoRepartoLegaleMod = fileRichiestaInfoRepartoLegaleMod;
	}
	public MultipartFile getFileRichiestaInfoUnitaInterneMod() {
		return fileRichiestaInfoUnitaInterneMod;
	}
	public void setFileRichiestaInfoUnitaInterneMod(MultipartFile fileRichiestaInfoUnitaInterneMod) {
		this.fileRichiestaInfoUnitaInterneMod = fileRichiestaInfoUnitaInterneMod;
	}
	public MultipartFile getFileLetteraTrasmissioneMod() {
		return fileLetteraTrasmissioneMod;
	}
	public void setFileLetteraTrasmissioneMod(MultipartFile fileLetteraTrasmissioneMod) {
		this.fileLetteraTrasmissioneMod = fileLetteraTrasmissioneMod;
	}
	public Long getIdSocieta() {
		return idSocieta;
	}
	public void setIdSocieta(Long idSocieta) {
		this.idSocieta = idSocieta;
	}
	public String getStepMsg() {
		return stepMsg;
	}
	public void setStepMsg(String stepMsg) {
		this.stepMsg = stepMsg;
	}
	public String getResetWizard() {
		return resetWizard;
	}
	public void setResetWizard(String resetWizard) {
		this.resetWizard = resetWizard;
	}
	public String getAnnoRichiesta() {
		return annoRichiesta;
	}
	public void setAnnoRichiesta(String annoRichiesta) {
		this.annoRichiesta = annoRichiesta;
	}
	public List<AutoritaGiudiziariaView> getAutoritaGiudiziariaSearch() {
		return autoritaGiudiziariaSearch;
	}
	public void setAutoritaGiudiziariaSearch(List<AutoritaGiudiziariaView> autoritaGiudiziariaSearch) {
		this.autoritaGiudiziariaSearch = autoritaGiudiziariaSearch;
	}
	public String getAutoritaGiudiziariaSearchJson() {
		return autoritaGiudiziariaSearchJson;
	}
	public void setAutoritaGiudiziariaSearchJson(String autoritaGiudiziariaSearchJson) {
		this.autoritaGiudiziariaSearchJson = autoritaGiudiziariaSearchJson;
	}
	public String getFornitore() {
		return fornitore;
	}
	public void setFornitore(String fornitore) {
		this.fornitore = fornitore;
	}
	public String getFornitoreMod() {
		return fornitoreMod;
	}
	public void setFornitoreMod(String fornitoreMod) {
		this.fornitoreMod = fornitoreMod;
	}
	
}