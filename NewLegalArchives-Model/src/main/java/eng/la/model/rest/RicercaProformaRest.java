package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaProformaRest {
	private long total;
	private List<ProformaRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<ProformaRest> getRows() {
		return rows;
	}

	public void setRows(List<ProformaRest> rows) {
		this.rows = rows;
	}

}
