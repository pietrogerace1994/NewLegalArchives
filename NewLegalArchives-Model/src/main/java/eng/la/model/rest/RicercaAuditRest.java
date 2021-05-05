package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaAuditRest {
	private long total;
	private List<AuditRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<AuditRest> getRows() {
		return rows;
	}

	public void setRows(List<AuditRest> rows) {
		this.rows = rows;
	}

}
