package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaUtilRest {
	long total;
	List<UtilRest> rows;
	
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<UtilRest> getRows() {
		return rows;
	}
	public void setRows(List<UtilRest> rows) {
		this.rows = rows;
	}

	
}
