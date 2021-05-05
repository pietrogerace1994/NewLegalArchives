package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PROPERTIES database table.
 * 
 */
@Entity
@Table(name="PROPERTIES")
@NamedQuery(name="Property.findByKey", query="SELECT r FROM Property r where r.key = :key")
public class Property implements Serializable {

	private static final long serialVersionUID = 86627719521978179L;

	@Id
	@Column(name="\"KEY\"")
	private String key;

	@Column(name="\"VALUE\"")
	private String value;

	public Property() {
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}