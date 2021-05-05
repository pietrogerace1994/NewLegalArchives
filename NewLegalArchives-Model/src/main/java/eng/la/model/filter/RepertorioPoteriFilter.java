package eng.la.model.filter;

public class RepertorioPoteriFilter {

	private String codice;
	private String descrizione;
	private String testo;
	private String lingua;
	private Long idCategoria;
	private Long idSubcategoria;
	private String order;
	
	
	private int numElementiPerPagina;
	private int numeroPagina;	
	
	
	public RepertorioPoteriFilter() {
	}


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


	public String getLingua() {
		return lingua;
	}


	public void setLingua(String lingua) {
		this.lingua = lingua;
	}


	public Long getIdCategoria() {
		return idCategoria;
	}


	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}


	public Long getIdSubcategoria() {
		return idSubcategoria;
	}


	public void setIdSubcategoria(Long idSubcategoria) {
		this.idSubcategoria = idSubcategoria;
	}


	public int getNumElementiPerPagina() {
		return numElementiPerPagina;
	}


	public void setNumElementiPerPagina(int numElementiPerPagina) {
		this.numElementiPerPagina = numElementiPerPagina;
	}


	public int getNumeroPagina() {
		return numeroPagina;
	}


	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}


	public String getOrder() {
		return order;
	}


	public void setOrder(String order) {
		this.order = order;
	}



	


}
