package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaBeautyContestRest {
	private long total;
	private List<BeautyContestRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<BeautyContestRest> getRows() {
		return rows;
	}

	public void setRows(List<BeautyContestRest> rows) {
		this.rows = rows;
	}
}
