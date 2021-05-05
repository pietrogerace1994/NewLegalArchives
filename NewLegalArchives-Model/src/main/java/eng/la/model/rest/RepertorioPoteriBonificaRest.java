package eng.la.model.rest;

public class RepertorioPoteriBonificaRest {
	
	private String codice;
	private String descrizione;
	private String testo;
	private String categoria;
	private String subcategoria;
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getSubcategoria() {
		return subcategoria;
	}
	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}
	@Override
	public String toString() {
		return "RepertorioPoteriBonificaRest [codice=" + codice + ", descrizione=" + descrizione + ", testo=" + testo
				+ ", categoria=" + categoria + ", subcategoria=" + subcategoria + "]";
	}
	
	
		


}
