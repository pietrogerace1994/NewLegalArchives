package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@XmlRootElement(namespace = AbstractRest.NAMESPACE, name="AgendaWebsocketRest")
@JsonDeserialize(as=AgendaWebsocketRest.class)
public class AgendaWebsocketRest extends AbstractRest {

	private long idFascicolo;
	private String nomeFascicolo;
	private String descrizioneFascicolo;
	private String oggetto;
	private String descrizione;
	private String dataEvento;
	private String dataScadenza;
	private long id;
	
	public AgendaWebsocketRest() { 
		super.setType(AgendaWebsocketRest.class);
	}

	public long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}

	public String getDescrizioneFascicolo() {
		return descrizioneFascicolo;
	}

	public void setDescrizioneFascicolo(String descrizioneFascicolo) {
		this.descrizioneFascicolo = descrizioneFascicolo;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(String dataEvento) {
		this.dataEvento = dataEvento;
	}

	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	


}
