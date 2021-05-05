package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProtocolloRest {
	
	private long id;
	private String numeroProtocollo;
	private String mittente;
	private String destinatario;
	private String unita;
	private String oggetto;
	private String dataCreazione;
	private String nomeFascicolo;
	private String azioni;
	private String stato;
	
	
	

	public String getStato() {
		return stato;
	}


	public void setStato(String stato) {
		this.stato = stato;
	}


	public String getAzioni() {
		return azioni;
	}


	public void setAzioni(String azioni) {
		this.azioni = azioni;
	}


	public String getNomeFascicolo() {
		return nomeFascicolo;
	}


	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}


	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}


	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}


	public String getMittente() {
		return mittente;
	}


	public void setMittente(String mittente) {
		this.mittente = mittente;
	}


	public String getDestinatario() {
		return destinatario;
	}


	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}


	public String getUnita() {
		return unita;
	}


	public void setUnita(String unita) {
		this.unita = unita;
	}


	public String getOggetto() {
		return oggetto;
	}


	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}


	public String getDataCreazione() {
		return dataCreazione;
	}


	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	public ProtocolloRest() {
	}

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	

}