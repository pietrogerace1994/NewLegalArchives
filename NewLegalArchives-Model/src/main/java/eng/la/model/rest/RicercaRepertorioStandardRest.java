package eng.la.model.rest;

import java.util.List;

public class RicercaRepertorioStandardRest {
	
	private long total;

	private List<RepertorioStandardRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<RepertorioStandardRest> getRows() {
		return rows;
	}

	public void setRows(List<RepertorioStandardRest> rows) {
		this.rows = rows;
	}



}
