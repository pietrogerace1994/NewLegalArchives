package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@XmlRootElement(namespace = AbstractRest.NAMESPACE, name="StringRest")
@JsonDeserialize(as=StringRest.class)
public class StringRest extends AbstractRest {

	private String value;

	public StringRest() { 
		super.setType(StringRest.class);
	}

	public StringRest(String value) {
		this.value = value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
