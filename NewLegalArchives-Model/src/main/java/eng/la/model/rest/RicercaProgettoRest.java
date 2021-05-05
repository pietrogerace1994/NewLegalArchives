package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaProgettoRest{
	private long total;
	private List<ProgettoRest> rows;
	 
	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}
	
	public List<ProgettoRest> getRows() {
		return rows;
	}
	
	public void setRows(List<ProgettoRest> rows) {
		this.rows = rows;
	}

}
