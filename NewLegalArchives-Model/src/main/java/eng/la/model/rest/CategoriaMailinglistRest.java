package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CategoriaMailinglistRest {
	private long id;
	private String codice;
	private String colore;
	private String nomeCategoria;
	private String categoriaFiglia;
	private String azioni;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public String getCategoriaFiglia() {
		return categoriaFiglia;
	}

	public void setCategoriaFiglia(String categoriaFiglia) {
		this.categoriaFiglia = categoriaFiglia;
	}

	public String getAzioni() {
		return azioni;
	}

	public void setAzioni(String azioni) {
		this.azioni = azioni;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}
	
	

	

	
	
}
