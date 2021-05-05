package velocity;

import java.util.List;

public class PageObject {
	private String titolo;
	private String pageClass;
	private String pageId;
	private String formId;
	
	
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	private List<FormObject> formObjects;

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getPageClass() {
		return pageClass;
	}

	public void setPageClass(String pageClass) {
		this.pageClass = pageClass;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public List<FormObject> getFormObjects() {
		return formObjects;
	}

	public void setFormObjects(List<FormObject> formObjects) {
		this.formObjects = formObjects;
	}

}
