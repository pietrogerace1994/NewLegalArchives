package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CollegioArbitraleRest {
	private long id;
	private String nomeIncarico;
	private String commento;
	private String nomeCollegioArbitrale; 
	private String anno;
	private String stato;
	private String dataCreazione;
	private String azioni;

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
 
	public String getNomeCollegioArbitrale() {
		return nomeCollegioArbitrale;
	}

	public void setNomeCollegioArbitrale(String nomeCollegioArbitrale) {
		this.nomeCollegioArbitrale = nomeCollegioArbitrale;
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

}
