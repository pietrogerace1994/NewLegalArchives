package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProcureSpecialiRest {

	private String tipologia;
	private String assegnatario;
	private String inizio;
	private String societa;
	private String autoreUltimaModifica;
	private String nomeFile;
	
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getAssegnatario() {
		return assegnatario;
	}
	public void setAssegnatario(String assegnatario) {
		this.assegnatario = assegnatario;
	}
	public String getInizio() {
		return inizio;
	}
	public void setInizio(String inizio) {
		this.inizio = inizio;
	}
	public String getSocieta() {
		return societa;
	}
	public void setSocieta(String societa) {
		this.societa = societa;
	}
	public String getAutoreUltimaModifica() {
		return autoreUltimaModifica;
	}
	public void setAutoreUltimaModifica(String autoreUltimaModifica) {
		this.autoreUltimaModifica = autoreUltimaModifica;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	@Override
	public String toString() {
		return "ProcureSpecialiRest [tipologia=" + tipologia + ", assegnatario=" + assegnatario + ", inizio=" + inizio
				+ ", societa=" + societa + ", autoreUltimaModifica=" + autoreUltimaModifica + ", nomeFile=" + nomeFile
				+ "]";
	}
	
	
	
}
