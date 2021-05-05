package eng.la.model.rest.idol;

public class IdolRest {

	private Object header;
	private SearchRequestPayload searchRequestPayload;

	public Object getHeader() {
		return header;
	}

	public void setHeader(Object header) {
		this.header = header;
	}

	public SearchRequestPayload getSearchRequestPayload() {
		return searchRequestPayload;
	}

	public void setSearchRequestPayload(
			SearchRequestPayload searchRequestPayload) {
		this.searchRequestPayload = searchRequestPayload;
	}

}