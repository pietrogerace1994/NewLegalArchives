package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UdienzaRest {
	private long id;
	private String nomeFascicolo; 
	private String anno;
	private String dataCreazione;
	private String dataUdienza; 
	private String azioni;
	private String ownerFascicolo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}
 
	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getAzioni() {
		return azioni;
	}

	public void setAzioni(String azioni) {
		this.azioni = azioni;
	}

	public String getDataUdienza() {
		return dataUdienza;
	}

	public void setDataUdienza(String dataUdienza) {
		this.dataUdienza = dataUdienza;
	}

	public String getOwnerFascicolo() {
		return ownerFascicolo;
	}

	public void setOwnerFascicolo(String ownerFascicolo) {
		this.ownerFascicolo = ownerFascicolo;
	}
}
