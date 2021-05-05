package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NewsletterRest {
	private long id;
	private String numero;
	private String anno;
	private String titolo; 
	private String stato;
	private String azioni; 
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
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

	

	

}
