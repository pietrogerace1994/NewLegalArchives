package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentoDueDiligenceRest {
	
	private long id;
	private long idDueDiligence;
	private long idDocumento;
	private String nomeFile;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdDueDiligence() {
		return idDueDiligence;
	}
	public void setIdDueDiligence(long idDueDiligence) {
		this.idDueDiligence = idDueDiligence;
	}
	public long getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(long idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
}
