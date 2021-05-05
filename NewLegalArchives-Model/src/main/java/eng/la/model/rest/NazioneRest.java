package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NazioneRest {
	private long id;
	private List<String> nazioneDesc;
	private boolean soloParteCorrelata;
	private String codGruppoLingua;
	private String descrizione;
	private Boolean voted;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getNazioneDesc() {
		return nazioneDesc;
	}

	public void setNazioneDesc(List<String> nazioneDesc) {
		this.nazioneDesc = nazioneDesc;
	}

	public boolean isSoloParteCorrelata() {
		return soloParteCorrelata;
	}

	public void setSoloParteCorrelata(boolean soloParteCorrelata) {
		this.soloParteCorrelata = soloParteCorrelata;
	}

	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Boolean getVoted() {
		return voted;
	}

	public void setVoted(Boolean voted) {
		this.voted = voted;
	}

	
}
