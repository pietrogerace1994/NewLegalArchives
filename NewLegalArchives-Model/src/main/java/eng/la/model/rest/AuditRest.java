package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuditRest {
	
	private String dataOra;

	private String nomeServer;

	private String nomeApp;

	private String operazione;

	private String nomeOggetto;

	private String userId;

	private String opzionale;

	private String valoreOld;

	private String valoreNew;

	private String note;

	public AuditRest() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDataOra() {
		return dataOra;
	}

	public void setDataOra(String dataOra) {
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
