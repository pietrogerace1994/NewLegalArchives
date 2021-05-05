package eng.la.model.rest;

import java.util.List;

public class RicercaRepertorioPoteriRest {
	
	private long total;

	private List<RepertorioPoteriRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<RepertorioPoteriRest> getRows() {
		return rows;
	}

	public void setRows(List<RepertorioPoteriRest> rows) {
		this.rows = rows;
	}



}
