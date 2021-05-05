package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SchedaFondoRischiRest {
	private long id;
	private String nomeFascicolo; 
	private String anno;
	private String stato;
	private String dataCreazione;
	private String dataAutorizzazione; 
	private String azioni;
	private String tipologia;
	private String rischioSoccombenza;
	private String rinvioVotazione;
	private String controparte;

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

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
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

	public String getRinvioVotazione() {
		return rinvioVotazione;
	}

	public void setRinvioVotazione(String votabile) {
		this.rinvioVotazione = votabile;
	}

	public String getDataAutorizzazione() {
		return dataAutorizzazione;
	}

	public void setDataAutorizzazione(String dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getRischioSoccombenza() {
		return rischioSoccombenza;
	}

	public void setRischioSoccombenza(String rischioSoccombenza) {
		this.rischioSoccombenza = rischioSoccombenza;
	}

	public String getControparte() {
		return controparte;
	}

	public void setControparte(String controparte) {
		this.controparte = controparte;
	}

}
