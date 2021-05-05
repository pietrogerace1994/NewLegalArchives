package eng.la.model.rest;

import java.util.List;

public class RicercaProcureRest {
	private long total;
	private List<ProcureRest> rows;
	 
	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}
	
	public List<ProcureRest> getRows() {
		return rows;
	}
	
	public void setRows(List<ProcureRest> rows) {
		this.rows = rows;
	}

}
