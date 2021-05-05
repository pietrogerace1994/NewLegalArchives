package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaUdienzaRest {
	private long total;
	private List<UdienzaRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<UdienzaRest> getRows() {
		return rows;
	}

	public void setRows(List<UdienzaRest> rows) {
		this.rows = rows;
	}
}
