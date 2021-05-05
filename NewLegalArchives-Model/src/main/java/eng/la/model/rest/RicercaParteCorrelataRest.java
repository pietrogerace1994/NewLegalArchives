package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaParteCorrelataRest {
	private long total;
	private List<ParteCorrelataRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<ParteCorrelataRest> getRows() {
		return rows;
	}

	public void setRows(List<ParteCorrelataRest> rows) {
		this.rows = rows;
	}

}
