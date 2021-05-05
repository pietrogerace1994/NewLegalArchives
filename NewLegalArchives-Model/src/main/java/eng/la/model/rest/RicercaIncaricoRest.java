package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaIncaricoRest {
	private long total;
	private List<IncaricoRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<IncaricoRest> getRows() {
		return rows;
	}

	public void setRows(List<IncaricoRest> rows) {
		this.rows = rows;
	}

}
