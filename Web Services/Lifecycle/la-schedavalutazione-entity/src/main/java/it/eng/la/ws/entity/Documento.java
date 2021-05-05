package it.eng.la.ws.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DOCUMENTI")
public class Documento implements Serializable {

	private static final long serialVersionUID = -1654693423008022108L;

	@Id
	private long id;

	@Column(name = "NOME_FILE")
	private String nomeFile;

	@Column(name = "CONTENT_TYPE")
	private String contentType;

	private String uuid;

	@Column(name = "CLASSE_DOCUMENTALE")
	private String classeDocumentale;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getClasseDocumentale() {
		return classeDocumentale;
	}

	public void setClasseDocumentale(String classeDocumentale) {
		this.classeDocumentale = classeDocumentale;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

}
