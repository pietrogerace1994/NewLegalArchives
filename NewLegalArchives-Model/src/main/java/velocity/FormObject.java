package velocity;


public class FormObject {
	private String type = "text";
	private String label;
	private String name;
	private String placeolder;
	private String defaultValue = "";
	private String idHtml;
	private String classHtml;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPlaceolder() {
		return placeolder;
	}

	public void setPlaceolder(String placeolder) {
		this.placeolder = placeolder;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getIdHtml() {
		return idHtml;
	}

	public void setIdHtml(String idHtml) {
		this.idHtml = idHtml;
	}

	public String getClassHtml() {
		return classHtml;
	}

	public void setClassHtml(String classHtml) {
		this.classHtml = classHtml;
	}

}
