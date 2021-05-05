package eng.la.model.rest.idol;

public class Filter {

	private Object complexFilter;
	private SimpleFilter simpleFilter;

	public Object getComplexFilter() {
		return complexFilter;
	}

	public void setComplexFilter(Object complexFilter) {
		this.complexFilter = complexFilter;
	}

	public SimpleFilter getSimpleFilter() {
		return simpleFilter;
	}

	public void setSimpleFilter(SimpleFilter simpleFilter) {
		this.simpleFilter = simpleFilter;
	}

}