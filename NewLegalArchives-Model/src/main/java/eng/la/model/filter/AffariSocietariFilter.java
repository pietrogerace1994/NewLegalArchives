package eng.la.model.filter;

public class AffariSocietariFilter {

	private String denominazione;
	private Long idNazione;
	private String quotazione;
	private String modelloDiGovernance;
	private String storico;
	private String gruppoSnam; 

	
	private String order;
	
	private int numElementiPerPagina;
	private int numeroPagina;	
	
	
	public AffariSocietariFilter() {
	}


	public String getDenominazione() {
		return denominazione;
	}


	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}


	public Long getIdNazione() {
		return idNazione;
	}


	public void setIdNazione(Long idNazione) {
		this.idNazione = idNazione;
	}


	public String getQuotazione() {
		return quotazione;
	}


	public void setQuotazione(String quotazione) {
		this.quotazione = quotazione;
	}


	public String getModelloDiGovernance() {
		return modelloDiGovernance;
	}


	public void setModelloDiGovernance(String modelloDiGovernance) {
		this.modelloDiGovernance = modelloDiGovernance;
	}


	public String getStorico() {
		return storico;
	}


	public void setStorico(String storico) {
		this.storico = storico;
	}


	public String getOrder() {
		return order;
	}


	public void setOrder(String order) {
		this.order = order;
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
