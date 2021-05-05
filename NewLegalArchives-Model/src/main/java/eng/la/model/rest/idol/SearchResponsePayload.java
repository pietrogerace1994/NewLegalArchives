package eng.la.model.rest.idol;

import java.util.List;

public class SearchResponsePayload {

	private PagingResponse pagingResponse;
	private List<RowView> rowView = null;

	public PagingResponse getPagingResponse() {
		return pagingResponse;
	}

	public void setPagingResponse(PagingResponse pagingResponse) {
		this.pagingResponse = pagingResponse;
	}

	public List<RowView> getRowView() {
		return rowView;
	}

	public void setRowView(List<RowView> rowView) {
		this.rowView = rowView;
	}

}