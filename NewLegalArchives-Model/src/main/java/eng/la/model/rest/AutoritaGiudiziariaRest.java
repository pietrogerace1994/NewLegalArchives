package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AutoritaGiudiziariaRest {
	
	private long id;
	private String autoritaGiudiziaria;
	private String dataCancellazione;
	private String dataInserimento;
	private String dataRicezione;
	private String oggetto;
	private long statoRichAutGiudId;
	private String statoCodGruppoLingua;
	private long tipologiaRichiestaId;
	private long idSocieta;
	private String nomeFileStep1;
	private String nomeFileStep2;
	private String nomeFileStep3;
	private String idFileStep1;
	private String idFileStep2;
	private String idFileStep3;
	private String fornitore;
	
	public String getIdFileStep1() {
		return idFileStep1;
	}
	public void setIdFileStep1(String idFileStep1) {
		this.idFileStep1 = idFileStep1;
	}
	public String getIdFileStep2() {
		return idFileStep2;
	}
	public void setIdFileStep2(String idFileStep2) {
		this.idFileStep2 = idFileStep2;
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
	public String getAutoritaGiudiziaria() {
		return autoritaGiudiziaria;
	}
	public void setAutoritaGiudiziaria(String autoritaGiudiziaria) {
		this.autoritaGiudiziaria = autoritaGiudiziaria;
	}
	public String getDataCancellazione() {
		return dataCancellazione;
	}
	public void setDataCancellazione(String dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
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
	public long getStatoRichAutGiudId() {
		return statoRichAutGiudId;
	}
	public void setStatoRichAutGiudId(long statoRichAutGiudId) {
		this.statoRichAutGiudId = statoRichAutGiudId;
	}
	public long getTipologiaRichiestaId() {
		return tipologiaRichiestaId;
	}
	public void setTipologiaRichiestaId(long tipologiaRichiestaId) {
		this.tipologiaRichiestaId = tipologiaRichiestaId;
	}
	public String getStatoCodGruppoLingua() {
		return statoCodGruppoLingua;
	}
	public void setStatoCodGruppoLingua(String statoCodGruppoLingua) {
		this.statoCodGruppoLingua = statoCodGruppoLingua;
	}
	public String getNomeFileStep1() {
		return nomeFileStep1;
	}
	public void setNomeFileStep1(String nomeFileStep1) {
		this.nomeFileStep1 = nomeFileStep1;
	}
	public String getNomeFileStep2() {
		return nomeFileStep2;
	}
	public void setNomeFileStep2(String nomeFileStep2) {
		this.nomeFileStep2 = nomeFileStep2;
	}
	public String getNomeFileStep3() {
		return nomeFileStep3;
	}
	public void setNomeFileStep3(String nomeFileStep3) {
		this.nomeFileStep3 = nomeFileStep3;
	}
	public long getIdSocieta() {
		return idSocieta;
	}
	public void setIdSocieta(long idSocieta) {
		this.idSocieta = idSocieta;
	}
	public String getFornitore() {
		return fornitore;
	}
	public void setFornitore(String fornitore) {
		this.fornitore = fornitore;
	}
}
