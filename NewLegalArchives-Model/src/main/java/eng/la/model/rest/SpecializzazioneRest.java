package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SpecializzazioneRest {
	private long id;
	private List<String> specializzazioneDesc;
	private String descrizione;
	private String codGruppoLingua; 
	private Boolean voted;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getSpecializzazioneDesc() {
		return specializzazioneDesc;
	}

	public void setSpecializzazioneDesc(List<String> specializzazioneDesc) {
		this.specializzazioneDesc = specializzazioneDesc;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public Boolean getVoted() {
		return voted;
	}

	public void setVoted(Boolean voted) {
		this.voted = voted;
	}

	
}
