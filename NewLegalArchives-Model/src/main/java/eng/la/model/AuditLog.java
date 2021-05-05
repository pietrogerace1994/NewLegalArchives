package eng.la.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="AUDIT_LOG")
public class AuditLog implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATAORA")
	private Date dataOra;
	@Id 
	@Column(name="NOME_SERVER")
	private String nomeServer;
	@Id 
	@Column(name="NOME_APP")
	private String nomeApp;
	@Id 
	private String operazione;
	@Id 
	@Column(name="NOME_OGGETTO")
	private String nomeOggetto;
	@Id 
	private String userId;
	@Id 
	private String opzionale;

	@Column(name="VALORE_OLD")
	private String valoreOld;
	
	@Column(name="VALORE_NEW")
	private String valoreNew;
	
	private String note;

	public Date getDataOra() {
		return dataOra;
	}

	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}

	public String getNomeServer() {
		return nomeServer;
	}

	public void setNomeServer(String nomeServer) {
		this.nomeServer = nomeServer;
	}

	public String getNomeApp() {
		return nomeApp;
	}

	public void setNomeApp(String nomeApp) {
		this.nomeApp = nomeApp;
	}

	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

	public String getNomeOggetto() {
		return nomeOggetto;
	}

	public void setNomeOggetto(String nomeOggetto) {
		this.nomeOggetto = nomeOggetto;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOpzionale() {
		return opzionale;
	}

	public void setOpzionale(String opzionale) {
		this.opzionale = opzionale;
	}

	public String getValoreOld() {
		return valoreOld;
	}

	public void setValoreOld(String valoreOld) {
		this.valoreOld = valoreOld;
	}

	public String getValoreNew() {
		return valoreNew;
	}

	public void setValoreNew(String valoreNew) {
		this.valoreNew = valoreNew;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	} 
	
	
}
