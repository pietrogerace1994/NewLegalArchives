package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaCategoriaMailinglistRest {
	private long total;
	private List<CategoriaMailinglistRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<CategoriaMailinglistRest> getRows() {
		return rows;
	}

	public void setRows(List<CategoriaMailinglistRest> rows) {
		this.rows = rows;
	}

}
