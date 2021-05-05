package eng.la.model.rest.idol;

public class IdolRestResponse {

	private SearchResponseStatus searchResponseStatus;
	private Header header;
	private SearchResponsePayload searchResponsePayload;

	public SearchResponseStatus getSearchResponseStatus() {
		return searchResponseStatus;
	}

	public void setSearchResponseStatus(
			SearchResponseStatus searchResponseStatus) {
		this.searchResponseStatus = searchResponseStatus;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public SearchResponsePayload getSearchResponsePayload() {
		return searchResponsePayload;
	}

	public void setSearchResponsePayload(
			SearchResponsePayload searchResponsePayload) {
		this.searchResponsePayload = searchResponsePayload;
	}

}