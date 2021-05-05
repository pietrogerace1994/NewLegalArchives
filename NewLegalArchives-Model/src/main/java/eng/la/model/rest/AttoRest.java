package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AttoRest {

	
	private long id;
	private String numeroProtocollo;
	private String societa;
	private String categoriaAtto;
	private String owner;
	private String unitaLegale;
	private String tipoAtto;
	private String statoAtto;
	private String azioni;
	private String statoCodice;
	private String pathFilenet;
	
	public AttoRest() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

 
  
	 
	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

 

	 

	public String getCategoriaAtto() {
		return this.categoriaAtto;
	}

	public void setCategoriaAtto(String categoriaAtto) {
		this.categoriaAtto = categoriaAtto;
	}

	 
	public String getSocieta() {
		return this.societa;
	}

	public void setSocieta(String societa) {
		this.societa = societa;
	}

	
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getUnitaLegale() {
		return unitaLegale;
	}

	public void setUnitaLegale(String unitaLegale) {
		this.unitaLegale = unitaLegale;
	}

	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}
	
	public String getTipoAtto() {
		return tipoAtto;
	}
	
	public String getStatoAtto() {
		return this.statoAtto;
	}

	public void setStatoAtto(String statoAtto) {
		this.statoAtto = statoAtto;
	}

 
	public String getAzioni() {
		return azioni;
	}
	
	public void setAzioni(String azioni) {
		this.azioni = azioni;
	}

	public String getStatoCodice() {
		return statoCodice;
	}

	public void setStatoCodice(String statoCodice) {
		this.statoCodice = statoCodice;
	}

	public String getPathFilenet() {
		return pathFilenet;
	}

	public void setPathFilenet(String pathFilenet) {
		this.pathFilenet = pathFilenet;
	}

	 

}