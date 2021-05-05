package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaAttoRest{
	private long total;
	private List<AttoRest> rows;
	 
	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}
	
	public List<AttoRest> getRows() {
		return rows;
	}
	
	public void setRows(List<AttoRest> rows) {
		this.rows = rows;
	}

}
