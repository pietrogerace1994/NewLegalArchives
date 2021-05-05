package eng.la.model.rest;

import java.util.List;

public class RicercaAffariSocietariRest {
	
	private long total;

	private List<AffariSocietariRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<AffariSocietariRest> getRows() {
		return rows;
	}

	public void setRows(List<AffariSocietariRest> rows) {
		this.rows = rows;
	}



}
