package eng.la.model.rest.idol;

import java.util.List;

public class SearchResponseStatus {

	private String returnCode;
	private List<Error> error = null;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public List<Error> getError() {
		return error;
	}

	public void setError(List<Error> error) {
		this.error = error;
	}

}