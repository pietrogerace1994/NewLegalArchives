package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaCollegioArbitraleRest {
	private long total;
	private List<CollegioArbitraleRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<CollegioArbitraleRest> getRows() {
		return rows;
	}

	public void setRows(List<CollegioArbitraleRest> rows) {
		this.rows = rows;
	}

}
