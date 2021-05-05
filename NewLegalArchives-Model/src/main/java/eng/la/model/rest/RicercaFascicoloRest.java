package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaFascicoloRest {
	private long total;
	private List<FascicoloRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<FascicoloRest> getRows() {
		return rows;
	}

	public void setRows(List<FascicoloRest> rows) {
		this.rows = rows;
	}

}
