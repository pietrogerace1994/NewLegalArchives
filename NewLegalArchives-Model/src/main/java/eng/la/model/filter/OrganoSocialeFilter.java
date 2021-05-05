package eng.la.model.filter;

public class OrganoSocialeFilter {

	private String cognome;
	private String nome;
	private String incarica;
	private Long tipoOrganoSociale;
	private Long idSocietaAffari;
	private String order;
	private String sortBy;
	private String gruppoSnam;
	
	
	private int numElementiPerPagina;
	private int numeroPagina;	
	
	
	public OrganoSocialeFilter() {
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getIncarica() {
		return incarica;
	}


	public void setIncarica(String incarica) {
		this.incarica = incarica;
	}


	public Long getTipoOrganoSociale() {
		return tipoOrganoSociale;
	}


	public void setTipoOrganoSociale(Long tipoOrganoSociale) {
		this.tipoOrganoSociale = tipoOrganoSociale;
	}


	public Long getIdSocietaAffari() {
		return idSocietaAffari;
	}


	public void setIdSocietaAffari(Long idSocietaAffari) {
		this.idSocietaAffari = idSocietaAffari;
	}


	public String getOrder() {
		return order;
	}


	public void setOrder(String order) {
		this.order = order;
	}


	public String getSortBy() {
		return sortBy;
	}


	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
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


	public String getGruppoSnam() {
		return gruppoSnam;
	}


	public void setGruppoSnam(String gruppoSnam) {
		this.gruppoSnam = gruppoSnam;
	}

	

	


}
