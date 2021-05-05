package eng.la.model.rest.idol;

public class PagingResponse {

	private Integer returnedRows;
	private Integer totalRows;

	public Integer getReturnedRows() {
		return returnedRows;
	}

	public void setReturnedRows(Integer returnedRows) {
		this.returnedRows = returnedRows;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

}