package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IncaricoRest {
	private long id;
	private String nomeIncarico;
	private String commento;
	private String nomeFascicolo; 
	private String anno;
	private String stato;
	private String dataCreazione;
	private String dataAutorizzazione; 
	private String azioni;
	private String rinvioVotazione;
	private String check;
	private String legaleInterno;
	private String controparte;
	private String statoFascicolo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeIncarico() {
		return nomeIncarico;
	}

	public void setNomeIncarico(String nomeIncarico) {
		this.nomeIncarico = nomeIncarico;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
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
	
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}

	public String getLegaleInterno() {
		return legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}

	public String getControparte() {
		return controparte;
	}

	public void setControparte(String controparte) {
		this.controparte = controparte;
	}

	public String getStatoFascicolo() {
		return statoFascicolo;
	}

	public void setStatoFascicolo(String statoFascicolo) {
		this.statoFascicolo = statoFascicolo;
	}
}
