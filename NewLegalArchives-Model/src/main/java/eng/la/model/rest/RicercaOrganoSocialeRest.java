package eng.la.model.rest;

import java.util.List;

public class RicercaOrganoSocialeRest {
	
	private long total;

	private List<OrganoSocialeRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<OrganoSocialeRest> getRows() {
		return rows;
	}

	public void setRows(List<OrganoSocialeRest> rows) {
		this.rows = rows;
	}



}
