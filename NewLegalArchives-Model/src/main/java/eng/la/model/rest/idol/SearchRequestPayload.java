package eng.la.model.rest.idol;

import java.util.List;

public class SearchRequestPayload {

	private List<Filter> filter = null;
	private Sorter sorter;
	private Object paging;

	public List<Filter> getFilter() {
		return filter;
	}

	public void setFilter(List<Filter> filter) {
		this.filter = filter;
	}

	public Sorter getSorter() {
		return sorter;
	}

	public void setSorter(Sorter sorter) {
		this.sorter = sorter;
	}

	public Object getPaging() {
		return paging;
	}

	public void setPaging(Object paging) {
		this.paging = paging;
	}

}