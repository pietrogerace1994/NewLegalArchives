package eng.la.model.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FascicoloRest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String nome;
	private String dataCreazione;
	private String oggetto;
	private String stato;
	private String legaleEsterno;
	private String controparte;
	private String anno;
	private String tipologiaAtto;
	private String tipoAzione;
	private String azioni;
	private String pathFilenet;
	private String owner;
	private String idProgetto;
	private String societaAddebito;
	private String societaProcedimento;
	
	
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

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getLegaleEsterno() {
		return legaleEsterno;
	}

	public void setLegaleEsterno(String legaleEsterno) {
		this.legaleEsterno = legaleEsterno;
	}

	public String getControparte() {
		return controparte;
	}

	public void setControparte(String controparte) {
		this.controparte = controparte;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getTipologiaAtto() {
		return tipologiaAtto;
	}

	public void setTipologiaAtto(String tipologiaAtto) {
		this.tipologiaAtto = tipologiaAtto;
	}

	public String getTipoAzione() {
		return tipoAzione;
	}

	public void setTipoAzione(String tipoAzione) {
		this.tipoAzione = tipoAzione;
	}

	public String getPathFilenet() {
		return pathFilenet;
	}

	public void setPathFilenet(String pathFilenet) {
		this.pathFilenet = pathFilenet;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(String idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getSocietaAddebito() {
		return societaAddebito;
	}

	public void setSocietaAddebito(String societaAddebito) {
		this.societaAddebito = societaAddebito;
	}

	public String getSocietaProcedimento() {
		return societaProcedimento;
	}

	public void setSocietaProcedimento(String societaProcedimento) {
		this.societaProcedimento = societaProcedimento;
	}
	
}
