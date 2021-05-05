package eng.la.model.view;

import java.util.List;
import java.util.Set;

import eng.la.model.Progetto;

public class ProgettoView extends BaseView {

	private static final long serialVersionUID = 1L;
	private Progetto vo;
	
	// Campi Insert
	private String dataCreazione;
	private String dataChiusura;
	private String oggetto;
	private String nome;
	private String descrizione;
	
//	private List<MultipartFile> files;
	private List<DocumentoView> listaDocumenti;
	private List<DocumentoView> listaAllegatiGenerici;
	private Long progettoId;
	
	private Set<String> allegatiDaRimuovereUuid;


	public Progetto getVo() {
		return vo;
	}
	public void setVo(Progetto vo) {
		this.vo = vo;
	}
	public String getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public String getDataChiusura() {
		return dataChiusura;
	}
	public void setDataChiusura(String dataChiusura) {
		this.dataChiusura = dataChiusura;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
//	public List<MultipartFile> getFiles() {
//		return files;
//	}
//	public void setFiles(List<MultipartFile> files) {
//		this.files = files;
//	}
	public List<DocumentoView> getListaDocumenti() {
		return listaDocumenti;
	}
	public void setListaDocumenti(List<DocumentoView> listaDocumenti) {
		this.listaDocumenti = listaDocumenti;
	}
	public List<DocumentoView> getListaAllegatiGenerici() {
		return listaAllegatiGenerici;
	}
	public void setListaAllegatiGenerici(List<DocumentoView> listaAllegatiGenerici) {
		this.listaAllegatiGenerici = listaAllegatiGenerici;
	}
	public Long getProgettoId() {
		return progettoId;
	}
	public void setProgettoId(Long progettoId) {
		this.progettoId = progettoId;
	}
	public Set<String> getAllegatiDaRimuovereUuid() {
		return allegatiDaRimuovereUuid;
	}
	public void setAllegatiDaRimuovereUuid(Set<String> allegatiDaRimuovereUuid) {
		this.allegatiDaRimuovereUuid = allegatiDaRimuovereUuid;
	}
	
}
