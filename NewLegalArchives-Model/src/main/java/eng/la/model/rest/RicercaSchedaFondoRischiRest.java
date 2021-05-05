package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaSchedaFondoRischiRest {
	private long total;
	private List<SchedaFondoRischiRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<SchedaFondoRischiRest> getRows() {
		return rows;
	}

	public void setRows(List<SchedaFondoRischiRest> rows) {
		this.rows = rows;
	}

}
