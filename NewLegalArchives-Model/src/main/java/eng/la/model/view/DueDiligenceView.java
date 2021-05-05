package eng.la.model.view;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import eng.la.model.DueDiligence;
import eng.la.model.rest.DocumentoDueDiligenceRest;

public class DueDiligenceView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	private DueDiligence vo;
	
	private boolean insertMode;
	private boolean editMode;
	private boolean deleteMode;
	private String tabAttiva = "1";
	
	// Campi mapping Model Modify
	private Long dueDiligenceId;
	private Long dueDiligenceIdMod; // id due diligence in modifica
	private List<DueDiligenceView> dueDiligenceViewList; // lista filtro ricerca
	private String jsonArrayDueDiligenceMod; // lista filtro ricerca
	private String dataAperturaMod;
	private String dataChiusuraMod;
	private Long professionistaCodeMod;
	private Long statoDueDiligenceCodeMod;
	private MultipartFile fileAssegnazioneMod;
	private MultipartFile fileVerificaMod;
	private MultipartFile fileEsitoVerificaMod;
	private List<DocumentoDueDiligenceRest> listaDocumentiVerifica;
	private String[] documentiVerificaToDelete;
	
	// Campi mapping Model Insert
	private String dataApertura;
	private String dataChiusura;
	private Long idSocieta;
	private Long statoDueDiligenceCode;
	private Long professionistaCode;
	private MultipartFile fileAssegnazione;
	// Campi per Search
	private String dataAperturaDal;
	private String dataAperturaAl;
	private String dataChiusuraDal;
	private String dataChiusuraAl;
	
	// Caricamento combo per View
	private List<StatoDueDiligenceView> statoDueDiligenceList;
	private List<ProfessionistaEsternoView> professionistaEsternoList;
	
	
	// Parte di Ricerca
	private String stepMsg;
	private String resetWizard;
	private List<DueDiligenceView> dueDiligenceSearch;
	private String dueDiligenceSearchJson;

	public DueDiligence getVo() {
		return vo;
	}
	public void setVo(DueDiligence vo) {
		this.vo = vo;
	}
	public String getDataApertura() {
		return dataApertura;
	}
	public void setDataApertura(String dataApertura) {
		this.dataApertura = dataApertura;
	}
	public String getDataChiusura() {
		return dataChiusura;
	}
	public void setDataChiusura(String dataChiusura) {
		this.dataChiusura = dataChiusura;
	}
	public List<StatoDueDiligenceView> getStatoDueDiligenceList() {
		return statoDueDiligenceList;
	}
	public void setStatoDueDiligenceList(List<StatoDueDiligenceView> statoDueDiligenceList) {
		this.statoDueDiligenceList = statoDueDiligenceList;
	}
	public Long getStatoDueDiligenceCode() {
		return statoDueDiligenceCode;
	}
	public void setStatoDueDiligenceCode(Long statoDueDiligenceCode) {
		this.statoDueDiligenceCode = statoDueDiligenceCode;
	}
	public Long getProfessionistaCode() {
		return professionistaCode;
	}
	public void setProfessionistaCode(Long professionistaCode) {
		this.professionistaCode = professionistaCode;
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
	public Long getDueDiligenceId() {
		return dueDiligenceId;
	}
	public void setDueDiligenceId(Long dueDiligenceId) {
		this.dueDiligenceId = dueDiligenceId;
	}
	public Long getDueDiligenceIdMod() {
		return dueDiligenceIdMod;
	}
	public void setDueDiligenceIdMod(Long dueDiligenceIdMod) {
		this.dueDiligenceIdMod = dueDiligenceIdMod;
	}
	public List<DueDiligenceView> getDueDiligenceViewList() {
		return dueDiligenceViewList;
	}
	public void setDueDiligenceViewList(List<DueDiligenceView> dueDiligenceViewList) {
		this.dueDiligenceViewList = dueDiligenceViewList;
	}
	public String getJsonArrayDueDiligenceMod() {
		return jsonArrayDueDiligenceMod;
	}
	public void setJsonArrayDueDiligenceMod(String jsonArrayDueDiligenceMod) {
		this.jsonArrayDueDiligenceMod = jsonArrayDueDiligenceMod;
	}
	public String getDataAperturaMod() {
		return dataAperturaMod;
	}
	public void setDataAperturaMod(String dataAperturaMod) {
		this.dataAperturaMod = dataAperturaMod;
	}
	public String getDataChiusuraMod() {
		return dataChiusuraMod;
	}
	public void setDataChiusuraMod(String dataChiusuraMod) {
		this.dataChiusuraMod = dataChiusuraMod;
	}
	public Long getProfessionistaCodeMod() {
		return professionistaCodeMod;
	}
	public void setProfessionistaCodeMod(Long professionistaCodeMod) {
		this.professionistaCodeMod = professionistaCodeMod;
	}
	public Long getStatoDueDiligenceCodeMod() {
		return statoDueDiligenceCodeMod;
	}
	public void setStatoDueDiligenceCodeMod(Long statoDueDiligenceCodeMod) {
		this.statoDueDiligenceCodeMod = statoDueDiligenceCodeMod;
	}
	public MultipartFile getFileAssegnazione() {
		return fileAssegnazione;
	}
	public void setFileAssegnazione(MultipartFile fileAssegnazione) {
		this.fileAssegnazione = fileAssegnazione;
	}
	public MultipartFile getFileAssegnazioneMod() {
		return fileAssegnazioneMod;
	}
	public void setFileAssegnazioneMod(MultipartFile fileAssegnazioneMod) {
		this.fileAssegnazioneMod = fileAssegnazioneMod;
	}
	public MultipartFile getFileVerificaMod() {
		return fileVerificaMod;
	}
	public void setFileVerificaMod(MultipartFile fileVerificaMod) {
		this.fileVerificaMod = fileVerificaMod;
	}
	public MultipartFile getFileEsitoVerificaMod() {
		return fileEsitoVerificaMod;
	}
	public void setFileEsitoVerificaMod(MultipartFile fileEsitoVerificaMod) {
		this.fileEsitoVerificaMod = fileEsitoVerificaMod;
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
	public List<DueDiligenceView> getDueDiligenceSearch() {
		return dueDiligenceSearch;
	}
	public void setDueDiligenceSearch(List<DueDiligenceView> dueDiligenceSearch) {
		this.dueDiligenceSearch = dueDiligenceSearch;
	}
	public String getDueDiligenceSearchJson() {
		return dueDiligenceSearchJson;
	}
	public void setDueDiligenceSearchJson(String dueDiligenceSearchJson) {
		this.dueDiligenceSearchJson = dueDiligenceSearchJson;
	}
	public List<ProfessionistaEsternoView> getProfessionistaEsternoList() {
		return professionistaEsternoList;
	}
	public void setProfessionistaEsternoList(List<ProfessionistaEsternoView> professionistaEsternoList) {
		this.professionistaEsternoList = professionistaEsternoList;
	}
	public List<DocumentoDueDiligenceRest> getListaDocumentiVerifica() {
		return listaDocumentiVerifica;
	}
	public void setListaDocumentiVerifica(List<DocumentoDueDiligenceRest> listaDocumentiVerifica) {
		this.listaDocumentiVerifica = listaDocumentiVerifica;
	}
	public String[] getDocumentiVerificaToDelete() {
		return documentiVerificaToDelete;
	}
	public void setDocumentiVerificaToDelete(String[] documentiVerificaToDelete) {
		this.documentiVerificaToDelete = documentiVerificaToDelete;
	}
	public String getDataAperturaDal() {
		return dataAperturaDal;
	}
	public void setDataAperturaDal(String dataAperturaDal) {
		this.dataAperturaDal = dataAperturaDal;
	}
	public String getDataAperturaAl() {
		return dataAperturaAl;
	}
	public void setDataAperturaAl(String dataAperturaAl) {
		this.dataAperturaAl = dataAperturaAl;
	}
	public String getDataChiusuraDal() {
		return dataChiusuraDal;
	}
	public void setDataChiusuraDal(String dataChiusuraDal) {
		this.dataChiusuraDal = dataChiusuraDal;
	}
	public String getDataChiusuraAl() {
		return dataChiusuraAl;
	}
	public void setDataChiusuraAl(String dataChiusuraAl) {
		this.dataChiusuraAl = dataChiusuraAl;
	}
}