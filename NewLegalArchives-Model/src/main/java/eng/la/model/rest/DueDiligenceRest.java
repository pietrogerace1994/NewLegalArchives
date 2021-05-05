package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DueDiligenceRest {
	
	private long id;
	private String dataCancellazione;
	private String dataApertura;
	private String dataChiusura;
	private long statoDueDiligenceId;
	private String statoCodGruppoLingua;
	private long professionistaId;
	private String nomeFileStep1;
	List<DocumentoDueDiligenceRest> fileStep2List;
	private String nomeFileStep3;
	
	private String idFileStep1;
	private String idFileStep3;
	
	public String getIdFileStep1() {
		return idFileStep1;
	}
	public void setIdFileStep1(String idFileStep1) {
		this.idFileStep1 = idFileStep1;
	}
	public String getIdFileStep3() {
		return idFileStep3;
	}
	public void setIdFileStep3(String idFileStep3) {
		this.idFileStep3 = idFileStep3;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDataCancellazione() {
		return dataCancellazione;
	}
	public void setDataCancellazione(String dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	public String getStatoCodGruppoLingua() {
		return statoCodGruppoLingua;
	}
	public void setStatoCodGruppoLingua(String statoCodGruppoLingua) {
		this.statoCodGruppoLingua = statoCodGruppoLingua;
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
	public long getStatoDueDiligenceId() {
		return statoDueDiligenceId;
	}
	public void setStatoDueDiligenceId(long statoDueDiligenceId) {
		this.statoDueDiligenceId = statoDueDiligenceId;
	}
	public long getProfessionistaId() {
		return professionistaId;
	}
	public void setProfessionistaId(long professionistaId) {
		this.professionistaId = professionistaId;
	}
	public String getNomeFileStep1() {
		return nomeFileStep1;
	}
	public void setNomeFileStep1(String nomeFileStep1) {
		this.nomeFileStep1 = nomeFileStep1;
	}
	public List<DocumentoDueDiligenceRest> getFileStep2List() {
		return fileStep2List;
	}
	public void setFileStep2List(List<DocumentoDueDiligenceRest> fileStep2List) {
		this.fileStep2List = fileStep2List;
	}
	public String getNomeFileStep3() {
		return nomeFileStep3;
	}
	public void setNomeFileStep3(String nomeFileStep3) {
		this.nomeFileStep3 = nomeFileStep3;
	}
	
}
