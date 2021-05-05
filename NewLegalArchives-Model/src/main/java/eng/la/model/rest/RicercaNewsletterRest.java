package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaNewsletterRest {
	private long total;
	private List<NewsletterRest> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<NewsletterRest> getRows() {
		return rows;
	}

	public void setRows(List<NewsletterRest> rows) {
		this.rows = rows;
	}

}
