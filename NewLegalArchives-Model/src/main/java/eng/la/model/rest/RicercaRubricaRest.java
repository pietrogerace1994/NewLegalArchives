package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaRubricaRest {
	private long total;
	private List<RubricaRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<RubricaRest> getRows() {
		return rows;
	}

	public void setRows(List<RubricaRest> rows) {
		this.rows = rows;
	}

}
