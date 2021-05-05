package eng.la.model.view;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import eng.la.model.Articolo;

public class EmailView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Articolo vo;

	public Articolo getVo() {
		return vo;
	}

	public void setVo(Articolo vo) {
		this.vo = vo;
	}

	private Long emailId;
	private String oggetto;
	private String contenuto;
	private String contenutoBreve;
	private String dataEmail;
	private String dataCreazione;
	private String categoriaCode;
	private String sottoCategoriaCode;
	private List<CategoriaMailinglistView> categorie;
	private List<CategoriaMailinglistView> sottocategorie;
	private String[] arrRubricaInvioEmail;
	private List<MultipartFile> files;
	private String[] documentiDaEliminare;
	private List<FileView> filesPresenti;
	

	
	
	public List<FileView> getFilesPresenti() {
		return filesPresenti;
	}

	public void setFilesPresenti(List<FileView> filesPresenti) {
		this.filesPresenti = filesPresenti;
	}

	public String[] getDocumentiDaEliminare() {
		return documentiDaEliminare;
	}

	public void setDocumentiDaEliminare(String[] documentiDaEliminare) {
		this.documentiDaEliminare = documentiDaEliminare;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public Long getEmailId() {
		return emailId;
	}

	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public String getDataEmail() {
		return dataEmail;
	}

	public void setDataEmail(String dataEmail) {
		this.dataEmail = dataEmail;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getCategoriaCode() {
		return categoriaCode;
	}

	public void setCategoriaCode(String categoriaCode) {
		this.categoriaCode = categoriaCode;
	}

	public List<CategoriaMailinglistView> getCategorie() {
		return categorie;
	}

	public void setCategorie(List<CategoriaMailinglistView> categorie) {
		this.categorie = categorie;
	}

	public String getSottoCategoriaCode() {
		return sottoCategoriaCode;
	}

	public void setSottoCategoriaCode(String sottoCategoriaCode) {
		this.sottoCategoriaCode = sottoCategoriaCode;
	}

	public List<CategoriaMailinglistView> getSottocategorie() {
		return sottocategorie;
	}

	public void setSottocategorie(List<CategoriaMailinglistView> sottocategorie) {
		this.sottocategorie = sottocategorie;
	}

	public String[] getArrRubricaInvioEmail() {
		return arrRubricaInvioEmail;
	}

	public void setArrRubricaInvioEmail(String[] arrRubricaInvioEmail) {
		this.arrRubricaInvioEmail = arrRubricaInvioEmail;
	}

	public String getContenutoBreve() {
		return contenutoBreve;
	}

	public void setContenutoBreve(String contenutoBreve) {
		this.contenutoBreve = contenutoBreve;
	}

	@Override
	public String toString() {
		return "EmailView [emailId=" + emailId + ", oggetto=" + oggetto + "]";
	}



	
	

}
