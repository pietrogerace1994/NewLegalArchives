package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaEmailRest {
	private long total;
	private List<EmailRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<EmailRest> getRows() {
		return rows;
	}

	public void setRows(List<EmailRest> rows) {
		this.rows = rows;
	}

}
